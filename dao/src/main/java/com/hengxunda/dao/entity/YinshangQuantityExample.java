package com.hengxunda.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class YinshangQuantityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public YinshangQuantityExample() {
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

        public Criteria andAdvertUserIdIsNull() {
            addCriterion("advert_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdIsNotNull() {
            addCriterion("advert_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdEqualTo(String value) {
            addCriterion("advert_user_id =", value, "advertUserId");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdNotEqualTo(String value) {
            addCriterion("advert_user_id <>", value, "advertUserId");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdGreaterThan(String value) {
            addCriterion("advert_user_id >", value, "advertUserId");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("advert_user_id >=", value, "advertUserId");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdLessThan(String value) {
            addCriterion("advert_user_id <", value, "advertUserId");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdLessThanOrEqualTo(String value) {
            addCriterion("advert_user_id <=", value, "advertUserId");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdLike(String value) {
            addCriterion("advert_user_id like", value, "advertUserId");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdNotLike(String value) {
            addCriterion("advert_user_id not like", value, "advertUserId");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdIn(List<String> values) {
            addCriterion("advert_user_id in", values, "advertUserId");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdNotIn(List<String> values) {
            addCriterion("advert_user_id not in", values, "advertUserId");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdBetween(String value1, String value2) {
            addCriterion("advert_user_id between", value1, value2, "advertUserId");
            return (Criteria) this;
        }

        public Criteria andAdvertUserIdNotBetween(String value1, String value2) {
            addCriterion("advert_user_id not between", value1, value2, "advertUserId");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(BigDecimal value) {
            addCriterion("quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(BigDecimal value) {
            addCriterion("quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(BigDecimal value) {
            addCriterion("quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(BigDecimal value) {
            addCriterion("quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(BigDecimal value) {
            addCriterion("quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<BigDecimal> values) {
            addCriterion("quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<BigDecimal> values) {
            addCriterion("quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quantity not between", value1, value2, "quantity");
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