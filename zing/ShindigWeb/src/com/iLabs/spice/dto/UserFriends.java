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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UserFriends complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UserFriends">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="friends" type="{}UserAuth" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;sequence>
 *           &lt;element name="userFriendsImages" type="{}Image" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *         &lt;sequence>
 *           &lt;element name="allFriendActvityList" type="{}Activity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="friendId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="pending">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="yes"/>
 *             &lt;enumeration value="no"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="userId" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserFriends", propOrder = {
    "friends",
    "userFriendsImages",
    "allFriendActvityList"
})
public class UserFriends extends BaseDTO{

    protected List<UserAuth> friends;
    protected List<Image> userFriendsImages;
    protected List<Activity> allFriendActvityList;
    @XmlAttribute
    protected Integer friendId;
    @XmlAttribute
    protected String pending;
    @XmlAttribute
    protected Integer userId;

    /**
     * Gets the value of the friends property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the friends property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFriends().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UserAuth }
     * 
     * 
     */
    public List<UserAuth> getFriends() {
        if (friends == null) {
            friends = new ArrayList<UserAuth>();
        }
        return this.friends;
    }

    /**
     * Gets the value of the userFriendsImages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the userFriendsImages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUserFriendsImages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Image }
     * 
     * 
     */
    public List<Image> getUserFriendsImages() {
        if (userFriendsImages == null) {
            userFriendsImages = new ArrayList<Image>();
        }
        return this.userFriendsImages;
    }

    /**
     * Gets the value of the allFriendActvityList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allFriendActvityList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllFriendActvityList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Activity }
     * 
     * 
     */
    public List<Activity> getAllFriendActvityList() {
        if (allFriendActvityList == null) {
            allFriendActvityList = new ArrayList<Activity>();
        }
        return this.allFriendActvityList;
    }

    /**
     * Gets the value of the friendId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFriendId() {
        return friendId;
    }

    /**
     * Sets the value of the friendId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFriendId(Integer value) {
        this.friendId = value;
    }

    /**
     * Gets the value of the pending property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPending() {
        return pending;
    }

    /**
     * Sets the value of the pending property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPending(String value) {
        this.pending = value;
    }

    /**
     * Gets the value of the userId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets the value of the userId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setUserId(Integer value) {
        this.userId = value;
    }

	public void setFriends(List<UserAuth> friends) {
		this.friends = friends;
	}

	public void setUserFriendsImages(List<Image> userFriendsImages) {
		this.userFriendsImages = userFriendsImages;
	}

}
