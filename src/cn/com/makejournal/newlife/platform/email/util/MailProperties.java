package cn.com.makejournal.newlife.platform.email.util;

import java.util.Properties;

/**
 * 发送邮件配置
 * 
 */
public class MailProperties extends Properties {

	private static final long serialVersionUID = 1630017215148936281L;

	public MailProperties(String auth) {
		super.setProperty("mail.smtp.auth", auth);
	}
}