package cn.com.makejournal.newlife.platform.email.util;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import cn.com.makejournal.newlife.platform.commons.config.SpringBeanHolder;

/**
 * 发送邮件工具类
 * 
 * * 发送Email的基本方法 要使用该方法，需在Spring环境中注册JavaMailSender，形式如下：
 * 
 * <bean id="mailProperties"
 * class="cn.com.gei.kmp4.kjjexternal.util.mail.MailProperties">
 * <constructor-arg index="0"> <value>true</value> </constructor-arg> </bean>
 * <bean id="mailSender"
 * class="org.springframework.mail.javamail.JavaMailSenderImpl"> <property
 * name="host"> <value>mailHost</value> </property> <property name="password">
 * <value>password</value> </property> <property name="username">
 * <value>mailUserName</value> </property> <property name="javaMailProperties">
 * <ref local="mailProperties"/> </property> </bean>
 * 
 * 
 */
public class MailUtil {
	protected static final Log logger = LogFactory.getLog(MailUtil.class);
	public static final String CONTENT_PRIX = "<META http-equiv=Content-Type content='text/html; charset=GBK'>";
	public static final String CONTENT_TYPE = "text/html;charset=GB2312";

	public static JavaMailSenderImpl mailSender = SpringBeanHolder
			.getBean("mailSender");
	public static final String MAIL_FROM = getMailFrom();

	public static boolean sendMail(String to, String subject, String content,
			InputStream is) {
		try {
			SimpleMailMessage simpleMessage = generateSimpleMessage(to,
					subject, content);

			sendMail(simpleMessage, is);
			return true;
		} catch (Exception e) {
			logger.warn("发送邮件时遇到错误", e);
			e.printStackTrace();
		}
		return false;
	}

	public static boolean sendEmail(String to, String subject,
			Map<String, File> map, String content) {
		try {
			SimpleMailMessage simpleMessage = generateSimpleMessage(to,
					subject, content);

			sendMail(map, simpleMessage);
			return true;
		} catch (Exception e) {
			logger.warn("发送邮件时遇到错误", e);
			e.printStackTrace();
		}
		return false;
	}

	public static boolean sendEmail(String to, String subject, String content,
			Map<String, InputStream> map) {
		try {
			SimpleMailMessage simpleMessage = generateSimpleMessage(to,
					subject, content);

			sendMail(simpleMessage, map);
			return true;
		} catch (Exception e) {
			logger.warn("发送邮件时遇到错误", e);
			e.printStackTrace();
		}
		return false;
	}

	public static boolean sendMail(SimpleMailMessage mail) {
		return sendMail(mail, (InputStream) null);
	}

	public static boolean sendMail(Map<String, File> map, SimpleMailMessage mail) {
		try {
			sendMail(getMimeMessage(mail, map));
			return true;
		} catch (Exception e) {
			logger.warn("发送邮件时遇到错误", e);
			e.printStackTrace();
		}
		return false;
	}

	// 得到系统邮箱的地址
	private static String getMailFrom() {
		return mailSender.getUsername();
	}

	public static boolean sendMail(SimpleMailMessage mail,
			Map<String, InputStream> map) {
		try {
			sendMail(getMimeMessage(map, mail));
			return true;
		} catch (Exception e) {
			logger.warn("发送邮件时遇到错误", e);
			e.printStackTrace();
		}
		return false;
	}

	public static boolean sendMail(SimpleMailMessage mail, InputStream is) {
		try {
			sendMail(getMimeMessage(mail, is));
			return true;
		} catch (Exception e) {
			logger.warn("发送邮件时遇到错误", e);
			e.printStackTrace();
		}
		return false;
	}

	public static boolean sendMail(MimeMessage mail) {
		try {
			mailSender.send(mail);
			return true;
		} catch (Exception e) {
			logger.warn("发送邮件时遇到错误", e);
			e.printStackTrace();
		}
		return false;
	}

	private static MimeMessage getMimeMessage(SimpleMailMessage mail,
			InputStream is) {
		try {
			HtmlMimeMailMessage message = null;
			if (is != null) {
				message = new HtmlMimeMailMessage(
						mailSender.createMimeMessage(is));
			} else {
				message = new HtmlMimeMailMessage(
						mailSender.createMimeMessage());
			}
			mail.copyTo(message);
			return message.getMimeMessage();
		} catch (Exception e) {
			logger.warn("发送邮件时遇到错误", e);
			e.printStackTrace();
		}
		return null;
	}

	private static MimeMessage getMimeMessage(Map<String, InputStream> map,
			SimpleMailMessage mail) {
		try {
			HtmlMimeMailMessage message = null;
			message = new HtmlMimeMailMessage(mailSender.createMimeMessage());

			if (map != null) {
				Iterator ite = map.entrySet().iterator();
				while (ite.hasNext()) {
					Map.Entry entry = (Map.Entry) ite.next();
					message.addAttachment(
							MimeUtility.encodeWord((String) entry.getKey()),
							(InputStream) entry.getValue());
				}
			}

			mail.copyTo(message);
			return message.getMimeMessage();
		} catch (Exception e) {
			logger.warn("发送邮件时遇到错误", e);
			e.printStackTrace();
		}
		return null;
	}

	private static MimeMessage getMimeMessage(SimpleMailMessage mail,
			Map<String, File> map) {
		try {
			HtmlMimeMailMessage message = null;
			message = new HtmlMimeMailMessage(mailSender.createMimeMessage());

			if (map != null) {
				Iterator ite = map.entrySet().iterator();
				while (ite.hasNext()) {
					Map.Entry entry = (Map.Entry) ite.next();
					message.addAttachment(
							MimeUtility.encodeWord((String) entry.getKey()),
							(File) entry.getValue());
				}
			}

			mail.copyTo(message);
			return message.getMimeMessage();
		} catch (Exception e) {
			logger.warn("发送邮件时遇到错误", e);
			e.printStackTrace();
		}
		return null;
	}

	private static SimpleMailMessage generateSimpleMessage(String to,
			String subject, String content) {
		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setTo(to.split(","));
		simpleMessage.setFrom(MAIL_FROM);
		simpleMessage.setSubject(subject);
		simpleMessage.setText(content);
		return simpleMessage;
	}

	public static boolean checkMailString(String thisMail) {
		if ((thisMail == null) || ("".equals(thisMail)))
			return false;
		int aIndex = thisMail.indexOf("@");
		if (aIndex < 0)
			return false;
		int dotIndex = thisMail.indexOf(".", aIndex);

		return dotIndex >= 0;
	}

	private static MimeMessage getMimeMessage(Map<String, File> fileMap,
			Map<String, byte[]> isMap, SimpleMailMessage mail) {
		try {
			HtmlMimeMailMessage message = null;
			message = new HtmlMimeMailMessage(mailSender.createMimeMessage());

			if ((fileMap != null) && (fileMap.size() > 0)) {
				Iterator ite = fileMap.entrySet().iterator();
				while (ite.hasNext()) {
					Map.Entry entry = (Map.Entry) ite.next();
					message.addAttachment(
							MimeUtility.encodeWord((String) entry.getKey()),
							(File) entry.getValue());
				}
			}

			if ((isMap != null) && (isMap.size() > 0)) {
				Iterator ite = isMap.entrySet().iterator();
				while (ite.hasNext()) {
					Map.Entry entry = (Map.Entry) ite.next();
					message.addAttachment(
							MimeUtility.encodeWord((String) entry.getKey()),
							(byte[]) entry.getValue());
				}
			}

			mail.copyTo(message);
			return message.getMimeMessage();
		} catch (Exception e) {
			logger.warn("发送邮件时遇到错误", e);
		}
		return null;
	}
}