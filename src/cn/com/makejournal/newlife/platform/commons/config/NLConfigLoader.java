package cn.com.makejournal.newlife.platform.commons.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

public class NLConfigLoader implements ServletContextListener {
	public void contextDestroyed(ServletContextEvent sce) {
		destroySpring(sce.getServletContext());
	}

	public void contextInitialized(ServletContextEvent sce) {
		initSpring(sce.getServletContext());
	}

	private void initSpring(ServletContext context) {
		SpringBeanHolder.setWebApplicationContext(WebApplicationContextUtils
				.getRequiredWebApplicationContext(context));
	}

	private void destroySpring(ServletContext context) {
		SpringBeanHolder.setWebApplicationContext(null);
	}
}
