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

import com.iLabs.spice.dto.UserAuth;
import com.iLabs.spice.dto.UserFriends;
import com.iLabs.spice.dto.UserProfile;

public class ProfileBean {
	UserAuth userAuth;
	UserFriends userFriends;
	
	public ProfileBean()
	{   
		UserProfile userProfile = new UserProfile();
		this.userAuth = new UserAuth();
		userAuth.setProfile(userProfile);
		this.userFriends = new UserFriends();
		
	}
	
	public UserAuth getUserAuth() {
		return userAuth;
	}
	public void setUserAuth(UserAuth userAuth) {
		this.userAuth = userAuth;
	}
	public UserFriends getUserFriends() {
		return userFriends;
	}
	public void setUserFriends(UserFriends userFriends) {
		this.userFriends = userFriends;
	}
}
