mailfilter
==========
Status 
[![Build Status](https://travis-ci.org/phamtuanchip/mailfilter.png)](https://travis-ci.org/phamtuanchip/mailfilter)

this is web application base on gatein portal to manage e-mail spamer

For binary source code you could get the latest from this link https://github.com/phamtuanchip/mailfilter/archive/v1.zip

Installation steps:

1)Pre-requirement  
Maven 3.x
Java SDK 7
JAVA_HOME = 'Java JDK 7 folder'
This web application compatible with browsers :
FireFox 29
Chrome  35
Internet Explorer 11

2) Unzip folder to anywhere you like e.g. C:\
3) Buld with maven 
3.1) Windows OS :  'mvn-clean-install.bat' 
3.2) Linux|Mac OS  'mvn-clean-install.sh' 
4) If build for all success:
4.2) Windows OS :
 go to 'C:\mailfilter\packaging\tomcat\tomcat7\target\tomcat\bin\' and call 'gatein.bat run'
4.2) Linux|Mac OS :
 go to '/mailfilter/packaging/tomcat/tomcat7/target/tomcat/bin/' and call 'gatein.sh run'
 
5)Troubleshooting 
5.1) Repository are not available:
in 'mailfilter' folder we have global settings.xml for maven, when we execute by script make sure internet available and this url http://repository.exoplatform.org/public/ should be accessible     
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
5.2) could not get tomcat clean server for deployment 
in the phase of build it will get clean tomcat from repository and deploy with bundle war jar define in 'package' module  
in this case you could execute these :
 Windows OS :  'mvn-pkg.bat' 
 Linux|Mac OS  'mvn-pkg.sh' 

5.3) if you get stuck by any other problem you could try to get fully tomcat bundle I shared in this link 
it's the fastest way to have demonstrate without any development steps 
after get tomcat bundle, go to 'tomcat\bin' and execute :
Windows : 'gatein.bat run'
Linux|Mac OS : 'gatein.sh run'
 
 
