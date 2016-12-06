package com.hsjc.ssoCenter.core.config;


import com.hsjc.ssoCenter.core.authrealm.MyAuthRealm;
import com.hsjc.ssoCenter.core.filter.KickoutSessionControlFilter;
import com.hsjc.ssoCenter.core.filter.SysUserFilter;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.quartz.QuartzSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : zga
 * @date : 2015-11-24
 *
 * 安全配置类
 */
@Configuration
public class SecurityConfig {
	/**
	 * 缓存管理器(ehcache实现)
	 * @return
	 */
	/*@Bean
	public SpringCacheManagerWrapper cacheManager(){
		SpringCacheManagerWrapper springCacheManagerWrapper = new SpringCacheManagerWrapper();
		springCacheManagerWrapper.setCacheManager(springCacheManager());

		return springCacheManagerWrapper;
	}

	@Bean
	public EhCacheCacheManager springCacheManager() {
		EhCacheCacheManager ehCacheCacheManager = new EhCacheCacheManager();
		ehCacheCacheManager.setCacheManager(ehcacheManager());
		return ehCacheCacheManager;
	}

	*//**
	 * ehcache
	 * @return
     *//*
	@Bean
	public CacheManager ehcacheManager(){
		EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		ehCacheManagerFactoryBean.setConfigLocation(new PathResource("ehcache.xml"));

		return ehCacheManagerFactoryBean.getObject();
	}*/

	/**
	 * 凭证匹配器
	 * @return
     */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");
		hashedCredentialsMatcher.setHashIterations(2);
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
		return hashedCredentialsMatcher;
	}

	/**
	 * 自定义Realm
	 * @return
	 */
	@Bean
	public MyAuthRealm myAuthRealm() {
		/**
		 * cachingEnabled 是否启用缓存,如果设置为true的话,可以设置相应的缓存
		 *
		 * authenticationCachingEnabled 是否启用认证缓存
		 *
		 * authorizationCacheName 认证缓存名字
		 *
		 * authorizationCachingEnabled 是否启用授权缓存
		 *
		 * authorizationCachingEnabled 授权缓存名字
		 */
		MyAuthRealm myAuthRealm = new MyAuthRealm();
		myAuthRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		myAuthRealm.setCachingEnabled(false);
		return myAuthRealm;
	}

	/**
	 * 会话ID生成器
	 * @return
	 */
	@Bean
	public JavaUuidSessionIdGenerator javaUuidSessionIdGenerator(){
		return new JavaUuidSessionIdGenerator();
	}

	/**
	 * 会话DAO(会话存储/持久化)
	 * @return
	 */
	@Bean
	public EnterpriseCacheSessionDAO enterpriseCacheSessionDAO(){
		/**
		 * activeSessionsCacheName 设置Session缓存名字,默认就是shiro-activeSessionCache
		 */
		EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
		enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
		enterpriseCacheSessionDAO.setSessionIdGenerator(javaUuidSessionIdGenerator());

		return enterpriseCacheSessionDAO;
	}

	/**
	 * 会话验证调度器
	 * 全局的会话信息检测扫描信息间隔30分钟
	 * @return
	 */
	@Bean
	public QuartzSessionValidationScheduler quartzSessionValidationScheduler(){
		QuartzSessionValidationScheduler quartzSessionValidationScheduler = new QuartzSessionValidationScheduler();
		quartzSessionValidationScheduler.setSessionValidationInterval(1800000);
		quartzSessionValidationScheduler.setSessionManager(defaultWebSessionManager());

		return quartzSessionValidationScheduler;
	}

	/**
	 * 会话Cookie模板
	 * @return
	 */
	@Bean
	public SimpleCookie sessionIdCookie() {
		/**
		 * httpOnly 为true,客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击
		 *
		 * maxAge Cookie的过期时间,单位为秒,默认-1表示关闭浏览器时过期Cookie
		 */
		SimpleCookie simpleCookie = new SimpleCookie("sid");
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(-1);
		return simpleCookie;
	}

	/**
	 * 持久cookie设置
	 * @return
	 */
	@Bean
	public SimpleCookie rememberMeCookie() {
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		simpleCookie.setHttpOnly(true);
		simpleCookie.setMaxAge(2592000);
		return simpleCookie;
	}

	/**
	 * rememberMe管理器
	 * @return
	 */
	@Bean
	public RememberMeManager rememberMeManager() {
		CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
		rememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
		rememberMeManager.setCookie(rememberMeCookie());
		return rememberMeManager;
	}

	/**
	 * 会话管理器
	 * 全局的会话信息设置成30分钟,sessionValidationSchedulerEnabled参数就是是否开启扫描
	 * @return
	 */
	@Bean
	public DefaultWebSessionManager defaultWebSessionManager(){
		/**
		 * globalSessionTimeout 会话的全局过期时间(默认30分钟-1800000)
		 *
		 * sessionIdCookieEnabled 是否启用/禁用 Session Id Cookie,
		 * 默认是启用的,如果禁用后将不会设置 Session Id Cookie，即默认使用了 Servlet 容器的 JSESSIONID，且通过URL重写（URL 中的";JSESSIONID=id"部分）保存 Session Id
		 *
		 */
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		defaultWebSessionManager.setGlobalSessionTimeout(1800000);
		defaultWebSessionManager.setDeleteInvalidSessions(true);
		defaultWebSessionManager.setSessionValidationSchedulerEnabled(true);
		//defaultWebSessionManager.setSessionValidationScheduler(quartzSessionValidationScheduler());
		defaultWebSessionManager.setSessionDAO(enterpriseCacheSessionDAO());
		defaultWebSessionManager.setSessionIdCookieEnabled(true);
		defaultWebSessionManager.setSessionIdCookie(sessionIdCookie());

		return defaultWebSessionManager;
	}

	/**
	 * 安全管理器
	 * @return
	 */
	@Bean
	public DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myAuthRealm());
		//securityManager.setCacheManager(cacheManager());
		securityManager.setSessionManager(defaultWebSessionManager());
		securityManager.setRememberMeManager(rememberMeManager());
		return securityManager;
	}

	/**
	 * 相当于调用SecurityUtils.setSecurityManager(securityManager)
	 * @return
     */
	@Bean
	public MethodInvokingFactoryBean methodInvokingFactoryBean(){
		MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
		methodInvokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");

		Object[] objects = {securityManager()};
		methodInvokingFactoryBean.setArguments(objects);

		return methodInvokingFactoryBean;
	}

	/**
	 * 基于Form表单的身份验证过滤器
	 * @return
     */
	@Bean
	public FormAuthenticationFilter formAuthenticationFilter(){
		FormAuthenticationFilter formAuthenticationFilter = new FormAuthenticationFilter();
		formAuthenticationFilter.setUsernameParam("username");
		formAuthenticationFilter.setPasswordParam("password");
		formAuthenticationFilter.setRememberMeParam("remeberMe");
		formAuthenticationFilter.setLoginUrl("/user/login.html");

		return formAuthenticationFilter;
	}

	/**
	 * 用户过滤器
	 * @return
     */
	@Bean
	public SysUserFilter sysUserFilter(){
		return new SysUserFilter();
	}

	/**
	 * 自定义拦截器
	 * @return
     */
	@Bean
	public KickoutSessionControlFilter kickoutSessionControlFilter(){
		KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
		//kickoutSessionControlFilter.setCacheManager(cacheManager());
		kickoutSessionControlFilter.setSessionManager(defaultWebSessionManager());
		kickoutSessionControlFilter.setKickoutAfter(false);
		kickoutSessionControlFilter.setMaxSession(1);
		kickoutSessionControlFilter.setKickoutUrl("/user/login.html");

		return kickoutSessionControlFilter;
	}

	/**
	 * shiro的WEB过滤器
	 *
	 * @return
     */
	@Bean
	public ShiroFilterFactoryBean shiroFilter() {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager());
		shiroFilterFactoryBean.setLoginUrl("/user/login.html");

		Map<String,Filter> filterMap = new HashMap<>();
		filterMap.put("authc",formAuthenticationFilter());
		filterMap.put("sysUser",sysUserFilter());
		shiroFilterFactoryBean.setFilters(filterMap);

		Map<String, String> filterChainDefinitionMap = new HashMap<>();
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/code.html", "anon");
		filterChainDefinitionMap.put("/page/register/*.html", "anon");
		filterChainDefinitionMap.put("/page/serverError.html", "anon");
		filterChainDefinitionMap.put("/sms/**", "anon");
		filterChainDefinitionMap.put("/user/register/*.html", "anon");
		filterChainDefinitionMap.put("/user/register/*.json", "anon");
		filterChainDefinitionMap.put("/3rd/**", "anon");

		filterChainDefinitionMap.put("/user/login.html", "authc");
		filterChainDefinitionMap.put("/page/logout.html", "logout");

		filterChainDefinitionMap.put("/**", "user,sysUser");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return shiroFilterFactoryBean;
	}

	@Bean
	@DependsOn({"lifecycleBeanPostProcessor"})
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);

		return defaultAdvisorAutoProxyCreator;
	}

	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor LifecycleBeanPostProcessor(){
		LifecycleBeanPostProcessor lifecycleBeanPostProcessor = new LifecycleBeanPostProcessor();
		return lifecycleBeanPostProcessor;
	}

	/**
	 * Shiro Spring AOP 权限注解的支持
	 * @return
     */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
		return authorizationAttributeSourceAdvisor;
	}

	@Bean
	public LogoutFilter logout() {
		LogoutFilter logoutFilter = new LogoutFilter();
		logoutFilter.setRedirectUrl("/user/login.html");
		return logoutFilter;
	}
}
