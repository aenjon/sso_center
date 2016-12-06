package com.hsjc.ssoCenter.core.config;

		import org.springframework.web.WebApplicationInitializer;
		import org.springframework.web.context.ContextLoaderListener;
		import org.springframework.web.context.WebApplicationContext;
		import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
		import org.springframework.web.filter.CharacterEncodingFilter;
		import org.springframework.web.filter.DelegatingFilterProxy;
		import org.springframework.web.servlet.DispatcherServlet;

		import javax.servlet.FilterRegistration;
		import javax.servlet.ServletContext;
		import javax.servlet.ServletException;
		import javax.servlet.ServletRegistration;

/**
 * @author : zga
 * @date : 2015-11-24
 */
public class AppInitializer implements WebApplicationInitializer {
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		WebApplicationContext context = getContext();

		DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

		servletContext.addListener(new ContextLoaderListener(context));
		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", dispatcherServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("*.html");
		dispatcher.addMapping("*.json");

		FilterRegistration charEncodingFilter = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
		charEncodingFilter.setInitParameter("encoding", "UTF-8");
		charEncodingFilter.setInitParameter("forceEncoding", "true");
		charEncodingFilter.addMappingForUrlPatterns(null, false, "/*");

		servletContext.addFilter("shiroFilter", new DelegatingFilterProxy("shiroFilter", context))
				.addMappingForUrlPatterns(null, false, "/*");
	}

	private AnnotationConfigWebApplicationContext getContext() {
		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
		context.setConfigLocation("com.hsjc.ssoCenter.core.config");
//		context.getEnvironment().setActiveProfiles("online");
		//context.getEnvironment().setActiveProfiles("production");
		context.getEnvironment().setActiveProfiles("development");
		return context;
	}

}
