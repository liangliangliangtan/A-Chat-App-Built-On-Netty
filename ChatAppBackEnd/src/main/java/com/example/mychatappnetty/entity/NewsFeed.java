package com.example.mychatappnetty.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*
* CREATE TABLE `tb_news_feed` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) NOT NULL COMMENT 'user who have the right to see the post',
  `post_id` varchar(64) NOT NULL COMMENT 'post image address',
  `create_time` datetime NOT NULL COMMENT 'post create time',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_news_feed` (`user_id`,`post_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

* */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsFeed {

    private String id;

    private String userId;

    private String postId;

    private Date createTime;

}
