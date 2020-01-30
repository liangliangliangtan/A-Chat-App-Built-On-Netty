package com.example.mychatappnetty.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostImg {

    private String id;

    private String postId;

    private String postImg;

    private String imgSize; // width x height

    private Integer imgPosition; //range：0-8

}
