package com.hsjc.ssoCenter.core.annotation;
  
import java.lang.annotation.*;

/**
 * @author : zga
 * @date : 2015-12-14
 *
 * 自定义注解(获取接口日志)
 */  
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public  @interface SSOSystemLog {
	int actionId(); //执行方法Id
	String module() default "";
	String description()  default "";  //
}
  
