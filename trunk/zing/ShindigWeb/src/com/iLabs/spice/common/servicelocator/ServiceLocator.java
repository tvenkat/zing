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
package com.iLabs.spice.common.servicelocator;

import java.lang.reflect.InvocationTargetException;

import com.iLabs.spice.common.exception.SysException;

/**
 * This class provides a mechanism to lookup to a service by name and 
 * optionally by the name of the implementation.
 */

public class ServiceLocator {

	/**
	 * This is the constructor of the class.
	 */
	public ServiceLocator() {
	}

	/**
	 * This method returns an instance of a service that has been looked up by name.
	 * 
	 * @param svcName	This is the the name of the service as given in the services configuration
	 * @return			If a service is successfully located, it is returned.
	 * @throws SysException 
	 */
	public static Object getService(String svcName) throws SysException {
		return getService(svcName,null);
		
	}

	/**
	 * This method returns an instance of a service that has been looked up by
	 * service name and implementation name.
	 * @param svcName 	This is the the name of the service as given in the 
	 * 					services configuration
	 * @param implName	This is the the name of the service implementation 
	 * 					as given in the services configuration
	 * @return			If a service is successfully located , it is returned.
	 * @throws SysException 
	 */
	public static Object getService(String svcName, String implName) throws SysException {
		Object svc = null;
		try {
			Class svcFactoryClass=Class.forName("com.iLabs.spice.common.core.ServiceFactory");
			Object svcFactory=svcFactoryClass.getMethod("getInstance",null).invoke(null,null);
			if (implName==null || implName.trim().length()==0) {
				implName = "default";
			}
			Class[] paramTypes = {String.class,String.class};
			Object[] paramValues = {svcName,implName};
			svc=svcFactory.getClass().getMethod("getService", paramTypes).invoke(svcFactory, paramValues);
		} catch (ClassNotFoundException classNotFoundException) {
			throw new SysException("SL001", classNotFoundException);
		} catch (IllegalAccessException illegalAccessException) {
			throw new SysException("SL001", illegalAccessException);
		} catch (InvocationTargetException invocationTargetException) {
			throw new SysException("SL001", invocationTargetException);
		} catch (NoSuchMethodException noSuchMethodException) {
			throw new SysException("SL001", noSuchMethodException);			
		}
		return svc;
	}
}
