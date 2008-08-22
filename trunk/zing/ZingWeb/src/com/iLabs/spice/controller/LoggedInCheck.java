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
package com.iLabs.spice.controller;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

public class LoggedInCheck implements PhaseListener {

	public void afterPhase(PhaseEvent event) {
		FacesContext fc = event.getFacesContext();
		try {
			// Check to see if they are on the login page.
			boolean loginPage = fc.getViewRoot().getViewId().lastIndexOf(
					"login") > -1 ? true : false;
			boolean isJspx = fc.getViewRoot().getViewId().lastIndexOf(".jspx") > -1 ? true
					: false;
			boolean loggedIn = fc.getExternalContext().getSessionMap().get(
					"ownerProfile") != null;
		  boolean registerJspx = fc.getViewRoot().getViewId().lastIndexOf("register.jspx") > -1 ? true
				: false;
			if (event.getPhaseId() == PhaseId.RESTORE_VIEW){ 
			if (isJspx && !loggedIn && !registerJspx) {
				if (!loginPage) {

					HttpServletResponse response = (HttpServletResponse) fc
							.getExternalContext().getResponse();

					System.out.println("LoggedInCheck Listner Called");
					

					response.sendRedirect(fc.getExternalContext()
							.getRequestContextPath()
							+ "/faces/pages/login.jspx");
					fc.renderResponse();
					fc.responseComplete();
				}
			}}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void beforePhase(PhaseEvent event) {
		if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
		FacesContext facesContext = event.getFacesContext();
		 HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
		 
		 response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
		 response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
		 response.setDateHeader("Expires", 0); //Causes the proxy cache to see the page as "stale"
		 response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
		}


	}

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}