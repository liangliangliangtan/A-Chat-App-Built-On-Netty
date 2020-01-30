package com.example.mychatappnetty.entity.dto.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyFriendsVO {

    private String friendUserId;
    private String friendUsername;
    private String friendFaceImage;
    private String friendNickname;

    private String friendImageBig;


}
