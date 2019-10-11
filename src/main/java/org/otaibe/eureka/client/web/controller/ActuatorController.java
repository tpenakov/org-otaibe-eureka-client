package org.otaibe.eureka.client.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.buffer.Buffer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.otaibe.eureka.client.config.JsonConfig;
import org.otaibe.eureka.client.utils.JsonUtils;
import org.otaibe.eureka.client.web.domain.GitInfo;
import org.otaibe.eureka.client.web.domain.Info;
import org.otaibe.eureka.client.web.domain.Metrics;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Path(StringUtils.EMPTY)
@Getter(AccessLevel.PROTECTED)
@Slf4j
public class ActuatorController {

    public static final String INFO = "/info";
    public static final String GIT_PROPERTIES = "META-INF/resources/git.properties";
    public static final String METRICS = "/metrics";

    @Inject
    JsonUtils jsonUtils;
    @Inject
    JsonConfig jsonConfig;
    @Inject
    Vertx vertx;

    private Info info = null;

    @GET
    @Path(INFO)
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<Response> info() {
        CompletableFuture<Response> result = new CompletableFuture<>();

        return Optional.ofNullable(info)
                .map(info1 -> {
                    result.complete(Response.ok().entity(info1).build());
                    return result;
                })
                .orElseGet(() -> {
                    info = new Info();
                    getVertx().fileSystem().rxReadFile(GIT_PROPERTIES)
                            .map(Buffer::toString)
                            .doOnSuccess(s -> log.info("git props: {}", s))
                            .map(s -> getJsonUtils().readValue(s, Map.class, getObjectMapper()))
                            .map(map -> map.orElse(new HashMap()))
                            .map(map -> {
                                GitInfo gitInfo = new GitInfo();
                                gitInfo.setBranch((String) map.get(GitInfo.GIT_BRANCH));
                                GitInfo.CommitInfo commit = new GitInfo.CommitInfo();
                                gitInfo.setCommit(commit);
                                commit.setId((String) map.get(GitInfo.GIT_COMMIT_ID));
                                commit.setTime((String) map.get(GitInfo.GIT_COMMIT_TIME));
                                return gitInfo;
                            })
                            .map(gitInfo -> {
                                info.setGit(gitInfo);
                                return info;
                            })
                            .doOnSuccess(info1 -> result.complete(Response.ok().entity(info1).build()))
                            .subscribe()
                    ;
                    return result;
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

    protected ObjectMapper getObjectMapper() {
        return getJsonConfig().getObjectMapper();
    }

}