package com.example.mychatappnetty.entity.dto.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * FriendsRequestVO is the data to be displayed that one client received an friend
 * Request from another user.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendsRequestVO {
    private String  senderId;
    private String  senderUsername;
    private String senderNickname;
    private String senderProfileImg;
}
