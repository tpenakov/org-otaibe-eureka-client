package org.otaibe.eureka.client.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.otaibe.eureka.client.config.JsonConfig;
import org.otaibe.eureka.client.utils.JsonUtils;
import org.otaibe.eureka.client.web.domain.GitInfo;
import org.otaibe.eureka.client.web.domain.Info;
import org.otaibe.eureka.client.web.domain.Metrics;
import reactor.adapter.rxjava.RxJava2Adapter;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Path(StringUtils.EMPTY)
@Getter(AccessLevel.PROTECTED)
@Slf4j
public class ActuatorController {

    public static final String INFO = "/info";
    public static final String GIT_PROPERTIES = "git.properties";
    public static final String METRICS = "/metrics";
    public static final String LOGFILE = "/logfile";

    @Inject
    JsonUtils jsonUtils;
    @Inject
    JsonConfig jsonConfig;
    @Inject
    Vertx vertx;

    @ConfigProperty(name = "quarkus.log.file.path")
    String logFilePath;

    private Info info = null;

    @GET
    @Path(INFO)
    @Produces(MediaType.APPLICATION_JSON)
    public Info info() {
        return Optional.ofNullable(info)
                .orElseGet(() -> {
                    info = new Info();
                    try {
                        PropertiesConfiguration configuration = new PropertiesConfiguration(GIT_PROPERTIES);


                        GitInfo git = new GitInfo();
                        git.setBranch(configuration.getString("git.branch", StringUtils.EMPTY));

                        GitInfo.CommitInfo commit = new GitInfo.CommitInfo();
                        commit.setTime(configuration.getString("git.commit.time", StringUtils.EMPTY));
                        commit.setId(configuration.getString("git.commit.id", StringUtils.EMPTY));

                        git.setCommit(commit);
                        info.setGit(git);
                    } catch (Exception e) {
                        log.error("unable to read properties", e);
                    }
                    return info;
                });
    }

    @GET
    @Path(METRICS)
    @Produces(MediaType.APPLICATION_JSON)
    public String metrics() {
        Metrics result = new Metrics();
        Runtime runtime = Runtime.getRuntime();
        result.setMemory(runtime.totalMemory());
        result.setMemoryFree(runtime.freeMemory());

        return jsonUtils.toStringLazy(result, getObjectMapper()).toString();
    }

    @GET
    @Path(LOGFILE)
    @Produces(MediaType.TEXT_PLAIN)
    public CompletionStage<Response> logfile() {
        CompletableFuture<Response> result = new CompletableFuture<>();

        RxJava2Adapter.singleToMono(
                getVertx().fileSystem().rxReadFile(getLogFilePath())
        )
                .map(Buffer::getBytes)
                .doOnNext(bytes -> result.complete(Response.ok(bytes).build()))
                .doOnError(throwable -> result.complete(
                        Response
                                .serverError()
                                .status(Response.Status.INTERNAL_SERVER_ERROR)
                                .entity(throwable.getMessage())
                                .build()
                ))
                .subscribe()
        ;
        return result;
    }

    protected ObjectMapper getObjectMapper() {
        return jsonConfig.getObjectMapper();
    }

}