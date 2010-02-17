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
package com.iLabs.custom.component;

import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

/**
 * This is the tag class for the custom error tag as defined in its 
 * respective tag library definition.
 * @author user
 *
 */
public class ErrorTag extends UIComponentTag {
	
	/**
	 * This attribute contains the style class
	 */
	private String styleClass="";
	
	/**
	 * @return
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * @param styleClass
	 */
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		return "SPICE_ERRORS";
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	protected void setProperties(UIComponent component) {
		FacesContext context = FacesContext
        .getCurrentInstance();
		super.setProperties(component);
		
		if (isValueReference(styleClass)) {
			ValueBinding vb = context
			.getApplication().createValueBinding(
					styleClass);
			component.setValueBinding("styleClass", vb);
		} else {
			((UIOutput) component).getAttributes().put("styleClass", styleClass);
		}
		
  }

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		return null;
	}


	
}
