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
package com.iLabs.spice.common.core;

import java.util.HashMap;
import java.util.Map;

import com.iLabs.spice.common.config.ConfigurationHandler;
import com.iLabs.spice.common.config.ConfigurationInterface;

/**
 * This is the abstract factory class for all the factories in the system. A
 * concrete instance of this class contains a singleton map, containing at most
 * only 1 instance of concrete factories in the application.
 * 
 */

public abstract class FactoryBase {

	/**
	 * This is the map contaning a single instance of other factories. Only one
	 * instance of this map is created per JVM process.
	 * 
	 */
	protected static Map factories = null;

	/**
	 * This attribute consists a reference of the configuration information of
	 * the entire system. A single instance of this attribute is maintained per
	 * JVM process. The factories use this configuraion attribute to create and
	 * configure the application objects.
	 * 
	 */
	protected static ConfigurationInterface appConfig = null;

	/**
	 * This is a static method that returns a reference to the application
	 * configuration. If the configuration is not currently loaded, this methods
	 * loads the application configuration and returns a reference to it.
	 * 
	 * @return ConfigurationInterface This is the reference to the application
	 *         configuration
	 */
	public synchronized static ConfigurationInterface getApplicationConfigurations() {
		if (appConfig == null) {
			appConfig = ConfigurationHandler.getApplicationConfiguration();
		}
		return appConfig;
	}

	/**
	 * This is a static method that queries the factories map for a factory by
	 * the factory name that is passed in as a parameter.
	 * 
	 * @param factoryName
	 *            The name of the factory with which a factory instance is
	 *            searched in the factories map.
	 * @return FactoryBase This is a reference to the factory being returned.
	 */
	protected static FactoryBase getRequestedFactory(String factoryName) {
		FactoryBase reqFactory = null;
		if (factories != null) {
			reqFactory = (FactoryBase) factories.get(factoryName);
		}
		return reqFactory;
	}

	/**
	 * This is a static and synchronized method for registering / adding the
	 * newly created factory to the factories map with the key as the factory
	 * name.
	 * 
	 * @param factory
	 *            The factory to be added to the factories map.
	 * @param factoryName
	 *            The name of the factory that would act as key to locate this
	 *            factory in the map.
	 */
	protected static synchronized void registerFactory(FactoryBase factory,
			String factoryName) {
		if (factories == null) {
			factories = new HashMap();
		}
		factories.put(factoryName, factory);
	}

}
