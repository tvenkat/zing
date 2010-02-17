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

import com.iLabs.spice.common.config.ConfigurationInterface;
import com.iLabs.spice.common.logging.SpiceLoggerImpl;

/**
 * 
 * This is the base service for the implementation of the logger service and the
 * configuration service of the application.
 * 
 */

public abstract class ServiceImplBase {

	/**
	 * This is a reference to the logger implementation of the application.
	 */
	protected SpiceLoggerImpl logger = null;

	/**
	 * This is a reference to the application configuration for all the services
	 * of the application.
	 */
	protected ConfigurationInterface config = null;
}
