package com.example.mychatappnetty.entity.dto.vo;

import com.example.mychatappnetty.entity.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostVO {


    private Integer count;
    private List<Posts>  posts;
    private List<MyFriendsVO> owners; // contains owner profile img and owner nickname;

}
