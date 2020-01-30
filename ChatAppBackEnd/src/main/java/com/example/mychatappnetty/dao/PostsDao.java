package com.example.mychatappnetty.dao;

import com.example.mychatappnetty.entity.Posts;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostsDao {

    int insertPost(Posts post);

    List<Posts> batchQueryPostsByPostId(@Param("postIdList") List<String> postIdList);

    int deletePostById(String postId);

}
