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
package com.iLabs.spice.services;

import java.util.Collection;

import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.dto.Activity;
/**
 * The interface provide all activities service for open-social site.
 * @author iLabs
 */
public interface IActivity {
	/**
	 * The api create new activity and return number of rows inserted.
	 * @param activity Activity
	 * @return int
	 * @throws SysException
	 */
	int createActivity(Activity activity)throws SysException;
	
	/**
	 * The api get all the activities of user corresponding to userId return collection of activities
	 * @param userId
	 * @return
	 * @throws SysException
	 */ 
	
	Collection<Activity> getActivities(int userId)throws SysException;
	
	/**
	 * The api get all activities of user friend corresponding to userId.
	 * return collection of activities
	 * @param userId
	 * @return
	 * @throws SysException
	 */
	Collection<Activity> getFriendsActivities(int userId)throws SysException;
	
}
