-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 10.200.11.13    Database: mkt_dev
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.12-MariaDB

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
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` char(10) DEFAULT NULL,
  `description` char(10) DEFAULT NULL,
  `url` char(10) DEFAULT NULL,
  `content` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asset`
--

LOCK TABLES `asset` WRITE;
/*!40000 ALTER TABLE `asset` DISABLE KEYS */;
/*!40000 ALTER TABLE `asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audience_columns`
--

DROP TABLE IF EXISTS `audience_columns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audience_columns` (
  `id` int(5) unsigned NOT NULL DEFAULT '1',
  `field_name` varchar(45) NOT NULL COMMENT '����',
  `field_code` varchar(45) NOT NULL COMMENT 'Ӣ����������Ӧdata_app�ȱ��column',
  `field_order` tinyint(4) NOT NULL DEFAULT '0' COMMENT '˳��',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Ⱥ�б�����';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audience_columns`
--

LOCK TABLES `audience_columns` WRITE;
/*!40000 ALTER TABLE `audience_columns` DISABLE KEYS */;
INSERT INTO `audience_columns` VALUES (1,'����','audience_list_name',1,0,'2016-06-12 15:50:42','2016-06-12 08:53:55'),(2,'����','audience_count',2,0,'2016-06-12 15:50:42','2016-06-12 08:53:55'),(3,'��Դ','source_name',3,0,'2016-06-12 15:50:42','2016-06-12 08:53:55'),(4,'����ʱ��','create_time',4,0,'2016-06-12 15:50:42','2016-06-12 07:51:20');
/*!40000 ALTER TABLE `audience_columns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audience_list`
--

DROP TABLE IF EXISTS `audience_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audience_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '��Ⱥ���',
  `audience_name` varchar(45) DEFAULT NULL COMMENT '��Ⱥ����',
  `audience_rows` int(45) DEFAULT '0' COMMENT '��Ⱥ������ϵ����',
  `source` varchar(512) DEFAULT NULL COMMENT '��Դ',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Ⱥ����';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audience_list`
--

LOCK TABLES `audience_list` WRITE;
/*!40000 ALTER TABLE `audience_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `audience_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audience_list_party_map`
--

DROP TABLE IF EXISTS `audience_list_party_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audience_list_party_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `audience_list_id` int(11) NOT NULL COMMENT '��ȺID',
  `party_id` int(11) NOT NULL COMMENT '������(��ϵ��)ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ����� 0-δɾ��  1-ɾ�� ',
  `oper` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Ⱥ������ϸ��Ϣ��ĳ����Ⱥ��������ϵ��\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audience_list_party_map`
--

LOCK TABLES `audience_list_party_map` WRITE;
/*!40000 ALTER TABLE `audience_list_party_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `audience_list_party_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_action_save_audience`
--

DROP TABLE IF EXISTS `campaign_action_save_audience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_save_audience` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL COMMENT '��������',
  `audience_id` int(11) DEFAULT NULL COMMENT 'audience_list�����id',
  `audience_name` varchar(45) DEFAULT NULL COMMENT 'audience_list�����audience_name',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����,0:δɾ����1����ɾ��',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�������Ա�:������Ⱥ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_action_save_audience`
--

LOCK TABLES `campaign_action_save_audience` WRITE;
/*!40000 ALTER TABLE `campaign_action_save_audience` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_action_save_audience` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_action_send_h5`
--

DROP TABLE IF EXISTS `campaign_action_send_h5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_send_h5` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL COMMENT '��������',
  `pub_asset_id` int(11) DEFAULT NULL COMMENT '��Ӧwechat_asset���asset_id',
  `prv_asset_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL COMMENT '����wechat_asset_group���id,����Ⱥ�飬��null',
  `img_text_asset_id` int(11) DEFAULT NULL COMMENT '��Ӧimg_text_asset���id',
  `pub_id` varchar(512) DEFAULT NULL COMMENT '��Ӧ�����ǱߵĹ��ں�pub_id',
  `uin` varchar(512) DEFAULT NULL COMMENT '��Ӧ�����Ǳߵĸ��˺�uin',
  `ucode` varchar(512) DEFAULT NULL COMMENT '��Ӧ������Ⱥ��ucode',
  `material_id` int(11) DEFAULT NULL COMMENT '��Ӧ�����Ǳߵ�material_id',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�������Ա�:����h5ͼ��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_action_send_h5`
--

LOCK TABLES `campaign_action_send_h5` WRITE;
/*!40000 ALTER TABLE `campaign_action_send_h5` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_action_send_h5` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_action_send_privt`
--

DROP TABLE IF EXISTS `campaign_action_send_privt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_send_privt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL COMMENT '�ڵ�����',
  `uin` varchar(200) DEFAULT NULL COMMENT '��Ӧ�����ĸ��˺�uin',
  `asset_id` int(11) DEFAULT NULL COMMENT '����wechat_asset���asset_id',
  `ucode` varchar(500) DEFAULT NULL COMMENT '��Ӧ������Ⱥ��ucode',
  `group_id` int(11) DEFAULT NULL COMMENT '����wechat_asset_group���id,����Ⱥ�飬��null',
  `text_info` varchar(2048) DEFAULT NULL COMMENT 'Ҫ���͵��ı���Ϣ',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�������Ա�:���͸��˺���Ϣ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_action_send_privt`
--

LOCK TABLES `campaign_action_send_privt` WRITE;
/*!40000 ALTER TABLE `campaign_action_send_privt` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_action_send_privt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_action_send_pub`
--

DROP TABLE IF EXISTS `campaign_action_send_pub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_send_pub` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL,
  `material_id` int(11) DEFAULT NULL COMMENT '��Ӧ������material_id',
  `img_text_asset_id` int(11) DEFAULT NULL COMMENT 'img_text_asset���id',
  `pub_id` varchar(512) DEFAULT NULL COMMENT '��Ӧ�����Ĺ��ں�pub_id',
  `asset_id` int(11) DEFAULT NULL COMMENT '��Ӧwechat_asset���asset_id',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�������Ա�:���͹��ں���Ϣ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_action_send_pub`
--

LOCK TABLES `campaign_action_send_pub` WRITE;
/*!40000 ALTER TABLE `campaign_action_send_pub` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_action_send_pub` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_action_set_tag`
--

DROP TABLE IF EXISTS `campaign_action_set_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_set_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL,
  `tag_ids` varchar(512) DEFAULT NULL COMMENT '��Ӧcustom_tag���id,����ö��ŷָ�',
  `tag_names` varchar(512) DEFAULT NULL COMMENT '�Զ����ǩ��������ö��ŷָ�',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�������Ա�:���ñ�ǩ ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_action_set_tag`
--

LOCK TABLES `campaign_action_set_tag` WRITE;
/*!40000 ALTER TABLE `campaign_action_set_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_action_set_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_action_wait`
--

DROP TABLE IF EXISTS `campaign_action_wait`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_wait` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL COMMENT '0:���ʱ��1:ָ��ʱ��',
  `relative_value` int(11) DEFAULT NULL,
  `relative_type` tinyint(4) DEFAULT NULL COMMENT '0:Сʱ��1���죬2���ܣ�3����',
  `specific_time` datetime DEFAULT NULL COMMENT 'ָ��ʱ��',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�������Ա�:�ȴ�';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_action_wait`
--

LOCK TABLES `campaign_action_wait` WRITE;
/*!40000 ALTER TABLE `campaign_action_wait` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_action_wait` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_audience_target`
--

DROP TABLE IF EXISTS `campaign_audience_target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_audience_target` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL,
  `segmentation_id` int(11) DEFAULT NULL,
  `segmentation_name` varchar(200) DEFAULT NULL,
  `allowed_new` tinyint(4) DEFAULT '0' COMMENT '0:����,1:������',
  `refresh_interval` int(11) DEFAULT NULL,
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:Сʱ,1:��,2:��,3:��',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ŀ����Ⱥ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_audience_target`
--

LOCK TABLES `campaign_audience_target` WRITE;
/*!40000 ALTER TABLE `campaign_audience_target` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_audience_target` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_body`
--

DROP TABLE IF EXISTS `campaign_body`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_body` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `head_id` int(11) DEFAULT NULL,
  `node_type` tinyint(4) DEFAULT '0' COMMENT '0:����,1:����,2����,3�ж�,4:��֧��',
  `item_type` tinyint(4) DEFAULT NULL COMMENT '0,1,2,3...����ԭ������ֵ�˳��',
  `item_id` varchar(200) DEFAULT NULL COMMENT '��������ڵ���ҵ��id',
  `statistics_url` varchar(1024) DEFAULT NULL COMMENT '����url',
  `pos_x` varchar(20) DEFAULT NULL COMMENT '���x����',
  `pos_y` varchar(20) DEFAULT NULL COMMENT '���y����',
  `pos_z` varchar(20) DEFAULT NULL,
  `audience_count` int(11) DEFAULT '0' COMMENT '��ǰ�ڵ���������',
  `task_id` int(11) DEFAULT NULL COMMENT '����task_schedule���id',
  `description` varchar(512) DEFAULT NULL COMMENT '�ڵ�����',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `head_item_unq` (`head_id`,`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ӫ���body��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_body`
--

LOCK TABLES `campaign_body` WRITE;
/*!40000 ALTER TABLE `campaign_body` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_body` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_columns`
--

DROP TABLE IF EXISTS `campaign_columns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_columns` (
  `id` int(5) unsigned NOT NULL DEFAULT '1',
  `field_name` varchar(45) NOT NULL COMMENT '����',
  `field_code` varchar(45) NOT NULL COMMENT 'Ӣ������',
  `field_order` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��б���������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_columns`
--

LOCK TABLES `campaign_columns` WRITE;
/*!40000 ALTER TABLE `campaign_columns` DISABLE KEYS */;
INSERT INTO `campaign_columns` VALUES (1,'����','name',1,0,'2016-06-12 16:15:42','2016-06-12 08:53:25'),(2,'״̬','status',2,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(3,'����ʱ��','create_time',3,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(4,'�ƻ���ʼʱ��','start_time',4,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(5,'�ƻ�����ʱ��','end_time',5,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(6,'������Ⱥ','segmentation_name',6,0,'2016-06-12 16:15:42','2016-06-12 08:15:42');
/*!40000 ALTER TABLE `campaign_columns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_prop_compare`
--

DROP TABLE IF EXISTS `campaign_decision_prop_compare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_prop_compare` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL,
  `prop_type` tinyint(4) DEFAULT NULL COMMENT '��������,0:�ı�,1:����,2:����',
  `rule` tinyint(4) DEFAULT NULL COMMENT '0������,1:����2:starts_with��3��ends_with4:empty,5:����,6:С��,7:���ڵ���,8:С�ڵ���',
  `exclude` tinyint(4) DEFAULT NULL COMMENT '0:��ѡ��"��",1:ѡ�С�����',
  `value` varchar(1024) DEFAULT NULL COMMENT '������ı�',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ϵ�����ԱȽϱ�';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_prop_compare`
--

LOCK TABLES `campaign_decision_prop_compare` WRITE;
/*!40000 ALTER TABLE `campaign_decision_prop_compare` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_prop_compare` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_prvt_friends`
--

DROP TABLE IF EXISTS `campaign_decision_prvt_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_prvt_friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL,
  `uin` varchar(200) DEFAULT NULL COMMENT '��Ӧ�����Ǳߵ�uin',
  `asset_id` int(11) DEFAULT NULL COMMENT '����wechat_asset���asset_id',
  `ucode` varchar(500) DEFAULT NULL COMMENT '��Ӧ������Ⱥ��ucode',
  `group_id` int(11) DEFAULT NULL COMMENT '����wechat_asset_group���id,����Ⱥ��,��null',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '״̬ˢ��Ƶ����ֵ',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:Сʱ,1:��,2;��,3:��',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�Ƿ��Ǹ��˺ź���';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_prvt_friends`
--

LOCK TABLES `campaign_decision_prvt_friends` WRITE;
/*!40000 ALTER TABLE `campaign_decision_prvt_friends` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_prvt_friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_pub_fans`
--

DROP TABLE IF EXISTS `campaign_decision_pub_fans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_pub_fans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL,
  `pub_id` varchar(500) DEFAULT NULL COMMENT '��Ӧ�����Ĺ��ں�pub_id',
  `asset_id` int(11) DEFAULT NULL COMMENT '���ں�����,��Ӧwechat_asset���asset_id',
  `subscribe_time` tinyint(4) DEFAULT NULL COMMENT '����ʱ��,0:һ�죬1��һ�ܣ�2��һ�£�3�����£�4�����£�5��һ�꣬6��һ������',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '״̬ˢ��Ƶ����ֵ',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:Сʱ,1:��,2;��,3:��,4:����',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�Ƿ����˹��ں�';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_pub_fans`
--

LOCK TABLES `campaign_decision_pub_fans` WRITE;
/*!40000 ALTER TABLE `campaign_decision_pub_fans` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_pub_fans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_tag`
--

DROP TABLE IF EXISTS `campaign_decision_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `campaign_head_id` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `rule` tinyint(4) DEFAULT '0' COMMENT '0:ȫ��ƥ�䣬1��ƥ����һ��2��ƥ�����������',
  `tag_ids` varchar(1024) DEFAULT NULL COMMENT '��ǩid,����ö��ŷָ�',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ǩ�ж�';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_tag`
--

LOCK TABLES `campaign_decision_tag` WRITE;
/*!40000 ALTER TABLE `campaign_decision_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_tag_map`
--

DROP TABLE IF EXISTS `campaign_decision_tag_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_tag_map` (
  `id` int(11) NOT NULL DEFAULT '0',
  `campaign_decision_tag_id` int(11) DEFAULT NULL COMMENT '����campaign_decision_tag���id',
  `tag_id` int(11) DEFAULT NULL,
  `tag_name` varchar(200) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='campaign_decision_tag��tag�Ĺ�����\n�ñ�ֹͣʹ��,tag��Ϣ�ŵ�campaign_decision_tag���tag_ids����';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_tag_map`
--

LOCK TABLES `campaign_decision_tag_map` WRITE;
/*!40000 ALTER TABLE `campaign_decision_tag_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_tag_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_wechat_forward`
--

DROP TABLE IF EXISTS `campaign_decision_wechat_forward`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_wechat_forward` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL,
  `asset_id` int(11) DEFAULT NULL COMMENT '��Ӧwechat_asset���asset_id',
  `pub_id` varchar(512) DEFAULT NULL COMMENT '�ԵĴ����Ĺ��ں�pub_id',
  `img_text_asset_id` int(11) DEFAULT NULL COMMENT 'img_text_asset���id',
  `material_id` int(11) DEFAULT NULL COMMENT '��Ӧ������material_id',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '�ʹ�״̬ˢ��Ƶ����ֵ',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:Сʱ,1:��,2;��,3:��',
  `forward_times` tinyint(4) DEFAULT NULL COMMENT 'ת������,0:����,1:��һ�˴Σ�2����ʮ��3����һ�٣�4�������',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢��ͼ���Ƿ�ת��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_wechat_forward`
--

LOCK TABLES `campaign_decision_wechat_forward` WRITE;
/*!40000 ALTER TABLE `campaign_decision_wechat_forward` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_wechat_forward` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_wechat_read`
--

DROP TABLE IF EXISTS `campaign_decision_wechat_read`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_wechat_read` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL,
  `asset_id` int(11) DEFAULT NULL COMMENT '��Ӧwechat_asset���asset_id',
  `pub_id` varchar(512) DEFAULT NULL COMMENT '�ԵĴ����Ĺ��ں�pub_id',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '״̬ˢ��Ƶ����ֵ',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:Сʱ,1:��,2;��,3:��',
  `img_text_asset_id` int(11) DEFAULT NULL COMMENT 'img_text_asset���id',
  `material_id` int(11) DEFAULT NULL COMMENT '��Ӧ������material_id',
  `read_time` tinyint(4) DEFAULT NULL COMMENT '�鿴ʱ��,0:����,1:��һ����,2:��������,3:�������,4:��ʮ����',
  `read_percent` tinyint(4) DEFAULT NULL COMMENT '�鿴��ɶȣ�0�����ޣ�1����һ�룬2:ȫ���Ķ�',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢��ͼ���Ƿ�鿴';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_wechat_read`
--

LOCK TABLES `campaign_decision_wechat_read` WRITE;
/*!40000 ALTER TABLE `campaign_decision_wechat_read` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_wechat_read` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_decision_wechat_sent`
--

DROP TABLE IF EXISTS `campaign_decision_wechat_sent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_wechat_sent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id����ǰ������',
  `name` varchar(200) DEFAULT NULL,
  `pub_id` varchar(1024) DEFAULT NULL COMMENT '���ں�id',
  `pub_name` varchar(1024) DEFAULT NULL COMMENT '���ں�����',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '�ʹ�״̬ˢ��Ƶ����ֵ',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:Сʱ,1:��,2;��,3:��',
  `wechat_h5_id` int(11) DEFAULT NULL,
  `wechat_h5_name` varchar(1024) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢��ͼ���Ƿ���\n�����ӿڲ�֧�֣���ʱͣ�øñ�';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_decision_wechat_sent`
--

LOCK TABLES `campaign_decision_wechat_sent` WRITE;
/*!40000 ALTER TABLE `campaign_decision_wechat_sent` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_decision_wechat_sent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_head`
--

DROP TABLE IF EXISTS `campaign_head`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_head` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `publish_status` tinyint(4) DEFAULT '0' COMMENT '0:δ����,1:�ѷ���,2:���,3:�ѽ���',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ӫ���head��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_head`
--

LOCK TABLES `campaign_head` WRITE;
/*!40000 ALTER TABLE `campaign_head` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_head` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_node_item`
--

DROP TABLE IF EXISTS `campaign_node_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_node_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ptype` tinyint(4) NOT NULL COMMENT 'ptype=-1��ʾ��Ԫ��Ϊ���ڵ�����',
  `type` tinyint(4) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `icon` varchar(45) DEFAULT NULL,
  `display_index` int(11) DEFAULT NULL COMMENT 'ҳ������ʾ��˳��',
  `url` varchar(1024) DEFAULT NULL,
  `color` varchar(45) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_ptype_unq` (`ptype`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_node_item`
--

LOCK TABLES `campaign_node_item` WRITE;
/*!40000 ALTER TABLE `campaign_node_item` DISABLE KEYS */;
INSERT INTO `campaign_node_item` VALUES (1,-1,0,'����','trigger',NULL,0,NULL,NULL,0,NULL,'2016-06-08 06:11:03'),(2,-1,1,'����','audiences',NULL,1,NULL,NULL,0,NULL,'2016-06-08 06:11:03'),(3,-1,2,'����','decisions',NULL,2,NULL,NULL,0,NULL,'2016-06-08 06:11:03'),(4,-1,3,'�ж�','activity',NULL,3,NULL,NULL,0,NULL,'2016-06-08 06:11:03'),(5,0,0,'ԤԼ����','timer-trigger','&#xe63f;',0,NULL,NULL,0,NULL,'2016-07-21 09:20:34'),(6,0,2,'�ֶ�����','manual-trigger','&#xe63e;',2,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(7,1,0,'Ŀ����Ⱥ','target-group','&#xe639;',0,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(8,2,0,'��ϵ�����ԱȽ�','attr-comparison','&#xe66e;',0,NULL,NULL,1,NULL,'2016-07-01 02:07:47'),(9,2,1,'΢��ͼ���Ƿ���','wechat-send','&#xe66d;',1,NULL,NULL,1,NULL,'2016-06-27 07:23:36'),(10,2,2,'΢��ͼ���Ƿ�鿴','wechat-check','&#xe66c;',2,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(11,2,3,'΢��ͼ���Ƿ�ת��','wechat-forwarded','&#xe673;',3,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(12,2,4,'�Ƿ��Ĺ��ں�','subscriber-public','&#xe66b;',4,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(14,2,6,'��ǩ�ж�','label-judgment','&#xe671;',6,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(15,3,0,'�ȴ�','wait-set','&#xe670;',0,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(16,3,1,'���浱ǰ��Ⱥ','save-current-group','&#xe669;',1,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(17,3,2,'���ñ�ǩ','set-tag','&#xe668;',2,NULL,NULL,0,NULL,'2016-06-08 06:16:10'),(18,3,3,'��ӵ������',NULL,NULL,3,NULL,NULL,1,NULL,'2016-06-08 03:31:10'),(19,3,4,'ת�Ƶ������',NULL,NULL,4,NULL,NULL,1,NULL,'2016-06-08 03:31:10'),(20,3,5,'����΢��ͼ��','send-img','&#xe63a;',5,NULL,NULL,0,NULL,'2016-06-08 06:16:10');
/*!40000 ALTER TABLE `campaign_node_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_switch`
--

DROP TABLE IF EXISTS `campaign_switch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_switch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(255) DEFAULT NULL COMMENT '����campaign_body���е�item_id',
  `type` tinyint(4) DEFAULT NULL COMMENT '��֧����:0:�Ƿ�֧,1:�Ƿ�֧,2:��������֧',
  `draw_type` tinyint(4) DEFAULT '0' COMMENT '��������,0:curveTriangle',
  `color` varchar(100) DEFAULT NULL,
  `next_item_id` varchar(255) DEFAULT NULL COMMENT '����campaign_body���е�item_id',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Ӫ����е����߱�';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_switch`
--

LOCK TABLES `campaign_switch` WRITE;
/*!40000 ALTER TABLE `campaign_switch` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_switch` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campaign_trigger_timer`
--

DROP TABLE IF EXISTS `campaign_trigger_timer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_trigger_timer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT 'ҵ��id��ǰ������',
  `name` varchar(100) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ʱ��������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campaign_trigger_timer`
--

LOCK TABLES `campaign_trigger_timer` WRITE;
/*!40000 ALTER TABLE `campaign_trigger_timer` DISABLE KEYS */;
/*!40000 ALTER TABLE `campaign_trigger_timer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_list`
--

DROP TABLE IF EXISTS `contact_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_list` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `name` varchar(100) DEFAULT NULL COMMENT '����',
  `gender` tinyint(4) DEFAULT NULL COMMENT '�Ա�\n1-��\n2-Ů \n3-δȷ�� \n4-��ȷ��',
  `birthday` date DEFAULT NULL COMMENT '����������',
  `provice` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL COMMENT 'ְҵ',
  `monthly_income` decimal(22,2) DEFAULT NULL COMMENT '������',
  `monthly_consume` decimal(22,2) DEFAULT NULL COMMENT '�¾�����',
  `marital_status` varchar(45) DEFAULT NULL COMMENT '����״��',
  `education` varchar(45) DEFAULT NULL COMMENT '�����̶�',
  `employment` varchar(45) DEFAULT NULL COMMENT '��ҵ���',
  `nationality` varchar(45) DEFAULT NULL COMMENT '����',
  `blood_type` varchar(45) DEFAULT NULL COMMENT 'Ѫ��',
  `citizenship` varchar(45) DEFAULT NULL COMMENT '����',
  `iq` int(11) DEFAULT NULL,
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:δ���������� 1:�Ѿ�Ϊ������д��bitmap 2:�����Ѿ����������ݱ� 3:ɾ��',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 01:00:00' COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  `nickname` varchar(128) DEFAULT NULL,
  `head_img_url` varchar(1000) DEFAULT NULL,
  `subscribe_time` datetime DEFAULT NULL,
  `language` varchar(100) DEFAULT NULL,
  `unionid` varchar(45) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  `commit_time` datetime DEFAULT NULL COMMENT '�ύʱ��',
  `fill_device` varchar(45) DEFAULT NULL COMMENT '��д�豸',
  `OS` varchar(45) DEFAULT NULL COMMENT '����ϵͳ',
  `brower` varchar(45) DEFAULT NULL COMMENT '�����',
  `IP` varchar(45) DEFAULT NULL COMMENT 'IP',
  `wx_nickname` varchar(45) DEFAULT NULL COMMENT '΢��-�ǳ�',
  `wx_gender` varchar(45) DEFAULT NULL COMMENT '΢��-�Ա�',
  `wx_openid` varchar(45) DEFAULT NULL COMMENT '΢��-openid',
  `wx_header_url` varchar(45) DEFAULT NULL COMMENT '΢��-ͷ��',
  `wx_country` varchar(45) DEFAULT NULL COMMENT '΢��-����',
  `wx_city` varchar(45) DEFAULT NULL COMMENT '΢��-����',
  `contact_templ_id` int(11) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `download_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_list`
--

LOCK TABLES `contact_list` WRITE;
/*!40000 ALTER TABLE `contact_list` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_template`
--

DROP TABLE IF EXISTS `contact_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_id` bigint(11) DEFAULT NULL,
  `contact_name` varchar(45) DEFAULT NULL COMMENT '������',
  `contact_title` varchar(50) DEFAULT NULL COMMENT '����',
  `contact_descript` varchar(100) DEFAULT NULL COMMENT '����',
  `field_name` varchar(45) DEFAULT NULL COMMENT '����',
  `field_code` varchar(45) DEFAULT NULL COMMENT 'Ӣ����������Ӧcontact_list���column',
  `field_type` int(11) DEFAULT NULL,
  `selected` tinyint(4) DEFAULT '1' COMMENT 'ѡ�� 0-δѡ�� 1-ѡ��',
  `status` tinyint(4) DEFAULT '0' COMMENT '0-δ����\n1-����\n2-ɾ��',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `qrcode_url` varchar(128) DEFAULT NULL COMMENT '��ά��\n��������Ϊ���½�����url',
  `qrcode_pic` varchar(128) DEFAULT NULL COMMENT '��ά��ͼƬ�ļ�����',
  `page_views` int(11) DEFAULT '0' COMMENT '�������',
  `key_list` varchar(500) DEFAULT NULL COMMENT '�������ƣ�����\n����,�绰\n',
  `required` int(11) DEFAULT NULL COMMENT '0:�Ǳ��� 1:����',
  `ischecked` int(11) DEFAULT NULL COMMENT '0:��У�� 1:У��',
  `is_remember_import_key` tinyint(4) DEFAULT '0' COMMENT '0:û�м�סĬ�ϵĵ���key 1:�Ѿ���Ĭ�ϵĵ���key',
  `field_index` int(11) DEFAULT NULL COMMENT '��ϵ��ģ�����ʾ˳��',
  `is_shown_in_feedback` tinyint(4) DEFAULT '0' COMMENT '0:��ʾ 1:����ʾ',
  `del_status` tinyint(4) DEFAULT '0' COMMENT '0:������ʾ 1:�߼�ɾ��',
  `qrcode_shorturl` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '��ά�������\\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ϵ�˱�ģ��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_template`
--

LOCK TABLES `contact_template` WRITE;
/*!40000 ALTER TABLE `contact_template` DISABLE KEYS */;
/*!40000 ALTER TABLE `contact_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_way`
--

DROP TABLE IF EXISTS `contact_way`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_way` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ϵ��ʽ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_way`
--

LOCK TABLES `contact_way` WRITE;
/*!40000 ALTER TABLE `contact_way` DISABLE KEYS */;
INSERT INTO `contact_way` VALUES (1,'�ֻ�',0,NULL,'0000-00-00 00:00:00'),(2,'�����ʼ�',0,NULL,'0000-00-00 00:00:00'),(3,'΢�ź�',0,NULL,'0000-00-00 00:00:00');
/*!40000 ALTER TABLE `contact_way` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_way_map`
--

DROP TABLE IF EXISTS `contact_way_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_way_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_way_id` int(11) NOT NULL COMMENT 'contact_way��Ӧ��Id',
  `contact_way_name` varchar(45) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0' COMMENT '0Ϊδ��ѡ,1Ϊ��ѡ',
  `time_condition` datetime DEFAULT NULL,
  `time_condition_abbreviation` varchar(1) DEFAULT NULL COMMENT '��дH:Сʱ , D:�� , W:��',
  `time_condition_status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_way_map`
--

LOCK TABLES `contact_way_map` WRITE;
/*!40000 ALTER TABLE `contact_way_map` DISABLE KEYS */;
INSERT INTO `contact_way_map` VALUES (1,0,'ȫ��',1,NULL,'A',NULL),(2,1,'�ֻ�',0,NULL,'A',NULL),(3,2,'�����ʼ�',0,NULL,'A',NULL);
/*!40000 ALTER TABLE `contact_way_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `custom_tag`
--

DROP TABLE IF EXISTS `custom_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '��ǩ���',
  `name` varchar(40) DEFAULT NULL COMMENT '��ǩ����',
  `cover_audience_count` int(11) DEFAULT NULL COMMENT '������Ⱥ����',
  `oper` varchar(45) DEFAULT NULL COMMENT '������',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ����� 0-δɾ�� 1-ɾ��',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�Զ����ǩ��ϸ��/�/��ϵ�ˣ�';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `custom_tag`
--

LOCK TABLES `custom_tag` WRITE;
/*!40000 ALTER TABLE `custom_tag` DISABLE KEYS */;
/*!40000 ALTER TABLE `custom_tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `custom_tag_map`
--

DROP TABLE IF EXISTS `custom_tag_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_tag_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_id` int(20) NOT NULL COMMENT '��ǩ���',
  `type` tinyint(40) DEFAULT NULL COMMENT '���ͣ�0-ϸ�� 1-� 2-��ϵ�˵���  3-�ļ����� 4-�ͻ��ϴ���ǩ',
  `map_id` int(11) DEFAULT NULL COMMENT 'tag��ʶ��ϸ��/�/��ϵ�˵�ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ����ʶ 0-δɾ�� 1-ɾ��',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�Զ����ǩӳ���';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `custom_tag_map`
--

LOCK TABLES `custom_tag_map` WRITE;
/*!40000 ALTER TABLE `custom_tag_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `custom_tag_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `custom_tag_original_data_map`
--

DROP TABLE IF EXISTS `custom_tag_original_data_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_tag_original_data_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `tag_id` int(11) NOT NULL,
  `original_data_type` int(11) NOT NULL,
  `original_data_id` int(11) NOT NULL COMMENT '�ϴ����ݵ�����ID',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `custom_tag_original_data_map`
--

LOCK TABLES `custom_tag_original_data_map` WRITE;
/*!40000 ALTER TABLE `custom_tag_original_data_map` DISABLE KEYS */;
/*!40000 ALTER TABLE `custom_tag_original_data_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_arch_point`
--

DROP TABLE IF EXISTS `data_arch_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_arch_point` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `event_id` int(11) DEFAULT NULL COMMENT '�¼�ID',
  `event_name` varchar(45) DEFAULT NULL COMMENT '�¼�����',
  `click_time` datetime DEFAULT NULL COMMENT '���ʱ��',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `cookie` varchar(128) DEFAULT NULL,
  `batch_id` varchar(45) NOT NULL DEFAULT '' COMMENT '���ݵ�������iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `event_id_click_time_unq` (`event_id`,`click_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='���ͳ��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_arch_point`
--

LOCK TABLES `data_arch_point` WRITE;
/*!40000 ALTER TABLE `data_arch_point` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_arch_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_customer_tags`
--

DROP TABLE IF EXISTS `data_customer_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_customer_tags` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `tag_type` varchar(45) DEFAULT NULL,
  `tag_name` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL COMMENT '����������',
  `identify_no` varchar(19) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT '1970-06-30 16:00:00' COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_data_customer_tags_tag_type_tag_name` (`tag_type`,`tag_name`) COMMENT '�ͻ����ƺͿͻ�����Ψһ'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�ͻ���ǩ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_customer_tags`
--

LOCK TABLES `data_customer_tags` WRITE;
/*!40000 ALTER TABLE `data_customer_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_customer_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_login`
--

DROP TABLE IF EXISTS `data_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_login` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `login_type` varchar(100) DEFAULT NULL COMMENT '��¼��ʽ',
  `login_time` datetime DEFAULT NULL COMMENT '��¼ʱ��',
  `logout_time` datetime DEFAULT NULL COMMENT '�˳�ʱ��',
  `login_ip` varchar(45) DEFAULT NULL COMMENT '��¼IP',
  `login_device` varchar(45) DEFAULT NULL COMMENT '��¼�豸',
  `resolution_ratio` varchar(100) DEFAULT NULL COMMENT '�ֱ���',
  `login_url` varchar(100) DEFAULT NULL COMMENT '��¼ҳ��',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_data_login_login_time` (`login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��¼��Ϊ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_login`
--

LOCK TABLES `data_login` WRITE;
/*!40000 ALTER TABLE `data_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_member`
--

DROP TABLE IF EXISTS `data_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_member` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `member_id` varchar(45) DEFAULT NULL COMMENT '��Ա����',
  `member_points` varchar(10) DEFAULT NULL COMMENT '��Ա����',
  `member_level` varchar(45) DEFAULT NULL COMMENT '��Ա�ȼ�',
  `regist_time` date DEFAULT NULL COMMENT '����ʱ��',
  `card_amt` decimal(22,2) DEFAULT NULL COMMENT '�������',
  `expire` date DEFAULT NULL COMMENT '������',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_memberid` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Ա����¼';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_member`
--

LOCK TABLES `data_member` WRITE;
/*!40000 ALTER TABLE `data_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_option_map`
--

DROP TABLE IF EXISTS `data_option_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_option_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) NOT NULL COMMENT '���ݱ��Ӧ��Id',
  `table_name` varchar(45) NOT NULL COMMENT '���ݱ������',
  `option_status` tinyint(1) DEFAULT '0' COMMENT '���ݱ��״̬ : 0Ϊδ��ѡ,1Ϊ��ѡ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `table_name_UNIQUE` (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_option_map`
--

LOCK TABLES `data_option_map` WRITE;
/*!40000 ALTER TABLE `data_option_map` DISABLE KEYS */;
INSERT INTO `data_option_map` VALUES (1,0,'data_party',0),(2,1,'data_population',1),(3,2,'data_customer_tags',1),(4,3,'data_arch_point',1),(5,4,'data_member',1),(6,5,'data_login',1),(7,6,'data_payment',1),(8,7,'data_shopping',1);
/*!40000 ALTER TABLE `data_option_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_party`
--

DROP TABLE IF EXISTS `data_party`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_party` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id,������ID,����������ʱ������ID',
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `name` varchar(200) DEFAULT NULL COMMENT '����',
  `gender` tinyint(4) DEFAULT NULL COMMENT '�Ա�\n1-��\n2-Ů \n3-δȷ�� \n4-��ȷ��',
  `birthday` date DEFAULT NULL COMMENT '����',
  `citizenship` varchar(45) DEFAULT NULL,
  `provice` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL COMMENT 'ְҵ',
  `monthly_income` decimal(22,2) DEFAULT NULL COMMENT '������',
  `member_level` varchar(10) DEFAULT NULL COMMENT '��Ա�ȼ�',
  `member_points` varchar(11) DEFAULT NULL COMMENT '��Ա����',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `monthly_consume` decimal(22,2) DEFAULT NULL COMMENT '�¾�����',
  `last_login` datetime DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `md_type` int(11) DEFAULT NULL COMMENT '����������\\n0-������\\n8-΢��',
  `mapping_keyid` varchar(100) DEFAULT NULL COMMENT '������ID��Ӧ��keyid���磺�ֻ��š����֤�š�΢��openid��',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `email` varchar(45) DEFAULT NULL COMMENT '����',
  `qq` varchar(45) DEFAULT NULL,
  `identify_no` varchar(45) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `wxmp_id` varchar(45) DEFAULT NULL,
  `wx_code` varchar(45) DEFAULT NULL,
  `wx_uin` varchar(45) DEFAULT NULL COMMENT '΢�Ÿ��˺�',
  `wxperson_id` varchar(45) DEFAULT NULL COMMENT '΢�Ÿ��˺��·�˿Ψһ��ʶ',
  `IDFA` varchar(45) DEFAULT NULL,
  `IMEI` varchar(45) DEFAULT NULL,
  `unionid` varchar(45) DEFAULT NULL,
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `flag1` varchar(45) DEFAULT NULL COMMENT 'Ԥ���ֶ�1',
  `flag2` varchar(45) DEFAULT NULL COMMENT 'Ԥ���ֶ�2',
  `bitmap` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_key` (`mobile`,`tel`,`email`,`qq`,`identify_no`,`driving_license`,`wxmp_id`,`wx_code`,`wx_uin`,`wxperson_id`,`IDFA`,`IMEI`,`unionid`,`acct_no`,`flag1`,`flag2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�����ݱ�';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_party`
--

LOCK TABLES `data_party` WRITE;
/*!40000 ALTER TABLE `data_party` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_party` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_party_tag_rule_map`
--

DROP TABLE IF EXISTS `data_party_tag_rule_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_party_tag_rule_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rule_type` tinyint(4) DEFAULT '0' COMMENT '��������:0:һһ��Ӧ,1:���ַ�Χ��Ӧ',
  `tag_id` int(11) NOT NULL COMMENT 'tag���id',
  `md_type` int(11) DEFAULT NULL COMMENT '��ӦMongoDB��data_party���е�md_type\n1-�˿�����\n2-�ͻ���ǩ\n3-���ͳ��\n4-��Ա����¼\n5-��¼��Ϊ\n6-֧����¼\n7-�����¼\n8-΢��',
  `field_name` varchar(512) NOT NULL COMMENT 'mongo��dataParty��po����ֶ���',
  `field_value` varchar(512) DEFAULT NULL COMMENT 'mongo��dataParty��po����ֶ�ֵ',
  `min` float DEFAULT NULL COMMENT 'type=1ʱ������ֵ��null��ʾ����С',
  `max` float DEFAULT NULL COMMENT 'type=1ʱ������ֵ,null��ʾ���޴�',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=699 DEFAULT CHARSET=utf8 COMMENT='��data_party����ǩ�Ĺ���:data_party����к�tag��ر�Ķ�Ӧ��ϵ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_party_tag_rule_map`
--

LOCK TABLES `data_party_tag_rule_map` WRITE;
/*!40000 ALTER TABLE `data_party_tag_rule_map` DISABLE KEYS */;
INSERT INTO `data_party_tag_rule_map` VALUES (1,0,5869,1,'gender','1',NULL,NULL,0,NULL,'2016-08-09 03:17:01'),(2,0,5870,1,'gender','2',NULL,NULL,0,NULL,'2016-08-09 03:17:01'),(13,0,5889,1,'maritalStatus','�ѻ�',NULL,NULL,0,NULL,'2016-08-09 06:22:34'),(14,0,5890,1,'maritalStatus','δ��',NULL,NULL,0,NULL,'2016-08-09 06:22:34'),(15,0,5891,1,'maritalStatus','δ֪',NULL,NULL,0,NULL,'2016-08-09 06:22:34'),(16,0,5895,1,'bloodType','O',NULL,NULL,0,NULL,'2016-08-09 06:23:33'),(17,0,5892,1,'bloodType','A',NULL,NULL,0,NULL,'2016-08-09 06:23:33'),(18,0,5893,1,'bloodType','B',NULL,NULL,0,NULL,'2016-08-09 06:23:33'),(19,0,5894,1,'bloodType','AB',NULL,NULL,0,NULL,'2016-08-09 06:23:33'),(20,0,6068,1,'citizenship','�й�',NULL,NULL,0,NULL,'2016-08-09 06:24:12'),(21,0,6068,1,'citizenship','china',NULL,NULL,0,NULL,'2016-08-09 06:24:12'),(31,0,5869,8,'sex','1',NULL,NULL,0,NULL,'2016-08-09 06:23:33'),(32,0,5870,8,'sex','2',NULL,NULL,0,NULL,'2016-08-09 06:23:33'),(34,1,6631,0,'lastShoppingTime','dataValue != null && dataValue<=1',NULL,NULL,0,NULL,'2016-08-08 03:45:32'),(35,1,6632,0,'lastShoppingTime','dataValue != null && dataValue<=3',NULL,NULL,0,NULL,'2016-08-08 03:45:32'),(36,1,6633,0,'lastShoppingTime','dataValue != null && dataValue<=6',NULL,NULL,0,NULL,'2016-08-12 11:15:14'),(37,1,6627,0,'totalShoppingCount','dataValue != null && dataValue==1',NULL,NULL,0,NULL,'2016-08-08 03:46:37'),(38,1,6628,0,'totalShoppingCount','dataValue != null && dataValue==2',NULL,NULL,0,NULL,'2016-08-08 03:46:37'),(39,1,6629,0,'totalShoppingCount','dataValue != null && dataValue==3',NULL,NULL,0,NULL,'2016-08-08 03:46:37'),(40,1,6630,0,'totalShoppingCount','dataValue != null && dataValue>3',NULL,NULL,0,NULL,'2016-08-08 03:46:37'),(41,1,6634,0,'singleMonthShoppingCount','dataValue != null && dataValue<3',NULL,NULL,0,NULL,'2016-08-08 05:07:28'),(42,1,6635,0,'singleMonthShoppingCount','dataValue != null && dataValue>=3',NULL,NULL,0,NULL,'2016-08-08 05:07:28'),(43,1,6636,0,'totalIncome','dataValue != null && dataValue<=50',NULL,NULL,0,NULL,'2016-08-08 05:08:17'),(44,1,6637,0,'totalIncome','dataValue != null && dataValue>50&& dataValue<=100',NULL,NULL,0,NULL,'2016-08-08 05:08:17'),(45,1,6638,0,'totalIncome','dataValue != null && dataValue>100&& dataValue<=150',NULL,NULL,0,NULL,'2016-08-08 05:08:17'),(46,1,6639,0,'totalIncome','dataValue != null && dataValue>150&& dataValue<=200',NULL,NULL,0,NULL,'2016-08-08 05:08:17'),(47,1,6640,0,'totalIncome','dataValue != null && dataValue>200&& dataValue<=300',NULL,NULL,0,NULL,'2016-08-08 05:08:17'),(48,1,6641,0,'totalIncome','dataValue != null && dataValue>300',NULL,NULL,0,NULL,'2016-08-08 05:08:17'),(50,1,6642,0,'averageIncome','dataValue != null && dataValue<=50',NULL,NULL,0,NULL,'2016-08-08 05:09:55'),(51,1,6643,0,'averageIncome','dataValue != null && dataValue>100&& dataValue<=150',NULL,NULL,0,NULL,'2016-08-08 05:09:55'),(52,1,6644,0,'averageIncome','dataValue != null && dataValue != null && dataValue>50&& dataValue<=100',NULL,NULL,0,NULL,'2016-08-08 05:09:55'),(53,1,6645,0,'averageIncome','dataValue != null && dataValue>150&& dataValue<=200',NULL,NULL,0,NULL,'2016-08-08 05:09:55'),(54,1,6646,0,'averageIncome','dataValue != null && dataValue>200&& dataValue<=300',NULL,NULL,0,NULL,'2016-08-08 05:09:55'),(55,1,6647,0,'averageIncome','dataValue != null && dataValue>300',NULL,NULL,0,NULL,'2016-08-08 05:09:55'),(56,1,6652,0,'isShoppingUser','dataValue == null || dataValue!=true',NULL,NULL,0,NULL,'2016-08-19 10:02:18'),(57,1,6651,0,'isShoppingUser','dataValue != null && dataValue==true',NULL,NULL,0,NULL,'2016-08-08 05:10:23'),(58,1,6626,0,'weimob','dataValue == null || dataValue.contains(\"����\")',NULL,NULL,0,NULL,'2016-08-29 08:13:32'),(59,1,6625,0,'weimob','dataValue != null && dataValue.contains(\"����\")',NULL,NULL,0,NULL,'2016-08-29 03:13:41'),(60,1,6649,0,'orderStatus','dataValue != null && dataValue.contains(\"���׹ر�\")',NULL,NULL,0,NULL,'2016-08-29 03:13:44'),(61,1,6648,0,'orderStatus','dataValue != null && dataValue.contains(\"�������\")',NULL,NULL,0,NULL,'2016-08-29 03:13:47'),(62,1,6650,0,'orderStatus','dataValue != null && dataValue.contains(\"��֧��\")',NULL,NULL,0,NULL,'2016-08-29 03:13:51'),(63,0,5871,0,'gender','3',NULL,NULL,0,NULL,'2016-08-12 10:30:33'),(65,0,5871,0,'sex','3',NULL,NULL,0,NULL,'2016-08-20 04:55:15'),(66,0,6183,1,'provice','����',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(67,0,6184,1,'provice','���',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(68,0,6185,1,'provice','�Ϻ�',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(69,0,6186,1,'provice','����',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(70,0,6187,1,'provice','�ӱ�ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(71,0,6188,1,'provice','ɽ��ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(72,0,6189,1,'provice','̨��ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(73,0,6190,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(74,0,6191,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(75,0,6192,1,'provice','������ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(76,0,6193,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(77,0,6194,1,'provice','�㽭ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(78,0,6195,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(79,0,6196,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(80,0,6197,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(81,0,6198,1,'provice','ɽ��ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(82,0,6199,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(83,0,6200,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(84,0,6201,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(85,0,6202,1,'provice','�㶫ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(86,0,6203,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(87,0,6204,1,'provice','�Ĵ�ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(88,0,6205,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(89,0,6206,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(90,0,6207,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(91,0,6208,1,'provice','�ຣʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(92,0,6209,1,'provice','����ʡ',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(93,0,6210,1,'provice','����׳��������',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(94,0,6211,1,'provice','����������',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(95,0,6212,1,'provice','���Ļ���������',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(96,0,6213,1,'provice','�½�ά���������',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(97,0,6214,1,'provice','���ɹ�������',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(98,0,6215,1,'provice','�����ر�������',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(99,0,6216,1,'provice','����ر�������',NULL,NULL,0,NULL,'2016-08-26 06:41:19'),(129,0,6217,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(130,0,6218,1,'city','�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(131,0,6219,1,'city','�Ϻ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(132,0,6220,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(133,0,6221,1,'city','ʯ��ׯ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(134,0,6222,1,'city','��ɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(135,0,6223,1,'city','�ػʵ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(136,0,6224,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(137,0,6225,1,'city','��̨��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(138,0,6226,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(139,0,6227,1,'city','�żҿ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(140,0,6228,1,'city','�е���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(141,0,6229,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(142,0,6230,1,'city','�ȷ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(143,0,6231,1,'city','��ˮ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(144,0,6232,1,'city','̫ԭ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(145,0,6233,1,'city','��ͬ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(146,0,6234,1,'city','��Ȫ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(147,0,6235,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(148,0,6236,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(149,0,6237,1,'city','˷����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(150,0,6238,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(151,0,6239,1,'city','�˳���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(152,0,6240,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(153,0,6241,1,'city','�ٷ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(154,0,6242,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(155,0,6243,1,'city','̨����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(156,0,6244,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(157,0,6245,1,'city','��¡��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(158,0,6246,1,'city','̨����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(159,0,6247,1,'city','̨����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(160,0,6248,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(161,0,6249,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(162,0,6250,1,'city','̨����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(163,0,6251,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(164,0,6252,1,'city','��԰��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(165,0,6253,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(166,0,6254,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(167,0,6255,1,'city','̨����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(168,0,6256,1,'city','�û���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(169,0,6257,1,'city','��Ͷ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(170,0,6258,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(171,0,6259,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(172,0,6260,1,'city','̨����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(173,0,6261,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(174,0,6262,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(175,0,6263,1,'city','�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(176,0,6264,1,'city','̨����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(177,0,6265,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(178,0,6266,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(179,0,6267,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(180,0,6268,1,'city','��ɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(181,0,6269,1,'city','��˳��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(182,0,6270,1,'city','��Ϫ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(183,0,6271,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(184,0,6272,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(185,0,6273,1,'city','Ӫ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(186,0,6274,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(187,0,6275,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(188,0,6276,1,'city','�̽���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(189,0,6277,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(190,0,6278,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(191,0,6279,1,'city','��«����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(192,0,6280,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(193,0,6281,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(194,0,6282,1,'city','��ƽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(195,0,6283,1,'city','��Դ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(196,0,6284,1,'city','ͨ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(197,0,6285,1,'city','��ɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(198,0,6286,1,'city','��ԭ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(199,0,6287,1,'city','�׳���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(200,0,6288,1,'city','�ӱ߳�����������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(201,0,6289,1,'city','��������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(202,0,6290,1,'city','���������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(203,0,6291,1,'city','�׸���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(204,0,6292,1,'city','˫Ѽɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(205,0,6293,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(206,0,6294,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(207,0,6295,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(208,0,6296,1,'city','ĵ������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(209,0,6297,1,'city','��ľ˹��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(210,0,6298,1,'city','��̨����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(211,0,6299,1,'city','�ں���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(212,0,6300,1,'city','�绯��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(213,0,6301,1,'city','���˰������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(214,0,6302,1,'city','�Ͼ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(215,0,6303,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(216,0,6304,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(217,0,6305,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(218,0,6306,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(219,0,6307,1,'city','��ͨ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(220,0,6308,1,'city','���Ƹ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(221,0,6309,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(222,0,6310,1,'city','�γ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(223,0,6311,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(224,0,6312,1,'city','����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(225,0,6313,1,'city','̩����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(226,0,6314,1,'city','��Ǩ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(227,0,6315,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(228,0,6316,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(229,0,6317,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(230,0,6318,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(231,0,6319,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(232,0,6320,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(233,0,6321,1,'city','����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(234,0,6322,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(235,0,6323,1,'city','��ɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(236,0,6324,1,'city','̨����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(237,0,6325,1,'city','��ˮ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(238,0,6326,1,'city','�Ϸ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(239,0,6327,1,'city','�ߺ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(240,0,6328,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(241,0,6329,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(242,0,6330,1,'city','��ɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(243,0,6331,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(244,0,6332,1,'city','ͭ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(245,0,6333,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(246,0,6334,1,'city','��ɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(247,0,6335,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(248,0,6336,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(249,0,6337,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(250,0,6338,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(251,0,6339,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(252,0,6340,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(253,0,6341,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(254,0,6342,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(255,0,6343,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(256,0,6344,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(257,0,6345,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(258,0,6346,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(259,0,6347,1,'city','Ȫ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(260,0,6348,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(261,0,6349,1,'city','��ƽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(262,0,6350,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(263,0,6351,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(264,0,6352,1,'city','�ϲ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(265,0,6353,1,'city','��������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(266,0,6354,1,'city','Ƽ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(267,0,6355,1,'city','�Ž���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(268,0,6356,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(269,0,6357,1,'city','ӥ̶��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(270,0,6358,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(271,0,6359,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(272,0,6360,1,'city','�˴���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(273,0,6361,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(274,0,6362,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(275,0,6363,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(276,0,6364,1,'city','�ൺ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(277,0,6365,1,'city','�Ͳ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(278,0,6366,1,'city','��ׯ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(279,0,6367,1,'city','��Ӫ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(280,0,6368,1,'city','��̨��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(281,0,6369,1,'city','Ϋ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(282,0,6370,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(283,0,6371,1,'city','̩����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(284,0,6372,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(285,0,6373,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(286,0,6374,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(287,0,6375,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(288,0,6376,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(289,0,6377,1,'city','�ĳ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(290,0,6378,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(291,0,6379,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(292,0,6380,1,'city','֣����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(293,0,6381,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(294,0,6382,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(295,0,6383,1,'city','ƽ��ɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(296,0,6384,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(297,0,6385,1,'city','�ױ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(298,0,6386,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(299,0,6387,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(300,0,6388,1,'city','�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(301,0,6389,1,'city','�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(302,0,6390,1,'city','�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(303,0,6391,1,'city','����Ͽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(304,0,6392,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(305,0,6393,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(306,0,6394,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(307,0,6395,1,'city','�ܿ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(308,0,6396,1,'city','פ�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(309,0,6397,1,'city','��Դ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(310,0,6398,1,'city','�人��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(311,0,6399,1,'city','��ʯ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(312,0,6400,1,'city','ʮ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(313,0,6401,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(314,0,6402,1,'city','�˲���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(315,0,6403,1,'city','�差��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(316,0,6404,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(317,0,6405,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(318,0,6406,1,'city','Т����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(319,0,6407,1,'city','�Ƹ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(320,0,6408,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(321,0,6409,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(322,0,6410,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(323,0,6411,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(324,0,6412,1,'city','Ǳ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(325,0,6413,1,'city','��ũ������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(326,0,6414,1,'city','��ʩ����������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(327,0,6415,1,'city','��ɳ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(328,0,6416,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(329,0,6417,1,'city','��̶��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(330,0,6418,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(331,0,6419,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(332,0,6420,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(333,0,6421,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(334,0,6422,1,'city','�żҽ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(335,0,6423,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(336,0,6424,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(337,0,6425,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(338,0,6426,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(339,0,6427,1,'city','¦����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(340,0,6428,1,'city','��������������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(341,0,6429,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(342,0,6430,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(343,0,6431,1,'city','�麣��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(344,0,6432,1,'city','��ͷ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(345,0,6433,1,'city','�ع���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(346,0,6434,1,'city','��ɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(347,0,6435,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(348,0,6436,1,'city','տ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(349,0,6437,1,'city','ï����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(350,0,6438,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(351,0,6439,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(352,0,6440,1,'city','÷����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(353,0,6441,1,'city','��β��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(354,0,6442,1,'city','��Դ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(355,0,6443,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(356,0,6444,1,'city','��Զ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(357,0,6445,1,'city','��ݸ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(358,0,6446,1,'city','��ɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(359,0,6447,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(360,0,6448,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(361,0,6449,1,'city','�Ƹ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(362,0,6450,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(363,0,6451,1,'city','�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(364,0,6452,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(365,0,6453,1,'city','��ˮ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(366,0,6454,1,'city','��������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(367,0,6455,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(368,0,6456,1,'city','��Ҵ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(369,0,6457,1,'city','ƽ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(370,0,6458,1,'city','��Ȫ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(371,0,6459,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(372,0,6460,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(373,0,6461,1,'city','¤����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(374,0,6462,1,'city','���Ļ���������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(375,0,6463,1,'city','���ϲ���������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(376,0,6464,1,'city','�ɶ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(377,0,6465,1,'city','�Թ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(378,0,6466,1,'city','��֦����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(379,0,6467,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(380,0,6468,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(381,0,6469,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(382,0,6470,1,'city','��Ԫ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(383,0,6471,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(384,0,6472,1,'city','�ڽ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(385,0,6473,1,'city','��ɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(386,0,6474,1,'city','�ϳ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(387,0,6475,1,'city','üɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(388,0,6476,1,'city','�˱���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(389,0,6477,1,'city','�㰲��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(390,0,6478,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(391,0,6479,1,'city','�Ű���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(392,0,6480,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(393,0,6481,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(394,0,6482,1,'city','���Ӳ���Ǽ��������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(395,0,6483,1,'city','���β���������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(396,0,6484,1,'city','��ɽ����������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(397,0,6485,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(398,0,6486,1,'city','�ൺ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(399,0,6487,1,'city','�Ͳ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(400,0,6488,1,'city','��ׯ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(401,0,6489,1,'city','��Ӫ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(402,0,6490,1,'city','��̨��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(403,0,6491,1,'city','Ϋ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(404,0,6492,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(405,0,6493,1,'city','̩����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(406,0,6494,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(407,0,6495,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(408,0,6496,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(409,0,6497,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(410,0,6498,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(411,0,6499,1,'city','�ĳ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(412,0,6500,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(413,0,6501,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(414,0,6502,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(415,0,6503,1,'city','����ˮ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(416,0,6504,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(417,0,6505,1,'city','��˳��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(418,0,6506,1,'city','ͭ�ʵ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(419,0,6507,1,'city','�Ͻڵ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(420,0,6508,1,'city','ǭ���ϲ���������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(421,0,6509,1,'city','ǭ�������嶱��������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(422,0,6510,1,'city','ǭ�ϲ���������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(423,0,6511,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(424,0,6512,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(425,0,6513,1,'city','��ָɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(426,0,6514,1,'city','����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(427,0,6515,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(428,0,6516,1,'city','�Ĳ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(429,0,6517,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(430,0,6518,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(431,0,6519,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(432,0,6520,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(433,0,6521,1,'city','�Ͳ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(434,0,6522,1,'city','�ٸ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(435,0,6523,1,'city','��ɳ����������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(436,0,6524,1,'city','��������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(437,0,6525,1,'city','�ֶ�����������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(438,0,6526,1,'city','��ˮ����������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(439,0,6527,1,'city','��ͤ��������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(440,0,6528,1,'city','������������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(441,0,6529,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(442,0,6530,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(443,0,6531,1,'city','��Ϫ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(444,0,6532,1,'city','��ɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(445,0,6533,1,'city','��ͨ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(446,0,6534,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(447,0,6535,1,'city','˼é��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(448,0,6536,1,'city','�ٲ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(449,0,6537,1,'city','��ɽ׳������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(450,0,6538,1,'city','��ӹ���������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(451,0,6539,1,'city','��˫���ɴ���������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(452,0,6540,1,'city','��������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(453,0,6541,1,'city','�������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(454,0,6542,1,'city','�º���徰����������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(455,0,6543,1,'city','ŭ��������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(456,0,6544,1,'city','�������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(457,0,6545,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(458,0,6546,1,'city','��������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(459,0,6547,1,'city','��������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(460,0,6548,1,'city','���ϲ���������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(461,0,6549,1,'city','���ϲ���������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(462,0,6550,1,'city','�������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(463,0,6551,1,'city','��������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(464,0,6552,1,'city','�����ɹ������������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(465,0,6553,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(466,0,6554,1,'city','ͭ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(467,0,6555,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(468,0,6556,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(469,0,6557,1,'city','μ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(470,0,6558,1,'city','�Ӱ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(471,0,6559,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(472,0,6560,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(473,0,6561,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(474,0,6562,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(475,0,6563,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(476,0,6564,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(477,0,6565,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(478,0,6566,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(479,0,6567,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(480,0,6568,1,'city','���Ǹ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(481,0,6569,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(482,0,6570,1,'city','�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(483,0,6571,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(484,0,6572,1,'city','��ɫ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(485,0,6573,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(486,0,6574,1,'city','�ӳ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(487,0,6575,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(488,0,6576,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(489,0,6577,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(490,0,6578,1,'city','��������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(491,0,6579,1,'city','��������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(492,0,6580,1,'city','ɽ�ϵ���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(493,0,6581,1,'city','�տ������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(494,0,6582,1,'city','�������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(495,0,6583,1,'city','��֥����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(496,0,6584,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(497,0,6585,1,'city','ʯ��ɽ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(498,0,6586,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(499,0,6587,1,'city','��ԭ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(500,0,6588,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(501,0,6589,1,'city','��³ľ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(502,0,6590,1,'city','����������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(503,0,6591,1,'city','ʯ������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(504,0,6592,1,'city','��������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(505,0,6593,1,'city','ͼľ�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(506,0,6594,1,'city','�������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(507,0,6595,1,'city','��³����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(508,0,6596,1,'city','��������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(509,0,6597,1,'city','��ʲ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(510,0,6598,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(511,0,6599,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(512,0,6600,1,'city','��ͼʲ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(513,0,6601,1,'city','�������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(514,0,6602,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(515,0,6603,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(516,0,6604,1,'city','��Ȫ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(517,0,6605,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(518,0,6606,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(519,0,6607,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(520,0,6608,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(521,0,6609,1,'city','������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(522,0,6610,1,'city','����̩��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(523,0,6611,1,'city','���ͺ�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(524,0,6612,1,'city','��ͷ��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(525,0,6613,1,'city','�ں���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(526,0,6614,1,'city','�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(527,0,6615,1,'city','ͨ����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(528,0,6616,1,'city','������˹��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(529,0,6617,1,'city','���ױ�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(530,0,6618,1,'city','�����׶���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(531,0,6619,1,'city','�����첼��',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(532,0,6620,1,'city','���ֹ�����',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(533,0,6621,1,'city','�˰���',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(534,0,6622,1,'city','��������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(535,0,6623,1,'city','�����ر�������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(536,0,6624,1,'city','����ر�������',NULL,NULL,0,NULL,'2016-08-26 06:47:02'),(640,0,6068,1,'citizenship','�й�',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(641,0,6069,1,'citizenship','����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(642,0,6070,1,'citizenship','�ձ�',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(643,0,6071,1,'citizenship','Ӣ��',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(644,0,6072,1,'citizenship','�¹�',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(645,0,6073,1,'citizenship','����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(646,0,6074,1,'citizenship','���',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(647,0,6075,1,'citizenship','�����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(648,0,6076,1,'citizenship','���ô�',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(649,0,6077,1,'citizenship','�Ĵ�����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(650,0,6078,1,'citizenship','����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(651,0,6079,1,'citizenship','���ɱ�',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(652,0,6080,1,'citizenship','��ɫ��',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(653,0,6081,1,'citizenship','������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(654,0,6082,1,'citizenship','̨��',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(655,0,6083,1,'citizenship','����ʱ',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(656,0,6084,1,'citizenship','�¼���',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(657,0,6085,1,'citizenship','����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(658,0,6086,1,'citizenship','����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(659,0,6087,1,'citizenship','����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(660,0,6088,1,'citizenship','��ʿ',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(661,0,6089,1,'citizenship','���',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(662,0,6090,1,'citizenship','̩��',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(663,0,6091,1,'citizenship','������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(664,0,6092,1,'citizenship','����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(665,0,6093,1,'citizenship','������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(666,0,6094,1,'citizenship','����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(667,0,6095,1,'citizenship','����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(668,0,6096,1,'citizenship','�µ���',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(669,0,6097,1,'citizenship','Խ��',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(670,0,6098,1,'citizenship','˹������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(671,0,6099,1,'citizenship','��������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(672,0,6100,1,'citizenship','������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(673,0,6101,1,'citizenship','�Ű�',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(674,0,6102,1,'citizenship','ֱ������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(675,0,6103,1,'citizenship','�ڿ���',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(676,0,6104,1,'citizenship','����͢',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(677,0,6105,1,'citizenship','����˹',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(678,0,6106,1,'citizenship','ϣ��',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(679,0,6107,1,'citizenship','Ų��',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(680,0,6108,1,'citizenship','�Ϸ�',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(681,0,6109,1,'citizenship','ī����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(682,0,6110,1,'citizenship','������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(683,0,6111,1,'citizenship','ӡ��������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(684,0,6112,1,'citizenship','ӡ��',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(685,0,6113,1,'citizenship','������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(686,0,6114,1,'citizenship','������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(687,0,6115,1,'citizenship','�ݿ�',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(688,0,6116,1,'citizenship','����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(689,0,6117,1,'citizenship','����·˹',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(690,0,6118,1,'citizenship','������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(691,0,6119,1,'citizenship','��������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(692,0,6120,1,'citizenship','�ո���',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(693,0,6121,1,'citizenship','��������',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(694,0,6122,1,'citizenship','����կ',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(695,0,6123,1,'citizenship','˹�工��',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(696,0,6124,1,'citizenship','����ά��',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(697,0,6125,1,'citizenship','����',NULL,NULL,0,NULL,'2016-08-26 07:00:32'),(698,0,6126,1,'citizenship','����',NULL,NULL,0,NULL,'2016-08-26 07:00:32');
/*!40000 ALTER TABLE `data_party_tag_rule_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_payment`
--

DROP TABLE IF EXISTS `data_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_payment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `pay_channel` varchar(10) DEFAULT NULL COMMENT '֧������',
  `pay_acct` varchar(45) DEFAULT NULL COMMENT '֧���˺�',
  `pay_serial` varchar(45) DEFAULT NULL COMMENT '֧����ˮ',
  `trans_serial` varchar(45) DEFAULT NULL COMMENT 'ҵ����ˮ',
  `order_no` varchar(45) DEFAULT NULL COMMENT '�������',
  `product_name` varchar(100) DEFAULT NULL COMMENT '��Ʒ����',
  `create_time` datetime DEFAULT NULL,
  `complete_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `pay_status` varchar(45) DEFAULT NULL COMMENT '֧��״̬',
  `counter_acct` varchar(45) DEFAULT NULL COMMENT '�Է��˺�',
  `income_amt` decimal(22,2) DEFAULT NULL COMMENT '������',
  `paid_amt` decimal(22,2) DEFAULT NULL COMMENT '֧�����',
  `acct_amt` decimal(22,2) DEFAULT NULL COMMENT '�˺����',
  `comments` varchar(100) DEFAULT NULL COMMENT '��ע',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_data_payment_pay_serial` (`pay_serial`) COMMENT '��ˮ�˺�',
  UNIQUE KEY `idx_data_payment_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='֧����¼';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_payment`
--

LOCK TABLES `data_payment` WRITE;
/*!40000 ALTER TABLE `data_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_personal`
--

DROP TABLE IF EXISTS `data_personal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_personal` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `task_id` int(10) NOT NULL COMMENT '���ڵ�����Id',
  `open_id` int(15) DEFAULT NULL COMMENT 'openid',
  `personal_name` varchar(20) DEFAULT NULL COMMENT '���˺�����',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '�ǳ�',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT 'ɾ�����',
  `delete_time` datetime DEFAULT NULL COMMENT 'ɾ��ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='΢�Ÿ��˺�����';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_personal`
--

LOCK TABLES `data_personal` WRITE;
/*!40000 ALTER TABLE `data_personal` DISABLE KEYS */;
INSERT INTO `data_personal` VALUES (3,0,NULL,NULL,NULL,NULL,NULL),(4,0,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `data_personal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_population`
--

DROP TABLE IF EXISTS `data_population`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_population` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `name` varchar(100) DEFAULT NULL COMMENT '����',
  `gender` tinyint(1) DEFAULT NULL COMMENT '�Ա�\n1-��\n2-Ů \n3-δȷ�� \n4-��ȷ��',
  `birthday` date DEFAULT NULL COMMENT '����������',
  `provice` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL COMMENT 'ְҵ',
  `monthly_income` decimal(22,2) DEFAULT NULL COMMENT '������',
  `monthly_consume` decimal(22,2) DEFAULT NULL COMMENT '�¾�����',
  `marital_status` varchar(45) DEFAULT NULL COMMENT '����״��',
  `education` varchar(45) DEFAULT NULL COMMENT '�����̶�',
  `employment` varchar(45) DEFAULT NULL COMMENT '��ҵ���',
  `nationality` varchar(45) DEFAULT NULL COMMENT '����',
  `blood_type` varchar(45) DEFAULT NULL COMMENT 'Ѫ��',
  `citizenship` varchar(45) DEFAULT NULL COMMENT '����',
  `iq` int(11) DEFAULT NULL,
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  `nickname` varchar(128) DEFAULT NULL,
  `head_img_url` varchar(1000) DEFAULT NULL,
  `subscribe_time` datetime DEFAULT NULL,
  `language` varchar(100) DEFAULT NULL,
  `unionid` varchar(45) DEFAULT NULL,
  `remark` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�˿�����';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_population`
--

LOCK TABLES `data_population` WRITE;
/*!40000 ALTER TABLE `data_population` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_population` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_public`
--

DROP TABLE IF EXISTS `data_public`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_public` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `task_id` int(10) NOT NULL COMMENT '���ڵ�����Id',
  `open_id` int(15) unsigned DEFAULT NULL COMMENT 'openid',
  `public_name` varchar(20) DEFAULT NULL COMMENT '���ں�����',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '�ǳ�',
  `icon_url` varchar(45) DEFAULT NULL COMMENT 'ͷ��url',
  `gender` tinyint(1) DEFAULT NULL COMMENT '�Ա�\n1-��\n2-Ů \n3-δȷ�� \n4-��ȷ��',
  `area` varchar(20) DEFAULT NULL COMMENT '����',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT 'ɾ�����',
  `delete_time` datetime DEFAULT NULL COMMENT 'ɾ��ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='΢�Ź��ں�����';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_public`
--

LOCK TABLES `data_public` WRITE;
/*!40000 ALTER TABLE `data_public` DISABLE KEYS */;
INSERT INTO `data_public` VALUES (3,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(4,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(5,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(6,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(7,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `data_public` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `data_shopping`
--

DROP TABLE IF EXISTS `data_shopping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_shopping` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `channel_type` varchar(45) DEFAULT NULL COMMENT '������������',
  `channel_id` varchar(10) DEFAULT NULL COMMENT '��������ID',
  `channel_name` varchar(45) DEFAULT NULL COMMENT '������������',
  `pay_type` varchar(45) DEFAULT NULL COMMENT '֧����ʽ',
  `trans_serial` varchar(45) DEFAULT NULL COMMENT 'ҵ����ˮ',
  `order_no` varchar(100) DEFAULT NULL COMMENT '��Ʒ������',
  `trans_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `product_id` varchar(100) DEFAULT NULL COMMENT '��Ʒ���',
  `specification` varchar(45) DEFAULT NULL COMMENT '��Ʒ���',
  `color` varchar(45) DEFAULT NULL COMMENT '��ɫ',
  `discount_type` varchar(45) DEFAULT NULL COMMENT '�ۿ�����',
  `discount_amt` decimal(22,2) DEFAULT NULL COMMENT '�ۿ۽��',
  `price` decimal(22,2) DEFAULT NULL COMMENT '����',
  `amount` int(11) DEFAULT NULL COMMENT '����',
  `inventory` int(11) DEFAULT NULL COMMENT '�����',
  `brand_id` varchar(45) DEFAULT NULL COMMENT 'Ʒ��iD',
  `brand_name` varchar(45) DEFAULT NULL COMMENT 'Ʒ������',
  `class1_id` varchar(45) DEFAULT NULL COMMENT 'һ��Ʒ��ID',
  `class1_name` varchar(45) DEFAULT NULL COMMENT 'һ��Ʒ������',
  `class2_id` varchar(45) DEFAULT NULL COMMENT '����Ʒ��ID',
  `class2_name` varchar(45) DEFAULT NULL COMMENT '����Ʒ������',
  `class3_id` varchar(45) DEFAULT NULL COMMENT '����Ʒ��ID',
  `class3_name` varchar(45) DEFAULT NULL COMMENT '����Ʒ������',
  `class4_id` varchar(45) DEFAULT NULL COMMENT '�ļ�Ʒ��ID',
  `class4_name` varchar(45) DEFAULT NULL COMMENT '����Ʒ������',
  `sale_assist_id` varchar(45) DEFAULT NULL COMMENT '����Ա���',
  `sale_assistance` varchar(45) DEFAULT NULL COMMENT '����Ա',
  `settlement_clerk_id` varchar(45) DEFAULT NULL COMMENT '������Ա���',
  `settlement_clerk` varchar(45) DEFAULT NULL COMMENT '������Ա',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `order_status` varchar(10) DEFAULT NULL COMMENT '����״̬',
  `delivery_way` varchar(10) DEFAULT NULL COMMENT '���ͷ�ʽ',
  `logistics_status` varchar(10) DEFAULT NULL COMMENT '����״̬',
  `shipping_fee` decimal(22,2) DEFAULT NULL COMMENT '�˷�',
  `shipping_way` varchar(10) DEFAULT NULL COMMENT '���ͷ�ʽ',
  `express_company` varchar(100) DEFAULT NULL COMMENT '��ݹ�˾',
  `express_order` varchar(50) DEFAULT NULL COMMENT '��ݵ���',
  `consignee` varchar(50) DEFAULT NULL COMMENT '�ջ���',
  `consignee_tel` varchar(20) DEFAULT NULL COMMENT '�ջ��˵绰',
  `consignee_addr` varchar(100) DEFAULT NULL COMMENT '�ջ���ַ',
  `buyer_comment` varchar(200) DEFAULT NULL COMMENT '��ұ�ע',
  `wxmp_id` varchar(128) DEFAULT NULL COMMENT '���ںű�ʶ',
  `wx_code` varchar(128) DEFAULT NULL COMMENT 'openid',
  `product_name` varchar(200) DEFAULT NULL COMMENT '��Ʒ����',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_data_shopping_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�����¼';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `data_shopping`
--

LOCK TABLES `data_shopping` WRITE;
/*!40000 ALTER TABLE `data_shopping` DISABLE KEYS */;
/*!40000 ALTER TABLE `data_shopping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `datareport_columns`
--

DROP TABLE IF EXISTS `datareport_columns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `datareport_columns` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `field_name` varchar(45) NOT NULL COMMENT '����',
  `field_code` varchar(45) NOT NULL COMMENT 'Ӣ������',
  `field_order` int(11) DEFAULT NULL COMMENT '�е�˳��',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����,0:δɾ��,1:��ɾ��',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='��������������������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `datareport_columns`
--

LOCK TABLES `datareport_columns` WRITE;
/*!40000 ALTER TABLE `datareport_columns` DISABLE KEYS */;
INSERT INTO `datareport_columns` VALUES (1,'��ʼʱ��','import_start_time',1,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(2,'���ʱ��','import_end_time',2,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(3,'����Դ','source',3,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(4,'�Ϸ���¼','legal_rows',4,0,'2016-06-12 16:15:42','2016-06-12 08:15:42'),(5,'�Ƿ���¼','illegal_rows',5,0,'2016-06-12 16:15:42','2016-06-12 08:15:53'),(6,'������¼','modify_rows',6,0,'2016-06-12 16:15:42','2016-06-12 08:15:42');
/*!40000 ALTER TABLE `datareport_columns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `default_contact_template`
--

DROP TABLE IF EXISTS `default_contact_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `default_contact_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field_name` varchar(100) DEFAULT NULL,
  `field_code` varchar(100) DEFAULT NULL,
  `is_selected` tinyint(4) DEFAULT NULL COMMENT '0:δѡ�� 1:ѡ��',
  `is_required` tinyint(4) DEFAULT NULL COMMENT '0:�Ǳ��� 1:����',
  `is_checked` tinyint(4) DEFAULT NULL COMMENT '0:����ҪУ�� 1:��ҪУ��',
  `default_shown_seq` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  `is_primary_key` tinyint(4) DEFAULT NULL COMMENT '0:�������� 1:������',
  `field_type` int(11) DEFAULT '0' COMMENT '0:����� 1:������ 2:����ѡ�� 3:�������� 4:�ֻ�(��֤��)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `default_contact_template`
--

LOCK TABLES `default_contact_template` WRITE;
/*!40000 ALTER TABLE `default_contact_template` DISABLE KEYS */;
INSERT INTO `default_contact_template` VALUES (1,'����','name',1,1,1,1,0,1,0),(2,'�Ա�','gender',1,1,0,2,0,0,1),(3,'����','birthday',1,1,0,3,0,0,2),(4,'�ֻ�����','mobile',1,1,1,4,0,1,0),(5,'�̻�����','tel',1,1,1,5,0,1,0),(6,'�����ַ','email',1,1,1,6,0,1,0),(7,'QQ��','qq',1,1,0,7,0,1,0),(8,'Ѫ��','blood_type',0,0,0,NULL,0,0,1),(9,'����','nationality',0,0,0,NULL,0,0,0),(10,'����','citizenship',0,0,0,NULL,0,0,0),(11,'����','city',0,0,0,NULL,0,0,3),(12,'������','monthly_income',0,0,0,NULL,0,0,0),(13,'������','monthly_consume',0,0,0,NULL,0,0,0),(14,'ְҵ','job',0,0,0,NULL,0,0,0),(15,'�����̶�','education',0,0,0,NULL,0,0,1),(16,'��ҵ���','employment',0,0,0,NULL,0,0,1),(17,'IQ','iq',0,0,1,NULL,0,0,0),(18,'���֤��','identify_no',0,0,1,NULL,0,1,0),(19,'��ʻ֤��','driving_license',0,0,1,NULL,0,1,0),(21,'���','marital_status',0,0,0,NULL,0,0,1);
/*!40000 ALTER TABLE `default_contact_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `illegal_data`
--

DROP TABLE IF EXISTS `illegal_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `illegal_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batch_id` bigint(20) NOT NULL,
  `type` char(10) DEFAULT NULL COMMENT '�������ͣ� app,pos\n',
  `origin_data` varchar(10000) DEFAULT NULL COMMENT 'ԭʼ�ı�����',
  `create_time` datetime DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `head_type` varchar(1) DEFAULT NULL COMMENT '�Ƿ�Ƿ����ݣ�0���ǣ�1��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�Ƿ����ݼ�¼��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `illegal_data`
--

LOCK TABLES `illegal_data` WRITE;
/*!40000 ALTER TABLE `illegal_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `illegal_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `img_text_asset`
--

DROP TABLE IF EXISTS `img_text_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `img_text_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `name` varchar(200) DEFAULT NULL COMMENT 'ͼ������',
  `type` tinyint(4) DEFAULT '0' COMMENT '0:΢���ʲ�,1:h5�ʲ�',
  `owner_name` varchar(200) DEFAULT NULL COMMENT '����������:΢�ź�/h5ƽ̨��',
  `pc_preview_url` varchar(1000) DEFAULT NULL COMMENT '����Ԥ��url',
  `mobile_preview_url` varchar(1000) DEFAULT NULL COMMENT '�ֻ�Ԥ��url',
  `imgfile_url` varchar(1000) DEFAULT NULL COMMENT '����ͼurl',
  `material_id` varchar(200) DEFAULT NULL COMMENT 'ͼ���ز��ڴ���H5���ݿ��е�����',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pub_id` varchar(45) DEFAULT NULL COMMENT 'ͼ���������ں�id',
  `pub_name` varchar(200) DEFAULT NULL COMMENT 'ͼ���������ں�����',
  `wechat_status` tinyint(4) DEFAULT NULL COMMENT '΢��ͼ��״̬  1:����΢�ŷ�������  0:��΢�ŷ������ϱ�ɾ����',
  `show_cover_pic` tinyint(4) DEFAULT NULL COMMENT '�Ƿ���ʾ���� 0:����ʾ  1:��ʾ',
  `thumb_ready` tinyint(4) DEFAULT NULL COMMENT '΢��ͼ���Ƿ��������: 0�����Ѿ�������� 1������δ�������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ͼ���ʲ���';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `img_text_asset`
--

LOCK TABLES `img_text_asset` WRITE;
/*!40000 ALTER TABLE `img_text_asset` DISABLE KEYS */;
/*!40000 ALTER TABLE `img_text_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_data_history`
--

DROP TABLE IF EXISTS `import_data_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `import_data_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '��������',
  `import_start_time` timestamp NULL DEFAULT NULL,
  `import_end_time` timestamp NULL DEFAULT NULL,
  `total_rows` int(11) DEFAULT NULL COMMENT '���������ݽ���������',
  `legal_rows` int(11) DEFAULT NULL COMMENT '���������ݽ���Ϸ���������',
  `success` tinyint(4) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL COMMENT '����Դ����',
  `source_filename` varchar(128) DEFAULT NULL COMMENT '����Դ�ļ�����',
  `download_filename` varchar(128) DEFAULT NULL,
  `email_rows` int(11) DEFAULT NULL COMMENT '�Ƿ�email����',
  `mobile_rows` int(11) DEFAULT NULL COMMENT '�Ƿ��ֻ�������',
  `duplicate_rows` int(11) DEFAULT NULL COMMENT '�ظ�����',
  `illegal_rows` int(11) DEFAULT NULL COMMENT '�Ƿ���¼������',
  `summary` varchar(512) DEFAULT NULL,
  `no_recognize_property` varchar(1024) DEFAULT NULL,
  `file_unique` varchar(50) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����,0:δɾ��,1:��ɾ��',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `file_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_file_unique` (`file_unique`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='���ݽ�����������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_data_history`
--

LOCK TABLES `import_data_history` WRITE;
/*!40000 ALTER TABLE `import_data_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `import_data_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_data_modify_log`
--

DROP TABLE IF EXISTS `import_data_modify_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `import_data_modify_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `import_data_id` bigint(20) NOT NULL COMMENT 'import_data_history. id',
  `handle_time` timestamp NOT NULL DEFAULT '1970-06-30 16:00:00',
  `total_rows` int(11) DEFAULT NULL,
  `modify_rows` int(11) DEFAULT NULL,
  `illegal_rows` int(11) DEFAULT NULL,
  `modify_filename` varchar(128) DEFAULT NULL COMMENT '�ϴ������ļ�����',
  `success` tinyint(4) DEFAULT NULL,
  `modify_download_filename` varchar(128) DEFAULT NULL COMMENT '�ϴ������ļ�����',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_data_modify_log`
--

LOCK TABLES `import_data_modify_log` WRITE;
/*!40000 ALTER TABLE `import_data_modify_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `import_data_modify_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `import_template`
--

DROP TABLE IF EXISTS `import_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `import_template` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `templ_type` int(5) NOT NULL COMMENT 'ģ������\n0-������\n1-�˿�����\n2-�ͻ���ǩ\n3-���ͳ��\n4-��Ա����¼\n5-��¼��Ϊ\n6-֧����¼\n7-�����¼',
  `templ_name` varchar(45) NOT NULL DEFAULT '' COMMENT 'ģ������',
  `field_name` varchar(45) NOT NULL COMMENT '����',
  `field_code` varchar(45) NOT NULL COMMENT 'Ӣ����������Ӧdata_app�ȱ��column',
  `selected` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'ѡ�� 0-δѡ�� 1-ѡ��',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_templ_name_field_name` (`field_name`,`templ_name`)
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=utf8 COMMENT='����ģ���';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `import_template`
--

LOCK TABLES `import_template` WRITE;
/*!40000 ALTER TABLE `import_template` DISABLE KEYS */;
INSERT INTO `import_template` VALUES (1,0,'������','�ֻ���','mobile',1,0,NULL,'2016-07-22 09:54:00'),(2,0,'������','����','name',1,0,NULL,'2016-07-22 09:54:00'),(3,0,'������','�Ա�','gender',1,0,NULL,'2016-07-22 09:54:00'),(4,0,'������','����������','birthday',1,0,NULL,'2016-07-18 09:12:11'),(5,0,'������','ʡ','provice',1,0,NULL,'2016-07-18 09:30:10'),(6,0,'������','��','city',1,0,NULL,'2016-07-22 09:54:00'),(7,0,'������','ְҵ','job',1,0,NULL,'2016-07-22 09:54:00'),(8,0,'������','������','monthly_income',1,0,NULL,'2016-07-22 09:54:00'),(9,0,'������','��Ա�ȼ�','member_level',1,0,NULL,'2016-07-18 09:41:36'),(10,0,'������','��Ա����','member_points',1,0,NULL,'2016-07-22 09:54:00'),(11,0,'������','������Դ','source',1,0,NULL,'2016-07-22 09:54:00'),(12,0,'������','�¾�����','monthly_consume',1,0,NULL,'2016-07-22 09:54:00'),(13,0,'������','����¼ʱ��','last_login',0,0,NULL,'2016-07-22 07:06:06'),(14,0,'������','ɾ�����','status',0,0,NULL,'2016-07-22 07:06:06'),(15,0,'������','����ʱ��','create_time',0,0,NULL,'2016-07-22 09:54:00'),(16,0,'������','ɾ��ʱ��','update_time',0,0,NULL,'2016-07-19 08:27:38'),(17,1,'�˿�����','�ֻ���','mobile',0,0,NULL,'2016-08-10 07:12:24'),(18,1,'�˿�����','����','name',0,0,NULL,'2016-08-10 07:12:24'),(19,1,'�˿�����','�Ա�','gender',0,0,NULL,'2016-08-10 07:12:24'),(20,1,'�˿�����','����','birthday',0,0,NULL,'2016-08-10 07:12:24'),(21,1,'�˿�����','ʡ','provice',1,0,NULL,'2016-07-08 11:48:20'),(22,1,'�˿�����','��','city',1,0,NULL,'2016-07-08 11:48:20'),(23,1,'�˿�����','ְҵ','job',0,0,NULL,'2016-08-10 07:12:24'),(24,1,'�˿�����','������','monthly_income',0,0,NULL,'2016-08-10 07:12:24'),(25,1,'�˿�����','������','monthly_consume',0,0,NULL,'2016-08-10 07:12:24'),(26,1,'�˿�����','���','marital_status',0,0,NULL,'2016-08-10 07:12:24'),(27,1,'�˿�����','�����̶�','education',0,0,NULL,'2016-08-10 07:12:24'),(28,1,'�˿�����','��ҵ���','employment',0,0,NULL,'2016-08-10 07:12:24'),(29,1,'�˿�����','����','nationality',0,0,NULL,'2016-08-10 07:12:24'),(30,1,'�˿�����','Ѫ��','blood_type',0,0,NULL,'2016-08-10 07:12:24'),(31,1,'�˿�����','����','citizenship',1,0,NULL,'2016-07-19 08:28:28'),(32,1,'�˿�����','IQ','iq',0,0,NULL,'2016-08-10 07:12:24'),(33,1,'�˿�����','���֤��','identify_no',0,0,NULL,'2016-08-10 07:12:24'),(34,1,'�˿�����','��ʻ֤��','driving_license',0,0,NULL,'2016-08-10 07:12:25'),(35,1,'�˿�����','����','email',0,0,NULL,'2016-08-10 07:12:25'),(36,1,'�˿�����','�̻�����','tel',0,0,NULL,'2016-08-10 07:12:25'),(37,1,'�˿�����','QQ','qq',0,0,NULL,'2016-08-10 07:12:25'),(38,1,'�˿�����','˽���˺�����','acct_type',0,0,NULL,'2016-08-10 07:12:25'),(39,1,'�˿�����','˽���˺�','acct_no',0,0,NULL,'2016-08-10 07:12:25'),(40,1,'�˿�����','IDFA','idfa',0,0,NULL,'2016-08-05 08:34:09'),(41,1,'�˿�����','IMEI','imei',0,0,NULL,'2016-08-05 08:34:18'),(42,1,'�˿�����','unionid','udid',0,0,NULL,'2016-08-05 08:34:36'),(43,1,'�˿�����','MAC','phone_mac',0,0,NULL,'2016-08-05 08:34:39'),(44,1,'�˿�����','ɾ�����','status',0,0,NULL,'2016-06-23 06:18:27'),(45,1,'�˿�����','����ʱ��','create_time',0,0,NULL,'2016-06-23 06:18:27'),(46,1,'�˿�����','ɾ��ʱ��','update_time',0,0,NULL,'2016-06-23 06:18:27'),(47,1,'�˿�����','������Դ','source',0,0,NULL,'2016-06-23 06:18:27'),(48,2,'�ͻ���ǩ','��ǩ����','tag_type',1,0,NULL,'2016-06-13 09:47:50'),(49,2,'�ͻ���ǩ','��ǩ����','tag_name',1,0,NULL,'2016-07-01 11:21:42'),(50,2,'�ͻ���ǩ','����','birthday',0,0,NULL,'2016-08-08 08:39:53'),(51,2,'�ͻ���ǩ','���֤��','identify_no',0,0,NULL,'2016-07-07 11:22:35'),(52,2,'�ͻ���ǩ','��ʻ֤��','driving_license',1,0,NULL,'2016-07-07 11:31:13'),(53,2,'�ͻ���ǩ','����','email',1,0,NULL,'2016-08-05 08:44:48'),(54,2,'�ͻ���ǩ','�ֻ���','mobile',1,0,NULL,'2016-07-08 10:56:30'),(55,2,'�ͻ���ǩ','˽���˺�����','acct_type',0,0,NULL,'2016-07-07 11:22:35'),(56,2,'�ͻ���ǩ','�̻�����','tel',0,0,NULL,'2016-08-05 08:56:33'),(57,2,'�ͻ���ǩ','QQ','qq',0,0,NULL,'2016-06-30 06:42:40'),(58,2,'�ͻ���ǩ','˽���˺�','acct_no',0,0,NULL,'2016-06-28 07:09:34'),(59,2,'�ͻ���ǩ','IDFA','idfa',0,0,NULL,'2016-08-05 08:56:53'),(60,2,'�ͻ���ǩ','IMEI','imei',0,0,NULL,'2016-08-05 08:56:59'),(61,2,'�ͻ���ǩ','unionid','udid',0,0,NULL,'2016-08-05 08:57:09'),(62,2,'�ͻ���ǩ','MAC','phone_mac',0,0,NULL,'2016-08-05 08:57:13'),(63,2,'�ͻ���ǩ','ɾ�����','status',0,0,NULL,'2016-06-28 07:09:34'),(64,2,'�ͻ���ǩ','����ʱ��','create_time',0,0,NULL,'2016-06-28 07:09:34'),(65,2,'�ͻ���ǩ','ɾ��ʱ��','update_time',0,0,NULL,'2016-06-28 07:09:34'),(66,2,'�ͻ���ǩ','������Դ','source',0,0,NULL,'2016-06-28 07:09:34'),(67,3,'���ͳ��','�¼�ID','event_id',1,0,NULL,'2016-06-24 11:50:41'),(68,3,'���ͳ��','�¼�����','event_name',1,0,NULL,'2016-06-24 11:50:41'),(69,3,'���ͳ��','���ʱ��','click_time',1,0,NULL,'2016-06-24 11:50:41'),(70,3,'���ͳ��','���֤��','identify_no',1,0,NULL,'2016-06-13 09:47:51'),(71,3,'���ͳ��','��ʻ֤��','driving_license',1,0,NULL,'2016-06-13 09:47:51'),(72,3,'���ͳ��','cookie','cookie',1,0,NULL,'2016-06-13 09:47:51'),(73,3,'���ͳ��','����','email',1,0,NULL,'2016-08-05 09:50:01'),(74,3,'���ͳ��','�ֻ���','mobile',1,0,NULL,'2016-06-13 09:47:51'),(75,3,'���ͳ��','˽���˺�����','acct_type',0,0,NULL,'2016-06-23 02:27:02'),(76,3,'���ͳ��','�̻�����','tel',0,0,NULL,'2016-08-05 09:50:34'),(77,3,'���ͳ��','QQ','qq',0,0,NULL,'2016-06-23 02:27:02'),(78,3,'���ͳ��','˽���˺�','acct_no',0,0,NULL,'2016-07-08 09:55:51'),(79,3,'���ͳ��','iphone�ֻ�ʶ����','idfa',0,0,NULL,'2016-07-08 09:55:51'),(80,3,'���ͳ��','�ֻ�ʶ����','imei',0,0,NULL,'2016-07-08 09:55:51'),(81,3,'���ͳ��','unionid','udid',0,0,NULL,'2016-08-05 09:50:56'),(82,3,'���ͳ��','�ֻ�����MAC','phone_mac',0,0,NULL,'2016-06-23 02:27:02'),(83,3,'���ͳ��','ɾ�����','status',0,0,NULL,'2016-06-23 02:27:02'),(84,3,'���ͳ��','����ʱ��','create_time',0,0,NULL,'2016-08-05 09:51:46'),(85,3,'���ͳ��','ɾ��ʱ��','update_time',0,0,NULL,'2016-07-08 09:55:51'),(86,3,'���ͳ��','������Դ','source',1,0,NULL,'2016-06-13 09:47:52'),(87,4,'��Ա����¼','��Ա����','member_id',1,0,NULL,'2016-06-13 09:47:52'),(88,4,'��Ա����¼','��Ա����','member_points',0,0,NULL,'2016-06-27 09:51:00'),(89,4,'��Ա����¼','��Ա�ȼ�','member_level',1,0,NULL,'2016-07-07 11:32:47'),(90,4,'��Ա����¼','����ʱ��','regist_time',1,0,NULL,'2016-06-13 09:47:52'),(91,4,'��Ա����¼','�������','card_amt',1,0,NULL,'2016-06-13 09:47:52'),(92,4,'��Ա����¼','������','expire',1,0,NULL,'2016-06-13 09:47:52'),(93,4,'��Ա����¼','���֤��','identify_no',1,0,NULL,'2016-06-13 09:47:52'),(94,4,'��Ա����¼','��ʻ֤��','driving_license',1,0,NULL,'2016-06-13 09:47:52'),(95,4,'��Ա����¼','cookie','cookie',1,0,NULL,'2016-06-13 09:47:52'),(96,4,'��Ա����¼','�����ʼ�','email',1,0,NULL,'2016-06-13 09:47:52'),(97,4,'��Ա����¼','�ֻ���','mobile',1,0,NULL,'2016-06-13 09:47:52'),(98,4,'��Ա����¼','˽���˺�����','acct_type',1,0,NULL,'2016-06-13 09:47:52'),(99,4,'��Ա����¼','�̻�����','tel',1,0,NULL,'2016-08-05 09:53:37'),(100,4,'��Ա����¼','QQ','qq',1,0,NULL,'2016-06-14 05:50:36'),(101,4,'��Ա����¼','˽���˺�','acct_no',1,0,NULL,'2016-06-13 09:47:52'),(102,4,'��Ա����¼','iphone�ֻ�ʶ����','idfa',1,0,NULL,'2016-06-14 05:50:36'),(103,4,'��Ա����¼','�ֻ�ʶ����','imei',1,0,NULL,'2016-06-14 05:50:35'),(104,4,'��Ա����¼','unionid','udid',1,0,NULL,'2016-08-05 09:53:57'),(105,4,'��Ա����¼','�ֻ�����MAC','phone_mac',1,0,NULL,'2016-06-13 09:47:53'),(106,4,'��Ա����¼','ɾ�����','status',1,0,NULL,'2016-06-13 09:47:53'),(107,4,'��Ա����¼','����ʱ��','create_time',0,0,NULL,'2016-06-27 09:51:00'),(108,4,'��Ա����¼','ɾ��ʱ��','update_time',1,0,NULL,'2016-06-13 09:47:53'),(109,4,'��Ա����¼','������Դ','source',0,0,NULL,'2016-06-27 09:51:00'),(110,5,'��¼��Ϊ','��¼��ʽ','login_type',1,0,NULL,'2016-06-13 09:47:53'),(111,5,'��¼��Ϊ','��¼ʱ��','login_time',1,0,NULL,'2016-06-13 09:47:53'),(112,5,'��¼��Ϊ','�˳�ʱ��','logout_time',1,0,NULL,'2016-06-13 09:47:53'),(113,5,'��¼��Ϊ','��¼IP','login_ip',1,0,NULL,'2016-06-13 09:47:53'),(114,5,'��¼��Ϊ','��¼�豸','login_device',1,0,NULL,'2016-06-13 09:47:53'),(115,5,'��¼��Ϊ','�ֱ���','resolution_ratio',1,0,NULL,'2016-06-13 09:47:53'),(116,5,'��¼��Ϊ','��¼ҳ��','login_url',1,0,NULL,'2016-06-13 09:47:53'),(117,5,'��¼��Ϊ','���֤��','identify_no',1,0,NULL,'2016-06-13 09:47:53'),(118,5,'��¼��Ϊ','��ʻ֤��','driving_license',1,0,NULL,'2016-06-13 09:47:53'),(119,5,'��¼��Ϊ','cookie','cookie',1,0,NULL,'2016-06-13 09:47:53'),(120,5,'��¼��Ϊ','�����ʼ�','email',1,0,NULL,'2016-06-13 09:47:53'),(121,5,'��¼��Ϊ','�ֻ���','mobile',1,0,NULL,'2016-06-13 09:47:53'),(122,5,'��¼��Ϊ','˽���˺�����','acct_type',1,0,NULL,'2016-06-13 09:47:53'),(123,5,'��¼��Ϊ','�̻�����','tel',1,0,NULL,'2016-08-05 09:54:51'),(124,5,'��¼��Ϊ','QQ','qq',1,0,NULL,'2016-06-14 05:50:35'),(125,5,'��¼��Ϊ','˽���˺�','acct_no',1,0,NULL,'2016-06-13 09:47:53'),(126,5,'��¼��Ϊ','iphone�ֻ�ʶ����','idfa',1,0,NULL,'2016-06-14 05:50:35'),(127,5,'��¼��Ϊ','�ֻ�ʶ����','imei',1,0,NULL,'2016-06-14 05:50:35'),(128,5,'��¼��Ϊ','unionid','udid',1,0,NULL,'2016-08-05 09:55:05'),(129,5,'��¼��Ϊ','�ֻ�����MAC','phone_mac',1,0,NULL,'2016-06-13 09:47:53'),(130,5,'��¼��Ϊ','ɾ�����','status',1,0,NULL,'2016-06-13 09:47:53'),(131,5,'��¼��Ϊ','����ʱ��','create_time',1,0,NULL,'2016-06-13 09:47:53'),(132,5,'��¼��Ϊ','ɾ��ʱ��','update_time',1,0,NULL,'2016-06-13 09:47:53'),(133,5,'��¼��Ϊ','������Դ','source',1,0,NULL,'2016-06-13 09:47:54'),(134,6,'֧����¼','֧������','pay_channel',1,0,NULL,'2016-06-13 09:47:54'),(135,6,'֧����¼','֧�����˺�','pay_acct',1,0,NULL,'2016-07-21 10:11:32'),(136,6,'֧����¼','������ˮ��','pay_serial',0,0,NULL,'2016-07-21 11:09:57'),(137,6,'֧����¼','ҵ����ˮ��','trans_serial',0,0,NULL,'2016-07-21 10:14:24'),(138,6,'֧����¼','�̻�������','order_no',0,0,NULL,'2016-07-21 10:14:38'),(139,6,'֧����¼','��Ʒ����','product_name',0,0,NULL,'2016-06-28 07:22:29'),(140,6,'֧����¼','����ʱ��','complete_time',1,0,NULL,'2016-07-07 11:31:02'),(141,6,'֧����¼','֧��״̬','pay_status',1,0,NULL,'2016-06-13 09:47:54'),(142,6,'֧����¼','�Է��˺�','counter_acct',0,0,NULL,'2016-06-28 07:22:29'),(143,6,'֧����¼','������','income_amt',0,0,NULL,'2016-06-28 07:22:29'),(144,6,'֧����¼','֧�����','paid_amt',0,0,NULL,'2016-06-28 07:22:29'),(145,6,'֧����¼','�˻����','acct_amt',0,0,NULL,'2016-07-21 11:10:25'),(146,6,'֧����¼','��ע','comments',1,0,NULL,'2016-06-13 09:47:54'),(147,6,'֧����¼','���֤��','identify_no',0,0,NULL,'2016-07-07 09:27:52'),(148,6,'֧����¼','��ʻ֤��','driving_license',0,0,NULL,'2016-07-07 09:27:52'),(149,6,'֧����¼','cookie','cookie',0,0,NULL,'2016-06-28 07:22:29'),(150,6,'֧����¼','����','email',1,0,NULL,'2016-07-21 10:15:35'),(151,6,'֧����¼','�ֻ���','mobile',1,0,NULL,'2016-06-13 09:47:54'),(152,6,'֧����¼','˽���˺���ϵ','acct_type',1,0,NULL,'2016-07-21 10:16:06'),(153,6,'֧����¼','�̻�����','tel',0,0,NULL,'2016-07-21 10:16:12'),(154,6,'֧����¼','QQ','qq',0,0,NULL,'2016-06-28 07:22:29'),(155,6,'֧����¼','˽���˺�','acct_no',0,0,NULL,'2016-06-28 07:22:29'),(156,6,'֧����¼','IDFA','idfa',0,0,NULL,'2016-07-21 10:16:29'),(157,6,'֧����¼','IMEI','imei',0,0,NULL,'2016-07-21 10:16:34'),(158,6,'֧����¼','UDID','udid',0,0,NULL,'2016-06-28 07:22:29'),(159,6,'֧����¼','MAC','phone_mac',0,0,NULL,'2016-07-21 10:16:54'),(160,6,'֧����¼','ɾ�����','status',0,0,NULL,'2016-06-28 07:22:29'),(161,6,'֧����¼','����ʱ��','create_time',0,0,NULL,'2016-07-21 10:17:14'),(162,6,'֧����¼','ɾ��ʱ��','update_time',0,0,NULL,'2016-06-28 07:22:29'),(163,6,'֧����¼','������Դ','source',0,0,NULL,'2016-06-28 07:22:29'),(164,7,'�����¼','��������','channel_type',1,0,NULL,'2016-07-21 09:29:50'),(165,7,'�����¼','��������ID','channel_id',1,0,NULL,'2016-06-13 09:47:55'),(166,7,'�����¼','��������','channel_name',1,0,NULL,'2016-07-21 09:29:58'),(167,7,'�����¼','֧����ʽ','pay_type',1,0,NULL,'2016-06-13 09:47:55'),(168,7,'�����¼','ҵ����ˮ��','trans_serial',0,0,NULL,'2016-07-21 09:15:16'),(169,7,'�����¼','�̻�������','order_no',1,0,NULL,'2016-07-21 09:15:12'),(170,7,'�����¼','����ʱ��','trans_time',1,0,NULL,'2016-06-13 09:47:55'),(171,7,'�����¼','��ƷID','product_id',1,0,NULL,'2016-07-21 09:30:43'),(172,7,'�����¼','���','specification',1,0,NULL,'2016-07-21 09:30:46'),(173,7,'�����¼','��ɫ','color',1,0,NULL,'2016-06-13 09:47:55'),(174,7,'�����¼','�ۿ�����','discount_type',1,0,NULL,'2016-06-13 09:47:55'),(175,7,'�����¼','�ۿ۽��','discount_amt',1,0,NULL,'2016-06-13 09:47:55'),(176,7,'�����¼','����','price',1,0,NULL,'2016-06-13 09:47:55'),(177,7,'�����¼','����','amount',1,0,NULL,'2016-06-13 09:47:55'),(178,7,'�����¼','�����','inventory',1,0,NULL,'2016-06-13 09:47:55'),(179,7,'�����¼','Ʒ��ID','brand_id',1,0,NULL,'2016-07-21 09:31:26'),(180,7,'�����¼','Ʒ��','brand_name',1,0,NULL,'2016-07-21 09:41:53'),(181,7,'�����¼','һ��Ʒ������ID','class1_id',1,0,NULL,'2016-07-21 09:31:32'),(182,7,'�����¼','һ��Ʒ������','class1_name',1,0,NULL,'2016-06-13 09:47:55'),(183,7,'�����¼','����Ʒ������ID','class2_id',1,0,NULL,'2016-07-21 09:31:39'),(184,7,'�����¼','����Ʒ������','class2_name',1,0,NULL,'2016-06-13 09:47:56'),(185,7,'�����¼','����Ʒ������ID','class3_id',1,0,NULL,'2016-07-21 09:31:50'),(186,7,'�����¼','����Ʒ������','class3_name',1,0,NULL,'2016-06-13 09:47:56'),(187,7,'�����¼','�ļ�Ʒ������ID','class4_id',1,0,NULL,'2016-07-21 09:31:57'),(188,7,'�����¼','�ļ�Ʒ������','class4_name',1,0,NULL,'2016-06-13 09:47:56'),(189,7,'�����¼','������ԱID','sale_assist_id',1,0,NULL,'2016-07-21 09:32:41'),(190,7,'�����¼','������Ա','sale_assistance',1,0,NULL,'2016-07-21 09:32:28'),(191,7,'�����¼','������ԱID','settlement_clerk_id',1,0,NULL,'2016-07-21 09:32:54'),(192,7,'�����¼','������Ա','settlement_clerk',1,0,NULL,'2016-06-13 09:47:56'),(193,7,'�����¼','���֤��','identify_no',0,0,NULL,'2016-07-01 11:51:42'),(194,7,'�����¼','��ʻ֤��','driving_license',0,0,NULL,'2016-07-01 11:51:42'),(195,7,'�����¼','cookie','cookie',0,0,NULL,'2016-07-01 11:51:42'),(196,7,'�����¼','����','email',0,0,NULL,'2016-07-21 09:33:54'),(197,7,'�����¼','�ֻ���','mobile',1,0,NULL,'2016-06-13 09:47:56'),(198,7,'�����¼','˽���˺���ϵ','acct_type',0,0,NULL,'2016-07-21 09:34:12'),(199,7,'�����¼','�̻�����','tel',0,0,NULL,'2016-07-21 09:34:05'),(200,7,'�����¼','QQ','qq',0,0,NULL,'2016-07-01 11:51:42'),(201,7,'�����¼','˽���˺�','acct_no',0,0,NULL,'2016-07-01 11:51:42'),(202,7,'�����¼','IDFA','idfa',0,0,NULL,'2016-07-21 09:43:41'),(203,7,'�����¼','IMEI','imei',0,0,NULL,'2016-07-21 09:43:36'),(204,7,'�����¼','UDID','udid',0,0,NULL,'2016-07-01 11:51:42'),(205,7,'�����¼','MAC','phone_mac',0,0,NULL,'2016-07-21 09:43:31'),(206,7,'�����¼','ɾ�����','status',0,0,NULL,'2016-07-01 11:51:42'),(207,7,'�����¼','����ʱ��','create_time',0,0,NULL,'2016-07-01 11:51:42'),(208,7,'�����¼','ɾ��ʱ��','update_time',0,0,NULL,'2016-07-01 11:51:42'),(209,7,'�����¼','������Դ','source',0,0,NULL,'2016-07-01 11:51:42'),(210,7,'�����¼','����״̬','order_status',0,0,NULL,'2016-07-19 02:50:22'),(211,7,'�����¼','���ͷ�ʽ','delivery_way',0,0,NULL,'2016-07-19 02:50:22'),(212,7,'�����¼','����״̬','logistics_status',0,0,NULL,'2016-07-19 02:50:22'),(213,7,'�����¼','�˷�','shipping_fee',0,0,NULL,'2016-07-19 02:50:22'),(214,7,'�����¼','���ͷ�ʽ','shipping_way',0,0,NULL,'2016-07-19 02:50:22'),(215,7,'�����¼','��ݹ�˾','express_company',0,0,NULL,'2016-07-19 02:50:22'),(216,7,'�����¼','��ݵ���','express_order',0,0,NULL,'2016-07-19 02:50:22'),(217,7,'�����¼','�ջ���','consignee',0,0,NULL,'2016-07-19 02:50:22'),(218,7,'�����¼','�ջ��˵绰','consignee_tel',0,0,NULL,'2016-07-19 02:50:22'),(219,7,'�����¼','�ջ���ַ','consignee_addr',0,0,NULL,'2016-07-19 02:50:22'),(220,7,'�����¼','��ұ�ע','buyer_comment',0,0,NULL,'2016-07-19 02:50:22'),(221,7,'�����¼','���ںű�ʶ','wxmp_id',0,0,NULL,'2016-08-04 11:14:55'),(222,7,'�����¼','openid','wx_code',0,0,NULL,'2016-08-04 11:15:02'),(223,6,'֧����¼','���ںű�ʶ','wxmp_id',0,0,NULL,'2016-08-04 11:12:59'),(224,6,'֧����¼','openid','wx_code',0,0,NULL,'2016-08-04 11:13:02'),(225,7,'�����¼','��Ʒ����','product_name',1,0,NULL,'2016-07-21 09:53:01'),(227,1,'�˿�����','���ںű�ʶ','wxmp_id',1,0,NULL,'2016-08-05 09:57:54'),(228,2,'�ͻ���ǩ','���ںű�ʶ','wxmp_id',1,0,NULL,'2016-08-05 09:57:44'),(229,3,'���ͳ��','���ںű�ʶ','wxmp_id',1,0,NULL,'2016-08-05 09:58:44'),(230,4,'��Ա����¼','���ںű�ʶ','wxmp_id',1,0,NULL,'2016-08-05 09:59:06'),(231,5,'��¼��Ϊ','���ںű�ʶ','wxmp_id',1,0,NULL,'2016-08-05 09:59:39'),(232,1,'�˿�����','openid','wx_code',1,0,NULL,'2016-08-05 10:00:45'),(233,2,'�ͻ���ǩ','openid','wx_code',1,0,NULL,'2016-08-05 10:01:39'),(234,3,'���ͳ��','openid','wx_code',1,0,NULL,'2016-08-05 10:01:57'),(235,4,'��Ա����¼','openid','wx_code',1,0,NULL,'2016-08-05 10:02:14'),(236,5,'��¼��Ϊ','openid','wx_code',1,0,NULL,'2016-08-05 10:02:33'),(237,1,'�˿�����','�ǳ�','nickname',1,0,NULL,'2016-08-10 06:54:44'),(238,1,'�˿�����','ͷ��','head_img_url',1,0,NULL,'2016-08-10 06:55:18'),(239,1,'�˿�����','��עʱ��','subscribe_time',1,0,NULL,'2016-08-10 06:55:56'),(240,1,'�˿�����','����','language',1,0,NULL,'2016-08-10 06:56:35'),(241,1,'�˿�����','΢��unionid','unionid',1,0,NULL,'2016-08-10 06:57:14'),(242,1,'�˿�����','����','remark',1,0,NULL,'2016-08-10 06:57:32');
/*!40000 ALTER TABLE `import_template` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `keyid_map_block`
--

DROP TABLE IF EXISTS `keyid_map_block`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `keyid_map_block` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `field` varchar(20) NOT NULL,
  `field_name` varchar(60) NOT NULL,
  `seq` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `field` (`field`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='id mapping����ӳ������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `keyid_map_block`
--

LOCK TABLES `keyid_map_block` WRITE;
/*!40000 ALTER TABLE `keyid_map_block` DISABLE KEYS */;
INSERT INTO `keyid_map_block` VALUES (1,'mobile','�ֻ���',1),(2,'tel','�̶��绰',2),(3,'email','����',3),(4,'qq','qq��',4),(5,'identify_no','���֤��',5),(6,'driving_license','��ʻ֤��',6),(7,'wxmp_id','΢�Ź��ں�',7),(8,'wx_code','openid',8),(9,'wx_uin','΢�Ÿ��˺�',9),(10,'wxperson_id','΢�Ÿ��˺��·�˿Ψһ��ʶ',10),(11,'IDFA','IDFA',11),(12,'IMEI','IMEI',12),(13,'unionid','unionid',13),(14,'acct_no','˽���˺�',14),(15,'flag1','Ԥ���ֶ�1',15),(16,'flag2','Ԥ���ֶ�2',16),(17,'flag3','Ԥ���ֶ�3',17);
/*!40000 ALTER TABLE `keyid_map_block` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation_log`
--

DROP TABLE IF EXISTS `operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL,
  `operate_desc` varchar(100) DEFAULT NULL COMMENT '��������',
  `operate_time` datetime NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�û�������־��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation_log`
--

LOCK TABLES `operation_log` WRITE;
/*!40000 ALTER TABLE `operation_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `operation_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_arch_point`
--

DROP TABLE IF EXISTS `original_data_arch_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_arch_point` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `event_id` int(11) DEFAULT NULL COMMENT '�¼�ID',
  `event_name` varchar(45) DEFAULT NULL COMMENT '�¼�����',
  `click_time` datetime DEFAULT NULL COMMENT '���ʱ��',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `cookie` varchar(128) DEFAULT NULL,
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_arch_point`
--

LOCK TABLES `original_data_arch_point` WRITE;
/*!40000 ALTER TABLE `original_data_arch_point` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_arch_point` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_customer_tags`
--

DROP TABLE IF EXISTS `original_data_customer_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_customer_tags` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `tag_type` varchar(1) DEFAULT NULL COMMENT '0 - �ı����� , 1 - ��������',
  `tag_name` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL COMMENT '����������',
  `identify_no` varchar(19) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_customer_tags`
--

LOCK TABLES `original_data_customer_tags` WRITE;
/*!40000 ALTER TABLE `original_data_customer_tags` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_customer_tags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_login`
--

DROP TABLE IF EXISTS `original_data_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_login` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `login_type` varchar(100) DEFAULT NULL COMMENT '��¼��ʽ',
  `login_time` datetime DEFAULT NULL COMMENT '��¼ʱ��',
  `logout_time` datetime DEFAULT NULL COMMENT '�˳�ʱ��',
  `login_ip` varchar(45) DEFAULT NULL COMMENT '��¼IP',
  `login_device` varchar(45) DEFAULT NULL COMMENT '��¼�豸',
  `resolution_ratio` varchar(100) DEFAULT NULL COMMENT '�ֱ���',
  `login_url` varchar(100) DEFAULT NULL COMMENT '��¼ҳ��',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_login`
--

LOCK TABLES `original_data_login` WRITE;
/*!40000 ALTER TABLE `original_data_login` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_member`
--

DROP TABLE IF EXISTS `original_data_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_member` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `member_id` varchar(45) DEFAULT NULL COMMENT '��Ա����',
  `member_points` varchar(10) DEFAULT NULL COMMENT '��Ա����',
  `member_level` varchar(45) DEFAULT NULL COMMENT '��Ա�ȼ�',
  `regist_time` date DEFAULT NULL COMMENT '����ʱ��',
  `card_amt` decimal(22,2) DEFAULT NULL COMMENT '�������',
  `expire` date DEFAULT NULL COMMENT '������',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_member`
--

LOCK TABLES `original_data_member` WRITE;
/*!40000 ALTER TABLE `original_data_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_payment`
--

DROP TABLE IF EXISTS `original_data_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_payment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `pay_channel` varchar(10) DEFAULT NULL COMMENT '֧������',
  `pay_acct` varchar(45) DEFAULT NULL COMMENT '֧���˺�',
  `pay_serial` varchar(45) DEFAULT NULL COMMENT '֧����ˮ',
  `trans_serial` varchar(45) DEFAULT NULL COMMENT 'ҵ����ˮ',
  `order_no` varchar(45) DEFAULT NULL COMMENT '�������',
  `product_name` varchar(100) DEFAULT NULL COMMENT '��Ʒ����',
  `create_time` datetime DEFAULT NULL,
  `complete_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `pay_status` varchar(45) DEFAULT NULL COMMENT '֧��״̬',
  `counter_acct` varchar(45) DEFAULT NULL COMMENT '�Է��˺�',
  `income_amt` decimal(22,2) DEFAULT NULL COMMENT '������',
  `paid_amt` decimal(22,2) DEFAULT NULL COMMENT '֧�����',
  `acct_amt` decimal(22,2) DEFAULT NULL COMMENT '�˺����',
  `comments` varchar(100) DEFAULT NULL COMMENT '��ע',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `wxmp_id` varchar(45) DEFAULT NULL COMMENT '΢�Ź��ںű�ʶ',
  `wx_code` varchar(45) DEFAULT NULL COMMENT '΢��openid',
  `bitmap` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_payment`
--

LOCK TABLES `original_data_payment` WRITE;
/*!40000 ALTER TABLE `original_data_payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_population`
--

DROP TABLE IF EXISTS `original_data_population`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_population` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `name` varchar(100) DEFAULT NULL COMMENT '����',
  `gender` tinyint(1) DEFAULT NULL COMMENT '�Ա�\n1-��\n2-Ů \n3-δȷ�� \n4-��ȷ��',
  `birthday` date DEFAULT NULL COMMENT '����������',
  `provice` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL COMMENT 'ְҵ',
  `monthly_income` decimal(22,2) DEFAULT NULL COMMENT '������',
  `monthly_consume` decimal(22,2) DEFAULT NULL COMMENT '�¾�����',
  `marital_status` varchar(45) DEFAULT NULL COMMENT '����״��',
  `education` varchar(45) DEFAULT NULL COMMENT '�����̶�',
  `employment` varchar(45) DEFAULT NULL COMMENT '��ҵ���',
  `nationality` varchar(45) DEFAULT NULL COMMENT '����',
  `blood_type` varchar(45) DEFAULT NULL COMMENT 'Ѫ��',
  `citizenship` varchar(45) DEFAULT NULL COMMENT '����',
  `iq` int(11) DEFAULT NULL,
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_population`
--

LOCK TABLES `original_data_population` WRITE;
/*!40000 ALTER TABLE `original_data_population` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_population` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `original_data_shopping`
--

DROP TABLE IF EXISTS `original_data_shopping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_shopping` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '����id',
  `channel_type` varchar(45) DEFAULT NULL COMMENT '������������',
  `channel_id` varchar(10) DEFAULT NULL COMMENT '��������ID',
  `channel_name` varchar(45) DEFAULT NULL COMMENT '������������',
  `pay_type` varchar(45) DEFAULT NULL COMMENT '֧����ʽ',
  `trans_serial` varchar(45) DEFAULT NULL COMMENT 'ҵ����ˮ',
  `order_no` varchar(100) DEFAULT NULL COMMENT '��Ʒ������',
  `trans_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `product_id` varchar(100) DEFAULT NULL COMMENT '��Ʒ���',
  `specification` varchar(45) DEFAULT NULL COMMENT '��Ʒ���',
  `color` varchar(45) DEFAULT NULL COMMENT '��ɫ',
  `discount_type` varchar(45) DEFAULT NULL COMMENT '�ۿ�����',
  `discount_amt` decimal(22,2) DEFAULT NULL COMMENT '�ۿ۽��',
  `price` decimal(22,2) DEFAULT NULL COMMENT '����',
  `amount` int(11) DEFAULT NULL COMMENT '����',
  `inventory` int(11) DEFAULT NULL COMMENT '�����',
  `brand_id` varchar(45) DEFAULT NULL COMMENT 'Ʒ��iD',
  `brand_name` varchar(45) DEFAULT NULL COMMENT 'Ʒ������',
  `class1_id` varchar(45) DEFAULT NULL COMMENT 'һ��Ʒ��ID',
  `class1_name` varchar(45) DEFAULT NULL COMMENT 'һ��Ʒ������',
  `class2_id` varchar(45) DEFAULT NULL COMMENT '����Ʒ��ID',
  `class2_name` varchar(45) DEFAULT NULL COMMENT '����Ʒ������',
  `class3_id` varchar(45) DEFAULT NULL COMMENT '����Ʒ��ID',
  `class3_name` varchar(45) DEFAULT NULL COMMENT '����Ʒ������',
  `class4_id` varchar(45) DEFAULT NULL COMMENT '�ļ�Ʒ��ID',
  `class4_name` varchar(45) DEFAULT NULL COMMENT '����Ʒ������',
  `sale_assist_id` varchar(45) DEFAULT NULL COMMENT '����Ա���',
  `sale_assistance` varchar(45) DEFAULT NULL COMMENT '����Ա',
  `settlement_clerk_id` varchar(45) DEFAULT NULL COMMENT '������Ա���',
  `settlement_clerk` varchar(45) DEFAULT NULL COMMENT '������Ա',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '���֤��',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '��ʻ֤��',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '�ֻ���',
  `tel` varchar(45) DEFAULT NULL COMMENT '�̶��绰',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '˽���˺�����',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '˽���˺�',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone�ֻ�ʶ����',
  `imei` varchar(45) DEFAULT NULL COMMENT '�ֻ�ʶ����',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '�ֻ�����MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'ɾ��ʱ��',
  `source` varchar(45) DEFAULT NULL COMMENT '������Դ',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���ݵ�������iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `order_status` varchar(10) DEFAULT NULL COMMENT '����״̬',
  `delivery_way` varchar(10) DEFAULT NULL COMMENT '���ͷ�ʽ',
  `logistics_status` varchar(10) DEFAULT NULL COMMENT '����״̬',
  `shipping_fee` decimal(22,2) DEFAULT NULL COMMENT '�˷�',
  `shipping_way` varchar(10) DEFAULT NULL COMMENT '���ͷ�ʽ',
  `express_company` varchar(100) DEFAULT NULL COMMENT '��ݹ�˾',
  `express_order` varchar(50) DEFAULT NULL COMMENT '��ݵ���',
  `consignee` varchar(50) DEFAULT NULL COMMENT '�ջ���',
  `consignee_tel` varchar(20) DEFAULT NULL COMMENT '�ջ��˵绰',
  `consignee_addr` varchar(200) DEFAULT NULL COMMENT '�ջ���ַ',
  `buyer_comment` varchar(200) DEFAULT NULL COMMENT '��ұ�ע',
  `wxmp_id` varchar(128) DEFAULT NULL COMMENT '���ںű�ʶ',
  `wx_code` varchar(128) DEFAULT NULL COMMENT 'openid',
  `product_name` varchar(200) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `original_data_shopping`
--

LOCK TABLES `original_data_shopping` WRITE;
/*!40000 ALTER TABLE `original_data_shopping` DISABLE KEYS */;
/*!40000 ALTER TABLE `original_data_shopping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `party_behavior`
--

DROP TABLE IF EXISTS `party_behavior`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `party_behavior` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_id` varchar(20) NOT NULL COMMENT '��ϵ��ID',
  `behavior_type` varchar(45) DEFAULT NULL COMMENT '��Ϊ����\n0-΢�š�1-web��2-Ӫ���',
  `behavior_name` varchar(45) DEFAULT NULL,
  `wechat_type` varchar(45) DEFAULT NULL,
  `wechat_name` varchar(45) DEFAULT NULL,
  `message` varchar(45) DEFAULT NULL,
  `createtime` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `party_behavior`
--

LOCK TABLES `party_behavior` WRITE;
/*!40000 ALTER TABLE `party_behavior` DISABLE KEYS */;
INSERT INTO `party_behavior` VALUES (1,'1','0','�鿴 ','0','���ں�1','�²�Ʒ����ȵ���','2016-06-07 11:58:11'),(2,'193','1','�鿴 ','0','���ں�2','3~24���±���Ӫ������΢��Ⱥ����','2016-06-07 10:32:13');
/*!40000 ALTER TABLE `party_behavior` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `party_radar`
--

DROP TABLE IF EXISTS `party_radar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `party_radar` (
  `contact_id` varchar(20) NOT NULL DEFAULT '0' COMMENT '��ϵ��ID',
  `contact_name` varchar(20) DEFAULT NULL COMMENT '��ϵ������',
  `recent_buy_time` datetime DEFAULT NULL COMMENT '	�������ʱ��',
  `buy_rate` decimal(5,2) DEFAULT NULL COMMENT '����Ƶ��',
  `goods_types` int(11) DEFAULT NULL COMMENT '������ƷƷ����',
  `average_trans_amt` decimal(22,2) DEFAULT NULL COMMENT 'ƽ�����׽��',
  `top_trans_amt` decimal(22,2) DEFAULT NULL COMMENT '������߽��׶�',
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `party_radar`
--

LOCK TABLES `party_radar` WRITE;
/*!40000 ALTER TABLE `party_radar` DISABLE KEYS */;
INSERT INTO `party_radar` VALUES ('1','����','2016-04-03 14:32:22',5.00,1,1002.56,458.21);
/*!40000 ALTER TABLE `party_radar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resource_id` varbinary(20) DEFAULT NULL,
  `resource_action` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL COMMENT '��Դ����',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��Դ��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL COMMENT '��ɫ��',
  `branch_id` bigint(20) DEFAULT NULL COMMENT '����id',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `crated_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='��ɫ��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'����Ա',1,0,'2016-05-17 07:51:01','2016-05-27 07:51:07');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_resource_map`
--

DROP TABLE IF EXISTS `role_resource_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_resource_map` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `resource_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='��ɫ-��Դ������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_resource_map`
--

LOCK TABLES `role_resource_map` WRITE;
/*!40000 ALTER TABLE `role_resource_map` DISABLE KEYS */;
INSERT INTO `role_resource_map` VALUES (1,1,1,0,'2016-05-04 15:55:52','2016-05-27 07:55:46'),(2,1,2,0,'2016-05-12 15:59:33','2016-05-20 07:59:37');
/*!40000 ALTER TABLE `role_resource_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `segment`
--

DROP TABLE IF EXISTS `segment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `segment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `segmentation_head_id` int(11) DEFAULT NULL,
  `data_id` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index2` (`segmentation_head_id`,`data_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='1.�������3Сʱ�Ľ��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `segment`
--

LOCK TABLES `segment` WRITE;
/*!40000 ALTER TABLE `segment` DISABLE KEYS */;
/*!40000 ALTER TABLE `segment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `segment_distribution`
--

DROP TABLE IF EXISTS `segment_distribution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `segment_distribution` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sagmentation_id` int(11) DEFAULT NULL,
  `audience_count` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϸ�ֵĸ�������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `segment_distribution`
--

LOCK TABLES `segment_distribution` WRITE;
/*!40000 ALTER TABLE `segment_distribution` DISABLE KEYS */;
/*!40000 ALTER TABLE `segment_distribution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `segmentation_body`
--

DROP TABLE IF EXISTS `segmentation_body`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `segmentation_body` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `head_id` int(11) DEFAULT NULL COMMENT '����segmentation_head���id',
  `tag_group_id` int(11) DEFAULT NULL COMMENT '��ǩ���������id',
  `tag_id` int(11) DEFAULT NULL COMMENT '��ǩid',
  `exclude` tinyint(4) DEFAULT '0' COMMENT '�Ƿ��ų�,0:���ų�,1:�ų�',
  `group_index` int(11) DEFAULT NULL COMMENT '�������,0��ʼ����',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����,0:δɾ��,1:��ɾ��',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `group_seq` int(11) DEFAULT NULL COMMENT '����˳���',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ϸ��body��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `segmentation_body`
--

LOCK TABLES `segmentation_body` WRITE;
/*!40000 ALTER TABLE `segmentation_body` DISABLE KEYS */;
/*!40000 ALTER TABLE `segmentation_body` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `segmentation_head`
--

DROP TABLE IF EXISTS `segmentation_head`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `segmentation_head` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ϸ�ֱ��',
  `name` varchar(40) DEFAULT NULL COMMENT 'ϸ������',
  `publish_status` tinyint(4) DEFAULT '0' COMMENT '0:δ��Ч,1:����Ч',
  `oper` varchar(45) DEFAULT NULL COMMENT '������',
  `tag_ids` varchar(200) DEFAULT NULL COMMENT '����cutom_tag���id\nϸ���������ı�ǩid������ö��ŷָ�',
  `refer_campaign_count` int(11) NOT NULL DEFAULT '0' COMMENT '���ʹ�ô���',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:δɾ��,1:��ɾ��',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='����ϸ��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `segmentation_head`
--

LOCK TABLES `segmentation_head` WRITE;
/*!40000 ALTER TABLE `segmentation_head` DISABLE KEYS */;
/*!40000 ALTER TABLE `segmentation_head` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '��ǩ���',
  `name` varchar(40) DEFAULT NULL COMMENT '��ǩ����',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tag_group_id` varchar(500) DEFAULT NULL COMMENT '��ǩ�������id(���и��ڵ��id)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6653 DEFAULT CHARSET=utf8 COMMENT='��ǩ��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag`
--

LOCK TABLES `tag` WRITE;
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
INSERT INTO `tag` VALUES (5869,'��',0,'2016-08-08 11:40:47','2016-08-30 09:44:37','14069'),(5870,'Ů',0,'2016-08-08 11:40:47','2016-09-07 07:53:44','14069'),(5871,'��δ֪',0,'2016-08-08 11:40:47','2016-08-29 07:09:21','55'),(5872,'00��',0,'2016-08-08 11:40:47','2016-09-07 07:53:53','14069'),(5873,'90��',0,'2016-08-08 11:40:47','2016-08-30 09:47:57','14073'),(5874,'80��',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5875,'70��',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5876,'69ǰ',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5877,'ˮƿ��',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5878,'˫����',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5879,'������',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5880,'��ţ��',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5881,'˫����',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5882,'��з��',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5883,'ʨ����',0,'2016-08-08 11:40:47','2016-08-08 03:40:47',NULL),(5884,'��Ů��',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5885,'�����',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5886,'��Ы��',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5887,'������',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5888,'Ħ����',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5889,'�ѻ�',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5890,'δ��',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5891,'δ֪',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5892,'A',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5893,'B',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5894,'AB',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5895,'O',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5896,'δ֪',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5897,'����',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5898,'�ͷ�',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5899,'�г�',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5900,'����',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5901,'����',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5902,'����',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5903,'����',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5904,'��Ӫ',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5905,'���ݷ���',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5906,'��ǰ',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5907,'�ۺ�',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5908,'��Ʒ����',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5909,'Ʒ�ƾ���',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5910,'��ѯ',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5911,'�߻�',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5912,'�İ�',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5913,'����',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5914,'ý��',0,'2016-08-08 11:40:48','2016-08-08 03:40:48',NULL),(5915,'��',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5916,'�г�����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5917,'ҵ����չ',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5918,'���',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5919,'��չ',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5920,'�ͻ�����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5921,'����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5922,'���',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5923,'����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5924,'�߻�',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5925,'����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5926,'���',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5927,'˰��',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5928,'������Դ',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5929,'���',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5930,'����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5931,'��ѵ',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5932,'��ͷ',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5933,'����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5934,'����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5935,'ǰ̨',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5936,'��Ŀ����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5937,'IT',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5938,'֤��',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5939,'Ͷ��',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5940,'ͨ��',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5941,'���ز�',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5942,'����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5943,'����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5944,'����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5945,'����',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5946,'��װ',0,'2016-08-08 11:40:49','2016-08-08 03:40:49',NULL),(5947,'��֯',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5948,'��Դ',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5949,'���',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5950,'����',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5951,'�ִ�',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5952,'ҽҩ',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5953,'����',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5954,'��ȫ����',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5955,'����',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5956,'��֤',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5957,'��Ӧ��',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5958,'�ɹ�',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5959,'�༭',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5960,'������',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5961,'����',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5962,'���',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5963,'����',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5964,'ϵͳ����',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5965,'�ƶ�������',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5966,'UE���',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5967,'UI���',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5968,'�ƶ�������',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5969,'��Ϸ�߻�',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5970,'��Ϸ��ֵ',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5971,'��Ϸ����',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5972,'��Ϸ���',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5973,'��Ϸ����',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5974,'Ӳ��',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5975,'��Ϣ��׼��',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5976,'���簲ȫ',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5977,'����',0,'2016-08-08 11:40:50','2016-08-08 03:40:50',NULL),(5978,'����',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5979,'����',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5980,'��ҵ',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5981,'��ľ',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5982,'װ��',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5983,'��������',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5984,'��·',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5985,'����',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5986,'�������',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5987,'ˮ��',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5988,'�ۿ�',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5989,'��ˮ',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5990,'��ͨ',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5991,'�յ�',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5992,'԰�־������',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5993,'���й滮���',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5994,'����',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5995,'������',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5996,'���տ���',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5997,'���',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5998,'����',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(5999,'����',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6000,'����',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6001,'����',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6002,'�䵱',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6003,'�鱦',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6004,'�ղ�',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6005,'ó��',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6006,'����',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6007,'����',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6008,'˾��',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6009,'����',0,'2016-08-08 11:40:51','2016-08-08 03:40:51',NULL),(6010,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6011,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6012,'�г�',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6013,'���',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6014,'���',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6015,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6016,'��ҵ����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6017,'�뵼��',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6018,'������',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6019,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6020,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6021,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6022,'���ֳ�',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6023,'4S��',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6024,'����ά��',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6025,'��������',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6026,'��е����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6027,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6028,'��ҩ',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6029,'ҽ����е',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6030,'��ý',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6031,'ӡˢ',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6032,'Ӱ��',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6033,'ý��',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6034,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6035,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6036,'��Ա',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6037,'���',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6038,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6039,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6040,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6041,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6042,'����',0,'2016-08-08 11:40:52','2016-08-08 03:40:52',NULL),(6043,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6044,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6045,'�̳�',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6046,'�Ƶ�',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6047,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6048,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6049,'���',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6050,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6051,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6052,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6053,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6054,'ҽ��',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6055,'ũҵ',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6056,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6057,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6058,'����Ա',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6059,'־Ը��',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6060,'�幤',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6061,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6062,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6063,'�������',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6064,'��������Ʒ',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6065,'�ݳ�Ʒ',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6066,'�ع�ҵ',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6067,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6068,'�й�',0,'2016-08-08 11:40:53','2016-08-30 09:49:30','14274'),(6069,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6070,'�ձ�',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6071,'Ӣ��',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6072,'�¹�',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6073,'����',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6074,'���',0,'2016-08-08 11:40:53','2016-08-08 03:40:53',NULL),(6075,'�����',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6076,'���ô�',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6077,'�Ĵ�����',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6078,'����',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6079,'���ɱ�',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6080,'��ɫ��',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6081,'������',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6082,'̨��',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6083,'����ʱ',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6084,'�¼���',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6085,'����',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6086,'����',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6087,'����',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6088,'��ʿ',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6089,'���',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6090,'̩��',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6091,'������',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6092,'����',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6093,'������',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6094,'����',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6095,'����',0,'2016-08-08 11:40:54','2016-08-08 03:40:54',NULL),(6096,'�µ���',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6097,'Խ��',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6098,'˹������',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6099,'��������',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6100,'������',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6101,'�Ű�',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6102,'ֱ������',0,'2016-08-08 11:40:55','2016-08-08 03:40:55',NULL),(6103,'�ڿ���',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6104,'����͢',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6105,'����˹',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6106,'ϣ��',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6107,'Ų��',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6108,'�Ϸ�',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6109,'ī����',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6110,'������',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6111,'ӡ��������',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6112,'ӡ��',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6113,'������',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6114,'������',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6115,'�ݿ�',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6116,'����',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6117,'����·˹',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6118,'������',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6119,'��������',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6120,'�ո���',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6121,'��������',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6122,'����կ',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6123,'˹�工��',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6124,'����ά��',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6125,'����',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6126,'����',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6127,'����',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6128,'�ɹ���',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6129,'����',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6130,'����',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6131,'ά�����',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6132,'����',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6133,'����',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6134,'׳��',0,'2016-08-08 11:40:56','2016-08-08 03:40:56',NULL),(6135,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6136,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6137,'����',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6138,'����',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6139,'����',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6140,'����',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6141,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6142,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6143,'��������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6144,'����',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6145,'����',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6146,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6147,'����',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6148,'���',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6149,'��ɽ��',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6150,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6151,'ˮ��',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6152,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6153,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6154,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6155,'�¶�������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6156,'����',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6157,'���Ӷ���',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6158,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6159,'Ǽ��',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6160,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6161,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6162,'ë����',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6163,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6164,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6165,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6166,'������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6167,'��������',0,'2016-08-08 11:40:57','2016-08-08 03:40:57',NULL),(6168,'ŭ��',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6169,'���α����',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6170,'����˹��',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6171,'���¿���',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6172,'������',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6173,'������',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6174,'ԣ����',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6175,'����',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6176,'��������',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6177,'������',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6178,'���״���',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6179,'������',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6180,'�Ű���',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6181,'�����',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6182,'��ŵ��',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6183,'����',0,'2016-08-08 11:40:58','2016-08-23 09:34:12',NULL),(6184,'���',0,'2016-08-08 11:40:58','2016-08-23 09:34:13',NULL),(6185,'�Ϻ�',0,'2016-08-08 11:40:58','2016-08-23 09:34:14',NULL),(6186,'����',0,'2016-08-08 11:40:58','2016-08-23 09:34:19',NULL),(6187,'�ӱ�ʡ',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6188,'ɽ��ʡ',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6189,'̨��ʡ',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6190,'����ʡ',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6191,'����ʡ',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6192,'������ʡ',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6193,'����ʡ',0,'2016-08-08 11:40:58','2016-08-08 03:40:58',NULL),(6194,'�㽭ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6195,'����ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6196,'����ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6197,'����ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6198,'ɽ��ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6199,'����ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6200,'����ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6201,'����ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6202,'�㶫ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6203,'����ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6204,'�Ĵ�ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6205,'����ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6206,'����ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6207,'����ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6208,'�ຣʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6209,'����ʡ',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6210,'����׳��������',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6211,'����������',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6212,'���Ļ���������',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6213,'�½�ά���������',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6214,'���ɹ�������',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6215,'�����ر�������',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6216,'����ر�������',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6217,'������',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6218,'�����',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6219,'�Ϻ���',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6220,'������',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6221,'ʯ��ׯ��',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6222,'��ɽ��',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6223,'�ػʵ���',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6224,'������',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6225,'��̨��',0,'2016-08-08 11:40:59','2016-08-08 03:40:59',NULL),(6226,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6227,'�żҿ���',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6228,'�е���',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6229,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6230,'�ȷ���',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6231,'��ˮ��',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6232,'̫ԭ��',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6233,'��ͬ��',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6234,'��Ȫ��',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6235,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6236,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6237,'˷����',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6238,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6239,'�˳���',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6240,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6241,'�ٷ���',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6242,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6243,'̨����',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6244,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6245,'��¡��',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6246,'̨����',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6247,'̨����',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6248,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6249,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6250,'̨����',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6251,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6252,'��԰��',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6253,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6254,'������',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6255,'̨����',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6256,'�û���',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6257,'��Ͷ��',0,'2016-08-08 11:41:00','2016-08-08 03:41:00',NULL),(6258,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6259,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6260,'̨����',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6261,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6262,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6263,'�����',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6264,'̨����',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6265,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6266,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6267,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6268,'��ɽ��',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6269,'��˳��',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6270,'��Ϫ��',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6271,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6272,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6273,'Ӫ����',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6274,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6275,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6276,'�̽���',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6277,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6278,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6279,'��«����',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6280,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6281,'������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6282,'��ƽ��',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6283,'��Դ��',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6284,'ͨ����',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6285,'��ɽ��',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6286,'��ԭ��',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6287,'�׳���',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6288,'�ӱ߳�����������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6289,'��������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6290,'���������',0,'2016-08-08 11:41:01','2016-08-08 03:41:01',NULL),(6291,'�׸���',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6292,'˫Ѽɽ��',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6293,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6294,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6295,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6296,'ĵ������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6297,'��ľ˹��',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6298,'��̨����',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6299,'�ں���',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6300,'�绯��',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6301,'���˰������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6302,'�Ͼ���',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6303,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6304,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6305,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6306,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6307,'��ͨ��',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6308,'���Ƹ���',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6309,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6310,'�γ���',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6311,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6312,'����',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6313,'̩����',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6314,'��Ǩ��',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6315,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6316,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6317,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6318,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6319,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6320,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6321,'����',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6322,'������',0,'2016-08-08 11:41:02','2016-08-08 03:41:02',NULL),(6323,'��ɽ��',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6324,'̨����',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6325,'��ˮ��',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6326,'�Ϸ���',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6327,'�ߺ���',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6328,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6329,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6330,'��ɽ��',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6331,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6332,'ͭ����',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6333,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6334,'��ɽ��',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6335,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6336,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6337,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6338,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6339,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6340,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6341,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6342,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6343,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6344,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6345,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6346,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6347,'Ȫ����',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6348,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6349,'��ƽ��',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6350,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6351,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6352,'�ϲ���',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6353,'��������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6354,'Ƽ����',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6355,'�Ž���',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6356,'������',0,'2016-08-08 11:41:03','2016-08-08 03:41:03',NULL),(6357,'ӥ̶��',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6358,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6359,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6360,'�˴���',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6361,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6362,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6363,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6364,'�ൺ��',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6365,'�Ͳ���',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6366,'��ׯ��',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6367,'��Ӫ��',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6368,'��̨��',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6369,'Ϋ����',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6370,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6371,'̩����',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6372,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6373,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6374,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6375,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6376,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6377,'�ĳ���',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6378,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6379,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6380,'֣����',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6381,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6382,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6383,'ƽ��ɽ��',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6384,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6385,'�ױ���',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6386,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6387,'������',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6388,'�����',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6389,'�����',0,'2016-08-08 11:41:04','2016-08-08 03:41:04',NULL),(6390,'�����',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6391,'����Ͽ��',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6392,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6393,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6394,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6395,'�ܿ���',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6396,'פ�����',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6397,'��Դ��',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6398,'�人��',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6399,'��ʯ��',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6400,'ʮ����',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6401,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6402,'�˲���',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6403,'�差��',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6404,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6405,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6406,'Т����',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6407,'�Ƹ���',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6408,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6409,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6410,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6411,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6412,'Ǳ����',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6413,'��ũ������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6414,'��ʩ����������������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6415,'��ɳ��',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6416,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6417,'��̶��',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6418,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6419,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6420,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6421,'������',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6422,'�żҽ���',0,'2016-08-08 11:41:05','2016-08-08 03:41:05',NULL),(6423,'������',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6424,'������',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6425,'������',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6426,'������',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6427,'¦����',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6428,'��������������������',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6429,'������',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6430,'������',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6431,'�麣��',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6432,'��ͷ��',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6433,'�ع���',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6434,'��ɽ��',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6435,'������',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6436,'տ����',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6437,'ï����',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6438,'������',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6439,'������',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6440,'÷����',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6441,'��β��',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6442,'��Դ��',0,'2016-08-08 11:41:06','2016-08-08 03:41:06',NULL),(6443,'������',0,'2016-08-08 11:41:07','2016-08-08 03:41:07',NULL),(6444,'��Զ��',0,'2016-08-08 11:41:07','2016-08-08 03:41:07',NULL),(6445,'��ݸ��',0,'2016-08-08 11:41:07','2016-08-08 03:41:07',NULL),(6446,'��ɽ��',0,'2016-08-08 11:41:07','2016-08-08 03:41:07',NULL),(6447,'������',0,'2016-08-08 11:41:07','2016-08-08 03:41:07',NULL),(6448,'������',0,'2016-08-08 11:41:07','2016-08-08 03:41:07',NULL),(6449,'�Ƹ���',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6450,'������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6451,'�����',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6452,'������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6453,'��ˮ��',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6454,'��������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6455,'������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6456,'��Ҵ��',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6457,'ƽ����',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6458,'��Ȫ��',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6459,'������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6460,'������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6461,'¤����',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6462,'���Ļ���������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6463,'���ϲ���������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6464,'�ɶ���',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6465,'�Թ���',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6466,'��֦����',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6467,'������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6468,'������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6469,'������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6470,'��Ԫ��',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6471,'������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6472,'�ڽ���',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6473,'��ɽ��',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6474,'�ϳ���',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6475,'üɽ��',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6476,'�˱���',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6477,'�㰲��',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6478,'������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6479,'�Ű���',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6480,'������',0,'2016-08-08 11:41:08','2016-08-08 03:41:08',NULL),(6481,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6482,'���Ӳ���Ǽ��������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6483,'���β���������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6484,'��ɽ����������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6485,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6486,'�ൺ��',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6487,'�Ͳ���',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6488,'��ׯ��',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6489,'��Ӫ��',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6490,'��̨��',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6491,'Ϋ����',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6492,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6493,'̩����',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6494,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6495,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6496,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6497,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6498,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6499,'�ĳ���',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6500,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6501,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6502,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6503,'����ˮ��',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6504,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6505,'��˳��',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6506,'ͭ�ʵ���',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6507,'�Ͻڵ���',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6508,'ǭ���ϲ���������������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6509,'ǭ�������嶱��������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6510,'ǭ�ϲ���������������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6511,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6512,'������',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6513,'��ָɽ��',0,'2016-08-08 11:41:09','2016-08-08 03:41:09',NULL),(6514,'����',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6515,'������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6516,'�Ĳ���',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6517,'������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6518,'������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6519,'������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6520,'������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6521,'�Ͳ���',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6522,'�ٸ���',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6523,'��ɳ����������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6524,'��������������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6525,'�ֶ�����������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6526,'��ˮ����������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6527,'��ͤ��������������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6528,'������������������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6529,'������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6530,'������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6531,'��Ϫ��',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6532,'��ɽ��',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6533,'��ͨ��',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6534,'������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6535,'˼é��',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6536,'�ٲ���',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6537,'��ɽ׳������������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6538,'��ӹ���������������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6539,'��˫���ɴ���������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6540,'��������������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6541,'�������������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6542,'�º���徰����������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6543,'ŭ��������������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6544,'�������������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6545,'������',0,'2016-08-08 11:41:10','2016-08-08 03:41:10',NULL),(6546,'��������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6547,'��������������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6548,'���ϲ���������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6549,'���ϲ���������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6550,'�������������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6551,'��������������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6552,'�����ɹ������������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6553,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6554,'ͭ����',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6555,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6556,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6557,'μ����',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6558,'�Ӱ���',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6559,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6560,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6561,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6562,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6563,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6564,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6565,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6566,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6567,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6568,'���Ǹ���',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6569,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6570,'�����',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6571,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6572,'��ɫ��',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6573,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6574,'�ӳ���',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6575,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6576,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6577,'������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6578,'��������',0,'2016-08-08 11:41:11','2016-08-08 03:41:11',NULL),(6579,'��������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6580,'ɽ�ϵ���',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6581,'�տ������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6582,'�������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6583,'��֥����',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6584,'������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6585,'ʯ��ɽ��',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6586,'������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6587,'��ԭ��',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6588,'������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6589,'��³ľ����',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6590,'����������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6591,'ʯ������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6592,'��������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6593,'ͼľ�����',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6594,'�������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6595,'��³����',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6596,'��������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6597,'��ʲ��',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6598,'������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6599,'������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6600,'��ͼʲ��',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6601,'�������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6602,'������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6603,'������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6604,'��Ȫ��',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6605,'������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6606,'������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6607,'������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6608,'������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6609,'������',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6610,'����̩��',0,'2016-08-08 11:41:12','2016-08-08 03:41:12',NULL),(6611,'���ͺ�����',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6612,'��ͷ��',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6613,'�ں���',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6614,'�����',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6615,'ͨ����',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6616,'������˹��',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6617,'���ױ�����',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6618,'�����׶���',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6619,'�����첼��',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6620,'���ֹ�����',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6621,'�˰���',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6622,'��������',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6623,'�����ر�������',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6624,'����ر�������',0,'2016-08-08 11:41:13','2016-08-08 03:41:13',NULL),(6625,'����',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6626,'����',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6627,'1��',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6628,'2��',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6629,'3��',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6630,'4������',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6631,'������',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6632,'��������',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6633,'������',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6634,'1��2��',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6635,'3������',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6636,'50����',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6637,'51-100',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6638,'101-150',0,'2016-08-08 11:41:13','2016-08-19 06:25:00',NULL),(6639,'151-200',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6640,'201-300',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6641,'300����',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6642,'50����',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6643,'51-100',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6644,'101-150',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6645,'151-200',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6646,'201-300',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6647,'300����',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6648,'�������',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6649,'���׹ر�',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6650,'��֧��',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6651,'��',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL),(6652,'��',0,'2016-08-08 11:41:14','2016-08-19 06:25:00',NULL);
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_distribution`
--

DROP TABLE IF EXISTS `tag_distribution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_distribution` (
  `id` int(11) NOT NULL,
  `tag_id` int(11) DEFAULT NULL,
  `coverage_percent` varchar(10) DEFAULT NULL,
  `inuse_tag_percent` varchar(10) DEFAULT NULL,
  `audience_count` char(10) DEFAULT NULL COMMENT '��ǩ���ǵ�����',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ǩ�ֲ�';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_distribution`
--

LOCK TABLES `tag_distribution` WRITE;
/*!40000 ALTER TABLE `tag_distribution` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag_distribution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_group_map`
--

DROP TABLE IF EXISTS `tag_group_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_group_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5568 DEFAULT CHARSET=utf8 COMMENT='��ǩ�ͷ���Ĺ�����ϵ��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_group_map`
--

LOCK TABLES `tag_group_map` WRITE;
/*!40000 ALTER TABLE `tag_group_map` DISABLE KEYS */;
INSERT INTO `tag_group_map` VALUES (4784,5869,14069,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4785,5870,14069,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4786,5871,14069,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4787,5872,14073,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4788,5873,14073,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4789,5874,14073,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4790,5875,14073,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4791,5876,14073,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4792,5877,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4793,5878,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4794,5879,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4795,5880,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4796,5881,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4797,5882,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4798,5883,14079,0,'2016-08-08 11:40:47','2016-08-08 03:40:47'),(4799,5884,14079,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4800,5885,14079,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4801,5886,14079,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4802,5887,14079,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4803,5888,14079,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4804,5889,14092,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4805,5890,14092,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4806,5891,14092,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4807,5892,14096,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4808,5893,14096,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4809,5894,14096,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4810,5895,14096,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4811,5896,14096,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4812,5897,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4813,5898,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4814,5899,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4815,5900,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4816,5901,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4817,5902,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4818,5903,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4819,5904,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4820,5905,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4821,5906,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4822,5907,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4823,5908,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4824,5909,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4825,5910,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4826,5911,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4827,5912,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4828,5913,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4829,5914,14102,0,'2016-08-08 11:40:48','2016-08-08 03:40:48'),(4830,5915,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4831,5916,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4832,5917,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4833,5918,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4834,5919,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4835,5920,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4836,5921,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4837,5922,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4838,5923,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4839,5924,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4840,5925,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4841,5926,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4842,5927,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4843,5928,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4844,5929,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4845,5930,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4846,5931,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4847,5932,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4848,5933,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4849,5934,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4850,5935,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4851,5936,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4852,5937,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4853,5938,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4854,5939,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4855,5940,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4856,5941,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4857,5942,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4858,5943,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4859,5944,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4860,5945,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4861,5946,14102,0,'2016-08-08 11:40:49','2016-08-08 03:40:49'),(4862,5947,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4863,5948,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4864,5949,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4865,5950,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4866,5951,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4867,5952,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4868,5953,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4869,5954,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4870,5955,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4871,5956,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4872,5957,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4873,5958,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4874,5959,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4875,5960,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4876,5961,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4877,5962,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4878,5963,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4879,5964,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4880,5965,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4881,5966,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4882,5967,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4883,5968,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4884,5969,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4885,5970,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4886,5971,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4887,5972,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4888,5973,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4889,5974,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4890,5975,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4891,5976,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4892,5977,14102,0,'2016-08-08 11:40:50','2016-08-08 03:40:50'),(4893,5978,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4894,5979,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4895,5980,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4896,5981,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4897,5982,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4898,5983,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4899,5984,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4900,5985,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4901,5986,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4902,5987,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4903,5988,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4904,5989,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4905,5990,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4906,5991,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4907,5992,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4908,5993,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4909,5994,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4910,5995,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4911,5996,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4912,5997,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4913,5998,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4914,5999,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4915,6000,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4916,6001,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4917,6002,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4918,6003,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4919,6004,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4920,6005,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4921,6006,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4922,6007,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4923,6008,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4924,6009,14102,0,'2016-08-08 11:40:51','2016-08-08 03:40:51'),(4925,6010,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4926,6011,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4927,6012,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4928,6013,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4929,6014,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4930,6015,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4931,6016,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4932,6017,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4933,6018,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4934,6019,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4935,6020,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4936,6021,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4937,6022,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4938,6023,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4939,6024,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4940,6025,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4941,6026,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4942,6027,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4943,6028,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4944,6029,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4945,6030,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4946,6031,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4947,6032,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4948,6033,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4949,6034,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4950,6035,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4951,6036,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4952,6037,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4953,6038,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4954,6039,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4955,6040,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4956,6041,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4957,6042,14102,0,'2016-08-08 11:40:52','2016-08-08 03:40:52'),(4958,6043,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4959,6044,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4960,6045,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4961,6046,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4962,6047,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4963,6048,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4964,6049,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4965,6050,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4966,6051,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4967,6052,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4968,6053,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4969,6054,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4970,6055,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4971,6056,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4972,6057,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4973,6058,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4974,6059,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4975,6060,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4976,6061,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4977,6062,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4978,6063,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4979,6064,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4980,6065,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4981,6066,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4982,6067,14102,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4983,6068,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4984,6069,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4985,6070,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4986,6071,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4987,6072,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4988,6073,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4989,6074,14274,0,'2016-08-08 11:40:53','2016-08-08 03:40:53'),(4990,6075,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4991,6076,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4992,6077,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4993,6078,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4994,6079,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4995,6080,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4996,6081,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4997,6082,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4998,6083,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(4999,6084,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5000,6085,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5001,6086,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5002,6087,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5003,6088,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5004,6089,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5005,6090,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5006,6091,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5007,6092,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5008,6093,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5009,6094,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5010,6095,14274,0,'2016-08-08 11:40:54','2016-08-08 03:40:54'),(5011,6096,14274,0,'2016-08-08 11:40:55','2016-08-08 03:40:55'),(5012,6097,14274,0,'2016-08-08 11:40:55','2016-08-08 03:40:55'),(5013,6098,14274,0,'2016-08-08 11:40:55','2016-08-08 03:40:55'),(5014,6099,14274,0,'2016-08-08 11:40:55','2016-08-08 03:40:55'),(5015,6100,14274,0,'2016-08-08 11:40:55','2016-08-08 03:40:55'),(5016,6101,14274,0,'2016-08-08 11:40:55','2016-08-08 03:40:55'),(5017,6102,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5018,6103,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5019,6104,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5020,6105,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5021,6106,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5022,6107,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5023,6108,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5024,6109,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5025,6110,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5026,6111,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5027,6112,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5028,6113,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5029,6114,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5030,6115,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5031,6116,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5032,6117,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5033,6118,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5034,6119,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5035,6120,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5036,6121,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5037,6122,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5038,6123,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5039,6124,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5040,6125,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5041,6126,14274,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5042,6127,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5043,6128,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5044,6129,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5045,6130,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5046,6131,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5047,6132,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5048,6133,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5049,6134,14334,0,'2016-08-08 11:40:56','2016-08-08 03:40:56'),(5050,6135,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5051,6136,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5052,6137,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5053,6138,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5054,6139,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5055,6140,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5056,6141,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5057,6142,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5058,6143,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5059,6144,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5060,6145,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5061,6146,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5062,6147,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5063,6148,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5064,6149,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5065,6150,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5066,6151,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5067,6152,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5068,6153,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5069,6154,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5070,6155,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5071,6156,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5072,6157,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5073,6158,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5074,6159,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5075,6160,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5076,6161,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5077,6162,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5078,6163,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5079,6164,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5080,6165,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5081,6166,14334,0,'2016-08-08 11:40:57','2016-08-08 03:40:57'),(5082,6167,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5083,6168,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5084,6169,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5085,6170,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5086,6171,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5087,6172,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5088,6173,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5089,6174,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5090,6175,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5091,6176,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5092,6177,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5093,6178,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5094,6179,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5095,6180,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5096,6181,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5097,6182,14334,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5098,6183,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5099,6184,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5100,6185,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5101,6186,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5102,6187,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5103,6188,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5104,6189,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5105,6190,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5106,6191,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5107,6192,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5108,6193,14392,0,'2016-08-08 11:40:58','2016-08-08 03:40:58'),(5109,6194,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5110,6195,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5111,6196,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5112,6197,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5113,6198,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5114,6199,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5115,6200,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5116,6201,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5117,6202,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5118,6203,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5119,6204,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5120,6205,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5121,6206,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5122,6207,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5123,6208,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5124,6209,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5125,6210,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5126,6211,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5127,6212,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5128,6213,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5129,6214,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5130,6215,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5131,6216,14392,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5132,6217,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5133,6218,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5134,6219,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5135,6220,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5136,6221,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5137,6222,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5138,6223,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5139,6224,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5140,6225,14427,0,'2016-08-08 11:40:59','2016-08-08 03:40:59'),(5141,6226,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5142,6227,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5143,6228,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5144,6229,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5145,6230,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5146,6231,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5147,6232,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5148,6233,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5149,6234,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5150,6235,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5151,6236,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5152,6237,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5153,6238,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5154,6239,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5155,6240,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5156,6241,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5157,6242,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5158,6243,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5159,6244,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5160,6245,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5161,6246,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5162,6247,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5163,6248,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5164,6249,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5165,6250,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5166,6251,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5167,6252,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5168,6253,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5169,6254,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5170,6255,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5171,6256,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5172,6257,14427,0,'2016-08-08 11:41:00','2016-08-08 03:41:00'),(5173,6258,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5174,6259,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5175,6260,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5176,6261,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5177,6262,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5178,6263,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5179,6264,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5180,6265,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5181,6266,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5182,6267,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5183,6268,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5184,6269,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5185,6270,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5186,6271,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5187,6272,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5188,6273,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5189,6274,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5190,6275,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5191,6276,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5192,6277,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5193,6278,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5194,6279,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5195,6280,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5196,6281,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5197,6282,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5198,6283,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5199,6284,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5200,6285,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5201,6286,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5202,6287,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5203,6288,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5204,6289,14427,0,'2016-08-08 11:41:01','2016-08-08 03:41:01'),(5205,6290,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5206,6291,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5207,6292,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5208,6293,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5209,6294,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5210,6295,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5211,6296,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5212,6297,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5213,6298,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5214,6299,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5215,6300,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5216,6301,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5217,6302,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5218,6303,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5219,6304,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5220,6305,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5221,6306,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5222,6307,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5223,6308,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5224,6309,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5225,6310,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5226,6311,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5227,6312,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5228,6313,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5229,6314,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5230,6315,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5231,6316,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5232,6317,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5233,6318,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5234,6319,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5235,6320,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5236,6321,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5237,6322,14427,0,'2016-08-08 11:41:02','2016-08-08 03:41:02'),(5238,6323,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5239,6324,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5240,6325,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5241,6326,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5242,6327,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5243,6328,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5244,6329,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5245,6330,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5246,6331,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5247,6332,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5248,6333,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5249,6334,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5250,6335,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5251,6336,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5252,6337,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5253,6338,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5254,6339,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5255,6340,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5256,6341,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5257,6342,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5258,6343,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5259,6344,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5260,6345,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5261,6346,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5262,6347,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5263,6348,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5264,6349,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5265,6350,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5266,6351,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5267,6352,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5268,6353,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5269,6354,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5270,6355,14427,0,'2016-08-08 11:41:03','2016-08-08 03:41:03'),(5271,6356,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5272,6357,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5273,6358,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5274,6359,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5275,6360,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5276,6361,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5277,6362,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5278,6363,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5279,6364,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5280,6365,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5281,6366,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5282,6367,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5283,6368,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5284,6369,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5285,6370,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5286,6371,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5287,6372,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5288,6373,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5289,6374,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5290,6375,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5291,6376,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5292,6377,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5293,6378,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5294,6379,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5295,6380,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5296,6381,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5297,6382,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5298,6383,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5299,6384,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5300,6385,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5301,6386,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5302,6387,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5303,6388,14427,0,'2016-08-08 11:41:04','2016-08-08 03:41:04'),(5304,6389,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5305,6390,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5306,6391,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5307,6392,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5308,6393,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5309,6394,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5310,6395,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5311,6396,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5312,6397,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5313,6398,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5314,6399,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5315,6400,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5316,6401,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5317,6402,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5318,6403,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5319,6404,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5320,6405,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5321,6406,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5322,6407,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5323,6408,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5324,6409,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5325,6410,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5326,6411,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5327,6412,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5328,6413,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5329,6414,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5330,6415,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5331,6416,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5332,6417,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5333,6418,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5334,6419,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5335,6420,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5336,6421,14427,0,'2016-08-08 11:41:05','2016-08-08 03:41:05'),(5337,6422,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5338,6423,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5339,6424,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5340,6425,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5341,6426,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5342,6427,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5343,6428,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5344,6429,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5345,6430,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5346,6431,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5347,6432,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5348,6433,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5349,6434,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5350,6435,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5351,6436,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5352,6437,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5353,6438,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5354,6439,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5355,6440,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5356,6441,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5357,6442,14427,0,'2016-08-08 11:41:06','2016-08-08 03:41:06'),(5358,6443,14427,0,'2016-08-08 11:41:07','2016-08-08 03:41:07'),(5359,6444,14427,0,'2016-08-08 11:41:07','2016-08-08 03:41:07'),(5360,6445,14427,0,'2016-08-08 11:41:07','2016-08-08 03:41:07'),(5361,6446,14427,0,'2016-08-08 11:41:07','2016-08-08 03:41:07'),(5362,6447,14427,0,'2016-08-08 11:41:07','2016-08-08 03:41:07'),(5363,6448,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5364,6449,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5365,6450,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5366,6451,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5367,6452,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5368,6453,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5369,6454,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5370,6455,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5371,6456,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5372,6457,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5373,6458,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5374,6459,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5375,6460,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5376,6461,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5377,6462,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5378,6463,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5379,6464,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5380,6465,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5381,6466,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5382,6467,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5383,6468,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5384,6469,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5385,6470,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5386,6471,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5387,6472,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5388,6473,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5389,6474,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5390,6475,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5391,6476,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5392,6477,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5393,6478,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5394,6479,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5395,6480,14427,0,'2016-08-08 11:41:08','2016-08-08 03:41:08'),(5396,6481,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5397,6482,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5398,6483,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5399,6484,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5400,6485,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5401,6486,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5402,6487,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5403,6488,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5404,6489,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5405,6490,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5406,6491,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5407,6492,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5408,6493,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5409,6494,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5410,6495,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5411,6496,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5412,6497,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5413,6498,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5414,6499,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5415,6500,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5416,6501,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5417,6502,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5418,6503,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5419,6504,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5420,6505,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5421,6506,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5422,6507,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5423,6508,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5424,6509,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5425,6510,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5426,6511,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5427,6512,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5428,6513,14427,0,'2016-08-08 11:41:09','2016-08-08 03:41:09'),(5429,6514,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5430,6515,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5431,6516,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5432,6517,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5433,6518,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5434,6519,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5435,6520,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5436,6521,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5437,6522,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5438,6523,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5439,6524,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5440,6525,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5441,6526,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5442,6527,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5443,6528,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5444,6529,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5445,6530,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5446,6531,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5447,6532,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5448,6533,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5449,6534,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5450,6535,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5451,6536,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5452,6537,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5453,6538,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5454,6539,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5455,6540,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5456,6541,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5457,6542,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5458,6543,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5459,6544,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5460,6545,14427,0,'2016-08-08 11:41:10','2016-08-08 03:41:10'),(5461,6546,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5462,6547,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5463,6548,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5464,6549,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5465,6550,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5466,6551,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5467,6552,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5468,6553,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5469,6554,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5470,6555,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5471,6556,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5472,6557,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5473,6558,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5474,6559,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5475,6560,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5476,6561,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5477,6562,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5478,6563,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5479,6564,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5480,6565,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5481,6566,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5482,6567,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5483,6568,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5484,6569,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5485,6570,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5486,6571,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5487,6572,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5488,6573,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5489,6574,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5490,6575,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5491,6576,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5492,6577,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5493,6578,14427,0,'2016-08-08 11:41:11','2016-08-08 03:41:11'),(5494,6579,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5495,6580,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5496,6581,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5497,6582,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5498,6583,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5499,6584,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5500,6585,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5501,6586,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5502,6587,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5503,6588,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5504,6589,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5505,6590,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5506,6591,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5507,6592,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5508,6593,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5509,6594,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5510,6595,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5511,6596,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5512,6597,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5513,6598,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5514,6599,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5515,6600,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5516,6601,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5517,6602,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5518,6603,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5519,6604,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5520,6605,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5521,6606,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5522,6607,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5523,6608,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5524,6609,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5525,6610,14427,0,'2016-08-08 11:41:12','2016-08-08 03:41:12'),(5526,6611,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5527,6612,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5528,6613,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5529,6614,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5530,6615,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5531,6616,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5532,6617,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5533,6618,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5534,6619,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5535,6620,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5536,6621,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5537,6622,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5538,6623,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5539,6624,14427,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5540,6625,14837,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5541,6626,14837,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5542,6627,14841,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5543,6628,14841,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5544,6629,14841,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5545,6630,14841,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5546,6631,14846,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5547,6632,14846,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5548,6633,14846,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5549,6634,14850,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5550,6635,14850,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5551,6636,14853,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5552,6637,14853,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5553,6638,14853,0,'2016-08-08 11:41:13','2016-08-08 03:41:13'),(5554,6639,14853,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5555,6640,14853,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5556,6641,14853,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5557,6642,14860,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5558,6643,14860,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5559,6644,14860,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5560,6645,14860,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5561,6646,14860,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5562,6647,14860,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5563,6648,14868,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5564,6649,14868,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5565,6650,14868,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5566,6651,14872,0,'2016-08-08 11:41:14','2016-08-08 03:41:14'),(5567,6652,14872,0,'2016-08-08 11:41:14','2016-08-08 03:41:14');
/*!40000 ALTER TABLE `tag_group_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_party_relation`
--

DROP TABLE IF EXISTS `tag_party_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_party_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_id` int(11) NOT NULL,
  `party_id` int(11) NOT NULL COMMENT '������(��ϵ��)ID',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_party_relation`
--

LOCK TABLES `tag_party_relation` WRITE;
/*!40000 ALTER TABLE `tag_party_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `tag_party_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tag_recommend`
--

DROP TABLE IF EXISTS `tag_recommend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_recommend` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_group_id` int(11) DEFAULT NULL,
  `tag_group_name` varchar(200) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8 COMMENT='ϵͳ�Ƽ���ǩ���';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_recommend`
--

LOCK TABLES `tag_recommend` WRITE;
/*!40000 ALTER TABLE `tag_recommend` DISABLE KEYS */;
INSERT INTO `tag_recommend` VALUES (153,14069,'��Ȼ������-������Ϣ-�Ա�',0,'2016-08-08 11:40:47','2016-08-09 02:33:38'),(159,14274,'��Ȼ������-������Ϣ-����',0,'2016-08-08 11:40:53','2016-08-09 02:37:49'),(161,14392,'��Ȼ������-��������-ʡ',0,'2016-08-08 11:40:58','2016-08-09 02:37:49'),(162,14427,'��Ȼ������-��������-��',0,'2016-08-08 17:48:00','2016-08-18 02:43:33'),(163,14837,'�Ӵ���ƫ��-��������ƫ��-΢��������Ⱥ',0,'2016-08-08 11:41:13','2016-08-17 09:44:37'),(164,14841,'�û���ֵ-�����ֵ(RFM)-�ܹ���Ƶ��',0,'2016-08-08 11:41:13','2016-08-09 02:37:49'),(165,14846,'�û���ֵ-�����ֵ(RFM)-���һ�ι���',0,'2016-08-08 11:41:13','2016-08-09 02:37:49'),(166,14850,'�û���ֵ-�����ֵ(RFM)-�¾�����Ƶ��',0,'2016-08-08 11:41:13','2016-08-09 02:37:49'),(167,14853,'�û���ֵ-�����ֵ(RFM)-�ܼƽ��׽��',0,'2016-08-08 11:41:13','2016-08-09 02:37:49'),(168,14860,'�û���ֵ-�����ֵ(RFM)-�͵���',0,'2016-08-08 11:41:14','2016-08-09 02:37:49'),(169,14868,'Ʒ����ϵǿ��-�û���ʧ����-֧��״̬',0,'2016-08-08 11:41:14','2016-08-09 02:37:49'),(170,14872,'Ʒ����ϵǿ��-�û���ʧ����-������Ⱥ',0,'2016-08-08 11:41:14','2016-08-17 09:44:52');
/*!40000 ALTER TABLE `tag_recommend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taggroup`
--

DROP TABLE IF EXISTS `taggroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taggroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '��ǩ��ID',
  `name` varchar(40) DEFAULT NULL COMMENT '��ǩ������',
  `parent_group_id` bigint(20) DEFAULT NULL COMMENT '����ǩ��ID',
  `level` int(11) DEFAULT NULL COMMENT '��ǩ��㼶 0-��δ�� 1-�μ�   n-��߼�(������)',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14875 DEFAULT CHARSET=utf8 COMMENT='��ǩ���';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taggroup`
--

LOCK TABLES `taggroup` WRITE;
/*!40000 ALTER TABLE `taggroup` DISABLE KEYS */;
INSERT INTO `taggroup` VALUES (14068,'��Ȼ������-������Ϣ',-1,0,0,'2016-08-08 11:40:47','2016-08-18 03:50:59'),(14069,'��Ȼ������-������Ϣ-�Ա�',14068,1,0,'2016-08-08 11:40:47','2016-08-18 03:51:00'),(14070,'��',14069,2,0,'2016-08-08 11:40:47','2016-08-18 03:51:01'),(14071,'Ů',14069,2,0,'2016-08-08 11:40:47','2016-08-18 03:51:03'),(14072,'δ֪',14069,2,0,'2016-08-08 11:40:47','2016-08-18 03:51:05'),(14073,'��Ȼ������-������Ϣ-�����',14068,1,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14074,'00��',14073,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14075,'90��',14073,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14076,'80��',14073,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14077,'70��',14073,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14078,'69ǰ',14073,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14079,'��Ȼ������-������Ϣ-����',14068,1,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14080,'ˮƿ��',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14081,'˫����',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14082,'������',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14083,'��ţ��',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14084,'˫����',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14085,'��з��',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14086,'ʨ����',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14087,'��Ů��',14079,2,1,'2016-08-08 11:40:47','2016-08-18 03:49:06'),(14088,'�����',14079,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14089,'��Ы��',14079,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14090,'������',14079,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14091,'Ħ����',14079,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14092,'��Ȼ������-������Ϣ-����״��',14068,1,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14093,'�ѻ�',14092,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14094,'δ��',14092,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14095,'δ֪',14092,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14096,'��Ȼ������-������Ϣ-Ѫ��',14068,1,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14097,'A',14096,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14098,'B',14096,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14099,'AB',14096,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14100,'O',14096,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14101,'δ֪',14096,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14102,'��Ȼ������-������Ϣ-ְҵ',14068,1,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14103,'����',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14104,'�ͷ�',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14105,'�г�',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14106,'����',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14107,'����',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14108,'����',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14109,'����',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14110,'��Ӫ',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14111,'���ݷ���',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14112,'��ǰ',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14113,'�ۺ�',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14114,'��Ʒ����',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14115,'Ʒ�ƾ���',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14116,'��ѯ',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14117,'�߻�',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14118,'�İ�',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14119,'����',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14120,'ý��',14102,2,1,'2016-08-08 11:40:48','2016-08-18 03:49:06'),(14121,'��',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14122,'�г�����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14123,'ҵ����չ',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14124,'���',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14125,'��չ',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14126,'�ͻ�����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14127,'����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14128,'���',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14129,'����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14130,'�߻�',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14131,'����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14132,'���',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14133,'˰��',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14134,'������Դ',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14135,'���',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14136,'����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14137,'��ѵ',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14138,'��ͷ',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14139,'����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14140,'����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14141,'ǰ̨',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14142,'��Ŀ����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14143,'IT',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14144,'֤��',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14145,'Ͷ��',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14146,'ͨ��',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14147,'���ز�',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14148,'����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14149,'����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14150,'����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14151,'����',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14152,'��װ',14102,2,1,'2016-08-08 11:40:49','2016-08-18 03:49:06'),(14153,'��֯',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14154,'��Դ',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14155,'���',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14156,'����',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14157,'�ִ�',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14158,'ҽҩ',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14159,'����',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14160,'��ȫ����',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14161,'����',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14162,'��֤',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14163,'��Ӧ��',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14164,'�ɹ�',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14165,'�༭',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14166,'������',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14167,'����',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14168,'���',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14169,'����',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14170,'ϵͳ����',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14171,'�ƶ�������',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14172,'UE���',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14173,'UI���',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14174,'�ƶ�������',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14175,'��Ϸ�߻�',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14176,'��Ϸ��ֵ',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14177,'��Ϸ����',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14178,'��Ϸ���',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14179,'��Ϸ����',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14180,'Ӳ��',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14181,'��Ϣ��׼��',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14182,'���簲ȫ',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14183,'����',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14184,'����',14102,2,1,'2016-08-08 11:40:50','2016-08-18 03:49:06'),(14185,'����',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14186,'��ҵ',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14187,'��ľ',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14188,'װ��',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14189,'��������',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14190,'��·',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14191,'����',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14192,'�������',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14193,'ˮ��',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14194,'�ۿ�',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14195,'��ˮ',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14196,'��ͨ',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14197,'�յ�',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14198,'԰�־������',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14199,'���й滮���',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14200,'����',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14201,'������',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14202,'���տ���',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14203,'���',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14204,'����',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14205,'����',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14206,'����',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14207,'����',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14208,'�䵱',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14209,'�鱦',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14210,'�ղ�',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14211,'ó��',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14212,'����',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14213,'����',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14214,'˾��',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14215,'����',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14216,'����',14102,2,1,'2016-08-08 11:40:51','2016-08-18 03:49:06'),(14217,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14218,'�г�',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14219,'���',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14220,'���',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14221,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14222,'��ҵ����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14223,'�뵼��',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14224,'������',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14225,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14226,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14227,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14228,'���ֳ�',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14229,'4S��',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14230,'����ά��',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14231,'��������',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14232,'��е����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14233,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14234,'��ҩ',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14235,'ҽ����е',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14236,'��ý',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14237,'ӡˢ',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14238,'Ӱ��',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14239,'ý��',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14240,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14241,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14242,'��Ա',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14243,'���',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14244,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14245,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14246,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14247,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14248,'����',14102,2,1,'2016-08-08 11:40:52','2016-08-18 03:49:06'),(14249,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14250,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14251,'�̳�',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14252,'�Ƶ�',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14253,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14254,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14255,'���',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14256,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14257,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14258,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14259,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14260,'ҽ��',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14261,'ũҵ',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14262,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14263,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14264,'����Ա',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14265,'־Ը��',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14266,'�幤',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14267,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14268,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14269,'�������',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14270,'��������Ʒ',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14271,'�ݳ�Ʒ',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14272,'�ع�ҵ',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14273,'����',14102,2,1,'2016-08-08 11:40:53','2016-08-18 03:49:06'),(14274,'��Ȼ������-������Ϣ-����',14068,1,0,'2016-08-08 11:40:53','2016-08-18 06:04:53'),(14275,'�й�',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14276,'����',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14277,'�ձ�',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14278,'Ӣ��',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14279,'�¹�',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14280,'����',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14281,'���',14274,2,0,'2016-08-08 11:40:53','2016-08-18 03:54:41'),(14282,'�����',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14283,'���ô�',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14284,'�Ĵ�����',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14285,'����',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14286,'���ɱ�',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14287,'��ɫ��',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14288,'������',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14289,'̨��',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14290,'����ʱ',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14291,'�¼���',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14292,'����',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14293,'����',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14294,'����',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14295,'��ʿ',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14296,'���',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14297,'̩��',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14298,'������',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14299,'����',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14300,'������',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14301,'����',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14302,'����',14274,2,0,'2016-08-08 11:40:54','2016-08-18 03:54:41'),(14303,'�µ���',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14304,'Խ��',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14305,'˹������',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14306,'��������',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14307,'������',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14308,'�Ű�',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14309,'ֱ������',14274,2,0,'2016-08-08 11:40:55','2016-08-18 03:54:41'),(14310,'�ڿ���',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14311,'����͢',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14312,'����˹',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14313,'ϣ��',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14314,'Ų��',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14315,'�Ϸ�',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14316,'ī����',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14317,'������',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14318,'ӡ��������',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14319,'ӡ��',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14320,'������',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14321,'������',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14322,'�ݿ�',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14323,'����',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14324,'����·˹',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14325,'������',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14326,'��������',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14327,'�ո���',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14328,'��������',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14329,'����կ',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14330,'˹�工��',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14331,'����ά��',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14332,'����',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14333,'����',14274,2,0,'2016-08-08 11:40:56','2016-08-18 03:54:41'),(14334,'��Ȼ������-������Ϣ-����',14068,1,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14335,'����',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14336,'�ɹ���',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14337,'����',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14338,'����',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14339,'ά�����',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14340,'����',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14341,'����',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14342,'׳��',14334,2,1,'2016-08-08 11:40:56','2016-08-18 03:49:06'),(14343,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14344,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14345,'����',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14346,'����',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14347,'����',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14348,'����',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14349,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14350,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14351,'��������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14352,'����',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14353,'����',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14354,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14355,'����',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14356,'���',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14357,'��ɽ��',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14358,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14359,'ˮ��',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14360,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14361,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14362,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14363,'�¶�������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14364,'����',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14365,'���Ӷ���',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14366,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14367,'Ǽ��',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14368,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14369,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14370,'ë����',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14371,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14372,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14373,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14374,'������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14375,'��������',14334,2,1,'2016-08-08 11:40:57','2016-08-18 03:49:06'),(14376,'ŭ��',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14377,'���α����',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14378,'����˹��',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14379,'���¿���',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14380,'������',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14381,'������',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14382,'ԣ����',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14383,'����',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14384,'��������',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14385,'������',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14386,'���״���',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14387,'������',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14388,'�Ű���',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14389,'�����',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14390,'��ŵ��',14334,2,1,'2016-08-08 11:40:58','2016-08-18 03:49:06'),(14391,'��Ȼ������-��������',-1,0,0,'2016-08-08 11:40:58','2016-08-18 03:55:32'),(14392,'��Ȼ������-��������-ʡ',14391,1,0,'2016-08-08 11:40:58','2016-08-18 03:55:34'),(14393,'����',14392,2,0,'2016-08-08 11:40:58','2016-08-23 09:35:05'),(14394,'���',14392,2,0,'2016-08-08 11:40:58','2016-08-23 09:35:06'),(14395,'�Ϻ�',14392,2,0,'2016-08-08 11:40:58','2016-08-23 09:35:06'),(14396,'����',14392,2,0,'2016-08-08 11:40:58','2016-08-23 09:35:09'),(14397,'�ӱ�ʡ',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14398,'ɽ��ʡ',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14399,'̨��ʡ',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14400,'����ʡ',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14401,'����ʡ',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14402,'������ʡ',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14403,'����ʡ',14392,2,0,'2016-08-08 11:40:58','2016-08-18 03:54:41'),(14404,'�㽭ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14405,'����ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14406,'����ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14407,'����ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14408,'ɽ��ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14409,'����ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14410,'����ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14411,'����ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14412,'�㶫ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14413,'����ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14414,'�Ĵ�ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14415,'����ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14416,'����ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14417,'����ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14418,'�ຣʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14419,'����ʡ',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14420,'����׳��������',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14421,'����������',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14422,'���Ļ���������',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14423,'�½�ά���������',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14424,'���ɹ�������',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14425,'�����ر�������',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14426,'����ر�������',14392,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14427,'��Ȼ������-��������-��',14391,1,0,'2016-08-08 11:40:59','2016-08-18 03:55:48'),(14428,'������',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14429,'�����',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14430,'�Ϻ���',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14431,'������',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14432,'ʯ��ׯ��',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14433,'��ɽ��',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14434,'�ػʵ���',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14435,'������',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14436,'��̨��',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14437,'������',14427,2,0,'2016-08-08 11:40:59','2016-08-18 03:54:41'),(14438,'�żҿ���',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14439,'�е���',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14440,'������',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14441,'�ȷ���',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14442,'��ˮ��',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14443,'̫ԭ��',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14444,'��ͬ��',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14445,'��Ȫ��',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14446,'������',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14447,'������',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14448,'˷����',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14449,'������',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14450,'�˳���',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14451,'������',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14452,'�ٷ���',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14453,'������',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14454,'̨����',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14455,'������',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14456,'��¡��',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14457,'̨����',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14458,'̨����',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14459,'������',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14460,'������',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14461,'̨����',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14462,'������',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14463,'��԰��',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14464,'������',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14465,'������',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14466,'̨����',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14467,'�û���',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14468,'��Ͷ��',14427,2,0,'2016-08-08 11:41:00','2016-08-18 03:54:41'),(14469,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14470,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14471,'̨����',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14472,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14473,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14474,'�����',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14475,'̨����',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14476,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14477,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14478,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14479,'��ɽ��',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14480,'��˳��',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14481,'��Ϫ��',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14482,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14483,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14484,'Ӫ����',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14485,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14486,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14487,'�̽���',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14488,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14489,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14490,'��«����',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14491,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14492,'������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14493,'��ƽ��',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14494,'��Դ��',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14495,'ͨ����',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14496,'��ɽ��',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14497,'��ԭ��',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14498,'�׳���',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14499,'�ӱ߳�����������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14500,'��������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14501,'���������',14427,2,0,'2016-08-08 11:41:01','2016-08-18 03:54:41'),(14502,'�׸���',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14503,'˫Ѽɽ��',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14504,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14505,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14506,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14507,'ĵ������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14508,'��ľ˹��',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14509,'��̨����',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14510,'�ں���',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14511,'�绯��',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14512,'���˰������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14513,'�Ͼ���',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14514,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14515,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14516,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14517,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14518,'��ͨ��',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14519,'���Ƹ���',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14520,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14521,'�γ���',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14522,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14523,'����',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14524,'̩����',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14525,'��Ǩ��',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14526,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14527,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14528,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14529,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14530,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14531,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14532,'����',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14533,'������',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14534,'��ɽ��',14427,2,0,'2016-08-08 11:41:02','2016-08-18 03:54:41'),(14535,'̨����',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14536,'��ˮ��',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14537,'�Ϸ���',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14538,'�ߺ���',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14539,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14540,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14541,'��ɽ��',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14542,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14543,'ͭ����',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14544,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14545,'��ɽ��',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14546,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14547,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14548,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14549,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14550,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14551,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14552,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14553,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14554,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14555,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14556,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14557,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14558,'Ȫ����',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14559,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14560,'��ƽ��',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14561,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14562,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14563,'�ϲ���',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14564,'��������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14565,'Ƽ����',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14566,'�Ž���',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14567,'������',14427,2,0,'2016-08-08 11:41:03','2016-08-18 03:54:41'),(14568,'ӥ̶��',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14569,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14570,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14571,'�˴���',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14572,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14573,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14574,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14575,'�ൺ��',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14576,'�Ͳ���',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14577,'��ׯ��',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14578,'��Ӫ��',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14579,'��̨��',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14580,'Ϋ����',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14581,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14582,'̩����',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14583,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14584,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14585,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14586,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14587,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14588,'�ĳ���',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14589,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14590,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14591,'֣����',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14592,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14593,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14594,'ƽ��ɽ��',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14595,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14596,'�ױ���',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14597,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14598,'������',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14599,'�����',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14600,'�����',14427,2,0,'2016-08-08 11:41:04','2016-08-18 03:54:41'),(14601,'�����',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14602,'����Ͽ��',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14603,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14604,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14605,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14606,'�ܿ���',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14607,'פ�����',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14608,'��Դ��',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14609,'�人��',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14610,'��ʯ��',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14611,'ʮ����',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14612,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14613,'�˲���',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14614,'�差��',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14615,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14616,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14617,'Т����',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14618,'�Ƹ���',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14619,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14620,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14621,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14622,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14623,'Ǳ����',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14624,'��ũ������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14625,'��ʩ����������������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14626,'��ɳ��',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14627,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14628,'��̶��',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14629,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14630,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14631,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14632,'������',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14633,'�żҽ���',14427,2,0,'2016-08-08 11:41:05','2016-08-18 03:54:41'),(14634,'������',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14635,'������',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14636,'������',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14637,'������',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14638,'¦����',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14639,'��������������������',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14640,'������',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14641,'������',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14642,'�麣��',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14643,'��ͷ��',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14644,'�ع���',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14645,'��ɽ��',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14646,'������',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14647,'տ����',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14648,'ï����',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14649,'������',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14650,'������',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14651,'÷����',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14652,'��β��',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14653,'��Դ��',14427,2,0,'2016-08-08 11:41:06','2016-08-18 03:54:41'),(14654,'������',14427,2,0,'2016-08-08 11:41:07','2016-08-18 03:54:41'),(14655,'��Զ��',14427,2,0,'2016-08-08 11:41:07','2016-08-18 03:54:41'),(14656,'��ݸ��',14427,2,0,'2016-08-08 11:41:07','2016-08-18 03:54:41'),(14657,'��ɽ��',14427,2,0,'2016-08-08 11:41:07','2016-08-18 03:54:41'),(14658,'������',14427,2,0,'2016-08-08 11:41:07','2016-08-18 03:54:41'),(14659,'������',14427,2,0,'2016-08-08 11:41:07','2016-08-18 03:54:41'),(14660,'�Ƹ���',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14661,'������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14662,'�����',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14663,'������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14664,'��ˮ��',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14665,'��������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14666,'������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14667,'��Ҵ��',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14668,'ƽ����',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14669,'��Ȫ��',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14670,'������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14671,'������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14672,'¤����',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14673,'���Ļ���������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14674,'���ϲ���������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14675,'�ɶ���',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14676,'�Թ���',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14677,'��֦����',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14678,'������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14679,'������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14680,'������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14681,'��Ԫ��',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14682,'������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14683,'�ڽ���',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14684,'��ɽ��',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14685,'�ϳ���',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14686,'üɽ��',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14687,'�˱���',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14688,'�㰲��',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14689,'������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14690,'�Ű���',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14691,'������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14692,'������',14427,2,0,'2016-08-08 11:41:08','2016-08-18 03:54:41'),(14693,'���Ӳ���Ǽ��������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14694,'���β���������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14695,'��ɽ����������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14696,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14697,'�ൺ��',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14698,'�Ͳ���',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14699,'��ׯ��',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14700,'��Ӫ��',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14701,'��̨��',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14702,'Ϋ����',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14703,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14704,'̩����',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14705,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14706,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14707,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14708,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14709,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14710,'�ĳ���',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14711,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14712,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14713,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14714,'����ˮ��',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14715,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14716,'��˳��',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14717,'ͭ�ʵ���',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14718,'�Ͻڵ���',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14719,'ǭ���ϲ���������������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14720,'ǭ�������嶱��������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14721,'ǭ�ϲ���������������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14722,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14723,'������',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14724,'��ָɽ��',14427,2,0,'2016-08-08 11:41:09','2016-08-18 03:54:41'),(14725,'����',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14726,'������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14727,'�Ĳ���',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14728,'������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14729,'������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14730,'������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14731,'������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14732,'�Ͳ���',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14733,'�ٸ���',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14734,'��ɳ����������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14735,'��������������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14736,'�ֶ�����������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14737,'��ˮ����������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14738,'��ͤ��������������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14739,'������������������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14740,'������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14741,'������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14742,'��Ϫ��',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14743,'��ɽ��',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14744,'��ͨ��',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14745,'������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14746,'˼é��',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14747,'�ٲ���',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14748,'��ɽ׳������������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14749,'��ӹ���������������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14750,'��˫���ɴ���������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14751,'��������������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14752,'�������������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14753,'�º���徰����������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14754,'ŭ��������������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14755,'�������������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14756,'������',14427,2,0,'2016-08-08 11:41:10','2016-08-18 03:54:41'),(14757,'��������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14758,'��������������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14759,'���ϲ���������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14760,'���ϲ���������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14761,'�������������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14762,'��������������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14763,'�����ɹ������������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14764,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14765,'ͭ����',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14766,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14767,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14768,'μ����',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14769,'�Ӱ���',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14770,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14771,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14772,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14773,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14774,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14775,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14776,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14777,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14778,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14779,'���Ǹ���',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14780,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14781,'�����',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14782,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14783,'��ɫ��',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14784,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14785,'�ӳ���',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14786,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14787,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14788,'������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14789,'��������',14427,2,0,'2016-08-08 11:41:11','2016-08-18 03:54:41'),(14790,'��������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14791,'ɽ�ϵ���',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14792,'�տ������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14793,'�������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14794,'��֥����',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14795,'������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14796,'ʯ��ɽ��',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14797,'������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14798,'��ԭ��',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14799,'������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14800,'��³ľ����',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14801,'����������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14802,'ʯ������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14803,'��������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14804,'ͼľ�����',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14805,'�������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14806,'��³����',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14807,'��������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14808,'��ʲ��',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14809,'������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14810,'������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14811,'��ͼʲ��',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14812,'�������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14813,'������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14814,'������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14815,'��Ȫ��',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14816,'������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14817,'������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14818,'������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14819,'������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14820,'������',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14821,'����̩��',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14822,'���ͺ�����',14427,2,0,'2016-08-08 11:41:12','2016-08-18 03:54:41'),(14823,'��ͷ��',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14824,'�ں���',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14825,'�����',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14826,'ͨ����',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14827,'������˹��',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14828,'���ױ�����',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14829,'�����׶���',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14830,'�����첼��',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14831,'���ֹ�����',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14832,'�˰���',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14833,'��������',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14834,'�����ر�������',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14835,'����ر�������',14427,2,0,'2016-08-08 11:41:13','2016-08-18 03:54:41'),(14836,'�Ӵ���ƫ��-��������ƫ��',-1,0,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14837,'�Ӵ���ƫ��-��������ƫ��-΢��������Ⱥ',14836,1,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14838,'����',14837,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14839,'����',14837,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14840,'�û���ֵ-�����ֵ(RFM)',-1,0,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14841,'�û���ֵ-�����ֵ(RFM)-�ܹ���Ƶ��',14840,1,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14842,'1��',14841,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14843,'2��',14841,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14844,'3��',14841,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14845,'4������',14841,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14846,'�û���ֵ-�����ֵ(RFM)-���һ�ι���',14840,1,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14847,'������',14846,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14848,'��������',14846,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14849,'������',14846,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14850,'�û���ֵ-�����ֵ(RFM)-�¾�����Ƶ��',14840,1,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14851,'1��2��',14850,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14852,'3������',14850,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14853,'�û���ֵ-�����ֵ(RFM)-�ܼƽ��׽��',14840,1,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14854,'50����',14853,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14855,'51-100',14853,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14856,'101-150',14853,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14857,'151-200',14853,2,0,'2016-08-08 11:41:13','2016-08-18 03:48:52'),(14858,'201-300',14853,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14859,'300����',14853,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14860,'�û���ֵ-�����ֵ(RFM)-�͵���',14840,1,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14861,'50����',14860,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14862,'51-100',14860,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14863,'101-150',14860,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14864,'151-200',14860,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14865,'201-300',14860,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14866,'300����',14860,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14867,'Ʒ����ϵǿ��-�û���ʧ����',-1,0,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14868,'Ʒ����ϵǿ��-�û���ʧ����-֧��״̬',14867,1,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14869,'�������',14868,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14870,'���׹ر�',14868,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14871,'��֧��',14868,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14872,'Ʒ����ϵǿ��-�û���ʧ����-������Ⱥ',14867,1,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14873,'��',14872,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52'),(14874,'��',14872,2,0,'2016-08-08 11:41:14','2016-08-18 03:48:52');
/*!40000 ALTER TABLE `taggroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_run_log`
--

DROP TABLE IF EXISTS `task_run_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_run_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` int(11) DEFAULT NULL,
  `task_name` varchar(80) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `start_time` datetime(6) DEFAULT NULL,
  `end_time` datetime(6) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `task_type` tinyint(4) NOT NULL DEFAULT '2' COMMENT '�����Ƿ���ǰ����ʾ,1:��ʾ,2:����ʾ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��̨����ִ����־';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_run_log`
--

LOCK TABLES `task_run_log` WRITE;
/*!40000 ALTER TABLE `task_run_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `task_run_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_schedule`
--

DROP TABLE IF EXISTS `task_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(45) DEFAULT NULL COMMENT '��������',
  `task_descript` varchar(45) DEFAULT NULL COMMENT '��������',
  `task_type` varchar(45) DEFAULT NULL COMMENT '��������',
  `start_time` datetime DEFAULT NULL COMMENT '����ʼʱ�䣬Ϊ�ձ�ʾ��������',
  `end_time` datetime DEFAULT NULL COMMENT '�������ʱ�䣬Ϊ�ձ�ʾû�н���ʱ��',
  `interval_minutes` float DEFAULT NULL COMMENT '�������м��ʱ��,С�ڵ���0��ֻ����1��',
  `schedule` varchar(45) DEFAULT NULL COMMENT 'ִ��ʱ�䰲��cron table,���Ϊ�գ���ʾֻ����1��',
  `service_name` varchar(512) DEFAULT NULL COMMENT '��ʱִ�е�service����',
  `task_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '����״̬,0:������,1:��������',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����,0:����,1:��ɾ��',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `campaign_head_id` int(11) DEFAULT NULL COMMENT '����campagin_head���id',
  `campaign_item_id` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2678 DEFAULT CHARSET=utf8 COMMENT='������ű�';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_schedule`
--

LOCK TABLES `task_schedule` WRITE;
/*!40000 ALTER TABLE `task_schedule` DISABLE KEYS */;
INSERT INTO `task_schedule` VALUES (1082,'�ϴ��ͻ���ǩͬ������',NULL,NULL,NULL,NULL,1,NULL,'originalDataCustomTagScheduleServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1083,'�ϴ����ͳ��ͬ������',NULL,NULL,NULL,NULL,1,NULL,'originalDataArchPointScheduleServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1084,'�ϴ��û���¼ͬ������',NULL,NULL,NULL,NULL,1,NULL,'originalDataLoginScheduleServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1085,'�ϴ���Ա��Ϣͬ������',NULL,NULL,NULL,NULL,1,NULL,'originalDataMemberScheduleServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1086,'�ϴ�֧����¼ͬ������',NULL,NULL,NULL,NULL,1,NULL,'originalDataPaymentScheduleServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1087,'�ϴ��˿�����ͬ������',NULL,NULL,NULL,NULL,1,NULL,'originalDataPopulationServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1088,'�ϴ������¼ͬ������',NULL,NULL,NULL,NULL,1,NULL,'originalDataShoppingScheduleServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1089,'dataPartySyncTaskServiceImpl',NULL,NULL,NULL,NULL,1,NULL,'dataPartySyncTaskServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(1090,'dataPartySyncMongoTaskServiceImpl',NULL,NULL,NULL,NULL,1,NULL,'dataPartySyncMongoTaskServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(1091,'WechatMemberScheduleToPopulationServiceImpl',NULL,NULL,NULL,NULL,1,NULL,'WechatMemberScheduleToPopulationServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1092,'ImgtextAssetSyncServiceImplTask',NULL,NULL,NULL,NULL,1,'','ImgtextAssetSyncServiceImpl',1,1,NULL,'2016-09-05 09:36:08',NULL,NULL),(1098,'��ȡ���˺��б���������',NULL,NULL,NULL,NULL,NULL,'0 0 1 ? * *','GetH5PersonalListImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1099,'GetPersonContactsListTask',NULL,NULL,NULL,NULL,NULL,'0 0 1 ? * *','GetPersonContactsList',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1100,'GetH5PersonalGroupListImplTask',NULL,NULL,NULL,NULL,NULL,'0 0 1 ? * *','GetH5PersonalGroupListImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1101,'WechatToWechatAssetSyncServiceImplTask',NULL,NULL,NULL,NULL,1,'','WechatToWechatAssetSyncServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1102,'GetH5PubListServiceImplTask',NULL,NULL,NULL,NULL,NULL,'0 0 1 ? * *','GetH5PubListServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1103,'GetPubFansListServiceImplTask',NULL,NULL,NULL,NULL,NULL,'0 0 1 ? * *','GetPubFansListServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1232,'DataPartyTagSyncMongoTaskImpl',NULL,NULL,NULL,NULL,2,NULL,'DataPartyTagSyncMongoTaskImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(1233,'DataSegmentSyncTaskServiceImpl',NULL,NULL,NULL,NULL,1,NULL,'DataSegmentSyncTaskServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1234,'DataSegmentClearTaskServiceImpl','ÿ���賿1�����mongodb��segment����',NULL,NULL,NULL,NULL,'0 0 1 ? * *','DataSegmentClearTaskServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1304,'AudienceCountTaskImpl',NULL,NULL,NULL,NULL,1,NULL,'AudienceCountTaskImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1449,'CustomTagAudienceCountTask',NULL,NULL,NULL,NULL,1,NULL,'CustomTagAudienceCountTask',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(1727,'�ϴ��ļ��Զ����ǩͬ������',NULL,NULL,NULL,NULL,1,NULL,'CustomTagMappingSyncTaskServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(2491,'����ÿ��Ĺ��ںţ����ĺŵ����������˺ŵĺ�����',NULL,NULL,'2016-07-26 00:00:00',NULL,1440,NULL,'CacheWechatMemberCountByTypeServiceImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(2500,'consumptionLastTimeTask',NULL,NULL,NULL,NULL,1,'','consumptionLastTimeTagImpl',1,1,NULL,'2016-08-26 12:57:09',NULL,NULL),(2544,'TagDataIsShoppingUserServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 1/8 * * * *','TagDataIsShoppingUserServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2546,'TagDataLastTransTimeServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 2/8 * * * *','TagDataIsShoppingUserServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2547,'TagDataShoppingDataStatusServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 3/8 * * * *','TagDataShoppingDataStatusServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2548,'TagDataSingleMonthShoppingCountServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 4/8 * * * *','TagDataSingleMonthShoppingCountServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2549,'TagDataTotalIncomeAmountServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 5/8 * * * *','TagDataTotalIncomeAmountServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2550,'TagDataTotalShoppingCountServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 6/8 * * * *','TagDataTotalShoppingCountServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2551,'TagDataWeimobServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 7/8 * * * *','TagDataWeimobServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2552,'TagDataAverageIncomeAmountServiceImpl',NULL,NULL,NULL,NULL,NULL,'0 8/8 * * * *','TagDataAverageIncomeAmountServiceImpl',1,1,NULL,'2016-09-01 08:35:46',NULL,NULL),(2602,NULL,NULL,NULL,'2016-08-15 14:56:00','2016-08-22 14:56:00',NULL,NULL,'campaignTriggerTimeTask',1,1,'2016-08-15 14:57:55','2016-08-22 06:56:30',4,'1471244201145'),(2603,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-08-15 14:57:55','2016-08-22 06:56:30',4,'1471244203688'),(2604,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionSaveAudienceTask',1,1,'2016-08-15 14:57:55','2016-08-22 06:56:30',4,'1471244206985'),(2617,'campaignActionPubWechatSendH5Task',NULL,NULL,NULL,NULL,1,NULL,'campaignActionPubWechatSendH5Task',1,1,'2016-08-15 14:59:43','2016-09-05 09:39:48',5,'1471244293612'),(2618,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionSaveAudienceTask',1,1,'2016-08-15 14:59:43','2016-08-22 06:58:30',5,'1471244301610'),(2619,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-08-15 14:59:43','2016-08-22 06:58:30',5,'1471244304672'),(2620,NULL,NULL,NULL,'2016-08-15 14:58:00','2016-08-22 14:58:00',NULL,NULL,'campaignTriggerTimeTask',1,1,'2016-08-15 14:59:43','2016-08-22 06:58:30',5,'1471244331664'),(2624,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-08-17 13:38:37','2016-08-26 12:57:09',10,'1471412145831'),(2625,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionSaveAudienceTask',1,1,'2016-08-17 13:38:37','2016-08-26 12:57:09',10,'1471412195502'),(2626,NULL,NULL,NULL,NULL,NULL,1,NULL,'campaignActionPubWechatSendH5Task',1,1,'2016-08-17 13:38:37','2016-09-05 09:43:44',10,'1471412225493'),(2640,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-09-07 11:22:13','2016-09-07 03:22:16',1,'1473216720873'),(2641,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionPubWechatSendH5Task',1,1,'2016-09-07 11:22:13','2016-09-07 03:22:16',1,'1473218429180'),(2642,NULL,NULL,NULL,'2016-09-07 11:20:00','2016-09-14 11:20:00',NULL,NULL,'campaignTriggerTimeTask',1,1,'2016-09-07 11:22:13','2016-09-07 03:22:13',1,'1473218443596'),(2643,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignAudienceTargetTask',1,1,'2016-09-08 18:17:53','2016-09-12 06:25:42',2,'1473329857332'),(2644,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignTriggerTimeTask',1,1,'2016-09-08 18:22:22','2016-09-08 10:22:24',4,'1473330142260'),(2645,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignAudienceTargetTask',1,1,'2016-09-09 11:13:41','2016-09-12 06:25:43',6,'1473390819119'),(2646,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-09-09 11:14:26','2016-09-12 06:25:44',7,'1473390865451'),(2647,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignDecisionWechatReadTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:45',8,'1473390886031'),(2648,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignDecisionWechatForwardTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:47',8,'1473390887262'),(2649,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignDecisionWechatSubscribeTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:48',8,'1473390888027'),(2650,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignDecisionWechatPrivFriendTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:49',8,'1473390888857'),(2651,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignDecisionTagTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:50',8,'1473390889760'),(2652,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionPrvWechatSendInfoTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:50',8,'1473390904360'),(2653,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionWaitTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:51',8,'1473390905602'),(2654,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionSaveAudienceTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:52',8,'1473390907313'),(2655,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionSetTagTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:53',8,'1473390908559'),(2656,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionPubWechatSendH5Task',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:54',8,'1473390909888'),(2657,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionWechatSendH5Task',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:54',8,'1473390910945'),(2658,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionPrvWechatSendInfoTask',1,1,'2016-09-09 11:16:03','2016-09-12 06:25:55',8,'1473390912161'),(2660,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-09-09 11:40:38','2016-09-12 06:25:56',11,'1473392117431'),(2661,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignDecisionWechatForwardTask',1,1,'2016-09-09 11:40:38','2016-09-12 06:25:57',11,'1473392431784'),(2662,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignDecisionWechatReadTask',1,1,'2016-09-09 11:40:38','2016-09-12 06:25:58',11,'1473392435482'),(2663,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignActionPubWechatSendH5Task',1,1,'2016-09-09 11:40:38','2016-09-12 06:25:59',11,'1473392439809'),(2665,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignDecisionWechatForwardTask',1,1,'2016-09-09 14:36:27','2016-09-09 06:36:27',12,'1473402894135'),(2666,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignAudienceTargetTask',1,1,'2016-09-09 14:36:27','2016-09-09 06:36:27',12,'1473402926413'),(2667,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignTriggerTimeTask',1,1,'2016-09-09 14:36:27','2016-09-09 06:36:27',12,'1473402971769'),(2668,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,0,'2016-09-13 09:06:12','2016-09-13 01:06:12',13,'1473728763367'),(2669,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'campaignAudienceTargetTask',1,0,'2016-09-13 09:06:45','2016-09-13 01:06:45',14,'1473728803121'),(2670,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,0,'2016-09-13 10:16:16','2016-09-13 02:16:16',15,'1473732974467'),(2672,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,0,'2016-09-13 10:19:22','2016-09-13 02:19:22',16,'1473733090565'),(2676,NULL,NULL,NULL,'2016-09-13 00:00:00','2016-09-20 00:00:00',NULL,NULL,'campaignTriggerTimeTask',1,1,'2016-09-13 10:51:50','2016-09-13 02:51:51',17,'1473735070428'),(2677,NULL,NULL,NULL,NULL,NULL,60,NULL,'campaignAudienceTargetTask',1,1,'2016-09-13 10:51:50','2016-09-13 02:51:52',17,'1473735078417');
/*!40000 ALTER TABLE `task_schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_app`
--

DROP TABLE IF EXISTS `tb_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_app` (
  `id` varchar(32) NOT NULL,
  `app_name` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `port` varchar(255) DEFAULT NULL,
  `app_uri` longtext,
  `remark` varchar(255) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  `use_yn` varchar(1) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `eicon` varchar(255) DEFAULT NULL,
  `version` varchar(255) DEFAULT NULL,
  `logout_url` varchar(255) DEFAULT NULL,
  `loginNotifyUrl` longtext,
  `loginFirstSendUrl` longtext,
  `maintenance` varchar(1) DEFAULT NULL,
  `requestStatus` varchar(1) DEFAULT NULL,
  `mt_info` varchar(255) DEFAULT NULL,
  `icon_url` longblob,
  `icon_name` varchar(50) DEFAULT NULL,
  `effect_iconCls` longblob,
  `effect_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_app`
--

LOCK TABLES `tb_app` WRITE;
/*!40000 ALTER TABLE `tb_app` DISABLE KEYS */;
INSERT INTO `tb_app` VALUES ('ff80808155fca4490155fcb0c1110002','DMP',NULL,NULL,NULL,'',1,NULL,'2016-07-18 14:28:10',NULL,'002001',NULL,NULL,NULL,'','DMP','',NULL,'0','0',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbb7f990016','�г�Ӫ��',NULL,NULL,NULL,'',2,NULL,'2016-07-18 14:39:54',NULL,'002001',NULL,NULL,NULL,'','�г�Ӫ��','',NULL,'0','0',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbc0b590018','���ݶ���',NULL,NULL,NULL,'',3,NULL,'2016-07-18 14:40:30',NULL,'002001',NULL,NULL,NULL,'','���ݶ���','',NULL,'0','0',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tb_app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_blacklist_type`
--

DROP TABLE IF EXISTS `tb_blacklist_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_blacklist_type` (
  `id` varchar(32) NOT NULL,
  `black_type` varchar(50) NOT NULL,
  `login_prompt_message` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_blacklist_type`
--

LOCK TABLES `tb_blacklist_type` WRITE;
/*!40000 ALTER TABLE `tb_blacklist_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_blacklist_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_ci_element`
--

DROP TABLE IF EXISTS `tb_ci_element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_ci_element` (
  `id` varchar(32) NOT NULL,
  `element_key` varchar(50) NOT NULL,
  `element_name` varchar(50) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `element_value` varchar(50) DEFAULT NULL,
  `element_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_ci_element`
--

LOCK TABLES `tb_ci_element` WRITE;
/*!40000 ALTER TABLE `tb_ci_element` DISABLE KEYS */;
INSERT INTO `tb_ci_element` VALUES ('007002','portal_toggle_show_flg','һ���˵�ѡ��Ƿ���ʾ˵����Ϣ','false:����ʾ true:��ʾ','true','boolean'),('007003','portal_one_tab_show_flg','һ���˵�ѡ��Ƿ���ʾ','false:��ʾ���� true:��ʾһ��','false','boolean'),('007004','portal_page_show_type_flg','Ҷ�ӽڵ�ѡ��Ƿ���ʾ','false:��ʹ�� true:ʹ��Tab','true','boolean'),('007005','portal_redis_deploy_flg','Portalϵͳ�Ƿ�ʹ��redis����','false:��ʹ�� true:ʹ��  Ӧ��ģ�����û��redis���棬redis���漴ʹ����ΪtrueҲ�޷�ʹ�á�','false','boolean'),('007007','portal_tree_view_app_flg','Portal��ർ���˵��Ƿ���ʾһ���˵�','false:����ʾ true:��ʾ  ���Ӧ��ģ��ֻ��һ���㼶����������ʾ�ڵ�������ʾһ���˵�������ֱ����ʾһ���˵�Ŀ¼�µ�Ӧ�ýڵ㡣','true','boolean'),('007008','portal_login_send_request_flg','Portal��½���Ƿ�Ĭ�Ϸ���APP����','false:��ʹ�� true:ʹ��','true','boolean'),('007009','portal_login_send_request_way','Portal��½��������ʽ','false:JS�˷������� true:JAVA�˷�������   ע��portal_login_send_request_flgΪtrue����Ч��','false','boolean'),('007010','portal_foot_info_flg','Portalҳ���Ƿ���ʾ','false:��ʹ�� true:ʹ��','true','boolean'),('007011','portal_users_registration_protocol_flg','Portal�û�ע��Э���Ƿ���ʾ','false:��ʹ�� true:ʹ��','true','boolean'),('007012','portal_company_registration_flg','Portal��ҵע���Ƿ�ʹ��','false:��ʹ�� true:ʹ��','true','boolean'),('007013','portal_personal_registration_flg','Portal����ע���Ƿ�ʹ��','false:��ʹ�� true:ʹ��ע��h5û�и���ע�ᣬh5ʹ��Ϊfalse��','true','boolean'),('007014','portal_login_way_with_colon_flg','Portal��ҵ����Ա��¼��ʽ�Ƿ��admin','false:��ʹ�� true:ʹ�� ע��h5��¼��ʽ����Ҫð�ţ�h5ʹ��Ϊfalse��','false','boolean'),('007015','portal_company_registration_qy_flg','��ҵע������Ƿ�����ҵ����','false:����ʾ true:��ʾע��h5����ҵע��ָ����ע�ᣬh5ʹ��Ϊfalse��','false','boolean'),('007016','portal_tab_refresh_button_flg','Portal��tab��ǩ�Ƿ���ˢ�°�ť','false:����ʾ true:��ʾע��Ĭ��Ϊfalse��h5ʹ��Ϊtrue��','false','boolean'),('007017','portal_login_frist_send_url','Portal�״ε�½�Ƿ�������','false:������ true:����','false','boolean'),('007018','portal_black_list_flg','Portal�������Ƿ�ʹ��','false:��ʹ�� true:ʹ��','false','boolean'),('007019','portal_data_mode_flg','Portal����ģ���Ƿ�ʹ��','false:��ʹ�� true:ʹ��','false','boolean'),('007020','portal_h5plus_create_flg','H5PLUS�̳Ǵ����û��Ƿ�ʹ��','false:��ʹ�� true:ʹ��','false','boolean');
/*!40000 ALTER TABLE `tb_ci_element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_company`
--

DROP TABLE IF EXISTS `tb_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_company` (
  `id` varchar(255) NOT NULL,
  `company_id` int(11) DEFAULT NULL,
  `company_name` varchar(255) DEFAULT NULL,
  `company_sname` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `detail_address` varchar(255) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `regist_time` datetime DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_insertuser` varchar(255) DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `rx_updateuser` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `companySname` (`company_sname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company`
--

LOCK TABLES `tb_company` WRITE;
/*!40000 ALTER TABLE `tb_company` DISABLE KEYS */;
INSERT INTO `tb_company` VALUES ('004001',1,'�����û�','personal',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tb_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_company_association`
--

DROP TABLE IF EXISTS `tb_company_association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_company_association` (
  `id` varchar(32) NOT NULL,
  `company_id` varchar(50) NOT NULL,
  `column_code` varchar(50) DEFAULT NULL,
  `column_value` varchar(50) DEFAULT NULL,
  `column_length` int(11) DEFAULT NULL,
  `column_name` varchar(50) DEFAULT NULL,
  `column_type` varchar(50) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company_association`
--

LOCK TABLES `tb_company_association` WRITE;
/*!40000 ALTER TABLE `tb_company_association` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_company_association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_company_blacklist`
--

DROP TABLE IF EXISTS `tb_company_blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_company_blacklist` (
  `id` varchar(32) NOT NULL,
  `company_id` varchar(50) NOT NULL,
  `blacktype_id` varchar(50) NOT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company_blacklist`
--

LOCK TABLES `tb_company_blacklist` WRITE;
/*!40000 ALTER TABLE `tb_company_blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_company_blacklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_company_function`
--

DROP TABLE IF EXISTS `tb_company_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_company_function` (
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  `data_id` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `portal` varchar(32) DEFAULT NULL,
  `use_yn` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_nt6nucw4yi0biba4tj0m0tiih` (`company`),
  KEY `FK_5e8f8riu71pmlti6fbleh1ibu` (`portal`),
  CONSTRAINT `FK_5e8f8riu71pmlti6fbleh1ibu` FOREIGN KEY (`portal`) REFERENCES `tb_portal` (`id`),
  CONSTRAINT `FK_nt6nucw4yi0biba4tj0m0tiih` FOREIGN KEY (`company`) REFERENCES `tb_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_company_function`
--

LOCK TABLES `tb_company_function` WRITE;
/*!40000 ALTER TABLE `tb_company_function` DISABLE KEYS */;
INSERT INTO `tb_company_function` VALUES ('07b411aa75894e3d847cba5aeae2ff21',NULL,'PORTAL',1,'003001','2016-08-05 15:00:57','2016-08-05 15:00:57','2016-08-05 15:00:57','004001','003001',NULL),('0a4dd6ee527d46038711a91e93ebe584','2306418c3cbb495094c80efd246575db','PAGE',3,'ff80808155fca4490155fcb2e888000a',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('1a27dbd5095f49c1a41a8c244f7ed3eb','813efce1e5054d739ab1fb14101a186e','PAGE',3,'ff80808155fca4490155fcbdd1c90022',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('20d7413afbb844638416a23af5607cf8','2f874e0be334417796f5a511873bcc09','PAGE',4,'ff80808155fca4490155fcc0f48a0032',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('2306418c3cbb495094c80efd246575db','07b411aa75894e3d847cba5aeae2ff21','APP',1,'ff80808155fca4490155fcb0c1110002',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('2a665bdc6d26437f9aebe776508c1a02','07b411aa75894e3d847cba5aeae2ff21','APP',1,'ff80808155fca4490155fcbc0b590018',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('2f874e0be334417796f5a511873bcc09','2a665bdc6d26437f9aebe776508c1a02','PAGE',2,'ff80808155fca4490155fcc0610a002e',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('45196e2f843b43e59a29f6702882fc7c','2f874e0be334417796f5a511873bcc09','PAGE',3,'ff80808155fca4490155fcc09e220030',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('4a9fb8bcb8c34dfebca664a21cbb8460','a620055a172e4066b943975f6f263d03','PAGE',11,'ff80808155fca4490155fcbd81b40020',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('5278bd909ac048ed97ccc830c8c587b2','0a4dd6ee527d46038711a91e93ebe584','PAGE',8,'ff80808155fca4490155fcb8f1a4000e',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('53a93d9f53974cba841be5a9e454afd9','d54332c919a74cd4a440f1f69f148e43','PAGE',10,'ff80808155fca4490155fcb27d7b0008',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('53d7280b78d749889db337b8c2a91286','a620055a172e4066b943975f6f263d03','PAGE',10,'ff80808155fca4490155fcbd28ac001e',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('5e7fb883761948af92ffa2e42d7240d9','2306418c3cbb495094c80efd246575db','PAGE',4,'ff80808155fca4490155fcb989ff0010',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('5ea382f515ef41f287b3344280ea9177','70a035a27ab64ef3827a5e91861e983a','PAGE',5,'ff80808155fca4490155fcbf4c61002a',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('70a035a27ab64ef3827a5e91861e983a','813efce1e5054d739ab1fb14101a186e','PAGE',4,'ff80808155fca4490155fcbefde50028',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('813efce1e5054d739ab1fb14101a186e','07b411aa75894e3d847cba5aeae2ff21','APP',1,'ff80808155fca4490155fcbb7f990016',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('91fd75eeeebb42c19b46d708bdd10642','1a27dbd5095f49c1a41a8c244f7ed3eb','PAGE',8,'ff80808155fca4490155fcbea6930026',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('94c5eeb8833441ebb1617ea111c35fd8','70a035a27ab64ef3827a5e91861e983a','PAGE',6,'ff80808155fca4490155fcbfc413002c',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('a620055a172e4066b943975f6f263d03','813efce1e5054d739ab1fb14101a186e','PAGE',2,'ff80808155fca4490155fcbc78c5001a',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('a8247629f59e418da5211ea300e9516b','1a27dbd5095f49c1a41a8c244f7ed3eb','PAGE',7,'ff80808155fca4490155fcbe29200024',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('aea3723a0cea4aa5a94f24c2a2104ebc','a620055a172e4066b943975f6f263d03','PAGE',9,'ff80808155fca4490155fcbcb44b001c',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('afba97f417d948ada9d5e66886924794','5e7fb883761948af92ffa2e42d7240d9','PAGE',6,'ff80808155fca4490155fcba83ae0014',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('bb26794800ef4d5b95fb3c4176f64d9f','0a4dd6ee527d46038711a91e93ebe584','PAGE',7,'ff80808155fca4490155fcb88cef000c',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('d54332c919a74cd4a440f1f69f148e43','2306418c3cbb495094c80efd246575db','PAGE',2,'ff80808155fca4490155fcb1b1d40004',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('e88de6b944624f17a5982ba79c8d2a54','d54332c919a74cd4a440f1f69f148e43','PAGE',9,'ff80808155fca4490155fcb1e9230006',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL),('f5c5550cbba34918b085f85941ddf1ff','5e7fb883761948af92ffa2e42d7240d9','PAGE',5,'ff80808155fca4490155fcba1edf0012',NULL,NULL,'2016-08-05 15:00:57','004001','003001',NULL);
/*!40000 ALTER TABLE `tb_company_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_component`
--

DROP TABLE IF EXISTS `tb_component`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_component` (
  `id` varchar(32) NOT NULL,
  `element_name` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `element_uri` varchar(255) DEFAULT NULL,
  `element_authority` varchar(2) DEFAULT '0',
  `element_type_id` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ml34q179ejba9uyabyms8a5ra` (`element_type_id`),
  CONSTRAINT `FK_ml34q179ejba9uyabyms8a5ra` FOREIGN KEY (`element_type_id`) REFERENCES `tb_element_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_component`
--

LOCK TABLES `tb_component` WRITE;
/*!40000 ALTER TABLE `tb_component` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_component` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_contact`
--

DROP TABLE IF EXISTS `tb_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_contact` (
  `id` varchar(32) NOT NULL,
  `contact_name` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `email_addr` varchar(255) DEFAULT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_contact`
--

LOCK TABLES `tb_contact` WRITE;
/*!40000 ALTER TABLE `tb_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_deploy_server_info`
--

DROP TABLE IF EXISTS `tb_deploy_server_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_deploy_server_info` (
  `id` varchar(255) NOT NULL,
  `uri` varchar(50) NOT NULL,
  `remark` varchar(50) DEFAULT NULL,
  `insert_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_deploy_server_info`
--

LOCK TABLES `tb_deploy_server_info` WRITE;
/*!40000 ALTER TABLE `tb_deploy_server_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_deploy_server_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_element`
--

DROP TABLE IF EXISTS `tb_element`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_element` (
  `id` varchar(32) NOT NULL,
  `element_name` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `element_uri` varchar(255) DEFAULT NULL,
  `element_authority` varchar(2) DEFAULT '0',
  `remark` varchar(255) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `element_type_id` varchar(255) DEFAULT NULL,
  `component` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1f9p5st816cqcpntgakeiqks2` (`element_type_id`),
  KEY `FK_1mv3c4jpix6ljdlvieh3rv1vh` (`component`),
  CONSTRAINT `FK_1f9p5st816cqcpntgakeiqks2` FOREIGN KEY (`element_type_id`) REFERENCES `tb_element_type` (`id`),
  CONSTRAINT `FK_1mv3c4jpix6ljdlvieh3rv1vh` FOREIGN KEY (`component`) REFERENCES `tb_component` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_element`
--

LOCK TABLES `tb_element` WRITE;
/*!40000 ALTER TABLE `tb_element` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_element` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_element_type`
--

DROP TABLE IF EXISTS `tb_element_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_element_type` (
  `id` varchar(255) NOT NULL,
  `element_label` varchar(255) DEFAULT NULL,
  `element_value` varchar(255) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_element_type`
--

LOCK TABLES `tb_element_type` WRITE;
/*!40000 ALTER TABLE `tb_element_type` DISABLE KEYS */;
INSERT INTO `tb_element_type` VALUES ('008001','Checkbox','1',NULL,NULL,NULL,NULL),('008002','Radio Button','2',NULL,NULL,NULL,NULL),('008003','Text Field','3',NULL,NULL,NULL,NULL),('008004','Text Area','4',NULL,NULL,NULL,NULL),('008005','Droplist','5',NULL,NULL,NULL,NULL),('008006','List Box','6',NULL,NULL,NULL,NULL),('008007','HTML Button','7',NULL,NULL,NULL,NULL),('008008','ģ��','8',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tb_element_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_function`
--

DROP TABLE IF EXISTS `tb_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_function` (
  `id` varchar(32) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  `data_id` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `app_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qgw2hcxtk82q9c4po8k56905m` (`app_id`),
  CONSTRAINT `FK_qgw2hcxtk82q9c4po8k56905m` FOREIGN KEY (`app_id`) REFERENCES `tb_app` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_function`
--

LOCK TABLES `tb_function` WRITE;
/*!40000 ALTER TABLE `tb_function` DISABLE KEYS */;
INSERT INTO `tb_function` VALUES ('ff80808155fca4490155fcb0c13a0003',NULL,'APP',1,'ff80808155fca4490155fcb0c1110002',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb1b1db0005','ff80808155fca4490155fcb0c13a0003','PAGE',2,'ff80808155fca4490155fcb1b1d40004',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb1e9320007','ff80808155fca4490155fcb1b1db0005','PAGE',9,'ff80808155fca4490155fcb1e9230006',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb27d810009','ff80808155fca4490155fcb1b1db0005','PAGE',10,'ff80808155fca4490155fcb27d7b0008',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb2e8c3000b','ff80808155fca4490155fcb0c13a0003','PAGE',3,'ff80808155fca4490155fcb2e888000a',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb88cff000d','ff80808155fca4490155fcb2e8c3000b','PAGE',7,'ff80808155fca4490155fcb88cef000c',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb8f1aa000f','ff80808155fca4490155fcb2e8c3000b','PAGE',8,'ff80808155fca4490155fcb8f1a4000e',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcb98a040011','ff80808155fca4490155fcb0c13a0003','PAGE',4,'ff80808155fca4490155fcb989ff0010',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcba1ee50013','ff80808155fca4490155fcb98a040011','PAGE',5,'ff80808155fca4490155fcba1edf0012',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcba83b50015','ff80808155fca4490155fcb98a040011','PAGE',6,'ff80808155fca4490155fcba83ae0014',NULL,NULL,'ff80808155fca4490155fcb0c1110002'),('ff80808155fca4490155fcbb7f9d0017',NULL,'APP',1,'ff80808155fca4490155fcbb7f990016',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbc0b5e0019',NULL,'APP',1,'ff80808155fca4490155fcbc0b590018',NULL,NULL,'ff80808155fca4490155fcbc0b590018'),('ff80808155fca4490155fcbc78cb001b','ff80808155fca4490155fcbb7f9d0017','PAGE',2,'ff80808155fca4490155fcbc78c5001a',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbcb451001d','ff80808155fca4490155fcbc78cb001b','PAGE',9,'ff80808155fca4490155fcbcb44b001c',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbd28b1001f','ff80808155fca4490155fcbc78cb001b','PAGE',10,'ff80808155fca4490155fcbd28ac001e',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbd81ba0021','ff80808155fca4490155fcbc78cb001b','PAGE',11,'ff80808155fca4490155fcbd81b40020',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbdd1ce0023','ff80808155fca4490155fcbb7f9d0017','PAGE',3,'ff80808155fca4490155fcbdd1c90022',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbe29270025','ff80808155fca4490155fcbdd1ce0023','PAGE',7,'ff80808155fca4490155fcbe29200024',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbea6980027','ff80808155fca4490155fcbdd1ce0023','PAGE',8,'ff80808155fca4490155fcbea6930026',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbefdeb0029','ff80808155fca4490155fcbb7f9d0017','PAGE',4,'ff80808155fca4490155fcbefde50028',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbf4c67002b','ff80808155fca4490155fcbefdeb0029','PAGE',5,'ff80808155fca4490155fcbf4c61002a',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcbfc417002d','ff80808155fca4490155fcbefdeb0029','PAGE',6,'ff80808155fca4490155fcbfc413002c',NULL,NULL,'ff80808155fca4490155fcbb7f990016'),('ff80808155fca4490155fcc06110002f','ff80808155fca4490155fcbc0b5e0019','PAGE',2,'ff80808155fca4490155fcc0610a002e',NULL,NULL,'ff80808155fca4490155fcbc0b590018'),('ff80808155fca4490155fcc09e270031','ff80808155fca4490155fcc06110002f','PAGE',3,'ff80808155fca4490155fcc09e220030',NULL,NULL,'ff80808155fca4490155fcbc0b590018'),('ff80808155fca4490155fcc0f48e0033','ff80808155fca4490155fcc06110002f','PAGE',4,'ff80808155fca4490155fcc0f48a0032',NULL,NULL,'ff80808155fca4490155fcbc0b590018');
/*!40000 ALTER TABLE `tb_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_industry`
--

DROP TABLE IF EXISTS `tb_industry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_industry` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_industry`
--

LOCK TABLES `tb_industry` WRITE;
/*!40000 ALTER TABLE `tb_industry` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_industry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_job`
--

DROP TABLE IF EXISTS `tb_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_job` (
  `id` varchar(32) NOT NULL,
  `job_id` int(11) DEFAULT NULL,
  `job_name` varchar(50) NOT NULL,
  `team_id` varchar(32) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `rx_inserttime` datetime NOT NULL,
  `rx_updatetime` datetime NOT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ncaa48o1c78fm36bp9007qakg` (`team_id`),
  CONSTRAINT `FK_ncaa48o1c78fm36bp9007qakg` FOREIGN KEY (`team_id`) REFERENCES `tb_team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_job`
--

LOCK TABLES `tb_job` WRITE;
/*!40000 ALTER TABLE `tb_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_job_user`
--

DROP TABLE IF EXISTS `tb_job_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_job_user` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `job_id` varchar(32) DEFAULT NULL,
  `master_job_yn` varchar(1) NOT NULL,
  `rx_inserttime` datetime NOT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cwil3ukuxmbr60u5c17ch1w1s` (`user_id`),
  KEY `FK_is5aweliaumeeg541609ccmu9` (`job_id`),
  CONSTRAINT `FK_cwil3ukuxmbr60u5c17ch1w1s` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FK_is5aweliaumeeg541609ccmu9` FOREIGN KEY (`job_id`) REFERENCES `tb_job` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_job_user`
--

LOCK TABLES `tb_job_user` WRITE;
/*!40000 ALTER TABLE `tb_job_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_job_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_login_page_manage_info`
--

DROP TABLE IF EXISTS `tb_login_page_manage_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_login_page_manage_info` (
  `id` varchar(255) NOT NULL,
  `login_url` varchar(255) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `logo_filename` varchar(50) DEFAULT NULL,
  `logo_image` longblob,
  `welcome_filename` varchar(50) DEFAULT NULL,
  `welcome_image` longblob,
  `footinfo` longtext,
  `insert_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `logo_bg_img` longblob,
  `logo_bg_imgName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_login_page_manage_info`
--

LOCK TABLES `tb_login_page_manage_info` WRITE;
/*!40000 ALTER TABLE `tb_login_page_manage_info` DISABLE KEYS */;
INSERT INTO `tb_login_page_manage_info` VALUES ('2c9485a8504a899401504a8aa2e40000','index.action','portalĬ��URL',NULL,NULL,NULL,NULL,NULL,'2014-09-22 00:00:00','2014-09-22 00:00:00','002001','002001',NULL,NULL);
/*!40000 ALTER TABLE `tb_login_page_manage_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_module`
--

DROP TABLE IF EXISTS `tb_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_module` (
  `id` varchar(32) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `rx_insert_time` datetime DEFAULT NULL,
  `rx_update_time` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_module`
--

LOCK TABLES `tb_module` WRITE;
/*!40000 ALTER TABLE `tb_module` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_operate_history`
--

DROP TABLE IF EXISTS `tb_operate_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_operate_history` (
  `id` varchar(32) NOT NULL,
  `operate_module_id` varchar(32) DEFAULT NULL,
  `operate_module` varchar(32) DEFAULT NULL,
  `operate_type` varchar(32) DEFAULT NULL,
  `operate_time` datetime DEFAULT NULL,
  `data_id_info` longtext,
  `operate_user_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_operate_history`
--

LOCK TABLES `tb_operate_history` WRITE;
/*!40000 ALTER TABLE `tb_operate_history` DISABLE KEYS */;
INSERT INTO `tb_operate_history` VALUES ('ff80808155fca4490155fcc2ffd40034','003001','PORTAL','ADD','2016-07-18 14:48:05','ff80808155fca4490155fcb0c1110002,ff80808155fca4490155fcb1b1d40004,ff80808155fca4490155fcb1e9230006,ff80808155fca4490155fcb27d7b0008,ff80808155fca4490155fcb2e888000a,ff80808155fca4490155fcb88cef000c,ff80808155fca4490155fcb8f1a4000e,ff80808155fca4490155fcb989ff0010,ff80808155fca4490155fcba1edf0012,ff80808155fca4490155fcba83ae0014,ff80808155fca4490155fcbb7f990016,ff80808155fca4490155fcbc78c5001a,ff80808155fca4490155fcbcb44b001c,ff80808155fca4490155fcbd28ac001e,ff80808155fca4490155fcbd81b40020,ff80808155fca4490155fcbdd1c90022,ff80808155fca4490155fcbe29200024,ff80808155fca4490155fcbea6930026,ff80808155fca4490155fcbefde50028,ff80808155fca4490155fcbf4c61002a,ff80808155fca4490155fcbfc413002c,ff80808155fca4490155fcbc0b590018,ff80808155fca4490155fcc0610a002e,ff80808155fca4490155fcc09e220030,ff80808155fca4490155fcc0f48a0032',NULL);
/*!40000 ALTER TABLE `tb_operate_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_page`
--

DROP TABLE IF EXISTS `tb_page`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_page` (
  `id` varchar(32) NOT NULL,
  `page_name` varchar(255) DEFAULT NULL,
  `page_uri` varchar(255) DEFAULT NULL,
  `data_level_id` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `eicon` varchar(255) DEFAULT NULL,
  `maintenance` varchar(1) DEFAULT NULL,
  `requestStatus` varchar(1) DEFAULT NULL,
  `mt_info` varchar(255) DEFAULT NULL,
  `icon_url` longblob,
  `icon_name` varchar(50) DEFAULT NULL,
  `effect_iconCls` longblob,
  `effect_name` varchar(50) DEFAULT NULL,
  `is_open_new` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_page`
--

LOCK TABLES `tb_page` WRITE;
/*!40000 ALTER TABLE `tb_page` DISABLE KEYS */;
INSERT INTO `tb_page` VALUES ('ff80808155fca4490155fcb1b1d40004','���ݽ���','',NULL,NULL,'2016-07-18 14:29:11',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcb1e9230006','�ļ�����','http://mktpro.rc.dataengine.com/html/data-access/file.html',NULL,NULL,'2016-07-18 14:29:25',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcb27d7b0008','΢�Ž���','http://mktpro.rc.dataengine.com/html/data-access/weixin.html',NULL,NULL,'2016-07-18 14:30:03',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcb2e888000a','���ݹ���','',NULL,NULL,'2016-07-18 14:30:31',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcb88cef000c','������������','http://mktpro.rc.dataengine.com/html/data-supervise/quality-report.html',NULL,NULL,'2016-07-18 14:36:41',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcb8f1a4000e','�����ݹ���','http://mktpro.rc.dataengine.com/html/data-supervise/master-data.html',NULL,NULL,'2016-07-18 14:37:06',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcb989ff0010','��ǩ����','',NULL,NULL,'2016-07-18 14:37:45',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcba1edf0012','ϵͳ��ǩ','http://mktpro.rc.dataengine.com/html/label-management/system.html',NULL,NULL,'2016-07-18 14:38:23',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcba83ae0014','�Զ����ǩ','http://mktpro.rc.dataengine.com/html/label-management/custom.html',NULL,NULL,'2016-07-18 14:38:49',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbc78c5001a','���ڹ���','',NULL,NULL,'2016-07-18 14:40:58',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbcb44b001c','ϸ�ֹ���','http://mktpro.rc.dataengine.com/html/audience/manage.html',NULL,NULL,'2016-07-18 14:41:13',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbd28ac001e','����ϸ��','http://mktpro.rc.dataengine.com/html/audience/segment.html',NULL,NULL,'2016-07-18 14:41:43',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbd81b40020','��Ⱥ����','http://mktpro.rc.dataengine.com/html/audience/crowd.html',NULL,NULL,'2016-07-18 14:42:05',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbdd1c90022','Ӫ���','',NULL,NULL,'2016-07-18 14:42:26',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbe29200024','�����','http://mktpro.rc.dataengine.com/html/activity/plan.html',NULL,NULL,'2016-07-18 14:42:48',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbea6930026','�����','http://mktpro.rc.dataengine.com/html/activity/supervise.html',NULL,NULL,'2016-07-18 14:43:20',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbefde50028','�����ʲ�','',NULL,NULL,'2016-07-18 14:43:43',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbf4c61002a','ͼ���ʲ�','http://mktpro.rc.dataengine.com/html/asset/graphic.html',NULL,NULL,'2016-07-18 14:44:03',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcbfc413002c','΢���ʲ�','http://mktpro.rc.dataengine.com/html/asset/weixin.html',NULL,NULL,'2016-07-18 14:44:33',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcc0610a002e','���ݶ���','',NULL,NULL,'2016-07-18 14:45:14',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcc09e220030','�ۺϷ���','http://mktpro.rc.dataengine.com/html/data-lnsight/comprehensive-analysis.html',NULL,NULL,'2016-07-18 14:45:29',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL),('ff80808155fca4490155fcc0f48a0032','���Ʊ���','http://mktpro.rc.dataengine.com/html/data-lnsight/custom-report.html',NULL,NULL,'2016-07-18 14:45:51',NULL,'002001',NULL,NULL,NULL,'0',NULL,'',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `tb_page` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_page_data_level`
--

DROP TABLE IF EXISTS `tb_page_data_level`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_page_data_level` (
  `id` varchar(32) NOT NULL,
  `data_level_name` varchar(100) NOT NULL,
  `use_yn` varchar(1) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `rx_inserttime` datetime NOT NULL,
  `rx_updatetime` datetime NOT NULL,
  `company_id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_page_data_level`
--

LOCK TABLES `tb_page_data_level` WRITE;
/*!40000 ALTER TABLE `tb_page_data_level` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_page_data_level` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_portal`
--

DROP TABLE IF EXISTS `tb_portal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_portal` (
  `id` varchar(32) NOT NULL,
  `ssid` varchar(255) DEFAULT NULL,
  `portal_name` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_portal`
--

LOCK TABLES `tb_portal` WRITE;
/*!40000 ALTER TABLE `tb_portal` DISABLE KEYS */;
INSERT INTO `tb_portal` VALUES ('003001','d39f769bea5c4a13a64e0ddd42b3c2f2','��ѩ','��ѩ','2014-09-22 00:00:00',NULL,'002001',NULL,NULL);
/*!40000 ALTER TABLE `tb_portal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_portal_function`
--

DROP TABLE IF EXISTS `tb_portal_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_portal_function` (
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  `data_id` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `portal` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ieh0o2vnnlk41qlivo3w4s9h` (`portal`),
  CONSTRAINT `FK_ieh0o2vnnlk41qlivo3w4s9h` FOREIGN KEY (`portal`) REFERENCES `tb_portal` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_portal_function`
--

LOCK TABLES `tb_portal_function` WRITE;
/*!40000 ALTER TABLE `tb_portal_function` DISABLE KEYS */;
INSERT INTO `tb_portal_function` VALUES ('0be3a358cbc94ebc826287090baf40e5','95e41bfd9bf64247b87d09dd00caeb69','PAGE',3,'ff80808155fca4490155fcc09e220030',NULL,NULL,'2016-08-05 15:00:57','003001'),('18431dd9aa3e43cfa9b6d630f098fbc3','52d052c6c08c4fcd85fb0a8160d772c7','APP',1,'ff80808155fca4490155fcbb7f990016',NULL,NULL,'2016-08-05 15:00:57','003001'),('23162ce577ea4fcf8c43fd2d88ed66ed','75660553e34e43e1bce07617a1c9c60d','PAGE',9,'ff80808155fca4490155fcbcb44b001c',NULL,NULL,'2016-08-05 15:00:57','003001'),('2b018dbdbdcd4cd09a55e726a4e3bffe','52d052c6c08c4fcd85fb0a8160d772c7','APP',1,'ff80808155fca4490155fcbc0b590018',NULL,NULL,'2016-08-05 15:00:57','003001'),('41cca3ffecdb4a54a1ce77a38a47d1ee','18431dd9aa3e43cfa9b6d630f098fbc3','PAGE',3,'ff80808155fca4490155fcbdd1c90022',NULL,NULL,'2016-08-05 15:00:57','003001'),('458a3fe3b5bb413faca3dfd5d26f9326','d3ebebca4ae24b50b30613354335a46d','PAGE',8,'ff80808155fca4490155fcb8f1a4000e',NULL,NULL,'2016-08-05 15:00:57','003001'),('4d47cacc5659443caf2a42009c93d17d','6d112ac4cc894ca199aa06bec74aae51','PAGE',10,'ff80808155fca4490155fcb27d7b0008',NULL,NULL,'2016-08-05 15:00:57','003001'),('52d052c6c08c4fcd85fb0a8160d772c7',NULL,'PORTAL',1,'003001',NULL,NULL,'2016-08-05 15:00:57','003001'),('5dbb35abaca447c3b9ea420528096439','d1a50df7fa214171ae0ab9cbaf582c6a','PAGE',6,'ff80808155fca4490155fcbfc413002c',NULL,NULL,'2016-08-05 15:00:57','003001'),('5fc7176d93f744b2babb9cbb8b00dde2','52d052c6c08c4fcd85fb0a8160d772c7','APP',1,'ff80808155fca4490155fcb0c1110002',NULL,NULL,'2016-08-05 15:00:57','003001'),('6d112ac4cc894ca199aa06bec74aae51','5fc7176d93f744b2babb9cbb8b00dde2','PAGE',2,'ff80808155fca4490155fcb1b1d40004',NULL,NULL,'2016-08-05 15:00:57','003001'),('749418a096b640a98e21d676d0e7c60b','d1a50df7fa214171ae0ab9cbaf582c6a','PAGE',5,'ff80808155fca4490155fcbf4c61002a',NULL,NULL,'2016-08-05 15:00:57','003001'),('75660553e34e43e1bce07617a1c9c60d','18431dd9aa3e43cfa9b6d630f098fbc3','PAGE',2,'ff80808155fca4490155fcbc78c5001a',NULL,NULL,'2016-08-05 15:00:57','003001'),('95e41bfd9bf64247b87d09dd00caeb69','2b018dbdbdcd4cd09a55e726a4e3bffe','PAGE',2,'ff80808155fca4490155fcc0610a002e',NULL,NULL,'2016-08-05 15:00:57','003001'),('a8c14010f2b149aea46a8c0c80bbd2bc','41cca3ffecdb4a54a1ce77a38a47d1ee','PAGE',8,'ff80808155fca4490155fcbea6930026',NULL,NULL,'2016-08-05 15:00:57','003001'),('b61b3d792f024574b3eb94e3b861a02e','75660553e34e43e1bce07617a1c9c60d','PAGE',10,'ff80808155fca4490155fcbd28ac001e',NULL,NULL,'2016-08-05 15:00:57','003001'),('cf2fcb1c9ea5463b8b1117e3650c22ea','d3ebebca4ae24b50b30613354335a46d','PAGE',7,'ff80808155fca4490155fcb88cef000c',NULL,NULL,'2016-08-05 15:00:57','003001'),('d1a50df7fa214171ae0ab9cbaf582c6a','18431dd9aa3e43cfa9b6d630f098fbc3','PAGE',4,'ff80808155fca4490155fcbefde50028',NULL,NULL,'2016-08-05 15:00:57','003001'),('d3ebebca4ae24b50b30613354335a46d','5fc7176d93f744b2babb9cbb8b00dde2','PAGE',3,'ff80808155fca4490155fcb2e888000a',NULL,NULL,'2016-08-05 15:00:57','003001'),('d639be0bab394ba5ba841d7a9efa6e92','ff27022bc93545128d7b3b57a799d3ff','PAGE',6,'ff80808155fca4490155fcba83ae0014',NULL,NULL,'2016-08-05 15:00:57','003001'),('dc92c7464cf74281b812d6b6feacb5bd','6d112ac4cc894ca199aa06bec74aae51','PAGE',9,'ff80808155fca4490155fcb1e9230006',NULL,NULL,'2016-08-05 15:00:57','003001'),('e808e4a54a784b22aede1fdd11d3bdce','75660553e34e43e1bce07617a1c9c60d','PAGE',11,'ff80808155fca4490155fcbd81b40020',NULL,NULL,'2016-08-05 15:00:57','003001'),('f6309ada530d4615b62d5f1c3d9ecf3c','95e41bfd9bf64247b87d09dd00caeb69','PAGE',4,'ff80808155fca4490155fcc0f48a0032',NULL,NULL,'2016-08-05 15:00:57','003001'),('f6466a3dc0fa43a8897332fae7eb3e76','41cca3ffecdb4a54a1ce77a38a47d1ee','PAGE',7,'ff80808155fca4490155fcbe29200024',NULL,NULL,'2016-08-05 15:00:57','003001'),('fd06b1d829ca44a2a67f0a270b3f1d14','ff27022bc93545128d7b3b57a799d3ff','PAGE',5,'ff80808155fca4490155fcba1edf0012',NULL,NULL,'2016-08-05 15:00:57','003001'),('ff27022bc93545128d7b3b57a799d3ff','5fc7176d93f744b2babb9cbb8b00dde2','PAGE',4,'ff80808155fca4490155fcb989ff0010',NULL,NULL,'2016-08-05 15:00:57','003001');
/*!40000 ALTER TABLE `tb_portal_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role`
--

DROP TABLE IF EXISTS `tb_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role` (
  `id` varchar(32) NOT NULL,
  `role_name` varchar(50) NOT NULL,
  `admin` bit(1) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4qtf6jv3emjrfpar67m64pur1` (`company_id`),
  CONSTRAINT `FK_4qtf6jv3emjrfpar67m64pur1` FOREIGN KEY (`company_id`) REFERENCES `tb_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role`
--

LOCK TABLES `tb_role` WRITE;
/*!40000 ALTER TABLE `tb_role` DISABLE KEYS */;
INSERT INTO `tb_role` VALUES ('010001','commRole','',NULL,'2015-07-16 11:59:55','2015-07-16 11:59:55','002002','002002','004001'),('ff80808155fca4490155fcadb0c00001','tester','\0','���Խ�ɫ','2016-07-18 14:24:49','2016-07-18 14:24:49','002002','002002','004001');
/*!40000 ALTER TABLE `tb_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role_function`
--

DROP TABLE IF EXISTS `tb_role_function`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role_function` (
  `id` varchar(255) NOT NULL,
  `parent_id` varchar(255) DEFAULT NULL,
  `data_level_id` int(11) DEFAULT NULL,
  `data_type` varchar(255) DEFAULT NULL,
  `order_number` int(11) DEFAULT NULL,
  `data_id` varchar(255) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `role` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ejvdsv86i87owr0p3i1i214ne` (`role`),
  CONSTRAINT `FK_ejvdsv86i87owr0p3i1i214ne` FOREIGN KEY (`role`) REFERENCES `tb_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role_function`
--

LOCK TABLES `tb_role_function` WRITE;
/*!40000 ALTER TABLE `tb_role_function` DISABLE KEYS */;
INSERT INTO `tb_role_function` VALUES ('052f7be1bfde49fd9de1ced6af7c70f0','cd27715271ee40699f5d42b83ca39419',1,'PAGE',4,'ff80808155fca4490155fcb989ff0010','2016-08-05 15:00:57','010001'),('15372e00012d47878b1f8f4aeb57309d','95c75a90d0f04e8393bd5d9e0e431180',NULL,'APP',1,'ff80808155fca4490155fcbc0b590018','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('26251ed35e76487dae7f7326f6826582','39b2fa94a195481aafc4d069ff3cc491',1,'PAGE',7,'ff80808155fca4490155fcb88cef000c','2016-08-05 15:00:57','010001'),('2a2af738f86142338f3ea829d1409cea','57bae3cc027d42eba69a294bcd63ab40',1,'PAGE',5,'ff80808155fca4490155fcba1edf0012','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('2e87812263a24489b3a05de72f7b97ca','df81efcb45024c5c8e17b802ee53ba1f',1,'PAGE',9,'ff80808155fca4490155fcbcb44b001c','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('39b2fa94a195481aafc4d069ff3cc491','cd27715271ee40699f5d42b83ca39419',1,'PAGE',3,'ff80808155fca4490155fcb2e888000a','2016-08-05 15:00:57','010001'),('3b2837c77ea54b8198386e689912890d','81f7e938b25342e7984def4db87db2e1',1,'PAGE',2,'ff80808155fca4490155fcb1b1d40004','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('404bb26612d34fcabedbe9c11e3e77fb','acb88d4d08304e5288b4ac55a649bef9',1,'PAGE',8,'ff80808155fca4490155fcb8f1a4000e','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('43addd5f836642f6882824ffe1fa8da8','f8ed07e2ee9d40149edd71d9a607d980',1,'PAGE',10,'ff80808155fca4490155fcbd28ac001e','2016-08-05 15:00:57','010001'),('46ad3daa4c4d4c3da307d6bef155e065','f0c4ba2c431c41f3809af72f5c82b65d',1,'PAGE',9,'ff80808155fca4490155fcb1e9230006','2016-08-05 15:00:57','010001'),('4fe5e6e9a8a94c059d0db2d55af0008d','d52aed1242164946a73ee10690bca403',1,'PAGE',5,'ff80808155fca4490155fcbf4c61002a','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('50689d99c5474d6fa7a9603e25d57b99','f8ed07e2ee9d40149edd71d9a607d980',1,'PAGE',9,'ff80808155fca4490155fcbcb44b001c','2016-08-05 15:00:57','010001'),('57bae3cc027d42eba69a294bcd63ab40','81f7e938b25342e7984def4db87db2e1',1,'PAGE',4,'ff80808155fca4490155fcb989ff0010','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('5c4959bb796f408fa49f0acb647b4a5a','3b2837c77ea54b8198386e689912890d',1,'PAGE',10,'ff80808155fca4490155fcb27d7b0008','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('5d93603854ed4effaad2fd222cef806d','052f7be1bfde49fd9de1ced6af7c70f0',1,'PAGE',5,'ff80808155fca4490155fcba1edf0012','2016-08-05 15:00:57','010001'),('5f8672c9c662423aa940a1cbb8da260f','e2554769d5de403ab8055d1fa2c34133',1,'PAGE',4,'ff80808155fca4490155fcc0f48a0032','2016-08-05 15:00:57','010001'),('5f9498db19544bd9a9ac645bca01a257','d33c948e8b164e15bb7b98d7c962426c',1,'PAGE',3,'ff80808155fca4490155fcbdd1c90022','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('68d2a6e575a94402bc0534c2738f5fd5','null',NULL,'PORTAL',1,'003001','2016-08-05 15:00:57','010001'),('6ca13261f46e4e89b71282fc2be43cdc','052f7be1bfde49fd9de1ced6af7c70f0',1,'PAGE',6,'ff80808155fca4490155fcba83ae0014','2016-08-05 15:00:57','010001'),('7260393f0c4f4ee1ad4a40633b7f3c66','15372e00012d47878b1f8f4aeb57309d',1,'PAGE',2,'ff80808155fca4490155fcc0610a002e','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('7787de480ebc4e82b0172d3416f20c69','68d2a6e575a94402bc0534c2738f5fd5',NULL,'APP',1,'ff80808155fca4490155fcbb7f990016','2016-08-05 15:00:57','010001'),('7a925c7a5d2f46188e17db4ae593c57a','93339804204d4174a7eda74a44483852',1,'PAGE',8,'ff80808155fca4490155fcbea6930026','2016-08-05 15:00:57','010001'),('81f7e938b25342e7984def4db87db2e1','95c75a90d0f04e8393bd5d9e0e431180',NULL,'APP',1,'ff80808155fca4490155fcb0c1110002','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('8d4b66af69b74a64b8605d635bac16a8','f8ed07e2ee9d40149edd71d9a607d980',1,'PAGE',11,'ff80808155fca4490155fcbd81b40020','2016-08-05 15:00:57','010001'),('92dda046c34645898f20ec52bab5e645','f0c4ba2c431c41f3809af72f5c82b65d',1,'PAGE',10,'ff80808155fca4490155fcb27d7b0008','2016-08-05 15:00:57','010001'),('93339804204d4174a7eda74a44483852','7787de480ebc4e82b0172d3416f20c69',1,'PAGE',3,'ff80808155fca4490155fcbdd1c90022','2016-08-05 15:00:57','010001'),('95c75a90d0f04e8393bd5d9e0e431180',NULL,NULL,'PORTAL',1,'003001','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('9ad0e7468d2e459292fb1bb8768ce0ce','d939ebbca7f746a692d0ab03e7c64af2',1,'PAGE',6,'ff80808155fca4490155fcbfc413002c','2016-08-05 15:00:57','010001'),('9adc6fe6f29e4fcabea5d76cd7da146e','df81efcb45024c5c8e17b802ee53ba1f',1,'PAGE',10,'ff80808155fca4490155fcbd28ac001e','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('9d6123dca093473fbcadba002d37d093','df81efcb45024c5c8e17b802ee53ba1f',1,'PAGE',11,'ff80808155fca4490155fcbd81b40020','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('9ef382465c0640018e714c81e7983687','93339804204d4174a7eda74a44483852',1,'PAGE',7,'ff80808155fca4490155fcbe29200024','2016-08-05 15:00:57','010001'),('a77f067607be427ba1fc046a104b8b92','5f9498db19544bd9a9ac645bca01a257',1,'PAGE',8,'ff80808155fca4490155fcbea6930026','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('a8f8e198a662491d9d522c792418210d','3b2837c77ea54b8198386e689912890d',1,'PAGE',9,'ff80808155fca4490155fcb1e9230006','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('acb88d4d08304e5288b4ac55a649bef9','81f7e938b25342e7984def4db87db2e1',1,'PAGE',3,'ff80808155fca4490155fcb2e888000a','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('b7d8195085d94587a613ce7bfda28ffc','5f9498db19544bd9a9ac645bca01a257',1,'PAGE',7,'ff80808155fca4490155fcbe29200024','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('bea51741d7634b888ba506b2f6206d1a','d52aed1242164946a73ee10690bca403',1,'PAGE',6,'ff80808155fca4490155fcbfc413002c','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('bf87e5ab878143e7a1124e60432838e1','7260393f0c4f4ee1ad4a40633b7f3c66',1,'PAGE',4,'ff80808155fca4490155fcc0f48a0032','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('c6532a3317774ebe9b75e7838bc0673e','7260393f0c4f4ee1ad4a40633b7f3c66',1,'PAGE',3,'ff80808155fca4490155fcc09e220030','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('cc849e7163f346a095516f90b61dd82b','57bae3cc027d42eba69a294bcd63ab40',1,'PAGE',6,'ff80808155fca4490155fcba83ae0014','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('cd27715271ee40699f5d42b83ca39419','68d2a6e575a94402bc0534c2738f5fd5',NULL,'APP',1,'ff80808155fca4490155fcb0c1110002','2016-08-05 15:00:57','010001'),('cdad967383394ba1b64c19e73e3aeb40','e2554769d5de403ab8055d1fa2c34133',1,'PAGE',3,'ff80808155fca4490155fcc09e220030','2016-08-05 15:00:57','010001'),('d33c948e8b164e15bb7b98d7c962426c','95c75a90d0f04e8393bd5d9e0e431180',NULL,'APP',1,'ff80808155fca4490155fcbb7f990016','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('d52aed1242164946a73ee10690bca403','d33c948e8b164e15bb7b98d7c962426c',1,'PAGE',4,'ff80808155fca4490155fcbefde50028','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('d939ebbca7f746a692d0ab03e7c64af2','7787de480ebc4e82b0172d3416f20c69',1,'PAGE',4,'ff80808155fca4490155fcbefde50028','2016-08-05 15:00:57','010001'),('ddca1f6d5ccf420791f45a4f2bdc4029','d939ebbca7f746a692d0ab03e7c64af2',1,'PAGE',5,'ff80808155fca4490155fcbf4c61002a','2016-08-05 15:00:57','010001'),('df81efcb45024c5c8e17b802ee53ba1f','d33c948e8b164e15bb7b98d7c962426c',1,'PAGE',2,'ff80808155fca4490155fcbc78c5001a','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('e1268e33c0cd45d3a8cd3c0fc68e85a6','68d2a6e575a94402bc0534c2738f5fd5',NULL,'APP',1,'ff80808155fca4490155fcbc0b590018','2016-08-05 15:00:57','010001'),('e23c40cc394a424f993b9c8956d9d2f6','39b2fa94a195481aafc4d069ff3cc491',1,'PAGE',8,'ff80808155fca4490155fcb8f1a4000e','2016-08-05 15:00:57','010001'),('e2554769d5de403ab8055d1fa2c34133','e1268e33c0cd45d3a8cd3c0fc68e85a6',1,'PAGE',2,'ff80808155fca4490155fcc0610a002e','2016-08-05 15:00:57','010001'),('ef579e1a7e564abeba9b031f240ceb22','acb88d4d08304e5288b4ac55a649bef9',1,'PAGE',7,'ff80808155fca4490155fcb88cef000c','2016-08-05 15:00:57','ff80808155fca4490155fcadb0c00001'),('f0c4ba2c431c41f3809af72f5c82b65d','cd27715271ee40699f5d42b83ca39419',1,'PAGE',2,'ff80808155fca4490155fcb1b1d40004','2016-08-05 15:00:57','010001'),('f8ed07e2ee9d40149edd71d9a607d980','7787de480ebc4e82b0172d3416f20c69',1,'PAGE',2,'ff80808155fca4490155fcbc78c5001a','2016-08-05 15:00:57','010001');
/*!40000 ALTER TABLE `tb_role_function` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_role_user`
--

DROP TABLE IF EXISTS `tb_role_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_role_user` (
  `id` varchar(32) NOT NULL,
  `role_id` varchar(32) DEFAULT NULL,
  `user_id` varchar(32) DEFAULT NULL,
  `rx_inserttime` datetime NOT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_olf42371gohdudfu8xmjrc9xf` (`role_id`),
  KEY `FK_fqson2ux2u8h8whbuht2ciphk` (`user_id`),
  CONSTRAINT `FK_fqson2ux2u8h8whbuht2ciphk` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`),
  CONSTRAINT `FK_olf42371gohdudfu8xmjrc9xf` FOREIGN KEY (`role_id`) REFERENCES `tb_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_role_user`
--

LOCK TABLES `tb_role_user` WRITE;
/*!40000 ALTER TABLE `tb_role_user` DISABLE KEYS */;
INSERT INTO `tb_role_user` VALUES ('010001','010001','002002','2014-09-22 00:00:00','002001'),('ff80808155fca4490155fcc4d2220035','ff80808155fca4490155fcadb0c00001','ff80808155fca4490155fcad0e310000','2016-07-18 14:50:05',NULL),('ff808081565980f9015659831e2c0007','ff80808155fca4490155fcadb0c00001','ff808081565980f901565981f1200000','2016-08-05 15:03:00',NULL),('ff808081565980f901565983363a0008','ff80808155fca4490155fcadb0c00001','ff808081565980f90156598218b80001','2016-08-05 15:03:06',NULL),('ff808081565980f9015659834d9e0009','ff80808155fca4490155fcadb0c00001','ff808081565980f9015659823e010002','2016-08-05 15:03:12',NULL),('ff808081565980f9015659836173000a','ff80808155fca4490155fcadb0c00001','ff808081565980f901565982688f0003','2016-08-05 15:03:17',NULL),('ff808081565980f90156598375c7000b','ff80808155fca4490155fcadb0c00001','ff808081565980f901565982b6aa0004','2016-08-05 15:03:22',NULL),('ff808081565980f9015659838b61000c','ff80808155fca4490155fcadb0c00001','ff808081565980f901565982dbee0005','2016-08-05 15:03:28',NULL),('ff808081565980f901565983a17c000d','ff80808155fca4490155fcadb0c00001','ff808081565980f901565982fe330006','2016-08-05 15:03:34',NULL);
/*!40000 ALTER TABLE `tb_role_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_team`
--

DROP TABLE IF EXISTS `tb_team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_team` (
  `id` varchar(32) NOT NULL,
  `team_name` varchar(50) NOT NULL,
  `sys_yn` varchar(1) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `rx_inserttime` datetime NOT NULL,
  `rx_updatetime` datetime NOT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) NOT NULL,
  `father_team_id` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dr2ggg5p8g5rwe9cn9uuj63um` (`father_team_id`),
  CONSTRAINT `FK_dr2ggg5p8g5rwe9cn9uuj63um` FOREIGN KEY (`father_team_id`) REFERENCES `tb_team` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_team`
--

LOCK TABLES `tb_team` WRITE;
/*!40000 ALTER TABLE `tb_team` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user` (
  `id` varchar(32) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `user_code` varchar(50) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `qq` varchar(15) DEFAULT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `pwd` varchar(100) NOT NULL,
  `question1` varchar(100) DEFAULT NULL,
  `answer1` varchar(100) DEFAULT NULL,
  `question2` varchar(100) DEFAULT NULL,
  `answer2` varchar(100) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `regist_time` datetime DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `rx_inserttime` datetime DEFAULT NULL,
  `rx_updatetime` datetime DEFAULT NULL,
  `create_user_id` varchar(255) DEFAULT NULL,
  `update_user_id` varchar(255) DEFAULT NULL,
  `company_id` varchar(255) DEFAULT NULL,
  `failure_validate_count` int(11) DEFAULT NULL,
  `h5_regist_flg` bit(1) DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_code` (`user_code`,`company_id`),
  KEY `FK_ght6skmy57ndsswrvm2sxtuu9` (`company_id`),
  CONSTRAINT `FK_ght6skmy57ndsswrvm2sxtuu9` FOREIGN KEY (`company_id`) REFERENCES `tb_company` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES ('002001',1,'admin','����Ա',NULL,NULL,NULL,'abc.1234',NULL,NULL,NULL,NULL,NULL,NULL,'2016-07-22 13:44:15',NULL,NULL,NULL,NULL,NULL,0,'\0'),('002002',2,'admin','��ҵ����Ա',NULL,NULL,NULL,'abc.1234',NULL,NULL,NULL,NULL,NULL,NULL,'2016-08-05 15:00:56',NULL,NULL,NULL,NULL,'004001',0,'\0'),('ff80808155fca4490155fcad0e310000',3,'tester1','����1','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,'2016-07-18 15:07:29','2016-07-18 14:24:07','2016-07-18 14:24:07',NULL,'002002','004001',0,'\0'),('ff808081565980f901565981f1200000',4,'tester2','����2','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,'2016-08-05 15:03:48','2016-08-05 15:01:43','2016-08-05 15:01:43',NULL,'002002','004001',0,'\0'),('ff808081565980f90156598218b80001',5,'tester3','����3','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,NULL,'2016-08-05 15:01:53','2016-08-05 15:01:53',NULL,'002002','004001',0,'\0'),('ff808081565980f9015659823e010002',6,'tester4','����4','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,NULL,'2016-08-05 15:02:03','2016-08-05 15:02:03',NULL,'002002','004001',0,'\0'),('ff808081565980f901565982688f0003',7,'tester5','����5','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,NULL,'2016-08-05 15:02:13','2016-08-05 15:02:13',NULL,'002002','004001',0,'\0'),('ff808081565980f901565982b6aa0004',8,'tester6','����6','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,NULL,'2016-08-05 15:02:33','2016-08-05 15:02:33',NULL,'002002','004001',0,'\0'),('ff808081565980f901565982dbee0005',9,'tester7','����7','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,NULL,'2016-08-05 15:02:43','2016-08-05 15:02:43',NULL,'002002','004001',0,'\0'),('ff808081565980f901565982fe330006',10,'tester8','����8','','','','abc.1234',NULL,NULL,NULL,NULL,'',NULL,NULL,'2016-08-05 15:02:52','2016-08-05 15:02:52',NULL,'002002','004001',0,'\0');
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_association`
--

DROP TABLE IF EXISTS `tb_user_association`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_association` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `column_code` varchar(50) DEFAULT NULL,
  `column_value` varchar(50) DEFAULT NULL,
  `column_length` int(11) DEFAULT NULL,
  `column_name` varchar(50) DEFAULT NULL,
  `column_type` varchar(50) DEFAULT NULL,
  `remark` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_association`
--

LOCK TABLES `tb_user_association` WRITE;
/*!40000 ALTER TABLE `tb_user_association` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user_association` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_blacklist`
--

DROP TABLE IF EXISTS `tb_user_blacklist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_blacklist` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `blacktype_id` varchar(50) NOT NULL,
  `company_mark` varchar(50) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_blacklist`
--

LOCK TABLES `tb_user_blacklist` WRITE;
/*!40000 ALTER TABLE `tb_user_blacklist` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user_blacklist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_login_frist_send_url_history`
--

DROP TABLE IF EXISTS `tb_user_login_frist_send_url_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_user_login_frist_send_url_history` (
  `id` varchar(32) NOT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `url` longtext,
  `send_flg` bit(1) DEFAULT NULL,
  `app_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_login_frist_send_url_history`
--

LOCK TABLES `tb_user_login_frist_send_url_history` WRITE;
/*!40000 ALTER TABLE `tb_user_login_frist_send_url_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user_login_frist_send_url_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tenement`
--

DROP TABLE IF EXISTS `tenement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tenement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '��ҵ����',
  `shortname` varchar(50) DEFAULT NULL COMMENT '��ҵ���',
  `pid` varchar(45) NOT NULL COMMENT '��ҵע��H5plus��pid',
  `email` varchar(100) DEFAULT NULL COMMENT '��ҵ����',
  `mobile` varchar(45) DEFAULT NULL COMMENT '�������ֻ�',
  `province` varchar(100) DEFAULT NULL COMMENT 'ʡ',
  `city` varchar(100) DEFAULT NULL COMMENT '��',
  `area` varchar(100) DEFAULT NULL COMMENT '��',
  `address` varchar(200) DEFAULT NULL COMMENT '��ַ',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `session_token` varchar(200) DEFAULT NULL,
  `bas_id` varchar(100) DEFAULT NULL,
  `register_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:����ע��΢��pid����Ϣ  1:����ע��bas_id����Ϣ',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pid_UNIQUE` (`pid`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='��ҵ�ͻ�(�⻧)��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tenement`
--

LOCK TABLES `tenement` WRITE;
/*!40000 ALTER TABLE `tenement` DISABLE KEYS */;
INSERT INTO `tenement` VALUES (1,NULL,'h5','55cbf3a3986a9b483376f279',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'2016-06-16 08:09:15',NULL,NULL,0),(2,'TestBasTokenInMC9','tBASTokenInMC9','',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'2016-07-19 11:03:01','02o+Yn2wYHHsjQUd9iQXnIoCAM/zL+beaI9UeyaBWnBhqZpKbD/yJt0g==','3f64e87df34f4918b5e3da59a3c8b0ec',1);
/*!40000 ALTER TABLE `tenement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(40) DEFAULT NULL,
  `user_id` varchar(100) DEFAULT NULL COMMENT 'email/mobile',
  `role_id` bigint(20) DEFAULT NULL,
  `icon` varchar(100) DEFAULT NULL,
  `passwd` varchar(64) DEFAULT NULL,
  `status` tinyint(4) NOT NULL,
  `created_time` datetime DEFAULT '0000-00-00 00:00:00',
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='�û���';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Jason','123456',1,'sdfs','123456',0,'2016-05-04 16:17:12','2016-05-04 16:17:12');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role_relation`
--

DROP TABLE IF EXISTS `user_role_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role_relation` (
  `id` int(11) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='�û���ɫ��ϵ��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role_relation`
--

LOCK TABLES `user_role_relation` WRITE;
/*!40000 ALTER TABLE `user_role_relation` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_role_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webchart_event_msg_log`
--

DROP TABLE IF EXISTS `webchart_event_msg_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webchart_event_msg_log` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `msg_id` varchar(255) DEFAULT NULL,
  `from_user_name` varchar(255) DEFAULT NULL,
  `to_user_name` varchar(255) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `event` varchar(1024) DEFAULT NULL,
  `event_key` varchar(255) DEFAULT NULL,
  `ticket` text,
  `latitude` varchar(255) DEFAULT NULL,
  `longitude` varchar(255) DEFAULT NULL,
  `precision` varchar(255) DEFAULT NULL COMMENT '����λ�þ���',
  `menuId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webchart_event_msg_log`
--

LOCK TABLES `webchart_event_msg_log` WRITE;
/*!40000 ALTER TABLE `webchart_event_msg_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `webchart_event_msg_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webchat_auth_info`
--

DROP TABLE IF EXISTS `webchat_auth_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webchat_auth_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authorizer_appid` varchar(255) DEFAULT NULL,
  `authorizer_access_token` varchar(255) DEFAULT NULL,
  `authorizer_refresh_token` varchar(255) DEFAULT NULL,
  `expires_in` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webchat_auth_info`
--

LOCK TABLES `webchat_auth_info` WRITE;
/*!40000 ALTER TABLE `webchat_auth_info` DISABLE KEYS */;
INSERT INTO `webchat_auth_info` VALUES (1,'wxa5fb7dea54673299','NkZQUdGdGhyA96nE1uvcOyPabtCvPYmslQghHAzT-_Z_7rdnK0RH43kOzEO_NjhIWEhKqyo5pUK7M8gYCjHq13IOSVofLpX7tj7QauR3d_uylN9H6dfoChZ-nb4Fuw8JQBLeAHDYJT','refreshtoken@@@MD3IehgTfUpPdnJrz8E_qyeuYHeDQRTxG184-_UKJkI','7200'),(2,'wxeb10897c0cd98e36','If5p0tuPYEc2Zcb-VQ0BcS8JSVXb2ONlmOJsH_f1y0Cw0h0exu69KcTGfdpPn9WCZBxPjI7BuvyY57l-V90HJl2ePE0jX8UqodADMHr00wcTq1vyNafhqUTjgxXrcarbNQEdAGDSVJ','refreshtoken@@@kkMVjC90JS9ooW_zsUUhfvjFbwAlfvB9pmBTZArGYMM','7200'),(3,'wx1f363449a14a1ad8','OOoupTS7zzW1jXgIGZadH5Donw7VW5PLwe1UroCCXPPzudVkBAu0z5AETTILdZ1KzY6w-vuAGRfqGwUQeIq3fX52SXIxgS02ugBQaD9-uUL2taxbXcAaRm1sg6ZW-ju3JZBiAMDNDF','refreshtoken@@@gcxmruaeql5C84jx-VHSnt99pOxbEWycsHz7tKgL-ao','7200');
/*!40000 ALTER TABLE `webchat_auth_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `webchat_component_verify_ticket`
--

DROP TABLE IF EXISTS `webchat_component_verify_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `webchat_component_verify_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `app_id` varchar(256) DEFAULT NULL,
  `create_time` bigint(20) DEFAULT NULL,
  `info_type` varchar(255) DEFAULT NULL,
  `component_verify_ticket` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1556 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `webchat_component_verify_ticket`
--

LOCK TABLES `webchat_component_verify_ticket` WRITE;
/*!40000 ALTER TABLE `webchat_component_verify_ticket` DISABLE KEYS */;
INSERT INTO `webchat_component_verify_ticket` VALUES (9,'wx00f7d56d549f82ce',20160827152326,'component_verify_ticket','ticket@@@86UEaYgnQWa5DXjOueoLsXTTq2SAomGW9xIOrbiWiLpaSjSHeYW8ckPpak5XBFVIaerwc41XXr_XIR6eycekLQ'),(10,'wx00f7d56d549f82ce',20160827153321,'component_verify_ticket','ticket@@@86UEaYgnQWa5DXjOueoLsXTTq2SAomGW9xIOrbiWiLpaSjSHeYW8ckPpak5XBFVIaerwc41XXr_XIR6eycekLQ'),(11,'wx00f7d56d549f82ce',20160827154324,'component_verify_ticket','ticket@@@86UEaYgnQWa5DXjOueoLsXTTq2SAomGW9xIOrbiWiLpaSjSHeYW8ckPpak5XBFVIaerwc41XXr_XIR6eycekLQ'),(12,'wx00f7d56d549f82ce',20160827155324,'component_verify_ticket','ticket@@@86UEaYgnQWa5DXjOueoLsXTTq2SAomGW9xIOrbiWiLpaSjSHeYW8ckPpak5XBFVIaerwc41XXr_XIR6eycekLQ'),(13,'wx00f7d56d549f82ce',20160827160323,'component_verify_ticket','ticket@@@3LIhwhd6E2XGisKXSHpeymDJQ4tCAH5LRxDBDrhMOcs16nWntteJXa9xyS5sGDOd6VB01SDIkZojG4G3N0n9SA'),(14,'wx00f7d56d549f82ce',20160827161328,'component_verify_ticket','ticket@@@3LIhwhd6E2XGisKXSHpeymDJQ4tCAH5LRxDBDrhMOcs16nWntteJXa9xyS5sGDOd6VB01SDIkZojG4G3N0n9SA'),(15,'wx00f7d56d549f82ce',20160827162325,'component_verify_ticket','ticket@@@3LIhwhd6E2XGisKXSHpeymDJQ4tCAH5LRxDBDrhMOcs16nWntteJXa9xyS5sGDOd6VB01SDIkZojG4G3N0n9SA'),(16,'wx00f7d56d549f82ce',20160827163324,'component_verify_ticket','ticket@@@3LIhwhd6E2XGisKXSHpeymDJQ4tCAH5LRxDBDrhMOcs16nWntteJXa9xyS5sGDOd6VB01SDIkZojG4G3N0n9SA'),(17,'wx00f7d56d549f82ce',20160827164321,'component_verify_ticket','ticket@@@3LIhwhd6E2XGisKXSHpeymDJQ4tCAH5LRxDBDrhMOcs16nWntteJXa9xyS5sGDOd6VB01SDIkZojG4G3N0n9SA'),(18,'wx00f7d56d549f82ce',20160827165322,'component_verify_ticket','ticket@@@3LIhwhd6E2XGisKXSHpeymDJQ4tCAH5LRxDBDrhMOcs16nWntteJXa9xyS5sGDOd6VB01SDIkZojG4G3N0n9SA'),(19,'wx00f7d56d549f82ce',20160827170326,'component_verify_ticket','ticket@@@xGsSmdP-udpRYZdEPwqAkd7AsQ3iyZIIDtwno9l4DADzFUk9pZYlhXhjQaWiBMoPZZduDvFiyUZ6zQNweNyVdQ'),(20,'wx00f7d56d549f82ce',20160827171325,'component_verify_ticket','ticket@@@xGsSmdP-udpRYZdEPwqAkd7AsQ3iyZIIDtwno9l4DADzFUk9pZYlhXhjQaWiBMoPZZduDvFiyUZ6zQNweNyVdQ'),(21,'wx00f7d56d549f82ce',20160827172322,'component_verify_ticket','ticket@@@xGsSmdP-udpRYZdEPwqAkd7AsQ3iyZIIDtwno9l4DADzFUk9pZYlhXhjQaWiBMoPZZduDvFiyUZ6zQNweNyVdQ'),(22,'wx00f7d56d549f82ce',20160827173327,'component_verify_ticket','ticket@@@xGsSmdP-udpRYZdEPwqAkd7AsQ3iyZIIDtwno9l4DADzFUk9pZYlhXhjQaWiBMoPZZduDvFiyUZ6zQNweNyVdQ'),(23,'wx00f7d56d549f82ce',20160827174325,'component_verify_ticket','ticket@@@xGsSmdP-udpRYZdEPwqAkd7AsQ3iyZIIDtwno9l4DADzFUk9pZYlhXhjQaWiBMoPZZduDvFiyUZ6zQNweNyVdQ'),(24,'wx00f7d56d549f82ce',20160827175325,'component_verify_ticket','ticket@@@xGsSmdP-udpRYZdEPwqAkd7AsQ3iyZIIDtwno9l4DADzFUk9pZYlhXhjQaWiBMoPZZduDvFiyUZ6zQNweNyVdQ'),(25,'wx00f7d56d549f82ce',20160827180327,'component_verify_ticket','ticket@@@-Qb8TutHztbkyJZMC21Ccbt1R-uHAebbbARtE2B8zaf9Or1BP0Swwh7LOsTQ_MpAOUgkeQtddiXDXh5Nr3zPxg'),(26,'wx00f7d56d549f82ce',20160827181324,'component_verify_ticket','ticket@@@-Qb8TutHztbkyJZMC21Ccbt1R-uHAebbbARtE2B8zaf9Or1BP0Swwh7LOsTQ_MpAOUgkeQtddiXDXh5Nr3zPxg'),(27,'wx00f7d56d549f82ce',20160827182325,'component_verify_ticket','ticket@@@-Qb8TutHztbkyJZMC21Ccbt1R-uHAebbbARtE2B8zaf9Or1BP0Swwh7LOsTQ_MpAOUgkeQtddiXDXh5Nr3zPxg'),(28,'wx00f7d56d549f82ce',20160827183324,'component_verify_ticket','ticket@@@-Qb8TutHztbkyJZMC21Ccbt1R-uHAebbbARtE2B8zaf9Or1BP0Swwh7LOsTQ_MpAOUgkeQtddiXDXh5Nr3zPxg'),(29,'wx00f7d56d549f82ce',20160827184323,'component_verify_ticket','ticket@@@-Qb8TutHztbkyJZMC21Ccbt1R-uHAebbbARtE2B8zaf9Or1BP0Swwh7LOsTQ_MpAOUgkeQtddiXDXh5Nr3zPxg'),(30,'wx00f7d56d549f82ce',20160827185324,'component_verify_ticket','ticket@@@-Qb8TutHztbkyJZMC21Ccbt1R-uHAebbbARtE2B8zaf9Or1BP0Swwh7LOsTQ_MpAOUgkeQtddiXDXh5Nr3zPxg'),(31,'wx00f7d56d549f82ce',20160827190322,'component_verify_ticket','ticket@@@yP71ewSkvPCLmKGhzoeh1CoIdVmxGnwWdN33BJ8Xcx1X3Ta6GYwv0LPx2qUDMgDa2DuycxvUFTvXMBNYoRkUpA'),(32,'wx00f7d56d549f82ce',20160827191324,'component_verify_ticket','ticket@@@yP71ewSkvPCLmKGhzoeh1CoIdVmxGnwWdN33BJ8Xcx1X3Ta6GYwv0LPx2qUDMgDa2DuycxvUFTvXMBNYoRkUpA'),(33,'wx00f7d56d549f82ce',20160827192326,'component_verify_ticket','ticket@@@yP71ewSkvPCLmKGhzoeh1CoIdVmxGnwWdN33BJ8Xcx1X3Ta6GYwv0LPx2qUDMgDa2DuycxvUFTvXMBNYoRkUpA'),(34,'wx00f7d56d549f82ce',20160827193322,'component_verify_ticket','ticket@@@yP71ewSkvPCLmKGhzoeh1CoIdVmxGnwWdN33BJ8Xcx1X3Ta6GYwv0LPx2qUDMgDa2DuycxvUFTvXMBNYoRkUpA'),(35,'wx00f7d56d549f82ce',20160827194325,'component_verify_ticket','ticket@@@yP71ewSkvPCLmKGhzoeh1CoIdVmxGnwWdN33BJ8Xcx1X3Ta6GYwv0LPx2qUDMgDa2DuycxvUFTvXMBNYoRkUpA'),(36,'wx00f7d56d549f82ce',20160827195324,'component_verify_ticket','ticket@@@yP71ewSkvPCLmKGhzoeh1CoIdVmxGnwWdN33BJ8Xcx1X3Ta6GYwv0LPx2qUDMgDa2DuycxvUFTvXMBNYoRkUpA'),(37,'wx00f7d56d549f82ce',20160827200325,'component_verify_ticket','ticket@@@c83F5_lmt_e8rY2fi-lAITHjs-c0IrFhqKp90FbbXyrn_alAWIYkCzLG_x8cM4KgJOfDJ30cIBMi5jfIvm3SvQ'),(38,'wx00f7d56d549f82ce',20160827201324,'component_verify_ticket','ticket@@@c83F5_lmt_e8rY2fi-lAITHjs-c0IrFhqKp90FbbXyrn_alAWIYkCzLG_x8cM4KgJOfDJ30cIBMi5jfIvm3SvQ'),(39,'wx00f7d56d549f82ce',20160827202327,'component_verify_ticket','ticket@@@c83F5_lmt_e8rY2fi-lAITHjs-c0IrFhqKp90FbbXyrn_alAWIYkCzLG_x8cM4KgJOfDJ30cIBMi5jfIvm3SvQ'),(40,'wx00f7d56d549f82ce',20160827203341,'component_verify_ticket','ticket@@@c83F5_lmt_e8rY2fi-lAITHjs-c0IrFhqKp90FbbXyrn_alAWIYkCzLG_x8cM4KgJOfDJ30cIBMi5jfIvm3SvQ'),(41,'wx00f7d56d549f82ce',20160827204341,'component_verify_ticket','ticket@@@c83F5_lmt_e8rY2fi-lAITHjs-c0IrFhqKp90FbbXyrn_alAWIYkCzLG_x8cM4KgJOfDJ30cIBMi5jfIvm3SvQ'),(42,'wx00f7d56d549f82ce',20160827205330,'component_verify_ticket','ticket@@@c83F5_lmt_e8rY2fi-lAITHjs-c0IrFhqKp90FbbXyrn_alAWIYkCzLG_x8cM4KgJOfDJ30cIBMi5jfIvm3SvQ'),(43,'wx00f7d56d549f82ce',20160827210408,'component_verify_ticket','ticket@@@hgPG2VDtEqdtI5subMyk6z8pF5De_5hYBr6Xjj28DUwoaa9VPm5ILyU8e6w_3Iletm8FheUtmLSqvoj-MGsewQ'),(44,'wx00f7d56d549f82ce',20160827211428,'component_verify_ticket','ticket@@@hgPG2VDtEqdtI5subMyk6z8pF5De_5hYBr6Xjj28DUwoaa9VPm5ILyU8e6w_3Iletm8FheUtmLSqvoj-MGsewQ'),(45,'wx00f7d56d549f82ce',20160827212429,'component_verify_ticket','ticket@@@hgPG2VDtEqdtI5subMyk6z8pF5De_5hYBr6Xjj28DUwoaa9VPm5ILyU8e6w_3Iletm8FheUtmLSqvoj-MGsewQ'),(46,'wx00f7d56d549f82ce',20160827213345,'component_verify_ticket','ticket@@@hgPG2VDtEqdtI5subMyk6z8pF5De_5hYBr6Xjj28DUwoaa9VPm5ILyU8e6w_3Iletm8FheUtmLSqvoj-MGsewQ'),(47,'wx00f7d56d549f82ce',20160827214331,'component_verify_ticket','ticket@@@hgPG2VDtEqdtI5subMyk6z8pF5De_5hYBr6Xjj28DUwoaa9VPm5ILyU8e6w_3Iletm8FheUtmLSqvoj-MGsewQ'),(48,'wx00f7d56d549f82ce',20160827215347,'component_verify_ticket','ticket@@@hgPG2VDtEqdtI5subMyk6z8pF5De_5hYBr6Xjj28DUwoaa9VPm5ILyU8e6w_3Iletm8FheUtmLSqvoj-MGsewQ'),(49,'wx00f7d56d549f82ce',20160827220401,'component_verify_ticket','ticket@@@4lYT1OApnZsY2aP68GJg8Ko1ne-j2EGtrEfM6IRgRwrFGd2lUQ6ach1Rx99kt1XuKebp3Nyj7N35qXNr5brkcg'),(50,'wx00f7d56d549f82ce',20160827221423,'component_verify_ticket','ticket@@@4lYT1OApnZsY2aP68GJg8Ko1ne-j2EGtrEfM6IRgRwrFGd2lUQ6ach1Rx99kt1XuKebp3Nyj7N35qXNr5brkcg'),(51,'wx00f7d56d549f82ce',20160827222413,'component_verify_ticket','ticket@@@4lYT1OApnZsY2aP68GJg8Ko1ne-j2EGtrEfM6IRgRwrFGd2lUQ6ach1Rx99kt1XuKebp3Nyj7N35qXNr5brkcg'),(52,'wx00f7d56d549f82ce',20160827223330,'component_verify_ticket','ticket@@@4lYT1OApnZsY2aP68GJg8Ko1ne-j2EGtrEfM6IRgRwrFGd2lUQ6ach1Rx99kt1XuKebp3Nyj7N35qXNr5brkcg'),(53,'wx00f7d56d549f82ce',20160827224335,'component_verify_ticket','ticket@@@4lYT1OApnZsY2aP68GJg8Ko1ne-j2EGtrEfM6IRgRwrFGd2lUQ6ach1Rx99kt1XuKebp3Nyj7N35qXNr5brkcg'),(54,'wx00f7d56d549f82ce',20160827225331,'component_verify_ticket','ticket@@@4lYT1OApnZsY2aP68GJg8Ko1ne-j2EGtrEfM6IRgRwrFGd2lUQ6ach1Rx99kt1XuKebp3Nyj7N35qXNr5brkcg'),(55,'wx00f7d56d549f82ce',20160827230339,'component_verify_ticket','ticket@@@WgdjqRph0AvxZVKbO4hPggPc-ICNBLSeesU5XuSnfqf4P8tiODKgZOJX02GQ4vyPZXUjgqv_26ZaEs7K0qV-UQ'),(56,'wx00f7d56d549f82ce',20160827231341,'component_verify_ticket','ticket@@@WgdjqRph0AvxZVKbO4hPggPc-ICNBLSeesU5XuSnfqf4P8tiODKgZOJX02GQ4vyPZXUjgqv_26ZaEs7K0qV-UQ'),(57,'wx00f7d56d549f82ce',20160827232329,'component_verify_ticket','ticket@@@WgdjqRph0AvxZVKbO4hPggPc-ICNBLSeesU5XuSnfqf4P8tiODKgZOJX02GQ4vyPZXUjgqv_26ZaEs7K0qV-UQ'),(58,'wx00f7d56d549f82ce',20160827233330,'component_verify_ticket','ticket@@@WgdjqRph0AvxZVKbO4hPggPc-ICNBLSeesU5XuSnfqf4P8tiODKgZOJX02GQ4vyPZXUjgqv_26ZaEs7K0qV-UQ'),(59,'wx00f7d56d549f82ce',20160827234333,'component_verify_ticket','ticket@@@WgdjqRph0AvxZVKbO4hPggPc-ICNBLSeesU5XuSnfqf4P8tiODKgZOJX02GQ4vyPZXUjgqv_26ZaEs7K0qV-UQ'),(60,'wx00f7d56d549f82ce',20160827235329,'component_verify_ticket','ticket@@@WgdjqRph0AvxZVKbO4hPggPc-ICNBLSeesU5XuSnfqf4P8tiODKgZOJX02GQ4vyPZXUjgqv_26ZaEs7K0qV-UQ'),(61,'wx00f7d56d549f82ce',20160828000330,'component_verify_ticket','ticket@@@A70hOQz0zo6NkD9OQyTk4li_nriGhtenRs96iwxWCUdAcpyrW_OapuNFjmq4ZvNzWWD4yHkB_DZDsPr2cFrr-Q'),(62,'wx00f7d56d549f82ce',20160828001333,'component_verify_ticket','ticket@@@A70hOQz0zo6NkD9OQyTk4li_nriGhtenRs96iwxWCUdAcpyrW_OapuNFjmq4ZvNzWWD4yHkB_DZDsPr2cFrr-Q'),(63,'wx00f7d56d549f82ce',20160828002329,'component_verify_ticket','ticket@@@A70hOQz0zo6NkD9OQyTk4li_nriGhtenRs96iwxWCUdAcpyrW_OapuNFjmq4ZvNzWWD4yHkB_DZDsPr2cFrr-Q'),(64,'wx00f7d56d549f82ce',20160828003330,'component_verify_ticket','ticket@@@A70hOQz0zo6NkD9OQyTk4li_nriGhtenRs96iwxWCUdAcpyrW_OapuNFjmq4ZvNzWWD4yHkB_DZDsPr2cFrr-Q'),(65,'wx00f7d56d549f82ce',20160828004323,'component_verify_ticket','ticket@@@A70hOQz0zo6NkD9OQyTk4li_nriGhtenRs96iwxWCUdAcpyrW_OapuNFjmq4ZvNzWWD4yHkB_DZDsPr2cFrr-Q'),(66,'wx00f7d56d549f82ce',20160828005329,'component_verify_ticket','ticket@@@A70hOQz0zo6NkD9OQyTk4li_nriGhtenRs96iwxWCUdAcpyrW_OapuNFjmq4ZvNzWWD4yHkB_DZDsPr2cFrr-Q'),(67,'wx00f7d56d549f82ce',20160828011323,'component_verify_ticket','ticket@@@NqSzW1yYZEUoyMAKREWbH1zQVGZC3U-Mauw2HhpWODhvivhGnZoczcFoT1s_YZx-Crp9nQNdmLuqA3yfhbHcLA'),(68,'wx00f7d56d549f82ce',20160828012333,'component_verify_ticket','ticket@@@NqSzW1yYZEUoyMAKREWbH1zQVGZC3U-Mauw2HhpWODhvivhGnZoczcFoT1s_YZx-Crp9nQNdmLuqA3yfhbHcLA'),(69,'wx00f7d56d549f82ce',20160828013325,'component_verify_ticket','ticket@@@NqSzW1yYZEUoyMAKREWbH1zQVGZC3U-Mauw2HhpWODhvivhGnZoczcFoT1s_YZx-Crp9nQNdmLuqA3yfhbHcLA'),(70,'wx00f7d56d549f82ce',20160828014318,'component_verify_ticket','ticket@@@NqSzW1yYZEUoyMAKREWbH1zQVGZC3U-Mauw2HhpWODhvivhGnZoczcFoT1s_YZx-Crp9nQNdmLuqA3yfhbHcLA'),(71,'wx00f7d56d549f82ce',20160828015320,'component_verify_ticket','ticket@@@NqSzW1yYZEUoyMAKREWbH1zQVGZC3U-Mauw2HhpWODhvivhGnZoczcFoT1s_YZx-Crp9nQNdmLuqA3yfhbHcLA'),(72,'wx00f7d56d549f82ce',20160828020326,'component_verify_ticket','ticket@@@lV0lOgYqMmkTBxlVAvtSsOKHivALDemG0iaRImbFsA5i2M3qSpeNt9IOHtUt-n8USPYe-SlYUTDNIcGFwd0dgw'),(73,'wx00f7d56d549f82ce',20160828021323,'component_verify_ticket','ticket@@@lV0lOgYqMmkTBxlVAvtSsOKHivALDemG0iaRImbFsA5i2M3qSpeNt9IOHtUt-n8USPYe-SlYUTDNIcGFwd0dgw'),(74,'wx00f7d56d549f82ce',20160828022325,'component_verify_ticket','ticket@@@lV0lOgYqMmkTBxlVAvtSsOKHivALDemG0iaRImbFsA5i2M3qSpeNt9IOHtUt-n8USPYe-SlYUTDNIcGFwd0dgw'),(75,'wx00f7d56d549f82ce',20160828023321,'component_verify_ticket','ticket@@@lV0lOgYqMmkTBxlVAvtSsOKHivALDemG0iaRImbFsA5i2M3qSpeNt9IOHtUt-n8USPYe-SlYUTDNIcGFwd0dgw'),(76,'wx00f7d56d549f82ce',20160828024326,'component_verify_ticket','ticket@@@lV0lOgYqMmkTBxlVAvtSsOKHivALDemG0iaRImbFsA5i2M3qSpeNt9IOHtUt-n8USPYe-SlYUTDNIcGFwd0dgw'),(77,'wx00f7d56d549f82ce',20160828025326,'component_verify_ticket','ticket@@@lV0lOgYqMmkTBxlVAvtSsOKHivALDemG0iaRImbFsA5i2M3qSpeNt9IOHtUt-n8USPYe-SlYUTDNIcGFwd0dgw'),(78,'wx00f7d56d549f82ce',20160828030312,'component_verify_ticket','ticket@@@uV3Sd3-aP9pjSTyRXyYOkaERHhfyVLIlagTfzm8RFBohkHQK2_0mf8NEoOZ6GhPpdBuasgYL5S5y3iFBaH8VYw'),(79,'wx00f7d56d549f82ce',20160828031322,'component_verify_ticket','ticket@@@uV3Sd3-aP9pjSTyRXyYOkaERHhfyVLIlagTfzm8RFBohkHQK2_0mf8NEoOZ6GhPpdBuasgYL5S5y3iFBaH8VYw'),(80,'wx00f7d56d549f82ce',20160828032325,'component_verify_ticket','ticket@@@uV3Sd3-aP9pjSTyRXyYOkaERHhfyVLIlagTfzm8RFBohkHQK2_0mf8NEoOZ6GhPpdBuasgYL5S5y3iFBaH8VYw'),(81,'wx00f7d56d549f82ce',20160828033322,'component_verify_ticket','ticket@@@uV3Sd3-aP9pjSTyRXyYOkaERHhfyVLIlagTfzm8RFBohkHQK2_0mf8NEoOZ6GhPpdBuasgYL5S5y3iFBaH8VYw'),(82,'wx00f7d56d549f82ce',20160828034323,'component_verify_ticket','ticket@@@uV3Sd3-aP9pjSTyRXyYOkaERHhfyVLIlagTfzm8RFBohkHQK2_0mf8NEoOZ6GhPpdBuasgYL5S5y3iFBaH8VYw'),(83,'wx00f7d56d549f82ce',20160828035326,'component_verify_ticket','ticket@@@uV3Sd3-aP9pjSTyRXyYOkaERHhfyVLIlagTfzm8RFBohkHQK2_0mf8NEoOZ6GhPpdBuasgYL5S5y3iFBaH8VYw'),(84,'wx00f7d56d549f82ce',20160828040328,'component_verify_ticket','ticket@@@5vt43lAkxyFiJuhICwLO_2av8DBJ6c3JB-1V8frKOYKo08Kb-deoCB_O0m6bs8Yj9UwOrnpnFP0k6uIdKV7wfg'),(85,'wx00f7d56d549f82ce',20160828041330,'component_verify_ticket','ticket@@@5vt43lAkxyFiJuhICwLO_2av8DBJ6c3JB-1V8frKOYKo08Kb-deoCB_O0m6bs8Yj9UwOrnpnFP0k6uIdKV7wfg'),(86,'wx00f7d56d549f82ce',20160828042326,'component_verify_ticket','ticket@@@5vt43lAkxyFiJuhICwLO_2av8DBJ6c3JB-1V8frKOYKo08Kb-deoCB_O0m6bs8Yj9UwOrnpnFP0k6uIdKV7wfg'),(87,'wx00f7d56d549f82ce',20160828043323,'component_verify_ticket','ticket@@@5vt43lAkxyFiJuhICwLO_2av8DBJ6c3JB-1V8frKOYKo08Kb-deoCB_O0m6bs8Yj9UwOrnpnFP0k6uIdKV7wfg'),(88,'wx00f7d56d549f82ce',20160828044319,'component_verify_ticket','ticket@@@5vt43lAkxyFiJuhICwLO_2av8DBJ6c3JB-1V8frKOYKo08Kb-deoCB_O0m6bs8Yj9UwOrnpnFP0k6uIdKV7wfg'),(89,'wx00f7d56d549f82ce',20160828045323,'component_verify_ticket','ticket@@@5vt43lAkxyFiJuhICwLO_2av8DBJ6c3JB-1V8frKOYKo08Kb-deoCB_O0m6bs8Yj9UwOrnpnFP0k6uIdKV7wfg'),(90,'wx00f7d56d549f82ce',20160828050316,'component_verify_ticket','ticket@@@2bvELv68CWgHNMPHpBdpBdvE6ZwlqHX_nWFFCAwqOFsXWCHKHTlRsYfHhY8rGZpyAvk30D75bdD1VAnC_Y99gA'),(91,'wx00f7d56d549f82ce',20160828051319,'component_verify_ticket','ticket@@@2bvELv68CWgHNMPHpBdpBdvE6ZwlqHX_nWFFCAwqOFsXWCHKHTlRsYfHhY8rGZpyAvk30D75bdD1VAnC_Y99gA'),(92,'wx00f7d56d549f82ce',20160828052319,'component_verify_ticket','ticket@@@2bvELv68CWgHNMPHpBdpBdvE6ZwlqHX_nWFFCAwqOFsXWCHKHTlRsYfHhY8rGZpyAvk30D75bdD1VAnC_Y99gA'),(93,'wx00f7d56d549f82ce',20160828053318,'component_verify_ticket','ticket@@@2bvELv68CWgHNMPHpBdpBdvE6ZwlqHX_nWFFCAwqOFsXWCHKHTlRsYfHhY8rGZpyAvk30D75bdD1VAnC_Y99gA'),(94,'wx00f7d56d549f82ce',20160828054321,'component_verify_ticket','ticket@@@2bvELv68CWgHNMPHpBdpBdvE6ZwlqHX_nWFFCAwqOFsXWCHKHTlRsYfHhY8rGZpyAvk30D75bdD1VAnC_Y99gA'),(95,'wx00f7d56d549f82ce',20160828055319,'component_verify_ticket','ticket@@@2bvELv68CWgHNMPHpBdpBdvE6ZwlqHX_nWFFCAwqOFsXWCHKHTlRsYfHhY8rGZpyAvk30D75bdD1VAnC_Y99gA'),(96,'wx00f7d56d549f82ce',20160828060319,'component_verify_ticket','ticket@@@T_U3QCu5GFukZhV8xZ1JzrJ5S7z9DwdetrphonOfnAS5qyqydjri1Ts6ML2OUn7NOdLWRSk8mMK6yVuH24JqSg'),(97,'wx00f7d56d549f82ce',20160828061320,'component_verify_ticket','ticket@@@T_U3QCu5GFukZhV8xZ1JzrJ5S7z9DwdetrphonOfnAS5qyqydjri1Ts6ML2OUn7NOdLWRSk8mMK6yVuH24JqSg'),(98,'wx00f7d56d549f82ce',20160828062320,'component_verify_ticket','ticket@@@T_U3QCu5GFukZhV8xZ1JzrJ5S7z9DwdetrphonOfnAS5qyqydjri1Ts6ML2OUn7NOdLWRSk8mMK6yVuH24JqSg'),(99,'wx00f7d56d549f82ce',20160828063318,'component_verify_ticket','ticket@@@T_U3QCu5GFukZhV8xZ1JzrJ5S7z9DwdetrphonOfnAS5qyqydjri1Ts6ML2OUn7NOdLWRSk8mMK6yVuH24JqSg'),(100,'wx00f7d56d549f82ce',20160828064316,'component_verify_ticket','ticket@@@T_U3QCu5GFukZhV8xZ1JzrJ5S7z9DwdetrphonOfnAS5qyqydjri1Ts6ML2OUn7NOdLWRSk8mMK6yVuH24JqSg'),(101,'wx00f7d56d549f82ce',20160828065316,'component_verify_ticket','ticket@@@T_U3QCu5GFukZhV8xZ1JzrJ5S7z9DwdetrphonOfnAS5qyqydjri1Ts6ML2OUn7NOdLWRSk8mMK6yVuH24JqSg'),(102,'wx00f7d56d549f82ce',20160828070326,'component_verify_ticket','ticket@@@TolNEJN4aGeGcxCKZzCQLqpSfE24KBzkhfqLsoYdK2Pj-CeC3Pzoi88_qbrT_gk1GkA4xmCBgQ44fqNYyVaMQA'),(103,'wx00f7d56d549f82ce',20160828071320,'component_verify_ticket','ticket@@@TolNEJN4aGeGcxCKZzCQLqpSfE24KBzkhfqLsoYdK2Pj-CeC3Pzoi88_qbrT_gk1GkA4xmCBgQ44fqNYyVaMQA'),(104,'wx00f7d56d549f82ce',20160828072326,'component_verify_ticket','ticket@@@TolNEJN4aGeGcxCKZzCQLqpSfE24KBzkhfqLsoYdK2Pj-CeC3Pzoi88_qbrT_gk1GkA4xmCBgQ44fqNYyVaMQA'),(105,'wx00f7d56d549f82ce',20160828073326,'component_verify_ticket','ticket@@@TolNEJN4aGeGcxCKZzCQLqpSfE24KBzkhfqLsoYdK2Pj-CeC3Pzoi88_qbrT_gk1GkA4xmCBgQ44fqNYyVaMQA'),(106,'wx00f7d56d549f82ce',20160828074324,'component_verify_ticket','ticket@@@TolNEJN4aGeGcxCKZzCQLqpSfE24KBzkhfqLsoYdK2Pj-CeC3Pzoi88_qbrT_gk1GkA4xmCBgQ44fqNYyVaMQA'),(107,'wx00f7d56d549f82ce',20160828075320,'component_verify_ticket','ticket@@@TolNEJN4aGeGcxCKZzCQLqpSfE24KBzkhfqLsoYdK2Pj-CeC3Pzoi88_qbrT_gk1GkA4xmCBgQ44fqNYyVaMQA'),(108,'wx00f7d56d549f82ce',20160828080324,'component_verify_ticket','ticket@@@XZvEhXMvAEdmYE8-ao-VEng5U3UoO-9ARTpesU6ptnPpj6EwKMVP9P2Drn966I-j3JykOSwIgN6sAOylXQOWPA'),(109,'wx00f7d56d549f82ce',20160828081325,'component_verify_ticket','ticket@@@XZvEhXMvAEdmYE8-ao-VEng5U3UoO-9ARTpesU6ptnPpj6EwKMVP9P2Drn966I-j3JykOSwIgN6sAOylXQOWPA'),(110,'wx00f7d56d549f82ce',20160828082330,'component_verify_ticket','ticket@@@XZvEhXMvAEdmYE8-ao-VEng5U3UoO-9ARTpesU6ptnPpj6EwKMVP9P2Drn966I-j3JykOSwIgN6sAOylXQOWPA'),(111,'wx00f7d56d549f82ce',20160828083327,'component_verify_ticket','ticket@@@XZvEhXMvAEdmYE8-ao-VEng5U3UoO-9ARTpesU6ptnPpj6EwKMVP9P2Drn966I-j3JykOSwIgN6sAOylXQOWPA'),(112,'wx00f7d56d549f82ce',20160828084322,'component_verify_ticket','ticket@@@XZvEhXMvAEdmYE8-ao-VEng5U3UoO-9ARTpesU6ptnPpj6EwKMVP9P2Drn966I-j3JykOSwIgN6sAOylXQOWPA'),(113,'wx00f7d56d549f82ce',20160828085313,'component_verify_ticket','ticket@@@XZvEhXMvAEdmYE8-ao-VEng5U3UoO-9ARTpesU6ptnPpj6EwKMVP9P2Drn966I-j3JykOSwIgN6sAOylXQOWPA'),(114,'wx00f7d56d549f82ce',20160828090338,'component_verify_ticket','ticket@@@l1YXB39_tJnqlf10WExMJKRR_oqjwCxRBqukSBKPAMbHwlDnRIreoVkk7725r0SFF5S_J-QnWiUlqxGmnlDD3g'),(115,'wx00f7d56d549f82ce',20160828091327,'component_verify_ticket','ticket@@@l1YXB39_tJnqlf10WExMJKRR_oqjwCxRBqukSBKPAMbHwlDnRIreoVkk7725r0SFF5S_J-QnWiUlqxGmnlDD3g'),(116,'wx00f7d56d549f82ce',20160828092335,'component_verify_ticket','ticket@@@l1YXB39_tJnqlf10WExMJKRR_oqjwCxRBqukSBKPAMbHwlDnRIreoVkk7725r0SFF5S_J-QnWiUlqxGmnlDD3g'),(117,'wx00f7d56d549f82ce',20160828093335,'component_verify_ticket','ticket@@@l1YXB39_tJnqlf10WExMJKRR_oqjwCxRBqukSBKPAMbHwlDnRIreoVkk7725r0SFF5S_J-QnWiUlqxGmnlDD3g'),(118,'wx00f7d56d549f82ce',20160828094329,'component_verify_ticket','ticket@@@l1YXB39_tJnqlf10WExMJKRR_oqjwCxRBqukSBKPAMbHwlDnRIreoVkk7725r0SFF5S_J-QnWiUlqxGmnlDD3g'),(119,'wx00f7d56d549f82ce',20160828095350,'component_verify_ticket','ticket@@@l1YXB39_tJnqlf10WExMJKRR_oqjwCxRBqukSBKPAMbHwlDnRIreoVkk7725r0SFF5S_J-QnWiUlqxGmnlDD3g'),(120,'wx00f7d56d549f82ce',20160828100332,'component_verify_ticket','ticket@@@bWxRSsBYHo-19OXVRjk7opFkx7TWnQgVBPzhVsw2nZvnmD8e6u9Cvc_IoAJdI0EL4Ih1YsgR5x09NWwJ2Qylug'),(121,'wx00f7d56d549f82ce',20160828101338,'component_verify_ticket','ticket@@@bWxRSsBYHo-19OXVRjk7opFkx7TWnQgVBPzhVsw2nZvnmD8e6u9Cvc_IoAJdI0EL4Ih1YsgR5x09NWwJ2Qylug'),(122,'wx00f7d56d549f82ce',20160828102325,'component_verify_ticket','ticket@@@bWxRSsBYHo-19OXVRjk7opFkx7TWnQgVBPzhVsw2nZvnmD8e6u9Cvc_IoAJdI0EL4Ih1YsgR5x09NWwJ2Qylug'),(123,'wx00f7d56d549f82ce',20160828103329,'component_verify_ticket','ticket@@@bWxRSsBYHo-19OXVRjk7opFkx7TWnQgVBPzhVsw2nZvnmD8e6u9Cvc_IoAJdI0EL4Ih1YsgR5x09NWwJ2Qylug'),(124,'wx00f7d56d549f82ce',20160828104352,'component_verify_ticket','ticket@@@bWxRSsBYHo-19OXVRjk7opFkx7TWnQgVBPzhVsw2nZvnmD8e6u9Cvc_IoAJdI0EL4Ih1YsgR5x09NWwJ2Qylug'),(125,'wx00f7d56d549f82ce',20160828105333,'component_verify_ticket','ticket@@@bWxRSsBYHo-19OXVRjk7opFkx7TWnQgVBPzhVsw2nZvnmD8e6u9Cvc_IoAJdI0EL4Ih1YsgR5x09NWwJ2Qylug'),(126,'wx00f7d56d549f82ce',20160828110349,'component_verify_ticket','ticket@@@zxuYGoWIDCzpmJWNZM7D7n_aRSEGjTz3lQYEImQ7HudXXrRLKksg9ksdhZB_kA2RKP2GEgb33lgOooadH5kvfw'),(127,'wx00f7d56d549f82ce',20160828111341,'component_verify_ticket','ticket@@@zxuYGoWIDCzpmJWNZM7D7n_aRSEGjTz3lQYEImQ7HudXXrRLKksg9ksdhZB_kA2RKP2GEgb33lgOooadH5kvfw'),(128,'wx00f7d56d549f82ce',20160828112339,'component_verify_ticket','ticket@@@zxuYGoWIDCzpmJWNZM7D7n_aRSEGjTz3lQYEImQ7HudXXrRLKksg9ksdhZB_kA2RKP2GEgb33lgOooadH5kvfw'),(129,'wx00f7d56d549f82ce',20160828113346,'component_verify_ticket','ticket@@@zxuYGoWIDCzpmJWNZM7D7n_aRSEGjTz3lQYEImQ7HudXXrRLKksg9ksdhZB_kA2RKP2GEgb33lgOooadH5kvfw'),(130,'wx00f7d56d549f82ce',20160828114327,'component_verify_ticket','ticket@@@zxuYGoWIDCzpmJWNZM7D7n_aRSEGjTz3lQYEImQ7HudXXrRLKksg9ksdhZB_kA2RKP2GEgb33lgOooadH5kvfw'),(131,'wx00f7d56d549f82ce',20160828115338,'component_verify_ticket','ticket@@@zxuYGoWIDCzpmJWNZM7D7n_aRSEGjTz3lQYEImQ7HudXXrRLKksg9ksdhZB_kA2RKP2GEgb33lgOooadH5kvfw'),(132,'wx00f7d56d549f82ce',20160828120345,'component_verify_ticket','ticket@@@eVRHGPPNW3B-scebn_PRoN0ltc-WXkVNu9MII9qjri16p5HUdeaoct62aUqKBi7uEGfhpyfON1jFBIDboGW50A'),(133,'wx00f7d56d549f82ce',20160828121400,'component_verify_ticket','ticket@@@eVRHGPPNW3B-scebn_PRoN0ltc-WXkVNu9MII9qjri16p5HUdeaoct62aUqKBi7uEGfhpyfON1jFBIDboGW50A'),(134,'wx00f7d56d549f82ce',20160828122357,'component_verify_ticket','ticket@@@eVRHGPPNW3B-scebn_PRoN0ltc-WXkVNu9MII9qjri16p5HUdeaoct62aUqKBi7uEGfhpyfON1jFBIDboGW50A'),(135,'wx00f7d56d549f82ce',20160828123342,'component_verify_ticket','ticket@@@eVRHGPPNW3B-scebn_PRoN0ltc-WXkVNu9MII9qjri16p5HUdeaoct62aUqKBi7uEGfhpyfON1jFBIDboGW50A'),(136,'wx00f7d56d549f82ce',20160828124329,'component_verify_ticket','ticket@@@eVRHGPPNW3B-scebn_PRoN0ltc-WXkVNu9MII9qjri16p5HUdeaoct62aUqKBi7uEGfhpyfON1jFBIDboGW50A'),(137,'wx00f7d56d549f82ce',20160828125329,'component_verify_ticket','ticket@@@eVRHGPPNW3B-scebn_PRoN0ltc-WXkVNu9MII9qjri16p5HUdeaoct62aUqKBi7uEGfhpyfON1jFBIDboGW50A'),(138,'wx00f7d56d549f82ce',20160828130346,'component_verify_ticket','ticket@@@2qTYrg6Ad9ut6PsYrPWN2O1O4HRMsmb5xKlqgnaDH5WripmoF8Ut0RpOwDr54H29FnxaKezs6CLD6xVic3hJAw'),(139,'wx00f7d56d549f82ce',20160828131400,'component_verify_ticket','ticket@@@2qTYrg6Ad9ut6PsYrPWN2O1O4HRMsmb5xKlqgnaDH5WripmoF8Ut0RpOwDr54H29FnxaKezs6CLD6xVic3hJAw'),(140,'wx00f7d56d549f82ce',20160828132358,'component_verify_ticket','ticket@@@2qTYrg6Ad9ut6PsYrPWN2O1O4HRMsmb5xKlqgnaDH5WripmoF8Ut0RpOwDr54H29FnxaKezs6CLD6xVic3hJAw'),(141,'wx00f7d56d549f82ce',20160828133329,'component_verify_ticket','ticket@@@2qTYrg6Ad9ut6PsYrPWN2O1O4HRMsmb5xKlqgnaDH5WripmoF8Ut0RpOwDr54H29FnxaKezs6CLD6xVic3hJAw'),(142,'wx00f7d56d549f82ce',20160828134338,'component_verify_ticket','ticket@@@2qTYrg6Ad9ut6PsYrPWN2O1O4HRMsmb5xKlqgnaDH5WripmoF8Ut0RpOwDr54H29FnxaKezs6CLD6xVic3hJAw'),(143,'wx00f7d56d549f82ce',20160828135337,'component_verify_ticket','ticket@@@2qTYrg6Ad9ut6PsYrPWN2O1O4HRMsmb5xKlqgnaDH5WripmoF8Ut0RpOwDr54H29FnxaKezs6CLD6xVic3hJAw'),(144,'wx00f7d56d549f82ce',20160828140356,'component_verify_ticket','ticket@@@iJXucgWKm5ZP2uiOfbKBaANFAMEo97mtwUzT4a5-52mlPScb4wfH8RGOCX_03HkZmE-8_ywlPKMyHimTjSOESw'),(145,'wx00f7d56d549f82ce',20160828141346,'component_verify_ticket','ticket@@@iJXucgWKm5ZP2uiOfbKBaANFAMEo97mtwUzT4a5-52mlPScb4wfH8RGOCX_03HkZmE-8_ywlPKMyHimTjSOESw'),(146,'wx00f7d56d549f82ce',20160828142331,'component_verify_ticket','ticket@@@iJXucgWKm5ZP2uiOfbKBaANFAMEo97mtwUzT4a5-52mlPScb4wfH8RGOCX_03HkZmE-8_ywlPKMyHimTjSOESw'),(147,'wx00f7d56d549f82ce',20160828143327,'component_verify_ticket','ticket@@@iJXucgWKm5ZP2uiOfbKBaANFAMEo97mtwUzT4a5-52mlPScb4wfH8RGOCX_03HkZmE-8_ywlPKMyHimTjSOESw'),(148,'wx00f7d56d549f82ce',20160828144335,'component_verify_ticket','ticket@@@iJXucgWKm5ZP2uiOfbKBaANFAMEo97mtwUzT4a5-52mlPScb4wfH8RGOCX_03HkZmE-8_ywlPKMyHimTjSOESw'),(149,'wx00f7d56d549f82ce',20160828145335,'component_verify_ticket','ticket@@@iJXucgWKm5ZP2uiOfbKBaANFAMEo97mtwUzT4a5-52mlPScb4wfH8RGOCX_03HkZmE-8_ywlPKMyHimTjSOESw'),(150,'wx00f7d56d549f82ce',20160828150349,'component_verify_ticket','ticket@@@RFpYT1E7vBHnT_fTj2APuYER6GRBaPyk9Tr_MLM3KA_5pomxEutnTawwptecOvF3F3IbzwALlUBKcAf_XDmbzg'),(151,'wx00f7d56d549f82ce',20160828151326,'component_verify_ticket','ticket@@@RFpYT1E7vBHnT_fTj2APuYER6GRBaPyk9Tr_MLM3KA_5pomxEutnTawwptecOvF3F3IbzwALlUBKcAf_XDmbzg'),(152,'wx00f7d56d549f82ce',20160828152334,'component_verify_ticket','ticket@@@RFpYT1E7vBHnT_fTj2APuYER6GRBaPyk9Tr_MLM3KA_5pomxEutnTawwptecOvF3F3IbzwALlUBKcAf_XDmbzg'),(153,'wx00f7d56d549f82ce',20160828153336,'component_verify_ticket','ticket@@@RFpYT1E7vBHnT_fTj2APuYER6GRBaPyk9Tr_MLM3KA_5pomxEutnTawwptecOvF3F3IbzwALlUBKcAf_XDmbzg'),(154,'wx00f7d56d549f82ce',20160828154338,'component_verify_ticket','ticket@@@RFpYT1E7vBHnT_fTj2APuYER6GRBaPyk9Tr_MLM3KA_5pomxEutnTawwptecOvF3F3IbzwALlUBKcAf_XDmbzg'),(155,'wx00f7d56d549f82ce',20160828155333,'component_verify_ticket','ticket@@@RFpYT1E7vBHnT_fTj2APuYER6GRBaPyk9Tr_MLM3KA_5pomxEutnTawwptecOvF3F3IbzwALlUBKcAf_XDmbzg'),(156,'wx00f7d56d549f82ce',20160828160339,'component_verify_ticket','ticket@@@w7Z8A7o9QpLumS0-qs1PbfN92Cy-RvVz0R5ZszRdVFTxQaLVUFloZCVTuoGC9xI6nYJAJ8CHjsQJ7PQUc6_A0w'),(157,'wx00f7d56d549f82ce',20160828161337,'component_verify_ticket','ticket@@@w7Z8A7o9QpLumS0-qs1PbfN92Cy-RvVz0R5ZszRdVFTxQaLVUFloZCVTuoGC9xI6nYJAJ8CHjsQJ7PQUc6_A0w'),(158,'wx00f7d56d549f82ce',20160828162329,'component_verify_ticket','ticket@@@w7Z8A7o9QpLumS0-qs1PbfN92Cy-RvVz0R5ZszRdVFTxQaLVUFloZCVTuoGC9xI6nYJAJ8CHjsQJ7PQUc6_A0w'),(159,'wx00f7d56d549f82ce',20160828163332,'component_verify_ticket','ticket@@@w7Z8A7o9QpLumS0-qs1PbfN92Cy-RvVz0R5ZszRdVFTxQaLVUFloZCVTuoGC9xI6nYJAJ8CHjsQJ7PQUc6_A0w'),(160,'wx00f7d56d549f82ce',20160828164336,'component_verify_ticket','ticket@@@w7Z8A7o9QpLumS0-qs1PbfN92Cy-RvVz0R5ZszRdVFTxQaLVUFloZCVTuoGC9xI6nYJAJ8CHjsQJ7PQUc6_A0w'),(161,'wx00f7d56d549f82ce',20160828165325,'component_verify_ticket','ticket@@@w7Z8A7o9QpLumS0-qs1PbfN92Cy-RvVz0R5ZszRdVFTxQaLVUFloZCVTuoGC9xI6nYJAJ8CHjsQJ7PQUc6_A0w'),(162,'wx00f7d56d549f82ce',20160828170332,'component_verify_ticket','ticket@@@3TQ2qfRgwRBCSziVolxWuqj2sJ-h6BvMR0kQSKTSAh1X1LWRBKNWl6dpx4gvsZoJzwNrPCNonbP4iQalUajqAA'),(163,'wx00f7d56d549f82ce',20160828171344,'component_verify_ticket','ticket@@@3TQ2qfRgwRBCSziVolxWuqj2sJ-h6BvMR0kQSKTSAh1X1LWRBKNWl6dpx4gvsZoJzwNrPCNonbP4iQalUajqAA'),(164,'wx00f7d56d549f82ce',20160828172344,'component_verify_ticket','ticket@@@3TQ2qfRgwRBCSziVolxWuqj2sJ-h6BvMR0kQSKTSAh1X1LWRBKNWl6dpx4gvsZoJzwNrPCNonbP4iQalUajqAA'),(165,'wx00f7d56d549f82ce',20160828173332,'component_verify_ticket','ticket@@@3TQ2qfRgwRBCSziVolxWuqj2sJ-h6BvMR0kQSKTSAh1X1LWRBKNWl6dpx4gvsZoJzwNrPCNonbP4iQalUajqAA'),(166,'wx00f7d56d549f82ce',20160828174324,'component_verify_ticket','ticket@@@3TQ2qfRgwRBCSziVolxWuqj2sJ-h6BvMR0kQSKTSAh1X1LWRBKNWl6dpx4gvsZoJzwNrPCNonbP4iQalUajqAA'),(167,'wx00f7d56d549f82ce',20160828175342,'component_verify_ticket','ticket@@@3TQ2qfRgwRBCSziVolxWuqj2sJ-h6BvMR0kQSKTSAh1X1LWRBKNWl6dpx4gvsZoJzwNrPCNonbP4iQalUajqAA'),(168,'wx00f7d56d549f82ce',20160828180332,'component_verify_ticket','ticket@@@SIJb-lUC4OUBMDRvpKpYmlpAqqH5_gGRcybxGD1ENhczGozvJogx17qXSry9Vy7oRjqAKcrVUQhCGJi6ydpoeg'),(169,'wx00f7d56d549f82ce',20160828181350,'component_verify_ticket','ticket@@@SIJb-lUC4OUBMDRvpKpYmlpAqqH5_gGRcybxGD1ENhczGozvJogx17qXSry9Vy7oRjqAKcrVUQhCGJi6ydpoeg'),(170,'wx00f7d56d549f82ce',20160828182331,'component_verify_ticket','ticket@@@SIJb-lUC4OUBMDRvpKpYmlpAqqH5_gGRcybxGD1ENhczGozvJogx17qXSry9Vy7oRjqAKcrVUQhCGJi6ydpoeg'),(171,'wx00f7d56d549f82ce',20160828183327,'component_verify_ticket','ticket@@@SIJb-lUC4OUBMDRvpKpYmlpAqqH5_gGRcybxGD1ENhczGozvJogx17qXSry9Vy7oRjqAKcrVUQhCGJi6ydpoeg'),(172,'wx00f7d56d549f82ce',20160828184333,'component_verify_ticket','ticket@@@SIJb-lUC4OUBMDRvpKpYmlpAqqH5_gGRcybxGD1ENhczGozvJogx17qXSry9Vy7oRjqAKcrVUQhCGJi6ydpoeg'),(173,'wx00f7d56d549f82ce',20160828185327,'component_verify_ticket','ticket@@@SIJb-lUC4OUBMDRvpKpYmlpAqqH5_gGRcybxGD1ENhczGozvJogx17qXSry9Vy7oRjqAKcrVUQhCGJi6ydpoeg'),(174,'wx00f7d56d549f82ce',20160828190408,'component_verify_ticket','ticket@@@kDv8xCkgRomFhrYOnejQbn41lvhYnUYzb_VpBWo_ULzkBTxGHiTvIRyt09-dTaN4FJR03BCTKUKCR2AuMqfANw'),(175,'wx00f7d56d549f82ce',20160828191342,'component_verify_ticket','ticket@@@kDv8xCkgRomFhrYOnejQbn41lvhYnUYzb_VpBWo_ULzkBTxGHiTvIRyt09-dTaN4FJR03BCTKUKCR2AuMqfANw'),(176,'wx00f7d56d549f82ce',20160828192317,'component_verify_ticket','ticket@@@kDv8xCkgRomFhrYOnejQbn41lvhYnUYzb_VpBWo_ULzkBTxGHiTvIRyt09-dTaN4FJR03BCTKUKCR2AuMqfANw'),(177,'wx00f7d56d549f82ce',20160828193331,'component_verify_ticket','ticket@@@kDv8xCkgRomFhrYOnejQbn41lvhYnUYzb_VpBWo_ULzkBTxGHiTvIRyt09-dTaN4FJR03BCTKUKCR2AuMqfANw'),(178,'wx00f7d56d549f82ce',20160828194329,'component_verify_ticket','ticket@@@kDv8xCkgRomFhrYOnejQbn41lvhYnUYzb_VpBWo_ULzkBTxGHiTvIRyt09-dTaN4FJR03BCTKUKCR2AuMqfANw'),(179,'wx00f7d56d549f82ce',20160828195356,'component_verify_ticket','ticket@@@kDv8xCkgRomFhrYOnejQbn41lvhYnUYzb_VpBWo_ULzkBTxGHiTvIRyt09-dTaN4FJR03BCTKUKCR2AuMqfANw'),(180,'wx00f7d56d549f82ce',20160828200338,'component_verify_ticket','ticket@@@gXDRbQPLvv9BiVCQWhmF_P1wN5aoZykkO-yK1o5n9Aj8OC0fILfk0hTfYCNbR6ICYwFDKRmic-2nwHUs7YRJ1A'),(181,'wx00f7d56d549f82ce',20160828201348,'component_verify_ticket','ticket@@@gXDRbQPLvv9BiVCQWhmF_P1wN5aoZykkO-yK1o5n9Aj8OC0fILfk0hTfYCNbR6ICYwFDKRmic-2nwHUs7YRJ1A'),(182,'wx00f7d56d549f82ce',20160828202333,'component_verify_ticket','ticket@@@gXDRbQPLvv9BiVCQWhmF_P1wN5aoZykkO-yK1o5n9Aj8OC0fILfk0hTfYCNbR6ICYwFDKRmic-2nwHUs7YRJ1A'),(183,'wx00f7d56d549f82ce',20160828203346,'component_verify_ticket','ticket@@@gXDRbQPLvv9BiVCQWhmF_P1wN5aoZykkO-yK1o5n9Aj8OC0fILfk0hTfYCNbR6ICYwFDKRmic-2nwHUs7YRJ1A'),(184,'wx00f7d56d549f82ce',20160828204437,'component_verify_ticket','ticket@@@gXDRbQPLvv9BiVCQWhmF_P1wN5aoZykkO-yK1o5n9Aj8OC0fILfk0hTfYCNbR6ICYwFDKRmic-2nwHUs7YRJ1A'),(185,'wx00f7d56d549f82ce',20160828205337,'component_verify_ticket','ticket@@@gXDRbQPLvv9BiVCQWhmF_P1wN5aoZykkO-yK1o5n9Aj8OC0fILfk0hTfYCNbR6ICYwFDKRmic-2nwHUs7YRJ1A'),(186,'wx00f7d56d549f82ce',20160828210326,'component_verify_ticket','ticket@@@T7l6JHB5CTD-f3LdyxdgsJmdBI0CASkYNa6tj_5ByCZQsVgYb7EJX-dLLWmQiGKoqEbYWsxPJHIs5a1UcMKkQA'),(187,'wx00f7d56d549f82ce',20160828211324,'component_verify_ticket','ticket@@@T7l6JHB5CTD-f3LdyxdgsJmdBI0CASkYNa6tj_5ByCZQsVgYb7EJX-dLLWmQiGKoqEbYWsxPJHIs5a1UcMKkQA'),(188,'wx00f7d56d549f82ce',20160828212325,'component_verify_ticket','ticket@@@T7l6JHB5CTD-f3LdyxdgsJmdBI0CASkYNa6tj_5ByCZQsVgYb7EJX-dLLWmQiGKoqEbYWsxPJHIs5a1UcMKkQA'),(189,'wx00f7d56d549f82ce',20160828213325,'component_verify_ticket','ticket@@@T7l6JHB5CTD-f3LdyxdgsJmdBI0CASkYNa6tj_5ByCZQsVgYb7EJX-dLLWmQiGKoqEbYWsxPJHIs5a1UcMKkQA'),(190,'wx00f7d56d549f82ce',20160828214325,'component_verify_ticket','ticket@@@T7l6JHB5CTD-f3LdyxdgsJmdBI0CASkYNa6tj_5ByCZQsVgYb7EJX-dLLWmQiGKoqEbYWsxPJHIs5a1UcMKkQA'),(191,'wx00f7d56d549f82ce',20160828215326,'component_verify_ticket','ticket@@@T7l6JHB5CTD-f3LdyxdgsJmdBI0CASkYNa6tj_5ByCZQsVgYb7EJX-dLLWmQiGKoqEbYWsxPJHIs5a1UcMKkQA'),(192,'wx00f7d56d549f82ce',20160828220328,'component_verify_ticket','ticket@@@Sur4LsA31MCIM3rImhTcZ7jHCp8f1zt2VFunVOsASwmHuyYnoOPf0Qkl9LcP8EijAATUDyc5xlsKheHaWwShuw'),(193,'wx00f7d56d549f82ce',20160828221328,'component_verify_ticket','ticket@@@Sur4LsA31MCIM3rImhTcZ7jHCp8f1zt2VFunVOsASwmHuyYnoOPf0Qkl9LcP8EijAATUDyc5xlsKheHaWwShuw'),(194,'wx00f7d56d549f82ce',20160828222330,'component_verify_ticket','ticket@@@Sur4LsA31MCIM3rImhTcZ7jHCp8f1zt2VFunVOsASwmHuyYnoOPf0Qkl9LcP8EijAATUDyc5xlsKheHaWwShuw'),(195,'wx00f7d56d549f82ce',20160828223325,'component_verify_ticket','ticket@@@Sur4LsA31MCIM3rImhTcZ7jHCp8f1zt2VFunVOsASwmHuyYnoOPf0Qkl9LcP8EijAATUDyc5xlsKheHaWwShuw'),(196,'wx00f7d56d549f82ce',20160828224324,'component_verify_ticket','ticket@@@Sur4LsA31MCIM3rImhTcZ7jHCp8f1zt2VFunVOsASwmHuyYnoOPf0Qkl9LcP8EijAATUDyc5xlsKheHaWwShuw'),(197,'wx00f7d56d549f82ce',20160828225323,'component_verify_ticket','ticket@@@Sur4LsA31MCIM3rImhTcZ7jHCp8f1zt2VFunVOsASwmHuyYnoOPf0Qkl9LcP8EijAATUDyc5xlsKheHaWwShuw'),(198,'wx00f7d56d549f82ce',20160828230323,'component_verify_ticket','ticket@@@W7IizL6acuTjyD60nDT7zztbR0P4sfSjMqUcjGXGKCkDffoH1w82njwgH-RJd282F4HQc8aEFRTRIe5HugJtJg'),(199,'wx00f7d56d549f82ce',20160828231328,'component_verify_ticket','ticket@@@W7IizL6acuTjyD60nDT7zztbR0P4sfSjMqUcjGXGKCkDffoH1w82njwgH-RJd282F4HQc8aEFRTRIe5HugJtJg'),(200,'wx00f7d56d549f82ce',20160828232328,'component_verify_ticket','ticket@@@W7IizL6acuTjyD60nDT7zztbR0P4sfSjMqUcjGXGKCkDffoH1w82njwgH-RJd282F4HQc8aEFRTRIe5HugJtJg'),(201,'wx00f7d56d549f82ce',20160828233325,'component_verify_ticket','ticket@@@W7IizL6acuTjyD60nDT7zztbR0P4sfSjMqUcjGXGKCkDffoH1w82njwgH-RJd282F4HQc8aEFRTRIe5HugJtJg'),(202,'wx00f7d56d549f82ce',20160828234325,'component_verify_ticket','ticket@@@W7IizL6acuTjyD60nDT7zztbR0P4sfSjMqUcjGXGKCkDffoH1w82njwgH-RJd282F4HQc8aEFRTRIe5HugJtJg'),(203,'wx00f7d56d549f82ce',20160828235324,'component_verify_ticket','ticket@@@W7IizL6acuTjyD60nDT7zztbR0P4sfSjMqUcjGXGKCkDffoH1w82njwgH-RJd282F4HQc8aEFRTRIe5HugJtJg'),(204,'wx00f7d56d549f82ce',20160829000327,'component_verify_ticket','ticket@@@LGhBQ0s2dyB1AVWDZT5mjVLqEJ7CIUSvIpoixpBtVHbPpXTtqBowl6l2nPfsEahfQPGOK7cdlvtkaqSRzp0rlg'),(205,'wx00f7d56d549f82ce',20160829001318,'component_verify_ticket','ticket@@@LGhBQ0s2dyB1AVWDZT5mjVLqEJ7CIUSvIpoixpBtVHbPpXTtqBowl6l2nPfsEahfQPGOK7cdlvtkaqSRzp0rlg'),(206,'wx00f7d56d549f82ce',20160829002325,'component_verify_ticket','ticket@@@LGhBQ0s2dyB1AVWDZT5mjVLqEJ7CIUSvIpoixpBtVHbPpXTtqBowl6l2nPfsEahfQPGOK7cdlvtkaqSRzp0rlg'),(207,'wx00f7d56d549f82ce',20160829003325,'component_verify_ticket','ticket@@@LGhBQ0s2dyB1AVWDZT5mjVLqEJ7CIUSvIpoixpBtVHbPpXTtqBowl6l2nPfsEahfQPGOK7cdlvtkaqSRzp0rlg'),(208,'wx00f7d56d549f82ce',20160829004319,'component_verify_ticket','ticket@@@LGhBQ0s2dyB1AVWDZT5mjVLqEJ7CIUSvIpoixpBtVHbPpXTtqBowl6l2nPfsEahfQPGOK7cdlvtkaqSRzp0rlg'),(209,'wx00f7d56d549f82ce',20160829005319,'component_verify_ticket','ticket@@@LGhBQ0s2dyB1AVWDZT5mjVLqEJ7CIUSvIpoixpBtVHbPpXTtqBowl6l2nPfsEahfQPGOK7cdlvtkaqSRzp0rlg'),(210,'wx00f7d56d549f82ce',20160829010319,'component_verify_ticket','ticket@@@lmXjcy0TKvVXKFG-dalIUqC9j7pLgjC07RiPHFKqWZXMkBof_Vi00kQqS8ZLBlV9mI7yzLtR-amL6Jz4S5ASsg'),(211,'wx00f7d56d549f82ce',20160829011321,'component_verify_ticket','ticket@@@lmXjcy0TKvVXKFG-dalIUqC9j7pLgjC07RiPHFKqWZXMkBof_Vi00kQqS8ZLBlV9mI7yzLtR-amL6Jz4S5ASsg'),(212,'wx00f7d56d549f82ce',20160829012323,'component_verify_ticket','ticket@@@lmXjcy0TKvVXKFG-dalIUqC9j7pLgjC07RiPHFKqWZXMkBof_Vi00kQqS8ZLBlV9mI7yzLtR-amL6Jz4S5ASsg'),(213,'wx00f7d56d549f82ce',20160829013324,'component_verify_ticket','ticket@@@lmXjcy0TKvVXKFG-dalIUqC9j7pLgjC07RiPHFKqWZXMkBof_Vi00kQqS8ZLBlV9mI7yzLtR-amL6Jz4S5ASsg'),(214,'wx00f7d56d549f82ce',20160829014321,'component_verify_ticket','ticket@@@lmXjcy0TKvVXKFG-dalIUqC9j7pLgjC07RiPHFKqWZXMkBof_Vi00kQqS8ZLBlV9mI7yzLtR-amL6Jz4S5ASsg'),(215,'wx00f7d56d549f82ce',20160829015303,'component_verify_ticket','ticket@@@lmXjcy0TKvVXKFG-dalIUqC9j7pLgjC07RiPHFKqWZXMkBof_Vi00kQqS8ZLBlV9mI7yzLtR-amL6Jz4S5ASsg'),(216,'wx00f7d56d549f82ce',20160829020323,'component_verify_ticket','ticket@@@OZSHFqdMidNRLmb6C2Xm-YHASiiOmjQ6RBjctFaglncVjmxFvwrPThgKaVe4TYrlJnOHlFTl5dZUYGggfk5g2w'),(217,'wx00f7d56d549f82ce',20160829021324,'component_verify_ticket','ticket@@@OZSHFqdMidNRLmb6C2Xm-YHASiiOmjQ6RBjctFaglncVjmxFvwrPThgKaVe4TYrlJnOHlFTl5dZUYGggfk5g2w'),(218,'wx00f7d56d549f82ce',20160829022325,'component_verify_ticket','ticket@@@OZSHFqdMidNRLmb6C2Xm-YHASiiOmjQ6RBjctFaglncVjmxFvwrPThgKaVe4TYrlJnOHlFTl5dZUYGggfk5g2w'),(219,'wx00f7d56d549f82ce',20160829023324,'component_verify_ticket','ticket@@@OZSHFqdMidNRLmb6C2Xm-YHASiiOmjQ6RBjctFaglncVjmxFvwrPThgKaVe4TYrlJnOHlFTl5dZUYGggfk5g2w'),(220,'wx00f7d56d549f82ce',20160829024324,'component_verify_ticket','ticket@@@OZSHFqdMidNRLmb6C2Xm-YHASiiOmjQ6RBjctFaglncVjmxFvwrPThgKaVe4TYrlJnOHlFTl5dZUYGggfk5g2w'),(221,'wx00f7d56d549f82ce',20160829025322,'component_verify_ticket','ticket@@@OZSHFqdMidNRLmb6C2Xm-YHASiiOmjQ6RBjctFaglncVjmxFvwrPThgKaVe4TYrlJnOHlFTl5dZUYGggfk5g2w'),(222,'wx00f7d56d549f82ce',20160829030322,'component_verify_ticket','ticket@@@4_BsbUmZJobb2wTiq4eciqT7jT73HEhXQhFgF1pt1X5P8GvMSS0BXmH7_InNrM3LwksyBVU7HvvpBliLxcw4QQ'),(223,'wx00f7d56d549f82ce',20160829031320,'component_verify_ticket','ticket@@@4_BsbUmZJobb2wTiq4eciqT7jT73HEhXQhFgF1pt1X5P8GvMSS0BXmH7_InNrM3LwksyBVU7HvvpBliLxcw4QQ'),(224,'wx00f7d56d549f82ce',20160829032324,'component_verify_ticket','ticket@@@4_BsbUmZJobb2wTiq4eciqT7jT73HEhXQhFgF1pt1X5P8GvMSS0BXmH7_InNrM3LwksyBVU7HvvpBliLxcw4QQ'),(225,'wx00f7d56d549f82ce',20160829033319,'component_verify_ticket','ticket@@@4_BsbUmZJobb2wTiq4eciqT7jT73HEhXQhFgF1pt1X5P8GvMSS0BXmH7_InNrM3LwksyBVU7HvvpBliLxcw4QQ'),(226,'wx00f7d56d549f82ce',20160829034323,'component_verify_ticket','ticket@@@4_BsbUmZJobb2wTiq4eciqT7jT73HEhXQhFgF1pt1X5P8GvMSS0BXmH7_InNrM3LwksyBVU7HvvpBliLxcw4QQ'),(227,'wx00f7d56d549f82ce',20160829035324,'component_verify_ticket','ticket@@@4_BsbUmZJobb2wTiq4eciqT7jT73HEhXQhFgF1pt1X5P8GvMSS0BXmH7_InNrM3LwksyBVU7HvvpBliLxcw4QQ'),(228,'wx00f7d56d549f82ce',20160829040325,'component_verify_ticket','ticket@@@5mocJA9BKlJ9o5cDiSh5DDqhhlDAXdEi-W9GV4NUqX9bo304Qt07QHJSeTUnQxMzp8BNQnHo84M_XAijRXi4sg'),(229,'wx00f7d56d549f82ce',20160829041330,'component_verify_ticket','ticket@@@5mocJA9BKlJ9o5cDiSh5DDqhhlDAXdEi-W9GV4NUqX9bo304Qt07QHJSeTUnQxMzp8BNQnHo84M_XAijRXi4sg'),(230,'wx00f7d56d549f82ce',20160829042323,'component_verify_ticket','ticket@@@5mocJA9BKlJ9o5cDiSh5DDqhhlDAXdEi-W9GV4NUqX9bo304Qt07QHJSeTUnQxMzp8BNQnHo84M_XAijRXi4sg'),(231,'wx00f7d56d549f82ce',20160829043327,'component_verify_ticket','ticket@@@5mocJA9BKlJ9o5cDiSh5DDqhhlDAXdEi-W9GV4NUqX9bo304Qt07QHJSeTUnQxMzp8BNQnHo84M_XAijRXi4sg'),(232,'wx00f7d56d549f82ce',20160829044323,'component_verify_ticket','ticket@@@5mocJA9BKlJ9o5cDiSh5DDqhhlDAXdEi-W9GV4NUqX9bo304Qt07QHJSeTUnQxMzp8BNQnHo84M_XAijRXi4sg'),(233,'wx00f7d56d549f82ce',20160829045306,'component_verify_ticket','ticket@@@5mocJA9BKlJ9o5cDiSh5DDqhhlDAXdEi-W9GV4NUqX9bo304Qt07QHJSeTUnQxMzp8BNQnHo84M_XAijRXi4sg'),(234,'wx00f7d56d549f82ce',20160829050316,'component_verify_ticket','ticket@@@rWEh_Cz0Y85NtyJ7tF8X8rwx_0u76oGZfKCpO02vz92FOMVP3KGh9x0bV_yrJ-GqZ0nRksI3-84Sxb3X70iG8A'),(235,'wx00f7d56d549f82ce',20160829051315,'component_verify_ticket','ticket@@@rWEh_Cz0Y85NtyJ7tF8X8rwx_0u76oGZfKCpO02vz92FOMVP3KGh9x0bV_yrJ-GqZ0nRksI3-84Sxb3X70iG8A'),(236,'wx00f7d56d549f82ce',20160829052318,'component_verify_ticket','ticket@@@rWEh_Cz0Y85NtyJ7tF8X8rwx_0u76oGZfKCpO02vz92FOMVP3KGh9x0bV_yrJ-GqZ0nRksI3-84Sxb3X70iG8A'),(237,'wx00f7d56d549f82ce',20160829053317,'component_verify_ticket','ticket@@@rWEh_Cz0Y85NtyJ7tF8X8rwx_0u76oGZfKCpO02vz92FOMVP3KGh9x0bV_yrJ-GqZ0nRksI3-84Sxb3X70iG8A'),(238,'wx00f7d56d549f82ce',20160829054314,'component_verify_ticket','ticket@@@rWEh_Cz0Y85NtyJ7tF8X8rwx_0u76oGZfKCpO02vz92FOMVP3KGh9x0bV_yrJ-GqZ0nRksI3-84Sxb3X70iG8A'),(239,'wx00f7d56d549f82ce',20160829055319,'component_verify_ticket','ticket@@@rWEh_Cz0Y85NtyJ7tF8X8rwx_0u76oGZfKCpO02vz92FOMVP3KGh9x0bV_yrJ-GqZ0nRksI3-84Sxb3X70iG8A'),(240,'wx00f7d56d549f82ce',20160829060318,'component_verify_ticket','ticket@@@xKDLevmPELlAKrxbf_mw_u_p4X6_7-gwEitw_hAok68wbuY-2yXa_qJ4qyP3FlO5SK1mYam3XUX-qneU61MVSw'),(241,'wx00f7d56d549f82ce',20160829061317,'component_verify_ticket','ticket@@@xKDLevmPELlAKrxbf_mw_u_p4X6_7-gwEitw_hAok68wbuY-2yXa_qJ4qyP3FlO5SK1mYam3XUX-qneU61MVSw'),(242,'wx00f7d56d549f82ce',20160829062317,'component_verify_ticket','ticket@@@xKDLevmPELlAKrxbf_mw_u_p4X6_7-gwEitw_hAok68wbuY-2yXa_qJ4qyP3FlO5SK1mYam3XUX-qneU61MVSw'),(243,'wx00f7d56d549f82ce',20160829063319,'component_verify_ticket','ticket@@@xKDLevmPELlAKrxbf_mw_u_p4X6_7-gwEitw_hAok68wbuY-2yXa_qJ4qyP3FlO5SK1mYam3XUX-qneU61MVSw'),(244,'wx00f7d56d549f82ce',20160829064313,'component_verify_ticket','ticket@@@xKDLevmPELlAKrxbf_mw_u_p4X6_7-gwEitw_hAok68wbuY-2yXa_qJ4qyP3FlO5SK1mYam3XUX-qneU61MVSw'),(245,'wx00f7d56d549f82ce',20160829065313,'component_verify_ticket','ticket@@@xKDLevmPELlAKrxbf_mw_u_p4X6_7-gwEitw_hAok68wbuY-2yXa_qJ4qyP3FlO5SK1mYam3XUX-qneU61MVSw'),(246,'wx00f7d56d549f82ce',20160829070317,'component_verify_ticket','ticket@@@wWdcSsFpkJjwNz-MrxFblQ4VlCIX9qmmtIjOi0U06GCoRR97DNdSrP_Wmu36JCx3_8GnzzxX6CRqUWM8aw5FKw'),(247,'wx00f7d56d549f82ce',20160829071317,'component_verify_ticket','ticket@@@wWdcSsFpkJjwNz-MrxFblQ4VlCIX9qmmtIjOi0U06GCoRR97DNdSrP_Wmu36JCx3_8GnzzxX6CRqUWM8aw5FKw'),(248,'wx00f7d56d549f82ce',20160829072318,'component_verify_ticket','ticket@@@wWdcSsFpkJjwNz-MrxFblQ4VlCIX9qmmtIjOi0U06GCoRR97DNdSrP_Wmu36JCx3_8GnzzxX6CRqUWM8aw5FKw'),(249,'wx00f7d56d549f82ce',20160829073320,'component_verify_ticket','ticket@@@wWdcSsFpkJjwNz-MrxFblQ4VlCIX9qmmtIjOi0U06GCoRR97DNdSrP_Wmu36JCx3_8GnzzxX6CRqUWM8aw5FKw'),(250,'wx00f7d56d549f82ce',20160829074307,'component_verify_ticket','ticket@@@wWdcSsFpkJjwNz-MrxFblQ4VlCIX9qmmtIjOi0U06GCoRR97DNdSrP_Wmu36JCx3_8GnzzxX6CRqUWM8aw5FKw'),(251,'wx00f7d56d549f82ce',20160829075304,'component_verify_ticket','ticket@@@wWdcSsFpkJjwNz-MrxFblQ4VlCIX9qmmtIjOi0U06GCoRR97DNdSrP_Wmu36JCx3_8GnzzxX6CRqUWM8aw5FKw'),(252,'wx00f7d56d549f82ce',20160829080324,'component_verify_ticket','ticket@@@D-gyn_q8rbvoTBaA2xLZLpHcPOoiFQU4I_0hGmhGpzVBqoX9mEKVau3AN311nZyeBgSZ9X6_yHEYcj3Q7AK2Yw'),(253,'wx00f7d56d549f82ce',20160829081318,'component_verify_ticket','ticket@@@D-gyn_q8rbvoTBaA2xLZLpHcPOoiFQU4I_0hGmhGpzVBqoX9mEKVau3AN311nZyeBgSZ9X6_yHEYcj3Q7AK2Yw'),(254,'wx00f7d56d549f82ce',20160829082319,'component_verify_ticket','ticket@@@D-gyn_q8rbvoTBaA2xLZLpHcPOoiFQU4I_0hGmhGpzVBqoX9mEKVau3AN311nZyeBgSZ9X6_yHEYcj3Q7AK2Yw'),(255,'wx00f7d56d549f82ce',20160829083326,'component_verify_ticket','ticket@@@D-gyn_q8rbvoTBaA2xLZLpHcPOoiFQU4I_0hGmhGpzVBqoX9mEKVau3AN311nZyeBgSZ9X6_yHEYcj3Q7AK2Yw'),(256,'wx00f7d56d549f82ce',20160829084324,'component_verify_ticket','ticket@@@D-gyn_q8rbvoTBaA2xLZLpHcPOoiFQU4I_0hGmhGpzVBqoX9mEKVau3AN311nZyeBgSZ9X6_yHEYcj3Q7AK2Yw'),(257,'wx00f7d56d549f82ce',20160829085324,'component_verify_ticket','ticket@@@D-gyn_q8rbvoTBaA2xLZLpHcPOoiFQU4I_0hGmhGpzVBqoX9mEKVau3AN311nZyeBgSZ9X6_yHEYcj3Q7AK2Yw'),(258,'wx00f7d56d549f82ce',20160829090328,'component_verify_ticket','ticket@@@C8UOqnimyhNI4cJNIXPC_orsDdyuXu_ViysznftK-m9FNBNeqkutVTt9oqcBQ1d-eQHfgb6wxUpeVYGWLa1M-g'),(259,'wx00f7d56d549f82ce',20160829091326,'component_verify_ticket','ticket@@@C8UOqnimyhNI4cJNIXPC_orsDdyuXu_ViysznftK-m9FNBNeqkutVTt9oqcBQ1d-eQHfgb6wxUpeVYGWLa1M-g'),(260,'wx00f7d56d549f82ce',20160829092329,'component_verify_ticket','ticket@@@C8UOqnimyhNI4cJNIXPC_orsDdyuXu_ViysznftK-m9FNBNeqkutVTt9oqcBQ1d-eQHfgb6wxUpeVYGWLa1M-g'),(261,'wx00f7d56d549f82ce',20160829093334,'component_verify_ticket','ticket@@@C8UOqnimyhNI4cJNIXPC_orsDdyuXu_ViysznftK-m9FNBNeqkutVTt9oqcBQ1d-eQHfgb6wxUpeVYGWLa1M-g'),(262,'wx00f7d56d549f82ce',20160829094330,'component_verify_ticket','ticket@@@C8UOqnimyhNI4cJNIXPC_orsDdyuXu_ViysznftK-m9FNBNeqkutVTt9oqcBQ1d-eQHfgb6wxUpeVYGWLa1M-g'),(263,'wx00f7d56d549f82ce',20160829095330,'component_verify_ticket','ticket@@@C8UOqnimyhNI4cJNIXPC_orsDdyuXu_ViysznftK-m9FNBNeqkutVTt9oqcBQ1d-eQHfgb6wxUpeVYGWLa1M-g'),(264,'wx00f7d56d549f82ce',20160829100332,'component_verify_ticket','ticket@@@JAt4lAYUjSDAzdBYIWS_1cNPfZWBRelOHSEvKdNd3KE3kCW0AOo37gv7Rqx4lYYX-4desUHUw1T36jOP_ongrg'),(265,'wx00f7d56d549f82ce',20160829101330,'component_verify_ticket','ticket@@@JAt4lAYUjSDAzdBYIWS_1cNPfZWBRelOHSEvKdNd3KE3kCW0AOo37gv7Rqx4lYYX-4desUHUw1T36jOP_ongrg'),(266,'wx00f7d56d549f82ce',20160829102332,'component_verify_ticket','ticket@@@JAt4lAYUjSDAzdBYIWS_1cNPfZWBRelOHSEvKdNd3KE3kCW0AOo37gv7Rqx4lYYX-4desUHUw1T36jOP_ongrg'),(267,'wx00f7d56d549f82ce',20160829103332,'component_verify_ticket','ticket@@@JAt4lAYUjSDAzdBYIWS_1cNPfZWBRelOHSEvKdNd3KE3kCW0AOo37gv7Rqx4lYYX-4desUHUw1T36jOP_ongrg'),(268,'wx00f7d56d549f82ce',20160829104328,'component_verify_ticket','ticket@@@JAt4lAYUjSDAzdBYIWS_1cNPfZWBRelOHSEvKdNd3KE3kCW0AOo37gv7Rqx4lYYX-4desUHUw1T36jOP_ongrg'),(269,'wx00f7d56d549f82ce',20160829105329,'component_verify_ticket','ticket@@@JAt4lAYUjSDAzdBYIWS_1cNPfZWBRelOHSEvKdNd3KE3kCW0AOo37gv7Rqx4lYYX-4desUHUw1T36jOP_ongrg'),(270,'wx00f7d56d549f82ce',20160829110334,'component_verify_ticket','ticket@@@wSrqakIvxMGquXCgD0--BS-pQ8V8k_0m5tiwajlOe7mlTlTe8r1c6mC2aATCRUsEDtL8xRLtnMcWnk4nkrOk3Q'),(271,'wx00f7d56d549f82ce',20160829111334,'component_verify_ticket','ticket@@@wSrqakIvxMGquXCgD0--BS-pQ8V8k_0m5tiwajlOe7mlTlTe8r1c6mC2aATCRUsEDtL8xRLtnMcWnk4nkrOk3Q'),(272,'wx00f7d56d549f82ce',20160829112340,'component_verify_ticket','ticket@@@wSrqakIvxMGquXCgD0--BS-pQ8V8k_0m5tiwajlOe7mlTlTe8r1c6mC2aATCRUsEDtL8xRLtnMcWnk4nkrOk3Q'),(273,'wx00f7d56d549f82ce',20160829113333,'component_verify_ticket','ticket@@@wSrqakIvxMGquXCgD0--BS-pQ8V8k_0m5tiwajlOe7mlTlTe8r1c6mC2aATCRUsEDtL8xRLtnMcWnk4nkrOk3Q'),(274,'wx00f7d56d549f82ce',20160829114330,'component_verify_ticket','ticket@@@wSrqakIvxMGquXCgD0--BS-pQ8V8k_0m5tiwajlOe7mlTlTe8r1c6mC2aATCRUsEDtL8xRLtnMcWnk4nkrOk3Q'),(275,'wx00f7d56d549f82ce',20160829115328,'component_verify_ticket','ticket@@@wSrqakIvxMGquXCgD0--BS-pQ8V8k_0m5tiwajlOe7mlTlTe8r1c6mC2aATCRUsEDtL8xRLtnMcWnk4nkrOk3Q'),(276,'wx00f7d56d549f82ce',20160829120336,'component_verify_ticket','ticket@@@l0gk2bLEemuSJ1GbA4Y26GKGrXAl9GffkbObUB6ib_x1XISz7RWBU03ez6NcX8eVbe7NIZ3T2z1xjiE9oVADug'),(277,'wx00f7d56d549f82ce',20160829121340,'component_verify_ticket','ticket@@@l0gk2bLEemuSJ1GbA4Y26GKGrXAl9GffkbObUB6ib_x1XISz7RWBU03ez6NcX8eVbe7NIZ3T2z1xjiE9oVADug'),(278,'wx00f7d56d549f82ce',20160829122331,'component_verify_ticket','ticket@@@l0gk2bLEemuSJ1GbA4Y26GKGrXAl9GffkbObUB6ib_x1XISz7RWBU03ez6NcX8eVbe7NIZ3T2z1xjiE9oVADug'),(279,'wx00f7d56d549f82ce',20160829123325,'component_verify_ticket','ticket@@@l0gk2bLEemuSJ1GbA4Y26GKGrXAl9GffkbObUB6ib_x1XISz7RWBU03ez6NcX8eVbe7NIZ3T2z1xjiE9oVADug'),(280,'wx00f7d56d549f82ce',20160829124313,'component_verify_ticket','ticket@@@l0gk2bLEemuSJ1GbA4Y26GKGrXAl9GffkbObUB6ib_x1XISz7RWBU03ez6NcX8eVbe7NIZ3T2z1xjiE9oVADug'),(281,'wx00f7d56d549f82ce',20160829125330,'component_verify_ticket','ticket@@@l0gk2bLEemuSJ1GbA4Y26GKGrXAl9GffkbObUB6ib_x1XISz7RWBU03ez6NcX8eVbe7NIZ3T2z1xjiE9oVADug'),(282,'wx00f7d56d549f82ce',20160829130332,'component_verify_ticket','ticket@@@aPv9es0UsgqygUGF-CeZ2iKOsc_qbhk1L78RkxzOhkzYdqDlRRnAtNhLYty4tKatKM3rGyLyCSlnW72DXUv2NA'),(283,'wx00f7d56d549f82ce',20160829131330,'component_verify_ticket','ticket@@@aPv9es0UsgqygUGF-CeZ2iKOsc_qbhk1L78RkxzOhkzYdqDlRRnAtNhLYty4tKatKM3rGyLyCSlnW72DXUv2NA'),(284,'wx00f7d56d549f82ce',20160829132330,'component_verify_ticket','ticket@@@aPv9es0UsgqygUGF-CeZ2iKOsc_qbhk1L78RkxzOhkzYdqDlRRnAtNhLYty4tKatKM3rGyLyCSlnW72DXUv2NA'),(285,'wx00f7d56d549f82ce',20160829133329,'component_verify_ticket','ticket@@@aPv9es0UsgqygUGF-CeZ2iKOsc_qbhk1L78RkxzOhkzYdqDlRRnAtNhLYty4tKatKM3rGyLyCSlnW72DXUv2NA'),(286,'wx00f7d56d549f82ce',20160829134329,'component_verify_ticket','ticket@@@aPv9es0UsgqygUGF-CeZ2iKOsc_qbhk1L78RkxzOhkzYdqDlRRnAtNhLYty4tKatKM3rGyLyCSlnW72DXUv2NA'),(287,'wx00f7d56d549f82ce',20160829135329,'component_verify_ticket','ticket@@@aPv9es0UsgqygUGF-CeZ2iKOsc_qbhk1L78RkxzOhkzYdqDlRRnAtNhLYty4tKatKM3rGyLyCSlnW72DXUv2NA'),(288,'wx00f7d56d549f82ce',20160829140331,'component_verify_ticket','ticket@@@G-1A4IT65rcPwnCMhefRr0ikBZQj5wMfH_KPOeaMr2hmZglHqsmOfZ_PzTCZXE78DnjP7AvsiM1sM9AJoQU9Sg'),(289,'wx00f7d56d549f82ce',20160829141330,'component_verify_ticket','ticket@@@G-1A4IT65rcPwnCMhefRr0ikBZQj5wMfH_KPOeaMr2hmZglHqsmOfZ_PzTCZXE78DnjP7AvsiM1sM9AJoQU9Sg'),(290,'wx00f7d56d549f82ce',20160829142350,'component_verify_ticket','ticket@@@G-1A4IT65rcPwnCMhefRr0ikBZQj5wMfH_KPOeaMr2hmZglHqsmOfZ_PzTCZXE78DnjP7AvsiM1sM9AJoQU9Sg'),(291,'wx00f7d56d549f82ce',20160829143407,'component_verify_ticket','ticket@@@G-1A4IT65rcPwnCMhefRr0ikBZQj5wMfH_KPOeaMr2hmZglHqsmOfZ_PzTCZXE78DnjP7AvsiM1sM9AJoQU9Sg'),(292,'wx00f7d56d549f82ce',20160829144329,'component_verify_ticket','ticket@@@G-1A4IT65rcPwnCMhefRr0ikBZQj5wMfH_KPOeaMr2hmZglHqsmOfZ_PzTCZXE78DnjP7AvsiM1sM9AJoQU9Sg'),(293,'wx00f7d56d549f82ce',20160829145331,'component_verify_ticket','ticket@@@G-1A4IT65rcPwnCMhefRr0ikBZQj5wMfH_KPOeaMr2hmZglHqsmOfZ_PzTCZXE78DnjP7AvsiM1sM9AJoQU9Sg'),(294,'wx00f7d56d549f82ce',20160829150332,'component_verify_ticket','ticket@@@eG6Kw0TMZbZzGv8G4Ev1x2yWewP-LZVKyzZI-nnT0gRqMYFHqxCWtDgE2MrSsMq51AWeVl5PsGkM2EIhlArrfw'),(295,'wx00f7d56d549f82ce',20160829151328,'component_verify_ticket','ticket@@@eG6Kw0TMZbZzGv8G4Ev1x2yWewP-LZVKyzZI-nnT0gRqMYFHqxCWtDgE2MrSsMq51AWeVl5PsGkM2EIhlArrfw'),(296,'wx00f7d56d549f82ce',20160829152331,'component_verify_ticket','ticket@@@eG6Kw0TMZbZzGv8G4Ev1x2yWewP-LZVKyzZI-nnT0gRqMYFHqxCWtDgE2MrSsMq51AWeVl5PsGkM2EIhlArrfw'),(297,'wx00f7d56d549f82ce',20160829153329,'component_verify_ticket','ticket@@@eG6Kw0TMZbZzGv8G4Ev1x2yWewP-LZVKyzZI-nnT0gRqMYFHqxCWtDgE2MrSsMq51AWeVl5PsGkM2EIhlArrfw'),(298,'wx00f7d56d549f82ce',20160829154333,'component_verify_ticket','ticket@@@eG6Kw0TMZbZzGv8G4Ev1x2yWewP-LZVKyzZI-nnT0gRqMYFHqxCWtDgE2MrSsMq51AWeVl5PsGkM2EIhlArrfw'),(299,'wx00f7d56d549f82ce',20160829155331,'component_verify_ticket','ticket@@@eG6Kw0TMZbZzGv8G4Ev1x2yWewP-LZVKyzZI-nnT0gRqMYFHqxCWtDgE2MrSsMq51AWeVl5PsGkM2EIhlArrfw'),(300,'wx00f7d56d549f82ce',20160829160337,'component_verify_ticket','ticket@@@kV-DcPPfhYpbre-z1K68iUJhK6jgmQsT-WtnP5U72CjclS3tuS2Ga74LDzOwFCSwcOM1CyKoVtSiMJftx80v7Q'),(301,'wx00f7d56d549f82ce',20160829161333,'component_verify_ticket','ticket@@@kV-DcPPfhYpbre-z1K68iUJhK6jgmQsT-WtnP5U72CjclS3tuS2Ga74LDzOwFCSwcOM1CyKoVtSiMJftx80v7Q'),(302,'wx00f7d56d549f82ce',20160829162330,'component_verify_ticket','ticket@@@kV-DcPPfhYpbre-z1K68iUJhK6jgmQsT-WtnP5U72CjclS3tuS2Ga74LDzOwFCSwcOM1CyKoVtSiMJftx80v7Q'),(303,'wx00f7d56d549f82ce',20160829163332,'component_verify_ticket','ticket@@@kV-DcPPfhYpbre-z1K68iUJhK6jgmQsT-WtnP5U72CjclS3tuS2Ga74LDzOwFCSwcOM1CyKoVtSiMJftx80v7Q'),(304,'wx00f7d56d549f82ce',20160829164331,'component_verify_ticket','ticket@@@kV-DcPPfhYpbre-z1K68iUJhK6jgmQsT-WtnP5U72CjclS3tuS2Ga74LDzOwFCSwcOM1CyKoVtSiMJftx80v7Q'),(305,'wx00f7d56d549f82ce',20160829165330,'component_verify_ticket','ticket@@@kV-DcPPfhYpbre-z1K68iUJhK6jgmQsT-WtnP5U72CjclS3tuS2Ga74LDzOwFCSwcOM1CyKoVtSiMJftx80v7Q'),(306,'wx00f7d56d549f82ce',20160829170335,'component_verify_ticket','ticket@@@l9WhABDnJ6Fj5fOg0MqSGjD2OmWlXe4UQ2mopha82bdHES27uIkDwit_YTsePJlTZvUkOKyqFUgHpLn1mKWg3g'),(307,'wx00f7d56d549f82ce',20160829171316,'component_verify_ticket','ticket@@@l9WhABDnJ6Fj5fOg0MqSGjD2OmWlXe4UQ2mopha82bdHES27uIkDwit_YTsePJlTZvUkOKyqFUgHpLn1mKWg3g'),(308,'wx00f7d56d549f82ce',20160829172336,'component_verify_ticket','ticket@@@l9WhABDnJ6Fj5fOg0MqSGjD2OmWlXe4UQ2mopha82bdHES27uIkDwit_YTsePJlTZvUkOKyqFUgHpLn1mKWg3g'),(309,'wx00f7d56d549f82ce',20160829173338,'component_verify_ticket','ticket@@@l9WhABDnJ6Fj5fOg0MqSGjD2OmWlXe4UQ2mopha82bdHES27uIkDwit_YTsePJlTZvUkOKyqFUgHpLn1mKWg3g'),(310,'wx00f7d56d549f82ce',20160829174328,'component_verify_ticket','ticket@@@l9WhABDnJ6Fj5fOg0MqSGjD2OmWlXe4UQ2mopha82bdHES27uIkDwit_YTsePJlTZvUkOKyqFUgHpLn1mKWg3g'),(311,'wx00f7d56d549f82ce',20160829175330,'component_verify_ticket','ticket@@@l9WhABDnJ6Fj5fOg0MqSGjD2OmWlXe4UQ2mopha82bdHES27uIkDwit_YTsePJlTZvUkOKyqFUgHpLn1mKWg3g'),(312,'wx00f7d56d549f82ce',20160829180339,'component_verify_ticket','ticket@@@PmebYLKDDQHj26QniBRSh0BEdytWJnW4MgIQhVOQuCtYbZLlZdA2NAY3El9p5C5aYQd-w8yExkOrB1vqoiy50w'),(313,'wx00f7d56d549f82ce',20160829181330,'component_verify_ticket','ticket@@@PmebYLKDDQHj26QniBRSh0BEdytWJnW4MgIQhVOQuCtYbZLlZdA2NAY3El9p5C5aYQd-w8yExkOrB1vqoiy50w'),(314,'wx00f7d56d549f82ce',20160829182330,'component_verify_ticket','ticket@@@PmebYLKDDQHj26QniBRSh0BEdytWJnW4MgIQhVOQuCtYbZLlZdA2NAY3El9p5C5aYQd-w8yExkOrB1vqoiy50w'),(315,'wx00f7d56d549f82ce',20160829183326,'component_verify_ticket','ticket@@@PmebYLKDDQHj26QniBRSh0BEdytWJnW4MgIQhVOQuCtYbZLlZdA2NAY3El9p5C5aYQd-w8yExkOrB1vqoiy50w'),(316,'wx00f7d56d549f82ce',20160829184326,'component_verify_ticket','ticket@@@PmebYLKDDQHj26QniBRSh0BEdytWJnW4MgIQhVOQuCtYbZLlZdA2NAY3El9p5C5aYQd-w8yExkOrB1vqoiy50w'),(317,'wx00f7d56d549f82ce',20160829185326,'component_verify_ticket','ticket@@@PmebYLKDDQHj26QniBRSh0BEdytWJnW4MgIQhVOQuCtYbZLlZdA2NAY3El9p5C5aYQd-w8yExkOrB1vqoiy50w'),(318,'wx00f7d56d549f82ce',20160829190332,'component_verify_ticket','ticket@@@W4C1WijF1JuZwWpsJB_5CvBC-kYjxoq5kCsEj2iUraXZaeZnh9_DKP4sIyzjNP_JP7I4XZ9Ns9FZ6Pykq-hz9g'),(319,'wx00f7d56d549f82ce',20160829191326,'component_verify_ticket','ticket@@@W4C1WijF1JuZwWpsJB_5CvBC-kYjxoq5kCsEj2iUraXZaeZnh9_DKP4sIyzjNP_JP7I4XZ9Ns9FZ6Pykq-hz9g'),(320,'wx00f7d56d549f82ce',20160829192327,'component_verify_ticket','ticket@@@W4C1WijF1JuZwWpsJB_5CvBC-kYjxoq5kCsEj2iUraXZaeZnh9_DKP4sIyzjNP_JP7I4XZ9Ns9FZ6Pykq-hz9g'),(321,'wx00f7d56d549f82ce',20160829193330,'component_verify_ticket','ticket@@@W4C1WijF1JuZwWpsJB_5CvBC-kYjxoq5kCsEj2iUraXZaeZnh9_DKP4sIyzjNP_JP7I4XZ9Ns9FZ6Pykq-hz9g'),(322,'wx00f7d56d549f82ce',20160829194326,'component_verify_ticket','ticket@@@W4C1WijF1JuZwWpsJB_5CvBC-kYjxoq5kCsEj2iUraXZaeZnh9_DKP4sIyzjNP_JP7I4XZ9Ns9FZ6Pykq-hz9g'),(323,'wx00f7d56d549f82ce',20160829195326,'component_verify_ticket','ticket@@@W4C1WijF1JuZwWpsJB_5CvBC-kYjxoq5kCsEj2iUraXZaeZnh9_DKP4sIyzjNP_JP7I4XZ9Ns9FZ6Pykq-hz9g'),(324,'wx00f7d56d549f82ce',20160829200330,'component_verify_ticket','ticket@@@7DY6orXEb47stM05JjCL3gWZm-jPfG6Tpay1xs3ULApjNaUzxrT53Wo-rLybo9uYurmR7MSingOfR45Z-LSerA'),(325,'wx00f7d56d549f82ce',20160829201327,'component_verify_ticket','ticket@@@7DY6orXEb47stM05JjCL3gWZm-jPfG6Tpay1xs3ULApjNaUzxrT53Wo-rLybo9uYurmR7MSingOfR45Z-LSerA'),(326,'wx00f7d56d549f82ce',20160829202330,'component_verify_ticket','ticket@@@7DY6orXEb47stM05JjCL3gWZm-jPfG6Tpay1xs3ULApjNaUzxrT53Wo-rLybo9uYurmR7MSingOfR45Z-LSerA'),(327,'wx00f7d56d549f82ce',20160829203327,'component_verify_ticket','ticket@@@7DY6orXEb47stM05JjCL3gWZm-jPfG6Tpay1xs3ULApjNaUzxrT53Wo-rLybo9uYurmR7MSingOfR45Z-LSerA'),(328,'wx00f7d56d549f82ce',20160829204327,'component_verify_ticket','ticket@@@7DY6orXEb47stM05JjCL3gWZm-jPfG6Tpay1xs3ULApjNaUzxrT53Wo-rLybo9uYurmR7MSingOfR45Z-LSerA'),(329,'wx00f7d56d549f82ce',20160829205330,'component_verify_ticket','ticket@@@7DY6orXEb47stM05JjCL3gWZm-jPfG6Tpay1xs3ULApjNaUzxrT53Wo-rLybo9uYurmR7MSingOfR45Z-LSerA'),(330,'wx00f7d56d549f82ce',20160829210332,'component_verify_ticket','ticket@@@eoZMybt7ue7kUY3ug8HMCUevgYURPIF-uyPQ-2W90FsmXLTuRQrPliHxSCGjbLPLHJkMeLWXnZWe_xwPlNisgA'),(331,'wx00f7d56d549f82ce',20160829211333,'component_verify_ticket','ticket@@@eoZMybt7ue7kUY3ug8HMCUevgYURPIF-uyPQ-2W90FsmXLTuRQrPliHxSCGjbLPLHJkMeLWXnZWe_xwPlNisgA'),(332,'wx00f7d56d549f82ce',20160829212329,'component_verify_ticket','ticket@@@eoZMybt7ue7kUY3ug8HMCUevgYURPIF-uyPQ-2W90FsmXLTuRQrPliHxSCGjbLPLHJkMeLWXnZWe_xwPlNisgA'),(333,'wx00f7d56d549f82ce',20160829213328,'component_verify_ticket','ticket@@@eoZMybt7ue7kUY3ug8HMCUevgYURPIF-uyPQ-2W90FsmXLTuRQrPliHxSCGjbLPLHJkMeLWXnZWe_xwPlNisgA'),(334,'wx00f7d56d549f82ce',20160829214328,'component_verify_ticket','ticket@@@eoZMybt7ue7kUY3ug8HMCUevgYURPIF-uyPQ-2W90FsmXLTuRQrPliHxSCGjbLPLHJkMeLWXnZWe_xwPlNisgA'),(335,'wx00f7d56d549f82ce',20160829215326,'component_verify_ticket','ticket@@@eoZMybt7ue7kUY3ug8HMCUevgYURPIF-uyPQ-2W90FsmXLTuRQrPliHxSCGjbLPLHJkMeLWXnZWe_xwPlNisgA'),(336,'wx00f7d56d549f82ce',20160829220329,'component_verify_ticket','ticket@@@Yh6lsycFfZt3BtT7TT_ty5aqDESQAk9cfxXl5mlgptA-e9whTWEMcuTimNHgTgSllAkyGAFIIjjUWXPgY3bhCQ'),(337,'wx00f7d56d549f82ce',20160829221320,'component_verify_ticket','ticket@@@Yh6lsycFfZt3BtT7TT_ty5aqDESQAk9cfxXl5mlgptA-e9whTWEMcuTimNHgTgSllAkyGAFIIjjUWXPgY3bhCQ'),(338,'wx00f7d56d549f82ce',20160829222327,'component_verify_ticket','ticket@@@Yh6lsycFfZt3BtT7TT_ty5aqDESQAk9cfxXl5mlgptA-e9whTWEMcuTimNHgTgSllAkyGAFIIjjUWXPgY3bhCQ'),(339,'wx00f7d56d549f82ce',20160829223328,'component_verify_ticket','ticket@@@Yh6lsycFfZt3BtT7TT_ty5aqDESQAk9cfxXl5mlgptA-e9whTWEMcuTimNHgTgSllAkyGAFIIjjUWXPgY3bhCQ'),(340,'wx00f7d56d549f82ce',20160829224326,'component_verify_ticket','ticket@@@Yh6lsycFfZt3BtT7TT_ty5aqDESQAk9cfxXl5mlgptA-e9whTWEMcuTimNHgTgSllAkyGAFIIjjUWXPgY3bhCQ'),(341,'wx00f7d56d549f82ce',20160829225326,'component_verify_ticket','ticket@@@Yh6lsycFfZt3BtT7TT_ty5aqDESQAk9cfxXl5mlgptA-e9whTWEMcuTimNHgTgSllAkyGAFIIjjUWXPgY3bhCQ'),(342,'wx00f7d56d549f82ce',20160829230324,'component_verify_ticket','ticket@@@k1XIGeuWoQGjw4_lTxWgF4WXQ81gtPHtPbHOcAmfvGiiCkKUNTtybgmLmqH5ZwUqCvaiMQrOHDAEgK_aOiliLQ'),(343,'wx00f7d56d549f82ce',20160829231327,'component_verify_ticket','ticket@@@k1XIGeuWoQGjw4_lTxWgF4WXQ81gtPHtPbHOcAmfvGiiCkKUNTtybgmLmqH5ZwUqCvaiMQrOHDAEgK_aOiliLQ'),(344,'wx00f7d56d549f82ce',20160829232327,'component_verify_ticket','ticket@@@k1XIGeuWoQGjw4_lTxWgF4WXQ81gtPHtPbHOcAmfvGiiCkKUNTtybgmLmqH5ZwUqCvaiMQrOHDAEgK_aOiliLQ'),(345,'wx00f7d56d549f82ce',20160829233327,'component_verify_ticket','ticket@@@k1XIGeuWoQGjw4_lTxWgF4WXQ81gtPHtPbHOcAmfvGiiCkKUNTtybgmLmqH5ZwUqCvaiMQrOHDAEgK_aOiliLQ'),(346,'wx00f7d56d549f82ce',20160829234326,'component_verify_ticket','ticket@@@k1XIGeuWoQGjw4_lTxWgF4WXQ81gtPHtPbHOcAmfvGiiCkKUNTtybgmLmqH5ZwUqCvaiMQrOHDAEgK_aOiliLQ'),(347,'wx00f7d56d549f82ce',20160829235325,'component_verify_ticket','ticket@@@k1XIGeuWoQGjw4_lTxWgF4WXQ81gtPHtPbHOcAmfvGiiCkKUNTtybgmLmqH5ZwUqCvaiMQrOHDAEgK_aOiliLQ'),(348,'wx00f7d56d549f82ce',20160830000326,'component_verify_ticket','ticket@@@-TRIKj4-oRBrfF382GEnCV-7LWHfPtOEKmGXnoRQIOgSWbbe2EWMa6eXO48JuV_THv3RWOJeYA5XAKbfolukQw'),(349,'wx00f7d56d549f82ce',20160830001327,'component_verify_ticket','ticket@@@-TRIKj4-oRBrfF382GEnCV-7LWHfPtOEKmGXnoRQIOgSWbbe2EWMa6eXO48JuV_THv3RWOJeYA5XAKbfolukQw'),(350,'wx00f7d56d549f82ce',20160830002322,'component_verify_ticket','ticket@@@-TRIKj4-oRBrfF382GEnCV-7LWHfPtOEKmGXnoRQIOgSWbbe2EWMa6eXO48JuV_THv3RWOJeYA5XAKbfolukQw'),(351,'wx00f7d56d549f82ce',20160830003312,'component_verify_ticket','ticket@@@-TRIKj4-oRBrfF382GEnCV-7LWHfPtOEKmGXnoRQIOgSWbbe2EWMa6eXO48JuV_THv3RWOJeYA5XAKbfolukQw'),(352,'wx00f7d56d549f82ce',20160830004320,'component_verify_ticket','ticket@@@-TRIKj4-oRBrfF382GEnCV-7LWHfPtOEKmGXnoRQIOgSWbbe2EWMa6eXO48JuV_THv3RWOJeYA5XAKbfolukQw'),(353,'wx00f7d56d549f82ce',20160830005320,'component_verify_ticket','ticket@@@-TRIKj4-oRBrfF382GEnCV-7LWHfPtOEKmGXnoRQIOgSWbbe2EWMa6eXO48JuV_THv3RWOJeYA5XAKbfolukQw'),(354,'wx00f7d56d549f82ce',20160830010319,'component_verify_ticket','ticket@@@a6tU-sNecl66945z2wJ-NB_aEubm5dIoj34NW0UFTuQT6XqvrVZ2lub5_Zo8srd6GTtpTe-3FvTptzBa9qoGSg'),(355,'wx00f7d56d549f82ce',20160830011320,'component_verify_ticket','ticket@@@a6tU-sNecl66945z2wJ-NB_aEubm5dIoj34NW0UFTuQT6XqvrVZ2lub5_Zo8srd6GTtpTe-3FvTptzBa9qoGSg'),(356,'wx00f7d56d549f82ce',20160830012321,'component_verify_ticket','ticket@@@a6tU-sNecl66945z2wJ-NB_aEubm5dIoj34NW0UFTuQT6XqvrVZ2lub5_Zo8srd6GTtpTe-3FvTptzBa9qoGSg'),(357,'wx00f7d56d549f82ce',20160830013323,'component_verify_ticket','ticket@@@a6tU-sNecl66945z2wJ-NB_aEubm5dIoj34NW0UFTuQT6XqvrVZ2lub5_Zo8srd6GTtpTe-3FvTptzBa9qoGSg'),(358,'wx00f7d56d549f82ce',20160830014321,'component_verify_ticket','ticket@@@a6tU-sNecl66945z2wJ-NB_aEubm5dIoj34NW0UFTuQT6XqvrVZ2lub5_Zo8srd6GTtpTe-3FvTptzBa9qoGSg'),(359,'wx00f7d56d549f82ce',20160830015310,'component_verify_ticket','ticket@@@a6tU-sNecl66945z2wJ-NB_aEubm5dIoj34NW0UFTuQT6XqvrVZ2lub5_Zo8srd6GTtpTe-3FvTptzBa9qoGSg'),(360,'wx00f7d56d549f82ce',20160830020321,'component_verify_ticket','ticket@@@_mw1Neni62PFUeaO_rElz42iYCHfdZxFNlA_VqC3NJ5c1IErZ3TL1Tlh_miTJZTb5UlN2fZ_u12aXxZjladFvg'),(361,'wx00f7d56d549f82ce',20160830021323,'component_verify_ticket','ticket@@@_mw1Neni62PFUeaO_rElz42iYCHfdZxFNlA_VqC3NJ5c1IErZ3TL1Tlh_miTJZTb5UlN2fZ_u12aXxZjladFvg'),(362,'wx00f7d56d549f82ce',20160830022321,'component_verify_ticket','ticket@@@_mw1Neni62PFUeaO_rElz42iYCHfdZxFNlA_VqC3NJ5c1IErZ3TL1Tlh_miTJZTb5UlN2fZ_u12aXxZjladFvg'),(363,'wx00f7d56d549f82ce',20160830023323,'component_verify_ticket','ticket@@@_mw1Neni62PFUeaO_rElz42iYCHfdZxFNlA_VqC3NJ5c1IErZ3TL1Tlh_miTJZTb5UlN2fZ_u12aXxZjladFvg'),(364,'wx00f7d56d549f82ce',20160830024322,'component_verify_ticket','ticket@@@_mw1Neni62PFUeaO_rElz42iYCHfdZxFNlA_VqC3NJ5c1IErZ3TL1Tlh_miTJZTb5UlN2fZ_u12aXxZjladFvg'),(365,'wx00f7d56d549f82ce',20160830025317,'component_verify_ticket','ticket@@@_mw1Neni62PFUeaO_rElz42iYCHfdZxFNlA_VqC3NJ5c1IErZ3TL1Tlh_miTJZTb5UlN2fZ_u12aXxZjladFvg'),(366,'wx00f7d56d549f82ce',20160830030322,'component_verify_ticket','ticket@@@Y9at2OBfqhf3KTbWK332Am5Kuv_aST5eys8pct1OX1dMawq1PAegz8HjT4DhW6IHSPCx6BjrIDNBtq70sdpZCQ'),(367,'wx00f7d56d549f82ce',20160830031321,'component_verify_ticket','ticket@@@Y9at2OBfqhf3KTbWK332Am5Kuv_aST5eys8pct1OX1dMawq1PAegz8HjT4DhW6IHSPCx6BjrIDNBtq70sdpZCQ'),(368,'wx00f7d56d549f82ce',20160830032324,'component_verify_ticket','ticket@@@Y9at2OBfqhf3KTbWK332Am5Kuv_aST5eys8pct1OX1dMawq1PAegz8HjT4DhW6IHSPCx6BjrIDNBtq70sdpZCQ'),(369,'wx00f7d56d549f82ce',20160830033323,'component_verify_ticket','ticket@@@Y9at2OBfqhf3KTbWK332Am5Kuv_aST5eys8pct1OX1dMawq1PAegz8HjT4DhW6IHSPCx6BjrIDNBtq70sdpZCQ'),(370,'wx00f7d56d549f82ce',20160830034322,'component_verify_ticket','ticket@@@Y9at2OBfqhf3KTbWK332Am5Kuv_aST5eys8pct1OX1dMawq1PAegz8HjT4DhW6IHSPCx6BjrIDNBtq70sdpZCQ'),(371,'wx00f7d56d549f82ce',20160830035322,'component_verify_ticket','ticket@@@Y9at2OBfqhf3KTbWK332Am5Kuv_aST5eys8pct1OX1dMawq1PAegz8HjT4DhW6IHSPCx6BjrIDNBtq70sdpZCQ'),(372,'wx00f7d56d549f82ce',20160830040325,'component_verify_ticket','ticket@@@gtTTm4obw_kxpKkJNJ6iJj5ukBJqH_R6BqPqCZEd3q74vDRc-N0lh_z7iV8TTpZL-amK8aJ3g_iQuFZ___FRCw'),(373,'wx00f7d56d549f82ce',20160830041324,'component_verify_ticket','ticket@@@gtTTm4obw_kxpKkJNJ6iJj5ukBJqH_R6BqPqCZEd3q74vDRc-N0lh_z7iV8TTpZL-amK8aJ3g_iQuFZ___FRCw'),(374,'wx00f7d56d549f82ce',20160830042323,'component_verify_ticket','ticket@@@gtTTm4obw_kxpKkJNJ6iJj5ukBJqH_R6BqPqCZEd3q74vDRc-N0lh_z7iV8TTpZL-amK8aJ3g_iQuFZ___FRCw'),(375,'wx00f7d56d549f82ce',20160830043322,'component_verify_ticket','ticket@@@gtTTm4obw_kxpKkJNJ6iJj5ukBJqH_R6BqPqCZEd3q74vDRc-N0lh_z7iV8TTpZL-amK8aJ3g_iQuFZ___FRCw'),(376,'wx00f7d56d549f82ce',20160830044301,'component_verify_ticket','ticket@@@gtTTm4obw_kxpKkJNJ6iJj5ukBJqH_R6BqPqCZEd3q74vDRc-N0lh_z7iV8TTpZL-amK8aJ3g_iQuFZ___FRCw'),(377,'wx00f7d56d549f82ce',20160830045320,'component_verify_ticket','ticket@@@gtTTm4obw_kxpKkJNJ6iJj5ukBJqH_R6BqPqCZEd3q74vDRc-N0lh_z7iV8TTpZL-amK8aJ3g_iQuFZ___FRCw'),(378,'wx00f7d56d549f82ce',20160830050314,'component_verify_ticket','ticket@@@RQjAJhNu3yFNwMSLRRxaPwxOCqLgkaMfNExyxDpIQZkFs_k7fSCkPUQb2xKbrPJEDA6grFo4mohmlgpM3gSPJA'),(379,'wx00f7d56d549f82ce',20160830051322,'component_verify_ticket','ticket@@@RQjAJhNu3yFNwMSLRRxaPwxOCqLgkaMfNExyxDpIQZkFs_k7fSCkPUQb2xKbrPJEDA6grFo4mohmlgpM3gSPJA'),(380,'wx00f7d56d549f82ce',20160830052317,'component_verify_ticket','ticket@@@RQjAJhNu3yFNwMSLRRxaPwxOCqLgkaMfNExyxDpIQZkFs_k7fSCkPUQb2xKbrPJEDA6grFo4mohmlgpM3gSPJA'),(381,'wx00f7d56d549f82ce',20160830053307,'component_verify_ticket','ticket@@@RQjAJhNu3yFNwMSLRRxaPwxOCqLgkaMfNExyxDpIQZkFs_k7fSCkPUQb2xKbrPJEDA6grFo4mohmlgpM3gSPJA'),(382,'wx00f7d56d549f82ce',20160830054314,'component_verify_ticket','ticket@@@RQjAJhNu3yFNwMSLRRxaPwxOCqLgkaMfNExyxDpIQZkFs_k7fSCkPUQb2xKbrPJEDA6grFo4mohmlgpM3gSPJA'),(383,'wx00f7d56d549f82ce',20160830055316,'component_verify_ticket','ticket@@@RQjAJhNu3yFNwMSLRRxaPwxOCqLgkaMfNExyxDpIQZkFs_k7fSCkPUQb2xKbrPJEDA6grFo4mohmlgpM3gSPJA'),(384,'wx00f7d56d549f82ce',20160830060317,'component_verify_ticket','ticket@@@1i9RQiYDIaYikOjmTZ1iJ4aOMBiO_EskQMSISEP5TAvZ-NoDEkqF1zeQzT7fcv_6HwYSlVNjO-puYQjFcmEn9w'),(385,'wx00f7d56d549f82ce',20160830061321,'component_verify_ticket','ticket@@@1i9RQiYDIaYikOjmTZ1iJ4aOMBiO_EskQMSISEP5TAvZ-NoDEkqF1zeQzT7fcv_6HwYSlVNjO-puYQjFcmEn9w'),(386,'wx00f7d56d549f82ce',20160830062322,'component_verify_ticket','ticket@@@1i9RQiYDIaYikOjmTZ1iJ4aOMBiO_EskQMSISEP5TAvZ-NoDEkqF1zeQzT7fcv_6HwYSlVNjO-puYQjFcmEn9w'),(387,'wx00f7d56d549f82ce',20160830063319,'component_verify_ticket','ticket@@@1i9RQiYDIaYikOjmTZ1iJ4aOMBiO_EskQMSISEP5TAvZ-NoDEkqF1zeQzT7fcv_6HwYSlVNjO-puYQjFcmEn9w'),(388,'wx00f7d56d549f82ce',20160830064316,'component_verify_ticket','ticket@@@1i9RQiYDIaYikOjmTZ1iJ4aOMBiO_EskQMSISEP5TAvZ-NoDEkqF1zeQzT7fcv_6HwYSlVNjO-puYQjFcmEn9w'),(389,'wx00f7d56d549f82ce',20160830065314,'component_verify_ticket','ticket@@@1i9RQiYDIaYikOjmTZ1iJ4aOMBiO_EskQMSISEP5TAvZ-NoDEkqF1zeQzT7fcv_6HwYSlVNjO-puYQjFcmEn9w'),(390,'wx00f7d56d549f82ce',20160830070319,'component_verify_ticket','ticket@@@6OBqxRA59_CAOwW5b2X4mH4Qm81nukLPU576BBY7IoenT3mLt0avSCh8HYsAzKt1dJLmnKpeUlXl7KW6rSo8Pw'),(391,'wx00f7d56d549f82ce',20160830071319,'component_verify_ticket','ticket@@@6OBqxRA59_CAOwW5b2X4mH4Qm81nukLPU576BBY7IoenT3mLt0avSCh8HYsAzKt1dJLmnKpeUlXl7KW6rSo8Pw'),(392,'wx00f7d56d549f82ce',20160830072321,'component_verify_ticket','ticket@@@6OBqxRA59_CAOwW5b2X4mH4Qm81nukLPU576BBY7IoenT3mLt0avSCh8HYsAzKt1dJLmnKpeUlXl7KW6rSo8Pw'),(393,'wx00f7d56d549f82ce',20160830073322,'component_verify_ticket','ticket@@@6OBqxRA59_CAOwW5b2X4mH4Qm81nukLPU576BBY7IoenT3mLt0avSCh8HYsAzKt1dJLmnKpeUlXl7KW6rSo8Pw'),(394,'wx00f7d56d549f82ce',20160830074320,'component_verify_ticket','ticket@@@6OBqxRA59_CAOwW5b2X4mH4Qm81nukLPU576BBY7IoenT3mLt0avSCh8HYsAzKt1dJLmnKpeUlXl7KW6rSo8Pw'),(395,'wx00f7d56d549f82ce',20160830075321,'component_verify_ticket','ticket@@@6OBqxRA59_CAOwW5b2X4mH4Qm81nukLPU576BBY7IoenT3mLt0avSCh8HYsAzKt1dJLmnKpeUlXl7KW6rSo8Pw'),(396,'wx00f7d56d549f82ce',20160830080325,'component_verify_ticket','ticket@@@RDwucOyhj1lM1_SC8hRd6ZXLznRYPHIVg3mLo2sIR2Up0yTQWaIq_ik1b7IoNovw4cS1iIM534PHgscvno_fZg'),(397,'wx00f7d56d549f82ce',20160830081323,'component_verify_ticket','ticket@@@RDwucOyhj1lM1_SC8hRd6ZXLznRYPHIVg3mLo2sIR2Up0yTQWaIq_ik1b7IoNovw4cS1iIM534PHgscvno_fZg'),(398,'wx00f7d56d549f82ce',20160830082324,'component_verify_ticket','ticket@@@RDwucOyhj1lM1_SC8hRd6ZXLznRYPHIVg3mLo2sIR2Up0yTQWaIq_ik1b7IoNovw4cS1iIM534PHgscvno_fZg'),(399,'wx00f7d56d549f82ce',20160830083328,'component_verify_ticket','ticket@@@RDwucOyhj1lM1_SC8hRd6ZXLznRYPHIVg3mLo2sIR2Up0yTQWaIq_ik1b7IoNovw4cS1iIM534PHgscvno_fZg'),(400,'wx00f7d56d549f82ce',20160830084324,'component_verify_ticket','ticket@@@RDwucOyhj1lM1_SC8hRd6ZXLznRYPHIVg3mLo2sIR2Up0yTQWaIq_ik1b7IoNovw4cS1iIM534PHgscvno_fZg'),(401,'wx00f7d56d549f82ce',20160830085326,'component_verify_ticket','ticket@@@RDwucOyhj1lM1_SC8hRd6ZXLznRYPHIVg3mLo2sIR2Up0yTQWaIq_ik1b7IoNovw4cS1iIM534PHgscvno_fZg'),(402,'wx00f7d56d549f82ce',20160830090327,'component_verify_ticket','ticket@@@-WJksFnoTLTUezStHEhQTgt827XKHxR7oXWsaagcPdCWtpIS8r9bDJad4-_-ChJcrwvK_As9hDwrZmcRrAndGw'),(403,'wx00f7d56d549f82ce',20160830091327,'component_verify_ticket','ticket@@@-WJksFnoTLTUezStHEhQTgt827XKHxR7oXWsaagcPdCWtpIS8r9bDJad4-_-ChJcrwvK_As9hDwrZmcRrAndGw'),(404,'wx00f7d56d549f82ce',20160830092327,'component_verify_ticket','ticket@@@-WJksFnoTLTUezStHEhQTgt827XKHxR7oXWsaagcPdCWtpIS8r9bDJad4-_-ChJcrwvK_As9hDwrZmcRrAndGw'),(405,'wx00f7d56d549f82ce',20160830093332,'component_verify_ticket','ticket@@@-WJksFnoTLTUezStHEhQTgt827XKHxR7oXWsaagcPdCWtpIS8r9bDJad4-_-ChJcrwvK_As9hDwrZmcRrAndGw'),(406,'wx00f7d56d549f82ce',20160830094326,'component_verify_ticket','ticket@@@-WJksFnoTLTUezStHEhQTgt827XKHxR7oXWsaagcPdCWtpIS8r9bDJad4-_-ChJcrwvK_As9hDwrZmcRrAndGw'),(407,'wx00f7d56d549f82ce',20160830095327,'component_verify_ticket','ticket@@@-WJksFnoTLTUezStHEhQTgt827XKHxR7oXWsaagcPdCWtpIS8r9bDJad4-_-ChJcrwvK_As9hDwrZmcRrAndGw'),(408,'wx00f7d56d549f82ce',20160830100336,'component_verify_ticket','ticket@@@suHKQvNBTCjtG7xJcm1HYnjIR5cynocFR8p22EGk7L3WBQ0MZaNkNOzn0rB99Rd4unJFAOp-spWV1U-ktpH8qQ'),(409,'wx00f7d56d549f82ce',20160830101332,'component_verify_ticket','ticket@@@suHKQvNBTCjtG7xJcm1HYnjIR5cynocFR8p22EGk7L3WBQ0MZaNkNOzn0rB99Rd4unJFAOp-spWV1U-ktpH8qQ'),(410,'wx00f7d56d549f82ce',20160830102334,'component_verify_ticket','ticket@@@suHKQvNBTCjtG7xJcm1HYnjIR5cynocFR8p22EGk7L3WBQ0MZaNkNOzn0rB99Rd4unJFAOp-spWV1U-ktpH8qQ'),(411,'wx00f7d56d549f82ce',20160830103331,'component_verify_ticket','ticket@@@suHKQvNBTCjtG7xJcm1HYnjIR5cynocFR8p22EGk7L3WBQ0MZaNkNOzn0rB99Rd4unJFAOp-spWV1U-ktpH8qQ'),(412,'wx00f7d56d549f82ce',20160830104328,'component_verify_ticket','ticket@@@suHKQvNBTCjtG7xJcm1HYnjIR5cynocFR8p22EGk7L3WBQ0MZaNkNOzn0rB99Rd4unJFAOp-spWV1U-ktpH8qQ'),(413,'wx00f7d56d549f82ce',20160830105332,'component_verify_ticket','ticket@@@suHKQvNBTCjtG7xJcm1HYnjIR5cynocFR8p22EGk7L3WBQ0MZaNkNOzn0rB99Rd4unJFAOp-spWV1U-ktpH8qQ'),(414,'wx00f7d56d549f82ce',20160830110334,'component_verify_ticket','ticket@@@a3boiphlnfnqAZfM_QjCpY94THD5YFEEcM8yhIfWKvQTZbpkYL-D_58RpIcCAkBdJ6nqlG-6AjkdRlXYeZSctw'),(415,'wx00f7d56d549f82ce',20160830111333,'component_verify_ticket','ticket@@@a3boiphlnfnqAZfM_QjCpY94THD5YFEEcM8yhIfWKvQTZbpkYL-D_58RpIcCAkBdJ6nqlG-6AjkdRlXYeZSctw'),(416,'wx00f7d56d549f82ce',20160830112332,'component_verify_ticket','ticket@@@a3boiphlnfnqAZfM_QjCpY94THD5YFEEcM8yhIfWKvQTZbpkYL-D_58RpIcCAkBdJ6nqlG-6AjkdRlXYeZSctw'),(417,'wx00f7d56d549f82ce',20160830113328,'component_verify_ticket','ticket@@@a3boiphlnfnqAZfM_QjCpY94THD5YFEEcM8yhIfWKvQTZbpkYL-D_58RpIcCAkBdJ6nqlG-6AjkdRlXYeZSctw'),(418,'wx00f7d56d549f82ce',20160830114329,'component_verify_ticket','ticket@@@a3boiphlnfnqAZfM_QjCpY94THD5YFEEcM8yhIfWKvQTZbpkYL-D_58RpIcCAkBdJ6nqlG-6AjkdRlXYeZSctw'),(419,'wx00f7d56d549f82ce',20160830115329,'component_verify_ticket','ticket@@@a3boiphlnfnqAZfM_QjCpY94THD5YFEEcM8yhIfWKvQTZbpkYL-D_58RpIcCAkBdJ6nqlG-6AjkdRlXYeZSctw'),(420,'wx00f7d56d549f82ce',20160830120336,'component_verify_ticket','ticket@@@sDkXJ56FpqKDbDBdoKvW3iDnzA8Hcry9oePdmspGVtki_ldaGTWE80e3d2olUDy52lTt-CxuMT_oo_-4hm9ObA'),(421,'wx00f7d56d549f82ce',20160830121325,'component_verify_ticket','ticket@@@sDkXJ56FpqKDbDBdoKvW3iDnzA8Hcry9oePdmspGVtki_ldaGTWE80e3d2olUDy52lTt-CxuMT_oo_-4hm9ObA'),(422,'wx00f7d56d549f82ce',20160830122333,'component_verify_ticket','ticket@@@sDkXJ56FpqKDbDBdoKvW3iDnzA8Hcry9oePdmspGVtki_ldaGTWE80e3d2olUDy52lTt-CxuMT_oo_-4hm9ObA'),(423,'wx00f7d56d549f82ce',20160830123331,'component_verify_ticket','ticket@@@sDkXJ56FpqKDbDBdoKvW3iDnzA8Hcry9oePdmspGVtki_ldaGTWE80e3d2olUDy52lTt-CxuMT_oo_-4hm9ObA'),(424,'wx00f7d56d549f82ce',20160830124327,'component_verify_ticket','ticket@@@sDkXJ56FpqKDbDBdoKvW3iDnzA8Hcry9oePdmspGVtki_ldaGTWE80e3d2olUDy52lTt-CxuMT_oo_-4hm9ObA'),(425,'wx00f7d56d549f82ce',20160830125330,'component_verify_ticket','ticket@@@sDkXJ56FpqKDbDBdoKvW3iDnzA8Hcry9oePdmspGVtki_ldaGTWE80e3d2olUDy52lTt-CxuMT_oo_-4hm9ObA'),(426,'wx00f7d56d549f82ce',20160830130340,'component_verify_ticket','ticket@@@o6VYJ8ePzlPREa6Z3YI5tPJkTehlvYZcshU0YT_P-_7VH1fiXz--afQNZNJBELHLsRbRMLd6I2nYaJ6fJOfrlw'),(427,'wx00f7d56d549f82ce',20160830131329,'component_verify_ticket','ticket@@@o6VYJ8ePzlPREa6Z3YI5tPJkTehlvYZcshU0YT_P-_7VH1fiXz--afQNZNJBELHLsRbRMLd6I2nYaJ6fJOfrlw'),(428,'wx00f7d56d549f82ce',20160830132328,'component_verify_ticket','ticket@@@o6VYJ8ePzlPREa6Z3YI5tPJkTehlvYZcshU0YT_P-_7VH1fiXz--afQNZNJBELHLsRbRMLd6I2nYaJ6fJOfrlw'),(429,'wx00f7d56d549f82ce',20160830133328,'component_verify_ticket','ticket@@@o6VYJ8ePzlPREa6Z3YI5tPJkTehlvYZcshU0YT_P-_7VH1fiXz--afQNZNJBELHLsRbRMLd6I2nYaJ6fJOfrlw'),(430,'wx00f7d56d549f82ce',20160830134321,'component_verify_ticket','ticket@@@o6VYJ8ePzlPREa6Z3YI5tPJkTehlvYZcshU0YT_P-_7VH1fiXz--afQNZNJBELHLsRbRMLd6I2nYaJ6fJOfrlw'),(431,'wx00f7d56d549f82ce',20160830135327,'component_verify_ticket','ticket@@@o6VYJ8ePzlPREa6Z3YI5tPJkTehlvYZcshU0YT_P-_7VH1fiXz--afQNZNJBELHLsRbRMLd6I2nYaJ6fJOfrlw'),(432,'wx00f7d56d549f82ce',20160830140329,'component_verify_ticket','ticket@@@g3yaw5BLB4dmVf8Can15cJ--MlQya76j-0MN7_2GLe4g0Esm2yzwJo74reki-HZPlcIuODq5smG-aKhEbXTppQ'),(433,'wx00f7d56d549f82ce',20160830141329,'component_verify_ticket','ticket@@@g3yaw5BLB4dmVf8Can15cJ--MlQya76j-0MN7_2GLe4g0Esm2yzwJo74reki-HZPlcIuODq5smG-aKhEbXTppQ'),(434,'wx00f7d56d549f82ce',20160830142329,'component_verify_ticket','ticket@@@g3yaw5BLB4dmVf8Can15cJ--MlQya76j-0MN7_2GLe4g0Esm2yzwJo74reki-HZPlcIuODq5smG-aKhEbXTppQ'),(435,'wx00f7d56d549f82ce',20160830143330,'component_verify_ticket','ticket@@@g3yaw5BLB4dmVf8Can15cJ--MlQya76j-0MN7_2GLe4g0Esm2yzwJo74reki-HZPlcIuODq5smG-aKhEbXTppQ'),(436,'wx00f7d56d549f82ce',20160830144328,'component_verify_ticket','ticket@@@g3yaw5BLB4dmVf8Can15cJ--MlQya76j-0MN7_2GLe4g0Esm2yzwJo74reki-HZPlcIuODq5smG-aKhEbXTppQ'),(437,'wx00f7d56d549f82ce',20160830145326,'component_verify_ticket','ticket@@@g3yaw5BLB4dmVf8Can15cJ--MlQya76j-0MN7_2GLe4g0Esm2yzwJo74reki-HZPlcIuODq5smG-aKhEbXTppQ'),(438,'wx00f7d56d549f82ce',20160830150328,'component_verify_ticket','ticket@@@7ogXYjul0oN8cPvEWM5_yH6JzJsG62Dfb9hEd72uO7gnTEZ6pF0Vvc80YNz_ZTglRbnR1c4qWHUCSgHYceky8Q'),(439,'wx00f7d56d549f82ce',20160830151329,'component_verify_ticket','ticket@@@7ogXYjul0oN8cPvEWM5_yH6JzJsG62Dfb9hEd72uO7gnTEZ6pF0Vvc80YNz_ZTglRbnR1c4qWHUCSgHYceky8Q'),(440,'wx00f7d56d549f82ce',20160830152331,'component_verify_ticket','ticket@@@7ogXYjul0oN8cPvEWM5_yH6JzJsG62Dfb9hEd72uO7gnTEZ6pF0Vvc80YNz_ZTglRbnR1c4qWHUCSgHYceky8Q'),(441,'wx00f7d56d549f82ce',20160830153331,'component_verify_ticket','ticket@@@7ogXYjul0oN8cPvEWM5_yH6JzJsG62Dfb9hEd72uO7gnTEZ6pF0Vvc80YNz_ZTglRbnR1c4qWHUCSgHYceky8Q'),(442,'wx00f7d56d549f82ce',20160830154327,'component_verify_ticket','ticket@@@7ogXYjul0oN8cPvEWM5_yH6JzJsG62Dfb9hEd72uO7gnTEZ6pF0Vvc80YNz_ZTglRbnR1c4qWHUCSgHYceky8Q'),(443,'wx00f7d56d549f82ce',20160830155330,'component_verify_ticket','ticket@@@7ogXYjul0oN8cPvEWM5_yH6JzJsG62Dfb9hEd72uO7gnTEZ6pF0Vvc80YNz_ZTglRbnR1c4qWHUCSgHYceky8Q'),(444,'wx00f7d56d549f82ce',20160830160334,'component_verify_ticket','ticket@@@Rb8XWXU3CGEKmszHkagovcpncx44Ta2f7UHDDJlUrEZ51yQLUj0KfQ-QmEmE0B5-p-bhP8Sgrbm4zipgYvSyYA'),(445,'wx00f7d56d549f82ce',20160830161332,'component_verify_ticket','ticket@@@Rb8XWXU3CGEKmszHkagovcpncx44Ta2f7UHDDJlUrEZ51yQLUj0KfQ-QmEmE0B5-p-bhP8Sgrbm4zipgYvSyYA'),(446,'wx00f7d56d549f82ce',20160830162330,'component_verify_ticket','ticket@@@Rb8XWXU3CGEKmszHkagovcpncx44Ta2f7UHDDJlUrEZ51yQLUj0KfQ-QmEmE0B5-p-bhP8Sgrbm4zipgYvSyYA'),(447,'wx00f7d56d549f82ce',20160830163330,'component_verify_ticket','ticket@@@Rb8XWXU3CGEKmszHkagovcpncx44Ta2f7UHDDJlUrEZ51yQLUj0KfQ-QmEmE0B5-p-bhP8Sgrbm4zipgYvSyYA'),(448,'wx00f7d56d549f82ce',20160830164327,'component_verify_ticket','ticket@@@Rb8XWXU3CGEKmszHkagovcpncx44Ta2f7UHDDJlUrEZ51yQLUj0KfQ-QmEmE0B5-p-bhP8Sgrbm4zipgYvSyYA'),(449,'wx00f7d56d549f82ce',20160830165331,'component_verify_ticket','ticket@@@Rb8XWXU3CGEKmszHkagovcpncx44Ta2f7UHDDJlUrEZ51yQLUj0KfQ-QmEmE0B5-p-bhP8Sgrbm4zipgYvSyYA'),(450,'wx00f7d56d549f82ce',20160830170334,'component_verify_ticket','ticket@@@CkCF_tJw5NK1PUuXN8sVcgXW02x6HjMrLEhuV7Qvf8ZsvLOc_uXhErv8eBsD_QSl57J9KlgTNZO6OAIAyckbTw'),(451,'wx00f7d56d549f82ce',20160830171333,'component_verify_ticket','ticket@@@CkCF_tJw5NK1PUuXN8sVcgXW02x6HjMrLEhuV7Qvf8ZsvLOc_uXhErv8eBsD_QSl57J9KlgTNZO6OAIAyckbTw'),(452,'wx00f7d56d549f82ce',20160830172331,'component_verify_ticket','ticket@@@CkCF_tJw5NK1PUuXN8sVcgXW02x6HjMrLEhuV7Qvf8ZsvLOc_uXhErv8eBsD_QSl57J9KlgTNZO6OAIAyckbTw'),(453,'wx00f7d56d549f82ce',20160830173330,'component_verify_ticket','ticket@@@CkCF_tJw5NK1PUuXN8sVcgXW02x6HjMrLEhuV7Qvf8ZsvLOc_uXhErv8eBsD_QSl57J9KlgTNZO6OAIAyckbTw'),(454,'wx00f7d56d549f82ce',20160830174331,'component_verify_ticket','ticket@@@CkCF_tJw5NK1PUuXN8sVcgXW02x6HjMrLEhuV7Qvf8ZsvLOc_uXhErv8eBsD_QSl57J9KlgTNZO6OAIAyckbTw'),(455,'wx00f7d56d549f82ce',20160830175329,'component_verify_ticket','ticket@@@CkCF_tJw5NK1PUuXN8sVcgXW02x6HjMrLEhuV7Qvf8ZsvLOc_uXhErv8eBsD_QSl57J9KlgTNZO6OAIAyckbTw'),(456,'wx00f7d56d549f82ce',20160830180332,'component_verify_ticket','ticket@@@vY1oIstBXs-DEl2PG031tskm-FHqFi7BjK3r1Nl64EZRws69YM1JAoLFaYiDM6RhClzDTZz7S5LXlJsoH6k1Pg'),(457,'wx00f7d56d549f82ce',20160830181330,'component_verify_ticket','ticket@@@vY1oIstBXs-DEl2PG031tskm-FHqFi7BjK3r1Nl64EZRws69YM1JAoLFaYiDM6RhClzDTZz7S5LXlJsoH6k1Pg'),(458,'wx00f7d56d549f82ce',20160830182330,'component_verify_ticket','ticket@@@vY1oIstBXs-DEl2PG031tskm-FHqFi7BjK3r1Nl64EZRws69YM1JAoLFaYiDM6RhClzDTZz7S5LXlJsoH6k1Pg'),(459,'wx00f7d56d549f82ce',20160830183328,'component_verify_ticket','ticket@@@vY1oIstBXs-DEl2PG031tskm-FHqFi7BjK3r1Nl64EZRws69YM1JAoLFaYiDM6RhClzDTZz7S5LXlJsoH6k1Pg'),(460,'wx00f7d56d549f82ce',20160830184328,'component_verify_ticket','ticket@@@vY1oIstBXs-DEl2PG031tskm-FHqFi7BjK3r1Nl64EZRws69YM1JAoLFaYiDM6RhClzDTZz7S5LXlJsoH6k1Pg'),(461,'wx00f7d56d549f82ce',20160830185327,'component_verify_ticket','ticket@@@vY1oIstBXs-DEl2PG031tskm-FHqFi7BjK3r1Nl64EZRws69YM1JAoLFaYiDM6RhClzDTZz7S5LXlJsoH6k1Pg'),(462,'wx00f7d56d549f82ce',20160830190324,'component_verify_ticket','ticket@@@EHQ_JIgDwvN9XTDRXNYTWb2HLjOq20uCs4C2OsMaJCQGwdyNu8xg88VSa2JqKZkKycqmzR1h4-5kgwbgGIpO6w'),(463,'wx00f7d56d549f82ce',20160830191330,'component_verify_ticket','ticket@@@EHQ_JIgDwvN9XTDRXNYTWb2HLjOq20uCs4C2OsMaJCQGwdyNu8xg88VSa2JqKZkKycqmzR1h4-5kgwbgGIpO6w'),(464,'wx00f7d56d549f82ce',20160830192331,'component_verify_ticket','ticket@@@EHQ_JIgDwvN9XTDRXNYTWb2HLjOq20uCs4C2OsMaJCQGwdyNu8xg88VSa2JqKZkKycqmzR1h4-5kgwbgGIpO6w'),(465,'wx00f7d56d549f82ce',20160830193330,'component_verify_ticket','ticket@@@EHQ_JIgDwvN9XTDRXNYTWb2HLjOq20uCs4C2OsMaJCQGwdyNu8xg88VSa2JqKZkKycqmzR1h4-5kgwbgGIpO6w'),(466,'wx00f7d56d549f82ce',20160830194329,'component_verify_ticket','ticket@@@EHQ_JIgDwvN9XTDRXNYTWb2HLjOq20uCs4C2OsMaJCQGwdyNu8xg88VSa2JqKZkKycqmzR1h4-5kgwbgGIpO6w'),(467,'wx00f7d56d549f82ce',20160830195327,'component_verify_ticket','ticket@@@EHQ_JIgDwvN9XTDRXNYTWb2HLjOq20uCs4C2OsMaJCQGwdyNu8xg88VSa2JqKZkKycqmzR1h4-5kgwbgGIpO6w'),(468,'wx00f7d56d549f82ce',20160830200331,'component_verify_ticket','ticket@@@kgp08v2UID8gjhRhHK5N8CuPu4UAP9dX7N2P1UrYZNh4Farj6OWvMOicYwqojuIG6jRHHWMdP1fFuI3mxB6smg'),(469,'wx00f7d56d549f82ce',20160830201330,'component_verify_ticket','ticket@@@kgp08v2UID8gjhRhHK5N8CuPu4UAP9dX7N2P1UrYZNh4Farj6OWvMOicYwqojuIG6jRHHWMdP1fFuI3mxB6smg'),(470,'wx00f7d56d549f82ce',20160830202333,'component_verify_ticket','ticket@@@kgp08v2UID8gjhRhHK5N8CuPu4UAP9dX7N2P1UrYZNh4Farj6OWvMOicYwqojuIG6jRHHWMdP1fFuI3mxB6smg'),(471,'wx00f7d56d549f82ce',20160830203330,'component_verify_ticket','ticket@@@kgp08v2UID8gjhRhHK5N8CuPu4UAP9dX7N2P1UrYZNh4Farj6OWvMOicYwqojuIG6jRHHWMdP1fFuI3mxB6smg'),(472,'wx00f7d56d549f82ce',20160830204329,'component_verify_ticket','ticket@@@kgp08v2UID8gjhRhHK5N8CuPu4UAP9dX7N2P1UrYZNh4Farj6OWvMOicYwqojuIG6jRHHWMdP1fFuI3mxB6smg'),(473,'wx00f7d56d549f82ce',20160830205331,'component_verify_ticket','ticket@@@kgp08v2UID8gjhRhHK5N8CuPu4UAP9dX7N2P1UrYZNh4Farj6OWvMOicYwqojuIG6jRHHWMdP1fFuI3mxB6smg'),(474,'wx00f7d56d549f82ce',20160830210334,'component_verify_ticket','ticket@@@xnazqZ9cAe8jRHkupeAy05WHYyzT3OFS3lH7Wk0z_k8R7XXY_lYBCrgudfgxJOwDH5L2F2qqGEg-6tspHHD4Jg'),(475,'wx00f7d56d549f82ce',20160830211332,'component_verify_ticket','ticket@@@xnazqZ9cAe8jRHkupeAy05WHYyzT3OFS3lH7Wk0z_k8R7XXY_lYBCrgudfgxJOwDH5L2F2qqGEg-6tspHHD4Jg'),(476,'wx00f7d56d549f82ce',20160830212334,'component_verify_ticket','ticket@@@xnazqZ9cAe8jRHkupeAy05WHYyzT3OFS3lH7Wk0z_k8R7XXY_lYBCrgudfgxJOwDH5L2F2qqGEg-6tspHHD4Jg'),(477,'wx00f7d56d549f82ce',20160830213329,'component_verify_ticket','ticket@@@xnazqZ9cAe8jRHkupeAy05WHYyzT3OFS3lH7Wk0z_k8R7XXY_lYBCrgudfgxJOwDH5L2F2qqGEg-6tspHHD4Jg'),(478,'wx00f7d56d549f82ce',20160830214329,'component_verify_ticket','ticket@@@xnazqZ9cAe8jRHkupeAy05WHYyzT3OFS3lH7Wk0z_k8R7XXY_lYBCrgudfgxJOwDH5L2F2qqGEg-6tspHHD4Jg'),(479,'wx00f7d56d549f82ce',20160830215329,'component_verify_ticket','ticket@@@xnazqZ9cAe8jRHkupeAy05WHYyzT3OFS3lH7Wk0z_k8R7XXY_lYBCrgudfgxJOwDH5L2F2qqGEg-6tspHHD4Jg'),(480,'wx00f7d56d549f82ce',20160830220326,'component_verify_ticket','ticket@@@pJzvtQQWypjK685xwPDbdymeVK4gNHZGRHaVIIhN3am4XiNPWUkgEzmMdbB54_QrBIOk7Kj4ygGQSaGwUkCkVw'),(481,'wx00f7d56d549f82ce',20160830221331,'component_verify_ticket','ticket@@@pJzvtQQWypjK685xwPDbdymeVK4gNHZGRHaVIIhN3am4XiNPWUkgEzmMdbB54_QrBIOk7Kj4ygGQSaGwUkCkVw'),(482,'wx00f7d56d549f82ce',20160830222331,'component_verify_ticket','ticket@@@pJzvtQQWypjK685xwPDbdymeVK4gNHZGRHaVIIhN3am4XiNPWUkgEzmMdbB54_QrBIOk7Kj4ygGQSaGwUkCkVw'),(483,'wx00f7d56d549f82ce',20160830223330,'component_verify_ticket','ticket@@@pJzvtQQWypjK685xwPDbdymeVK4gNHZGRHaVIIhN3am4XiNPWUkgEzmMdbB54_QrBIOk7Kj4ygGQSaGwUkCkVw'),(484,'wx00f7d56d549f82ce',20160830224328,'component_verify_ticket','ticket@@@pJzvtQQWypjK685xwPDbdymeVK4gNHZGRHaVIIhN3am4XiNPWUkgEzmMdbB54_QrBIOk7Kj4ygGQSaGwUkCkVw'),(485,'wx00f7d56d549f82ce',20160830225326,'component_verify_ticket','ticket@@@pJzvtQQWypjK685xwPDbdymeVK4gNHZGRHaVIIhN3am4XiNPWUkgEzmMdbB54_QrBIOk7Kj4ygGQSaGwUkCkVw'),(486,'wx00f7d56d549f82ce',20160830230328,'component_verify_ticket','ticket@@@hSzdiPsVip1sumhkaeZIHw3o9ZcHe8KuFOXsycYQnGYC_z0TDgWkduD8C-Rnp-wqJ4E829BuuuIURfbRSjZ5mA'),(487,'wx00f7d56d549f82ce',20160830231329,'component_verify_ticket','ticket@@@hSzdiPsVip1sumhkaeZIHw3o9ZcHe8KuFOXsycYQnGYC_z0TDgWkduD8C-Rnp-wqJ4E829BuuuIURfbRSjZ5mA'),(488,'wx00f7d56d549f82ce',20160830232329,'component_verify_ticket','ticket@@@hSzdiPsVip1sumhkaeZIHw3o9ZcHe8KuFOXsycYQnGYC_z0TDgWkduD8C-Rnp-wqJ4E829BuuuIURfbRSjZ5mA'),(489,'wx00f7d56d549f82ce',20160830233328,'component_verify_ticket','ticket@@@hSzdiPsVip1sumhkaeZIHw3o9ZcHe8KuFOXsycYQnGYC_z0TDgWkduD8C-Rnp-wqJ4E829BuuuIURfbRSjZ5mA'),(490,'wx00f7d56d549f82ce',20160830234319,'component_verify_ticket','ticket@@@hSzdiPsVip1sumhkaeZIHw3o9ZcHe8KuFOXsycYQnGYC_z0TDgWkduD8C-Rnp-wqJ4E829BuuuIURfbRSjZ5mA'),(491,'wx00f7d56d549f82ce',20160830235326,'component_verify_ticket','ticket@@@hSzdiPsVip1sumhkaeZIHw3o9ZcHe8KuFOXsycYQnGYC_z0TDgWkduD8C-Rnp-wqJ4E829BuuuIURfbRSjZ5mA'),(492,'wx00f7d56d549f82ce',20160831000329,'component_verify_ticket','ticket@@@UaJCJHkockAfrUMzjUWsp2IUl6M_131JEs2ncMOhXYenkvOJM9JUqmXuXrpFtJhqHVgPF4Oc2GL6CUqqKw2pEw'),(493,'wx00f7d56d549f82ce',20160831001328,'component_verify_ticket','ticket@@@UaJCJHkockAfrUMzjUWsp2IUl6M_131JEs2ncMOhXYenkvOJM9JUqmXuXrpFtJhqHVgPF4Oc2GL6CUqqKw2pEw'),(494,'wx00f7d56d549f82ce',20160831002328,'component_verify_ticket','ticket@@@UaJCJHkockAfrUMzjUWsp2IUl6M_131JEs2ncMOhXYenkvOJM9JUqmXuXrpFtJhqHVgPF4Oc2GL6CUqqKw2pEw'),(495,'wx00f7d56d549f82ce',20160831003327,'component_verify_ticket','ticket@@@UaJCJHkockAfrUMzjUWsp2IUl6M_131JEs2ncMOhXYenkvOJM9JUqmXuXrpFtJhqHVgPF4Oc2GL6CUqqKw2pEw'),(496,'wx00f7d56d549f82ce',20160831004321,'component_verify_ticket','ticket@@@UaJCJHkockAfrUMzjUWsp2IUl6M_131JEs2ncMOhXYenkvOJM9JUqmXuXrpFtJhqHVgPF4Oc2GL6CUqqKw2pEw'),(497,'wx00f7d56d549f82ce',20160831005317,'component_verify_ticket','ticket@@@UaJCJHkockAfrUMzjUWsp2IUl6M_131JEs2ncMOhXYenkvOJM9JUqmXuXrpFtJhqHVgPF4Oc2GL6CUqqKw2pEw'),(498,'wx00f7d56d549f82ce',20160831010318,'component_verify_ticket','ticket@@@b1UspgkncAP1nhg2cEuByMu-uncNBF7xGdpvefppU0QV5pstDvA-07PZ78zrgr-rqaYGMzTYOqC6JQmyxmkjHA'),(499,'wx00f7d56d549f82ce',20160831011319,'component_verify_ticket','ticket@@@b1UspgkncAP1nhg2cEuByMu-uncNBF7xGdpvefppU0QV5pstDvA-07PZ78zrgr-rqaYGMzTYOqC6JQmyxmkjHA'),(500,'wx00f7d56d549f82ce',20160831012323,'component_verify_ticket','ticket@@@b1UspgkncAP1nhg2cEuByMu-uncNBF7xGdpvefppU0QV5pstDvA-07PZ78zrgr-rqaYGMzTYOqC6JQmyxmkjHA'),(501,'wx00f7d56d549f82ce',20160831013324,'component_verify_ticket','ticket@@@b1UspgkncAP1nhg2cEuByMu-uncNBF7xGdpvefppU0QV5pstDvA-07PZ78zrgr-rqaYGMzTYOqC6JQmyxmkjHA'),(502,'wx00f7d56d549f82ce',20160831014322,'component_verify_ticket','ticket@@@b1UspgkncAP1nhg2cEuByMu-uncNBF7xGdpvefppU0QV5pstDvA-07PZ78zrgr-rqaYGMzTYOqC6JQmyxmkjHA'),(503,'wx00f7d56d549f82ce',20160831015321,'component_verify_ticket','ticket@@@b1UspgkncAP1nhg2cEuByMu-uncNBF7xGdpvefppU0QV5pstDvA-07PZ78zrgr-rqaYGMzTYOqC6JQmyxmkjHA'),(504,'wx00f7d56d549f82ce',20160831020322,'component_verify_ticket','ticket@@@2IaKVRJj0welgBmZxhHKwWmsn9yYn_V0ULLkc2505ttAKJRWzGDHqneKtE52rmHIn1z2sqEweNyO_m6EDXZWwQ'),(505,'wx00f7d56d549f82ce',20160831021324,'component_verify_ticket','ticket@@@2IaKVRJj0welgBmZxhHKwWmsn9yYn_V0ULLkc2505ttAKJRWzGDHqneKtE52rmHIn1z2sqEweNyO_m6EDXZWwQ'),(506,'wx00f7d56d549f82ce',20160831022325,'component_verify_ticket','ticket@@@2IaKVRJj0welgBmZxhHKwWmsn9yYn_V0ULLkc2505ttAKJRWzGDHqneKtE52rmHIn1z2sqEweNyO_m6EDXZWwQ'),(507,'wx00f7d56d549f82ce',20160831023324,'component_verify_ticket','ticket@@@2IaKVRJj0welgBmZxhHKwWmsn9yYn_V0ULLkc2505ttAKJRWzGDHqneKtE52rmHIn1z2sqEweNyO_m6EDXZWwQ'),(508,'wx00f7d56d549f82ce',20160831024321,'component_verify_ticket','ticket@@@2IaKVRJj0welgBmZxhHKwWmsn9yYn_V0ULLkc2505ttAKJRWzGDHqneKtE52rmHIn1z2sqEweNyO_m6EDXZWwQ'),(509,'wx00f7d56d549f82ce',20160831025321,'component_verify_ticket','ticket@@@2IaKVRJj0welgBmZxhHKwWmsn9yYn_V0ULLkc2505ttAKJRWzGDHqneKtE52rmHIn1z2sqEweNyO_m6EDXZWwQ'),(510,'wx00f7d56d549f82ce',20160831030324,'component_verify_ticket','ticket@@@V6MoSyfchXw3GJ-p4CIbL8T2XQETXapHAEHg1lAZyjqFYzGfoOKrt_bhbcc9ldIbuVm3DpZPDe9ORoJ71SooWQ'),(511,'wx00f7d56d549f82ce',20160831031322,'component_verify_ticket','ticket@@@V6MoSyfchXw3GJ-p4CIbL8T2XQETXapHAEHg1lAZyjqFYzGfoOKrt_bhbcc9ldIbuVm3DpZPDe9ORoJ71SooWQ'),(512,'wx00f7d56d549f82ce',20160831032302,'component_verify_ticket','ticket@@@V6MoSyfchXw3GJ-p4CIbL8T2XQETXapHAEHg1lAZyjqFYzGfoOKrt_bhbcc9ldIbuVm3DpZPDe9ORoJ71SooWQ'),(513,'wx00f7d56d549f82ce',20160831033322,'component_verify_ticket','ticket@@@V6MoSyfchXw3GJ-p4CIbL8T2XQETXapHAEHg1lAZyjqFYzGfoOKrt_bhbcc9ldIbuVm3DpZPDe9ORoJ71SooWQ'),(514,'wx00f7d56d549f82ce',20160831034322,'component_verify_ticket','ticket@@@V6MoSyfchXw3GJ-p4CIbL8T2XQETXapHAEHg1lAZyjqFYzGfoOKrt_bhbcc9ldIbuVm3DpZPDe9ORoJ71SooWQ'),(515,'wx00f7d56d549f82ce',20160831035324,'component_verify_ticket','ticket@@@V6MoSyfchXw3GJ-p4CIbL8T2XQETXapHAEHg1lAZyjqFYzGfoOKrt_bhbcc9ldIbuVm3DpZPDe9ORoJ71SooWQ'),(516,'wx00f7d56d549f82ce',20160831040325,'component_verify_ticket','ticket@@@mTetJ9BHjKr7YX9TLAvxVRWqZf9vXD-Igq1Quz-uyqetlm6NNGNUN-rrohbwQGHMLgQW-f-1BVSl3xPyj231Tw'),(517,'wx00f7d56d549f82ce',20160831041330,'component_verify_ticket','ticket@@@mTetJ9BHjKr7YX9TLAvxVRWqZf9vXD-Igq1Quz-uyqetlm6NNGNUN-rrohbwQGHMLgQW-f-1BVSl3xPyj231Tw'),(518,'wx00f7d56d549f82ce',20160831042326,'component_verify_ticket','ticket@@@mTetJ9BHjKr7YX9TLAvxVRWqZf9vXD-Igq1Quz-uyqetlm6NNGNUN-rrohbwQGHMLgQW-f-1BVSl3xPyj231Tw'),(519,'wx00f7d56d549f82ce',20160831043321,'component_verify_ticket','ticket@@@mTetJ9BHjKr7YX9TLAvxVRWqZf9vXD-Igq1Quz-uyqetlm6NNGNUN-rrohbwQGHMLgQW-f-1BVSl3xPyj231Tw'),(520,'wx00f7d56d549f82ce',20160831044315,'component_verify_ticket','ticket@@@mTetJ9BHjKr7YX9TLAvxVRWqZf9vXD-Igq1Quz-uyqetlm6NNGNUN-rrohbwQGHMLgQW-f-1BVSl3xPyj231Tw'),(521,'wx00f7d56d549f82ce',20160831045313,'component_verify_ticket','ticket@@@mTetJ9BHjKr7YX9TLAvxVRWqZf9vXD-Igq1Quz-uyqetlm6NNGNUN-rrohbwQGHMLgQW-f-1BVSl3xPyj231Tw'),(522,'wx00f7d56d549f82ce',20160831050319,'component_verify_ticket','ticket@@@THTgsXsP4ijIp3S-pK9NCu5_s2gclm9T3WfOmCOzwUgS1BdNm-fZfQig9gvhXM_-FG860RNG85MjRvB8tVJa1g'),(523,'wx00f7d56d549f82ce',20160831051325,'component_verify_ticket','ticket@@@THTgsXsP4ijIp3S-pK9NCu5_s2gclm9T3WfOmCOzwUgS1BdNm-fZfQig9gvhXM_-FG860RNG85MjRvB8tVJa1g'),(524,'wx00f7d56d549f82ce',20160831052314,'component_verify_ticket','ticket@@@THTgsXsP4ijIp3S-pK9NCu5_s2gclm9T3WfOmCOzwUgS1BdNm-fZfQig9gvhXM_-FG860RNG85MjRvB8tVJa1g'),(525,'wx00f7d56d549f82ce',20160831053315,'component_verify_ticket','ticket@@@THTgsXsP4ijIp3S-pK9NCu5_s2gclm9T3WfOmCOzwUgS1BdNm-fZfQig9gvhXM_-FG860RNG85MjRvB8tVJa1g'),(526,'wx00f7d56d549f82ce',20160831054317,'component_verify_ticket','ticket@@@THTgsXsP4ijIp3S-pK9NCu5_s2gclm9T3WfOmCOzwUgS1BdNm-fZfQig9gvhXM_-FG860RNG85MjRvB8tVJa1g'),(527,'wx00f7d56d549f82ce',20160831055317,'component_verify_ticket','ticket@@@THTgsXsP4ijIp3S-pK9NCu5_s2gclm9T3WfOmCOzwUgS1BdNm-fZfQig9gvhXM_-FG860RNG85MjRvB8tVJa1g'),(528,'wx00f7d56d549f82ce',20160831060321,'component_verify_ticket','ticket@@@lMPjdbuDw8lK336kP75Jh7gioVNuE8UVHezbIoZCM_HqTe9S8YcbmWdfXkD9YEqwF3EMJ2HIvZixZk4_xD6CyQ'),(529,'wx00f7d56d549f82ce',20160831061325,'component_verify_ticket','ticket@@@lMPjdbuDw8lK336kP75Jh7gioVNuE8UVHezbIoZCM_HqTe9S8YcbmWdfXkD9YEqwF3EMJ2HIvZixZk4_xD6CyQ'),(530,'wx00f7d56d549f82ce',20160831062319,'component_verify_ticket','ticket@@@lMPjdbuDw8lK336kP75Jh7gioVNuE8UVHezbIoZCM_HqTe9S8YcbmWdfXkD9YEqwF3EMJ2HIvZixZk4_xD6CyQ'),(531,'wx00f7d56d549f82ce',20160831063331,'component_verify_ticket','ticket@@@lMPjdbuDw8lK336kP75Jh7gioVNuE8UVHezbIoZCM_HqTe9S8YcbmWdfXkD9YEqwF3EMJ2HIvZixZk4_xD6CyQ'),(532,'wx00f7d56d549f82ce',20160831064313,'component_verify_ticket','ticket@@@lMPjdbuDw8lK336kP75Jh7gioVNuE8UVHezbIoZCM_HqTe9S8YcbmWdfXkD9YEqwF3EMJ2HIvZixZk4_xD6CyQ'),(533,'wx00f7d56d549f82ce',20160831065315,'component_verify_ticket','ticket@@@lMPjdbuDw8lK336kP75Jh7gioVNuE8UVHezbIoZCM_HqTe9S8YcbmWdfXkD9YEqwF3EMJ2HIvZixZk4_xD6CyQ'),(534,'wx00f7d56d549f82ce',20160831070321,'component_verify_ticket','ticket@@@9I5hk-b37_LkfBkUo6Ypx6ATsIMHNfb8xyTLf4X1IUVtM6Yip90hfCTP_GcXxXr8u0aFfMq6J1gp8AuxjOY-og'),(535,'wx00f7d56d549f82ce',20160831071323,'component_verify_ticket','ticket@@@9I5hk-b37_LkfBkUo6Ypx6ATsIMHNfb8xyTLf4X1IUVtM6Yip90hfCTP_GcXxXr8u0aFfMq6J1gp8AuxjOY-og'),(536,'wx00f7d56d549f82ce',20160831072322,'component_verify_ticket','ticket@@@9I5hk-b37_LkfBkUo6Ypx6ATsIMHNfb8xyTLf4X1IUVtM6Yip90hfCTP_GcXxXr8u0aFfMq6J1gp8AuxjOY-og'),(537,'wx00f7d56d549f82ce',20160831073322,'component_verify_ticket','ticket@@@9I5hk-b37_LkfBkUo6Ypx6ATsIMHNfb8xyTLf4X1IUVtM6Yip90hfCTP_GcXxXr8u0aFfMq6J1gp8AuxjOY-og'),(538,'wx00f7d56d549f82ce',20160831074322,'component_verify_ticket','ticket@@@9I5hk-b37_LkfBkUo6Ypx6ATsIMHNfb8xyTLf4X1IUVtM6Yip90hfCTP_GcXxXr8u0aFfMq6J1gp8AuxjOY-og'),(539,'wx00f7d56d549f82ce',20160831075323,'component_verify_ticket','ticket@@@9I5hk-b37_LkfBkUo6Ypx6ATsIMHNfb8xyTLf4X1IUVtM6Yip90hfCTP_GcXxXr8u0aFfMq6J1gp8AuxjOY-og'),(540,'wx00f7d56d549f82ce',20160831080326,'component_verify_ticket','ticket@@@Qsg1UAKRLersZlJT7TJHuK2cJUMejc1vWgajq_3B7HjrSGEagUHqlBaY6dZHG3jMtyWp4XDvtUyex0qJuE1XSQ'),(541,'wx00f7d56d549f82ce',20160831081326,'component_verify_ticket','ticket@@@Qsg1UAKRLersZlJT7TJHuK2cJUMejc1vWgajq_3B7HjrSGEagUHqlBaY6dZHG3jMtyWp4XDvtUyex0qJuE1XSQ'),(542,'wx00f7d56d549f82ce',20160831082327,'component_verify_ticket','ticket@@@Qsg1UAKRLersZlJT7TJHuK2cJUMejc1vWgajq_3B7HjrSGEagUHqlBaY6dZHG3jMtyWp4XDvtUyex0qJuE1XSQ'),(543,'wx00f7d56d549f82ce',20160831083327,'component_verify_ticket','ticket@@@Qsg1UAKRLersZlJT7TJHuK2cJUMejc1vWgajq_3B7HjrSGEagUHqlBaY6dZHG3jMtyWp4XDvtUyex0qJuE1XSQ'),(544,'wx00f7d56d549f82ce',20160831084326,'component_verify_ticket','ticket@@@Qsg1UAKRLersZlJT7TJHuK2cJUMejc1vWgajq_3B7HjrSGEagUHqlBaY6dZHG3jMtyWp4XDvtUyex0qJuE1XSQ'),(545,'wx00f7d56d549f82ce',20160831085325,'component_verify_ticket','ticket@@@Qsg1UAKRLersZlJT7TJHuK2cJUMejc1vWgajq_3B7HjrSGEagUHqlBaY6dZHG3jMtyWp4XDvtUyex0qJuE1XSQ'),(546,'wx00f7d56d549f82ce',20160831090332,'component_verify_ticket','ticket@@@JmwzcDZJ7bk1eYQvOqpLD6J9bZUilYA75Iy_SbV4PP1-qA4heZxeiyyG-RQGiDAW9mPKUFy8U6S7HXT2fk9mmg'),(547,'wx00f7d56d549f82ce',20160831091330,'component_verify_ticket','ticket@@@JmwzcDZJ7bk1eYQvOqpLD6J9bZUilYA75Iy_SbV4PP1-qA4heZxeiyyG-RQGiDAW9mPKUFy8U6S7HXT2fk9mmg'),(548,'wx00f7d56d549f82ce',20160831092330,'component_verify_ticket','ticket@@@JmwzcDZJ7bk1eYQvOqpLD6J9bZUilYA75Iy_SbV4PP1-qA4heZxeiyyG-RQGiDAW9mPKUFy8U6S7HXT2fk9mmg'),(549,'wx00f7d56d549f82ce',20160831093330,'component_verify_ticket','ticket@@@JmwzcDZJ7bk1eYQvOqpLD6J9bZUilYA75Iy_SbV4PP1-qA4heZxeiyyG-RQGiDAW9mPKUFy8U6S7HXT2fk9mmg'),(550,'wx00f7d56d549f82ce',20160831094328,'component_verify_ticket','ticket@@@JmwzcDZJ7bk1eYQvOqpLD6J9bZUilYA75Iy_SbV4PP1-qA4heZxeiyyG-RQGiDAW9mPKUFy8U6S7HXT2fk9mmg'),(551,'wx00f7d56d549f82ce',20160831095330,'component_verify_ticket','ticket@@@JmwzcDZJ7bk1eYQvOqpLD6J9bZUilYA75Iy_SbV4PP1-qA4heZxeiyyG-RQGiDAW9mPKUFy8U6S7HXT2fk9mmg'),(552,'wx00f7d56d549f82ce',20160831100336,'component_verify_ticket','ticket@@@-tezlhs0hrC1yM00RNTtUvzKSrB7AyvDTIX6ZZUOx1sg6hsHKXP9rVvapn1uijMvnCbmAG_NYE1jPxU2xV26oA'),(553,'wx00f7d56d549f82ce',20160831101333,'component_verify_ticket','ticket@@@-tezlhs0hrC1yM00RNTtUvzKSrB7AyvDTIX6ZZUOx1sg6hsHKXP9rVvapn1uijMvnCbmAG_NYE1jPxU2xV26oA'),(554,'wx00f7d56d549f82ce',20160831102333,'component_verify_ticket','ticket@@@-tezlhs0hrC1yM00RNTtUvzKSrB7AyvDTIX6ZZUOx1sg6hsHKXP9rVvapn1uijMvnCbmAG_NYE1jPxU2xV26oA'),(555,'wx00f7d56d549f82ce',20160831103330,'component_verify_ticket','ticket@@@-tezlhs0hrC1yM00RNTtUvzKSrB7AyvDTIX6ZZUOx1sg6hsHKXP9rVvapn1uijMvnCbmAG_NYE1jPxU2xV26oA'),(556,'wx00f7d56d549f82ce',20160831104331,'component_verify_ticket','ticket@@@-tezlhs0hrC1yM00RNTtUvzKSrB7AyvDTIX6ZZUOx1sg6hsHKXP9rVvapn1uijMvnCbmAG_NYE1jPxU2xV26oA'),(557,'wx00f7d56d549f82ce',20160831105333,'component_verify_ticket','ticket@@@-tezlhs0hrC1yM00RNTtUvzKSrB7AyvDTIX6ZZUOx1sg6hsHKXP9rVvapn1uijMvnCbmAG_NYE1jPxU2xV26oA'),(558,'wx00f7d56d549f82ce',20160831110334,'component_verify_ticket','ticket@@@xxX6KQRq2o07uQ1RhbPOYSfTxeKcYWpu8ifLL7M87_PoqCZntDAvN_eJ9V4h9Recovd2PVeONK6GNK4h4RhqBQ'),(559,'wx00f7d56d549f82ce',20160831111333,'component_verify_ticket','ticket@@@xxX6KQRq2o07uQ1RhbPOYSfTxeKcYWpu8ifLL7M87_PoqCZntDAvN_eJ9V4h9Recovd2PVeONK6GNK4h4RhqBQ'),(560,'wx00f7d56d549f82ce',20160831112334,'component_verify_ticket','ticket@@@xxX6KQRq2o07uQ1RhbPOYSfTxeKcYWpu8ifLL7M87_PoqCZntDAvN_eJ9V4h9Recovd2PVeONK6GNK4h4RhqBQ'),(561,'wx00f7d56d549f82ce',20160831113333,'component_verify_ticket','ticket@@@xxX6KQRq2o07uQ1RhbPOYSfTxeKcYWpu8ifLL7M87_PoqCZntDAvN_eJ9V4h9Recovd2PVeONK6GNK4h4RhqBQ'),(562,'wx00f7d56d549f82ce',20160831114331,'component_verify_ticket','ticket@@@xxX6KQRq2o07uQ1RhbPOYSfTxeKcYWpu8ifLL7M87_PoqCZntDAvN_eJ9V4h9Recovd2PVeONK6GNK4h4RhqBQ'),(563,'wx00f7d56d549f82ce',20160831115329,'component_verify_ticket','ticket@@@xxX6KQRq2o07uQ1RhbPOYSfTxeKcYWpu8ifLL7M87_PoqCZntDAvN_eJ9V4h9Recovd2PVeONK6GNK4h4RhqBQ'),(564,'wx00f7d56d549f82ce',20160831120332,'component_verify_ticket','ticket@@@LHlLMBJkFV-qIh3Cz5i_MmyAk4tCEx-KI-l8XIrZs7UhJM52gLZB-c-MYT4RJDYXCBHOS6MBsLbkV-1Go_j_Zg'),(565,'wx00f7d56d549f82ce',20160831121332,'component_verify_ticket','ticket@@@LHlLMBJkFV-qIh3Cz5i_MmyAk4tCEx-KI-l8XIrZs7UhJM52gLZB-c-MYT4RJDYXCBHOS6MBsLbkV-1Go_j_Zg'),(566,'wx00f7d56d549f82ce',20160831122331,'component_verify_ticket','ticket@@@LHlLMBJkFV-qIh3Cz5i_MmyAk4tCEx-KI-l8XIrZs7UhJM52gLZB-c-MYT4RJDYXCBHOS6MBsLbkV-1Go_j_Zg'),(567,'wx00f7d56d549f82ce',20160831123331,'component_verify_ticket','ticket@@@LHlLMBJkFV-qIh3Cz5i_MmyAk4tCEx-KI-l8XIrZs7UhJM52gLZB-c-MYT4RJDYXCBHOS6MBsLbkV-1Go_j_Zg'),(568,'wx00f7d56d549f82ce',20160831124329,'component_verify_ticket','ticket@@@LHlLMBJkFV-qIh3Cz5i_MmyAk4tCEx-KI-l8XIrZs7UhJM52gLZB-c-MYT4RJDYXCBHOS6MBsLbkV-1Go_j_Zg'),(569,'wx00f7d56d549f82ce',20160831125330,'component_verify_ticket','ticket@@@LHlLMBJkFV-qIh3Cz5i_MmyAk4tCEx-KI-l8XIrZs7UhJM52gLZB-c-MYT4RJDYXCBHOS6MBsLbkV-1Go_j_Zg'),(570,'wx00f7d56d549f82ce',20160831130332,'component_verify_ticket','ticket@@@ZXJSDtbgow6S89r69-GtR3o_BenYWs1ttHmc31R8MNlg8dlUtML3wF9EMqhK32N9GXu_lZPyk8IPOe0G1uMU1g'),(571,'wx00f7d56d549f82ce',20160831131329,'component_verify_ticket','ticket@@@ZXJSDtbgow6S89r69-GtR3o_BenYWs1ttHmc31R8MNlg8dlUtML3wF9EMqhK32N9GXu_lZPyk8IPOe0G1uMU1g'),(572,'wx00f7d56d549f82ce',20160831132330,'component_verify_ticket','ticket@@@ZXJSDtbgow6S89r69-GtR3o_BenYWs1ttHmc31R8MNlg8dlUtML3wF9EMqhK32N9GXu_lZPyk8IPOe0G1uMU1g'),(573,'wx00f7d56d549f82ce',20160831133329,'component_verify_ticket','ticket@@@ZXJSDtbgow6S89r69-GtR3o_BenYWs1ttHmc31R8MNlg8dlUtML3wF9EMqhK32N9GXu_lZPyk8IPOe0G1uMU1g'),(574,'wx00f7d56d549f82ce',20160831134340,'component_verify_ticket','ticket@@@ZXJSDtbgow6S89r69-GtR3o_BenYWs1ttHmc31R8MNlg8dlUtML3wF9EMqhK32N9GXu_lZPyk8IPOe0G1uMU1g'),(575,'wx00f7d56d549f82ce',20160831135317,'component_verify_ticket','ticket@@@ZXJSDtbgow6S89r69-GtR3o_BenYWs1ttHmc31R8MNlg8dlUtML3wF9EMqhK32N9GXu_lZPyk8IPOe0G1uMU1g'),(576,'wx00f7d56d549f82ce',20160831140331,'component_verify_ticket','ticket@@@dJ9UwQuUJ4lyuG6TPX1jeDZZmpC_Yd8Ayi4NIeet0kFiIH4s3a6AKpj036m72CkgqhLYzSbzF0SrU6NE_LMlRQ'),(577,'wx00f7d56d549f82ce',20160831161332,'component_verify_ticket','ticket@@@_Mn0IQUwS7s0v8TWf9oGK_S7Ee2aar2UlpUZHmun77LJrzr6VR96he_ueGB3PMBN4cvJ6BVxThj8ap1AyZkliQ'),(578,'wx00f7d56d549f82ce',20160831162330,'component_verify_ticket','ticket@@@_Mn0IQUwS7s0v8TWf9oGK_S7Ee2aar2UlpUZHmun77LJrzr6VR96he_ueGB3PMBN4cvJ6BVxThj8ap1AyZkliQ'),(579,'wx00f7d56d549f82ce',20160831163330,'component_verify_ticket','ticket@@@_Mn0IQUwS7s0v8TWf9oGK_S7Ee2aar2UlpUZHmun77LJrzr6VR96he_ueGB3PMBN4cvJ6BVxThj8ap1AyZkliQ'),(580,'wx00f7d56d549f82ce',20160831164329,'component_verify_ticket','ticket@@@_Mn0IQUwS7s0v8TWf9oGK_S7Ee2aar2UlpUZHmun77LJrzr6VR96he_ueGB3PMBN4cvJ6BVxThj8ap1AyZkliQ'),(581,'wx00f7d56d549f82ce',20160831165328,'component_verify_ticket','ticket@@@_Mn0IQUwS7s0v8TWf9oGK_S7Ee2aar2UlpUZHmun77LJrzr6VR96he_ueGB3PMBN4cvJ6BVxThj8ap1AyZkliQ'),(582,'wx00f7d56d549f82ce',20160906180300,'component_verify_ticket','ticket@@@Hj0cFgK3D6UG53CqK1cDyUl8ZRc90aeIDkNDo83f7wZGBivepKR6gKkSwE7wRSoT9HI6ubadQ4_Lb9Hrv-gnqg'),(583,'wx00f7d56d549f82ce',20160906181259,'component_verify_ticket','ticket@@@Hj0cFgK3D6UG53CqK1cDyUl8ZRc90aeIDkNDo83f7wZGBivepKR6gKkSwE7wRSoT9HI6ubadQ4_Lb9Hrv-gnqg'),(584,'wx00f7d56d549f82ce',20160906182256,'component_verify_ticket','ticket@@@Hj0cFgK3D6UG53CqK1cDyUl8ZRc90aeIDkNDo83f7wZGBivepKR6gKkSwE7wRSoT9HI6ubadQ4_Lb9Hrv-gnqg'),(585,'wx00f7d56d549f82ce',20160906183256,'component_verify_ticket','ticket@@@Hj0cFgK3D6UG53CqK1cDyUl8ZRc90aeIDkNDo83f7wZGBivepKR6gKkSwE7wRSoT9HI6ubadQ4_Lb9Hrv-gnqg'),(586,'wx00f7d56d549f82ce',20160906184256,'component_verify_ticket','ticket@@@Hj0cFgK3D6UG53CqK1cDyUl8ZRc90aeIDkNDo83f7wZGBivepKR6gKkSwE7wRSoT9HI6ubadQ4_Lb9Hrv-gnqg'),(587,'wx00f7d56d549f82ce',20160906185254,'component_verify_ticket','ticket@@@Hj0cFgK3D6UG53CqK1cDyUl8ZRc90aeIDkNDo83f7wZGBivepKR6gKkSwE7wRSoT9HI6ubadQ4_Lb9Hrv-gnqg'),(588,'wx00f7d56d549f82ce',20160906190257,'component_verify_ticket','ticket@@@MHkL0ScF57NuwkQzn1hanbPnUiGoC7mf5uVfOL7YoNGbX4P5nekywrOl-yrPeZ3Pp-NQW6T0LiSudaZ3Z4nBNg'),(589,'wx00f7d56d549f82ce',20160906191255,'component_verify_ticket','ticket@@@MHkL0ScF57NuwkQzn1hanbPnUiGoC7mf5uVfOL7YoNGbX4P5nekywrOl-yrPeZ3Pp-NQW6T0LiSudaZ3Z4nBNg'),(590,'wx00f7d56d549f82ce',20160906192254,'component_verify_ticket','ticket@@@MHkL0ScF57NuwkQzn1hanbPnUiGoC7mf5uVfOL7YoNGbX4P5nekywrOl-yrPeZ3Pp-NQW6T0LiSudaZ3Z4nBNg'),(591,'wx00f7d56d549f82ce',20160906193254,'component_verify_ticket','ticket@@@MHkL0ScF57NuwkQzn1hanbPnUiGoC7mf5uVfOL7YoNGbX4P5nekywrOl-yrPeZ3Pp-NQW6T0LiSudaZ3Z4nBNg'),(592,'wx00f7d56d549f82ce',20160906194253,'component_verify_ticket','ticket@@@MHkL0ScF57NuwkQzn1hanbPnUiGoC7mf5uVfOL7YoNGbX4P5nekywrOl-yrPeZ3Pp-NQW6T0LiSudaZ3Z4nBNg'),(593,'wx00f7d56d549f82ce',20160906195255,'component_verify_ticket','ticket@@@MHkL0ScF57NuwkQzn1hanbPnUiGoC7mf5uVfOL7YoNGbX4P5nekywrOl-yrPeZ3Pp-NQW6T0LiSudaZ3Z4nBNg'),(594,'wx00f7d56d549f82ce',20160906200257,'component_verify_ticket','ticket@@@D80NUHqCN7mICBOMREbKymZ4kNAbGdW-5BByZhlIYNWpthD4qPsDuFDt6eU9i5w6U6X5vPwHS49qVBjYmc4bfQ'),(595,'wx00f7d56d549f82ce',20160906201256,'component_verify_ticket','ticket@@@D80NUHqCN7mICBOMREbKymZ4kNAbGdW-5BByZhlIYNWpthD4qPsDuFDt6eU9i5w6U6X5vPwHS49qVBjYmc4bfQ'),(596,'wx00f7d56d549f82ce',20160906202258,'component_verify_ticket','ticket@@@D80NUHqCN7mICBOMREbKymZ4kNAbGdW-5BByZhlIYNWpthD4qPsDuFDt6eU9i5w6U6X5vPwHS49qVBjYmc4bfQ'),(597,'wx00f7d56d549f82ce',20160906203258,'component_verify_ticket','ticket@@@D80NUHqCN7mICBOMREbKymZ4kNAbGdW-5BByZhlIYNWpthD4qPsDuFDt6eU9i5w6U6X5vPwHS49qVBjYmc4bfQ'),(598,'wx00f7d56d549f82ce',20160906204255,'component_verify_ticket','ticket@@@D80NUHqCN7mICBOMREbKymZ4kNAbGdW-5BByZhlIYNWpthD4qPsDuFDt6eU9i5w6U6X5vPwHS49qVBjYmc4bfQ'),(599,'wx00f7d56d549f82ce',20160906205256,'component_verify_ticket','ticket@@@D80NUHqCN7mICBOMREbKymZ4kNAbGdW-5BByZhlIYNWpthD4qPsDuFDt6eU9i5w6U6X5vPwHS49qVBjYmc4bfQ'),(600,'wx00f7d56d549f82ce',20160906210257,'component_verify_ticket','ticket@@@U1kizhhecGXmzxsHmMBPQU9ud3V80zjR6B0JnmDj3DIjXgolnZBft-o4X7k_6RF3Nquv4EXddu2M2I_M_G1WXQ'),(601,'wx00f7d56d549f82ce',20160906211255,'component_verify_ticket','ticket@@@U1kizhhecGXmzxsHmMBPQU9ud3V80zjR6B0JnmDj3DIjXgolnZBft-o4X7k_6RF3Nquv4EXddu2M2I_M_G1WXQ'),(602,'wx00f7d56d549f82ce',20160906212257,'component_verify_ticket','ticket@@@U1kizhhecGXmzxsHmMBPQU9ud3V80zjR6B0JnmDj3DIjXgolnZBft-o4X7k_6RF3Nquv4EXddu2M2I_M_G1WXQ'),(603,'wx00f7d56d549f82ce',20160906213257,'component_verify_ticket','ticket@@@U1kizhhecGXmzxsHmMBPQU9ud3V80zjR6B0JnmDj3DIjXgolnZBft-o4X7k_6RF3Nquv4EXddu2M2I_M_G1WXQ'),(604,'wx00f7d56d549f82ce',20160906214258,'component_verify_ticket','ticket@@@U1kizhhecGXmzxsHmMBPQU9ud3V80zjR6B0JnmDj3DIjXgolnZBft-o4X7k_6RF3Nquv4EXddu2M2I_M_G1WXQ'),(605,'wx00f7d56d549f82ce',20160906215256,'component_verify_ticket','ticket@@@U1kizhhecGXmzxsHmMBPQU9ud3V80zjR6B0JnmDj3DIjXgolnZBft-o4X7k_6RF3Nquv4EXddu2M2I_M_G1WXQ'),(606,'wx00f7d56d549f82ce',20160906220301,'component_verify_ticket','ticket@@@IJoSG2-PXzHGx_QclpwK_TrnvWpy9Gt5LEdyUGwJ6js-eWnc0u_bz-hYHXmIsxB9HrrCNz94cuRGJ7J0P4DlRQ'),(607,'wx00f7d56d549f82ce',20160906221257,'component_verify_ticket','ticket@@@IJoSG2-PXzHGx_QclpwK_TrnvWpy9Gt5LEdyUGwJ6js-eWnc0u_bz-hYHXmIsxB9HrrCNz94cuRGJ7J0P4DlRQ'),(608,'wx00f7d56d549f82ce',20160906222255,'component_verify_ticket','ticket@@@IJoSG2-PXzHGx_QclpwK_TrnvWpy9Gt5LEdyUGwJ6js-eWnc0u_bz-hYHXmIsxB9HrrCNz94cuRGJ7J0P4DlRQ'),(609,'wx00f7d56d549f82ce',20160906223252,'component_verify_ticket','ticket@@@IJoSG2-PXzHGx_QclpwK_TrnvWpy9Gt5LEdyUGwJ6js-eWnc0u_bz-hYHXmIsxB9HrrCNz94cuRGJ7J0P4DlRQ'),(610,'wx00f7d56d549f82ce',20160906224253,'component_verify_ticket','ticket@@@IJoSG2-PXzHGx_QclpwK_TrnvWpy9Gt5LEdyUGwJ6js-eWnc0u_bz-hYHXmIsxB9HrrCNz94cuRGJ7J0P4DlRQ'),(611,'wx00f7d56d549f82ce',20160906225251,'component_verify_ticket','ticket@@@IJoSG2-PXzHGx_QclpwK_TrnvWpy9Gt5LEdyUGwJ6js-eWnc0u_bz-hYHXmIsxB9HrrCNz94cuRGJ7J0P4DlRQ'),(612,'wx00f7d56d549f82ce',20160906230253,'component_verify_ticket','ticket@@@ko3Xx_0dGZNiVj9KlaFFVybn3phi1A9jqXndKKSxdkHw5-iSXstMmXKIfbIb5xx8XMpfhzHhad2F9ebqC3HzgA'),(613,'wx00f7d56d549f82ce',20160906231256,'component_verify_ticket','ticket@@@ko3Xx_0dGZNiVj9KlaFFVybn3phi1A9jqXndKKSxdkHw5-iSXstMmXKIfbIb5xx8XMpfhzHhad2F9ebqC3HzgA'),(614,'wx00f7d56d549f82ce',20160906232256,'component_verify_ticket','ticket@@@ko3Xx_0dGZNiVj9KlaFFVybn3phi1A9jqXndKKSxdkHw5-iSXstMmXKIfbIb5xx8XMpfhzHhad2F9ebqC3HzgA'),(615,'wx00f7d56d549f82ce',20160906233255,'component_verify_ticket','ticket@@@ko3Xx_0dGZNiVj9KlaFFVybn3phi1A9jqXndKKSxdkHw5-iSXstMmXKIfbIb5xx8XMpfhzHhad2F9ebqC3HzgA'),(616,'wx00f7d56d549f82ce',20160906234253,'component_verify_ticket','ticket@@@ko3Xx_0dGZNiVj9KlaFFVybn3phi1A9jqXndKKSxdkHw5-iSXstMmXKIfbIb5xx8XMpfhzHhad2F9ebqC3HzgA'),(617,'wx00f7d56d549f82ce',20160906235252,'component_verify_ticket','ticket@@@ko3Xx_0dGZNiVj9KlaFFVybn3phi1A9jqXndKKSxdkHw5-iSXstMmXKIfbIb5xx8XMpfhzHhad2F9ebqC3HzgA'),(618,'wx00f7d56d549f82ce',20160907000255,'component_verify_ticket','ticket@@@JVgH4XB_qt0AzWwo_di1v_YwqdDfaP4ZZAJ_bLhABb4YCzLD7hhI0lUC_s7ef-6rqJ04UFD9U0TmyZn0ZDOUTg'),(619,'wx00f7d56d549f82ce',20160907001253,'component_verify_ticket','ticket@@@JVgH4XB_qt0AzWwo_di1v_YwqdDfaP4ZZAJ_bLhABb4YCzLD7hhI0lUC_s7ef-6rqJ04UFD9U0TmyZn0ZDOUTg'),(620,'wx00f7d56d549f82ce',20160907002253,'component_verify_ticket','ticket@@@JVgH4XB_qt0AzWwo_di1v_YwqdDfaP4ZZAJ_bLhABb4YCzLD7hhI0lUC_s7ef-6rqJ04UFD9U0TmyZn0ZDOUTg'),(621,'wx00f7d56d549f82ce',20160907003249,'component_verify_ticket','ticket@@@JVgH4XB_qt0AzWwo_di1v_YwqdDfaP4ZZAJ_bLhABb4YCzLD7hhI0lUC_s7ef-6rqJ04UFD9U0TmyZn0ZDOUTg'),(622,'wx00f7d56d549f82ce',20160907004247,'component_verify_ticket','ticket@@@JVgH4XB_qt0AzWwo_di1v_YwqdDfaP4ZZAJ_bLhABb4YCzLD7hhI0lUC_s7ef-6rqJ04UFD9U0TmyZn0ZDOUTg'),(623,'wx00f7d56d549f82ce',20160907005247,'component_verify_ticket','ticket@@@JVgH4XB_qt0AzWwo_di1v_YwqdDfaP4ZZAJ_bLhABb4YCzLD7hhI0lUC_s7ef-6rqJ04UFD9U0TmyZn0ZDOUTg'),(624,'wx00f7d56d549f82ce',20160907010246,'component_verify_ticket','ticket@@@aSRRjyuDjTl3JPbSIry-uUUj9M9KYP7LAnYV0P_0aQDUAKBHxU0bTghzUu4_WQFr5OAPV69g3fjRtOJdzCA83A'),(625,'wx00f7d56d549f82ce',20160907011249,'component_verify_ticket','ticket@@@aSRRjyuDjTl3JPbSIry-uUUj9M9KYP7LAnYV0P_0aQDUAKBHxU0bTghzUu4_WQFr5OAPV69g3fjRtOJdzCA83A'),(626,'wx00f7d56d549f82ce',20160907012249,'component_verify_ticket','ticket@@@aSRRjyuDjTl3JPbSIry-uUUj9M9KYP7LAnYV0P_0aQDUAKBHxU0bTghzUu4_WQFr5OAPV69g3fjRtOJdzCA83A'),(627,'wx00f7d56d549f82ce',20160907013250,'component_verify_ticket','ticket@@@aSRRjyuDjTl3JPbSIry-uUUj9M9KYP7LAnYV0P_0aQDUAKBHxU0bTghzUu4_WQFr5OAPV69g3fjRtOJdzCA83A'),(628,'wx00f7d56d549f82ce',20160907014245,'component_verify_ticket','ticket@@@aSRRjyuDjTl3JPbSIry-uUUj9M9KYP7LAnYV0P_0aQDUAKBHxU0bTghzUu4_WQFr5OAPV69g3fjRtOJdzCA83A'),(629,'wx00f7d56d549f82ce',20160907015248,'component_verify_ticket','ticket@@@aSRRjyuDjTl3JPbSIry-uUUj9M9KYP7LAnYV0P_0aQDUAKBHxU0bTghzUu4_WQFr5OAPV69g3fjRtOJdzCA83A'),(630,'wx00f7d56d549f82ce',20160907020251,'component_verify_ticket','ticket@@@R07si2VgqDNF37VYAP9gIwC9LR_tWcOisZpwNK-TU66JMdCg_DEklQ-xxU7kOVjYQv1ECsjs5j7hDGvbspFLZA'),(631,'wx00f7d56d549f82ce',20160907021249,'component_verify_ticket','ticket@@@R07si2VgqDNF37VYAP9gIwC9LR_tWcOisZpwNK-TU66JMdCg_DEklQ-xxU7kOVjYQv1ECsjs5j7hDGvbspFLZA'),(632,'wx00f7d56d549f82ce',20160907022252,'component_verify_ticket','ticket@@@R07si2VgqDNF37VYAP9gIwC9LR_tWcOisZpwNK-TU66JMdCg_DEklQ-xxU7kOVjYQv1ECsjs5j7hDGvbspFLZA'),(633,'wx00f7d56d549f82ce',20160907023248,'component_verify_ticket','ticket@@@R07si2VgqDNF37VYAP9gIwC9LR_tWcOisZpwNK-TU66JMdCg_DEklQ-xxU7kOVjYQv1ECsjs5j7hDGvbspFLZA'),(634,'wx00f7d56d549f82ce',20160907024244,'component_verify_ticket','ticket@@@R07si2VgqDNF37VYAP9gIwC9LR_tWcOisZpwNK-TU66JMdCg_DEklQ-xxU7kOVjYQv1ECsjs5j7hDGvbspFLZA'),(635,'wx00f7d56d549f82ce',20160907025250,'component_verify_ticket','ticket@@@R07si2VgqDNF37VYAP9gIwC9LR_tWcOisZpwNK-TU66JMdCg_DEklQ-xxU7kOVjYQv1ECsjs5j7hDGvbspFLZA'),(636,'wx00f7d56d549f82ce',20160907030246,'component_verify_ticket','ticket@@@iuPUm1erzjzkT4Eg3RYmO9io0-0nMF5QMOPMZ3GUbCk0afRzZ8Ly40ZbYRPdSLofRt0J_tHYlxhK8vGWRXbZGg'),(637,'wx00f7d56d549f82ce',20160907031250,'component_verify_ticket','ticket@@@iuPUm1erzjzkT4Eg3RYmO9io0-0nMF5QMOPMZ3GUbCk0afRzZ8Ly40ZbYRPdSLofRt0J_tHYlxhK8vGWRXbZGg'),(638,'wx00f7d56d549f82ce',20160907032254,'component_verify_ticket','ticket@@@iuPUm1erzjzkT4Eg3RYmO9io0-0nMF5QMOPMZ3GUbCk0afRzZ8Ly40ZbYRPdSLofRt0J_tHYlxhK8vGWRXbZGg'),(639,'wx00f7d56d549f82ce',20160907033252,'component_verify_ticket','ticket@@@iuPUm1erzjzkT4Eg3RYmO9io0-0nMF5QMOPMZ3GUbCk0afRzZ8Ly40ZbYRPdSLofRt0J_tHYlxhK8vGWRXbZGg'),(640,'wx00f7d56d549f82ce',20160907034250,'component_verify_ticket','ticket@@@iuPUm1erzjzkT4Eg3RYmO9io0-0nMF5QMOPMZ3GUbCk0afRzZ8Ly40ZbYRPdSLofRt0J_tHYlxhK8vGWRXbZGg'),(641,'wx00f7d56d549f82ce',20160907035251,'component_verify_ticket','ticket@@@iuPUm1erzjzkT4Eg3RYmO9io0-0nMF5QMOPMZ3GUbCk0afRzZ8Ly40ZbYRPdSLofRt0J_tHYlxhK8vGWRXbZGg'),(642,'wx00f7d56d549f82ce',20160907040256,'component_verify_ticket','ticket@@@ATUGTcjpVy7cyO8XunbN-9TuQRariXp7Ny-l3YVQD6FzkrbplamYYknp5YCitKPLeAwlUolQqSqWVTmlUICorQ'),(643,'wx00f7d56d549f82ce',20160907041253,'component_verify_ticket','ticket@@@ATUGTcjpVy7cyO8XunbN-9TuQRariXp7Ny-l3YVQD6FzkrbplamYYknp5YCitKPLeAwlUolQqSqWVTmlUICorQ'),(644,'wx00f7d56d549f82ce',20160907042251,'component_verify_ticket','ticket@@@ATUGTcjpVy7cyO8XunbN-9TuQRariXp7Ny-l3YVQD6FzkrbplamYYknp5YCitKPLeAwlUolQqSqWVTmlUICorQ'),(645,'wx00f7d56d549f82ce',20160907043250,'component_verify_ticket','ticket@@@ATUGTcjpVy7cyO8XunbN-9TuQRariXp7Ny-l3YVQD6FzkrbplamYYknp5YCitKPLeAwlUolQqSqWVTmlUICorQ'),(646,'wx00f7d56d549f82ce',20160907044250,'component_verify_ticket','ticket@@@ATUGTcjpVy7cyO8XunbN-9TuQRariXp7Ny-l3YVQD6FzkrbplamYYknp5YCitKPLeAwlUolQqSqWVTmlUICorQ'),(647,'wx00f7d56d549f82ce',20160907045255,'component_verify_ticket','ticket@@@ATUGTcjpVy7cyO8XunbN-9TuQRariXp7Ny-l3YVQD6FzkrbplamYYknp5YCitKPLeAwlUolQqSqWVTmlUICorQ'),(648,'wx00f7d56d549f82ce',20160907050243,'component_verify_ticket','ticket@@@oWUSBHpQU-jDlvjF8m6dmj_Uul5MpLlmie1vr7BGkYgkZsGiwbZOKKXLUYhJMV8OuVQ2V9kb6b16ENEj80Lp_g'),(649,'wx00f7d56d549f82ce',20160907051248,'component_verify_ticket','ticket@@@oWUSBHpQU-jDlvjF8m6dmj_Uul5MpLlmie1vr7BGkYgkZsGiwbZOKKXLUYhJMV8OuVQ2V9kb6b16ENEj80Lp_g'),(650,'wx00f7d56d549f82ce',20160907052243,'component_verify_ticket','ticket@@@oWUSBHpQU-jDlvjF8m6dmj_Uul5MpLlmie1vr7BGkYgkZsGiwbZOKKXLUYhJMV8OuVQ2V9kb6b16ENEj80Lp_g'),(651,'wx00f7d56d549f82ce',20160907053242,'component_verify_ticket','ticket@@@oWUSBHpQU-jDlvjF8m6dmj_Uul5MpLlmie1vr7BGkYgkZsGiwbZOKKXLUYhJMV8OuVQ2V9kb6b16ENEj80Lp_g'),(652,'wx00f7d56d549f82ce',20160907054245,'component_verify_ticket','ticket@@@oWUSBHpQU-jDlvjF8m6dmj_Uul5MpLlmie1vr7BGkYgkZsGiwbZOKKXLUYhJMV8OuVQ2V9kb6b16ENEj80Lp_g'),(653,'wx00f7d56d549f82ce',20160907055234,'component_verify_ticket','ticket@@@oWUSBHpQU-jDlvjF8m6dmj_Uul5MpLlmie1vr7BGkYgkZsGiwbZOKKXLUYhJMV8OuVQ2V9kb6b16ENEj80Lp_g'),(654,'wx00f7d56d549f82ce',20160907060246,'component_verify_ticket','ticket@@@fUtDcwUFO99qF7UjFMKmCqRmBa8v5GJ0z_LInb5PUxQBkgnb3F3BbaMslvDYPjYfzgxgFID7OHnx3Sgb2DLKGA'),(655,'wx00f7d56d549f82ce',20160907061249,'component_verify_ticket','ticket@@@fUtDcwUFO99qF7UjFMKmCqRmBa8v5GJ0z_LInb5PUxQBkgnb3F3BbaMslvDYPjYfzgxgFID7OHnx3Sgb2DLKGA'),(656,'wx00f7d56d549f82ce',20160907062250,'component_verify_ticket','ticket@@@fUtDcwUFO99qF7UjFMKmCqRmBa8v5GJ0z_LInb5PUxQBkgnb3F3BbaMslvDYPjYfzgxgFID7OHnx3Sgb2DLKGA'),(657,'wx00f7d56d549f82ce',20160907063249,'component_verify_ticket','ticket@@@fUtDcwUFO99qF7UjFMKmCqRmBa8v5GJ0z_LInb5PUxQBkgnb3F3BbaMslvDYPjYfzgxgFID7OHnx3Sgb2DLKGA'),(658,'wx00f7d56d549f82ce',20160907064240,'component_verify_ticket','ticket@@@fUtDcwUFO99qF7UjFMKmCqRmBa8v5GJ0z_LInb5PUxQBkgnb3F3BbaMslvDYPjYfzgxgFID7OHnx3Sgb2DLKGA'),(659,'wx00f7d56d549f82ce',20160907065242,'component_verify_ticket','ticket@@@fUtDcwUFO99qF7UjFMKmCqRmBa8v5GJ0z_LInb5PUxQBkgnb3F3BbaMslvDYPjYfzgxgFID7OHnx3Sgb2DLKGA'),(660,'wx00f7d56d549f82ce',20160907070251,'component_verify_ticket','ticket@@@2ylfudOOpZuZMXCIOG1ceks9kIijXi9d2P6_Ykd9FHikzjna_FdsOuCpTmla9PmEI4iK6oPFPJFjuE-pt-_xOQ'),(661,'wx00f7d56d549f82ce',20160907071245,'component_verify_ticket','ticket@@@2ylfudOOpZuZMXCIOG1ceks9kIijXi9d2P6_Ykd9FHikzjna_FdsOuCpTmla9PmEI4iK6oPFPJFjuE-pt-_xOQ'),(662,'wx00f7d56d549f82ce',20160907072244,'component_verify_ticket','ticket@@@2ylfudOOpZuZMXCIOG1ceks9kIijXi9d2P6_Ykd9FHikzjna_FdsOuCpTmla9PmEI4iK6oPFPJFjuE-pt-_xOQ'),(663,'wx00f7d56d549f82ce',20160907073247,'component_verify_ticket','ticket@@@2ylfudOOpZuZMXCIOG1ceks9kIijXi9d2P6_Ykd9FHikzjna_FdsOuCpTmla9PmEI4iK6oPFPJFjuE-pt-_xOQ'),(664,'wx00f7d56d549f82ce',20160907074231,'component_verify_ticket','ticket@@@2ylfudOOpZuZMXCIOG1ceks9kIijXi9d2P6_Ykd9FHikzjna_FdsOuCpTmla9PmEI4iK6oPFPJFjuE-pt-_xOQ'),(665,'wx00f7d56d549f82ce',20160907075248,'component_verify_ticket','ticket@@@2ylfudOOpZuZMXCIOG1ceks9kIijXi9d2P6_Ykd9FHikzjna_FdsOuCpTmla9PmEI4iK6oPFPJFjuE-pt-_xOQ'),(666,'wx00f7d56d549f82ce',20160907080307,'component_verify_ticket','ticket@@@uQ8QuTXPw96oTIH84Yn-VPfBhg5hb0lAGoPwRZiuV4RpM6P8IreSu59XoQ1z1RjUuvvWiA4IlJx1UWyWF2InmA'),(667,'wx00f7d56d549f82ce',20160907081251,'component_verify_ticket','ticket@@@uQ8QuTXPw96oTIH84Yn-VPfBhg5hb0lAGoPwRZiuV4RpM6P8IreSu59XoQ1z1RjUuvvWiA4IlJx1UWyWF2InmA'),(668,'wx00f7d56d549f82ce',20160907082253,'component_verify_ticket','ticket@@@uQ8QuTXPw96oTIH84Yn-VPfBhg5hb0lAGoPwRZiuV4RpM6P8IreSu59XoQ1z1RjUuvvWiA4IlJx1UWyWF2InmA'),(669,'wx00f7d56d549f82ce',20160907083235,'component_verify_ticket','ticket@@@uQ8QuTXPw96oTIH84Yn-VPfBhg5hb0lAGoPwRZiuV4RpM6P8IreSu59XoQ1z1RjUuvvWiA4IlJx1UWyWF2InmA'),(670,'wx00f7d56d549f82ce',20160907084252,'component_verify_ticket','ticket@@@uQ8QuTXPw96oTIH84Yn-VPfBhg5hb0lAGoPwRZiuV4RpM6P8IreSu59XoQ1z1RjUuvvWiA4IlJx1UWyWF2InmA'),(671,'wx00f7d56d549f82ce',20160907085251,'component_verify_ticket','ticket@@@uQ8QuTXPw96oTIH84Yn-VPfBhg5hb0lAGoPwRZiuV4RpM6P8IreSu59XoQ1z1RjUuvvWiA4IlJx1UWyWF2InmA'),(672,'wx00f7d56d549f82ce',20160907090256,'component_verify_ticket','ticket@@@tp5or_4yQnjg_PFKFPy3vp-B5jTaYDCwR31vjiGXMFrPjSh5yXZveV8XAHCqnUmz8O5bG-nONOX6BJr1yhIUSA'),(673,'wx00f7d56d549f82ce',20160907091253,'component_verify_ticket','ticket@@@tp5or_4yQnjg_PFKFPy3vp-B5jTaYDCwR31vjiGXMFrPjSh5yXZveV8XAHCqnUmz8O5bG-nONOX6BJr1yhIUSA'),(674,'wx00f7d56d549f82ce',20160907092253,'component_verify_ticket','ticket@@@tp5or_4yQnjg_PFKFPy3vp-B5jTaYDCwR31vjiGXMFrPjSh5yXZveV8XAHCqnUmz8O5bG-nONOX6BJr1yhIUSA'),(675,'wx00f7d56d549f82ce',20160907093254,'component_verify_ticket','ticket@@@tp5or_4yQnjg_PFKFPy3vp-B5jTaYDCwR31vjiGXMFrPjSh5yXZveV8XAHCqnUmz8O5bG-nONOX6BJr1yhIUSA'),(676,'wx00f7d56d549f82ce',20160907094254,'component_verify_ticket','ticket@@@tp5or_4yQnjg_PFKFPy3vp-B5jTaYDCwR31vjiGXMFrPjSh5yXZveV8XAHCqnUmz8O5bG-nONOX6BJr1yhIUSA'),(677,'wx00f7d56d549f82ce',20160907095256,'component_verify_ticket','ticket@@@tp5or_4yQnjg_PFKFPy3vp-B5jTaYDCwR31vjiGXMFrPjSh5yXZveV8XAHCqnUmz8O5bG-nONOX6BJr1yhIUSA'),(678,'wx00f7d56d549f82ce',20160907100310,'component_verify_ticket','ticket@@@x9fd_x-_5Jleqm-jHFsZbRJURNv5ht6s7Jx3udgRQS0pIYeDzfYGfikBEKz9589DOnJQGINWuA31BZpxlnJFDA'),(679,'wx00f7d56d549f82ce',20160907101259,'component_verify_ticket','ticket@@@x9fd_x-_5Jleqm-jHFsZbRJURNv5ht6s7Jx3udgRQS0pIYeDzfYGfikBEKz9589DOnJQGINWuA31BZpxlnJFDA'),(680,'wx00f7d56d549f82ce',20160907102257,'component_verify_ticket','ticket@@@x9fd_x-_5Jleqm-jHFsZbRJURNv5ht6s7Jx3udgRQS0pIYeDzfYGfikBEKz9589DOnJQGINWuA31BZpxlnJFDA'),(681,'wx00f7d56d549f82ce',20160907103255,'component_verify_ticket','ticket@@@x9fd_x-_5Jleqm-jHFsZbRJURNv5ht6s7Jx3udgRQS0pIYeDzfYGfikBEKz9589DOnJQGINWuA31BZpxlnJFDA'),(682,'wx00f7d56d549f82ce',20160907104256,'component_verify_ticket','ticket@@@x9fd_x-_5Jleqm-jHFsZbRJURNv5ht6s7Jx3udgRQS0pIYeDzfYGfikBEKz9589DOnJQGINWuA31BZpxlnJFDA'),(683,'wx00f7d56d549f82ce',20160907105254,'component_verify_ticket','ticket@@@x9fd_x-_5Jleqm-jHFsZbRJURNv5ht6s7Jx3udgRQS0pIYeDzfYGfikBEKz9589DOnJQGINWuA31BZpxlnJFDA'),(684,'wx00f7d56d549f82ce',20160907110256,'component_verify_ticket','ticket@@@mHDVQiu_ERVtCPpkHVK-NwC9eRvc1RIJx2npiOJGzBTFymmRDabGdyiVcqjtXP7tJtQfFvyIsFPclbeoO8547g'),(685,'wx00f7d56d549f82ce',20160907111257,'component_verify_ticket','ticket@@@mHDVQiu_ERVtCPpkHVK-NwC9eRvc1RIJx2npiOJGzBTFymmRDabGdyiVcqjtXP7tJtQfFvyIsFPclbeoO8547g'),(686,'wx00f7d56d549f82ce',20160907112301,'component_verify_ticket','ticket@@@mHDVQiu_ERVtCPpkHVK-NwC9eRvc1RIJx2npiOJGzBTFymmRDabGdyiVcqjtXP7tJtQfFvyIsFPclbeoO8547g'),(687,'wx00f7d56d549f82ce',20160907113258,'component_verify_ticket','ticket@@@mHDVQiu_ERVtCPpkHVK-NwC9eRvc1RIJx2npiOJGzBTFymmRDabGdyiVcqjtXP7tJtQfFvyIsFPclbeoO8547g'),(688,'wx00f7d56d549f82ce',20160907114254,'component_verify_ticket','ticket@@@mHDVQiu_ERVtCPpkHVK-NwC9eRvc1RIJx2npiOJGzBTFymmRDabGdyiVcqjtXP7tJtQfFvyIsFPclbeoO8547g'),(689,'wx00f7d56d549f82ce',20160907115257,'component_verify_ticket','ticket@@@mHDVQiu_ERVtCPpkHVK-NwC9eRvc1RIJx2npiOJGzBTFymmRDabGdyiVcqjtXP7tJtQfFvyIsFPclbeoO8547g'),(690,'wx00f7d56d549f82ce',20160907120258,'component_verify_ticket','ticket@@@jipfAdmy-pcrXX0mDeDlUK7iveReh01j-MwztAEOQjcOekmeRaR8lTrgr62B0CLyCvEIoG97RV605X878aNYcg'),(691,'wx00f7d56d549f82ce',20160907121256,'component_verify_ticket','ticket@@@jipfAdmy-pcrXX0mDeDlUK7iveReh01j-MwztAEOQjcOekmeRaR8lTrgr62B0CLyCvEIoG97RV605X878aNYcg'),(692,'wx00f7d56d549f82ce',20160907122258,'component_verify_ticket','ticket@@@jipfAdmy-pcrXX0mDeDlUK7iveReh01j-MwztAEOQjcOekmeRaR8lTrgr62B0CLyCvEIoG97RV605X878aNYcg'),(693,'wx00f7d56d549f82ce',20160907123256,'component_verify_ticket','ticket@@@jipfAdmy-pcrXX0mDeDlUK7iveReh01j-MwztAEOQjcOekmeRaR8lTrgr62B0CLyCvEIoG97RV605X878aNYcg'),(694,'wx00f7d56d549f82ce',20160907124258,'component_verify_ticket','ticket@@@jipfAdmy-pcrXX0mDeDlUK7iveReh01j-MwztAEOQjcOekmeRaR8lTrgr62B0CLyCvEIoG97RV605X878aNYcg'),(695,'wx00f7d56d549f82ce',20160907125256,'component_verify_ticket','ticket@@@jipfAdmy-pcrXX0mDeDlUK7iveReh01j-MwztAEOQjcOekmeRaR8lTrgr62B0CLyCvEIoG97RV605X878aNYcg'),(696,'wx00f7d56d549f82ce',20160907130258,'component_verify_ticket','ticket@@@wOtFj1WuP5y_fRsRjRo4Qse6zA0zujc7zvKARFGEpXZzrhxv_GX4bcR6ZWnmj8ZjkJfbCDStF4INr-tWtkAJNQ'),(697,'wx00f7d56d549f82ce',20160907131256,'component_verify_ticket','ticket@@@wOtFj1WuP5y_fRsRjRo4Qse6zA0zujc7zvKARFGEpXZzrhxv_GX4bcR6ZWnmj8ZjkJfbCDStF4INr-tWtkAJNQ'),(698,'wx00f7d56d549f82ce',20160907132256,'component_verify_ticket','ticket@@@wOtFj1WuP5y_fRsRjRo4Qse6zA0zujc7zvKARFGEpXZzrhxv_GX4bcR6ZWnmj8ZjkJfbCDStF4INr-tWtkAJNQ'),(699,'wx00f7d56d549f82ce',20160907133255,'component_verify_ticket','ticket@@@wOtFj1WuP5y_fRsRjRo4Qse6zA0zujc7zvKARFGEpXZzrhxv_GX4bcR6ZWnmj8ZjkJfbCDStF4INr-tWtkAJNQ'),(700,'wx00f7d56d549f82ce',20160907134252,'component_verify_ticket','ticket@@@wOtFj1WuP5y_fRsRjRo4Qse6zA0zujc7zvKARFGEpXZzrhxv_GX4bcR6ZWnmj8ZjkJfbCDStF4INr-tWtkAJNQ'),(701,'wx00f7d56d549f82ce',20160907135253,'component_verify_ticket','ticket@@@wOtFj1WuP5y_fRsRjRo4Qse6zA0zujc7zvKARFGEpXZzrhxv_GX4bcR6ZWnmj8ZjkJfbCDStF4INr-tWtkAJNQ'),(702,'wx00f7d56d549f82ce',20160907140257,'component_verify_ticket','ticket@@@cNXAnEkB4POteOH2nehHC4sIFTmACE8GMuAAxO19uvEa2Z_sz16HhzyPlGbXpsuqsmDbJwEI7H_-HZpXuoFDTw'),(703,'wx00f7d56d549f82ce',20160907141254,'component_verify_ticket','ticket@@@cNXAnEkB4POteOH2nehHC4sIFTmACE8GMuAAxO19uvEa2Z_sz16HhzyPlGbXpsuqsmDbJwEI7H_-HZpXuoFDTw'),(704,'wx00f7d56d549f82ce',20160907142256,'component_verify_ticket','ticket@@@cNXAnEkB4POteOH2nehHC4sIFTmACE8GMuAAxO19uvEa2Z_sz16HhzyPlGbXpsuqsmDbJwEI7H_-HZpXuoFDTw'),(705,'wx00f7d56d549f82ce',20160907143250,'component_verify_ticket','ticket@@@cNXAnEkB4POteOH2nehHC4sIFTmACE8GMuAAxO19uvEa2Z_sz16HhzyPlGbXpsuqsmDbJwEI7H_-HZpXuoFDTw'),(706,'wx00f7d56d549f82ce',20160907144255,'component_verify_ticket','ticket@@@cNXAnEkB4POteOH2nehHC4sIFTmACE8GMuAAxO19uvEa2Z_sz16HhzyPlGbXpsuqsmDbJwEI7H_-HZpXuoFDTw'),(707,'wx00f7d56d549f82ce',20160907145255,'component_verify_ticket','ticket@@@cNXAnEkB4POteOH2nehHC4sIFTmACE8GMuAAxO19uvEa2Z_sz16HhzyPlGbXpsuqsmDbJwEI7H_-HZpXuoFDTw'),(708,'wx00f7d56d549f82ce',20160907150259,'component_verify_ticket','ticket@@@Dr8b08WH-5iwSzcnbVruRcHBdTBzuSefehQTwxTzbDtpkGFvCiwG6IiSF5AXPIISF9zUR0yxS15pC61_NteDTQ'),(709,'wx00f7d56d549f82ce',20160907151258,'component_verify_ticket','ticket@@@Dr8b08WH-5iwSzcnbVruRcHBdTBzuSefehQTwxTzbDtpkGFvCiwG6IiSF5AXPIISF9zUR0yxS15pC61_NteDTQ'),(710,'wx00f7d56d549f82ce',20160907152256,'component_verify_ticket','ticket@@@Dr8b08WH-5iwSzcnbVruRcHBdTBzuSefehQTwxTzbDtpkGFvCiwG6IiSF5AXPIISF9zUR0yxS15pC61_NteDTQ'),(711,'wx00f7d56d549f82ce',20160907153254,'component_verify_ticket','ticket@@@Dr8b08WH-5iwSzcnbVruRcHBdTBzuSefehQTwxTzbDtpkGFvCiwG6IiSF5AXPIISF9zUR0yxS15pC61_NteDTQ'),(712,'wx00f7d56d549f82ce',20160907154254,'component_verify_ticket','ticket@@@Dr8b08WH-5iwSzcnbVruRcHBdTBzuSefehQTwxTzbDtpkGFvCiwG6IiSF5AXPIISF9zUR0yxS15pC61_NteDTQ'),(713,'wx00f7d56d549f82ce',20160907155255,'component_verify_ticket','ticket@@@Dr8b08WH-5iwSzcnbVruRcHBdTBzuSefehQTwxTzbDtpkGFvCiwG6IiSF5AXPIISF9zUR0yxS15pC61_NteDTQ'),(714,'wx00f7d56d549f82ce',20160907160300,'component_verify_ticket','ticket@@@dbi0WNpHW0yOmGyKoDQnqDaPQOmg2-j4Lf5XC-Cux8VBJs_rJqMyVf9XI71NBScGcsR9j36qsUwZ_d0awpO53Q'),(715,'wx00f7d56d549f82ce',20160907161258,'component_verify_ticket','ticket@@@dbi0WNpHW0yOmGyKoDQnqDaPQOmg2-j4Lf5XC-Cux8VBJs_rJqMyVf9XI71NBScGcsR9j36qsUwZ_d0awpO53Q'),(716,'wx00f7d56d549f82ce',20160907162257,'component_verify_ticket','ticket@@@dbi0WNpHW0yOmGyKoDQnqDaPQOmg2-j4Lf5XC-Cux8VBJs_rJqMyVf9XI71NBScGcsR9j36qsUwZ_d0awpO53Q'),(717,'wx00f7d56d549f82ce',20160907163255,'component_verify_ticket','ticket@@@dbi0WNpHW0yOmGyKoDQnqDaPQOmg2-j4Lf5XC-Cux8VBJs_rJqMyVf9XI71NBScGcsR9j36qsUwZ_d0awpO53Q'),(718,'wx00f7d56d549f82ce',20160907164256,'component_verify_ticket','ticket@@@dbi0WNpHW0yOmGyKoDQnqDaPQOmg2-j4Lf5XC-Cux8VBJs_rJqMyVf9XI71NBScGcsR9j36qsUwZ_d0awpO53Q'),(719,'wx00f7d56d549f82ce',20160907165256,'component_verify_ticket','ticket@@@dbi0WNpHW0yOmGyKoDQnqDaPQOmg2-j4Lf5XC-Cux8VBJs_rJqMyVf9XI71NBScGcsR9j36qsUwZ_d0awpO53Q'),(720,'wx00f7d56d549f82ce',20160907170258,'component_verify_ticket','ticket@@@guGIlhRKo_xDokntny27pVmg2z7kdfQwy8AavX_A72Ak2VvxNasWfL-d8pBT6O65FlO1mjZRDM1-xrJLwbJRuA'),(721,'wx00f7d56d549f82ce',20160907171257,'component_verify_ticket','ticket@@@guGIlhRKo_xDokntny27pVmg2z7kdfQwy8AavX_A72Ak2VvxNasWfL-d8pBT6O65FlO1mjZRDM1-xrJLwbJRuA'),(722,'wx00f7d56d549f82ce',20160907172300,'component_verify_ticket','ticket@@@guGIlhRKo_xDokntny27pVmg2z7kdfQwy8AavX_A72Ak2VvxNasWfL-d8pBT6O65FlO1mjZRDM1-xrJLwbJRuA'),(723,'wx00f7d56d549f82ce',20160907173258,'component_verify_ticket','ticket@@@guGIlhRKo_xDokntny27pVmg2z7kdfQwy8AavX_A72Ak2VvxNasWfL-d8pBT6O65FlO1mjZRDM1-xrJLwbJRuA'),(724,'wx00f7d56d549f82ce',20160907174257,'component_verify_ticket','ticket@@@guGIlhRKo_xDokntny27pVmg2z7kdfQwy8AavX_A72Ak2VvxNasWfL-d8pBT6O65FlO1mjZRDM1-xrJLwbJRuA'),(725,'wx00f7d56d549f82ce',20160907175257,'component_verify_ticket','ticket@@@guGIlhRKo_xDokntny27pVmg2z7kdfQwy8AavX_A72Ak2VvxNasWfL-d8pBT6O65FlO1mjZRDM1-xrJLwbJRuA'),(726,'wx00f7d56d549f82ce',20160907180257,'component_verify_ticket','ticket@@@k_7GNlkCnFh3XY7PCGxJpNXgg98dO70T8509cWR8s-RM8eQ3QttVF5Gp5aIsewHkhCQQPs58xAldgTHchv_ZaA'),(727,'wx00f7d56d549f82ce',20160907181258,'component_verify_ticket','ticket@@@k_7GNlkCnFh3XY7PCGxJpNXgg98dO70T8509cWR8s-RM8eQ3QttVF5Gp5aIsewHkhCQQPs58xAldgTHchv_ZaA'),(728,'wx00f7d56d549f82ce',20160907182256,'component_verify_ticket','ticket@@@k_7GNlkCnFh3XY7PCGxJpNXgg98dO70T8509cWR8s-RM8eQ3QttVF5Gp5aIsewHkhCQQPs58xAldgTHchv_ZaA'),(729,'wx00f7d56d549f82ce',20160907183258,'component_verify_ticket','ticket@@@k_7GNlkCnFh3XY7PCGxJpNXgg98dO70T8509cWR8s-RM8eQ3QttVF5Gp5aIsewHkhCQQPs58xAldgTHchv_ZaA'),(730,'wx00f7d56d549f82ce',20160907184258,'component_verify_ticket','ticket@@@k_7GNlkCnFh3XY7PCGxJpNXgg98dO70T8509cWR8s-RM8eQ3QttVF5Gp5aIsewHkhCQQPs58xAldgTHchv_ZaA'),(731,'wx00f7d56d549f82ce',20160907185254,'component_verify_ticket','ticket@@@k_7GNlkCnFh3XY7PCGxJpNXgg98dO70T8509cWR8s-RM8eQ3QttVF5Gp5aIsewHkhCQQPs58xAldgTHchv_ZaA'),(732,'wx00f7d56d549f82ce',20160907190257,'component_verify_ticket','ticket@@@tESDPJvAUaJS6FHZ8_GvhrZtFjtF-h1AABPAUaQ0qBo33PSsVpypIYhe0LVNK-WpWQAFTwmouBPNxvaPm9ygoQ'),(733,'wx00f7d56d549f82ce',20160907191256,'component_verify_ticket','ticket@@@tESDPJvAUaJS6FHZ8_GvhrZtFjtF-h1AABPAUaQ0qBo33PSsVpypIYhe0LVNK-WpWQAFTwmouBPNxvaPm9ygoQ'),(734,'wx00f7d56d549f82ce',20160907192255,'component_verify_ticket','ticket@@@tESDPJvAUaJS6FHZ8_GvhrZtFjtF-h1AABPAUaQ0qBo33PSsVpypIYhe0LVNK-WpWQAFTwmouBPNxvaPm9ygoQ'),(735,'wx00f7d56d549f82ce',20160907193255,'component_verify_ticket','ticket@@@tESDPJvAUaJS6FHZ8_GvhrZtFjtF-h1AABPAUaQ0qBo33PSsVpypIYhe0LVNK-WpWQAFTwmouBPNxvaPm9ygoQ'),(736,'wx00f7d56d549f82ce',20160907194254,'component_verify_ticket','ticket@@@tESDPJvAUaJS6FHZ8_GvhrZtFjtF-h1AABPAUaQ0qBo33PSsVpypIYhe0LVNK-WpWQAFTwmouBPNxvaPm9ygoQ'),(737,'wx00f7d56d549f82ce',20160907195254,'component_verify_ticket','ticket@@@tESDPJvAUaJS6FHZ8_GvhrZtFjtF-h1AABPAUaQ0qBo33PSsVpypIYhe0LVNK-WpWQAFTwmouBPNxvaPm9ygoQ'),(738,'wx00f7d56d549f82ce',20160907200258,'component_verify_ticket','ticket@@@kdKlIsNLs04mL-shgdUhRTFyhT9L9U4JaXt_X_7VLt34mzib48LlXyg7U9emSThiAgdkqPP1lOeu1MjhqjLIcg'),(739,'wx00f7d56d549f82ce',20160907201258,'component_verify_ticket','ticket@@@kdKlIsNLs04mL-shgdUhRTFyhT9L9U4JaXt_X_7VLt34mzib48LlXyg7U9emSThiAgdkqPP1lOeu1MjhqjLIcg'),(740,'wx00f7d56d549f82ce',20160907202259,'component_verify_ticket','ticket@@@kdKlIsNLs04mL-shgdUhRTFyhT9L9U4JaXt_X_7VLt34mzib48LlXyg7U9emSThiAgdkqPP1lOeu1MjhqjLIcg'),(741,'wx00f7d56d549f82ce',20160907203301,'component_verify_ticket','ticket@@@kdKlIsNLs04mL-shgdUhRTFyhT9L9U4JaXt_X_7VLt34mzib48LlXyg7U9emSThiAgdkqPP1lOeu1MjhqjLIcg'),(742,'wx00f7d56d549f82ce',20160907204256,'component_verify_ticket','ticket@@@kdKlIsNLs04mL-shgdUhRTFyhT9L9U4JaXt_X_7VLt34mzib48LlXyg7U9emSThiAgdkqPP1lOeu1MjhqjLIcg'),(743,'wx00f7d56d549f82ce',20160907205255,'component_verify_ticket','ticket@@@kdKlIsNLs04mL-shgdUhRTFyhT9L9U4JaXt_X_7VLt34mzib48LlXyg7U9emSThiAgdkqPP1lOeu1MjhqjLIcg'),(744,'wx00f7d56d549f82ce',20160907210259,'component_verify_ticket','ticket@@@Fn1r6ojDjEIN3R-XQcLIRce0TYLevgKXBs7RxR6j1wdj8G8Axd7CKqxcHdeJAkQwh3kybTJFkBAjxYHg4ml2lw'),(745,'wx00f7d56d549f82ce',20160907211256,'component_verify_ticket','ticket@@@Fn1r6ojDjEIN3R-XQcLIRce0TYLevgKXBs7RxR6j1wdj8G8Axd7CKqxcHdeJAkQwh3kybTJFkBAjxYHg4ml2lw'),(746,'wx00f7d56d549f82ce',20160907212257,'component_verify_ticket','ticket@@@Fn1r6ojDjEIN3R-XQcLIRce0TYLevgKXBs7RxR6j1wdj8G8Axd7CKqxcHdeJAkQwh3kybTJFkBAjxYHg4ml2lw'),(747,'wx00f7d56d549f82ce',20160907213257,'component_verify_ticket','ticket@@@Fn1r6ojDjEIN3R-XQcLIRce0TYLevgKXBs7RxR6j1wdj8G8Axd7CKqxcHdeJAkQwh3kybTJFkBAjxYHg4ml2lw'),(748,'wx00f7d56d549f82ce',20160907214256,'component_verify_ticket','ticket@@@Fn1r6ojDjEIN3R-XQcLIRce0TYLevgKXBs7RxR6j1wdj8G8Axd7CKqxcHdeJAkQwh3kybTJFkBAjxYHg4ml2lw'),(749,'wx00f7d56d549f82ce',20160907215258,'component_verify_ticket','ticket@@@Fn1r6ojDjEIN3R-XQcLIRce0TYLevgKXBs7RxR6j1wdj8G8Axd7CKqxcHdeJAkQwh3kybTJFkBAjxYHg4ml2lw'),(750,'wx00f7d56d549f82ce',20160907220302,'component_verify_ticket','ticket@@@HIfoQlCJBoG8F-iCnKXvWhDnju2-6rSIueS5ft3pwpWCB9MykdvapZkVEcZ1UEXEAfFXZ8mZbDkccq5GreBoeQ'),(751,'wx00f7d56d549f82ce',20160907221247,'component_verify_ticket','ticket@@@HIfoQlCJBoG8F-iCnKXvWhDnju2-6rSIueS5ft3pwpWCB9MykdvapZkVEcZ1UEXEAfFXZ8mZbDkccq5GreBoeQ'),(752,'wx00f7d56d549f82ce',20160907222256,'component_verify_ticket','ticket@@@HIfoQlCJBoG8F-iCnKXvWhDnju2-6rSIueS5ft3pwpWCB9MykdvapZkVEcZ1UEXEAfFXZ8mZbDkccq5GreBoeQ'),(753,'wx00f7d56d549f82ce',20160907223253,'component_verify_ticket','ticket@@@HIfoQlCJBoG8F-iCnKXvWhDnju2-6rSIueS5ft3pwpWCB9MykdvapZkVEcZ1UEXEAfFXZ8mZbDkccq5GreBoeQ'),(754,'wx00f7d56d549f82ce',20160907224252,'component_verify_ticket','ticket@@@HIfoQlCJBoG8F-iCnKXvWhDnju2-6rSIueS5ft3pwpWCB9MykdvapZkVEcZ1UEXEAfFXZ8mZbDkccq5GreBoeQ'),(755,'wx00f7d56d549f82ce',20160907225251,'component_verify_ticket','ticket@@@HIfoQlCJBoG8F-iCnKXvWhDnju2-6rSIueS5ft3pwpWCB9MykdvapZkVEcZ1UEXEAfFXZ8mZbDkccq5GreBoeQ'),(756,'wx00f7d56d549f82ce',20160907230252,'component_verify_ticket','ticket@@@UULPqM0s5Yr9i_JDgERnGNWXdczZbyq4PKON-4WeXpI4vGr21SAagEK_Aza_rcNqkxsM93N7K2dF8cz5E-RI6w'),(757,'wx00f7d56d549f82ce',20160907231255,'component_verify_ticket','ticket@@@UULPqM0s5Yr9i_JDgERnGNWXdczZbyq4PKON-4WeXpI4vGr21SAagEK_Aza_rcNqkxsM93N7K2dF8cz5E-RI6w'),(758,'wx00f7d56d549f82ce',20160907232255,'component_verify_ticket','ticket@@@UULPqM0s5Yr9i_JDgERnGNWXdczZbyq4PKON-4WeXpI4vGr21SAagEK_Aza_rcNqkxsM93N7K2dF8cz5E-RI6w'),(759,'wx00f7d56d549f82ce',20160907233256,'component_verify_ticket','ticket@@@UULPqM0s5Yr9i_JDgERnGNWXdczZbyq4PKON-4WeXpI4vGr21SAagEK_Aza_rcNqkxsM93N7K2dF8cz5E-RI6w'),(760,'wx00f7d56d549f82ce',20160907234254,'component_verify_ticket','ticket@@@UULPqM0s5Yr9i_JDgERnGNWXdczZbyq4PKON-4WeXpI4vGr21SAagEK_Aza_rcNqkxsM93N7K2dF8cz5E-RI6w'),(761,'wx00f7d56d549f82ce',20160907235253,'component_verify_ticket','ticket@@@UULPqM0s5Yr9i_JDgERnGNWXdczZbyq4PKON-4WeXpI4vGr21SAagEK_Aza_rcNqkxsM93N7K2dF8cz5E-RI6w'),(762,'wx00f7d56d549f82ce',20160908000255,'component_verify_ticket','ticket@@@7dV3xxrj3glLNYVul1eR_ogywlSkv33qDP7WjODPO6qlIIeLLU9u7T1pNdTCD1N-ikxQaWgdUkerchAhJ64ykg'),(763,'wx00f7d56d549f82ce',20160908001249,'component_verify_ticket','ticket@@@7dV3xxrj3glLNYVul1eR_ogywlSkv33qDP7WjODPO6qlIIeLLU9u7T1pNdTCD1N-ikxQaWgdUkerchAhJ64ykg'),(764,'wx00f7d56d549f82ce',20160908002243,'component_verify_ticket','ticket@@@7dV3xxrj3glLNYVul1eR_ogywlSkv33qDP7WjODPO6qlIIeLLU9u7T1pNdTCD1N-ikxQaWgdUkerchAhJ64ykg'),(765,'wx00f7d56d549f82ce',20160908003253,'component_verify_ticket','ticket@@@7dV3xxrj3glLNYVul1eR_ogywlSkv33qDP7WjODPO6qlIIeLLU9u7T1pNdTCD1N-ikxQaWgdUkerchAhJ64ykg'),(766,'wx00f7d56d549f82ce',20160908004248,'component_verify_ticket','ticket@@@7dV3xxrj3glLNYVul1eR_ogywlSkv33qDP7WjODPO6qlIIeLLU9u7T1pNdTCD1N-ikxQaWgdUkerchAhJ64ykg'),(767,'wx00f7d56d549f82ce',20160908005247,'component_verify_ticket','ticket@@@7dV3xxrj3glLNYVul1eR_ogywlSkv33qDP7WjODPO6qlIIeLLU9u7T1pNdTCD1N-ikxQaWgdUkerchAhJ64ykg'),(768,'wx00f7d56d549f82ce',20160908010242,'component_verify_ticket','ticket@@@BNRyM4H2UGKSVQOlkUWYs2RdZP-zTbc5BvEkZGO9mM8IQMRpGdqFoZOSwMTzAaEOP35Cq2z6qPgqU9tJlfd9mA'),(769,'wx00f7d56d549f82ce',20160908011249,'component_verify_ticket','ticket@@@BNRyM4H2UGKSVQOlkUWYs2RdZP-zTbc5BvEkZGO9mM8IQMRpGdqFoZOSwMTzAaEOP35Cq2z6qPgqU9tJlfd9mA'),(770,'wx00f7d56d549f82ce',20160908012249,'component_verify_ticket','ticket@@@BNRyM4H2UGKSVQOlkUWYs2RdZP-zTbc5BvEkZGO9mM8IQMRpGdqFoZOSwMTzAaEOP35Cq2z6qPgqU9tJlfd9mA'),(771,'wx00f7d56d549f82ce',20160908013249,'component_verify_ticket','ticket@@@BNRyM4H2UGKSVQOlkUWYs2RdZP-zTbc5BvEkZGO9mM8IQMRpGdqFoZOSwMTzAaEOP35Cq2z6qPgqU9tJlfd9mA'),(772,'wx00f7d56d549f82ce',20160908014249,'component_verify_ticket','ticket@@@BNRyM4H2UGKSVQOlkUWYs2RdZP-zTbc5BvEkZGO9mM8IQMRpGdqFoZOSwMTzAaEOP35Cq2z6qPgqU9tJlfd9mA'),(773,'wx00f7d56d549f82ce',20160908015242,'component_verify_ticket','ticket@@@BNRyM4H2UGKSVQOlkUWYs2RdZP-zTbc5BvEkZGO9mM8IQMRpGdqFoZOSwMTzAaEOP35Cq2z6qPgqU9tJlfd9mA'),(774,'wx00f7d56d549f82ce',20160908020251,'component_verify_ticket','ticket@@@T_2HPJJaQVMdEIB6hBFFTc9DSWt9xB9vdQT6lB_MDw_ZHK0HuH1ojHUQJE6N7E2TQ0B-Hq43IqA_6-SZ-A7r4w'),(775,'wx00f7d56d549f82ce',20160908021250,'component_verify_ticket','ticket@@@T_2HPJJaQVMdEIB6hBFFTc9DSWt9xB9vdQT6lB_MDw_ZHK0HuH1ojHUQJE6N7E2TQ0B-Hq43IqA_6-SZ-A7r4w'),(776,'wx00f7d56d549f82ce',20160908022252,'component_verify_ticket','ticket@@@T_2HPJJaQVMdEIB6hBFFTc9DSWt9xB9vdQT6lB_MDw_ZHK0HuH1ojHUQJE6N7E2TQ0B-Hq43IqA_6-SZ-A7r4w'),(777,'wx00f7d56d549f82ce',20160908023251,'component_verify_ticket','ticket@@@T_2HPJJaQVMdEIB6hBFFTc9DSWt9xB9vdQT6lB_MDw_ZHK0HuH1ojHUQJE6N7E2TQ0B-Hq43IqA_6-SZ-A7r4w'),(778,'wx00f7d56d549f82ce',20160908024250,'component_verify_ticket','ticket@@@T_2HPJJaQVMdEIB6hBFFTc9DSWt9xB9vdQT6lB_MDw_ZHK0HuH1ojHUQJE6N7E2TQ0B-Hq43IqA_6-SZ-A7r4w'),(779,'wx00f7d56d549f82ce',20160908025250,'component_verify_ticket','ticket@@@T_2HPJJaQVMdEIB6hBFFTc9DSWt9xB9vdQT6lB_MDw_ZHK0HuH1ojHUQJE6N7E2TQ0B-Hq43IqA_6-SZ-A7r4w'),(780,'wx00f7d56d549f82ce',20160908030251,'component_verify_ticket','ticket@@@uKo1lg8E_v30H9DDHHVuVzkeHOuIQMXdvUj-U1HYRtoBuzJSbACDAsGvyzfuvAcIOnh2X1ytyXikUfv6dxT4Hw'),(781,'wx00f7d56d549f82ce',20160908031252,'component_verify_ticket','ticket@@@uKo1lg8E_v30H9DDHHVuVzkeHOuIQMXdvUj-U1HYRtoBuzJSbACDAsGvyzfuvAcIOnh2X1ytyXikUfv6dxT4Hw'),(782,'wx00f7d56d549f82ce',20160908032251,'component_verify_ticket','ticket@@@uKo1lg8E_v30H9DDHHVuVzkeHOuIQMXdvUj-U1HYRtoBuzJSbACDAsGvyzfuvAcIOnh2X1ytyXikUfv6dxT4Hw'),(783,'wx00f7d56d549f82ce',20160908033252,'component_verify_ticket','ticket@@@uKo1lg8E_v30H9DDHHVuVzkeHOuIQMXdvUj-U1HYRtoBuzJSbACDAsGvyzfuvAcIOnh2X1ytyXikUfv6dxT4Hw'),(784,'wx00f7d56d549f82ce',20160908034252,'component_verify_ticket','ticket@@@uKo1lg8E_v30H9DDHHVuVzkeHOuIQMXdvUj-U1HYRtoBuzJSbACDAsGvyzfuvAcIOnh2X1ytyXikUfv6dxT4Hw'),(785,'wx00f7d56d549f82ce',20160908035251,'component_verify_ticket','ticket@@@uKo1lg8E_v30H9DDHHVuVzkeHOuIQMXdvUj-U1HYRtoBuzJSbACDAsGvyzfuvAcIOnh2X1ytyXikUfv6dxT4Hw'),(786,'wx00f7d56d549f82ce',20160908040255,'component_verify_ticket','ticket@@@TNH02tsrxJ06Z_UQsQa9EhN_Tcr-G3CcxPSJgYeXDygo_h27mh2tqmqwQZszXeV6PYRDRF5vqYQriSsGQef5bw'),(787,'wx00f7d56d549f82ce',20160908041252,'component_verify_ticket','ticket@@@TNH02tsrxJ06Z_UQsQa9EhN_Tcr-G3CcxPSJgYeXDygo_h27mh2tqmqwQZszXeV6PYRDRF5vqYQriSsGQef5bw'),(788,'wx00f7d56d549f82ce',20160908042251,'component_verify_ticket','ticket@@@TNH02tsrxJ06Z_UQsQa9EhN_Tcr-G3CcxPSJgYeXDygo_h27mh2tqmqwQZszXeV6PYRDRF5vqYQriSsGQef5bw'),(789,'wx00f7d56d549f82ce',20160908043252,'component_verify_ticket','ticket@@@TNH02tsrxJ06Z_UQsQa9EhN_Tcr-G3CcxPSJgYeXDygo_h27mh2tqmqwQZszXeV6PYRDRF5vqYQriSsGQef5bw'),(790,'wx00f7d56d549f82ce',20160908044251,'component_verify_ticket','ticket@@@TNH02tsrxJ06Z_UQsQa9EhN_Tcr-G3CcxPSJgYeXDygo_h27mh2tqmqwQZszXeV6PYRDRF5vqYQriSsGQef5bw'),(791,'wx00f7d56d549f82ce',20160908045244,'component_verify_ticket','ticket@@@TNH02tsrxJ06Z_UQsQa9EhN_Tcr-G3CcxPSJgYeXDygo_h27mh2tqmqwQZszXeV6PYRDRF5vqYQriSsGQef5bw'),(792,'wx00f7d56d549f82ce',20160908050235,'component_verify_ticket','ticket@@@Yjrcu6mNoxX_TmiWDa8YcCL5MFBz3Uj5bM-mgg8CBj7pSo8tXT2LGLTN2Jza9oRQQmn7s6zTfE6FazHawQ6lCQ'),(793,'wx00f7d56d549f82ce',20160908051242,'component_verify_ticket','ticket@@@Yjrcu6mNoxX_TmiWDa8YcCL5MFBz3Uj5bM-mgg8CBj7pSo8tXT2LGLTN2Jza9oRQQmn7s6zTfE6FazHawQ6lCQ'),(794,'wx00f7d56d549f82ce',20160908052242,'component_verify_ticket','ticket@@@Yjrcu6mNoxX_TmiWDa8YcCL5MFBz3Uj5bM-mgg8CBj7pSo8tXT2LGLTN2Jza9oRQQmn7s6zTfE6FazHawQ6lCQ'),(795,'wx00f7d56d549f82ce',20160908053244,'component_verify_ticket','ticket@@@Yjrcu6mNoxX_TmiWDa8YcCL5MFBz3Uj5bM-mgg8CBj7pSo8tXT2LGLTN2Jza9oRQQmn7s6zTfE6FazHawQ6lCQ'),(796,'wx00f7d56d549f82ce',20160908054243,'component_verify_ticket','ticket@@@Yjrcu6mNoxX_TmiWDa8YcCL5MFBz3Uj5bM-mgg8CBj7pSo8tXT2LGLTN2Jza9oRQQmn7s6zTfE6FazHawQ6lCQ'),(797,'wx00f7d56d549f82ce',20160908055249,'component_verify_ticket','ticket@@@Yjrcu6mNoxX_TmiWDa8YcCL5MFBz3Uj5bM-mgg8CBj7pSo8tXT2LGLTN2Jza9oRQQmn7s6zTfE6FazHawQ6lCQ'),(798,'wx00f7d56d549f82ce',20160908060250,'component_verify_ticket','ticket@@@qCer11-x9owfPL4eBD9doQhzXWW56Trpe5rWcH5SU-h0aTQZXG_ngueKi2TWbaD3Z2TZn4P38DhFEYkhhfxH1g'),(799,'wx00f7d56d549f82ce',20160908061249,'component_verify_ticket','ticket@@@qCer11-x9owfPL4eBD9doQhzXWW56Trpe5rWcH5SU-h0aTQZXG_ngueKi2TWbaD3Z2TZn4P38DhFEYkhhfxH1g'),(800,'wx00f7d56d549f82ce',20160908062248,'component_verify_ticket','ticket@@@qCer11-x9owfPL4eBD9doQhzXWW56Trpe5rWcH5SU-h0aTQZXG_ngueKi2TWbaD3Z2TZn4P38DhFEYkhhfxH1g'),(801,'wx00f7d56d549f82ce',20160908063255,'component_verify_ticket','ticket@@@qCer11-x9owfPL4eBD9doQhzXWW56Trpe5rWcH5SU-h0aTQZXG_ngueKi2TWbaD3Z2TZn4P38DhFEYkhhfxH1g'),(802,'wx00f7d56d549f82ce',20160908064241,'component_verify_ticket','ticket@@@qCer11-x9owfPL4eBD9doQhzXWW56Trpe5rWcH5SU-h0aTQZXG_ngueKi2TWbaD3Z2TZn4P38DhFEYkhhfxH1g'),(803,'wx00f7d56d549f82ce',20160908065242,'component_verify_ticket','ticket@@@qCer11-x9owfPL4eBD9doQhzXWW56Trpe5rWcH5SU-h0aTQZXG_ngueKi2TWbaD3Z2TZn4P38DhFEYkhhfxH1g'),(804,'wx00f7d56d549f82ce',20160908070249,'component_verify_ticket','ticket@@@7Jgg3nNxT7n4hFnH0dBPGhT5_4IPPEkY2270JwPM8xUrpi_iNDr_FOlhlWuhgXqsTnvBdxgwYZ4hNToTK8Wn-w'),(805,'wx00f7d56d549f82ce',20160908071243,'component_verify_ticket','ticket@@@7Jgg3nNxT7n4hFnH0dBPGhT5_4IPPEkY2270JwPM8xUrpi_iNDr_FOlhlWuhgXqsTnvBdxgwYZ4hNToTK8Wn-w'),(806,'wx00f7d56d549f82ce',20160908072248,'component_verify_ticket','ticket@@@7Jgg3nNxT7n4hFnH0dBPGhT5_4IPPEkY2270JwPM8xUrpi_iNDr_FOlhlWuhgXqsTnvBdxgwYZ4hNToTK8Wn-w'),(807,'wx00f7d56d549f82ce',20160908073242,'component_verify_ticket','ticket@@@7Jgg3nNxT7n4hFnH0dBPGhT5_4IPPEkY2270JwPM8xUrpi_iNDr_FOlhlWuhgXqsTnvBdxgwYZ4hNToTK8Wn-w'),(808,'wx00f7d56d549f82ce',20160908074248,'component_verify_ticket','ticket@@@7Jgg3nNxT7n4hFnH0dBPGhT5_4IPPEkY2270JwPM8xUrpi_iNDr_FOlhlWuhgXqsTnvBdxgwYZ4hNToTK8Wn-w'),(809,'wx00f7d56d549f82ce',20160908075247,'component_verify_ticket','ticket@@@7Jgg3nNxT7n4hFnH0dBPGhT5_4IPPEkY2270JwPM8xUrpi_iNDr_FOlhlWuhgXqsTnvBdxgwYZ4hNToTK8Wn-w'),(810,'wx00f7d56d549f82ce',20160908080252,'component_verify_ticket','ticket@@@dHW0_dDOzdYVjlMjlWwdMQguSlrwk2idWb3rJq1NMnn5UgCCCzyMT5BAjGythjIybc9_wvU6V_DLcpDChU8FGA'),(811,'wx00f7d56d549f82ce',20160908081251,'component_verify_ticket','ticket@@@dHW0_dDOzdYVjlMjlWwdMQguSlrwk2idWb3rJq1NMnn5UgCCCzyMT5BAjGythjIybc9_wvU6V_DLcpDChU8FGA'),(812,'wx00f7d56d549f82ce',20160908082250,'component_verify_ticket','ticket@@@dHW0_dDOzdYVjlMjlWwdMQguSlrwk2idWb3rJq1NMnn5UgCCCzyMT5BAjGythjIybc9_wvU6V_DLcpDChU8FGA'),(813,'wx00f7d56d549f82ce',20160908083248,'component_verify_ticket','ticket@@@dHW0_dDOzdYVjlMjlWwdMQguSlrwk2idWb3rJq1NMnn5UgCCCzyMT5BAjGythjIybc9_wvU6V_DLcpDChU8FGA'),(814,'wx00f7d56d549f82ce',20160908084252,'component_verify_ticket','ticket@@@dHW0_dDOzdYVjlMjlWwdMQguSlrwk2idWb3rJq1NMnn5UgCCCzyMT5BAjGythjIybc9_wvU6V_DLcpDChU8FGA'),(815,'wx00f7d56d549f82ce',20160908085255,'component_verify_ticket','ticket@@@dHW0_dDOzdYVjlMjlWwdMQguSlrwk2idWb3rJq1NMnn5UgCCCzyMT5BAjGythjIybc9_wvU6V_DLcpDChU8FGA'),(816,'wx00f7d56d549f82ce',20160908090255,'component_verify_ticket','ticket@@@EeQEzjvQWjez7EUATm5GM2FhKs-h4iEuEIwMtetfQGQ4OJXV97mnzJTKRzWzZBEGSYXMgMzFYccqh5W9oSEJhw'),(817,'wx00f7d56d549f82ce',20160908091255,'component_verify_ticket','ticket@@@EeQEzjvQWjez7EUATm5GM2FhKs-h4iEuEIwMtetfQGQ4OJXV97mnzJTKRzWzZBEGSYXMgMzFYccqh5W9oSEJhw'),(818,'wx00f7d56d549f82ce',20160908092254,'component_verify_ticket','ticket@@@EeQEzjvQWjez7EUATm5GM2FhKs-h4iEuEIwMtetfQGQ4OJXV97mnzJTKRzWzZBEGSYXMgMzFYccqh5W9oSEJhw'),(819,'wx00f7d56d549f82ce',20160908093252,'component_verify_ticket','ticket@@@EeQEzjvQWjez7EUATm5GM2FhKs-h4iEuEIwMtetfQGQ4OJXV97mnzJTKRzWzZBEGSYXMgMzFYccqh5W9oSEJhw'),(820,'wx00f7d56d549f82ce',20160908094253,'component_verify_ticket','ticket@@@EeQEzjvQWjez7EUATm5GM2FhKs-h4iEuEIwMtetfQGQ4OJXV97mnzJTKRzWzZBEGSYXMgMzFYccqh5W9oSEJhw'),(821,'wx00f7d56d549f82ce',20160908095255,'component_verify_ticket','ticket@@@EeQEzjvQWjez7EUATm5GM2FhKs-h4iEuEIwMtetfQGQ4OJXV97mnzJTKRzWzZBEGSYXMgMzFYccqh5W9oSEJhw'),(822,'wx00f7d56d549f82ce',20160908100303,'component_verify_ticket','ticket@@@p1wBAjJmXEwXuqz9_pToocKRYpGeDANzlGLAr5o-dnUntO3Mry7u-1Lq9232eRdk71M7q5wwA4pKNKadQiE0Lw'),(823,'wx00f7d56d549f82ce',20160908101303,'component_verify_ticket','ticket@@@p1wBAjJmXEwXuqz9_pToocKRYpGeDANzlGLAr5o-dnUntO3Mry7u-1Lq9232eRdk71M7q5wwA4pKNKadQiE0Lw'),(824,'wx00f7d56d549f82ce',20160908102259,'component_verify_ticket','ticket@@@p1wBAjJmXEwXuqz9_pToocKRYpGeDANzlGLAr5o-dnUntO3Mry7u-1Lq9232eRdk71M7q5wwA4pKNKadQiE0Lw'),(825,'wx00f7d56d549f82ce',20160908103255,'component_verify_ticket','ticket@@@p1wBAjJmXEwXuqz9_pToocKRYpGeDANzlGLAr5o-dnUntO3Mry7u-1Lq9232eRdk71M7q5wwA4pKNKadQiE0Lw'),(826,'wx00f7d56d549f82ce',20160908104300,'component_verify_ticket','ticket@@@p1wBAjJmXEwXuqz9_pToocKRYpGeDANzlGLAr5o-dnUntO3Mry7u-1Lq9232eRdk71M7q5wwA4pKNKadQiE0Lw'),(827,'wx00f7d56d549f82ce',20160908105256,'component_verify_ticket','ticket@@@p1wBAjJmXEwXuqz9_pToocKRYpGeDANzlGLAr5o-dnUntO3Mry7u-1Lq9232eRdk71M7q5wwA4pKNKadQiE0Lw'),(828,'wx00f7d56d549f82ce',20160908111301,'component_verify_ticket','ticket@@@1Fc93hfIougLKvlaRKMcDo9RbtpVqPy6jNdpbwlfaephZUPdPX36hD57UbPe1PlAGumEX4LviZl8kAiUaQHbJQ'),(829,'wx00f7d56d549f82ce',20160908112258,'component_verify_ticket','ticket@@@1Fc93hfIougLKvlaRKMcDo9RbtpVqPy6jNdpbwlfaephZUPdPX36hD57UbPe1PlAGumEX4LviZl8kAiUaQHbJQ'),(830,'wx00f7d56d549f82ce',20160908113302,'component_verify_ticket','ticket@@@1Fc93hfIougLKvlaRKMcDo9RbtpVqPy6jNdpbwlfaephZUPdPX36hD57UbPe1PlAGumEX4LviZl8kAiUaQHbJQ'),(831,'wx00f7d56d549f82ce',20160908114248,'component_verify_ticket','ticket@@@1Fc93hfIougLKvlaRKMcDo9RbtpVqPy6jNdpbwlfaephZUPdPX36hD57UbPe1PlAGumEX4LviZl8kAiUaQHbJQ'),(832,'wx00f7d56d549f82ce',20160908115259,'component_verify_ticket','ticket@@@1Fc93hfIougLKvlaRKMcDo9RbtpVqPy6jNdpbwlfaephZUPdPX36hD57UbPe1PlAGumEX4LviZl8kAiUaQHbJQ'),(833,'wx00f7d56d549f82ce',20160908134242,'component_verify_ticket','ticket@@@TdXlecL9NeORB58HTK2md1-Pa67DVTz7aB-H0DIQkP80S4w5hYbvmpYYJhIr_Qn-L5gDSuCpvegoVOZGChFUVA'),(834,'wx00f7d56d549f82ce',20160908135254,'component_verify_ticket','ticket@@@TdXlecL9NeORB58HTK2md1-Pa67DVTz7aB-H0DIQkP80S4w5hYbvmpYYJhIr_Qn-L5gDSuCpvegoVOZGChFUVA'),(835,'wx00f7d56d549f82ce',20160908140255,'component_verify_ticket','ticket@@@6ulL4CaMZuCzBzILSMHno2tXTV3liPS22QMaEBZo8BA4XKsIQdnSPjXnpH9PQQ_qBFzUuQOoJdyT32vkWMgzyg'),(836,'wx00f7d56d549f82ce',20160908142300,'component_verify_ticket','ticket@@@6ulL4CaMZuCzBzILSMHno2tXTV3liPS22QMaEBZo8BA4XKsIQdnSPjXnpH9PQQ_qBFzUuQOoJdyT32vkWMgzyg'),(837,'wx00f7d56d549f82ce',20160908143243,'component_verify_ticket','ticket@@@6ulL4CaMZuCzBzILSMHno2tXTV3liPS22QMaEBZo8BA4XKsIQdnSPjXnpH9PQQ_qBFzUuQOoJdyT32vkWMgzyg'),(838,'wx00f7d56d549f82ce',20160908144243,'component_verify_ticket','ticket@@@6ulL4CaMZuCzBzILSMHno2tXTV3liPS22QMaEBZo8BA4XKsIQdnSPjXnpH9PQQ_qBFzUuQOoJdyT32vkWMgzyg'),(839,'wx00f7d56d549f82ce',20160908145246,'component_verify_ticket','ticket@@@6ulL4CaMZuCzBzILSMHno2tXTV3liPS22QMaEBZo8BA4XKsIQdnSPjXnpH9PQQ_qBFzUuQOoJdyT32vkWMgzyg'),(840,'wx00f7d56d549f82ce',20160908150256,'component_verify_ticket','ticket@@@uLRcNuRFmbCSWb5ZzWIxK5REns7VBuaBd-JBk8jf0xcuQjwB_Sj-jy8fYc56AZJGD35ZArsXk6gFxJADu0Zbhw'),(841,'wx00f7d56d549f82ce',20160908151255,'component_verify_ticket','ticket@@@uLRcNuRFmbCSWb5ZzWIxK5REns7VBuaBd-JBk8jf0xcuQjwB_Sj-jy8fYc56AZJGD35ZArsXk6gFxJADu0Zbhw'),(842,'wx00f7d56d549f82ce',20160908152256,'component_verify_ticket','ticket@@@uLRcNuRFmbCSWb5ZzWIxK5REns7VBuaBd-JBk8jf0xcuQjwB_Sj-jy8fYc56AZJGD35ZArsXk6gFxJADu0Zbhw'),(843,'wx00f7d56d549f82ce',20160908153256,'component_verify_ticket','ticket@@@uLRcNuRFmbCSWb5ZzWIxK5REns7VBuaBd-JBk8jf0xcuQjwB_Sj-jy8fYc56AZJGD35ZArsXk6gFxJADu0Zbhw'),(844,'wx00f7d56d549f82ce',20160908154256,'component_verify_ticket','ticket@@@uLRcNuRFmbCSWb5ZzWIxK5REns7VBuaBd-JBk8jf0xcuQjwB_Sj-jy8fYc56AZJGD35ZArsXk6gFxJADu0Zbhw'),(845,'wx00f7d56d549f82ce',20160908155255,'component_verify_ticket','ticket@@@uLRcNuRFmbCSWb5ZzWIxK5REns7VBuaBd-JBk8jf0xcuQjwB_Sj-jy8fYc56AZJGD35ZArsXk6gFxJADu0Zbhw'),(846,'wx00f7d56d549f82ce',20160908160256,'component_verify_ticket','ticket@@@VRKTx3x-ykI2b30ytX37Byb0emNnM4MxPVnSzLbsxfaxzF27RBrESjDKss87icOeoC4QESq98VjHwkI9GkWHMw'),(847,'wx00f7d56d549f82ce',20160908161257,'component_verify_ticket','ticket@@@VRKTx3x-ykI2b30ytX37Byb0emNnM4MxPVnSzLbsxfaxzF27RBrESjDKss87icOeoC4QESq98VjHwkI9GkWHMw'),(848,'wx00f7d56d549f82ce',20160908162258,'component_verify_ticket','ticket@@@VRKTx3x-ykI2b30ytX37Byb0emNnM4MxPVnSzLbsxfaxzF27RBrESjDKss87icOeoC4QESq98VjHwkI9GkWHMw'),(849,'wx00f7d56d549f82ce',20160908163255,'component_verify_ticket','ticket@@@VRKTx3x-ykI2b30ytX37Byb0emNnM4MxPVnSzLbsxfaxzF27RBrESjDKss87icOeoC4QESq98VjHwkI9GkWHMw'),(850,'wx00f7d56d549f82ce',20160908164256,'component_verify_ticket','ticket@@@VRKTx3x-ykI2b30ytX37Byb0emNnM4MxPVnSzLbsxfaxzF27RBrESjDKss87icOeoC4QESq98VjHwkI9GkWHMw'),(851,'wx00f7d56d549f82ce',20160908165256,'component_verify_ticket','ticket@@@VRKTx3x-ykI2b30ytX37Byb0emNnM4MxPVnSzLbsxfaxzF27RBrESjDKss87icOeoC4QESq98VjHwkI9GkWHMw'),(852,'wx00f7d56d549f82ce',20160908170300,'component_verify_ticket','ticket@@@R1Z3u6nTszNaVm1_5RcALEKHza5POSEYXJwqpgiW0dT8-IjhE-r0_wFIRq5Q-YXLqHIWZEfzlhhSRpFHVswibg'),(853,'wx00f7d56d549f82ce',20160908171256,'component_verify_ticket','ticket@@@R1Z3u6nTszNaVm1_5RcALEKHza5POSEYXJwqpgiW0dT8-IjhE-r0_wFIRq5Q-YXLqHIWZEfzlhhSRpFHVswibg'),(854,'wx00f7d56d549f82ce',20160908172259,'component_verify_ticket','ticket@@@R1Z3u6nTszNaVm1_5RcALEKHza5POSEYXJwqpgiW0dT8-IjhE-r0_wFIRq5Q-YXLqHIWZEfzlhhSRpFHVswibg'),(855,'wx00f7d56d549f82ce',20160908173258,'component_verify_ticket','ticket@@@R1Z3u6nTszNaVm1_5RcALEKHza5POSEYXJwqpgiW0dT8-IjhE-r0_wFIRq5Q-YXLqHIWZEfzlhhSRpFHVswibg'),(856,'wx00f7d56d549f82ce',20160908174303,'component_verify_ticket','ticket@@@R1Z3u6nTszNaVm1_5RcALEKHza5POSEYXJwqpgiW0dT8-IjhE-r0_wFIRq5Q-YXLqHIWZEfzlhhSRpFHVswibg'),(857,'wx00f7d56d549f82ce',20160908175258,'component_verify_ticket','ticket@@@R1Z3u6nTszNaVm1_5RcALEKHza5POSEYXJwqpgiW0dT8-IjhE-r0_wFIRq5Q-YXLqHIWZEfzlhhSRpFHVswibg'),(858,'wx00f7d56d549f82ce',20160908180301,'component_verify_ticket','ticket@@@wESpVVQJd5A-_BmnxVdDorFkQhaQl1hF_NvuDXoQ0q4cvMgRuqP8uQq5LPwLH-6Tg0Q3S78QQyY3ajOLWQQ8TA'),(859,'wx00f7d56d549f82ce',20160908181257,'component_verify_ticket','ticket@@@wESpVVQJd5A-_BmnxVdDorFkQhaQl1hF_NvuDXoQ0q4cvMgRuqP8uQq5LPwLH-6Tg0Q3S78QQyY3ajOLWQQ8TA'),(860,'wx00f7d56d549f82ce',20160908182257,'component_verify_ticket','ticket@@@wESpVVQJd5A-_BmnxVdDorFkQhaQl1hF_NvuDXoQ0q4cvMgRuqP8uQq5LPwLH-6Tg0Q3S78QQyY3ajOLWQQ8TA'),(861,'wx00f7d56d549f82ce',20160908183256,'component_verify_ticket','ticket@@@wESpVVQJd5A-_BmnxVdDorFkQhaQl1hF_NvuDXoQ0q4cvMgRuqP8uQq5LPwLH-6Tg0Q3S78QQyY3ajOLWQQ8TA'),(862,'wx00f7d56d549f82ce',20160908184254,'component_verify_ticket','ticket@@@wESpVVQJd5A-_BmnxVdDorFkQhaQl1hF_NvuDXoQ0q4cvMgRuqP8uQq5LPwLH-6Tg0Q3S78QQyY3ajOLWQQ8TA'),(863,'wx00f7d56d549f82ce',20160908185254,'component_verify_ticket','ticket@@@wESpVVQJd5A-_BmnxVdDorFkQhaQl1hF_NvuDXoQ0q4cvMgRuqP8uQq5LPwLH-6Tg0Q3S78QQyY3ajOLWQQ8TA'),(864,'wx00f7d56d549f82ce',20160908190259,'component_verify_ticket','ticket@@@oELV_TIQ8GFucGIPKBi7ubHbrfQciSwVDZH2XB1AFngth2TkkN0SBj_-oE6RzN54EL6iSpBWleC0V1knqK2r_Q'),(865,'wx00f7d56d549f82ce',20160908191300,'component_verify_ticket','ticket@@@oELV_TIQ8GFucGIPKBi7ubHbrfQciSwVDZH2XB1AFngth2TkkN0SBj_-oE6RzN54EL6iSpBWleC0V1knqK2r_Q'),(866,'wx00f7d56d549f82ce',20160908192259,'component_verify_ticket','ticket@@@oELV_TIQ8GFucGIPKBi7ubHbrfQciSwVDZH2XB1AFngth2TkkN0SBj_-oE6RzN54EL6iSpBWleC0V1knqK2r_Q'),(867,'wx00f7d56d549f82ce',20160908193301,'component_verify_ticket','ticket@@@oELV_TIQ8GFucGIPKBi7ubHbrfQciSwVDZH2XB1AFngth2TkkN0SBj_-oE6RzN54EL6iSpBWleC0V1knqK2r_Q'),(868,'wx00f7d56d549f82ce',20160908194247,'component_verify_ticket','ticket@@@oELV_TIQ8GFucGIPKBi7ubHbrfQciSwVDZH2XB1AFngth2TkkN0SBj_-oE6RzN54EL6iSpBWleC0V1knqK2r_Q'),(869,'wx00f7d56d549f82ce',20160908195254,'component_verify_ticket','ticket@@@oELV_TIQ8GFucGIPKBi7ubHbrfQciSwVDZH2XB1AFngth2TkkN0SBj_-oE6RzN54EL6iSpBWleC0V1knqK2r_Q'),(870,'wx00f7d56d549f82ce',20160908200259,'component_verify_ticket','ticket@@@YAM4nyqqPq1hKX2algAb7t6IkSEMMNApOqc73fbwDID0i2mddHrTZnHreeeVHbR6HFiXl-Ile0O9Q2XOck0A7A'),(871,'wx00f7d56d549f82ce',20160908201256,'component_verify_ticket','ticket@@@YAM4nyqqPq1hKX2algAb7t6IkSEMMNApOqc73fbwDID0i2mddHrTZnHreeeVHbR6HFiXl-Ile0O9Q2XOck0A7A'),(872,'wx00f7d56d549f82ce',20160908202259,'component_verify_ticket','ticket@@@YAM4nyqqPq1hKX2algAb7t6IkSEMMNApOqc73fbwDID0i2mddHrTZnHreeeVHbR6HFiXl-Ile0O9Q2XOck0A7A'),(873,'wx00f7d56d549f82ce',20160908203259,'component_verify_ticket','ticket@@@YAM4nyqqPq1hKX2algAb7t6IkSEMMNApOqc73fbwDID0i2mddHrTZnHreeeVHbR6HFiXl-Ile0O9Q2XOck0A7A'),(874,'wx00f7d56d549f82ce',20160908204303,'component_verify_ticket','ticket@@@YAM4nyqqPq1hKX2algAb7t6IkSEMMNApOqc73fbwDID0i2mddHrTZnHreeeVHbR6HFiXl-Ile0O9Q2XOck0A7A'),(875,'wx00f7d56d549f82ce',20160908205302,'component_verify_ticket','ticket@@@YAM4nyqqPq1hKX2algAb7t6IkSEMMNApOqc73fbwDID0i2mddHrTZnHreeeVHbR6HFiXl-Ile0O9Q2XOck0A7A'),(876,'wx00f7d56d549f82ce',20160908210306,'component_verify_ticket','ticket@@@M7VKiNmOnALvueWgLIPC8kWRl8agsCAVEDn6hpnukUJbzu_9oRucVupPUVqybeNxU0y8lbgz5hBp7Elpc5egTg'),(877,'wx00f7d56d549f82ce',20160908211303,'component_verify_ticket','ticket@@@M7VKiNmOnALvueWgLIPC8kWRl8agsCAVEDn6hpnukUJbzu_9oRucVupPUVqybeNxU0y8lbgz5hBp7Elpc5egTg'),(878,'wx00f7d56d549f82ce',20160908212302,'component_verify_ticket','ticket@@@M7VKiNmOnALvueWgLIPC8kWRl8agsCAVEDn6hpnukUJbzu_9oRucVupPUVqybeNxU0y8lbgz5hBp7Elpc5egTg'),(879,'wx00f7d56d549f82ce',20160908213259,'component_verify_ticket','ticket@@@M7VKiNmOnALvueWgLIPC8kWRl8agsCAVEDn6hpnukUJbzu_9oRucVupPUVqybeNxU0y8lbgz5hBp7Elpc5egTg'),(880,'wx00f7d56d549f82ce',20160908214257,'component_verify_ticket','ticket@@@M7VKiNmOnALvueWgLIPC8kWRl8agsCAVEDn6hpnukUJbzu_9oRucVupPUVqybeNxU0y8lbgz5hBp7Elpc5egTg'),(881,'wx00f7d56d549f82ce',20160908215257,'component_verify_ticket','ticket@@@M7VKiNmOnALvueWgLIPC8kWRl8agsCAVEDn6hpnukUJbzu_9oRucVupPUVqybeNxU0y8lbgz5hBp7Elpc5egTg'),(882,'wx00f7d56d549f82ce',20160908220301,'component_verify_ticket','ticket@@@TSoFSlI4gmTEpui2-hUT726Z_56Aq0JQTjX7PGnE0a0cfeQBswr_l0_LPGNRxM5AKwveS-MJ1BYucQRugJny6A'),(883,'wx00f7d56d549f82ce',20160908221302,'component_verify_ticket','ticket@@@TSoFSlI4gmTEpui2-hUT726Z_56Aq0JQTjX7PGnE0a0cfeQBswr_l0_LPGNRxM5AKwveS-MJ1BYucQRugJny6A'),(884,'wx00f7d56d549f82ce',20160908222300,'component_verify_ticket','ticket@@@TSoFSlI4gmTEpui2-hUT726Z_56Aq0JQTjX7PGnE0a0cfeQBswr_l0_LPGNRxM5AKwveS-MJ1BYucQRugJny6A'),(885,'wx00f7d56d549f82ce',20160908223237,'component_verify_ticket','ticket@@@TSoFSlI4gmTEpui2-hUT726Z_56Aq0JQTjX7PGnE0a0cfeQBswr_l0_LPGNRxM5AKwveS-MJ1BYucQRugJny6A'),(886,'wx00f7d56d549f82ce',20160908224255,'component_verify_ticket','ticket@@@TSoFSlI4gmTEpui2-hUT726Z_56Aq0JQTjX7PGnE0a0cfeQBswr_l0_LPGNRxM5AKwveS-MJ1BYucQRugJny6A'),(887,'wx00f7d56d549f82ce',20160908225251,'component_verify_ticket','ticket@@@TSoFSlI4gmTEpui2-hUT726Z_56Aq0JQTjX7PGnE0a0cfeQBswr_l0_LPGNRxM5AKwveS-MJ1BYucQRugJny6A'),(888,'wx00f7d56d549f82ce',20160908230253,'component_verify_ticket','ticket@@@0M04p-gbmju6zRB8A4NMf7rKe8jmzjhf-mke9Z63-BkQ1GDJ8ZUxgmgdKG-TYHILHVVPKhJt90En47hQWpz8Iw'),(889,'wx00f7d56d549f82ce',20160908231256,'component_verify_ticket','ticket@@@0M04p-gbmju6zRB8A4NMf7rKe8jmzjhf-mke9Z63-BkQ1GDJ8ZUxgmgdKG-TYHILHVVPKhJt90En47hQWpz8Iw'),(890,'wx00f7d56d549f82ce',20160908232253,'component_verify_ticket','ticket@@@0M04p-gbmju6zRB8A4NMf7rKe8jmzjhf-mke9Z63-BkQ1GDJ8ZUxgmgdKG-TYHILHVVPKhJt90En47hQWpz8Iw'),(891,'wx00f7d56d549f82ce',20160908233255,'component_verify_ticket','ticket@@@0M04p-gbmju6zRB8A4NMf7rKe8jmzjhf-mke9Z63-BkQ1GDJ8ZUxgmgdKG-TYHILHVVPKhJt90En47hQWpz8Iw'),(892,'wx00f7d56d549f82ce',20160908234255,'component_verify_ticket','ticket@@@0M04p-gbmju6zRB8A4NMf7rKe8jmzjhf-mke9Z63-BkQ1GDJ8ZUxgmgdKG-TYHILHVVPKhJt90En47hQWpz8Iw'),(893,'wx00f7d56d549f82ce',20160908235252,'component_verify_ticket','ticket@@@0M04p-gbmju6zRB8A4NMf7rKe8jmzjhf-mke9Z63-BkQ1GDJ8ZUxgmgdKG-TYHILHVVPKhJt90En47hQWpz8Iw'),(894,'wx00f7d56d549f82ce',20160909000255,'component_verify_ticket','ticket@@@DdOGW35-z9AGltAgMLr3JEnvEHY0GESXPJNdD9J5V3KQNXIebMScNogrEP-F9_NVg1VLySpMQ23LeVLm_ZpDHg'),(895,'wx00f7d56d549f82ce',20160909001253,'component_verify_ticket','ticket@@@DdOGW35-z9AGltAgMLr3JEnvEHY0GESXPJNdD9J5V3KQNXIebMScNogrEP-F9_NVg1VLySpMQ23LeVLm_ZpDHg'),(896,'wx00f7d56d549f82ce',20160909002253,'component_verify_ticket','ticket@@@DdOGW35-z9AGltAgMLr3JEnvEHY0GESXPJNdD9J5V3KQNXIebMScNogrEP-F9_NVg1VLySpMQ23LeVLm_ZpDHg'),(897,'wx00f7d56d549f82ce',20160909003253,'component_verify_ticket','ticket@@@DdOGW35-z9AGltAgMLr3JEnvEHY0GESXPJNdD9J5V3KQNXIebMScNogrEP-F9_NVg1VLySpMQ23LeVLm_ZpDHg'),(898,'wx00f7d56d549f82ce',20160909004247,'component_verify_ticket','ticket@@@DdOGW35-z9AGltAgMLr3JEnvEHY0GESXPJNdD9J5V3KQNXIebMScNogrEP-F9_NVg1VLySpMQ23LeVLm_ZpDHg'),(899,'wx00f7d56d549f82ce',20160909005247,'component_verify_ticket','ticket@@@DdOGW35-z9AGltAgMLr3JEnvEHY0GESXPJNdD9J5V3KQNXIebMScNogrEP-F9_NVg1VLySpMQ23LeVLm_ZpDHg'),(900,'wx00f7d56d549f82ce',20160909010247,'component_verify_ticket','ticket@@@zvdWUqekU-u1ITmHl7MnJW7DANoYxh9mhHKqJlQM1QmrQsPVZW3gD1w9rhVYLbrl8uHCi3CnT2rq24_cNgc6KQ'),(901,'wx00f7d56d549f82ce',20160909011250,'component_verify_ticket','ticket@@@zvdWUqekU-u1ITmHl7MnJW7DANoYxh9mhHKqJlQM1QmrQsPVZW3gD1w9rhVYLbrl8uHCi3CnT2rq24_cNgc6KQ'),(902,'wx00f7d56d549f82ce',20160909012250,'component_verify_ticket','ticket@@@zvdWUqekU-u1ITmHl7MnJW7DANoYxh9mhHKqJlQM1QmrQsPVZW3gD1w9rhVYLbrl8uHCi3CnT2rq24_cNgc6KQ'),(903,'wx00f7d56d549f82ce',20160909013249,'component_verify_ticket','ticket@@@zvdWUqekU-u1ITmHl7MnJW7DANoYxh9mhHKqJlQM1QmrQsPVZW3gD1w9rhVYLbrl8uHCi3CnT2rq24_cNgc6KQ'),(904,'wx00f7d56d549f82ce',20160909014250,'component_verify_ticket','ticket@@@zvdWUqekU-u1ITmHl7MnJW7DANoYxh9mhHKqJlQM1QmrQsPVZW3gD1w9rhVYLbrl8uHCi3CnT2rq24_cNgc6KQ'),(905,'wx00f7d56d549f82ce',20160909015250,'component_verify_ticket','ticket@@@zvdWUqekU-u1ITmHl7MnJW7DANoYxh9mhHKqJlQM1QmrQsPVZW3gD1w9rhVYLbrl8uHCi3CnT2rq24_cNgc6KQ'),(906,'wx00f7d56d549f82ce',20160909020250,'component_verify_ticket','ticket@@@9bxLUvESBDHrtOYamNGI8bX7zTBMQiEkNw_pVwn3VUIrsxJIb0jPjn32COdNCUIIag__YlS9uOW61T6QK0R4dQ'),(907,'wx00f7d56d549f82ce',20160909021247,'component_verify_ticket','ticket@@@9bxLUvESBDHrtOYamNGI8bX7zTBMQiEkNw_pVwn3VUIrsxJIb0jPjn32COdNCUIIag__YlS9uOW61T6QK0R4dQ'),(908,'wx00f7d56d549f82ce',20160909022251,'component_verify_ticket','ticket@@@9bxLUvESBDHrtOYamNGI8bX7zTBMQiEkNw_pVwn3VUIrsxJIb0jPjn32COdNCUIIag__YlS9uOW61T6QK0R4dQ'),(909,'wx00f7d56d549f82ce',20160909023250,'component_verify_ticket','ticket@@@9bxLUvESBDHrtOYamNGI8bX7zTBMQiEkNw_pVwn3VUIrsxJIb0jPjn32COdNCUIIag__YlS9uOW61T6QK0R4dQ'),(910,'wx00f7d56d549f82ce',20160909024249,'component_verify_ticket','ticket@@@9bxLUvESBDHrtOYamNGI8bX7zTBMQiEkNw_pVwn3VUIrsxJIb0jPjn32COdNCUIIag__YlS9uOW61T6QK0R4dQ'),(911,'wx00f7d56d549f82ce',20160909025253,'component_verify_ticket','ticket@@@9bxLUvESBDHrtOYamNGI8bX7zTBMQiEkNw_pVwn3VUIrsxJIb0jPjn32COdNCUIIag__YlS9uOW61T6QK0R4dQ'),(912,'wx00f7d56d549f82ce',20160909030249,'component_verify_ticket','ticket@@@wSvIhaZ-fNQvlnSdrzOs0hnX67wLIy0INoyXG5KKiGJmkuUQItY2ZyiFLeT3UgI9KJD4Jf_tPxym1gCxO4Q1nA'),(913,'wx00f7d56d549f82ce',20160909031251,'component_verify_ticket','ticket@@@wSvIhaZ-fNQvlnSdrzOs0hnX67wLIy0INoyXG5KKiGJmkuUQItY2ZyiFLeT3UgI9KJD4Jf_tPxym1gCxO4Q1nA'),(914,'wx00f7d56d549f82ce',20160909032250,'component_verify_ticket','ticket@@@wSvIhaZ-fNQvlnSdrzOs0hnX67wLIy0INoyXG5KKiGJmkuUQItY2ZyiFLeT3UgI9KJD4Jf_tPxym1gCxO4Q1nA'),(915,'wx00f7d56d549f82ce',20160909033250,'component_verify_ticket','ticket@@@wSvIhaZ-fNQvlnSdrzOs0hnX67wLIy0INoyXG5KKiGJmkuUQItY2ZyiFLeT3UgI9KJD4Jf_tPxym1gCxO4Q1nA'),(916,'wx00f7d56d549f82ce',20160909034252,'component_verify_ticket','ticket@@@wSvIhaZ-fNQvlnSdrzOs0hnX67wLIy0INoyXG5KKiGJmkuUQItY2ZyiFLeT3UgI9KJD4Jf_tPxym1gCxO4Q1nA'),(917,'wx00f7d56d549f82ce',20160909035252,'component_verify_ticket','ticket@@@wSvIhaZ-fNQvlnSdrzOs0hnX67wLIy0INoyXG5KKiGJmkuUQItY2ZyiFLeT3UgI9KJD4Jf_tPxym1gCxO4Q1nA'),(918,'wx00f7d56d549f82ce',20160909040251,'component_verify_ticket','ticket@@@Vioo-5fbJpKv1xa66I-3dTcENxNyUEtxc8-T9f1yW1J3L5hcJHxlpecK3NudJBV5UUMGCUgEC8iuJWkMaH9lSQ'),(919,'wx00f7d56d549f82ce',20160909041253,'component_verify_ticket','ticket@@@Vioo-5fbJpKv1xa66I-3dTcENxNyUEtxc8-T9f1yW1J3L5hcJHxlpecK3NudJBV5UUMGCUgEC8iuJWkMaH9lSQ'),(920,'wx00f7d56d549f82ce',20160909042252,'component_verify_ticket','ticket@@@Vioo-5fbJpKv1xa66I-3dTcENxNyUEtxc8-T9f1yW1J3L5hcJHxlpecK3NudJBV5UUMGCUgEC8iuJWkMaH9lSQ'),(921,'wx00f7d56d549f82ce',20160909043250,'component_verify_ticket','ticket@@@Vioo-5fbJpKv1xa66I-3dTcENxNyUEtxc8-T9f1yW1J3L5hcJHxlpecK3NudJBV5UUMGCUgEC8iuJWkMaH9lSQ'),(922,'wx00f7d56d549f82ce',20160909044251,'component_verify_ticket','ticket@@@Vioo-5fbJpKv1xa66I-3dTcENxNyUEtxc8-T9f1yW1J3L5hcJHxlpecK3NudJBV5UUMGCUgEC8iuJWkMaH9lSQ'),(923,'wx00f7d56d549f82ce',20160909045257,'component_verify_ticket','ticket@@@Vioo-5fbJpKv1xa66I-3dTcENxNyUEtxc8-T9f1yW1J3L5hcJHxlpecK3NudJBV5UUMGCUgEC8iuJWkMaH9lSQ'),(924,'wx00f7d56d549f82ce',20160909050230,'component_verify_ticket','ticket@@@wlnZlr4zl-0aXuMYfaI70m5jA6x93NRP6kNhe4EGt-5tfPiADvZ6bb6aVBzAnJCHxG2gSYETh3QXfv1eqRRVxw'),(925,'wx00f7d56d549f82ce',20160909051242,'component_verify_ticket','ticket@@@wlnZlr4zl-0aXuMYfaI70m5jA6x93NRP6kNhe4EGt-5tfPiADvZ6bb6aVBzAnJCHxG2gSYETh3QXfv1eqRRVxw'),(926,'wx00f7d56d549f82ce',20160909052247,'component_verify_ticket','ticket@@@wlnZlr4zl-0aXuMYfaI70m5jA6x93NRP6kNhe4EGt-5tfPiADvZ6bb6aVBzAnJCHxG2gSYETh3QXfv1eqRRVxw'),(927,'wx00f7d56d549f82ce',20160909053245,'component_verify_ticket','ticket@@@wlnZlr4zl-0aXuMYfaI70m5jA6x93NRP6kNhe4EGt-5tfPiADvZ6bb6aVBzAnJCHxG2gSYETh3QXfv1eqRRVxw'),(928,'wx00f7d56d549f82ce',20160909054245,'component_verify_ticket','ticket@@@wlnZlr4zl-0aXuMYfaI70m5jA6x93NRP6kNhe4EGt-5tfPiADvZ6bb6aVBzAnJCHxG2gSYETh3QXfv1eqRRVxw'),(929,'wx00f7d56d549f82ce',20160909055248,'component_verify_ticket','ticket@@@wlnZlr4zl-0aXuMYfaI70m5jA6x93NRP6kNhe4EGt-5tfPiADvZ6bb6aVBzAnJCHxG2gSYETh3QXfv1eqRRVxw'),(930,'wx00f7d56d549f82ce',20160909060247,'component_verify_ticket','ticket@@@TbVMSI51q2Sl88tmI7yVWcV3mONd-SVhRP1ok9SRL0xHIbZ_n8edFwLsfd8yYWiD1G3tQ7WUyblCFitJvrC2KA'),(931,'wx00f7d56d549f82ce',20160909061248,'component_verify_ticket','ticket@@@TbVMSI51q2Sl88tmI7yVWcV3mONd-SVhRP1ok9SRL0xHIbZ_n8edFwLsfd8yYWiD1G3tQ7WUyblCFitJvrC2KA'),(932,'wx00f7d56d549f82ce',20160909062251,'component_verify_ticket','ticket@@@TbVMSI51q2Sl88tmI7yVWcV3mONd-SVhRP1ok9SRL0xHIbZ_n8edFwLsfd8yYWiD1G3tQ7WUyblCFitJvrC2KA'),(933,'wx00f7d56d549f82ce',20160909063248,'component_verify_ticket','ticket@@@TbVMSI51q2Sl88tmI7yVWcV3mONd-SVhRP1ok9SRL0xHIbZ_n8edFwLsfd8yYWiD1G3tQ7WUyblCFitJvrC2KA'),(934,'wx00f7d56d549f82ce',20160909064243,'component_verify_ticket','ticket@@@TbVMSI51q2Sl88tmI7yVWcV3mONd-SVhRP1ok9SRL0xHIbZ_n8edFwLsfd8yYWiD1G3tQ7WUyblCFitJvrC2KA'),(935,'wx00f7d56d549f82ce',20160909065241,'component_verify_ticket','ticket@@@TbVMSI51q2Sl88tmI7yVWcV3mONd-SVhRP1ok9SRL0xHIbZ_n8edFwLsfd8yYWiD1G3tQ7WUyblCFitJvrC2KA'),(936,'wx00f7d56d549f82ce',20160909070247,'component_verify_ticket','ticket@@@IhbWcetO1LlqSGuS48ySte9rNI3MnJIiooRia-hm1CC3xCU5aUqniC2CM6oOlRa4PGZizZRyBVjNmvoQ-j4rFA'),(937,'wx00f7d56d549f82ce',20160909071250,'component_verify_ticket','ticket@@@IhbWcetO1LlqSGuS48ySte9rNI3MnJIiooRia-hm1CC3xCU5aUqniC2CM6oOlRa4PGZizZRyBVjNmvoQ-j4rFA'),(938,'wx00f7d56d549f82ce',20160909072248,'component_verify_ticket','ticket@@@IhbWcetO1LlqSGuS48ySte9rNI3MnJIiooRia-hm1CC3xCU5aUqniC2CM6oOlRa4PGZizZRyBVjNmvoQ-j4rFA'),(939,'wx00f7d56d549f82ce',20160909073248,'component_verify_ticket','ticket@@@IhbWcetO1LlqSGuS48ySte9rNI3MnJIiooRia-hm1CC3xCU5aUqniC2CM6oOlRa4PGZizZRyBVjNmvoQ-j4rFA'),(940,'wx00f7d56d549f82ce',20160909074248,'component_verify_ticket','ticket@@@IhbWcetO1LlqSGuS48ySte9rNI3MnJIiooRia-hm1CC3xCU5aUqniC2CM6oOlRa4PGZizZRyBVjNmvoQ-j4rFA'),(941,'wx00f7d56d549f82ce',20160909075249,'component_verify_ticket','ticket@@@IhbWcetO1LlqSGuS48ySte9rNI3MnJIiooRia-hm1CC3xCU5aUqniC2CM6oOlRa4PGZizZRyBVjNmvoQ-j4rFA'),(942,'wx00f7d56d549f82ce',20160909080257,'component_verify_ticket','ticket@@@W_iAbwkI1034p9O13LjjjXFg7nFtszW76VAhbti5tyx7RMBiUe6xTQB5N6naGrwYFZ_KeGJlV28WWYusH4Y-Ug'),(943,'wx00f7d56d549f82ce',20160909081250,'component_verify_ticket','ticket@@@W_iAbwkI1034p9O13LjjjXFg7nFtszW76VAhbti5tyx7RMBiUe6xTQB5N6naGrwYFZ_KeGJlV28WWYusH4Y-Ug'),(944,'wx00f7d56d549f82ce',20160909082252,'component_verify_ticket','ticket@@@W_iAbwkI1034p9O13LjjjXFg7nFtszW76VAhbti5tyx7RMBiUe6xTQB5N6naGrwYFZ_KeGJlV28WWYusH4Y-Ug'),(945,'wx00f7d56d549f82ce',20160909083252,'component_verify_ticket','ticket@@@W_iAbwkI1034p9O13LjjjXFg7nFtszW76VAhbti5tyx7RMBiUe6xTQB5N6naGrwYFZ_KeGJlV28WWYusH4Y-Ug'),(946,'wx00f7d56d549f82ce',20160909084255,'component_verify_ticket','ticket@@@W_iAbwkI1034p9O13LjjjXFg7nFtszW76VAhbti5tyx7RMBiUe6xTQB5N6naGrwYFZ_KeGJlV28WWYusH4Y-Ug'),(947,'wx00f7d56d549f82ce',20160909085255,'component_verify_ticket','ticket@@@W_iAbwkI1034p9O13LjjjXFg7nFtszW76VAhbti5tyx7RMBiUe6xTQB5N6naGrwYFZ_KeGJlV28WWYusH4Y-Ug'),(948,'wx00f7d56d549f82ce',20160909090255,'component_verify_ticket','ticket@@@h3KJ8fS20YVa-xAPATtpcZcG0oi_7Ma0zuirB3tyQRKxz7Wq0d9434F6bCFPcdSCa3aP6czvFk7eUSRYCLTMow'),(949,'wx00f7d56d549f82ce',20160909091257,'component_verify_ticket','ticket@@@h3KJ8fS20YVa-xAPATtpcZcG0oi_7Ma0zuirB3tyQRKxz7Wq0d9434F6bCFPcdSCa3aP6czvFk7eUSRYCLTMow'),(950,'wx00f7d56d549f82ce',20160909092257,'component_verify_ticket','ticket@@@h3KJ8fS20YVa-xAPATtpcZcG0oi_7Ma0zuirB3tyQRKxz7Wq0d9434F6bCFPcdSCa3aP6czvFk7eUSRYCLTMow'),(951,'wx00f7d56d549f82ce',20160909093315,'component_verify_ticket','ticket@@@h3KJ8fS20YVa-xAPATtpcZcG0oi_7Ma0zuirB3tyQRKxz7Wq0d9434F6bCFPcdSCa3aP6czvFk7eUSRYCLTMow'),(952,'wx00f7d56d549f82ce',20160909094258,'component_verify_ticket','ticket@@@h3KJ8fS20YVa-xAPATtpcZcG0oi_7Ma0zuirB3tyQRKxz7Wq0d9434F6bCFPcdSCa3aP6czvFk7eUSRYCLTMow'),(953,'wx00f7d56d549f82ce',20160909095300,'component_verify_ticket','ticket@@@h3KJ8fS20YVa-xAPATtpcZcG0oi_7Ma0zuirB3tyQRKxz7Wq0d9434F6bCFPcdSCa3aP6czvFk7eUSRYCLTMow'),(954,'wx00f7d56d549f82ce',20160909100306,'component_verify_ticket','ticket@@@i2cJUETJg5IW6r9JrcUdQ1PehS_UkV8p1-pEUCyfkK-lOq2uCVkRGLNx-4cUYRtczbqWoGY6xIzRyOi4_TgIdQ'),(955,'wx00f7d56d549f82ce',20160909101303,'component_verify_ticket','ticket@@@i2cJUETJg5IW6r9JrcUdQ1PehS_UkV8p1-pEUCyfkK-lOq2uCVkRGLNx-4cUYRtczbqWoGY6xIzRyOi4_TgIdQ'),(956,'wx00f7d56d549f82ce',20160909102305,'component_verify_ticket','ticket@@@i2cJUETJg5IW6r9JrcUdQ1PehS_UkV8p1-pEUCyfkK-lOq2uCVkRGLNx-4cUYRtczbqWoGY6xIzRyOi4_TgIdQ'),(957,'wx00f7d56d549f82ce',20160909105302,'component_verify_ticket','ticket@@@i2cJUETJg5IW6r9JrcUdQ1PehS_UkV8p1-pEUCyfkK-lOq2uCVkRGLNx-4cUYRtczbqWoGY6xIzRyOi4_TgIdQ'),(958,'wx00f7d56d549f82ce',20160909110307,'component_verify_ticket','ticket@@@f3qCIG9fBEiERzuPvMgt--QljWHyTeYV4kI7sZIEtAHVsJFHm0OJYEMMD_PBQt6jCzZe6ztZXB0SV6jOQrp1DA'),(959,'wx00f7d56d549f82ce',20160909111301,'component_verify_ticket','ticket@@@f3qCIG9fBEiERzuPvMgt--QljWHyTeYV4kI7sZIEtAHVsJFHm0OJYEMMD_PBQt6jCzZe6ztZXB0SV6jOQrp1DA'),(960,'wx00f7d56d549f82ce',20160909112308,'component_verify_ticket','ticket@@@f3qCIG9fBEiERzuPvMgt--QljWHyTeYV4kI7sZIEtAHVsJFHm0OJYEMMD_PBQt6jCzZe6ztZXB0SV6jOQrp1DA'),(961,'wx00f7d56d549f82ce',20160909113308,'component_verify_ticket','ticket@@@f3qCIG9fBEiERzuPvMgt--QljWHyTeYV4kI7sZIEtAHVsJFHm0OJYEMMD_PBQt6jCzZe6ztZXB0SV6jOQrp1DA'),(962,'wx00f7d56d549f82ce',20160909114301,'component_verify_ticket','ticket@@@f3qCIG9fBEiERzuPvMgt--QljWHyTeYV4kI7sZIEtAHVsJFHm0OJYEMMD_PBQt6jCzZe6ztZXB0SV6jOQrp1DA'),(963,'wx00f7d56d549f82ce',20160909115259,'component_verify_ticket','ticket@@@f3qCIG9fBEiERzuPvMgt--QljWHyTeYV4kI7sZIEtAHVsJFHm0OJYEMMD_PBQt6jCzZe6ztZXB0SV6jOQrp1DA'),(964,'wx00f7d56d549f82ce',20160909120255,'component_verify_ticket','ticket@@@zOfQBAgiorR6mglW9BtcuTbN-XBnu9LyGv5gMg3mlRshBhtw8m2rl44BB13bHh0kEYvwgjd9g3YOKCIvrUiIcw'),(965,'wx00f7d56d549f82ce',20160909121257,'component_verify_ticket','ticket@@@zOfQBAgiorR6mglW9BtcuTbN-XBnu9LyGv5gMg3mlRshBhtw8m2rl44BB13bHh0kEYvwgjd9g3YOKCIvrUiIcw'),(966,'wx00f7d56d549f82ce',20160909122259,'component_verify_ticket','ticket@@@zOfQBAgiorR6mglW9BtcuTbN-XBnu9LyGv5gMg3mlRshBhtw8m2rl44BB13bHh0kEYvwgjd9g3YOKCIvrUiIcw'),(967,'wx00f7d56d549f82ce',20160909123319,'component_verify_ticket','ticket@@@zOfQBAgiorR6mglW9BtcuTbN-XBnu9LyGv5gMg3mlRshBhtw8m2rl44BB13bHh0kEYvwgjd9g3YOKCIvrUiIcw'),(968,'wx00f7d56d549f82ce',20160909124253,'component_verify_ticket','ticket@@@zOfQBAgiorR6mglW9BtcuTbN-XBnu9LyGv5gMg3mlRshBhtw8m2rl44BB13bHh0kEYvwgjd9g3YOKCIvrUiIcw'),(969,'wx00f7d56d549f82ce',20160909125254,'component_verify_ticket','ticket@@@zOfQBAgiorR6mglW9BtcuTbN-XBnu9LyGv5gMg3mlRshBhtw8m2rl44BB13bHh0kEYvwgjd9g3YOKCIvrUiIcw'),(970,'wx00f7d56d549f82ce',20160909130256,'component_verify_ticket','ticket@@@lbFgw1f0VP32UtjwKM1bLvq97T84tJF3oZEVEXurU0e8NqpicKme9m8vOP5FVveZoqt9SohytbKK43w8jTrgbg'),(971,'wx00f7d56d549f82ce',20160909131301,'component_verify_ticket','ticket@@@lbFgw1f0VP32UtjwKM1bLvq97T84tJF3oZEVEXurU0e8NqpicKme9m8vOP5FVveZoqt9SohytbKK43w8jTrgbg'),(972,'wx00f7d56d549f82ce',20160909132334,'component_verify_ticket','ticket@@@lbFgw1f0VP32UtjwKM1bLvq97T84tJF3oZEVEXurU0e8NqpicKme9m8vOP5FVveZoqt9SohytbKK43w8jTrgbg'),(973,'wx00f7d56d549f82ce',20160909133254,'component_verify_ticket','ticket@@@lbFgw1f0VP32UtjwKM1bLvq97T84tJF3oZEVEXurU0e8NqpicKme9m8vOP5FVveZoqt9SohytbKK43w8jTrgbg'),(974,'wx00f7d56d549f82ce',20160909134254,'component_verify_ticket','ticket@@@lbFgw1f0VP32UtjwKM1bLvq97T84tJF3oZEVEXurU0e8NqpicKme9m8vOP5FVveZoqt9SohytbKK43w8jTrgbg'),(975,'wx00f7d56d549f82ce',20160909135252,'component_verify_ticket','ticket@@@lbFgw1f0VP32UtjwKM1bLvq97T84tJF3oZEVEXurU0e8NqpicKme9m8vOP5FVveZoqt9SohytbKK43w8jTrgbg'),(976,'wx00f7d56d549f82ce',20160909140255,'component_verify_ticket','ticket@@@tJbBZqYRsoB3evblK08Pd-28WDsL-oGKTxNhQH9QfZRirKd8H58t5qwtM2Q4aWFlamuUEn28BC8QauKJuMX5rA'),(977,'wx00f7d56d549f82ce',20160909141334,'component_verify_ticket','ticket@@@tJbBZqYRsoB3evblK08Pd-28WDsL-oGKTxNhQH9QfZRirKd8H58t5qwtM2Q4aWFlamuUEn28BC8QauKJuMX5rA'),(978,'wx00f7d56d549f82ce',20160909142320,'component_verify_ticket','ticket@@@tJbBZqYRsoB3evblK08Pd-28WDsL-oGKTxNhQH9QfZRirKd8H58t5qwtM2Q4aWFlamuUEn28BC8QauKJuMX5rA'),(979,'wx00f7d56d549f82ce',20160909143258,'component_verify_ticket','ticket@@@tJbBZqYRsoB3evblK08Pd-28WDsL-oGKTxNhQH9QfZRirKd8H58t5qwtM2Q4aWFlamuUEn28BC8QauKJuMX5rA'),(980,'wx00f7d56d549f82ce',20160909144258,'component_verify_ticket','ticket@@@tJbBZqYRsoB3evblK08Pd-28WDsL-oGKTxNhQH9QfZRirKd8H58t5qwtM2Q4aWFlamuUEn28BC8QauKJuMX5rA'),(981,'wx00f7d56d549f82ce',20160909145321,'component_verify_ticket','ticket@@@tJbBZqYRsoB3evblK08Pd-28WDsL-oGKTxNhQH9QfZRirKd8H58t5qwtM2Q4aWFlamuUEn28BC8QauKJuMX5rA'),(982,'wx00f7d56d549f82ce',20160909150301,'component_verify_ticket','ticket@@@aM-xKtWixnJG5zs14Rc2YY-yBLuO0KFlRDcnzgZFNeXhiJ1DJlV4va1EbKnI1RteusQFJKZhaUTMQyocOIQfvQ'),(983,'wx00f7d56d549f82ce',20160909151305,'component_verify_ticket','ticket@@@aM-xKtWixnJG5zs14Rc2YY-yBLuO0KFlRDcnzgZFNeXhiJ1DJlV4va1EbKnI1RteusQFJKZhaUTMQyocOIQfvQ'),(984,'wx00f7d56d549f82ce',20160909152259,'component_verify_ticket','ticket@@@aM-xKtWixnJG5zs14Rc2YY-yBLuO0KFlRDcnzgZFNeXhiJ1DJlV4va1EbKnI1RteusQFJKZhaUTMQyocOIQfvQ'),(985,'wx00f7d56d549f82ce',20160909153251,'component_verify_ticket','ticket@@@aM-xKtWixnJG5zs14Rc2YY-yBLuO0KFlRDcnzgZFNeXhiJ1DJlV4va1EbKnI1RteusQFJKZhaUTMQyocOIQfvQ'),(986,'wx00f7d56d549f82ce',20160909154301,'component_verify_ticket','ticket@@@aM-xKtWixnJG5zs14Rc2YY-yBLuO0KFlRDcnzgZFNeXhiJ1DJlV4va1EbKnI1RteusQFJKZhaUTMQyocOIQfvQ'),(987,'wx00f7d56d549f82ce',20160909155254,'component_verify_ticket','ticket@@@aM-xKtWixnJG5zs14Rc2YY-yBLuO0KFlRDcnzgZFNeXhiJ1DJlV4va1EbKnI1RteusQFJKZhaUTMQyocOIQfvQ'),(988,'wx00f7d56d549f82ce',20160909160249,'component_verify_ticket','ticket@@@1hMrDUO1-1jAh7GpdPizL0-6ih8m0Zb-goLK_PNx8pjX-hGXbDIIiVyYQMmMn1ZEBkqE5MmdYfS3vs313LSpcw'),(989,'wx00f7d56d549f82ce',20160909161302,'component_verify_ticket','ticket@@@1hMrDUO1-1jAh7GpdPizL0-6ih8m0Zb-goLK_PNx8pjX-hGXbDIIiVyYQMmMn1ZEBkqE5MmdYfS3vs313LSpcw'),(990,'wx00f7d56d549f82ce',20160909162302,'component_verify_ticket','ticket@@@1hMrDUO1-1jAh7GpdPizL0-6ih8m0Zb-goLK_PNx8pjX-hGXbDIIiVyYQMmMn1ZEBkqE5MmdYfS3vs313LSpcw'),(991,'wx00f7d56d549f82ce',20160909163307,'component_verify_ticket','ticket@@@1hMrDUO1-1jAh7GpdPizL0-6ih8m0Zb-goLK_PNx8pjX-hGXbDIIiVyYQMmMn1ZEBkqE5MmdYfS3vs313LSpcw'),(992,'wx00f7d56d549f82ce',20160909164301,'component_verify_ticket','ticket@@@1hMrDUO1-1jAh7GpdPizL0-6ih8m0Zb-goLK_PNx8pjX-hGXbDIIiVyYQMmMn1ZEBkqE5MmdYfS3vs313LSpcw'),(993,'wx00f7d56d549f82ce',20160909165304,'component_verify_ticket','ticket@@@1hMrDUO1-1jAh7GpdPizL0-6ih8m0Zb-goLK_PNx8pjX-hGXbDIIiVyYQMmMn1ZEBkqE5MmdYfS3vs313LSpcw'),(994,'wx00f7d56d549f82ce',20160909170307,'component_verify_ticket','ticket@@@fyT14-syu1eTR1ky4rrfd2G1je17KF-t7pVp75j70sMaCs19L_7RsRWVbrUsKgFbIAuTk4_qPpgBHy8es_Z5fw'),(995,'wx00f7d56d549f82ce',20160909171259,'component_verify_ticket','ticket@@@fyT14-syu1eTR1ky4rrfd2G1je17KF-t7pVp75j70sMaCs19L_7RsRWVbrUsKgFbIAuTk4_qPpgBHy8es_Z5fw'),(996,'wx00f7d56d549f82ce',20160909172307,'component_verify_ticket','ticket@@@fyT14-syu1eTR1ky4rrfd2G1je17KF-t7pVp75j70sMaCs19L_7RsRWVbrUsKgFbIAuTk4_qPpgBHy8es_Z5fw'),(997,'wx00f7d56d549f82ce',20160909173313,'component_verify_ticket','ticket@@@fyT14-syu1eTR1ky4rrfd2G1je17KF-t7pVp75j70sMaCs19L_7RsRWVbrUsKgFbIAuTk4_qPpgBHy8es_Z5fw'),(998,'wx00f7d56d549f82ce',20160909174315,'component_verify_ticket','ticket@@@fyT14-syu1eTR1ky4rrfd2G1je17KF-t7pVp75j70sMaCs19L_7RsRWVbrUsKgFbIAuTk4_qPpgBHy8es_Z5fw'),(999,'wx00f7d56d549f82ce',20160909175308,'component_verify_ticket','ticket@@@fyT14-syu1eTR1ky4rrfd2G1je17KF-t7pVp75j70sMaCs19L_7RsRWVbrUsKgFbIAuTk4_qPpgBHy8es_Z5fw'),(1000,'wx00f7d56d549f82ce',20160909180314,'component_verify_ticket','ticket@@@SRChaTFVAsdiANvBEXWSRlAzZLyeb6PWkQ6ByPcPCDmYGgF6OHxbNm3m9rySUJCz8lmLpSqolZOqzKwpu3oxKg'),(1001,'wx00f7d56d549f82ce',20160909181307,'component_verify_ticket','ticket@@@SRChaTFVAsdiANvBEXWSRlAzZLyeb6PWkQ6ByPcPCDmYGgF6OHxbNm3m9rySUJCz8lmLpSqolZOqzKwpu3oxKg'),(1002,'wx00f7d56d549f82ce',20160909182308,'component_verify_ticket','ticket@@@SRChaTFVAsdiANvBEXWSRlAzZLyeb6PWkQ6ByPcPCDmYGgF6OHxbNm3m9rySUJCz8lmLpSqolZOqzKwpu3oxKg'),(1003,'wx00f7d56d549f82ce',20160909183306,'component_verify_ticket','ticket@@@SRChaTFVAsdiANvBEXWSRlAzZLyeb6PWkQ6ByPcPCDmYGgF6OHxbNm3m9rySUJCz8lmLpSqolZOqzKwpu3oxKg'),(1004,'wx00f7d56d549f82ce',20160909184303,'component_verify_ticket','ticket@@@SRChaTFVAsdiANvBEXWSRlAzZLyeb6PWkQ6ByPcPCDmYGgF6OHxbNm3m9rySUJCz8lmLpSqolZOqzKwpu3oxKg'),(1005,'wx00f7d56d549f82ce',20160909185305,'component_verify_ticket','ticket@@@SRChaTFVAsdiANvBEXWSRlAzZLyeb6PWkQ6ByPcPCDmYGgF6OHxbNm3m9rySUJCz8lmLpSqolZOqzKwpu3oxKg'),(1006,'wx00f7d56d549f82ce',20160909190312,'component_verify_ticket','ticket@@@S7bgs3T2RMsFqGWrkWX2pZoqbiP8SZxSzmutA9U0CotG0uAv5jnKSlpKlMXNEXxpzuH3DVmKgQz3uhOq9R8qNA'),(1007,'wx00f7d56d549f82ce',20160909191308,'component_verify_ticket','ticket@@@S7bgs3T2RMsFqGWrkWX2pZoqbiP8SZxSzmutA9U0CotG0uAv5jnKSlpKlMXNEXxpzuH3DVmKgQz3uhOq9R8qNA'),(1008,'wx00f7d56d549f82ce',20160909192311,'component_verify_ticket','ticket@@@S7bgs3T2RMsFqGWrkWX2pZoqbiP8SZxSzmutA9U0CotG0uAv5jnKSlpKlMXNEXxpzuH3DVmKgQz3uhOq9R8qNA'),(1009,'wx00f7d56d549f82ce',20160909193309,'component_verify_ticket','ticket@@@S7bgs3T2RMsFqGWrkWX2pZoqbiP8SZxSzmutA9U0CotG0uAv5jnKSlpKlMXNEXxpzuH3DVmKgQz3uhOq9R8qNA'),(1010,'wx00f7d56d549f82ce',20160909194302,'component_verify_ticket','ticket@@@S7bgs3T2RMsFqGWrkWX2pZoqbiP8SZxSzmutA9U0CotG0uAv5jnKSlpKlMXNEXxpzuH3DVmKgQz3uhOq9R8qNA'),(1011,'wx00f7d56d549f82ce',20160909195304,'component_verify_ticket','ticket@@@S7bgs3T2RMsFqGWrkWX2pZoqbiP8SZxSzmutA9U0CotG0uAv5jnKSlpKlMXNEXxpzuH3DVmKgQz3uhOq9R8qNA'),(1012,'wx00f7d56d549f82ce',20160909200315,'component_verify_ticket','ticket@@@gUAtWvNip-8WXNXcMbwz4-gKDWleD-K0oLKRUV0CSb_N9LX8ZAQlAFZKcsTT4CDOk3BeOAJXmDXFoW_3shMpPg'),(1013,'wx00f7d56d549f82ce',20160909201309,'component_verify_ticket','ticket@@@gUAtWvNip-8WXNXcMbwz4-gKDWleD-K0oLKRUV0CSb_N9LX8ZAQlAFZKcsTT4CDOk3BeOAJXmDXFoW_3shMpPg'),(1014,'wx00f7d56d549f82ce',20160909202311,'component_verify_ticket','ticket@@@gUAtWvNip-8WXNXcMbwz4-gKDWleD-K0oLKRUV0CSb_N9LX8ZAQlAFZKcsTT4CDOk3BeOAJXmDXFoW_3shMpPg'),(1015,'wx00f7d56d549f82ce',20160909203259,'component_verify_ticket','ticket@@@gUAtWvNip-8WXNXcMbwz4-gKDWleD-K0oLKRUV0CSb_N9LX8ZAQlAFZKcsTT4CDOk3BeOAJXmDXFoW_3shMpPg'),(1016,'wx00f7d56d549f82ce',20160909204309,'component_verify_ticket','ticket@@@gUAtWvNip-8WXNXcMbwz4-gKDWleD-K0oLKRUV0CSb_N9LX8ZAQlAFZKcsTT4CDOk3BeOAJXmDXFoW_3shMpPg'),(1017,'wx00f7d56d549f82ce',20160909205304,'component_verify_ticket','ticket@@@gUAtWvNip-8WXNXcMbwz4-gKDWleD-K0oLKRUV0CSb_N9LX8ZAQlAFZKcsTT4CDOk3BeOAJXmDXFoW_3shMpPg'),(1018,'wx00f7d56d549f82ce',20160909210317,'component_verify_ticket','ticket@@@lCesJYDCYmmYnH9Wgh5pfzUrPJ3g_eTv8tG8tyGCzpfdB8GPI7O9NyW6Yqn1Ju93cKpqJ2wV90Y5wqbjEK_K4w'),(1019,'wx00f7d56d549f82ce',20160909211309,'component_verify_ticket','ticket@@@lCesJYDCYmmYnH9Wgh5pfzUrPJ3g_eTv8tG8tyGCzpfdB8GPI7O9NyW6Yqn1Ju93cKpqJ2wV90Y5wqbjEK_K4w'),(1020,'wx00f7d56d549f82ce',20160909212309,'component_verify_ticket','ticket@@@lCesJYDCYmmYnH9Wgh5pfzUrPJ3g_eTv8tG8tyGCzpfdB8GPI7O9NyW6Yqn1Ju93cKpqJ2wV90Y5wqbjEK_K4w'),(1021,'wx00f7d56d549f82ce',20160909213306,'component_verify_ticket','ticket@@@lCesJYDCYmmYnH9Wgh5pfzUrPJ3g_eTv8tG8tyGCzpfdB8GPI7O9NyW6Yqn1Ju93cKpqJ2wV90Y5wqbjEK_K4w'),(1022,'wx00f7d56d549f82ce',20160909214307,'component_verify_ticket','ticket@@@lCesJYDCYmmYnH9Wgh5pfzUrPJ3g_eTv8tG8tyGCzpfdB8GPI7O9NyW6Yqn1Ju93cKpqJ2wV90Y5wqbjEK_K4w'),(1023,'wx00f7d56d549f82ce',20160909215304,'component_verify_ticket','ticket@@@lCesJYDCYmmYnH9Wgh5pfzUrPJ3g_eTv8tG8tyGCzpfdB8GPI7O9NyW6Yqn1Ju93cKpqJ2wV90Y5wqbjEK_K4w'),(1024,'wx00f7d56d549f82ce',20160909220313,'component_verify_ticket','ticket@@@N4y_7CbThcK4zDdVb9h8zsBTCwMqcL_n5Cog1khWcWhPFogp5jrYK-5TWcKlDYjllOIzgJ6c9lOOumBsEI58Gw'),(1025,'wx00f7d56d549f82ce',20160909221311,'component_verify_ticket','ticket@@@N4y_7CbThcK4zDdVb9h8zsBTCwMqcL_n5Cog1khWcWhPFogp5jrYK-5TWcKlDYjllOIzgJ6c9lOOumBsEI58Gw'),(1026,'wx00f7d56d549f82ce',20160909222309,'component_verify_ticket','ticket@@@N4y_7CbThcK4zDdVb9h8zsBTCwMqcL_n5Cog1khWcWhPFogp5jrYK-5TWcKlDYjllOIzgJ6c9lOOumBsEI58Gw'),(1027,'wx00f7d56d549f82ce',20160909223303,'component_verify_ticket','ticket@@@N4y_7CbThcK4zDdVb9h8zsBTCwMqcL_n5Cog1khWcWhPFogp5jrYK-5TWcKlDYjllOIzgJ6c9lOOumBsEI58Gw'),(1028,'wx00f7d56d549f82ce',20160909224259,'component_verify_ticket','ticket@@@N4y_7CbThcK4zDdVb9h8zsBTCwMqcL_n5Cog1khWcWhPFogp5jrYK-5TWcKlDYjllOIzgJ6c9lOOumBsEI58Gw'),(1029,'wx00f7d56d549f82ce',20160909225249,'component_verify_ticket','ticket@@@N4y_7CbThcK4zDdVb9h8zsBTCwMqcL_n5Cog1khWcWhPFogp5jrYK-5TWcKlDYjllOIzgJ6c9lOOumBsEI58Gw'),(1030,'wx00f7d56d549f82ce',20160909230304,'component_verify_ticket','ticket@@@rKBhIeItyPWy5NNTw3YNhkdaRmtVFoQIG6tE34va2PDhiycA2p4GIKhrgGAKbOpNdoGXkB2JHvb2zPJ7AyIFrQ'),(1031,'wx00f7d56d549f82ce',20160909231311,'component_verify_ticket','ticket@@@rKBhIeItyPWy5NNTw3YNhkdaRmtVFoQIG6tE34va2PDhiycA2p4GIKhrgGAKbOpNdoGXkB2JHvb2zPJ7AyIFrQ'),(1032,'wx00f7d56d549f82ce',20160909232313,'component_verify_ticket','ticket@@@rKBhIeItyPWy5NNTw3YNhkdaRmtVFoQIG6tE34va2PDhiycA2p4GIKhrgGAKbOpNdoGXkB2JHvb2zPJ7AyIFrQ'),(1033,'wx00f7d56d549f82ce',20160909233315,'component_verify_ticket','ticket@@@rKBhIeItyPWy5NNTw3YNhkdaRmtVFoQIG6tE34va2PDhiycA2p4GIKhrgGAKbOpNdoGXkB2JHvb2zPJ7AyIFrQ'),(1034,'wx00f7d56d549f82ce',20160909234311,'component_verify_ticket','ticket@@@rKBhIeItyPWy5NNTw3YNhkdaRmtVFoQIG6tE34va2PDhiycA2p4GIKhrgGAKbOpNdoGXkB2JHvb2zPJ7AyIFrQ'),(1035,'wx00f7d56d549f82ce',20160909235312,'component_verify_ticket','ticket@@@rKBhIeItyPWy5NNTw3YNhkdaRmtVFoQIG6tE34va2PDhiycA2p4GIKhrgGAKbOpNdoGXkB2JHvb2zPJ7AyIFrQ'),(1036,'wx00f7d56d549f82ce',20160910000301,'component_verify_ticket','ticket@@@EvImR_jaIVqg5pR2lzwjK7T9Y1_YqjIPXGcrnM7x2G7vbpbpER_0m3f7m7UO0YHY4vG2zf7hgUeLbbZrQgMWEA'),(1037,'wx00f7d56d549f82ce',20160910001311,'component_verify_ticket','ticket@@@EvImR_jaIVqg5pR2lzwjK7T9Y1_YqjIPXGcrnM7x2G7vbpbpER_0m3f7m7UO0YHY4vG2zf7hgUeLbbZrQgMWEA'),(1038,'wx00f7d56d549f82ce',20160910002310,'component_verify_ticket','ticket@@@EvImR_jaIVqg5pR2lzwjK7T9Y1_YqjIPXGcrnM7x2G7vbpbpER_0m3f7m7UO0YHY4vG2zf7hgUeLbbZrQgMWEA'),(1039,'wx00f7d56d549f82ce',20160910003251,'component_verify_ticket','ticket@@@EvImR_jaIVqg5pR2lzwjK7T9Y1_YqjIPXGcrnM7x2G7vbpbpER_0m3f7m7UO0YHY4vG2zf7hgUeLbbZrQgMWEA'),(1040,'wx00f7d56d549f82ce',20160910004258,'component_verify_ticket','ticket@@@EvImR_jaIVqg5pR2lzwjK7T9Y1_YqjIPXGcrnM7x2G7vbpbpER_0m3f7m7UO0YHY4vG2zf7hgUeLbbZrQgMWEA'),(1041,'wx00f7d56d549f82ce',20160910005307,'component_verify_ticket','ticket@@@EvImR_jaIVqg5pR2lzwjK7T9Y1_YqjIPXGcrnM7x2G7vbpbpER_0m3f7m7UO0YHY4vG2zf7hgUeLbbZrQgMWEA'),(1042,'wx00f7d56d549f82ce',20160910010307,'component_verify_ticket','ticket@@@4-DXa3gm7CTLBPwtcHqfxDmg4HHVMVOFQW9TEjumydqBV_h40xaNGWfEuoWgpXS81melIkRv2SXKFWiATfAO4g'),(1043,'wx00f7d56d549f82ce',20160910011259,'component_verify_ticket','ticket@@@4-DXa3gm7CTLBPwtcHqfxDmg4HHVMVOFQW9TEjumydqBV_h40xaNGWfEuoWgpXS81melIkRv2SXKFWiATfAO4g'),(1044,'wx00f7d56d549f82ce',20160910012257,'component_verify_ticket','ticket@@@4-DXa3gm7CTLBPwtcHqfxDmg4HHVMVOFQW9TEjumydqBV_h40xaNGWfEuoWgpXS81melIkRv2SXKFWiATfAO4g'),(1045,'wx00f7d56d549f82ce',20160910013255,'component_verify_ticket','ticket@@@4-DXa3gm7CTLBPwtcHqfxDmg4HHVMVOFQW9TEjumydqBV_h40xaNGWfEuoWgpXS81melIkRv2SXKFWiATfAO4g'),(1046,'wx00f7d56d549f82ce',20160910014255,'component_verify_ticket','ticket@@@4-DXa3gm7CTLBPwtcHqfxDmg4HHVMVOFQW9TEjumydqBV_h40xaNGWfEuoWgpXS81melIkRv2SXKFWiATfAO4g'),(1047,'wx00f7d56d549f82ce',20160910015254,'component_verify_ticket','ticket@@@4-DXa3gm7CTLBPwtcHqfxDmg4HHVMVOFQW9TEjumydqBV_h40xaNGWfEuoWgpXS81melIkRv2SXKFWiATfAO4g'),(1048,'wx00f7d56d549f82ce',20160910020257,'component_verify_ticket','ticket@@@UCVgNn6jzdxeI04wEhYvo3xzuLjfF-GFzpLQqomLtHBbX3A_LMjatRbDTi1I2rXUzufDChmuFdtOwfHFSVVKZg'),(1049,'wx00f7d56d549f82ce',20160910021257,'component_verify_ticket','ticket@@@UCVgNn6jzdxeI04wEhYvo3xzuLjfF-GFzpLQqomLtHBbX3A_LMjatRbDTi1I2rXUzufDChmuFdtOwfHFSVVKZg'),(1050,'wx00f7d56d549f82ce',20160910022257,'component_verify_ticket','ticket@@@UCVgNn6jzdxeI04wEhYvo3xzuLjfF-GFzpLQqomLtHBbX3A_LMjatRbDTi1I2rXUzufDChmuFdtOwfHFSVVKZg'),(1051,'wx00f7d56d549f82ce',20160910023257,'component_verify_ticket','ticket@@@UCVgNn6jzdxeI04wEhYvo3xzuLjfF-GFzpLQqomLtHBbX3A_LMjatRbDTi1I2rXUzufDChmuFdtOwfHFSVVKZg'),(1052,'wx00f7d56d549f82ce',20160910024258,'component_verify_ticket','ticket@@@UCVgNn6jzdxeI04wEhYvo3xzuLjfF-GFzpLQqomLtHBbX3A_LMjatRbDTi1I2rXUzufDChmuFdtOwfHFSVVKZg'),(1053,'wx00f7d56d549f82ce',20160910025255,'component_verify_ticket','ticket@@@UCVgNn6jzdxeI04wEhYvo3xzuLjfF-GFzpLQqomLtHBbX3A_LMjatRbDTi1I2rXUzufDChmuFdtOwfHFSVVKZg'),(1054,'wx00f7d56d549f82ce',20160910030258,'component_verify_ticket','ticket@@@8hYCtV6r1WPOpJ0HCTd9M7TAKCtW9fbus1KRe3VCHRJXuPJZ_fwYUDlrjXDXIFAcUM3ZwxMwl36q-xVpiJDDAA'),(1055,'wx00f7d56d549f82ce',20160910031253,'component_verify_ticket','ticket@@@8hYCtV6r1WPOpJ0HCTd9M7TAKCtW9fbus1KRe3VCHRJXuPJZ_fwYUDlrjXDXIFAcUM3ZwxMwl36q-xVpiJDDAA'),(1056,'wx00f7d56d549f82ce',20160910032251,'component_verify_ticket','ticket@@@8hYCtV6r1WPOpJ0HCTd9M7TAKCtW9fbus1KRe3VCHRJXuPJZ_fwYUDlrjXDXIFAcUM3ZwxMwl36q-xVpiJDDAA'),(1057,'wx00f7d56d549f82ce',20160910033258,'component_verify_ticket','ticket@@@8hYCtV6r1WPOpJ0HCTd9M7TAKCtW9fbus1KRe3VCHRJXuPJZ_fwYUDlrjXDXIFAcUM3ZwxMwl36q-xVpiJDDAA'),(1058,'wx00f7d56d549f82ce',20160910034258,'component_verify_ticket','ticket@@@8hYCtV6r1WPOpJ0HCTd9M7TAKCtW9fbus1KRe3VCHRJXuPJZ_fwYUDlrjXDXIFAcUM3ZwxMwl36q-xVpiJDDAA'),(1059,'wx00f7d56d549f82ce',20160910035302,'component_verify_ticket','ticket@@@8hYCtV6r1WPOpJ0HCTd9M7TAKCtW9fbus1KRe3VCHRJXuPJZ_fwYUDlrjXDXIFAcUM3ZwxMwl36q-xVpiJDDAA'),(1060,'wx00f7d56d549f82ce',20160910040300,'component_verify_ticket','ticket@@@ekcuvdfuUdr4vcgj-ng7bz4uNuJVyy_KwGrR-rYMhaA5Aj6kMVyhJ-K_1LCLWWCKgg0gAzbmUr46X0Z3OPiJbA'),(1061,'wx00f7d56d549f82ce',20160910041303,'component_verify_ticket','ticket@@@ekcuvdfuUdr4vcgj-ng7bz4uNuJVyy_KwGrR-rYMhaA5Aj6kMVyhJ-K_1LCLWWCKgg0gAzbmUr46X0Z3OPiJbA'),(1062,'wx00f7d56d549f82ce',20160910042259,'component_verify_ticket','ticket@@@ekcuvdfuUdr4vcgj-ng7bz4uNuJVyy_KwGrR-rYMhaA5Aj6kMVyhJ-K_1LCLWWCKgg0gAzbmUr46X0Z3OPiJbA'),(1063,'wx00f7d56d549f82ce',20160910043256,'component_verify_ticket','ticket@@@ekcuvdfuUdr4vcgj-ng7bz4uNuJVyy_KwGrR-rYMhaA5Aj6kMVyhJ-K_1LCLWWCKgg0gAzbmUr46X0Z3OPiJbA'),(1064,'wx00f7d56d549f82ce',20160910044301,'component_verify_ticket','ticket@@@ekcuvdfuUdr4vcgj-ng7bz4uNuJVyy_KwGrR-rYMhaA5Aj6kMVyhJ-K_1LCLWWCKgg0gAzbmUr46X0Z3OPiJbA'),(1065,'wx00f7d56d549f82ce',20160910045249,'component_verify_ticket','ticket@@@ekcuvdfuUdr4vcgj-ng7bz4uNuJVyy_KwGrR-rYMhaA5Aj6kMVyhJ-K_1LCLWWCKgg0gAzbmUr46X0Z3OPiJbA'),(1066,'wx00f7d56d549f82ce',20160910050247,'component_verify_ticket','ticket@@@LZC1T7SniEQs6ku_ZfGs7-PquRzjOLIROtO78Unc1wCIBARrV-gaDrycOdQx24LPVglqJltWUSRohB8obtXdFg'),(1067,'wx00f7d56d549f82ce',20160910051251,'component_verify_ticket','ticket@@@LZC1T7SniEQs6ku_ZfGs7-PquRzjOLIROtO78Unc1wCIBARrV-gaDrycOdQx24LPVglqJltWUSRohB8obtXdFg'),(1068,'wx00f7d56d549f82ce',20160910052251,'component_verify_ticket','ticket@@@LZC1T7SniEQs6ku_ZfGs7-PquRzjOLIROtO78Unc1wCIBARrV-gaDrycOdQx24LPVglqJltWUSRohB8obtXdFg'),(1069,'wx00f7d56d549f82ce',20160910053250,'component_verify_ticket','ticket@@@LZC1T7SniEQs6ku_ZfGs7-PquRzjOLIROtO78Unc1wCIBARrV-gaDrycOdQx24LPVglqJltWUSRohB8obtXdFg'),(1070,'wx00f7d56d549f82ce',20160910054240,'component_verify_ticket','ticket@@@LZC1T7SniEQs6ku_ZfGs7-PquRzjOLIROtO78Unc1wCIBARrV-gaDrycOdQx24LPVglqJltWUSRohB8obtXdFg'),(1071,'wx00f7d56d549f82ce',20160910055256,'component_verify_ticket','ticket@@@LZC1T7SniEQs6ku_ZfGs7-PquRzjOLIROtO78Unc1wCIBARrV-gaDrycOdQx24LPVglqJltWUSRohB8obtXdFg'),(1072,'wx00f7d56d549f82ce',20160910060247,'component_verify_ticket','ticket@@@NhpdsXRdlq2Qd-CqPQi9MaDrpeVLS2k1nLxq3rJyI_mTiCpZf1mGIEnG71dq1ZRsWDZ2_EjV_NHwKaShFC7hLQ'),(1073,'wx00f7d56d549f82ce',20160910061253,'component_verify_ticket','ticket@@@NhpdsXRdlq2Qd-CqPQi9MaDrpeVLS2k1nLxq3rJyI_mTiCpZf1mGIEnG71dq1ZRsWDZ2_EjV_NHwKaShFC7hLQ'),(1074,'wx00f7d56d549f82ce',20160910062255,'component_verify_ticket','ticket@@@NhpdsXRdlq2Qd-CqPQi9MaDrpeVLS2k1nLxq3rJyI_mTiCpZf1mGIEnG71dq1ZRsWDZ2_EjV_NHwKaShFC7hLQ'),(1075,'wx00f7d56d549f82ce',20160910063252,'component_verify_ticket','ticket@@@NhpdsXRdlq2Qd-CqPQi9MaDrpeVLS2k1nLxq3rJyI_mTiCpZf1mGIEnG71dq1ZRsWDZ2_EjV_NHwKaShFC7hLQ'),(1076,'wx00f7d56d549f82ce',20160910064251,'component_verify_ticket','ticket@@@NhpdsXRdlq2Qd-CqPQi9MaDrpeVLS2k1nLxq3rJyI_mTiCpZf1mGIEnG71dq1ZRsWDZ2_EjV_NHwKaShFC7hLQ'),(1077,'wx00f7d56d549f82ce',20160910065250,'component_verify_ticket','ticket@@@NhpdsXRdlq2Qd-CqPQi9MaDrpeVLS2k1nLxq3rJyI_mTiCpZf1mGIEnG71dq1ZRsWDZ2_EjV_NHwKaShFC7hLQ'),(1078,'wx00f7d56d549f82ce',20160910070256,'component_verify_ticket','ticket@@@j5EgzKF8ZVfczp80TscUD8g2bSLiCmDq0qrdt9I0LNDaBugLV9fe6B1tlYYCpxCDlaKG_ysf7i-b3sIFo5kBPQ'),(1079,'wx00f7d56d549f82ce',20160910071256,'component_verify_ticket','ticket@@@j5EgzKF8ZVfczp80TscUD8g2bSLiCmDq0qrdt9I0LNDaBugLV9fe6B1tlYYCpxCDlaKG_ysf7i-b3sIFo5kBPQ'),(1080,'wx00f7d56d549f82ce',20160910072256,'component_verify_ticket','ticket@@@j5EgzKF8ZVfczp80TscUD8g2bSLiCmDq0qrdt9I0LNDaBugLV9fe6B1tlYYCpxCDlaKG_ysf7i-b3sIFo5kBPQ'),(1081,'wx00f7d56d549f82ce',20160910073257,'component_verify_ticket','ticket@@@j5EgzKF8ZVfczp80TscUD8g2bSLiCmDq0qrdt9I0LNDaBugLV9fe6B1tlYYCpxCDlaKG_ysf7i-b3sIFo5kBPQ'),(1082,'wx00f7d56d549f82ce',20160910074253,'component_verify_ticket','ticket@@@j5EgzKF8ZVfczp80TscUD8g2bSLiCmDq0qrdt9I0LNDaBugLV9fe6B1tlYYCpxCDlaKG_ysf7i-b3sIFo5kBPQ'),(1083,'wx00f7d56d549f82ce',20160910075256,'component_verify_ticket','ticket@@@j5EgzKF8ZVfczp80TscUD8g2bSLiCmDq0qrdt9I0LNDaBugLV9fe6B1tlYYCpxCDlaKG_ysf7i-b3sIFo5kBPQ'),(1084,'wx00f7d56d549f82ce',20160910080318,'component_verify_ticket','ticket@@@_ojLjV9C1WXQ2RdqMGRuOtuCJaQUb5bGpiyc9AyIZrBNnOMTm6h7udQ2r49KJjx4jQr5G_Ti_c55YC6wtMnTnA'),(1085,'wx00f7d56d549f82ce',20160910081258,'component_verify_ticket','ticket@@@_ojLjV9C1WXQ2RdqMGRuOtuCJaQUb5bGpiyc9AyIZrBNnOMTm6h7udQ2r49KJjx4jQr5G_Ti_c55YC6wtMnTnA'),(1086,'wx00f7d56d549f82ce',20160910082257,'component_verify_ticket','ticket@@@_ojLjV9C1WXQ2RdqMGRuOtuCJaQUb5bGpiyc9AyIZrBNnOMTm6h7udQ2r49KJjx4jQr5G_Ti_c55YC6wtMnTnA'),(1087,'wx00f7d56d549f82ce',20160910083258,'component_verify_ticket','ticket@@@_ojLjV9C1WXQ2RdqMGRuOtuCJaQUb5bGpiyc9AyIZrBNnOMTm6h7udQ2r49KJjx4jQr5G_Ti_c55YC6wtMnTnA'),(1088,'wx00f7d56d549f82ce',20160910084259,'component_verify_ticket','ticket@@@_ojLjV9C1WXQ2RdqMGRuOtuCJaQUb5bGpiyc9AyIZrBNnOMTm6h7udQ2r49KJjx4jQr5G_Ti_c55YC6wtMnTnA'),(1089,'wx00f7d56d549f82ce',20160910085301,'component_verify_ticket','ticket@@@_ojLjV9C1WXQ2RdqMGRuOtuCJaQUb5bGpiyc9AyIZrBNnOMTm6h7udQ2r49KJjx4jQr5G_Ti_c55YC6wtMnTnA'),(1090,'wx00f7d56d549f82ce',20160910090304,'component_verify_ticket','ticket@@@cIUSEMEGJyNE5mtaYfLXrf5_sVBuGZY1HeW15zyre4D_mWWqS0UdTmnk5Gv-IyuOyzyuAgNyYQtlq81Pu0CA0A'),(1091,'wx00f7d56d549f82ce',20160910091300,'component_verify_ticket','ticket@@@cIUSEMEGJyNE5mtaYfLXrf5_sVBuGZY1HeW15zyre4D_mWWqS0UdTmnk5Gv-IyuOyzyuAgNyYQtlq81Pu0CA0A'),(1092,'wx00f7d56d549f82ce',20160910092301,'component_verify_ticket','ticket@@@cIUSEMEGJyNE5mtaYfLXrf5_sVBuGZY1HeW15zyre4D_mWWqS0UdTmnk5Gv-IyuOyzyuAgNyYQtlq81Pu0CA0A'),(1093,'wx00f7d56d549f82ce',20160910093300,'component_verify_ticket','ticket@@@cIUSEMEGJyNE5mtaYfLXrf5_sVBuGZY1HeW15zyre4D_mWWqS0UdTmnk5Gv-IyuOyzyuAgNyYQtlq81Pu0CA0A'),(1094,'wx00f7d56d549f82ce',20160910094300,'component_verify_ticket','ticket@@@cIUSEMEGJyNE5mtaYfLXrf5_sVBuGZY1HeW15zyre4D_mWWqS0UdTmnk5Gv-IyuOyzyuAgNyYQtlq81Pu0CA0A'),(1095,'wx00f7d56d549f82ce',20160910095255,'component_verify_ticket','ticket@@@cIUSEMEGJyNE5mtaYfLXrf5_sVBuGZY1HeW15zyre4D_mWWqS0UdTmnk5Gv-IyuOyzyuAgNyYQtlq81Pu0CA0A'),(1096,'wx00f7d56d549f82ce',20160910100305,'component_verify_ticket','ticket@@@6XAuJsLM0-5M5am9J558H3i5RZCZvm5s6GsqGyn7D3I2yYLgmTF0ENZhnOUWZGbPu3DW_zqd-eIgXmVLMfniEQ'),(1097,'wx00f7d56d549f82ce',20160910101302,'component_verify_ticket','ticket@@@6XAuJsLM0-5M5am9J558H3i5RZCZvm5s6GsqGyn7D3I2yYLgmTF0ENZhnOUWZGbPu3DW_zqd-eIgXmVLMfniEQ'),(1098,'wx00f7d56d549f82ce',20160910102305,'component_verify_ticket','ticket@@@6XAuJsLM0-5M5am9J558H3i5RZCZvm5s6GsqGyn7D3I2yYLgmTF0ENZhnOUWZGbPu3DW_zqd-eIgXmVLMfniEQ'),(1099,'wx00f7d56d549f82ce',20160910103304,'component_verify_ticket','ticket@@@6XAuJsLM0-5M5am9J558H3i5RZCZvm5s6GsqGyn7D3I2yYLgmTF0ENZhnOUWZGbPu3DW_zqd-eIgXmVLMfniEQ'),(1100,'wx00f7d56d549f82ce',20160910104304,'component_verify_ticket','ticket@@@6XAuJsLM0-5M5am9J558H3i5RZCZvm5s6GsqGyn7D3I2yYLgmTF0ENZhnOUWZGbPu3DW_zqd-eIgXmVLMfniEQ'),(1101,'wx00f7d56d549f82ce',20160910105304,'component_verify_ticket','ticket@@@6XAuJsLM0-5M5am9J558H3i5RZCZvm5s6GsqGyn7D3I2yYLgmTF0ENZhnOUWZGbPu3DW_zqd-eIgXmVLMfniEQ'),(1102,'wx00f7d56d549f82ce',20160910110306,'component_verify_ticket','ticket@@@j-_aCVPbrEMYeZTWcBNF88G4iVQ28ycbRI7FiFICM2Osmon4Lz5sTiNc9Y0vR8Ya2N-QHeEiBAT3k9Q6FRqk-g'),(1103,'wx00f7d56d549f82ce',20160910111306,'component_verify_ticket','ticket@@@j-_aCVPbrEMYeZTWcBNF88G4iVQ28ycbRI7FiFICM2Osmon4Lz5sTiNc9Y0vR8Ya2N-QHeEiBAT3k9Q6FRqk-g'),(1104,'wx00f7d56d549f82ce',20160910112313,'component_verify_ticket','ticket@@@j-_aCVPbrEMYeZTWcBNF88G4iVQ28ycbRI7FiFICM2Osmon4Lz5sTiNc9Y0vR8Ya2N-QHeEiBAT3k9Q6FRqk-g'),(1105,'wx00f7d56d549f82ce',20160910113303,'component_verify_ticket','ticket@@@j-_aCVPbrEMYeZTWcBNF88G4iVQ28ycbRI7FiFICM2Osmon4Lz5sTiNc9Y0vR8Ya2N-QHeEiBAT3k9Q6FRqk-g'),(1106,'wx00f7d56d549f82ce',20160910114301,'component_verify_ticket','ticket@@@j-_aCVPbrEMYeZTWcBNF88G4iVQ28ycbRI7FiFICM2Osmon4Lz5sTiNc9Y0vR8Ya2N-QHeEiBAT3k9Q6FRqk-g'),(1107,'wx00f7d56d549f82ce',20160910115303,'component_verify_ticket','ticket@@@j-_aCVPbrEMYeZTWcBNF88G4iVQ28ycbRI7FiFICM2Osmon4Lz5sTiNc9Y0vR8Ya2N-QHeEiBAT3k9Q6FRqk-g'),(1108,'wx00f7d56d549f82ce',20160910120307,'component_verify_ticket','ticket@@@0MeooOZnZ7Ftr7u8nIAWhzbgxTrJaTmZ4AnFkIhHaItqNDSxmBKy4H0MGiaI-alVO8t8ses9oJ7YIUzXEIhNDg'),(1109,'wx00f7d56d549f82ce',20160910121302,'component_verify_ticket','ticket@@@0MeooOZnZ7Ftr7u8nIAWhzbgxTrJaTmZ4AnFkIhHaItqNDSxmBKy4H0MGiaI-alVO8t8ses9oJ7YIUzXEIhNDg'),(1110,'wx00f7d56d549f82ce',20160910122304,'component_verify_ticket','ticket@@@0MeooOZnZ7Ftr7u8nIAWhzbgxTrJaTmZ4AnFkIhHaItqNDSxmBKy4H0MGiaI-alVO8t8ses9oJ7YIUzXEIhNDg'),(1111,'wx00f7d56d549f82ce',20160910123304,'component_verify_ticket','ticket@@@0MeooOZnZ7Ftr7u8nIAWhzbgxTrJaTmZ4AnFkIhHaItqNDSxmBKy4H0MGiaI-alVO8t8ses9oJ7YIUzXEIhNDg'),(1112,'wx00f7d56d549f82ce',20160910124242,'component_verify_ticket','ticket@@@0MeooOZnZ7Ftr7u8nIAWhzbgxTrJaTmZ4AnFkIhHaItqNDSxmBKy4H0MGiaI-alVO8t8ses9oJ7YIUzXEIhNDg'),(1113,'wx00f7d56d549f82ce',20160910125300,'component_verify_ticket','ticket@@@0MeooOZnZ7Ftr7u8nIAWhzbgxTrJaTmZ4AnFkIhHaItqNDSxmBKy4H0MGiaI-alVO8t8ses9oJ7YIUzXEIhNDg'),(1114,'wx00f7d56d549f82ce',20160910130306,'component_verify_ticket','ticket@@@7iEHjQ1V14QZkNg6vx3h-TkpOinqZO5LoIIIxJDFRLo30vfjcEk1q77W-U4Sg2YvcvqIIw1JG0Fk4_lxDk3VSg'),(1115,'wx00f7d56d549f82ce',20160910131302,'component_verify_ticket','ticket@@@7iEHjQ1V14QZkNg6vx3h-TkpOinqZO5LoIIIxJDFRLo30vfjcEk1q77W-U4Sg2YvcvqIIw1JG0Fk4_lxDk3VSg'),(1116,'wx00f7d56d549f82ce',20160910132304,'component_verify_ticket','ticket@@@7iEHjQ1V14QZkNg6vx3h-TkpOinqZO5LoIIIxJDFRLo30vfjcEk1q77W-U4Sg2YvcvqIIw1JG0Fk4_lxDk3VSg'),(1117,'wx00f7d56d549f82ce',20160910133303,'component_verify_ticket','ticket@@@7iEHjQ1V14QZkNg6vx3h-TkpOinqZO5LoIIIxJDFRLo30vfjcEk1q77W-U4Sg2YvcvqIIw1JG0Fk4_lxDk3VSg'),(1118,'wx00f7d56d549f82ce',20160910134258,'component_verify_ticket','ticket@@@7iEHjQ1V14QZkNg6vx3h-TkpOinqZO5LoIIIxJDFRLo30vfjcEk1q77W-U4Sg2YvcvqIIw1JG0Fk4_lxDk3VSg'),(1119,'wx00f7d56d549f82ce',20160910135300,'component_verify_ticket','ticket@@@7iEHjQ1V14QZkNg6vx3h-TkpOinqZO5LoIIIxJDFRLo30vfjcEk1q77W-U4Sg2YvcvqIIw1JG0Fk4_lxDk3VSg'),(1120,'wx00f7d56d549f82ce',20160910140301,'component_verify_ticket','ticket@@@g3_F8M_z4TrHUiZQU45FJJCctYzYsKK0oz2BiZhQW4KsRlmDyZLlk-Vcn-Ltry0yglHP6BL4I39pqt8nJbopvw'),(1121,'wx00f7d56d549f82ce',20160910141302,'component_verify_ticket','ticket@@@g3_F8M_z4TrHUiZQU45FJJCctYzYsKK0oz2BiZhQW4KsRlmDyZLlk-Vcn-Ltry0yglHP6BL4I39pqt8nJbopvw'),(1122,'wx00f7d56d549f82ce',20160910142304,'component_verify_ticket','ticket@@@g3_F8M_z4TrHUiZQU45FJJCctYzYsKK0oz2BiZhQW4KsRlmDyZLlk-Vcn-Ltry0yglHP6BL4I39pqt8nJbopvw'),(1123,'wx00f7d56d549f82ce',20160910143301,'component_verify_ticket','ticket@@@g3_F8M_z4TrHUiZQU45FJJCctYzYsKK0oz2BiZhQW4KsRlmDyZLlk-Vcn-Ltry0yglHP6BL4I39pqt8nJbopvw'),(1124,'wx00f7d56d549f82ce',20160910144301,'component_verify_ticket','ticket@@@g3_F8M_z4TrHUiZQU45FJJCctYzYsKK0oz2BiZhQW4KsRlmDyZLlk-Vcn-Ltry0yglHP6BL4I39pqt8nJbopvw'),(1125,'wx00f7d56d549f82ce',20160910145300,'component_verify_ticket','ticket@@@g3_F8M_z4TrHUiZQU45FJJCctYzYsKK0oz2BiZhQW4KsRlmDyZLlk-Vcn-Ltry0yglHP6BL4I39pqt8nJbopvw'),(1126,'wx00f7d56d549f82ce',20160910150305,'component_verify_ticket','ticket@@@PEb2O_ZtFMqmKnjUHNf8STVTWr3TnagXH6mX_zj_LUQlmzQiTNRceXnb02SKTicoeKY0rprythwu0uKxmLh8UQ'),(1127,'wx00f7d56d549f82ce',20160910151302,'component_verify_ticket','ticket@@@PEb2O_ZtFMqmKnjUHNf8STVTWr3TnagXH6mX_zj_LUQlmzQiTNRceXnb02SKTicoeKY0rprythwu0uKxmLh8UQ'),(1128,'wx00f7d56d549f82ce',20160910152302,'component_verify_ticket','ticket@@@PEb2O_ZtFMqmKnjUHNf8STVTWr3TnagXH6mX_zj_LUQlmzQiTNRceXnb02SKTicoeKY0rprythwu0uKxmLh8UQ'),(1129,'wx00f7d56d549f82ce',20160910153302,'component_verify_ticket','ticket@@@PEb2O_ZtFMqmKnjUHNf8STVTWr3TnagXH6mX_zj_LUQlmzQiTNRceXnb02SKTicoeKY0rprythwu0uKxmLh8UQ'),(1130,'wx00f7d56d549f82ce',20160910154259,'component_verify_ticket','ticket@@@PEb2O_ZtFMqmKnjUHNf8STVTWr3TnagXH6mX_zj_LUQlmzQiTNRceXnb02SKTicoeKY0rprythwu0uKxmLh8UQ'),(1131,'wx00f7d56d549f82ce',20160910155300,'component_verify_ticket','ticket@@@PEb2O_ZtFMqmKnjUHNf8STVTWr3TnagXH6mX_zj_LUQlmzQiTNRceXnb02SKTicoeKY0rprythwu0uKxmLh8UQ'),(1132,'wx00f7d56d549f82ce',20160910160305,'component_verify_ticket','ticket@@@GOvaMbEKZgh2K5Sbt2uxMabOKnRUCLLDWNfFTBz0tEhfWex0EC2H1onmPopCJ5i4sThRh_qlKQkjDkkdhH5EAw'),(1133,'wx00f7d56d549f82ce',20160910161303,'component_verify_ticket','ticket@@@GOvaMbEKZgh2K5Sbt2uxMabOKnRUCLLDWNfFTBz0tEhfWex0EC2H1onmPopCJ5i4sThRh_qlKQkjDkkdhH5EAw'),(1134,'wx00f7d56d549f82ce',20160910162307,'component_verify_ticket','ticket@@@GOvaMbEKZgh2K5Sbt2uxMabOKnRUCLLDWNfFTBz0tEhfWex0EC2H1onmPopCJ5i4sThRh_qlKQkjDkkdhH5EAw'),(1135,'wx00f7d56d549f82ce',20160910163252,'component_verify_ticket','ticket@@@GOvaMbEKZgh2K5Sbt2uxMabOKnRUCLLDWNfFTBz0tEhfWex0EC2H1onmPopCJ5i4sThRh_qlKQkjDkkdhH5EAw'),(1136,'wx00f7d56d549f82ce',20160910164300,'component_verify_ticket','ticket@@@GOvaMbEKZgh2K5Sbt2uxMabOKnRUCLLDWNfFTBz0tEhfWex0EC2H1onmPopCJ5i4sThRh_qlKQkjDkkdhH5EAw'),(1137,'wx00f7d56d549f82ce',20160910165302,'component_verify_ticket','ticket@@@GOvaMbEKZgh2K5Sbt2uxMabOKnRUCLLDWNfFTBz0tEhfWex0EC2H1onmPopCJ5i4sThRh_qlKQkjDkkdhH5EAw'),(1138,'wx00f7d56d549f82ce',20160910170304,'component_verify_ticket','ticket@@@63H0mKc0Ldo2_IIswqCOTBzkRdsiQqTI2JotIQdBXp4ScTBtHmqqTj_FjfVh1Wi2fLEhxowuV8V7bDIPQ97N6A'),(1139,'wx00f7d56d549f82ce',20160910171302,'component_verify_ticket','ticket@@@63H0mKc0Ldo2_IIswqCOTBzkRdsiQqTI2JotIQdBXp4ScTBtHmqqTj_FjfVh1Wi2fLEhxowuV8V7bDIPQ97N6A'),(1140,'wx00f7d56d549f82ce',20160910172300,'component_verify_ticket','ticket@@@63H0mKc0Ldo2_IIswqCOTBzkRdsiQqTI2JotIQdBXp4ScTBtHmqqTj_FjfVh1Wi2fLEhxowuV8V7bDIPQ97N6A'),(1141,'wx00f7d56d549f82ce',20160910173259,'component_verify_ticket','ticket@@@63H0mKc0Ldo2_IIswqCOTBzkRdsiQqTI2JotIQdBXp4ScTBtHmqqTj_FjfVh1Wi2fLEhxowuV8V7bDIPQ97N6A'),(1142,'wx00f7d56d549f82ce',20160910174300,'component_verify_ticket','ticket@@@63H0mKc0Ldo2_IIswqCOTBzkRdsiQqTI2JotIQdBXp4ScTBtHmqqTj_FjfVh1Wi2fLEhxowuV8V7bDIPQ97N6A'),(1143,'wx00f7d56d549f82ce',20160910175301,'component_verify_ticket','ticket@@@63H0mKc0Ldo2_IIswqCOTBzkRdsiQqTI2JotIQdBXp4ScTBtHmqqTj_FjfVh1Wi2fLEhxowuV8V7bDIPQ97N6A'),(1144,'wx00f7d56d549f82ce',20160910180307,'component_verify_ticket','ticket@@@vt4JDh6bXUwofX3wN9C2Cy7B-VZkUipiDmJTSsGCmOjbYiSuTDfV6OPwHWbC2afo83c7yAbuZErrlF1betpzPQ'),(1145,'wx00f7d56d549f82ce',20160910181300,'component_verify_ticket','ticket@@@vt4JDh6bXUwofX3wN9C2Cy7B-VZkUipiDmJTSsGCmOjbYiSuTDfV6OPwHWbC2afo83c7yAbuZErrlF1betpzPQ'),(1146,'wx00f7d56d549f82ce',20160910182300,'component_verify_ticket','ticket@@@vt4JDh6bXUwofX3wN9C2Cy7B-VZkUipiDmJTSsGCmOjbYiSuTDfV6OPwHWbC2afo83c7yAbuZErrlF1betpzPQ'),(1147,'wx00f7d56d549f82ce',20160910183246,'component_verify_ticket','ticket@@@vt4JDh6bXUwofX3wN9C2Cy7B-VZkUipiDmJTSsGCmOjbYiSuTDfV6OPwHWbC2afo83c7yAbuZErrlF1betpzPQ'),(1148,'wx00f7d56d549f82ce',20160910184302,'component_verify_ticket','ticket@@@vt4JDh6bXUwofX3wN9C2Cy7B-VZkUipiDmJTSsGCmOjbYiSuTDfV6OPwHWbC2afo83c7yAbuZErrlF1betpzPQ'),(1149,'wx00f7d56d549f82ce',20160910185300,'component_verify_ticket','ticket@@@vt4JDh6bXUwofX3wN9C2Cy7B-VZkUipiDmJTSsGCmOjbYiSuTDfV6OPwHWbC2afo83c7yAbuZErrlF1betpzPQ'),(1150,'wx00f7d56d549f82ce',20160910190311,'component_verify_ticket','ticket@@@U0Vs9e06hDEK0RRMXJifluVbx7BrD8uKNzpHQyLuvZZ87W0-P4Oirmz6ZWCQnOHx2RtkU4BpnzhBbnhUyKtTZg'),(1151,'wx00f7d56d549f82ce',20160910191301,'component_verify_ticket','ticket@@@U0Vs9e06hDEK0RRMXJifluVbx7BrD8uKNzpHQyLuvZZ87W0-P4Oirmz6ZWCQnOHx2RtkU4BpnzhBbnhUyKtTZg'),(1152,'wx00f7d56d549f82ce',20160910192303,'component_verify_ticket','ticket@@@U0Vs9e06hDEK0RRMXJifluVbx7BrD8uKNzpHQyLuvZZ87W0-P4Oirmz6ZWCQnOHx2RtkU4BpnzhBbnhUyKtTZg'),(1153,'wx00f7d56d549f82ce',20160910193305,'component_verify_ticket','ticket@@@U0Vs9e06hDEK0RRMXJifluVbx7BrD8uKNzpHQyLuvZZ87W0-P4Oirmz6ZWCQnOHx2RtkU4BpnzhBbnhUyKtTZg'),(1154,'wx00f7d56d549f82ce',20160910194300,'component_verify_ticket','ticket@@@U0Vs9e06hDEK0RRMXJifluVbx7BrD8uKNzpHQyLuvZZ87W0-P4Oirmz6ZWCQnOHx2RtkU4BpnzhBbnhUyKtTZg'),(1155,'wx00f7d56d549f82ce',20160910195300,'component_verify_ticket','ticket@@@U0Vs9e06hDEK0RRMXJifluVbx7BrD8uKNzpHQyLuvZZ87W0-P4Oirmz6ZWCQnOHx2RtkU4BpnzhBbnhUyKtTZg'),(1156,'wx00f7d56d549f82ce',20160910200310,'component_verify_ticket','ticket@@@OMRJwz__SLuZvPx_ahMdOBm2-UMlrHIkhPVlMwj_i-ynZlQnNp69RBDUeE1sOziMTBhYtASDOd1BsDyl9e0YEA'),(1157,'wx00f7d56d549f82ce',20160910201304,'component_verify_ticket','ticket@@@OMRJwz__SLuZvPx_ahMdOBm2-UMlrHIkhPVlMwj_i-ynZlQnNp69RBDUeE1sOziMTBhYtASDOd1BsDyl9e0YEA'),(1158,'wx00f7d56d549f82ce',20160910202308,'component_verify_ticket','ticket@@@OMRJwz__SLuZvPx_ahMdOBm2-UMlrHIkhPVlMwj_i-ynZlQnNp69RBDUeE1sOziMTBhYtASDOd1BsDyl9e0YEA'),(1159,'wx00f7d56d549f82ce',20160910203319,'component_verify_ticket','ticket@@@OMRJwz__SLuZvPx_ahMdOBm2-UMlrHIkhPVlMwj_i-ynZlQnNp69RBDUeE1sOziMTBhYtASDOd1BsDyl9e0YEA'),(1160,'wx00f7d56d549f82ce',20160910204309,'component_verify_ticket','ticket@@@OMRJwz__SLuZvPx_ahMdOBm2-UMlrHIkhPVlMwj_i-ynZlQnNp69RBDUeE1sOziMTBhYtASDOd1BsDyl9e0YEA'),(1161,'wx00f7d56d549f82ce',20160910205305,'component_verify_ticket','ticket@@@OMRJwz__SLuZvPx_ahMdOBm2-UMlrHIkhPVlMwj_i-ynZlQnNp69RBDUeE1sOziMTBhYtASDOd1BsDyl9e0YEA'),(1162,'wx00f7d56d549f82ce',20160910210502,'component_verify_ticket','ticket@@@TZyI_hAsF0gRr2mvSk_wgHH6oeBHcR5iCo-NvfC-F3BbSc7pfpQMqu8EQ4T5taRaYXCEkLL37wiGrjPwsZCshA'),(1163,'wx00f7d56d549f82ce',20160910211537,'component_verify_ticket','ticket@@@TZyI_hAsF0gRr2mvSk_wgHH6oeBHcR5iCo-NvfC-F3BbSc7pfpQMqu8EQ4T5taRaYXCEkLL37wiGrjPwsZCshA'),(1164,'wx00f7d56d549f82ce',20160910212351,'component_verify_ticket','ticket@@@TZyI_hAsF0gRr2mvSk_wgHH6oeBHcR5iCo-NvfC-F3BbSc7pfpQMqu8EQ4T5taRaYXCEkLL37wiGrjPwsZCshA'),(1165,'wx00f7d56d549f82ce',20160910213336,'component_verify_ticket','ticket@@@TZyI_hAsF0gRr2mvSk_wgHH6oeBHcR5iCo-NvfC-F3BbSc7pfpQMqu8EQ4T5taRaYXCEkLL37wiGrjPwsZCshA'),(1166,'wx00f7d56d549f82ce',20160910214331,'component_verify_ticket','ticket@@@TZyI_hAsF0gRr2mvSk_wgHH6oeBHcR5iCo-NvfC-F3BbSc7pfpQMqu8EQ4T5taRaYXCEkLL37wiGrjPwsZCshA'),(1167,'wx00f7d56d549f82ce',20160910215346,'component_verify_ticket','ticket@@@TZyI_hAsF0gRr2mvSk_wgHH6oeBHcR5iCo-NvfC-F3BbSc7pfpQMqu8EQ4T5taRaYXCEkLL37wiGrjPwsZCshA'),(1168,'wx00f7d56d549f82ce',20160910220430,'component_verify_ticket','ticket@@@ndBIPMTAA6wPqB_iyPKnRJWODIOvxwoPDWZjIpeng05dhR4p0NgVpl7ks5mLxozDlpZshdcnU4CrQKYMa1kYkw'),(1169,'wx00f7d56d549f82ce',20160910221423,'component_verify_ticket','ticket@@@ndBIPMTAA6wPqB_iyPKnRJWODIOvxwoPDWZjIpeng05dhR4p0NgVpl7ks5mLxozDlpZshdcnU4CrQKYMa1kYkw'),(1170,'wx00f7d56d549f82ce',20160910222403,'component_verify_ticket','ticket@@@ndBIPMTAA6wPqB_iyPKnRJWODIOvxwoPDWZjIpeng05dhR4p0NgVpl7ks5mLxozDlpZshdcnU4CrQKYMa1kYkw'),(1171,'wx00f7d56d549f82ce',20160910223319,'component_verify_ticket','ticket@@@ndBIPMTAA6wPqB_iyPKnRJWODIOvxwoPDWZjIpeng05dhR4p0NgVpl7ks5mLxozDlpZshdcnU4CrQKYMa1kYkw'),(1172,'wx00f7d56d549f82ce',20160910224309,'component_verify_ticket','ticket@@@ndBIPMTAA6wPqB_iyPKnRJWODIOvxwoPDWZjIpeng05dhR4p0NgVpl7ks5mLxozDlpZshdcnU4CrQKYMa1kYkw'),(1173,'wx00f7d56d549f82ce',20160910225315,'component_verify_ticket','ticket@@@ndBIPMTAA6wPqB_iyPKnRJWODIOvxwoPDWZjIpeng05dhR4p0NgVpl7ks5mLxozDlpZshdcnU4CrQKYMa1kYkw'),(1174,'wx00f7d56d549f82ce',20160910230319,'component_verify_ticket','ticket@@@sgCVQsJb2YP0E9nh5h3FH0W0ytFF0yvhxe7ZjQ9IxGM49VR0SJmaLa61iOvJAzhaioK0ILbHUP78ond0XmSLUA'),(1175,'wx00f7d56d549f82ce',20160910231328,'component_verify_ticket','ticket@@@sgCVQsJb2YP0E9nh5h3FH0W0ytFF0yvhxe7ZjQ9IxGM49VR0SJmaLa61iOvJAzhaioK0ILbHUP78ond0XmSLUA'),(1176,'wx00f7d56d549f82ce',20160910232319,'component_verify_ticket','ticket@@@sgCVQsJb2YP0E9nh5h3FH0W0ytFF0yvhxe7ZjQ9IxGM49VR0SJmaLa61iOvJAzhaioK0ILbHUP78ond0XmSLUA'),(1177,'wx00f7d56d549f82ce',20160910233319,'component_verify_ticket','ticket@@@sgCVQsJb2YP0E9nh5h3FH0W0ytFF0yvhxe7ZjQ9IxGM49VR0SJmaLa61iOvJAzhaioK0ILbHUP78ond0XmSLUA'),(1178,'wx00f7d56d549f82ce',20160910234319,'component_verify_ticket','ticket@@@sgCVQsJb2YP0E9nh5h3FH0W0ytFF0yvhxe7ZjQ9IxGM49VR0SJmaLa61iOvJAzhaioK0ILbHUP78ond0XmSLUA'),(1179,'wx00f7d56d549f82ce',20160910235318,'component_verify_ticket','ticket@@@sgCVQsJb2YP0E9nh5h3FH0W0ytFF0yvhxe7ZjQ9IxGM49VR0SJmaLa61iOvJAzhaioK0ILbHUP78ond0XmSLUA'),(1180,'wx00f7d56d549f82ce',20160911000311,'component_verify_ticket','ticket@@@1z9Mz9oX5OoufH-2Zjdb9HcStRkieSVOJ5iH6Zqq9N16DNgBtL_53CIn1jvLLmGdRTugoLmSF-3tLeDx-nFTOA'),(1181,'wx00f7d56d549f82ce',20160911001314,'component_verify_ticket','ticket@@@1z9Mz9oX5OoufH-2Zjdb9HcStRkieSVOJ5iH6Zqq9N16DNgBtL_53CIn1jvLLmGdRTugoLmSF-3tLeDx-nFTOA'),(1182,'wx00f7d56d549f82ce',20160911002313,'component_verify_ticket','ticket@@@1z9Mz9oX5OoufH-2Zjdb9HcStRkieSVOJ5iH6Zqq9N16DNgBtL_53CIn1jvLLmGdRTugoLmSF-3tLeDx-nFTOA'),(1183,'wx00f7d56d549f82ce',20160911003312,'component_verify_ticket','ticket@@@1z9Mz9oX5OoufH-2Zjdb9HcStRkieSVOJ5iH6Zqq9N16DNgBtL_53CIn1jvLLmGdRTugoLmSF-3tLeDx-nFTOA'),(1184,'wx00f7d56d549f82ce',20160911004319,'component_verify_ticket','ticket@@@1z9Mz9oX5OoufH-2Zjdb9HcStRkieSVOJ5iH6Zqq9N16DNgBtL_53CIn1jvLLmGdRTugoLmSF-3tLeDx-nFTOA'),(1185,'wx00f7d56d549f82ce',20160911005311,'component_verify_ticket','ticket@@@1z9Mz9oX5OoufH-2Zjdb9HcStRkieSVOJ5iH6Zqq9N16DNgBtL_53CIn1jvLLmGdRTugoLmSF-3tLeDx-nFTOA'),(1186,'wx00f7d56d549f82ce',20160911010320,'component_verify_ticket','ticket@@@RQ5rrKOUaXDA_96rN7KrxxhmSKNttu7tF-JbK9YhTvAOCgxQGFmUjyKHauvMEu44xqDI655rTYJJmXQalwoLVg'),(1187,'wx00f7d56d549f82ce',20160911011258,'component_verify_ticket','ticket@@@RQ5rrKOUaXDA_96rN7KrxxhmSKNttu7tF-JbK9YhTvAOCgxQGFmUjyKHauvMEu44xqDI655rTYJJmXQalwoLVg'),(1188,'wx00f7d56d549f82ce',20160911012300,'component_verify_ticket','ticket@@@RQ5rrKOUaXDA_96rN7KrxxhmSKNttu7tF-JbK9YhTvAOCgxQGFmUjyKHauvMEu44xqDI655rTYJJmXQalwoLVg'),(1189,'wx00f7d56d549f82ce',20160911013259,'component_verify_ticket','ticket@@@RQ5rrKOUaXDA_96rN7KrxxhmSKNttu7tF-JbK9YhTvAOCgxQGFmUjyKHauvMEu44xqDI655rTYJJmXQalwoLVg'),(1190,'wx00f7d56d549f82ce',20160911014257,'component_verify_ticket','ticket@@@RQ5rrKOUaXDA_96rN7KrxxhmSKNttu7tF-JbK9YhTvAOCgxQGFmUjyKHauvMEu44xqDI655rTYJJmXQalwoLVg'),(1191,'wx00f7d56d549f82ce',20160911015256,'component_verify_ticket','ticket@@@RQ5rrKOUaXDA_96rN7KrxxhmSKNttu7tF-JbK9YhTvAOCgxQGFmUjyKHauvMEu44xqDI655rTYJJmXQalwoLVg'),(1192,'wx00f7d56d549f82ce',20160911020259,'component_verify_ticket','ticket@@@U5m9h7xMw9rRsUXhNPAYJqb_EmcQhD0sw-GFufn3Zvn0KUnxHFn0MPBqdN_xy_YAqtZpRECk8DjgEEKqbhbxZw'),(1193,'wx00f7d56d549f82ce',20160911021258,'component_verify_ticket','ticket@@@U5m9h7xMw9rRsUXhNPAYJqb_EmcQhD0sw-GFufn3Zvn0KUnxHFn0MPBqdN_xy_YAqtZpRECk8DjgEEKqbhbxZw'),(1194,'wx00f7d56d549f82ce',20160911022258,'component_verify_ticket','ticket@@@U5m9h7xMw9rRsUXhNPAYJqb_EmcQhD0sw-GFufn3Zvn0KUnxHFn0MPBqdN_xy_YAqtZpRECk8DjgEEKqbhbxZw'),(1195,'wx00f7d56d549f82ce',20160911023306,'component_verify_ticket','ticket@@@U5m9h7xMw9rRsUXhNPAYJqb_EmcQhD0sw-GFufn3Zvn0KUnxHFn0MPBqdN_xy_YAqtZpRECk8DjgEEKqbhbxZw'),(1196,'wx00f7d56d549f82ce',20160911024257,'component_verify_ticket','ticket@@@U5m9h7xMw9rRsUXhNPAYJqb_EmcQhD0sw-GFufn3Zvn0KUnxHFn0MPBqdN_xy_YAqtZpRECk8DjgEEKqbhbxZw'),(1197,'wx00f7d56d549f82ce',20160911025300,'component_verify_ticket','ticket@@@U5m9h7xMw9rRsUXhNPAYJqb_EmcQhD0sw-GFufn3Zvn0KUnxHFn0MPBqdN_xy_YAqtZpRECk8DjgEEKqbhbxZw'),(1198,'wx00f7d56d549f82ce',20160911030259,'component_verify_ticket','ticket@@@kzv1mGOmbHpOv-3OsnL7dIW6H1JIqb9w0p93EgNgt733u63l_tDW1E7hJmFeeiOx14F1Pv6j_IeQ8uy9VViYeQ'),(1199,'wx00f7d56d549f82ce',20160911031303,'component_verify_ticket','ticket@@@kzv1mGOmbHpOv-3OsnL7dIW6H1JIqb9w0p93EgNgt733u63l_tDW1E7hJmFeeiOx14F1Pv6j_IeQ8uy9VViYeQ'),(1200,'wx00f7d56d549f82ce',20160911032301,'component_verify_ticket','ticket@@@kzv1mGOmbHpOv-3OsnL7dIW6H1JIqb9w0p93EgNgt733u63l_tDW1E7hJmFeeiOx14F1Pv6j_IeQ8uy9VViYeQ'),(1201,'wx00f7d56d549f82ce',20160911033253,'component_verify_ticket','ticket@@@kzv1mGOmbHpOv-3OsnL7dIW6H1JIqb9w0p93EgNgt733u63l_tDW1E7hJmFeeiOx14F1Pv6j_IeQ8uy9VViYeQ'),(1202,'wx00f7d56d549f82ce',20160911034300,'component_verify_ticket','ticket@@@kzv1mGOmbHpOv-3OsnL7dIW6H1JIqb9w0p93EgNgt733u63l_tDW1E7hJmFeeiOx14F1Pv6j_IeQ8uy9VViYeQ'),(1203,'wx00f7d56d549f82ce',20160911035302,'component_verify_ticket','ticket@@@kzv1mGOmbHpOv-3OsnL7dIW6H1JIqb9w0p93EgNgt733u63l_tDW1E7hJmFeeiOx14F1Pv6j_IeQ8uy9VViYeQ'),(1204,'wx00f7d56d549f82ce',20160911040257,'component_verify_ticket','ticket@@@HCtKGwOs_1uTwL3DmM3vQUrdQT3-U_WZ8rL4HBtHPXviTYPORRTYjIHXERgZXzAAJOZDAV7UPGocteyKuJ2_Aw'),(1205,'wx00f7d56d549f82ce',20160911041259,'component_verify_ticket','ticket@@@HCtKGwOs_1uTwL3DmM3vQUrdQT3-U_WZ8rL4HBtHPXviTYPORRTYjIHXERgZXzAAJOZDAV7UPGocteyKuJ2_Aw'),(1206,'wx00f7d56d549f82ce',20160911042303,'component_verify_ticket','ticket@@@HCtKGwOs_1uTwL3DmM3vQUrdQT3-U_WZ8rL4HBtHPXviTYPORRTYjIHXERgZXzAAJOZDAV7UPGocteyKuJ2_Aw'),(1207,'wx00f7d56d549f82ce',20160911043258,'component_verify_ticket','ticket@@@HCtKGwOs_1uTwL3DmM3vQUrdQT3-U_WZ8rL4HBtHPXviTYPORRTYjIHXERgZXzAAJOZDAV7UPGocteyKuJ2_Aw'),(1208,'wx00f7d56d549f82ce',20160911044258,'component_verify_ticket','ticket@@@HCtKGwOs_1uTwL3DmM3vQUrdQT3-U_WZ8rL4HBtHPXviTYPORRTYjIHXERgZXzAAJOZDAV7UPGocteyKuJ2_Aw'),(1209,'wx00f7d56d549f82ce',20160911045259,'component_verify_ticket','ticket@@@HCtKGwOs_1uTwL3DmM3vQUrdQT3-U_WZ8rL4HBtHPXviTYPORRTYjIHXERgZXzAAJOZDAV7UPGocteyKuJ2_Aw'),(1210,'wx00f7d56d549f82ce',20160911050256,'component_verify_ticket','ticket@@@WTKillumFM-5IdWtVGyELhzN6SQNvWPF-b6kMaW2-EdPsX5XkXGJpWnUFNbKyYd8n6SiJlnvfeKrX6Un-myqxg'),(1211,'wx00f7d56d549f82ce',20160911051303,'component_verify_ticket','ticket@@@WTKillumFM-5IdWtVGyELhzN6SQNvWPF-b6kMaW2-EdPsX5XkXGJpWnUFNbKyYd8n6SiJlnvfeKrX6Un-myqxg'),(1212,'wx00f7d56d549f82ce',20160911052252,'component_verify_ticket','ticket@@@WTKillumFM-5IdWtVGyELhzN6SQNvWPF-b6kMaW2-EdPsX5XkXGJpWnUFNbKyYd8n6SiJlnvfeKrX6Un-myqxg'),(1213,'wx00f7d56d549f82ce',20160911053253,'component_verify_ticket','ticket@@@WTKillumFM-5IdWtVGyELhzN6SQNvWPF-b6kMaW2-EdPsX5XkXGJpWnUFNbKyYd8n6SiJlnvfeKrX6Un-myqxg'),(1214,'wx00f7d56d549f82ce',20160911054250,'component_verify_ticket','ticket@@@WTKillumFM-5IdWtVGyELhzN6SQNvWPF-b6kMaW2-EdPsX5XkXGJpWnUFNbKyYd8n6SiJlnvfeKrX6Un-myqxg'),(1215,'wx00f7d56d549f82ce',20160911055257,'component_verify_ticket','ticket@@@WTKillumFM-5IdWtVGyELhzN6SQNvWPF-b6kMaW2-EdPsX5XkXGJpWnUFNbKyYd8n6SiJlnvfeKrX6Un-myqxg'),(1216,'wx00f7d56d549f82ce',20160911060253,'component_verify_ticket','ticket@@@kTydN3cCAZWXgHKxufF0DOriN3u6y2ZvqlTEfp4BEDK5lCFSXnDSUWl6GJsd58oJx-lZ48w8WX2VxZ6dPkYKXw'),(1217,'wx00f7d56d549f82ce',20160911061301,'component_verify_ticket','ticket@@@kTydN3cCAZWXgHKxufF0DOriN3u6y2ZvqlTEfp4BEDK5lCFSXnDSUWl6GJsd58oJx-lZ48w8WX2VxZ6dPkYKXw'),(1218,'wx00f7d56d549f82ce',20160911062258,'component_verify_ticket','ticket@@@kTydN3cCAZWXgHKxufF0DOriN3u6y2ZvqlTEfp4BEDK5lCFSXnDSUWl6GJsd58oJx-lZ48w8WX2VxZ6dPkYKXw'),(1219,'wx00f7d56d549f82ce',20160911063256,'component_verify_ticket','ticket@@@kTydN3cCAZWXgHKxufF0DOriN3u6y2ZvqlTEfp4BEDK5lCFSXnDSUWl6GJsd58oJx-lZ48w8WX2VxZ6dPkYKXw'),(1220,'wx00f7d56d549f82ce',20160911064248,'component_verify_ticket','ticket@@@kTydN3cCAZWXgHKxufF0DOriN3u6y2ZvqlTEfp4BEDK5lCFSXnDSUWl6GJsd58oJx-lZ48w8WX2VxZ6dPkYKXw'),(1221,'wx00f7d56d549f82ce',20160911065249,'component_verify_ticket','ticket@@@kTydN3cCAZWXgHKxufF0DOriN3u6y2ZvqlTEfp4BEDK5lCFSXnDSUWl6GJsd58oJx-lZ48w8WX2VxZ6dPkYKXw'),(1222,'wx00f7d56d549f82ce',20160911070258,'component_verify_ticket','ticket@@@tgGliopcwfCzfp495x_RbZz14fJuAXxSgKU59gzzLVFDXu9bTclBiHdB4KVTiHp_y0wXl2hu1lT63JGc_fny5w'),(1223,'wx00f7d56d549f82ce',20160911071256,'component_verify_ticket','ticket@@@tgGliopcwfCzfp495x_RbZz14fJuAXxSgKU59gzzLVFDXu9bTclBiHdB4KVTiHp_y0wXl2hu1lT63JGc_fny5w'),(1224,'wx00f7d56d549f82ce',20160911072258,'component_verify_ticket','ticket@@@tgGliopcwfCzfp495x_RbZz14fJuAXxSgKU59gzzLVFDXu9bTclBiHdB4KVTiHp_y0wXl2hu1lT63JGc_fny5w'),(1225,'wx00f7d56d549f82ce',20160911073256,'component_verify_ticket','ticket@@@tgGliopcwfCzfp495x_RbZz14fJuAXxSgKU59gzzLVFDXu9bTclBiHdB4KVTiHp_y0wXl2hu1lT63JGc_fny5w'),(1226,'wx00f7d56d549f82ce',20160911074259,'component_verify_ticket','ticket@@@tgGliopcwfCzfp495x_RbZz14fJuAXxSgKU59gzzLVFDXu9bTclBiHdB4KVTiHp_y0wXl2hu1lT63JGc_fny5w'),(1227,'wx00f7d56d549f82ce',20160911075253,'component_verify_ticket','ticket@@@tgGliopcwfCzfp495x_RbZz14fJuAXxSgKU59gzzLVFDXu9bTclBiHdB4KVTiHp_y0wXl2hu1lT63JGc_fny5w'),(1228,'wx00f7d56d549f82ce',20160911080302,'component_verify_ticket','ticket@@@scRYqHchbKAM3lE7lyOWJyjmzKn34WXghijUaNZeEIVW1Tw1MvRHcYoSZCEUspc1pMVy2qSjVRv4LDBhUULHnA'),(1229,'wx00f7d56d549f82ce',20160911081303,'component_verify_ticket','ticket@@@scRYqHchbKAM3lE7lyOWJyjmzKn34WXghijUaNZeEIVW1Tw1MvRHcYoSZCEUspc1pMVy2qSjVRv4LDBhUULHnA'),(1230,'wx00f7d56d549f82ce',20160911082304,'component_verify_ticket','ticket@@@scRYqHchbKAM3lE7lyOWJyjmzKn34WXghijUaNZeEIVW1Tw1MvRHcYoSZCEUspc1pMVy2qSjVRv4LDBhUULHnA'),(1231,'wx00f7d56d549f82ce',20160911083302,'component_verify_ticket','ticket@@@scRYqHchbKAM3lE7lyOWJyjmzKn34WXghijUaNZeEIVW1Tw1MvRHcYoSZCEUspc1pMVy2qSjVRv4LDBhUULHnA'),(1232,'wx00f7d56d549f82ce',20160911084300,'component_verify_ticket','ticket@@@scRYqHchbKAM3lE7lyOWJyjmzKn34WXghijUaNZeEIVW1Tw1MvRHcYoSZCEUspc1pMVy2qSjVRv4LDBhUULHnA'),(1233,'wx00f7d56d549f82ce',20160911085259,'component_verify_ticket','ticket@@@scRYqHchbKAM3lE7lyOWJyjmzKn34WXghijUaNZeEIVW1Tw1MvRHcYoSZCEUspc1pMVy2qSjVRv4LDBhUULHnA'),(1234,'wx00f7d56d549f82ce',20160911090304,'component_verify_ticket','ticket@@@2JCJsvBlilsZJtFGZmVF8PJq30y8yCWjR7fVpDJrEMTH_Vp7Nn6NUpcI836TivJWI3vDurNAoGxemqxYgcQhnw'),(1235,'wx00f7d56d549f82ce',20160911091309,'component_verify_ticket','ticket@@@2JCJsvBlilsZJtFGZmVF8PJq30y8yCWjR7fVpDJrEMTH_Vp7Nn6NUpcI836TivJWI3vDurNAoGxemqxYgcQhnw'),(1236,'wx00f7d56d549f82ce',20160911092304,'component_verify_ticket','ticket@@@2JCJsvBlilsZJtFGZmVF8PJq30y8yCWjR7fVpDJrEMTH_Vp7Nn6NUpcI836TivJWI3vDurNAoGxemqxYgcQhnw'),(1237,'wx00f7d56d549f82ce',20160911093306,'component_verify_ticket','ticket@@@2JCJsvBlilsZJtFGZmVF8PJq30y8yCWjR7fVpDJrEMTH_Vp7Nn6NUpcI836TivJWI3vDurNAoGxemqxYgcQhnw'),(1238,'wx00f7d56d549f82ce',20160911094310,'component_verify_ticket','ticket@@@2JCJsvBlilsZJtFGZmVF8PJq30y8yCWjR7fVpDJrEMTH_Vp7Nn6NUpcI836TivJWI3vDurNAoGxemqxYgcQhnw'),(1239,'wx00f7d56d549f82ce',20160911095306,'component_verify_ticket','ticket@@@2JCJsvBlilsZJtFGZmVF8PJq30y8yCWjR7fVpDJrEMTH_Vp7Nn6NUpcI836TivJWI3vDurNAoGxemqxYgcQhnw'),(1240,'wx00f7d56d549f82ce',20160911100334,'component_verify_ticket','ticket@@@3eKtx8ob2Hwm-hWaIKfS2-JvtgiErvM_9pok-Rv-3qFVK-9AYL9r_ghhLJsSx18a5JzPDoJVdgRLfsoP-x2O6w'),(1241,'wx00f7d56d549f82ce',20160911101257,'component_verify_ticket','ticket@@@3eKtx8ob2Hwm-hWaIKfS2-JvtgiErvM_9pok-Rv-3qFVK-9AYL9r_ghhLJsSx18a5JzPDoJVdgRLfsoP-x2O6w'),(1242,'wx00f7d56d549f82ce',20160911102315,'component_verify_ticket','ticket@@@3eKtx8ob2Hwm-hWaIKfS2-JvtgiErvM_9pok-Rv-3qFVK-9AYL9r_ghhLJsSx18a5JzPDoJVdgRLfsoP-x2O6w'),(1243,'wx00f7d56d549f82ce',20160911103309,'component_verify_ticket','ticket@@@3eKtx8ob2Hwm-hWaIKfS2-JvtgiErvM_9pok-Rv-3qFVK-9AYL9r_ghhLJsSx18a5JzPDoJVdgRLfsoP-x2O6w'),(1244,'wx00f7d56d549f82ce',20160911104306,'component_verify_ticket','ticket@@@3eKtx8ob2Hwm-hWaIKfS2-JvtgiErvM_9pok-Rv-3qFVK-9AYL9r_ghhLJsSx18a5JzPDoJVdgRLfsoP-x2O6w'),(1245,'wx00f7d56d549f82ce',20160911105328,'component_verify_ticket','ticket@@@3eKtx8ob2Hwm-hWaIKfS2-JvtgiErvM_9pok-Rv-3qFVK-9AYL9r_ghhLJsSx18a5JzPDoJVdgRLfsoP-x2O6w'),(1246,'wx00f7d56d549f82ce',20160911110322,'component_verify_ticket','ticket@@@4DB3waLSS_KtzDR5_OhOJVPJYcAN1Gn-AJHs7yGIQlMdm9Vo1BQXXYkNhnLE75mO4NbMSXGw-OulfC2AGTaEXw'),(1247,'wx00f7d56d549f82ce',20160911111414,'component_verify_ticket','ticket@@@4DB3waLSS_KtzDR5_OhOJVPJYcAN1Gn-AJHs7yGIQlMdm9Vo1BQXXYkNhnLE75mO4NbMSXGw-OulfC2AGTaEXw'),(1248,'wx00f7d56d549f82ce',20160911112310,'component_verify_ticket','ticket@@@4DB3waLSS_KtzDR5_OhOJVPJYcAN1Gn-AJHs7yGIQlMdm9Vo1BQXXYkNhnLE75mO4NbMSXGw-OulfC2AGTaEXw'),(1249,'wx00f7d56d549f82ce',20160911113304,'component_verify_ticket','ticket@@@4DB3waLSS_KtzDR5_OhOJVPJYcAN1Gn-AJHs7yGIQlMdm9Vo1BQXXYkNhnLE75mO4NbMSXGw-OulfC2AGTaEXw'),(1250,'wx00f7d56d549f82ce',20160911114313,'component_verify_ticket','ticket@@@4DB3waLSS_KtzDR5_OhOJVPJYcAN1Gn-AJHs7yGIQlMdm9Vo1BQXXYkNhnLE75mO4NbMSXGw-OulfC2AGTaEXw'),(1251,'wx00f7d56d549f82ce',20160911115311,'component_verify_ticket','ticket@@@4DB3waLSS_KtzDR5_OhOJVPJYcAN1Gn-AJHs7yGIQlMdm9Vo1BQXXYkNhnLE75mO4NbMSXGw-OulfC2AGTaEXw'),(1252,'wx00f7d56d549f82ce',20160911120345,'component_verify_ticket','ticket@@@wZo9X2COPK2ISJficOvf7IxF4UHdM4PwINPtF5_FBUq-J-XNsWAFDT4oijzwHjxukA9yCV9OXsUSwuoHTOEPHA'),(1253,'wx00f7d56d549f82ce',20160911121325,'component_verify_ticket','ticket@@@wZo9X2COPK2ISJficOvf7IxF4UHdM4PwINPtF5_FBUq-J-XNsWAFDT4oijzwHjxukA9yCV9OXsUSwuoHTOEPHA'),(1254,'wx00f7d56d549f82ce',20160911122342,'component_verify_ticket','ticket@@@wZo9X2COPK2ISJficOvf7IxF4UHdM4PwINPtF5_FBUq-J-XNsWAFDT4oijzwHjxukA9yCV9OXsUSwuoHTOEPHA'),(1255,'wx00f7d56d549f82ce',20160911123320,'component_verify_ticket','ticket@@@wZo9X2COPK2ISJficOvf7IxF4UHdM4PwINPtF5_FBUq-J-XNsWAFDT4oijzwHjxukA9yCV9OXsUSwuoHTOEPHA'),(1256,'wx00f7d56d549f82ce',20160911124319,'component_verify_ticket','ticket@@@wZo9X2COPK2ISJficOvf7IxF4UHdM4PwINPtF5_FBUq-J-XNsWAFDT4oijzwHjxukA9yCV9OXsUSwuoHTOEPHA'),(1257,'wx00f7d56d549f82ce',20160911125348,'component_verify_ticket','ticket@@@wZo9X2COPK2ISJficOvf7IxF4UHdM4PwINPtF5_FBUq-J-XNsWAFDT4oijzwHjxukA9yCV9OXsUSwuoHTOEPHA'),(1258,'wx00f7d56d549f82ce',20160911130313,'component_verify_ticket','ticket@@@pVSayJLWqFzIahORPaay4FDf6K3i5uljI_Nw2JHdXwbCqvkRkJ6BVJUPzx_b46_paz_NhVYRRcBHM8BkdguygA'),(1259,'wx00f7d56d549f82ce',20160911131314,'component_verify_ticket','ticket@@@pVSayJLWqFzIahORPaay4FDf6K3i5uljI_Nw2JHdXwbCqvkRkJ6BVJUPzx_b46_paz_NhVYRRcBHM8BkdguygA'),(1260,'wx00f7d56d549f82ce',20160911132316,'component_verify_ticket','ticket@@@pVSayJLWqFzIahORPaay4FDf6K3i5uljI_Nw2JHdXwbCqvkRkJ6BVJUPzx_b46_paz_NhVYRRcBHM8BkdguygA'),(1261,'wx00f7d56d549f82ce',20160911133313,'component_verify_ticket','ticket@@@pVSayJLWqFzIahORPaay4FDf6K3i5uljI_Nw2JHdXwbCqvkRkJ6BVJUPzx_b46_paz_NhVYRRcBHM8BkdguygA'),(1262,'wx00f7d56d549f82ce',20160911134308,'component_verify_ticket','ticket@@@pVSayJLWqFzIahORPaay4FDf6K3i5uljI_Nw2JHdXwbCqvkRkJ6BVJUPzx_b46_paz_NhVYRRcBHM8BkdguygA'),(1263,'wx00f7d56d549f82ce',20160911135309,'component_verify_ticket','ticket@@@pVSayJLWqFzIahORPaay4FDf6K3i5uljI_Nw2JHdXwbCqvkRkJ6BVJUPzx_b46_paz_NhVYRRcBHM8BkdguygA'),(1264,'wx00f7d56d549f82ce',20160911140322,'component_verify_ticket','ticket@@@SqH0EW2Ge5y3SVO5YOJ3vyBkLA5DtREZv6HdSlhLYyLTfiDKrJIjP14BYcU_O6gIaXTFy32nEeDydKtDKCPIEw'),(1265,'wx00f7d56d549f82ce',20160911141314,'component_verify_ticket','ticket@@@SqH0EW2Ge5y3SVO5YOJ3vyBkLA5DtREZv6HdSlhLYyLTfiDKrJIjP14BYcU_O6gIaXTFy32nEeDydKtDKCPIEw'),(1266,'wx00f7d56d549f82ce',20160911142318,'component_verify_ticket','ticket@@@SqH0EW2Ge5y3SVO5YOJ3vyBkLA5DtREZv6HdSlhLYyLTfiDKrJIjP14BYcU_O6gIaXTFy32nEeDydKtDKCPIEw'),(1267,'wx00f7d56d549f82ce',20160911143302,'component_verify_ticket','ticket@@@SqH0EW2Ge5y3SVO5YOJ3vyBkLA5DtREZv6HdSlhLYyLTfiDKrJIjP14BYcU_O6gIaXTFy32nEeDydKtDKCPIEw'),(1268,'wx00f7d56d549f82ce',20160911144302,'component_verify_ticket','ticket@@@SqH0EW2Ge5y3SVO5YOJ3vyBkLA5DtREZv6HdSlhLYyLTfiDKrJIjP14BYcU_O6gIaXTFy32nEeDydKtDKCPIEw'),(1269,'wx00f7d56d549f82ce',20160911145311,'component_verify_ticket','ticket@@@SqH0EW2Ge5y3SVO5YOJ3vyBkLA5DtREZv6HdSlhLYyLTfiDKrJIjP14BYcU_O6gIaXTFy32nEeDydKtDKCPIEw'),(1270,'wx00f7d56d549f82ce',20160911150312,'component_verify_ticket','ticket@@@CnUEcfAS9dhFzGGJb6vlHALH2uXdI9orLVM0kx28kz3z-OgWldKa3s68Yl_HosfUucjzTpl456JHnYT1AazIhw'),(1271,'wx00f7d56d549f82ce',20160911151325,'component_verify_ticket','ticket@@@CnUEcfAS9dhFzGGJb6vlHALH2uXdI9orLVM0kx28kz3z-OgWldKa3s68Yl_HosfUucjzTpl456JHnYT1AazIhw'),(1272,'wx00f7d56d549f82ce',20160911152306,'component_verify_ticket','ticket@@@CnUEcfAS9dhFzGGJb6vlHALH2uXdI9orLVM0kx28kz3z-OgWldKa3s68Yl_HosfUucjzTpl456JHnYT1AazIhw'),(1273,'wx00f7d56d549f82ce',20160911153310,'component_verify_ticket','ticket@@@CnUEcfAS9dhFzGGJb6vlHALH2uXdI9orLVM0kx28kz3z-OgWldKa3s68Yl_HosfUucjzTpl456JHnYT1AazIhw'),(1274,'wx00f7d56d549f82ce',20160911154303,'component_verify_ticket','ticket@@@CnUEcfAS9dhFzGGJb6vlHALH2uXdI9orLVM0kx28kz3z-OgWldKa3s68Yl_HosfUucjzTpl456JHnYT1AazIhw'),(1275,'wx00f7d56d549f82ce',20160911155302,'component_verify_ticket','ticket@@@CnUEcfAS9dhFzGGJb6vlHALH2uXdI9orLVM0kx28kz3z-OgWldKa3s68Yl_HosfUucjzTpl456JHnYT1AazIhw'),(1276,'wx00f7d56d549f82ce',20160911160315,'component_verify_ticket','ticket@@@Eg6qfIMMsTxi9NJEzBuoLGH07jagonre-b6QwAz1_CGaYkGbaB54y9uO-PUWZPaxGyAI01LOsHrQrjkkdF83Hg'),(1277,'wx00f7d56d549f82ce',20160911161309,'component_verify_ticket','ticket@@@Eg6qfIMMsTxi9NJEzBuoLGH07jagonre-b6QwAz1_CGaYkGbaB54y9uO-PUWZPaxGyAI01LOsHrQrjkkdF83Hg'),(1278,'wx00f7d56d549f82ce',20160911162312,'component_verify_ticket','ticket@@@Eg6qfIMMsTxi9NJEzBuoLGH07jagonre-b6QwAz1_CGaYkGbaB54y9uO-PUWZPaxGyAI01LOsHrQrjkkdF83Hg'),(1279,'wx00f7d56d549f82ce',20160911163304,'component_verify_ticket','ticket@@@Eg6qfIMMsTxi9NJEzBuoLGH07jagonre-b6QwAz1_CGaYkGbaB54y9uO-PUWZPaxGyAI01LOsHrQrjkkdF83Hg'),(1280,'wx00f7d56d549f82ce',20160911164302,'component_verify_ticket','ticket@@@Eg6qfIMMsTxi9NJEzBuoLGH07jagonre-b6QwAz1_CGaYkGbaB54y9uO-PUWZPaxGyAI01LOsHrQrjkkdF83Hg'),(1281,'wx00f7d56d549f82ce',20160911165303,'component_verify_ticket','ticket@@@Eg6qfIMMsTxi9NJEzBuoLGH07jagonre-b6QwAz1_CGaYkGbaB54y9uO-PUWZPaxGyAI01LOsHrQrjkkdF83Hg'),(1282,'wx00f7d56d549f82ce',20160911170310,'component_verify_ticket','ticket@@@3zQHmzB-_d3wewM8DCnxPNkd0Zgq2UuNzHSZSshbyQ4ULVyrDg3Y5aIZNlhCers5niSx51QEgWo9XQ_nf3YOcw'),(1283,'wx00f7d56d549f82ce',20160911171310,'component_verify_ticket','ticket@@@3zQHmzB-_d3wewM8DCnxPNkd0Zgq2UuNzHSZSshbyQ4ULVyrDg3Y5aIZNlhCers5niSx51QEgWo9XQ_nf3YOcw'),(1284,'wx00f7d56d549f82ce',20160911172259,'component_verify_ticket','ticket@@@3zQHmzB-_d3wewM8DCnxPNkd0Zgq2UuNzHSZSshbyQ4ULVyrDg3Y5aIZNlhCers5niSx51QEgWo9XQ_nf3YOcw'),(1285,'wx00f7d56d549f82ce',20160911173304,'component_verify_ticket','ticket@@@3zQHmzB-_d3wewM8DCnxPNkd0Zgq2UuNzHSZSshbyQ4ULVyrDg3Y5aIZNlhCers5niSx51QEgWo9XQ_nf3YOcw'),(1286,'wx00f7d56d549f82ce',20160911174259,'component_verify_ticket','ticket@@@3zQHmzB-_d3wewM8DCnxPNkd0Zgq2UuNzHSZSshbyQ4ULVyrDg3Y5aIZNlhCers5niSx51QEgWo9XQ_nf3YOcw'),(1287,'wx00f7d56d549f82ce',20160911175300,'component_verify_ticket','ticket@@@3zQHmzB-_d3wewM8DCnxPNkd0Zgq2UuNzHSZSshbyQ4ULVyrDg3Y5aIZNlhCers5niSx51QEgWo9XQ_nf3YOcw'),(1288,'wx00f7d56d549f82ce',20160911180312,'component_verify_ticket','ticket@@@DKzZ--Jl149zPsOX1aXkgJFCWLBEGIJpBBPQLpk_88jxsJCZ7Py-NESZGFkW6OwpkzAMgXFkqJpphL5Psn8bHg'),(1289,'wx00f7d56d549f82ce',20160911181302,'component_verify_ticket','ticket@@@DKzZ--Jl149zPsOX1aXkgJFCWLBEGIJpBBPQLpk_88jxsJCZ7Py-NESZGFkW6OwpkzAMgXFkqJpphL5Psn8bHg'),(1290,'wx00f7d56d549f82ce',20160911182317,'component_verify_ticket','ticket@@@DKzZ--Jl149zPsOX1aXkgJFCWLBEGIJpBBPQLpk_88jxsJCZ7Py-NESZGFkW6OwpkzAMgXFkqJpphL5Psn8bHg'),(1291,'wx00f7d56d549f82ce',20160911183255,'component_verify_ticket','ticket@@@DKzZ--Jl149zPsOX1aXkgJFCWLBEGIJpBBPQLpk_88jxsJCZ7Py-NESZGFkW6OwpkzAMgXFkqJpphL5Psn8bHg'),(1292,'wx00f7d56d549f82ce',20160911184305,'component_verify_ticket','ticket@@@DKzZ--Jl149zPsOX1aXkgJFCWLBEGIJpBBPQLpk_88jxsJCZ7Py-NESZGFkW6OwpkzAMgXFkqJpphL5Psn8bHg'),(1293,'wx00f7d56d549f82ce',20160911185256,'component_verify_ticket','ticket@@@DKzZ--Jl149zPsOX1aXkgJFCWLBEGIJpBBPQLpk_88jxsJCZ7Py-NESZGFkW6OwpkzAMgXFkqJpphL5Psn8bHg'),(1294,'wx00f7d56d549f82ce',20160911190258,'component_verify_ticket','ticket@@@yi0lAKjG986XzksTYD8ayf14zC2u5U8WloJKp4rFmODtYyjjfXfLXD-Z0M0d9H0Ld7uDOjlWneoFZkWKpGL4VA'),(1295,'wx00f7d56d549f82ce',20160911191308,'component_verify_ticket','ticket@@@yi0lAKjG986XzksTYD8ayf14zC2u5U8WloJKp4rFmODtYyjjfXfLXD-Z0M0d9H0Ld7uDOjlWneoFZkWKpGL4VA'),(1296,'wx00f7d56d549f82ce',20160911192300,'component_verify_ticket','ticket@@@yi0lAKjG986XzksTYD8ayf14zC2u5U8WloJKp4rFmODtYyjjfXfLXD-Z0M0d9H0Ld7uDOjlWneoFZkWKpGL4VA'),(1297,'wx00f7d56d549f82ce',20160911193306,'component_verify_ticket','ticket@@@yi0lAKjG986XzksTYD8ayf14zC2u5U8WloJKp4rFmODtYyjjfXfLXD-Z0M0d9H0Ld7uDOjlWneoFZkWKpGL4VA'),(1298,'wx00f7d56d549f82ce',20160911194255,'component_verify_ticket','ticket@@@yi0lAKjG986XzksTYD8ayf14zC2u5U8WloJKp4rFmODtYyjjfXfLXD-Z0M0d9H0Ld7uDOjlWneoFZkWKpGL4VA'),(1299,'wx00f7d56d549f82ce',20160911195306,'component_verify_ticket','ticket@@@yi0lAKjG986XzksTYD8ayf14zC2u5U8WloJKp4rFmODtYyjjfXfLXD-Z0M0d9H0Ld7uDOjlWneoFZkWKpGL4VA'),(1300,'wx00f7d56d549f82ce',20160911200358,'component_verify_ticket','ticket@@@vm8fu3f5F_vCibXtIgabnojKk52vrDMhmmKG19yCPEerPGXRzwZD1_ii1XtpGbAPeRdq4iHScqNoobsHg0YU8A'),(1301,'wx00f7d56d549f82ce',20160911201313,'component_verify_ticket','ticket@@@vm8fu3f5F_vCibXtIgabnojKk52vrDMhmmKG19yCPEerPGXRzwZD1_ii1XtpGbAPeRdq4iHScqNoobsHg0YU8A'),(1302,'wx00f7d56d549f82ce',20160911202408,'component_verify_ticket','ticket@@@vm8fu3f5F_vCibXtIgabnojKk52vrDMhmmKG19yCPEerPGXRzwZD1_ii1XtpGbAPeRdq4iHScqNoobsHg0YU8A'),(1303,'wx00f7d56d549f82ce',20160911203341,'component_verify_ticket','ticket@@@vm8fu3f5F_vCibXtIgabnojKk52vrDMhmmKG19yCPEerPGXRzwZD1_ii1XtpGbAPeRdq4iHScqNoobsHg0YU8A'),(1304,'wx00f7d56d549f82ce',20160911204322,'component_verify_ticket','ticket@@@vm8fu3f5F_vCibXtIgabnojKk52vrDMhmmKG19yCPEerPGXRzwZD1_ii1XtpGbAPeRdq4iHScqNoobsHg0YU8A'),(1305,'wx00f7d56d549f82ce',20160911205320,'component_verify_ticket','ticket@@@vm8fu3f5F_vCibXtIgabnojKk52vrDMhmmKG19yCPEerPGXRzwZD1_ii1XtpGbAPeRdq4iHScqNoobsHg0YU8A'),(1306,'wx00f7d56d549f82ce',20160911210331,'component_verify_ticket','ticket@@@Ven7BHApXv9fkpi8YJvgzK_RfsHz7zUESYCq0w6rjUQVmCmqlcyh5RQB6q3T44_QTuYsxryQ7GWXFrVo-_e9Wg'),(1307,'wx00f7d56d549f82ce',20160911211301,'component_verify_ticket','ticket@@@Ven7BHApXv9fkpi8YJvgzK_RfsHz7zUESYCq0w6rjUQVmCmqlcyh5RQB6q3T44_QTuYsxryQ7GWXFrVo-_e9Wg'),(1308,'wx00f7d56d549f82ce',20160911212256,'component_verify_ticket','ticket@@@Ven7BHApXv9fkpi8YJvgzK_RfsHz7zUESYCq0w6rjUQVmCmqlcyh5RQB6q3T44_QTuYsxryQ7GWXFrVo-_e9Wg'),(1309,'wx00f7d56d549f82ce',20160911213257,'component_verify_ticket','ticket@@@Ven7BHApXv9fkpi8YJvgzK_RfsHz7zUESYCq0w6rjUQVmCmqlcyh5RQB6q3T44_QTuYsxryQ7GWXFrVo-_e9Wg'),(1310,'wx00f7d56d549f82ce',20160911214256,'component_verify_ticket','ticket@@@Ven7BHApXv9fkpi8YJvgzK_RfsHz7zUESYCq0w6rjUQVmCmqlcyh5RQB6q3T44_QTuYsxryQ7GWXFrVo-_e9Wg'),(1311,'wx00f7d56d549f82ce',20160911215256,'component_verify_ticket','ticket@@@Ven7BHApXv9fkpi8YJvgzK_RfsHz7zUESYCq0w6rjUQVmCmqlcyh5RQB6q3T44_QTuYsxryQ7GWXFrVo-_e9Wg'),(1312,'wx00f7d56d549f82ce',20160911220255,'component_verify_ticket','ticket@@@D6OUf3YXw4650ZnT-r4tJ2nLOhfw1TnvcG_lg-u0vbajAslbvMu7bW0RGbj_GyjvFiumazPF1iS7wJj4OWtk4Q'),(1313,'wx00f7d56d549f82ce',20160911221253,'component_verify_ticket','ticket@@@D6OUf3YXw4650ZnT-r4tJ2nLOhfw1TnvcG_lg-u0vbajAslbvMu7bW0RGbj_GyjvFiumazPF1iS7wJj4OWtk4Q'),(1314,'wx00f7d56d549f82ce',20160911222254,'component_verify_ticket','ticket@@@D6OUf3YXw4650ZnT-r4tJ2nLOhfw1TnvcG_lg-u0vbajAslbvMu7bW0RGbj_GyjvFiumazPF1iS7wJj4OWtk4Q'),(1315,'wx00f7d56d549f82ce',20160911223254,'component_verify_ticket','ticket@@@D6OUf3YXw4650ZnT-r4tJ2nLOhfw1TnvcG_lg-u0vbajAslbvMu7bW0RGbj_GyjvFiumazPF1iS7wJj4OWtk4Q'),(1316,'wx00f7d56d549f82ce',20160911224249,'component_verify_ticket','ticket@@@D6OUf3YXw4650ZnT-r4tJ2nLOhfw1TnvcG_lg-u0vbajAslbvMu7bW0RGbj_GyjvFiumazPF1iS7wJj4OWtk4Q'),(1317,'wx00f7d56d549f82ce',20160911225251,'component_verify_ticket','ticket@@@D6OUf3YXw4650ZnT-r4tJ2nLOhfw1TnvcG_lg-u0vbajAslbvMu7bW0RGbj_GyjvFiumazPF1iS7wJj4OWtk4Q'),(1318,'wx00f7d56d549f82ce',20160911230253,'component_verify_ticket','ticket@@@hvF4SQoMsJj9_FEoRlZmVSfFRsQLJJ1OGEl0hhEt6R4JasXRdinD6VJ7oJjsz-HcebNHAjZ-I5ScB_nSpwfgkg'),(1319,'wx00f7d56d549f82ce',20160911231255,'component_verify_ticket','ticket@@@hvF4SQoMsJj9_FEoRlZmVSfFRsQLJJ1OGEl0hhEt6R4JasXRdinD6VJ7oJjsz-HcebNHAjZ-I5ScB_nSpwfgkg'),(1320,'wx00f7d56d549f82ce',20160911232254,'component_verify_ticket','ticket@@@hvF4SQoMsJj9_FEoRlZmVSfFRsQLJJ1OGEl0hhEt6R4JasXRdinD6VJ7oJjsz-HcebNHAjZ-I5ScB_nSpwfgkg'),(1321,'wx00f7d56d549f82ce',20160911233256,'component_verify_ticket','ticket@@@hvF4SQoMsJj9_FEoRlZmVSfFRsQLJJ1OGEl0hhEt6R4JasXRdinD6VJ7oJjsz-HcebNHAjZ-I5ScB_nSpwfgkg'),(1322,'wx00f7d56d549f82ce',20160911234254,'component_verify_ticket','ticket@@@hvF4SQoMsJj9_FEoRlZmVSfFRsQLJJ1OGEl0hhEt6R4JasXRdinD6VJ7oJjsz-HcebNHAjZ-I5ScB_nSpwfgkg'),(1323,'wx00f7d56d549f82ce',20160911235244,'component_verify_ticket','ticket@@@hvF4SQoMsJj9_FEoRlZmVSfFRsQLJJ1OGEl0hhEt6R4JasXRdinD6VJ7oJjsz-HcebNHAjZ-I5ScB_nSpwfgkg'),(1324,'wx00f7d56d549f82ce',20160912000254,'component_verify_ticket','ticket@@@05jY6d3k6N956rtYZi23rnP5CuG9hMoBWXx2zrt60V_QpvRWFRgGMYi_wSERky24UvukmGF2GFzdRNBP5bT5rA'),(1325,'wx00f7d56d549f82ce',20160912001237,'component_verify_ticket','ticket@@@05jY6d3k6N956rtYZi23rnP5CuG9hMoBWXx2zrt60V_QpvRWFRgGMYi_wSERky24UvukmGF2GFzdRNBP5bT5rA'),(1326,'wx00f7d56d549f82ce',20160912002246,'component_verify_ticket','ticket@@@05jY6d3k6N956rtYZi23rnP5CuG9hMoBWXx2zrt60V_QpvRWFRgGMYi_wSERky24UvukmGF2GFzdRNBP5bT5rA'),(1327,'wx00f7d56d549f82ce',20160912003252,'component_verify_ticket','ticket@@@05jY6d3k6N956rtYZi23rnP5CuG9hMoBWXx2zrt60V_QpvRWFRgGMYi_wSERky24UvukmGF2GFzdRNBP5bT5rA'),(1328,'wx00f7d56d549f82ce',20160912004247,'component_verify_ticket','ticket@@@05jY6d3k6N956rtYZi23rnP5CuG9hMoBWXx2zrt60V_QpvRWFRgGMYi_wSERky24UvukmGF2GFzdRNBP5bT5rA'),(1329,'wx00f7d56d549f82ce',20160912005247,'component_verify_ticket','ticket@@@05jY6d3k6N956rtYZi23rnP5CuG9hMoBWXx2zrt60V_QpvRWFRgGMYi_wSERky24UvukmGF2GFzdRNBP5bT5rA'),(1330,'wx00f7d56d549f82ce',20160912010247,'component_verify_ticket','ticket@@@2JcheVO388tzTXmEWgZkxzrqPpwiWpcZaV-roV9b9rPvedPlFBfK2OR_Snw-EMYaxwomzpx6Z0qEhtzloDlKDQ'),(1331,'wx00f7d56d549f82ce',20160912011248,'component_verify_ticket','ticket@@@2JcheVO388tzTXmEWgZkxzrqPpwiWpcZaV-roV9b9rPvedPlFBfK2OR_Snw-EMYaxwomzpx6Z0qEhtzloDlKDQ'),(1332,'wx00f7d56d549f82ce',20160912012249,'component_verify_ticket','ticket@@@2JcheVO388tzTXmEWgZkxzrqPpwiWpcZaV-roV9b9rPvedPlFBfK2OR_Snw-EMYaxwomzpx6Z0qEhtzloDlKDQ'),(1333,'wx00f7d56d549f82ce',20160912013249,'component_verify_ticket','ticket@@@2JcheVO388tzTXmEWgZkxzrqPpwiWpcZaV-roV9b9rPvedPlFBfK2OR_Snw-EMYaxwomzpx6Z0qEhtzloDlKDQ'),(1334,'wx00f7d56d549f82ce',20160912014249,'component_verify_ticket','ticket@@@2JcheVO388tzTXmEWgZkxzrqPpwiWpcZaV-roV9b9rPvedPlFBfK2OR_Snw-EMYaxwomzpx6Z0qEhtzloDlKDQ'),(1335,'wx00f7d56d549f82ce',20160912015246,'component_verify_ticket','ticket@@@2JcheVO388tzTXmEWgZkxzrqPpwiWpcZaV-roV9b9rPvedPlFBfK2OR_Snw-EMYaxwomzpx6Z0qEhtzloDlKDQ'),(1336,'wx00f7d56d549f82ce',20160912020252,'component_verify_ticket','ticket@@@FyymucX6QXtS57KY7-rSzWBOvtLoU2KgulXVyRDrTZEE25ZcE7ubCUDtB0T6vusuht7PBfWfr4o0OqGaTSH5pw'),(1337,'wx00f7d56d549f82ce',20160912021251,'component_verify_ticket','ticket@@@FyymucX6QXtS57KY7-rSzWBOvtLoU2KgulXVyRDrTZEE25ZcE7ubCUDtB0T6vusuht7PBfWfr4o0OqGaTSH5pw'),(1338,'wx00f7d56d549f82ce',20160912022252,'component_verify_ticket','ticket@@@FyymucX6QXtS57KY7-rSzWBOvtLoU2KgulXVyRDrTZEE25ZcE7ubCUDtB0T6vusuht7PBfWfr4o0OqGaTSH5pw'),(1339,'wx00f7d56d549f82ce',20160912023250,'component_verify_ticket','ticket@@@FyymucX6QXtS57KY7-rSzWBOvtLoU2KgulXVyRDrTZEE25ZcE7ubCUDtB0T6vusuht7PBfWfr4o0OqGaTSH5pw'),(1340,'wx00f7d56d549f82ce',20160912024250,'component_verify_ticket','ticket@@@FyymucX6QXtS57KY7-rSzWBOvtLoU2KgulXVyRDrTZEE25ZcE7ubCUDtB0T6vusuht7PBfWfr4o0OqGaTSH5pw'),(1341,'wx00f7d56d549f82ce',20160912025250,'component_verify_ticket','ticket@@@FyymucX6QXtS57KY7-rSzWBOvtLoU2KgulXVyRDrTZEE25ZcE7ubCUDtB0T6vusuht7PBfWfr4o0OqGaTSH5pw'),(1342,'wx00f7d56d549f82ce',20160912030252,'component_verify_ticket','ticket@@@wNQqZVOdmQCf0ekefcgQ-Jo52LaBY_mPzyvM5_7yDotWUIN00CoSQ8_gWFVWfmLZxIqQwY-EI-5tgVUXOAh2BQ'),(1343,'wx00f7d56d549f82ce',20160912031252,'component_verify_ticket','ticket@@@wNQqZVOdmQCf0ekefcgQ-Jo52LaBY_mPzyvM5_7yDotWUIN00CoSQ8_gWFVWfmLZxIqQwY-EI-5tgVUXOAh2BQ'),(1344,'wx00f7d56d549f82ce',20160912032251,'component_verify_ticket','ticket@@@wNQqZVOdmQCf0ekefcgQ-Jo52LaBY_mPzyvM5_7yDotWUIN00CoSQ8_gWFVWfmLZxIqQwY-EI-5tgVUXOAh2BQ'),(1345,'wx00f7d56d549f82ce',20160912033251,'component_verify_ticket','ticket@@@wNQqZVOdmQCf0ekefcgQ-Jo52LaBY_mPzyvM5_7yDotWUIN00CoSQ8_gWFVWfmLZxIqQwY-EI-5tgVUXOAh2BQ'),(1346,'wx00f7d56d549f82ce',20160912034251,'component_verify_ticket','ticket@@@wNQqZVOdmQCf0ekefcgQ-Jo52LaBY_mPzyvM5_7yDotWUIN00CoSQ8_gWFVWfmLZxIqQwY-EI-5tgVUXOAh2BQ'),(1347,'wx00f7d56d549f82ce',20160912035252,'component_verify_ticket','ticket@@@wNQqZVOdmQCf0ekefcgQ-Jo52LaBY_mPzyvM5_7yDotWUIN00CoSQ8_gWFVWfmLZxIqQwY-EI-5tgVUXOAh2BQ'),(1348,'wx00f7d56d549f82ce',20160912040256,'component_verify_ticket','ticket@@@l5tnx_A3Y-XPhcF8CRty-uFI6taEd_FcISHmxtArbinpZtTut9xMfJxo01fajdjrzAo_DbC6OBNI4X51xssQHw'),(1349,'wx00f7d56d549f82ce',20160912041300,'component_verify_ticket','ticket@@@l5tnx_A3Y-XPhcF8CRty-uFI6taEd_FcISHmxtArbinpZtTut9xMfJxo01fajdjrzAo_DbC6OBNI4X51xssQHw'),(1350,'wx00f7d56d549f82ce',20160912042254,'component_verify_ticket','ticket@@@l5tnx_A3Y-XPhcF8CRty-uFI6taEd_FcISHmxtArbinpZtTut9xMfJxo01fajdjrzAo_DbC6OBNI4X51xssQHw'),(1351,'wx00f7d56d549f82ce',20160912043246,'component_verify_ticket','ticket@@@l5tnx_A3Y-XPhcF8CRty-uFI6taEd_FcISHmxtArbinpZtTut9xMfJxo01fajdjrzAo_DbC6OBNI4X51xssQHw'),(1352,'wx00f7d56d549f82ce',20160912044252,'component_verify_ticket','ticket@@@l5tnx_A3Y-XPhcF8CRty-uFI6taEd_FcISHmxtArbinpZtTut9xMfJxo01fajdjrzAo_DbC6OBNI4X51xssQHw'),(1353,'wx00f7d56d549f82ce',20160912045251,'component_verify_ticket','ticket@@@l5tnx_A3Y-XPhcF8CRty-uFI6taEd_FcISHmxtArbinpZtTut9xMfJxo01fajdjrzAo_DbC6OBNI4X51xssQHw'),(1354,'wx00f7d56d549f82ce',20160912050247,'component_verify_ticket','ticket@@@Qcv8Uw1xuOCD8syOywGbPRNu7jgtjRqIkkOxSGdJK2QuTHoxZOj3cppjGGh7677UrXNBGLAbpRLRagCBTA2jfA'),(1355,'wx00f7d56d549f82ce',20160912051245,'component_verify_ticket','ticket@@@Qcv8Uw1xuOCD8syOywGbPRNu7jgtjRqIkkOxSGdJK2QuTHoxZOj3cppjGGh7677UrXNBGLAbpRLRagCBTA2jfA'),(1356,'wx00f7d56d549f82ce',20160912052242,'component_verify_ticket','ticket@@@Qcv8Uw1xuOCD8syOywGbPRNu7jgtjRqIkkOxSGdJK2QuTHoxZOj3cppjGGh7677UrXNBGLAbpRLRagCBTA2jfA'),(1357,'wx00f7d56d549f82ce',20160912053243,'component_verify_ticket','ticket@@@Qcv8Uw1xuOCD8syOywGbPRNu7jgtjRqIkkOxSGdJK2QuTHoxZOj3cppjGGh7677UrXNBGLAbpRLRagCBTA2jfA'),(1358,'wx00f7d56d549f82ce',20160912054241,'component_verify_ticket','ticket@@@Qcv8Uw1xuOCD8syOywGbPRNu7jgtjRqIkkOxSGdJK2QuTHoxZOj3cppjGGh7677UrXNBGLAbpRLRagCBTA2jfA'),(1359,'wx00f7d56d549f82ce',20160912055245,'component_verify_ticket','ticket@@@Qcv8Uw1xuOCD8syOywGbPRNu7jgtjRqIkkOxSGdJK2QuTHoxZOj3cppjGGh7677UrXNBGLAbpRLRagCBTA2jfA'),(1360,'wx00f7d56d549f82ce',20160912060250,'component_verify_ticket','ticket@@@KavDcKIjhg6La6cUW-Hq0_I5qAdsUKhJIjMkkLkd3nJCqCC4agaOZQ2WhYtC_45yCqxb0gYfHTOB-oFvaFuCKQ'),(1361,'wx00f7d56d549f82ce',20160912061250,'component_verify_ticket','ticket@@@KavDcKIjhg6La6cUW-Hq0_I5qAdsUKhJIjMkkLkd3nJCqCC4agaOZQ2WhYtC_45yCqxb0gYfHTOB-oFvaFuCKQ'),(1362,'wx00f7d56d549f82ce',20160912062249,'component_verify_ticket','ticket@@@KavDcKIjhg6La6cUW-Hq0_I5qAdsUKhJIjMkkLkd3nJCqCC4agaOZQ2WhYtC_45yCqxb0gYfHTOB-oFvaFuCKQ'),(1363,'wx00f7d56d549f82ce',20160912063245,'component_verify_ticket','ticket@@@KavDcKIjhg6La6cUW-Hq0_I5qAdsUKhJIjMkkLkd3nJCqCC4agaOZQ2WhYtC_45yCqxb0gYfHTOB-oFvaFuCKQ'),(1364,'wx00f7d56d549f82ce',20160912064240,'component_verify_ticket','ticket@@@KavDcKIjhg6La6cUW-Hq0_I5qAdsUKhJIjMkkLkd3nJCqCC4agaOZQ2WhYtC_45yCqxb0gYfHTOB-oFvaFuCKQ'),(1365,'wx00f7d56d549f82ce',20160912065240,'component_verify_ticket','ticket@@@KavDcKIjhg6La6cUW-Hq0_I5qAdsUKhJIjMkkLkd3nJCqCC4agaOZQ2WhYtC_45yCqxb0gYfHTOB-oFvaFuCKQ'),(1366,'wx00f7d56d549f82ce',20160912070247,'component_verify_ticket','ticket@@@tzOWDuT4VMAj75SARSGUyNgpURC8tckaKHwxnTM3dDhxvPbjXKf5lhp9RPWcKkh0bE73qyccbeaHiBhQHsxSDQ'),(1367,'wx00f7d56d549f82ce',20160912071248,'component_verify_ticket','ticket@@@tzOWDuT4VMAj75SARSGUyNgpURC8tckaKHwxnTM3dDhxvPbjXKf5lhp9RPWcKkh0bE73qyccbeaHiBhQHsxSDQ'),(1368,'wx00f7d56d549f82ce',20160912072248,'component_verify_ticket','ticket@@@tzOWDuT4VMAj75SARSGUyNgpURC8tckaKHwxnTM3dDhxvPbjXKf5lhp9RPWcKkh0bE73qyccbeaHiBhQHsxSDQ'),(1369,'wx00f7d56d549f82ce',20160912073243,'component_verify_ticket','ticket@@@tzOWDuT4VMAj75SARSGUyNgpURC8tckaKHwxnTM3dDhxvPbjXKf5lhp9RPWcKkh0bE73qyccbeaHiBhQHsxSDQ'),(1370,'wx00f7d56d549f82ce',20160912074241,'component_verify_ticket','ticket@@@tzOWDuT4VMAj75SARSGUyNgpURC8tckaKHwxnTM3dDhxvPbjXKf5lhp9RPWcKkh0bE73qyccbeaHiBhQHsxSDQ'),(1371,'wx00f7d56d549f82ce',20160912075241,'component_verify_ticket','ticket@@@tzOWDuT4VMAj75SARSGUyNgpURC8tckaKHwxnTM3dDhxvPbjXKf5lhp9RPWcKkh0bE73qyccbeaHiBhQHsxSDQ'),(1372,'wx00f7d56d549f82ce',20160912080313,'component_verify_ticket','ticket@@@0N09NvGf_JCWNAaQ3jeeG_HaZj3csnGwWhnI2tEI4auDckzetMOmuBlFFgFsebsZm5P1mhQ8OzXCIF5ZYnvRyw'),(1373,'wx00f7d56d549f82ce',20160912081252,'component_verify_ticket','ticket@@@0N09NvGf_JCWNAaQ3jeeG_HaZj3csnGwWhnI2tEI4auDckzetMOmuBlFFgFsebsZm5P1mhQ8OzXCIF5ZYnvRyw'),(1374,'wx00f7d56d549f82ce',20160912082251,'component_verify_ticket','ticket@@@0N09NvGf_JCWNAaQ3jeeG_HaZj3csnGwWhnI2tEI4auDckzetMOmuBlFFgFsebsZm5P1mhQ8OzXCIF5ZYnvRyw'),(1375,'wx00f7d56d549f82ce',20160912083252,'component_verify_ticket','ticket@@@0N09NvGf_JCWNAaQ3jeeG_HaZj3csnGwWhnI2tEI4auDckzetMOmuBlFFgFsebsZm5P1mhQ8OzXCIF5ZYnvRyw'),(1376,'wx00f7d56d549f82ce',20160912084253,'component_verify_ticket','ticket@@@0N09NvGf_JCWNAaQ3jeeG_HaZj3csnGwWhnI2tEI4auDckzetMOmuBlFFgFsebsZm5P1mhQ8OzXCIF5ZYnvRyw'),(1377,'wx00f7d56d549f82ce',20160912085252,'component_verify_ticket','ticket@@@0N09NvGf_JCWNAaQ3jeeG_HaZj3csnGwWhnI2tEI4auDckzetMOmuBlFFgFsebsZm5P1mhQ8OzXCIF5ZYnvRyw'),(1378,'wx00f7d56d549f82ce',20160912090301,'component_verify_ticket','ticket@@@yWrNIU9gIixPl7lU8WFwiqx6fjNfIhNg6tLfrg-QxF68NpHokUfN_9WnFP2wH0GvvLwBxDRg326LXg5qD1jZTQ'),(1379,'wx00f7d56d549f82ce',20160912091257,'component_verify_ticket','ticket@@@yWrNIU9gIixPl7lU8WFwiqx6fjNfIhNg6tLfrg-QxF68NpHokUfN_9WnFP2wH0GvvLwBxDRg326LXg5qD1jZTQ'),(1380,'wx00f7d56d549f82ce',20160912092256,'component_verify_ticket','ticket@@@yWrNIU9gIixPl7lU8WFwiqx6fjNfIhNg6tLfrg-QxF68NpHokUfN_9WnFP2wH0GvvLwBxDRg326LXg5qD1jZTQ'),(1381,'wx00f7d56d549f82ce',20160912093317,'component_verify_ticket','ticket@@@yWrNIU9gIixPl7lU8WFwiqx6fjNfIhNg6tLfrg-QxF68NpHokUfN_9WnFP2wH0GvvLwBxDRg326LXg5qD1jZTQ'),(1382,'wx00f7d56d549f82ce',20160912094300,'component_verify_ticket','ticket@@@yWrNIU9gIixPl7lU8WFwiqx6fjNfIhNg6tLfrg-QxF68NpHokUfN_9WnFP2wH0GvvLwBxDRg326LXg5qD1jZTQ'),(1383,'wx00f7d56d549f82ce',20160912095259,'component_verify_ticket','ticket@@@yWrNIU9gIixPl7lU8WFwiqx6fjNfIhNg6tLfrg-QxF68NpHokUfN_9WnFP2wH0GvvLwBxDRg326LXg5qD1jZTQ'),(1384,'wx00f7d56d549f82ce',20160912100339,'component_verify_ticket','ticket@@@AlRnti_VE9kEvwZP4ps20KAe2yd4vXRpGx7ouIMUPkwfRjwR9GwQqD9kuS5Z753iO2SyrgBI7wrHuO9YVL-XGg'),(1385,'wx00f7d56d549f82ce',20160912101328,'component_verify_ticket','ticket@@@AlRnti_VE9kEvwZP4ps20KAe2yd4vXRpGx7ouIMUPkwfRjwR9GwQqD9kuS5Z753iO2SyrgBI7wrHuO9YVL-XGg'),(1386,'wx00f7d56d549f82ce',20160912102301,'component_verify_ticket','ticket@@@AlRnti_VE9kEvwZP4ps20KAe2yd4vXRpGx7ouIMUPkwfRjwR9GwQqD9kuS5Z753iO2SyrgBI7wrHuO9YVL-XGg'),(1387,'wx00f7d56d549f82ce',20160912103318,'component_verify_ticket','ticket@@@AlRnti_VE9kEvwZP4ps20KAe2yd4vXRpGx7ouIMUPkwfRjwR9GwQqD9kuS5Z753iO2SyrgBI7wrHuO9YVL-XGg'),(1388,'wx00f7d56d549f82ce',20160912104300,'component_verify_ticket','ticket@@@AlRnti_VE9kEvwZP4ps20KAe2yd4vXRpGx7ouIMUPkwfRjwR9GwQqD9kuS5Z753iO2SyrgBI7wrHuO9YVL-XGg'),(1389,'wx00f7d56d549f82ce',20160912105259,'component_verify_ticket','ticket@@@AlRnti_VE9kEvwZP4ps20KAe2yd4vXRpGx7ouIMUPkwfRjwR9GwQqD9kuS5Z753iO2SyrgBI7wrHuO9YVL-XGg'),(1390,'wx00f7d56d549f82ce',20160912110313,'component_verify_ticket','ticket@@@3khswq9fUbScM4fIY8gotJCQKZl4pIL36_aIDHd9E0K9rS1pZE4soW_0RiDzhVWYGP-7UwZ5l9wPwjRO0p3lwg'),(1391,'wx00f7d56d549f82ce',20160912111312,'component_verify_ticket','ticket@@@3khswq9fUbScM4fIY8gotJCQKZl4pIL36_aIDHd9E0K9rS1pZE4soW_0RiDzhVWYGP-7UwZ5l9wPwjRO0p3lwg'),(1392,'wx00f7d56d549f82ce',20160912112317,'component_verify_ticket','ticket@@@3khswq9fUbScM4fIY8gotJCQKZl4pIL36_aIDHd9E0K9rS1pZE4soW_0RiDzhVWYGP-7UwZ5l9wPwjRO0p3lwg'),(1393,'wx00f7d56d549f82ce',20160912113300,'component_verify_ticket','ticket@@@3khswq9fUbScM4fIY8gotJCQKZl4pIL36_aIDHd9E0K9rS1pZE4soW_0RiDzhVWYGP-7UwZ5l9wPwjRO0p3lwg'),(1394,'wx00f7d56d549f82ce',20160912114259,'component_verify_ticket','ticket@@@3khswq9fUbScM4fIY8gotJCQKZl4pIL36_aIDHd9E0K9rS1pZE4soW_0RiDzhVWYGP-7UwZ5l9wPwjRO0p3lwg'),(1395,'wx00f7d56d549f82ce',20160912115258,'component_verify_ticket','ticket@@@3khswq9fUbScM4fIY8gotJCQKZl4pIL36_aIDHd9E0K9rS1pZE4soW_0RiDzhVWYGP-7UwZ5l9wPwjRO0p3lwg'),(1396,'wx00f7d56d549f82ce',20160912120305,'component_verify_ticket','ticket@@@NFZUi5e0PiCSofa8TZEC94FYDTI1Y_nqE7SEIgxWmiQ1YicJBcsF2qxUZuV_XYYR706uKdSNrQhO4GmkpIo-0w'),(1397,'wx00f7d56d549f82ce',20160912121259,'component_verify_ticket','ticket@@@NFZUi5e0PiCSofa8TZEC94FYDTI1Y_nqE7SEIgxWmiQ1YicJBcsF2qxUZuV_XYYR706uKdSNrQhO4GmkpIo-0w'),(1398,'wx00f7d56d549f82ce',20160912122258,'component_verify_ticket','ticket@@@NFZUi5e0PiCSofa8TZEC94FYDTI1Y_nqE7SEIgxWmiQ1YicJBcsF2qxUZuV_XYYR706uKdSNrQhO4GmkpIo-0w'),(1399,'wx00f7d56d549f82ce',20160912123258,'component_verify_ticket','ticket@@@NFZUi5e0PiCSofa8TZEC94FYDTI1Y_nqE7SEIgxWmiQ1YicJBcsF2qxUZuV_XYYR706uKdSNrQhO4GmkpIo-0w'),(1400,'wx00f7d56d549f82ce',20160912124258,'component_verify_ticket','ticket@@@NFZUi5e0PiCSofa8TZEC94FYDTI1Y_nqE7SEIgxWmiQ1YicJBcsF2qxUZuV_XYYR706uKdSNrQhO4GmkpIo-0w'),(1401,'wx00f7d56d549f82ce',20160912125259,'component_verify_ticket','ticket@@@NFZUi5e0PiCSofa8TZEC94FYDTI1Y_nqE7SEIgxWmiQ1YicJBcsF2qxUZuV_XYYR706uKdSNrQhO4GmkpIo-0w'),(1402,'wx00f7d56d549f82ce',20160912130316,'component_verify_ticket','ticket@@@HHBe_0452gSzzFvFOnp6ThJ8VU47ZV9xQyAs0qWvrq_C-Z7ao-eXoC1WjPpWeTmWuVxEHvxX0lpniYuIqztSnA'),(1403,'wx00f7d56d549f82ce',20160912131302,'component_verify_ticket','ticket@@@HHBe_0452gSzzFvFOnp6ThJ8VU47ZV9xQyAs0qWvrq_C-Z7ao-eXoC1WjPpWeTmWuVxEHvxX0lpniYuIqztSnA'),(1404,'wx00f7d56d549f82ce',20160912132300,'component_verify_ticket','ticket@@@HHBe_0452gSzzFvFOnp6ThJ8VU47ZV9xQyAs0qWvrq_C-Z7ao-eXoC1WjPpWeTmWuVxEHvxX0lpniYuIqztSnA'),(1405,'wx00f7d56d549f82ce',20160912133300,'component_verify_ticket','ticket@@@HHBe_0452gSzzFvFOnp6ThJ8VU47ZV9xQyAs0qWvrq_C-Z7ao-eXoC1WjPpWeTmWuVxEHvxX0lpniYuIqztSnA'),(1406,'wx00f7d56d549f82ce',20160912134257,'component_verify_ticket','ticket@@@HHBe_0452gSzzFvFOnp6ThJ8VU47ZV9xQyAs0qWvrq_C-Z7ao-eXoC1WjPpWeTmWuVxEHvxX0lpniYuIqztSnA'),(1407,'wx00f7d56d549f82ce',20160912135306,'component_verify_ticket','ticket@@@HHBe_0452gSzzFvFOnp6ThJ8VU47ZV9xQyAs0qWvrq_C-Z7ao-eXoC1WjPpWeTmWuVxEHvxX0lpniYuIqztSnA'),(1408,'wx00f7d56d549f82ce',20160912140301,'component_verify_ticket','ticket@@@VD_VqJivn7sZ75XxVbuZBG7tbP5McqhSnFoeMWKokVWsauq_T7OHZfdFh8UHdkJrGYm6eD0xkO_KyNndjk43sA'),(1409,'wx00f7d56d549f82ce',20160912145258,'component_verify_ticket','ticket@@@VD_VqJivn7sZ75XxVbuZBG7tbP5McqhSnFoeMWKokVWsauq_T7OHZfdFh8UHdkJrGYm6eD0xkO_KyNndjk43sA'),(1410,'wx00f7d56d549f82ce',20160912150302,'component_verify_ticket','ticket@@@G1nOuSjxnVTVuT_64R5NkW94I3OKGjuZwLYtjvFmutH698n-hY8tfGH02xGgCtM_x427T0qKVp3ljYV8MlyJZQ'),(1411,'wx00f7d56d549f82ce',20160912151302,'component_verify_ticket','ticket@@@G1nOuSjxnVTVuT_64R5NkW94I3OKGjuZwLYtjvFmutH698n-hY8tfGH02xGgCtM_x427T0qKVp3ljYV8MlyJZQ'),(1412,'wx00f7d56d549f82ce',20160912152302,'component_verify_ticket','ticket@@@G1nOuSjxnVTVuT_64R5NkW94I3OKGjuZwLYtjvFmutH698n-hY8tfGH02xGgCtM_x427T0qKVp3ljYV8MlyJZQ'),(1413,'wx00f7d56d549f82ce',20160912153304,'component_verify_ticket','ticket@@@G1nOuSjxnVTVuT_64R5NkW94I3OKGjuZwLYtjvFmutH698n-hY8tfGH02xGgCtM_x427T0qKVp3ljYV8MlyJZQ'),(1414,'wx00f7d56d549f82ce',20160912154257,'component_verify_ticket','ticket@@@G1nOuSjxnVTVuT_64R5NkW94I3OKGjuZwLYtjvFmutH698n-hY8tfGH02xGgCtM_x427T0qKVp3ljYV8MlyJZQ'),(1415,'wx00f7d56d549f82ce',20160912155255,'component_verify_ticket','ticket@@@G1nOuSjxnVTVuT_64R5NkW94I3OKGjuZwLYtjvFmutH698n-hY8tfGH02xGgCtM_x427T0qKVp3ljYV8MlyJZQ'),(1416,'wx00f7d56d549f82ce',20160912160245,'component_verify_ticket','ticket@@@PF-sXVi_6ZkMmx2UJYn7A78BgWJcxHR6ZB032ms9OmjMs8L253J5gApK1Yv1JkGCBNA_-IPDjq1uqDfAVuChOg'),(1417,'wx00f7d56d549f82ce',20160912161258,'component_verify_ticket','ticket@@@PF-sXVi_6ZkMmx2UJYn7A78BgWJcxHR6ZB032ms9OmjMs8L253J5gApK1Yv1JkGCBNA_-IPDjq1uqDfAVuChOg'),(1418,'wx00f7d56d549f82ce',20160912162300,'component_verify_ticket','ticket@@@PF-sXVi_6ZkMmx2UJYn7A78BgWJcxHR6ZB032ms9OmjMs8L253J5gApK1Yv1JkGCBNA_-IPDjq1uqDfAVuChOg'),(1419,'wx00f7d56d549f82ce',20160912163303,'component_verify_ticket','ticket@@@PF-sXVi_6ZkMmx2UJYn7A78BgWJcxHR6ZB032ms9OmjMs8L253J5gApK1Yv1JkGCBNA_-IPDjq1uqDfAVuChOg'),(1420,'wx00f7d56d549f82ce',20160912164300,'component_verify_ticket','ticket@@@PF-sXVi_6ZkMmx2UJYn7A78BgWJcxHR6ZB032ms9OmjMs8L253J5gApK1Yv1JkGCBNA_-IPDjq1uqDfAVuChOg'),(1421,'wx00f7d56d549f82ce',20160912165300,'component_verify_ticket','ticket@@@PF-sXVi_6ZkMmx2UJYn7A78BgWJcxHR6ZB032ms9OmjMs8L253J5gApK1Yv1JkGCBNA_-IPDjq1uqDfAVuChOg'),(1422,'wx00f7d56d549f82ce',20160912170304,'component_verify_ticket','ticket@@@OAc6pZynj2Q0o36p3Sks795Vunnq4qF3nq15oEUQxU3bs3G6viRCraki_VuJD9ClqjUaIKxlZBxd_AypAPBSKA'),(1423,'wx00f7d56d549f82ce',20160912171643,'component_verify_ticket','ticket@@@OAc6pZynj2Q0o36p3Sks795Vunnq4qF3nq15oEUQxU3bs3G6viRCraki_VuJD9ClqjUaIKxlZBxd_AypAPBSKA'),(1424,'wx00f7d56d549f82ce',20160912172304,'component_verify_ticket','ticket@@@OAc6pZynj2Q0o36p3Sks795Vunnq4qF3nq15oEUQxU3bs3G6viRCraki_VuJD9ClqjUaIKxlZBxd_AypAPBSKA'),(1425,'wx00f7d56d549f82ce',20160912173301,'component_verify_ticket','ticket@@@OAc6pZynj2Q0o36p3Sks795Vunnq4qF3nq15oEUQxU3bs3G6viRCraki_VuJD9ClqjUaIKxlZBxd_AypAPBSKA'),(1426,'wx00f7d56d549f82ce',20160912174300,'component_verify_ticket','ticket@@@OAc6pZynj2Q0o36p3Sks795Vunnq4qF3nq15oEUQxU3bs3G6viRCraki_VuJD9ClqjUaIKxlZBxd_AypAPBSKA'),(1427,'wx00f7d56d549f82ce',20160912175302,'component_verify_ticket','ticket@@@OAc6pZynj2Q0o36p3Sks795Vunnq4qF3nq15oEUQxU3bs3G6viRCraki_VuJD9ClqjUaIKxlZBxd_AypAPBSKA'),(1428,'wx00f7d56d549f82ce',20160912180304,'component_verify_ticket','ticket@@@__JMI3wn9stL7E6GmxfgAHSm9s0DeYQAJkxw1Tu1VsgARO3sVJz6qY7OL5lvuPTN-VxI1ul45wTyeaTHs8d4uQ'),(1429,'wx00f7d56d549f82ce',20160912181302,'component_verify_ticket','ticket@@@__JMI3wn9stL7E6GmxfgAHSm9s0DeYQAJkxw1Tu1VsgARO3sVJz6qY7OL5lvuPTN-VxI1ul45wTyeaTHs8d4uQ'),(1430,'wx00f7d56d549f82ce',20160912182259,'component_verify_ticket','ticket@@@__JMI3wn9stL7E6GmxfgAHSm9s0DeYQAJkxw1Tu1VsgARO3sVJz6qY7OL5lvuPTN-VxI1ul45wTyeaTHs8d4uQ'),(1431,'wx00f7d56d549f82ce',20160912183255,'component_verify_ticket','ticket@@@__JMI3wn9stL7E6GmxfgAHSm9s0DeYQAJkxw1Tu1VsgARO3sVJz6qY7OL5lvuPTN-VxI1ul45wTyeaTHs8d4uQ'),(1432,'wx00f7d56d549f82ce',20160912184255,'component_verify_ticket','ticket@@@__JMI3wn9stL7E6GmxfgAHSm9s0DeYQAJkxw1Tu1VsgARO3sVJz6qY7OL5lvuPTN-VxI1ul45wTyeaTHs8d4uQ'),(1433,'wx00f7d56d549f82ce',20160912185256,'component_verify_ticket','ticket@@@__JMI3wn9stL7E6GmxfgAHSm9s0DeYQAJkxw1Tu1VsgARO3sVJz6qY7OL5lvuPTN-VxI1ul45wTyeaTHs8d4uQ'),(1434,'wx00f7d56d549f82ce',20160912190258,'component_verify_ticket','ticket@@@6CwUtdc4kKYTvLxfDMAUPiEteh2dFRjruu1wLdXv66hUuNzjCMsCVo1SifKpkHFTR8WP-bkh0QHgCbQYDD-HzQ'),(1435,'wx00f7d56d549f82ce',20160912191258,'component_verify_ticket','ticket@@@6CwUtdc4kKYTvLxfDMAUPiEteh2dFRjruu1wLdXv66hUuNzjCMsCVo1SifKpkHFTR8WP-bkh0QHgCbQYDD-HzQ'),(1436,'wx00f7d56d549f82ce',20160912192258,'component_verify_ticket','ticket@@@6CwUtdc4kKYTvLxfDMAUPiEteh2dFRjruu1wLdXv66hUuNzjCMsCVo1SifKpkHFTR8WP-bkh0QHgCbQYDD-HzQ'),(1437,'wx00f7d56d549f82ce',20160912193259,'component_verify_ticket','ticket@@@6CwUtdc4kKYTvLxfDMAUPiEteh2dFRjruu1wLdXv66hUuNzjCMsCVo1SifKpkHFTR8WP-bkh0QHgCbQYDD-HzQ'),(1438,'wx00f7d56d549f82ce',20160912194606,'component_verify_ticket','ticket@@@6CwUtdc4kKYTvLxfDMAUPiEteh2dFRjruu1wLdXv66hUuNzjCMsCVo1SifKpkHFTR8WP-bkh0QHgCbQYDD-HzQ'),(1439,'wx00f7d56d549f82ce',20160912195359,'component_verify_ticket','ticket@@@6CwUtdc4kKYTvLxfDMAUPiEteh2dFRjruu1wLdXv66hUuNzjCMsCVo1SifKpkHFTR8WP-bkh0QHgCbQYDD-HzQ'),(1440,'wx00f7d56d549f82ce',20160912200259,'component_verify_ticket','ticket@@@-nxu8bjVpudxcK6EHVbq6TZB9tmDJ_lEktaJnCfQCqYXVNEJOZeBa_LMi1s0_4U5spdZfxJRkc0Rge8bcsRnGA'),(1441,'wx00f7d56d549f82ce',20160912201303,'component_verify_ticket','ticket@@@-nxu8bjVpudxcK6EHVbq6TZB9tmDJ_lEktaJnCfQCqYXVNEJOZeBa_LMi1s0_4U5spdZfxJRkc0Rge8bcsRnGA'),(1442,'wx00f7d56d549f82ce',20160912202253,'component_verify_ticket','ticket@@@-nxu8bjVpudxcK6EHVbq6TZB9tmDJ_lEktaJnCfQCqYXVNEJOZeBa_LMi1s0_4U5spdZfxJRkc0Rge8bcsRnGA'),(1443,'wx00f7d56d549f82ce',20160912203255,'component_verify_ticket','ticket@@@-nxu8bjVpudxcK6EHVbq6TZB9tmDJ_lEktaJnCfQCqYXVNEJOZeBa_LMi1s0_4U5spdZfxJRkc0Rge8bcsRnGA'),(1444,'wx00f7d56d549f82ce',20160912204255,'component_verify_ticket','ticket@@@-nxu8bjVpudxcK6EHVbq6TZB9tmDJ_lEktaJnCfQCqYXVNEJOZeBa_LMi1s0_4U5spdZfxJRkc0Rge8bcsRnGA'),(1445,'wx00f7d56d549f82ce',20160912205609,'component_verify_ticket','ticket@@@-nxu8bjVpudxcK6EHVbq6TZB9tmDJ_lEktaJnCfQCqYXVNEJOZeBa_LMi1s0_4U5spdZfxJRkc0Rge8bcsRnGA'),(1446,'wx00f7d56d549f82ce',20160912210429,'component_verify_ticket','ticket@@@NV6CbLcDr8m93Aq8aszh2eDfl04HvL_fO0eJAwgKlgNLxPhxeh1hrspXTunLywqoOCITM62NMFYtNMNK7wN2bg'),(1447,'wx00f7d56d549f82ce',20160912211301,'component_verify_ticket','ticket@@@NV6CbLcDr8m93Aq8aszh2eDfl04HvL_fO0eJAwgKlgNLxPhxeh1hrspXTunLywqoOCITM62NMFYtNMNK7wN2bg'),(1448,'wx00f7d56d549f82ce',20160912212300,'component_verify_ticket','ticket@@@NV6CbLcDr8m93Aq8aszh2eDfl04HvL_fO0eJAwgKlgNLxPhxeh1hrspXTunLywqoOCITM62NMFYtNMNK7wN2bg'),(1449,'wx00f7d56d549f82ce',20160912213300,'component_verify_ticket','ticket@@@NV6CbLcDr8m93Aq8aszh2eDfl04HvL_fO0eJAwgKlgNLxPhxeh1hrspXTunLywqoOCITM62NMFYtNMNK7wN2bg'),(1450,'wx00f7d56d549f82ce',20160912214259,'component_verify_ticket','ticket@@@NV6CbLcDr8m93Aq8aszh2eDfl04HvL_fO0eJAwgKlgNLxPhxeh1hrspXTunLywqoOCITM62NMFYtNMNK7wN2bg'),(1451,'wx00f7d56d549f82ce',20160912215255,'component_verify_ticket','ticket@@@NV6CbLcDr8m93Aq8aszh2eDfl04HvL_fO0eJAwgKlgNLxPhxeh1hrspXTunLywqoOCITM62NMFYtNMNK7wN2bg'),(1452,'wx00f7d56d549f82ce',20160912220307,'component_verify_ticket','ticket@@@WEONe83cUdp8Zjp5_ur4xa9kuyc_l3vQAlkaNkvELm8vJgopdyu1-AGT9cLHLir64IIOpln_qqaNC3FWxNppLQ'),(1453,'wx00f7d56d549f82ce',20160912221257,'component_verify_ticket','ticket@@@WEONe83cUdp8Zjp5_ur4xa9kuyc_l3vQAlkaNkvELm8vJgopdyu1-AGT9cLHLir64IIOpln_qqaNC3FWxNppLQ'),(1454,'wx00f7d56d549f82ce',20160912222257,'component_verify_ticket','ticket@@@WEONe83cUdp8Zjp5_ur4xa9kuyc_l3vQAlkaNkvELm8vJgopdyu1-AGT9cLHLir64IIOpln_qqaNC3FWxNppLQ'),(1455,'wx00f7d56d549f82ce',20160912223255,'component_verify_ticket','ticket@@@WEONe83cUdp8Zjp5_ur4xa9kuyc_l3vQAlkaNkvELm8vJgopdyu1-AGT9cLHLir64IIOpln_qqaNC3FWxNppLQ'),(1456,'wx00f7d56d549f82ce',20160912224256,'component_verify_ticket','ticket@@@WEONe83cUdp8Zjp5_ur4xa9kuyc_l3vQAlkaNkvELm8vJgopdyu1-AGT9cLHLir64IIOpln_qqaNC3FWxNppLQ'),(1457,'wx00f7d56d549f82ce',20160912225254,'component_verify_ticket','ticket@@@WEONe83cUdp8Zjp5_ur4xa9kuyc_l3vQAlkaNkvELm8vJgopdyu1-AGT9cLHLir64IIOpln_qqaNC3FWxNppLQ'),(1458,'wx00f7d56d549f82ce',20160912230255,'component_verify_ticket','ticket@@@uD4MXXGIwEdlsJw4YmBpeEE9kA225oP4NPfvb-ex2d-QVMElw8Dqaqh-aXp6GdYmsR6ZU9x1UhTe3u-kGmsA0w'),(1459,'wx00f7d56d549f82ce',20160912231257,'component_verify_ticket','ticket@@@uD4MXXGIwEdlsJw4YmBpeEE9kA225oP4NPfvb-ex2d-QVMElw8Dqaqh-aXp6GdYmsR6ZU9x1UhTe3u-kGmsA0w'),(1460,'wx00f7d56d549f82ce',20160912232255,'component_verify_ticket','ticket@@@uD4MXXGIwEdlsJw4YmBpeEE9kA225oP4NPfvb-ex2d-QVMElw8Dqaqh-aXp6GdYmsR6ZU9x1UhTe3u-kGmsA0w'),(1461,'wx00f7d56d549f82ce',20160912233257,'component_verify_ticket','ticket@@@uD4MXXGIwEdlsJw4YmBpeEE9kA225oP4NPfvb-ex2d-QVMElw8Dqaqh-aXp6GdYmsR6ZU9x1UhTe3u-kGmsA0w'),(1462,'wx00f7d56d549f82ce',20160912234254,'component_verify_ticket','ticket@@@uD4MXXGIwEdlsJw4YmBpeEE9kA225oP4NPfvb-ex2d-QVMElw8Dqaqh-aXp6GdYmsR6ZU9x1UhTe3u-kGmsA0w'),(1463,'wx00f7d56d549f82ce',20160912235251,'component_verify_ticket','ticket@@@uD4MXXGIwEdlsJw4YmBpeEE9kA225oP4NPfvb-ex2d-QVMElw8Dqaqh-aXp6GdYmsR6ZU9x1UhTe3u-kGmsA0w'),(1464,'wx00f7d56d549f82ce',20160913000257,'component_verify_ticket','ticket@@@UW1MaFCH3VeFfGvuI0joSyDQXKb7Q9wYqEzC0wSAs565MEWNA_VW738G3uklK2w87PF9aTGP3U6c2FSvrD8a8Q'),(1465,'wx00f7d56d549f82ce',20160913001254,'component_verify_ticket','ticket@@@UW1MaFCH3VeFfGvuI0joSyDQXKb7Q9wYqEzC0wSAs565MEWNA_VW738G3uklK2w87PF9aTGP3U6c2FSvrD8a8Q'),(1466,'wx00f7d56d549f82ce',20160913002253,'component_verify_ticket','ticket@@@UW1MaFCH3VeFfGvuI0joSyDQXKb7Q9wYqEzC0wSAs565MEWNA_VW738G3uklK2w87PF9aTGP3U6c2FSvrD8a8Q'),(1467,'wx00f7d56d549f82ce',20160913003255,'component_verify_ticket','ticket@@@UW1MaFCH3VeFfGvuI0joSyDQXKb7Q9wYqEzC0wSAs565MEWNA_VW738G3uklK2w87PF9aTGP3U6c2FSvrD8a8Q'),(1468,'wx00f7d56d549f82ce',20160913004253,'component_verify_ticket','ticket@@@UW1MaFCH3VeFfGvuI0joSyDQXKb7Q9wYqEzC0wSAs565MEWNA_VW738G3uklK2w87PF9aTGP3U6c2FSvrD8a8Q'),(1469,'wx00f7d56d549f82ce',20160913005250,'component_verify_ticket','ticket@@@UW1MaFCH3VeFfGvuI0joSyDQXKb7Q9wYqEzC0wSAs565MEWNA_VW738G3uklK2w87PF9aTGP3U6c2FSvrD8a8Q'),(1470,'wx00f7d56d549f82ce',20160913010248,'component_verify_ticket','ticket@@@PnXKuW6p9BLW2V2kc1MfFCPtxMvUk2usnr2_AWu0l8aWtDnFvG-gUCPwrX_tcxseQ2VuTH1dpUHXuK1pzwd0NA'),(1471,'wx00f7d56d549f82ce',20160913011250,'component_verify_ticket','ticket@@@PnXKuW6p9BLW2V2kc1MfFCPtxMvUk2usnr2_AWu0l8aWtDnFvG-gUCPwrX_tcxseQ2VuTH1dpUHXuK1pzwd0NA'),(1472,'wx00f7d56d549f82ce',20160913012251,'component_verify_ticket','ticket@@@PnXKuW6p9BLW2V2kc1MfFCPtxMvUk2usnr2_AWu0l8aWtDnFvG-gUCPwrX_tcxseQ2VuTH1dpUHXuK1pzwd0NA'),(1473,'wx00f7d56d549f82ce',20160913013251,'component_verify_ticket','ticket@@@PnXKuW6p9BLW2V2kc1MfFCPtxMvUk2usnr2_AWu0l8aWtDnFvG-gUCPwrX_tcxseQ2VuTH1dpUHXuK1pzwd0NA'),(1474,'wx00f7d56d549f82ce',20160913014250,'component_verify_ticket','ticket@@@PnXKuW6p9BLW2V2kc1MfFCPtxMvUk2usnr2_AWu0l8aWtDnFvG-gUCPwrX_tcxseQ2VuTH1dpUHXuK1pzwd0NA'),(1475,'wx00f7d56d549f82ce',20160913015250,'component_verify_ticket','ticket@@@PnXKuW6p9BLW2V2kc1MfFCPtxMvUk2usnr2_AWu0l8aWtDnFvG-gUCPwrX_tcxseQ2VuTH1dpUHXuK1pzwd0NA'),(1476,'wx00f7d56d549f82ce',20160913020254,'component_verify_ticket','ticket@@@nkcWBuhpzjpgyLHoylzSOm06bUiQ4diiO3qBr41KaYDAjf3ZUtTFrFMMiSGf3wyGm7bOJLYmJbnodzbIx4mFgw'),(1477,'wx00f7d56d549f82ce',20160913021252,'component_verify_ticket','ticket@@@nkcWBuhpzjpgyLHoylzSOm06bUiQ4diiO3qBr41KaYDAjf3ZUtTFrFMMiSGf3wyGm7bOJLYmJbnodzbIx4mFgw'),(1478,'wx00f7d56d549f82ce',20160913022250,'component_verify_ticket','ticket@@@nkcWBuhpzjpgyLHoylzSOm06bUiQ4diiO3qBr41KaYDAjf3ZUtTFrFMMiSGf3wyGm7bOJLYmJbnodzbIx4mFgw'),(1479,'wx00f7d56d549f82ce',20160913023249,'component_verify_ticket','ticket@@@nkcWBuhpzjpgyLHoylzSOm06bUiQ4diiO3qBr41KaYDAjf3ZUtTFrFMMiSGf3wyGm7bOJLYmJbnodzbIx4mFgw'),(1480,'wx00f7d56d549f82ce',20160913024242,'component_verify_ticket','ticket@@@nkcWBuhpzjpgyLHoylzSOm06bUiQ4diiO3qBr41KaYDAjf3ZUtTFrFMMiSGf3wyGm7bOJLYmJbnodzbIx4mFgw'),(1481,'wx00f7d56d549f82ce',20160913025249,'component_verify_ticket','ticket@@@nkcWBuhpzjpgyLHoylzSOm06bUiQ4diiO3qBr41KaYDAjf3ZUtTFrFMMiSGf3wyGm7bOJLYmJbnodzbIx4mFgw'),(1482,'wx00f7d56d549f82ce',20160913030252,'component_verify_ticket','ticket@@@DNXQcNRhUUyhhf65uGH_HHWEiFZvT-8-IRZSiXUy5nleXSfLEZ6mOlORUdp6wPzRSYRk7t3kB2TbdXwRu0DtCA'),(1483,'wx00f7d56d549f82ce',20160913031249,'component_verify_ticket','ticket@@@DNXQcNRhUUyhhf65uGH_HHWEiFZvT-8-IRZSiXUy5nleXSfLEZ6mOlORUdp6wPzRSYRk7t3kB2TbdXwRu0DtCA'),(1484,'wx00f7d56d549f82ce',20160913032250,'component_verify_ticket','ticket@@@DNXQcNRhUUyhhf65uGH_HHWEiFZvT-8-IRZSiXUy5nleXSfLEZ6mOlORUdp6wPzRSYRk7t3kB2TbdXwRu0DtCA'),(1485,'wx00f7d56d549f82ce',20160913033251,'component_verify_ticket','ticket@@@DNXQcNRhUUyhhf65uGH_HHWEiFZvT-8-IRZSiXUy5nleXSfLEZ6mOlORUdp6wPzRSYRk7t3kB2TbdXwRu0DtCA'),(1486,'wx00f7d56d549f82ce',20160913034250,'component_verify_ticket','ticket@@@DNXQcNRhUUyhhf65uGH_HHWEiFZvT-8-IRZSiXUy5nleXSfLEZ6mOlORUdp6wPzRSYRk7t3kB2TbdXwRu0DtCA'),(1487,'wx00f7d56d549f82ce',20160913035249,'component_verify_ticket','ticket@@@DNXQcNRhUUyhhf65uGH_HHWEiFZvT-8-IRZSiXUy5nleXSfLEZ6mOlORUdp6wPzRSYRk7t3kB2TbdXwRu0DtCA'),(1488,'wx00f7d56d549f82ce',20160913040248,'component_verify_ticket','ticket@@@iHf5caMuOWibCZ54l2_wRX3Gk_NPkUhjGbOyP-TXO5y96wIovyoVbeZfHzPYZmfIt9V1LpvZgjUzXQuwBKjerw'),(1489,'wx00f7d56d549f82ce',20160913041252,'component_verify_ticket','ticket@@@iHf5caMuOWibCZ54l2_wRX3Gk_NPkUhjGbOyP-TXO5y96wIovyoVbeZfHzPYZmfIt9V1LpvZgjUzXQuwBKjerw'),(1490,'wx00f7d56d549f82ce',20160913042251,'component_verify_ticket','ticket@@@iHf5caMuOWibCZ54l2_wRX3Gk_NPkUhjGbOyP-TXO5y96wIovyoVbeZfHzPYZmfIt9V1LpvZgjUzXQuwBKjerw'),(1491,'wx00f7d56d549f82ce',20160913043250,'component_verify_ticket','ticket@@@iHf5caMuOWibCZ54l2_wRX3Gk_NPkUhjGbOyP-TXO5y96wIovyoVbeZfHzPYZmfIt9V1LpvZgjUzXQuwBKjerw'),(1492,'wx00f7d56d549f82ce',20160913044250,'component_verify_ticket','ticket@@@iHf5caMuOWibCZ54l2_wRX3Gk_NPkUhjGbOyP-TXO5y96wIovyoVbeZfHzPYZmfIt9V1LpvZgjUzXQuwBKjerw'),(1493,'wx00f7d56d549f82ce',20160913045251,'component_verify_ticket','ticket@@@iHf5caMuOWibCZ54l2_wRX3Gk_NPkUhjGbOyP-TXO5y96wIovyoVbeZfHzPYZmfIt9V1LpvZgjUzXQuwBKjerw'),(1494,'wx00f7d56d549f82ce',20160913050247,'component_verify_ticket','ticket@@@eGz8is7T1a4KDmdRl8-10nP9A18_W0NIUUWt55nJurQWNaaS6tv6CLQgKb4lPm3L_wXYctwteczl5e7tE4xXMg'),(1495,'wx00f7d56d549f82ce',20160913051252,'component_verify_ticket','ticket@@@eGz8is7T1a4KDmdRl8-10nP9A18_W0NIUUWt55nJurQWNaaS6tv6CLQgKb4lPm3L_wXYctwteczl5e7tE4xXMg'),(1496,'wx00f7d56d549f82ce',20160913052244,'component_verify_ticket','ticket@@@eGz8is7T1a4KDmdRl8-10nP9A18_W0NIUUWt55nJurQWNaaS6tv6CLQgKb4lPm3L_wXYctwteczl5e7tE4xXMg'),(1497,'wx00f7d56d549f82ce',20160913053246,'component_verify_ticket','ticket@@@eGz8is7T1a4KDmdRl8-10nP9A18_W0NIUUWt55nJurQWNaaS6tv6CLQgKb4lPm3L_wXYctwteczl5e7tE4xXMg'),(1498,'wx00f7d56d549f82ce',20160913054242,'component_verify_ticket','ticket@@@eGz8is7T1a4KDmdRl8-10nP9A18_W0NIUUWt55nJurQWNaaS6tv6CLQgKb4lPm3L_wXYctwteczl5e7tE4xXMg'),(1499,'wx00f7d56d549f82ce',20160913055249,'component_verify_ticket','ticket@@@eGz8is7T1a4KDmdRl8-10nP9A18_W0NIUUWt55nJurQWNaaS6tv6CLQgKb4lPm3L_wXYctwteczl5e7tE4xXMg'),(1500,'wx00f7d56d549f82ce',20160913060247,'component_verify_ticket','ticket@@@Q7qUml3usj01DgtHjfjo1VrEylZNpfuffcdIvN9fBgkXwAET2LH0poO_tevms8PGQcLiFhQus3cqcunK3-enNQ'),(1501,'wx00f7d56d549f82ce',20160913061249,'component_verify_ticket','ticket@@@Q7qUml3usj01DgtHjfjo1VrEylZNpfuffcdIvN9fBgkXwAET2LH0poO_tevms8PGQcLiFhQus3cqcunK3-enNQ'),(1502,'wx00f7d56d549f82ce',20160913062248,'component_verify_ticket','ticket@@@Q7qUml3usj01DgtHjfjo1VrEylZNpfuffcdIvN9fBgkXwAET2LH0poO_tevms8PGQcLiFhQus3cqcunK3-enNQ'),(1503,'wx00f7d56d549f82ce',20160913063249,'component_verify_ticket','ticket@@@Q7qUml3usj01DgtHjfjo1VrEylZNpfuffcdIvN9fBgkXwAET2LH0poO_tevms8PGQcLiFhQus3cqcunK3-enNQ'),(1504,'wx00f7d56d549f82ce',20160913064241,'component_verify_ticket','ticket@@@Q7qUml3usj01DgtHjfjo1VrEylZNpfuffcdIvN9fBgkXwAET2LH0poO_tevms8PGQcLiFhQus3cqcunK3-enNQ'),(1505,'wx00f7d56d549f82ce',20160913065240,'component_verify_ticket','ticket@@@Q7qUml3usj01DgtHjfjo1VrEylZNpfuffcdIvN9fBgkXwAET2LH0poO_tevms8PGQcLiFhQus3cqcunK3-enNQ'),(1506,'wx00f7d56d549f82ce',20160913070249,'component_verify_ticket','ticket@@@w-kdrLpdgBacOvaQNn9KCBUqIOXG6o4z0wgFxwK11BUc4Lio3eFcxO1sjNbvjcJrBDY_7_fQ5axdaLVqGL0M2w'),(1507,'wx00f7d56d549f82ce',20160913071248,'component_verify_ticket','ticket@@@w-kdrLpdgBacOvaQNn9KCBUqIOXG6o4z0wgFxwK11BUc4Lio3eFcxO1sjNbvjcJrBDY_7_fQ5axdaLVqGL0M2w'),(1508,'wx00f7d56d549f82ce',20160913072241,'component_verify_ticket','ticket@@@w-kdrLpdgBacOvaQNn9KCBUqIOXG6o4z0wgFxwK11BUc4Lio3eFcxO1sjNbvjcJrBDY_7_fQ5axdaLVqGL0M2w'),(1509,'wx00f7d56d549f82ce',20160913073248,'component_verify_ticket','ticket@@@w-kdrLpdgBacOvaQNn9KCBUqIOXG6o4z0wgFxwK11BUc4Lio3eFcxO1sjNbvjcJrBDY_7_fQ5axdaLVqGL0M2w'),(1510,'wx00f7d56d549f82ce',20160913074248,'component_verify_ticket','ticket@@@w-kdrLpdgBacOvaQNn9KCBUqIOXG6o4z0wgFxwK11BUc4Lio3eFcxO1sjNbvjcJrBDY_7_fQ5axdaLVqGL0M2w'),(1511,'wx00f7d56d549f82ce',20160913075248,'component_verify_ticket','ticket@@@w-kdrLpdgBacOvaQNn9KCBUqIOXG6o4z0wgFxwK11BUc4Lio3eFcxO1sjNbvjcJrBDY_7_fQ5axdaLVqGL0M2w'),(1512,'wx00f7d56d549f82ce',20160913080255,'component_verify_ticket','ticket@@@oC3t2oq8fAK2o_imrOs8Cde0zibyc8Li38HW-F_VUuqTM-asuf0o7Nj1WGg9LcFCmirxPdvygbPq8tbZFr01CQ'),(1513,'wx00f7d56d549f82ce',20160913081251,'component_verify_ticket','ticket@@@oC3t2oq8fAK2o_imrOs8Cde0zibyc8Li38HW-F_VUuqTM-asuf0o7Nj1WGg9LcFCmirxPdvygbPq8tbZFr01CQ'),(1514,'wx00f7d56d549f82ce',20160913082252,'component_verify_ticket','ticket@@@oC3t2oq8fAK2o_imrOs8Cde0zibyc8Li38HW-F_VUuqTM-asuf0o7Nj1WGg9LcFCmirxPdvygbPq8tbZFr01CQ'),(1515,'wx00f7d56d549f82ce',20160913083254,'component_verify_ticket','ticket@@@oC3t2oq8fAK2o_imrOs8Cde0zibyc8Li38HW-F_VUuqTM-asuf0o7Nj1WGg9LcFCmirxPdvygbPq8tbZFr01CQ'),(1516,'wx00f7d56d549f82ce',20160913084253,'component_verify_ticket','ticket@@@oC3t2oq8fAK2o_imrOs8Cde0zibyc8Li38HW-F_VUuqTM-asuf0o7Nj1WGg9LcFCmirxPdvygbPq8tbZFr01CQ'),(1517,'wx00f7d56d549f82ce',20160913085243,'component_verify_ticket','ticket@@@oC3t2oq8fAK2o_imrOs8Cde0zibyc8Li38HW-F_VUuqTM-asuf0o7Nj1WGg9LcFCmirxPdvygbPq8tbZFr01CQ'),(1518,'wx00f7d56d549f82ce',20160913090307,'component_verify_ticket','ticket@@@FdwKi4RIu8CZIVjQMlBHFGiVa9hM7E8df5qIs6Da4rSYiQUdDW8QXa_yopopNbP9HJxBVp-hGD-0pkRi6UjDjA'),(1519,'wx00f7d56d549f82ce',20160913091256,'component_verify_ticket','ticket@@@FdwKi4RIu8CZIVjQMlBHFGiVa9hM7E8df5qIs6Da4rSYiQUdDW8QXa_yopopNbP9HJxBVp-hGD-0pkRi6UjDjA'),(1520,'wx00f7d56d549f82ce',20160913092256,'component_verify_ticket','ticket@@@FdwKi4RIu8CZIVjQMlBHFGiVa9hM7E8df5qIs6Da4rSYiQUdDW8QXa_yopopNbP9HJxBVp-hGD-0pkRi6UjDjA'),(1521,'wx00f7d56d549f82ce',20160913093300,'component_verify_ticket','ticket@@@FdwKi4RIu8CZIVjQMlBHFGiVa9hM7E8df5qIs6Da4rSYiQUdDW8QXa_yopopNbP9HJxBVp-hGD-0pkRi6UjDjA'),(1522,'wx00f7d56d549f82ce',20160913094300,'component_verify_ticket','ticket@@@FdwKi4RIu8CZIVjQMlBHFGiVa9hM7E8df5qIs6Da4rSYiQUdDW8QXa_yopopNbP9HJxBVp-hGD-0pkRi6UjDjA'),(1523,'wx00f7d56d549f82ce',20160913095255,'component_verify_ticket','ticket@@@FdwKi4RIu8CZIVjQMlBHFGiVa9hM7E8df5qIs6Da4rSYiQUdDW8QXa_yopopNbP9HJxBVp-hGD-0pkRi6UjDjA'),(1524,'wx00f7d56d549f82ce',20160913100304,'component_verify_ticket','ticket@@@95G6jScrxgQ_NezWjqO8c7QMUUD0PwuLQeCtnqI4mFe-aaKKRHwpAvz-u0IpiVZfi6wGUHiknl_Epvr6FpqN9A'),(1525,'wx00f7d56d549f82ce',20160913101258,'component_verify_ticket','ticket@@@95G6jScrxgQ_NezWjqO8c7QMUUD0PwuLQeCtnqI4mFe-aaKKRHwpAvz-u0IpiVZfi6wGUHiknl_Epvr6FpqN9A'),(1526,'wx00f7d56d549f82ce',20160913102259,'component_verify_ticket','ticket@@@95G6jScrxgQ_NezWjqO8c7QMUUD0PwuLQeCtnqI4mFe-aaKKRHwpAvz-u0IpiVZfi6wGUHiknl_Epvr6FpqN9A'),(1527,'wx00f7d56d549f82ce',20160913103259,'component_verify_ticket','ticket@@@95G6jScrxgQ_NezWjqO8c7QMUUD0PwuLQeCtnqI4mFe-aaKKRHwpAvz-u0IpiVZfi6wGUHiknl_Epvr6FpqN9A'),(1528,'wx00f7d56d549f82ce',20160913104256,'component_verify_ticket','ticket@@@95G6jScrxgQ_NezWjqO8c7QMUUD0PwuLQeCtnqI4mFe-aaKKRHwpAvz-u0IpiVZfi6wGUHiknl_Epvr6FpqN9A'),(1529,'wx00f7d56d549f82ce',20160913105256,'component_verify_ticket','ticket@@@95G6jScrxgQ_NezWjqO8c7QMUUD0PwuLQeCtnqI4mFe-aaKKRHwpAvz-u0IpiVZfi6wGUHiknl_Epvr6FpqN9A'),(1530,'wx00f7d56d549f82ce',20160913110253,'component_verify_ticket','ticket@@@tboFiw-MoEPmNovDXCnNhYu4SeAcu180M06kkgXjFryDvFBlJCDE24kyU29oVNvZVWmgTAzwJCBf86BlTsPtSg'),(1531,'wx00f7d56d549f82ce',20160913111302,'component_verify_ticket','ticket@@@tboFiw-MoEPmNovDXCnNhYu4SeAcu180M06kkgXjFryDvFBlJCDE24kyU29oVNvZVWmgTAzwJCBf86BlTsPtSg'),(1532,'wx00f7d56d549f82ce',20160913112302,'component_verify_ticket','ticket@@@tboFiw-MoEPmNovDXCnNhYu4SeAcu180M06kkgXjFryDvFBlJCDE24kyU29oVNvZVWmgTAzwJCBf86BlTsPtSg'),(1533,'wx00f7d56d549f82ce',20160913113300,'component_verify_ticket','ticket@@@tboFiw-MoEPmNovDXCnNhYu4SeAcu180M06kkgXjFryDvFBlJCDE24kyU29oVNvZVWmgTAzwJCBf86BlTsPtSg'),(1534,'wx00f7d56d549f82ce',20160913114255,'component_verify_ticket','ticket@@@tboFiw-MoEPmNovDXCnNhYu4SeAcu180M06kkgXjFryDvFBlJCDE24kyU29oVNvZVWmgTAzwJCBf86BlTsPtSg'),(1535,'wx00f7d56d549f82ce',20160913115256,'component_verify_ticket','ticket@@@tboFiw-MoEPmNovDXCnNhYu4SeAcu180M06kkgXjFryDvFBlJCDE24kyU29oVNvZVWmgTAzwJCBf86BlTsPtSg'),(1536,'wx00f7d56d549f82ce',20160913120303,'component_verify_ticket','ticket@@@clFIiqjj6nldzvW3qBmLTVMxnY9otsIoNWotbXP4rfCM5snJp2Csgm159JVUAGs2ncUkeppbIxXBBPEvBm3PbQ'),(1537,'wx00f7d56d549f82ce',20160913121300,'component_verify_ticket','ticket@@@clFIiqjj6nldzvW3qBmLTVMxnY9otsIoNWotbXP4rfCM5snJp2Csgm159JVUAGs2ncUkeppbIxXBBPEvBm3PbQ'),(1538,'wx00f7d56d549f82ce',20160913122256,'component_verify_ticket','ticket@@@clFIiqjj6nldzvW3qBmLTVMxnY9otsIoNWotbXP4rfCM5snJp2Csgm159JVUAGs2ncUkeppbIxXBBPEvBm3PbQ'),(1539,'wx00f7d56d549f82ce',20160913123258,'component_verify_ticket','ticket@@@clFIiqjj6nldzvW3qBmLTVMxnY9otsIoNWotbXP4rfCM5snJp2Csgm159JVUAGs2ncUkeppbIxXBBPEvBm3PbQ'),(1540,'wx00f7d56d549f82ce',20160913124258,'component_verify_ticket','ticket@@@clFIiqjj6nldzvW3qBmLTVMxnY9otsIoNWotbXP4rfCM5snJp2Csgm159JVUAGs2ncUkeppbIxXBBPEvBm3PbQ'),(1541,'wx00f7d56d549f82ce',20160913125256,'component_verify_ticket','ticket@@@clFIiqjj6nldzvW3qBmLTVMxnY9otsIoNWotbXP4rfCM5snJp2Csgm159JVUAGs2ncUkeppbIxXBBPEvBm3PbQ'),(1542,'wx00f7d56d549f82ce',20160913130303,'component_verify_ticket','ticket@@@z8-Wi4XQBmleAb9Pg2hjH_ipIINApcJ5fC5RIgyNX0j7CBxh_sQJNSokRebv7_nBbuRYvZ7VuVrqNK9PIAz_zg'),(1543,'wx00f7d56d549f82ce',20160913131246,'component_verify_ticket','ticket@@@z8-Wi4XQBmleAb9Pg2hjH_ipIINApcJ5fC5RIgyNX0j7CBxh_sQJNSokRebv7_nBbuRYvZ7VuVrqNK9PIAz_zg'),(1544,'wx00f7d56d549f82ce',20160913132259,'component_verify_ticket','ticket@@@z8-Wi4XQBmleAb9Pg2hjH_ipIINApcJ5fC5RIgyNX0j7CBxh_sQJNSokRebv7_nBbuRYvZ7VuVrqNK9PIAz_zg'),(1545,'wx00f7d56d549f82ce',20160913133256,'component_verify_ticket','ticket@@@z8-Wi4XQBmleAb9Pg2hjH_ipIINApcJ5fC5RIgyNX0j7CBxh_sQJNSokRebv7_nBbuRYvZ7VuVrqNK9PIAz_zg'),(1546,'wx00f7d56d549f82ce',20160913134248,'component_verify_ticket','ticket@@@z8-Wi4XQBmleAb9Pg2hjH_ipIINApcJ5fC5RIgyNX0j7CBxh_sQJNSokRebv7_nBbuRYvZ7VuVrqNK9PIAz_zg'),(1547,'wx00f7d56d549f82ce',20160913135248,'component_verify_ticket','ticket@@@z8-Wi4XQBmleAb9Pg2hjH_ipIINApcJ5fC5RIgyNX0j7CBxh_sQJNSokRebv7_nBbuRYvZ7VuVrqNK9PIAz_zg'),(1548,'wx00f7d56d549f82ce',20160913140256,'component_verify_ticket','ticket@@@gt2lqC7_sQOKRlo4IcEOnsz_RVqx6We_7n7LRUdyAXJKIoWAO7ffnRoUURMwpAn226kuuwtz5Ost2a1Qce46WQ'),(1549,'wx00f7d56d549f82ce',20160913141256,'component_verify_ticket','ticket@@@gt2lqC7_sQOKRlo4IcEOnsz_RVqx6We_7n7LRUdyAXJKIoWAO7ffnRoUURMwpAn226kuuwtz5Ost2a1Qce46WQ'),(1550,'wx00f7d56d549f82ce',20160913142252,'component_verify_ticket','ticket@@@gt2lqC7_sQOKRlo4IcEOnsz_RVqx6We_7n7LRUdyAXJKIoWAO7ffnRoUURMwpAn226kuuwtz5Ost2a1Qce46WQ'),(1551,'wx00f7d56d549f82ce',20160913143257,'component_verify_ticket','ticket@@@gt2lqC7_sQOKRlo4IcEOnsz_RVqx6We_7n7LRUdyAXJKIoWAO7ffnRoUURMwpAn226kuuwtz5Ost2a1Qce46WQ'),(1552,'wx00f7d56d549f82ce',20160913144255,'component_verify_ticket','ticket@@@gt2lqC7_sQOKRlo4IcEOnsz_RVqx6We_7n7LRUdyAXJKIoWAO7ffnRoUURMwpAn226kuuwtz5Ost2a1Qce46WQ'),(1553,'wx00f7d56d549f82ce',20160913145255,'component_verify_ticket','ticket@@@gt2lqC7_sQOKRlo4IcEOnsz_RVqx6We_7n7LRUdyAXJKIoWAO7ffnRoUURMwpAn226kuuwtz5Ost2a1Qce46WQ'),(1554,'wx00f7d56d549f82ce',20160913150301,'component_verify_ticket','ticket@@@3RBhGHNDeWt3Gbn_FluHunFKAlN1U2q8D4bHMbXL0LMuaKwqiTG3KYtzeE4sPltxN8rXwQtUdfKehiy9gGJfKg'),(1555,'wx00f7d56d549f82ce',20160913151259,'component_verify_ticket','ticket@@@3RBhGHNDeWt3Gbn_FluHunFKAlN1U2q8D4bHMbXL0LMuaKwqiTG3KYtzeE4sPltxN8rXwQtUdfKehiy9gGJfKg');
/*!40000 ALTER TABLE `webchat_component_verify_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_asset`
--

DROP TABLE IF EXISTS `wechat_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL DEFAULT '0',
  `asset_type` int(11) NOT NULL DEFAULT '0' COMMENT '0:����� 1:���˺� 2:���ĺ�',
  `asset_name` varchar(500) DEFAULT NULL COMMENT '�ʲ�����',
  `nickname` varchar(500) DEFAULT NULL COMMENT '�ǳ�',
  `wx_acct` varchar(200) DEFAULT NULL COMMENT '΢���˺�',
  `consignation_time` datetime DEFAULT NULL,
  `total_count` int(11) DEFAULT NULL,
  `group_ids` varchar(1000) DEFAULT NULL,
  `imgfile_url` varchar(1000) DEFAULT NULL COMMENT 'ͷ������ͼ',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `app_id` varchar(22) NOT NULL,
  `wechat_qrcode` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_wx_acct` (`wx_acct`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_asset`
--

LOCK TABLES `wechat_asset` WRITE;
/*!40000 ALTER TABLE `wechat_asset` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_asset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_asset_group`
--

DROP TABLE IF EXISTS `wechat_asset_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_asset_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `import_group_id` bigint(20) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL COMMENT 'Ⱥ������',
  `members` int(11) DEFAULT NULL COMMENT 'Ⱥ������',
  `wx_acct` varchar(500) DEFAULT NULL COMMENT '��Ӧwechat_asset.wx_acct',
  `is_sys_group` int(11) DEFAULT NULL,
  `asset_id` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_asset_group`
--

LOCK TABLES `wechat_asset_group` WRITE;
/*!40000 ALTER TABLE `wechat_asset_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_asset_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_channel`
--

DROP TABLE IF EXISTS `wechat_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '�������',
  `ch_name` varchar(100) NOT NULL COMMENT '��������',
  `status` varchar(1) NOT NULL DEFAULT '0' COMMENT '״̬\n0-����\n1-ɾ��',
  `type` int(11) DEFAULT '0' COMMENT '��������\n0-ϵͳĬ������\n1-�Զ�������',
  `is_removed` int(11) DEFAULT '0' COMMENT '0-����ɾ��\n1-����ɾ��',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ch_name` (`ch_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢�Ŷ�ά������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_channel`
--

LOCK TABLES `wechat_channel` WRITE;
/*!40000 ALTER TABLE `wechat_channel` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_channel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_group`
--

DROP TABLE IF EXISTS `wechat_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Ⱥ����',
  `group_id` varchar(100) DEFAULT NULL COMMENT 'H5���ص�group_id',
  `group_name` varchar(500) DEFAULT NULL COMMENT 'Ⱥ�����ƣ�����ȫ���Ե�������',
  `group_nickname` varchar(500) DEFAULT NULL COMMENT 'Ⱥ��ı��',
  `header_image` varchar(500) DEFAULT NULL COMMENT 'Ⱥ��ͷ������ͼ',
  `wx_acct` varchar(500) NOT NULL COMMENT '��Ӧ΢�źţ�wechat_register.wx_acct\n',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `count` int(11) DEFAULT NULL COMMENT 'Ⱥ������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢��Ⱥ����Ϣ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_group`
--

LOCK TABLES `wechat_group` WRITE;
/*!40000 ALTER TABLE `wechat_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_member`
--

DROP TABLE IF EXISTS `wechat_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '����',
  `wx_code` varchar(200) DEFAULT NULL COMMENT '��Ա΢���˺ţ�\n���ںţ�openid\n���˺� ��ucode',
  `wx_name` varchar(200) DEFAULT NULL COMMENT '��Ա΢������',
  `wx_group_id` varchar(200) DEFAULT NULL COMMENT '����΢��Ⱥ���ID��wechat_group.id',
  `nickname` varchar(1000) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `country` varchar(500) DEFAULT NULL,
  `province` varchar(500) DEFAULT NULL,
  `city` varchar(500) DEFAULT NULL,
  `county` varchar(500) DEFAULT NULL COMMENT 'λ��',
  `birthday` varchar(500) DEFAULT NULL,
  `signature` varchar(1000) DEFAULT NULL COMMENT 'ǩ��',
  `is_friend` varchar(45) DEFAULT NULL COMMENT '�Ƿ����',
  `pub_id` varchar(200) DEFAULT NULL COMMENT '���ں�Ψһ��ʶ',
  `uin` varchar(200) DEFAULT NULL COMMENT '���˺�Ψһ��ʶ',
  `subscribe_yn` varchar(200) DEFAULT NULL,
  `subscribe_time` varchar(500) DEFAULT NULL,
  `active_time` varchar(500) DEFAULT NULL,
  `activity_48h_yn` varchar(200) DEFAULT NULL,
  `head_image_url` varchar(1000) DEFAULT NULL,
  `remark` varchar(5000) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `selected` tinyint(4) NOT NULL DEFAULT '0' COMMENT '��data_party�����ͬ��״̬',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢�ų�Ա(���ѡ���˿)��Ϣ��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_member`
--

LOCK TABLES `wechat_member` WRITE;
/*!40000 ALTER TABLE `wechat_member` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_member_count_cache`
--

DROP TABLE IF EXISTS `wechat_member_count_cache`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_member_count_cache` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `wechat_asset_type` int(11) NOT NULL,
  `wechat_asset_type_member_count` int(11) NOT NULL DEFAULT '0',
  `count_time` varchar(10) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_member_count_cache`
--

LOCK TABLES `wechat_member_count_cache` WRITE;
/*!40000 ALTER TABLE `wechat_member_count_cache` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_member_count_cache` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_personal_uuid`
--

DROP TABLE IF EXISTS `wechat_personal_uuid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_personal_uuid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) NOT NULL,
  `status` int(10) unsigned zerofill NOT NULL DEFAULT '0000000000' COMMENT 'uuid��״̬����ЧΪ0����ЧΪ1',
  `uin` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid_index` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_personal_uuid`
--

LOCK TABLES `wechat_personal_uuid` WRITE;
/*!40000 ALTER TABLE `wechat_personal_uuid` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_personal_uuid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_qrcode`
--

DROP TABLE IF EXISTS `wechat_qrcode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_name` varchar(100) DEFAULT NULL COMMENT '΢�Ź��ں�����',
  `wx_acct` varchar(500) DEFAULT NULL COMMENT '΢���˺�\n���˺ţ� uin\n�����/���ĺţ�weixin_id',
  `ch_code` int(11) DEFAULT NULL COMMENT '�������\nwechat_channel.id',
  `is_audience` tinyint(4) DEFAULT NULL COMMENT '�Ƿ��½��̶���Ⱥ\n0-��\n1-��',
  `audience_name` varchar(100) DEFAULT NULL COMMENT '�̶���Ⱥ����\n',
  `related_tags` varchar(2000) DEFAULT NULL COMMENT '����ϵͳ��ǩID�������ǩID����;�ָ������磺\n101;102;103',
  `comments` varchar(100) DEFAULT NULL COMMENT '��ע',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `expiration_time` datetime DEFAULT NULL COMMENT 'ʧЧʱ��',
  `status` tinyint(4) DEFAULT NULL COMMENT '״̬\n0-δ��\n1-����\n2-ɾ��3-����',
  `qrcode_pic` varchar(128) DEFAULT NULL COMMENT '��ά��ͼƬ�ļ�����',
  `qrcode_url` varchar(128) DEFAULT NULL COMMENT '��ά��url',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���κţ�����������ά��ı��',
  `ticket` text,
  `qrcode_name` varchar(128) DEFAULT NULL COMMENT '��ά������',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='΢�Ź��ںŶ�ά��';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_qrcode`
--

LOCK TABLES `wechat_qrcode` WRITE;
/*!40000 ALTER TABLE `wechat_qrcode` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_qrcode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_qrcode_focus`
--

DROP TABLE IF EXISTS `wechat_qrcode_focus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode_focus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qrcode_id` varchar(45) DEFAULT NULL COMMENT '��ά����',
  `wx_name` varchar(100) DEFAULT NULL COMMENT '΢�Ź��ں�����',
  `ch_code` int(11) DEFAULT NULL COMMENT '�������\nwechat_channel.id',
  `openid` varchar(128) DEFAULT NULL COMMENT '��ע��openid',
  `focus_datetime` datetime DEFAULT NULL COMMENT '��עʱ��',
  `unfocus_datetime` datetime DEFAULT NULL COMMENT 'ȡ����עʱ��',
  `focus_status` tinyint(4) DEFAULT NULL COMMENT '0:��עʱ�� 1:ȡ����עʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��ע��ά���¼';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_qrcode_focus`
--

LOCK TABLES `wechat_qrcode_focus` WRITE;
/*!40000 ALTER TABLE `wechat_qrcode_focus` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_qrcode_focus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_qrcode_log`
--

DROP TABLE IF EXISTS `wechat_qrcode_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source_filename` varchar(128) DEFAULT NULL COMMENT '����Դ�ļ�����',
  `total_rows` int(11) DEFAULT NULL COMMENT '������',
  `success` tinyint(4) DEFAULT NULL COMMENT '�ɹ�����',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-ʧ��\n1-�ɹ�',
  `create_time` datetime DEFAULT NULL,
  `batch_id` varchar(45) DEFAULT NULL COMMENT '���κţ�����������ά��ı��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_qrcode_log`
--

LOCK TABLES `wechat_qrcode_log` WRITE;
/*!40000 ALTER TABLE `wechat_qrcode_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_qrcode_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_qrcode_scan`
--

DROP TABLE IF EXISTS `wechat_qrcode_scan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode_scan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL COMMENT '�û�ID',
  `user_host` varchar(500) DEFAULT NULL COMMENT 'ɨ���û��ֻ�host name',
  `qrcode_id` int(11) DEFAULT NULL COMMENT '��ά�볡����ţ�wechat_qrcode.id',
  `create_time` datetime DEFAULT NULL COMMENT 'ɨ��ʱ��',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='��¼ɨ�����������';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_qrcode_scan`
--

LOCK TABLES `wechat_qrcode_scan` WRITE;
/*!40000 ALTER TABLE `wechat_qrcode_scan` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_qrcode_scan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_qrcode_ticket`
--

DROP TABLE IF EXISTS `wechat_qrcode_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ticket` text COMMENT '��ȡ�Ķ�ά��ticket��ƾ���ticket��������Чʱ���ڻ�ȡ��ά��',
  `url` varchar(128) DEFAULT NULL COMMENT '��ά��ͼƬ������ĵ�ַ�������߿ɸ��ݸõ�ַ����������Ҫ�Ķ�ά��ͼƬ',
  `scene_id` int(11) DEFAULT NULL COMMENT '����ֵID',
  `create_time` datetime DEFAULT NULL COMMENT '����ʱ��',
  `state` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_qrcode_ticket`
--

LOCK TABLES `wechat_qrcode_ticket` WRITE;
/*!40000 ALTER TABLE `wechat_qrcode_ticket` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_qrcode_ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wechat_register`
--

DROP TABLE IF EXISTS `wechat_register`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_register` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_acct` varchar(500) NOT NULL COMMENT '΢���˺�\n���˺ţ� uin\n�����/���ĺţ�weixin_id',
  `name` varchar(500) DEFAULT NULL COMMENT '΢��(���ˡ����ġ������)����',
  `type` int(11) NOT NULL DEFAULT '-1' COMMENT '΢�Ž�������\n0-�����\n1-���˺�\n2-���ĺ�\n3-δ��֤�����\n4-δ��֤���ĺ�',
  `nickname` varchar(500) DEFAULT NULL COMMENT '���˺ŵ��ǳ�',
  `header_image` varchar(1000) DEFAULT NULL COMMENT '���˺ŵ�ͷ������ͼ',
  `sex` varchar(10) DEFAULT NULL COMMENT '���˺ŵ��Ա�',
  `province` varchar(50) DEFAULT NULL COMMENT '���˺ŵ�ʡ',
  `city` varchar(50) DEFAULT NULL COMMENT '���˺ŵ���',
  `signature` varchar(100) DEFAULT NULL COMMENT '���˺ŵ�ǩ������',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT 'ɾ�����',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `app_id` varchar(22) NOT NULL,
  `wechat_qrcode` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wechat_register`
--

LOCK TABLES `wechat_register` WRITE;
/*!40000 ALTER TABLE `wechat_register` DISABLE KEYS */;
/*!40000 ALTER TABLE `wechat_register` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-13 15:14:03
-- ���������Ǳ�΢�Ŷ�ά���Ҫ���޸�Ϊwechat_qrcode_focus�����һ�������洢΢�Ź��ں�
ALTER TABLE wechat_qrcode_focus add column wxmp_id varchar(45) DEFAULT null COMMENT '΢�Ź��ں�ΨһID';








