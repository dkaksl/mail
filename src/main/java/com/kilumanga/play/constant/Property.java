/** 
 * 
 * Copyright (C) 2018 Amani
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package com.kilumanga.play.constant;

public enum Property {
	MAIL_SMTPS_HOST("mail.smtps.host", "smtp.mailgun.org"), //
	MAIL_SMTPS_AUTH("mail.smtps.auth", "true"), //
	;

	private final String name;
	private final String value;

	private Property(String name, String value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
