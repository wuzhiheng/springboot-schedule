package com.wonders.commonweb.service;

import com.wonders.commonweb.model.SysJob;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wonders.commonweb.vo.ResultList;
import com.wonders.commonweb.vo.ReturnMsg;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuzhiheng
 * @since 2020-02-11
 */
public interface ISysJobService extends IService<SysJob> {

    ReturnMsg addJob(SysJob sysJob);

    ReturnMsg updateJob(SysJob sysJob);

    ReturnMsg deleteJob(Integer id);

    ResultList jobList(Integer status,Integer limit, Integer offset);

    ReturnMsg getOne(Integer id);

    ReturnMsg updateStatus(Integer id, Integer status);
}
