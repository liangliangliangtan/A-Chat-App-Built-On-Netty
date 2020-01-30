package com.example.mychatappnetty.controller;

import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.Base64;


/**
 * Test the controller for uploading the images from the client to the server by calling the remote
 * REST API.
 * Test scenario:
 * Upload the userId = a51675a9840c400cb02f6846b8558c31 with the test img file
 */
public class UploadFileControllerTest{

    private final static String url =  "http://ip:port/upload/uploadprofileimg";// your ip address and address 

    private static RestTemplate restTemplate = new RestTemplate();
    private static HttpHeaders headers = new HttpHeaders();


    @Test
    @Ignore
    public void uploadProfileImgBase64() throws IOException {
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        File testFile = new File ("C:\\Users\\tan3\\Downloads\\testuploadclient.jpg");
        byte[] fileContent = FileUtils.readFileToByteArray(testFile);
        String base64Data = Base64.getEncoder().encodeToString(fileContent);


        JSONObject usersBOJsonObject = new JSONObject();
        try {
            usersBOJsonObject.put("userId", "a51675a9840c400cb02f6846b8558c31");
            usersBOJsonObject.put("faceData", base64Data);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        HttpEntity<String> request = new HttpEntity<String>(usersBOJsonObject.toString(), headers);
        String responseEntityStr = restTemplate.
                postForObject(url, request, String.class);

        System.out.println(responseEntityStr);

    }
}
