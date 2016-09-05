CREATE DATABASE  IF NOT EXISTS `olympics_db` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `olympics_db`;
-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: olympics_db
-- ------------------------------------------------------
-- Server version	5.7.13-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `competition`
--

DROP TABLE IF EXISTS `competition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_sport` int(11) NOT NULL,
  `id_sport_discipline` int(11) NOT NULL,
  `sex` char(1) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `finish_date` date DEFAULT NULL,
  `location` varchar(128) DEFAULT NULL,
  `id_delegate` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_sport` (`id_sport`),
  KEY `id_sport_discipline` (`id_sport_discipline`),
  KEY `id_delegate` (`id_delegate`),
  CONSTRAINT `competition_ibfk_1` FOREIGN KEY (`id_sport`) REFERENCES `sport` (`idsport`),
  CONSTRAINT `competition_ibfk_2` FOREIGN KEY (`id_sport_discipline`) REFERENCES `sport_discipline` (`id`),
  CONSTRAINT `competition_ibfk_3` FOREIGN KEY (`id_delegate`) REFERENCES `user` (`iduser`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition`
--

LOCK TABLES `competition` WRITE;
/*!40000 ALTER TABLE `competition` DISABLE KEYS */;
INSERT INTO `competition` VALUES (13,1,1,'m','2016-08-09','2016-08-18','Arena',3),(15,4,18,'m','2016-08-01','2016-08-10','Staza',3),(16,4,7,'m','2016-09-15','2016-09-15','Arena',3),(17,8,27,'m','2016-09-03','2016-09-12','Pucacki Stadion',3),(18,9,31,'z','2016-09-08','2016-09-21','Teniski teren',3),(19,7,24,'m','2016-09-06','2016-09-15','Teniski teren',20);
/*!40000 ALTER TABLE `competition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competition_sportsman`
--

DROP TABLE IF EXISTS `competition_sportsman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competition_sportsman` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_competition` int(11) NOT NULL,
  `id_sportsman` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_competition` (`id_competition`),
  KEY `id_sportsman` (`id_sportsman`),
  CONSTRAINT `competition_sportsman_ibfk_1` FOREIGN KEY (`id_competition`) REFERENCES `competition` (`id`),
  CONSTRAINT `competition_sportsman_ibfk_2` FOREIGN KEY (`id_sportsman`) REFERENCES `sportsman` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition_sportsman`
--

LOCK TABLES `competition_sportsman` WRITE;
/*!40000 ALTER TABLE `competition_sportsman` DISABLE KEYS */;
INSERT INTO `competition_sportsman` VALUES (3,15,139),(4,15,140),(5,15,141),(6,15,142),(7,15,143),(8,15,144),(9,15,145),(10,15,146),(11,15,147),(12,15,148),(13,15,149),(14,17,150),(15,17,151),(16,17,152),(17,19,161),(18,19,163),(19,19,165),(20,19,166);
/*!40000 ALTER TABLE `competition_sportsman` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competition_team`
--

DROP TABLE IF EXISTS `competition_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `competition_team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_competition` int(11) NOT NULL,
  `id_team` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_competition` (`id_competition`),
  KEY `id_team` (`id_team`),
  CONSTRAINT `competition_team_ibfk_1` FOREIGN KEY (`id_competition`) REFERENCES `competition` (`id`),
  CONSTRAINT `competition_team_ibfk_2` FOREIGN KEY (`id_team`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competition_team`
--

LOCK TABLES `competition_team` WRITE;
/*!40000 ALTER TABLE `competition_team` DISABLE KEYS */;
INSERT INTO `competition_team` VALUES (24,13,1),(25,13,17),(26,13,18),(27,13,10),(28,13,12),(29,13,13),(30,13,15),(31,13,16),(40,18,19),(41,18,20),(42,18,21),(43,18,22);
/*!40000 ALTER TABLE `competition_team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `flag_ref` varchar(50) DEFAULT NULL,
  `bronze` int(11) DEFAULT '0',
  `silver` int(11) DEFAULT '0',
  `gold` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'Serbia','flag_serbia.jpg',4,0,5),(2,'Austria','flag_austria.png',0,0,0),(3,'USA','flag_usa.png',0,1,0),(4,'Spain','flag_spain.png',0,0,0),(5,'Australia','flag_australia.png',0,3,0),(6,'Croatia','flag_croatia.png',0,1,1),(7,'France','flag_france.png',0,0,0),(8,'Lithuania','flag_lithuania.png',1,0,0),(9,'Argentina','flag_argentina.png',2,1,2),(10,'Kenya','flag_kenya.png',0,3,0),(11,'Ethiopia','flag_ethiopia.png',0,0,0),(12,'Tanzania','flag_tanzania.png',0,0,0),(13,'Eritrea','flag_eritrea.png',1,1,0),(14,'Switzerland','flag_switzerland.png',0,0,0),(15,'Uganda','flag_uganda.png',0,0,1),(16,'Great Britain','flag_great_britain.png',0,0,0),(17,'Netherlands','flag_netherlands.png',0,0,0),(18,'Canada','flag_canada.png',2,0,1),(19,'Jamaica','flag_jamaica.png',0,0,0);
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `elimination_round`
--

DROP TABLE IF EXISTS `elimination_round`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `elimination_round` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_competition` int(11) DEFAULT NULL,
  `id_quarter_game` int(11) DEFAULT NULL,
  `id_semi_game` int(11) DEFAULT NULL,
  `id_third_game` int(11) DEFAULT NULL,
  `id_finals_game` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_competition` (`id_competition`),
  KEY `id_quarter_game` (`id_quarter_game`),
  KEY `id_semi_game` (`id_semi_game`),
  KEY `id_third_game` (`id_third_game`),
  KEY `id_finals_game` (`id_finals_game`),
  CONSTRAINT `elimination_round_ibfk_1` FOREIGN KEY (`id_competition`) REFERENCES `competition` (`id`),
  CONSTRAINT `elimination_round_ibfk_2` FOREIGN KEY (`id_quarter_game`) REFERENCES `game` (`id`),
  CONSTRAINT `elimination_round_ibfk_3` FOREIGN KEY (`id_semi_game`) REFERENCES `game` (`id`),
  CONSTRAINT `elimination_round_ibfk_4` FOREIGN KEY (`id_third_game`) REFERENCES `game` (`id`),
  CONSTRAINT `elimination_round_ibfk_5` FOREIGN KEY (`id_finals_game`) REFERENCES `game` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `elimination_round`
--

LOCK TABLES `elimination_round` WRITE;
/*!40000 ALTER TABLE `elimination_round` DISABLE KEYS */;
INSERT INTO `elimination_round` VALUES (5,13,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `elimination_round` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_team1` int(11) DEFAULT NULL,
  `id_team2` int(11) DEFAULT NULL,
  `id_qualifying_group` int(11) DEFAULT NULL,
  `date_and_time` datetime DEFAULT NULL,
  `result` varchar(32) DEFAULT NULL,
  `id_sportsman1` int(11) DEFAULT NULL,
  `id_sportsman2` int(11) DEFAULT NULL,
  `location` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_team1` (`id_team1`),
  KEY `id_team2` (`id_team2`),
  KEY `id_qualifying_group` (`id_qualifying_group`),
  KEY `id_sportsman1` (`id_sportsman1`),
  KEY `id_sportsman2` (`id_sportsman2`),
  CONSTRAINT `game_ibfk_1` FOREIGN KEY (`id_team1`) REFERENCES `team` (`id`),
  CONSTRAINT `game_ibfk_2` FOREIGN KEY (`id_team2`) REFERENCES `team` (`id`),
  CONSTRAINT `game_ibfk_3` FOREIGN KEY (`id_qualifying_group`) REFERENCES `qualifying_group` (`id`),
  CONSTRAINT `game_ibfk_4` FOREIGN KEY (`id_sportsman1`) REFERENCES `sportsman` (`id`),
  CONSTRAINT `game_ibfk_5` FOREIGN KEY (`id_sportsman2`) REFERENCES `sportsman` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=412 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
INSERT INTO `game` VALUES (389,NULL,NULL,113,'2016-09-07 00:00:00','0:2',166,165,NULL),(390,NULL,NULL,113,'2016-09-20 00:00:00','0:2',163,166,NULL),(391,NULL,NULL,113,'2016-09-14 00:00:00','2:1',165,161,NULL),(392,18,12,114,'2016-09-15 00:00:00','0:2',NULL,NULL,NULL),(393,13,16,114,'2016-09-07 00:00:00','50:70',NULL,NULL,NULL),(394,18,13,114,'2016-09-22 04:00:00','0:50',NULL,NULL,NULL),(395,12,16,114,'2016-09-01 00:00:00','2:1',NULL,NULL,NULL),(396,18,16,114,'2016-09-07 00:00:00','0:12',NULL,NULL,NULL),(397,12,13,114,'2016-09-13 00:00:00','69:78',NULL,NULL,NULL),(398,1,10,115,'2016-09-23 00:00:00','50:40',NULL,NULL,NULL),(399,17,15,115,'2016-09-07 00:00:00','50:70',NULL,NULL,NULL),(400,1,15,115,'2016-09-13 00:00:00','50:22',NULL,NULL,NULL),(401,17,10,115,'2016-09-23 00:00:00','44:47',NULL,NULL,NULL),(402,1,17,115,'2016-09-09 00:00:00','100:0',NULL,NULL,NULL),(403,10,15,115,'2016-09-12 00:00:00','50:55',NULL,NULL,NULL),(404,18,1,116,'2016-09-02 00:00:00','40:50',NULL,NULL,NULL),(405,17,16,116,'2016-09-02 00:00:00','50:70',NULL,NULL,NULL),(406,17,18,116,'2016-09-09 00:00:00','0:29',NULL,NULL,NULL),(407,1,16,116,'2016-09-08 00:00:00','50:21',NULL,NULL,NULL),(408,12,17,116,'2016-09-09 00:00:00','0:100',NULL,NULL,NULL),(409,15,18,116,'2016-09-08 00:00:00','50:55',NULL,NULL,NULL),(410,1,13,116,'2016-09-01 00:00:00','50:2',NULL,NULL,NULL),(411,16,10,116,'2016-09-07 00:00:00','59:25',NULL,NULL,NULL);
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qualifying_group`
--

DROP TABLE IF EXISTS `qualifying_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qualifying_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_competition` int(11) DEFAULT NULL,
  `symbol` char(1) DEFAULT NULL,
  `is_elimination` tinyint(1) DEFAULT NULL,
  `is_finished` tinyint(1) DEFAULT NULL,
  `date_and_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_competition` (`id_competition`),
  CONSTRAINT `qualifying_group_ibfk_1` FOREIGN KEY (`id_competition`) REFERENCES `competition` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qualifying_group`
--

LOCK TABLES `qualifying_group` WRITE;
/*!40000 ALTER TABLE `qualifying_group` DISABLE KEYS */;
INSERT INTO `qualifying_group` VALUES (113,19,'F',1,0,NULL),(114,13,'A',0,1,NULL),(115,13,'B',0,1,NULL),(116,13,'F',1,0,NULL),(117,15,'0',0,0,'2016-09-08 00:00:00'),(118,15,'1',0,0,'2016-09-09 00:00:00');
/*!40000 ALTER TABLE `qualifying_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `record`
--

DROP TABLE IF EXISTS `record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `year` int(11) DEFAULT NULL,
  `location` varchar(64) DEFAULT NULL,
  `id_sport_discipline` int(11) DEFAULT NULL,
  `first_name` varchar(32) DEFAULT NULL,
  `last_name` varchar(32) DEFAULT NULL,
  `id_country` int(11) DEFAULT NULL,
  `result` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_sport_discipline` (`id_sport_discipline`),
  KEY `id_country` (`id_country`),
  CONSTRAINT `record_ibfk_1` FOREIGN KEY (`id_sport_discipline`) REFERENCES `sport_discipline` (`id`),
  CONSTRAINT `record_ibfk_2` FOREIGN KEY (`id_country`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `record`
--

LOCK TABLES `record` WRITE;
/*!40000 ALTER TABLE `record` DISABLE KEYS */;
INSERT INTO `record` VALUES (1,2012,'London',4,'Usain','Bolt',19,'9.63'),(2,2008,'Beijing',5,'Usain','Bolt',19,'19.30'),(3,2012,'London',7,'David Lekuta','Rudisha',10,'1:40.91');
/*!40000 ALTER TABLE `record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rio_sport`
--

DROP TABLE IF EXISTS `rio_sport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rio_sport` (
  `idsport` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `idsport` (`idsport`),
  CONSTRAINT `rio_sport_ibfk_1` FOREIGN KEY (`idsport`) REFERENCES `sport` (`idsport`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rio_sport`
--

LOCK TABLES `rio_sport` WRITE;
/*!40000 ALTER TABLE `rio_sport` DISABLE KEYS */;
INSERT INTO `rio_sport` VALUES (1,5),(2,1),(4,2),(5,4),(7,7),(8,6),(9,3);
/*!40000 ALTER TABLE `rio_sport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rio_sport_discipline`
--

DROP TABLE IF EXISTS `rio_sport_discipline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rio_sport_discipline` (
  `id_discipline` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `id_discipline` (`id_discipline`),
  CONSTRAINT `rio_sport_discipline_ibfk_1` FOREIGN KEY (`id_discipline`) REFERENCES `sport_discipline` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rio_sport_discipline`
--

LOCK TABLES `rio_sport_discipline` WRITE;
/*!40000 ALTER TABLE `rio_sport_discipline` DISABLE KEYS */;
INSERT INTO `rio_sport_discipline` VALUES (1,8),(6,4),(7,6),(8,3),(10,7),(17,12),(18,9),(21,5),(24,11),(27,10),(30,1),(31,2);
/*!40000 ALTER TABLE `rio_sport_discipline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `round`
--

DROP TABLE IF EXISTS `round`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `round` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_qualifying_group` int(11) DEFAULT NULL,
  `is_finished` tinyint(1) DEFAULT NULL,
  `num` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_qualifying_group` (`id_qualifying_group`),
  CONSTRAINT `round_ibfk_1` FOREIGN KEY (`id_qualifying_group`) REFERENCES `qualifying_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `round`
--

LOCK TABLES `round` WRITE;
/*!40000 ALTER TABLE `round` DISABLE KEYS */;
INSERT INTO `round` VALUES (230,113,1,0),(231,113,1,1),(232,114,1,0),(233,114,1,1),(234,114,1,2),(235,115,1,0),(236,115,1,1),(237,115,1,2),(238,116,1,0),(239,116,1,1),(240,116,1,2);
/*!40000 ALTER TABLE `round` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `round_game`
--

DROP TABLE IF EXISTS `round_game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `round_game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_round` int(11) DEFAULT NULL,
  `id_game` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_round` (`id_round`),
  KEY `id_game` (`id_game`),
  CONSTRAINT `round_game_ibfk_1` FOREIGN KEY (`id_round`) REFERENCES `round` (`id`),
  CONSTRAINT `round_game_ibfk_2` FOREIGN KEY (`id_game`) REFERENCES `game` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=412 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `round_game`
--

LOCK TABLES `round_game` WRITE;
/*!40000 ALTER TABLE `round_game` DISABLE KEYS */;
INSERT INTO `round_game` VALUES (389,230,389),(390,231,390),(391,231,391),(392,232,392),(393,232,393),(394,233,394),(395,233,395),(396,234,396),(397,234,397),(398,235,398),(399,235,399),(400,236,400),(401,236,401),(402,237,402),(403,237,403),(404,238,404),(405,238,405),(406,239,406),(407,239,407),(408,240,408),(409,240,409),(410,240,410),(411,240,411);
/*!40000 ALTER TABLE `round_game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sport`
--

DROP TABLE IF EXISTS `sport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sport` (
  `idsport` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`idsport`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sport`
--

LOCK TABLES `sport` WRITE;
/*!40000 ALTER TABLE `sport` DISABLE KEYS */;
INSERT INTO `sport` VALUES (1,'Kosarka'),(2,'Odbojka'),(3,'Vaterpolo'),(4,'Atletika'),(5,'Biciklizam'),(6,'Plivanje'),(7,'Stoni tenis'),(8,'Streljastvo'),(9,'Tenis');
/*!40000 ALTER TABLE `sport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sport_discipline`
--

DROP TABLE IF EXISTS `sport_discipline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sport_discipline` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `sport_id` int(11) DEFAULT NULL,
  `min_players` int(11) DEFAULT NULL,
  `max_players` int(11) DEFAULT NULL,
  `discipline_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sport_id` (`sport_id`),
  CONSTRAINT `sport_discipline_ibfk_1` FOREIGN KEY (`sport_id`) REFERENCES `sport` (`idsport`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sport_discipline`
--

LOCK TABLES `sport_discipline` WRITE;
/*!40000 ALTER TABLE `sport_discipline` DISABLE KEYS */;
INSERT INTO `sport_discipline` VALUES (1,'Kosarka',1,5,12,'ekipni'),(2,'Odbojka',2,6,12,'ekipni'),(3,'Vaterpolo',3,6,13,'ekipni'),(4,'100m trcanje',4,1,1,'individualni'),(5,'200m trcanje',4,1,1,'individualni'),(6,'400m trcanje',4,1,1,'individualni'),(7,'800m trcanje',4,1,1,'individualni'),(8,'1000m trcanje',4,1,1,'individualni'),(9,'5000m trcanje',4,1,1,'individualni'),(10,'Skok u vis',4,1,1,'individualni'),(11,'Skok u dalj',4,1,1,'individualni'),(12,'Troskok',4,1,1,'individualni'),(13,'Skok s motkom',4,1,1,'individualni'),(14,'Bacanje kugle',4,1,1,'individualni'),(15,'Bacanje diska',4,1,1,'individualni'),(16,'Bacanje kladiva',4,1,1,'individualni'),(17,'Bacanje koplja',4,1,1,'individualni'),(18,'Maraton',4,1,1,'individualni'),(19,'20km brzo hodanje',4,1,1,'individualni'),(20,'50km brzo hodanje',4,1,1,'individualni'),(21,'Drumska trka 255km',5,1,1,'individualni'),(22,'100m leptir',6,1,1,'individualni'),(23,'200m slobodno',6,1,1,'individualni'),(24,'Signl',7,1,1,'individualni'),(25,'Dubl',7,2,2,'ekipni'),(26,'50m trostav',8,1,1,'individualni'),(27,'10m vazdusna puska',8,1,1,'individualni'),(28,'25m malokalib. pistolj',8,1,1,'individualni'),(29,'10m vazdusni pistolj',8,1,1,'individualni'),(30,'Signl',9,1,1,'individualni'),(31,'Dubl',9,2,2,'ekipni');
/*!40000 ALTER TABLE `sport_discipline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sportsman`
--

DROP TABLE IF EXISTS `sportsman`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sportsman` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `id_country` int(11) DEFAULT NULL,
  `id_sport` int(11) DEFAULT NULL,
  `id_team` int(11) DEFAULT NULL,
  `sex` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_country` (`id_country`),
  KEY `id_sport` (`id_sport`),
  KEY `id_team` (`id_team`),
  CONSTRAINT `sportsman_ibfk_1` FOREIGN KEY (`id_country`) REFERENCES `country` (`id`),
  CONSTRAINT `sportsman_ibfk_2` FOREIGN KEY (`id_sport`) REFERENCES `sport` (`idsport`),
  CONSTRAINT `sportsman_ibfk_3` FOREIGN KEY (`id_team`) REFERENCES `team` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sportsman`
--

LOCK TABLES `sportsman` WRITE;
/*!40000 ALTER TABLE `sportsman` DISABLE KEYS */;
INSERT INTO `sportsman` VALUES (74,'Milos','Teodosic',1,1,1,'m'),(75,'Marko','Simonovic',1,1,1,'m'),(76,'Bogdan','Bogdanovic',1,1,1,'m'),(77,'Stefan','Markovic',1,1,1,'m'),(78,'Nikola','Kalinic',1,1,1,'m'),(79,'Nemanja','Nedovic',1,1,1,'m'),(80,'Stefan','Bircevic',1,1,1,'m'),(81,'Miroslav','Raduljica',1,1,1,'m'),(82,'Nikola','Jokic',1,1,1,'m'),(83,'Vladimir','Stimac',1,1,1,'m'),(84,'Stefan','Jovic',1,1,1,'m'),(85,'Milan','Macvan',1,1,1,'m'),(92,'Buba','Beba',1,4,NULL,'z'),(93,'Buba','Cuba',1,4,NULL,'m'),(97,'Buba','Hauba',1,5,NULL,'z'),(98,'Odb1','Odb1',1,2,2,'m'),(99,'Odb2','Odb2',1,2,2,'m'),(100,'Thomas','Schreiner',2,1,3,'m'),(101,'Davor','Lamesic',2,1,3,'m'),(102,'Enis','Murati',2,1,3,'m'),(103,'Joe','Dean Shaw',2,1,3,'m'),(104,'Jozo','Rados',2,1,3,'m'),(105,'Romed','Vieider',2,1,3,'m'),(106,'Florian','Trmal',2,1,3,'m'),(107,'Rasid','Mahalbasic',2,1,3,'m'),(108,'Moritz','Lanegger',2,1,3,'m'),(109,'Sebastian','Koch',2,1,3,'m'),(110,'Maximilian','Hopfgartner',2,1,3,'m'),(111,'Filip','Kramer',2,1,3,'m'),(112,'Anton','Marech',2,1,3,'m'),(113,'Thomas','Klepeisz',2,1,3,'m'),(114,'Sebastian','Kaeferle',2,1,3,'m'),(121,'Jimmy','Buttler',3,1,10,NULL),(122,'Kevin','Durant',3,1,10,NULL),(124,'Pau','Gasol',4,1,12,'m'),(125,'Rudy','Fernandez',4,1,12,'m'),(127,'Goulding','Chris',5,1,13,'m'),(128,'Mills','Parry',5,1,13,'m'),(131,'Thomas','Heurtel',7,1,15,'m'),(132,'Nicolas','Batum',7,1,15,'m'),(133,'Mantas','Kalnietis',8,1,16,'m'),(134,'Adas','Juskevicius',8,1,16,'m'),(135,'Luis','Scola',9,1,17,'m'),(136,'Manu','Ginobili',9,1,17,'m'),(137,'Luka','Babic',6,1,18,'m'),(138,'Filip','Kruslin',6,1,18,'m'),(139,'Feyisa','Lilesa',11,4,NULL,'m'),(140,'Eliud','Kipchoge',10,4,NULL,'m'),(141,'Galen','Rupp',3,4,NULL,'m'),(142,'Ghirmay','Ghebreslassie',13,4,NULL,'m'),(143,'Alphonce Felix','Simbu',12,4,NULL,'m'),(144,'Jared','Ward',3,4,NULL,'m'),(145,'Tadesse','Abraham',14,4,NULL,'m'),(146,'Solomon','Mutai',15,4,NULL,'m'),(147,'Callum','Hawkins',16,4,NULL,'m'),(148,'Eric','Gillis',18,4,NULL,'m'),(149,'Abdi','Nageeye',17,4,NULL,'m'),(150,'Streljas','Argentina',9,8,NULL,'m'),(151,'Streljas','Austraila',5,8,NULL,'m'),(152,'Streljas','Kanadski',18,8,NULL,'m'),(153,'Teniserka1','Erithrea',13,9,19,'z'),(154,'Teniserka2','Erithrea',13,9,19,'z'),(155,'Teniserka1','Hrvatska',7,9,20,'z'),(156,'Teniserka2','Hrvatska',7,9,20,'z'),(157,'Teniserka1','Britanija',16,9,21,'z'),(158,'Teniserka2','Britanija',16,9,21,'z'),(159,'Jelena','Jankovic',1,9,22,'z'),(160,'Ana','Ivanovic',1,9,22,'z'),(161,'StoniTeniser1','Argentinski',9,7,NULL,'m'),(162,'StoniTeniser2','Austrailjski',5,9,NULL,'m'),(163,'StoniTeniser1','Kanadski',18,7,NULL,'m'),(164,'StoniTeniser','Srpski',1,9,NULL,'m'),(165,'StoniTeniser1','Srpski',1,7,NULL,'m'),(166,'StoniTeniser','Enijanski',10,7,NULL,'m'),(167,'Odbojkasica','NekaTamo',9,2,23,'m'),(168,'asdsad','asdsa',9,2,23,'m'),(169,'TestIme','TestPrezime',9,4,NULL,'m');
/*!40000 ALTER TABLE `sportsman` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sportsman_discipline`
--

DROP TABLE IF EXISTS `sportsman_discipline`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sportsman_discipline` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sportsman_id` int(11) DEFAULT NULL,
  `sport_discipline_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sportsman_id` (`sportsman_id`),
  KEY `sport_discipline_id` (`sport_discipline_id`),
  CONSTRAINT `sportsman_discipline_ibfk_1` FOREIGN KEY (`sportsman_id`) REFERENCES `sportsman` (`id`),
  CONSTRAINT `sportsman_discipline_ibfk_2` FOREIGN KEY (`sport_discipline_id`) REFERENCES `sport_discipline` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sportsman_discipline`
--

LOCK TABLES `sportsman_discipline` WRITE;
/*!40000 ALTER TABLE `sportsman_discipline` DISABLE KEYS */;
INSERT INTO `sportsman_discipline` VALUES (12,74,1),(13,75,1),(14,76,1),(15,77,1),(16,78,1),(17,79,1),(18,80,1),(19,81,1),(20,82,1),(21,83,1),(22,84,1),(23,85,1),(36,92,7),(37,92,6),(38,92,8),(39,93,7),(40,97,21),(41,98,2),(42,99,2),(43,100,1),(44,101,1),(45,102,1),(46,103,1),(47,104,1),(48,105,1),(49,106,1),(50,107,1),(51,108,1),(52,109,1),(53,110,1),(54,111,1),(55,112,1),(56,113,1),(57,114,1),(64,121,1),(65,122,1),(67,124,1),(68,125,1),(70,127,1),(71,128,1),(74,131,1),(75,132,1),(76,133,1),(77,134,1),(78,135,1),(79,136,1),(80,137,1),(81,138,1),(82,139,18),(83,140,18),(84,141,18),(85,142,18),(86,143,18),(87,144,18),(88,145,18),(89,146,18),(90,147,18),(91,148,18),(92,149,18),(93,150,27),(94,151,27),(95,152,27),(96,153,31),(97,154,31),(98,155,31),(99,156,31),(100,157,31),(101,158,31),(102,159,31),(103,160,31),(104,161,24),(105,162,30),(106,163,24),(107,164,30),(108,165,24),(109,166,24),(110,167,2),(111,168,2),(112,169,4),(113,169,6),(114,169,9),(115,169,13),(116,169,17),(117,169,18);
/*!40000 ALTER TABLE `sportsman_discipline` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sportsman_qualifying_group`
--

DROP TABLE IF EXISTS `sportsman_qualifying_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sportsman_qualifying_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_sportsman` int(11) DEFAULT NULL,
  `id_qualifying_group` int(11) DEFAULT NULL,
  `result` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_sportsman` (`id_sportsman`),
  KEY `id_qualifying_group` (`id_qualifying_group`),
  CONSTRAINT `sportsman_qualifying_group_ibfk_1` FOREIGN KEY (`id_sportsman`) REFERENCES `sportsman` (`id`),
  CONSTRAINT `sportsman_qualifying_group_ibfk_2` FOREIGN KEY (`id_qualifying_group`) REFERENCES `qualifying_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=138 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sportsman_qualifying_group`
--

LOCK TABLES `sportsman_qualifying_group` WRITE;
/*!40000 ALTER TABLE `sportsman_qualifying_group` DISABLE KEYS */;
INSERT INTO `sportsman_qualifying_group` VALUES (127,139,117,''),(128,140,117,''),(129,141,117,''),(130,142,117,''),(131,143,117,''),(132,144,117,''),(133,145,118,''),(134,146,118,''),(135,147,118,''),(136,148,118,''),(137,149,118,'');
/*!40000 ALTER TABLE `sportsman_qualifying_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_country` int(11) NOT NULL,
  `id_sport` int(11) NOT NULL,
  `sex` char(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_country` (`id_country`),
  KEY `id_sport` (`id_sport`),
  CONSTRAINT `team_ibfk_1` FOREIGN KEY (`id_country`) REFERENCES `country` (`id`),
  CONSTRAINT `team_ibfk_2` FOREIGN KEY (`id_sport`) REFERENCES `sport` (`idsport`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (1,1,1,'m'),(2,1,2,'z'),(3,2,1,'m'),(10,3,1,'m'),(12,4,1,'m'),(13,5,1,'m'),(14,4,1,'m'),(15,7,1,'m'),(16,8,1,'m'),(17,9,1,'m'),(18,6,1,'m'),(19,13,9,'z'),(20,7,9,'z'),(21,16,9,'z'),(22,1,9,'z'),(23,9,2,'m');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_qualifying_group`
--

DROP TABLE IF EXISTS `team_qualifying_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `team_qualifying_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_team` int(11) DEFAULT NULL,
  `id_qualifying_group` int(11) DEFAULT NULL,
  `points` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_team` (`id_team`),
  KEY `id_qualifying_group` (`id_qualifying_group`),
  CONSTRAINT `team_qualifying_group_ibfk_1` FOREIGN KEY (`id_team`) REFERENCES `team` (`id`),
  CONSTRAINT `team_qualifying_group_ibfk_2` FOREIGN KEY (`id_qualifying_group`) REFERENCES `qualifying_group` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=175 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_qualifying_group`
--

LOCK TABLES `team_qualifying_group` WRITE;
/*!40000 ALTER TABLE `team_qualifying_group` DISABLE KEYS */;
INSERT INTO `team_qualifying_group` VALUES (167,18,114,3),(168,12,114,5),(169,13,114,0),(170,16,114,5),(171,1,115,6),(172,17,115,3),(173,10,115,4),(174,15,115,5);
/*!40000 ALTER TABLE `team_qualifying_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `country_id` int(11) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `approved` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`iduser`),
  KEY `fk_country_id` (`country_id`),
  CONSTRAINT `fk_country_id` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'NemanjaLu','Olovcica1','Nemanja','Lucic',1,'lucic_92@hotmail.com','vodja',1),(2,'Austar','asdASD1','Austar','Austric',2,'austar@au.au','vodja',1),(3,'delegat1','asdASD1','Delegat1','Delegatovic1',1,'delegat1@delegat.com','delegat',1),(4,'USALeader','asdASD1','USA','Leader',3,'usa@leader.com','vodja',1),(5,'SpainLeader','asdASD1','Spain','Leader',4,'spain@leader.com','vodja',1),(6,'AustraliaLeader','asdASD1','Australia','Leader',5,'australia@leader.com','vodja',1),(7,'CroatiaLeader','asdASD1','Croatia','Leader',6,'croatia@leader.com','vodja',1),(8,'FranceLeader','asdASD1','France','Leader',7,'france@leader.com','vodja',1),(9,'LithuaniaLeader','asdASD1','Lithuania','Leader',8,'lithuania@leader.com','vodja',1),(10,'ArgentinaLeader','asdASD1','Argentina','Leader',9,'argentina@leader.com','vodja',1),(11,'EthiopiaLeader','asdASD1','Ethiopia','Leader',11,'ethiopia@eth.com','vodja',1),(12,'KenyaLeader','asdASD1','Kenya','Leader',10,'keynaleader@ol.com','vodja',1),(13,'TanzaniaLeader','asdASD1','Tanzania','Leader',12,'tanzania@leader.ol','vodja',1),(14,'EritreaLeader','asdASD1','Eritrea','Leader',13,'eritrea@leader.ol','vodja',1),(15,'SwitzerlandLeader','asdASD1','Switzerland','Leader',14,'switzerland@leader.ol','vodja',1),(16,'UgandaLeader','asdASD1','Uganda','Leader',15,'uganda@leader.ol','vodja',1),(17,'GreatBritainLeader','asdASD1','GreabBritain','Leader',16,'greatBritain@leader.ol','vodja',1),(18,'NetherlandsLeader','asdASD1','Netherlands','Leader',17,'netherlands@leader.ol','vodja',1),(19,'CanadaLeader','asdASD1','Canada','Leader',18,'canada@leader.ol','vodja',1),(20,'delega2','asdASd1','Delegat','Delgatovski',2,'delegat2@ol.rio','delegat',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_request`
--

DROP TABLE IF EXISTS `user_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_request` (
  `iduser` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `country_id` int(11) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`iduser`),
  KEY `fk_country_id` (`country_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_request`
--

LOCK TABLES `user_request` WRITE;
/*!40000 ALTER TABLE `user_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_request` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-05  9:14:28
