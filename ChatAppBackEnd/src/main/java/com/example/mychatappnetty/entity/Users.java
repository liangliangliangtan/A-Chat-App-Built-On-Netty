package com.example.mychatappnetty.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {

    private String id;

    private String username;


    private String password;


    private String faceImage; // image shown in chat room


    private String faceImageBig; // profile img

    private String nickname;


    private String qrcode;

    private String clientId; // user mobile id.

    private String userEmail;

    private Integer status;

    private String momentBackgroundImg;
}
