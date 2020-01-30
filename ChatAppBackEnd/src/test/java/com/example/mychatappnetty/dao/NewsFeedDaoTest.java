package com.example.mychatappnetty.dao;

import com.example.mychatappnetty.MyChatappNettyApplicationTests;
import com.example.mychatappnetty.entity.NewsFeed;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class NewsFeedDaoTest extends MyChatappNettyApplicationTests {


    @Autowired
    private NewsFeedDao newsFeedDao;


    @Test
    @Ignore
    public void batchInsertNewsFeed() {

        List<NewsFeed> newsFeedList = new ArrayList<>();

        newsFeedList.add(NewsFeed.builder().id("testId").postId("idtest").createTime(new Date()).userId("owner_id").build());
        newsFeedList.add(NewsFeed.builder().id("testId2").postId("idtest").createTime(new Date()).userId("owner_id_2").build());
        int affectedRow = newsFeedDao.batchInsertNewsFeed(newsFeedList);

        assertEquals(affectedRow,2);


    }

    @Test
    @Ignore
    public void queryNewsFeedByUserId() {
        List<NewsFeed> newsFeedList = newsFeedDao.queryNewsFeedByUserId("owner_id",0,2);

        assertEquals(newsFeedList.size(),1);


    }

    @Test
    @Ignore
    public void deleteNewsFeedByPostId() {
        int affectRow = newsFeedDao.deleteNewsFeedByPostId("idtest");
        assertEquals(affectRow,2);
    }
}
