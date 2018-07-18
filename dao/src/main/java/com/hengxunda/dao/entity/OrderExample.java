package com.hengxunda.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderExample() {
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

        public Criteria andTransactionPairIsNull() {
            addCriterion("transaction_pair is null");
            return (Criteria) this;
        }

        public Criteria andTransactionPairIsNotNull() {
            addCriterion("transaction_pair is not null");
            return (Criteria) this;
        }

        public Criteria andTransactionPairEqualTo(String value) {
            addCriterion("transaction_pair =", value, "transactionPair");
            return (Criteria) this;
        }

        public Criteria andTransactionPairNotEqualTo(String value) {
            addCriterion("transaction_pair <>", value, "transactionPair");
            return (Criteria) this;
        }

        public Criteria andTransactionPairGreaterThan(String value) {
            addCriterion("transaction_pair >", value, "transactionPair");
            return (Criteria) this;
        }

        public Criteria andTransactionPairGreaterThanOrEqualTo(String value) {
            addCriterion("transaction_pair >=", value, "transactionPair");
            return (Criteria) this;
        }

        public Criteria andTransactionPairLessThan(String value) {
            addCriterion("transaction_pair <", value, "transactionPair");
            return (Criteria) this;
        }

        public Criteria andTransactionPairLessThanOrEqualTo(String value) {
            addCriterion("transaction_pair <=", value, "transactionPair");
            return (Criteria) this;
        }

        public Criteria andTransactionPairLike(String value) {
            addCriterion("transaction_pair like", value, "transactionPair");
            return (Criteria) this;
        }

        public Criteria andTransactionPairNotLike(String value) {
            addCriterion("transaction_pair not like", value, "transactionPair");
            return (Criteria) this;
        }

        public Criteria andTransactionPairIn(List<String> values) {
            addCriterion("transaction_pair in", values, "transactionPair");
            return (Criteria) this;
        }

        public Criteria andTransactionPairNotIn(List<String> values) {
            addCriterion("transaction_pair not in", values, "transactionPair");
            return (Criteria) this;
        }

        public Criteria andTransactionPairBetween(String value1, String value2) {
            addCriterion("transaction_pair between", value1, value2, "transactionPair");
            return (Criteria) this;
        }

        public Criteria andTransactionPairNotBetween(String value1, String value2) {
            addCriterion("transaction_pair not between", value1, value2, "transactionPair");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNull() {
            addCriterion("order_no is null");
            return (Criteria) this;
        }

        public Criteria andOrderNoIsNotNull() {
            addCriterion("order_no is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNoEqualTo(String value) {
            addCriterion("order_no =", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotEqualTo(String value) {
            addCriterion("order_no <>", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThan(String value) {
            addCriterion("order_no >", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoGreaterThanOrEqualTo(String value) {
            addCriterion("order_no >=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThan(String value) {
            addCriterion("order_no <", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLessThanOrEqualTo(String value) {
            addCriterion("order_no <=", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoLike(String value) {
            addCriterion("order_no like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotLike(String value) {
            addCriterion("order_no not like", value, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoIn(List<String> values) {
            addCriterion("order_no in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotIn(List<String> values) {
            addCriterion("order_no not in", values, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoBetween(String value1, String value2) {
            addCriterion("order_no between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andOrderNoNotBetween(String value1, String value2) {
            addCriterion("order_no not between", value1, value2, "orderNo");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdIsNull() {
            addCriterion("adveru_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdIsNotNull() {
            addCriterion("adveru_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdEqualTo(String value) {
            addCriterion("adveru_user_id =", value, "adveruUserId");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdNotEqualTo(String value) {
            addCriterion("adveru_user_id <>", value, "adveruUserId");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdGreaterThan(String value) {
            addCriterion("adveru_user_id >", value, "adveruUserId");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("adveru_user_id >=", value, "adveruUserId");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdLessThan(String value) {
            addCriterion("adveru_user_id <", value, "adveruUserId");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdLessThanOrEqualTo(String value) {
            addCriterion("adveru_user_id <=", value, "adveruUserId");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdLike(String value) {
            addCriterion("adveru_user_id like", value, "adveruUserId");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdNotLike(String value) {
            addCriterion("adveru_user_id not like", value, "adveruUserId");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdIn(List<String> values) {
            addCriterion("adveru_user_id in", values, "adveruUserId");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdNotIn(List<String> values) {
            addCriterion("adveru_user_id not in", values, "adveruUserId");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdBetween(String value1, String value2) {
            addCriterion("adveru_user_id between", value1, value2, "adveruUserId");
            return (Criteria) this;
        }

        public Criteria andAdveruUserIdNotBetween(String value1, String value2) {
            addCriterion("adveru_user_id not between", value1, value2, "adveruUserId");
            return (Criteria) this;
        }

        public Criteria andAdvertIdIsNull() {
            addCriterion("advert_id is null");
            return (Criteria) this;
        }

        public Criteria andAdvertIdIsNotNull() {
            addCriterion("advert_id is not null");
            return (Criteria) this;
        }

        public Criteria andAdvertIdEqualTo(String value) {
            addCriterion("advert_id =", value, "advertId");
            return (Criteria) this;
        }

        public Criteria andAdvertIdNotEqualTo(String value) {
            addCriterion("advert_id <>", value, "advertId");
            return (Criteria) this;
        }

        public Criteria andAdvertIdGreaterThan(String value) {
            addCriterion("advert_id >", value, "advertId");
            return (Criteria) this;
        }

        public Criteria andAdvertIdGreaterThanOrEqualTo(String value) {
            addCriterion("advert_id >=", value, "advertId");
            return (Criteria) this;
        }

        public Criteria andAdvertIdLessThan(String value) {
            addCriterion("advert_id <", value, "advertId");
            return (Criteria) this;
        }

        public Criteria andAdvertIdLessThanOrEqualTo(String value) {
            addCriterion("advert_id <=", value, "advertId");
            return (Criteria) this;
        }

        public Criteria andAdvertIdLike(String value) {
            addCriterion("advert_id like", value, "advertId");
            return (Criteria) this;
        }

        public Criteria andAdvertIdNotLike(String value) {
            addCriterion("advert_id not like", value, "advertId");
            return (Criteria) this;
        }

        public Criteria andAdvertIdIn(List<String> values) {
            addCriterion("advert_id in", values, "advertId");
            return (Criteria) this;
        }

        public Criteria andAdvertIdNotIn(List<String> values) {
            addCriterion("advert_id not in", values, "advertId");
            return (Criteria) this;
        }

        public Criteria andAdvertIdBetween(String value1, String value2) {
            addCriterion("advert_id between", value1, value2, "advertId");
            return (Criteria) this;
        }

        public Criteria andAdvertIdNotBetween(String value1, String value2) {
            addCriterion("advert_id not between", value1, value2, "advertId");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIsNull() {
            addCriterion("unit_price is null");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIsNotNull() {
            addCriterion("unit_price is not null");
            return (Criteria) this;
        }

        public Criteria andUnitPriceEqualTo(BigDecimal value) {
            addCriterion("unit_price =", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotEqualTo(BigDecimal value) {
            addCriterion("unit_price <>", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceGreaterThan(BigDecimal value) {
            addCriterion("unit_price >", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("unit_price >=", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceLessThan(BigDecimal value) {
            addCriterion("unit_price <", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("unit_price <=", value, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceIn(List<BigDecimal> values) {
            addCriterion("unit_price in", values, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotIn(List<BigDecimal> values) {
            addCriterion("unit_price not in", values, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unit_price between", value1, value2, "unitPrice");
            return (Criteria) this;
        }

        public Criteria andUnitPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("unit_price not between", value1, value2, "unitPrice");
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

        public Criteria andMoneyIsNull() {
            addCriterion("money is null");
            return (Criteria) this;
        }

        public Criteria andMoneyIsNotNull() {
            addCriterion("money is not null");
            return (Criteria) this;
        }

        public Criteria andMoneyEqualTo(BigDecimal value) {
            addCriterion("money =", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotEqualTo(BigDecimal value) {
            addCriterion("money <>", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyGreaterThan(BigDecimal value) {
            addCriterion("money >", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("money >=", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyLessThan(BigDecimal value) {
            addCriterion("money <", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("money <=", value, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyIn(List<BigDecimal> values) {
            addCriterion("money in", values, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotIn(List<BigDecimal> values) {
            addCriterion("money not in", values, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("money between", value1, value2, "money");
            return (Criteria) this;
        }

        public Criteria andMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("money not between", value1, value2, "money");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNull() {
            addCriterion("tax_rate is null");
            return (Criteria) this;
        }

        public Criteria andTaxRateIsNotNull() {
            addCriterion("tax_rate is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRateEqualTo(String value) {
            addCriterion("tax_rate =", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotEqualTo(String value) {
            addCriterion("tax_rate <>", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThan(String value) {
            addCriterion("tax_rate >", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateGreaterThanOrEqualTo(String value) {
            addCriterion("tax_rate >=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThan(String value) {
            addCriterion("tax_rate <", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLessThanOrEqualTo(String value) {
            addCriterion("tax_rate <=", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateLike(String value) {
            addCriterion("tax_rate like", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotLike(String value) {
            addCriterion("tax_rate not like", value, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateIn(List<String> values) {
            addCriterion("tax_rate in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotIn(List<String> values) {
            addCriterion("tax_rate not in", values, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateBetween(String value1, String value2) {
            addCriterion("tax_rate between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andTaxRateNotBetween(String value1, String value2) {
            addCriterion("tax_rate not between", value1, value2, "taxRate");
            return (Criteria) this;
        }

        public Criteria andCancelStatusIsNull() {
            addCriterion("cancel_status is null");
            return (Criteria) this;
        }

        public Criteria andCancelStatusIsNotNull() {
            addCriterion("cancel_status is not null");
            return (Criteria) this;
        }

        public Criteria andCancelStatusEqualTo(Integer value) {
            addCriterion("cancel_status =", value, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusNotEqualTo(Integer value) {
            addCriterion("cancel_status <>", value, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusGreaterThan(Integer value) {
            addCriterion("cancel_status >", value, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("cancel_status >=", value, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusLessThan(Integer value) {
            addCriterion("cancel_status <", value, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusLessThanOrEqualTo(Integer value) {
            addCriterion("cancel_status <=", value, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusIn(List<Integer> values) {
            addCriterion("cancel_status in", values, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusNotIn(List<Integer> values) {
            addCriterion("cancel_status not in", values, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusBetween(Integer value1, Integer value2) {
            addCriterion("cancel_status between", value1, value2, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andCancelStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("cancel_status not between", value1, value2, "cancelStatus");
            return (Criteria) this;
        }

        public Criteria andAppealStatusIsNull() {
            addCriterion("appeal_status is null");
            return (Criteria) this;
        }

        public Criteria andAppealStatusIsNotNull() {
            addCriterion("appeal_status is not null");
            return (Criteria) this;
        }

        public Criteria andAppealStatusEqualTo(Integer value) {
            addCriterion("appeal_status =", value, "appealStatus");
            return (Criteria) this;
        }

        public Criteria andAppealStatusNotEqualTo(Integer value) {
            addCriterion("appeal_status <>", value, "appealStatus");
            return (Criteria) this;
        }

        public Criteria andAppealStatusGreaterThan(Integer value) {
            addCriterion("appeal_status >", value, "appealStatus");
            return (Criteria) this;
        }

        public Criteria andAppealStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("appeal_status >=", value, "appealStatus");
            return (Criteria) this;
        }

        public Criteria andAppealStatusLessThan(Integer value) {
            addCriterion("appeal_status <", value, "appealStatus");
            return (Criteria) this;
        }

        public Criteria andAppealStatusLessThanOrEqualTo(Integer value) {
            addCriterion("appeal_status <=", value, "appealStatus");
            return (Criteria) this;
        }

        public Criteria andAppealStatusIn(List<Integer> values) {
            addCriterion("appeal_status in", values, "appealStatus");
            return (Criteria) this;
        }

        public Criteria andAppealStatusNotIn(List<Integer> values) {
            addCriterion("appeal_status not in", values, "appealStatus");
            return (Criteria) this;
        }

        public Criteria andAppealStatusBetween(Integer value1, Integer value2) {
            addCriterion("appeal_status between", value1, value2, "appealStatus");
            return (Criteria) this;
        }

        public Criteria andAppealStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("appeal_status not between", value1, value2, "appealStatus");
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

        public Criteria andRoleIsNull() {
            addCriterion("role is null");
            return (Criteria) this;
        }

        public Criteria andRoleIsNotNull() {
            addCriterion("role is not null");
            return (Criteria) this;
        }

        public Criteria andRoleEqualTo(Integer value) {
            addCriterion("role =", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotEqualTo(Integer value) {
            addCriterion("role <>", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThan(Integer value) {
            addCriterion("role >", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleGreaterThanOrEqualTo(Integer value) {
            addCriterion("role >=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThan(Integer value) {
            addCriterion("role <", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleLessThanOrEqualTo(Integer value) {
            addCriterion("role <=", value, "role");
            return (Criteria) this;
        }

        public Criteria andRoleIn(List<Integer> values) {
            addCriterion("role in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotIn(List<Integer> values) {
            addCriterion("role not in", values, "role");
            return (Criteria) this;
        }

        public Criteria andRoleBetween(Integer value1, Integer value2) {
            addCriterion("role between", value1, value2, "role");
            return (Criteria) this;
        }

        public Criteria andRoleNotBetween(Integer value1, Integer value2) {
            addCriterion("role not between", value1, value2, "role");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIsNull() {
            addCriterion("confirm_time is null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIsNotNull() {
            addCriterion("confirm_time is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeEqualTo(Date value) {
            addCriterion("confirm_time =", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotEqualTo(Date value) {
            addCriterion("confirm_time <>", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeGreaterThan(Date value) {
            addCriterion("confirm_time >", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("confirm_time >=", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeLessThan(Date value) {
            addCriterion("confirm_time <", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeLessThanOrEqualTo(Date value) {
            addCriterion("confirm_time <=", value, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeIn(List<Date> values) {
            addCriterion("confirm_time in", values, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotIn(List<Date> values) {
            addCriterion("confirm_time not in", values, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeBetween(Date value1, Date value2) {
            addCriterion("confirm_time between", value1, value2, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTimeNotBetween(Date value1, Date value2) {
            addCriterion("confirm_time not between", value1, value2, "confirmTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andAppealUserIsNull() {
            addCriterion("appeal_user is null");
            return (Criteria) this;
        }

        public Criteria andAppealUserIsNotNull() {
            addCriterion("appeal_user is not null");
            return (Criteria) this;
        }

        public Criteria andAppealUserEqualTo(String value) {
            addCriterion("appeal_user =", value, "appealUser");
            return (Criteria) this;
        }

        public Criteria andAppealUserNotEqualTo(String value) {
            addCriterion("appeal_user <>", value, "appealUser");
            return (Criteria) this;
        }

        public Criteria andAppealUserGreaterThan(String value) {
            addCriterion("appeal_user >", value, "appealUser");
            return (Criteria) this;
        }

        public Criteria andAppealUserGreaterThanOrEqualTo(String value) {
            addCriterion("appeal_user >=", value, "appealUser");
            return (Criteria) this;
        }

        public Criteria andAppealUserLessThan(String value) {
            addCriterion("appeal_user <", value, "appealUser");
            return (Criteria) this;
        }

        public Criteria andAppealUserLessThanOrEqualTo(String value) {
            addCriterion("appeal_user <=", value, "appealUser");
            return (Criteria) this;
        }

        public Criteria andAppealUserLike(String value) {
            addCriterion("appeal_user like", value, "appealUser");
            return (Criteria) this;
        }

        public Criteria andAppealUserNotLike(String value) {
            addCriterion("appeal_user not like", value, "appealUser");
            return (Criteria) this;
        }

        public Criteria andAppealUserIn(List<String> values) {
            addCriterion("appeal_user in", values, "appealUser");
            return (Criteria) this;
        }

        public Criteria andAppealUserNotIn(List<String> values) {
            addCriterion("appeal_user not in", values, "appealUser");
            return (Criteria) this;
        }

        public Criteria andAppealUserBetween(String value1, String value2) {
            addCriterion("appeal_user between", value1, value2, "appealUser");
            return (Criteria) this;
        }

        public Criteria andAppealUserNotBetween(String value1, String value2) {
            addCriterion("appeal_user not between", value1, value2, "appealUser");
            return (Criteria) this;
        }

        public Criteria andAppealTimeIsNull() {
            addCriterion("appeal_time is null");
            return (Criteria) this;
        }

        public Criteria andAppealTimeIsNotNull() {
            addCriterion("appeal_time is not null");
            return (Criteria) this;
        }

        public Criteria andAppealTimeEqualTo(Date value) {
            addCriterion("appeal_time =", value, "appealTime");
            return (Criteria) this;
        }

        public Criteria andAppealTimeNotEqualTo(Date value) {
            addCriterion("appeal_time <>", value, "appealTime");
            return (Criteria) this;
        }

        public Criteria andAppealTimeGreaterThan(Date value) {
            addCriterion("appeal_time >", value, "appealTime");
            return (Criteria) this;
        }

        public Criteria andAppealTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("appeal_time >=", value, "appealTime");
            return (Criteria) this;
        }

        public Criteria andAppealTimeLessThan(Date value) {
            addCriterion("appeal_time <", value, "appealTime");
            return (Criteria) this;
        }

        public Criteria andAppealTimeLessThanOrEqualTo(Date value) {
            addCriterion("appeal_time <=", value, "appealTime");
            return (Criteria) this;
        }

        public Criteria andAppealTimeIn(List<Date> values) {
            addCriterion("appeal_time in", values, "appealTime");
            return (Criteria) this;
        }

        public Criteria andAppealTimeNotIn(List<Date> values) {
            addCriterion("appeal_time not in", values, "appealTime");
            return (Criteria) this;
        }

        public Criteria andAppealTimeBetween(Date value1, Date value2) {
            addCriterion("appeal_time between", value1, value2, "appealTime");
            return (Criteria) this;
        }

        public Criteria andAppealTimeNotBetween(Date value1, Date value2) {
            addCriterion("appeal_time not between", value1, value2, "appealTime");
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