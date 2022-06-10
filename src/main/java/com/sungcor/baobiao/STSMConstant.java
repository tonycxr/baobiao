package com.sungcor.baobiao;

public final class STSMConstant {

    /*
     * Bean Name
     */
    public static final String SPRING_BEAN_NAME_MAIL_HELPER = "mailHelper";
    public static final String SPRING_BEAN_NAME_SYSTEM_CONFIG_MAIL_SERVICE = "systemconfig/MailService";
    public static final String SPRING_BEAN_NAME_TASK_DELETE_OBJECT = "task/DeleteObject";
    public static final String SPRING_BEAN_NAME_TASK_SEND_OBJECT = "task/SendObject";
    public static final String SPRING_BEAN_NAME_TASK_ADD_SEND_OBJECT = "task/AddSendObject";
    public static final String SPRING_BEAN_NAME_MAIL_SYSTEM = "mailSystem";
    public static final String SPRING_BEAN_NAME_SMS_SYSTEM = "smsSystem";
    public static final String SPRING_BEAN_USER_INFO_SERVICE = "user/Info/Service/userInfoService";
    public static final String SPRING_BEAN_NAME_SYSTEM_CATEGORY_SERVICE = "system/categoryService";
    public static final String SPRING_BEAN_NAME_DICT_ITEM_SERVICE = "dictItem/dictItemService";
    public static final String SPRING_BEAN_NAME_KNOWLEDGE_SERVICE = "knowledge/knowledgeService";
    public static final String SPRING_BEAN_NAME_KNOWLEDGE_KBCATEGORY_SERVICE = "knowledge/knowledgeKBCatService";
    public static final String SPRING_BEAN_NAME_KNOWLEDGE_COMMENT_SERVICE = "knowledge/knowledgeCommentService";
    public static final String SPRING_BEAN_NAME_KNOWLEDGE_ATTACHMENT_SERVICE = "knowledge/knowledgeAttachementService";
    public static final String SPRING_BEAN_NAME_DATA_EXPORT_SERVICE = "sys/dataExportService";
    public static final String SPRING_BEAN_NAME_ORGANISE_SERVICE = "system/organiseService";
    public static final String SPRING_BEAN_NAME_USER_SERVICE = "stsm/sys/UserService";
    public static final String SPRING_BEAN_NAME_AUTHENTICATION_SERVICE = "stsm/sys/AuthenticationService";
    public static final String SPRING_BEAN_NAME_MESSAGE_LOG_SERVICE = "stsm/general/MessageLogService";
    public static final String SPRING_BEAN_NAME_LDAP_SYNC_SERVICE ="ldap/ldapService";
    public static final String PROPERTIES_CONFIG_LDAP_SYNC_KEY ="ldap.enable";
    public static final String SERVLETCONTEXT_CONFIG_LDAP_SYNC_KEY="ldapEnable";

    public static final String SPRING_BEAN_NAME_OPT_LOGS  ="optLogs/operationLogsService";
    public static final String SPRING_BEAN_NAME_CMDB_AREA_SERVICE="cmdb/areaService";
    public static final String SPRING_BEAN_NAME_CMDB_ATTR_SERVICE="cmdb/attrService";

    public static final int[] STSM_CMS_ROLE_IDS = {14, 15, 16, 17, 26,27};
    public static final String STSM_CMS_ROLE_ENABLE="cmsRoleEnable" ;
    /*
     * JSON 变量名
     */
    public static final String JSON_RESULT_OK = "ok";
    public static final String JSON_RESULT_ERRORS = "errors";
    public static final String JSON_RESULT_ERRORS_CODE = "errors-code";
    public static final String JSON_RESULT_CONTENTS = "contents";
    public static final String JSON_TAG_ROLE_FUNCTIONS = "role_functions";
    public static final String JSON_TAG_USER_FUNCTIONS = "user_functions";
    public static final String JSON_TAG_SUPER_ADMIN= "superAdmin";

    public static final String FUNCTION_TYPE_CATALOG = "1";
    public static final String FUNCTION_TYPE_MENU = "2";
    public static final String FUNCTION_TYPE_BUTTON = "3";

    /* properties文件的名称 */
    public static final String PROPERTIES_CLASSPATH_PREFIX="classpath:**/";
    public static final String LDAP_CONFIG_FILENAME= "stsm_ldap.properties";
    /*
     * 与struts 的XXDao.xml中的参数对应
     */
    public static final String DAO_PARAM_ID = "id";
    public static final String DAO_PARAM_LIMIT = "limit";
    public static final String DAO_PARAM_USER_ID = "userId";
    public static final String DAO_PARAM_USER_NAME = "userName";
    public static final String DAO_PARAM_ROLE_NAME = "roleName";
    public static final String DAO_PARAM_ORGANIZATION_ID = "organizationId";
    public static final String DAO_PARAM_ROLE_DESCRIBE = "roleDescribe";
    public static final String DAO_PARAM_CREATE_USER = "createUser";
    public static final String DAO_PARAM_CREATE_TIME = "createTime";
    public static final String DAO_PARAM_ROLE_ID = "roleId";
    public static final String DAO_PARAM_FUNCTION_CODE = "functionCode";
    public static final String DAO_PARAM_MODIFY_USER = "modifyUser";
    public static final String DAO_PARAM_BEGIN_ROW = "beginRow";
    public static final String DAO_PARAM_PAGE_ROW_CNT = "pageRowCnt";
    public static final String DAO_PARAM_SKIN = "skin";
    public static final String DAO_PARAM_LOGIN_NAME = "loginName";
    public static final String DAO_PARAM_NEW_PASSWORD = "newPassword";
    public static final String DAO_PARAM_TITLE = "title";
    public static final String DAO_PARAM_SUBHEAD = "subhead";
    public static final String DAO_PARAM_TYPE = "type";
    public static final String DAO_PARAM_LEVEL = "level";
    public static final String DAO_PARAM_KEYWORD = "keyword";
    public static final String DAO_PARAM_SUMMARY = "summary";
    public static final String DAO_PARAM_PUBLISH_DATE = "publishDate";
    public static final String DAO_PARAM_STATUS = "status";
    public static final String DAO_PARAM_UPLOAD_TIME = "uploadTime";
    public static final String DAO_PARAM_UPLOAD_USER = "uploadUser";


    public static final String DAO_PARAM_TO = "to";
    public static final String DAO_PARAM_CC = "cc";
    public static final String DAO_PARAM_SUBJECT = "subject";
    public static final String DAO_PARAM_CONTENT = "content";
    public static final String DAO_PARAM_RETRY_COUNT = "retryCount";
    public static final String DAO_PARAM_EXTRA1 = "EXTRA1";
    public static final String DAO_PARAM_EXTRA2 = "EXTRA2";

    public static final int DAO_PARAM_VALUE_MAIL_MAX_RETRY_COUNT = 3;
    public static final int DAO_PARAM_VALUE_SMS_MAX_RETRY_COUNT = 5;

    /*
     * 状态
     */
    public static final String STATUS_KNOWLEDGE_DRAFTS = "0";



    public static final String STATUS_WORK_ORDER_CREATE = "-2,1";
    public static final String STATUS_WORK_ORDER_OPT = "2,3,4,5,6,7";
    public static final String STATUS_WORK_ORDER_CLOSED = "-1,0";
    //手机终端参数
    public static final String SYS = "STSM_MOBILE";

    //巡检定义常量
    public static final String STR_EMPTY = "";
    public static final String STR_ZERO = "0";
    public static final String STR_ONE = "1";
    public static final String STR_ONE_ = "-1";
    public static final String STR_TWO_ = "-2";
    public static final String STR_TWO = "2";
    public static final String STR_THREE = "3";
    public static final String STR_FOUR = "4";
    public static final String STR_FIVE ="5";
    public static final String STR_SIX ="6";
    public static final String STR_SIXTY ="60";
    public static final String STR_EIGHT ="8";
    public static final String STR_SEVEN ="7";
    public static final String STR_NINE = "9";
    public static final String FORMAT_PATTERN ="000";
    public static final String STR_SIXTEEN ="16";
    public static final String STR_SEVENTEEN ="17";
    public static final String STR_EIGHTEEN ="18";
    public static final String STR_FIFTEEN ="15";
    public static final String STR_FIFTYFOUR ="54";
    public static final String STR_HUNDRED ="100";
    public static final int NUM_HUNDRED =100;
    public static final int NUM_ZERO =0;
    public static final int NUM_ONE =1;
    public static final int NUM_ONE_ =-1;
    public static final int NUM_TWO =2;
    public static final int NUM_THREE=3;
    public static final int NUM_FOUR =4;
    public static final int NUM_FIVE =5;
    public static final int NUM_SIX =6;
    public static final int NUM_SEVEN =7;
    public static final int NUM_TWELVE  = 12;
    public static final int NUM_EIGHT = 8;
    public static final int NUM_NINE = 9;
    public static final int NUM_TEN = 10;
    public static final int NUM_ELEVEN= 11;
    public static final int NUM_SIXTEEN =16;
    public static final int NUM_SEVENTEEN =17;
    public static final int NUM_EIGHTEEN =18;
    public static final int NUM_FIFTEEN =15;
    public static final int NUM_FIFTYFOUR =54;
    public static final int NUM_FIVE_HUNDRED= 500;
    public static final int NUM_ONE_THOUSAND_TWENTY_FOUR = 1024;
    public static final int NUM_TWO_HUNDRED = 200;
    public static final int NUM_TWENTY = 20;
    /**
     * 知识类型“服务支持”默认ID
     */
    public static final int KN_CATEGORY_ID = 1;
    /**
     * 知识类型“服务支持”默认根节点编码
     */
    public static final String KN_CATEGORY_CODE = "01";

    /**
     * webservice 返回值
     */
    public static final String WS_RESULT_NORMAL = "0000";//正常
    public static final String WS_RESULT_MISMATCH = "0001";//无匹配值
    public static final String WS_RESULT_SYSTEM_EXCEPTION = "1000";//系统异常
    public static final String WS_RESULT_SERVICE_EXCEPTION = "1001";//业务异常异常 参数为空
}