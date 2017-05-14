package com.shframework.modules.comn.entity.vo;

/**
 * <P>学籍有模板设置时，需要导出功能（勾选导出、或带搜索条件导出）</p>
 * @author zhangjk
 *
 */
public class CriteriaParamVO {

	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 表别名
	 */
	private String tableAlias;
	/**
	 * 关联关系：
	 * <p>eg: 	_ersr.student_id = _erph.student_id </p>
	 */
	private String relation;			//关联关系		
	/**
	 * 关联方法：
	 * <p>eg: 	INNER JOIN</p>
	 */
	private String relationMethod; //关联方法	 	
	
	private String[] relationPrimaryKey ;  //联合主键
	
	private Integer[] multiTable ;//获取单表或者多表： column name, 和 comment 
	
	private String defaultWhereSql;//各模块特殊，默认的查询条件
	
	/**
	 * <p>获取单表或者多表： column name, 和 comment </p>
	 * <p>List<CriteriaParamVO> CriteriaParamVO 在list中的顺序</p>
	 * @return
	 */
	public Integer[] getMultiTable() {
		return multiTable;
	}

	/**
	 * <p>获取单表或者多表： column name, 和 comment </p>
	 * <p>List<CriteriaParamVO> CriteriaParamVO 在list中的顺序</p>
	 * @return
	 */
	public void setMultiTable(Integer[] multiTable) {
		this.multiTable = multiTable;
	}

	public String[] getRelationPrimaryKey() {
		return relationPrimaryKey;
	}

	public void setRelationPrimaryKey(String[] relationPrimaryKey) {
		this.relationPrimaryKey = relationPrimaryKey;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableAlias() {
		return tableAlias;
	}
	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}
	/**
	 * 关联关系：
	 * <p>eg: 	_ersr.student_id = _erph.student_id </p>
	 */
	public String getRelation() {
		return relation;
	}
	/**
	 * 关联关系：
	 * <p>eg: 	_ersr.student_id = _erph.student_id </p>
	 */
	public void setRelation(String relation) {
		this.relation = relation;
	}
	/**
	 * 关联方法：
	 * <p>eg: 	INNER JOIN</p>
	 */
	public String getRelationMethod() {
		return relationMethod;
	}
	/**
	 * 关联方法：
	 * <p>eg: 	INNER JOIN</p>
	 */
	public void setRelationMethod(String relationMethod) {
		this.relationMethod = relationMethod;
	}
	public CriteriaParamVO(String tableName, String tableAlias) {
		this.tableName = tableName;
		this.tableAlias = tableAlias;
	}
	
	/**
	 * 构造函数
	 * @param tableName 表名
	 * @param tableAlias	表别名
	 * @param relation		关联关系
	 * @param relationMethod 关联方法
	 */
	public CriteriaParamVO(String tableName, String tableAlias,
			String relation, String relationMethod) {
		this.tableName = tableName;
		this.tableAlias = tableAlias;
		this.relation = relation;
		this.relationMethod = relationMethod;
	}
	public CriteriaParamVO(String tableName, String tableAlias,
			String relation, String relationMethod, String[] relationPrimaryKey) {
		this.tableName = tableName;
		this.tableAlias = tableAlias;
		this.relation = relation;
		this.relationMethod = relationMethod;
		this.relationPrimaryKey = relationPrimaryKey;
	}

	public CriteriaParamVO() {
		super();
	}

	public String getDefaultWhereSql() {
		return defaultWhereSql;
	}

	public void setDefaultWhereSql(String defaultWhereSql) {
		this.defaultWhereSql = defaultWhereSql;
	}

	
	
}
