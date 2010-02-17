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
package com.iLabs.spice.common.logging;

/**
 * The interface is used to define Levels for logging standards.
 * It contains some abstract methods which will be implemented in the
 * implementing class. Each variable for the error levels must have a unique
 * number assigned to it.
 */

public interface SpiceLoggerInterface {

	/**
	 * This method returns whether "debug" level is enabled or not as a boolean
	 * 
	 * @return boolean true or false
	 */
	boolean isDebugEnabled();

	/**
	 * This method prints the debug message.
	 * 
	 * @param message
	 */
	void debug(String message);

	/**
	 * This method returns whether "info" level is enabled or not as a boolean
	 * 
	 * @return boolean true or false
	 */
	boolean isInfoEnabled();

	/**
	 * This method prints the information message.
	 * 
	 * @param message
	 */
	void info(String message);

	/**
	 * This method returns whether "warn" level is enabled or not as a boolean
	 * 
	 * @return boolean true or false
	 */
	boolean isWarnEnabled();

	/**
	 * This method prints the warning message.
	 * 
	 * @param message
	 */
	void warn(String message);

	/**
	 * This method returns whether "error" level is enabled or not as a boolean
	 * 
	 * @return boolean true or false
	 */
	boolean isErrorEnabled();

	/**
	 * This method prints the error message.
	 * 
	 * @param message
	 */
	void error(String message);

	/**
	 * This method prints the error message with the cause.
	 * 
	 * @param message
	 * @param throwable
	 */
	void error(String message, Throwable throwable);

}
