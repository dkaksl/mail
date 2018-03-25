/** 
 * 
 * Copyright (C) 2018 Amani
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package com.kilumanga.play.mail;

import com.kilumanga.play.mail.constant.ExceptionMessage;
import com.kilumanga.play.mail.data.EmailAddress;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MailgunApiMailer {
	private final String domainName;
	private final String apiKey;
	private final EmailAddress fromAddress;

	public MailgunApiMailer(String domainName, String apiKey, EmailAddress fromAddress) {
		if (domainName == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}
		if (apiKey == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}
		if (fromAddress == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}

		this.domainName = domainName;
		this.apiKey = apiKey;
		this.fromAddress = fromAddress;
	}

	public JsonNode sendMail(EmailAddress toAddress, String subject, String text) throws UnirestException {
		if (toAddress == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}
		if (subject == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}
		if (text == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}

		HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + this.domainName + "/messages")
				.basicAuth("api", this.apiKey).queryString("from", this.fromAddress.getEmailAddress())
				.queryString("to", toAddress.getEmailAddress()).queryString("subject", subject)
				.queryString("text", text).asJson();
		return request.getBody();
	}
}
