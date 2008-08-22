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

package com.spice.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import junit.framework.TestCase;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.dto.Image;
import com.iLabs.spice.dto.UserAlbum;
import com.iLabs.spice.dto.UserAuth;
import com.iLabs.spice.dto.UserFriends;
import com.iLabs.spice.dto.UserPresence;
import com.iLabs.spice.dto.UserProfile;
import com.iLabs.spice.services.IPerson;

public class TestPersonService extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("COMPOSITE_CONFIG","..\\ShindigWebConfig\\configurations\\configurationControlFile.xml");
	}

/*	public void testCreatePerson() throws SysException {
		IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
		UserAuth personObj = new UserAuth();
		personObj.setUserName("SunilKumar");
		personObj.setUserEmail("sunil@gmail.com");
		personObj.setUserPassword("12345");
		person.createPerson(personObj);

	}

	public void testCreateProfile() throws SysException {
		IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
		UserProfile personObj = new UserProfile();
		personObj.setUserId(83);
		personObj.setFirstName("SunilKumar");
		personObj.setLastName("SunilKumar");
		personObj.setUserImage("../images/pic.jpg");
		personObj.setProfileURL("../images/pic.jpg");
		personObj.setInterests("Friendship");

		person.createProfile(personObj);

	}

	public void testAuthenticateUser() throws SysException {
		IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
		UserAuth authPerson = person.authenticateUser("Dipti", "12345");
		System.out.println("authPerson " + authPerson.getUserId());

	}

	public void testGetPerson() throws SysException {
		IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
		UserAuth authPerson = person.getPerson(86);
		System.out.println("email " + authPerson.getUserEmail());

	}

	public void testUpdateOnlineStatus() throws SysException {
		IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
		UserPresence userPresence = new UserPresence();
		// XMLGregorianCalendar XMLGregorianCalendar = Calendar.getInstance();
		userPresence.setLastOnlineTime(null);
		person.updateOnlineStatus(userPresence);

	}

	public void testGetPersonDetails() throws SysException {
		IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
		UserAuth userAuth = new UserAuth();
		userAuth = person.getPersonDetails(86);
		System.out.println("User Activity " + userAuth.getActivities());
		System.out.println("User Activity " + userAuth.getApplications());
		System.out.println("User Activity " + userAuth.getProfile());
		System.out.println("user Activity " + userAuth.getUserEmail());
	}

	public void testGetUserScraps() throws SysException {
		IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
		Collection userAuths = null;
		userAuths = person.getScraps(59);
		System.out.println("scraps ==" + userAuths);
	}
	
	public void testGetPersonAlbum()throws SysException
	{
		IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
			
		List<UserAlbum> albumList= (List<UserAlbum>)person.getPersonAlbum(70);
		System.out.println("No. of user Album: "+albumList.size());
		System.out.println("No. of user Images: "+albumList.get(0).getImage().size());
		
	}*/
	
	public void testGetFriends()throws SysException
	{
		IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
		UserFriends userFriends= person.getFriends(70);
		System.out.println("No. of user Album: "+userFriends.getUserFriendsImages().size()); 
			
	}
	
	/*public void testAddFriend()throws SysException
	{
		IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
		boolean  added = person.addFriend(70, 86);
		System.out.println("All Done "+ added );
		 
			
	}
	
	public void testaddToAlbum()throws SysException
	{
		IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
		List<Image> imageList=new ArrayList<Image>();
		Image image =new Image();
		image.setImageCaption("Added for Test2");
		image.setImageName("Added for Test2");
		image.setThumbName("Added for Test2");
		image.setUserId(87);
		imageList.add(image);
		Image image1 =new Image();
		image1.setImageCaption("Added for Test3");
		image1.setImageName("Added for Test3");
		image1.setThumbName("Added for Test3");
		image1.setUserId(87);		
		imageList.add(image1);
		UserAlbum userAlbum= new UserAlbum();
		userAlbum.setImage(imageList);
		boolean  added = person.addToAlbum(userAlbum);
		
		System.out.println("All Done "+ added );
	}
	
	
	public void testDeletePerson()throws SysException
	{
		IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
		boolean  added = person.deletePerson(60);
		
		System.out.println("All Done "+ added ); 
	}
	
	public void testisFriend()throws SysException
	{
		IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
		
		assertEquals(true, person.isFriend(60, 59));
		assertEquals(false, person.isFriend(60, 87));
		
	}
	
		public void testSearchPerson()throws SysException
	{
		IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
		List<UserAuth> userList =(List<UserAuth>)person.searchPerson("ilabs");
		System.out.println("No. of user: "+userList.size()); 
			
	}
		
	public void testUpdatePerson()throws SysException
		{
			IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
			UserAuth userAuth = new UserAuth();
			userAuth.setUserEmail("sunil@impetus.oc.in");
			userAuth.setUserId(81);
			userAuth.setUserName("suniluiit");
			userAuth.setUserPassword("suniluiit");
			UserAuth userAuth1 = person.updatePerson(userAuth);
			System.out.println(userAuth1.getUserEmail());
			
				
		}
	
	public void testGetProfile()throws SysException
	{
		IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
		 UserProfile userProfile= person.getProfile(62);
		 System.out.println("Username"+userProfile.getFirstName());
				
	}
		
	public void testSearchProfile()throws SysException
		{
			IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
			List<UserProfile> userProfileList= (List<UserProfile> )person.searchProfile("i kumar");
			System.out.println("Total no. of Profile "+ userProfileList.size());
					
		}
	public void testUpdateProfile()throws SysException
	{
		IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
		 UserProfile userProfile =new UserProfile();
		 userProfile.setCity("Noida");
		 userProfile.setCountry("India");
		 userProfile.setDateOfBirth("20 Dec");
		 userProfile.setFirstName("Sandeep");
		 userProfile.setLastName("Thakur");
		 userProfile.setGender("m");
		 userProfile.setInterests("Dating");
		 userProfile.setProfileURL("orkut.com.");
		 userProfile.setUserId(69);
		 userProfile.setUserImage("../images/humtum.jpg");		 
		 UserProfile userProfile2= person.updateProfile(userProfile);		 
		System.out.println("Total no. of Profile "+ userProfile2.getDateOfBirth());
				
	}
	
	public void testDeleteAlbum()throws SysException
	{
		IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
		person.deleteAlbum(55);
				
	}
	public void testGetPassword()throws SysException
	{
		IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
		person.getPassword("Dipti");
				
	}
	
	public void testDeleteScrap()throws SysException
	{
		IPerson person = (IPerson)ServiceLocator.getService("PersonSvc");
		person.deleteScrap(59,1);
	}
*/
}
