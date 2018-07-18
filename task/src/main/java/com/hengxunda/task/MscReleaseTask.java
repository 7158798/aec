package com.hengxunda.task;

import com.hengxunda.task.service.IMscReleaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * msc股权释放task
 *
 * @author chengwei
 * @date 2018-06-06
 */
@Component
public class MscReleaseTask {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IMscReleaseService mscReleaseService;

//    @Scheduled(cron = "0 0/5 * * * ?")  //  测试5分钟一次
//    @Scheduled(cron = "0 0 1 * * ?")    //  每晚1点运行
    @Scheduled(cron = "0 0/30 * * * ?")   //  测试30分钟一次
    public synchronized void doTask() {
        logger.info("开始股权释放任务...");
        mscReleaseService.releaseMsc();
        logger.info("结束股权释放任务...");
    }
}
