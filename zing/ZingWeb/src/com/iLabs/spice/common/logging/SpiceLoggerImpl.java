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
package com.iLabs.spice.common.logging;

import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.iLabs.spice.common.config.ConfigurationHandler;


/**
 * This class implements the PortalLogger interface and contains a constructor
 * and implementation of methods defined in PortalLogger Interface.
 */
public class SpiceLoggerImpl implements SpiceLoggerInterface {

	static final int WPS_ERROR = Level.ERROR_INT;

	static final int WPS_INFO = Level.INFO_INT;

	static final int WPS_WARN = Level.WARN_INT;

	static final int WPS_DEBUG = Level.DEBUG_INT;
	
	static boolean initialized = false;

	private static Logger logger;
	private static Properties properties;
	
	static {
		PropertyConfigurator.configure(ConfigurationHandler.getApplicationConfiguration().getConfigBasePath()+"/logger/log4j.properties");
		
	}	
	
	SpiceLoggerImpl() {
	}
	
	
	public SpiceLoggerImpl(final Class aClass) {
		if (logger == null){
			logger = Logger.getLogger(aClass);
		}
	}
	/* (non-Javadoc)
	 * @see com.livecargo.portal.common.logger.PortalLoggerInterface#debug(java.lang.String)
	 */
	public void debug(String message) {
		if (isDebugEnabled()) {
			logger.debug(message);
		}		
	}

	/* (non-Javadoc)
	 * @see com.livecargo.portal.common.logger.PortalLoggerInterface#error(java.lang.String)
	 */
	public void error(String message) {
		if (isErrorEnabled()) {
			logger.error(message);
		}		
	}
	/* (non-Javadoc)
	 * @see com.livecargo.portal.common.logger.PortalLoggerInterface#error(java.lang.String, java.lang.Throwable)
	 */
	public void error(String message, Throwable cause) {
		if (isErrorEnabled()) {
			logger.error(message, cause);
		}
		
	}
	/* (non-Javadoc)
	 * @see com.livecargo.portal.common.logger.PortalLoggerInterface#info(java.lang.String)
	 */
	public void info(String message) {
		if (isInfoEnabled()) {
			logger.info(message);
		}
	}
	/* (non-Javadoc)
	 * @see com.livecargo.portal.common.logger.PortalLoggerInterface#warn(java.lang.String)
	 */
	public void warn(String message) {
		if (isWarnEnabled()) {
			logger.warn(message);
		}		
	}
	/* (non-Javadoc)
	 * @see com.livecargo.portal.common.logger.PortalLoggerInterface#isDebugEnabled()
	 */
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	/* (non-Javadoc)
	 * @see com.livecargo.portal.common.logger.PortalLoggerInterface#isInfoEnabled()
	 */
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}
	
	/* (non-Javadoc)
	 * @see com.livecargo.portal.common.logger.PortalLoggerInterface#isErrorEnabled()
	 */
	public boolean isErrorEnabled() {
		return isLoggerLevel(WPS_ERROR);
	}

	/* (non-Javadoc)
	 * @see com.livecargo.portal.common.logger.PortalLoggerInterface#isWarnEnabled()
	 */
	public boolean isWarnEnabled() {
		return isLoggerLevel(WPS_WARN);		
	}
	private boolean isLoggerLevel(int level){
		if ( logger!= null && logger.getLevel()!= null && logger.getLevel().toInt() == level){
			return true;
		}
		return false;
	}
}
