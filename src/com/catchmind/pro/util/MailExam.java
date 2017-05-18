package com.catchmind.pro.util;

import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class MailExam {

	public String mailPasswordFind(String address, String password) {
		try {
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.quitwait", "false");

			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("wkdwhdrb1212@gmail.com", "jik55166");
				}
			};

			Session session = Session.getDefaultInstance(props, auth);

			MimeMessage message = new MimeMessage(session);
			message.setSender(new InternetAddress("wkdwhdrb1212@gmail.com"));
			message.setSubject("[ 캐치업 임시비밀번호 ]");

			message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));

			Multipart mp = new MimeMultipart();
			MimeBodyPart mbpl = new MimeBodyPart();
			mbpl.setText("고객님의 캐치업 임시비밀번호는 [ " + password + " ] 입니다.");
			mp.addBodyPart(mbpl);

			MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(mc);

			message.setContent(mp);

			Transport.send(message);

			System.out.println("[전송완료!]");
			JOptionPane.showMessageDialog(null, "임시 비밀번호를 계정에 등록된 이메일로 전송했습니다.!");
			return "@pwFindOK";
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "이메일전송에 실패했습니다.");
		}
		return "@pwFindFail";
	}

	public boolean mailJoinConfirm(String address, String confirm) {
		try {
			Properties props = new Properties();
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.quitwait", "false");

			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("wkdwhdrb1212@gmail.com", "jik55166");
				}
			};

			Session session = Session.getDefaultInstance(props, auth);

			MimeMessage message = new MimeMessage(session);
			message.setSender(new InternetAddress("wkdwhdrb1212@gmail.com"));
			message.setSubject("[캐치업 메일인증]");

			message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));

			Multipart mp = new MimeMultipart();
			MimeBodyPart mbpl = new MimeBodyPart();
			mbpl.setText("캐치업 이메일 인증번호는  [ " + confirm + " ] 입니다.");
			mp.addBodyPart(mbpl);

			MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
			mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
			mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
			mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
			mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
			mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
			CommandMap.setDefaultCommandMap(mc);

			message.setContent(mp);

			Transport.send(message);

			System.out.println("[전송완료!]");
			JOptionPane.showMessageDialog(null, "인증번호 전송완료 !");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "인증번호를 보내는데 실패했습니다. 맞는형식의 이메일인지 확인해보세요.");
			return false;
		}
	}
}
