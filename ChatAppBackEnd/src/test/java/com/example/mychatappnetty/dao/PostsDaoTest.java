package com.example.mychatappnetty.dao;

import com.example.mychatappnetty.MyChatappNettyApplicationTests;
import com.example.mychatappnetty.entity.Posts;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class PostsDaoTest extends MyChatappNettyApplicationTests {

    @Autowired
    private PostsDao postsDao;

    @Test
    @Ignore
    public void insertPost() {

        Posts post = Posts.builder()
                            .id("idtest")
                            .postContent("test post")
                            .postCreateTime(new Date())
                            .userId("test userId").build();
        assertEquals(postsDao.insertPost(post), 1);
    }

    @Test
    @Ignore
    public void batchQueryPostsByPostId() {
        /*Posts another = Posts.builder()
                .id("id2test")
                .postContent("test post2")
                .postCreateTime(new Date())
                .userId("test userId2").build();

        postsDao.insertPost(another);*/

        List<String> postIdList = new ArrayList<>();
        postIdList.add("idtest");

        postIdList.add("id2test");

        List<Posts> postsList = postsDao.batchQueryPostsByPostId(postIdList);

        assertEquals(postsList.size(), 2);
    }

    @Test
    @Ignore
    public void deletePostById() {
        int affectRow = postsDao.deletePostById("id2test");
        assertEquals(affectRow,1 );

    }
}
