package com.hengxunda.task.service;


import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/6/7.
 */

public interface ISyncETHTransactionService {

    /**
     * 同步外部转入交易记录
     */
    void syncExternalReceive();
}
