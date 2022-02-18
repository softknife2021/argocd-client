package com.softknife.argocd.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restbusters.rest.client.RestClientHelper;
import com.restbusters.rest.model.HttpRestRequest;
import com.restbusters.util.common.RBFileUtils;
import com.softknife.argocd.client.model.ArgoCdRequests;

import com.softknife.resource.ArgoClientResourceManager;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author smatsaylo
 * @project argocd-client
 */
public class ArgoCdClient {

    private String authToken;
    private String serverUrl;
    private OkHttpClient argoCdClient;
    private ObjectMapper objectMapper;
    private ArgoCdRequests argoCdRequests;
    private String jsonargoCdRequests;
    private final String requestsFile = "argocd-http-requests.json";

    public ArgoCdClient(String serverUrl, String authToken) throws Exception {
        if(StringUtils.isBlank(serverUrl)){
            throw new IllegalArgumentException("Server url must be provided");
        }
        if(StringUtils.isBlank(authToken)){
            throw new IllegalArgumentException("Auth token must be provided");
        }
        this.authToken = authToken;
        this.serverUrl = serverUrl.replaceAll("/$", "");
        this.argoCdClient = RestClientHelper.getInstance().buildBearerClient(authToken, getHeaders());
        this.objectMapper = ArgoClientResourceManager.getInstance().getObjectMapper();
        this.jsonargoCdRequests = RBFileUtils.getFileOnClassPathAsString(this.requestsFile);
        this.argoCdRequests = objectMapper.readValue(jsonargoCdRequests, ArgoCdRequests.class);
        this.initServerUrl(this.serverUrl);
        RestClientHelper.getInstance().addHeader(argoCdClient, "Accept", "application/json");
}

//    public Response getBuilds() {
//        try {
//            return RestClientHelper.getInstance().executeRequest(argoCdClient, this.argoCdRequests.getGetBuilds());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public Response getBuildById(String buildId){
//        Map<String, String> urlParams = new HashMap<>();
//        urlParams.put("id", buildId);
//        HttpRestRequest httpRestRequest = this.argoCdRequests.getGetBuildById();
//        httpRestRequest.setUrlParams(urlParams);
//        return executeCall(httpRestRequest);
//    }
//
//    public Response postBuild(String jsonRequestBody) {
//        HttpRestRequest httpRestRequest = this.argoCdRequests.getPostBuild();
//        httpRestRequest.setRequestBody(jsonRequestBody);
//        return executeCall(httpRestRequest);
//    }

    private Response executeCall(HttpRestRequest httpRestRequest){
        try {
            return RestClientHelper.getInstance().executeRequest(argoCdClient, httpRestRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> getHeaders(){
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        return headers;
    }

    public void initServerUrl(String serverUrl) {
        Map<String, HttpRestRequest> restRequestMap;
        restRequestMap =
                objectMapper.convertValue(
                        argoCdRequests, new TypeReference<Map<String, HttpRestRequest>>() {});
        for (Map.Entry<String, HttpRestRequest> entry : restRequestMap.entrySet()) {
            entry.getValue().setUrl(serverUrl + entry.getValue().getUri());
        }
        try {
            this.jsonargoCdRequests = this.objectMapper.writeValueAsString(restRequestMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            this.argoCdRequests = objectMapper.readValue(jsonargoCdRequests, ArgoCdRequests.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}