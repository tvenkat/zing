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
package com.iLabs.spice.common.config;

import java.math.BigDecimal;
import java.util.Iterator;

/**
 * This is the interface that isolates the low level details of apache commons
 * configuration to proide an easy to user interface as per the application
 * configuration requirements.
 */
public interface ConfigurationInterface {

	/**
	 * This method returns a subset of the configuration. This is particularly
	 * applicable in of hierarical property file (XML).
	 * 
	 * @param propertyGroup
	 *            This is the name of the property that defines the subset to be
	 *            returned
	 * @return The return is IConfig which is a subset of the entire
	 *         configuration.
	 */
	ConfigurationInterface getConfigSubset(String propertyGroup);

	/**
	 * This method returns a list property values matching the property name.
	 * 
	 * @param propertyName
	 *            This parameter consists of the parameter name
	 * @return This is an array containing all the property values
	 */
	ConfigurationInterface[] getList(String propertyName);

	/**
	 * This method returns the property value as a type casted string
	 * 
	 * @param propertyName
	 *            This attribute consists the property name.
	 * @return The return value consists of the type casted property value.
	 */
	String getPropertyAsString(String propertyName);

	/**
	 * This method returns the property value as a type casted primitive
	 * integer.
	 * 
	 * @param propertyName
	 *            This attribute consists the property name.
	 * @return The return value consists of the type casted property value.
	 */
	int getPropertyAsInteger(String propertyName);

	/**
	 * This method returns the property value as a type casted primitive float.
	 * 
	 * @param propertyName
	 *            This attribute consists the property name.
	 * @return The return value consists of the type casted property value.
	 */
	float getPropertyAsFloat(String propertyName);

	/**
	 * This method returns the property value as a type casted BigDecimal
	 * 
	 * @param propertyName
	 *            This attribute consists the property name.
	 * @return The return value consists of the type casted property value.
	 */
	BigDecimal getPropertyAsBigDecimal(String propertyName);

	/**
	 * This method returns the property value as a type casted primitive boolean
	 * 
	 * @param propertyName
	 *            This attribute consists the property name.
	 * @return The return value consists of the type casted property value.
	 */
	boolean getPropertyAsBoolean(String propertyName);

	/**
	 * This method returns the property value as a type casted Byte
	 * 
	 * @param propertyName
	 *            This attribute consists the property name.
	 * @return The return value consists of the type casted property value.
	 */
	Byte getPropertyAsByte(String propertyName);

	/**
	 * This method returns all the property names.
	 * 
	 * @return The return consists of an iterator to facilitate iteration of
	 *         property names.
	 */
	Iterator getPropertyNames();
	
	/**
	 * This method is used to return the configuration base path
	 * 
	 * @return String path
	 */
	public String getConfigBasePath();

}
