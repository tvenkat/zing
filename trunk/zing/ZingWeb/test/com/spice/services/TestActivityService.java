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
import com.iLabs.spice.dto.Activity;
import com.iLabs.spice.services.IActivity;

public class TestActivityService extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("COMPOSITE_CONFIG","..\\ShindigWebConfig\\configurations\\configurationControlFile.xml");
	}

	public void testCreateActivity() throws SysException {
		IActivity activity =(IActivity)ServiceLocator.getService("ActivitySvc");		
		Activity activityObj= new Activity();
		activityObj.setApplicationId(12);
		activityObj.setBody("Test Body");
		activityObj.setTitle("Test Title");
		int time = (int)System.currentTimeMillis();		
		activityObj.setCreated(time);
		activityObj.setUserId(87);
		activity.createActivity(activityObj);
	}
	public void testGetActivity() throws SysException {
		IActivity activity =(IActivity)ServiceLocator.getService("ActivitySvc");		
		List<Activity> actvityList =(List<Activity>)activity.getActivities(71);
		System.out.println("Total No of activities:  "+actvityList.size());
		for(Activity activityObj: actvityList){
			System.out.println("Activity ID"+ activityObj.getTitle());
			System.out.println("Activity title"+ activityObj.getTitle());
			System.out.println("Activity Body"+ activityObj.getBody());
			System.out.println("Activity Created"+ activityObj.getCreated());
			}
		}
	
}
