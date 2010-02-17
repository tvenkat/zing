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
package com.iLabs.spice.common.exception;

/**
 * Description : This class is responsible for handling exceptions. Exceptions
 * can be thrown by the components in the application. They can be either system
 * or application or business or SQL Exceptions. If an error occurs at any stage
 * in the life cycle of application the same is thrown back. The specific
 * message for the corresponding exception is read from the property file and is
 * populated at the front end.
 */
public class AppException extends AbstractBaseException {

	/** Constructor for AppException with the errorcode */
	public AppException(String errorCode) {
		super(errorCode);
	}

	/** Constructor for AppException with the errorcode and user-defined message */
	public AppException(String errorCode, String message) {
		super(errorCode, message);
	}

	/** Constructor for AppException with the errorcode and the throwable object */
	public AppException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	/**
	 * Constructor for AppException with the errorcode, user-defined message and
	 * the throwable object
	 */
	public AppException(String errorCode, String message, Throwable cause) {
		super(errorCode, message, cause);
	}
}
