package com.example.mychatappnetty.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posts {

    private String id;// primary Key for table my_friends
    private String postContent;
    private String userId;
    private Date postCreateTime;

    List<PostImg> postImgList;

}
