package com.shframework.common.util;

import java.util.Queue;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;

import com.google.common.collect.Queues;



public class Constants {
	//将部分配置内容放到配置文件
	private static Configuration CONFIG = new Configuration("/../classes/cfg/config.properties");
	//读取jdbc的配置文件
	private static Configuration JDBC_CONFIG = new Configuration("/../classes/db/jdbc/jdbc.properties");
	
	private static Configuration MORE_TEMPLATE_CONFIG = new Configuration("/../classes/cfg/more.template.properties");
	
	private static Configuration REDIS_CONFIG = new Configuration("/../classes/redis/redis.properties");
	
	public static String SITEMESH3_PATH = "sitemesh3.xml";

	public static Queue<String> CK_QUEUE = Queues.newConcurrentLinkedQueue();
	
	public static String getProperty(String key) {
		return CONFIG.getValue(key);
	}
	
	public static String getConfigValue(String key, String defaultVal) {
		String ret = CONFIG.getValue(key);
		if (StringUtils.isEmpty(ret)) {
			ret = defaultVal;
		}
		return ret;
	}
	
	public static String getMoreTemplateValue(String key) {
		return MORE_TEMPLATE_CONFIG.getValue(key);
	}
	
	public static String getRedisKey(String key) {
		return REDIS_CONFIG.getValue(key);
	}
	
	public static Integer getRedisValue(String key) {
		return Integer.parseInt(REDIS_CONFIG.getValue(key));
	}
	
	public static String getDbName(){
		String url = JDBC_CONFIG.getValue("app.jdbc.url");
		if (StringUtils.isNotBlank(url)){
			 url = EncryptionUtil.decrypt(url);//rwz Fix
			 String[] arr = url.split("/");
			 int endIndex = arr[arr.length-1].indexOf("?");
			 if (endIndex >0){
				 return arr[arr.length-1].substring(0, endIndex);
			 }else{
				 return arr[arr.length-1];
			 } 
		}//if 
		return "";
	}

	// 迭代次数
	public static final int HASH_ITERATION_AMOUNT = 1024;
	public static final String HASH_DEFAULT_SALT = "s4h8t3d6:";//"s4h8t3d6:7JRJNgZnAOzPhSw2S0b3rw=="
	
	//动态获取数据库名称
	public static final String DB_NAME = getDbName();
	//公用错误页面
	public static final String ERROR_PAGE = "/errors/exception_error";
	
	// 文件目录分割
	public static final String URL_SEP = "/";
	public static final String SEP = System.getProperty("file.separator");
	
	public static final int COOKIE_BASE_LIFECYCLE = 60 * 60 * 24;
	
	public static final String ID_SEPARATOR = ",";  // 后台，多个id拼成字符串时的分隔符

	public static final String COMMON_ZERO = "0";
	public static final String COMMON_ONE = "1";
	public static final String COMMON_TWO = "2";
	public static final String COMMON_THREE = "3";
	public static final String COMMON_FOUR = "4";
	public static final String COMMON_FIVE = "5";
	public static final String COMMON_SIX = "6";
	
	public static final String COMMON_NO = "0";			//0：代表否、不支持、不可用等信息
	public static final String COMMON_YES = "1";		//1：代表是、支持、可用的信息
	
	public static final int DATA_BEAN_TYPE_DEFAULT = 1;//数据解析类型标识符，默认
	public static final int DATA_BEAN_TYPE_DICT = 2;//数据解析类型标识符，字典表
	
	public static final String WEB_PAGE_SIZE = "25";		//页面分页大小
	public static final String WEB_PAGE_START = "0";	//页面起始数据行
	public static final String WEB_PAGE_NUM = "1";		//当前页面号
	
	public static final String KEEP_PAGE_ONE = "1";		// 保留当前页
	public static final String KEEP_PAGE_ZERO = "0";	// 不保留当前页

	public static final int PATH_STYLE = 2;
	
	public static final int PATH_LAST_INDEX_ONE = 1;
	public static final int PATH_LAST_INDEX_TWO = 2;
	public static final int PATH_LAST_INDEX_THREE = 3;
	public static final int PATH_LAST_INDEX_FOUR = 4;
	public static final int PATH_LAST_INDEX_FIVE = 5;
	public static final int PATH_LAST_INDEX_SIX = 6;
	public static final int PATH_LAST_INDEX_SEVEN = 7;

	public static final int SEARCH_FIELD_HANDLE_TYPE_ONE = 1;
	public static final int SEARCH_FIELD_HANDLE_TYPE_TWO = 2;
	public static final int SEARCH_FIELD_HANDLE_TYPE_THREE = 3;
	public static final int SEARCH_FIELD_HANDLE_TYPE_FOUR = 4;
	public static final int SEARCH_FIELD_HANDLE_TYPE_FIVE = 5;
	public static final int SEARCH_FIELD_HANDLE_TYPE_SIX = 6;
	public static final int SEARCH_FIELD_HANDLE_TYPE_SEVEN = 7;
	public static final int SEARCH_FIELD_HANDLE_TYPE_EIGHT = 8;
	public static final int SEARCH_FIELD_HANDLE_TYPE_NINE = 9;
	public static final int SEARCH_FIELD_HANDLE_TYPE_TEN = 10;
	public static final int SEARCH_FIELD_HANDLE_TYPE_ELEVEN = 11;
	public static final int SEARCH_FIELD_HANDLE_TYPE_TWELVE = 12;
	public static final int SEARCH_FIELD_HANDLE_TYPE_IN = 13;
	public static final int SEARCH_FIELD_HANDLE_TYPE_IS_NULL = 14;
	
	public static final String[] WEB_RES_TYPE = {".css", ".gif", ".ico", ".jpg", ".js", ".png", ".properties", ".woff", "notification/directaccess"}; 	//页面资源类型
	
	public static final String SYS_RESOURCE_SCOPE_TOKEN = "rc-";
	
	public static final String BROKER_URL = getProperty("broker.url");
	public static final String QUEUE_NAME = getProperty("mq.queue.name");	//消息队列名称
	
	//系统名称 读取配置文件（config.properties）
	public static final String SYSTEM_NAME = "system.name";
	
	// 通道标识
	public static final String APP_CHANNEL = "talker";
	// 上线消息标识
	public static final String UP = "up";
	// 下线消息标识
	public static final String DOWN = "down";
	// 改名消息标识
	public static final String RENAME = "rename";
	// 话语消息标识
	public static final String TALK = "talk";
	// 系统健康信息
	public static final String HEALTH = "health";

	//ajax操作返回结果 add by RanWeizheng
	public static final String AJAX_SUCC = "OK";
	public static final String AJAX_FAIL = "FAIL";
	public static final String AJAX_TRUE = "true";
	public static final String AJAX_FALSE = "false";
	public static final String AJAX_CONFLICT = "conflict";//代表 “冲突”，校验用
	public static final String AJAX_WITHOUTSCHOOLCALENDAR = "withoutSchoolCalendar";//代表 “根据学年学期、上课周、周几 查询不出数据”，校验用
	public static final String AJAX_DUPLICATEKEY = "DUPLICATEKEY";
	
	//字典表，选择框溢出值，当使用这个值时，所有判断不生效——选择框会选择第一个值
	public static final String DICT_SELECT_OUTOFRANGE =  "";
	//字典表保存时的错误返回值
	public static final int ERR_SAVE_FAIL = -1;//一般性失败：未找到符合条件的类型\参数不合法等
	public static final int ERR_SAVE_DUPLICATEKEY = -2;//存在唯一性冲突， 如code/name冲突
	public static final int ERR_DEDUCT_WEEK = -3;//教学周扣除数大于周数
	
	//功能同COMMON_NO / COMMON_YES  只是类型是int
	public static final int DICT_COMMON_NO = 0;
	public static final int DICT_COMMON_YES = 1;
	
	//存放校验错误数据的key
	public static final String BINDING_RESULT_CLAZZ_NAME = BindingResult.class.getName();
	public static final String getBindingResultKey(String entityName){
		if (StringUtils.isNotBlank(entityName)){
			return BINDING_RESULT_CLAZZ_NAME + "." + entityName;
		}else {
			return "";
		}
	}
	
	
	//字典表状态字段 表示 标准
	public static final int DICT_STANDARD_UNKNOWN = -1; //字典表 未定义
	public static final int DICT_STANDARD_NATION = 0; //字典表 国标
	public static final int DICT_STANDARD_SCHOOL = 1; //字典表 校标
	public static final int DICT_STANDARD_DATAPLATFORM = 2; //字典表 数据平台

	public static final String COMMON_DEFINED_RULE = "rule";
	public static final String COMMON_DEFINED_CURMENU = "curMenu";
	public static final String COMMON_DEFINED_SHOWSTYLE = "showStyle";
	public static final String COMMON_DEFINED_BACKURL = "backUrl";
	
	// 2
	public static final String SYS_RESOURCE_ADD = "0";	// 资源r/0/p新增
	
	// 1
	public static final String SYS_PERMISSION_SAVE = "save";	// 保存
	public static final String SYS_PERMISSION_ADD = "add";	// 新增
	public static final String SYS_PERMISSION_DELETE = "delete";	// 删除
	public static final String SYS_PERMISSION_EDIT = "edit";	// 编辑
	public static final String SYS_PERMISSION_READ = "read";	// 查看明细
	public static final String SYS_PERMISSION_LIST = "list";	// 查看列表
	public static final String SYS_PERMISSION_IMPORT = "import";	// 导入
	public static final String SYS_PERMISSION_EXPORT = "export";	// 导出
	
	public static final String SYS_PERMISSION_BATCHADD = "batchadd"; // 批量新增

	// 4
	public static final String SYS_IS_BATCH_TRUE = "batch"; 
	public static final String SYS_IS_BATCH_TRUE_HANZI = "批量"; 
	
	
	public static final String DEFAULT_COMMON_USER_PICTURE = "default.jpg";
	
	public static final String SYS_PERMISSION_DATA_ALL = "all"; // 列表
	public static final String SYS_PERMISSION_DATA_ZERO = "0"; // 新增url[2]
	
	//字典表 不同类型表对应的表名前缀
	public static final String DICT_NAME_PREFIX_COMMON = "dictcommon";
	public static final String DICT_NAME_PREFIX_EDU_COMMON = "dicteducommon";
	public static final String DICT_NAME_PREFIX_EDU_ROLL = "dicteduroll";
	public static final String DICT_NAME_PREFIX_EDU_SKD = "dicteduskd";//教务-教学计划
	public static final String DICT_NAME_PREFIX_EDU_TCH = "dictedutch";//教务-师资
	public static final String DICT_NAME_PREFIX_EDU_CLR = "dicteduclr";//教务-教学资源
	public static final String DICT_NAME_PREFIX_EDU_EXAM = "dicteduexam";//教务-考务
	public static final String DICT_NAME_REPLACE_PREFIX_EDU_EXAM = "dictedu";//教务-考务 考务字典获取key时的替换前缀
	public static final String DICT_NAME_PREFIX_EDU_CERT = "dicteducert";//教务-证书管理
	public static final String DICT_NAME_PREFIX_EDU_GRAD = "dictedugrad";//教务-毕业资格审核
	
	//字典表 不同类型表对应的路径前缀
	public static final String DICT_PATH_COMMON = "common";
	public static final String DICT_PATH_EDU_COMMON = "edu/common";
	public static final String DICT_PATH_EDU_ROLL = "edu/roll";
	public static final String DICT_PATH_EDU_SKD = "edu/skd";
	public static final String DICT_PATH_EDU_TCH = "edu/tch";
	public static final String DICT_PATH_EDU_CLR = "edu/clr";
	public static final String DICT_PATH_EDU_EXAM = "edu/exam";
	public static final String DICT_PATH_EDU_CERT = "edu/cert";
	public static final String DICT_PATH_EDU_GRAD = "edu/grad";

	
	public static final int DICT_PRIORITY_DEFAULT = 999999;//默认的顺序号
	
	//级联生成专业方向时，其对应的code Ranweizheng
	public static final String DICT_EDU_COMMON_MAJOR_FIELD_CODE_DEFAULT = "_0";
	
	public static final String DICT_TABLE_NAME_EDU_COMMON_ACADEMIC_YEAR = "dict_edu_common_academic_year";
	//特殊的，在读入缓存时，需要单独处理的表（表结构与通用的不太一样） Ranweizheng
	public static final String DICT_TABLE_NAME_COMMON_POLICITAL_STATUS = "dict_common_policital_status";
	public static final String DICT_TABLE_NAME_EDU_COMMON_ACD_YEAR_TERM = "dict_edu_common_acd_year_term";
	public static final String DICT_TABLE_NAME_EDU_COMMON_LABEL = "dict_edu_common_label";
	public static final String DICT_TABLE_NAME_EDU_COMMON_CAMPUS = "dict_edu_common_campus";
	public static final String DICT_TABLE_NAME_EDU_COMMON_COLLEGE = "dict_edu_common_college";
	public static final String DICT_TABLE_NAME_EDU_COMMON_DEPARTMENT = "dict_edu_common_department";
	public static final String DICT_TABLE_NAME_EDU_COMMON_GROUP_MAJOR = "dict_edu_common_group_major";
	public static final String DICT_TABLE_NAME_EDU_COMMON_MAJOR = "dict_edu_common_major";
	public static final String DICT_TABLE_NAME_EDU_COMMON_MAJOR_FIELD = "dict_edu_common_major_field";
	public static final String DICT_TABLE_NAME_EDU_COMMON_MAJOR_DEPARTMENT = "dict_edu_common_major_department";
	public static final String DICT_TABLE_NAME_EDU_COMMON_CATEGORY_MAJOR = "dict_edu_common_category_major";
	public static final String DICT_TABLE_NAME_COMMON_AREA = "dict_common_area";//地区级联时使用
	public static final String DICT_TABLE_NAME_EDU_SKD_COURSE_MODE = "dict_edu_skd_course_mode";
	public static final String DICT_TABLE_NAME_EDU_TCH_POST_LEVEL = "dict_edu_tch_post_level";
	public static final String DICT_TABLE_NAME_EDU_CLR_BUILDING = "dict_edu_clr_building";
	public static final String DICT_TABLE_NAME_EDU_CLR_CLR_TYPE = "dict_edu_clr_clr_type";
	public static final String DICT_TABLE_NAME_EDU_GRAD_REWARDS_TYPE = "dict_edu_grad_rewards_type";
	
	//字典表，保存在缓存map中对应的键值, 级联map中，也使用这几个字段作为key值
	public static final String DICT_TABLE_KEY_EDU_COMMON_GROUP_MAJOR = "groupmajor";
	public static final String DICT_TABLE_KEY_EDU_COMMON_MAJOR = "major";
	public static final String DICT_TABLE_KEY_EDU_COMMON_MAJOR_FIELD = "majorfield";
	public static final String DICT_TABLE_KEY_COMMON_AREA = "area";

	public static final String TABLE_KEY_EDU_ROLL_NATURALCLAZZ = "naturalclazz";
	public static final String KEY_EDU_ROLL_CLAZZ_CAMPUS = "clazzcampus";//班级-学期-校区级联
	public static final String CASCADE_KEY_PROVINCE = "provincecascade";//省-市-县级联
	public static final String TABLE_KEY_EDU_SKD_COURSE_TYPE = "coursetype";//edu_skd_course_type
	public static final String TABLE_KEY_EDU_SKD_COURSE = "course";//edu_skd_course_type
	
	public static final String KEY_EDU_TCH_EMPLOYEE_NAME = "employeename";//教职工姓名和拼音
	public static final String KEY_SYS_SETTING = "syssetting";//系统设置
	public static final String KEY_YEAR_TERM = "yearterm";// 上学期，当前学期，下学期

	public static final String KEY_DIVISION = "division";//部门 
	public static final String KEY_SEC_DIVISION = "secdivision";//部门 学校下面第一级

	public static final String KEY_SYS_ROLE = "sysrole";//系统角色 
	public static final String KEY_SKD_SYS_ROLE = "skdsysrole";//教学计划部分，使用的系统角色。有一定的特殊性。
	public static final String DICT_TABLE_KEY_YEAR_TERM_FULLCODE = "acdyeartermfc";//学年学期字典表 ，code为 完整形式的  rwz add 20160304
	
	public static final String KEY_CAMPUS = "campus";	// 校区
	
	public static final String KEY_EDU_ROLL_STUDENT_NAME = "studentname";//学生姓名和拼音
	
	
	public static final String KEY_CCM = "ccm";//学分计算方法
	public static final String KEY_WCM = "wcm";//教学周计算方法
	
	public static final String DICT_TABLE_KEY_ACAYEAR_FULLCODE = "academicyearfc";//学年字典表 ，code为 完整形式的 rwz add 20160304
	public static final String DICT_EDIT_TYPE_CHANGESTATUS = "changestatus";//字典表操作类型--修改可用状态  add by rwz
	public static final String DICT_EDIT_TYPE_CHANGEPRINCIPAL = "changeprincipal";//字典表操作类型--修改专业方向负责人  add by rwz   principal
	
	public static final String EDU_CLR_CLASSROOM = "classroom";	// 场地信息
	
	
	//dictInitService中，获取字典表基础信息时使用的数组的大小
	//modify by zhangjinkui
	public static final int DICT_INFO_LENGTH = 5;
	
	//数组中各个数据的保存位置exam方法
	public static final int DICT_INFO_TABLENAME = 0;//表名
	public static final int DICT_INFO_TABLEDESC = 1;//表中文名
	public static final int DICT_INFO_PATHPREFIX = 2;//表路径前缀
	//modify by zhangjinkui
	public static final int DICT_INFO_ISCONTAINCODE = 3;//此表中是否包含 "code"字段。若不包含，页面隐藏”编码“输入列。
	public static final int DICT_INFO_KEY = 4;//key

	public static final int WEB_CONFIRM_FLAG_ONE = 1; //是否网上确认 0:否
	public static final int WEB_CONFIRM_FLAG_ZERO = 0; //是否网上确认 1:是
	
	public static final int CONFIRM_FLAG_ONE = 1; 	// 确认信息正确
	public static final int CONFIRM_FLAG_ZERO = 0;	// 未确认
	public static final int CONFIRM_FLAG_TWO = 2;	// 确认信息有误
	
	public static final int BASE_STATUS_ONE = 1;	// 使用
	public static final int BASE_STATUS_ZERO = 0; 	// 不使用
	
	public static final int BASE_LOGIC_DELETE_ONE = 1;	// 逻辑删除
	public static final int BASE_LOGIC_DELETE_ZERO = 0;	// 正常
	
	public static final int UPLOAD_FILE_SUCCESS = 0;	// 文件上传成功
	public static final int UPLOAD_FILE_FAIL = 1;	// 失败
	//附件 是否临时文件
	public static final int UPLOAD_ATTACHMENT_TMP_FLAG_NO= 0;	// 是否临时文件（0：不是，1：是）
	public static final int UPLOAD_ATTACHMENT_TMP_FLAG_YES= 1;	// 是否临时文件（0：不是，1：是）
	//附件 文件类型
	public static final int UPLOAD_ATTACHMENT_TYPE_ZERO = 0;	// 文件类型（0：未指定，1：照片，2：文档）
	public static final int UPLOAD_ATTACHMENT_TYPE_ONE = 1;	// 文件类型（0：未指定，1：照片，2：文档）
	public static final int UPLOAD_ATTACHMENT_TYPE_TWO = 2;	// 文件类型（0：未指定，1：照片，2：文档）
	
	/**
	 * 导入时，如果出现与db中重复的内容时是否覆盖原有记录，默认的处理方式--覆盖
	 */
	public static final String IMPORT_IS_COVER_COVER= "1";
	public static final String IMPORT_IS_COVER_IGNORE= "2";
	public static final String IMPORT_IS_COVER_EXCEPTION= "3";
	
	//院系表中院系的类型
	public static final int DEPARTMENT_TYPE_COLLEGE = 0;//二级学院
	public static final int DEPARTMENT_TYPE_DEPARTMENT = 1;//院系，院系
	public static final int DEPARTMENT_TYPE_DIVISION = 2;//部门
	
	public static final int DEPARTMENT_ROOT_ID = 1;//根节点id
	
	public static final int RESOURCE_ROOT_ID = 1;//资源 根节点id
	
	//字典表默认排序
	public static final String DICT_LIST_ORDERBY_DEFAULT = "id,priority";
	
	//字典表map中，级联map对应的key
	public static final String DICT_KEY_CASCADE = "dictcascade";

	public static final String DEFAULT_SORT_FIELD = "defaultSortField";
	public static final String DEFAULT_SORT_ORDERBY = "defaultSortOrderby";
	
	//导入模式：选择模板模式"excel"
	public static final String SELECT_TEMPLATE_PATTERN = "excel";
	
	// 课程  是否校企合作课程 0,1
	public static final String EDU_SKD_COURSE_COOPFLAG_NO = "0";
	public static final String EDU_SKD_COURSE_COOPFLAG_YES = "1";
	
	// 课程  考试考查 0,1
	public static final String EDU_SKD_COURSE_EXAMFLAG_NO = "0";
	public static final String EDU_SKD_COURSE_EXAMFLAG_YES = "1";
	
	// 教学计划-课程  是否使用参考网:不使用,使用 0,1
	public static final String EDU_SKD_COURSE_SHOWURL_NO = "0";
	public static final String EDU_SKD_COURSE_SHOWURL_YES = "1";
	
	// 教学计划-课程  文件类型 0:课标，1:大纲
	public static final String EDU_SKD_COURSE_STANDARDFILETYPE_NO = "0";
	public static final String EDU_SKD_COURSE_STANDARDFILETYPE_YES = "1";
	
	// 教学计划(用)-课程  是否可选课  0:不可选课，1:可选课
	public static final Integer EDU_SKD_SCHEDULE_OPTIONAL_NO =  0;
	public static final Integer EDU_SKD_SCHEDULE_OPTIONAL_YES = 1;
	
	// 0:否，1:是
	public static final String ISZERO = "0";
	public static final String NOTZERO = "1";
	
	/**
	 * 教学计划课程计算方法配置表中的值
	 */
	public static final String METHOD3_HOURS_PER_CREDIT = "edu_skd_method3_hours_per_credit";
	public static final String METHOD5_HOURS_PER_CREDIT = "edu_skd_method5_hours_per_credit";
	public static final String METHOD6_WEEKS = "edu_skd_method6_weeks";
	
	public static final String EDU_SKD_SCHEDULE_NO = "edu_skd_schedule_no";//编号 系统配置表中对应的title
	public static final String EDU_SCR_CREDIT_DETAIL_NO = "edu_scr_credit_detail_no";//学期成绩编号，对应着PDF中的编号
	public static final String EDU_SKD_SCHEDULE_ISNOT_LIMIT_TIME = "edu_skd_schedule_isnot_limit_time";	// 教学计划不受时间限制（1：不受时间限制 0:受时间限制）
	public static final String EDU_ROLL_REGISTER_END_ACD_YEAR_TERM = "edu_roll_register_end_acd_year_term";		//学籍学期注册管理已经翻滚的学期
	public static final String EDU_TXBK_NOT_OPTIONAL_MIN = "edu_txbk_not_optional_min";//非工程实践类的不可选课教材数量下限
	public static final String EDU_TXBK_NOT_OPTIONAL_MAX = "edu_txbk_not_optional_max";//非工程实践类的不可选课教材数量上限
	public static final String EDU_TXBK_OPTIONAL_MIN = "edu_txbk_optional_min";//非工程实践类的可选课教材数量下限
	public static final String EDU_TXBK_OPTIONAL_MAX = "edu_txbk_optional_max";//非工程实践类的可选课教材数量上限
	public static final String EDU_TXBK_PRACTICE_MIN = "edu_txbk_practice_min";//工程实践课教材数量下限
	public static final String EDU_TXBK_PRACTICE_MAX = "edu_txbk_practice_max";//工程实践课教材数量上限
	public static final String EDU_TXBK_EXPERIMENT_PAPERS_PRICE = "edu_txbk_experiment_papers_price";//实验报告纸价格
	public static final String EDU_EXAM_CLAZZ_PROGRESS_STATUS_LIMIT_OPEN = "edu_exam_clazz_progress_status_limit_open";//考务中对教学班生成实时课表限制与否
	public static final String EDU_TASK_SEL_NATURALCLAZZCODEORTITLE = "edu_task_sel_naturalclazzcodeortitle";
	
	//高职毕业审核要求 系统设置
	public static final String HIGH_GRAD_TOTAL_CREDIT_FLAG = "high_grad_total_credit_flag"; //总学分
	public static final String HIGH_GRAD_REQUIRED_CREDIT_FLAG = "high_grad_required_credit_flag"; //必修课学分
	public static final String HIGH_GRAD_REQUIRED_SCORE_FLAG = "high_grad_required_score_flag"; //必修课成绩
	public static final String HIGH_GRAD_UNREQUIRED_CREDIT_FLAG = "high_grad_unrequired_credit_flag"; //非必修课学分
	public static final String HIGH_GRAD_QUALITY_CREDIT_FLAG = "high_grad_quality_credit_flag";  //素质学分
	public static final String HIGH_TWO_QUALITY_CREDIT_MIN = "high_two_quality_credit_min"; //二年制全日制高职素质学分下限
	public static final String HIGH_THREE_QUALITY_CREDIT_MIN = "high_three_quality_credit_min"; //三年制全日制高职素质学分下限
	public static final String HIGH_FIVE_QUALITY_CREDIT_MIN = "high_five_quality_credit_min"; //五年制高职阶段素质学分下限
	public static final String ABROAD_QUALITY_CREDIT_MIN = "abroad_quality_credit_min"; // 四年制留学生素质学分下限
	public static final String HIGH_GRAD_CERT_REQUIRE_FLAG = "high_grad_cert_require_flag"; //获得三证
	
	//五年制以中专身份毕业审核要求
	public static final String MIDDLE_GRAD_REQUIRED_SCORE_FLAG = "middle_grad_required_score_flag"; //必修课成绩
	public static final String MIDDLE_GRAD_QUALITY_CREDIT_FLAG = "middle_grad_quality_credit_flag";  //素质学分
	public static final String MIDDLE_GRAD_QUALITY_CREDIT_MIN = "middle_grad_quality_credit_min"; //素质学分下限
	
	//五年制升段审核要求
	public static final String MIDDLE_UP_LACK_SCORE_FLAG = "middle_up_lack_score_flag"; //前3年累计欠修学分
	public static final String MIDDLE_UP_LACK_SCORE_COUNT = "middle_up_lack_score_count"; //前3年累计欠修学分数量
	public static final String MIDDLE_UP_QUALITY_CREDIT_FLAG = "middle_up_quality_credit_flag";  //素质学分
	public static final String MIDDLE_UP_QUALITY_CREDIT_MIN = "middle_up_quality_credit_min"; //素质学分下限
	
	//角色 超级管理员
	public static final String  SUPER_ADMIN_ROLL= "admin4dev";
	
	public static final String  HEAD_SYS = "head_sys";//系统管理员
	//学籍 角色code
	public static final String HEAD_ROLL = "head_roll";//总校学籍干事
	public static final String CAMPUS_ROLL = "campus_roll";//校区学籍干事
	
	/**
	 * 教学计划  最后待处理人	（总校 head_skd，院系负责人 dept_skd，专业负责人 major_skd，教务处长 boss_skd，需要关联 sys_role 表的code对应）
	 */ 
	public static final String HEAD_SCH = "head_skd";
	public static final String HEAD_DEPT = "dept_skd";
	public static final String HEAD_MAJOR = "major_skd";
	public static final String HEAD_TECH = "boss_skd";
	
	//教学资源 (场地管理员 角色：clr_clr) （教学任务信息表 edu_cskd_tch_detail，场地最后待处理角色：场地管理员，主讲教师）
	public static final String CLR_CLR = "clr_clr";
	public static final String MAJOR_CLR = "major_clr";

	
	/**
	 * 教学计划 任务表 最后处理结果 -1:开始， 0：待处理，1：通过，2：退回，3：下发，4：上报  ，5：不调整结束
	 */
	public static final Integer START = -1;
	public static final Integer PENDING = 0;
	public static final Integer PASS = 1;
	public static final Integer REJECT = 2;
	public static final Integer SEND = 3;
	public static final Integer REPORT = 4;
	public static final Integer END = 5;
	
	/**
	 * 教学计划 计划表  是否新生计划 0:指导性教学计划，1:实施性教学计划
	 */
	public static final Integer ROOKIE_FLAG_NO = 0;
	public static final Integer ROOKIE_FLAG_YES = 1;
	
	/**
	 * 教学计划 计划表  是否归档 0:否，1:是
	 */
	public static final Integer ARCHIVE_FLAG_NO = 0;
	public static final Integer ARCHIVE_FLAG_YES = 1;
	
	/**
	 * 教学计划 计划表  是否调整 0:否，1:是
	 */
	public static final Integer MODIFY_FLAG_NO = 0;
	public static final Integer MODIFY_FLAG_YES = 1;
	
	/**
	 * 教学计划 计划表  审核 是否通过 0:不通过，1:通过
	 */
	public static final String PASS_NO = "0";
	public static final String PASS_YES = "1";
	
	// dbf 文件默认长度
	public static final Integer DEFAULT_FILED_LENGTH = 30;
	
	//默认的负责人(principal)Id
	public static final Integer DEFAULT_PRINCIPAL_ID = 0; 
	
	//dbf导出编码字符集
	public static final String DBF_ENCODING_JIAOWEI = "dbf.encoding.jiaowei";
	public static final String DBF_ENCODING_PRINT = "dbf.encoding.print";
	
	//#dbf教委导出时需要使用的专业全称的字段
	public static final String DBF_JIAOWEI_MAJORFULLNAME = "dbf.jiaowei.majorfullname";
	//#dbf教委导出中需要制定时间格式的字段
	public static final String DBF_JIAOWEI_DATE = "dbf.jiaowei.date";
	//#dbf教委导出中 日期格式
	public static final String DBF_JIAOWEI_DATE_FORMAT = "dbf.jiaowei.date.format";
	
	//db中，code字段的长度
	public static final int DB_COL_LENGTH_CODE = 32;
	//db中，title字段的长度
	public static final int DB_COL_LENGTH_TITLE = 128;
	
	public static final int SYS_NOTIFICATION_TYPE_ONE = 1;
	public static final int SYS_NOTIFICATION_TYPE_TWO = 2;

	public static final String ASYNC_KEY_SYS_NOTIFICATION = "async.tms.edu.sys.notification";	// 系统通知
	public static final String ASYNC_KEY_EDU_STUDENT_COUNT = "async.tms.edu.roll.clazz.studentcount";//行政班在校生计数
	public static final String ASYNC_KEY_CSKD_DELETE_TCHCLAZZALOOCANDDETAIL = "async.tms.edu.cskd.delete.tchclazzaloocanddetail";	// 删除教学班分配及详情

	public static final String ASYNC_KEY_SCR_EXAM_UPDATECREDIT = "async.tms.edu.scr.exam.updatecredit";//设置/撤销成绩异常状态时，更新计算总评成绩 rwz add
	public static final String ASYNC_KEY_SCR_CREDITENTRY_INITSTUDENT =  "async.tms.edu.scr.creditentry.initstudent";//修改教学班中的学生时，对新增的学生，初始化应有的成绩构成详情 rwz add
	public static final String ASYNC_KEY_SCR_CREDITENTRY_BATCHINITSTUDENT =  "async.tms.edu.scr.creditentry.batchinitstudent";//修改教学班中的学生时，对新增的学生，初始化应有的成绩构成详情，选课的四种形式，由于教学班和学生数量都是随机的故增加这个接口rwz add
	public static final String ASYNC_KEY_SCR_CREDITENTRY_HASHCREDIT = "async.tms.edu.scr.creditentry.hashcredit";//分数在正常变动后，更新表edu_scr_student_credit_hash和表edu_scr_student_credit_detail_hash中的相应数据 rwz add
	
	public static final String ASYNC_KEY_REV_STUDENTREGISTER_DELETESTDALLOC = "async.tms.edu.rev.studentregister.deletestdalloc";	// 将补修学生撤销后，需重修需补修学生查询、重修学生确认情况页面不应该显示补修的学生。根据 student_id 和 pending_course_id 删除表 edu_rev_pending_course_std_alloc 的数据
	
	//系进程管理员：进程所有的开课系和管理系是否都已经上报了，如果都上报了则更新进程的状态为“系里已提交”。
	public static final String ASYNC_KEY_EDU_PROG_REPORT = "async.tms.edu.prog.report";
	
	//考试形式保存或更新异步接口名
	public static final String ASYNC_KEY_EXAM_STORE_REVEXAMREG = "async.tms.edu.exam.store.revexamreg";
	//教材选配 添加、删除 印刷单/订购单 更新选配的订单状态
	public static final String ASYNC_KEY_EDU_TXBK_MATCHING = "async.tms.edu.txbk.matching";
	//学生登记、审核通过的三证，通知 毕业资格审核 模块
	public static final String ASYNC_KEY_EDU_CERT_STUDENT = "async.tms.edu.cert.student";
	
	//毕业资格审核 五年制学生流向 由升段降为中专毕业 修改学分，三证，素质学分的审核状态
	public static final String ASYNC_TMS_EDU_GRAD_AUDIT =  "async.tms.edu.grad.audit";
	
	public static final String SAVEORUPDATE_GRAD_CERT_STUDENT = "saveorupdate_grad_cert_student";//毕业资格审核、学生证书表
	public static final String DELETE_GRAD_CERT_STUDENT = "delete_grad_cert_student";//毕业资格审核、学生证书表
	
	public static final String ASYNC_KEY_SKD_ADDTCHCLAZZNATUALCLAZZINFO = "async.tms.edu.skd.addtchclazznatualclazzinfo";	// 添加教学班和行政班相关信息
	
	//异步系统正常测试
	public static final String ASYNC_TMS_ALIVE_TEST =  "async.tms.alive.test";

	
	//是否教学日
	public static final Integer WEEKDAY_YES = 1;	// 是
	public static final Integer WEEKDAY_NO =  0;	// 否
	
	// 0：未读，1：已读
	public static final Integer READ_NO = 0;
	public static final Integer READ_YES = 1;
	
	//证书管理：总校免修管理员、院系免修管理员
	public static final String CERT_HEAD = "head_cert";
	public static final String CERT_DEPT = "dept_cert";
	
	/**
	 * 进程管理 最后待处理角色	（校区进程管理员，院系进程管理员，需要关联 sys_role 表的code对应）
	 */ 
	public static final String PROG_CAMPUS = "campus_prog";
	public static final String PROG_DEPT = "dept_prog";
	
	//教材 rule
	public static final String TXBK_RULE = "txbk";

	/**
	 * 系部教材负责人
	 */ 
	public static final String TXBK_DEPT = "dept_txbk";
	/**
	 * 教务教材负责人
	 */
	public static final String TXBK_BOSS = "boss_txbk";
	/**
	 * 教材主讲教师
	 */
	public static final String TXBK_MAJOR = "major_txbk";
	/**
	 * 校区教材管理员
	 */
	public static final String TXBK_CAMPUS = "campus_txbk";
	/**
	 * 总校教材管理员
	 */
	public static final String TXBK_HEAD = "head_txbk";

	/**
	 * 进程管理:
	 * 进程制定状态："0",  "未制定"
	 * "1",  "已创建教务编辑中"
	 * "2",  "已下发到系里"
	 * "3",  "系里已提交"
	 * "4",  "制定完成"
	 */
	public static final Integer PROG_NOT_CREATED = 0;
	public static final Integer PROG_CREATED = 1;
	public static final Integer PROG_SEND = 2;
	public static final Integer PROG_REPORT = 3;
	public static final Integer PROG_END = 4;

	public static final String SESSION_FORCE_LOGOUT_KEY = "logout";
	
	// 教学任务权限部分设计的2种角色 
	public static final String HEAD_TASK = "head_task"; // 教学任务管理员（总校）
	public static final String DEPT_TASK = "dept_task_course"; // 开课管理员（院系）

	public static final String SESSION_FORCE_LOGOUT_KEY_ID = "logout_id";
	//(教学计划用)课程表 :  工程实践类
	public static final Integer SCHEDULE_COURSETYPEID = 6;
	
	/**
	 * 成绩 
	 */
	//学分设置 状态 0:未启用，1:启用中， 2:已作废 
	public static final int SCR_CREDIT_STATUS_DISABLE = 0;
	public static final int SCR_CREDIT_STATUS_ENABLE = 1;
	public static final int SCR_CREDIT_STATUS_OBSOLETE = 2;

	//总评成绩 类别 0:总校模板，1:教学班 
	public static final int SCR_CREDIT_GENERAL_MODE_HEAD = 0;
	public static final int SCR_CREDIT_GENERAL_MODE_CLASS = 1;
	
	/**
	 * 成绩构成 类型
	 * 0：自定义  
	 * 1：期中成绩  （仅新修）
	 * 2：期末成绩  （仅新修）
	 * 3：总评成绩  
	 * 4：补考成绩  （仅新修）
	 * 5 缓考-补考成绩 （仅新修）
	 * 6 重修成绩    （仅重修）
	 * 7 自主重修
	 */
	public static final int SCR_CREDIT_DETAIL_TYPE_CUSTOM = 0;
	public static final int SCR_CREDIT_DETAIL_TYPE_MIDDLE = 1;
	public static final int SCR_CREDIT_DETAIL_TYPE_FINAL = 2;
	public static final int SCR_CREDIT_DETAIL_TYPE_OVERALL = 3;
	public static final int SCR_CREDIT_DETAIL_TYPE_MAKEUP = 4;
	public static final int SCR_CREDIT_DETAIL_TYPE_DELAYED= 5;
	public static final int SCR_CREDIT_DETAIL_TYPE_REVAMP= 6;
	public static final int SCR_CREDIT_DETAIL_TYPE_OWN_REVAMP= 7;
	
	//考试类型：1：期中、2：期末、3: 总评， 4：补考、5：缓考   6重修考 7 自主重修   与成绩构成的类型一致
	public static final int SCR_EXAM_TYPE_MIDDLE = 1;
	public static final int SCR_EXAM_TYPE_FINAL = 2;
	public static final int SCR_EXAM_TYPE_OVERALL = 3;
	public static final int SCR_EXAM_TYPE_MAKEUP = 4;
	public static final int SCR_EXAM_TYPE_DELAYED = 5;
	public static final int SCR_EXAM_TYPE_REVAMP = 6;
	public static final int SCR_EXAM_TYPE_OWN_REVAMP= 7;

	//教学班成绩构成 提交状态 -1：无效， 0：未提交， 1：已提交 
	public static final int SCR_TCH_CLAZZ_CREDIT_DETAIL_STATUS_INIT = -1;
	public static final int SCR_TCH_CLAZZ_CREDIT_DETAIL_STATUS_NO = 0;
	public static final int SCR_TCH_CLAZZ_CREDIT_DETAIL_STATUS_YES = 1;
	
	// 修习类型：0：新修、1：重修、2:自主重修 、 3：补修
	public static final int SCR_STUDY_TYPE_NEW = 0;
	public static final int SCR_STUDY_TYPE_REVAMP = 1;
	public static final int SCR_STUDY_TYPE_OWN_REVAMP= 2;
	public static final int SCR_STUDY_TYPE_REMEDY = 3;
	
	// 成绩结果：0：未录入、1：通过、2：需补考、3：需重修、4 补考通过、 5 重修通过
	//6：待缓考 7：需补修 8：补修通过 9：不通过（可选课使用）10:缓考通过
	//11:免修通过   12:互认通过 13:奖励通过
	public static final int SCR_STUDY_RESULT_NOTHING = 0;
	public static final int SCR_STUDY_RESULT_PASS = 1;
	public static final int SCR_STUDY_RESULT_MAKEUP = 2;
	public static final int SCR_STUDY_RESULT_REVAMP = 3;
	public static final int SCR_STUDY_RESULT_MAKEUP_PASS = 4;
	public static final int SCR_STUDY_RESULT_REVAMP_PASS = 5;
	public static final int SCR_STUDY_RESULT_DELAYED = 6;
	public static final int SCR_STUDY_RESULT_REMEDY = 7;
	public static final int SCR_STUDY_RESULT_REMEDY_PASS = 8;
	public static final int SCR_STUDY_RESULT_NOT_PASS = 9;
	public static final int SCR_STUDY_RESULT_DELAYED_PASS = 10;
	public static final int SCR_STUDY_RESULT_EXMP_PASS = 11;
	public static final int SCR_STUDY_RESULT_REPLACE_PASS = 12;
	public static final int SCR_STUDY_RESULT_REWD_PASS = 13;
	
	//考试状态： -1 初始化 0：待录入 、1：正常、2：缓考、3：禁考、4：缺考、5：作弊、6：违纪
	public static final int SCR_EXAM_STUTUS_INIT = -1;
	public static final int SCR_EXAM_STUTUS_NOENTRY = 0;
	public static final int SCR_EXAM_STUTUS_NORMAL = 1;
	public static final int SCR_EXAM_STUTUS_DEFER = 2;
	public static final int SCR_EXAM_STUTUS_BAN = 3;
	public static final int SCR_EXAM_STUTUS_ABSENCE = 4;
	public static final int SCR_EXAM_STUTUS_CHEAT = 5;
	public static final int SCR_EXAM_STUTUS_BREAKRULE = 6;
	
	/**
	 * 原因类型
	 * 0：修改总评成绩
	 * 1：教务修正成绩
	 * 2：学生缓考
	 * 3：学生禁考
	 * 4：学生作弊
	 * 5：学生缺考
	 * 6：学生违纪
	 */
	public static final int SCR_REASON_TYPE_HEAD_EDIT = 0;
	public static final int SCR_REASON_TYPE_MAJOR_EDIT = 1;
	public static final int SCR_REASON_TYPE_STU_DEFER = 2;
	public static final int SCR_REASON_TYPE_STU_BAN = 3;
	public static final int SCR_REASON_TYPE_STU_CHEAT = 4;
	public static final int SCR_REASON_TYPE_STU_ABSENT = 5;
	public static final int SCR_REASON_TYPE_STU_BREAKRULE = 6;
	public static final int SCR_REASON_TYPE_REV_REASON = 7; //补修原因
	
	/**成绩更新类型  更新*/
	public static final int SCR_STU_CREDIT_CHANGE_TYPE_UPDATE = 0;
	/**成绩更新类型  免修*/
	public static final int SCR_STU_CREDIT_CHANGE_TYPE_EXMP = 1;
	/**成绩更新类型  学分互认*/
	public static final int SCR_STU_CREDIT_CHANGE_TYPE_REPLACE = 2;
	/**成绩更新类型  奖励*/
	public static final int SCR_STU_CREDIT_CHANGE_TYPE_REWD = 3;
	
	//默认及格线
	public static final int SCR_DEFAULT_PASS_LINE = 60;
	//默认没有填写时，记为-1
	public static final double SCR_DEFAULT_NULL = -1.0;
	//默认满分
	public static final int SCR_DEFAULT_FULLCREDIT = 100;
	//默认权重
	public static final double SCR_DEFAULT_RATIO = 0;
	//默认权重priority
	public static final int SCR_DEFAULT_PRIORITY = 9;
	
	//补考通过时的总评 1毕业总评60；     2 毕业总评 == 补考成绩
	public static final String SCR_CREDIT_DEAL_METHOD_MAKEUP = "edu_scr_credit_deal_method_makeup";
	//缓考通过时的总评 1 期末成绩==缓考成绩；     2 毕业总评==缓考成绩
	public static final String SCR_CREDIT_DEAL_METHOD_DELAY = "edu_scr_credit_deal_method_delay";
	
		
	// 排课管理员
	public static final String HEAD_CSKD = "head_cskd_cskd";	// 排课管理排课管理员（总校）
	public static final String DEPT_CSKD = "dept_cskd_cskd";	// 排课管理排课管理员（院系）
	
	public static final String CSKD_LECTURER = "cskd_lecturer";	// 排课管理主讲教师
	
	/**
	 * 排课管理：排课节次设置（每周天数，上午、下午节数，晚上节数）
	 */
	public static final String CSKDPERIODSETTINGS_PERWEEKDAY = "edu_cskd_period_perweekday";
	public static final String CSKDPERIODSETTINGS_AMPERIOD = "edu_cskd_period_amperiod";
	public static final String CSKDPERIODSETTINGS_PMPERIOD = "edu_cskd_period_pmperiod";
	public static final String CSKDPERIODSETTINGS_NIGHTPERIOD = "edu_cskd_period_nightperiod";
	
/**
 * "0",  "单班"
 */
public static final Integer tchclazz_mode0 = 0;
/**
 * "1",  "拆班"
 */
public static final Integer tchclazz_mode1 = 1;
/**
 * "2",  "合班"
 */
public static final Integer tchclazz_mode2 = 2;
/**
 * "3",  "拆合班"
 */
public static final Integer tchclazz_mode3 = 3;
/**
 * "4",  "分层"
 */
public static final Integer tchclazz_mode4 = 4;
/**
 * "5",  "可选课"
 */
public static final Integer tchclazz_mode5 = 5;
/**
 * "6"，"重修班"
 */
public static final Integer tchclazz_mode6 = 6;

//edu_scr_student_credit（学生成绩概要表）：修习类型
/**
 * 0:新修
 */
public static final Integer STUDENTCREDIT_TYPE0 = 0;

/**
 * 1:重修
 */
public static final Integer STUDENTCREDIT_TYPE1 = 1;
/**
 * 2:自主重修
 */
public static final Integer STUDENTCREDIT_TYPE2 = 2;
/**
 * 3:补修
 */
public static final Integer STUDENTCREDIT_TYPE3 = 3;

/**
 * 可选课组建教学班，默认，每个教学班的班容量都是30。
 */
public static final Integer tchclazz_capacity = 30;

/**
 *  选课方式   0：不可选课 1：竞争式 2：选拔式  3：抽签式
 */
public static final Integer optionalSignUpType_0 = 0;
public static final Integer optionalSignUpType_1 = 1;
public static final Integer optionalSignUpType_2 = 2;
public static final Integer optionalSignUpType_3 = 3;

/**
 *	连排节数	1：单节上课  2：两节连排 3：三节连排  4：四节连排
 */
public static final Integer continuousPeriod_1 = 1;
public static final Integer continuousPeriod_2 = 2;
public static final Integer continuousPeriod_3 = 3;
public static final Integer continuousPeriod_4 = 4;

/**
 * 证书管理：学生获得三证导入 类型
 */
public static final int EDU_CERT_IMPORT_INSERT = 1;//允许重复数据
public static final int EDU_CERT_IMPORT_SKIP = 2;///跳过不覆盖
/**
 * 1：计算机水平证书 2：英语水平证书 3：职业资格证书
 */
public static final int CERT_TYPE_IT = 1;
public static final int CERT_TYPE_ENGLISH = 2;
public static final int CERT_TYPE_VOCA = 3;

//学生获得证书审核提交状态, 0:未提交，1:已提交
public static final int CERT_CERTSTUDENT_PROCESS_STATUS_ZERO = 0;
public static final int CERT_CERTSTUDENT_PROCESS_STATUS_ONE = 1;
//学生获得证书审核 审核状态,0:未审核，1:审核通过，2：审核不通过
public static final int CERT_CERTSTUDENT_VERIFY_STATUS_INIT = 0;
public static final int CERT_CERTSTUDENT_VERIFY_STATUS_YES = 1;
public static final int CERT_CERTSTUDENT_VERIFY_STATUS_NO = 2;

//免修与奖励学分管理：学生奖励学分登记第二步：请选择申请奖励必修课还是选修课学分
public static final  String REWD_REWDSTD_COURSE_PROPERTY = "必修课";

// 0：未提交教学任务，1：已提交教学任务，2：已公布课表草稿，3：已生成实时课表
	public static final Integer TASK_SUBMIT_STATUS_ZERO = 0;
	public static final Integer TASK_SUBMIT_STATUS_ONE  = 1;
	public static final Integer TASK_SUBMIT_STATUS_TWO  = 2;
	public static final Integer TASK_SUBMIT_STATUS_THREE  = 3;

// edu_cskd_tch_detail  场地审批状态、院系审批状态、教务审批状态(-1:初始状态  0:未提交，1:已提交，2:审批通过，3:驳回)
	public static final Integer TCHDETAIL_PROCESS_STATUS_INIT  = -1;
	public static final Integer TCHDETAIL_PROCESS_STATUS_ZERO = 0;
	public static final Integer TCHDETAIL_PROCESS_STATUS_ONE  = 1;
	public static final Integer TCHDETAIL_PROCESS_STATUS_TWO  = 2;
	public static final Integer TCHDETAIL_PROCESS_STATUS_THREE  = 3;
	
//  院系调整原因类型/教务调整原因类型  ( 0:排课，1:调换，2:临时)
	public static final Integer CLASSROOM_APPLY_TYPE_ZERO = 0;
	public static final Integer CLASSROOM_APPLY_TYPE_ONE  = 1;
	public static final Integer CLASSROOM_APPLY_TYPE_TWO  = 2;
	//教学任务：通用课程类型 0:非工程实践类 1:工程实践类
	public static final Integer COURSE_TYPE_GENERAL_ZERO  = 0;
	public static final Integer COURSE_TYPE_GENERAL_ONE  = 1;
	//0:主讲教师，1:辅助教师
	public static final Integer DETAIL_ALLOC_TEACHER_ZERO  = 0;
	public static final Integer DETAIL_ALLOC_TEACHER_ONE  = 1;
	
	// -1:开始，0:待处理，1:通过，2:退回，3:下发，4:上报，5：不调整结束
	public static final Integer COMNTASK_PROCESS_STATUS_INIT  = -1;
	public static final Integer COMNTASK_PROCESS_STATUS_ZERO  = 0;
	public static final Integer COMNTASK_PROCESS_STATUS_ONE  = 1;
	public static final Integer COMNTASK_PROCESS_STATUS_TWO  = 2;
	public static final Integer COMNTASK_PROCESS_STATUS_THREE  = 3;
	public static final Integer COMNTASK_PROCESS_STATUS_FOUR  = 4;
	public static final Integer COMNTASK_PROCESS_STATUS_FIVE  = 5;

	//教材：印刷任务表,0：未申请 1：已申请未提交 2：已提交未审核 3：已审核(编辑中) 4：添加到印刷单 5：确认印刷
	public static final Integer TXBK_PRINT_TASK_STATUS_ZERO  = 0;
	public static final Integer TXBK_PRINT_TASK_STATUS_ONE  = 1;
	public static final Integer TXBK_PRINT_TASK_STATUS_TWO  = 2;
	public static final Integer TXBK_PRINT_TASK_STATUS_THREE  = 3;
	public static final Integer TXBK_PRINT_TASK_STATUS_FOUR  = 4;
	public static final Integer TXBK_PRINT_TASK_STATUS_FIVE  = 5;
	
	//教材：印刷单表,订购单表,0：未确认 1：已确认
	public static final Integer TXBK_PRINT_ORDER_STATUS_ZERO  = 0;
	public static final Integer TXBK_PRINT_ORDER_STATUS_ONE  = 1;
	
	//教材：书目订购任务表,0：学生，1：教师
	public static final Integer TXBK_TEXTBOOK_TASK_SCOPE_ZERO  = 0;
	public static final Integer TXBK_TEXTBOOK_TASK_SCOPE_ONE  = 1;
	//教材：书目订购任务表,0：编辑中 1：添加到订购单 2：确认订购
	public static final Integer TXBK_TEXTBOOK_TASK_STATUS_ZERO  = 0;
	public static final Integer TXBK_TEXTBOOK_TASK_STATUS_ONE  = 1;
	public static final Integer TXBK_TEXTBOOK_TASK_STATUS_TWO  = 2;

	//选配处理状态
	public static final int TXBK_MATCHING_PROCESS_STATUS_ZERO = 0; //未提交
	public static final int TXBK_MATCHING_PROCESS_STATUS_ONE = 1;  //已提交 未审核
	public static final int TXBK_MATCHING_PROCESS_STATUS_TWO = 2; //已审核
	
	//教材选配 选配方式
	public static final int  TXBK_MATCHING_TYPE_UNIFY = 0;   //统一选配
	public static final int  TXBK_MATCHING_TYPE_TEACHER = 1; //教师选配
	public static final int  TXBK_MATCHING_TYPE_NOTASK = 2;  //无任务选配
	public static final int  TXBK_MATCHING_TYPE_SPECIAL = 3; //特殊选配
	
	public static final String TXBK_MATCHING_TYPE_UNIFY_STR = "unify"; 	  //统一选配 
	public static final String TXBK_MATCHING_TYPE_TEACHER_STR = "teacher"; //教师选配
	public static final String TXBK_MATCHING_TYPE_NOTASK_STR = "notask";   //无任务选配
	public static final String TXBK_MATCHING_TYPE_SPECIAL_STR = "special"; //特殊选配
	
	//教材选配 是否选择教材标识
	public static final int TXBK_MATCHING_SELECT_ONE = 1; //选择教材
	public static final int TXBK_MATCHING_SELECT_ZERO = 0; //不选教材
	
	//教材选配 选配变更标识
	public static final int TXBK_MATCHING_MODIFY_FLAG_ONE = 1;  //已变更
	public static final int TXBK_MATCHING_MODIFY_FLAG_ZERO = 0; //未变更
	
	//教材选配 订单状态
	public static final int TXBK_MATCHING_ORDER_STATUS_ONE = 1;  //已生成
 	public static final int TXBK_MATCHING_ORDER_STATUS_ZERO = 0; //未生成
 	
	//选配书目表订购需求常量
	public static final int TXBK_MATCHING_TEXTBOOK_SCOPE_ZERO = 0; //学生及教师
	public static final int TXBK_MATCHING_TEXTBOOK_SCOPE_ONE = 1; //仅学生
	public static final int TXBK_MATCHING_TEXTBOOK_SCOPE_TWO = 2; //仅教师
	public static final int TXBK_MATCHING_TEXTBOOK_SCOPE_THREE = 3;	   //无
	
	//订购类型常量
	public static final int TXBK_MATCHING_TEXTBOOK_TYPE_BOOK = 0; //教材
	public static final int TXBK_MATCHING_TEXTBOOK_TYPE_NOTE = 1; //讲义
	public static final int TXBK_MATCHING_TEXTBOOK_TYPE_NONE = 2; //不选教材
	
	//选配申报管理，批量变更教材 校验 结果 标识
	public static final String TXBK_MATCHING_TEXTBOOK_COURSE_DIFFERENT = "COURSE";
	public static final String TXBK_MATCHING_TEXTBOOK_HAVE_ORDER = "ORDER";
	
	/**
	 * 学生阶段类型 CODE 
	 */
	public static final String EDU_ROLL_GRADATION_FULL = "1"; //全日制高职
	public static final String EDU_ROLL_GRADATION_MIDDLE = "2"; //五年制中职阶段
	public static final String EDU_ROLL_GRADATION_FIVE = "3"; //五年制高职阶段
	public static final String EDU_ROLL_GRADATION_ABROAD = "4"; //留学生班

	/**
	 *学制类型 CODE 
	 */
	public static final String EDU_SYSTEM_TWO = "2"; //二年制
	public static final String EDU_SYSTEM_THREE = "3"; //三年制
	public static final String EDU_SYSTEM_FOUR = "4"; //四年制
	public static final String EDU_SYSTEM_FIVE = "5"; //五年制
	public static final String EDU_SYSTEM_SIX = "6"; //六年制
	
	/**
	 * 学生奖惩信息导入 类型
	 */
	public static final int EDU_GRAD_REWARDS_IMPORT_REPLACE = 1;//覆盖已有值
	public static final int EDU_GRAD_REWARDS_IMPORT_SKIP = 2;//跳过不覆盖
	public static final int EDU_GRAD_REWARDS_IMPORT_BACK = 3;//回滚不操作
	
	//单点登录配置参数  Begin
	//访问系统方式  1-普通登录方式（账号密码） 2-单点登录方式 
	public static final int SYS_LOGIN_TYPE_DEFAULT = 1;
	public static final int SYS_LOGIN_TYPE_SSO = 2;
	//访问系统方式配置参数
	public static final String SYS_LOGIN_TYPE = "sys.login.type";
	//登录时长限制
	public static final String SYS_LOGIN_VERIFY_TIMEOUT = "sys.login.verify.timeout";
	//url参数跳转标识 0-不跳转 1-跳转
	public static final String SYS_LOGIN_ZFSSO_URL_FLAG = "sys.login.zfsso.url.flag";
	//事先商定的握手密码
	public static final String SYS_LOGIN_ZFSSOKEY = "sys.login.zfssokey";
	//单点登录配置参数  End
	
	//毕业证书编号组成
	//院校代码
	public static final String COLLEGE_CODE = "college.code";
	//办学类型代码 高职
	public static final String HIGH_TYPE_CODE = "high.type.code";
	//办学类型代码 留学生
	public static final String ABROAD_TYPE_CODE = "abroad.type.code";
	//层次代码
	public static final String LEVEL_CODE = "level.code";
	
	//系统启动时是否需要更新缓存  0-否  1-是
	public static final String SYS_RENEW_CACHE = "sys.renew.cache";
	
	public static final String EXCLUDED_PATH_LIST = "excludedPathList";
	
	public static final String SYS_RESOURCE_FILTER_SUFFIX = "sys.resource.filter.suffix";
	
	public static final String EDU_GRAD_REWARDS_NO = "edu_grad_rewards_no";//奖惩及违纪处理信息编号，对应着excel中的编号
}