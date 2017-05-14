package com.shframework.modules.sys.entity;

import java.util.ArrayList;
import java.util.List;

public class ComnTemplateDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart = -1;

    protected Integer limitEnd = -1;

    public ComnTemplateDetailExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart=limitStart;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd=limitEnd;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("template_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("template_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(Integer value) {
            addCriterion("template_id =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(Integer value) {
            addCriterion("template_id <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(Integer value) {
            addCriterion("template_id >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("template_id >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(Integer value) {
            addCriterion("template_id <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(Integer value) {
            addCriterion("template_id <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<Integer> values) {
            addCriterion("template_id in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<Integer> values) {
            addCriterion("template_id not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(Integer value1, Integer value2) {
            addCriterion("template_id between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("template_id not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andColDbNameIsNull() {
            addCriterion("col_db_name is null");
            return (Criteria) this;
        }

        public Criteria andColDbNameIsNotNull() {
            addCriterion("col_db_name is not null");
            return (Criteria) this;
        }

        public Criteria andColDbNameEqualTo(String value) {
            addCriterion("col_db_name =", value, "colDbName");
            return (Criteria) this;
        }

        public Criteria andColDbNameNotEqualTo(String value) {
            addCriterion("col_db_name <>", value, "colDbName");
            return (Criteria) this;
        }

        public Criteria andColDbNameGreaterThan(String value) {
            addCriterion("col_db_name >", value, "colDbName");
            return (Criteria) this;
        }

        public Criteria andColDbNameGreaterThanOrEqualTo(String value) {
            addCriterion("col_db_name >=", value, "colDbName");
            return (Criteria) this;
        }

        public Criteria andColDbNameLessThan(String value) {
            addCriterion("col_db_name <", value, "colDbName");
            return (Criteria) this;
        }

        public Criteria andColDbNameLessThanOrEqualTo(String value) {
            addCriterion("col_db_name <=", value, "colDbName");
            return (Criteria) this;
        }

        public Criteria andColDbNameLike(String value) {
            addCriterion("col_db_name like", value, "colDbName");
            return (Criteria) this;
        }

        public Criteria andColDbNameNotLike(String value) {
            addCriterion("col_db_name not like", value, "colDbName");
            return (Criteria) this;
        }

        public Criteria andColDbNameIn(List<String> values) {
            addCriterion("col_db_name in", values, "colDbName");
            return (Criteria) this;
        }

        public Criteria andColDbNameNotIn(List<String> values) {
            addCriterion("col_db_name not in", values, "colDbName");
            return (Criteria) this;
        }

        public Criteria andColDbNameBetween(String value1, String value2) {
            addCriterion("col_db_name between", value1, value2, "colDbName");
            return (Criteria) this;
        }

        public Criteria andColDbNameNotBetween(String value1, String value2) {
            addCriterion("col_db_name not between", value1, value2, "colDbName");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasIsNull() {
            addCriterion("table_db_alias is null");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasIsNotNull() {
            addCriterion("table_db_alias is not null");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasEqualTo(String value) {
            addCriterion("table_db_alias =", value, "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasNotEqualTo(String value) {
            addCriterion("table_db_alias <>", value, "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasGreaterThan(String value) {
            addCriterion("table_db_alias >", value, "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasGreaterThanOrEqualTo(String value) {
            addCriterion("table_db_alias >=", value, "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasLessThan(String value) {
            addCriterion("table_db_alias <", value, "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasLessThanOrEqualTo(String value) {
            addCriterion("table_db_alias <=", value, "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasLike(String value) {
            addCriterion("table_db_alias like", value, "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasNotLike(String value) {
            addCriterion("table_db_alias not like", value, "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasIn(List<String> values) {
            addCriterion("table_db_alias in", values, "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasNotIn(List<String> values) {
            addCriterion("table_db_alias not in", values, "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasBetween(String value1, String value2) {
            addCriterion("table_db_alias between", value1, value2, "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasNotBetween(String value1, String value2) {
            addCriterion("table_db_alias not between", value1, value2, "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andColFileNameIsNull() {
            addCriterion("col_file_name is null");
            return (Criteria) this;
        }

        public Criteria andColFileNameIsNotNull() {
            addCriterion("col_file_name is not null");
            return (Criteria) this;
        }

        public Criteria andColFileNameEqualTo(String value) {
            addCriterion("col_file_name =", value, "colFileName");
            return (Criteria) this;
        }

        public Criteria andColFileNameNotEqualTo(String value) {
            addCriterion("col_file_name <>", value, "colFileName");
            return (Criteria) this;
        }

        public Criteria andColFileNameGreaterThan(String value) {
            addCriterion("col_file_name >", value, "colFileName");
            return (Criteria) this;
        }

        public Criteria andColFileNameGreaterThanOrEqualTo(String value) {
            addCriterion("col_file_name >=", value, "colFileName");
            return (Criteria) this;
        }

        public Criteria andColFileNameLessThan(String value) {
            addCriterion("col_file_name <", value, "colFileName");
            return (Criteria) this;
        }

        public Criteria andColFileNameLessThanOrEqualTo(String value) {
            addCriterion("col_file_name <=", value, "colFileName");
            return (Criteria) this;
        }

        public Criteria andColFileNameLike(String value) {
            addCriterion("col_file_name like", value, "colFileName");
            return (Criteria) this;
        }

        public Criteria andColFileNameNotLike(String value) {
            addCriterion("col_file_name not like", value, "colFileName");
            return (Criteria) this;
        }

        public Criteria andColFileNameIn(List<String> values) {
            addCriterion("col_file_name in", values, "colFileName");
            return (Criteria) this;
        }

        public Criteria andColFileNameNotIn(List<String> values) {
            addCriterion("col_file_name not in", values, "colFileName");
            return (Criteria) this;
        }

        public Criteria andColFileNameBetween(String value1, String value2) {
            addCriterion("col_file_name between", value1, value2, "colFileName");
            return (Criteria) this;
        }

        public Criteria andColFileNameNotBetween(String value1, String value2) {
            addCriterion("col_file_name not between", value1, value2, "colFileName");
            return (Criteria) this;
        }

        public Criteria andColDbNameLikeInsensitive(String value) {
            addCriterion("upper(col_db_name) like", value.toUpperCase(), "colDbName");
            return (Criteria) this;
        }

        public Criteria andTableDbAliasLikeInsensitive(String value) {
            addCriterion("upper(table_db_alias) like", value.toUpperCase(), "tableDbAlias");
            return (Criteria) this;
        }

        public Criteria andColFileNameLikeInsensitive(String value) {
            addCriterion("upper(col_file_name) like", value.toUpperCase(), "colFileName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}