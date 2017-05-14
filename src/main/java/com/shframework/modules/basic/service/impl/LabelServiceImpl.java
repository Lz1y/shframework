package com.shframework.modules.basic.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shframework.common.util.Constants;
import com.shframework.common.util.LabelUtil;
import com.shframework.modules.basic.service.LabelService;
import com.shframework.modules.dict.entity.vo.LabelVo;
import com.shframework.modules.dict.service.DictHelpService;

@Service
public class LabelServiceImpl implements LabelService{
	
	@Autowired
	private DictHelpService dictHelpService;
	
	/**
	 * 将所有字典表相关的静态标签放到这里
	 * @return
	 * @author RanWeizheng
	 */
	@Override
//	@Cacheable(value = "staticLabelCache", key = "'labelName'")
	public Map<String, Object> getAllStaticLabel(){
		Map<String, Object> allMap = new HashMap<String, Object>();
		allMap.put("status", getAllStatusName());//选用状态
		allMap.put("standard", getAllStandardName());//标准
		allMap.put("majorType", getAllMajorTypeName());//专业类型（中职，高职）
		allMap.put("departmentType", getAllDepartmentTypeName());//部门类型
		
		allMap.put("clazzStatus", getAllClazzStatusName());//班级状态
		allMap.put("presenceFlag", getAllPresenceFlagName());//是否在校
		allMap.put("rollFlag", getAllRollFlagName());//是否有教委学籍
		allMap.put("paymentFlag", getAllPaymentFlagName());//是否缴费
		allMap.put("registerStatus", getAllRegisterStatusName());//注册状态
		allMap.put("confirmFlag", getAllConfirmFlagName());//学籍确认状态
		allMap.put("employeeType", getAllEmployeeTypeName());//雇员类型 班主任/教学
		allMap.put("scheduleStatus", getAllScheduleStatusName());//任务完成状态
		allMap.put("jobType", getAllJobTypeName());//职务类别
		allMap.put("gender", getAllGenderName());//性别
		
		allMap.put("status_name", getCommonlStatusName());//可用 or 不可用
		allMap.put("locked_name", getLockedName());//锁定状态
		allMap.put("delete_name", getLogicDeleteName());//是否删除

		allMap.put("startStatus", getStatus());
		allMap.put("forceFlag", getForceFlag());
		allMap.put("optionalFlag", getOptionalFlag());
		
		allMap.put("coopFlag", getCoopflag());//课程  是否校企合作课程  0,1
		allMap.put("examFlag", getExamflag());//课程  考试考查  0,1
		allMap.put("showUrl", getShowUrl());//课程  是否显示文件:显示,不显示  0,1
		
		allMap.put("isZeroORNot", getIsZeroORNot());// 0:否，1:是
		allMap.put("isYesORNo", getIsYesORNo());// 0:不可以，1:可以
		
		//模板格式
		allMap.put("templateFormat", getTemplateFormat());
		//模板类型
		allMap.put("templateType", getTemplateType());
		
		// 教学计划 进行状态 : 总校开始，pending到院系，pending到专业负责人，pending到教务处长，pending到总校，审批结束，不调整结束 （复合查询条件）
		allMap.put("pendingRoleProcessStatus", getPendingRoleProcessStatus());
		
		//教学计划 是否通过 0:不通过，1:通过
		allMap.put("passStatus", getPassStatus());
		
		//教学计划课程计算方法名称集合
		allMap.put("courseTypeMethodNameList", getCourseTypeMethodNameList());
		
		/**
		 * 教学任务   教学任务管理   不可选课的教学任务   不可选课组织教学班
		 */
		allMap.put("mode", mode());							// 组班模式 0：单一行政班  1：拆班   2：合班   3：拆合班   4：分层教学 
		allMap.put("allmode", allmode());					// 组班模式 0：单班  1：拆班   2：合班   3：拆合班   4：分层 5：可选课 6：重修班
		allMap.put("shortmode", shortmode());				// 组班模式简称 0：单班  1：拆班   2：合班   3：拆合班   4：分层 5：可选课 6：重修班
		allMap.put("optshortmode", optshortmode());				// 组班模式简称 0：单班  1：拆班   2：合班   3：拆合班   4：分层
		
		allMap.put("signUpType", signUpType());				// 选课方式 0：不可选课  1：竞争式 2：选拔式  3：抽签式
		
		allMap.put("optionalSignUpType", optionalSignUpType());	// 选课方式   1：竞争式 2：选拔式  3：抽签式
		
		allMap.put("continuousPeriod", continuousPeriod());	// 连排节数	1：单节上课  2：两节连排 3：三节连排  4：四节连排
		allMap.put("continuousPeriod2", continuousPeriod2());	// 连排节数	2-6
		
		allMap.put("clazzCount", clazzCount());				// 教学任务   教学任务管理   不可选课的教学任务   拆班-->拆成几个教学班  2-8
		
		//教学班分配节次表	0:每周，1:单周，2:双周
		allMap.put("oddDualWeek", oddDualWeek());
		/**
		 * 周三3、4节（1-18单周），校本部-1号楼-306；
		 * 周五5、6节（1-18周），系定；
		 * ${weekSeqStr + " " + oddDualWeekStr + " " + weekDayStr +" "+ periodStr + " " + classroomStr +"；<br/>"}
		 */
		allMap.put("oddDualWeekShow", oddDualWeekShow());
		//教学班分配节次表	 "0",  "周日"  "1",  "周一" ... "6",  "周六"
		allMap.put("weekDay", weekDay());
		// 节次
		allMap.put("taskPeriod", taskPeriod()); 
		// 教学场地录入类型
		allMap.put("entryType", entryType()); 
		//场地审批状态、院系审批状态、教务审批状态(-1:——  0:未提交，1:已提交，2:审批通过，3:驳回)
		allMap.put("timetableApplyShow", timetableApplyShow()); 
		//场地审批状态、院系审批状态、教务审批状态，搜索显示用(0:未提交，1:已提交，2:审批通过，3:驳回)
		allMap.put("timetableApplySearch", timetableApplySearch()); 
		//教学运行，代课教师确认状态 ：(-1:——  0:未提交，1:已提交，2:已确认，3:驳回)
		allMap.put("empApproveShow", empApproveShow()); 
		//教学运行，代课教师确认状态 ：(0:未提交，1:已提交，2:已确认，3:驳回)
		allMap.put("empApproveSearch", empApproveSearch()); 
		//教学运行，调整原因类型 0:因公，1:因私，2:其他
		allMap.put("applyReasonType", applyReasonType()); 
		
		//进程自定义符号
		allMap.put("progCustomSymbol", progCustomSymbol());
		//进程制定状态
		allMap.put("processStatus", processStatus());

		allMap.put("scopetype", getScopeType());
		
		/**
		 * 成绩管理部分，特有label
		 */
		//学分 启用状态  0:未启用，1:启用中， 2:已作废
		allMap.put("creditStatus", getCreditStatus());
		
		//总评成绩构成 类型 (不包括补考缓考)
		allMap.put("creditDetailType", getCreditDetailType());
		
		//教学班成绩构成 提交状态
		allMap.put("tchCreditDetailStatus", getTchCreditDetailStatus());
		
		//成绩管理 总评成绩表 修习类型
		allMap.put("scrCGeneralType", getCGeneralType());
		//修习类型
		allMap.put("scrStudyType", getStudyType());
		//考试类型
		allMap.put("scrExamType", getExamType());
		//考试状态
		allMap.put("scrExamStatus", getExamStatus());
		//待缓考、补考查询页 考试状态
		allMap.put("scrExamStatus1", getExamStatus1());
		//成绩结果
		allMap.put("scrStudyResult", getStudyResult());
		//成绩单标识
		allMap.put("scrTranscriptSign", getTranscriptSign());
		
		allMap.put("revmode", getRevMode());
		allMap.put("allrevmode", getAllRevMode());
		//重修 确认状态   新生学籍信息确认
		allMap.put("confirmStatus", getConfirmStatus());
		allMap.put("initiateStatus", getInitiateStatus());
		//重修-待修习类型
		allMap.put("revtype", getRevType());
		//是否组织过补修
		allMap.put("isrepair", isRepair());
		//排课状态 0:未排课 1:已排课
		allMap.put("isAlloc", isAlloc());
		//分配状态 0:未分配 1:已分配
		allMap.put("isNullAlloc", isNullAlloc());
		
		//学生获得证书提交 0:未提交，1:已提交
		allMap.put("certProcessStatus", certProcessStatus());
		//学生获得证书审核 0:未审核，1:审核通过，2：审核不通过
		allMap.put("certVerifyStatus", certVerifyStatus());
		
		//课类  0：非工程实践  1：工程实践
		allMap.put("courseTypeGeneral", courseTypeGeneral());
		
		// 选课方式 0:竞争式，1:选拔式，2:抽签式，3:指定式
		allMap.put("elecmode", elecmode());
		
		// yesorno 0:否，1:是 
		allMap.put("yesorno", yesorno());
		
		// 是否允许  0：不允许 1：允许
		allMap.put("isAllow", isAllow());
		
		// 开放选课状态  0：关闭 1：开放
		allMap.put("isOpen", isOpen());
		// 选课批次表进行状态  0:未确认抽签结果，1:已确认抽签结果
		allMap.put("batchstatus", batchstatus());
		
		//选课 进行状态
		allMap.put("processStatus1", getElctPorcessStatus());
		
		allMap.put("processStatusRevTask", processStatusRevTask());

		//考试形式登记审批状态 0：未提交，1：已提前排考，2：已提交，3：审核通过，4.驳回
		allMap.put("processStatusExam", processStatusExam());
		
		//考试排考登记审批状态 0：未提交，1：已提交，2：审核通过，3：教务审核通过，4.驳回
		allMap.put("processStatusExamSchedule", processStatusExamSchedule());
		
		//考试类型考务用 0：期中，1：期末，2：补考，3：重考
		allMap.put("eduExamExamType", eduExamExamType());
		
		//教材选配提交状态 0：未提交 ，1：已提交未审核， 2：已审核
		allMap.put("processStatusTxbkMatching", processStatusTxbkMatching());
		
		//教材：印刷申请、审核状态，0：未申请 1：已申请未提交 2：已提交未审核 3：已审核
		allMap.put("txbkPrintApplyStatus", txbkPrintApplyStatus());
		//教材：印刷任务状态，3：编辑中 4：添加到印刷单 5：确认印刷
		allMap.put("txbkPrintTaskStatus", txbkPrintTaskStatus());
		//教材：印刷单状态，0：未确认 1：已确认
		allMap.put("txbkPrintOrderStatus", txbkPrintOrderStatus());
		
		//教材选配订购需求  0：学生及教师 1：学生 2：教师  3：无
		allMap.put("txbkMatchingScope", txbkMatchingScope());
		//书目订购任务表  0：编辑中1：添加到订购单 2：确认订购
		allMap.put("txbkTextbookTaskStatus", txbkTextbookTaskStatus());
		
		//教材选配选配类型 0统一选配 1教师选配 2无任务选配 3特殊选配 
		allMap.put("txbkMatchingType", txbkMatchingType());
		
		//工作量审批
		allMap.put("processStatusWkld", processStatusWkld());
		
		//工作量审批
		allMap.put("WkldStatus", getWkldStatus());
		
		allMap.put("isNewORNot", getIsNewORNot());// 0:否，1:是
		
		//毕业资格审核
		allMap.put("gradStatus", getGradStatus());
		
		//毕业证书类型
		allMap.put("gradCertType", getGradCertType());
		
		//证书管理：三证类别
		allMap.put("qualCertType", getQualCertType());
		
		//学生流向
		allMap.put("upgradeMode", getUpgradeMode());
		
		//成绩结果 毕业资格审核用
		allMap.put("scoreResult", getScoreResult());
		
		return allMap;
	}
	
	private void putLabelVo2Map(Map<String, LabelVo> map, String id, String title){
		map.put(id, new LabelVo(id, title));
	}
	
	private Map<String, LabelVo> processStatusRevTask() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "未提交重修任务");
		putLabelVo2Map(tempMap, "1",  "已提交重修任务");
		putLabelVo2Map(tempMap, "3",  "已公布实时课表");
		return tempMap;
	}
	
	/**
	 * 获取所有状态名称
	 */
	private Map<String, LabelVo> getAllStatusName() {
		Map<String, LabelVo> statusNameMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(statusNameMap, Constants.COMMON_NO, LabelUtil.getLabelName(LabelUtil.DICT_STATUS_NAME_HIDE, "不选用"));
		putLabelVo2Map(statusNameMap, Constants.COMMON_YES, LabelUtil.getLabelName(LabelUtil.DICT_STATUS_NAME_PUBLIC, "选用"));
		return statusNameMap;
	}

	private Map<String, LabelVo> getStatus() {
		Map<String, LabelVo> statusNameMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(statusNameMap, Constants.COMMON_NO, "未启动");
		putLabelVo2Map(statusNameMap, Constants.COMMON_YES, "已启动");
		return statusNameMap;
	}

	private Map<String, LabelVo> getOptionalFlag() {
		Map<String, LabelVo> statusNameMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(statusNameMap, Constants.COMMON_NO, "不可选");
		putLabelVo2Map(statusNameMap, Constants.COMMON_YES, "可选");
		return statusNameMap;
	}
	
	/**
	 * 获取 确认状态 
	 * rwz add 
	 */
	private Map<String, LabelVo> getConfirmStatus() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  "未确认");
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  "已确认");
		return tempMap;
	}
	
	/**
	 * 获取 发起状态 
	 * rwz add 
	 */
	private Map<String, LabelVo> getInitiateStatus() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  "未发起");
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  "已发起");
		putLabelVo2Map(tempMap, 3+"",  "已结束");
		return tempMap;
	}
	
	private Map<String, LabelVo> getForceFlag() {
		Map<String, LabelVo> statusNameMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(statusNameMap, Constants.COMMON_NO, "不强制");
		putLabelVo2Map(statusNameMap, Constants.COMMON_YES, "强制");
		return statusNameMap;
	}
	
	/**
	 * 获取所有标准名称
	 */
	private Map<String, LabelVo> getAllStandardName() {
		Map<String, LabelVo> standardNameMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(standardNameMap, "" + Constants.DICT_STANDARD_NATION, LabelUtil.getLabelName(LabelUtil.DICT_STANDARD_NAME_NATION, "国标"));
		putLabelVo2Map(standardNameMap, "" + Constants.DICT_STANDARD_SCHOOL, LabelUtil.getLabelName(LabelUtil.DICT_STANDARD_NAME_SCHOOL, "校标"));
		putLabelVo2Map(standardNameMap, "" + Constants.DICT_STANDARD_UNKNOWN, LabelUtil.getLabelName(LabelUtil.DICT_STANDARD_NAME_UNKNOWN, "未知"));
		putLabelVo2Map(standardNameMap, "" + Constants.DICT_STANDARD_DATAPLATFORM, LabelUtil.getLabelName(LabelUtil.DICT_STANDARD_NAME_DATAPLATFORM, "数据平台"));
		return standardNameMap;
	}
	
	/**
	 * 获取所有专业-类型名称
	 */
	private Map<String, LabelVo> getAllMajorTypeName() {
		Map<String, LabelVo> majorTypeMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(majorTypeMap, Constants.COMMON_NO,  LabelUtil.getLabelName(LabelUtil.DICT_MAJOR_TYPE_HIGH, "高职"));
		putLabelVo2Map(majorTypeMap, Constants.COMMON_YES,  LabelUtil.getLabelName(LabelUtil.DICT_MAJOR_TYPE_MIDDLE, "中职"));
		return majorTypeMap;
	}
	
	/**
	 * 获取所有 院系类型 ： 学院、院系、部门
	 */
	private Map<String, LabelVo> getAllDepartmentTypeName() {
		Map<String, LabelVo> departmentTypeMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(departmentTypeMap, Constants.DEPARTMENT_TYPE_COLLEGE + "",  LabelUtil.getLabelName(LabelUtil.DICT_DEPARTMENT_TYPE_COLLEGE, "学院"));
		putLabelVo2Map(departmentTypeMap, Constants.DEPARTMENT_TYPE_DEPARTMENT + "",  LabelUtil.getLabelName(LabelUtil.DICT_DEPARTMENT_TYPE_DEPARTMENT, "院系"));
		putLabelVo2Map(departmentTypeMap, Constants.DEPARTMENT_TYPE_DIVISION + "",  LabelUtil.getLabelName(LabelUtil.DICT_DEPARTMENT_TYPE_DIVISION, "部门"));
		
		return departmentTypeMap;
	}
	
	/**
	 * 获取所有班级状态的名称
	 */
	private Map<String, LabelVo> getAllClazzStatusName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "1",  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_NATURAL_CLAZZ_STATUS_NORMAL, "正常"));
		putLabelVo2Map(tempMap, "2",  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_NATURAL_CLAZZ_STATUS_EXPECTED, "预计毕业班"));
		putLabelVo2Map(tempMap, "3",  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_NATURAL_CLAZZ_STATUS_GRADUATE, "已毕业"));
		return tempMap;
	}
	
	/**
	 * 获取所有学生信息-是否在校
	 */
	private Map<String, LabelVo> getAllPresenceFlagName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_PRESENCEFLAG_NO, "不在校"));
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_PRESENCEFLAG_YES, "在校"));
		return tempMap;
	}
	
	/**
	 * 获取所有学生信息-是否有教委学籍
	 */
	private Map<String, LabelVo> getAllRollFlagName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_ROLLFLAG_YES, "是"));
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_ROLLFLAG_NO, "否"));
		return tempMap;
	}
	
	/**
	 * 获取所有学生信息-是否缴费
	 */
	private Map<String, LabelVo> getAllPaymentFlagName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_PAYMENTFLAG_YES, "已缴费"));
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_PAYMENTFLAG_NO, "未缴费"));
		return tempMap;
	}
	
	/**
	 * 获取所有 注册状态
	 */
	private Map<String, LabelVo> getAllRegisterStatusName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_REGISTERSTATUS_WAIT, "待注册"));
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_REGISTERSTATUS_REGISTERED, "注册学籍"));
		putLabelVo2Map(tempMap, "2",  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_REGISTERSTATUS_SUSPENDED, "暂缓注册"));
		putLabelVo2Map(tempMap, "3",  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_REGISTERSTATUS_LEAVE, "休学"));
		putLabelVo2Map(tempMap, "4",  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_REGISTERSTATUS_WRITEOFF, "已注销"));
		return tempMap;
	}
	
	/**
	 * 获取所有 学籍确认状态
	 */
	private Map<String, LabelVo> getAllConfirmFlagName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_CONFIRMFLAG_UNCONFIRMED, "未确认"));
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_CONFIRMFLAG_CONFIRMED_RIGHT, "确认信息正确"));
		putLabelVo2Map(tempMap, "2",  LabelUtil.getLabelName(LabelUtil.EDU_ROLL_CONFIRMFLAG_CONFIRMED_ERROR, "确认信息错误"));
		return tempMap;
	}
	
	/**
	 * 获取所有 雇员类型 班主任/教学
	 */
	private Map<String, LabelVo> getAllEmployeeTypeName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  LabelUtil.getLabelName(LabelUtil.EMPLOYEE_TYPE_CLASS, "班主任"));
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  LabelUtil.getLabelName(LabelUtil.EMPLOYEE_TYPE_TEACH, "教学"));
		return tempMap;
	}
	
	/**
	 * 获取所有 任务完成状态
	 */
	private Map<String, LabelVo> getAllScheduleStatusName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  LabelUtil.getLabelName(LabelUtil.SYS_SCHEDULE_STATUS_UNFINISHED, "未完成"));
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  LabelUtil.getLabelName(LabelUtil.SYS_SCHEDULE_STATUS_FINISHED, "已完成"));
		putLabelVo2Map(tempMap, "2",  LabelUtil.getLabelName(LabelUtil.SYS_SCHEDULE_STATUS_ABANDON, "放弃完成"));
		putLabelVo2Map(tempMap, "3",  LabelUtil.getLabelName(LabelUtil.SYS_SCHEDULE_STATUS_NEEDNOT, "无需完成"));
		return tempMap;
	}
	
	/**
	 * 获取所有 职务类别
	 */
	private Map<String, LabelVo> getAllJobTypeName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  LabelUtil.getLabelName(LabelUtil.HEAD_JOB_TYPE_ADMINISTRATIVE, "行政职务") );
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  LabelUtil.getLabelName(LabelUtil.HEAD_JOB_TYPE_TECH, "技术职务"));
		return tempMap;
	}
	
	/**
	 * 获取所有 性别
	 */
	private Map<String, LabelVo> getAllGenderName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  LabelUtil.getLabelName(LabelUtil.GENDER_NAME_MALE, "男"));
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  LabelUtil.getLabelName(LabelUtil.GENDER_NAME_FEMALE, "女") );
		return tempMap;
	}
	
	/**
	 * 使用状态
	 */
	private Map<String, LabelVo> getCommonlStatusName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  "不可用");
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  "可用");
		return tempMap;
	}
	
	/**
	 * 锁定状态
	 */
	private Map<String, LabelVo> getLockedName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  "未锁定");
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  "锁定");
		return tempMap;
	}
	
	/**
	 * 是否删除
	 */
	private Map<String, LabelVo> getLogicDeleteName() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.COMMON_NO,  "正常");
		putLabelVo2Map(tempMap, Constants.COMMON_YES,  "逻辑删除");
		return tempMap;
	}
	
	/**
	 * 教学计划-课程  是否校企合作课程  0,1
	 */
	private Map<String, LabelVo> getCoopflag() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.EDU_SKD_COURSE_COOPFLAG_NO,  LabelUtil.getLabelName(LabelUtil.EDU_SKD_COURSE_COOPFLAG_NO, "否") );
		putLabelVo2Map(tempMap, Constants.EDU_SKD_COURSE_COOPFLAG_YES,  LabelUtil.getLabelName(LabelUtil.EDU_SKD_COURSE_COOPFLAG_YES, "是") );
		return tempMap;
	}
	
	
	/**
	 * 教学计划-课程  考试考查  0,1
	 */
	private Map<String, LabelVo> getExamflag() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.EDU_SKD_COURSE_EXAMFLAG_NO,  LabelUtil.getLabelName(LabelUtil.EDU_SKD_COURSE_EXAMFLAG_NO, "考查") );
		putLabelVo2Map(tempMap, Constants.EDU_SKD_COURSE_EXAMFLAG_YES,  LabelUtil.getLabelName(LabelUtil.EDU_SKD_COURSE_EXAMFLAG_YES, "考试") );
		return tempMap;
	}
	
	/**
	 * 教学计划-课程  是否使用参考网:不使用,使用 0,1
	 */
	private Map<String, LabelVo> getShowUrl() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.EDU_SKD_COURSE_SHOWURL_NO,  LabelUtil.getLabelName(LabelUtil.EDU_SKD_COURSE_SHOWURL_NO, "不使用") );
		putLabelVo2Map(tempMap, Constants.EDU_SKD_COURSE_SHOWURL_YES,  LabelUtil.getLabelName(LabelUtil.EDU_SKD_COURSE_SHOWURL_YES, "使用") );
		return tempMap;
	}
	
	/**
	 * 0:否，1:是
	 */
	private Map<String, LabelVo> getIsZeroORNot() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.NOTZERO, "是");
		putLabelVo2Map(tempMap, Constants.ISZERO,  "否");
		return tempMap;
	}
	
	/**
	 * 0:否，1:是
	 */
	private Map<String, LabelVo> getIsYesORNo() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.NOTZERO, "可以");
		putLabelVo2Map(tempMap, Constants.ISZERO,  "不可以");
		return tempMap;
	}
	
	private Map<String, LabelVo> getTemplateFormat() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  LabelUtil.getLabelName(LabelUtil.COMN_TEMPLATE_FILEFORMAT_EXCEL, "excel"));
		putLabelVo2Map(tempMap, "1",  LabelUtil.getLabelName(LabelUtil.COMN_TEMPLATE_FILEFORMAT_DBF, "dbf"));
		return tempMap;
	}
	
	private Map<String, LabelVo> getTemplateType() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  LabelUtil.getLabelName(LabelUtil.COMN_TEMPLATE_TYPE_IMPORT, "导入"));
		putLabelVo2Map(tempMap, "1",  LabelUtil.getLabelName(LabelUtil.COMN_TEMPLATE_TYPE_EXPORT, "导出"));
		return tempMap;
	}
	
	/**
	 * 教学计划 进行状态 : 总校开始，pending到院系，pending到专业负责人，pending到教务处长，pending到总校，审批结束，不调整结束 （复合查询条件）
	 */
	private Map<String, LabelVo> getPendingRoleProcessStatus() {
		
		Integer head_sch_id = dictHelpService.getIdbyCode("skdsysrole", Constants.HEAD_SCH);
		Integer head_dept_id = dictHelpService.getIdbyCode("skdsysrole", Constants.HEAD_DEPT);
		Integer head_major_id = dictHelpService.getIdbyCode("skdsysrole", Constants.HEAD_MAJOR);
		Integer head_tech_id = dictHelpService.getIdbyCode("skdsysrole", Constants.HEAD_TECH);
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		
		// key 值  head_skd_id+"_"+Constants.START =  EduSkdSchedule.pendingRoleId + "_" + EduSkdSchedule.processStatus 的形式，方便页面取值。（复合查询条件）
		putLabelVo2Map(tempMap, head_sch_id+"_"+Constants.START,  "总校开始");				// 新生成计划，总校开始
		putLabelVo2Map(tempMap, head_dept_id+"_"+Constants.PENDING, "pending到院系");			// 院系待处理
		putLabelVo2Map(tempMap, head_major_id+"_"+Constants.PENDING, "pending到专业负责人");	// 专业负责人待处理
		putLabelVo2Map(tempMap, head_sch_id+"_"+Constants.PENDING, "pending到总校");			// 总校待处理
		putLabelVo2Map(tempMap, head_tech_id+"_"+Constants.PENDING, "pending到教务处长");		// 教务处长待处理
		
		putLabelVo2Map(tempMap, head_tech_id+"_"+Constants.PASS, "审批结束");					// 教务处长通过
		putLabelVo2Map(tempMap, head_dept_id+"_"+Constants.END, "不调整结束");					// 院系不调整
		return tempMap;
	}
		
	/**
	 * 教学计划 是否通过 0:不通过，1:通过
	 */
	private Map<String, LabelVo> getPassStatus() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.PASS_NO,  "不通过");
		putLabelVo2Map(tempMap, Constants.PASS_YES, "通过");
		return tempMap;
	}
	
	
	/**
	 * 教学计划课程计算方法名称集合
	 */
	public Map<String, LabelVo> getCourseTypeMethodNameList(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "1",  "方法一");
		putLabelVo2Map(tempMap, "2",  "方法二");
		putLabelVo2Map(tempMap, "3",  "方法三");
		putLabelVo2Map(tempMap, "4",  "方法四");
		putLabelVo2Map(tempMap, "5",  "方法五");
		putLabelVo2Map(tempMap, "6",  "方法六");
		return tempMap;
	}
	
	/**
	 * 教学任务   教学任务管理   不可选课的教学任务   不可选课组织教学班
	 * 组班模式 0：单一行政班  1：拆班   2：合班   3：拆合班   4：分层教学 5：可选课
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年7月30日 下午3:43:29
	 */
	public Map<String, LabelVo> allmode(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "单班");
		putLabelVo2Map(tempMap, "1",  "拆班");
		putLabelVo2Map(tempMap, "2",  "合班");
		putLabelVo2Map(tempMap, "3",  "拆合班");
		putLabelVo2Map(tempMap, "4",  "分层");
		putLabelVo2Map(tempMap, "5",  "可选课");
		putLabelVo2Map(tempMap, "6",  "重修班");
		return tempMap;
	}
	public Map<String, LabelVo> mode(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "单一行政班教学");
		putLabelVo2Map(tempMap, "1",  "拆班教学");
		putLabelVo2Map(tempMap, "2",  "合班教学");
		putLabelVo2Map(tempMap, "3",  "拆合班教学");
		putLabelVo2Map(tempMap, "4",  "分层教学");
//		putLabelVo2Map(tempMap, "5",  "可选课");
		return tempMap;
	}
	
	/**
	 * 教学任务   教学任务管理   不可选课的教学任务   不可选课组织教学班
	 * 组班模式简称 0：单班  1：拆班   2：合班   3：拆合班   4：分层 5：可选课
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年7月30日 下午3:43:29
	 */
	public Map<String, LabelVo> optshortmode(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "单班");
		putLabelVo2Map(tempMap, "1",  "拆班");
		putLabelVo2Map(tempMap, "2",  "合班");
		putLabelVo2Map(tempMap, "3",  "拆合班");
		putLabelVo2Map(tempMap, "4",  "分层");
		return tempMap;
	}

	public Map<String, LabelVo> shortmode(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "单班");
		putLabelVo2Map(tempMap, "1",  "拆班");
		putLabelVo2Map(tempMap, "2",  "合班");
		putLabelVo2Map(tempMap, "3",  "拆合班");
		putLabelVo2Map(tempMap, "4",  "分层");
		putLabelVo2Map(tempMap, "5",  "可选课");
		putLabelVo2Map(tempMap, "6",  "重修班");
		return tempMap;
	}
	
	/**
	 * 教学任务   教学任务管理   不可选课的教学任务   不可选课组织教学班
	 * 选课方式 0：不可选课  1：竞争式 2：选拔式  3：抽签式
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年7月30日 下午3:43:29
	 */
	public Map<String, LabelVo> signUpType(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "不可选课");
		putLabelVo2Map(tempMap, "1",  "竞争式");
		putLabelVo2Map(tempMap, "2",  "选拔式");
		putLabelVo2Map(tempMap, "3",  "抽签式");
		return tempMap;
	}	
	
	public Map<String, LabelVo> optionalSignUpType(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "1",  "竞争式");
		putLabelVo2Map(tempMap, "2",  "选拔式");
		putLabelVo2Map(tempMap, "3",  "抽签式");
		return tempMap;
	}	
	
	/**
	 * 教学任务   教学任务管理   不可选课的教学任务   不可选课组织教学班
	 * 连排节数	1：单节上课  2：两节连排 3：三节连排  4：四节连排
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年7月30日 下午3:43:29
	 */
	public Map<String, LabelVo> continuousPeriod(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "1",  "单节上课");
		putLabelVo2Map(tempMap, "2",  "两节连排");
		putLabelVo2Map(tempMap, "3",  "三节连排");
		putLabelVo2Map(tempMap, "4",  "四节连排");
		return tempMap;
	}	
	
	public Map<String, LabelVo> continuousPeriod2(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "2",  "2");
		putLabelVo2Map(tempMap, "3",  "3");
		putLabelVo2Map(tempMap, "4",  "4");
		putLabelVo2Map(tempMap, "5",  "5");
		putLabelVo2Map(tempMap, "6",  "6");
		return tempMap;
	}	
	
	public Map<String, LabelVo> oddDualWeek(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "每周上");
		putLabelVo2Map(tempMap, "1",  "单周上");
		putLabelVo2Map(tempMap, "2",  "双周上");
		return tempMap;
	}
	
	public Map<String, LabelVo> oddDualWeekShow(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "周");
		putLabelVo2Map(tempMap, "1",  "单周");
		putLabelVo2Map(tempMap, "2",  "双周");
		return tempMap;
	}
	
	public Map<String, LabelVo> weekDay(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "1",  "周一");
		putLabelVo2Map(tempMap, "2",  "周二");
		putLabelVo2Map(tempMap, "3",  "周三");
		putLabelVo2Map(tempMap, "4",  "周四");
		putLabelVo2Map(tempMap, "5",  "周五");
		putLabelVo2Map(tempMap, "6",  "周六");
		putLabelVo2Map(tempMap, "0",  "周日");
		return tempMap;
	}
	
	// 节次
	public Map<String, LabelVo> taskPeriod(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "1",  "1");
		putLabelVo2Map(tempMap, "2",  "2");
		putLabelVo2Map(tempMap, "3",  "3");
		putLabelVo2Map(tempMap, "4",  "4");
		putLabelVo2Map(tempMap, "5",  "5");
		putLabelVo2Map(tempMap, "6",  "6");
		putLabelVo2Map(tempMap, "7",  "7");
		putLabelVo2Map(tempMap, "8",  "8");
		putLabelVo2Map(tempMap, "9",  "9");
		putLabelVo2Map(tempMap, "10",  "10");
		putLabelVo2Map(tempMap, "11",  "11");
		putLabelVo2Map(tempMap, "12",  "12");
		putLabelVo2Map(tempMap, "13",  "13");
		putLabelVo2Map(tempMap, "14",  "14");
		putLabelVo2Map(tempMap, "15",  "15");
		putLabelVo2Map(tempMap, "16",  "16");
		return tempMap;
	}
	
	// 教学场地录入类型
	public Map<String, LabelVo> entryType(){
		
		int head_cskd_id = dictHelpService.getRoleIdByRoleCode(Constants.HEAD_CSKD);
		int dept_cskd_id = dictHelpService.getRoleIdByRoleCode(Constants.DEPT_CSKD);
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, String.valueOf(head_cskd_id),  "教务录入");		
		putLabelVo2Map(tempMap, String.valueOf(dept_cskd_id), "授课院系录入");
		return tempMap;
	}
	
	// edu_cskd_tch_detail  场地审批状态、院系审批状态、教务审批状态  (-1:初始状态  0:未提交，1:已提交，2:审批通过，3:驳回)
	public Map<String, LabelVo> timetableApplyShow(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "-1", "——");	
		putLabelVo2Map(tempMap, "0",  "未提交");		
		putLabelVo2Map(tempMap, "1",  "已提交");		
		putLabelVo2Map(tempMap, "2",  "审批通过");		
		putLabelVo2Map(tempMap, "3",  "驳回");		
		return tempMap;
	}
	public Map<String, LabelVo> timetableApplySearch(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "未申请");		
		putLabelVo2Map(tempMap, "1",  "已提交");		
		putLabelVo2Map(tempMap, "2",  "审批通过");		
		putLabelVo2Map(tempMap, "3",  "驳回");		
		return tempMap;
	}
	public Map<String, LabelVo> empApproveShow(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "-1", "——");	
		putLabelVo2Map(tempMap, "0",  "未提交");		
		putLabelVo2Map(tempMap, "1",  "已提交");		
		putLabelVo2Map(tempMap, "2",  "已确认");		
		putLabelVo2Map(tempMap, "3",  "驳回");		
		return tempMap;
	}
	public Map<String, LabelVo> empApproveSearch(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "未提交");		
		putLabelVo2Map(tempMap, "1",  "已提交");		
		putLabelVo2Map(tempMap, "2",  "已确认");		
		putLabelVo2Map(tempMap, "3",  "驳回");		
		return tempMap;
	}
	public Map<String, LabelVo> applyReasonType(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "因公");		
		putLabelVo2Map(tempMap, "1",  "因私");		
		putLabelVo2Map(tempMap, "2",  "其他");		
		return tempMap;
	}
	
	//学生获得证书提交 0:未提交，1:已提交
	public Map<String, LabelVo> certProcessStatus(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.CERT_CERTSTUDENT_PROCESS_STATUS_ZERO+"",  "未提交");		
		putLabelVo2Map(tempMap, Constants.CERT_CERTSTUDENT_PROCESS_STATUS_ONE+"",  "已提交");		
		return tempMap;
	}
	//学生获得证书审核 0:未审核，1:审核通过，2：审核不通过
	public Map<String, LabelVo> certVerifyStatus(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.CERT_CERTSTUDENT_VERIFY_STATUS_INIT+"",  "未审核");		
		putLabelVo2Map(tempMap, Constants.CERT_CERTSTUDENT_VERIFY_STATUS_YES+"",  "审核通过");		
		putLabelVo2Map(tempMap, Constants.CERT_CERTSTUDENT_VERIFY_STATUS_NO+"",  "审核不通过");		
		return tempMap;
	}
	
	/**
	 * 教学任务   教学任务管理   不可选课的教学任务   拆班-->拆成几个教学班  2-8
	 * @param
	 * @return		
	 * @memo			 					
	 * @author 	wangkang
	 * @date    2015年7月30日 下午3:43:29
	 */
	public Map<String, LabelVo> clazzCount(){
		
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "2",  "2");
		putLabelVo2Map(tempMap, "3",  "3");
		putLabelVo2Map(tempMap, "4",  "4");
		putLabelVo2Map(tempMap, "5",  "5");
		putLabelVo2Map(tempMap, "6",  "6");
		putLabelVo2Map(tempMap, "7",  "7");
		putLabelVo2Map(tempMap, "8",  "8");
		return tempMap;
	}	
	
	/**
	 * 进程自定义符号
	 * edu.prog.symbol.theory=理论 
	 *	edu.prog.symbol.training=军训 J
	 *	edu.prog.symbol.examination=考试 :
	 *	edu.prog.symbol.flexible=机动 #
	 *	edu.prog.symbol.alongschoolcalendar=随校历 X
	 * @return
	 * 	public static final String EDU_PROG_SYMBOL_THEORY_SHOW = "edu.prog.symbol.theory.show";
		public static final String EDU_PROG_SYMBOL_TRAINING_SHOW  = "edu.prog.symbol.training.show";
		public static final String EDU_PROG_SYMBOL_EXAMINATION_SHOW  = "edu.prog.symbol.examination.show";
		public static final String EDU_PROG_SYMBOL_FLEXIBLE_SHOW  = "edu.prog.symbol.flexible.show";
		public static final String EDU_PROG_SYMBOL_ALONGSCHOOLCALENDAR_SHOW  = "edu.prog.symbol.alongschoolcalendar.show";
	 */
	public Map<String, LabelVo> progCustomSymbol(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, LabelUtil.getLabelName(LabelUtil.EDU_PROG_SYMBOL_THEORY_SHOW, "L"),  LabelUtil.getLabelName(LabelUtil.EDU_PROG_SYMBOL_THEORY, "理论 L"));
		putLabelVo2Map(tempMap, LabelUtil.getLabelName(LabelUtil.EDU_PROG_SYMBOL_TRAINING_SHOW, "J"),  LabelUtil.getLabelName(LabelUtil.EDU_PROG_SYMBOL_TRAINING, "军训 J") );
		putLabelVo2Map(tempMap, LabelUtil.getLabelName(LabelUtil.EDU_PROG_SYMBOL_EXAMINATION_SHOW, ":"),  LabelUtil.getLabelName(LabelUtil.EDU_PROG_SYMBOL_EXAMINATION, "考试 :") );
		putLabelVo2Map(tempMap, LabelUtil.getLabelName(LabelUtil.EDU_PROG_SYMBOL_FLEXIBLE_SHOW, "#"),  LabelUtil.getLabelName(LabelUtil.EDU_PROG_SYMBOL_FLEXIBLE, "机动 #") );
		putLabelVo2Map(tempMap, LabelUtil.getLabelName(LabelUtil.EDU_PROG_SYMBOL_ALONGSCHOOLCALENDAR_SHOW, "X"),  LabelUtil.getLabelName(LabelUtil.EDU_PROG_SYMBOL_ALONGSCHOOLCALENDAR, "随校历 X") );
		return tempMap;
	}
	
	/**
	 * 教学进程：进程制定状态
	 * @return
	 */
	public Map<String, LabelVo> processStatus(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
//		putLabelVo2Map(tempMap, Constants.PROG_NOT_CREATED+"",  "未制定");
		putLabelVo2Map(tempMap, Constants.PROG_CREATED+"",  "已创建教务编辑中");
		putLabelVo2Map(tempMap, Constants.PROG_SEND+"",  "已下发到系里");
		putLabelVo2Map(tempMap, Constants.PROG_REPORT+"",  "系里已提交");
		putLabelVo2Map(tempMap, Constants.PROG_END+"",  "制定完成");
		return tempMap;
	}
	
	/**
	 * 系统， 角色-资源-权限关联关系：资源可见范围
	 * @author RanWeizheng
	 * @return
	 */
	public Map<String, LabelVo> getScopeType(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "无数据权限");
		putLabelVo2Map(tempMap, "1",  "校区级别");
		putLabelVo2Map(tempMap, "2",  "校区总校级别");
		putLabelVo2Map(tempMap, "3",  "院系级别");
		putLabelVo2Map(tempMap, "4",  "院系总校级别");
		putLabelVo2Map(tempMap, "5",  "个人级别");
		return tempMap;
	}
	
	/**学分 启用状态  0:未启用，1:启用中， 2:已作废 
		@author RanWeizheng
	 * @return
	 */
	public Map<String, LabelVo> getCreditStatus(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_STATUS_DISABLE + "",  "未启用");
		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_STATUS_ENABLE + "",  "启用中");
		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_STATUS_OBSOLETE + "",  "已作废");
		return tempMap;
	}	
	
	/**
	 * 总评成绩构成 类型 （不包括补考缓考）
	 * @return
	 */
	public Map<String, LabelVo> getCreditDetailType(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_DETAIL_TYPE_CUSTOM + "",  "自定义");
		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_DETAIL_TYPE_MIDDLE + "",  "期中成绩");
		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_DETAIL_TYPE_FINAL + "",  "期末成绩");
		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_DETAIL_TYPE_REVAMP + "",  "重修考成绩");
		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_DETAIL_TYPE_OVERALL + "",  "总评成绩");
//		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_DETAIL_TYPE_REMEDY + "",  "补考成绩");
//		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_DETAIL_TYPE_REHABILITATION + "",  "重修成绩");
		return tempMap;
	}	
	
	/**
	 * 教学班成绩构成 提交状态
	 */
	public Map<String, LabelVo> getTchCreditDetailStatus(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.SCR_TCH_CLAZZ_CREDIT_DETAIL_STATUS_NO + "",  "未提交");
		putLabelVo2Map(tempMap, Constants.SCR_TCH_CLAZZ_CREDIT_DETAIL_STATUS_YES + "",  "已提交");
		return tempMap;
	}
	
	
	/**
	 *  成绩管理 总评成绩表 修习类型
	 */
	public Map<String, LabelVo> getCGeneralType(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_TYPE_NEW + "",  "新修");
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_TYPE_REVAMP + "",  "重修");
		return tempMap;
	}
	/**
	 *  成绩管理 修习类型
	 *  免修模块中，重修、自主重修，都显示“重修”。针对成绩“只有重修概念”，“自主重修概念来源于教学班”。
	 *  此处合并显示了。
	 *  modify by zhangjk 
	 */
	public Map<String, LabelVo> getStudyType(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_TYPE_NEW + "",  "新修");
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_TYPE_REVAMP + "",  "重修");
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_TYPE_OWN_REVAMP + "",  "重修");
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_TYPE_REMEDY + "",  "补修");
		return tempMap;
	}
	/**
	 * 成绩管理  考试类型
	 */
	public Map<String, LabelVo> getExamType(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_TYPE_MIDDLE + "",  "期中");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_TYPE_FINAL + "",  "期末");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_TYPE_OVERALL + "",  "总评");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_TYPE_MAKEUP + "",  "补考");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_TYPE_DELAYED + "",  "缓考");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_TYPE_REVAMP + "",  "重修考");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_TYPE_OWN_REVAMP + "",  "自主重修考");
		return tempMap;
	}
	/**
	 * 成绩管理  考试状态
	 */
	public Map<String, LabelVo> getExamStatus(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
//		putLabelVo2Map(tempMap, Constants.SCR_EXAM_STUTUS_INIT + "",  "初始化");
//		putLabelVo2Map(tempMap, Constants.SCR_EXAM_STUTUS_NOENTRY + "",  "未录入");
//		putLabelVo2Map(tempMap, Constants.SCR_EXAM_STUTUS_NORMAL + "",  "正常");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_STUTUS_DEFER + "",  "缓考");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_STUTUS_BAN + "",  "禁考");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_STUTUS_ABSENCE + "",  "缺考");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_STUTUS_CHEAT + "",  "作弊");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_STUTUS_BREAKRULE + "",  "违纪");
		return tempMap;
	} 
	
	/**
	 * 成绩管理  考试状态  （待缓考、补考查询页中使用）
	 */
	public Map<String, LabelVo> getExamStatus1(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_STUTUS_NOENTRY + "",  "无异常");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_STUTUS_NORMAL + "",  "无异常");
		putLabelVo2Map(tempMap, Constants.SCR_EXAM_STUTUS_DEFER + "",  "申请缓考");
		return tempMap;
	} 
	
	/**
	 * 成绩管理  成绩结果
	 * // 成绩结果：0：未录入、1：通过、2：需补考、3：需重修、4 补考通过、 5 重修通过
	 * //6：待缓考 7：需补修 8：补修通过 9：不通过（可选课使用）10:缓考通过
	 */
	public Map<String, LabelVo> getStudyResult(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
//		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_NOTHING + "",  "待录入");
//		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_PASS + "",  "通过");
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_MAKEUP + "",  "需补考");
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_REVAMP + "",  "需重修");
//		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_MAKEUP_PASS + "",  "补考通过");
//		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_REVAMP_PASS + "",  "重修通过");
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_DELAYED + "",  "待缓考");
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_REMEDY + "",  "需补修");
//		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_REMEDY_PASS + "",  "补修通过");
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_NOT_PASS + "",  "不通过");
//		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_DELAYED_PASS + "",  "缓考通过");
//		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_EXMP_PASS + "",  "免修通过");
//		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_REPLACE_PASS + "",  "互认通过");
//		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_REWD_PASS + "",  "奖励通过");
		
		return tempMap;
	}
	
	/**
	 * 成绩管理  成绩单标识
	 * <#if statusOne.result=4>补及<#elseif statusOne.result=5>重及</#if>
	 */
	public Map<String, LabelVo> getTranscriptSign(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_MAKEUP_PASS + "",  "补及");
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_REVAMP_PASS + "",  "重及");
		
		return tempMap;
	}
	
	/**
	 * 重修方式  0:新修 1:非工程实践类组班重修 2:工程实践类组班重修 3:自主重修
	 * 
	 * EduTaskTchClazzVo
	 * private Integer type;					// 修习类型 0:新修 1:重修 2:自主重修 
       private Integer courseTypeGeneral;		// 通用课程类型 0:非工程实践类 1:工程实践类 
              综上2个字段得出结论：  非工程实践类组班重修："1_0"		工程实践类组班重修："1_1"		自主重修："2_0"
	 */
	public Map<String, LabelVo> getAllRevMode(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap,  "1_0",  "非工程实践类组班重修");
		putLabelVo2Map(tempMap,  "1_1",  "工程实践类组班重修");
		putLabelVo2Map(tempMap,  "2_0",  "自主重修");
		
		return tempMap;
	}
	/**
	 * 重修方式  0:新修 1:组班重修 2:自主重修
	 */
	public Map<String, LabelVo> getRevMode(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
//		putLabelVo2Map(tempMap, 0 + "",  "新修");
		putLabelVo2Map(tempMap, 1 + "",  "组班重修");
		putLabelVo2Map(tempMap, 2 + "",  "自主重修");
		
		return tempMap;
	}
	
	/**
	 * 重修类型  0:需补修 1:需重修
	 * @author RanWeizheng
	 */
	public Map<String, LabelVo> getRevType(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, 0 + "",  "需补修");
		putLabelVo2Map(tempMap, 1 + "",  "需重修");
		return tempMap;
	}
	
	/**
	 * 是否组织过补修		
	 * @author 
	 */
	public Map<String, LabelVo> isRepair(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, 0 + "",  "未组织");
		putLabelVo2Map(tempMap, 1 + "",  "已组织");
		return tempMap;
	}
	
	/**
	 * 排课状态 0:未排课 1:已排课
	 * @author wangkang
	 */
	public Map<String, LabelVo> isAlloc(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, 0 + "",  "未排课");
		putLabelVo2Map(tempMap, 1 + "",  "已排课");
		return tempMap;
	}
	
	/**
	 * 分配状态 0:未分配 1:已分配
	 */
	private Map<String, LabelVo> isNullAlloc() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.NOTZERO, "已分配");
		putLabelVo2Map(tempMap, Constants.ISZERO,  "未分配");
		return tempMap;
	}
	
	//课类  0：非工程实践  1：工程实践
	public Map<String, LabelVo> courseTypeGeneral(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, 0 + "",  "非工程实践类");
		putLabelVo2Map(tempMap, 1 + "",  "工程实践类");
		return tempMap;
	}
	
	/**
	 * 选课方式 0:竞争式，1:选拔式，2:抽签式，3:指定式
	 */
	public Map<String, LabelVo> elecmode(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, 0 + "",  "竞争式");
		putLabelVo2Map(tempMap, 1 + "",  "选拔式");
		putLabelVo2Map(tempMap, 2 + "",  "抽签式");
		putLabelVo2Map(tempMap, 3 + "",  "指定式");
		return tempMap;
	} 
	
	/**
	 * yesorno 0:否，1:是 
	 */
	private Map<String, LabelVo> yesorno() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, 1+"", "是");
		putLabelVo2Map(tempMap, 0+"",  "否");
		return tempMap;
	}
	
	/**
	 * 是否允许  0：不允许 1：允许
	 */
	private Map<String, LabelVo> isAllow() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, 1+"", "允许");
		putLabelVo2Map(tempMap, 0+"",  "不允许");
		return tempMap;
	}
	
	/**
	 * 开放选课状态  0：关闭 1：开放
	 */
	private Map<String, LabelVo> isOpen() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, 0+"",  "关闭");
		putLabelVo2Map(tempMap, 1+"", "开放");
		return tempMap;
	}
	

	/**
	 * 选课批次表进行状态  0:未确认抽签结果，1:已确认抽签结果
	 */
	private Map<String, LabelVo> batchstatus() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, 0+"", "未确认抽签结果");
		putLabelVo2Map(tempMap, 1+"", "已确认抽签结果");
		return tempMap;
	}
	
	//选课 进行状态 0:已报名 ；1：报名成功； 2： 报名失败； 3：处理中； 4：取消  5:未报名
	private Map<String, LabelVo> getElctPorcessStatus() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, 0+"", "已报名");
		putLabelVo2Map(tempMap, 1+"", "报名成功");
		putLabelVo2Map(tempMap, 2+"", "报名失败");
		putLabelVo2Map(tempMap, 3+"", "处理中");
		putLabelVo2Map(tempMap, 4+"", "取消");
		putLabelVo2Map(tempMap, 5+"", "未报名");
		return tempMap;
	}
	
	//考试形式审批
	private Map<String, LabelVo> processStatusExam() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "未提交");
		putLabelVo2Map(tempMap, "1",  "已提交");
		putLabelVo2Map(tempMap, "2",  "院系审核通过");
		putLabelVo2Map(tempMap, "3",  "教务审核通过");
		putLabelVo2Map(tempMap, "4",  "驳回");
		return tempMap;
	}
	
	//考试排考审批
	private Map<String, LabelVo> processStatusExamSchedule() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "未提交");
		putLabelVo2Map(tempMap, "1",  "已提交");
		putLabelVo2Map(tempMap, "2",  "审核通过");
		putLabelVo2Map(tempMap, "3",  "已提前排考");
		putLabelVo2Map(tempMap, "4",  "驳回");
		return tempMap;
	}

	//考试类型
	private Map<String, LabelVo> eduExamExamType() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "期中");
		putLabelVo2Map(tempMap, "1",  "期末");
		putLabelVo2Map(tempMap, "2",  "补考");
		putLabelVo2Map(tempMap, "3",  "重修");
		return tempMap;
	}

	//教材选配提交状态
	private Map<String, LabelVo> processStatusTxbkMatching() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0", "未提交");
		putLabelVo2Map(tempMap, "1", "已提交未审核");
		putLabelVo2Map(tempMap, "2", "已审核");
		return tempMap;
	}
	//教材：印刷申请、审核状态，0：未申请 1：已申请未提交 2：已提交未审核 3：已审核
	private Map<String, LabelVo> txbkPrintApplyStatus() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0", "未申请");
		putLabelVo2Map(tempMap, "1", "已申请未提交");
		putLabelVo2Map(tempMap, "2", "已提交未审核");
		putLabelVo2Map(tempMap, "3", "已审核");
		return tempMap;
	}
	//教材：印刷任务状态，3：编辑中 4：添加到印刷单 5：确认印刷
	private Map<String, LabelVo> txbkPrintTaskStatus() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "3", "编辑中");
		putLabelVo2Map(tempMap, "4", "添加到印刷单");
		putLabelVo2Map(tempMap, "5", "确认印刷");
		return tempMap;
	}
	//教材：印刷单状态，0：未确认 1：已确认
	private Map<String, LabelVo> txbkPrintOrderStatus() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0", "未确认");
		putLabelVo2Map(tempMap, "1", "已确认");
		return tempMap;
	}
	
	//教材选配订购需求
	private Map<String, LabelVo> txbkMatchingScope(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0", "学生及教师都需要");
		putLabelVo2Map(tempMap, "1", "仅学生需要");
		putLabelVo2Map(tempMap, "2", "仅教师需要");		
		putLabelVo2Map(tempMap, "3", "学生及教师都不需要");
		return tempMap;
	}
	
	//教材选配  选配类型
	private Map<String, LabelVo> txbkMatchingType(){
		Map<String, LabelVo> tempMap = new HashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0", "统一选配");
		putLabelVo2Map(tempMap, "1", "教师选配");
		putLabelVo2Map(tempMap, "2", "无任务选配");
//		putLabelVo2Map(tempMap, "3", "特殊选配");
		
		return tempMap;
	}
	//书目订购任务表  0：编辑中1：添加到订购单 2：确认订购
	private Map<String, LabelVo> txbkTextbookTaskStatus() {
		Map<String, LabelVo> tempMap = new HashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0", "编辑中");
		putLabelVo2Map(tempMap, "1", "添加到订购单");
		putLabelVo2Map(tempMap, "2", "确认订购");
		return tempMap;
	}
	
	//工作量审批
	private Map<String, LabelVo> processStatusWkld() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "未提交");
		putLabelVo2Map(tempMap, "1",  "已提交");
		putLabelVo2Map(tempMap, "2",  "审核通过");
		putLabelVo2Map(tempMap, "3",  "驳回");
		putLabelVo2Map(tempMap, "4",  "已生成");
		return tempMap;
	}
	
	/**工作量 启用状态  0:未启用，1:启用中， 2:已作废 
		@author RanWeizheng
	 * @return
	 */
	public Map<String, LabelVo> getWkldStatus(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_STATUS_ENABLE + "",  "启用中");		
		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_STATUS_DISABLE + "",  "未启用");
		putLabelVo2Map(tempMap, Constants.SCR_CREDIT_STATUS_OBSOLETE + "",  "已作废");
		return tempMap;
	}
	
	/**
	 * 0:否，1:是
	 */
	private Map<String, LabelVo> getIsNewORNot() {
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.ISZERO,  "不是新课");
		putLabelVo2Map(tempMap, Constants.NOTZERO, "是新课");
		return tempMap;
	}
	
	/**
	 * 毕业资格审核 审核状态
	 * @return
	 */
	public Map<String, LabelVo> getGradStatus(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "未审核");		
		putLabelVo2Map(tempMap, "1",  "审核通过");
		putLabelVo2Map(tempMap, "2",  "审核不通过");
		return tempMap;
	}
	
	/**
	 * 0：未设置
	 * 1：毕业证
	 * 2：结业证
	 * @return
	 */
	public Map<String, LabelVo> getGradCertType(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, "0",  "未设置");		
		putLabelVo2Map(tempMap, "1",  "毕业证");
		putLabelVo2Map(tempMap, "2",  "结业证");
		putLabelVo2Map(tempMap, "3",  "结业转毕业");
		return tempMap;
	}	
	
	/**
	 * 1：计算机水平证书
	 * 2：英语水平证书
	 * 3：职业资格证书
	 * @return
	 */
	public Map<String, LabelVo> getQualCertType(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		putLabelVo2Map(tempMap, Constants.CERT_TYPE_IT+"",  "计算机水平证书");
		putLabelVo2Map(tempMap, Constants.CERT_TYPE_ENGLISH+"",  "英语水平证书");
		putLabelVo2Map(tempMap, Constants.CERT_TYPE_VOCA+"",  "职业资格证书");
		return tempMap;
	}	
	
	/**
	 * 0：未设置，1：升段，2：以中专身份毕业
	 * @return
	 */
	public Map<String, LabelVo> getUpgradeMode(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
//		putLabelVo2Map(tempMap, "0",  "未设置");		
		putLabelVo2Map(tempMap, "1",  "升段");
		putLabelVo2Map(tempMap, "2",  "中专毕业");
		return tempMap;
	} 	
	
	/**
	 * 成绩结果 毕业资格审核使用
	 * @return
	 */
	public Map<String, LabelVo> getScoreResult(){
		Map<String, LabelVo> tempMap = new LinkedHashMap<String, LabelVo>();
		
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_MAKEUP_PASS + "",  "补及");
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_REVAMP_PASS + "",  "重及");
		
		putLabelVo2Map(tempMap, Constants.SCR_STUDY_RESULT_EXMP_PASS + "",  "免修");
		
		return tempMap;
	}
}
