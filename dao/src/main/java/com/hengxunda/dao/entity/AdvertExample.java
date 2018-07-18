package com.hengxunda.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdvertExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AdvertExample() {
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

        public Criteria andStockIsNull() {
            addCriterion("stock is null");
            return (Criteria) this;
        }

        public Criteria andStockIsNotNull() {
            addCriterion("stock is not null");
            return (Criteria) this;
        }

        public Criteria andStockEqualTo(BigDecimal value) {
            addCriterion("stock =", value, "stock");
            return (Criteria) this;
        }

        public Criteria andStockNotEqualTo(BigDecimal value) {
            addCriterion("stock <>", value, "stock");
            return (Criteria) this;
        }

        public Criteria andStockGreaterThan(BigDecimal value) {
            addCriterion("stock >", value, "stock");
            return (Criteria) this;
        }

        public Criteria andStockGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("stock >=", value, "stock");
            return (Criteria) this;
        }

        public Criteria andStockLessThan(BigDecimal value) {
            addCriterion("stock <", value, "stock");
            return (Criteria) this;
        }

        public Criteria andStockLessThanOrEqualTo(BigDecimal value) {
            addCriterion("stock <=", value, "stock");
            return (Criteria) this;
        }

        public Criteria andStockIn(List<BigDecimal> values) {
            addCriterion("stock in", values, "stock");
            return (Criteria) this;
        }

        public Criteria andStockNotIn(List<BigDecimal> values) {
            addCriterion("stock not in", values, "stock");
            return (Criteria) this;
        }

        public Criteria andStockBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("stock between", value1, value2, "stock");
            return (Criteria) this;
        }

        public Criteria andStockNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("stock not between", value1, value2, "stock");
            return (Criteria) this;
        }

        public Criteria andEnableStockIsNull() {
            addCriterion("enable_stock is null");
            return (Criteria) this;
        }

        public Criteria andEnableStockIsNotNull() {
            addCriterion("enable_stock is not null");
            return (Criteria) this;
        }

        public Criteria andEnableStockEqualTo(BigDecimal value) {
            addCriterion("enable_stock =", value, "enableStock");
            return (Criteria) this;
        }

        public Criteria andEnableStockNotEqualTo(BigDecimal value) {
            addCriterion("enable_stock <>", value, "enableStock");
            return (Criteria) this;
        }

        public Criteria andEnableStockGreaterThan(BigDecimal value) {
            addCriterion("enable_stock >", value, "enableStock");
            return (Criteria) this;
        }

        public Criteria andEnableStockGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("enable_stock >=", value, "enableStock");
            return (Criteria) this;
        }

        public Criteria andEnableStockLessThan(BigDecimal value) {
            addCriterion("enable_stock <", value, "enableStock");
            return (Criteria) this;
        }

        public Criteria andEnableStockLessThanOrEqualTo(BigDecimal value) {
            addCriterion("enable_stock <=", value, "enableStock");
            return (Criteria) this;
        }

        public Criteria andEnableStockIn(List<BigDecimal> values) {
            addCriterion("enable_stock in", values, "enableStock");
            return (Criteria) this;
        }

        public Criteria andEnableStockNotIn(List<BigDecimal> values) {
            addCriterion("enable_stock not in", values, "enableStock");
            return (Criteria) this;
        }

        public Criteria andEnableStockBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("enable_stock between", value1, value2, "enableStock");
            return (Criteria) this;
        }

        public Criteria andEnableStockNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("enable_stock not between", value1, value2, "enableStock");
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

        public Criteria andIsLimitIsNull() {
            addCriterion("is_limit is null");
            return (Criteria) this;
        }

        public Criteria andIsLimitIsNotNull() {
            addCriterion("is_limit is not null");
            return (Criteria) this;
        }

        public Criteria andIsLimitEqualTo(Integer value) {
            addCriterion("is_limit =", value, "isLimit");
            return (Criteria) this;
        }

        public Criteria andIsLimitNotEqualTo(Integer value) {
            addCriterion("is_limit <>", value, "isLimit");
            return (Criteria) this;
        }

        public Criteria andIsLimitGreaterThan(Integer value) {
            addCriterion("is_limit >", value, "isLimit");
            return (Criteria) this;
        }

        public Criteria andIsLimitGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_limit >=", value, "isLimit");
            return (Criteria) this;
        }

        public Criteria andIsLimitLessThan(Integer value) {
            addCriterion("is_limit <", value, "isLimit");
            return (Criteria) this;
        }

        public Criteria andIsLimitLessThanOrEqualTo(Integer value) {
            addCriterion("is_limit <=", value, "isLimit");
            return (Criteria) this;
        }

        public Criteria andIsLimitIn(List<Integer> values) {
            addCriterion("is_limit in", values, "isLimit");
            return (Criteria) this;
        }

        public Criteria andIsLimitNotIn(List<Integer> values) {
            addCriterion("is_limit not in", values, "isLimit");
            return (Criteria) this;
        }

        public Criteria andIsLimitBetween(Integer value1, Integer value2) {
            addCriterion("is_limit between", value1, value2, "isLimit");
            return (Criteria) this;
        }

        public Criteria andIsLimitNotBetween(Integer value1, Integer value2) {
            addCriterion("is_limit not between", value1, value2, "isLimit");
            return (Criteria) this;
        }

        public Criteria andMinValueIsNull() {
            addCriterion("min_value is null");
            return (Criteria) this;
        }

        public Criteria andMinValueIsNotNull() {
            addCriterion("min_value is not null");
            return (Criteria) this;
        }

        public Criteria andMinValueEqualTo(BigDecimal value) {
            addCriterion("min_value =", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueNotEqualTo(BigDecimal value) {
            addCriterion("min_value <>", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueGreaterThan(BigDecimal value) {
            addCriterion("min_value >", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("min_value >=", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueLessThan(BigDecimal value) {
            addCriterion("min_value <", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("min_value <=", value, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueIn(List<BigDecimal> values) {
            addCriterion("min_value in", values, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueNotIn(List<BigDecimal> values) {
            addCriterion("min_value not in", values, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_value between", value1, value2, "minValue");
            return (Criteria) this;
        }

        public Criteria andMinValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("min_value not between", value1, value2, "minValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueIsNull() {
            addCriterion("max_value is null");
            return (Criteria) this;
        }

        public Criteria andMaxValueIsNotNull() {
            addCriterion("max_value is not null");
            return (Criteria) this;
        }

        public Criteria andMaxValueEqualTo(BigDecimal value) {
            addCriterion("max_value =", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueNotEqualTo(BigDecimal value) {
            addCriterion("max_value <>", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueGreaterThan(BigDecimal value) {
            addCriterion("max_value >", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_value >=", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueLessThan(BigDecimal value) {
            addCriterion("max_value <", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_value <=", value, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueIn(List<BigDecimal> values) {
            addCriterion("max_value in", values, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueNotIn(List<BigDecimal> values) {
            addCriterion("max_value not in", values, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_value between", value1, value2, "maxValue");
            return (Criteria) this;
        }

        public Criteria andMaxValueNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_value not between", value1, value2, "maxValue");
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

        public Criteria andCnyIsNull() {
            addCriterion("CNY is null");
            return (Criteria) this;
        }

        public Criteria andCnyIsNotNull() {
            addCriterion("CNY is not null");
            return (Criteria) this;
        }

        public Criteria andCnyEqualTo(Integer value) {
            addCriterion("CNY =", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyNotEqualTo(Integer value) {
            addCriterion("CNY <>", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyGreaterThan(Integer value) {
            addCriterion("CNY >", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyGreaterThanOrEqualTo(Integer value) {
            addCriterion("CNY >=", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyLessThan(Integer value) {
            addCriterion("CNY <", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyLessThanOrEqualTo(Integer value) {
            addCriterion("CNY <=", value, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyIn(List<Integer> values) {
            addCriterion("CNY in", values, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyNotIn(List<Integer> values) {
            addCriterion("CNY not in", values, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyBetween(Integer value1, Integer value2) {
            addCriterion("CNY between", value1, value2, "cny");
            return (Criteria) this;
        }

        public Criteria andCnyNotBetween(Integer value1, Integer value2) {
            addCriterion("CNY not between", value1, value2, "cny");
            return (Criteria) this;
        }

        public Criteria andUsdIsNull() {
            addCriterion("USD is null");
            return (Criteria) this;
        }

        public Criteria andUsdIsNotNull() {
            addCriterion("USD is not null");
            return (Criteria) this;
        }

        public Criteria andUsdEqualTo(Integer value) {
            addCriterion("USD =", value, "usd");
            return (Criteria) this;
        }

        public Criteria andUsdNotEqualTo(Integer value) {
            addCriterion("USD <>", value, "usd");
            return (Criteria) this;
        }

        public Criteria andUsdGreaterThan(Integer value) {
            addCriterion("USD >", value, "usd");
            return (Criteria) this;
        }

        public Criteria andUsdGreaterThanOrEqualTo(Integer value) {
            addCriterion("USD >=", value, "usd");
            return (Criteria) this;
        }

        public Criteria andUsdLessThan(Integer value) {
            addCriterion("USD <", value, "usd");
            return (Criteria) this;
        }

        public Criteria andUsdLessThanOrEqualTo(Integer value) {
            addCriterion("USD <=", value, "usd");
            return (Criteria) this;
        }

        public Criteria andUsdIn(List<Integer> values) {
            addCriterion("USD in", values, "usd");
            return (Criteria) this;
        }

        public Criteria andUsdNotIn(List<Integer> values) {
            addCriterion("USD not in", values, "usd");
            return (Criteria) this;
        }

        public Criteria andUsdBetween(Integer value1, Integer value2) {
            addCriterion("USD between", value1, value2, "usd");
            return (Criteria) this;
        }

        public Criteria andUsdNotBetween(Integer value1, Integer value2) {
            addCriterion("USD not between", value1, value2, "usd");
            return (Criteria) this;
        }

        public Criteria andEurIsNull() {
            addCriterion("EUR is null");
            return (Criteria) this;
        }

        public Criteria andEurIsNotNull() {
            addCriterion("EUR is not null");
            return (Criteria) this;
        }

        public Criteria andEurEqualTo(Integer value) {
            addCriterion("EUR =", value, "eur");
            return (Criteria) this;
        }

        public Criteria andEurNotEqualTo(Integer value) {
            addCriterion("EUR <>", value, "eur");
            return (Criteria) this;
        }

        public Criteria andEurGreaterThan(Integer value) {
            addCriterion("EUR >", value, "eur");
            return (Criteria) this;
        }

        public Criteria andEurGreaterThanOrEqualTo(Integer value) {
            addCriterion("EUR >=", value, "eur");
            return (Criteria) this;
        }

        public Criteria andEurLessThan(Integer value) {
            addCriterion("EUR <", value, "eur");
            return (Criteria) this;
        }

        public Criteria andEurLessThanOrEqualTo(Integer value) {
            addCriterion("EUR <=", value, "eur");
            return (Criteria) this;
        }

        public Criteria andEurIn(List<Integer> values) {
            addCriterion("EUR in", values, "eur");
            return (Criteria) this;
        }

        public Criteria andEurNotIn(List<Integer> values) {
            addCriterion("EUR not in", values, "eur");
            return (Criteria) this;
        }

        public Criteria andEurBetween(Integer value1, Integer value2) {
            addCriterion("EUR between", value1, value2, "eur");
            return (Criteria) this;
        }

        public Criteria andEurNotBetween(Integer value1, Integer value2) {
            addCriterion("EUR not between", value1, value2, "eur");
            return (Criteria) this;
        }

        public Criteria andHkdIsNull() {
            addCriterion("HKD is null");
            return (Criteria) this;
        }

        public Criteria andHkdIsNotNull() {
            addCriterion("HKD is not null");
            return (Criteria) this;
        }

        public Criteria andHkdEqualTo(Integer value) {
            addCriterion("HKD =", value, "hkd");
            return (Criteria) this;
        }

        public Criteria andHkdNotEqualTo(Integer value) {
            addCriterion("HKD <>", value, "hkd");
            return (Criteria) this;
        }

        public Criteria andHkdGreaterThan(Integer value) {
            addCriterion("HKD >", value, "hkd");
            return (Criteria) this;
        }

        public Criteria andHkdGreaterThanOrEqualTo(Integer value) {
            addCriterion("HKD >=", value, "hkd");
            return (Criteria) this;
        }

        public Criteria andHkdLessThan(Integer value) {
            addCriterion("HKD <", value, "hkd");
            return (Criteria) this;
        }

        public Criteria andHkdLessThanOrEqualTo(Integer value) {
            addCriterion("HKD <=", value, "hkd");
            return (Criteria) this;
        }

        public Criteria andHkdIn(List<Integer> values) {
            addCriterion("HKD in", values, "hkd");
            return (Criteria) this;
        }

        public Criteria andHkdNotIn(List<Integer> values) {
            addCriterion("HKD not in", values, "hkd");
            return (Criteria) this;
        }

        public Criteria andHkdBetween(Integer value1, Integer value2) {
            addCriterion("HKD between", value1, value2, "hkd");
            return (Criteria) this;
        }

        public Criteria andHkdNotBetween(Integer value1, Integer value2) {
            addCriterion("HKD not between", value1, value2, "hkd");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
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