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
package com.iLabs.spice.common.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.iLabs.spice.common.config.ConfigurationHandler;
import com.iLabs.spice.common.config.ConfigurationInterface;
import com.iLabs.spice.common.exception.SysException;


/**
 * This class is a utility class that would contain static methods to provide
 * connection from a server data source.
 */
public class DBUtility {
	/**
	 * The contructor is hidden to prevent any instantiation of this class.
	 */
	private DBUtility() {
	}

	/**
	 * This is the name of the config tag containing the authentication 
	 * information for database access
	 */
	private static final String AUTHENTICATION_ALIAS_TAG="Authentication-Alias";


	/**
	 * Variable to handle configuration
	 */
	private static ConfigurationInterface config = null;

	/**
	 * This method establishes and returns a database connection by using
	 * connection configuration infromation and the server data source.
	 * @param String		Contains the authentication alias name
	 * @return Connection 	This is a live connection from the data source.
	 * @throws Exception
	 */
	public static Connection getConnection(String authenticationAlias) throws SysException {
		Connection connection = null;
		config = ConfigurationHandler.getApplicationConfiguration();
		if (authenticationAlias==null || authenticationAlias.trim().length()==0) {
			authenticationAlias="default";
		}
		ConfigurationInterface authConfig=config.getConfigSubset(AUTHENTICATION_ALIAS_TAG+"[@name='"+authenticationAlias+"']");
		
		//Connection String Implementation
		String userName = authConfig.getPropertyAsString("username");
		String password = authConfig.getPropertyAsString("password");		
		String driver = authConfig.getPropertyAsString("driver");				
		String url = authConfig.getPropertyAsString("url");
		
		//String dataSourceName=authConfig.getPropertyAsString("dataSource");
		//ConfigurationInterface dataSourceConfig = config.getConfigSubset("dataSource[@name='"+dataSourceName+"']");
		//String isJNDIDataSource = dataSourceConfig.getPropertyAsString("@isJNDIDataSource");
		if (userName==null || password==null) {
			throw new SysException("DB001");
		}
		
		// Variable to check type of data source being used
//		if (isJNDIDataSource.equalsIgnoreCase("true")) {
//			// JNDI implementation
//			
//			String dataSourceJndiName =dataSourceConfig.getPropertyAsString("@dataSourceJndiName");
//			if (dataSourceJndiName==null || dataSourceJndiName.length()==0) {
//				throw new SysException("DB002");
//			}
//			
//			Properties props = System.getProperties();
//			try {
//				
//				String factoryClass=dataSourceConfig.getPropertyAsString("@factoryClass");
//				if (factoryClass==null || factoryClass.length()==0) {
//					throw new SysException("DB003");
//				}
//				props.put(Context.INITIAL_CONTEXT_FACTORY,	factoryClass);
//				// Retrieves and stores the factory class from Persistence
//				// Configuration file in a variable
//				String providerURL=dataSourceConfig.getPropertyAsString("@providerUrl");
//				if (providerURL==null || providerURL.length()==0) {
//					throw new SysException("DB004");
//				}
//				props.put(Context.PROVIDER_URL,	providerURL);
//				// Retrieves and stores the provide url of the WAS server from
//				// Persistence configuration file in a variable
//				InitialContext context = new InitialContext(props);
//				// Initializing Initial context
//				DataSource dataSource =
//						(DataSource) context.lookup(dataSourceJndiName);
//				// Data Source Lookup
//				if (dataSource != null) {
//					connection = dataSource.getConnection(userName, password);
//					connection.setAutoCommit(false);
//					connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//				}
//			} catch (SQLException sqlException) {
//				SysException ex = new SysException("DB005", sqlException);
//				throw ex;
//			} catch (NamingException namingException) {
//				SysException ex = new SysException("DB006", namingException);
//				throw ex;
//			}
//		} else {
		
			try {
				Class.forName(driver);
				connection =
						DriverManager.getConnection(url, userName, password);
			} catch (ClassNotFoundException classNotFoundException) {
				SysException ex = new SysException("DB006", classNotFoundException);
				throw ex;
			} catch (SQLException sqlException) {
				SysException ex = new SysException("DB005", sqlException);
				throw ex;
			}
		//}
		
		return connection;
		// Returning the active connection
	}

	public static java.sql.Date convertUtilDateToSQLDate(java.util.Date date) {
		java.sql.Date convertedDate = null;
		convertedDate = new java.sql.Date(date.getTime());
		return convertedDate;
	}

	public static java.util.Date convertSQLDateToUtilDate(java.sql.Date date) {
		java.util.Date convertedDate = null;
		date.setYear(date.getYear() - 1900);
		date.setMonth(date.getMonth() - 1);
		convertedDate = new java.util.Date(date.getTime());
		return convertedDate;
	}
	
	/**
	 * This method establishes and returns a database connection by using
	 * connection configuration infromation and the server data source.
	 * 
	 * @return Connection This is a live connection from the data source.
	 * @throws Exception
	 */
	public static Connection getConnection() throws SysException {
			return getConnection(null);
	}
}
