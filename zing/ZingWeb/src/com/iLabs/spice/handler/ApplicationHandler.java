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
package com.iLabs.spice.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.beans.AllApplication;
import com.iLabs.spice.beans.ProfileBean;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.dto.Activity;
import com.iLabs.spice.dto.Application;
import com.iLabs.spice.dto.UserApplications;
import com.iLabs.spice.dto.UserAuth;
import com.iLabs.spice.services.IActivity;
import com.iLabs.spice.services.IApplication;
import com.iLabs.spice.services.IPerson;
/**
 * This class handle all action related application like add application,
 * delete application,get all information regarding application and also 
 * handle delete and add user application. 
 * @author iLabs
 * @date 01/08/2008
 */
public class ApplicationHandler extends PageCodeBase {
	
	// hold the application Id  when user clicks on add or delete application.
	String applicationId = "";
	// hold the title of application .
	String applicationTitle = "";
	// List of all the applications available in the system.
	Collection allApplications;
	// gadget url for parsing.
	private String gadgetURL;
	//this attribute handle information for the rendering of the component.
	private boolean render;
	
	/**
	 * Setter of applicationList;
	 * @param allApplications
	 */
	public void setAllApplications(Collection allApplications) {
		this.allApplications = allApplications;
	}
	/**
	 * Getter for application Id.
	 * @return String
	 */
	public String getApplicationId() {
		return applicationId;
	}
	/**
	 * Setter for application Id.
	 * @param applicationId
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * Delete user application entry from user's account
	 * check if third party application have order zero then delete 
	 * application also.
	 * 
	 * @return String
	 */
	public String deleteApplication() {
		IApplication application;
		try {
			application = (IApplication) ServiceLocator
					.getService("ApplicationSvc");
			ProfileBean currentProfile = (ProfileBean) getSessionScope().get(
					"currentProfile");
			UserAuth userAuth = currentProfile.getUserAuth();
			int userId = userAuth.getUserId();

			int applicationId = Integer.parseInt(getRequestParam().get(
					"applicationId").toString());
			//delete user application.
			application.deleteUserApplication(userId, applicationId);
			UserApplications applicationObjToRemove = null;
			for (UserApplications applicationObj : userAuth.getApplications()) {
				if (applicationObj.getApplicationId().intValue() == applicationId) {
					applicationObjToRemove = applicationObj;
					break;
				}

			}
			// If application  is user specific it remove application from database. 
			if (applicationObjToRemove != null
					&& (applicationObjToRemove.getApplication().getOrder() == 0)) {
				// delete application with order zero.
				application.deleteApplication(applicationId);
			}
			userAuth.getApplications().remove(applicationObjToRemove);

			FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().put("userAuth", userAuth);
		} catch (SysException e) {
			e.printStackTrace();
		}
	  return "";
	}
	
	/**
	 * Show all available application in the system and set add option on the basis that user already user 
	 * have that application there list or not. 
	 * @return
	 */
	public String showApplicationGallery() {
		try {
			OwnerProfileHandler ownerProfileHandler = new OwnerProfileHandler();
			ownerProfileHandler.updateCurrentProfileWithOwner();
			IApplication application = (IApplication) ServiceLocator
					.getService("ApplicationSvc");
			List<Application> applicatiosList = (List<Application>) application
					.getAllApplication();
			AllApplication allApplication = (AllApplication) resolveExpression("#{sessionScope.allApplication}");
			if (allApplication == null)
				allApplication = new AllApplication();

			ProfileBean ownerProfile = (ProfileBean) getSessionScope().get(
					"ownerProfile");
			List<UserApplications> userApplicationList = ownerProfile
					.getUserAuth().getApplications();

			for (Application genriApp : applicatiosList) {
				genriApp.setStatus(true);
				for (UserApplications userApplications : userApplicationList) {
					int userAppId = userApplications.getApplicationId();
					if (userAppId == genriApp.getApplicationId()) {
						genriApp.setStatus(false);
						break;
					}

				}

			}

			allApplication.setApplicationList(applicatiosList);
			getSessionScope().put("allApplication", allApplication);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "lnkApplicationGallery";
	}
	
	/**
	 * Create application entry in user's account as user application.
	 * @return String.
	 */
	public String addApplicationToUser() {

		try {
			IApplication application = (IApplication) ServiceLocator
					.getService("ApplicationSvc");
			ProfileBean currentProfile = (ProfileBean) getSessionScope().get(
					"currentProfile");
			ProfileBean ownerProfile = (ProfileBean) getSessionScope().get(
					"ownerProfile");
			UserAuth userAuth = currentProfile.getUserAuth();

			int applicationId = Integer.parseInt(getRequestParam().get(
					"applicationId").toString());
			int userId = userAuth.getUserId();
			application.addApplication(userId, applicationId);

			IPerson person = (IPerson) ServiceLocator.getService("PersonSvc");
			UserAuth authPerson = person.getPersonDetails(userAuth.getUserId());
			currentProfile.setUserAuth(authPerson);
			ownerProfile.setUserAuth(authPerson);
			updateActivity(applicationId, userId);

			getSessionScope().put("currentProfile", currentProfile);
			getSessionScope().put("ownerProfile", ownerProfile);
			showApplicationGallery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "lnkApplicationGallery";
	}
	/**
	 * Update user's activity when user add any application. 
	 * @param applicationId requested application's id. 
	 * @param userId User id.
	 */
	private void updateActivity(int applicationId, int userId) {
		IActivity activity = null;
		Activity activityObj = null;
		ProfileBean ownerProfile = null;
		UserAuth authPerson = null;
		IPerson person = null;
		try {
			activity = (IActivity) ServiceLocator.getService("ActivitySvc");
			activityObj = new Activity();
			activityObj.setApplicationId(applicationId);
			activityObj.setBody("Application Added");
			String title = (String) getRequestParam().get("applicationTitle");
			activityObj.setTitle(" has added " + title);
			int time = (int) System.currentTimeMillis();
			activityObj.setCreated(time);
			activityObj.setUserId(userId);
			activity.createActivity(activityObj);
			ownerProfile = (ProfileBean) getSessionScope().get("ownerProfile");
			person = (IPerson) ServiceLocator.getService("PersonSvc");
			authPerson = person.getPersonDetails(userId);
			ownerProfile.setUserAuth(authPerson);
			getSessionScope().put("ownerProfile", ownerProfile);

		} catch (SysException e) {
			
			e.printStackTrace();
		}

	}

	/**
	 * Add new application into application and also check if application already into 
	 * user application then don't add.
	 * 
	 * @return void
	 */
	public void addApplication() {
		Application userApplication = (Application) getRequestScope().get(
				"applicationBean");
		ProfileBean currentProfile = (ProfileBean) getSessionScope().get(
				"currentProfile");
		String url = userApplication.getUrl();
		userApplication = parseURL(url);

		if (userApplication == null) {
			FacesMessage message = new FacesMessage("Invalid Gadget URL!!");
			getFacesContext().addMessage("addApplication", message);
		} else {
			IApplication application;
			try {

				Collection<UserApplications> applications = currentProfile
						.getUserAuth().getApplications();
				Iterator<UserApplications> iterator = applications.iterator();
				boolean alreadyAdded = false;
				while (iterator.hasNext()) {
					UserApplications apps = iterator.next();
					if (apps.getApplication().getUrl().equalsIgnoreCase(url)) {
						alreadyAdded = true;
						break;
					}
				}
				if (alreadyAdded) {
					FacesMessage message = new FacesMessage(
							"Application already available!!");
					getFacesContext().addMessage("addApplication", message);
					return ;
				}

				userApplication.setUrl(url);
				application = (IApplication) ServiceLocator
						.getService("ApplicationSvc");
				application.createAndAddApplication(currentProfile
						.getUserAuth().getUserId(), userApplication);

				ProfileBean ownerProfile = (ProfileBean) getSessionScope().get(
						"ownerProfile");
				IPerson person = (IPerson) ServiceLocator
						.getService("PersonSvc");
				UserAuth authPerson = person.getPersonDetails(currentProfile
						.getUserAuth().getUserId());
				currentProfile.setUserAuth(authPerson);
				ownerProfile.setUserAuth(authPerson);
				getSessionScope().put("currentProfile", currentProfile);
				getSessionScope().put("ownerProfile", ownerProfile);
			} catch (SysException e) {
				
				e.printStackTrace();
			}

		}

	}
	 /**
	  * parse URL of application 
	  * @param gadgetURL 
	  * @return Application 
	  */
	private Application parseURL(String gadgetURL) {
		Application application = null;
		try {
			URL url = new URL(gadgetURL);
			InputStream inputStream = url.openStream();
			application = parseXML(inputStream);
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		return application;
	}
	/**
	 * This method parse xml for getting gadget information like height,width
	 * and other attribute.
	 * @param inputStream InputStream.
	 * @return Application
	 */
	private Application parseXML(InputStream inputStream) {
		Application application = null;
		try {
			// create an object of the Document implementation class
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = dbf.newDocumentBuilder();
			Document doc = docBuilder.parse(inputStream);
			NodeList nodeList = doc.getElementsByTagName("ModulePrefs");

			if (nodeList.getLength() == 1) {
				application = new Application();

				Node node = nodeList.item(0);
				NamedNodeMap namedNodeMap = node.getAttributes();
				if (namedNodeMap != null) {
					if (namedNodeMap.getNamedItem("author") != null) {
						application.setAuthor(namedNodeMap.getNamedItem(
								"author").getNodeValue());
					}

					if (namedNodeMap.getNamedItem("author_email") != null) {
						application.setAuthorEmail(namedNodeMap.getNamedItem(
								"author_email").getNodeValue());
					}

					if (namedNodeMap.getNamedItem("description") != null) {
						application.setDescription(namedNodeMap.getNamedItem(
								"description").getNodeValue());
					}

					if (namedNodeMap.getNamedItem("screenshot") != null) {
						application.setScreenshot(namedNodeMap.getNamedItem(
								"screenshot").getNodeValue());
					}
					if (namedNodeMap.getNamedItem("title") != null) {
						application.setTitle(namedNodeMap.getNamedItem("title")
								.getNodeValue());
					}
					if (namedNodeMap.getNamedItem("thumbnail") != null) {
						application.setThumbnail(namedNodeMap.getNamedItem(
								"thumbnail").getNodeValue());
					}
					if (namedNodeMap.getNamedItem("height") != null) {
						application.setHeight(Integer.parseInt(namedNodeMap
								.getNamedItem("height").getNodeValue()));
					}
					application.setOrder(0);
				}
			}

		} catch (Exception e) {
			System.out.println("error: " + e);
			return null;
		}

		return application;
	}
	/**
	 * This method get stream contents.
	 * @param inputStream 
	 * @return byte[]
	 */
	private byte[] getStreamContents(InputStream inputStream) {
		int fileLength = 0;
		byte[] buffer = null;
		try {
			fileLength = inputStream.available();
			buffer = new byte[fileLength];
			int readBytes = inputStream.read(buffer, 0, fileLength);
			int remainBytes = fileLength - readBytes;

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			outputStream.write(buffer, 0, readBytes);
			int totalRead = readBytes;
			while (remainBytes > 0) {
				buffer = new byte[2048];
				readBytes = inputStream.read(buffer, 0, 2048);
				remainBytes = remainBytes - readBytes;
				outputStream.write(buffer, 0, readBytes);
				readBytes = readBytes + totalRead;
			}
		} catch (IOException e) { 
			e.printStackTrace();
		}
		return buffer;
	}
	/**
	 * This method get all user application.
	 * @return String
	 */
	public String showApplication() {
		String URL = null;
		ProfileBean currentProfile = (ProfileBean) getSessionScope().get(
				"currentProfile");
		ProfileBean ownerProfile = (ProfileBean) getSessionScope().get(
				"ownerProfile");
		Collection<UserApplications> applications = currentProfile
				.getUserAuth().getApplications();
		Iterator<UserApplications> iterator = applications.iterator();
		int applicationId = Integer.parseInt(getRequestParam().get(
				"applicationId").toString());
		if (ownerProfile.getUserAuth().getUserId() == currentProfile
				.getUserAuth().getUserId()) {
			setRender(true);
		}
		while (iterator.hasNext()) {
			UserApplications apps = iterator.next();
			if (apps.getApplication().getApplicationId() == applicationId) {
				URL = apps.getApplication().getUrl();
				break;
			}
		}
		getRequestScope().put("gadgetURL", getGadgetURL(null, URL));

		return "showApplication";
	}
	/**
	 * This method getGadget url.
	 * @param applications UserApplications
	 * @param URL
	 * @return String
	 */
	private String getGadgetURL(UserApplications applications, String URL) {
		if (URL == null) {
			URL = applications.getApplication().getUrl();
		}
		String gadgetServerURL = "http://192.168.145.103:8080";
		if (getFacesContext().getExternalContext().getRequestMap().get(
				"GadgetServerURL") != null) {
			gadgetServerURL = (String) getFacesContext().getExternalContext()
					.getRequestMap().get("GadgetServerURL");
		}
		ProfileBean currentProfile = (ProfileBean) getSessionScope().get(
				"currentProfile");
		UserAuth userAuth = currentProfile.getUserAuth();
		int userId = userAuth.getUserId();
		String urlPrefix = gadgetServerURL
				+ "/gadgets/ifr?container=default&mid=0&nocache=1&country=ALL&lang=ALL&view=default&";
		urlPrefix += "parent=" + gadgetServerURL + "&";
		urlPrefix += "url=" + URL + "&";
		String escapedURL = URL.replaceAll(":", "%253A");
		urlPrefix += "st=" + userId + ":" + userId + ":" + getAppId(URL)
				+ ":shindig:" + escapedURL + ":0";
		return urlPrefix;
	}
    /**
     * This method get all applications.
     * @return Collection.
     */
	public Collection getAllApplications() {
		allApplications = new ArrayList();
		ProfileBean currentProfile = (ProfileBean) getSessionScope().get(
				"currentProfile");
		Collection<UserApplications> applications = currentProfile
				.getUserAuth().getApplications();
		Iterator<UserApplications> iterator = applications.iterator();
		while (iterator.hasNext()) {
			UserApplications apps = iterator.next();
			if (apps != null && apps.getApplication() != null) {

				UserApplications tempApps = new UserApplications();
				tempApps.setApplication(new Application());

				String url = getGadgetURL(apps, null);
				tempApps.getApplication().setUrl(url);
				if (apps.getApplication().getHeight() != null) {
					if (apps.getApplication().getHeight() == 0) {
						tempApps.getApplication().setHeight(100);
					} else {
						tempApps.getApplication().setHeight(
								apps.getApplication().getHeight());
					}
				}
				allApplications.add(tempApps);
			}
		}
		return allApplications;
	}

	/*
	 * // TODO: Use a less silly mechanism of mapping a gadget URL to an appid
	 * var appId = 0; for (var i = 0; i < gadgetUrl.length; i++) { appId +=
	 * gadgetUrl.charCodeAt(i); } var fields = [ownerId, viewerId, appId,
	 * "shindig", gadgetUrl, "0"]; for (var i = 0; i < fields.length; i++) { //
	 * escape each field individually, for metachars in URL fields[i] =
	 * escape(fields[i]); } return fields.join(":");
	 */
	private int getAppId(String url) {
		int appId = 0;
		for (int i = 0; i < url.length(); i++) {
			appId += url.charAt(i);
		}
		return appId;
	}
/**
 * Getter method.
 * @return String
 */
	public String getApplicationTitle() {
		return applicationTitle;
	}

	/**
	 * Setter method. 
	 * @param applicationTitle String
	 */
	public void setApplicationTitle(String applicationTitle) {
		this.applicationTitle = applicationTitle;
	}
	/**
	 * Getter method.
	 * @return String
	 */
	public String getGadgetURL() {
		return (String) getRequestScope().get("gadgetURL");
	}
	/**
	 * setter method.
	 * @param gadgetURL String
	 */
	public void setGadgetURL(String gadgetURL) {
		this.gadgetURL = gadgetURL;
	}
	/**
	 * Get Render.
	 * @return boolean
	 */
	public boolean getRender() {
		return render;
	}
	/**
	 * Settter method for render.
	 * @param render
	 */
	public void setRender(boolean render) {
		this.render = render;
	}

}
