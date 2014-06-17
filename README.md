E-mail filter v2
==========
Status 
[![Build Status](https://travis-ci.org/phamtuanchip/mailfilter.png)](https://travis-ci.org/phamtuanchip/mailfilter)


This is web application base on GateIn portal to manage e-mail spammer
This version cover about implementation with mongodb
- Introduction 
	Because in very sort time I chose the GateIn portal to develop the requirement, 
	which is a rich ui component portal and which I am familiar with.
	for more information you could visit http://gatein.jboss.org/
	Main technologies using here are:
	HSQL for storing data and quick development
	eXo JCR is a java content repository to work in model layer
	implement JcrDataStorage for mail filter API 
	implement MailfilterWebservice to provide rest service for application
	implement MailfilterServiceTest, TestWebservice for unit test service API and web service 
	and various of GateIn base technologies,
	Application implemented with basic function as the requirement of test:
	- Auto add banned domain by xml configuration at initialize time 
	- Add new spammer form : with domain name validation 
	- List all spammer by status: default(all), pending (waiting for validation), blocked( already blocked), archive (archived)
	- Search box with ajax real time search and using rest service to response the search result

- Summary
  Because very sort of time I have no enough time to finish the 'bonus' part, it's really a pity because 
  I could finish it as my knowledge about Selenium SDK 
  Current search function is not really good user experiences, if have time I will implement more intelligence search action, 
  I mean when user type an email, if it is could not find an existed recode in database it will have suggestion with action to 
  open add spammer form so it should be very convince with user 
   
- Installation steps:

- 1)Pre-requirement  
	Maven 3.x add 'path' system environment variable to your 'maven\bin' folder
	Java SDK 7 set JAVA_HOME = 'Java JDK 7 folder'
	This web application compatible with browsers :
	FireFox 29
	Chrome  35
	Internet Explorer 11
	Mongodb server download from there http://fastdl.mongodb.org/win32/mongodb-win32-i386-2.6.1.zip
	Help to start http://dochub.mongodb.org/core/startingandstoppingmongo
	
- 2) Run test without development source code with mongodb

- 2.1) Download Mongodb server download from there http://fastdl.mongodb.org/win32/mongodb-win32-i386-2.6.1.zip 
	   Unzip and setting 'path' environment variable to 'mongodbserver\bin'
	   Start mongoDb 
       Go to 'C:\' create folder 'data\db' call 'mongod --dbpath ./data/db' to start mongodb server
- 2.2) I shared in this link https://drive.google.com/folderview?id=0Bw2eZ8CfkgNBRFh0NENsTHQtSXc&usp=sharing
	and select to download the 'tomcat-mongo' bundle. It's the fastest way to have demonstrate without any development steps 
	Start tomcat server: After get tomcat bundle, unzip and go to 'tomcat\bin' and execute :
	Windows : 'gatein.bat run'
	Linux|Mac OS : 'gatein.sh run'
	
- 3) Start using application 
	After run execution scrip in step 2 if you see this line in console :

	"Jun 15, 2014 3:33:07 PM org.apache.coyote.AbstractProtocol start
	INFO: Starting ProtocolHandler ["http-bio-8080"]
	Jun 15, 2014 3:33:07 PM org.apache.coyote.AbstractProtocol start
	INFO: Starting ProtocolHandler ["ajp-bio-8009"]
	Jun 15, 2014 3:33:07 PM org.apache.catalina.startup.Catalina start
	INFO: Server startup in 130483 ms "

	 mean that you already start for the web application
	Open browser with url htt://localhost:8080/portal wait several seconds 
	you will see the complete ui for web server and with the link mailFilter in navigation 
	or detail url will be http://localhost:8080/portal/classic/mailfilterPage
	you will go to application now you could test as well. There are some functions you could test:
	- 3.1) Default display all initialize data base 
	- 3.2) Search box, this using to check email to see does it in any domain persisted on database or not 
	- 3.3) Add new domain and email rule action: this will be shown add new form 
	- 3.4) In the list we can see the edit action to edit existed data
	- 3.5) In the list we could delete any row by clicking delete action and with confirmation message
		
	
	
- 4) Check source code and build yourself 
  For binary source code you could get the latest from this link https://github.com/phamtuanchip/mailfilter/archive/v1.zip
  Unzip folder to anywhere you like e.g. C:\
  Buld with maven 
- 4.1) Windows OS :  'mvn-pkg.bat' 
- 4.2) Linux|Mac OS  'mvn-pkg.sh' 
- 5) If build for all success could start the server:
- 5.2) Windows OS :
  go to 'C:\mailfilter\packaging\tomcat\tomcat7\target\tomcat\bin\' and call 'gatein.bat run'
- 5.2) Linux|Mac OS :
  go to '/mailfilter/packaging/tomcat/tomcat7/target/tomcat/bin/' and call 'gatein.sh run'
 
- 6)Troubleshooting 
- 6.1) Repository are not available:
	in 'mailfilter' folder we have global settings.xml for maven, when we execute by script make sure internet available
	 and this url http://repository.exoplatform.org/public/ should be accessible     
	if you are under proxy please add those config to settings.xml file and call build script again;
	  <proxies>
		<proxy>
		  <id>httpproxy</id>
		  <active>true</active>
		  <protocol>http</protocol>
		  <host>{your-proxy-host}</host>
		  <port>{your-proxy-port}</port>
		  
		</proxy>
		<proxy>
		  <id>httpsproxy</id>
		  <active>true</active>
		  <protocol>https</protocol>
		  <host>{your-proxy-host}</host>
		  <port>{your-proxy-port}</port>
		</proxy>
	  </proxies>
- 6.2) could not get tomcat clean server for deployment 
	in the phase of build it will get clean tomcat from repository and deploy with bundle war jar define in 'package' module  
	in this case you could execute these :
	 Windows OS :  'mvn-pkg.bat' 
	 Linux|Mac OS  'mvn-pkg.sh' 

- 6.3) if you get stuck by any other problem you could try to get fully tomcat bundle 
	I shared in this link https://drive.google.com/folderview?id=0Bw2eZ8CfkgNBRFh0NENsTHQtSXc&usp=sharing
	and select to download the 'tomcat' bundle. It's the fastest way to have demonstrate without any development steps 
	after get tomcat bundle, go to 'tomcat\bin' and execute :
	Windows : 'gatein.bat run'
	Linux|Mac OS : 'gatein.sh run'

- 6.4) Could not start server because some port already is use, 
	this server will take all the port define in tomcat\conf\server.xml 
	 8005 : shutdown application 
	 8080 : http port
	 8009 : ajp port
	 8443 : for redirection port
	 so please make sure those port does not use when you start server
	 

- 7) For any unclear, unavailable to start contact me:
	phamtuanchip@gmail.com
	mobile: +84 902318580
	skype : phamtuanchip
    
