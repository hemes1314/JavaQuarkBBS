package com.quark.rest;

import com.quark.rest.utils.UumsPasswordUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginTest {

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void loginTest() {
        Map<String, String> uumsLoginParams = new HashMap<>();
        uumsLoginParams.put("name", "wbb");
        uumsLoginParams.put("password", UumsPasswordUtil.encrypt("123456"));
        LinkedMultiValueMap multiMap = new LinkedMultiValueMap();
        multiMap.setAll(uumsLoginParams);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(multiMap, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity("http://uums.aps.com:31019/uums/loginx", httpEntity, String.class);
        String result = response.getBody();
        System.out.println(result);
    }
}
