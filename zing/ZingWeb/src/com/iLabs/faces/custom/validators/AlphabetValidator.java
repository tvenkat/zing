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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 * This class is used to validate only alphabets in the entered field. The characters can
 * have any values between a-z or A-Z
 * 
 * @author N9913845
 */
public class AlphabetValidator implements Validator{
	
	/** Accepts characters in the range [a-z] [A-Z] */
	private static final String ALPHABET_REGEX = "[a-zA-Z-'.\\s]*";

	/** Validator id for AlphabetValidator */
	private String validatorMessageId = "validator_alphabet";

	/* (non-Javadoc)
	 * @see javax.faces.validator.Validator#validate(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {
		/* Create the correct mask */
		Pattern mask = null;

		mask = Pattern.compile(ALPHABET_REGEX);

		/* Get the string value of the current field */
		String alphabetField = (String) value;		
		alphabetField = alphabetField.trim();

		/* Check to see if the value is a string with alphabets */
		Matcher matcher = mask.matcher(alphabetField);

		if (!matcher.matches()) {
			ValidatorMessageUtils.logValidationMessage(context, component, value, validatorMessageId);
		}
	}
}
