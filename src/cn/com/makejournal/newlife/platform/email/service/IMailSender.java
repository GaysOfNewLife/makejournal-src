package cn.com.makejournal.newlife.platform.email.service;

/**
 * 发送邮件接口
 * 
 * @author huke
 * 
 */
public interface IMailSender {
	/**
	 * 根据类型发送本系统相应的邮件
	 * 
	 * @param mailTo
	 * @param title
	 * @param content
	 * @param type
	 */
	public Boolean sendMail(String mailTo, String title, String content,
			String type);
}
