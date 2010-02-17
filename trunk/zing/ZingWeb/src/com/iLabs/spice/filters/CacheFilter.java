/*
 * Copyright 2008 Impetus Infotech.
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
import javax.servlet.http.HttpServletResponse;

public class CacheFilter extends GenericFilter{

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
//		httpResponse.addHeader("Pragma", "no-cache");
//		
//		httpResponse.addHeader("Expires", "-1");
		
		// set the provided HTTP response parameters
	    for (Enumeration e=getFilterConfig().getInitParameterNames();
	        e.hasMoreElements();) {
	      String headerName = (String)e.nextElement();
	      httpResponse.addHeader(headerName,
	    		  getFilterConfig().getInitParameter(headerName));
	    }
	    super.doFilter(request, response, chain);
	}

	
}
