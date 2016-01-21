# Introduction #

The purpose of this document is to explicate the detailed technical architecture of the Zing, which is a Social Networking Site (!SNS) !vis-!à-!vis Java Shindig OpenSocial Reference implementation.

In this document a thorough inspection of all the individual constituent components and the features supported by Shindig server in technical perspective.


# Project Stack #

## Hardware Stack ##

As provided.

## Software Stack ##

This section enlists all the software that has been used for the application development.

  1. Java 1.6 (!jdk version 1.6.0\_02)
  1. !MySQL 5.0
  1. Java Shindig Server(OpenSocial-0.8)
  1. Jetty Server 6.1.9 (J2EE 2.5 Spec)
  1. !JSF 1.2 with !Facelets 1.1 and Trinidad component library 1.0.8


# Architectural Components #



## Zing Architecture Diagram ##
![http://zing.googlecode.com/svn/trunk/zing/images/Architecture_Overview.gif](http://zing.googlecode.com/svn/trunk/zing/images/Architecture_Overview.gif)

![http://zing.googlecode.com/svn/trunk/zing/images/Architecture_Detailed.gif](http://zing.googlecode.com/svn/trunk/zing/images/Architecture_Detailed.gif)

### Configuration Handler ###
This class is a configuration handler for the entire application. It makes use of the apache commons configuration internally. The objective of this class is to manage all types of properties (.properties/.xml and system) by the use of a common interface (ConfigInterface).

### Persistence Framework ###
This framework is a reusable component that is used for entire database access across the Zing application. This framework uses the configuration interface for retrieving the persistence configuration to map the objects to queries.

### Services Factory ###
This is a factory class responsible for singleton instantiation of services in the application that are stateless in nature. Service Locator uses the service factory for one-time instantiation of service objects

### Exception Handler ###
The exception handler handles the application exception and uses the logger and configuration framework to log the appropriate exception and re-throw it appropriately

### Logger ###
This is an application wide logger that is responsible for logging messages in the log file.

### Data Objects ###
These are the objects that hold the state for the user requests and responses for rendering information on the web pages. They are serializable for future remotability.

### SNS Container ###

This is basically web container that hosts Java Social Networking Site (SNS) which supports various different features. To make this site open social compatible, i.e., it can host the OpenSocial gadgets; it is integrated with Shindig Java Server, which is an open source implementation of the OpenSocial specification and gadgets specification.

SNS site is also referred as container, as it can host the OpenSocial gadgets and conforms to the open social specifications. The term container is bit bewildering and used to refer to SNS site and gadget container interchangeably.



### Gadget Container (iFrame) ###

The integration of Shindig with SNS is done via iFrame, which act as a container to host the gadgets inside the SNS. iFrame provides the environment to execute the gadgets inside the web pages.

iFrame is the significant means for the integration of SNS with the Shindig server.  Below is the code snippet use to embed the gadgets within the SNS.

#### Code Snippet ####
```
<iframe src="http://path/to/shindig/gadgets/ifr?parent=http://path/to/shindig&amp;url=http://url/of/gadget &amp;st=viewer_id:owner_id:app_key:domain_key:app_url_key:module_key">
</iframe>
```

Parameter Description:

|**src**|**Path to handler to render gadget**|
|:------|:-----------------------------------|
|**url**|**URL for the gadget to be rendered**|
|**st** |**Security Token**                  |



### SNS Custom Service Layer ###

Custom Service Layer is used by the SNS to fetch the data from the database bypassing the Shindig Container service layer. This provides the flexibility and performance improvement over retrieving the data from Shindig Service Layer via RESTful services.

### Shindig Container ###

Shindig container is an open source implementation of the OpenSocial specification and gadgets specification. It executes atop of Servlet APIs and handles various request of Gadget rendering and Data serving to the gadgets and pages over the HTTP protocol.

Various features provided by the Shindig container are CAJA, OAuth etc. Adding to this, it also contains a service layer, which inscribes people services, activity service and application service, which talks with the database layer to retrieve respective data.

### Gadget Rendering Handler ###

The sole responsibility of this component is to handle the Gadget Rendering request and returns the rendered component over the HTTP protocol. This component works vis-à-vis Gadget Server which renders the gadget XML into JavaScript and HTML for the container to expose via the container JavaScript.

The gadget rendering request diverted to the Gadget Rendering Handler, which contains the following information as query string:

  1. Gadget URL
  1. Security Token


### RESTful Service Handler ###

The RESTful capabilities of the Shindig container are exposed through this handler. It serves to fulfill the OpenSocial data request via RESTful calls. When a gadget is rendered within the SNS, it simultaneously makes the OpenSocial data request, which is handled through this component of Shindig Container.

This component works in conjunction with Shindig Container Service Layer to fetch data on its behalf.

Following are the different scenario for feting user\’s data (profile, activities, & app data)

  1. erson\’s Profile Data

  1. Collection of all people connected to user \{guid\}
> ''http://path/to/shindig/social/rest/people/\{guid\}/@all''

  1. Collection of all friends of user \{guid\}
> ''http://path/to/shindig/social/rest/people/\{guid\}/@friends''

  1. Collection of all people connected to user \{guid\} in group \{groupid\}
> ''http://path/to/shindig/social/rest/people/\{guid\}/\{groupid\}''

  1. Individual person record for a specific person known to \{guid\}; shows \{guid\}\’s view of \{pid\}
> ''http://path/to/shindig/social/rest/people/\{guid\}/@all/\{pid\}                ''

  1. Profile record for user \{guid\}
> ''http://path/to/shindig/social/rest /people/\{guid\}/@self''

  1. Profile record for requestor
> ''http://path/to/shindig/social/rest /people/@me/@self                       ''

  1. Activities Data

  1. Collection  of Activities generated by given user \{guid\}
> > ''http://path/to/shindig/social/rest/activities/\{guid\}/@self''

  1. Collection of all friends of user \{guid\} Collection of activities for friends of the user \{guid\}
> > ''http://path/to/shindig/social/rest/activities/\{guid\}/@friends             ''

  1. Collection of activities for people in group \{groupid\} belonging to given user \{uid \}

> ''http://path/to/shindig/social/rest/activities/\{guid\}/\{groupid\}            ''

  1. Individual activity resource; usually discovered from collection
> ''http://path/to/shindig/social/rest/activities/\{guid \}/@self/\{activityid\}   ''



  1. ppData (Application Data)

  1. All app data for user \{guid\}, app \{appid\}
> ''http://path/to/shindig/social/rest/appdata/\{guid\}/@self/\{appid\}''

  1. app data for friends of user \{guid\} and app \{appid\}
> ''http://path/to/shindig/social/rest/appdata/\{guid\}/@friends/\{appid\} ''

  1. Just the count field for user \{guid\}, app \{appid\}
> ''http://path/to/shindig/social/rest/appdata/\{guid\}/@self/\{appid\}?fields=count ''


All of the above requests can have standard query parameter like **format (format is used to tell the container in which format output is required. The two standard values of format are json & atom. Container may support more other than these two but these two are must)**.


## Shindig Container Features ##

Shindig contains various features, of which CAJA and OAuth were exploited within the SNS. These features are elaborated below:

### Caja ###

Using Caja, Shindig Container can safely allow scripts gadgets provided by third party. Caja enables documents to carry active content safely, i.e., scripts in web pages.

Web apps generally remove scripts from third party content, reducing content to passive data. CAJA component of Shindig container cajoles the JavaScript embedded inside the gadgets and provides the script in object capability language, using which web apps web apps could provide active content safely, simply, and flexibly.

There were some tweakins made to enable the CAJA feature in Shindig, which is disable by default are enlisted below:

  1. Uncomment the line\#23 =feature.xml= file located at: Shindig\_SRC\features\opensocial-current so that it can include the CAJA dependency. Find the code snippet below:
```
  <name>opensocial-0.8</name>
  <dependency>opensocial-reference</dependency>
  =<dependency>caja</dependency> =
  <!-- Must include the "caja" feature to display samplecontainer -->
  <!-- gadgets when "use caja" is checked -->
  <gadget>

  # Change the =container.js= located at Shindig_SRC\shindig\config to modify the parameter =enableCaja =to true at line\#103. Find the code snippet below:

"opensocial-0.8" : \{
    // Path to fetch opensocial data from
    // Must be on the same domain as the gadget rendering server
    "path" : "/social/rest",
    "domain" : "shindig",
    ="enableCaja" : true,=
    "supportedFields" : \{
       "person" : \["id", "name", "thumbnailUrl", "profileUrl"\],
       "activity" : \["id", "title"\]
    \}
  \}
```
### OAuth ###

OAuth allows to share private resources (photos, videos, contact list, bank accounts) stored on one site with another site without having to hand out username and password.

Within this process, site (OAuth Consumer) trying to access the resources on behalf of its user, makes request to third party (OAuth Service provider) using Request Token, and receives the Access token, after the consumer is being authorized by the user. After receiving the Access Token from service provider, consumer can act authorized party to access the user resources on the service provider.

To use this service, service provider provides secret key to the consumer, which act as a Request Token for the specific gadget.

Shindig works as a consumer to OAuth service and a secret key need to be provided to be provided to formulate the Request Token for a specific gadget. This key is provided in =oauth.json= file located at Shindig\_SRC\shindig\config.

The snippet for the same is given below:
```
\{
"Gadget URL" : \{
    "Service_Name" : \{
"consumer_key" : "Any Consumer Key",
"consumer_secret" : "Any Secret Key",
     	 "key_type" : "Key Type"
    \}
  \}
```
Parameter Description:

|**GadgetURL**|URL of the gadget that uses OAuth services|
|:------------|:-----------------------------------------|
|**Service\_Name**|Name of the service                       |
|**consumer\_key**|Consumer Key provided by service provider |
|_consumer\_secret**key**_|Secret key provided by the service provider|
|**key\_type**|Specifies the key type, like RSA, HMAC etc|



Same snippet need to be embedded for each new gadget using OAuth feature.

## Service Layer ##

Shindig Service Layer comprises of the services which conforms to OpenSocial specs. It comprises of People Service, Activity Service, and Application Service.

Elaboration to each of the services is given below:

### People Service ###

People Service provides the list of people and friends bound to this SNS. This service forms the epicenter of complete OpenSocial spec.

### Activity Service ###

The Activity Service defines the service provider interface to retrieve the activities from the underlying SNS database.


### Application Service ###

The Application Service defines the service provider interface that is used to retrieve information bound to a person. Also, there are methods to update an delete data.


## ZING DB Schema ##

Attached is the MYSQL DB Structure used in ZING:

### Activities ###

|**Field**|**Type**|**Null**|**Default**|
|:--------|:-------|:-------|:----------|
|activity\_id|int(11) |Yes     |NULL       |
|user\_id |int(11) |Yes     |-          |
|application\_id|int(11) |Yes     |-          |
|title    |char(128)|Yes     |-          |
|body     |char(255)|Yes     |-          |


### Media\_items ###

|**Field**|**Type**|**Null**|**Default**|
|:--------|:-------|:-------|:----------|
|id       |int(11) |Yes     |NULL       |
|activity\_id|int(11) |Yes     |-          |
|mime\_type|char(64)|Yes     |-          |
|media\_type|char(10)|Yes     |-          |
|url      |char(128)|Yes     |-          |



### Applications ###

|**Field**|**Type**|**Null**|**Default**|
|:--------|:-------|:-------|:----------|
|id       |int(11) |Yes     |NULL       |
|=application\_url=|char(128)|Yes     |-          |
|title    |char(128)|Yes     |NULL       |
|thumbnail|char(128)|Yes     |NULL       |
|author   |char(128)|Yes     |NULL       |
|author\_email|char(128)|Yes     |NULL       |
|description|mediumtext|Yes     |NULL       |
|settings |mediumtext|Yes     |NULL       |
|version  |varchar(64)|Yes     |-          |
|height   |int(11) |Yes     |0          |
|order    |int(11) |Yes     |-          |


### Application\_data ###

|**Field**|**Type**|**Null**|**Default**|
|:--------|:-------|:-------|:----------|
|application\_id|int(11) |Yes     |-          |
|**user\_id**|int(11) |Yes     |-          |
|**module\_id**|int(11) |Yes     |-          |
|**Key**  |char(128)|Yes     |-          |
|value    |char(255)|Yes     |-          |


### User\_album ###

|**Field**|**Type**|**Null**|**Default**|
|:--------|:-------|:-------|:----------|
|user\_id |int(11) |Yes     |-          |
|photo\_id|int(11) |Yes     |-          |
|photo\_name|varchar(50)|Yes     |-          |
|thumb\_name|varchar(50)|Yes     |-          |
|photo\_caption|varchar(100)|Yes     |-          |


### User\_applications ###

|**Field**|**Type**|**Null**|**Default**|
|:--------|:-------|:-------|:----------|
|id       |int(11) |Yes     |NULL       |
|user\_id |int(11) |Yes     |-          |
|application\_id|int(11) |Yes     |-          |


### User\_broadcast ###

|**Field**|**Type**|**Null**|**Default**|
|:--------|:-------|:-------|:----------|
|user\_id |int(11) |Yes     |-          |
|header   |varchar(100)|Yes     |NULL       |
|article  |varchar(1024)|Yes     |NULL       |
|article\_title|varchar(50)|Yes     |NULL       |
|time\_expire|datetime|Yes     |-          |


### User\_friend ###

|**Field**|**Type**|**Null**|**Default**|
|:--------|:-------|:-------|:----------|
|user\_id |int(11) |Yes     |-          |
|**friend\_id**|int(11) |Yes     |-          |
|pending  |enum(\’yes\’, \’no\’)|Yes     |-          |


### User\_main ###

|**Field**|**Type**|**Null**|**Default**|
|:--------|:-------|:-------|:----------|
|user\_id |int(11) |Yes     |NULL       |
|user\_name|varchar(25)|Yes     |-          |
|user\_password|varchar(25)|Yes     |-          |
|user\_email|varchar(128)|Yes     |-          |


### User\_online\_status ###

|**Field**|**Type**|**Null**|**Default**|
|:--------|:-------|:-------|:----------|
|user\_id |int(11) |Yes     |-          |
|last\_online\_time|datetime|Yes     |-          |
|online\_status|enum(\’yes\’, \’no\’)|Yes     |-          |


### User\_profile ###

|**Field**|**Type**|**Null**|**Default**|
|:--------|:-------|:-------|:----------|
|user\_id |int(11) |Yes     |-          |
|first\_name|varchar(25)|Yes     |NULL       |
|last\_name|varchar(25)|Yes     |NULL       |
|Gender   |enum(\’m\’, \’f\’)|Yes     |m          |
|user\_image|varchar(50)|Yes     |NULL       |
|profile\_url|varchar(100)|Yes     |-          |
|country  |varchar(50)|Yes     |NULL       |
|city     |varchar(50)|Yes     |NULL       |
|interests|varchar(100)|Yes     |-          |
|date\_of\_birth|varchar(20)|Yes     |NULL       |


### User\_scrap ###

|**Field**|**Type**|**Null**|**Default**|
|:--------|:-------|:-------|:----------|
|scrap\_id|int(11) |Yes     |-          |
|sender\_id|int(11) |Yes     |-          |
|receiver\_id|int(11) |Yes     |-          |
|scrap\_content|text    |Yes     |-          |
