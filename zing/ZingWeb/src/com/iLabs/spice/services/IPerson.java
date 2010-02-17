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
import com.iLabs.spice.dto.CodeImage;
import com.iLabs.spice.dto.SendMail;
import com.iLabs.spice.dto.UserAlbum;
import com.iLabs.spice.dto.UserAuth;
import com.iLabs.spice.dto.UserFriends;
import com.iLabs.spice.dto.UserPresence;
import com.iLabs.spice.dto.UserProfile;
import com.iLabs.spice.dto.UserScrap;
/**
 * The interface provide all people service for open-social site.
 * @author iLabs
 */
public interface IPerson {
	/**
	 * The api find a UserAuth object corresponding to userId.
	 * @param userId int
	 * @return UserAuth
	 * @throws SysException
	 */
	UserAuth getPerson(int userId)throws SysException;
	/**
	 * The api get all friends corresponding to userId.
	 * @param userId int
	 * @return UserFriends
	 * @throws SysException
	 */

	UserFriends getFriends(int userId)throws SysException; 
	 
	/**
	 * The api get details like profile, actvities, friends of corresponding to userId.
	 * @param userId int
	 * @return UserAuth
	 * @throws SysException
	 */

	UserAuth getPersonDetails(int userId)throws SysException;
	
	/**
	 * The api updates userDetails 
	 * @param person UserAuth
	 * @return UserAuth
	 * @throws SysException
	 */
	
	UserAuth updatePerson(UserAuth person)throws SysException;
	
	/**
	 * The api delete particular person corresponding to userId and return true if successfully deleted
	 * otherwise return false.
	 * @param userId int
	 * @return boolean
	 * @throws SysException
	 */
	
	boolean deletePerson(int userId)throws SysException;
	
	/**
	 * Create a new person object and return no the userId created for the ne person object.
	 * @param person UserAuth
	 * @return int
	 * @throws SysException
	 */
	
	int createPerson(UserAuth person)throws SysException;
	
	/**
	 * The api add new friend corresponding to userId and friendId return true if successfully added
	 * otherwise return false.
	 * @param userId int 
	 * @param friendId int
	 * @return boolean
	 * @throws SysException
	 */
	boolean addFriend(int userId,int friendId)throws SysException;
	
	/**
	 * The api get user album corresponding to userID.
	 * @param userId int
	 * @return Collection<UserAlbum> 
	 * @throws SysException
	 */
	
	Collection<UserAlbum> getPersonAlbum(int userId)throws  SysException;
	
	/**
	 * The api add new album and return true if successfully added otherwise return false.
	 * @param object UserAlbum
	 * @return boolean
	 * @throws SysException
	 */
	
	boolean addToAlbum(UserAlbum object)throws  SysException;
	
	/**
	 * The api search all user corresponding to username and return Collection of UserAuth objects.
	 * @param name String
	 * @return Collection<UserAuth>
	 * @throws SysException
	 */
	
	Collection<UserAuth> searchPerson(String name)throws  SysException;
	
	/**
	 * The api find that particular personId is friend of userId or not.
	 * @param userId int
	 * @param personId int
	 * @return boolean
	 * @throws SysException
	 */
	
	boolean isFriend(int userId, int personId)throws  SysException;
	
	/**
	 * The api get all the scraps corresponding to userId.
	 * @param userId int
	 * @return Collection<UserScrap> 
	 * @throws SysException
	 */
	
	Collection<UserScrap> getScraps(int userId)throws  SysException;

	/**
	 * The api Post a scrap object corresponding to UserAuth object
	 * @param auth UserAuth
	 * @param object UserScrap
	 * @return UserScrap
	 * @throws SysException
	 */
	UserScrap sendScrap(UserAuth auth,UserScrap object)throws  SysException;
	
	/**
	 * The api authenticate a user corresponding to username and password and return the object
	 * of UserAuth corresponding to username and password.  
	 * @param name String
	 * @param password String
	 * @return UserAuth
	 * @throws SysException
	 */
	
	UserAuth authenticateUser(String name,String password)throws  SysException;
	
	/**
	 * The api create a new user profile and return the generated profile id of the user.
	 * @param profile UserProfile
	 * @return int
	 * @throws SysException
	 */
	
	int createProfile(UserProfile profile)throws  SysException;
	
	/**
	 * The api update userprofile of user.
	 * @param profile
	 * @return UserProfile 
	 * @throws SysException
	 */
	
	UserProfile updateProfile(UserProfile profile)throws  SysException;
	
	/**
	 * The api search all user profile corresponding to user first name or last name and 
	 * return collection of searched profile.
	 * @param name
	 * @return Collection<UserProfile>
	 * @throws SysException
	 */
	
	Collection<UserProfile> searchProfile(String name)throws  SysException;
	
	/**
	 *The api get user profile corresponding to userId and 
	 * return UserProfile object of searched profile. 
	 * @param userId
	 * @return UserProfile
	 * @throws SysException
	 */
	
	UserProfile getProfile(int userId)throws  SysException;
	
	/**
	 * The api gives the online status of user return true if user is online otherwise return false
	 * @param userPresence UserPresence
	 * @return boolean
	 * @throws SysException
	 */
	
	boolean updateOnlineStatus(UserPresence userPresence)throws  SysException;
	
	/**
	 * The api get CodeImage object corresponding to codeImage Id.
	 * @param codeImageId int
	 * @return CodeImage
	 * @throws SysException
	 */
	CodeImage getCodeImage(int codeImageId)throws SysException;
	
	/**
	 * The api delete a image from album corresponding to imageId return true if successfully deleted else false.
	 * @param imageId int
	 * @return boolean
	 * @throws SysException
	 */
	
	boolean deleteAlbum(int imageId)throws  SysException;
	
	/**
	 * The api send a mail corresponding to mail message which contain sender and receiver mailId 
	 * @param mailMessage SendMail
	 * @return boolean
	 * @throws SysException
	 */
	
	boolean sendMail(SendMail mailMessage)throws  SysException;
	
	/**
	 * The api used to get password of user.
	 * @param userName String
	 * @return UserAuth
	 * @throws SysException
	 */

	UserAuth getPassword(String userName)throws  SysException;
	/**
	 * The api delete a Scrap corresponding to scrapId.
	 * @param scrapId int
	 * @return boolean
	 * @throws SysException
	 */
	
	 boolean deleteScrap(int scrapId) throws SysException;
	 
	 /**
	  * The api used to check all friend request corresponding to userId and return collection of user profile 
	  * who had sent request to that user. 
	  * @param userId int
	  * @return  Collection<UserProfile>
	  * @throws SysException
	  */
	 Collection<UserProfile>  checkFriendRequest(int userId) throws SysException;
	 
	 /**
	  * The api accept the friend request corresponding to friendId.
	  * @param userId int
	  * @param friendId int
	  * @return boolean
	  * @throws SysException
	  */
	 boolean acceptFriendRequest(int userId, int friendId)throws SysException ;
	 
	 /**
	  * The api deny the friend request corresponding to friendId.
	  * @param userId int
	  * @param friendId int
	  * @return boolean
	  * @throws SysException
	  */	 
	 boolean denyFriendRequest(int userId, int friendId)throws SysException ;
	
	
}
