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
package com.iLabs.custom.component;

import java.io.IOException;
import java.util.Collection;

import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;

public class DataIterator extends UIComponentBase {

	public String getFamily() {
		return "com.iLabs.custom.DataIterator";
	}

	public void encodeBegin(FacesContext arg0) throws IOException {
		UIComponent component=createCustomComponent(arg0);
		component.encodeBegin(arg0);
		component.encodeChildren(arg0);
		component.encodeEnd(arg0);
	}

	public void encodeEnd(FacesContext arg0) throws IOException {
	}
	
	private UIComponent createCustomComponent(FacesContext arg0){
		String headerStyleClass=(String)getAttributes().get("headerStyleClass");
		if (headerStyleClass==null) {
			headerStyleClass=(String)this.getValueBinding("headerStyleClass").getValue(arg0);
		} 
		
		String evenRowStyleClass=(String)getAttributes().get("evenRowStyleClass");
		
		String oddRowStyleClass=(String)getAttributes().get("oddRowStyleClass");
		if (oddRowStyleClass==null) {
			oddRowStyleClass=(String)this.getValueBinding("oddRowStyleClass").getValue(arg0);
		} 
		
		String colStyle=(String)getAttributes().get("colStyle");
		if (colStyle==null) {
			colStyle=(String)this.getValueBinding("colStyle").getValue(arg0);
		} 
		
		Object value=getAttributes().get("value");
		if (value==null) {
			value=this.getValueBinding("value").getValue(arg0);
		}

		String rows=(String)getAttributes().get("rows");
		if (rows==null) {
			rows=(String)this.getValueBinding("rows").getValue(arg0);
		}
		String tableStyle=(String)getAttributes().get("tableStyle");
		if (tableStyle==null) {
			tableStyle=(String)this.getValueBinding("tableStyle").getValue(arg0);
		}
		
		HtmlDataTable table=(HtmlDataTable)arg0.getApplication().createComponent(HtmlDataTable.COMPONENT_TYPE);
		table.setId("priceData");
		table.setRendered(true);
		this.getChildren().add(table);
		if (rows.trim().length()>0) {
			table.setRows(Integer.parseInt(rows));
		}
		
		if (value!=null && (value instanceof Collection) || (value instanceof String[][])) {
			table.setValue(value);
		}
		table.setVar("phonePrice");
		
		if (value instanceof String[][]){
			String[][] pricingInfo=(String[][])value;
			
			if (pricingInfo.length>0) {
				String[] pricingCols=pricingInfo[0];
				table.setFirst(1);
				table.setHeaderClass(headerStyleClass);
				table.setFooterClass(headerStyleClass);
				table.setRowClasses(evenRowStyleClass+", "+oddRowStyleClass);
				table.setColumnClasses(colStyle);
				table.setStyleClass(tableStyle);
				table.setCellspacing("0");
				table.setCellpadding("0");
				table.setBorder(0);
				for(int ctrCols=0;ctrCols<pricingCols.length;ctrCols++) {
					HtmlOutputText outputHeaderText=(HtmlOutputText)arg0.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);
					outputHeaderText.setValue(pricingInfo[0][ctrCols]);
					HtmlOutputText outputFooterText=(HtmlOutputText)arg0.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);
					UIColumn column=(UIColumn)arg0.getApplication().createComponent(UIColumn.COMPONENT_TYPE);
					column.setTransient(true);
					column.setHeader(outputHeaderText);
					column.setFooter(outputFooterText);
					table.getChildren().add(column);
					HtmlOutputText outputText=(HtmlOutputText)arg0.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);
					outputText.setValueBinding("value", arg0.getApplication().createValueBinding("#{phonePrice["+ctrCols+"]}"));
					outputText.setTransient(true);
					column.getChildren().add(outputText);
				}
			}
		}			
		return table;
	}
}
