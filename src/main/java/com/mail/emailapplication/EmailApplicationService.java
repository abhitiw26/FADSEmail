package com.mail.emailapplication;

import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class EmailApplicationService {
	private String messageBody;

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
	public Boolean sendEmail(String messageBody) throws UnsupportedEncodingException, MessagingException {
		String FROM = "2016pcecegarvit041@poornima.org";
		String FROMNAME = "Garvit Sharma";

		String TO = "2016pcecegarvit041@poornima.org";

		String SMTP_USERNAME = "AKIAYUMLG6CJ3QFPHWGL";
		String SMTP_PASSWORD = "BBg6w8jEhQpvbDtgTrqPB2VlUd8to5bo97vd26gVXdw+";
		String HOST = "email-smtp.ap-south-1.amazonaws.com";

		int PORT = 587;
		String SUBJECT = "Amazon SES mail test";
		String str = org.apache.commons.text.StringEscapeUtils.unescapeJava(messageBody);
		System.out.println("str"+str);
		String BODY = str;
//				"<html><body><tr><td class=\"blackbar\"><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td align=\"left\" valign=\"middle\" class=\"left\"><p>Automated Email Alert from FADS !!</p></td></tr></table></td></tr><tr><td height=\"115\" align=\"left\" valign=\"top\"><table width=\"580\" border=\"0\" cellspacing=\"0\"cellpadding=\"0\"><tr><td width=\"290\" align=\"right\" valign=\"top\" class=\"issue\"><p><strong>Preliminary</strong> Issue Acknowledged</p></td></tr></table></td></tr><tr><td class=\"blackbar\"><table width=\"100%\" border=\"0\" cellspacing=\"0\"cellpadding=\"0\"><tr><td align=\"left\" valign=\"middle\" class=\"left\"><br/><p>Thanks</p><p>Bot</p></td></tr></body>	  </html>";

		Properties props = System.getProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);

		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(FROM, FROMNAME));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
		msg.setSubject(SUBJECT);
		msg.setContent(BODY, "text/html");

		Transport transport = session.getTransport();

		try {
			System.out.println("Sending Email Using AWS SNS...");
			transport.connect(HOST, SMTP_USERNAME, SMTP_PASSWORD);
			transport.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Email sent!");
			return true;
		} catch (Exception ex) {
			System.out.println("The email was not sent.");
			System.out.println("Error message: " + ex.getMessage());
			return false;

		} finally {
			transport.close();
		}
	}
	
}
