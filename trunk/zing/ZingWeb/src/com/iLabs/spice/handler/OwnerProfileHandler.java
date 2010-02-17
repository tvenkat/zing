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

import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.beans.ProfileBean;

public class OwnerProfileHandler extends PageCodeBase{
	
	/**
	 * This method update current profile with owner and link with current profile.
	 * @return String 
	 */
	
	public String linkMyProfile() {		
		updateCurrentProfileWithOwner();		
		return "lnkMyProfile";
	} 
	
	/**
	 * This method update current profile with owner and link with current profile and 
	 * display current friends List.
	 * @return String.
	 */
	
	public String linkViewAllFriends() {		
		updateCurrentProfileWithOwner();		
		return "lnkViewAllFriends";
	} 
	
	/**
	 * This method update current profile with owner and link with current profile.
	 * @return String.
	 */
	public String getOwnerProfile() {		
		updateCurrentProfileWithOwner();		
		return "lnkHome";
	}  
	
	/**
	 * This method update current profile with owner and link with current profile.
	 */
	public void updateCurrentProfileWithOwner()
	{
		
		ProfileBean ownerProfile = (ProfileBean)getSessionScope().get("ownerProfile");
			if (ownerProfile != null ) {
				ProfileBean currentProfile =(ProfileBean) getSessionScope().get("currentProfile");
				currentProfile.setUserAuth(ownerProfile.getUserAuth());							
				currentProfile.setUserFriends(ownerProfile.getUserFriends());
				getSessionScope().put("currentProfile", currentProfile);
			}
	}
}
