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
package com.iLabs.spice.handler;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.beans.ProfileBean;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.dto.UserAuth;
import com.iLabs.spice.dto.UserFriends;
import com.iLabs.spice.services.IPerson;

/**
 * This class is used to handle functionality related to login like 
 * authenticate user,forgot password and logout.
 * @author iLabs
 * @date 01/08/2008
 */
public class LoginHandler extends PageCodeBase{

	/**
	 * This function is called when user enters the site.
	 * It first authenticates the user and after successful authentication, user info is 
	 * set into currentProfile bean and ownerProfile bean.
	 * On successful authentication user is redirected to home page.
	 * 
	 * @return String 
	 */
	
	public String loginAction()  {
		String result="failure";
		try {			
			ProfileBean ownerProfile = (ProfileBean)  getSessionScope().get("ownerProfile");
			ProfileBean currentProfile = (ProfileBean)  getSessionScope().get("currentProfile");
			if(ownerProfile==null)
			{
				ownerProfile = new ProfileBean();
			}
			if(currentProfile==null)
			{
				currentProfile = new ProfileBean();
			}
			IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
			UserAuth authPerson = person.authenticateUser(currentProfile.getUserAuth().getUserName(),currentProfile.getUserAuth().getUserPassword());
			//This condition checks if the authPerson returned from authentication service is null or not.
			//If the user who enters the site is an authenticated user, the user's info and his friends info is stored in 
			//currentProfile as well as ownerProfile bean.
		     if(authPerson!=null && authPerson.getUserName()!=null){	    	 
		    	
		    	 UserFriends userFriends = person.getFriends(authPerson.getUserId());		    	    	 		    	
		    	 ownerProfile.setUserAuth(authPerson);
		    	 ownerProfile.setUserFriends(userFriends);
		    	 currentProfile.setUserAuth(authPerson);
		    	 currentProfile.setUserFriends(userFriends);
		    	 getSessionScope().put("ownerProfile", ownerProfile);
		    	 getSessionScope().put("currentProfile", currentProfile);
		    	 result="success";
		     }
		     else{               // if user is not an authenticate user, then error message is generated.
		    	 FacesMessage message = new FacesMessage("Please Check Username and password");			
				  FacesContext.getCurrentInstance().addMessage("login:user_password", message);
		     }
		     
		    		   
		} catch (SysException e) {			
			e.printStackTrace();
		}
    return result; 
	
 	}
	
	/**
	 * This function is used to send mail to the user who forgets password.
	 * User enters his user name which is used to retrieve his password and emailId.
	 * User's password is sent to corresponding mail id using mail service.
	 * 
	 * @return void
	 */
	
	public void forgotPwd()   {
		
		IPerson person;
		try {
			ProfileBean currentProfile = (ProfileBean)  getSessionScope().get("currentProfile");
			UserAuth userAuth = currentProfile.getUserAuth();
			person = (IPerson)ServiceLocator.getService("PersonSvc");			
			String userName=userAuth.getUserName();
		    UserAuth userUpdate= person.getPassword(userName);	
		    //this checks whether the provided user name exists or not.
		    if(userUpdate != null){
		    	FacesMessage message = new FacesMessage("Your password has been sent to your mail account");			
				FacesContext.getCurrentInstance().addMessage("forgotPassword:submit", message);
		    }
		    else{
		    	FacesMessage message = new FacesMessage("The user name does not exist!!!");			
				FacesContext.getCurrentInstance().addMessage("forgotPassword:submit", message);
		    }
		} catch (SysException e) {			
			e.printStackTrace();
		}			
	}
   
	/**
	 * This function is called when user logs out from the system.
	 * It invalidates the session info.
	 * 
	 * @return String "success" is returned which determines the navigation. User is redirected to login page.
	 */
   public String logout()   {
		 HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		 session.invalidate();
	return "success";
	   
   }
}