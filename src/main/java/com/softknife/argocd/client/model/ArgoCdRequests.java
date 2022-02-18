package com.softknife.argocd.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.restbusters.rest.model.HttpRestRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author smatsaylo on 6/2/21
 * @project argocd-client
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Setter
@Getter
public class ArgoCdRequests {

    private HttpRestRequest getBuilds;
    private HttpRestRequest getBuildById;
    private HttpRestRequest getBuildStatisticByBuildId;
    private HttpRestRequest getBuildChangesByBuildId;
    private HttpRestRequest postBuild;
}
