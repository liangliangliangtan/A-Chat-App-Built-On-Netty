package com.example.mychatappnetty.dao;

import com.example.mychatappnetty.entity.NewsFeed;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NewsFeedDao {

    int batchInsertNewsFeed(List<NewsFeed> newsFeedsList);



    List<NewsFeed> queryNewsFeedByUserId(@Param("userId") String userId,
                                        @Param("pageNumber")Integer pageNumber,
                                        @Param("pageSize") Integer pageSize);


    int deleteNewsFeedByPostId(String postId);
}
