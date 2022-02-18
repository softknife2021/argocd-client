package com.softknife.argocd.client.model.task;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sasha Matsaylo on 12/25/21
 * @project argocd-client
 */
@Setter
@Getter
public class BuildExecResult {

    private String state;
    private String executionMetaData;
    private List<String> errors = new ArrayList<>();
    private String buildId;
}
