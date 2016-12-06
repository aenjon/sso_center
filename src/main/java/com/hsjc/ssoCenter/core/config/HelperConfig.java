package com.hsjc.ssoCenter.core.config;

import com.hsjc.ssoCenter.core.helper.RedisHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

/**
 * @author : zga
 * @date : 15-11-24
 */
@Configuration
public class HelperConfig {

	@Bean
	public RedisHelper redisHelper(
			@Value("${redis.host}") String host,
			@Value("${redis.port}") Integer port
	) {
		return new RedisHelper(host, port);
	}

	@PreDestroy
	public void redisDestroy() {
		RedisHelper.stop();
	}
}
