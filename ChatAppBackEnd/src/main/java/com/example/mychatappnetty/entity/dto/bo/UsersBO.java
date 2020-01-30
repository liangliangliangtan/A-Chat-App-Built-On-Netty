package com.example.mychatappnetty.entity.dto.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersBO {

    private String userId;
    private String faceData;
    private String momentImgData;
    private String nickname;

    private String userEmail;
    private String vcode;
    private String password;
    private String username;
}
