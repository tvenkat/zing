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

import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.beans.ProfileBean;

/**
 * This class holds rendering information of my friends list.
 * @author iLabs
 * @date 01/08/2008
 */
public class MyFriendsHandler extends PageCodeBase {

	//this attribute handle information for the rendering of the component.
	private boolean renderFriendList;
	//this attribute handle information for the rendering of the component.
	private boolean renderNoFriends;

	/**
	 * This function is used whether the friend list is render or not.
	 * @return boolean
	 */
	public boolean getRenderFriendList() {
		displayFriendList();
		return renderFriendList;
	}
	
	/**
	 * This function is used to determine whether to display friend list or not
	 * Number of friends is retrieved using currentProfile bean
	 * If user has no friends then No Friends label is displayed else
	 * friend list is rendered
	 */

	public void displayFriendList() {

		ProfileBean currentProfile = (ProfileBean) getSessionScope().get(
				"currentProfile");
		
		//Checks whether number of user friends is equal to zero or not
		//If user has no friends then display No Friends label
		//Else Friend List is displayed
		if (currentProfile.getUserFriends().getUserFriendsImages().size() == 0)
		{
			renderFriendList = false;
			renderNoFriends = true;
		}else
		{
			renderFriendList = true;
			renderNoFriends = false;
		}
	}

	/**
	 * This function handle the render information of link for no friends.
	 * @return boolean
	 */
	public boolean getRenderNoFriends() {
		return renderNoFriends;
	}


}
