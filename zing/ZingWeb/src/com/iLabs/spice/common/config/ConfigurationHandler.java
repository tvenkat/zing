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

import java.io.File;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

/**
 * This class is a configuration handler for the entire application. It makes
 * use of the apache commons configuration internally. The objective of this
 * class is to manage all types of properties (.properties/.xml and system) by
 * the use of a common interface (ConfigInterface).
 * 
 */

public class ConfigurationHandler implements ConfigurationInterface {

	/**
	 * This attribute is a reference to the application configuration by use of
	 * apache's Configuration interface. Only one instance of this attribute is
	 * maintained across a JVM process.
	 */
	private static Configuration compositeConfig = null;

	/**
	 * This attribute is used to create subset of the application
	 * configuration,so that an application object has only the configuration
	 * that is required for its functionality.
	 */
	private Configuration configSubset = null;

	/**
	 * This is the system property that would point to the physical location of
	 * the global configuration file on the machine. This property would be
	 * required to be configured on the server.
	 */
	private static final String SYS_PROPERTY_COMPOSITE_CONFIGURATION =
			"COMPOSITE_CONFIG";

	/**
	 * This is the system property that would point to the physical location of
	 * the global configuration file on the machine. This property would be
	 * required to be configured on the local machine.
	 */
	private static final String SYS_PROPERTY_COMPOSITE_CONFIGURATION_LOC_DEV =
			"configurationControlFile.xml";
	
	private static final char NON_PROCESSING_QUERY_START_CHAR='[';
	private static final char NON_PROCESSING_QUERY_END_CHAR=']';
	private static final char XPATH_PROPERTY_SEPARATOR='/';
	private static final char CONFIG_PROPERTY_SEPERATOR='.';
	
	private static String configBasePath=null;

	/**
	 * The constructor is hidden to make this class as a Singletion class.
	 */
	private ConfigurationHandler() {

	}

	/**
	 * This method returns the application configuration to the requesting
	 * code.If the configuration is not loaded, it is loaded by this method. In
	 * case the configuration is already loaded, it is simple returned to the
	 * caller.
	 * 
	 * @return ConfigurtaionInerface This application configuration is exposed
	 *         via this interface.
	 */
	public static synchronized ConfigurationInterface getApplicationConfiguration() {
		ConfigurationHandler configHandler = null;
		if (compositeConfig == null) {
			String filePath =
					System.getProperty(SYS_PROPERTY_COMPOSITE_CONFIGURATION);
			if (filePath == null) {
				filePath = SYS_PROPERTY_COMPOSITE_CONFIGURATION_LOC_DEV;
			}
			DefaultConfigurationBuilder builder = null;
			if (filePath != null && filePath.trim().length() > 0) {
				builder = new DefaultConfigurationBuilder(new File(filePath));
				try {
					Configuration configuration =
							builder.getConfiguration(true);
					((HierarchicalConfiguration) configuration)
							.setExpressionEngine(new XPathExpressionEngine());
					compositeConfig = configuration;
					configBasePath=builder.getBasePath();					
				} catch (ConfigurationException e) {
					throw new RuntimeException(e);
				}
			} else {
				throw new RuntimeException(
						"Composite Configuration file could not be loaded");
			}
		}
		configHandler = new ConfigurationHandler();
		return configHandler;
	}

	/**
	 * This method returns the property value as big decimal
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getPropertyAsBigDecimal(String propertyName) {
		propertyName = preProcessPropertyName(propertyName);
		if (configSubset != null) {
			return configSubset.getBigDecimal(propertyName);
		} else {
			return compositeConfig.getBigDecimal(propertyName);
		}
	}

	/**
	 * This method returns the property value as Boolean
	 * 
	 * @return boolean
	 */
	public boolean getPropertyAsBoolean(String propertyName) {
		propertyName = preProcessPropertyName(propertyName);
		if (configSubset != null) {
			return configSubset.getBoolean(propertyName);
		} else {
			return compositeConfig.getBoolean(propertyName);
		}
	}

	/**
	 * This method returns the property value as Byte
	 * 
	 * @return Byte
	 */
	public Byte getPropertyAsByte(String propertyName) {
		propertyName = preProcessPropertyName(propertyName);
		if (configSubset != null) {
			return configSubset.getByte(propertyName, new Byte(""));
		} else {
			return compositeConfig.getByte(propertyName, new Byte(""));
		}
	}

	/**
	 * This method returns the property value as float
	 * 
	 * @return float
	 */
	public float getPropertyAsFloat(String propertyName) {
		propertyName = preProcessPropertyName(propertyName);
		if (configSubset != null) {
			return configSubset.getFloat(propertyName);
		} else {
			return compositeConfig.getFloat(propertyName);
		}
	}

	/**
	 * This method returns the property value as integer
	 * 
	 * @return int
	 */
	public int getPropertyAsInteger(String propertyName) {
		propertyName = preProcessPropertyName(propertyName);
		if (configSubset != null) {
			return configSubset.getInt(propertyName);
		} else {
			return compositeConfig.getInt(propertyName);
		}
	}

	/**
	 * This method returns the property value as String
	 * 
	 * @return String
	 */
	public String getPropertyAsString(String propertyName) {
		propertyName = preProcessPropertyName(propertyName);

		if (configSubset != null) {
			return configSubset.getString(propertyName);
		} else {
			return compositeConfig.getString(propertyName);
		}
	}

	/**
	 * This method returns the property value as Iterator
	 * 
	 * @return Iterator
	 */
	public Iterator getPropertyNames() {
		if (configSubset != null) {
			return configSubset.getKeys();
		} else {
			return compositeConfig.getKeys();
		}
	}

	/**
	 * This method returns the subset of the configuration based on specified
	 * property group
	 * 
	 * @param propertyGroup
	 * @return ConfigurationInterface
	 */
	public ConfigurationInterface getConfigSubset(String propertyGroup) {
		propertyGroup = preProcessPropertyName(propertyGroup);
		ConfigurationHandler appConfigSubset = new ConfigurationHandler();
		Configuration subset = null;
		if (configSubset != null) {
			subset = configSubset.subset(propertyGroup);
		} else {
			subset = compositeConfig.subset(propertyGroup);
		}
		((HierarchicalConfiguration) subset)
				.setExpressionEngine(new XPathExpressionEngine());
		appConfigSubset.configSubset = subset;
		return (ConfigurationInterface) appConfigSubset;
	}

	/**
	 * This method pre-process the property name, by replacing a '.' with a
	 * '/'in order to perform any XPATH expression evaluation if required.
	 * 
	 * @param propertyName
	 *            This is the name of the property
	 * @return String This is the processed property name.
	 */
	private String preProcessPropertyName(String propertyName) {
		StringBuffer preparedPropertyName = new StringBuffer();
		if (propertyName != null && propertyName.length() > 0) {
			int lenPropertyName=propertyName.length();
			for(int ctrPropertyNameChar=0;ctrPropertyNameChar<lenPropertyName;ctrPropertyNameChar++){
				boolean processPropertyPeriod=true;
				char currentChar=propertyName.charAt(ctrPropertyNameChar);
				if (processPropertyPeriod) {
					if (currentChar==CONFIG_PROPERTY_SEPERATOR && processPropertyPeriod) {
						currentChar=XPATH_PROPERTY_SEPARATOR;
					}
					preparedPropertyName.append(currentChar);
					if (currentChar==NON_PROCESSING_QUERY_START_CHAR) {
						processPropertyPeriod=!processPropertyPeriod;
					} else if (currentChar==NON_PROCESSING_QUERY_END_CHAR) {
						processPropertyPeriod=!processPropertyPeriod;
					}
					
				}
			}
		}
		return preparedPropertyName.toString();
	}

	/**
	 * This method returns an array of the properties
	 * 
	 * @return ConfigurationInterface
	 */
	public ConfigurationInterface[] getList(String propertyName) {
		propertyName = preProcessPropertyName(propertyName);
		// Object[] configs=null;
		ConfigurationInterface[] configurations = null;
		List subConfigs = null;
		if (configSubset != null) {
			subConfigs =
					((HierarchicalConfiguration) configSubset)
							.configurationsAt(propertyName);
		} else {
			subConfigs =
					((HierarchicalConfiguration) compositeConfig)
							.configurationsAt(propertyName);

		}
		int configsLength = subConfigs.size();
		configurations = new ConfigurationInterface[configsLength];
		for (int ctrConfigs = 0; ctrConfigs < configsLength; ctrConfigs++) {
			ConfigurationHandler config = new ConfigurationHandler();
			HierarchicalConfiguration currentConfig =
					(HierarchicalConfiguration) subConfigs.get(ctrConfigs);
			currentConfig.setExpressionEngine(new XPathExpressionEngine());
			config.configSubset = currentConfig;
			configurations[ctrConfigs] = config;
		}
		return configurations;
	}
	
	public String getConfigBasePath(){
		return configBasePath;
	}
}
