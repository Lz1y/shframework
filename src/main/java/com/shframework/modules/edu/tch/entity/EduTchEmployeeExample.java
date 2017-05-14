package com.shframework.modules.edu.tch.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EduTchEmployeeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart = -1;

    protected Integer limitEnd = -1;

    public EduTchEmployeeExample() {
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Integer value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Integer value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Integer value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Integer value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Integer> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Integer> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Integer value1, Integer value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andPinyinIsNull() {
            addCriterion("pinyin is null");
            return (Criteria) this;
        }

        public Criteria andPinyinIsNotNull() {
            addCriterion("pinyin is not null");
            return (Criteria) this;
        }

        public Criteria andPinyinEqualTo(String value) {
            addCriterion("pinyin =", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinNotEqualTo(String value) {
            addCriterion("pinyin <>", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinGreaterThan(String value) {
            addCriterion("pinyin >", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinGreaterThanOrEqualTo(String value) {
            addCriterion("pinyin >=", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinLessThan(String value) {
            addCriterion("pinyin <", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinLessThanOrEqualTo(String value) {
            addCriterion("pinyin <=", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinLike(String value) {
            addCriterion("pinyin like", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinNotLike(String value) {
            addCriterion("pinyin not like", value, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinIn(List<String> values) {
            addCriterion("pinyin in", values, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinNotIn(List<String> values) {
            addCriterion("pinyin not in", values, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinBetween(String value1, String value2) {
            addCriterion("pinyin between", value1, value2, "pinyin");
            return (Criteria) this;
        }

        public Criteria andPinyinNotBetween(String value1, String value2) {
            addCriterion("pinyin not between", value1, value2, "pinyin");
            return (Criteria) this;
        }

        public Criteria andJianpinIsNull() {
            addCriterion("jianpin is null");
            return (Criteria) this;
        }

        public Criteria andJianpinIsNotNull() {
            addCriterion("jianpin is not null");
            return (Criteria) this;
        }

        public Criteria andJianpinEqualTo(String value) {
            addCriterion("jianpin =", value, "jianpin");
            return (Criteria) this;
        }

        public Criteria andJianpinNotEqualTo(String value) {
            addCriterion("jianpin <>", value, "jianpin");
            return (Criteria) this;
        }

        public Criteria andJianpinGreaterThan(String value) {
            addCriterion("jianpin >", value, "jianpin");
            return (Criteria) this;
        }

        public Criteria andJianpinGreaterThanOrEqualTo(String value) {
            addCriterion("jianpin >=", value, "jianpin");
            return (Criteria) this;
        }

        public Criteria andJianpinLessThan(String value) {
            addCriterion("jianpin <", value, "jianpin");
            return (Criteria) this;
        }

        public Criteria andJianpinLessThanOrEqualTo(String value) {
            addCriterion("jianpin <=", value, "jianpin");
            return (Criteria) this;
        }

        public Criteria andJianpinLike(String value) {
            addCriterion("jianpin like", value, "jianpin");
            return (Criteria) this;
        }

        public Criteria andJianpinNotLike(String value) {
            addCriterion("jianpin not like", value, "jianpin");
            return (Criteria) this;
        }

        public Criteria andJianpinIn(List<String> values) {
            addCriterion("jianpin in", values, "jianpin");
            return (Criteria) this;
        }

        public Criteria andJianpinNotIn(List<String> values) {
            addCriterion("jianpin not in", values, "jianpin");
            return (Criteria) this;
        }

        public Criteria andJianpinBetween(String value1, String value2) {
            addCriterion("jianpin between", value1, value2, "jianpin");
            return (Criteria) this;
        }

        public Criteria andJianpinNotBetween(String value1, String value2) {
            addCriterion("jianpin not between", value1, value2, "jianpin");
            return (Criteria) this;
        }

        public Criteria andGenderIsNull() {
            addCriterion("gender is null");
            return (Criteria) this;
        }

        public Criteria andGenderIsNotNull() {
            addCriterion("gender is not null");
            return (Criteria) this;
        }

        public Criteria andGenderEqualTo(Integer value) {
            addCriterion("gender =", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotEqualTo(Integer value) {
            addCriterion("gender <>", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThan(Integer value) {
            addCriterion("gender >", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderGreaterThanOrEqualTo(Integer value) {
            addCriterion("gender >=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThan(Integer value) {
            addCriterion("gender <", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderLessThanOrEqualTo(Integer value) {
            addCriterion("gender <=", value, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderIn(List<Integer> values) {
            addCriterion("gender in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotIn(List<Integer> values) {
            addCriterion("gender not in", values, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderBetween(Integer value1, Integer value2) {
            addCriterion("gender between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andGenderNotBetween(Integer value1, Integer value2) {
            addCriterion("gender not between", value1, value2, "gender");
            return (Criteria) this;
        }

        public Criteria andPostTitleIsNull() {
            addCriterion("post_title is null");
            return (Criteria) this;
        }

        public Criteria andPostTitleIsNotNull() {
            addCriterion("post_title is not null");
            return (Criteria) this;
        }

        public Criteria andPostTitleEqualTo(String value) {
            addCriterion("post_title =", value, "postTitle");
            return (Criteria) this;
        }

        public Criteria andPostTitleNotEqualTo(String value) {
            addCriterion("post_title <>", value, "postTitle");
            return (Criteria) this;
        }

        public Criteria andPostTitleGreaterThan(String value) {
            addCriterion("post_title >", value, "postTitle");
            return (Criteria) this;
        }

        public Criteria andPostTitleGreaterThanOrEqualTo(String value) {
            addCriterion("post_title >=", value, "postTitle");
            return (Criteria) this;
        }

        public Criteria andPostTitleLessThan(String value) {
            addCriterion("post_title <", value, "postTitle");
            return (Criteria) this;
        }

        public Criteria andPostTitleLessThanOrEqualTo(String value) {
            addCriterion("post_title <=", value, "postTitle");
            return (Criteria) this;
        }

        public Criteria andPostTitleLike(String value) {
            addCriterion("post_title like", value, "postTitle");
            return (Criteria) this;
        }

        public Criteria andPostTitleNotLike(String value) {
            addCriterion("post_title not like", value, "postTitle");
            return (Criteria) this;
        }

        public Criteria andPostTitleIn(List<String> values) {
            addCriterion("post_title in", values, "postTitle");
            return (Criteria) this;
        }

        public Criteria andPostTitleNotIn(List<String> values) {
            addCriterion("post_title not in", values, "postTitle");
            return (Criteria) this;
        }

        public Criteria andPostTitleBetween(String value1, String value2) {
            addCriterion("post_title between", value1, value2, "postTitle");
            return (Criteria) this;
        }

        public Criteria andPostTitleNotBetween(String value1, String value2) {
            addCriterion("post_title not between", value1, value2, "postTitle");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNull() {
            addCriterion("department_id is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIsNotNull() {
            addCriterion("department_id is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdEqualTo(Integer value) {
            addCriterion("department_id =", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotEqualTo(Integer value) {
            addCriterion("department_id <>", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThan(Integer value) {
            addCriterion("department_id >", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("department_id >=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThan(Integer value) {
            addCriterion("department_id <", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdLessThanOrEqualTo(Integer value) {
            addCriterion("department_id <=", value, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdIn(List<Integer> values) {
            addCriterion("department_id in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotIn(List<Integer> values) {
            addCriterion("department_id not in", values, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdBetween(Integer value1, Integer value2) {
            addCriterion("department_id between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andDepartmentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("department_id not between", value1, value2, "departmentId");
            return (Criteria) this;
        }

        public Criteria andPostTypeIdIsNull() {
            addCriterion("post_type_id is null");
            return (Criteria) this;
        }

        public Criteria andPostTypeIdIsNotNull() {
            addCriterion("post_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andPostTypeIdEqualTo(Integer value) {
            addCriterion("post_type_id =", value, "postTypeId");
            return (Criteria) this;
        }

        public Criteria andPostTypeIdNotEqualTo(Integer value) {
            addCriterion("post_type_id <>", value, "postTypeId");
            return (Criteria) this;
        }

        public Criteria andPostTypeIdGreaterThan(Integer value) {
            addCriterion("post_type_id >", value, "postTypeId");
            return (Criteria) this;
        }

        public Criteria andPostTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_type_id >=", value, "postTypeId");
            return (Criteria) this;
        }

        public Criteria andPostTypeIdLessThan(Integer value) {
            addCriterion("post_type_id <", value, "postTypeId");
            return (Criteria) this;
        }

        public Criteria andPostTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("post_type_id <=", value, "postTypeId");
            return (Criteria) this;
        }

        public Criteria andPostTypeIdIn(List<Integer> values) {
            addCriterion("post_type_id in", values, "postTypeId");
            return (Criteria) this;
        }

        public Criteria andPostTypeIdNotIn(List<Integer> values) {
            addCriterion("post_type_id not in", values, "postTypeId");
            return (Criteria) this;
        }

        public Criteria andPostTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("post_type_id between", value1, value2, "postTypeId");
            return (Criteria) this;
        }

        public Criteria andPostTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("post_type_id not between", value1, value2, "postTypeId");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIdIsNull() {
            addCriterion("staff_type_id is null");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIdIsNotNull() {
            addCriterion("staff_type_id is not null");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIdEqualTo(Integer value) {
            addCriterion("staff_type_id =", value, "staffTypeId");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIdNotEqualTo(Integer value) {
            addCriterion("staff_type_id <>", value, "staffTypeId");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIdGreaterThan(Integer value) {
            addCriterion("staff_type_id >", value, "staffTypeId");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("staff_type_id >=", value, "staffTypeId");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIdLessThan(Integer value) {
            addCriterion("staff_type_id <", value, "staffTypeId");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("staff_type_id <=", value, "staffTypeId");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIdIn(List<Integer> values) {
            addCriterion("staff_type_id in", values, "staffTypeId");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIdNotIn(List<Integer> values) {
            addCriterion("staff_type_id not in", values, "staffTypeId");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("staff_type_id between", value1, value2, "staffTypeId");
            return (Criteria) this;
        }

        public Criteria andStaffTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("staff_type_id not between", value1, value2, "staffTypeId");
            return (Criteria) this;
        }

        public Criteria andPostLevelIdIsNull() {
            addCriterion("post_level_id is null");
            return (Criteria) this;
        }

        public Criteria andPostLevelIdIsNotNull() {
            addCriterion("post_level_id is not null");
            return (Criteria) this;
        }

        public Criteria andPostLevelIdEqualTo(Integer value) {
            addCriterion("post_level_id =", value, "postLevelId");
            return (Criteria) this;
        }

        public Criteria andPostLevelIdNotEqualTo(Integer value) {
            addCriterion("post_level_id <>", value, "postLevelId");
            return (Criteria) this;
        }

        public Criteria andPostLevelIdGreaterThan(Integer value) {
            addCriterion("post_level_id >", value, "postLevelId");
            return (Criteria) this;
        }

        public Criteria andPostLevelIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("post_level_id >=", value, "postLevelId");
            return (Criteria) this;
        }

        public Criteria andPostLevelIdLessThan(Integer value) {
            addCriterion("post_level_id <", value, "postLevelId");
            return (Criteria) this;
        }

        public Criteria andPostLevelIdLessThanOrEqualTo(Integer value) {
            addCriterion("post_level_id <=", value, "postLevelId");
            return (Criteria) this;
        }

        public Criteria andPostLevelIdIn(List<Integer> values) {
            addCriterion("post_level_id in", values, "postLevelId");
            return (Criteria) this;
        }

        public Criteria andPostLevelIdNotIn(List<Integer> values) {
            addCriterion("post_level_id not in", values, "postLevelId");
            return (Criteria) this;
        }

        public Criteria andPostLevelIdBetween(Integer value1, Integer value2) {
            addCriterion("post_level_id between", value1, value2, "postLevelId");
            return (Criteria) this;
        }

        public Criteria andPostLevelIdNotBetween(Integer value1, Integer value2) {
            addCriterion("post_level_id not between", value1, value2, "postLevelId");
            return (Criteria) this;
        }

        public Criteria andTeachFlagIsNull() {
            addCriterion("teach_flag is null");
            return (Criteria) this;
        }

        public Criteria andTeachFlagIsNotNull() {
            addCriterion("teach_flag is not null");
            return (Criteria) this;
        }

        public Criteria andTeachFlagEqualTo(Integer value) {
            addCriterion("teach_flag =", value, "teachFlag");
            return (Criteria) this;
        }

        public Criteria andTeachFlagNotEqualTo(Integer value) {
            addCriterion("teach_flag <>", value, "teachFlag");
            return (Criteria) this;
        }

        public Criteria andTeachFlagGreaterThan(Integer value) {
            addCriterion("teach_flag >", value, "teachFlag");
            return (Criteria) this;
        }

        public Criteria andTeachFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("teach_flag >=", value, "teachFlag");
            return (Criteria) this;
        }

        public Criteria andTeachFlagLessThan(Integer value) {
            addCriterion("teach_flag <", value, "teachFlag");
            return (Criteria) this;
        }

        public Criteria andTeachFlagLessThanOrEqualTo(Integer value) {
            addCriterion("teach_flag <=", value, "teachFlag");
            return (Criteria) this;
        }

        public Criteria andTeachFlagIn(List<Integer> values) {
            addCriterion("teach_flag in", values, "teachFlag");
            return (Criteria) this;
        }

        public Criteria andTeachFlagNotIn(List<Integer> values) {
            addCriterion("teach_flag not in", values, "teachFlag");
            return (Criteria) this;
        }

        public Criteria andTeachFlagBetween(Integer value1, Integer value2) {
            addCriterion("teach_flag between", value1, value2, "teachFlag");
            return (Criteria) this;
        }

        public Criteria andTeachFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("teach_flag not between", value1, value2, "teachFlag");
            return (Criteria) this;
        }

        public Criteria andRetireFlagIsNull() {
            addCriterion("retire_flag is null");
            return (Criteria) this;
        }

        public Criteria andRetireFlagIsNotNull() {
            addCriterion("retire_flag is not null");
            return (Criteria) this;
        }

        public Criteria andRetireFlagEqualTo(Integer value) {
            addCriterion("retire_flag =", value, "retireFlag");
            return (Criteria) this;
        }

        public Criteria andRetireFlagNotEqualTo(Integer value) {
            addCriterion("retire_flag <>", value, "retireFlag");
            return (Criteria) this;
        }

        public Criteria andRetireFlagGreaterThan(Integer value) {
            addCriterion("retire_flag >", value, "retireFlag");
            return (Criteria) this;
        }

        public Criteria andRetireFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("retire_flag >=", value, "retireFlag");
            return (Criteria) this;
        }

        public Criteria andRetireFlagLessThan(Integer value) {
            addCriterion("retire_flag <", value, "retireFlag");
            return (Criteria) this;
        }

        public Criteria andRetireFlagLessThanOrEqualTo(Integer value) {
            addCriterion("retire_flag <=", value, "retireFlag");
            return (Criteria) this;
        }

        public Criteria andRetireFlagIn(List<Integer> values) {
            addCriterion("retire_flag in", values, "retireFlag");
            return (Criteria) this;
        }

        public Criteria andRetireFlagNotIn(List<Integer> values) {
            addCriterion("retire_flag not in", values, "retireFlag");
            return (Criteria) this;
        }

        public Criteria andRetireFlagBetween(Integer value1, Integer value2) {
            addCriterion("retire_flag between", value1, value2, "retireFlag");
            return (Criteria) this;
        }

        public Criteria andRetireFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("retire_flag not between", value1, value2, "retireFlag");
            return (Criteria) this;
        }

        public Criteria andCertFlagIsNull() {
            addCriterion("cert_flag is null");
            return (Criteria) this;
        }

        public Criteria andCertFlagIsNotNull() {
            addCriterion("cert_flag is not null");
            return (Criteria) this;
        }

        public Criteria andCertFlagEqualTo(Integer value) {
            addCriterion("cert_flag =", value, "certFlag");
            return (Criteria) this;
        }

        public Criteria andCertFlagNotEqualTo(Integer value) {
            addCriterion("cert_flag <>", value, "certFlag");
            return (Criteria) this;
        }

        public Criteria andCertFlagGreaterThan(Integer value) {
            addCriterion("cert_flag >", value, "certFlag");
            return (Criteria) this;
        }

        public Criteria andCertFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("cert_flag >=", value, "certFlag");
            return (Criteria) this;
        }

        public Criteria andCertFlagLessThan(Integer value) {
            addCriterion("cert_flag <", value, "certFlag");
            return (Criteria) this;
        }

        public Criteria andCertFlagLessThanOrEqualTo(Integer value) {
            addCriterion("cert_flag <=", value, "certFlag");
            return (Criteria) this;
        }

        public Criteria andCertFlagIn(List<Integer> values) {
            addCriterion("cert_flag in", values, "certFlag");
            return (Criteria) this;
        }

        public Criteria andCertFlagNotIn(List<Integer> values) {
            addCriterion("cert_flag not in", values, "certFlag");
            return (Criteria) this;
        }

        public Criteria andCertFlagBetween(Integer value1, Integer value2) {
            addCriterion("cert_flag between", value1, value2, "certFlag");
            return (Criteria) this;
        }

        public Criteria andCertFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("cert_flag not between", value1, value2, "certFlag");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIdIsNull() {
            addCriterion("last_modify_user_id is null");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIdIsNotNull() {
            addCriterion("last_modify_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIdEqualTo(Integer value) {
            addCriterion("last_modify_user_id =", value, "lastModifyUserId");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIdNotEqualTo(Integer value) {
            addCriterion("last_modify_user_id <>", value, "lastModifyUserId");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIdGreaterThan(Integer value) {
            addCriterion("last_modify_user_id >", value, "lastModifyUserId");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("last_modify_user_id >=", value, "lastModifyUserId");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIdLessThan(Integer value) {
            addCriterion("last_modify_user_id <", value, "lastModifyUserId");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("last_modify_user_id <=", value, "lastModifyUserId");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIdIn(List<Integer> values) {
            addCriterion("last_modify_user_id in", values, "lastModifyUserId");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIdNotIn(List<Integer> values) {
            addCriterion("last_modify_user_id not in", values, "lastModifyUserId");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIdBetween(Integer value1, Integer value2) {
            addCriterion("last_modify_user_id between", value1, value2, "lastModifyUserId");
            return (Criteria) this;
        }

        public Criteria andLastModifyUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("last_modify_user_id not between", value1, value2, "lastModifyUserId");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(Date value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(Date value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(Date value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(Date value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(Date value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<Date> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<Date> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(Date value1, Date value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(Date value1, Date value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateIsNull() {
            addCriterion("modify_date is null");
            return (Criteria) this;
        }

        public Criteria andModifyDateIsNotNull() {
            addCriterion("modify_date is not null");
            return (Criteria) this;
        }

        public Criteria andModifyDateEqualTo(Date value) {
            addCriterion("modify_date =", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotEqualTo(Date value) {
            addCriterion("modify_date <>", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateGreaterThan(Date value) {
            addCriterion("modify_date >", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_date >=", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateLessThan(Date value) {
            addCriterion("modify_date <", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateLessThanOrEqualTo(Date value) {
            addCriterion("modify_date <=", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateIn(List<Date> values) {
            addCriterion("modify_date in", values, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotIn(List<Date> values) {
            addCriterion("modify_date not in", values, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateBetween(Date value1, Date value2) {
            addCriterion("modify_date between", value1, value2, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotBetween(Date value1, Date value2) {
            addCriterion("modify_date not between", value1, value2, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andLogicDeleteIsNull() {
            addCriterion("logic_delete is null");
            return (Criteria) this;
        }

        public Criteria andLogicDeleteIsNotNull() {
            addCriterion("logic_delete is not null");
            return (Criteria) this;
        }

        public Criteria andLogicDeleteEqualTo(Integer value) {
            addCriterion("logic_delete =", value, "logicDelete");
            return (Criteria) this;
        }

        public Criteria andLogicDeleteNotEqualTo(Integer value) {
            addCriterion("logic_delete <>", value, "logicDelete");
            return (Criteria) this;
        }

        public Criteria andLogicDeleteGreaterThan(Integer value) {
            addCriterion("logic_delete >", value, "logicDelete");
            return (Criteria) this;
        }

        public Criteria andLogicDeleteGreaterThanOrEqualTo(Integer value) {
            addCriterion("logic_delete >=", value, "logicDelete");
            return (Criteria) this;
        }

        public Criteria andLogicDeleteLessThan(Integer value) {
            addCriterion("logic_delete <", value, "logicDelete");
            return (Criteria) this;
        }

        public Criteria andLogicDeleteLessThanOrEqualTo(Integer value) {
            addCriterion("logic_delete <=", value, "logicDelete");
            return (Criteria) this;
        }

        public Criteria andLogicDeleteIn(List<Integer> values) {
            addCriterion("logic_delete in", values, "logicDelete");
            return (Criteria) this;
        }

        public Criteria andLogicDeleteNotIn(List<Integer> values) {
            addCriterion("logic_delete not in", values, "logicDelete");
            return (Criteria) this;
        }

        public Criteria andLogicDeleteBetween(Integer value1, Integer value2) {
            addCriterion("logic_delete between", value1, value2, "logicDelete");
            return (Criteria) this;
        }

        public Criteria andLogicDeleteNotBetween(Integer value1, Integer value2) {
            addCriterion("logic_delete not between", value1, value2, "logicDelete");
            return (Criteria) this;
        }

        public Criteria andPinyinLikeInsensitive(String value) {
            addCriterion("upper(pinyin) like", value.toUpperCase(), "pinyin");
            return (Criteria) this;
        }

        public Criteria andJianpinLikeInsensitive(String value) {
            addCriterion("upper(jianpin) like", value.toUpperCase(), "jianpin");
            return (Criteria) this;
        }

        public Criteria andPostTitleLikeInsensitive(String value) {
            addCriterion("upper(post_title) like", value.toUpperCase(), "postTitle");
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