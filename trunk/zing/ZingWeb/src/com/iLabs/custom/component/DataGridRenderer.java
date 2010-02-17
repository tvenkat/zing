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

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlOutputLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.iLabs.pagecode.PageCodeBase;
import com.iLabs.spice.dto.Image;


public class DataGridRenderer extends Renderer {
    public void encodeBegin(FacesContext context, UIComponent component)
        throws IOException {
        UIComponent custPanel = createCustomComponent(component, context);
        component.getChildren().add(custPanel);
        custPanel.setParent(component);

        //custComponent.encodeBegin(context);
        //custComponent.encodeChildren(context);
        //custComponent.encodeEnd(context);
        //context.getViewRoot().processDecodes(context);
    }

    public void encodeEnd(FacesContext context, UIComponent component)
        throws IOException {
        // TODO Auto-generated method stub
        //super.encodeEnd(context, component);
    }

    private UIComponent createCustomComponent(UIComponent custComponent,
        FacesContext arg0) {
        HtmlGraphicImage image = null;
        String columns = (String) custComponent.getAttributes().get("columns");

        String styleClass = (String) custComponent.getAttributes().get("styleClass");

        String style = (String) custComponent.getAttributes().get("style");
        
        String imageStyleClass = (String) custComponent.getAttributes().get("imageStyleClass");
        
        String layoutStyleClass = (String) custComponent.getAttributes().get("layoutStyleClass");
        
        List<Image> value=(List<Image>)custComponent.getAttributes().get("value");
        //Object value=custComponent.getAttributes().get("value");
        String action = "";

        if (custComponent.getAttributes().containsKey("action")) {
            action = (String) custComponent.getAttributes().get("action");
        } else {
            action = custComponent.getValueExpression("action")
                                  .getExpressionString();
        }

        String deleteAction = "";

        if (custComponent.getAttributes().containsKey("deleteAction")) {
            deleteAction = (String) custComponent.getAttributes().get("deleteAction");
        } else {
            deleteAction = custComponent.getValueExpression("deleteAction")
                                        .getExpressionString();
        }

        String linkId = (String) custComponent.getAttributes().get("linkId");

        String userParamName = (String) custComponent.getAttributes().get("userParamName");
        String imageParamName = (String) custComponent.getAttributes().get("imageParamName");
        String propertyName = (String) custComponent.getAttributes().get("propertyName");
        String propertyValue = (String) custComponent.getAttributes().get("propertyValue");

        Boolean showDeleteLink = new Boolean(false);

        if (((String) custComponent.getAttributes().get("showDeleteLink")).trim()
                 .length() > 0) {
            showDeleteLink = Boolean.parseBoolean((String) custComponent.getAttributes()
                                                                        .get("showDeleteLink"));
        }

        String cellpadding = (String) custComponent.getAttributes().get("cellpadding");
        String cellspacing = (String) custComponent.getAttributes().get("cellspacing");

        HtmlPanelGrid panel = (HtmlPanelGrid) arg0.getApplication()
                                                  .createComponent(HtmlPanelGrid.COMPONENT_TYPE);

        UIComponent panelComponent = PageCodeBase.findComponentInRoot(
                "dataPanel");

        if (panelComponent == null) {
            panel.setId("dataPanel");
            panel.setRendered(true);
//            panel.setStyle(style);
            panel.setStyleClass(layoutStyleClass);
            panel.setColumns(Integer.parseInt(columns));

            if ((cellpadding != null) && (cellpadding.trim().length() > 0)) {
                panel.setCellpadding(cellpadding);
            }

            if ((cellspacing != null) && (cellspacing.trim().length() > 0)) {
                panel.setCellspacing(cellspacing);
            }

            panel.setValueBinding("value",
                custComponent.getValueBinding("value"));

            if ((value != null) && value instanceof Collection) {
                Collection data = (Collection) value;

                for (int ctrValues = 0; ctrValues < data.size(); ctrValues++) {
                    HtmlPanelGrid grpPanel = (HtmlPanelGrid) arg0.getApplication()
                                                                 .createComponent(HtmlPanelGrid.COMPONENT_TYPE);
                    grpPanel.setId("grpPanel" + ctrValues);
                    grpPanel.setStyleClass(layoutStyleClass);
//                    grpPanel.setStyle(style);
                    grpPanel.setColumns(1);
                    panel.getChildren().add(grpPanel);
                    grpPanel.setParent(panel);

                    HtmlGraphicImage profileImage = (HtmlGraphicImage) arg0.getApplication()
                                                                           .createComponent(HtmlGraphicImage.COMPONENT_TYPE);

                    String imageURL = "";
                    String userName = "";
                    String caption = "";

                    boolean displayUserName = false;

                    Image currentImage = value.get(ctrValues);

                    imageURL = currentImage.getImageName();

                    if (currentImage.getUserName() != null) {
                        userName = currentImage.getUserName();
                    }

                    if (currentImage.getImageCaption() != null) {
                        caption = currentImage.getImageCaption();
                    }

                    if (((userName != null) && (userName.trim().length() > 0)) ||
                            ((caption != null) &&
                            (caption.trim().length() > 0))) {
                        displayUserName = true;
                    }

                    profileImage.setId(linkId + ctrValues);
                    profileImage.setUrl(imageURL);
//                    profileImage.setStyle(style);
                    profileImage.setStyleClass(imageStyleClass);
					
                    
					
                    HtmlCommandLink textLink = null;
                    HtmlCommandLink deleteLink = (HtmlCommandLink) arg0.getApplication()
                                                                       .createComponent(HtmlCommandLink.COMPONENT_TYPE);
                    
                    HtmlCommandLink picLink = null;
                    
                    
                    deleteLink.setRendered(showDeleteLink.booleanValue());
                    deleteLink.setValue("Delete");

                    if ((userName != null) && (userName.trim().length() > 0)) {
                    	textLink = (HtmlCommandLink) arg0.getApplication()
                                                     .createComponent(HtmlCommandLink.COMPONENT_TYPE);
                    	picLink = (HtmlCommandLink) arg0.getApplication().createComponent(HtmlCommandLink.COMPONENT_TYPE);
                    	textLink.setRendered(displayUserName);
                    	textLink.setValue(userName + " " + caption);
                    	picLink.setValue("");
                        //if ((action.startsWith("#{") && action.endsWith("}"))) {
                        if (action.trim().length() > 0) {
                        	textLink.setActionExpression(arg0.getApplication()
                                                                             .getExpressionFactory()
                                                                             .createMethodExpression(arg0.getELContext(),
                                    action, Object.class, new Class[0]));
                        	
                        	picLink.setActionExpression(arg0.getApplication()
                                    .getExpressionFactory()
                                    .createMethodExpression(arg0.getELContext(),
                                    			action, Object.class, new Class[0]));
                        }
                        
                        picLink.getChildren().add(profileImage);
                        profileImage.setParent(picLink);
                        
                       
                        
                        
                        
                        //} else {
                        //((HtmlCommandLink)text).getAttributes().put("action", action);
                        //}
//                        ((HtmlCommandLink) text).setStyle(style);
//                        ((HtmlCommandLink) text).setStyleClass(styleClass);

                        UIParameter paramFriendIdTextLink = (UIParameter) arg0.getApplication()
                                                                      .createComponent(UIParameter.COMPONENT_TYPE);
                        
                        UIParameter paramFriendIdPicLink = (UIParameter) arg0.getApplication()
                        												.createComponent(UIParameter.COMPONENT_TYPE);
                        
                        paramFriendIdTextLink.setName(userParamName);
                        paramFriendIdTextLink.setValue(currentImage.getUserId());
                        
                        paramFriendIdPicLink.setName(userParamName);
                        paramFriendIdPicLink.setValue(currentImage.getUserId());
                        
                        textLink.getChildren().add(paramFriendIdTextLink);
                        paramFriendIdTextLink.setParent(textLink);
                        
                        picLink.getChildren().add(paramFriendIdPicLink);
                        paramFriendIdPicLink.setParent(picLink);
                        
                        grpPanel.getChildren().add(picLink);
                        grpPanel.getChildren().add(textLink);
                        
                        textLink.setParent(grpPanel);
                        picLink.setParent(grpPanel);
                    } else {
                        if (caption.trim().length() > 0) {
                        	HtmlOutputText text = (HtmlOutputText) arg0.getApplication()
                                                        .createComponent(HtmlOutputText.COMPONENT_TYPE);
                            ((HtmlOutputText) text).setValue(caption);
                           
                        }

                        if (deleteAction.trim().length() > 0) {
                            deleteLink.setActionExpression(arg0.getApplication()
                                                               .getExpressionFactory()
                                                               .createMethodExpression(arg0.getELContext(),
                                    deleteAction, Object.class, new Class[0]));
                        }

                        UIParameter paramImageId = (UIParameter) arg0.getApplication()
                                                                     .createComponent(UIParameter.COMPONENT_TYPE);
                        paramImageId.setName(imageParamName);
                        paramImageId.setValue(currentImage.getImageId());
                        
                        HtmlOutputLink outputLink=(HtmlOutputLink)arg0.getApplication().createComponent(HtmlOutputLink.COMPONENT_TYPE);
    					outputLink.setRel("lightbox[album]");
    					outputLink.setValue(arg0.getExternalContext().getRequestContextPath() + imageURL);
    					
    					HtmlOutputText outputText=(HtmlOutputText)arg0.getApplication().createComponent(HtmlOutputText.COMPONENT_TYPE);
    					outputText.setValue(caption);
    					
                        deleteLink.getChildren().add(paramImageId);
                        paramImageId.setParent(deleteLink);
                        outputLink.getChildren().add(profileImage);
    					grpPanel.getChildren().add(outputLink);
    					profileImage.setParent(outputLink);
    					outputLink.setParent(grpPanel);
    					
    					grpPanel.getChildren().add(outputText);
    					outputText.setParent(grpPanel);
    					
    					grpPanel.getChildren().add(deleteLink);
                        deleteLink.setParent(grpPanel);
                    }
                    
					
				
                    
                    

                    /*
                    HtmlCommandLink htmlCommandLink=(HtmlCommandLink)arg0.getApplication().createComponent(HtmlCommandLink.COMPONENT_TYPE);
                    htmlCommandLink.setId("htmlCommandLink"+ctrValues);
                    htmlCommandLink.setValue(value.get(ctrValues).getProfile().getFirstName());
                    htmlCommandLink.setStyleClass(styleClass);
                    panel.getChildren().add(htmlCommandLink);
                    */
                }
            }
        }

        return panel;
    }
}
