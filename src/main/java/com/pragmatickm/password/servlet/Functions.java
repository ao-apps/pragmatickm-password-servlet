/*
 * pragmatickm-password-servlet - Passwords nested within SemanticCMS pages and elements in a Servlet environment.
 * Copyright (C) 2013, 2014, 2015, 2016, 2017, 2018  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of pragmatickm-password-servlet.
 *
 * pragmatickm-password-servlet is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pragmatickm-password-servlet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with pragmatickm-password-servlet.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.pragmatickm.password.servlet;

import com.aoindustries.aoserv.client.password.PasswordGenerator;
import com.semanticcms.core.servlet.CaptureLevel;
import java.io.IOException;
import java.security.SecureRandom;
import javax.servlet.ServletRequest;

final public class Functions {

	private static final int SHORT_PASSWORD_LENGTH = 8;

	private static final String CAPTURING_CONSTANT = "<<<CAPTURING>>>";

	private static final SecureRandom secureRandom = new SecureRandom();

	public static String generatePassword(ServletRequest request) throws IOException {
		if(CaptureLevel.getCaptureLevel(request) != CaptureLevel.BODY) {
			return CAPTURING_CONSTANT;
		} else {
			return PasswordGenerator.generatePassword(secureRandom);
		}
	}

	public static String generateShortPassword() {
		// Only use characters 0-9, A-Z, and a-z
		StringBuilder sb = new StringBuilder(SHORT_PASSWORD_LENGTH);
		for(int i=0; i<SHORT_PASSWORD_LENGTH; i++) {
			int val = secureRandom.nextInt(10 + 26 + 26);
			if(val<10) sb.append(val);
			else if(val < (10 + 26)) {
				sb.append((char)(val - 10 + 'A'));
			} else {
				sb.append((char)(val - (10 + 26) + 'a'));
			}
		}
		return sb.toString();
	}

	public static String generateShortPassword(ServletRequest request) {
		if(CaptureLevel.getCaptureLevel(request) != CaptureLevel.BODY) {
			return CAPTURING_CONSTANT;
		} else {
			return generateShortPassword();
		}
	}

	/**
	 * Make no instances.
	 */
	private Functions() {
	}
}