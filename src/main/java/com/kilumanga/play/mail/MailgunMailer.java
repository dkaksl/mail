/** 
 * 
 * Copyright (C) 2018 Amani
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package com.kilumanga.play.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.kilumanga.play.constant.ExceptionMessage;
import com.kilumanga.play.constant.Property;
import com.sun.mail.smtp.SMTPTransport;

public class MailgunMailer {
	private final String fromAddress;
	private final String smtpUsername;
	private final String smtpPassword;

	public MailgunMailer(String fromAddress, String smtpUsername, String smtpPassword) {
		if (fromAddress == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}
		if (smtpUsername == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}
		if (smtpPassword == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}

		this.fromAddress = fromAddress;
		this.smtpUsername = smtpUsername;
		this.smtpPassword = smtpPassword;
	}

	public String sendSmtpMail(String[] toAddresses, String[] ccAddresses, String[] bccAddresses, String subject,
			String text) throws AddressException, MessagingException {
		if (toAddresses == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}
		if (ccAddresses == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}
		if (bccAddresses == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}
		if (subject == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}
		if (text == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}

		Properties properties = System.getProperties();
		putProperty(properties, Property.MAIL_SMTPS_HOST);
		putProperty(properties, Property.MAIL_SMTPS_AUTH);

		Session session = Session.getInstance(properties, null);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(this.fromAddress));

		setRecipients(message, Message.RecipientType.TO, toAddresses);
		setRecipients(message, Message.RecipientType.CC, ccAddresses);
		setRecipients(message, Message.RecipientType.BCC, bccAddresses);

		message.setSubject(subject);
		message.setText(text);
		message.setSentDate(new Date());

		try (SMTPTransport transport = (SMTPTransport) session.getTransport("smtps")) {
			transport.connect("smtp.mailgun.com", this.smtpUsername, this.smtpPassword);
			transport.sendMessage(message, message.getAllRecipients());
			return transport.getLastServerResponse();
		}
	}

	private void putProperty(Properties properties, Property property) {
		properties.put(property.getName(), property.getValue());
	}

	public String sendSmtpMail(String[] toAddresses, String subject, String text)
			throws AddressException, MessagingException {
		return sendSmtpMail(toAddresses, new String[] {}, new String[] {}, subject, text);
	}

	private void setRecipients(Message message, Message.RecipientType recipientType, String[] addresses)
			throws AddressException, MessagingException {
		if (addresses.length == 0) {
			return;
		}
		message.setRecipients(recipientType, getInternetAddresses(addresses));
	}

	private InternetAddress[] getInternetAddresses(String[] addresses) throws AddressException {
		StringBuilder addressListBuilder = new StringBuilder();
		for (String address : addresses) {
			addressListBuilder.append(address);
			addressListBuilder.append(",");
		}
		addressListBuilder.deleteCharAt(addressListBuilder.length() - 1);
		return InternetAddress.parse(addressListBuilder.toString());
	}
}
