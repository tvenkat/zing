This guide provides step by step configuration of Zing project, which is a Java based SNS site, conforming to opensocial specification.

### OS Platform ###
Zing project is tested on Windows XP platform for now. Though it should work on any platform which supports Java. We will test on other platforms soon. That is #1 in our roadmap.

### Prerequisites ###
  1. JDK 1.6
  1. Maven 2.0.9
    * Set M2\_HOME as environment variable.
    * Update path environment variable to append %M2\_HOME%/bin.
  1. Jetty 6.1.9. Jetty can be downloaded from http://dist.codehaus.org/jetty/jetty-6.1.9
  1. Shindig: Do a svn checkout for [revision 687705](https://code.google.com/p/zing/source/detail?r=687705) from http://svn.apache.org/repos/asf/incubator/shindig/trunk. Zing has not been tested on the newer versions yet.
  1. MySQL 5

### Path References ###
  1. ${ZingWeb} = path to ZingWeb project
  1. ${ZingWebConfig} = path to ZingWebConfig project
  1. ${Jetty\_Home} = path to Jetty server
  1. ${Shindig} = path to Shindig source

### Configuring Zing Database ###
  1. Download the database script zing\_db.sql from http://zing.googlecode.com/svn/trunk/zing/downloads/database/script/zing_db.sql, and execute the script on MySQL database.

### Configuring Zing ###
  1. Get the projects ZingWeb and ZingWebConfig. Do a svn checkout for http://zing.googlecode.com/svn/trunk/zing/ZingWeb/ and http://zing.googlecode.com/svn/trunk/zing/ZingWebConfig/
  1. Change the web.xml for ZingWeb project located at ${ZingWeb}/WebContent/WEB-INF and configure the "GadgetServerURL" parameter according to "ip-of-jetty-server" and "jetty-port".
  1. Execute the command **` mvn package`** to generate build for ZingWeb project. Maven build file "pom.xml" can be located within "ZingWeb" project. This will generate "target"  directory within the project, which contains "ZingWeb" directory.
  1. Browse to "lib" directory within "WEB-INF" of "ZingWeb" exploded war, then cut "el-api-6.0.18.jar" and paste it to ${Jetty\_Home}/lib/ext directory.
  1. Place the "ZingWeb" exploded war within ${Jetty\_Home}/webapps directory.
  1. Browse to ${ZingWebConfig}/configurations/persistence/persistence.xml. Configure this file to provide database URL and credentials.
  1. Set COMPOSITE\_CONFIG="${ZingWebConfig}/configurations/configurationControlFile.xml" as environment variable.

### Configuring Shindig ###
  1. Download ZingShindigUpdate files from : http://zing.googlecode.com/svn/trunk/zing/downloads/zingshindigupdate/update_01.rar.
  1. Extract this archive and make changes into SpiceDBLayer.java, to provide database credentials.
  1. Place these two files in "${Shindig}/java/social-api/src/main/java/org/apache/shindig/social/sample/spi" directory.
  1. Delete the "test" directories located at ${Shindig}/java/server/src & ${Shindig}/java/social-api/src.
  1. Download oauth.json from http://zing.googlecode.com/svn/trunk/zing/downloads/oauth/oauth.json for OAuth gadget. Configure this file to provide ip-of-jetty-server and jetty-port. Then place this file at ${Shindig}/config.
  1. Execute **` mvn package`** command for maven build file located at ${Shindig} directory. It generates "shindig-server-1-SNAPSHOT" directory at ${Shindig}/java/server/target directory.
  1. Copy the contents of generated "shindig-server-1-SNAPSHOT" to ${Jetty\_Home}/webapps/ROOT directory of Jetty server.

Finally, start the Jetty server and hit the URL: **`http://<ip-of-jetty-server>:<jetty-port>/ZingWeb/faces/pages/login.jspx`**

### Login Credentials ###

Below are the test login credentials:

  1. Username: **test**, password:**test**


User can also signup with Zing to create new user.