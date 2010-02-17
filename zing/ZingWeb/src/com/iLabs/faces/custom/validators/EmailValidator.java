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
package com.iLabs.faces.custom.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * This class is used to validate email id. The characters can 
 * have any values between [A-Za-z0-9,_,-,.]@[A-Za-z0-9,_,-,.].{2,3} character long in the end
 *
 * @author N9913845
 */
public class EmailValidator implements Validator {
	
	/** Accepts email like (xxx@xxx.com or xxx@xxx.co.in) */
	private static final String EMAIL_REGEX = "^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(.\\w{2,3})+$";

	/** Validator id for EmailValidator */
	private String validatorMessageId = "validator_email";

	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		/* Create the correct mask */
		Pattern mask = null;

		mask = Pattern.compile(EMAIL_REGEX);

		/* Get the string value of the current field */
		String emailField = (String) value;

		/* Check to see if the value is a valid email id */
		Matcher matcher = mask.matcher(emailField);
		if (!matcher.matches()) {
			ValidatorMessageUtils.logValidationMessage(context, component, value, validatorMessageId);
		}
	}

}
