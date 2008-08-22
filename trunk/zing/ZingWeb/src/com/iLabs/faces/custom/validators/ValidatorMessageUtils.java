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
package com.iLabs.faces.custom.validators;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

public abstract class ValidatorMessageUtils {
	
	private static final String DEFAULT_MESSAGE="default_message";
	
	private static final String VALIDATION_MESSAGE_BUNDLE="com.iLabs.faces.validation.messages.nl.CustomValidationMessages";
	
	/**
	 * This method is used to get the validator message from the resource bundle
	 * 
	 * @return String validator message
	 */
	static void logValidationMessage(FacesContext context, UIComponent component,
			Object value,String validatorMessageId) throws ValidatorException {
		FacesMessage message = new FacesMessage();
		String validationMessage = null;
		ResourceBundle bundle = ResourceBundle.getBundle(VALIDATION_MESSAGE_BUNDLE, context.getViewRoot()
				.getLocale());
		try {			
			validationMessage = bundle.getString(validatorMessageId);								
		} catch(MissingResourceException missingResourceException){
			validationMessage = bundle.getString(DEFAULT_MESSAGE);
		}		
		String formattedMessage=MessageFormat.format(validationMessage, new Object[]{component.getId()});
		message.setDetail(formattedMessage);
		message.setSummary(formattedMessage);
		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		throw new ValidatorException(message);
	}
}
