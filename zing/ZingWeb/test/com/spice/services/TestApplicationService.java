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

package com.spice.services;

import java.util.List;

import junit.framework.TestCase;

import com.iLabs.spice.common.exception.SysException;
import com.iLabs.spice.common.servicelocator.ServiceLocator;
import com.iLabs.spice.dto.Application;
import com.iLabs.spice.dto.UserApplications;
import com.iLabs.spice.services.IApplication;

public class TestApplicationService extends TestCase {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("COMPOSITE_CONFIG","..\\ShindigWebConfig\\configurations\\configurationControlFile.xml");
	}

	/*public void testAddApplication() throws SysException {
		IApplication application =(IApplication)ServiceLocator.getService("ApplicationSvc");		
		assertEquals(true, application.addApplication(71,5));	
	}*/
	
	public void testCreateApplication() throws SysException {
		IApplication application =(IApplication)ServiceLocator.getService("ApplicationSvc");
		Application application2 = new Application();
		application2.setUrl("aaaaaaaaaaaaasssssdadsfddfdfds");
		assertEquals(true, application.createAndAddApplication(71, application2));	
	}
	
	/*public void testDeleteApplication() throws SysException {
		IApplication application =(IApplication)ServiceLocator.getService("ApplicationSvc");		
		assertEquals(true, application.deleteApplication(71,67));	
	}
	
	public void testGetAllApplication() throws SysException {
		IApplication application =(IApplication)ServiceLocator.getService("ApplicationSvc");		
		List<Application> applicatiosList= (List<Application>) application.getAllApplication();	
		System.out.println("Total No. Of Applications"+applicatiosList.size());
	}

	
	public void testGetPersonApplication() throws SysException {
		IApplication application =(IApplication)ServiceLocator.getService("ApplicationSvc");		
		List<UserApplications> applicatiosList= (List<UserApplications>) application.getPersonApplications(60);	
		System.out.println("Total No. Of  person are Applications"+applicatiosList.size());
	}
*/
}
