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
package com.iLabs.spice.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserScrap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserScrap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="recieverId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="scrapContent" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="scrapId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="senderId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="senderImage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="senderName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserScrap")
public class UserScrap  extends BaseDTO{

    @XmlAttribute
    protected Integer recieverId;
    @XmlAttribute
    protected String scrapContent;
    @XmlAttribute
    protected Integer scrapId;
    @XmlAttribute
    protected Integer senderId;
    @XmlAttribute
    protected String senderImage;
    @XmlAttribute
    protected String senderName;
    
    @XmlAttribute
    protected Boolean renderd;
    

    /**
     * Gets the value of the recieverId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRecieverId() {
        return recieverId;
    }

    /**
     * Sets the value of the recieverId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRecieverId(Integer value) {
        this.recieverId = value;
    }

    /**
     * Gets the value of the scrapContent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getScrapContent() {
        return scrapContent;
    }

    /**
     * Sets the value of the scrapContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setScrapContent(String value) {
        this.scrapContent = value;
    }

    /**
     * Gets the value of the scrapId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getScrapId() {
        return scrapId;
    }

    /**
     * Sets the value of the scrapId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setScrapId(Integer value) {
        this.scrapId = value;
    }

    /**
     * Gets the value of the senderId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getSenderId() {
        return senderId;
    }

    /**
     * Sets the value of the senderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSenderId(Integer value) {
        this.senderId = value;
    }

    /**
     * Gets the value of the senderImage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderImage() {
        return senderImage;
    }

    /**
     * Sets the value of the senderImage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderImage(String value) {
        this.senderImage = value;
    }

    /**
     * Gets the value of the senderName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * Sets the value of the senderName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSenderName(String value) {
        this.senderName = value;
    }

	public Boolean getRenderd() {
		return renderd;
	}

	public void setRenderd(Boolean renderd) {
		this.renderd = renderd;
	}

}
