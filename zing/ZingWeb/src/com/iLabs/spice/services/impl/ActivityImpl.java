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
package com.iLabs.spice.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import com.iLabs.spice.common.config.ConfigurationHandler;
import com.iLabs.spice.common.core.ServiceImplBase;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.persistence.PersistenceInterface;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.common.utils.DBUtility;
import com.iLabs.spice.dto.Activity;
import com.iLabs.spice.dto.Image;
import com.iLabs.spice.dto.UserFriends;
import com.iLabs.spice.services.IActivity;
import com.iLabs.spice.services.IPerson;

/**
 * The Class implements all the method of IActivity interface for implementing activity service of 
 * Open-Social site.
 * @author iLabs
 */
public class ActivityImpl extends ServiceImplBase implements IActivity {
	/**
	 * Public constructor 
	 */
	public ActivityImpl() {
		config = ConfigurationHandler.getApplicationConfiguration();
	}

	/**
	 * The api create new activity and return number of rows inserted.
	 * @param activity Activity
	 * @return int
	 * @throws SysException
	 */
	
	public int createActivity(Activity activity) throws SysException{
		String authAlias = null;
	 	String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		int val =0;
		try { 
			operationName = "createActivity";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			val= objPersistenceInterface
					.create(activity, con, operationName);
		} catch (SysException sysException) { 
			new SysException("AP004", sysException);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqlException) {
				
				throw new SysException("AP003", sqlException);
			}
		}
		return val;
	}
	
	/**
	 * The api get all the activities of user corresponding to userId return collection of activities
	 * @param userId
	 * @return
	 * @throws SysException
	 */
	
	public Collection<Activity> getActivities(int userId) throws SysException{
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		Activity activity= new Activity();
		Collection<Activity> activityList =null;
		try {
			activity.setUserId(userId);
			operationName = "getActivity";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			activityList=objPersistenceInterface.search(activity, con, operationName);
		} catch (SysException sysException) { 
			new SysException("AP004", sysException);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqlException) {				
				throw new SysException("AP003", sqlException);
			}
		}
		return activityList;
	}
	
	/**
	 * The api get all activities of user friend corresponding to userId.
	 * return collection of activities
	 * @param userId
	 * @return
	 * @throws SysException
	 */
	
	public Collection<Activity> getFriendsActivities(int userId) throws SysException{
		Collection<Activity> allActivity=new ArrayList<Activity>();
		try {
		
			IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
			UserFriends userFriends= person.getFriends(userId);
			
			for(Image image:userFriends.getUserFriendsImages())
			{
				allActivity.addAll(getActivities(image.getUserId()));
			}
		} catch (SysException sysException) { 
			new SysException("AP004", sysException);
		} 
		return allActivity;
	}

}
