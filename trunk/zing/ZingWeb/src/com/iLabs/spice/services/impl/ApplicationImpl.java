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
import com.iLabs.spice.dto.Application;
import com.iLabs.spice.dto.UserApplications;
import com.iLabs.spice.services.IApplication;
/**
 * The Class implements all the method of IApplication interface and provides application service for open-social site.
 * @author iLabs
 */
public class ApplicationImpl extends ServiceImplBase implements IApplication {

	public ApplicationImpl() {
		config = ConfigurationHandler.getApplicationConfiguration();
	}

	/**
	 * This api delete the application corresponding to userId returns true on success else false.
	 * @param applicationId int
	 * @return boolean
	 * @throws SysException
	 */
	public boolean deleteApplication(int applicationId)
			throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		boolean status = false;
		try {
			Application application = new Application();

			application.setApplicationId(applicationId);

			operationName = "deleteApplication";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			objPersistenceInterface.delete(application, con, operationName);
			status = true;

		} catch (SysException sysException) {
			throw new SysException("AP004", sysException);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				throw new SysException("AP003", sqlException);
			}
		}
		return status;
	}
	/**
	 * The api delete particular application from user applications.
	 * @param userId
	 * @param applicationId
	 * @return boolean
	 * @throws SysException
	 */

	public boolean deleteUserApplication(int userId, int applicationId)
			throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		boolean status = false;
		try {
			UserApplications userApplications = new UserApplications();
			userApplications.setUserId(userId);
			userApplications.setApplicationId(applicationId);

			operationName = "deleteUserApplication";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			objPersistenceInterface
					.delete(userApplications, con, operationName);
			status = true;

		} catch (SysException sysException) {
			throw new SysException("AP004", sysException);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqlException) {
				sqlException.printStackTrace();
				throw new SysException("AP003", sqlException);
			}
		}
		return status;
	}
	
	/**
	 * The api get all the application in the system.
	 * @return Collection<Application>
	 * @throws SysException
	 */

	public Collection<Application> getAllApplication() throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		Collection<Application> appCollection = null;
		try {
			Application application = new Application();
			operationName = "getAllApplication";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			appCollection = objPersistenceInterface.search(application, con,
					operationName);
		} catch (SysException sysException) {
			throw new SysException("AP004", sysException);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqlException) {
				throw new SysException("AP004", sqlException);
			}
		}
		return appCollection;
	}
	
	/**
	 * This api get all the applications corresponding to userId returns collection of UserApllictions objets.
	 * @param userId
	 * @return Collection<UserApplications>
	 * @throws SysException
	 */

	public Collection<UserApplications> getPersonApplications(int userId)
			throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		Collection<UserApplications> personAppsCollection = null;
		Collection<UserApplications> updatedCollection = new ArrayList<UserApplications>();
		try {
			UserApplications userApplication = new UserApplications();
			userApplication.setUserId(userId);
			operationName = "getPersonApplications";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			personAppsCollection = objPersistenceInterface.search(
					userApplication, con, operationName);

			if (personAppsCollection != null) {
				for (UserApplications userAppl : personAppsCollection) {

					Application application = getApplication(userAppl
							.getApplicationId());
					userAppl.setApplication(application);
					updatedCollection.add(userAppl);
				}
			}

		} catch (SysException sysException) {
			throw new SysException("AP004", sysException);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqlException) {
				throw new SysException("AP004", sqlException);
			}
		}
		return updatedCollection;
	}

	/**
	 * The api get details of application corresponding to applicationId.
	 * @param applicationId int
	 * @return Application
	 * @throws SysException
	 */
	public Application getApplication(int applicationId) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		Application appCollection = null;
		try {
			Application application = new Application();
			operationName = "getApplication";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			application = new Application();
			application.setApplicationId(applicationId);
			appCollection = (Application) objPersistenceInterface.read(
					application, con, operationName);
		} catch (SysException sysException) {
			throw new SysException("AP004", sysException);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqlException) {
				throw new SysException("AP004", sqlException);
			}
		}
		return appCollection;
	}

	/**
	 * The api add appliction corresponding to userId.
	 * @param userId int
	 * @param appId int
	 * @return boolean
	 * @throws SysException
	 */
	public boolean addApplication(int userId, int appId) throws SysException {

		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		boolean status = false;
		try {
			operationName = "addApplication";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);

			status = addApplication(userId, appId, con);
		} catch (SysException sysException) {
			throw new SysException("AP004", sysException);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqlException) {

				throw new SysException("AP003", sqlException);
			}
		}
		return status;
	}
	/**
	 * The api add application corresponding to user application
	 * @param userId int
	 * @param appId int
	 * @param con Connection
	 * @return boolean
	 * @throws SysException
	 */

	private boolean addApplication(int userId, int appId, Connection con)
			throws SysException {
		String authAlias = null;
		String operationName = null;
		PersistenceInterface objPersistenceInterface = null;
		boolean status = false;
		try {
			UserApplications userApplications = new UserApplications();
			userApplications.setUserId(userId);
			userApplications.setApplicationId(appId);

			operationName = "addApplication";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			objPersistenceInterface
					.create(userApplications, con, operationName);
			status = true;
		} catch (SysException sysException) {
			throw new SysException("AP004", sysException);
		}
		return status;
	}
	/**
	 * The api create a new application and also add the created application to user application
	 *  corresponding to userId.
	 * @param userId int
	 * @param application Application
	 * @return int
	 * @throws SysException
	 */
		
	public int createAndAddApplication(int userId, Application application)
			throws SysException {
		Connection con = null;
		String authAlias = null;
		String operationName = null;
		int status = 0;
		try {
			operationName = "addApplication";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");

			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			con.setAutoCommit(false);

			int id = createApplication(application, con);
			addApplication(userId, id, con);
			con.commit();

		} catch (SysException sysException) {
			throw new SysException("AP004", sysException);
		} catch (SQLException e) {
			throw new SysException("AP003", e);
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException sqlException) {

				throw new SysException("AP003", sqlException);
			}
		}
		return status;
	}

	/**
	 * The api create new application.
	 * @param application Application
	 * @param con Connection
	 * @return int
	 * @throws SysException
	 */
	private int createApplication(Application application, Connection con)
			throws SysException {
		String authAlias = null;
		String operationName = null;
		PersistenceInterface objPersistenceInterface = null;
		int appId = 0;
		try {

			operationName = "createApplication";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			appId = objPersistenceInterface.createAndReturnId(application, con,
					operationName);

		} catch (SysException sysException) {
			throw new SysException("AP004", sysException);
		}
		return appId;
	}
}
