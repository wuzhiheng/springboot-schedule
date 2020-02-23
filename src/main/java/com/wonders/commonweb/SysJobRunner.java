package com.wonders.commonweb;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.wonders.commonweb.dao.SysJobDao;
import com.wonders.commonweb.model.SysJob;
import com.wonders.commonweb.schedule.CronTaskRegistrar;
import com.wonders.commonweb.schedule.SchedulingRunnable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : wuzhiheng
 * @Description :
 * @Date Created in 21:04 2020-02-11
 */
@Service
@Slf4j
public class SysJobRunner implements CommandLineRunner {

    @Autowired
    private SysJobDao sysJobDao;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... args) {
        // 初始加载数据库里状态为正常的定时任务
        List<SysJob> jobList = sysJobDao.selectList(
                new QueryWrapper<SysJob>().eq("status","1")
        );
        if (CollectionUtils.isNotEmpty(jobList)) {
            for (SysJob job : jobList) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                try {
                    cronTaskRegistrar.addCronTask(task, job.getCronExpression());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            log.info("定时任务已加载完毕...");
        }
    }
}
