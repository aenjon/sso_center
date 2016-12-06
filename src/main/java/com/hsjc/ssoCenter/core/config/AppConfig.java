package com.hsjc.ssoCenter.core.config;

import com.hsjc.ssoCenter.core.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * @author : zga
 * @date : 2015-11-24
 *
 */
@Configuration
@ComponentScan({"com.hsjc"})
@EnableWebMvc
@EnableScheduling
@EnableSpringDataWebSupport
public class AppConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean
	public RedisConstant redisConstant(
			@Value("${redis.host}") String HOST,
			@Value("${redis.port}") Integer PORT,
			@Value("${redis.password}") String PASSWORD,
			@Value("${redis.dictDatabase}") Integer DB_DICT
	) {
		RedisConstant.DB_HOST = HOST;
		RedisConstant.DB_PORT = PORT;
		RedisConstant.DB_PASSWORD = PASSWORD;
		RedisConstant.DB_DICT = DB_DICT;

		return null;
	}

	/*@Bean
	public Constant Constant(){
		return null;
	}

	@Bean
	public MailConstant MailConstant(){
		return null;
	}

	@Bean
	public SMSConstant SMSConstant(){
		return null;
	}*/

	@Configuration
	@Profile("development")
	@PropertySources({
			@PropertySource("classpath:application.development.properties"),
			@PropertySource("classpath:log4j.properties")
	})
	static class Development{}

	@Configuration
	@Profile("production")
	@PropertySources({
			@PropertySource("classpath:application.production.properties"),
			@PropertySource("classpath:log4j.properties")
	})
	static class Production{}

	@Configuration
	@Profile("online")
	@PropertySources({
			@PropertySource("classpath:application.online.properties"),
			@PropertySource("classpath:log4j.properties")
	})
	static class Online{}
}
