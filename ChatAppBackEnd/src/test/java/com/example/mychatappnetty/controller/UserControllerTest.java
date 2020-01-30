package com.example.mychatappnetty.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

public class UserControllerTest {


    private final static String url =  "http://ip:port/user/getfriendsrequestlist";// your ip address and port
    private static RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders headers = new HttpHeaders();

    //expect senderId : c7413800ae7247c4a10baa8bcae538a9
    @Test
    @Ignore
    public void getFriendsRequestList() {

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);


        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("userId", "82eda55d509a4c9798049c6b56393395");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        String responseEntityStr = restTemplate.
                postForObject(url, request, String.class);

        System.out.println(responseEntityStr);
    }
}
