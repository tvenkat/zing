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
package com.iLabs.spice.common.persistence;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.iLabs.spice.common.config.ConfigurationInterface;
import com.iLabs.spice.common.core.ServiceImplBase;
import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.utils.DBUtility;


/**
 * This is a persistence service implementation of the Persistence interface.
 */
public class PersistenceImpl extends ServiceImplBase implements
		PersistenceInterface {
	/**
	 * This method is used to insert new records in the database by using
	 * connection configuration information and the server data source.
	 * 
	 * @return Serializable
	 * @param entity
	 * @param conn
	 * @throws SysException
	 */
	public int create(Serializable entity, Connection conn, String operationName)
			throws SysException {
		boolean result = false;
		int rowCount = 0;
		if (entity != null) {
			// Retrieving List of field values.
			PreparedStatement stmt=null;
			if (conn == null) {
				//Getting connection.
				conn = DBUtility.getConnection();
			}
			stmt=getStatement(conn, entity, operationName);
			try {
				result = stmt.execute();
				if (result != true) 
					rowCount = stmt.getUpdateCount();
			} catch (SQLException sqlException) {
				SysException ex = new SysException("PE001", sqlException);
				throw ex;
			} finally {
				try {
					if (stmt!=null){
						stmt.close();
						stmt=null;
					}
				} catch (SQLException sqlException) {
					throw new SysException("PE001", sqlException);
				}
			}
		}
		return rowCount;}
	/**
	 * This method is used to insert new records in the database by using
	 * connection configuration information and the server data source.
	 * 
	 * @return Serializable
	 * @param entity
	 * @param conn
	 * @throws SysException
	 */
	public int createAndReturnId(Serializable entity, Connection conn, String operationName)
			throws SysException {
		boolean result = false;
		int id = 0;
		ResultSet rs = null;
		if (entity != null) {
			// Retrieving List of field values.
			PreparedStatement stmt=null;
			if (conn == null) {
				//Getting connection.
				conn = DBUtility.getConnection();
			}
			stmt=getStatement(conn, entity, operationName);
			try {
				result = stmt.execute();
				if (result != true) 
					id = stmt.getUpdateCount();
				 rs = stmt.getGeneratedKeys();
				while (rs.next()) {
					id = rs.getInt(1);
				}
			} catch (SQLException sqlException) {
				SysException ex = new SysException("PE001", sqlException);
				throw ex;
			} finally {
				try {
					if (stmt!=null){
						stmt.close();
						stmt=null;
					}
				} catch (SQLException sqlException) {
					throw new SysException("PE001", sqlException);
				}
			}
		}
		return id;
		}
	/**
	 * This method is used to delete records from the database by using
	 * connection configuration information and the server data source.
	 * 
	 * @return int
	 * @param entity
	 * @param conn
	 * @param operationName
	 * @throws SysException
	 */
	public int delete(Serializable entity, Connection conn, String operationName)
			throws SysException {
		// Logging operation Name
		int recordsAffected = 0;
		if (entity != null) {
			// Retrieving List of field values
			PreparedStatement stmt=null;
			if (conn == null) {
				//Getting connection
				conn = DBUtility.getConnection();
			}
			stmt=getStatement(conn, entity, operationName);
			try {
				recordsAffected = stmt.executeUpdate();
				// Executing the statement
			} catch (SQLException sqlException) {
				throw new SysException("PE001", sqlException);
			}finally {
				try {
					if (stmt!=null){
						stmt.close();
						stmt=null;
					}
				} catch (SQLException sqlException) {
					throw new SysException("PE001", sqlException);
				}
			}
		}
		return recordsAffected;
	}

	/**
	 * This method is used to search records from the database by using
	 * connection configuration information and the server data source.
	 * 
	 * @return List
	 * @param entity
	 * @param conn
	 * @param operationName
	 * @throws SysException
	 */
	public List search(Serializable entity, Connection conn,
			String operationName) throws SysException {
		List dtos = null;

		// Logging operation Name
		if (entity != null) {
			// Retrieving List of field values
			PreparedStatement stmt=null;
			if (conn == null) {
				//Getting connection
				conn = DBUtility.getConnection();
			}
			stmt=getStatement(conn, entity, operationName);
			ResultSet result =null;
			try {
				result = stmt.executeQuery();
				// Preparing the ResultSet
				dtos =	populateDTOsFromResultSet(result,
								operationName);
				
				result.close();
				result=null;
			} catch (SQLException sqlException) {
				throw new SysException("PE001", sqlException);
			}finally {
				try {
					if (stmt!=null){
						stmt.close();
						stmt=null;
					}
					if (result!=null) {
						result.close();
						result=null;
					}
				} catch (SQLException sqlException) {
					throw new SysException("PE001", sqlException);
				}
			}

		}
		return dtos;
	}

	/**
	 * This method is used to read records from the database and returns the
	 * number of objects.
	 * 
	 * @return Serializable
	 * @param entity
	 * @param conn
	 * @param operationName
	 * @throws SysException
	 */
	public Serializable read(Serializable entity, Connection conn,
			String operationName) throws SysException {
		// Logging operation Name
		Serializable dto = null;
		List returnedObjects = search(entity, conn, operationName);
		if (returnedObjects != null) {
			if (returnedObjects.size() > 0) {
				dto = (Serializable) returnedObjects.get(0);
				if (returnedObjects.size() > 1) {
					logger
							.info("Multiple Objects Found, returning the first available object.");
				}
			}
		}
		return dto;
	}

	/**
	 * This method is used to update records from the database based on the
	 * configuration information and returns the no. of records affected in the
	 * database
	 * 
	 * @return Serializable
	 * @param entity
	 * @param conn
	 * @param operationName
	 * @throws SysException
	 */
	public int update(Serializable entity, Connection conn, String operationName) throws SysException{
		int val = 0;
		if (entity != null) {
			// Retrieving List of field values
			PreparedStatement stmt=null;
			if (conn == null) {
				//Getting connection
				conn = DBUtility.getConnection();
			}
			stmt=getStatement(conn, entity, operationName);
			try {
				val = stmt.executeUpdate();
			} catch (SQLException sqlException) {
				throw new SysException("PE001", sqlException);
			}finally {
				try {
					if (stmt!=null){
						stmt.close();
						stmt=null;
					}
				} catch (SQLException sqlException) {
					throw new SysException("PE001", sqlException);
				}
			}
		}
		return val;
	}

	/**
	 * This method would prepare the statement from the Data Transfer Object and
	 * the input mappings.
	 * 
	 * @param stmt
	 * @param dto
	 *            The data transfer object containing values to be substituted
	 *            for preparing the statment
	 * @param inputMappings
	 *            The configuration information containing the input mappings.
	 * @return PreparedStatement This would be completely prepared statement
	 *         ready for execution.
	 */
	protected void prepareStmtFromDTO(PreparedStatement stmt, Serializable dto,
			ConfigurationInterface[] inputMappings) throws SysException {
		for (int ctrParameters = 0; ctrParameters < inputMappings.length; ctrParameters++) {
			ConfigurationInterface currentMapping =
					inputMappings[ctrParameters];
			int index = currentMapping.getPropertyAsInteger("@index");
			// Retrieving index value
			String fieldName =
					currentMapping.getPropertyAsString("@dtoAttribute");
			if ((this instanceof PersistenceCalleableImpl) && fieldName==null) {
				try {
					((CallableStatement)stmt).registerOutParameter(index, oracle.jdbc.driver.OracleTypes.CURSOR);
				} catch (SQLException sqlException) {
					throw new SysException("PE001", sqlException);
				}
				continue;
			}
			// Retrieving field name
			String firstStringChar = fieldName.substring(0, 1).toUpperCase();
			// Retrieving first char and converting it to uppercase
			String remaingString = fieldName.substring(1);
			// Retrieving remaining characters
			Class fieldType=null;
			Field currentField=null;
			
			Class inputClass=dto.getClass();
			while (inputClass!=Object.class){
				try {
					currentField=inputClass.getDeclaredField(fieldName);
					break;
				} catch (SecurityException securityException) {
					throw new SysException("PE002", securityException);
				} catch (NoSuchFieldException noSuchFieldException) {
					if (inputClass==Object.class) {
						throw new SysException("PE002", noSuchFieldException);
					} else {
						inputClass=inputClass.getSuperclass();
					}
				}
			}
			


			fieldType=currentField.getType();
			String methodName =null;
			if (fieldType==boolean.class) {
				methodName = "is" + firstStringChar + remaingString;
			} else {
				methodName = "get" + firstStringChar + remaingString;
			}
			
			// Appending get string with the method name
			try {
				Method method=null;
				try {
					inputClass=dto.getClass();
					method=inputClass.getMethod(methodName, null);

					Object currentObject = method.invoke(dto, null);
					if (currentObject instanceof java.util.Date) {
						currentObject =
								DBUtility
										.convertUtilDateToSQLDate((java.util.Date) currentObject);
					}
					if (fieldType==boolean.class) {
						if (((Boolean)currentObject).booleanValue()==true) {
							currentObject=new String("Y");
						} else {
							currentObject=new String("N");
						}
					}
					stmt.setObject(index, currentObject);
					// Mapping methods using reflection API
				} catch (InvocationTargetException invocationTargetException) {
					throw new SysException("PE002", invocationTargetException);
				} catch (NoSuchMethodException noSuchMethodException) {
					throw new SysException("PE002", noSuchMethodException);
				}
			} catch (SecurityException securityException) {
				throw new SysException("PE002", securityException);
			} catch (IllegalArgumentException illegalArgumentException) {
				throw new SysException("PE002", illegalArgumentException);
			} catch (SQLException sqlException) {
				throw new SysException("PE001", sqlException);
			} catch (IllegalAccessException iIllegalAccessException) {
				throw new SysException("PE002", iIllegalAccessException);
			}
		}
	}

	/**
	 * This method would prepare the Data Transfer Object from the resultset and
	 * the output mappings.
	 * 
	 * @param dtoClassName
	 * @param resultSet
	 * @param outputMappings
	 * @return List
	 */
	protected List populateDTOsFromResultSet(ResultSet resultSet, String operationName) throws SysException{
		List objects = new ArrayList();
		ConfigurationInterface operationConfig=config.getConfigSubset("Entity[@operation='"+operationName+"']");
		String dtoClassName =
			operationConfig
					.getPropertyAsString("OutputMappings.@dto");
		ConfigurationInterface[] outputMappings =
			operationConfig.getList("OutputMappings.fieldVal");
		try {
			while (resultSet.next()) {
				
				Object dtoObject = Class.forName(dtoClassName).newInstance();
				
				for (int ctrParameters = 0; ctrParameters < outputMappings.length; ctrParameters++) {
					ConfigurationInterface currentMapping =
							outputMappings[ctrParameters];
					int index = currentMapping.getPropertyAsInteger("@index");
					// Retrieving index value
					String fieldName =
							currentMapping.getPropertyAsString("@dtoAttribute");
					// Retrieving index value
					String firstStringChar =
							fieldName.substring(0, 1).toUpperCase();
					String remaingString = fieldName.substring(1);
					String methodName = "set" + firstStringChar + remaingString;
					
					Object currentObject = resultSet.getObject(index);
					Class fieldType=null;
					Field currentField=null;
					Class inputClass=dtoObject.getClass();
					while (inputClass!=Object.class){
						try {
							currentField=inputClass.getDeclaredField(fieldName);
							break;
						} catch (SecurityException securityException) {
							throw new SysException("PE002", securityException);
						} catch (NoSuchFieldException noSuchFieldException) {
							if (inputClass==Object.class) {
								throw new SysException("PE002", noSuchFieldException);
							} else {
								inputClass=inputClass.getSuperclass();
							}
						}
					}
					fieldType=currentField.getType();
					String getMethodName =null;
					if (fieldType==boolean.class) {
						getMethodName = "is" + firstStringChar + remaingString;
						if (currentObject!=null) { 
							if (currentObject instanceof String) {
								if (((String)currentObject).equalsIgnoreCase("Y")||
										((String)currentObject).equalsIgnoreCase("Yes")||
											((String)currentObject).equalsIgnoreCase("True")||
												((String)currentObject).equalsIgnoreCase("T")) {
									currentObject=new Boolean(true);
								} else {
									currentObject=new Boolean(false);
								}
							} else {
								currentObject=new Boolean(false);
							}
						}
					} else {
						getMethodName = "get" + firstStringChar + remaingString;
					}
					
					if (currentObject==null) {
						if (fieldType==long.class) {
							currentObject=new Long(0);
						} else if (fieldType==int.class){
							currentObject=new Integer(0);
						} else if (fieldType==double.class){
							currentObject=new Double(0);
						} else if (fieldType==float.class) {
							currentObject=new Float(0);
						} else if (fieldType==boolean.class) {
							currentObject=new Boolean(false);
						}
					}
					Method getMethod = dtoObject.getClass().getMethod(getMethodName, null);
					Class returnType=getMethod.getReturnType();
					Class[] parameterTypes = new Class[] {returnType};		
					Method method =
							dtoObject.getClass().getMethod(methodName,
									parameterTypes);					
					
					
					if (currentObject instanceof java.math.BigDecimal) {
						java.math.BigDecimal value=(java.math.BigDecimal)currentObject;
						if (returnType == long.class){
							currentObject=new Long(value.longValue());
						} else if (returnType==int.class) {
							currentObject=new Integer(value.intValue());
						} else if (returnType==float.class) {
							currentObject=new Float(value.floatValue());
						} else if (returnType==double.class) {
							currentObject=new Double(value.doubleValue());
						} 						
					}
					Object[] parameters = {currentObject};
					if (currentObject instanceof java.sql.Timestamp) {
						java.sql.Timestamp ts=(java.sql.Timestamp)currentObject;
						long time=ts.getTime();
						currentObject= new java.sql.Date(time);
						
					}
					if (currentObject instanceof java.sql.Date) {
						currentObject =
								DBUtility
										.convertSQLDateToUtilDate((java.sql.Date) currentObject);
					}

					method.invoke(dtoObject, parameters);
				}
				objects.add(dtoObject);
			}
		} catch (SQLException sqlException) {
			throw new SysException("PE001", sqlException);
		} catch (InstantiationException instantiationException) {
			throw new SysException("PE002", instantiationException);
		} catch (IllegalAccessException illegalAccessException) {
			throw new SysException("PE002", illegalAccessException);
		} catch (SecurityException securityException) {
			throw new SysException("PE002", securityException);
		} catch (NoSuchMethodException noSuchMethodException) {
			throw new SysException("PE002", noSuchMethodException);
		} catch (IllegalArgumentException illegalArgumentException) {
			throw new SysException("PE002", illegalArgumentException);
		} catch (InvocationTargetException invocationTargetException) {
			throw new SysException("PE002", invocationTargetException);
		} catch (ClassNotFoundException classNotFoundException) {
			throw new SysException("PE002", classNotFoundException);
		}
		return objects;
	}
	
	protected PreparedStatement getStatement(Connection conn, Serializable entity, String operationName) throws SysException {
		PreparedStatement statement=null;
		ConfigurationInterface operationConfig=config.getConfigSubset("Entity[@operation='"+operationName+"']");
		String sql=operationConfig.getPropertyAsString("sql");
		String statementType=operationConfig.getPropertyAsString("sql.@type");
		try {
			if (statementType==null || statementType.equals("preparedStatement")) {
				statement=conn.prepareStatement(sql);
			} else if (statementType.equals("call")){
				statement=conn.prepareCall(sql);
			}
		} catch (SQLException sqlException){
			throw new SysException("PE001", sqlException);
		}
		ConfigurationInterface[] dtoInputMappings =
			operationConfig.getList("InputMappings.fieldVal");
		// Preparing Statement based on the sql statement
		prepareStmtFromDTO(statement, entity, dtoInputMappings);
		return statement;
	}

}
