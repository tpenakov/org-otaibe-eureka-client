package org.otaibe.eureka.client.web.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GitInfo {

    String branch;
    CommitInfo commit;

    @Data
    @NoArgsConstructor
    public static class CommitInfo {
        String time;
        String id;
    }
}
