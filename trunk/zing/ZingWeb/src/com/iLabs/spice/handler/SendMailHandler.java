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

import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;

import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.beans.ProfileBean;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.dto.Image;
import com.iLabs.spice.dto.SendMail;
import com.iLabs.spice.dto.UserAuth;
import com.iLabs.spice.dto.UserFriends;
import com.iLabs.spice.services.IPerson;

/**
 * This class handles all actions related to mail functionality like sendMail,
 * populating friend email ids and also provide rendering information of send
 * mail button.
 * 
 * @author iLabs
 * @date 01/08/2008
 */
public class SendMailHandler extends PageCodeBase {

	// this attribute handle information for the rendering of the component.
	boolean rendered = false;

	public SendMailHandler() {

	}

	/**
	 * Attribute for holding user emails Id.
	 */
	private Map friendEmailIds;

	/**
	 * Getter for friendEmailIds.
	 * 
	 * @return Map
	 */
	public Map getFriendEmailIds() {
		populateFriendEmailIds();
		return friendEmailIds;
	}

	/**
	 * Setter for friendEmailIds.
	 * 
	 * @param friendEmailIds
	 *            Map
	 */
	public void setFriendEmailIds(Map friendEmailIds) {
		this.friendEmailIds = friendEmailIds;
	}

	/**
	 * This function is used to send mail. Sender's mailId is fetched from
	 * ownerProfile bean. Mail service is invoked and sendMail bean is passed as
	 * parameter.
	 * 
	 * @return String
	 */

	public String sendMail() {
		getFacesContext().getViewRoot().processValidators(getFacesContext());

		System.out.println("In sendMail");
		try {
			SendMail sendMail = (SendMail) FacesContext.getCurrentInstance()
					.getExternalContext().getRequestMap().get("sendMail");
			ProfileBean ownerProfile = (ProfileBean) getSessionScope().get(
					"ownerProfile");
			UserAuth userAuth = (UserAuth) ownerProfile.getUserAuth();

			String from = userAuth.getUserEmail();
			sendMail.setMailFrom(from);

			try {
				IPerson person = (IPerson) ServiceLocator
						.getService("PersonSvc");
				person.sendMail(sendMail);

			} catch (SysException e) {
				e.printStackTrace();
			}

			FacesContext.getCurrentInstance().getExternalContext()
					.getRequestMap().put("sendMail", sendMail);

			this.rendered = true;
			return "success";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * This function creates a HashMap which stores emailIds of friends.
	 * Friend's user name is used as key in HashMap. The HashMap is used to
	 * populate dropdownlist on send mail page.
	 */

	public void populateFriendEmailIds() {

		this.friendEmailIds = new HashMap();

		ProfileBean ownerProfile = (ProfileBean) getSessionScope().get(
				"ownerProfile");
		UserFriends userFriends = ownerProfile.getUserFriends();
		int numOfFriends = userFriends.getUserFriendsImages().size();

		// this checks for number of friends. If number of friends is zero then
		// HashMap containing friend's email Ids is not populated.
		if (numOfFriends > 0) {
			ListIterator friendsIterator = userFriends.getUserFriendsImages()
					.listIterator();
			while (friendsIterator.hasNext()) {
				Image image = (Image) friendsIterator.next();
				this.friendEmailIds.put(image.getUserName(), ""
						+ image.getEmailId());
			}
			HtmlCommandButton uiComponent = (HtmlCommandButton) findComponentInRoot("sendMail");
			uiComponent.setDisabled(false);
		} else { // if number of friends is zero then send mail button is
					// disabled

			HtmlCommandButton uiComponent = (HtmlCommandButton) findComponentInRoot("sendMail");
			uiComponent.setDisabled(true);

		}

	}

	/**
	 * Getter for rendered.
	 * 
	 * @return boolean
	 */
	public boolean getRendered() {
		return rendered;
	}

	/**
	 * Setter for rendered.
	 * 
	 * @param rendered
	 *            boolean
	 */
	public void setRendered(boolean rendered) {
		this.rendered = rendered;
	}

}
