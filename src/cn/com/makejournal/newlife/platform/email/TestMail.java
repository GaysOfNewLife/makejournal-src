package cn.com.makejournal.newlife.platform.email;

import cn.com.makejournal.newlife.platform.email.service.IMailSender;
import cn.com.makejournal.newlife.platform.email.service.MailSenderImpl;

public class TestMail {
	public static void main(String[] args) {
		String content = "this is a test mail by cc in java.";
		IMailSender mailSender = new MailSenderImpl();
		boolean flag = mailSender.sendMail("463496006@qq.com", "测试", content,
				null);
		if (flag) {
			System.out.println("Oh, yeah!");
		}
	}
}