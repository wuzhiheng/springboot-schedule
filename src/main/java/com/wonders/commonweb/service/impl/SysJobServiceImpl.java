package com.wonders.commonweb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wonders.commonweb.dao.SysJobDao;
import com.wonders.commonweb.model.SysJob;
import com.wonders.commonweb.schedule.CronTaskRegistrar;
import com.wonders.commonweb.schedule.SchedulingRunnable;
import com.wonders.commonweb.service.ISysJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wonders.commonweb.vo.ResultList;
import com.wonders.commonweb.vo.ReturnMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuzhiheng
 * @since 2020-02-11
 */
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobDao, SysJob> implements ISysJobService {

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public ReturnMsg addJob(SysJob sysJob) {

        this.save(sysJob);

        if ("1".equals(sysJob.getStatus().toString())) {
            SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
            cronTaskRegistrar.addCronTask(task, sysJob.getCronExpression());
        }

        return ReturnMsg.successTip();
    }

    @Override
    public ReturnMsg updateJob(SysJob sysJob) {
        SysJob oldJob = this.getById(sysJob.getId());

        sysJob.setUpdateTime(new Date());
        this.updateById(sysJob);

        //先移除再添加
        if ("1".equals(oldJob.getStatus().toString())) {
            SchedulingRunnable task = new SchedulingRunnable(oldJob.getBeanName(), oldJob.getMethodName(), oldJob.getMethodParams());
            cronTaskRegistrar.removeCronTask(task);
        }

        if ("1".equals(sysJob.getStatus().toString())) {
            SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
            cronTaskRegistrar.addCronTask(task, sysJob.getCronExpression());
        }
        return ReturnMsg.successTip();
    }

    @Override
    public ReturnMsg deleteJob(Integer id) {

        SysJob sysJob = this.getById(id);

        this.removeById(sysJob.getId());

        if ("1".equals(sysJob.getStatus().toString())) {
            SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
            cronTaskRegistrar.removeCronTask(task);
        }

        return ReturnMsg.successTip();
    }

    @Override
    public ResultList jobList(Integer status,Integer limit, Integer offset) {
        PageHelper.offsetPage(offset,limit);
        QueryWrapper<SysJob> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq(status != null,"status",status);
        List<SysJob> list = this.list(queryWrapper);
        PageInfo<SysJob> pageInfo = new PageInfo<>(list);
        return new ResultList(pageInfo.getTotal(),list);
    }

    @Override
    public ReturnMsg getOne(Integer id) {

        return ReturnMsg.successTip(this.getById(id));
    }

    @Override
    public ReturnMsg updateStatus(Integer id, Integer status) {

        SysJob existJob = this.getById(id);

        SysJob sysJob = new SysJob()
                .setId(id).setStatus(status);

        this.updateById(sysJob);

        //先移除再添加
        SchedulingRunnable task = new SchedulingRunnable(existJob.getBeanName(), existJob.getMethodName(), existJob.getMethodParams());
        cronTaskRegistrar.removeCronTask(task);

        if ("1".equals(status.toString())) {
            cronTaskRegistrar.addCronTask(task, existJob.getCronExpression());
        }

        return ReturnMsg.successTip();
    }
}
