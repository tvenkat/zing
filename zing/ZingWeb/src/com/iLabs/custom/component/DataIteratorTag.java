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
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

public class DataIteratorTag extends UIComponentTag {

	private String id="";
	private String headerStyleClass="";
	private String evenRowStyleClass="";
	private String oddRowStyleClass="";
	private String value="";
	private String rows="";
	private String colStyle="";
	private String tableStyle="";
	
	
	public String getTableStyle() {
		return tableStyle;
	}

	public void setTableStyle(String tableStyle) {
		this.tableStyle = tableStyle;
	}

	public String getColStyle() {
		return colStyle;
	}

	public void setColStyle(String colStyle) {
		this.colStyle = colStyle;
	}

	public String getEvenRowStyleClass() {
		return evenRowStyleClass;
	}

	public void setEvenRowStyleClass(String evenRowStyleClass) {
		this.evenRowStyleClass = evenRowStyleClass;
	}

	public String getHeaderStyleClass() {
		return headerStyleClass;
	}

	public void setHeaderStyleClass(String headerStyleClass) {
		this.headerStyleClass = headerStyleClass;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOddRowStyleClass() {
		return oddRowStyleClass;
	}

	public void setOddRowStyleClass(String oddRowStyleClass) {
		this.oddRowStyleClass = oddRowStyleClass;
	}

	public String getRows() {
		return rows;
	}

	public void setRows(String rows) {
		this.rows = rows;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getComponentType() {
		// TODO Auto-generated method stub
		return "iLABS_DATA_ITERATOR";
	}

	public String getRendererType() {
		// TODO Auto-generated method stub
		return "";
	}

	protected void setProperties(UIComponent component) {
		FacesContext context = FacesContext
        .getCurrentInstance();
		component.setRendered(true);
		component.setTransient(true);
			//component.setTransient(true);
		component.setId(id);
			((UIComponentBase) component).getAttributes().put("headerStyleClass", headerStyleClass);
			((UIComponentBase) component).getAttributes().put("evenRowStyleClass", evenRowStyleClass);
			((UIComponentBase) component).getAttributes().put("colStyle", colStyle);
			((UIComponentBase) component).getAttributes().put("oddRowStyleClass", oddRowStyleClass);	
			((UIComponentBase) component).getAttributes().put("tableStyle", tableStyle);
			if (isValueReference(value)) {
				ValueBinding vb = context
				.getApplication().createValueBinding(
						value);
				component.setValueBinding("value", vb);
			} else {
				((UIComponentBase) component).getAttributes().put("value", value);
			}



			
			if (isValueReference(rows)) {
				ValueBinding vb = context
				.getApplication().createValueBinding(
						rows);
				component.setValueBinding("rows", vb);
			} else {
				((UIComponentBase) component).getAttributes().put("rows", rows);
			}

	}
	
	

}
