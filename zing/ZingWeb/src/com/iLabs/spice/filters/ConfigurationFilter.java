/*
 * Copyright 2008 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.iLabs.spice.filters;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ConfigurationFilter extends GenericFilter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;

		
		for (Enumeration e = getFilterConfig().getInitParameterNames(); e
				.hasMoreElements();) {
			String config = (String) e.nextElement();
			if("GadgetServerURL".equals(config)){
				if (httpReq != null) {
					httpReq.setAttribute("GadgetServerURL", getFilterConfig().getInitParameter(config));
				}
			}
		}
		
		/*
		 * Object obj1 = httpReq.getSession().getAttribute("ownerProfile");
		 * Object obj2 = httpReq.getSession().getAttribute("currentProfile");
		 * String requestURI = httpReq.getRequestURI(); if((obj1 == null || obj2 ==
		 * null) && !(requestURI.contains("login.jspx") ||
		 * requestURI.contains("register.jspx")) &&
		 * requestURI.contains(".jspx")){ HttpServletResponse httpResponse =
		 * (HttpServletResponse)response;
		 * httpResponse.sendRedirect("http://localhost:8080/ShindigWeb/faces/pages/login.jspx"); }
		 */

		// TODO Auto-generated method stub
		super.doFilter(request, response, chain);
	}
}
