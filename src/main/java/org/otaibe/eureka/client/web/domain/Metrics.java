package org.otaibe.eureka.client.web.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Metrics {
    @JsonProperty("mem")
    Long memory;
    @JsonProperty("mem.free")
    Long memoryFree;
    @JsonProperty("systemload.average")
    Long systemLoadAverage = 0L;
    @JsonProperty("httpsessions.active")
    Long httpSessionsActive = 0L;
}
