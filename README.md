# otaibe.org - Simple Netflix Eureka client using Quarkus

Bare minimum Eureka client functionality in order to work with Netflix Eureka Server

## Some tips
* Use ``org.otaibe.eureka.client.service.EurekaClient.getNextServer`` to retrieve the next registered instance.
* Start native application sample command: ``./target/org-otaibe-eureka-client-00.00.01-SNAPSHOT-runner -Dquarkus.application.name=org-otaibe-eureka-client -Dquarkus.servlet.context-path=/otaibe-at-flight``
 
