package com.example.mychatappnetty.dao;

import com.example.mychatappnetty.MyChatappNettyApplicationTests;
import com.example.mychatappnetty.entity.PostImg;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PostImgDaoTest  extends MyChatappNettyApplicationTests {

    @Autowired
    private PostImgDao postImgDao;

    @Test
    @Ignore
    public void batchInsertPostImg() {

        List<PostImg> postImgList = new ArrayList<>();
        postImgList.add(PostImg.builder().id("testId2").imgPosition(3).postId("idtest").postImg("img_url_3").build());
        postImgList.add(PostImg.builder().id("anotherId2").imgPosition(4).postId("idtest").postImg("img_url_4").build());
        int affectRow  = postImgDao.batchInsertPostImg(postImgList);

        assertEquals(affectRow, 2);

    }

    @Test
    @Ignore
    public void queryPostImgListByPostId() {

        List<PostImg> postImgList = postImgDao.queryPostImgListByPostId("idtest");
        assertEquals(postImgList.size(),4);

    }

    @Test
    @Ignore
    public void deletePostImgByPostId() {

        postImgDao.deletePostImgByPostId("idtest");
    }
}
