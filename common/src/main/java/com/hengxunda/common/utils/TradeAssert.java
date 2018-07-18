package com.hengxunda.common.utils;


/**
 * 交易公用断言类
 * Created by wqt on 2017/10/18.
 */
public class TradeAssert {

    /**
     * 检查交易金额
     *
     * @param amount 交易金额
     */
    public static void amount(double amount) {
        A.check(amount <= 0D, "交易数额必须大于0");

        A.check(amount > 10000000D, "单笔交易数额不能超过1000万");
    }

    /**
     * 检查交易金额
     *
     * @param amount 交易金额
     */
    public static void amount(long amount, String accountName) {
        A.check(amount < 0D, accountName + "交易数额必须大于0");

        A.check(amount > 10000000L, accountName + "单笔交易数额不能超过1000万");
    }

}
