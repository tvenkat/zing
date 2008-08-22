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

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.iLabs.spice.dto package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory  extends BaseDTO{

    private final static QName _NewElement_QNAME = new QName("", "NewElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.iLabs.spice.dto
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UserAlbum }
     * 
     */
    public UserAlbum createUserAlbum() {
        return new UserAlbum();
    }

    /**
     * Create an instance of {@link ApplicationSettings }
     * 
     */
    public ApplicationSettings createApplicationSettings() {
        return new ApplicationSettings();
    }

    /**
     * Create an instance of {@link UserBroadcast }
     * 
     */
    public UserBroadcast createUserBroadcast() {
        return new UserBroadcast();
    }

    /**
     * Create an instance of {@link UserApplications }
     * 
     */
    public UserApplications createUserApplications() {
        return new UserApplications();
    }

    /**
     * Create an instance of {@link Activity }
     * 
     */
    public Activity createActivity() {
        return new Activity();
    }

    /**
     * Create an instance of {@link Application }
     * 
     */
    public Application createApplication() {
        return new Application();
    }

    /**
     * Create an instance of {@link UserPresence }
     * 
     */
    public UserPresence createUserPresence() {
        return new UserPresence();
    }

    /**
     * Create an instance of {@link UserScrap }
     * 
     */
    public UserScrap createUserScrap() {
        return new UserScrap();
    }

    /**
     * Create an instance of {@link UserFriends }
     * 
     */
    public UserFriends createUserFriends() {
        return new UserFriends();
    }

    /**
     * Create an instance of {@link CodeImage }
     * 
     */
    public CodeImage createCodeImage() {
        return new CodeImage();
    }

    /**
     * Create an instance of {@link UserAuth }
     * 
     */
    public UserAuth createUserAuth() {
        return new UserAuth();
    }

    /**
     * Create an instance of {@link ActivityMedia }
     * 
     */
    public ActivityMedia createActivityMedia() {
        return new ActivityMedia();
    }

    /**
     * Create an instance of {@link Image }
     * 
     */
    public Image createImage() {
        return new Image();
    }

    /**
     * Create an instance of {@link SendMail }
     * 
     */
    public SendMail createSendMail() {
        return new SendMail();
    }

    /**
     * Create an instance of {@link UserProfile }
     * 
     */
    public UserProfile createUserProfile() {
        return new UserProfile();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NewElement")
    public JAXBElement<String> createNewElement(String value) {
        return new JAXBElement<String>(_NewElement_QNAME, String.class, null, value);
    }

}
