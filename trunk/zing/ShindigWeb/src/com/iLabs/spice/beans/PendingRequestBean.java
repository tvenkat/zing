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
package com.iLabs.spice.beans;

import java.util.List;

import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.dto.UserProfile;
import com.iLabs.spice.handler.AddFriendHandler;

public class PendingRequestBean extends PageCodeBase{
 private List <UserProfile> pendingList;
 private AddFriendHandler addFriendHandler; 
 private boolean hasPendingRequest = true;
 private boolean hasfriendUpdate = false;
 private boolean hasOwnUpdate = false;
 
 public PendingRequestBean() {
	 addFriendHandler= new AddFriendHandler();
	 setPendingList(addFriendHandler.checkFriendRequest());
 }
	


	public List<UserProfile> getPendingList() {
		return pendingList;
	}


	public void setPendingList(List<UserProfile> pendingList) {
		this.pendingList = pendingList;
	}

	public boolean getHasPendingRequest() {
		if(pendingList == null || pendingList.size() ==0){
			hasPendingRequest = false;
		}
		return hasPendingRequest;
	}

	public void setHasPendingRequest(boolean hasPendingRequest) {
		this.hasPendingRequest = hasPendingRequest;
	}
	
	public boolean gethasfriendUpdate() {		
		ProfileBean ownerProfile = (ProfileBean) getSessionScope().get(
				"ownerProfile");
		if ((ownerProfile.getUserFriends().getAllFriendActvityList()) != null &&
				ownerProfile.getUserFriends().getAllFriendActvityList().size() > 0) 
			hasfriendUpdate = true;	
		return hasfriendUpdate;
	}
	
	public void sethasfriendUpdate(boolean hasfriendUpdate) {
		this.hasfriendUpdate = hasfriendUpdate;
	}
	
	public boolean gethasOwnUpdate() {		
		ProfileBean ownerProfile = (ProfileBean) getSessionScope().get(
				"ownerProfile");
		if ((ownerProfile.getUserAuth().getActivities()) != null &&
				ownerProfile.getUserAuth().getActivities().size() > 0) 
			hasOwnUpdate = true;	
		return hasOwnUpdate;
	}
	
	public void sethasOwnUpdate(boolean hasOwnUpdate) {
		this.hasOwnUpdate = hasOwnUpdate;
	}
	
}
