package com.softknife.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.spi.json.JacksonJsonNodeJsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author restbusters on 10/15/18
 * @project argocd-client
 */

public class ArgoClientResourceManager {

    private static ArgoClientResourceManager instance;
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Configuration configuration = Configuration.builder()
            .jsonProvider(new JacksonJsonNodeJsonProvider())
            .mappingProvider(new JacksonMappingProvider())
            .build();
    private ObjectMapper objectMapper = new ObjectMapper();
    private ObjectMapper yamlObjectMapper = new ObjectMapper(new YAMLFactory());


    private ArgoClientResourceManager() {
    }


    public static synchronized ArgoClientResourceManager getInstance() {
        if (instance == null) {
            instance = new ArgoClientResourceManager();
        }
        return instance;
    }

    public Configuration getConfiguration() {
        return configuration;
    }


    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public ObjectMapper getYamlObjectMapper() {
        return yamlObjectMapper;
    }
}



