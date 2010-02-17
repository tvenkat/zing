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
 * <p>Java class for CodeImage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CodeImage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="codeEntered" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="codeImageCaption" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="codeImageId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="codeImageUrl" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodeImage")
public class CodeImage   extends BaseDTO{

    @XmlAttribute
    protected String codeEntered;
    @XmlAttribute
    protected String codeImageCaption;
    @XmlAttribute
    protected Integer codeImageId;
    @XmlAttribute
    protected String codeImageUrl;

    /**
     * Gets the value of the codeEntered property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeEntered() {
        return codeEntered;
    }

    /**
     * Sets the value of the codeEntered property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeEntered(String value) {
        this.codeEntered = value;
    }

    /**
     * Gets the value of the codeImageCaption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeImageCaption() {
        return codeImageCaption;
    }

    /**
     * Sets the value of the codeImageCaption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeImageCaption(String value) {
        this.codeImageCaption = value;
    }

    /**
     * Gets the value of the codeImageId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getCodeImageId() {
        return codeImageId;
    }

    /**
     * Sets the value of the codeImageId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCodeImageId(Integer value) {
        this.codeImageId = value;
    }

    /**
     * Gets the value of the codeImageUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeImageUrl() {
        return codeImageUrl;
    }

    /**
     * Sets the value of the codeImageUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeImageUrl(String value) {
        this.codeImageUrl = value;
    }

}
