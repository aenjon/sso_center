package com.hsjc.ssoCenter.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.hsjc.ssoCenter.core.constant.RedisConstant;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;

/**
 * @author : zga
 * @date : 2015-11-24
 *
 * 数据源配置类
 */
@Configuration
@EnableTransactionManagement
public class DataConfig {

	@Bean
	public DruidDataSource druidDataSource(
			@Value("${db.driver}") String driver,
			@Value("${db.url}") String url,
			@Value("${db.username}") String username,
			@Value("${db.password}") String password
	) {
		DruidDataSource druidDataSource = new DruidDataSource();
		druidDataSource.setDriverClassName(driver);
		druidDataSource.setUrl(url);
		druidDataSource.setUsername(username);
		druidDataSource.setPassword(password);

		druidDataSource.setInitialSize(24);
		druidDataSource.setMinIdle(24);
		druidDataSource.setMaxActive(1024);
		druidDataSource.setMaxIdle(1024);
		druidDataSource.setMaxWait(60000);
		druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
		druidDataSource.setMinEvictableIdleTimeMillis(30000);
		druidDataSource.setValidationQuery("select 1");
		druidDataSource.setTestWhileIdle(true);
		druidDataSource.setTestOnBorrow(false);
		druidDataSource.setTestOnReturn(false);
		druidDataSource.setPoolPreparedStatements(true);
		druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

		try {
			druidDataSource.setFilters("wall,stat");
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return druidDataSource;
	}

	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager(DruidDataSource druidDataSource){
		DataSourceTransactionManager dataSourceTransactionManager  = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(druidDataSource);
		return dataSourceTransactionManager;
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean sqlSessionFactoryBean(DruidDataSource druidDataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(druidDataSource);
		sqlSessionFactoryBean.setTypeAliasesPackage("com.hsjc.ssoCenter.core.domain");
		final Resource configLocation = new ClassPathResource("mybatis-config.xml");
		sqlSessionFactoryBean.setConfigLocation(configLocation);
		return sqlSessionFactoryBean;
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() throws Exception{
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setBasePackage("com.hsjc.ssoCenter.core.mapper");
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		return mapperScannerConfigurer;
	}

	@Bean
	public JedisConnectionFactory dictJedisConnectionFactory(
            @Value("${redis.host}") String host,
            @Value("${redis.port}") int port,
            @Value("${redis.password}") String password,
            @Value("${redis.dictDatabase}") Integer dataBase
    ) {
		JedisConnectionFactory factory = new JedisConnectionFactory();

		factory.setHostName(host);
		factory.setPort(port);
        if(StringUtils.isNotEmpty(password)){
            factory.setPassword(password);
        }
		factory.setDatabase(RedisConstant.DB_DICT);

		return factory;
	}

	@Bean
	public RedisTemplate redisTemplate(
            JedisConnectionFactory dictJedisConnectionFactory) {
		RedisTemplate redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(dictJedisConnectionFactory);

		return redisTemplate;
	}
}
