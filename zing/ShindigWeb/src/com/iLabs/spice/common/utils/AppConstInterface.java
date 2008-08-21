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

/**
 * This class is used to set the constants used all through the application
 */
public interface AppConstInterface {

	String APPENDER_ATTHERATE = "@";

	String APPENDER_DOT = ".";

	String APPENDER_OPENBRACKET = "[";

	String APPENDER_CLOSEBRACKET = "]";

	String APPENDER_EQUALS = "=";

	String APPENDER_COLON = ":";

	String APPENDER_DBLLESSTHAN = "<<";

	String APPENDER_DBLGREATERTHAN = ">>";

	String APPENDER_SINGLESPACE = " ";

	// Logger types

	String LOG_ERROR = "error";

	String LOG_DEBUG = "debug";

	String LOG_INFO = "info";

	String LOG_WARN = "warn";

	// Transaction Isolocation Levels

	int TRANSACTION_READ_UNCOMMITTED = 0;

	int TRANSACTION_READ_COMMITTED = 1;

	int TRANSACTION_REPEATABLE_READ = 2;

	int TRANSACTION_SERIALIZABLE = 3;

}
