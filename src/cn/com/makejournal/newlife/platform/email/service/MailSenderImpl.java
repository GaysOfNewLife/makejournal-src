package cn.com.makejournal.newlife.platform.email.service;

import cn.com.makejournal.newlife.platform.email.util.MailUtil;

/**
 * 发送邮件实现
 * 
 * @author huke
 * 
 */
public class MailSenderImpl implements IMailSender {

	/**
	 * 注册时发送的邮件验证内容
	 * 
	 * @param content
	 * @return
	 */
	private static String generateRegisterEmailContent(String content) {
		StringBuilder mailContents = new StringBuilder();
		mailContents.append("<html><head>");
		mailContents.append(MailUtil.CONTENT_PRIX);
		mailContents.append("</head><body>");
		mailContents.append("您好：<br/>");
		mailContents
				.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ZXXXX系统提示您邮箱验证：");
		mailContents.append("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		mailContents.append("&nbsp;&nbsp;");
		mailContents.append("<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;邮箱验证请登录:"
				+ content);
		mailContents.append("</p><hr />");
		mailContents
				.append("<font color='#c0c0c0'>此邮件为XXXX平台系统系统邮件，请勿回复。如您有疑问，请致电：010-67873781,谢谢!</font>");
		mailContents.append("</body></html>");
		return mailContents.toString();
	}

	/**
	 * 发送邮件
	 */
	@Override
	public Boolean sendMail(String mailTo, String title, String content,
			String type) {
		return MailUtil.sendEmail(mailTo, title, null,
				generateRegisterEmailContent(content));
	}

}
