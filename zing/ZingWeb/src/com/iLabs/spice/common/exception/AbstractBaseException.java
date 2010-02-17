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
package com.iLabs.spice.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.iLabs.spice.common.config.ConfigurationHandler;
import com.iLabs.spice.common.config.ConfigurationInterface;
import com.iLabs.spice.common.logging.SpiceLoggerImpl;
import com.iLabs.spice.common.utils.AppConstInterface;

/**
 * Description : This class is responsible for handling exceptions. Exceptions
 * can be thrown by the components in the application. They can be either
 * system, application or business exceptions. If an error occurs at any stage
 * in the life cycle of application the same is thrown back. The specific
 * message for the corresponding exception is read from the xml,property file
 * and is populated at the front end.
 */

public abstract class AbstractBaseException extends Exception {
	
	private String errorId;
	
	private static final String ERROR_BEGIN_TAG="<<";
	
	private static final String ERROR_END_TAG=">>";
	
	private static final String ERROR_METHOD_SEPARATOR=".";
	
	private static final String ERROR_MESSAGE_SEPARATOR=":";
	
	private static final String ERROR_MESSAGE_FILLER=" ";
	
	private static final String CEM_ERROR_STRING_QUERY="CEMError.@value";
	
	private static final ConfigurationInterface config=ConfigurationHandler.getApplicationConfiguration();
	
	SpiceLoggerImpl logger = null;

	public AbstractBaseException(String errorId) {
		super();		
		this.errorId=errorId;	
		performErrorAction();
	}

	public AbstractBaseException(String errorId, String message) {
		super(message);
		this.errorId = errorId;	
		performErrorAction();
	}

	public AbstractBaseException(String errorId, Throwable cause) {
		super(cause);
		this.errorId = errorId;	
		performErrorAction();
	}

	public AbstractBaseException(String errorId, String message, Throwable cause) {
		super(message, cause);
		this.errorId = errorId;	
		performErrorAction();
	}

	/**
	 * This method takes the class object, method name, error code, message and
	 * throwable object. The error code is used to identify the severity of the
	 * exception thrown, based on which an appropriate error message is logged
	 * 
	 * @param errorId
	 *            java.lang.String
	 * @param message
	 *            java.lang.String
	 * @param throwableException
	 *            java.lang.Throwable
	 * @return boolean true or false
	 */

	private void performErrorAction() {
		String className = getStackTrace()[0].getClassName();
		String methodName = getStackTrace()[0].getMethodName();
	
		StringBuffer logMessage=new StringBuffer();
		logMessage.append(ERROR_BEGIN_TAG)
						.append(className)
						.append(ERROR_METHOD_SEPARATOR)
						.append(methodName)
						.append(ERROR_END_TAG)
						.append(ERROR_MESSAGE_SEPARATOR)
						.append(ERROR_BEGIN_TAG)
						.append(getCEMErrorMessage())
						.append(ERROR_END_TAG);
		String strLoggerMessage=logMessage.toString();
		try {
			logger = new SpiceLoggerImpl(Class.forName(className));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		String strSeverity=getErrorSeverity();
		if (strSeverity
				.equalsIgnoreCase(AppConstInterface.LOG_ERROR)) {
			logger.error(strLoggerMessage);
		}
		if (strSeverity
				.equalsIgnoreCase(AppConstInterface.LOG_INFO)) {
			logger.info(strLoggerMessage);
		}
		if (strSeverity
				.equalsIgnoreCase(AppConstInterface.LOG_WARN)) {
			logger.warn(strLoggerMessage);
		}	
		if (this.getCause()!=null && (this.getCause().getCause()==null)) {
			logger.error(generateStackTraceString());
		}
	}

	/**
	 * This method is used to convert stackTrace into String so that it will be
	 * Serialized
	 * 
	 * @param cause
	 *            java.lang.Throwable
	 * @return String stackTrace
	 */
	private String generateStackTraceString() {
		StringWriter stringWriter = new StringWriter();
		if (this.getCause()!=null) {
			this.getCause().printStackTrace(new PrintWriter(stringWriter));
		}
		return stringWriter.toString();
	}
	
	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		this.errorId = errorId;		
	}
	
	public String getCEMErrorSeverity(){
		String cemErrorSeverity=config.getPropertyAsString("Error[@errorId='"+errorId+"'].@cemErrorSeverity");
		if (cemErrorSeverity==null) {
			cemErrorSeverity="1";
		}
		return cemErrorSeverity;
	}
	
	public String getErrorSeverity(){
		String errorSeverity=config.getPropertyAsString("Error[@errorId='"+errorId+"'].@severity");;
		if (errorSeverity==null) {
			errorSeverity="error";
		}
		return errorSeverity;
	}
	
	public String getCEMErrorMessage(){
		String cemErrorString=config.getPropertyAsString(CEM_ERROR_STRING_QUERY);
		
		StringBuffer cemError=new StringBuffer();
		cemError.append(cemErrorString)
				.append(getCEMErrorSeverity())
				.append(ERROR_MESSAGE_FILLER)
				.append(getErrorMessage());
		return cemError.toString();
	}
	
	public String getErrorMessage() {
		String errorMessage=null;
		errorMessage=config.getPropertyAsString("Error[@id='"+errorId+"'].@value");
		if (errorMessage==null || errorMessage.trim().length()==0){
			errorMessage=this.getMessage();
		} 
		if (errorMessage==null || errorMessage.trim().length()==0) {
			errorMessage=config.getPropertyAsString("Error[@id='default_message'].@value");
		}
		return errorId+ERROR_MESSAGE_FILLER+errorMessage;
	}
}