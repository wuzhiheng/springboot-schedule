package com.wonders.commonweb.schedule.job;

import com.wonders.commonweb.dao.SysJobDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author : wuzhiheng
 * @Description :
 * @Date Created in 11:14 2020-02-12
 */
@Component
@Slf4j
public class MyTask {

    @Autowired
    private SysJobDao sysJobDao;

    public void test1(){
        log.info(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }

    public void test2(){
        log.info("{}",sysJobDao);
    }

}
