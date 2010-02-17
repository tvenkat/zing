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

import java.util.ArrayList;
import java.util.List;

import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.beans.PendingRequestBean;
import com.iLabs.spice.beans.ProfileBean;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.dto.UserAuth;
import com.iLabs.spice.dto.UserProfile;
import com.iLabs.spice.services.IPerson;

/**
 * This class handle all action related to adding a friend
 * like send friend request, accept or deny friend request.
 * 
 * @author iLabs
 * @date 01/08/2008
 */

public class AddFriendHandler extends PageCodeBase {

	/**
	 * This function is called when user click on add friend link,
	 * while visiting one's profile.
	 * It sends friend request, setting the pending status as yes 
	 * It redirects to addFriend page
	 * 
	 * @return String
	 */
	
	public String addFriendAction() {

		ProfileBean currentProfile = (ProfileBean) getSessionScope().get(
				"currentProfile");
		ProfileBean ownerProfile = (ProfileBean) getSessionScope().get(
				"ownerProfile");
		UserAuth userAuth = ownerProfile.getUserAuth();
		UserAuth userSearched = currentProfile.getUserAuth();
		String result = "failure";
		IPerson person;
		try {
			person = (IPerson) ServiceLocator.getService("PersonSvc");
			boolean added = person.addFriend(userAuth.getUserId(), userSearched
					.getUserId());
			result = "success";

		} catch (SysException e) {
			e.printStackTrace();
		}

		return "success";

	}
	
	/**
	 * This function is called when user login to spice
	 * It shows the pending friend requests, if any, on user's home page
	 * It also brings complete profile of the persons who sent friend request,
	 * who sent friend request.
	 * 
	 * @return List<UserProfile>
	 */

	public List<UserProfile> checkFriendRequest() {
		ProfileBean ownerProfile = (ProfileBean) getSessionScope().get(
				"ownerProfile");
		UserAuth userAuth = ownerProfile.getUserAuth();

		IPerson person;
		List<UserProfile> pendingList = new ArrayList<UserProfile>();
		try {
			person = (IPerson) ServiceLocator.getService("PersonSvc");
			pendingList = (List<UserProfile>) person
					.checkFriendRequest(userAuth.getUserId());

		} catch (SysException e) {
			e.printStackTrace();
		}
		return pendingList;
	}	

	/**
	 * This function is called when user accept friend request
	 * It sets the pending status as no
	 * and shows the person in user friend list 
	 *  
	 * @return String
	 */

	public String acceptFriendRequest() {
		ProfileBean ownerProfile = (ProfileBean) getSessionScope().get(
				"ownerProfile");
		UserAuth userAuth = ownerProfile.getUserAuth();

		int userId = userAuth.getUserId();
		int friendId = Integer.parseInt(getRequestParam().get("userId")
				.toString());
		IPerson person;
		try {
			person = (IPerson) ServiceLocator.getService("PersonSvc");
			person.acceptFriendRequest(userId, friendId);
			PendingRequestBean pendingRequestBean = new PendingRequestBean();
			getSessionScope().put("pendingRequestBean", pendingRequestBean);

		} catch (SysException e) {
			e.printStackTrace();
		}
		return "success";
	}

	/**
	 * This function is called when user deny to friend request
	 * 
	 * @return String
	 */
	
	public String denyFriendRequest() {
		ProfileBean ownerProfile = (ProfileBean) getSessionScope().get(
				"ownerProfile");
		UserAuth userAuth = ownerProfile.getUserAuth();
		int userId = userAuth.getUserId();
		int friendId = Integer.parseInt(getRequestParam().get("userId")
				.toString());
		IPerson person;
		try {
			person = (IPerson) ServiceLocator.getService("PersonSvc");
			person.denyFriendRequest(userId, friendId);
			PendingRequestBean pendingRequestBean = new PendingRequestBean();
			getSessionScope().put("pendingRequestBean", pendingRequestBean);

		} catch (SysException e) {
			e.printStackTrace();
		}
		return "success";
	}

	
}
