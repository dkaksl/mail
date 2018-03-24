/** 
 * 
 * Copyright (C) 2018 Amani
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package com.kilumanga.play.mail.data;

import org.apache.commons.validator.routines.EmailValidator;

import com.kilumanga.play.mail.constant.ExceptionMessage;

public class EmailAddress {
	private final String emailAddress;

	public EmailAddress(String emailAddress) {
		if (emailAddress == null) {
			throw new IllegalArgumentException(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		}

		if (emailAddress.isEmpty()) {
			throw new IllegalArgumentException(ExceptionMessage.INVALID_EMAIL_ADDRESS.getExceptionMessage());
		}

		if (!EmailValidator.getInstance().isValid(emailAddress)) {
			throw new IllegalArgumentException(ExceptionMessage.INVALID_EMAIL_ADDRESS.getExceptionMessage());
		}
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

}
