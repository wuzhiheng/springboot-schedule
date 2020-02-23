package com.wonders.commonweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.controller
 * @authorName:wangjiaming
 * @createDate:2019-08-29
 * @editor:IntelliJ IDEA
 * @other:
 **/
@Controller
@RequestMapping("/indexPage")
public class IndexController {

    @RequestMapping("/index")
    public String toIndex() {
        return "pages/index";
    }

    @RequestMapping("/job")
    public String job() {
        return "pages/job";
    }

    @RequestMapping("/job2")
    public String job2() {
        return "pages/job2";
    }

}
