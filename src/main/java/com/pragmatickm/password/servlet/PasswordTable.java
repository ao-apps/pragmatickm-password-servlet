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

import com.aoindustries.io.TempFileList;
import com.aoindustries.io.buffer.AutoTempFileWriter;
import com.aoindustries.io.buffer.BufferResult;
import com.aoindustries.io.buffer.BufferWriter;
import com.aoindustries.servlet.filter.TempFileContext;
import com.aoindustries.taglib.AutoEncodingBufferedTag;
import com.pragmatickm.password.servlet.impl.PasswordTableImpl;
import com.semanticcms.core.model.ElementContext;
import com.semanticcms.core.servlet.CaptureLevel;
import com.semanticcms.core.servlet.Element;
import com.semanticcms.core.servlet.PageContext;
import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.SkipPageException;

public class PasswordTable extends Element<com.pragmatickm.password.model.PasswordTable> {

	public PasswordTable(
		ServletContext servletContext,
		HttpServletRequest request,
		HttpServletResponse response
	) {
		super(
			servletContext,
			request,
			response,
			new com.pragmatickm.password.model.PasswordTable()
		);
	}

	public PasswordTable(
		ServletContext servletContext,
		HttpServletRequest request,
		HttpServletResponse response,
		String header
	) {
		this(servletContext, request, response);
		element.setHeader(header);
	}

	/**
	 * Creates a new password table in the current page context.
	 *
	 * @see  PageContext
	 */
	public PasswordTable() {
		this(
			PageContext.getServletContext(),
			PageContext.getRequest(),
			PageContext.getResponse()
		);
	}

	/**
	 * @see  #PasswordTable()
	 */
	public PasswordTable(String header) {
		this();
		element.setHeader(header);
	}

	@Override
	public PasswordTable id(String id) {
		super.id(id);
		return this;
	}

	public PasswordTable header(String header) {
		element.setHeader(header);
		return this;
	}

	private Iterable<? extends com.pragmatickm.password.model.Password> passwords;
	public PasswordTable passwords(Iterable<? extends com.pragmatickm.password.model.Password> passwords) {
		this.passwords = passwords;
		return this;
	}

	private Object style;
	public PasswordTable style(Object style) {
		this.style = style;
		return this;
	}

	private BufferResult writeMe;
	@Override
	protected void doBody(CaptureLevel captureLevel, Body<? super com.pragmatickm.password.model.PasswordTable> body) throws ServletException, IOException, SkipPageException {
		super.doBody(captureLevel, body);
		if(captureLevel == CaptureLevel.BODY) {
			// Enable temp files if temp file context active
			BufferWriter capturedOut = TempFileContext.wrapTempFileList(
				AutoEncodingBufferedTag.newBufferWriter(),
				request,
				// Java 1.8: AutoTempFileWriter::new
				new TempFileContext.Wrapper<BufferWriter>() {
					@Override
					public BufferWriter call(BufferWriter original, TempFileList tempFileList) {
						return new AutoTempFileWriter(original, tempFileList);
					}
				}
			);
			try {
				PasswordTableImpl.writePasswordTable(
					servletContext,
					request,
					response,
					capturedOut,
					element,
					passwords,
					style
				);
			} finally {
				capturedOut.close();
			}
			writeMe = capturedOut.getResult();
		} else {
			writeMe = null;
		}
	}

	@Override
	public void writeTo(Writer out, ElementContext context) throws IOException {
		if(writeMe != null) writeMe.writeTo(out);
	}
}
