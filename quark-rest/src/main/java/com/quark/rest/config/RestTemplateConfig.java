package com.quark.rest.config;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private static final int HTTP_TIME_OUT = 5000;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        HttpMessageConverters httpMessageConverters = new HttpMessageConverters();
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        RestTemplate restTemplate = new RestTemplate(httpMessageConverters.getConverters());
        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        requestFactory.setConnectTimeout(HTTP_TIME_OUT);
        requestFactory.setReadTimeout(HTTP_TIME_OUT);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
}
