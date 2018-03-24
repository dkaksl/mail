/** 
 * 
 * Copyright (C) 2018 Amani
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
package com.kilumanga.play.mail.data;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.kilumanga.play.constant.ExceptionMessage;

public class EmailAddressTest {
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void testGoodAddress() {
		new EmailAddress("abc@example.com");
	}

	@Test
	public void testNull() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ExceptionMessage.NULL_PARAMETER.getExceptionMessage());
		new EmailAddress(null);
	}

	@Test
	public void testBlank() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ExceptionMessage.INVALID_EMAIL_ADDRESS.getExceptionMessage());
		new EmailAddress("");
	}

	@Test
	public void testGarbledAddress() {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage(ExceptionMessage.INVALID_EMAIL_ADDRESS.getExceptionMessage());
		new EmailAddress("fi8j2a9038fj0a29j");
	}

}
