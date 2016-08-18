/*
 * pragmatickm-password-servlet - Passwords nested within SemanticCMS pages and elements in a Servlet environment.
 * Copyright (C) 2013, 2014, 2015, 2016  AO Industries, Inc.
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

import com.pragmatickm.password.model.PasswordTable;
import com.pragmatickm.password.servlet.impl.PasswordImpl;
import com.semanticcms.core.model.ElementContext;
import com.semanticcms.core.model.PageRef;
import com.semanticcms.core.servlet.CaptureLevel;
import com.semanticcms.core.servlet.Element;
import com.semanticcms.core.servlet.PageContext;
import com.semanticcms.core.servlet.PageIndex;
import com.semanticcms.core.servlet.SemanticCMS;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.SkipPageException;

public class Password extends Element<com.pragmatickm.password.model.Password> {

	public static final String DEMO_MODE_PASSWORD = "********";

	public Password(
		ServletContext servletContext,
		HttpServletRequest request,
		HttpServletResponse response,
		String password
	) {
		super(
			servletContext,
			request,
			response,
			new com.pragmatickm.password.model.Password()
		);
		boolean demoMode = SemanticCMS.getInstance(servletContext).getDemoMode();
		element.setPassword(demoMode ? DEMO_MODE_PASSWORD : password);
	}

	/**
	 * Creates a new password in the current page context.
	 *
	 * @see  PageContext
	 */
	public Password(String password) {
		this(
			PageContext.getServletContext(),
			PageContext.getRequest(),
			PageContext.getResponse(),
			password
		);
	}

	@Override
	public Password id(String id) {
		super.id(id);
		return this;
	}

	public Password href(String href) {
		element.setHref(href);
		return this;
	}

	public Password username(String username) {
		element.setUsername(username);
		return this;
	}

	/* Already set in constructor
	public Password password(String password) {
		element.setPassword(password);
		return this;
	}*/

	public Password customField(String name, PageRef pageRef, String element, String value) {
		this.element.addCustomField(name, pageRef, element, value);
		return this;
	}

	public Password secretQuestion(String question, String answer) {
		element.addSecretQuestion(question, answer);
		return this;
	}

	private PageIndex pageIndex;
	@Override
	protected void doBody(CaptureLevel captureLevel, Body<? super com.pragmatickm.password.model.Password> body) throws ServletException, IOException, SkipPageException {
		pageIndex = PageIndex.getCurrentPageIndex(request);
		super.doBody(captureLevel, body);
	}

	@Override
	public void writeTo(Writer out, ElementContext context) throws IOException, ServletException, SkipPageException {
		if(!(element.getParentElement() instanceof PasswordTable)) {
			PasswordImpl.writePassword(pageIndex, out, context, element);
		}
	}
}
