package com.shframework.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shframework.modules.comn.entity.vo.CriteriaParamVO;

public class ResourceTemplateUtils {

	public static Map<String,List<CriteriaParamVO>>staticMap = new HashMap<String, List<CriteriaParamVO>>();
	
	public static List<String> commentList = new ArrayList<String>();
	
	static{
		
		List<CriteriaParamVO> paymentCriteriaList = new ArrayList<CriteriaParamVO>();
		CriteriaParamVO paymentParamVO = new CriteriaParamVO();
		paymentParamVO.setTableName("edu_roll_payment_history");
		paymentParamVO.setTableAlias("_erph");
		paymentParamVO.setMultiTable(new Integer[]{ 0, 1, 2, 3 });
		/*
		  _erph.student_id = #{studentId,jdbcType=INTEGER}
  			and _erph.academic_year_id = #{academicYearId,jdbcType=INTEGER}
		 */
		paymentParamVO.setRelationPrimaryKey(new String[]{"_erph.student_id", "_erph.academic_year_id"});
		
		paymentCriteriaList.add(0, paymentParamVO);
		paymentCriteriaList.add(1, new CriteriaParamVO("edu_roll_student_roll", "_ersr", "_ersr.student_id = _erph.student_id", "INNER JOIN"));
		paymentCriteriaList.add(2, new CriteriaParamVO("sys_user", "_su", "_su.id = _erph.student_id", "INNER JOIN"));
		paymentCriteriaList.add(3, new CriteriaParamVO("edu_roll_natural_clazz", "_ernc", "_ernc.id= _ersr.clazz_id", "INNER JOIN"));
		paymentCriteriaList.add(4, new CriteriaParamVO("dict_edu_common_major_field", "_decmf", "_decmf.id= _ersr.major_field_id", "INNER JOIN"));
		//缴费管理
		staticMap.put(PurviewUtil.TEMPLATE_PAYMENT, paymentCriteriaList);
		
		List<CriteriaParamVO> termenrollCriteriaList = new ArrayList<CriteriaParamVO>();
		CriteriaParamVO termenrollParamVO = new CriteriaParamVO();
		termenrollParamVO.setTableName("edu_roll_student_roll");
		termenrollParamVO.setTableAlias("_ersr");
		termenrollParamVO.setMultiTable(new Integer[]{0,1,2,3,4,5,6,7});
		termenrollParamVO.setRelationPrimaryKey(new String[]{"_ersr.id"});
		
		termenrollParamVO.setDefaultWhereSql(" and _ersr.new_student_flag = 0 ");
		//index index at which the specified element is to be inserted
		//element element to be inserted
		termenrollCriteriaList.add(0, termenrollParamVO);
		termenrollCriteriaList.add(1, new CriteriaParamVO("sys_user", "_su", "_ersr.student_id = _su.id", "INNER JOIN"));
		termenrollCriteriaList.add(2, new CriteriaParamVO("edu_roll_student_poll_info", "_erspi", " _erspi.student_id = _ersr.student_id", "INNER JOIN"));
		termenrollCriteriaList.add(3, new CriteriaParamVO("edu_roll_natural_clazz", "_ernc", "_ersr.clazz_id = _ernc.id", "INNER JOIN"));
		termenrollCriteriaList.add(4, new CriteriaParamVO("dict_edu_common_major_field", "_decmf", "_ersr.major_field_id = _decmf.id", "INNER JOIN"));
		termenrollCriteriaList.add(5, new CriteriaParamVO("edu_roll_clazz_campus", "_ercc", "_ercc.clazz_id= _ernc.id", "INNER JOIN"));
		termenrollCriteriaList.add(6, new CriteriaParamVO("dict_edu_common_acd_year_term", "_decayt", "_decayt.id = _ercc.acd_year_term_id", "INNER JOIN"));
		termenrollCriteriaList.add(7, new CriteriaParamVO("edu_roll_payment_history", "_erph", "_ersr.student_id = _erph.student_id AND _erph.academic_year_id = _decayt.academic_year_id", "LEFT JOIN"));
		
		//学期注册管理
		staticMap.put(PurviewUtil.TEMPLATE_TERMENROLL, termenrollCriteriaList);
		
		List<CriteriaParamVO> freshmanCriteriaList = new ArrayList<CriteriaParamVO>();
		CriteriaParamVO freshmanParamVO = new CriteriaParamVO();
		freshmanParamVO.setTableName("edu_roll_student");
		freshmanParamVO.setTableAlias("_ers");
		freshmanParamVO.setMultiTable(new Integer[]{0,1,2,3,4,5,6,7,8});
		freshmanParamVO.setRelationPrimaryKey(new String[]{"_ers.user_id"});
		
		//默认条件
		freshmanParamVO.setDefaultWhereSql(" and _ers.status != 0 and _ers.logic_delete = 0 and _ersr.new_student_flag = 1 ");

		freshmanCriteriaList.add(0, freshmanParamVO);
		freshmanCriteriaList.add(1, new CriteriaParamVO("sys_user", "_su", "_ers.user_id = _su.id", "inner join"));
		freshmanCriteriaList.add(2, new CriteriaParamVO("edu_roll_student_poll_info", "_erspi", "_ers.user_id = _erspi.student_id", "inner join"));
		freshmanCriteriaList.add(3, new CriteriaParamVO("edu_roll_student_roll", "_ersr", "_ers.user_id = _ersr.student_id", "inner join"));
		freshmanCriteriaList.add(4, new CriteriaParamVO("edu_roll_natural_clazz", "_ernc", "_ersr.clazz_id = _ernc.id", "inner join"));
		freshmanCriteriaList.add(5, new CriteriaParamVO("dict_edu_common_major_field", "_decmf", "_ersr.major_field_id = _decmf.id", "INNER JOIN"));
		freshmanCriteriaList.add(6, new CriteriaParamVO("edu_roll_student_confirm", "_ersc", "_ersc.student_id= _ersr.student_id", "INNER JOIN"));
		freshmanCriteriaList.add(7, new CriteriaParamVO("dict_edu_common_major", "_decm", "_decmf.major_id = _decm.id", "INNER JOIN"));
		//rwz add for export To jiaowei
		//政治面貌（简称），学制（编号）
		freshmanCriteriaList.add(8, new CriteriaParamVO("dict_common_policital_status", "_dcps", "_ers.policital_status_id = _dcps.id", "INNER JOIN"));
		freshmanCriteriaList.add(9, new CriteriaParamVO("dict_edu_common_edu_system", "_deces", "_ersr.edu_system_id = _deces.id", "INNER JOIN"));

		//新生管理
		staticMap.put(PurviewUtil.TEMPLATE_FRESHMAN, freshmanCriteriaList);
		
		List<CriteriaParamVO> rollchangeCriteriaList = new ArrayList<CriteriaParamVO>();
		CriteriaParamVO rollchangeParamVO = new CriteriaParamVO();
		rollchangeParamVO.setTableName("edu_roll_student_roll_history");
		rollchangeParamVO.setTableAlias("_history");
		rollchangeParamVO.setMultiTable(new Integer[]{ 0, 1, 2});
		rollchangeParamVO.setRelationPrimaryKey(new String[]{ "_history.id" });
		rollchangeParamVO.setDefaultWhereSql(" and _history.parent_id != 0 ");

		rollchangeCriteriaList.add(0, rollchangeParamVO);
		rollchangeCriteriaList.add(1, new CriteriaParamVO("sys_user", "_su", "_history.student_id = _su.id", "inner join"));
		rollchangeCriteriaList.add(2, new CriteriaParamVO("edu_roll_student", "_ers", "_history.student_id = _ers.user_id", "inner join"));
		rollchangeCriteriaList.add(3, new CriteriaParamVO("edu_roll_student_roll", "_ersr", "_ers.user_id = _ersr.student_id", "inner join"));//rwz 恢复
		rollchangeCriteriaList.add(4, new CriteriaParamVO("dict_edu_common_major_field", "_decmf", "_history.major_field_id = _decmf.id", "INNER JOIN"));//rwz modify //rwz 恢复
		//学籍异动
		staticMap.put(PurviewUtil.TEMPLATE_ROLLCHANGE, rollchangeCriteriaList);
		
		List<CriteriaParamVO> studentCriteriaList = new ArrayList<CriteriaParamVO>();
		CriteriaParamVO studentParamVO = new CriteriaParamVO();
		studentParamVO.setTableName("edu_roll_student");
		studentParamVO.setTableAlias("_ers");
		studentParamVO.setMultiTable(new Integer[]{0,1,2,3,4});
		studentParamVO.setRelationPrimaryKey(new String[]{"_ers.user_id"});
		studentParamVO.setDefaultWhereSql(" and _ersr.new_student_flag = 0 and _ers.status != 0 and _ers.logic_delete = 0 AND _ersr.`graduate_flag` = 0 ");

		studentCriteriaList.add(0, studentParamVO);
		studentCriteriaList.add(1, new CriteriaParamVO("sys_user", "_su", "_ers.user_id = _su.id and _su.user_role=1 and _su.logic_delete=0", "inner join"));
		studentCriteriaList.add(2, new CriteriaParamVO("edu_roll_student_poll_info", "_erspi", "_ers.user_id = _erspi.student_id", "inner join"));
		studentCriteriaList.add(3, new CriteriaParamVO("edu_roll_student_roll", "_ersr", "_ers.user_id = _ersr.student_id", "inner join"));
		studentCriteriaList.add(4, new CriteriaParamVO("edu_roll_natural_clazz", "_ernc", "_ersr.clazz_id = _ernc.id", "left join"));
		studentCriteriaList.add(5, new CriteriaParamVO("dict_edu_common_major_field", "_decmf", "_ersr.major_field_id = _decmf.id", "inner join"));
		studentCriteriaList.add(6, new CriteriaParamVO("edu_roll_clazz_campus", "_ercc", "_ercc.clazz_id= _ernc.id", "left JOIN"));
		studentCriteriaList.add(7, new CriteriaParamVO("edu_grad_student", "_egs", " _egs.student_id = _su.id and _egs.grad_cert_type = 1", "left JOIN"));
		//在校生管理
		staticMap.put(PurviewUtil.TEMPLATE_STUDENT, studentCriteriaList);
	}
	
	public static List<CriteriaParamVO> getCriteriaParamVOByKey(String resourceRule) {
		return staticMap.get(resourceRule);
	}
	
	
	public static List<String> getColumnComment() {
		commentList.add("备注");
		commentList.add("最后修改人");
		commentList.add("创建日期");
		commentList.add("修改日期");
		commentList.add("学生基础信息流水号");
		commentList.add("流水号");
		commentList.add("状态");
		commentList.add("用户密码");
		commentList.add("盐");
		commentList.add("锁定状态");
		commentList.add("拼音");
		commentList.add("简拼");
		commentList.add("用户表流水号");
		commentList.add("入学照片");
		commentList.add("显示次序");
		commentList.add("选用状态");
		commentList.add("锁定状态");
		commentList.add("标准");
		commentList.add("逻辑删除");
		return commentList;
	}
	
}
