-- MySQL dump 10.13  Distrib 8.0.14, for Win64 (x86_64)
--
-- Host: localhost    Database: chat_app_db
-- ------------------------------------------------------
-- Server version	8.0.14

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_chat_msg`
--

DROP TABLE IF EXISTS `tb_chat_msg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_chat_msg` (
  `chat_msg_id` varchar(64) NOT NULL,
  `send_user_id` varchar(64) NOT NULL,
  `accept_user_id` varchar(64) NOT NULL,
  `chat_msg` varchar(1024) NOT NULL,
  `sign_flag` int(1) NOT NULL COMMENT 'Sign for whether message is read or not \r\n1´╝Üþ¡¥µöÂ\r\n0´╝Üµ£¬þ¡¥µöÂ\r\n',
  `create_time` datetime NOT NULL COMMENT 'Message Request sent time',
  PRIMARY KEY (`chat_msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_chat_msg`
--

LOCK TABLES `tb_chat_msg` WRITE;
/*!40000 ALTER TABLE `tb_chat_msg` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_chat_msg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_friends_request`
--

DROP TABLE IF EXISTS `tb_friends_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_friends_request` (
  `id` varchar(64) NOT NULL,
  `send_user_id` varchar(64) NOT NULL,
  `accept_user_id` varchar(64) NOT NULL,
  `request_date_time` datetime NOT NULL COMMENT 'Friend Request sent time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_friends_request`
--

LOCK TABLES `tb_friends_request` WRITE;
/*!40000 ALTER TABLE `tb_friends_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_friends_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_my_friends`
--

DROP TABLE IF EXISTS `tb_my_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_my_friends` (
  `id` varchar(64) NOT NULL,
  `my_user_id` varchar(64) NOT NULL COMMENT 'User id',
  `my_friend_user_id` varchar(64) NOT NULL COMMENT 'User friend id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `my_user_id` (`my_user_id`,`my_friend_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_my_friends`
--

LOCK TABLES `tb_my_friends` WRITE;
/*!40000 ALTER TABLE `tb_my_friends` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_my_friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_users`
--

DROP TABLE IF EXISTS `tb_users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `tb_users` (
  `user_id` varchar(64) NOT NULL,
  `username` varchar(20) NOT NULL COMMENT 'UserName ',
  `password` varchar(64) NOT NULL COMMENT 'Password ',
  `user_email` varchar(64) NOT NULL COMMENT 'User Email, Gmail only ',
  `face_image` varchar(255) NOT NULL COMMENT 'Smaller img address for chatting ',
  `face_image_big` varchar(255) NOT NULL COMMENT 'detail and account larger img address',
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT 'Nick Name',
  `qrcode` varchar(255) NOT NULL COMMENT 'When new user registered ,QR code will be generated and sent to fastdfs',
  `client_id` varchar(64) DEFAULT NULL COMMENT 'CLINET ID FOR EACH MOBILE DEVICE',
  `status` int(1) NOT NULL COMMENT '1 is active, 0 is inactive',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_id` (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `user_email` (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_users`
--

LOCK TABLES `tb_users` WRITE;
/*!40000 ALTER TABLE `tb_users` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-08-27 10:01:29


CREATE TABLE `tb_my_friends` (
  `id` varchar(64) NOT NULL,
  `my_user_id` varchar(64) NOT NULL COMMENT 'User id',
  `my_friend_user_id` varchar(64) NOT NULL COMMENT 'User friend id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `my_user_id` (`my_user_id`,`my_friend_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;



-- ----------------------------
-- Validation Email by sending eamil
-- ----------------------------
CREATE TABLE `tb_validate` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(64) NOT NULL,
  `user_email` varchar(40) NOT NULL,
  `reset_token` varchar(40) NOT NULL,
  `type` varchar(20) NOT NULL COMMENT 'Which type of validation',
  `create_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;