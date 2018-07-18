package com.hengxunda.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YinshangIsPayExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public YinshangIsPayExample() {
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

        public Criteria andAlipayIsNull() {
            addCriterion("Alipay is null");
            return (Criteria) this;
        }

        public Criteria andAlipayIsNotNull() {
            addCriterion("Alipay is not null");
            return (Criteria) this;
        }

        public Criteria andAlipayEqualTo(Integer value) {
            addCriterion("Alipay =", value, "alipay");
            return (Criteria) this;
        }

        public Criteria andAlipayNotEqualTo(Integer value) {
            addCriterion("Alipay <>", value, "alipay");
            return (Criteria) this;
        }

        public Criteria andAlipayGreaterThan(Integer value) {
            addCriterion("Alipay >", value, "alipay");
            return (Criteria) this;
        }

        public Criteria andAlipayGreaterThanOrEqualTo(Integer value) {
            addCriterion("Alipay >=", value, "alipay");
            return (Criteria) this;
        }

        public Criteria andAlipayLessThan(Integer value) {
            addCriterion("Alipay <", value, "alipay");
            return (Criteria) this;
        }

        public Criteria andAlipayLessThanOrEqualTo(Integer value) {
            addCriterion("Alipay <=", value, "alipay");
            return (Criteria) this;
        }

        public Criteria andAlipayIn(List<Integer> values) {
            addCriterion("Alipay in", values, "alipay");
            return (Criteria) this;
        }

        public Criteria andAlipayNotIn(List<Integer> values) {
            addCriterion("Alipay not in", values, "alipay");
            return (Criteria) this;
        }

        public Criteria andAlipayBetween(Integer value1, Integer value2) {
            addCriterion("Alipay between", value1, value2, "alipay");
            return (Criteria) this;
        }

        public Criteria andAlipayNotBetween(Integer value1, Integer value2) {
            addCriterion("Alipay not between", value1, value2, "alipay");
            return (Criteria) this;
        }

        public Criteria andPaypalIsNull() {
            addCriterion("PayPal is null");
            return (Criteria) this;
        }

        public Criteria andPaypalIsNotNull() {
            addCriterion("PayPal is not null");
            return (Criteria) this;
        }

        public Criteria andPaypalEqualTo(Integer value) {
            addCriterion("PayPal =", value, "paypal");
            return (Criteria) this;
        }

        public Criteria andPaypalNotEqualTo(Integer value) {
            addCriterion("PayPal <>", value, "paypal");
            return (Criteria) this;
        }

        public Criteria andPaypalGreaterThan(Integer value) {
            addCriterion("PayPal >", value, "paypal");
            return (Criteria) this;
        }

        public Criteria andPaypalGreaterThanOrEqualTo(Integer value) {
            addCriterion("PayPal >=", value, "paypal");
            return (Criteria) this;
        }

        public Criteria andPaypalLessThan(Integer value) {
            addCriterion("PayPal <", value, "paypal");
            return (Criteria) this;
        }

        public Criteria andPaypalLessThanOrEqualTo(Integer value) {
            addCriterion("PayPal <=", value, "paypal");
            return (Criteria) this;
        }

        public Criteria andPaypalIn(List<Integer> values) {
            addCriterion("PayPal in", values, "paypal");
            return (Criteria) this;
        }

        public Criteria andPaypalNotIn(List<Integer> values) {
            addCriterion("PayPal not in", values, "paypal");
            return (Criteria) this;
        }

        public Criteria andPaypalBetween(Integer value1, Integer value2) {
            addCriterion("PayPal between", value1, value2, "paypal");
            return (Criteria) this;
        }

        public Criteria andPaypalNotBetween(Integer value1, Integer value2) {
            addCriterion("PayPal not between", value1, value2, "paypal");
            return (Criteria) this;
        }

        public Criteria andXilianIsNull() {
            addCriterion("Xilian is null");
            return (Criteria) this;
        }

        public Criteria andXilianIsNotNull() {
            addCriterion("Xilian is not null");
            return (Criteria) this;
        }

        public Criteria andXilianEqualTo(Integer value) {
            addCriterion("Xilian =", value, "xilian");
            return (Criteria) this;
        }

        public Criteria andXilianNotEqualTo(Integer value) {
            addCriterion("Xilian <>", value, "xilian");
            return (Criteria) this;
        }

        public Criteria andXilianGreaterThan(Integer value) {
            addCriterion("Xilian >", value, "xilian");
            return (Criteria) this;
        }

        public Criteria andXilianGreaterThanOrEqualTo(Integer value) {
            addCriterion("Xilian >=", value, "xilian");
            return (Criteria) this;
        }

        public Criteria andXilianLessThan(Integer value) {
            addCriterion("Xilian <", value, "xilian");
            return (Criteria) this;
        }

        public Criteria andXilianLessThanOrEqualTo(Integer value) {
            addCriterion("Xilian <=", value, "xilian");
            return (Criteria) this;
        }

        public Criteria andXilianIn(List<Integer> values) {
            addCriterion("Xilian in", values, "xilian");
            return (Criteria) this;
        }

        public Criteria andXilianNotIn(List<Integer> values) {
            addCriterion("Xilian not in", values, "xilian");
            return (Criteria) this;
        }

        public Criteria andXilianBetween(Integer value1, Integer value2) {
            addCriterion("Xilian between", value1, value2, "xilian");
            return (Criteria) this;
        }

        public Criteria andXilianNotBetween(Integer value1, Integer value2) {
            addCriterion("Xilian not between", value1, value2, "xilian");
            return (Criteria) this;
        }

        public Criteria andSwiftIsNull() {
            addCriterion("SWIFT is null");
            return (Criteria) this;
        }

        public Criteria andSwiftIsNotNull() {
            addCriterion("SWIFT is not null");
            return (Criteria) this;
        }

        public Criteria andSwiftEqualTo(Integer value) {
            addCriterion("SWIFT =", value, "swift");
            return (Criteria) this;
        }

        public Criteria andSwiftNotEqualTo(Integer value) {
            addCriterion("SWIFT <>", value, "swift");
            return (Criteria) this;
        }

        public Criteria andSwiftGreaterThan(Integer value) {
            addCriterion("SWIFT >", value, "swift");
            return (Criteria) this;
        }

        public Criteria andSwiftGreaterThanOrEqualTo(Integer value) {
            addCriterion("SWIFT >=", value, "swift");
            return (Criteria) this;
        }

        public Criteria andSwiftLessThan(Integer value) {
            addCriterion("SWIFT <", value, "swift");
            return (Criteria) this;
        }

        public Criteria andSwiftLessThanOrEqualTo(Integer value) {
            addCriterion("SWIFT <=", value, "swift");
            return (Criteria) this;
        }

        public Criteria andSwiftIn(List<Integer> values) {
            addCriterion("SWIFT in", values, "swift");
            return (Criteria) this;
        }

        public Criteria andSwiftNotIn(List<Integer> values) {
            addCriterion("SWIFT not in", values, "swift");
            return (Criteria) this;
        }

        public Criteria andSwiftBetween(Integer value1, Integer value2) {
            addCriterion("SWIFT between", value1, value2, "swift");
            return (Criteria) this;
        }

        public Criteria andSwiftNotBetween(Integer value1, Integer value2) {
            addCriterion("SWIFT not between", value1, value2, "swift");
            return (Criteria) this;
        }

        public Criteria andWeixinIsNull() {
            addCriterion("Weixin is null");
            return (Criteria) this;
        }

        public Criteria andWeixinIsNotNull() {
            addCriterion("Weixin is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinEqualTo(Integer value) {
            addCriterion("Weixin =", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotEqualTo(Integer value) {
            addCriterion("Weixin <>", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinGreaterThan(Integer value) {
            addCriterion("Weixin >", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinGreaterThanOrEqualTo(Integer value) {
            addCriterion("Weixin >=", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLessThan(Integer value) {
            addCriterion("Weixin <", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinLessThanOrEqualTo(Integer value) {
            addCriterion("Weixin <=", value, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinIn(List<Integer> values) {
            addCriterion("Weixin in", values, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotIn(List<Integer> values) {
            addCriterion("Weixin not in", values, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinBetween(Integer value1, Integer value2) {
            addCriterion("Weixin between", value1, value2, "weixin");
            return (Criteria) this;
        }

        public Criteria andWeixinNotBetween(Integer value1, Integer value2) {
            addCriterion("Weixin not between", value1, value2, "weixin");
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

        public Criteria andUpateUserIsNull() {
            addCriterion("upate_user is null");
            return (Criteria) this;
        }

        public Criteria andUpateUserIsNotNull() {
            addCriterion("upate_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpateUserEqualTo(String value) {
            addCriterion("upate_user =", value, "upateUser");
            return (Criteria) this;
        }

        public Criteria andUpateUserNotEqualTo(String value) {
            addCriterion("upate_user <>", value, "upateUser");
            return (Criteria) this;
        }

        public Criteria andUpateUserGreaterThan(String value) {
            addCriterion("upate_user >", value, "upateUser");
            return (Criteria) this;
        }

        public Criteria andUpateUserGreaterThanOrEqualTo(String value) {
            addCriterion("upate_user >=", value, "upateUser");
            return (Criteria) this;
        }

        public Criteria andUpateUserLessThan(String value) {
            addCriterion("upate_user <", value, "upateUser");
            return (Criteria) this;
        }

        public Criteria andUpateUserLessThanOrEqualTo(String value) {
            addCriterion("upate_user <=", value, "upateUser");
            return (Criteria) this;
        }

        public Criteria andUpateUserLike(String value) {
            addCriterion("upate_user like", value, "upateUser");
            return (Criteria) this;
        }

        public Criteria andUpateUserNotLike(String value) {
            addCriterion("upate_user not like", value, "upateUser");
            return (Criteria) this;
        }

        public Criteria andUpateUserIn(List<String> values) {
            addCriterion("upate_user in", values, "upateUser");
            return (Criteria) this;
        }

        public Criteria andUpateUserNotIn(List<String> values) {
            addCriterion("upate_user not in", values, "upateUser");
            return (Criteria) this;
        }

        public Criteria andUpateUserBetween(String value1, String value2) {
            addCriterion("upate_user between", value1, value2, "upateUser");
            return (Criteria) this;
        }

        public Criteria andUpateUserNotBetween(String value1, String value2) {
            addCriterion("upate_user not between", value1, value2, "upateUser");
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