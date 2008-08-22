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

import java.util.Collection;

import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.beans.ProfileBean;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.dto.UserScrap;
import com.iLabs.spice.services.IPerson;
/**
 * This class is used to handle functionality related to scrap like
 * post scrap, show scrap, count no of scraps and delete scrap.
 * @author iLabs
 * @date 01/08/2008
 */
public class ScrapHandler extends PageCodeBase {
	//this attribute handle information for the rendering of the component.
	boolean scrapRender = true;
	// scrap count message for count no of scraps.
	private String scrapCountMessage;
	
	
	/**
	 * This function is used to generate message which tells number of scraps of user.
	 * This message is used to display no of scraps in scrap book of a user.
	 * 
	 * @return String
	 */
	
	public String getScrapCountMessage() {
		String msg = "Records ";
		Collection list = (Collection)getSessionScope().get("userScrapsList");
		int count = 0;
		if(list != null){
			count = list.size();
		}
		return msg + count + " of " + count;
	}

	/**
	 * Setter for scrapCountMessage.
	 * @param scrapCountMessage String
	 */
	public void setScrapCountMessage(String scrapCountMessage) {
		this.scrapCountMessage = scrapCountMessage;
	}

	
	/**
	 * This function is used to post scrap to a friend of a user using sendScrap service.
	 * senderId is retrieved from ownerProfile and recieverId is used from currentProfile.
	 *  
	 * @return String
	 */
	
	public String scrapAction() {
		String result = "failure";
		try {
			UserScrap userScrap = (UserScrap) resolveExpression("#{userScrap}");
			IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
			ProfileBean profileBean = (ProfileBean) resolveExpression("#{sessionScope.currentProfile}");
			ProfileBean ownerProfile = (ProfileBean) resolveExpression("#{sessionScope.ownerProfile}");
			userScrap.setSenderId(ownerProfile.getUserAuth().getUserId());
			userScrap = person.sendScrap(profileBean.getUserAuth(), userScrap);
			showScrap();
			result = "success";
		} catch (SysException e) {
			e.printStackTrace();
		}
		return result;

	}
	
	/**
	 * This function is used to display scraps of a person using getScraps service. 
	 * User is redirected to the scrap book page.
	 * 
	 * @return String
	 */

	public String showScrap() {
		try {
			ProfileBean currentProfile = (ProfileBean) resolveExpression("#{sessionScope.currentProfile}");
			ProfileBean ownerProfile = (ProfileBean) resolveExpression("#{sessionScope.ownerProfile}");
			
			// This check determine whether to display postScrap html component or not.
			// If user is the owner of the profile, then postScrap html component is not rendered.
			if (currentProfile.getUserAuth().getUserId() == ownerProfile
					.getUserAuth().getUserId()) {
				setScrapRender(false);
			}
			IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
			
			Collection<UserScrap> userScrapsList = person.getScraps(currentProfile
					.getUserAuth().getUserId());
			int ownerId=ownerProfile.getUserAuth().getUserId();	
			int currentProfileUserId=currentProfile.getUserAuth().getUserId();	
			for(UserScrap userScrap:userScrapsList)
			{
				userScrap.setRenderd(true);
				// If user is the owner of the scrap, then he can delete the scrap.
				 if(ownerId!=userScrap.getSenderId()&&currentProfileUserId!=ownerId)
				{
					 userScrap.setRenderd(false);
				}
			}
			
			getSessionScope().put("userScrapsList", userScrapsList);
		} catch (Exception e) {

		}

		return "lnkMyScrapbook";
	}

	/**
	 * This function is used to delete scrap.
	 * 
	 * @return String
	 */
	
	public String deleteScrap() {
		try {			
			IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
			int scrapId = Integer.parseInt(getRequestParam().get("scrapId").toString());
			
			person.deleteScrap(scrapId);
			
			showScrap();
			
		} catch (Exception e) {

		}
		return "lnkMyScrapbook";
	}

	/**
	 * Getter for scrapRender.
	 * @return boolean
	 */
	public boolean getScrapRender() {
		return scrapRender;
	}

	/**
	 * Setter for scrapRender.
	 * @param scrapRender boolean
	 */
	public void setScrapRender(boolean scrapRender) {
		this.scrapRender = scrapRender;
	}
	
}
