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
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 * This is a custom JSF component that renders the error message on the page
 * 
 */
public class ErrorHandler extends UIOutput {

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext arg0) throws IOException {
		ResponseWriter writer= arg0.getResponseWriter();
		writer.startElement("DIV", this);
		writer.writeAttribute("class", getAttributes().get("styleClass"), "");
		Iterator messages=arg0.getMessages();
		while (messages.hasNext()) {
			FacesMessage currentMessage=(FacesMessage)messages.next();
			writer.write(currentMessage.getDetail());
			writer.write("<BR>");
			messages.remove();
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.component.UIComponentBase#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext arg0) throws IOException {
		ResponseWriter writer= arg0.getResponseWriter();
		writer.endElement("DIV");
	}

	
}
