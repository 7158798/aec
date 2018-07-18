package com.hengxunda.dao.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MscRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MscRecordExample() {
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

        public Criteria andWalletRecordIdIsNull() {
            addCriterion("wallet_record_id is null");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdIsNotNull() {
            addCriterion("wallet_record_id is not null");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdEqualTo(String value) {
            addCriterion("wallet_record_id =", value, "walletRecordId");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdNotEqualTo(String value) {
            addCriterion("wallet_record_id <>", value, "walletRecordId");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdGreaterThan(String value) {
            addCriterion("wallet_record_id >", value, "walletRecordId");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdGreaterThanOrEqualTo(String value) {
            addCriterion("wallet_record_id >=", value, "walletRecordId");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdLessThan(String value) {
            addCriterion("wallet_record_id <", value, "walletRecordId");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdLessThanOrEqualTo(String value) {
            addCriterion("wallet_record_id <=", value, "walletRecordId");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdLike(String value) {
            addCriterion("wallet_record_id like", value, "walletRecordId");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdNotLike(String value) {
            addCriterion("wallet_record_id not like", value, "walletRecordId");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdIn(List<String> values) {
            addCriterion("wallet_record_id in", values, "walletRecordId");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdNotIn(List<String> values) {
            addCriterion("wallet_record_id not in", values, "walletRecordId");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdBetween(String value1, String value2) {
            addCriterion("wallet_record_id between", value1, value2, "walletRecordId");
            return (Criteria) this;
        }

        public Criteria andWalletRecordIdNotBetween(String value1, String value2) {
            addCriterion("wallet_record_id not between", value1, value2, "walletRecordId");
            return (Criteria) this;
        }

        public Criteria andAecAmountIsNull() {
            addCriterion("aec_amount is null");
            return (Criteria) this;
        }

        public Criteria andAecAmountIsNotNull() {
            addCriterion("aec_amount is not null");
            return (Criteria) this;
        }

        public Criteria andAecAmountEqualTo(BigDecimal value) {
            addCriterion("aec_amount =", value, "aecAmount");
            return (Criteria) this;
        }

        public Criteria andAecAmountNotEqualTo(BigDecimal value) {
            addCriterion("aec_amount <>", value, "aecAmount");
            return (Criteria) this;
        }

        public Criteria andAecAmountGreaterThan(BigDecimal value) {
            addCriterion("aec_amount >", value, "aecAmount");
            return (Criteria) this;
        }

        public Criteria andAecAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("aec_amount >=", value, "aecAmount");
            return (Criteria) this;
        }

        public Criteria andAecAmountLessThan(BigDecimal value) {
            addCriterion("aec_amount <", value, "aecAmount");
            return (Criteria) this;
        }

        public Criteria andAecAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("aec_amount <=", value, "aecAmount");
            return (Criteria) this;
        }

        public Criteria andAecAmountIn(List<BigDecimal> values) {
            addCriterion("aec_amount in", values, "aecAmount");
            return (Criteria) this;
        }

        public Criteria andAecAmountNotIn(List<BigDecimal> values) {
            addCriterion("aec_amount not in", values, "aecAmount");
            return (Criteria) this;
        }

        public Criteria andAecAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("aec_amount between", value1, value2, "aecAmount");
            return (Criteria) this;
        }

        public Criteria andAecAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("aec_amount not between", value1, value2, "aecAmount");
            return (Criteria) this;
        }

        public Criteria andMscAmountIsNull() {
            addCriterion("msc_amount is null");
            return (Criteria) this;
        }

        public Criteria andMscAmountIsNotNull() {
            addCriterion("msc_amount is not null");
            return (Criteria) this;
        }

        public Criteria andMscAmountEqualTo(BigDecimal value) {
            addCriterion("msc_amount =", value, "mscAmount");
            return (Criteria) this;
        }

        public Criteria andMscAmountNotEqualTo(BigDecimal value) {
            addCriterion("msc_amount <>", value, "mscAmount");
            return (Criteria) this;
        }

        public Criteria andMscAmountGreaterThan(BigDecimal value) {
            addCriterion("msc_amount >", value, "mscAmount");
            return (Criteria) this;
        }

        public Criteria andMscAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("msc_amount >=", value, "mscAmount");
            return (Criteria) this;
        }

        public Criteria andMscAmountLessThan(BigDecimal value) {
            addCriterion("msc_amount <", value, "mscAmount");
            return (Criteria) this;
        }

        public Criteria andMscAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("msc_amount <=", value, "mscAmount");
            return (Criteria) this;
        }

        public Criteria andMscAmountIn(List<BigDecimal> values) {
            addCriterion("msc_amount in", values, "mscAmount");
            return (Criteria) this;
        }

        public Criteria andMscAmountNotIn(List<BigDecimal> values) {
            addCriterion("msc_amount not in", values, "mscAmount");
            return (Criteria) this;
        }

        public Criteria andMscAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("msc_amount between", value1, value2, "mscAmount");
            return (Criteria) this;
        }

        public Criteria andMscAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("msc_amount not between", value1, value2, "mscAmount");
            return (Criteria) this;
        }

        public Criteria andRestMscAmountIsNull() {
            addCriterion("rest_msc_amount is null");
            return (Criteria) this;
        }

        public Criteria andRestMscAmountIsNotNull() {
            addCriterion("rest_msc_amount is not null");
            return (Criteria) this;
        }

        public Criteria andRestMscAmountEqualTo(BigDecimal value) {
            addCriterion("rest_msc_amount =", value, "restMscAmount");
            return (Criteria) this;
        }

        public Criteria andRestMscAmountNotEqualTo(BigDecimal value) {
            addCriterion("rest_msc_amount <>", value, "restMscAmount");
            return (Criteria) this;
        }

        public Criteria andRestMscAmountGreaterThan(BigDecimal value) {
            addCriterion("rest_msc_amount >", value, "restMscAmount");
            return (Criteria) this;
        }

        public Criteria andRestMscAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("rest_msc_amount >=", value, "restMscAmount");
            return (Criteria) this;
        }

        public Criteria andRestMscAmountLessThan(BigDecimal value) {
            addCriterion("rest_msc_amount <", value, "restMscAmount");
            return (Criteria) this;
        }

        public Criteria andRestMscAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("rest_msc_amount <=", value, "restMscAmount");
            return (Criteria) this;
        }

        public Criteria andRestMscAmountIn(List<BigDecimal> values) {
            addCriterion("rest_msc_amount in", values, "restMscAmount");
            return (Criteria) this;
        }

        public Criteria andRestMscAmountNotIn(List<BigDecimal> values) {
            addCriterion("rest_msc_amount not in", values, "restMscAmount");
            return (Criteria) this;
        }

        public Criteria andRestMscAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rest_msc_amount between", value1, value2, "restMscAmount");
            return (Criteria) this;
        }

        public Criteria andRestMscAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rest_msc_amount not between", value1, value2, "restMscAmount");
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

        public Criteria andEffectStatusIsNull() {
            addCriterion("effect_status is null");
            return (Criteria) this;
        }

        public Criteria andEffectStatusIsNotNull() {
            addCriterion("effect_status is not null");
            return (Criteria) this;
        }

        public Criteria andEffectStatusEqualTo(Integer value) {
            addCriterion("effect_status =", value, "effectStatus");
            return (Criteria) this;
        }

        public Criteria andEffectStatusNotEqualTo(Integer value) {
            addCriterion("effect_status <>", value, "effectStatus");
            return (Criteria) this;
        }

        public Criteria andEffectStatusGreaterThan(Integer value) {
            addCriterion("effect_status >", value, "effectStatus");
            return (Criteria) this;
        }

        public Criteria andEffectStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("effect_status >=", value, "effectStatus");
            return (Criteria) this;
        }

        public Criteria andEffectStatusLessThan(Integer value) {
            addCriterion("effect_status <", value, "effectStatus");
            return (Criteria) this;
        }

        public Criteria andEffectStatusLessThanOrEqualTo(Integer value) {
            addCriterion("effect_status <=", value, "effectStatus");
            return (Criteria) this;
        }

        public Criteria andEffectStatusIn(List<Integer> values) {
            addCriterion("effect_status in", values, "effectStatus");
            return (Criteria) this;
        }

        public Criteria andEffectStatusNotIn(List<Integer> values) {
            addCriterion("effect_status not in", values, "effectStatus");
            return (Criteria) this;
        }

        public Criteria andEffectStatusBetween(Integer value1, Integer value2) {
            addCriterion("effect_status between", value1, value2, "effectStatus");
            return (Criteria) this;
        }

        public Criteria andEffectStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("effect_status not between", value1, value2, "effectStatus");
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