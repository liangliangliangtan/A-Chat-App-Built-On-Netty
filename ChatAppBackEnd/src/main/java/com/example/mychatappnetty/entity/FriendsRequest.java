package com.example.mychatappnetty.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendsRequest {


    private String id;

    private String sendUserId;

    private String acceptUserId;

    private Date requestDateTime;
}
