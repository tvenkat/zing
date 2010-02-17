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
package com.iLabs.spice.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SendMail complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SendMail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="contents" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mailFrom" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mailTo" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="subject" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SendMail")
public class SendMail  extends BaseDTO{

    @XmlAttribute
    protected String contents;
    @XmlAttribute
    protected String mailFrom;
    @XmlAttribute
    protected String mailTo;
    @XmlAttribute
    protected String subject;

    /**
     * Gets the value of the contents property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContents() {
        return contents;
    }

    /**
     * Sets the value of the contents property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContents(String value) {
        this.contents = value;
    }

    /**
     * Gets the value of the mailFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailFrom() {
        return mailFrom;
    }

    /**
     * Sets the value of the mailFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailFrom(String value) {
        this.mailFrom = value;
    }

    /**
     * Gets the value of the mailTo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMailTo() {
        return mailTo;
    }

    /**
     * Sets the value of the mailTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMailTo(String value) {
        this.mailTo = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubject(String value) {
        this.subject = value;
    }

}
