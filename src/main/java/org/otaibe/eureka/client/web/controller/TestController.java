package org.otaibe.eureka.client.web.controller;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
@Slf4j
public class TestController {

    public static final String HELLO = "hello";

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        log.debug(HELLO);
        return HELLO;
    }
}