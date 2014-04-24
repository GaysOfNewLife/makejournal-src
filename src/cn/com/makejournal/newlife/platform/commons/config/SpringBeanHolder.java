package cn.com.makejournal.newlife.platform.commons.config;

import org.springframework.web.context.WebApplicationContext;

public class SpringBeanHolder {
	private static WebApplicationContext wac;

	public static void setWebApplicationContext(WebApplicationContext context) {
		wac = context;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		if ((beanName == null) || ("".equals(beanName)))
			return null;
		return (T) wac.getBean(beanName);
	}
}
