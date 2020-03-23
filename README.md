# otaibe.org - Simple Netflix Eureka client using Quarkus

Bare minimum Eureka client functionality in order to work with Netflix Eureka Server

## Additional info
The Quarkus Based Eureka Client is rewritten as a library in [otaibe-commons-quarkus project](https://github.com/tpenakov/otaibe-commons-quarkus) .
In that way it is easier to plug it in into existing project. Some basic Actuators endpoints are also added there. 

In this project you can check the implementation of the `Quarkus Based Eureka Client` with some additional `Actuator Endpoints` like `/metrics`, `/info` and `/health`. The endpoints are related to the `quarkus.servlet.context-path` property.

## Some tips
* Use ``org.otaibe.eureka.client.service.EurekaClient.getNextServer`` to retrieve the next registered instance.
* You can use the [Prebuild Eureka Docker Image](https://hub.docker.com/repository/docker/triphon/otaibe-nginx-with-eureka-demo-eureka-server)
* Image is listen on 24455 and is with `enableSelfPreservation` set to `false` by default.
* You can start the image with:
```
docker run -i --rm -p 24455:24455 triphon/otaibe-nginx-with-eureka-demo-eureka-server
``` 

## Start and build
* Build native applicaiton with: `mvn clean package -Pnative -Dquarkus.native.container-build=true`
* Package the docker image: `docker build -f src/main/docker/Dockerfile.native -t triphon/org-otaibe-eureka-client .`
* Run the [Prebuild Eureka Docker Image](https://hub.docker.com/repository/docker/triphon/otaibe-nginx-with-eureka-demo-eureka-server)
```
docker run -i --rm -p 24455:24455 triphon/otaibe-nginx-with-eureka-demo-eureka-server
``` 
* Start the native application (does not forget to change the `__HOST_NAME_OR_IP__` with the correct value): 
```
docker run -i --rm -p 9888:9888 \
    --env "EUREKA_INSTANCE_HOSTNAME=__HOST_NAME_OR_IP__" \
    triphon/org-otaibe-eureka-client
```
* If you want to connect to other Eureka server the command should be (adjust `__EUREKA_HOST_PORT__` too):
```
docker run -i --rm -p 9888:9888 \
    --env "EUREKA_INSTANCE_HOSTNAME=__HOST_NAME_OR_IP__" \
    --env "EUREKA_CLIENT_SERVICEURL_DEFAULTZONE=http://__EUREKA_HOST_PORT__/eureka/" \
    triphon/org-otaibe-eureka-client
```
* Review the app in the [Eureka Frontend](http://localhost:24455/).

 
