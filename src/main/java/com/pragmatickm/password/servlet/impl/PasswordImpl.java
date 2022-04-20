/*
 * pragmatickm-password-servlet - Passwords nested within SemanticCMS pages and elements in a Servlet environment.
 * Copyright (C) 2013, 2014, 2015, 2016, 2017, 2020, 2021, 2022  AO Industries, Inc.
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
 * along with pragmatickm-password-servlet.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.pragmatickm.password.servlet.impl;

import com.aoapps.html.any.AnyUnion_Palpable_Phrasing;
import com.pragmatickm.password.model.Password;
import com.semanticcms.core.model.ElementContext;
import com.semanticcms.core.servlet.PageIndex;
import com.semanticcms.core.servlet.SemanticCMS;
import java.io.IOException;

public final class PasswordImpl {

  /** Make no instances. */
  private PasswordImpl() {
    throw new AssertionError();
  }

  public static void writePassword(
    SemanticCMS semanticCMS,
    PageIndex pageIndex,
    AnyUnion_Palpable_Phrasing<?, ?> content,
    ElementContext context,
    Password password
  ) throws IOException {
    String id = password.getId();
    content.span()
      .id((id == null) ? null : idAttr -> PageIndex.appendIdInPage(
        pageIndex,
        password.getPage(),
        id,
        idAttr
      ))
      .clazz(semanticCMS.getLinkCssClass(password))
    .__(password.getPassword());
  }
}
