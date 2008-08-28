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
package com.iLabs.spice.handler;

import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.beans.ProfileBean;
import com.iLabs.spice.beans.RegisterBean;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.dto.CodeImage;
import com.iLabs.spice.dto.UserAuth;
import com.iLabs.spice.dto.UserProfile;
import com.iLabs.spice.services.IPerson;
/**
 * This class handle all action related registration of user 
 * 
 * @author iLabs
 * @date 01/08/2008
 */
public class RegisterHandler extends PageCodeBase{
	
	/**
	 * This function is called when user wants to register the site
	 * and click on join spice link on login page.
	 * It randomly brings verification code image from CodeImage data object.
	 * It also randomly changes the code image on page refresh.
	 * It renders the user to registration page.
	 * 
	 * @return String 
	 */
	
	public String joinSpiceAction() {

		IPerson person = null;
		try {
			getSessionScope().remove("codeImage");
			getSessionScope().remove("profileBean");
			person = (IPerson) ServiceLocator.getService("PersonSvc");
			Random rm = new Random();
			int codeId;
			codeId = rm.nextInt(11);
			while (codeId == 0) {
				codeId = rm.nextInt(11);
			}
			CodeImage codeImage = person.getCodeImage(codeId);
			getSessionScope().put("codeImage", codeImage);
		} catch (SysException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	
	/**
	 * This function is called when user submits the data to register the site.
	 * It first validates the entered data 
	 * After successful validation, authenticates whether user already exists, if not,
	 * then user info is set into currentProfile bean.
	 * On successful registration user is redirected to login page.
	 * 
	 * @return String 
	 */
	
	public String registerAction() {
		boolean flag = false;
		ProfileBean profileBean = (ProfileBean)getSessionScope().get("currentProfile");
		UserAuth userAuth=profileBean.getUserAuth();

		UserProfile userProfile = userAuth.getProfile();

		CodeImage codeImage = (CodeImage)getSessionScope().get("codeImage");
		
		RegisterBean registerBean = (RegisterBean)getRequestScope().get("registerBean");
		
		String email = userAuth.getUserEmail();
		
		String result = "failure";
						
		if (!(registerBean.getConfirmPassword().equals(userAuth.getUserPassword()))) {
			FacesMessage message = new FacesMessage("Password do not match");			
			  FacesContext.getCurrentInstance().addMessage("frmRegister:Pwd", message);
			  flag = true;
			}
		
		if ((email.indexOf("@")< 0)|| (email.indexOf(".")< 0)) {
			FacesMessage message = new FacesMessage("Invalid EMail Address");			
			  FacesContext.getCurrentInstance().addMessage("frmRegister:MailId", message);
			  flag = true;
		}
		
		if (!(codeImage.getCodeEntered().equals(codeImage.getCodeImageCaption()))) {
			FacesMessage message = new FacesMessage("Invalid Code");			
			  FacesContext.getCurrentInstance().addMessage("frmRegister:Code", message);
			  flag = true;
		}	
		if (flag == true)
			return result;
		
		IPerson person;		
		
		try {
			person = (IPerson) ServiceLocator.getService("PersonSvc");
			int user_Id=person.createPerson(userAuth);			    
				userProfile.setUserId(user_Id);
				userProfile.setUserImage("/images/default.jpg");
			    person.createProfile(userProfile);	
			    FacesMessage message = new FacesMessage("Registered Successfully in Zing !!!");			
				FacesContext.getCurrentInstance().addMessage(null, message);
				result = "success";						
		} catch (SysException e) {			
			FacesMessage message = new FacesMessage("User Already Exists");			
			  FacesContext.getCurrentInstance().addMessage(null, message);
		}		
		
		return result;
	}
}
	


