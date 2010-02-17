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

import com.iLabs.spice.common.config.ConfigurationInterface;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.logging.SpiceLoggerImpl;

/**
 * This is the service factory, one of the concrete implementations of the
 * abstract factory. The concreteness is achieved by a private inner class. This
 * has been done to strictly limit the instantiation of this service factory by
 * any other means. Additionally this service factory maintains a Map of
 * services that have been created with specific implementations and factory
 * name. This is a singleton class.
 */

public abstract class ServiceFactory extends FactoryBase {

	/**
	 * This is a reference to the application configuration for all the services
	 * of the application. Subsets of this configuration is distributed to the
	 * instantiated service.
	 */
	private static ConfigurationInterface servicesConfig = null;

	/**
	 * This is an instance of the service factory. Only one instance is
	 * maintained per JVM process. A reference of the same is added into the
	 * factories map.
	 * 
	 */
	private static ServiceFactory svcFactory = null;

	/**
	 * This attribute identifies this factory as the service factory
	 * 
	 */
	private static String SERVICE_FACTORY_NAME = "SERVICE_FACTORY";

	/**
	 * This map consists at most of a single instance of a service. This map is
	 * utilized by the services loactor to determine the service availability.
	 */
	private static Map services = null;

	/**
	 * This method returns an instance of the service factory to the calling
	 * code. The availability of the service factory is first checked in the
	 * factories Map. If it is found, it is returned as it is. If the service
	 * factory is not found, it is instantiated using the concerete inner class
	 * definition, added to the factories Map with the key as the
	 * SERVICE_FACTORY_NAME and then returned.
	 * 
	 * @return ServiceFactory This is the instance of service factory.
	 */
	public static ServiceFactory getInstance() {
		svcFactory = (ServiceFactory) getRequestedFactory(SERVICE_FACTORY_NAME);
		if (svcFactory == null) {
			svcFactory = ServiceFactoryConcrete.createConcreteServiceFactory();
			svcFactory.servicesConfig =
					getApplicationConfigurations().getConfigSubset("Services");
			registerFactory(svcFactory, SERVICE_FACTORY_NAME);
		}
		return svcFactory;
	}

	/**
	 * This method returns the requested service, with the default
	 * implementation to the requesting code.
	 * 
	 * @param svcName
	 *            This parameter contains the name of the service, as specified
	 *            in the services configuration.
	 * @return Object This is a reference to the default service implementation.
	 * @throws SysException
	 */
	public synchronized Object getService(String svcName) throws SysException {
		return getService(svcName, "default");
	}

	/**
	 * This method returns the requested service, with the default
	 * implementation to the requesting code.
	 * 
	 * @param svcName
	 *            This parameter contains the name of the service, as specified
	 *            in the services configuration.
	 * @param implName
	 *            This parameter contains the name of the service
	 *            implementation, as specified in the services configuration.
	 * @return Object This is a reference to the default service implementation.
	 * @throws SysException
	 */
	public synchronized Object getService(String svcName, String implName)
			throws SysException {
		Object svc = null;
		if (services == null) {
			services = new HashMap();
		}
		svc = services.get(svcName + implName);
		if (svc == null) {
			setupImplementations(svcName);
			svc = services.get(svcName + implName);
		}

		return svc;
	}

	/**
	 * This method creates all possible implementations of the service and
	 * checks for the conformance of these implementations to the interface
	 * definition as specified in the services configuration. The service
	 * implementations thus created are put into the services Map with the key
	 * as SERVICE_NAME + IMPLEMENTATION_NAME as specified in the services
	 * configurtaion.
	 * 
	 * @param svcName
	 *            This input parameter contains the name of the service to be
	 *            created.
	 * @throws SysException
	 */
	private synchronized void setupImplementations(String svcName)
			throws SysException {
		ConfigurationInterface svcConfig =
				servicesConfig.getConfigSubset("Service[@name='" + svcName
						+ "']");
		String interfaceName = svcConfig.getPropertyAsString("@interface");
		ConfigurationInterface[] implsConfig = svcConfig.getList("Impls.impl");
		int numImpls = implsConfig.length;
		for (int ctrImpls = 0; ctrImpls < numImpls; ctrImpls++) {
			String className =
					implsConfig[ctrImpls].getPropertyAsString("@className");
			String implName =
					implsConfig[ctrImpls].getPropertyAsString("@name");
			ServiceImplBase impl = null;
			try {
				impl = (ServiceImplBase) Class.forName(className).newInstance();
				SpiceLoggerImpl logger = new SpiceLoggerImpl(impl.getClass());
				impl.logger = logger;
				impl.config = appConfig;
				if (Class.forName(interfaceName).isAssignableFrom(
						impl.getClass())) {
					services.put(svcName + implName, impl);
				} else {
					throw new RuntimeException(
							"Service Implementations do not match the interface");
				}
			} catch (InstantiationException instantiationException) {
				throw new SysException("SF001",
						instantiationException);
			} catch (IllegalAccessException illegalAccessException) {
				throw new SysException("SF001",
						illegalAccessException);
			} catch (ClassNotFoundException classNotFoundException) {
				throw new SysException("SF001",
						classNotFoundException);
			}
		}
	}

	/**
	 * This is the private inner concrete implementation of service factory. The
	 * only objective of this class definition is to provide an instantiation
	 * mechanism for service factory.
	 * 
	 */
	private static class ServiceFactoryConcrete extends ServiceFactory {
		/**
		 * This is a static method that returns a concrete implementation of
		 * service factory.
		 * 
		 * @return ServiceFactory This is the service factory returned.
		 */
		public static ServiceFactory createConcreteServiceFactory() {
			ServiceFactory svcConcrete = new ServiceFactoryConcrete();
			return svcConcrete;

		}
	}

}
