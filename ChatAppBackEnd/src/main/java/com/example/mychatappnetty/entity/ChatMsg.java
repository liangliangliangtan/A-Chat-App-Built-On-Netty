package com.example.mychatappnetty.entity;

import lombok.*;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMsg {

    private String chatMsgId; // primary Key


    private String sendUserId;


    private String acceptUserId;


    private String chatMsg;


    private Integer signFlag; // 0: unread, 1: read


    private Date createTime;


}
