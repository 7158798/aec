package com.hengxunda.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LevelAwardParameterExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LevelAwardParameterExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andLevelNameIsNull() {
            addCriterion("level_name is null");
            return (Criteria) this;
        }

        public Criteria andLevelNameIsNotNull() {
            addCriterion("level_name is not null");
            return (Criteria) this;
        }

        public Criteria andLevelNameEqualTo(String value) {
            addCriterion("level_name =", value, "levelName");
            return (Criteria) this;
        }

        public Criteria andLevelNameNotEqualTo(String value) {
            addCriterion("level_name <>", value, "levelName");
            return (Criteria) this;
        }

        public Criteria andLevelNameGreaterThan(String value) {
            addCriterion("level_name >", value, "levelName");
            return (Criteria) this;
        }

        public Criteria andLevelNameGreaterThanOrEqualTo(String value) {
            addCriterion("level_name >=", value, "levelName");
            return (Criteria) this;
        }

        public Criteria andLevelNameLessThan(String value) {
            addCriterion("level_name <", value, "levelName");
            return (Criteria) this;
        }

        public Criteria andLevelNameLessThanOrEqualTo(String value) {
            addCriterion("level_name <=", value, "levelName");
            return (Criteria) this;
        }

        public Criteria andLevelNameLike(String value) {
            addCriterion("level_name like", value, "levelName");
            return (Criteria) this;
        }

        public Criteria andLevelNameNotLike(String value) {
            addCriterion("level_name not like", value, "levelName");
            return (Criteria) this;
        }

        public Criteria andLevelNameIn(List<String> values) {
            addCriterion("level_name in", values, "levelName");
            return (Criteria) this;
        }

        public Criteria andLevelNameNotIn(List<String> values) {
            addCriterion("level_name not in", values, "levelName");
            return (Criteria) this;
        }

        public Criteria andLevelNameBetween(String value1, String value2) {
            addCriterion("level_name between", value1, value2, "levelName");
            return (Criteria) this;
        }

        public Criteria andLevelNameNotBetween(String value1, String value2) {
            addCriterion("level_name not between", value1, value2, "levelName");
            return (Criteria) this;
        }

        public Criteria andLevelCodeIsNull() {
            addCriterion("level_code is null");
            return (Criteria) this;
        }

        public Criteria andLevelCodeIsNotNull() {
            addCriterion("level_code is not null");
            return (Criteria) this;
        }

        public Criteria andLevelCodeEqualTo(String value) {
            addCriterion("level_code =", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeNotEqualTo(String value) {
            addCriterion("level_code <>", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeGreaterThan(String value) {
            addCriterion("level_code >", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeGreaterThanOrEqualTo(String value) {
            addCriterion("level_code >=", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeLessThan(String value) {
            addCriterion("level_code <", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeLessThanOrEqualTo(String value) {
            addCriterion("level_code <=", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeLike(String value) {
            addCriterion("level_code like", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeNotLike(String value) {
            addCriterion("level_code not like", value, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeIn(List<String> values) {
            addCriterion("level_code in", values, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeNotIn(List<String> values) {
            addCriterion("level_code not in", values, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeBetween(String value1, String value2) {
            addCriterion("level_code between", value1, value2, "levelCode");
            return (Criteria) this;
        }

        public Criteria andLevelCodeNotBetween(String value1, String value2) {
            addCriterion("level_code not between", value1, value2, "levelCode");
            return (Criteria) this;
        }

        public Criteria andMinAmtIsNull() {
            addCriterion("min_amt is null");
            return (Criteria) this;
        }

        public Criteria andMinAmtIsNotNull() {
            addCriterion("min_amt is not null");
            return (Criteria) this;
        }

        public Criteria andMinAmtEqualTo(BigDecimal value) {
            addCriterion("min_amt =", value, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtNotEqualTo(BigDecimal value) {
            addCriterion("min_amt <>", value, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtGreaterThan(BigDecimal value) {
            addCriterion("min_amt >", value, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("min_amt >=", value, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtLessThan(BigDecimal value) {
            addCriterion("min_amt <", value, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("min_amt <=", value, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtIn(List<BigDecimal> values) {
            addCriterion("min_amt in", values, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtNotIn(List<BigDecimal> values) {
            addCriterion("min_amt not in", values, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_amt between", value1, value2, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMinAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_amt not between", value1, value2, "minAmt");
            return (Criteria) this;
        }

        public Criteria andMaxAmtIsNull() {
            addCriterion("max_amt is null");
            return (Criteria) this;
        }

        public Criteria andMaxAmtIsNotNull() {
            addCriterion("max_amt is not null");
            return (Criteria) this;
        }

        public Criteria andMaxAmtEqualTo(BigDecimal value) {
            addCriterion("max_amt =", value, "maxAmt");
            return (Criteria) this;
        }

        public Criteria andMaxAmtNotEqualTo(BigDecimal value) {
            addCriterion("max_amt <>", value, "maxAmt");
            return (Criteria) this;
        }

        public Criteria andMaxAmtGreaterThan(BigDecimal value) {
            addCriterion("max_amt >", value, "maxAmt");
            return (Criteria) this;
        }

        public Criteria andMaxAmtGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_amt >=", value, "maxAmt");
            return (Criteria) this;
        }

        public Criteria andMaxAmtLessThan(BigDecimal value) {
            addCriterion("max_amt <", value, "maxAmt");
            return (Criteria) this;
        }

        public Criteria andMaxAmtLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_amt <=", value, "maxAmt");
            return (Criteria) this;
        }

        public Criteria andMaxAmtIn(List<BigDecimal> values) {
            addCriterion("max_amt in", values, "maxAmt");
            return (Criteria) this;
        }

        public Criteria andMaxAmtNotIn(List<BigDecimal> values) {
            addCriterion("max_amt not in", values, "maxAmt");
            return (Criteria) this;
        }

        public Criteria andMaxAmtBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_amt between", value1, value2, "maxAmt");
            return (Criteria) this;
        }

        public Criteria andMaxAmtNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_amt not between", value1, value2, "maxAmt");
            return (Criteria) this;
        }

        public Criteria andPrizeAecIsNull() {
            addCriterion("prize_aec is null");
            return (Criteria) this;
        }

        public Criteria andPrizeAecIsNotNull() {
            addCriterion("prize_aec is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeAecEqualTo(BigDecimal value) {
            addCriterion("prize_aec =", value, "prizeAec");
            return (Criteria) this;
        }

        public Criteria andPrizeAecNotEqualTo(BigDecimal value) {
            addCriterion("prize_aec <>", value, "prizeAec");
            return (Criteria) this;
        }

        public Criteria andPrizeAecGreaterThan(BigDecimal value) {
            addCriterion("prize_aec >", value, "prizeAec");
            return (Criteria) this;
        }

        public Criteria andPrizeAecGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_aec >=", value, "prizeAec");
            return (Criteria) this;
        }

        public Criteria andPrizeAecLessThan(BigDecimal value) {
            addCriterion("prize_aec <", value, "prizeAec");
            return (Criteria) this;
        }

        public Criteria andPrizeAecLessThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_aec <=", value, "prizeAec");
            return (Criteria) this;
        }

        public Criteria andPrizeAecIn(List<BigDecimal> values) {
            addCriterion("prize_aec in", values, "prizeAec");
            return (Criteria) this;
        }

        public Criteria andPrizeAecNotIn(List<BigDecimal> values) {
            addCriterion("prize_aec not in", values, "prizeAec");
            return (Criteria) this;
        }

        public Criteria andPrizeAecBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_aec between", value1, value2, "prizeAec");
            return (Criteria) this;
        }

        public Criteria andPrizeAecNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_aec not between", value1, value2, "prizeAec");
            return (Criteria) this;
        }

        public Criteria andPrizeMscIsNull() {
            addCriterion("prize_msc is null");
            return (Criteria) this;
        }

        public Criteria andPrizeMscIsNotNull() {
            addCriterion("prize_msc is not null");
            return (Criteria) this;
        }

        public Criteria andPrizeMscEqualTo(BigDecimal value) {
            addCriterion("prize_msc =", value, "prizeMsc");
            return (Criteria) this;
        }

        public Criteria andPrizeMscNotEqualTo(BigDecimal value) {
            addCriterion("prize_msc <>", value, "prizeMsc");
            return (Criteria) this;
        }

        public Criteria andPrizeMscGreaterThan(BigDecimal value) {
            addCriterion("prize_msc >", value, "prizeMsc");
            return (Criteria) this;
        }

        public Criteria andPrizeMscGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_msc >=", value, "prizeMsc");
            return (Criteria) this;
        }

        public Criteria andPrizeMscLessThan(BigDecimal value) {
            addCriterion("prize_msc <", value, "prizeMsc");
            return (Criteria) this;
        }

        public Criteria andPrizeMscLessThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_msc <=", value, "prizeMsc");
            return (Criteria) this;
        }

        public Criteria andPrizeMscIn(List<BigDecimal> values) {
            addCriterion("prize_msc in", values, "prizeMsc");
            return (Criteria) this;
        }

        public Criteria andPrizeMscNotIn(List<BigDecimal> values) {
            addCriterion("prize_msc not in", values, "prizeMsc");
            return (Criteria) this;
        }

        public Criteria andPrizeMscBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_msc between", value1, value2, "prizeMsc");
            return (Criteria) this;
        }

        public Criteria andPrizeMscNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_msc not between", value1, value2, "prizeMsc");
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