DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `attendance` (
  `attendance_id` int NOT NULL AUTO_INCREMENT,
  `attendance_date` datetime DEFAULT NULL,
  `attend` varchar(1) DEFAULT 'N',
  `quiz_score` int DEFAULT '0',
  `chat_score` int DEFAULT '0',
  `user_id` int NOT NULL,
  `broadcast_id` int NOT NULL,
  `broadcast_track_id` int NOT NULL,
  PRIMARY KEY (`attendance_id`),
  KEY `fk_ATTENDENCE_USER1_idx` (`user_id`),
  KEY `fk_ATTENDENCE_BROADCAST1_idx` (`broadcast_id`),
  KEY `fk_ATTENDANCE_BROADCAST_TRACK1_idx` (`broadcast_track_id`),
  CONSTRAINT `fk_ATTENDANCE_BROADCAST1` FOREIGN KEY (`broadcast_id`) REFERENCES `broadcast` (`broadcast_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ATTENDANCE_BROADCAST_TRACK1` FOREIGN KEY (`broadcast_track_id`) REFERENCES `broadcast_track` (`broadcast_track_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_ATTENDANCE_USER1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=213 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `award`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `award` (
  `award_id` int NOT NULL AUTO_INCREMENT,
  `type` int NOT NULL,
  `score` int NOT NULL,
  `user_id` int NOT NULL,
  `date` datetime NOT NULL,
  `gifticon_yn` varchar(1) NOT NULL,
  `broadcast_id` int DEFAULT NULL,
  PRIMARY KEY (`award_id`),
  KEY `fk_AWARD_USER1_idx` (`user_id`),
  KEY `FKq3q4dhcx7mgsbwm14pyefjf31` (`broadcast_id`),
  CONSTRAINT `fk_AWARD_USER1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FKq3q4dhcx7mgsbwm14pyefjf31` FOREIGN KEY (`broadcast_id`) REFERENCES `broadcast` (`broadcast_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `broadcast`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `broadcast` (
  `broadcast_id` int NOT NULL AUTO_INCREMENT,
  `streaming_key` varchar(255) NOT NULL,
  `thumbnail_url` varchar(255) NOT NULL,
  `broadcast_date` datetime NOT NULL,
  `title` varchar(100) NOT NULL,
  `teacher` varchar(30) NOT NULL,
  `description` varchar(45) NOT NULL,
  `chat_count` int DEFAULT '0',
  `live_code` varchar(255) DEFAULT 'C',
  PRIMARY KEY (`broadcast_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `broadcast_replay`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `broadcast_replay` (
  `broadcast_replay_id` int NOT NULL AUTO_INCREMENT,
  `broadcast_id` int NOT NULL,
  `replay_url` varchar(255) NOT NULL,
  `open_yn` varchar(1) DEFAULT 'N',
  PRIMARY KEY (`broadcast_replay_id`),
  UNIQUE KEY `broadcast_id_UNIQUE` (`broadcast_id`),
  CONSTRAINT `broadcast_replay_ibfk_1` FOREIGN KEY (`broadcast_id`) REFERENCES `broadcast` (`broadcast_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `broadcast_replay_ordinal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `broadcast_replay_ordinal` (
  `broadcast_replay_ordinal_id` int NOT NULL AUTO_INCREMENT,
  `ordinal_no` int NOT NULL,
  `broadcast_replay_id` int NOT NULL,
  PRIMARY KEY (`broadcast_replay_ordinal_id`),
  KEY `fk_broadcast_replay_ordinal_broadcast_replay1_idx` (`broadcast_replay_id`),
  CONSTRAINT `fk_broadcast_replay_ordinal_broadcast_replay1` FOREIGN KEY (`broadcast_replay_id`) REFERENCES `broadcast_replay` (`broadcast_replay_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `broadcast_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `broadcast_track` (
  `broadcast_track_id` int NOT NULL AUTO_INCREMENT,
  `broadcast_id` int NOT NULL,
  `track_id` int NOT NULL,
  PRIMARY KEY (`broadcast_track_id`),
  KEY `fk_broadcast_track_broadcast1_idx` (`broadcast_id`),
  KEY `fk_broadcast_track_track1_idx` (`track_id`),
  CONSTRAINT `fk_broadcast_track_broadcast1` FOREIGN KEY (`broadcast_id`) REFERENCES `broadcast` (`broadcast_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_broadcast_track_track1` FOREIGN KEY (`track_id`) REFERENCES `track` (`track_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `gifticon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gifticon` (
  `gifticon_id` int NOT NULL AUTO_INCREMENT,
  `broadcast_id` int NOT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`gifticon_id`),
  KEY `fk_gifticon_broadcast1_idx` (`broadcast_id`),
  KEY `fk_gifticon_user1` (`user_id`),
  CONSTRAINT `fk_gifticon_broadcast1` FOREIGN KEY (`broadcast_id`) REFERENCES `broadcast` (`broadcast_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_gifticon_user1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `mattermost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mattermost` (
  `mattermost_id` int NOT NULL AUTO_INCREMENT,
  `webhook` varchar(255) NOT NULL,
  `name` varchar(100) NOT NULL,
  `path_name` varchar(50) NOT NULL,
  `track_setting_id` int NOT NULL,
  PRIMARY KEY (`mattermost_id`),
  KEY `fk_mattermost_track_setting1_idx` (`track_setting_id`),
  CONSTRAINT `fk_mattermost_track_setting1` FOREIGN KEY (`track_setting_id`) REFERENCES `track_setting` (`track_setting_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `mattermost_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mattermost_track` (
  `mattermost_track_id` int NOT NULL AUTO_INCREMENT,
  `mattermost_id` int NOT NULL,
  `track_id` int NOT NULL,
  PRIMARY KEY (`mattermost_track_id`),
  KEY `fk_track_mattermost_mattermost1_idx` (`mattermost_id`),
  KEY `fk_track_mattermost_track1_idx` (`track_id`),
  CONSTRAINT `fk_track_mattermost_mattermost1` FOREIGN KEY (`mattermost_id`) REFERENCES `mattermost` (`mattermost_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_track_mattermost_track1` FOREIGN KEY (`track_id`) REFERENCES `track` (`track_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `quiz`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz` (
  `quiz_id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(1) NOT NULL,
  `question` varchar(255) NOT NULL,
  `answer` varchar(100) NOT NULL,
  `use_yn` varchar(1) NOT NULL DEFAULT 'N',
  `broadcast_id` int NOT NULL,
  `answer_count` int DEFAULT '0',
  `attend_count` int DEFAULT '0',
  PRIMARY KEY (`quiz_id`),
  KEY `fk_QUIZ_BROADCAST1_idx` (`broadcast_id`),
  CONSTRAINT `fk_QUIZ_BROADCAST1` FOREIGN KEY (`broadcast_id`) REFERENCES `broadcast` (`broadcast_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `quiz_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_answer` (
  `quiz_answer_id` int NOT NULL AUTO_INCREMENT,
  `submit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int NOT NULL,
  `quiz_id` int NOT NULL,
  `answer_yn` varchar(1) NOT NULL,
  `answer` varchar(50) NOT NULL,
  PRIMARY KEY (`quiz_answer_id`),
  KEY `fk_QUIZ_ANSWER_USER1_idx` (`user_id`),
  KEY `fk_QUIZ_ANSWER_QUIZ1_idx` (`quiz_id`),
  CONSTRAINT `fk_QUIZ_ANSWER_QUIZ1` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_QUIZ_ANSWER_USER1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `quiz_select`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quiz_select` (
  `quiz_select_id` int NOT NULL AUTO_INCREMENT,
  `number` int NOT NULL,
  `view` varchar(50) NOT NULL,
  `quiz_id` int NOT NULL,
  PRIMARY KEY (`quiz_select_id`),
  KEY `fk_QUIZ_SELECT_QUIZ1_idx` (`quiz_id`),
  CONSTRAINT `fk_QUIZ_SELECT_QUIZ1` FOREIGN KEY (`quiz_id`) REFERENCES `quiz` (`quiz_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `schedule_id` int NOT NULL AUTO_INCREMENT,
  `photo_url` varchar(255) NOT NULL,
  `start_date` date NOT NULL,
  `expire_date` date NOT NULL,
  PRIMARY KEY (`schedule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `textbook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `textbook` (
  `textbook_id` int NOT NULL AUTO_INCREMENT,
  `textbook_url` varchar(255) NOT NULL,
  `broadcast_id` int NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`textbook_id`),
  KEY `fk_TEXTBOOK_BROADCAST1_idx` (`broadcast_id`),
  CONSTRAINT `fk_TEXTBOOK_BROADCAST1` FOREIGN KEY (`broadcast_id`) REFERENCES `broadcast` (`broadcast_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `track` (
  `track_id` int NOT NULL AUTO_INCREMENT,
  `track_name` varchar(50) NOT NULL,
  `track_subject_id` int NOT NULL,
  PRIMARY KEY (`track_id`),
  UNIQUE KEY `track_name_UNIQUE` (`track_name`),
  KEY `fk_track_track_subject1_idx` (`track_subject_id`),
  CONSTRAINT `fk_track_track_subject1` FOREIGN KEY (`track_subject_id`) REFERENCES `track_subject` (`track_subject_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `track`
--

LOCK TABLES `track` WRITE;
/*!40000 ALTER TABLE `track` DISABLE KEYS */;
INSERT INTO `track` VALUES (1,'',1),(2,'웹디자인',2),(3,'웹Iot',2),(4,'웹기술',2),(5,'인공지능',3),(6,'블록체인',3),(7,'빅데이터',3),(8,'IoT제어',3),(9,'자유주제',4),(10,'기업연계',4),(11,'오픈소스',4),(12,'코딩(비전공)',5),(13,'코딩(전공)',5),(14,'임베디드',5),(15,'모바일',5),(23,'웹 SNS',2);
/*!40000 ALTER TABLE `track` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `track_setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `track_setting` (
  `track_setting_id` int NOT NULL AUTO_INCREMENT,
  `ordinal_no` int NOT NULL,
  `semester` int NOT NULL,
  PRIMARY KEY (`track_setting_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `track_setting`
--

LOCK TABLES `track_setting` WRITE;
/*!40000 ALTER TABLE `track_setting` DISABLE KEYS */;
INSERT INTO `track_setting` VALUES (1,0,0),(2,5,2),(3,6,1);
/*!40000 ALTER TABLE `track_setting` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `track_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `track_subject` (
  `track_subject_id` int NOT NULL AUTO_INCREMENT,
  `subject_name` varchar(50) NOT NULL,
  `track_setting_id` int NOT NULL,
  `now_subject` varchar(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`track_subject_id`),
  KEY `fk_track_subject_track_setting1_idx` (`track_setting_id`),
  CONSTRAINT `fk_track_subject_track_setting1` FOREIGN KEY (`track_setting_id`) REFERENCES `track_setting` (`track_setting_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `track_subject`
--

LOCK TABLES `track_subject` WRITE;
/*!40000 ALTER TABLE `track_subject` DISABLE KEYS */;
INSERT INTO `track_subject` VALUES (1,'',1,'N'),(2,'공통 PJT',2,'N'),(3,'특화 PJT',2,'N'),(4,'자율 PJT',2,'Y'),(5,'코딩집중교육',3,'N');
/*!40000 ALTER TABLE `track_subject` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `ordinal_no` int NOT NULL,
  `region` varchar(10) NOT NULL,
  `class_no` int NOT NULL,
  `profile_url` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `status_code` varchar(1) NOT NULL DEFAULT 'Y',
  `track_id` int NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `fk_USER_TRACK_idx` (`track_id`),
  CONSTRAINT `fk_USER_TRACK` FOREIGN KEY (`track_id`) REFERENCES `track` (`track_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1234568 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `name`, `email`, `password`, `ordinal_no`, `region`, `class_no`, `profile_url`, `nickname`, `phone`, `status_code`, `track_id`) VALUES ('1111111', '관리자', 'admin@ssafy.com', '$2a$10$8NCQq5eOdPRjrleTbfG/iO4MAjCcOqMoI4b.5IWIGn/LkzHeIbMmW', '0', '서울', '0', 'https://mann-goofy.s3.ap-northeast-2.amazonaws.com/profiles/default.jpg', '관리자', '010-1111-1113', 'A', '1');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;