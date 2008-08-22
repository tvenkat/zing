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
package com.iLabs.spice.common.persistence;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.utils.DBUtility;

public class PersistenceCalleableImpl extends PersistenceImpl implements PersistenceInterface {

	public List search(Serializable entity, Connection conn,
			String operationName) throws SysException {
		List dtos = null;

		// Logging operation Name
		if (entity != null) {
			// Retrieving List of field values
			CallableStatement stmt=null;
			if (conn == null) {
				//Getting connection
				conn = DBUtility.getConnection();
			}
			stmt=(CallableStatement)getStatement(conn, entity, operationName);
			
			ResultSet resultSet=null;
			try {
				stmt.execute();
				String sql=config.getPropertyAsString("Entity[@operation='"+operationName+"'].sql");
				String formattedSql=sql.replaceAll(" ","");
				if (formattedSql.indexOf("?=call")>0) {
					Object result=stmt.getObject(1);
					if (result instanceof ResultSet) {
						resultSet=(ResultSet)result;
						// Preparing the ResultSet
						dtos =	populateDTOsFromResultSet(resultSet,
										operationName);
						resultSet.close();
						resultSet=null;
					}
				}
			} catch (SQLException sqlException) {
				throw new SysException("PE001", sqlException);
			}finally {
				try {
					if (stmt!=null){
						stmt.close();
						stmt=null;
					}
					if (resultSet!=null) {
						resultSet.close();
						resultSet=null;
					}
				} catch (SQLException sqlException) {
					throw new SysException("PE001", sqlException);
				}
			}
		}
		return dtos;
	}
}
