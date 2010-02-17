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
package com.iLabs.spice.controller;

import java.io.IOException;

import javax.faces.application.ViewExpiredException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class TimeoutListener implements PhaseListener {

	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

	public void beforePhase(PhaseEvent event) {
		try{
		FacesContext facesContext = event.getFacesContext();
	    boolean isJspx = facesContext.getViewRoot().getViewId().lastIndexOf(".jspx") > -1 ? true
					: false;
	    boolean loginPage = facesContext.getViewRoot().getViewId().lastIndexOf(
		"login") > -1 ? true : false;
		 if(isJspx&&!loginPage && getPhaseId()==PhaseId.RESTORE_VIEW){			 		
			HttpSession sessionx = (HttpSession) facesContext
					.getExternalContext().getSession(false);

			if (sessionx == null || sessionx.getId().equals("")) {
				HttpServletResponse response = (HttpServletResponse) facesContext
						.getExternalContext().getResponse();
				try {
					
					System.out.println("Session Time out Listner Called");
					response.sendRedirect(facesContext.getExternalContext()
							.getRequestContextPath()
							+ "/faces/pages/login.jspx");
					facesContext.renderResponse();
					facesContext.responseComplete();
				} catch (IOException ex) {
					throw new RuntimeException(ex);
				}

			}
		   }
		}catch (ViewExpiredException e) {
			FacesContext fc = FacesContext.getCurrentInstance();
			HttpServletResponse response1 = (HttpServletResponse) fc
					.getExternalContext().getResponse();

			System.out.println("Session Time out Listner Called");

			try {
				response1.sendRedirect(fc.getExternalContext()
						.getRequestContextPath()
						+ "/faces/pages/login.jspx");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			fc.renderResponse();
			fc.responseComplete();
		}
		
		
	}

	public void afterPhase(PhaseEvent e) {
		
	}
	
	
}
