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
import java.sql.Connection;
import java.util.List;

import com.iLabs.spice.common.exception.SysException;

/**
 * This is the persistence interface used by the persistence service implementations. 
 * Every persistence interface has to comply to this interface, in order to be consumed
 * by other services of the system. This interface provides methods for performing basic 
 * Create, Read, Update, Delete (CRUD) of the objects in the database. Additionally, it also
 * provides supporting method for performing a finder operation. This abstraction provides the system
 * with the flexibility to be easily replaceable with other industry wide persistencse frameworks as 
 * well, mking this layer loosely-coupled with the application, due to separation of implementation with 
 * interface. Any other service or the front-end layer would only know about this interface and would 
 * not be aware of the underlying implementation.
 *
 */
public interface PersistenceInterface {

	/**
	 * This method provides the capability to persist a new object in the database. The object thus passed 
	 * has to be a Data Transfer Object (DTO). This DTO would be directly mapped to a database entity in 
	 * the database. 
	 * @param 	entity  		The DTO representing an entity in the database.
	 * @param 	conn			This is the connection object that would be pass from the service.
	 * 							If this object is null, persistence implementation would create
	 * 							a new connection.
	 * @param	operationName	This is the name of the database operation to be performed as 
	 * 							defined in the application configuration.
	 * @return 	int 		This is the number of affected records.
	 */
	
	public int create(Serializable entity, Connection conn, String operationName) throws SysException;

	/**
	 * This method provides the capability to read an object from the database. The object thus returned 
	 * is  a Data Transfer Object (DTO). This DTO would be directly mapped to a database entity in 
	 * the database. The object is queried by passing a DTO with certain populated fields representing the
	 * search criteria.
	 * @param 	entity  		The DTO contaning fields representing the search criteria.
	 * @param 	conn			This is the connection object that would be pass from the service.
	 * 							If this object is null, persistence implementation would create
	 * 							a new connection.
	 * @param	operationName	This is the name of the database operation to be performed as 
	 * 							defined in the application configuration.
	 * @return 	Serializable 	The object returned from the database.
	 */
	public Serializable read(Serializable entity, Connection conn, String operationName)  throws SysException;
	
	/**
	 * This method provides the capability to update an object in the database. The object thus updated 
	 * is  returned as a Data Transfer Object (DTO). This DTO would be directly mapped to a database entity in 
	 * the database. The object is updated by passing a DTO with all populated fields representing the
	 * updated object.
	 * @param 	entity  		The DTO contaning all the fields with desired values.
	 * @param 	conn			This is the connection object that would be pass from the service.
	 * 							If this object is null, persistence implementation would create
	 * 							a new connection.
	 * @param	operationName	This is the name of the database operation to be performed as 
	 * 							defined in the application configuration.
	 * @return 	int 			This is the number of affected records.
	 * @throws SysException 
	 */
	public int update(Serializable entity, Connection conn, String operationName)  throws SysException;

	/**
	 * This method provides the capability to delete an object from the database. If the object is  
	 * successfully deleted, a boolean value of 'true' is returned else a boolean value of 'false' is 
	 * returned.
	 * @param 	entity  		The DTO contaning fields to identity the entity in the database.
	 * @param 	conn			This is the connection object that would be pass from the service.
	 * 							If this object is null, persistence implementation would create
	 * 							a new connection.
	 * @param	operationName	This is the name of the database operation to be performed as 
	 * 							defined in the application configuration.
	 * @return 	int 		    This is the number of affected records.
	 * @throws SysException 
	 */
	public int delete(Serializable entity, Connection conn, String operationName) throws SysException;

	/**
	 * This method provides the capability to read multiple objects from the datbase matching a particuler
	 * search. The objects thus returned are Data Transfer Objects (DTOs). These DTOs are returned as an 
	 * array. The objects are queried by passing a DTO with certain populated fields representing the
	 * search criteria.
	 * @param 	entity  		The DTO contaning fields representing the search criteria.
	 * @param 	conn			This is the connection object that would be pass from the service.
	 * 							If this object is null, persistence implementation would create
	 * 							a new connection.
	 * @param	operationName	This is the name of the database operation to be performed as 
	 * 							defined in the application configuration.
	 * @return 	Serializable[] 	The objects returned from the database.
	 */
	public List search(Serializable entity, Connection conn, String operationName) throws SysException;


	/**
	 * This method provides the capability to persist a new object in the database. The object thus passed 
	 * has to be a Data Transfer Object (DTO). This DTO would be directly mapped to a database entity in 
	 * the database. 
	 * @param 	entity  		The DTO representing an entity in the database.
	 * @param 	conn			This is the connection object that would be pass from the service.
	 * 							If this object is null, persistence implementation would create
	 * 							a new connection.
	 * @param	operationName	This is the name of the database operation to be performed as 
	 * 							defined in the application configuration.
	 * @return 	int 		This is the number of affected records.
	 */
	
	public int createAndReturnId(Serializable entity, Connection conn, String operationName) throws SysException;

}
