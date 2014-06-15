mailfilter
==========
Status 
[![Build Status](https://travis-ci.org/phamtuanchip/mailfilter.png)](https://travis-ci.org/phamtuanchip/mailfilter)


This is web application base on GateIn portal to manage e-mail spammer
PreFace
Because in very sort time I chose the GateIn portal to develop the requirement because it a rich ui component portal, wich I am familer with.
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

Installation steps:

- 1)Pre-requirement  
Maven 3.x
Java SDK 7
JAVA_HOME = 'Java JDK 7 folder'
This web application compatible with browsers :
FireFox 29
Chrome  35
Internet Explorer 11
For binary source code you could get the latest from this link https://github.com/phamtuanchip/mailfilter/archive/v1.zip
- 2) Unzip folder to anywhere you like e.g. C:\
- 3) Buld with maven 
- 3.1) Windows OS :  'mvn-clean-install.bat' 
- 3.2) Linux|Mac OS  'mvn-clean-install.sh' 
- 4) If build for all success:
- 4.2) Windows OS :
 go to 'C:\mailfilter\packaging\tomcat\tomcat7\target\tomcat\bin\' and call 'gatein.bat run'
- 4.2) Linux|Mac OS :
 go to '/mailfilter/packaging/tomcat/tomcat7/target/tomcat/bin/' and call 'gatein.sh run'
 
- 5)Troubleshooting 
- 5.1) Repository are not available:
in 'mailfilter' folder we have global settings.xml for maven, when we execute by script make sure internet available and this url https://drive.google.com/folderview?id=0Bw2eZ8CfkgNBRFh0NENsTHQtSXc&usp=drive_web should be accessible     
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
- 5.2) could not get tomcat clean server for deployment 
in the phase of build it will get clean tomcat from repository and deploy with bundle war jar define in 'package' module  
in this case you could execute these :
 Windows OS :  'mvn-pkg.bat' 
 Linux|Mac OS  'mvn-pkg.sh' 

- 5.3) if you get stuck by any other problem you could try to get fully tomcat bundle I shared in this link https://drive.google.com/folderview?id=0Bw2eZ8CfkgNBRFh0NENsTHQtSXc&usp=sharing
and select to download the 'tomcat' bundle. It's the fastest way to have demonstrate without any development steps 
after get tomcat bundle, go to 'tomcat\bin' and execute :
Windows : 'gatein.bat run'
Linux|Mac OS : 'gatein.sh run'

- 5.4) Could not start server because some port already is use, this server will take all the port define in tomcat\conf\server.xml 
 8005 : shutdown application 
 8080 : http port
 8009 : ajp port
 8443 : for redirection port
 so please make sure those port does not use when you start server
 
- 6) Start using application 
After run execution scrip in step 5.3 if you see this line in console :

"Jun 15, 2014 3:33:07 PM org.apache.coyote.AbstractProtocol start
INFO: Starting ProtocolHandler ["http-bio-8080"]
Jun 15, 2014 3:33:07 PM org.apache.coyote.AbstractProtocol start
INFO: Starting ProtocolHandler ["ajp-bio-8009"]
Jun 15, 2014 3:33:07 PM org.apache.catalina.startup.Catalina start
INFO: Server startup in 130483 ms "

 mean that you already start for the web application
Open browser with url htt://localhost:8080/portal wait several seconds you will see the complete ui for web server and with the link mailFilter in navigation or detail url will be http://localhost:8080/portal/classic/mailfilterPage
you will go to application now you could test as well. There are some functions you could test:
- 6.1) Default display all initialize data base 
- 6.2) Search box, this using to check email to see does it in any domain persisted on database or not 
- 6.3) Add new domain and email rule action: this will be shown add new form 
- 6.4) In the list we can see the edit action to edit existed data
- 6.5) In the list we could delete any row by clicking delete action and with confirmation message

- 7) For any unclear, unavailable to start contact me:
phamtuanchip@gmail.com
mobile: +84 902318580
skype : phamtuanchip
    
