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
package com.iLabs.spice.services;

import java.util.Collection;

import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.dto.Application;
import com.iLabs.spice.dto.UserApplications;
import com.iLabs.spice.dto.UserAuth;
/**
 * The interface provide all application service for open-social site.
 * @author iLabs
 */
public interface IApplication {
	/**
	 * This api get all the applications corresponding to userId returns collection of UserApllictions objets.
	 * @param userId
	 * @return Collection<UserApplications>
	 * @throws SysException
	 */
	Collection<UserApplications> getPersonApplications(int userId)throws SysException;
	/**
	 * This api delete the application corresponding to userId returns true on success else false.
	 * @param applicationId int
	 * @return boolean
	 * @throws SysException
	 */
	boolean deleteApplication(int applicationId)throws SysException;
	/**
	 * The api add appliction corresponding to userId.
	 * @param userId int
	 * @param appId int
	 * @return boolean
	 * @throws SysException
	 */
	boolean addApplication(int userId,int appId)throws SysException;
	/**
	 * The api get all the application in the system.
	 * @return Collection<Application>
	 * @throws SysException
	 */
	Collection<Application> getAllApplication()throws SysException;
	/**
	 * The api get details of application corresponding to applicationId.
	 * @param applicationId int
	 * @return Application
	 * @throws SysException
	 */
	Application getApplication(int applicationId)throws SysException;
	
	/**
	 * The api create a new application and also add the created application to user application
	 *  corresponding to userId.
	 * @param userId int
	 * @param application Application
	 * @return int
	 * @throws SysException
	 */
			
	int createAndAddApplication(int userId, Application  application)throws SysException;
	/**
	 * The api delete particular application from user applications.
	 * @param userId
	 * @param applicationId
	 * @return boolean
	 * @throws SysException
	 */
	boolean deleteUserApplication(int userId,int applicationId)throws SysException;
}
