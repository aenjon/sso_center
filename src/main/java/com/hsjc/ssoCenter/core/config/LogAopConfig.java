package com.hsjc.ssoCenter.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author : zga
 * @date : 2015-12-21
 *
 * 日志拦截配置类
 */


@Configuration
@ComponentScan({"com.hsjc.ssoCenter"})
@EnableAspectJAutoProxy
public class LogAopConfig {
}

