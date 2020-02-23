package com.wonders.commonweb.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wonders.commonweb.util.IConstant;
import com.wonders.commonweb.vo.ReturnMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局捕获controller的异常处理
 * 创建日期：2018-1-23下午4:40:49
 * author:wuzhiheng
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
//	@ResponseBody
	public String handler(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.error(e.getMessage());
		e.printStackTrace();
		ReturnMsg returnMsg = new ReturnMsg(IConstant.CODE_UNKNOW, e.getMessage());
		if(!isAjax(request)){
			return "error404";	//如果不是ajax请求，这里只是简单的返回404页面，实际上应该返回一个反馈错误信息的页面
		}
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(new ObjectMapper().writeValueAsString(returnMsg));
		return null;
//		return returnMsg;
	}

	private boolean isAjax(HttpServletRequest request){
		return (request.getHeader("X-Requested-With") != null &&
				"XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString()));
	}
}
