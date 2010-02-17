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
package com.iLabs.faces.utils;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public abstract class FacesMessageUtils {
	
	private static final String HTML_COMMENT_START="<!--";
	
	private static final String HTML_COMMENT_END="-->";
	
	private static final String DEFAULT_MESSAGE = "default_message";
	
	/**
	 * This method is used to log error messages in the UI
	 * 
	 * @param messageId
	 *            Message Id as String
	 * @param args
	 *            Message to be displayed as String
	 */
	public static void logFacesErrorMessage(String messageId,String sysExceptionMessage,
			String[] args) {
		FacesMessage objFacesMessage = null;
		FacesContext context = FacesContext.getCurrentInstance();
		String strMessage =null;
		try {			
			strMessage = getBundle().getString(messageId);								
		} catch(MissingResourceException missingResourceException){
			strMessage = getBundle().getString(DEFAULT_MESSAGE);
		}		
		String strDisplayMessage = null;
		if (args != null) {
			strDisplayMessage = MessageFormat.format(strMessage, args);
		} else {
			strDisplayMessage = strMessage;
		}
		StringBuffer completeMessage=new StringBuffer();
		completeMessage.append(HTML_COMMENT_START)
						.append(sysExceptionMessage)
						.append(HTML_COMMENT_END)
						.append(strDisplayMessage);
		objFacesMessage = new FacesMessage(completeMessage.toString());
		context.addMessage(null, objFacesMessage);
	}
	
	/**
	 * This method is used to get the  the resource bundle based on the current locale
	 * 
	 * @return String validator message
	 */
	public static ResourceBundle getBundle(){
		FacesContext context = FacesContext.getCurrentInstance();				
		ResourceBundle bundle = ResourceBundle.getBundle(context.getApplication().getMessageBundle(), context.getViewRoot().getLocale());		
		return bundle;
	}
}
