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

import java.util.Iterator;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentELTag;

public class DataGridTag extends UIComponentELTag  {

	/**
	 * This attribute contains the syle class as specified in 
	 * the JSP file
	 */
	protected ValueExpression styleClass;
	
	/**
	 * This attribute contains the inline style definition
	 */
	protected ValueExpression style;
	
	/**
	 * This attribute contains the values to be displayed
	 */
	protected ValueExpression value;
	
	/**
	 * This attribute contains the number of columns the data
	 * is required to be shown.
	 */
	protected ValueExpression columns;
	
	/**
	 * This attribute contains the ID for the JSF component
	 */
	protected String id;
	
	/**
	 * This attribute contains the action for this component
	 */
	protected ValueExpression action;
	
	/**
	 * This attribute contains the Id for the links rendered by this component
	 */
	protected ValueExpression linkId;
	
	protected ValueExpression deleteAction;
	
	protected ValueExpression userParamName;
	
	protected ValueExpression imageParamName;
	
	protected ValueExpression propertyName;
	
	protected ValueExpression propertyValue;
	
	protected ValueExpression showDeleteLink;
	
	protected ValueExpression cellPadding;
	
	protected ValueExpression cellSpacing;
	
	protected ValueExpression imageStyleClass;
	
	protected ValueExpression layoutStyleClass;
	
	
	public ValueExpression getCellPadding() {
		return cellPadding;
	}

	public void setCellPadding(ValueExpression cellPadding) {
		this.cellPadding = cellPadding;
	}

	public ValueExpression getCellSpacing() {
		return cellSpacing;
	}

	public void setCellSpacing(ValueExpression cellSpacing) {
		this.cellSpacing = cellSpacing;
	}

	public ValueExpression getActionParamName() {
		return userParamName;
	}

	public void setUserParamName(ValueExpression userParamName) {
		this.userParamName = userParamName;
	}

	public ValueExpression getImageParamName() {
		return imageParamName;
	}

	public void setImageParamName(ValueExpression imageParamName) {
		this.imageParamName = imageParamName;
	}

	public ValueExpression getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(ValueExpression propertyName) {
		this.propertyName = propertyName;
	}

	public ValueExpression getPropertyValue() {
		return propertyValue;
	}

	public void setPropertyValue(ValueExpression propertyValue) {
		this.propertyValue = propertyValue;
	}

	public ValueExpression getDeleteAction() {
		return deleteAction;
	}

	public void setDeleteAction(ValueExpression deleteAction) {
		this.deleteAction = deleteAction;
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getId()
	 */
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#setId(java.lang.String)
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public ValueExpression getLinkId() {
		return linkId;
	}

	/**
	 * @param linkId
	 */
	public void setLinkId(ValueExpression linkId) {
		this.linkId = linkId;
	}

	/**
	 * @return
	 */
	public ValueExpression getAction() {
		return action;
	}

	/**
	 * @param action
	 */
	public void setAction(ValueExpression action) {
		this.action = action;
	}

	/**
	 * @return
	 */
	public ValueExpression getStyle() {
		return style;
	}

	/**
	 * @param style
	 */
	public void setStyle(ValueExpression style) {
		this.style = style;
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	public String getComponentType() {
		return "iLABS_DATA_GRID";
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	public String getRendererType() {
		// TODO Auto-generated method stub
		return "";
	}

	/**
	 * @return
	 */
	public ValueExpression getColumns() {
		return columns;
	}

	/**
	 * @param columns
	 */
	public void setColumns(ValueExpression columns) {
		this.columns = columns;
	}

	/**
	 * @return
	 */
	public ValueExpression getStyleClass() {
		return styleClass;
	}

	/**
	 * @param styleClass
	 */
	public void setStyleClass(ValueExpression styleClass) {
		this.styleClass = styleClass;
	}

	/**
	 * @return
	 */
	public ValueExpression getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(ValueExpression value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	protected void setProperties(UIComponent component) {
		FacesContext context = FacesContext.getCurrentInstance();
		component.setRendered(true);
		findAndRemoveComponentInRoot(id);
		findAndRemoveComponentInRoot("dataPanel");
		component.setTransient(true);
		component.setId(id);
		component.setValueExpression("styleClass", styleClass);
		component.setValueExpression("style", style);
		component.setValueExpression("value", value);
		component.setValueExpression("columns", columns);
		component.setValueExpression("propertyValue", propertyValue);
		component.setValueExpression("showDeleteLink", showDeleteLink);
		
		
		((UIComponentBase) component).getAttributes().put("action", action);
		((UIComponentBase) component).getAttributes().put("deleteAction", deleteAction);
		((UIComponentBase) component).getAttributes().put("imageStyle", imageStyleClass);
		((UIComponentBase) component).getAttributes().put("layoutStyle", layoutStyleClass);
		((UIComponentBase) component).getAttributes().put("linkId", linkId);
		((UIComponentBase) component).getAttributes().put("userParamName", userParamName);
		((UIComponentBase) component).getAttributes().put("imageParamName", userParamName);
		((UIComponentBase) component).getAttributes().put("propertyName", propertyName);
		((UIComponentBase) component).getAttributes().put("cellPadding", cellPadding);
		((UIComponentBase) component).getAttributes().put("cellPadding", cellSpacing);
		
		
	}
	
	/**
	 * @param base
	 * @param id
	 * @return
	 */
	public UIComponent findAndRemoveComponent(UIComponent base, String id) {

		// Is the "base" component itself the match we are looking for?
		if (id.equals(base.getId())) {
			return base;
		}

		// Search through our facets and children
		UIComponent kid = null;
		UIComponent result = null;
		Iterator kids = base.getFacetsAndChildren();
		while (kids.hasNext() && (result == null)) {
			kid = (UIComponent) kids.next();
			if (id.equals(kid.getId())) {
				result = kid;
				base.getChildren().remove(kid);
				break;
			}
			result = findAndRemoveComponent(kid, id);
			if (result != null) {
				break;
			}
		}
		return result;
	}

	/**
	 * @param id
	 * @return
	 */
	public UIComponent findAndRemoveComponentInRoot(String id) {
		UIComponent ret = null;

		FacesContext context = FacesContext.getCurrentInstance();
		if (context != null) {
			UIComponent root = context.getViewRoot();
			ret = findAndRemoveComponent(root, id);
		}

		return ret;
	}

	public ValueExpression getShowDeleteLink() {
		return showDeleteLink;
	}

	public void setShowDeleteLink(ValueExpression showDeleteLink) {
		this.showDeleteLink = showDeleteLink;
	}

	public ValueExpression getImageStyleClass() {
		return imageStyleClass;
	}

	public void setImageStyleClass(ValueExpression imageStyleClass) {
		this.imageStyleClass = imageStyleClass;
	}

	public ValueExpression getLayoutStyleClass() {
		return layoutStyleClass;
	}

	public void setLayoutStyleClass(ValueExpression layoutStyleClass) {
		this.layoutStyleClass = layoutStyleClass;
	}

	
}
