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

import javax.faces.context.FacesContext;

import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.services.IPerson;

public class NavigationBean {
	boolean renderEditProfile = false;	
	boolean renderAddApplication = false;	
	
	boolean renderSendMail = false;
    boolean renderAddFriend = false;
    
    String scrapbookText ;
    String applicationText;
    String albumText;
 
    public NavigationBean()
    {
    	checkRenderAttribute();
    }
	public boolean getRenderEditProfile() {
		return renderEditProfile;
	}
	public void setRenderEditProfile(boolean renderEditProfile) {
		this.renderEditProfile = renderEditProfile;
	}
	public boolean getRenderAddApplication() {
		return renderAddApplication;
	}
	public void setRenderAddApplication(boolean renderAddApplication) {
		this.renderAddApplication = renderAddApplication;
	}
	public boolean getRenderSendMail() {
		return renderSendMail;
	}
	public void setRenderSendMail(boolean renderSendMail) {
		this.renderSendMail = renderSendMail;
	}
	public boolean getRenderAddFriend() {
		return renderAddFriend;
	}
	public void setRenderAddFriend(boolean renderAddFriend) {
		this.renderAddFriend = renderAddFriend;
	}
	public String getScrapbookText() {
		return scrapbookText;
	}
	public void setScrapbookText(String scrapbookText) {
		this.scrapbookText = scrapbookText;
	}
	public String getApplicationText() {
		return applicationText;
	}
	public void setApplicationText(String applicationText) {
		this.applicationText = applicationText;
	}
	public String getAlbumText() {
		return albumText;
	}
	public void setAlbumText(String albumText) {
		this.albumText = albumText;
	}
	
	public void checkRenderAttribute()
	{
		ProfileBean currentProfile=(ProfileBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentProfile");
		ProfileBean ownerProfile =(ProfileBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ownerProfile");
		int currentUserId=currentProfile.getUserAuth().getUserId();
		int ownerUserId=ownerProfile.getUserAuth().getUserId();
		setRenderAddApplication(true);
		setRenderEditProfile(true);
		setRenderSendMail(true);
		setScrapbookText("My ScrapBook");
		setAlbumText("My Album");	
		setApplicationText("My Application");
		if(ownerUserId!=currentUserId)
		{
			setRenderAddFriend(true);
			setRenderAddApplication(false);			
			setRenderEditProfile(false);
			setRenderSendMail(false);
			setScrapbookText("ScrapBook");
			setAlbumText("Album");	
			setApplicationText("Application");
			try {
				IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
				if(person.isFriend(ownerUserId, currentUserId))
				{
					setRenderAddFriend(false);
				}
			} catch (SysException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
    
}
