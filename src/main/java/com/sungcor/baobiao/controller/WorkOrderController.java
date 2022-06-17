package com.sungcor.baobiao.controller;


import com.alibaba.fastjson.JSONObject;
import com.sungcor.baobiao.STSMConstant;
import com.sungcor.baobiao.entity.*;
import com.sungcor.baobiao.entity.Process;
import com.sungcor.baobiao.mapper.NodeInstanceMapper;
import com.sungcor.baobiao.mapper.ProcessInstanceMapper;
import com.sungcor.baobiao.mapper.ServiceProductMapper;
import com.sungcor.baobiao.report.service.IReportBrowseService;
import com.sungcor.baobiao.service.*;
import com.sungcor.baobiao.service.Impl.UserService;
import com.sungcor.baobiao.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class WorkOrderController {
    @Autowired
    IReportBrowseService IReportBrowseService;
    @Autowired
    private UserService userService;
    @Autowired
    private IServiceProductService serviceProductService;
    @Autowired
    private IProcessManagementService processManagementService;

    @Autowired
    private IDictItemService dictItemService;

    @Autowired
    private IFMFormService formService;

    @Autowired
    private IOperateFormService operateFormService;

    @Autowired
    private ServiceProductMapper serviceProductMapper;

    @Autowired
    private IProcessControlService processControlService;

    @Autowired
    private ProcessInstanceMapper processInstanceMapper;

    @Autowired
    private NodeInstanceMapper nodeInstanceMapper;

    @Autowired
    private IProcessStatusService processStatusService;

    @Autowired
    private IOrdersService ordersService;

    private final String LOCATION_PATTERN = "classpath:resources/webserviceConfig.properties";

    @GetMapping("/hello")
    public Map helloWorld() {
        Map map = new HashMap();
        map.put("String", "Hello,World!");
        return map;
    }

    @PostMapping("/getProducts")
    public String getProducts(@RequestBody Map map) {
        Map<String, Object> resultMap = new HashMap(STSMConstant.NUM_ONE);//初始化返回参数
        resultMap.put("result", STSMConstant.WS_RESULT_NORMAL);//默认返回正常
        resultMap.put("info", STSMConstant.STR_EMPTY);
        String data = map.get("userId").toString();
        try {
            if (StringUtils.isNotBlank(data)) {//验证参数
//                Properties properties = STSMApplicationContext.loadProperties(LOCATION_PATTERN);
                //验证必填参数
                Map parameter = new HashMap(STSMConstant.NUM_TWO);//创建查询参数
                /**
                 * 首先进行用户判断 如果非STSM内部用户只能查询固定选项
                 */
                List<UserInfoBean> userInfoBeans = userService.findByLoginID(data);
                if (null != userInfoBeans && userInfoBeans.size() > STSMConstant.NUM_ZERO) {
                    UserInfoBean userInfoBean = userInfoBeans.get(STSMConstant.NUM_ZERO);//判断用户是STSM内部用户还是外部用户，内部用户则需要添加组织过滤
                    List orgIds = userService.getUserOrgs(Integer.parseInt(userInfoBean.getUserId()));
                    parameter.put("list", orgIds);
                } else {
                    String idConfig = "";//获取服务产品固定所有配置信息
                    if (StringUtils.isNotBlank(idConfig)) {
                        String[] array = idConfig.split(",");
                        parameter.put("array", array);
                    }
                }

                List<ServiceProduct> products = serviceProductService.getServiceProductPaging(parameter);//进行查询
                if (!products.isEmpty()) { //如果为空代表未查询到配置的服务产品
                    List<Map> productList = new ArrayList<Map>(products.size());
                    Map valueMap = null;
                    int index = STSMConstant.NUM_ONE;
                    for (ServiceProduct product : products) { //参数封装，后续可以优化为通过sql查询，此处赶时间
                        valueMap = new HashMap(products.size());
                        valueMap.put("productId", product.getId());
                        valueMap.put("processId", product.getFlowId());
                        valueMap.put("name", product.getName());
                        valueMap.put("icon", product.getIcon());
                        valueMap.put("description", product.getDescription());
                        valueMap.put("sequence", index);
                        productList.add(valueMap);
                        index++;
                    }

                    resultMap.put("productSum", productList.size());
                    resultMap.put("datas", productList);
                    resultMap.put("info", "查询成功");
                } else {
                    resultMap.put("info", "未查询到数据！");
                    resultMap.put("result", STSMConstant.WS_RESULT_MISMATCH);
                }

            } else {
                resultMap.put("info", "请求参数不能为空！");
                resultMap.put("result", STSMConstant.WS_RESULT_SERVICE_EXCEPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("info", "接口异常");
            resultMap.put("result", STSMConstant.WS_RESULT_SYSTEM_EXCEPTION);
//            log.error("getProducts fail ：".concat(Util.getExceptionMessage(e)));
        }
        return JSONObject.toJSONString(resultMap);
    }

    @PostMapping("/goCreateWorkOrder")
    public String goCreateWorkOrder(@RequestBody Map map) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("result", STSMConstant.WS_RESULT_NORMAL);//默认返回正常
        resultObject.put("info", STSMConstant.STR_EMPTY);

        String productId = map.get("productId").toString();
        String processId = map.get("processId").toString();
        try {
            if (StringUtils.isNotBlank(processId) && StringUtils.isNotBlank(productId)) { //必须不能够为空
                //根据服务产品id进行信息加载
                Process process = processManagementService.getProcessById(Integer.parseInt(processId)); //查询服务流程与服务产品的信息
                ServiceProduct sp = serviceProductService.getServiceProductById(Integer.parseInt(productId));
                int formID = process.getFormID();
                FMForm fmeform = formService.getFMForm(formID);

                if (null != process && null != sp && null != fmeform) { //必须所有的参数都不为空才能够执行下一步
                    FormAccessControl busins = new FormAccessControl();
                    busins.setBusinessDef1(process.getProcessDefinitionID().toString());
                    busins.setBusinessDef2("nodeUniqueID:start");

                    List<FMFormArea> formAreas = operateFormService.showFormByControl(busins, fmeform.getVersionID()); //循环拼装表单对象
                    JSONObject fieldObject = null;
                    List<JSONObject> fieldList = new ArrayList<JSONObject>(STSMConstant.NUM_TWO_HUNDRED);
                    List<JSONObject> fieldConfigList = new ArrayList<JSONObject>(STSMConstant.NUM_TWO_HUNDRED);//已配置数据
                    FMFormField field = null;

                    for (int index = STSMConstant.NUM_ZERO; index < formAreas.size(); index++) {//进行表单的数据拼装
                        FMFormArea area = formAreas.get(index);
                        List<FMFormField> list = area.getFieldList();
                        for (int indexF = STSMConstant.NUM_ZERO; indexF < list.size(); indexF++) {
                            field = list.get(indexF);
                            fieldObject = new JSONObject();
                            fieldObject.put("sequence", indexF + STSMConstant.NUM_ONE);
                            fieldObject.put("label", field.getLabel());
                            fieldObject.put("name", field.getName());
                            fieldObject.put("isRequired", null != field.getIsRequired() ? field.getIsRequired() : STSMConstant.STR_ZERO);//1为必填 0 为不是必填
                            fieldObject.put("contentSize", field.getContentSize());
                            fieldObject.put("fullLine", StringUtils.isNotBlank(field.getFullLine()) ? field.getFullLine() : STSMConstant.STR_EMPTY);
                            fieldObject.put("group", index + STSMConstant.NUM_ONE);
                            fieldObject.put("groupName", area.getTitle());
                            fieldObject.put("defaultValue", StringUtils.isNotBlank(field.getDefaultValue()) ? field.getDefaultValue() : STSMConstant.STR_EMPTY);
                            fieldObject.put("option", STSMConstant.STR_EMPTY);
                            //类型设置text（文本框）
                            //	textarea（多行的文本输入）
                            //	select（下拉选择）
                            //	datetime（时间输入框）
                            //	rpopwin cpopwin  根据客户需求弹出框默认都设置下拉选项
                            if ("select".equals(field.getRenderType())) { //添加下拉框选项默认值
                                List<DictItem> items = dictItemService.getDictItems(field.getDataSource());
                                List<JSONObject> options = new ArrayList<JSONObject>(items.size());
                                JSONObject option = null;
                                for (DictItem item : items) {
                                    option = new JSONObject();
                                    option.put("id", item.getId());
                                    option.put("name", item.getName());
                                    options.add(option);
                                }
                                fieldObject.put("option", options);
                            }
                            fieldObject.put("type", field.getRenderType());
                            fieldList.add(fieldObject);
                            if (STSMConstant.STR_ONE.equals(field.getUseFlag())) {
                                fieldConfigList.add(fieldObject);
                            }
                        }
                    }

                    JSONObject dataObject = new JSONObject();
                    if (fieldConfigList.isEmpty()) {
                        dataObject.put("fieldList", fieldList); //如果未设置返回表单项 则返回所有
                    } else {
                        dataObject.put("fieldList", fieldConfigList);
                    }
                    dataObject.put("productId", sp.getId());
                    dataObject.put("processId", process.getId());
                    dataObject.put("name", sp.getName());//设置服务产品名称
                    dataObject.put("status", "新建"); //状态
                    dataObject.put("icon", sp.getIcon());//图标
                    resultObject.put("datas", dataObject);
                    resultObject.put("info", "查询成功");
                } else {
                    resultObject.put("info", "服务产品不存在");
                    resultObject.put("result", STSMConstant.WS_RESULT_MISMATCH);
                }

            } else {
                resultObject.put("info", "请求参数不能为空！");
                resultObject.put("result", STSMConstant.WS_RESULT_SERVICE_EXCEPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.put("info", "接口异常");
            resultObject.put("result", STSMConstant.WS_RESULT_SYSTEM_EXCEPTION);
//            log.error("goCreateWorkOrder fail ：".concat(Util.getExceptionMessage(e)));
        }

        return resultObject.toString();
    }

    @PostMapping("/getWorkOrderDetails")
    public String getWorkOrderDetails(@RequestBody Map map2) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("result", STSMConstant.WS_RESULT_NORMAL);//默认返回正常
        resultObject.put("info", STSMConstant.STR_EMPTY);
        String data = map2.get("processInstanceId").toString();
        try {
            if (StringUtils.isNotBlank(data)) {
                int processInstanceID = Integer.parseInt(data);
                ProcessInstance pro = processControlService.getProcessInstance(processInstanceID);
                if (null != pro) {
                    Process process = processManagementService.getProcessById(pro.getProcessID()); //查询服务流程与服务产品的信息
                    Map para = new HashMap();
                    para.put("pid", processInstanceID);
                    ServiceProduct sp = serviceProductService.getServiceProductById(serviceProductMapper.getServiceProduceProcessInstance(para).getServiceProductId());
                    int formID = process.getFormID();
                    FMForm fmeform = formService.getFMForm(formID);

                    if (null != process && null != sp && null != fmeform) { //必须所有的参数都不为空才能够执行下一步
                        FormAccessControl busins = new FormAccessControl();
                        busins.setBusinessDef1(process.getProcessDefinitionID().toString());
                        busins.setBusinessDef2("nodeUniqueID:start");

//                            Map processStatus = processStatusService.getAll(Util.getRootPath()+"/conf/bpm");//生成状态Map
                        FMFormInstance formInstance = operateFormService.getFormFieldValueById(pro.getId());
                        List<FMFormArea> formAreas = operateFormService.showFormByControl(busins, fmeform.getVersionID()); //循环拼装表单对象

                        Map<String, String> valueFields = ordersService.setTypeValue(formAreas, formInstance.getFields(), formInstance.getReporterSource());

                        JSONObject fieldObject = null;
                        List<JSONObject> fieldList = new ArrayList<JSONObject>(STSMConstant.NUM_TWO_HUNDRED);//数据
                        List<JSONObject> fieldConfigList = new ArrayList<JSONObject>(STSMConstant.NUM_TWO_HUNDRED);//已配置数据
                        FMFormField field = null;

                        for (int index = STSMConstant.NUM_ZERO; index < formAreas.size(); index++) {
                            FMFormArea area = formAreas.get(index);
                            List<FMFormField> list = area.getFieldList();
                            for (int indexF = STSMConstant.NUM_ZERO; indexF < list.size(); indexF++) {
                                field = list.get(indexF);
                                fieldObject = new JSONObject();
                                fieldObject.put("sequence", indexF + STSMConstant.NUM_ONE);
                                fieldObject.put("label", field.getLabel());
                                fieldObject.put("name", field.getName());
                                fieldObject.put("isRequired", null != field.getIsRequired() ? field.getIsRequired() : STSMConstant.STR_ZERO);//1为必填 0 为不是必填
                                fieldObject.put("type", field.getRenderType());
                                fieldObject.put("contentSize", field.getContentSize());
                                fieldObject.put("fullLine", StringUtils.isNotBlank(field.getFullLine()) ? field.getFullLine() : STSMConstant.STR_EMPTY);
                                fieldObject.put("group", index + STSMConstant.NUM_ONE);
                                fieldObject.put("groupName", area.getTitle());
                                if ("select".equals(field.getRenderType())) { //添加下拉框选项默认值
                                    List<DictItem> items = dictItemService.getDictItems(field.getDataSource());
                                    List<JSONObject> options = new ArrayList<JSONObject>(items.size());
                                    JSONObject option = null;
                                    for (DictItem item : items) {
                                        option = new JSONObject();
                                        option.put("id", item.getId());
                                        option.put("name", item.getName());
                                        options.add(option);
                                    }
                                    fieldObject.put("option", options);
                                } else {
                                    fieldObject.put("option", STSMConstant.STR_EMPTY);
                                }
                                String defaultValue = valueFields.get(field.getName());
                                fieldObject.put("defaultValue", StringUtils.isNotBlank(defaultValue) ? defaultValue : STSMConstant.STR_EMPTY);
                                fieldList.add(fieldObject);
                                if (STSMConstant.STR_ONE.equals(field.getUseFlag())) {
                                    fieldConfigList.add(fieldObject);
                                }
                            }
                        }

                        JSONObject dataObject = new JSONObject();
                        Map map = processControlService.selectVisitEvaluation(pro.getId());
                        if (STSMConstant.STR_ZERO.equals(pro.getStates()) && null == map) {//回访评价标识
                            dataObject.put("isEvaluate", "true");
                        } else {
                            dataObject.put("isEvaluate", "false");
                        }
                        Set<NodeHistory> runNodeHistorys = processControlService.getRunNodeHistorys(pro);
                        if (null != runNodeHistorys && !STSMConstant.STR_ZERO.equals(pro.getStates())) { //如果为空 和为已经关闭则不能
                            int QBPMNodeInstanceID = STSMConstant.NUM_ZERO;
                            int i = STSMConstant.NUM_ZERO;
                            for (NodeHistory nodeHis : runNodeHistorys) {
                                if (i == STSMConstant.NUM_ZERO) {
                                    QBPMNodeInstanceID = nodeHis.getNodeInstanceID();
                                    break;
                                }
                            }
                            NodeInstance qbpmNodeInstance = nodeInstanceMapper.get(QBPMNodeInstanceID);//判断是否可以撤销
                            ProcessDefinition qpd = processManagementService.getProcessDefinitionById(pro.getProcessDefinitionID());
                            dataObject.put("isRevoke", "true");
                        } else {
                            dataObject.put("isRevoke", "false");
                        }
                        if (fieldConfigList.isEmpty()) {
                            dataObject.put("fieldList", fieldList); //如果未设置返回表单项 则返回所有
                        } else {
                            dataObject.put("fieldList", fieldConfigList);
                        }
                        if (STSMConstant.STR_ONE.equals(pro.getStates())) { //状态为1 代表操作 可以保存和发布
                            dataObject.put("isSave", "true");
                            dataObject.put("isPublish", "true");
                        } else {
                            dataObject.put("isSave", "false");
                            dataObject.put("isPublish", "false");
                        }
                        List<BusinessOpt> opts = processControlService.listBusinessOpts(processInstanceID);//设置日志
                        List logList = new ArrayList(opts.size());
                        JSONObject log = null;
                        for (BusinessOpt opt : opts) {
                            log = new JSONObject();
                            log.put("id", opt.getId());
                            log.put("userName", opt.getOptPerson().getUserName());
                            log.put("remarks", opt.getOptReason() == null ? "" : opt.getOptReason());
                            log.put("optTime", DateUtil.fmtDate(opt.getOptTime(), null));
                            if ("start".equals(opt.getOptType())) {
                                log.put("optContent", "创建");
                            } else if ("singin".equals(opt.getOptType())) {
                                log.put("optContent", "签收");
                            } else if ("refuse".equals(opt.getOptType())) {
                                log.put("optContent", "拒绝");
                            } else if ("deal".equals(opt.getOptType())) {
                                log.put("optContent", "处理");
                            } else if ("audit".equals(opt.getOptType())) {
                                log.put("optContent", "审批");
                            } else if ("confirm".equals(opt.getOptType())) {
                                log.put("optContent", "关闭");
                            } else if ("retake".equals(opt.getOptType())) {
                                log.put("optContent", "取回");
                            } else if ("terminate".equals(opt.getOptType())) {
                                log.put("optContent", "中止");
                            } else if ("decision".equals(opt.getOptType())) {
                                log.put("optContent", "提交决策");
                            } else if ("review".equals(opt.getOptType())) {
                                log.put("optContent", "多人处理");
                            } else {
                                log.put("optContent", opt.getOptType());
                            }
                            logList.add(log);
                        }
                        //评价信息设置
                        Map visitEvaluation = processInstanceMapper.selectVisitEvaluation(processInstanceID);
                        if (null != visitEvaluation && null != visitEvaluation.get("EVALUATION_LEVEL")) {
                            JSONObject visit = new JSONObject();
                            visit.put("evaluationLevel", String.valueOf(visitEvaluation.get("EVALUATION_LEVEL")));
                            visit.put("opinions", String.valueOf(visitEvaluation.get("EVALUATION_LEVEL")));
                            visit.put("createTime", String.valueOf(visitEvaluation.get("CREATE_TIME")));
                            dataObject.put("evaluation", visit);
                        } else {
                            dataObject.put("evaluation", STSMConstant.STR_EMPTY);
                        }

                        JSONObject evaluateLevels = new JSONObject(); //评价级别设置
                        dataObject.put("evaluateLevels", evaluateLevels);
                        dataObject.put("logList", logList);
                        dataObject.put("productId", sp.getId()); //设置返回值
                        dataObject.put("processId", process.getId());
                        dataObject.put("name", sp.getName());
                        dataObject.put("status", STSMConstant.NUM_ONE);
                        dataObject.put("icon", sp.getIcon());
                        resultObject.put("info", "查询成功");
                        resultObject.put("datas", dataObject);
                    } else {
                        resultObject.put("info", "服务产品不存在");
                        resultObject.put("result", STSMConstant.WS_RESULT_MISMATCH);
                    }
                } else {
                    resultObject.put("info", "工单不存在!");
                    resultObject.put("result", STSMConstant.WS_RESULT_MISMATCH);
                }

            } else {
                resultObject.put("info", "参数不能为空");
                resultObject.put("result", STSMConstant.WS_RESULT_SERVICE_EXCEPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.put("info", "接口异常");
            resultObject.put("result", STSMConstant.WS_RESULT_SYSTEM_EXCEPTION);
        }
        return resultObject.toString();
    }
}
