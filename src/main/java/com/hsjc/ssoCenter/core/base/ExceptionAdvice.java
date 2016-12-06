package com.hsjc.ssoCenter.core.base;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;

/**
 * @author : zga
 * @date : 2016-1-18
 *
 * 异常处理类
 *
 */
@ControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) throws IOException, ParseException {
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		e.printStackTrace(pw);
		String exceptionTrace =  writer.toString();
		pw.close();
		writer.close();

		System.out.println("出现异常了:"+exceptionTrace);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("/user/login");
		return mav;
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView doFaultNotFoundHandler(HttpServletRequest request, Exception e){
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("url", request.getRequestURL());
		mav.setViewName("/user/login");
		return mav;
	}
}
