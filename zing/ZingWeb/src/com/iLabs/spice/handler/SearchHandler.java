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

import java.util.List;

import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.dto.UserProfile;
import com.iLabs.spice.services.IPerson;
/**
 * This class handle all action related search user function. 
 * @author iLabs
 * @date 01/08/2008
 */
public class SearchHandler extends PageCodeBase {
	// searchKey holds string provided by user for search.
	private String searchKey;
	// holds  userList of result of search. 
	private List<UserProfile> userList;
  /**
   * This method is used for fetch all the user 
   * on the basis of search key provided by user. 
   * @return String
   */
	public String searchPerson() {
		IPerson person;

		try {
			if (searchKey == null || searchKey.equalsIgnoreCase("")) {
				userList = null;
				return "success";
			}
			person = (IPerson) ServiceLocator.getService("PersonSvc");

			userList = (List<UserProfile>) person.searchProfile(searchKey);
			getSessionScope().put("searchHandler", this);

		} catch (SysException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "success";
	}
	/**
	 * Getter method for searchKey	
	 * @return String
	 */
	public String getSearchKey() {
		return searchKey;
	}
	/**
	 * Setter method for searchKey
	 * @param searchKey
	 */
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	/**
	 * Getter method for userList	
	 * @return List<UserProfile>
	 */
	public List<UserProfile> getUserList() {
		return userList;
	}
	/**
	 * Setter method for userList
	 * @param userList
	 */
	public void setUserList(List<UserProfile> userList) {
		this.userList = userList;
	}

}
