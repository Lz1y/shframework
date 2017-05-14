package com.shframework.modules.basic.component;

import static com.shframework.common.util.Constants.CK_QUEUE;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shframework.common.base.BaseComponent;
import com.shframework.common.notification.service.NotifyService;
import com.shframework.common.serializer.JedisSerializer;
import com.shframework.common.util.Constants;
import com.shframework.common.util.ReflectionUtils;
import com.shframework.modules.basic.service.HomeService;
import com.shframework.modules.basic.service.LabelService;
import com.shframework.modules.dict.service.DictCascadeService;
import com.shframework.modules.dict.service.DictEduGradRewardsCategoryService;
import com.shframework.modules.dict.service.DictEduGradRewardsReasonService;
import com.shframework.modules.dict.service.DictEduGradRewardsTypeService;
import com.shframework.modules.dict.service.DictInitService;
import com.shframework.modules.sys.helper.SysHelper;
import com.shframework.modules.sys.service.PermissionService;
import com.shframework.modules.sys.service.ResourceService;
import com.shframework.modules.sys.service.UserService;

import redis.clients.jedis.Jedis;

@Component
public class CacheComponent<T extends Serializable> extends BaseComponent {

	public static final String KEY_SYSNAME="sysname";
	public static final String KEY_CONTEXTPATH="contextPath";
	public static final String KEY_VMROOTPATH="vmRootPath";

	public static final String KEY_STATICLABEL="staticlabel";
	public static final String KEY_MENU="menu";
	public static final String KEY_RESPERMISSION="respermission";
	public static final String KEY_PERMISSIONS="permissions";
	public static final String KEY_CURRC="currc";
	public static final String KEY_RESROLE="resrole";
	public static final String KEY_BREADCRUMB="breadcrumb";
//	public static final String KEY_ALLOCVO = "allocvo";
//	public static final String KEY_TCHCLAZZ = "tchclazz";
//	public static final String KEY_ELCT_TCHCLAZZ = "elct_tchclazz";
	public static final String KEY_DIVISION = "division";
//	public static final String KEY_CLASSROOM = "classroom";
//	public static final String KEY_ALL_CLASSROOM = "classroomAll";
	public static final String KEY_NOTIFYUNREAD = "notifyunread";
	public static final String KEY_SYSUSER = "sysuser";
//	public static final String KEY_STUDENT = "student";
	public static final String KEY_PARENTMAJOR = "parentmajor";
	public static final String KEY_HEADEDITEXAM = "headeditexam";
	public static final String KEY_MAJOREDITEXAM = "majoreditexam";
	public static final String KEY_DELAYEDEXAM = "delayedexam";
	public static final String KEY_BANEXAM = "banexam";
	public static final String KEY_CHEATEXAM = "cheatexam";
	public static final String KEY_ABSENTEXAM = "absentexam";
	public static final String KEY_VIOLATIONEXAM = "violationexam";
	public static final String KEY_REVREASON = "revreason";
	public static final String KEY_ALLSCRREASON = "allscrreason";
	public static final String KEY_EXAMMODE = "exammode";
//	public static final String KEY_EXAMMODE_TEST = "exammodetest";
//	public static final String KEY_EXAMMODE_EXAM = "exammodeexam";
//	public static final String KEY_NATURALCLAZZ = "naturalclazz";
//	public static final String KEY_CLAZZCAMPUS = "clazzcampus";//班级-学期-校区级联
	public static final String KEY_PROVINCE = "provincecascade";//省-市-县级联
//	public static final String KEY_COURSETYPE = "coursetype";//edu_skd_course_type
//	public static final String KEY_COURSE = "course";//edu_skd_course
//	public static final String KEY_EMPLOYEENAME = "employeename";//教职工姓名和拼音
	public static final String KEY_SYSSETTING = "syssetting";//系统设置
	public static final String KEY_YEARTERM = "yearterm";// 上学期，当前学期，下学期 TODO
	public static final String KEY_AllYEARTERM = "allyearterm";// 所有学年学期
	public static final String KEY_SECDIVISION = "secdivision";//部门 学校下面第一级
	public static final String KEY_SYSROLE = "sysrole";//系统角色 
	public static final String KEY_SKDSYSROLE = "skdsysrole";//教学计划部分，使用的系统角色。有一定的特殊性。
	public static final String KEY_AREA = "area"; 
	public static final String KEY_CERT = "cert"; //证件类型
	public static final String KEY_ETHNICITY = "ethnicity"; 
	public static final String KEY_NATION = "nation"; 
	public static final String KEY_POLICITALSTATUS = "policitalstatus"; 
	public static final String KEY_CERTTYPE = "certtype"; //证书类型
//	public static final String KEY_CERT_CERT = "certcert"; //证书
	public static final String KEY_BUILDING = "building"; 
	public static final String KEY_CLRTYPE = "clrtype"; 
	public static final String KEY_ACADEMICYEAR = "academicyear"; 
	public static final String KEY_ACDYEARTERM = "acdyearterm"; 
	public static final String KEY_CAMPUS = "campus"; 
	public static final String KEY_CATEGORYMAJOR = "categorymajor"; 
	public static final String KEY_COLLEGE = "college"; 
	public static final String KEY_DEGREE = "degree"; 
	public static final String KEY_DEPARTMENT = "department"; 
	public static final String KEY_DIPLOMA = "diploma"; 
	public static final String KEY_EDUSYSTEM = "edusystem"; 
	public static final String KEY_GROUPMAJOR = "groupmajor"; 
	public static final String KEY_LABEL = "label"; 
	public static final String KEY_MAJOR = "major"; 
	public static final String KEY_MAJORDEPARTMENT = "majordepartment"; 
	public static final String KEY_MAJORFIELD = "majorfield"; 
	public static final String KEY_TERM = "term"; 
	public static final String KEY_MODE = "mode"; 
	public static final String KEY_CANDIDATETYPE = "candidatetype"; 
	public static final String KEY_COMEFROM = "comefrom"; 
	public static final String KEY_DIPLOMALEVEL = "diplomalevel"; 
	public static final String KEY_ENROLLCODE = "enrollcode"; 
	public static final String KEY_ENROLLFROM = "enrollfrom"; 
	public static final String KEY_ENROLLTYPE = "enrolltype";
	public static final String KEY_GRADATION = "gradation"; 
	public static final String KEY_LOCAL = "local"; 
	public static final String KEY_ROLLCHANGEREASON = "rollchangereason"; 
	public static final String KEY_ROLLCHANGETYPE = "rollchangetype"; 
	public static final String KEY_STUDYMODE = "studymode"; 
	public static final String KEY_SUBJECT = "subject"; 
	public static final String KEY_TRAININGLEVEL = "traininglevel"; 
	public static final String KEY_TRAININGMODE = "trainingmode"; 
	public static final String KEY_COURSEATTRIBUTE = "courseattribute"; 
	public static final String KEY_COURSELEVEL = "courselevel"; 
	public static final String KEY_COURSEMODE = "coursemode"; 
	public static final String KEY_COURSEPROPERTY = "courseproperty"; 
	public static final String KEY_OPENTERM = "openterm"; 
	public static final String KEY_TEACHINGMETHODS = "teachingmethods"; 
	public static final String KEY_POSTLEVEL = "postlevel"; 
	public static final String KEY_POSTTYPE = "posttype"; 
	public static final String KEY_STAFFTYPE = "stafftype";
//	public static final String KEY_EMPLOYEENO = "employeeno";//教职工姓名和拼音
	
	//教材相关-start
//	public static final String KEY_EDUTXBKPUBLISHER = "edutxbkpublisher";//出版单位表
//	public static final String KEY_EDUTXBKTEXTBOOKATTR = "edutxbktextbookattr";//教材性质表
//	public static final String KEY_EDUTXBKTEXTBOOKLEVEL = "edutxbktextbooklevel";//教材层次表
//	public static final String KEY_EDUTXBKSUPPLIER = "edutxbksupplier";//供书商表
//	public static final String KEY_EDUTXBKPRINTSHOP = "edutxbkprintshop";//印刷厂表
//	public static final String KEY_EDUTXBKMATCHINGCHANGEREASON = "edutxbkmatchingchangereason";//选配变更原因表
	
//	public static final String KEY_EDUTXBKTEXTBOOK = "edutxbktextbook";//书目表

	//教材相关-end

//	public static final String KEY_CASCADE_DEP_TEACHER = "cascade_dep_teacher";//系部-教师级联
//	public static final String KEY_CASCADE_CAMPUS_YEARTERM_TCHCLAZZ = "cascade_campus_yearterm_tchclazz";//“校区 + 学年学期 - 教学班” 级联
//	public static final String KEY_ELCT_BATCH_TCHCLAZZ = "batch_tchclazz";
//	public static final String KEY_ELCT_TCHBATCH_STUDENT = "tchbatch_student";
//	public static final String KEY_ELCT_ELCTBATCH = "elctbatch";
//	public static final String KEY_ELCT_ALLMYELCTCRS = "elct_allmyelctcrs"; // 所有学生的我的可选课
	
	//字典表级联
	public static final String KEY_CASCADE_CAMPUS_BUILDING = "cascade_campus_building";
	public static final String KEY_CASCADE_DEP_MAJOR = "cascade_dep_major";
	public static final String KEY_CASCADE_CATE_GROUP = "cascade_cate_group";
	public static final String KEY_CASCADE_GROUP_MAJOR = "cascade_group_major";
	public static final String KEY_CASCADE_MAJOR_MAJORFIELD = "cascade_major_majorfield";
//	public static final String KEY_CASCADE_MAJORFIELD_CLAZZ = "cascade_majorfield_clazz";//通过majorFieldId, 找到班级信息
	public static final String KEY_CASCADE_CAMPUS_DEPARTMENT = "cascade_campus_department"; // 校区-系部
	//院系-行政班
//	public static final String KEY_CASCADE_DEPARTMENT_CLAZZ = "cascade_department_clazz"; 

	
	//工作量相关
//	public static final String KEY_WKLD_DICT_PC_TITLE = "wkldDictPcTitle";
	
//	public static final String KEY_WKLD_DICT_AUX = "wkldDictAux"; //教辅工作项目
	
//	public static final String KEY_WKLD_DICT_AUX_TITLE = "wkldDictAuxTitle"; //教辅工作项目 key为title
	
//	public static final String KEY_WKLD_DICT_INNO = "wkldDictInno";//新课系数
	
//	public static final String KEY_WKLD_DICT_CAPACITY = "wkldDictcapacity";//班级容量系数
	
	public static final String KEY_DICT_GRAD_REWARDS_TYPE = "rewardstype";
	
	public static final String KEY_DICT_GRAD_REWARDS_CATEGORY = "dictEduGradRewardsCategory";
	
	public static final String KEY_DICT_GRAD_REWARDS_REASON = "dictEduGradRewardsReason";
	
	public static final List<String> KEYS = Arrays.asList(Constants.getRedisKey("keys").split(","));
	
	public static void main(String[] args) {
		System.out.println(KEYS.size());
	}
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private LabelService labelService;
	
	@Autowired
	private DictInitService dictInitService;
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private NotifyService notifyService;
	
	@Autowired
	private SysHelper sysHelper;
	
	@Autowired
	private DictCascadeService dictCascadeService;
	
	@Autowired
	private DictEduGradRewardsTypeService dictEduGradRewardsTypeService;
	
	@Autowired
	private DictEduGradRewardsCategoryService dictEduGradRewardsCategoryService;
	
	@Autowired
	private DictEduGradRewardsReasonService dictEduGradRewardsReasonService;
	
	public void set(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key, value);
//			jedis.expire(key, 7200);
		} finally {
			returnJedis(jedis);
		}
	}
	
	/**
	 * 更新缓存
	 * @param key
	 */
	public void renew(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(key.getBytes());
		} catch (Exception e) {
			returnJedis(jedis);
		}
		CK_QUEUE.add(key);
	}
	
	/**
	 * hash类型更新缓存   key是页面不能修改的值，如：id
	 * redis hash 格式： {student_10001:[id:10001,userNo:a10001,....],...}
	 */
	public void renew4hash(String operate,String key,Map<String,String> value) {
		
		Jedis jedis = null;
		try {
			jedis = getJedis();
			switch (operate) {
			case "insert":
				jedis.hmset(key, value);
				break;
				
			case "update":
				jedis.hmset(key, value);
				break;	
				
			case "delete":
				jedis.del(key);
				break;	
				
			default:
				break;
			}
		} catch (Exception e) {
			returnJedis(jedis);
		}
//		CK_QUEUE.add(key);
	}
	
	/**
	 * hash类型更新缓存  key是页面不能修改的值，如：userNO
	 * redis hash 格式： {student_a10001:[id:10001,userNo:a10001,....],...}
	 */
	public void renew4hash(String operate,String key,Map<String,String> value,String oldKey) {
		
		Jedis jedis = null;
		try {
			jedis = getJedis();
			switch (operate) {
			case "insert":
				jedis.hmset(key, value);
				break;
				
			case "update":
				jedis.del(oldKey);
				jedis.hmset(key, value);
				break;	
				
			case "delete":
				jedis.del(oldKey);
				break;	
				
			default:
				break;
			}
		} catch (Exception e) {
			returnJedis(jedis);
		}
//		CK_QUEUE.add(key);
	}
	
	
	/**
	 * 获取具体属性
	 * @param key 缓存Key
	 * @param id  业务id
	 * @param property null为tilte
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String attr(String key, Integer id, String...property) {
		if(id == null || id==0) return null;
		return ReflectionUtils.invokeGetterMethod(((Map<String, Object>)resource(key)).get(id.toString()), property.length>0?property[0]:"title").toString();
	}

	/**
	 * 业务缓存更新(暂限权限使用)
	 * @param key
	 * @param t
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update(String key, Object t) {
		JedisSerializer js = new JedisSerializer();
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.set(key.getBytes(), js.serialize(t));
		} finally {
			js = null;
			returnJedis(jedis);
			CK_QUEUE.add(key);
		}
	}
	
	/**
	 * 获取缓存
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T resource(String key) {
		JedisSerializer<T> js = new JedisSerializer<T>();
		Jedis jedis = null;
		try {
			jedis = getJedis();
			byte[] data = jedis.get(key.getBytes());
			if (data == null || data.length == 0) {
				T t = null;
				switch (key) {
				case KEY_CURRC:
					t = (T) resourceService.curRc();
					break;
					
				case KEY_BREADCRUMB:
					t = (T) homeService.getBreadcrumbNavigation();
					break;
					
				case KEY_RESROLE:
					t = (T) resourceService.res_role();
					break;
					
				case KEY_MENU:
					t = (T) homeService.getMenuTree();
					break;
					
//				case KEY_TCHCLAZZ:
//					t = (T) dictInitService.getAllTchClazz();
//					break;
					
//				case KEY_ELCT_TCHCLAZZ:
//					t = (T) dictInitService.getAllElctTchClazzCache();
//					break;	
					
				case KEY_STATICLABEL:
					t = (T) labelService.getAllStaticLabel();
					break;
					
				case KEY_SYSUSER:
					t = (T) userService.getAllActiveUser();
					break;
					
//				case KEY_STUDENT:
//					t = (T) userService.getStuUser();
//					break;
					
//				case KEY_ALLOCVO:
//					t = (T) dictInitService.allocVo();
//					break;
					
//				case KEY_CLASSROOM:
//					t = (T) dictInitService.queryAllClassroomVO();
//					break;
					
//				case KEY_ALL_CLASSROOM:
//					t = (T) dictInitService.queryAllStatusClassroomVO();
//					break;
					
				case KEY_DIVISION:
					t = (T) dictInitService.getAllDivision(null);
					break;
					
				case KEY_NOTIFYUNREAD:
					t = (T) notifyService.unReadCount();
					break;

				case KEY_PERMISSIONS:
					t = (T) sysHelper.permissions();
					break;
				case KEY_RESPERMISSION:
					t = (T) permissionService.getResPerMap();
					break;
					
				case KEY_PARENTMAJOR:
					t = (T) dictInitService.getParentMajor();
					break;
					
				case KEY_HEADEDITEXAM:
					t = (T) dictInitService.getScrReason(Constants.SCR_REASON_TYPE_HEAD_EDIT);
					break;
					
				case KEY_MAJOREDITEXAM:
					t = (T) dictInitService.getScrReason(Constants.SCR_REASON_TYPE_MAJOR_EDIT);
					break;
					
				case KEY_DELAYEDEXAM:
					t = (T) dictInitService.getScrReason(Constants.SCR_REASON_TYPE_STU_DEFER);
					break;
					
				case KEY_BANEXAM:
					t = (T) dictInitService.getScrReason(Constants.SCR_REASON_TYPE_STU_BAN);
					break;
					
				case KEY_CHEATEXAM:
					t = (T) dictInitService.getScrReason(Constants.SCR_REASON_TYPE_STU_CHEAT);
					break;
					
				case KEY_ABSENTEXAM:
					t = (T) dictInitService.getScrReason(Constants.SCR_REASON_TYPE_STU_ABSENT);
					break;
					
				case KEY_VIOLATIONEXAM:
					t = (T) dictInitService.getScrReason(Constants.SCR_REASON_TYPE_STU_BREAKRULE);
					break;
					
				case KEY_REVREASON:
					t = (T) dictInitService.getScrReason(Constants.SCR_REASON_TYPE_REV_REASON);
					break;
					
				case KEY_ALLSCRREASON:
					t = (T) dictInitService.getScrReason(null);
					break;
					
				case KEY_EXAMMODE:
					t = (T) dictInitService.getExamMode();
					break;
					
//				case KEY_EXAMMODE_TEST:
//					t = (T) dictInitService.getExamMode(ExamConstants.EXAMFLAG_TEST);
//					break;
					
//				case KEY_EXAMMODE_EXAM:
//					t = (T) dictInitService.getExamMode(ExamConstants.EXAMFLAG_EXAMINATION);
//					break;
					
//				case KEY_NATURALCLAZZ:
//					t = (T) dictInitService.getAllClazz();
//					break;
					
//				case KEY_CLAZZCAMPUS:
//					t = (T) dictInitService.getAllClazzCampus();
//					break;
					
				case KEY_PROVINCE:
					t = (T) dictInitService.getProvinceCascade();
					break;
					
//				case KEY_COURSETYPE:
//					t = (T) dictInitService.getAllSkdCourseType();
//					break;
					
//				case KEY_COURSE:
//					t = (T) dictInitService.getAllSkdCourse();
//					break;
					
//				case KEY_EMPLOYEENAME:
//					t = (T) dictInitService.getAllEmployeeName();
//					break;
//				case KEY_EMPLOYEENO:
//					t = (T) dictInitService.getAllEmployeeNo();
//					break;
					
				case KEY_SYSSETTING:
					t = (T) dictInitService.getAllSysSetting();
					break;
					
				case KEY_YEARTERM:
					t = (T) dictInitService.getYearTerm();
					break;
					
				case KEY_AllYEARTERM:
					t = (T) dictInitService.getAllYearTerm();
					break;
					
				case KEY_SECDIVISION:
					t = (T) dictInitService.getAllDivision(Constants.DEPARTMENT_ROOT_ID);
					break;
					
				case KEY_SYSROLE:
					t = (T) dictInitService.getSysRole(true);
					break;
					
				case KEY_SKDSYSROLE:
					t = (T) dictInitService.getSysRole(false);
					break;

				case KEY_AREA:
					t = (T) getTableMapByKey(KEY_AREA);
					break;

				case KEY_CERT:
					t = (T) getTableMapByKey(KEY_CERT);
					break;

				case KEY_ETHNICITY:
					t = (T) getTableMapByKey(KEY_ETHNICITY);
					break;

				case KEY_NATION:
					t = (T) getTableMapByKey(KEY_NATION);
					break;

				case KEY_POLICITALSTATUS:
					t = (T) getTableMapByKey(KEY_POLICITALSTATUS);
					break;

				case KEY_CERTTYPE:
					t = (T) getTableMapByKey(KEY_CERTTYPE);
					break;

//				case KEY_CERT_CERT:
//					t = (T) certService.getCertTableMapByKey(KEY_CERT_CERT);
//					break;
					
				case KEY_BUILDING:
					t = (T) getTableMapByKey(KEY_BUILDING);
					break;

				case KEY_CLRTYPE:
					t = (T) getTableMapByKey(KEY_CLRTYPE);
					break;

				case KEY_ACADEMICYEAR:
					t = (T) getTableMapByKey(KEY_ACADEMICYEAR);
					break;

				case KEY_ACDYEARTERM:
					t = (T) getTableMapByKey(KEY_ACDYEARTERM);
					break;

				case KEY_CAMPUS:
					t = (T) getTableMapByKey(KEY_CAMPUS);
					break;

				case KEY_CATEGORYMAJOR:
					t = (T) getTableMapByKey(KEY_CATEGORYMAJOR);
					break;

				case KEY_COLLEGE:
					t = (T) getTableMapByKey(KEY_COLLEGE);
					break;

				case KEY_DEGREE:
					t = (T) getTableMapByKey(KEY_DEGREE);
					break;

				case KEY_DEPARTMENT:
					t = (T) getTableMapByKey(KEY_DEPARTMENT);
					break;

				case KEY_DIPLOMA:
					t = (T) getTableMapByKey(KEY_DIPLOMA);
					break;

				case KEY_EDUSYSTEM:
					t = (T) getTableMapByKey(KEY_EDUSYSTEM);
					break;

				case KEY_GROUPMAJOR:
					t = (T) getTableMapByKey(KEY_GROUPMAJOR);
					break;

				case KEY_LABEL:
					t = (T) getTableMapByKey(KEY_LABEL);
					break;

				case KEY_MAJOR:
					t = (T) getTableMapByKey(KEY_MAJOR);
					break;

				case KEY_MAJORDEPARTMENT:
					t = (T) getTableMapByKey(KEY_MAJORDEPARTMENT);
					break;

				case KEY_MAJORFIELD:
					t = (T) getTableMapByKey(KEY_MAJORFIELD);
					break;

				case KEY_TERM:
					t = (T) getTableMapByKey(KEY_TERM);
					break;

				case KEY_MODE:
					t = (T) getTableMapByKey(KEY_MODE);
					break;

				case KEY_CANDIDATETYPE:
					t = (T) getTableMapByKey(KEY_CANDIDATETYPE);
					break;

				case KEY_COMEFROM:
					t = (T) getTableMapByKey(KEY_COMEFROM);
					break;

				case KEY_DIPLOMALEVEL:
					t = (T) getTableMapByKey(KEY_DIPLOMALEVEL);
					break;

				case KEY_ENROLLCODE:
					t = (T) getTableMapByKey(KEY_ENROLLCODE);
					break;

				case KEY_ENROLLFROM:
					t = (T) getTableMapByKey(KEY_ENROLLFROM);
					break;

				case KEY_ENROLLTYPE:
					t = (T) getTableMapByKey(KEY_ENROLLTYPE);
					break;

				case KEY_GRADATION:
					t = (T) getTableMapByKey(KEY_GRADATION);
					break;

				case KEY_LOCAL:
					t = (T) getTableMapByKey(KEY_LOCAL);
					break;

				case KEY_ROLLCHANGEREASON:
					t = (T) getTableMapByKey(KEY_ROLLCHANGEREASON);
					break;

				case KEY_ROLLCHANGETYPE:
					t = (T) getTableMapByKey(KEY_ROLLCHANGETYPE);
					break;

				case KEY_STUDYMODE:
					t = (T) getTableMapByKey(KEY_STUDYMODE);
					break;

				case KEY_SUBJECT:
					t = (T) getTableMapByKey(KEY_SUBJECT);
					break;

				case KEY_TRAININGLEVEL:
					t = (T) getTableMapByKey(KEY_TRAININGLEVEL);
					break;

				case KEY_TRAININGMODE:
					t = (T) getTableMapByKey(KEY_TRAININGMODE);
					break;

				case KEY_COURSEATTRIBUTE:
					t = (T) getTableMapByKey(KEY_COURSEATTRIBUTE);
					break;

				case KEY_COURSELEVEL:
					t = (T) getTableMapByKey(KEY_COURSELEVEL);
					break;

				case KEY_COURSEMODE:
					t = (T) getTableMapByKey(KEY_COURSEMODE);
					break;

				case KEY_COURSEPROPERTY:
					t = (T) getTableMapByKey(KEY_COURSEPROPERTY);
					break;

				case KEY_OPENTERM:
					t = (T) getTableMapByKey(KEY_OPENTERM);
					break;

				case KEY_TEACHINGMETHODS:
					t = (T) getTableMapByKey(KEY_TEACHINGMETHODS);
					break;

				case KEY_POSTLEVEL:
					t = (T) getTableMapByKey(KEY_POSTLEVEL);
					break;

				case KEY_POSTTYPE:
					t = (T) getTableMapByKey(KEY_POSTTYPE);
					break;

				case KEY_STAFFTYPE:
					t = (T) getTableMapByKey(KEY_STAFFTYPE);
					break;
					
//				case KEY_ELCT_BATCH_TCHCLAZZ:
//					t = (T) elctModeService.getTchClazzListOfElctMode();
//					break;
					
//				case KEY_ELCT_TCHBATCH_STUDENT:
//					t = (T) elctModeService.getTchBatchStudentList(0,null); // 0:已报名，1:报名成功，2:报名失败，3:处理中，4:取消
//					break;
					
//				case KEY_ELCT_ELCTBATCH:
//					t = (T) elctBatchService.getAllBatch();
//					break;
					
//				case KEY_CASCADE_DEP_TEACHER:
//					t = (T) employeeService.getCasDepTeacher();
//					break;
					
//				case KEY_CASCADE_CAMPUS_YEARTERM_TCHCLAZZ:
//					t = (T) tchClazzService.getCascadeCampusYeartermTchClazz();
//					break;
				case KEY_CASCADE_CAMPUS_BUILDING:
					t = (T) dictCascadeService.getCampusBuildingCascadeInfo();
					break;
//				case KEY_CASCADE_DEPARTMENT_CLAZZ:
//					t = (T) dictCascadeService.getDepartmentClazzCascadeInfo();
//					break;
				case KEY_CASCADE_DEP_MAJOR:
					t = (T) dictCascadeService.getDepMajorCascadeInfo();
					break;
				case KEY_CASCADE_CATE_GROUP:
					t = (T) dictCascadeService.getCategoryGroupCascadeInfo();
					break;
				case KEY_CASCADE_GROUP_MAJOR:
					t = (T) dictCascadeService.getGroupMajorCascadeInfo();
					break;
				case KEY_CASCADE_MAJOR_MAJORFIELD:
					t = (T) dictCascadeService.getMajorMajorfieldCascadeInfo();
					break;
				case KEY_CASCADE_CAMPUS_DEPARTMENT:
					t = (T) dictCascadeService.getDepartmentsByCampusId();
					break;
//				case KEY_CASCADE_MAJORFIELD_CLAZZ:
//					t = (T) dictCascadeService.getMajorfieldClazzCascadeInfo();
//					break;
//				case KEY_EDUTXBKPUBLISHER:
//					t = (T) txbkCommonService.getTableMapByKey(key);
//					break;
//				case KEY_EDUTXBKTEXTBOOKATTR:
//					t = (T) txbkCommonService.getTableMapByKey(key);
//					break;
//				case KEY_EDUTXBKTEXTBOOKLEVEL:
//					t = (T) txbkCommonService.getTableMapByKey(key);
//					break;
//				case KEY_EDUTXBKSUPPLIER:
//					t = (T) supplierService.getTableMap();
//					break;
//				case KEY_EDUTXBKPRINTSHOP:
//					t = (T) printshopService.getTableMap();
//					break;
//				case KEY_EDUTXBKMATCHINGCHANGEREASON:
//					t = (T) txbkCommonService.getTableMapByKey(key);
//					break;
//				case KEY_EDUTXBKTEXTBOOK:
//					t = (T) txbkCommonService.getTextbookTableMapByKey(key);
//					break;
//				case KEY_ELCT_ALLMYELCTCRS:
//					t = (T) dictInitService.allmyelctcrs();
//					break;
//				case KEY_WKLD_DICT_PC_TITLE:
//					t = (T) dictPracticeCategoryService.getTableMap();
//					break;	
//				case KEY_WKLD_DICT_AUX:
//					t = (T) auxiliaryService.getTableMap();
//					break;
//				case KEY_WKLD_DICT_AUX_TITLE:
//					t = (T) auxiliaryService.getAuxiliaryTitleMap();
//					break;
//				case KEY_WKLD_DICT_INNO:
//					t = (T) innoFactorService.getTableMap();
//					break;
//				case KEY_WKLD_DICT_CAPACITY:
//					t = (T) capacityFactorService.getTableMap();
//					break;					
				case KEY_DICT_GRAD_REWARDS_TYPE:
					t = (T) dictEduGradRewardsTypeService.getTableMap();
					break;
				case KEY_DICT_GRAD_REWARDS_CATEGORY:
					t = (T) dictEduGradRewardsCategoryService.getTableMap();
					break;
				case KEY_DICT_GRAD_REWARDS_REASON:
					t = (T) dictEduGradRewardsReasonService.getTableMap();
					break;					
				}

				jedis.set(key.getBytes(), js.serialize(t));
				data = jedis.get(key.getBytes());
			}
			return js.deserialize(data);
		} finally {
			js = null;
			returnJedis(jedis);
		}
	}
	
	/**
	 * 通过key 加载相应表的未删除且状态为“选用”的记录
	 * 
	 * @param key
	 * @return
	 * @author RanWeizheng
	 * @date 2016年7月29日 下午3:41:39
	 */
	private Map<String, Object> getTableMapByKey(String key) {
		Map<String, String[]> dictInfo = dictInitService.getAllDictInfo();
		String[] info = dictInfo.get(key);
		if (info != null) {
			return dictInitService.getTableMapByName(info[0]);
		}
		return new HashMap<>();
	}
	
	// -n
	public void decr(String key,Integer n) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.decrBy(key,n);
		} finally {
			returnJedis(jedis);
		}
	}
		
	// -1
	public void decr(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.decr(key);
		} finally {
			returnJedis(jedis);
		}
	}

	
	// +n
	public void incr(String key,Integer n) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.incrBy(key,n);
		} finally {
			returnJedis(jedis);
		}
	}
	
	// +1
	public void incr(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.incr(key);
		} finally {
			returnJedis(jedis);
		}
	}
	
	public Set<String> getKeys(String pattern){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.keys(pattern);
		} finally {
			returnJedis(jedis);
		}
	}
	
	public void flushDB(){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.flushDB();
		} finally {
			returnJedis(jedis);
		}
	}
	
	
	@PostConstruct
	public void initAllCache(){
		if(Integer.valueOf(Constants.getConfigValue(Constants.SYS_RENEW_CACHE, Constants.DICT_COMMON_NO+"")).intValue() == Constants.DICT_COMMON_YES){
			for (String key : CacheComponent.KEYS) this.renew(key);
		}
	}
	
}