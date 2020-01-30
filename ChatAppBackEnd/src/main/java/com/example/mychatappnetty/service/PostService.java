package com.example.mychatappnetty.service;

import com.example.mychatappnetty.entity.PostImg;
import com.example.mychatappnetty.entity.Posts;
import com.example.mychatappnetty.entity.dto.vo.PostVO;

import java.util.List;

public interface PostService {

    int insertPost(Posts posts);

    int batchInsertPostImg(List<PostImg> postImgList);

    void fanout(Posts posts);


    PostVO queryPostsByCondition(String userId, Integer pageNumber, Integer pageSize);
}
