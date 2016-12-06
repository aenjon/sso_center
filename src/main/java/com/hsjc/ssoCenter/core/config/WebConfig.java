package com.hsjc.ssoCenter.core.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.hsjc.ssoCenter.app.base.ExceptionHandler;
import com.hsjc.ssoCenter.core.formatter.DateFormatter;
import com.hsjc.ssoCenter.core.interceptor.LoginInterceptor;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.dialect.SpringStandardDialect;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.StandardTemplateModeHandlers;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.nio.charset.Charset;
import java.util.*;


/**
 * @author : zga
 * @date : 2015-11-24
 *
 * Web MVC配置类
 */
@Configuration
@ComponentScan({"com.hsjc"})
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(stringHttpMessageConverter());
		converters.add(fastJsonHttpMessageConverter());
		restTemplate.setMessageConverters(converters);
		return restTemplate;
	}

	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		return stringHttpMessageConverter;
	}


	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		return fastJsonHttpMessageConverter;
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(stringHttpMessageConverter());
		converters.add(fastJsonHttpMessageConverter());
		super.configureMessageConverters(converters);
	}

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("Messages", "ValidationMessages");
		source.setDefaultEncoding("UTF-8");
		return source;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		super.addResourceHandlers(registry);
	}

	@Bean
	public TemplateResolver templateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(StandardTemplateModeHandlers.HTML5.getTemplateModeName());
		resolver.setCharacterEncoding("UTF-8");
		resolver.setCacheable(false);
		return resolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		Set<IDialect> additionalDialects = new HashSet<>();
		additionalDialects.add(new SpringStandardDialect());
		additionalDialects.add(new ShiroDialect());
		additionalDialects.add(new LayoutDialect());
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver());
		engine.setAdditionalDialects(additionalDialects);
		return engine;
	}

	@Bean
	public ContentNegotiationManager contentNegotiationManager() {
		ContentNegotiationManagerFactoryBean contentNegotiationManager = new ContentNegotiationManagerFactoryBean();
		Properties mediaTypes = new Properties();
		mediaTypes.put("json", MediaType.APPLICATION_JSON_VALUE);
		mediaTypes.put("html", MediaType.TEXT_HTML_VALUE);

		contentNegotiationManager.setMediaTypes(mediaTypes);
		contentNegotiationManager.setDefaultContentType(MediaType.TEXT_HTML);
		contentNegotiationManager.setIgnoreAcceptHeader(true);
		return contentNegotiationManager.getObject();
	}

	@Bean
	public ViewResolver contentNegotiatingViewResolver(
			ContentNegotiationManager contentNegotiationManager,
			SpringTemplateEngine templateEngine
	) {
		ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();
		viewResolver.setOrder(1);
		viewResolver.setContentNegotiationManager(contentNegotiationManager);

		List<ViewResolver> viewResolvers = new ArrayList<>();
		ThymeleafViewResolver thymeleafViewResolver = new ThymeleafViewResolver();
		thymeleafViewResolver.setTemplateEngine(templateEngine);
		thymeleafViewResolver.setCharacterEncoding("UTF-8");
		viewResolvers.add(thymeleafViewResolver);
		viewResolver.setViewResolvers(viewResolvers);


		List<View> defaultViews = new ArrayList<>();
		defaultViews.add(new FastJsonJsonView());

		//XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
		//xStreamMarshaller.setAutodetectAnnotations(true);
		//MarshallingView marshallingView = new MarshallingView(xStreamMarshaller);
		//defaultViews.add(marshallingView);

		viewResolver.setDefaultViews(defaultViews);
		return viewResolver;
	}

	@Bean
	public FormattingConversionServiceFactoryBean conversionService() {
		FormattingConversionServiceFactoryBean conversionService = new FormattingConversionServiceFactoryBean();
		Set<org.springframework.format.Formatter> formatters = new HashSet<>();
		formatters.add(new DateFormatter());
		conversionService.setFormatters(formatters);
		return conversionService;
	}

	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1000000000);
		return multipartResolver;

	}

	@Bean
	public BeanNameUrlHandlerMapping BeanNameUrlHandlerMapping(){
		BeanNameUrlHandlerMapping beanNameUrlHandlerMapping = new BeanNameUrlHandlerMapping();
		Object[] interceptors = {loginInterceptor()};

		beanNameUrlHandlerMapping.setInterceptors(interceptors);
		return beanNameUrlHandlerMapping;
	}

	@Bean
	public LoginInterceptor loginInterceptor(){
		LoginInterceptor loginInterceptor = new LoginInterceptor();
		loginInterceptor.setLoginUrl("/user/login.html");
		return loginInterceptor;
	}

	@Bean
	public ExceptionHandler exceptionResolver(){
		return new ExceptionHandler();
	}

	/*
	@Bean
	public SchedulerFactory schedulerFactory() throws ParseException, SchedulerException {
		//MethodInvokingJobDetailFactoryBean detailFactoryBean=new MethodInvokingJobDetailFactoryBean();
		//detailFactoryBean.setTargetObject();

		JobDetail job=new JobDetail("job", "group", TimeJob.class);
		JobDataMap dataMap = new JobDataMap();
		dataMap.put("service", CountResourceService.class);
		job.setJobDataMap(dataMap);

		SimpleTrigger simTrig=new SimpleTrigger("myTrigger", -1, 1000);
		CronTrigger cronTrigger=new CronTrigger("myTrigger", "group", "0 02,05 19 * * ?");

		SchedulerFactory factory=new StdSchedulerFactory();
		Scheduler scheduler=factory.getScheduler();

		scheduler.scheduleJob(job, cronTrigger);
		//scheduler.scheduleJob(job, simTrig);
		scheduler.start();
		return factory;
	}
	*/
}
