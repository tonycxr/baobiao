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
        Map<String, Object> resultMap = new HashMap(STSMConstant.NUM_ONE);//?????????????????????
        resultMap.put("result", STSMConstant.WS_RESULT_NORMAL);//??????????????????
        resultMap.put("info", STSMConstant.STR_EMPTY);
        String data = map.get("userId").toString();
        try {
            if (StringUtils.isNotBlank(data)) {//????????????
//                Properties properties = STSMApplicationContext.loadProperties(LOCATION_PATTERN);
                //??????????????????
                Map parameter = new HashMap(STSMConstant.NUM_TWO);//??????????????????
                /**
                 * ???????????????????????? ?????????STSM????????????????????????????????????
                 */
                List<UserInfoBean> userInfoBeans = userService.findByLoginID(data);
                if (null != userInfoBeans && userInfoBeans.size() > STSMConstant.NUM_ZERO) {
                    UserInfoBean userInfoBean = userInfoBeans.get(STSMConstant.NUM_ZERO);//???????????????STSM????????????????????????????????????????????????????????????????????????
                    List orgIds = userService.getUserOrgs(Integer.parseInt(userInfoBean.getUserId()));
                    parameter.put("list", orgIds);
                } else {
                    String idConfig = "";//??????????????????????????????????????????
                    if (StringUtils.isNotBlank(idConfig)) {
                        String[] array = idConfig.split(",");
                        parameter.put("array", array);
                    }
                }

                List<ServiceProduct> products = serviceProductService.getServiceProductPaging(parameter);//????????????
                if (!products.isEmpty()) { //???????????????????????????????????????????????????
                    List<Map> productList = new ArrayList<Map>(products.size());
                    Map valueMap = null;
                    int index = STSMConstant.NUM_ONE;
                    for (ServiceProduct product : products) { //??????????????????????????????????????????sql????????????????????????
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
                    resultMap.put("info", "????????????");
                } else {
                    resultMap.put("info", "?????????????????????");
                    resultMap.put("result", STSMConstant.WS_RESULT_MISMATCH);
                }

            } else {
                resultMap.put("info", "???????????????????????????");
                resultMap.put("result", STSMConstant.WS_RESULT_SERVICE_EXCEPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("info", "????????????");
            resultMap.put("result", STSMConstant.WS_RESULT_SYSTEM_EXCEPTION);
//            log.error("getProducts fail ???".concat(Util.getExceptionMessage(e)));
        }
        return JSONObject.toJSONString(resultMap);
    }

    @PostMapping("/goCreateWorkOrder")
    public String goCreateWorkOrder(@RequestBody Map map) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("result", STSMConstant.WS_RESULT_NORMAL);//??????????????????
        resultObject.put("info", STSMConstant.STR_EMPTY);

        String productId = map.get("productId").toString();
        String processId = map.get("processId").toString();
        try {
            if (StringUtils.isNotBlank(processId) && StringUtils.isNotBlank(productId)) { //?????????????????????
                //??????????????????id??????????????????
                Process process = processManagementService.getProcessById(Integer.parseInt(processId)); //??????????????????????????????????????????
                ServiceProduct sp = serviceProductService.getServiceProductById(Integer.parseInt(productId));
                int formID = process.getFormID();
                FMForm fmeform = formService.getFMForm(formID);

                if (null != process && null != sp && null != fmeform) { //?????????????????????????????????????????????????????????
                    FormAccessControl busins = new FormAccessControl();
                    busins.setBusinessDef1(process.getProcessDefinitionID().toString());
                    busins.setBusinessDef2("nodeUniqueID:start");

                    List<FMFormArea> formAreas = operateFormService.showFormByControl(busins, fmeform.getVersionID()); //????????????????????????
                    JSONObject fieldObject = null;
                    List<JSONObject> fieldList = new ArrayList<JSONObject>(STSMConstant.NUM_TWO_HUNDRED);
                    List<JSONObject> fieldConfigList = new ArrayList<JSONObject>(STSMConstant.NUM_TWO_HUNDRED);//???????????????
                    FMFormField field = null;

                    for (int index = STSMConstant.NUM_ZERO; index < formAreas.size(); index++) {//???????????????????????????
                        FMFormArea area = formAreas.get(index);
                        List<FMFormField> list = area.getFieldList();
                        for (int indexF = STSMConstant.NUM_ZERO; indexF < list.size(); indexF++) {
                            field = list.get(indexF);
                            fieldObject = new JSONObject();
                            fieldObject.put("sequence", indexF + STSMConstant.NUM_ONE);
                            fieldObject.put("label", field.getLabel());
                            fieldObject.put("name", field.getName());
                            fieldObject.put("isRequired", null != field.getIsRequired() ? field.getIsRequired() : STSMConstant.STR_ZERO);//1????????? 0 ???????????????
                            fieldObject.put("contentSize", field.getContentSize());
                            fieldObject.put("fullLine", StringUtils.isNotBlank(field.getFullLine()) ? field.getFullLine() : STSMConstant.STR_EMPTY);
                            fieldObject.put("group", index + STSMConstant.NUM_ONE);
                            fieldObject.put("groupName", area.getTitle());
                            fieldObject.put("defaultValue", StringUtils.isNotBlank(field.getDefaultValue()) ? field.getDefaultValue() : STSMConstant.STR_EMPTY);
                            fieldObject.put("option", STSMConstant.STR_EMPTY);
                            //????????????text???????????????
                            //	textarea???????????????????????????
                            //	select??????????????????
                            //	datetime?????????????????????
                            //	rpopwin cpopwin  ??????????????????????????????????????????????????????
                            if ("select".equals(field.getRenderType())) { //??????????????????????????????
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
                        dataObject.put("fieldList", fieldList); //?????????????????????????????? ???????????????
                    } else {
                        dataObject.put("fieldList", fieldConfigList);
                    }
                    dataObject.put("productId", sp.getId());
                    dataObject.put("processId", process.getId());
                    dataObject.put("name", sp.getName());//????????????????????????
                    dataObject.put("status", "??????"); //??????
                    dataObject.put("icon", sp.getIcon());//??????
                    resultObject.put("datas", dataObject);
                    resultObject.put("info", "????????????");
                } else {
                    resultObject.put("info", "?????????????????????");
                    resultObject.put("result", STSMConstant.WS_RESULT_MISMATCH);
                }

            } else {
                resultObject.put("info", "???????????????????????????");
                resultObject.put("result", STSMConstant.WS_RESULT_SERVICE_EXCEPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.put("info", "????????????");
            resultObject.put("result", STSMConstant.WS_RESULT_SYSTEM_EXCEPTION);
//            log.error("goCreateWorkOrder fail ???".concat(Util.getExceptionMessage(e)));
        }

        return resultObject.toString();
    }

    @PostMapping("/getWorkOrderDetails")
    public String getWorkOrderDetails(@RequestBody Map map2) {
        JSONObject resultObject = new JSONObject();
        resultObject.put("result", STSMConstant.WS_RESULT_NORMAL);//??????????????????
        resultObject.put("info", STSMConstant.STR_EMPTY);
        String data = map2.get("processInstanceId").toString();
        try {
            if (StringUtils.isNotBlank(data)) {
                int processInstanceID = Integer.parseInt(data);
                ProcessInstance pro = processControlService.getProcessInstance(processInstanceID);
                if (null != pro) {
                    Process process = processManagementService.getProcessById(pro.getProcessID()); //??????????????????????????????????????????
                    Map para = new HashMap();
                    para.put("pid", processInstanceID);
                    ServiceProduct sp = serviceProductService.getServiceProductById(serviceProductMapper.getServiceProduceProcessInstance(para).getServiceProductId());
                    int formID = process.getFormID();
                    FMForm fmeform = formService.getFMForm(formID);

                    if (null != process && null != sp && null != fmeform) { //?????????????????????????????????????????????????????????
                        FormAccessControl busins = new FormAccessControl();
                        busins.setBusinessDef1(process.getProcessDefinitionID().toString());
                        busins.setBusinessDef2("nodeUniqueID:start");

//                            Map processStatus = processStatusService.getAll(Util.getRootPath()+"/conf/bpm");//????????????Map
                        FMFormInstance formInstance = operateFormService.getFormFieldValueById(pro.getId());
                        List<FMFormArea> formAreas = operateFormService.showFormByControl(busins, fmeform.getVersionID()); //????????????????????????

                        Map<String, String> valueFields = ordersService.setTypeValue(formAreas, formInstance.getFields(), formInstance.getReporterSource());

                        JSONObject fieldObject = null;
                        List<JSONObject> fieldList = new ArrayList<JSONObject>(STSMConstant.NUM_TWO_HUNDRED);//??????
                        List<JSONObject> fieldConfigList = new ArrayList<JSONObject>(STSMConstant.NUM_TWO_HUNDRED);//???????????????
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
                                fieldObject.put("isRequired", null != field.getIsRequired() ? field.getIsRequired() : STSMConstant.STR_ZERO);//1????????? 0 ???????????????
                                fieldObject.put("type", field.getRenderType());
                                fieldObject.put("contentSize", field.getContentSize());
                                fieldObject.put("fullLine", StringUtils.isNotBlank(field.getFullLine()) ? field.getFullLine() : STSMConstant.STR_EMPTY);
                                fieldObject.put("group", index + STSMConstant.NUM_ONE);
                                fieldObject.put("groupName", area.getTitle());
                                if ("select".equals(field.getRenderType())) { //??????????????????????????????
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
                        if (STSMConstant.STR_ZERO.equals(pro.getStates()) && null == map) {//??????????????????
                            dataObject.put("isEvaluate", "true");
                        } else {
                            dataObject.put("isEvaluate", "false");
                        }
                        Set<NodeHistory> runNodeHistorys = processControlService.getRunNodeHistorys(pro);
                        if (null != runNodeHistorys && !STSMConstant.STR_ZERO.equals(pro.getStates())) { //???????????? ???????????????????????????
                            int QBPMNodeInstanceID = STSMConstant.NUM_ZERO;
                            int i = STSMConstant.NUM_ZERO;
                            for (NodeHistory nodeHis : runNodeHistorys) {
                                if (i == STSMConstant.NUM_ZERO) {
                                    QBPMNodeInstanceID = nodeHis.getNodeInstanceID();
                                    break;
                                }
                            }
                            NodeInstance qbpmNodeInstance = nodeInstanceMapper.get(QBPMNodeInstanceID);//????????????????????????
                            ProcessDefinition qpd = processManagementService.getProcessDefinitionById(pro.getProcessDefinitionID());
                            dataObject.put("isRevoke", "true");
                        } else {
                            dataObject.put("isRevoke", "false");
                        }
                        if (fieldConfigList.isEmpty()) {
                            dataObject.put("fieldList", fieldList); //?????????????????????????????? ???????????????
                        } else {
                            dataObject.put("fieldList", fieldConfigList);
                        }
                        if (STSMConstant.STR_ONE.equals(pro.getStates())) { //?????????1 ???????????? ?????????????????????
                            dataObject.put("isSave", "true");
                            dataObject.put("isPublish", "true");
                        } else {
                            dataObject.put("isSave", "false");
                            dataObject.put("isPublish", "false");
                        }
                        List<BusinessOpt> opts = processControlService.listBusinessOpts(processInstanceID);//????????????
                        List logList = new ArrayList(opts.size());
                        JSONObject log = null;
                        for (BusinessOpt opt : opts) {
                            log = new JSONObject();
                            log.put("id", opt.getId());
                            log.put("userName", opt.getOptPerson().getUserName());
                            log.put("remarks", opt.getOptReason() == null ? "" : opt.getOptReason());
                            log.put("optTime", DateUtil.fmtDate(opt.getOptTime(), null));
                            if ("start".equals(opt.getOptType())) {
                                log.put("optContent", "??????");
                            } else if ("singin".equals(opt.getOptType())) {
                                log.put("optContent", "??????");
                            } else if ("refuse".equals(opt.getOptType())) {
                                log.put("optContent", "??????");
                            } else if ("deal".equals(opt.getOptType())) {
                                log.put("optContent", "??????");
                            } else if ("audit".equals(opt.getOptType())) {
                                log.put("optContent", "??????");
                            } else if ("confirm".equals(opt.getOptType())) {
                                log.put("optContent", "??????");
                            } else if ("retake".equals(opt.getOptType())) {
                                log.put("optContent", "??????");
                            } else if ("terminate".equals(opt.getOptType())) {
                                log.put("optContent", "??????");
                            } else if ("decision".equals(opt.getOptType())) {
                                log.put("optContent", "????????????");
                            } else if ("review".equals(opt.getOptType())) {
                                log.put("optContent", "????????????");
                            } else {
                                log.put("optContent", opt.getOptType());
                            }
                            logList.add(log);
                        }
                        //??????????????????
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

                        JSONObject evaluateLevels = new JSONObject(); //??????????????????
                        dataObject.put("evaluateLevels", evaluateLevels);
                        dataObject.put("logList", logList);
                        dataObject.put("productId", sp.getId()); //???????????????
                        dataObject.put("processId", process.getId());
                        dataObject.put("name", sp.getName());
                        dataObject.put("status", STSMConstant.NUM_ONE);
                        dataObject.put("icon", sp.getIcon());
                        resultObject.put("info", "????????????");
                        resultObject.put("datas", dataObject);
                    } else {
                        resultObject.put("info", "?????????????????????");
                        resultObject.put("result", STSMConstant.WS_RESULT_MISMATCH);
                    }
                } else {
                    resultObject.put("info", "???????????????!");
                    resultObject.put("result", STSMConstant.WS_RESULT_MISMATCH);
                }

            } else {
                resultObject.put("info", "??????????????????");
                resultObject.put("result", STSMConstant.WS_RESULT_SERVICE_EXCEPTION);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.put("info", "????????????");
            resultObject.put("result", STSMConstant.WS_RESULT_SYSTEM_EXCEPTION);
        }
        return resultObject.toString();
    }
}
