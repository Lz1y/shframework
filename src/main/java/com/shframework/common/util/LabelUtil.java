package com.shframework.common.util;

import org.apache.commons.lang3.StringUtils;

public class LabelUtil {
	//标签 中文字对应的配置文件
		private static Configuration LABEL_FRONT = new Configuration("/../classes/cfg/label_front.properties");
		public static String getLabelName(String key, String defaultVal) {
			String ret = LABEL_FRONT.getValue(key);
			if (StringUtils.isEmpty(ret)) {
				ret = defaultVal;
			}
			return ret;
		}
		
		//需要从配置文件中读取的文字内容
		//标准的文字描述 读取配置文件
		public static final String DICT_STANDARD_NAME_UNKNOWN = "dict.all.standard.unknow";
		public static final String DICT_STANDARD_NAME_NATION = "dict.all.standard.nation";
		public static final String DICT_STANDARD_NAME_SCHOOL = "dict.all.standard.school";
		public static final String DICT_STANDARD_NAME_DATAPLATFORM = "dict.all.standard.dataplatform";
			
		//选用状态的文字描述 读取配置文件
		public static final String DICT_STATUS_NAME_HIDE = "dict.all.status.hide";
		public static final String DICT_STATUS_NAME_PUBLIC = "dict.all.status.public";
			
		//专业-状态的文字描述 读取配置文件
		public static final String DICT_MAJOR_TYPE_MIDDLE = "dict.major.type.middle";
		public static final String DICT_MAJOR_TYPE_HIGH = "dict.major.type.high";
			
		//院系-类型的文字描述 读取配置文件
		public static final String DICT_DEPARTMENT_TYPE_COLLEGE = "dict.department.type.college";
		public static final String DICT_DEPARTMENT_TYPE_DEPARTMENT = "dict.department.type.department";
		public static final String DICT_DEPARTMENT_TYPE_DIVISION = "dict.department.type.division";
		
		//班级 状态的文字描述  读取配置文件
		public static final String EDU_ROLL_NATURAL_CLAZZ_STATUS_NORMAL = "edu.roll.clazz.status.normal";
		public static final String EDU_ROLL_NATURAL_CLAZZ_STATUS_EXPECTED = "edu.roll.clazz.status.expected";
		public static final String EDU_ROLL_NATURAL_CLAZZ_STATUS_GRADUATE = "edu.roll.clazz.status.graduate";
		
		//学籍 是否在校  读取配置文件
		public static final String EDU_ROLL_PRESENCEFLAG_NO = "edu.roll.presenceflag.no";
		public static final String EDU_ROLL_PRESENCEFLAG_YES = "edu.roll.presenceflag.yes";
		
		//学籍 是否有教委学籍  读取配置文件
		public static final String EDU_ROLL_ROLLFLAG_NO = "edu.roll.rollflag.no";
		public static final String EDU_ROLL_ROLLFLAG_YES = "edu.roll.rollflag.yes";
		
		//学籍 是否缴费  读取配置文件
		public static final String EDU_ROLL_PAYMENTFLAG_NO = "edu.roll.paymentflag.no";
		public static final String EDU_ROLL_PAYMENTFLAG_YES = "edu.roll.paymentflag.yes";
		
		//学籍 注册状态  读取配置文件
		public static final String EDU_ROLL_REGISTERSTATUS_WAIT = "edu.roll.registerstatus.wait";
		public static final String EDU_ROLL_REGISTERSTATUS_REGISTERED = "edu.roll.registerstatus.registerstatus";
		public static final String EDU_ROLL_REGISTERSTATUS_SUSPENDED = "edu.roll.registerstatus.suspended";
		public static final String EDU_ROLL_REGISTERSTATUS_LEAVE = "edu.roll.registerstatus.leave";
		public static final String EDU_ROLL_REGISTERSTATUS_WRITEOFF = "edu.roll.registerstatus.writeoff";

		//学籍 学籍确认状态  读取配置文件
		public static final String EDU_ROLL_CONFIRMFLAG_UNCONFIRMED = "edu.roll.confirmflag.unconfirmed";
		public static final String EDU_ROLL_CONFIRMFLAG_CONFIRMED_RIGHT = "edu.roll.confirmflag.confirmed.right";
		public static final String EDU_ROLL_CONFIRMFLAG_CONFIRMED_ERROR = "edu.roll.confirmflag.confirmed.error";
		
		//学籍 教师类型  读取配置文件
		public static final String EMPLOYEE_TYPE_CLASS = "edu.employee.type.class";
		public static final String EMPLOYEE_TYPE_TEACH = "edu.employee.type.teach";
		
		//系统 任务  读取配置文件
		public static final String SYS_SCHEDULE_STATUS_UNFINISHED = "sys.schedule.status.unfinshed";
		public static final String SYS_SCHEDULE_STATUS_FINISHED = "sys.schedule.status.finished";
		public static final String SYS_SCHEDULE_STATUS_ABANDON = "sys.schedule.status.abandon";
		public static final String SYS_SCHEDULE_STATUS_NEEDNOT = "sys.schedule.status.neednot";
		
		//职务  类别 读取配置文件
		public static final String HEAD_JOB_TYPE_ADMINISTRATIVE = "head.job.type.administrative";
		public static final String HEAD_JOB_TYPE_TECH = "head.job.type.tech";
		
		//性别 类别 读取配置文件
		public static final String GENDER_NAME_FEMALE = "gender.female";
		public static final String GENDER_NAME_MALE = "gender.male";
		
		// 课程  是否校企合作课程  0,1
		public static final String EDU_SKD_COURSE_COOPFLAG_NO = "edu.skd.course.coopflag.no";
		public static final String EDU_SKD_COURSE_COOPFLAG_YES = "edu.skd.course.coopflag.yes";
		
		// 课程  考试考查  0,1
		public static final String EDU_SKD_COURSE_EXAMFLAG_NO = "edu.skd.course.examflag.no";
		public static final String EDU_SKD_COURSE_EXAMFLAG_YES = "edu.skd.course.examflag.yes";
		
		// 教学计划-课程  是否使用参考网:不使用,使用 0,1
		public static final String EDU_SKD_COURSE_SHOWURL_NO = "edu.skd.course.showurl.no";
		public static final String EDU_SKD_COURSE_SHOWURL_YES = "edu.skd.course.showurl.yes";
		
		// 教学计划-课程  文件类型 0:课标，1:大纲
		public static final String EDU_SKD_COURSE_STANDARDFILETYPE_NO = "edu.skd.course.standardfiletype.no";
		public static final String EDU_SKD_COURSE_STANDARDFILETYPE_YES = "edu.skd.course.standardfiletype.yes";
		
		//教学计划-课程 学分计算方法 1:等于周学时数，2:学时小计/每学分学时， 3:等于实时周数 ， 4:手输学分
		public static final String EDU_SKD_COURSE_CCM_1 = "edu.skd.course.ccm.1"; 
		public static final String EDU_SKD_COURSE_CCM_2 = "edu.skd.course.ccm.2";
		public static final String EDU_SKD_COURSE_CCM_3 = "edu.skd.course.ccm.3";
		public static final String EDU_SKD_COURSE_CCM_4 = "edu.skd.course.ccm.4";
		
		//教学计划-课程 教学周计算方法 1:随进程，2:固定周数， 3: 手输周数
		public static final String EDU_SKD_COURSE_WCM_WITHPROCESS = "edu.skd.course.wcm.withprocess";
		public static final String EDU_SKD_COURSE_WCM_REGULAR = "edu.skd.course.wcm.regular";
		public static final String EDU_SKD_COURSE_WCM_MANUALLY = "edu.skd.course.wcm.manually";
		
		//学籍(模板管理) 模板格式  读取配置文件
		public static final String COMN_TEMPLATE_FILEFORMAT_EXCEL = "comn.template.fileformate.excel";
		public static final String COMN_TEMPLATE_FILEFORMAT_DBF = "comn.template.fileformate.dbf";
		
		//学籍(模板管理) 模板类型  读取配置文件
		public static final String COMN_TEMPLATE_TYPE_IMPORT = "comn.template.type.import";
		public static final String COMN_TEMPLATE_TYPE_EXPORT = "comn.template.type.export";
		
		//进程自定义符号
		public static final String EDU_PROG_SYMBOL_THEORY = "edu.prog.symbol.theory";
		public static final String EDU_PROG_SYMBOL_TRAINING = "edu.prog.symbol.training";
		public static final String EDU_PROG_SYMBOL_EXAMINATION = "edu.prog.symbol.examination";
		public static final String EDU_PROG_SYMBOL_FLEXIBLE = "edu.prog.symbol.flexible";
		public static final String EDU_PROG_SYMBOL_ALONGSCHOOLCALENDAR = "edu.prog.symbol.alongschoolcalendar";
		
		//进程自定义符号 显示用：符号
		public static final String EDU_PROG_SYMBOL_THEORY_SHOW = "edu.prog.symbol.theory.show";
		public static final String EDU_PROG_SYMBOL_TRAINING_SHOW  = "edu.prog.symbol.training.show";
		public static final String EDU_PROG_SYMBOL_EXAMINATION_SHOW  = "edu.prog.symbol.examination.show";
		public static final String EDU_PROG_SYMBOL_FLEXIBLE_SHOW  = "edu.prog.symbol.flexible.show";
		public static final String EDU_PROG_SYMBOL_ALONGSCHOOLCALENDAR_SHOW  = "edu.prog.symbol.alongschoolcalendar.show";
		
		//进程自定义符号 显示用：名称
		public static final String EDU_PROG_SYMBOL_THEORY_SHOWTITLE = "edu.prog.symbol.theory.showtitle";
		public static final String EDU_PROG_SYMBOL_TRAINING_SHOWTITLE  = "edu.prog.symbol.training.showtitle";
		public static final String EDU_PROG_SYMBOL_EXAMINATION_SHOWTITLE  = "edu.prog.symbol.examination.showtitle";
		public static final String EDU_PROG_SYMBOL_FLEXIBLE_SHOWTITLE  = "edu.prog.symbol.flexible.showtitle";
		public static final String EDU_PROG_SYMBOL_ALONGSCHOOLCALENDAR_SHOWTITLE  = "edu.prog.symbol.alongschoolcalendar.showtitle";
}