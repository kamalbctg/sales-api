# sales-api
========================
A: System Requirements:
=======================
1. JDK 1.7 or later
2. Apache Maven
3. Apache Tomcat-7
4. MySql 5.6

===========================
B: Backend build instructions: 
===========================
1. Edit database.properties to set mysql user and password
2. Import sql script from SQL directory

3.
To deploy backend using maven
------------------------------
Edit "tomcat7-maven-plugin" configuration in pom.xml to provide tomcat url, username and password.

example of user configured in tomcat-users.xml as follows
<role rolename="manager"/>
<role rolename="admin"/>
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<user username="admin" password="admin" roles="admin,manager,manager-gui,manager-script, manager-jmx"/>

run following command 
    mvn clean tomcat7:deploy 
