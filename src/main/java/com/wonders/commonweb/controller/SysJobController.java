package com.wonders.commonweb.controller;


import com.wonders.commonweb.model.SysJob;
import com.wonders.commonweb.service.ISysJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuzhiheng
 * @since 2020-02-11
 */
@RestController
@RequestMapping("/sysJob")
public class SysJobController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysJobService sysJobService;

    @RequestMapping("list")
    public Object list(Integer status,Integer limit,Integer offset){
        return sysJobService.jobList(status,limit,offset);
    }

    @RequestMapping("add")
    public Object add(SysJob sysJob){
        return sysJobService.addJob(sysJob);
    }

    @RequestMapping("update")
    public Object update(SysJob sysJob){
        return sysJobService.updateJob(sysJob);
    }

    @RequestMapping("delete")
    public Object delete(Integer id){
        return sysJobService.deleteJob(id);
    }

    @RequestMapping("getOne")
    public Object getOne(Integer id){
        return sysJobService.getOne(id);
    }

    @RequestMapping("status")
    public Object status(Integer id,Integer status){
        return sysJobService.updateStatus(id,status);
    }

}
