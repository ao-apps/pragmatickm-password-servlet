/*
 * pragmatickm-password-servlet - Passwords nested within SemanticCMS pages and elements in a Servlet environment.
 * Copyright (C) 2013, 2014, 2015, 2016, 2020  AO Industries, Inc.
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
package com.pragmatickm.password.servlet.impl;

import com.aoindustries.encoding.MediaWriter;
import static com.aoindustries.encoding.TextInXhtmlAttributeEncoder.encodeTextInXhtmlAttribute;
import static com.aoindustries.encoding.TextInXhtmlAttributeEncoder.textInXhtmlAttributeEncoder;
import com.aoindustries.html.Html;
import com.pragmatickm.password.model.Password;
import com.semanticcms.core.model.ElementContext;
import com.semanticcms.core.servlet.PageIndex;
import com.semanticcms.core.servlet.SemanticCMS;
import java.io.IOException;

final public class PasswordImpl {

	public static void writePassword(
		SemanticCMS semanticCMS,
		PageIndex pageIndex,
		Html html,
		ElementContext context,
		Password password
	) throws IOException {
		html.out.write("<span");
		String id = password.getId();
		if(id != null) {
			html.out.write(" id=\"");
			PageIndex.appendIdInPage(
				pageIndex,
				password.getPage(),
				id,
				new MediaWriter(textInXhtmlAttributeEncoder, html.out)
			);
			html.out.write('"');
		}
		String linkCssClass = semanticCMS.getLinkCssClass(password);
		if(linkCssClass != null) {
			html.out.write(" class=\"");
			encodeTextInXhtmlAttribute(linkCssClass, html.out);
			html.out.write('"');
		}
		html.out.write('>');
		html.text(password.getPassword());
		html.out.write("</span>");
	}

	/**
	 * Make no instances.
	 */
	private PasswordImpl() {
	}
}