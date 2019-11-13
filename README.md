# otaibe.org - Simple Netflix Eureka client using Quarkus

Bare minimum Eureka client functionality in order to work with Netflix Eureka Server

## Additional info
The Quarkus Based Eureka Client is rewritten as a library in [otaibe-commons-quarkus project](https://github.com/tpenakov/otaibe-commons-quarkus) .
In that way is easier to plug it in the existing project. Some basic Actuators endpoints are also added there. 

Nevertheless I will continue to support this project too!

## Some tips
* Use ``org.otaibe.eureka.client.service.EurekaClient.getNextServer`` to retrieve the next registered instance.
* Start native application sample command: ``./target/org-otaibe-eureka-client-00.00.01-SNAPSHOT-runner -Dquarkus.application.name=org-otaibe-eureka-client -Dquarkus.servlet.context-path=/otaibe-at-flight``
 
