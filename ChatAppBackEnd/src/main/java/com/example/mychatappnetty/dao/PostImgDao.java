package com.example.mychatappnetty.dao;

import com.example.mychatappnetty.entity.PostImg;

import java.util.List;

public interface PostImgDao {

    int batchInsertPostImg(List<PostImg> postImgList);

    List<PostImg> queryPostImgListByPostId(String postId);

    int deletePostImgByPostId(String postId);

}
