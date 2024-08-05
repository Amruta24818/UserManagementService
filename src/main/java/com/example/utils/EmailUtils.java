package com.example.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtils {

	@Autowired
	private JavaMailSender mailSender;

	public boolean sendEmail(String to, String subject, String body) {

		boolean isMailSent = false;
		try {
			MimeMessage mimiMessage = mailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mimiMessage);

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);

			mailSender.send(mimiMessage);
			
			isMailSent = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isMailSent;
	}
}
