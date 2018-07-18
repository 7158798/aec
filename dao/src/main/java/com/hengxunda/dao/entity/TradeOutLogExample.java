package com.hengxunda.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TradeOutLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TradeOutLogExample() {
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andShopuseridIsNull() {
            addCriterion("shopUserId is null");
            return (Criteria) this;
        }

        public Criteria andShopuseridIsNotNull() {
            addCriterion("shopUserId is not null");
            return (Criteria) this;
        }

        public Criteria andShopuseridEqualTo(String value) {
            addCriterion("shopUserId =", value, "shopuserid");
            return (Criteria) this;
        }

        public Criteria andShopuseridNotEqualTo(String value) {
            addCriterion("shopUserId <>", value, "shopuserid");
            return (Criteria) this;
        }

        public Criteria andShopuseridGreaterThan(String value) {
            addCriterion("shopUserId >", value, "shopuserid");
            return (Criteria) this;
        }

        public Criteria andShopuseridGreaterThanOrEqualTo(String value) {
            addCriterion("shopUserId >=", value, "shopuserid");
            return (Criteria) this;
        }

        public Criteria andShopuseridLessThan(String value) {
            addCriterion("shopUserId <", value, "shopuserid");
            return (Criteria) this;
        }

        public Criteria andShopuseridLessThanOrEqualTo(String value) {
            addCriterion("shopUserId <=", value, "shopuserid");
            return (Criteria) this;
        }

        public Criteria andShopuseridLike(String value) {
            addCriterion("shopUserId like", value, "shopuserid");
            return (Criteria) this;
        }

        public Criteria andShopuseridNotLike(String value) {
            addCriterion("shopUserId not like", value, "shopuserid");
            return (Criteria) this;
        }

        public Criteria andShopuseridIn(List<String> values) {
            addCriterion("shopUserId in", values, "shopuserid");
            return (Criteria) this;
        }

        public Criteria andShopuseridNotIn(List<String> values) {
            addCriterion("shopUserId not in", values, "shopuserid");
            return (Criteria) this;
        }

        public Criteria andShopuseridBetween(String value1, String value2) {
            addCriterion("shopUserId between", value1, value2, "shopuserid");
            return (Criteria) this;
        }

        public Criteria andShopuseridNotBetween(String value1, String value2) {
            addCriterion("shopUserId not between", value1, value2, "shopuserid");
            return (Criteria) this;
        }

        public Criteria andUseridIsNull() {
            addCriterion("userId is null");
            return (Criteria) this;
        }

        public Criteria andUseridIsNotNull() {
            addCriterion("userId is not null");
            return (Criteria) this;
        }

        public Criteria andUseridEqualTo(String value) {
            addCriterion("userId =", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotEqualTo(String value) {
            addCriterion("userId <>", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThan(String value) {
            addCriterion("userId >", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridGreaterThanOrEqualTo(String value) {
            addCriterion("userId >=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThan(String value) {
            addCriterion("userId <", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLessThanOrEqualTo(String value) {
            addCriterion("userId <=", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridLike(String value) {
            addCriterion("userId like", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotLike(String value) {
            addCriterion("userId not like", value, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridIn(List<String> values) {
            addCriterion("userId in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotIn(List<String> values) {
            addCriterion("userId not in", values, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridBetween(String value1, String value2) {
            addCriterion("userId between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andUseridNotBetween(String value1, String value2) {
            addCriterion("userId not between", value1, value2, "userid");
            return (Criteria) this;
        }

        public Criteria andBillcodeIsNull() {
            addCriterion("billCode is null");
            return (Criteria) this;
        }

        public Criteria andBillcodeIsNotNull() {
            addCriterion("billCode is not null");
            return (Criteria) this;
        }

        public Criteria andBillcodeEqualTo(String value) {
            addCriterion("billCode =", value, "billcode");
            return (Criteria) this;
        }

        public Criteria andBillcodeNotEqualTo(String value) {
            addCriterion("billCode <>", value, "billcode");
            return (Criteria) this;
        }

        public Criteria andBillcodeGreaterThan(String value) {
            addCriterion("billCode >", value, "billcode");
            return (Criteria) this;
        }

        public Criteria andBillcodeGreaterThanOrEqualTo(String value) {
            addCriterion("billCode >=", value, "billcode");
            return (Criteria) this;
        }

        public Criteria andBillcodeLessThan(String value) {
            addCriterion("billCode <", value, "billcode");
            return (Criteria) this;
        }

        public Criteria andBillcodeLessThanOrEqualTo(String value) {
            addCriterion("billCode <=", value, "billcode");
            return (Criteria) this;
        }

        public Criteria andBillcodeLike(String value) {
            addCriterion("billCode like", value, "billcode");
            return (Criteria) this;
        }

        public Criteria andBillcodeNotLike(String value) {
            addCriterion("billCode not like", value, "billcode");
            return (Criteria) this;
        }

        public Criteria andBillcodeIn(List<String> values) {
            addCriterion("billCode in", values, "billcode");
            return (Criteria) this;
        }

        public Criteria andBillcodeNotIn(List<String> values) {
            addCriterion("billCode not in", values, "billcode");
            return (Criteria) this;
        }

        public Criteria andBillcodeBetween(String value1, String value2) {
            addCriterion("billCode between", value1, value2, "billcode");
            return (Criteria) this;
        }

        public Criteria andBillcodeNotBetween(String value1, String value2) {
            addCriterion("billCode not between", value1, value2, "billcode");
            return (Criteria) this;
        }

        public Criteria andOrdnoIsNull() {
            addCriterion("ordNo is null");
            return (Criteria) this;
        }

        public Criteria andOrdnoIsNotNull() {
            addCriterion("ordNo is not null");
            return (Criteria) this;
        }

        public Criteria andOrdnoEqualTo(String value) {
            addCriterion("ordNo =", value, "ordno");
            return (Criteria) this;
        }

        public Criteria andOrdnoNotEqualTo(String value) {
            addCriterion("ordNo <>", value, "ordno");
            return (Criteria) this;
        }

        public Criteria andOrdnoGreaterThan(String value) {
            addCriterion("ordNo >", value, "ordno");
            return (Criteria) this;
        }

        public Criteria andOrdnoGreaterThanOrEqualTo(String value) {
            addCriterion("ordNo >=", value, "ordno");
            return (Criteria) this;
        }

        public Criteria andOrdnoLessThan(String value) {
            addCriterion("ordNo <", value, "ordno");
            return (Criteria) this;
        }

        public Criteria andOrdnoLessThanOrEqualTo(String value) {
            addCriterion("ordNo <=", value, "ordno");
            return (Criteria) this;
        }

        public Criteria andOrdnoLike(String value) {
            addCriterion("ordNo like", value, "ordno");
            return (Criteria) this;
        }

        public Criteria andOrdnoNotLike(String value) {
            addCriterion("ordNo not like", value, "ordno");
            return (Criteria) this;
        }

        public Criteria andOrdnoIn(List<String> values) {
            addCriterion("ordNo in", values, "ordno");
            return (Criteria) this;
        }

        public Criteria andOrdnoNotIn(List<String> values) {
            addCriterion("ordNo not in", values, "ordno");
            return (Criteria) this;
        }

        public Criteria andOrdnoBetween(String value1, String value2) {
            addCriterion("ordNo between", value1, value2, "ordno");
            return (Criteria) this;
        }

        public Criteria andOrdnoNotBetween(String value1, String value2) {
            addCriterion("ordNo not between", value1, value2, "ordno");
            return (Criteria) this;
        }

        public Criteria andTradetypeIsNull() {
            addCriterion("tradeType is null");
            return (Criteria) this;
        }

        public Criteria andTradetypeIsNotNull() {
            addCriterion("tradeType is not null");
            return (Criteria) this;
        }

        public Criteria andTradetypeEqualTo(String value) {
            addCriterion("tradeType =", value, "tradetype");
            return (Criteria) this;
        }

        public Criteria andTradetypeNotEqualTo(String value) {
            addCriterion("tradeType <>", value, "tradetype");
            return (Criteria) this;
        }

        public Criteria andTradetypeGreaterThan(String value) {
            addCriterion("tradeType >", value, "tradetype");
            return (Criteria) this;
        }

        public Criteria andTradetypeGreaterThanOrEqualTo(String value) {
            addCriterion("tradeType >=", value, "tradetype");
            return (Criteria) this;
        }

        public Criteria andTradetypeLessThan(String value) {
            addCriterion("tradeType <", value, "tradetype");
            return (Criteria) this;
        }

        public Criteria andTradetypeLessThanOrEqualTo(String value) {
            addCriterion("tradeType <=", value, "tradetype");
            return (Criteria) this;
        }

        public Criteria andTradetypeLike(String value) {
            addCriterion("tradeType like", value, "tradetype");
            return (Criteria) this;
        }

        public Criteria andTradetypeNotLike(String value) {
            addCriterion("tradeType not like", value, "tradetype");
            return (Criteria) this;
        }

        public Criteria andTradetypeIn(List<String> values) {
            addCriterion("tradeType in", values, "tradetype");
            return (Criteria) this;
        }

        public Criteria andTradetypeNotIn(List<String> values) {
            addCriterion("tradeType not in", values, "tradetype");
            return (Criteria) this;
        }

        public Criteria andTradetypeBetween(String value1, String value2) {
            addCriterion("tradeType between", value1, value2, "tradetype");
            return (Criteria) this;
        }

        public Criteria andTradetypeNotBetween(String value1, String value2) {
            addCriterion("tradeType not between", value1, value2, "tradetype");
            return (Criteria) this;
        }

        public Criteria andCointypeIsNull() {
            addCriterion("coinType is null");
            return (Criteria) this;
        }

        public Criteria andCointypeIsNotNull() {
            addCriterion("coinType is not null");
            return (Criteria) this;
        }

        public Criteria andCointypeEqualTo(String value) {
            addCriterion("coinType =", value, "cointype");
            return (Criteria) this;
        }

        public Criteria andCointypeNotEqualTo(String value) {
            addCriterion("coinType <>", value, "cointype");
            return (Criteria) this;
        }

        public Criteria andCointypeGreaterThan(String value) {
            addCriterion("coinType >", value, "cointype");
            return (Criteria) this;
        }

        public Criteria andCointypeGreaterThanOrEqualTo(String value) {
            addCriterion("coinType >=", value, "cointype");
            return (Criteria) this;
        }

        public Criteria andCointypeLessThan(String value) {
            addCriterion("coinType <", value, "cointype");
            return (Criteria) this;
        }

        public Criteria andCointypeLessThanOrEqualTo(String value) {
            addCriterion("coinType <=", value, "cointype");
            return (Criteria) this;
        }

        public Criteria andCointypeLike(String value) {
            addCriterion("coinType like", value, "cointype");
            return (Criteria) this;
        }

        public Criteria andCointypeNotLike(String value) {
            addCriterion("coinType not like", value, "cointype");
            return (Criteria) this;
        }

        public Criteria andCointypeIn(List<String> values) {
            addCriterion("coinType in", values, "cointype");
            return (Criteria) this;
        }

        public Criteria andCointypeNotIn(List<String> values) {
            addCriterion("coinType not in", values, "cointype");
            return (Criteria) this;
        }

        public Criteria andCointypeBetween(String value1, String value2) {
            addCriterion("coinType between", value1, value2, "cointype");
            return (Criteria) this;
        }

        public Criteria andCointypeNotBetween(String value1, String value2) {
            addCriterion("coinType not between", value1, value2, "cointype");
            return (Criteria) this;
        }

        public Criteria andAmountIsNull() {
            addCriterion("amount is null");
            return (Criteria) this;
        }

        public Criteria andAmountIsNotNull() {
            addCriterion("amount is not null");
            return (Criteria) this;
        }

        public Criteria andAmountEqualTo(BigDecimal value) {
            addCriterion("amount =", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotEqualTo(BigDecimal value) {
            addCriterion("amount <>", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThan(BigDecimal value) {
            addCriterion("amount >", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amount >=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThan(BigDecimal value) {
            addCriterion("amount <", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amount <=", value, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountIn(List<BigDecimal> values) {
            addCriterion("amount in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotIn(List<BigDecimal> values) {
            addCriterion("amount not in", values, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount between", value1, value2, "amount");
            return (Criteria) this;
        }

        public Criteria andAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amount not between", value1, value2, "amount");
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

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(Date value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(Date value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(Date value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(Date value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(Date value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(Date value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<Date> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<Date> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(Date value1, Date value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(Date value1, Date value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andLasttimeIsNull() {
            addCriterion("lastTime is null");
            return (Criteria) this;
        }

        public Criteria andLasttimeIsNotNull() {
            addCriterion("lastTime is not null");
            return (Criteria) this;
        }

        public Criteria andLasttimeEqualTo(Date value) {
            addCriterion("lastTime =", value, "lasttime");
            return (Criteria) this;
        }

        public Criteria andLasttimeNotEqualTo(Date value) {
            addCriterion("lastTime <>", value, "lasttime");
            return (Criteria) this;
        }

        public Criteria andLasttimeGreaterThan(Date value) {
            addCriterion("lastTime >", value, "lasttime");
            return (Criteria) this;
        }

        public Criteria andLasttimeGreaterThanOrEqualTo(Date value) {
            addCriterion("lastTime >=", value, "lasttime");
            return (Criteria) this;
        }

        public Criteria andLasttimeLessThan(Date value) {
            addCriterion("lastTime <", value, "lasttime");
            return (Criteria) this;
        }

        public Criteria andLasttimeLessThanOrEqualTo(Date value) {
            addCriterion("lastTime <=", value, "lasttime");
            return (Criteria) this;
        }

        public Criteria andLasttimeIn(List<Date> values) {
            addCriterion("lastTime in", values, "lasttime");
            return (Criteria) this;
        }

        public Criteria andLasttimeNotIn(List<Date> values) {
            addCriterion("lastTime not in", values, "lasttime");
            return (Criteria) this;
        }

        public Criteria andLasttimeBetween(Date value1, Date value2) {
            addCriterion("lastTime between", value1, value2, "lasttime");
            return (Criteria) this;
        }

        public Criteria andLasttimeNotBetween(Date value1, Date value2) {
            addCriterion("lastTime not between", value1, value2, "lasttime");
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