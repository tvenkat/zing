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
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.iLabs.spice.common.config.ConfigurationHandler;
import com.iLabs.spice.common.core.ServiceImplBase;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.persistence.PersistenceInterface;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.common.utils.DBUtility;
import com.iLabs.spice.dto.Activity;
import com.iLabs.spice.dto.CodeImage;
import com.iLabs.spice.dto.Image;
import com.iLabs.spice.dto.SendMail;
import com.iLabs.spice.dto.UserAlbum;
import com.iLabs.spice.dto.UserApplications;
import com.iLabs.spice.dto.UserAuth;
import com.iLabs.spice.dto.UserFriends;
import com.iLabs.spice.dto.UserPresence;
import com.iLabs.spice.dto.UserProfile;
import com.iLabs.spice.dto.UserScrap;
import com.iLabs.spice.services.IActivity;
import com.iLabs.spice.services.IApplication;
import com.iLabs.spice.services.IPerson;
/**
 * The Class implements all the method of IPerson interface for implementing person service of Open-Social site.
 * @author snrkumar
 */

public class PersonImpl extends ServiceImplBase implements IPerson {
    /**
     * Public constructor
     */
	public PersonImpl() {
		config = ConfigurationHandler.getApplicationConfiguration();
	}

	
	/**
	 * The api add new friend corresponding to userId and friendId return true if successfully added
	 * otherwise return false.
	 * @param userId int 
	 * @param friendId int
	 * @return boolean
	 * @throws SysException
	 */
	 
	public boolean addFriend(int userId, int friendId) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		UserFriends userFriends = new UserFriends();
		boolean friendAdded = false;
		try {

			operationName = "addFriend";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			userFriends.setUserId(userId);
			userFriends.setFriendId(friendId);
			userFriends.setPending("yes");
			objPersistenceInterface.create(userFriends, con, operationName);
			friendAdded = true;
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
		return friendAdded;
	}

	/**
	 * The api add new album and return true if successfully added otherwise return false.
	 * @param object UserAlbum
	 * @return boolean
	 * @throws SysException
	 */
	
	
	public boolean addToAlbum(UserAlbum userAlbum) throws SysException {

		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		boolean status = false;
		try {
			List<Image> albumImageList = userAlbum.getImage();
			for (Image image : albumImageList) {
				operationName = "addToAlbum";
				authAlias = config.getPropertyAsString("Entity[@operation='"
						+ operationName + "'].sql.@authenticationAlias");
				// Use the alias to get the connection
				con = DBUtility.getConnection(authAlias);
				objPersistenceInterface = (PersistenceInterface) ServiceLocator
						.getService("PersistenceSvc");
				objPersistenceInterface.createAndReturnId(image, con, operationName);
				status = true;
			}
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
	 * The api authenticate a user corresponding to username and password and return the object
	 * of UserAuth corresponding to username and password.  
	 * @param name String
	 * @param password String
	 * @return UserAuth
	 * @throws SysException
	 */

	public UserAuth authenticateUser(String name, String password)
			throws SysException {

		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		UserAuth userAuth = new UserAuth();
		try {
			operationName = "getUserAuth";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");

			userAuth.setUserName(name);
			userAuth.setUserPassword(password);
			userAuth = (UserAuth) objPersistenceInterface.read(userAuth, con,
					operationName);
			if (userAuth != null) {
				userAuth = getPersonDetails(userAuth.getUserId());
			}
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

		return userAuth;
	}

	
	/**
	 * Create a new person object and return no the userId created for the ne person object.
	 * @param person UserAuth
	 * @return int
	 * @throws SysException
	 */
	
	public int createPerson(UserAuth person) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		int val=0;
		try {
			operationName = "createPerson";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			 val = objPersistenceInterface
					.createAndReturnId(person, con, operationName);
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
		return val;
	}

	/**
	 * The api delete particular person corresponding to userId and return true if successfully deleted
	 * otherwise return false.
	 * @param userId int
	 * @return boolean
	 * @throws SysException
	 */
	
	public boolean deletePerson(int userId) throws SysException {

		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		boolean status = false;
		try {
			UserAuth userAuth = new UserAuth();
			userAuth.setUserId(userId);
			operationName = "deletePerson";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			objPersistenceInterface.delete(userAuth, con, operationName);

			status = true;
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
	 * The api get all friends corresponding to userId.
	 * @param userId int
	 * @return UserFriends
	 * @throws SysException
	 */
	public UserFriends getFriends(int userId) throws SysException {

		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		UserFriends userFriends = new UserFriends();
		try {

			operationName = "getFriends";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			userFriends.setUserId(userId);		
			List<Image> userFriendsImages = (List<Image>) objPersistenceInterface
					.search(userFriends, con, operationName);
			userFriends.getUserFriendsImages().addAll(userFriendsImages);
			IActivity activity =(IActivity)ServiceLocator.getService("ActivitySvc");
			for(Image image :userFriendsImages ){
				List<Activity> allfriendActvitiies =(List<Activity>)activity.getActivities(image.getUserId());
				userFriends.getAllFriendActvityList().addAll(allfriendActvitiies);
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
		return userFriends;
	}

	/**
	 * The api find a UserAuth object corresponding to userId.
	 * @param userId int
	 * @return UserAuth
	 * @throws SysException
	 */
	public UserAuth getPerson(int userId) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		UserAuth userAuth = new UserAuth();
		try {
			operationName = "getPerson";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");

			userAuth.setUserId(userId);
			userAuth = (UserAuth) objPersistenceInterface.read(userAuth, con,
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

		return userAuth;
	}

	
	/**
	 * The api get user album corresponding to userID.
	 * @param userId int
	 * @return Collection<UserAlbum> 
	 * @throws SysException
	 */
	
	public Collection<UserAlbum> getPersonAlbum(int userId) throws SysException {

		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		List<Image> images = null;
		List<UserAlbum> userAlbumCollection = new ArrayList<UserAlbum>();
		try {
			UserAlbum userAlbum = new UserAlbum();
			Image image = new Image();

			operationName = "getPersonAlbum";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			image.setUserId(userId);
			images = (List<Image>) objPersistenceInterface.search(image, con,
					operationName);
			userAlbum.getImage().addAll(images);
			userAlbumCollection.add(userAlbum);

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

		return userAlbumCollection;
	}

	/**
	 * The api get details like profile, actvities, friends of corresponding to userId.
	 * @param userId int
	 * @return UserAuth
	 * @throws SysException
	 */

	public UserAuth getPersonDetails(int userId) throws SysException {

		UserAuth userAuth = new UserAuth();
		try {
			userAuth = getPerson(userId);
			if (userAuth != null) {
				// if user data available get activity
				IActivity activity = (IActivity) ServiceLocator
						.getService("ActivitySvc");
				List<Activity> actvityList = (List<Activity>) activity
						.getActivities(userId);
				userAuth.getActivities().addAll(actvityList);

				// if user data available get profile
				userAuth.setProfile(getProfile(userId));
				// if user data available get profile
				IApplication application = (IApplication) ServiceLocator
						.getService("ApplicationSvc");
				List<UserApplications> applicationList = (List<UserApplications>) application
						.getPersonApplications(userId);
				userAuth.getApplications().addAll(applicationList);

			}

		} catch (SysException sysException) {
			throw new SysException("AP004", sysException);

		}

		return userAuth;
	}
	
	/**
	 * The api get all the scraps corresponding to userId.
	 * @param userId int
	 * @return Collection<UserScrap> 
	 * @throws SysException
	 */
	
	public Collection<UserScrap> getScraps(int userId) throws SysException {

		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		Collection userScrapList = null;
		try {
			operationName = "getScrap";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			UserAuth userAuth = new UserAuth();
			userAuth.setUserId(userId);
			userScrapList = objPersistenceInterface.search(userAuth, con,
					operationName);
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

		return userScrapList;
	}

	/**
	 * The api find that particular personId is friend of userId or not.
	 * @param userId int
	 * @param personId int
	 * @return boolean
	 * @throws SysException
	 */
	public boolean isFriend(int userId, int personId) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		UserFriends userFriends = new UserFriends();
		boolean friendAdded = false;
		try {
			operationName = "isFriend";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			userFriends.setUserId(userId);
			userFriends.setFriendId(personId);
			userFriends = (UserFriends) objPersistenceInterface.read(
					userFriends, con, operationName);
			if (userFriends != null) {
				friendAdded = true;
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
		return friendAdded;
	}

	
	
	/**
	 * The api search all user corresponding to username and return Collection of UserAuth objects.
	 * @param name String
	 * @return Collection<UserAuth>
	 * @throws SysException
	 */
	public Collection<UserAuth> searchPerson(String name) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		List<UserAuth> userList = new ArrayList<UserAuth>();
		try {
			UserAuth userAuth = new UserAuth();
			name = "%" + name + "%";
			System.out.println(name);
			userAuth.setUserName(name);
			operationName = "searchPerson";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			userList = (List<UserAuth>) objPersistenceInterface.search(
					userAuth, con, operationName);

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

		return userList;
	}

	
	/**
	 * The api Post a scrap object corresponding to UserAuth object
	 * @param auth UserAuth
	 * @param object UserScrap
	 * @return UserScrap
	 * @throws SysException
	 */
	public UserScrap sendScrap(UserAuth user, UserScrap scrap) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		try {
			scrap.setRecieverId(user.getUserId());
			operationName = "createScrap";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			int scrapId = objPersistenceInterface.createAndReturnId(scrap, con,
					operationName);
			scrap.setScrapId(scrapId);
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
		return scrap;
	}

	
	/**
	 * The api updates userDetails 
	 * @param person UserAuth
	 * @return UserAuth
	 * @throws SysException
	 */
	
	public UserAuth updatePerson(UserAuth person) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		try {
			operationName = "updatePerson";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			int noOfRecordUpdated = objPersistenceInterface.update(person, con,
					operationName);
			if (noOfRecordUpdated == 0) {
				person = null;
				System.out.println("Record Not apdated");
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

		return person;
	}

	
	/**
	 * The api create a new user profile and return the generated profile id of the user.
	 * @param profile UserProfile
	 * @return int
	 * @throws SysException
	 */
	
	public int createProfile(UserProfile profile) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		int profileId;
		try {
			operationName = "createProfile";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			profileId = objPersistenceInterface.createAndReturnId(profile, con,
					operationName);
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
		return profileId;
	}

	/**
	 *The api get user profile corresponding to userId and 
	 * return UserProfile object of searched profile. 
	 * @param userId
	 * @return UserProfile
	 * @throws SysException
	 */
	
	public UserProfile getProfile(int userId) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		UserProfile userProfile = new UserProfile();
		try {

			userProfile.setUserId(userId);
			operationName = "getProfile";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			userProfile = (UserProfile) objPersistenceInterface.read(
					userProfile, con, operationName);
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
		return userProfile;
	}
	
	/**
	 * The api search all user profile corresponding to user first name or last name and 
	 * return collection of searched profile.
	 * @param name
	 * @return Collection<UserProfile>
	 * @throws SysException
	 */
	

	public Collection<UserProfile> searchProfile(String name)
			throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		List<UserProfile> userProfileList = null;
		try {
			UserProfile userProfile = new UserProfile();
			StringTokenizer st = new StringTokenizer(name);
			int noOfToken = st.countTokens();
			if (noOfToken > 1) {
				userProfile.setFirstName("%" + st.nextElement().toString()
						+ "%");
				userProfile
						.setLastName("%" + st.nextElement().toString() + "%");
			} else {
				String anyName = "%" + st.nextElement().toString() + "%";
				userProfile.setFirstName(anyName);
				userProfile.setLastName(anyName);
			}
			operationName = "searchProfile";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			userProfileList = (List<UserProfile>) objPersistenceInterface
					.search(userProfile, con, operationName);
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
		return userProfileList;
	}
	
	/**
	 * The api update userprofile of user.
	 * @param profile
	 * @return UserProfile 
	 * @throws SysException
	 */
	

	public UserProfile updateProfile(UserProfile profile) throws SysException {

		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;

		try {
			operationName = "updateProfile";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			int noOfRecordUpdated = objPersistenceInterface.update(profile,
					con, operationName);
			if (noOfRecordUpdated == 0) {
				profile = null;
				System.out.println("Record Not apdated");
			}
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
		return profile;

	}

	
	/**
	 * The api gives the online status of user return true if user is online otherwise return false
	 * @param userPresence UserPresence
	 * @return boolean
	 * @throws SysException
	 */

	public boolean updateOnlineStatus(UserPresence userPresence)
			throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		try {
			operationName = "updateStatus";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");

			int rowCount = objPersistenceInterface.update(userPresence, con,
					operationName);
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

		return true;
	}

	/**
	 * The api get CodeImage object corresponding to codeImage Id.
	 * @param codeImageId int
	 * @return CodeImage
	 * @throws SysException
	 */
	
	public CodeImage getCodeImage(int codeImageId) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		CodeImage codeImage = new CodeImage();
		try {
			operationName = "getCodeImage";
			codeImage.setCodeImageId(codeImageId);

			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			codeImage = (CodeImage) objPersistenceInterface.read(codeImage,
					con, operationName);
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

		return codeImage;
	}

	/**
	 * The api delete a image from album corresponding to imageId return true if successfully deleted else false.
	 * @param imageId int
	 * @return boolean
	 * @throws SysException
	 */
	
	public boolean deleteAlbum(int imageId) throws SysException {	
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;

		try {
			operationName = "deleteImage";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			Image image  = new Image();
			image.setImageId(imageId);
			int noOfRecordUpdated = objPersistenceInterface.delete(image,
					con, operationName);
			if (noOfRecordUpdated == 0) {	
				
				System.out.println("Record Not apdated");
				
			}
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
		
		
		return true;
	}
	
	/**
	 * The api send a mail corresponding to mail message which contain sender and receiver mailId 
	 * @param mailMessage SendMail
	 * @return boolean
	 * @throws SysException
	 */
	
	public boolean sendMail(SendMail sendMail)throws SysException{
		// Setup mail server
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "192.168.100.28");

		// Get a mail session
		Session session = Session.getDefaultInstance(props, null);
		try{
		// Define a new mail message
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(sendMail.getMailFrom()));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendMail.getMailTo()));
		message.setSubject(sendMail.getSubject());

		// Create a message part to represent the body text
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText(sendMail.getContents());

		// use a MimeMultipart as we need to handle the file attachments
		Multipart multipart = new MimeMultipart();

		// add the message body to the mime message
		multipart.addBodyPart(messageBodyPart);

		// add any file attachments to the message
		

		// Put all message parts in the message
		message.setContent(multipart);

		// Send the message
		Transport.send(message);
		} catch (Exception sysException) {
			throw new SysException("AP004", sysException);
		} 
		return true;
	}

	
	/**
	 * The api used to get password of user.
	 * @param userName String
	 * @return UserAuth
	 * @throws SysException
	 */
	public UserAuth getPassword(String userName) throws SysException {
	
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		UserAuth userAuth = new UserAuth();
		String CONTENT="Your password for username "+userName+" is " ;
		try {
			operationName = "getUserPassword";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");

			userAuth.setUserName(userName);			
			userAuth = (UserAuth) objPersistenceInterface.read(userAuth, con,
					operationName);			
			if (userAuth != null) {
			SendMail sendMail = new SendMail();
			sendMail.setMailFrom("ilabs.administrator@impetus.co.in");
			sendMail.setMailTo(userAuth.getUserEmail());
			sendMail.setContents(CONTENT+userAuth.getUserPassword());
			sendMail.setSubject("Password");
			sendMail(sendMail);
			}
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

		return userAuth;
	}
	
	/**
	 * The api delete a Scrap corresponding to scrapId.
	 * @param scrapId int
	 * @return boolean
	 * @throws SysException
	 */
	
	public boolean deleteScrap(int scrapId) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		UserScrap userScrap = new UserScrap();
		
		try { 
			operationName = "deleteScrap";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			
			userScrap.setScrapId(scrapId);
			 objPersistenceInterface.delete(userScrap, con,
					operationName);					
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

		return true;
	}
	
	 /**
	  * The api used to check all friend request corresponding to userId and return collection of user profile 
	  * who had sent request to that user. 
	  * @param userId int
	  * @return  Collection<UserProfile>
	  * @throws SysException
	  */

	public Collection<UserProfile> checkFriendRequest(int userId) throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		UserFriends userFriends = new UserFriends();
		List <UserProfile> friendList= new ArrayList<UserProfile>();
		try {
			userFriends.setFriendId(userId);
			userFriends.setPending("yes");
			operationName = "checkFriendRequest";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");

			friendList=(List <UserProfile>) objPersistenceInterface.search(userFriends, con,
					operationName);					
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

		return friendList;
	}
	
	 /**
	  * The api accept the friend request corresponding to friendId.
	  * @param userId int
	  * @param friendId int
	  * @return boolean
	  * @throws SysException
	  */
	
	public boolean acceptFriendRequest(int userId, int friendId)
	throws SysException {

		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		UserFriends userFriends = new UserFriends();
		boolean status = false;
		try {
			userFriends.setUserId(userId);
			userFriends.setFriendId(friendId);
			userFriends.setPending("no");
			operationName = "acceptFriendRequest";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
		        objPersistenceInterface.update(userFriends, con,
					operationName);	
		        addSelfToRequestedFreiend(userId, friendId, con);
		    status=true;
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
	 * The api add self as friend of other user that sent request to user
	 * @param userId int
	 * @param friendId int
	 * @param con Connection
	 * @return boolean
	 * @throws SysException
	 */
	private boolean addSelfToRequestedFreiend(int userId, int friendId, Connection con)throws SysException {
		String authAlias = null;
		String operationName = null;
		PersistenceInterface objPersistenceInterface = null;
		UserFriends userFriends = new UserFriends();
		boolean friendAdded = false;
		try {
			operationName = "addFriend";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
			userFriends.setUserId(userId);
			userFriends.setFriendId(friendId);
			userFriends.setPending("no");
			objPersistenceInterface.create(userFriends, con, operationName);
			friendAdded = true;
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
		return friendAdded;
	}
	
	 /**
	  * The api deny the friend request corresponding to friendId.
	  * @param userId int
	  * @param friendId int
	  * @return boolean
	  * @throws SysException
	  */
	
 public boolean denyFriendRequest(int userid, int friendid)
			throws SysException {
		String authAlias = null;
		String operationName = null;
		Connection con = null;
		PersistenceInterface objPersistenceInterface = null;
		UserFriends userFriends = new UserFriends();
		boolean status = false;
		try {
			userFriends.setUserId(userid);
			userFriends.setFriendId(friendid);
			
			operationName = "denyFriendRequest";
			authAlias = config.getPropertyAsString("Entity[@operation='"
					+ operationName + "'].sql.@authenticationAlias");
			// Use the alias to get the connection
			con = DBUtility.getConnection(authAlias);
			objPersistenceInterface = (PersistenceInterface) ServiceLocator
					.getService("PersistenceSvc");
		  objPersistenceInterface.delete(userFriends, con,
					operationName);		
		    status=true;
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


}
