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
-- Table structure for table `audience_columns`
--

DROP TABLE IF EXISTS `audience_columns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audience_columns` (
  `id` int(5) unsigned NOT NULL DEFAULT '1',
  `field_name` varchar(45) NOT NULL COMMENT '列名',
  `field_code` varchar(45) NOT NULL COMMENT '英文列名，对应data_app等表的column',
  `field_order` tinyint(4) NOT NULL DEFAULT '0' COMMENT '顺序',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人群列表列名';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audience_list`
--

DROP TABLE IF EXISTS `audience_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audience_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '人群编号',
  `audience_name` varchar(45) DEFAULT NULL COMMENT '人群名称',
  `audience_rows` int(45) DEFAULT '0' COMMENT '人群包含联系人数',
  `source` varchar(512) DEFAULT NULL COMMENT '来源',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL COMMENT '产生时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='人群管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `audience_list_party_map`
--

DROP TABLE IF EXISTS `audience_list_party_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `audience_list_party_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `audience_list_id` int(11) NOT NULL COMMENT '人群ID',
  `party_id` int(11) NOT NULL COMMENT '主数据(联系人)ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0-未删除  1-删除 ',
  `oper` varchar(45) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人群管理详细信息，某个人群包含的联系人\n';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_action_save_audience`
--

DROP TABLE IF EXISTS `campaign_action_save_audience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_save_audience` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL COMMENT '动作名称',
  `audience_id` int(11) DEFAULT NULL COMMENT 'audience_list表里的id',
  `audience_name` varchar(45) DEFAULT NULL COMMENT 'audience_list表里的audience_name',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记,0:未删除，1：已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='动作属性表:保存人群';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_action_send_h5`
--

DROP TABLE IF EXISTS `campaign_action_send_h5`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_send_h5` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL COMMENT '动作名称',
  `pub_asset_id` int(11) DEFAULT NULL COMMENT '对应wechat_asset表的asset_id',
  `prv_asset_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL COMMENT '关联wechat_asset_group表的id,不限群组，则传null',
  `img_text_asset_id` int(11) DEFAULT NULL COMMENT '对应img_text_asset表的id',
  `pub_id` varchar(512) DEFAULT NULL COMMENT '对应大连那边的公众号pub_id',
  `uin` varchar(512) DEFAULT NULL COMMENT '对应大连那边的个人号uin',
  `ucode` varchar(512) DEFAULT NULL COMMENT '对应大连的群组ucode',
  `material_id` int(11) DEFAULT NULL COMMENT '对应大连那边的material_id',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='动作属性表:发送h5图文';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_action_send_privt`
--

DROP TABLE IF EXISTS `campaign_action_send_privt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_send_privt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL COMMENT '节点名称',
  `uin` varchar(200) DEFAULT NULL COMMENT '对应大连的个人号uin',
  `asset_id` int(11) DEFAULT NULL COMMENT '关联wechat_asset表的asset_id',
  `ucode` varchar(500) DEFAULT NULL COMMENT '对应大连的群组ucode',
  `group_id` int(11) DEFAULT NULL COMMENT '关联wechat_asset_group表的id,不限群组，则传null',
  `text_info` varchar(2048) DEFAULT NULL COMMENT '要发送的文本信息',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='动作属性表:发送个人号信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_action_send_pub`
--

DROP TABLE IF EXISTS `campaign_action_send_pub`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_send_pub` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `material_id` int(11) DEFAULT NULL COMMENT '对应大连的material_id',
  `img_text_asset_id` int(11) DEFAULT NULL COMMENT 'img_text_asset表的id',
  `pub_id` varchar(512) DEFAULT NULL COMMENT '对应大连的公众号pub_id',
  `asset_id` int(11) DEFAULT NULL COMMENT '对应wechat_asset表的asset_id',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='动作属性表:发送公众号消息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_action_set_tag`
--

DROP TABLE IF EXISTS `campaign_action_set_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_set_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `tag_ids` varchar(512) DEFAULT NULL COMMENT '对应custom_tag表的id,多个用逗号分隔',
  `tag_names` varchar(512) DEFAULT NULL COMMENT '自定义标签名，多个用逗号分隔',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='动作属性表:设置标签 ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_action_wait`
--

DROP TABLE IF EXISTS `campaign_action_wait`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_action_wait` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `type` tinyint(4) DEFAULT NULL COMMENT '0:相对时间1:指定时间',
  `relative_value` int(11) DEFAULT NULL,
  `relative_type` tinyint(4) DEFAULT NULL COMMENT '0:小时，1：天，2：周，3：月',
  `specific_time` datetime DEFAULT NULL COMMENT '指定时间',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='动作属性表:等待';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_audience_target`
--

DROP TABLE IF EXISTS `campaign_audience_target`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_audience_target` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `segmentation_id` int(11) DEFAULT NULL,
  `segmentation_name` varchar(200) DEFAULT NULL,
  `allowed_new` tinyint(4) DEFAULT '0' COMMENT '0:允许,1:不允许',
  `refresh_interval` int(11) DEFAULT NULL,
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:小时,1:天,2:周,3:月',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8 COMMENT='目标人群';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_body`
--

DROP TABLE IF EXISTS `campaign_body`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_body` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `head_id` int(11) DEFAULT NULL,
  `node_type` tinyint(4) DEFAULT '0' COMMENT '0:触发,1:受众,2决策,3行动,4:分支线',
  `item_type` tinyint(4) DEFAULT NULL COMMENT '0,1,2,3...按照原型里出现的顺序',
  `item_id` varchar(200) DEFAULT NULL COMMENT '关联具体节点表的业务id',
  `statistics_url` varchar(1024) DEFAULT NULL COMMENT '报表url',
  `pos_x` varchar(20) DEFAULT NULL COMMENT '组件x坐标',
  `pos_y` varchar(20) DEFAULT NULL COMMENT '组件y坐标',
  `pos_z` varchar(20) DEFAULT NULL,
  `audience_count` int(11) DEFAULT '0' COMMENT '当前节点受众人数',
  `task_id` int(11) DEFAULT NULL COMMENT '关联task_schedule表的id',
  `description` varchar(512) DEFAULT NULL COMMENT '节点描述',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `head_item_unq` (`head_id`,`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='营销活动body表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_columns`
--

DROP TABLE IF EXISTS `campaign_columns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_columns` (
  `id` int(5) unsigned NOT NULL DEFAULT '1',
  `field_name` varchar(45) NOT NULL COMMENT '列名',
  `field_code` varchar(45) NOT NULL COMMENT '英文列名',
  `field_order` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='活动列表中文列名';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_decision_prop_compare`
--

DROP TABLE IF EXISTS `campaign_decision_prop_compare`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_prop_compare` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `prop_type` tinyint(4) DEFAULT NULL COMMENT '属性类型,0:文本,1:数字,2:日期',
  `rule` tinyint(4) DEFAULT NULL COMMENT '0：等于,1:包含2:starts_with，3：ends_with4:empty,5:大于,6:小于,7:大于等于,8:小于等于',
  `exclude` tinyint(4) DEFAULT NULL COMMENT '0:不选中"不",1:选中“不”',
  `value` varchar(1024) DEFAULT NULL COMMENT '输入的文本',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系人属性比较表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_decision_prvt_friends`
--

DROP TABLE IF EXISTS `campaign_decision_prvt_friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_prvt_friends` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `uin` varchar(200) DEFAULT NULL COMMENT '对应大连那边的uin',
  `asset_id` int(11) DEFAULT NULL COMMENT '关联wechat_asset表的asset_id',
  `ucode` varchar(500) DEFAULT NULL COMMENT '对应大连的群组ucode',
  `group_id` int(11) DEFAULT NULL COMMENT '关联wechat_asset_group表的id,不限群组,传null',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '状态刷新频率数值',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:小时,1:天,2;周,3:月',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='是否是个人号好友';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_decision_pub_fans`
--

DROP TABLE IF EXISTS `campaign_decision_pub_fans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_pub_fans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `pub_id` varchar(500) DEFAULT NULL COMMENT '对应大连的公众号pub_id',
  `asset_id` int(11) DEFAULT NULL COMMENT '公众号名称,对应wechat_asset表的asset_id',
  `subscribe_time` tinyint(4) DEFAULT NULL COMMENT '订阅时间,0:一天，1：一周，2：一月，3：三月，4：六月，5：一年，6：一年以上',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '状态刷新频率数值',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:小时,1:天,2;周,3:月,4:分钟',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='是否订阅了公众号';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_decision_tag`
--

DROP TABLE IF EXISTS `campaign_decision_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `campaign_head_id` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `rule` tinyint(4) DEFAULT '0' COMMENT '0:全部匹配，1：匹配其一，2：匹配二个及以上',
  `tag_ids` varchar(1024) DEFAULT NULL COMMENT '标签id,多个用逗号分隔',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='标签判断';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_decision_tag_map`
--

DROP TABLE IF EXISTS `campaign_decision_tag_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_tag_map` (
  `id` int(11) NOT NULL DEFAULT '0',
  `campaign_decision_tag_id` int(11) DEFAULT NULL COMMENT '关联campaign_decision_tag表的id',
  `tag_id` int(11) DEFAULT NULL,
  `tag_name` varchar(200) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='campaign_decision_tag和tag的关联表\n该表停止使用,tag信息放到campaign_decision_tag表的tag_ids列里';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_decision_wechat_forward`
--

DROP TABLE IF EXISTS `campaign_decision_wechat_forward`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_wechat_forward` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `asset_id` int(11) DEFAULT NULL COMMENT '对应wechat_asset表的asset_id',
  `pub_id` varchar(512) DEFAULT NULL COMMENT '对的大连的公众号pub_id',
  `img_text_asset_id` int(11) DEFAULT NULL COMMENT 'img_text_asset表的id',
  `material_id` int(11) DEFAULT NULL COMMENT '对应大连的material_id',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '送达状态刷新频率数值',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:小时,1:天,2;周,3:月',
  `forward_times` tinyint(4) DEFAULT NULL COMMENT '转发次数,0:不限,1:超一人次，2：超十，3：超一百，4：超五百',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='微信图文是否转发';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_decision_wechat_read`
--

DROP TABLE IF EXISTS `campaign_decision_wechat_read`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_wechat_read` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `asset_id` int(11) DEFAULT NULL COMMENT '对应wechat_asset表的asset_id',
  `pub_id` varchar(512) DEFAULT NULL COMMENT '对的大连的公众号pub_id',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '状态刷新频率数值',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:小时,1:天,2;周,3:月',
  `img_text_asset_id` int(11) DEFAULT NULL COMMENT 'img_text_asset表的id',
  `material_id` int(11) DEFAULT NULL COMMENT '对应大连的material_id',
  `read_time` tinyint(4) DEFAULT NULL COMMENT '查看时长,0:不限,1:超一分钟,2:超三分钟,3:超五分钟,4:超十分钟',
  `read_percent` tinyint(4) DEFAULT NULL COMMENT '查看完成度，0：不限，1：超一半，2:全部阅读',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='微信图文是否查看';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_decision_wechat_sent`
--

DROP TABLE IF EXISTS `campaign_decision_wechat_sent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_decision_wechat_sent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，由前端生成',
  `name` varchar(200) DEFAULT NULL,
  `pub_id` varchar(1024) DEFAULT NULL COMMENT '公众号id',
  `pub_name` varchar(1024) DEFAULT NULL COMMENT '公众号名称',
  `refresh_interval` int(11) DEFAULT NULL COMMENT '送达状态刷新频率数值',
  `refresh_interval_type` tinyint(4) DEFAULT '0' COMMENT '0:小时,1:天,2;周,3:月',
  `wechat_h5_id` int(11) DEFAULT NULL,
  `wechat_h5_name` varchar(1024) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='微信图文是否发送\n大连接口不支持，暂时停用该表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_head`
--

DROP TABLE IF EXISTS `campaign_head`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_head` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `publish_status` tinyint(4) DEFAULT '0' COMMENT '0:未发布,1:已发布,2:活动中,3:已结束',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='营销活动head表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_node_item`
--

DROP TABLE IF EXISTS `campaign_node_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_node_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ptype` tinyint(4) NOT NULL COMMENT 'ptype=-1表示该元素为父节点类型',
  `type` tinyint(4) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  `icon` varchar(45) DEFAULT NULL,
  `display_index` int(11) DEFAULT NULL COMMENT '页面上显示的顺序',
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
-- Table structure for table `campaign_switch`
--

DROP TABLE IF EXISTS `campaign_switch`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_switch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(255) DEFAULT NULL COMMENT '关联campaign_body表中的item_id',
  `type` tinyint(4) DEFAULT NULL COMMENT '分支类型:0:是分支,1:非分支,2:多条件分支',
  `draw_type` tinyint(4) DEFAULT '0' COMMENT '连线类型,0:curveTriangle',
  `color` varchar(100) DEFAULT NULL,
  `next_item_id` varchar(255) DEFAULT NULL COMMENT '关联campaign_body表中的item_id',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='营销活动中的连线表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `campaign_trigger_timer`
--

DROP TABLE IF EXISTS `campaign_trigger_timer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campaign_trigger_timer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `campaign_head_id` int(11) DEFAULT NULL,
  `item_id` varchar(200) DEFAULT NULL COMMENT '业务id，前端生成',
  `name` varchar(100) DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='定时触发器表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact_list`
--

DROP TABLE IF EXISTS `contact_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_list` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别\n1-男\n2-女 \n3-未确定 \n4-不确定',
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `provice` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL COMMENT '职业',
  `monthly_income` decimal(22,2) DEFAULT NULL COMMENT '月收入',
  `monthly_consume` decimal(22,2) DEFAULT NULL COMMENT '月均消费',
  `marital_status` varchar(45) DEFAULT NULL COMMENT '婚姻状况',
  `education` varchar(45) DEFAULT NULL COMMENT '教育程度',
  `employment` varchar(45) DEFAULT NULL COMMENT '就业情况',
  `nationality` varchar(45) DEFAULT NULL COMMENT '民族',
  `blood_type` varchar(45) DEFAULT NULL COMMENT '血型',
  `citizenship` varchar(45) DEFAULT NULL COMMENT '国籍',
  `iq` int(11) DEFAULT NULL,
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:未导入主数据 1:已经为数据填写了bitmap 2:数据已经进入主数据表 3:删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime NOT NULL DEFAULT '1970-01-01 01:00:00' COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
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
  `commit_time` datetime DEFAULT NULL COMMENT '提交时间',
  `fill_device` varchar(45) DEFAULT NULL COMMENT '填写设备',
  `OS` varchar(45) DEFAULT NULL COMMENT '操作系统',
  `brower` varchar(45) DEFAULT NULL COMMENT '浏览器',
  `IP` varchar(45) DEFAULT NULL COMMENT 'IP',
  `wx_nickname` varchar(45) DEFAULT NULL COMMENT '微信-昵称',
  `wx_gender` varchar(45) DEFAULT NULL COMMENT '微信-性别',
  `wx_openid` varchar(45) DEFAULT NULL COMMENT '微信-openid',
  `wx_header_url` varchar(45) DEFAULT NULL COMMENT '微信-头像',
  `wx_country` varchar(45) DEFAULT NULL COMMENT '微信-国家',
  `wx_city` varchar(45) DEFAULT NULL COMMENT '微信-城市',
  `contact_templ_id` int(11) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `download_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact_template`
--

DROP TABLE IF EXISTS `contact_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_id` bigint(11) DEFAULT NULL,
  `contact_name` varchar(45) DEFAULT NULL COMMENT '表单名称',
  `contact_title` varchar(50) DEFAULT NULL COMMENT '标题',
  `contact_descript` varchar(100) DEFAULT NULL COMMENT '描述',
  `field_name` varchar(45) DEFAULT NULL COMMENT '列名',
  `field_code` varchar(45) DEFAULT NULL COMMENT '英文列名，对应contact_list表的column',
  `field_type` int(11) DEFAULT NULL,
  `selected` tinyint(4) DEFAULT '1' COMMENT '选中 0-未选中 1-选中',
  `status` tinyint(4) DEFAULT '0' COMMENT '0-未启用\n1-启用\n2-删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `qrcode_url` varchar(128) DEFAULT NULL COMMENT '二维码\n包含内容为：新建表单的url',
  `qrcode_pic` varchar(128) DEFAULT NULL COMMENT '二维码图片文件名称',
  `page_views` int(11) DEFAULT '0' COMMENT '浏览次数',
  `key_list` varchar(500) DEFAULT NULL COMMENT '主键名称，例如\n姓名,电话\n',
  `required` int(11) DEFAULT NULL COMMENT '0:非必须 1:必须',
  `ischecked` int(11) DEFAULT NULL COMMENT '0:不校验 1:校验',
  `is_remember_import_key` tinyint(4) DEFAULT '0' COMMENT '0:没有记住默认的导入key 1:已经是默认的导入key',
  `field_index` int(11) DEFAULT NULL COMMENT '联系人模板的显示顺序',
  `is_shown_in_feedback` tinyint(4) DEFAULT '0' COMMENT '0:显示 1:不显示',
  `del_status` tinyint(4) DEFAULT '0' COMMENT '0:正常显示 1:逻辑删除',
  `qrcode_shorturl` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '二维码短链接\\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8 COMMENT='联系人表单模板';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系方式';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact_way_map`
--

DROP TABLE IF EXISTS `contact_way_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_way_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_way_id` int(11) NOT NULL COMMENT 'contact_way对应的Id',
  `contact_way_name` varchar(45) DEFAULT NULL,
  `status` tinyint(1) DEFAULT '0' COMMENT '0为未勾选,1为勾选',
  `time_condition` datetime DEFAULT NULL,
  `time_condition_abbreviation` varchar(1) DEFAULT NULL COMMENT '缩写H:小时 , D:天 , W:周',
  `time_condition_status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `custom_tag`
--

DROP TABLE IF EXISTS `custom_tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签编号',
  `name` varchar(40) DEFAULT NULL COMMENT '标签名称',
  `cover_audience_count` int(11) DEFAULT NULL COMMENT '覆盖人群数量',
  `oper` varchar(45) DEFAULT NULL COMMENT '操作者',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记 0-未删除 1-删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义标签（细分/活动/联系人）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `custom_tag_map`
--

DROP TABLE IF EXISTS `custom_tag_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_tag_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_id` int(20) NOT NULL COMMENT '标签编号',
  `type` tinyint(40) DEFAULT NULL COMMENT '类型：0-细分 1-活动 2-联系人档案  3-文件接入 4-客户上传标签',
  `map_id` int(11) DEFAULT NULL COMMENT 'tag标识的细分/活动/联系人的ID',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标识 0-未删除 1-删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='自定义标签映射表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `custom_tag_original_data_map`
--

DROP TABLE IF EXISTS `custom_tag_original_data_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_tag_original_data_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_id` int(11) NOT NULL,
  `original_data_type` int(11) NOT NULL,
  `original_data_id` int(11) NOT NULL COMMENT '上传数据的主键ID',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_arch_point`
--

DROP TABLE IF EXISTS `data_arch_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_arch_point` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `event_id` int(11) DEFAULT NULL COMMENT '事件ID',
  `event_name` varchar(45) DEFAULT NULL COMMENT '事件名称',
  `click_time` datetime DEFAULT NULL COMMENT '点击时间',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `cookie` varchar(128) DEFAULT NULL,
  `batch_id` varchar(45) NOT NULL DEFAULT '' COMMENT '数据导入批次iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `event_id_click_time_unq` (`event_id`,`click_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='埋点统计';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_customer_tags`
--

DROP TABLE IF EXISTS `data_customer_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_customer_tags` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_type` varchar(45) DEFAULT NULL,
  `tag_name` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `identify_no` varchar(19) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT '1970-06-30 16:00:00' COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_data_customer_tags_tag_type_tag_name` (`tag_type`,`tag_name`) COMMENT '客户名称和客户类型唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户标签';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_login`
--

DROP TABLE IF EXISTS `data_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_login` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `login_type` varchar(100) DEFAULT NULL COMMENT '登录方式',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `logout_time` datetime DEFAULT NULL COMMENT '退出时间',
  `login_ip` varchar(45) DEFAULT NULL COMMENT '登录IP',
  `login_device` varchar(45) DEFAULT NULL COMMENT '登录设备',
  `resolution_ratio` varchar(100) DEFAULT NULL COMMENT '分辨率',
  `login_url` varchar(100) DEFAULT NULL COMMENT '登录页面',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_data_login_login_time` (`login_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录行为';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_member`
--

DROP TABLE IF EXISTS `data_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_member` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` varchar(45) DEFAULT NULL COMMENT '会员卡号',
  `member_points` varchar(10) DEFAULT NULL COMMENT '会员积分',
  `member_level` varchar(45) DEFAULT NULL COMMENT '会员等级',
  `regist_time` date DEFAULT NULL COMMENT '开卡时间',
  `card_amt` decimal(22,2) DEFAULT NULL COMMENT '卡内余额',
  `expire` date DEFAULT NULL COMMENT '过期日',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_memberid` (`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员卡记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_option_map`
--

DROP TABLE IF EXISTS `data_option_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_option_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) NOT NULL COMMENT '数据表对应的Id',
  `table_name` varchar(45) NOT NULL COMMENT '数据表的名字',
  `option_status` tinyint(1) DEFAULT '0' COMMENT '数据表的状态 : 0为未勾选,1为勾选',
  PRIMARY KEY (`id`),
  UNIQUE KEY `table_name_UNIQUE` (`table_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_party`
--

DROP TABLE IF EXISTS `data_party`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_party` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id,主数据ID,主数据生成时产生的ID',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `name` varchar(200) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性别\n1-男\n2-女 \n3-未确定 \n4-不确定',
  `birthday` date DEFAULT NULL COMMENT '年龄',
  `citizenship` varchar(45) DEFAULT NULL,
  `provice` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL COMMENT '职业',
  `monthly_income` decimal(22,2) DEFAULT NULL COMMENT '月收入',
  `member_level` varchar(10) DEFAULT NULL COMMENT '会员等级',
  `member_points` varchar(11) DEFAULT NULL COMMENT '会员积分',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `monthly_consume` decimal(22,2) DEFAULT NULL COMMENT '月均消费',
  `last_login` datetime DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `md_type` int(11) DEFAULT NULL COMMENT '主数据类型\\n0-主数据\\n8-微信',
  `mapping_keyid` varchar(100) DEFAULT NULL COMMENT '主数据ID对应的keyid，如：手机号、身份证号、微信openid等',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `email` varchar(45) DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(45) DEFAULT NULL,
  `identify_no` varchar(45) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `wxmp_id` varchar(45) DEFAULT NULL,
  `wx_code` varchar(45) DEFAULT NULL,
  `wx_uin` varchar(45) DEFAULT NULL COMMENT '微信个人号',
  `wxperson_id` varchar(45) DEFAULT NULL COMMENT '微信个人号下粉丝唯一标识',
  `IDFA` varchar(45) DEFAULT NULL,
  `IMEI` varchar(45) DEFAULT NULL,
  `unionid` varchar(45) DEFAULT NULL,
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `flag1` varchar(45) DEFAULT NULL COMMENT '预留字段1',
  `flag2` varchar(45) DEFAULT NULL COMMENT '预留字段2',
  `bitmap` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_key` (`mobile`,`tel`,`email`,`qq`,`identify_no`,`driving_license`,`wxmp_id`,`wx_code`,`wx_uin`,`wxperson_id`,`IDFA`,`IMEI`,`unionid`,`acct_no`,`flag1`,`flag2`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='主数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_party_tag_rule_map`
--

DROP TABLE IF EXISTS `data_party_tag_rule_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_party_tag_rule_map` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rule_type` tinyint(4) DEFAULT '0' COMMENT '规则类型:0:一一对应,1:数字范围对应',
  `tag_id` int(11) NOT NULL COMMENT 'tag表的id',
  `md_type` int(11) DEFAULT NULL COMMENT '对应MongoDB的data_party表中的md_type\n1-人口属性\n2-客户标签\n3-埋点统计\n4-会员卡记录\n5-登录行为\n6-支付记录\n7-购物记录\n8-微信',
  `field_name` varchar(512) NOT NULL COMMENT 'mongo的dataParty的po类的字段名',
  `field_value` varchar(512) DEFAULT NULL COMMENT 'mongo的dataParty的po类的字段值',
  `min` float DEFAULT NULL COMMENT 'type=1时，会有值，null表示无限小',
  `max` float DEFAULT NULL COMMENT 'type=1时，会有值,null表示无限大',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=699 DEFAULT CHARSET=utf8 COMMENT='给data_party表打标签的规则:data_party表的列和tag相关表的对应关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_payment`
--

DROP TABLE IF EXISTS `data_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_payment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pay_channel` varchar(10) DEFAULT NULL COMMENT '支付渠道',
  `pay_acct` varchar(45) DEFAULT NULL COMMENT '支付账号',
  `pay_serial` varchar(45) DEFAULT NULL COMMENT '支付流水',
  `trans_serial` varchar(45) DEFAULT NULL COMMENT '业务流水',
  `order_no` varchar(45) DEFAULT NULL COMMENT '订单编号',
  `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `create_time` datetime DEFAULT NULL,
  `complete_time` datetime DEFAULT NULL COMMENT '结束时间',
  `pay_status` varchar(45) DEFAULT NULL COMMENT '支付状态',
  `counter_acct` varchar(45) DEFAULT NULL COMMENT '对方账号',
  `income_amt` decimal(22,2) DEFAULT NULL COMMENT '收入金额',
  `paid_amt` decimal(22,2) DEFAULT NULL COMMENT '支出金额',
  `acct_amt` decimal(22,2) DEFAULT NULL COMMENT '账号余额',
  `comments` varchar(100) DEFAULT NULL COMMENT '备注',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_data_payment_pay_serial` (`pay_serial`) COMMENT '流水账号',
  UNIQUE KEY `idx_data_payment_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='支付记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_personal`
--

DROP TABLE IF EXISTS `data_personal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_personal` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `task_id` int(10) NOT NULL COMMENT '所在的任务Id',
  `open_id` int(15) DEFAULT NULL COMMENT 'openid',
  `personal_name` varchar(20) DEFAULT NULL COMMENT '个人号名称',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='微信个人号数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_population`
--

DROP TABLE IF EXISTS `data_population`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_population` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别\n1-男\n2-女 \n3-未确定 \n4-不确定',
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `provice` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL COMMENT '职业',
  `monthly_income` decimal(22,2) DEFAULT NULL COMMENT '月收入',
  `monthly_consume` decimal(22,2) DEFAULT NULL COMMENT '月均消费',
  `marital_status` varchar(45) DEFAULT NULL COMMENT '婚姻状况',
  `education` varchar(45) DEFAULT NULL COMMENT '教育程度',
  `employment` varchar(45) DEFAULT NULL COMMENT '就业情况',
  `nationality` varchar(45) DEFAULT NULL COMMENT '民族',
  `blood_type` varchar(45) DEFAULT NULL COMMENT '血型',
  `citizenship` varchar(45) DEFAULT NULL COMMENT '国籍',
  `iq` int(11) DEFAULT NULL,
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人口属性';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_public`
--

DROP TABLE IF EXISTS `data_public`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_public` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `task_id` int(10) NOT NULL COMMENT '所在的任务Id',
  `open_id` int(15) unsigned DEFAULT NULL COMMENT 'openid',
  `public_name` varchar(20) DEFAULT NULL COMMENT '公众号名称',
  `nick_name` varchar(20) DEFAULT NULL COMMENT '昵称',
  `icon_url` varchar(45) DEFAULT NULL COMMENT '头像url',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别\n1-男\n2-女 \n3-未确定 \n4-不确定',
  `area` varchar(20) DEFAULT NULL COMMENT '区域',
  `deleted` tinyint(1) unsigned DEFAULT '0' COMMENT '删除标记',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='微信公众号数据';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_shopping`
--

DROP TABLE IF EXISTS `data_shopping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_shopping` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `channel_type` varchar(45) DEFAULT NULL COMMENT '购物渠道分类',
  `channel_id` varchar(10) DEFAULT NULL COMMENT '购物渠道ID',
  `channel_name` varchar(45) DEFAULT NULL COMMENT '购物渠道名称',
  `pay_type` varchar(45) DEFAULT NULL COMMENT '支付方式',
  `trans_serial` varchar(45) DEFAULT NULL COMMENT '业务流水',
  `order_no` varchar(100) DEFAULT NULL COMMENT '商品订单号',
  `trans_time` datetime DEFAULT NULL COMMENT '消费时间',
  `product_id` varchar(100) DEFAULT NULL COMMENT '商品编号',
  `specification` varchar(45) DEFAULT NULL COMMENT '商品规格',
  `color` varchar(45) DEFAULT NULL COMMENT '颜色',
  `discount_type` varchar(45) DEFAULT NULL COMMENT '折扣类型',
  `discount_amt` decimal(22,2) DEFAULT NULL COMMENT '折扣金额',
  `price` decimal(22,2) DEFAULT NULL COMMENT '单价',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `inventory` int(11) DEFAULT NULL COMMENT '库存量',
  `brand_id` varchar(45) DEFAULT NULL COMMENT '品牌iD',
  `brand_name` varchar(45) DEFAULT NULL COMMENT '品牌名称',
  `class1_id` varchar(45) DEFAULT NULL COMMENT '一级品类ID',
  `class1_name` varchar(45) DEFAULT NULL COMMENT '一级品类名称',
  `class2_id` varchar(45) DEFAULT NULL COMMENT '二级品类ID',
  `class2_name` varchar(45) DEFAULT NULL COMMENT '二级品类名称',
  `class3_id` varchar(45) DEFAULT NULL COMMENT '三级品类ID',
  `class3_name` varchar(45) DEFAULT NULL COMMENT '三级品类名称',
  `class4_id` varchar(45) DEFAULT NULL COMMENT '四级品类ID',
  `class4_name` varchar(45) DEFAULT NULL COMMENT '二级品类名称',
  `sale_assist_id` varchar(45) DEFAULT NULL COMMENT '导购员编号',
  `sale_assistance` varchar(45) DEFAULT NULL COMMENT '导购员',
  `settlement_clerk_id` varchar(45) DEFAULT NULL COMMENT '结算人员编号',
  `settlement_clerk` varchar(45) DEFAULT NULL COMMENT '结算人员',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `order_status` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `delivery_way` varchar(10) DEFAULT NULL COMMENT '配送方式',
  `logistics_status` varchar(10) DEFAULT NULL COMMENT '物流状态',
  `shipping_fee` decimal(22,2) DEFAULT NULL COMMENT '运费',
  `shipping_way` varchar(10) DEFAULT NULL COMMENT '运送方式',
  `express_company` varchar(100) DEFAULT NULL COMMENT '快递公司',
  `express_order` varchar(50) DEFAULT NULL COMMENT '快递单号',
  `consignee` varchar(50) DEFAULT NULL COMMENT '收货人',
  `consignee_tel` varchar(20) DEFAULT NULL COMMENT '收货人电话',
  `consignee_addr` varchar(100) DEFAULT NULL COMMENT '收货地址',
  `buyer_comment` varchar(200) DEFAULT NULL COMMENT '买家备注',
  `wxmp_id` varchar(128) DEFAULT NULL COMMENT '公众号标识',
  `wx_code` varchar(128) DEFAULT NULL COMMENT 'openid',
  `product_name` varchar(200) DEFAULT NULL COMMENT '商品名称',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_data_shopping_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='购物记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `datareport_columns`
--

DROP TABLE IF EXISTS `datareport_columns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `datareport_columns` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `field_name` varchar(45) NOT NULL COMMENT '列名',
  `field_code` varchar(45) NOT NULL COMMENT '英文列名',
  `field_order` int(11) DEFAULT NULL COMMENT '列的顺序',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记,0:未删除,1:已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='数据质量报告中文列名';
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `is_selected` tinyint(4) DEFAULT NULL COMMENT '0:未选中 1:选中',
  `is_required` tinyint(4) DEFAULT NULL COMMENT '0:非必须 1:必须',
  `is_checked` tinyint(4) DEFAULT NULL COMMENT '0:不需要校验 1:需要校验',
  `default_shown_seq` int(11) DEFAULT NULL,
  `status` tinyint(4) DEFAULT '0',
  `is_primary_key` tinyint(4) DEFAULT NULL COMMENT '0:不是主键 1:是主键',
  `field_type` int(11) DEFAULT '0' COMMENT '0:输入框 1:下拉框 2:日历选择 3:地区联动 4:手机(验证码)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `illegal_data`
--

DROP TABLE IF EXISTS `illegal_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `illegal_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batch_id` bigint(20) NOT NULL,
  `type` char(10) DEFAULT NULL COMMENT '数据类型： app,pos\n',
  `origin_data` varchar(10000) DEFAULT NULL COMMENT '原始文本数据',
  `create_time` datetime DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `head_type` varchar(1) DEFAULT NULL COMMENT '是否非法数据，0不是，1是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='非法数据记录表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `img_text_asset`
--

DROP TABLE IF EXISTS `img_text_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `img_text_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(200) DEFAULT NULL COMMENT '图文名称',
  `type` tinyint(4) DEFAULT '0' COMMENT '0:微信资产,1:h5资产',
  `owner_name` varchar(200) DEFAULT NULL COMMENT '创建者名称:微信号/h5平台名',
  `pc_preview_url` varchar(1000) DEFAULT NULL COMMENT '电脑预览url',
  `mobile_preview_url` varchar(1000) DEFAULT NULL COMMENT '手机预览url',
  `imgfile_url` varchar(1000) DEFAULT NULL COMMENT '缩略图url',
  `material_id` varchar(200) DEFAULT NULL COMMENT '图文素材在大连H5数据库中的主键',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `pub_id` varchar(45) DEFAULT NULL COMMENT '图文所属公众号id',
  `pub_name` varchar(200) DEFAULT NULL COMMENT '图文所属公众号名称',
  `wechat_status` tinyint(4) DEFAULT NULL COMMENT '微信图文状态  1:仍在微信服务器上  0:从微信服务器上被删除了',
  `show_cover_pic` tinyint(4) DEFAULT NULL COMMENT '是否显示封面 0:不显示  1:显示',
  `thumb_ready` tinyint(4) DEFAULT NULL COMMENT '微信图文是否下载完毕: 0代表已经下载完毕 1代表尚未下载完毕',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='图文资产表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `import_data_history`
--

DROP TABLE IF EXISTS `import_data_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `import_data_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '任务名称',
  `import_start_time` timestamp NULL DEFAULT NULL,
  `import_end_time` timestamp NULL DEFAULT NULL,
  `total_rows` int(11) DEFAULT NULL COMMENT '本批次数据接入总条数',
  `legal_rows` int(11) DEFAULT NULL COMMENT '本批次数据接入合法数据条数',
  `success` tinyint(4) DEFAULT NULL,
  `source` varchar(50) DEFAULT NULL COMMENT '数据源类型',
  `source_filename` varchar(128) DEFAULT NULL COMMENT '数据源文件名称',
  `download_filename` varchar(128) DEFAULT NULL,
  `email_rows` int(11) DEFAULT NULL COMMENT '非法email条数',
  `mobile_rows` int(11) DEFAULT NULL COMMENT '非法手机号条数',
  `duplicate_rows` int(11) DEFAULT NULL COMMENT '重复条数',
  `illegal_rows` int(11) DEFAULT NULL COMMENT '非法记录总条数',
  `summary` varchar(512) DEFAULT NULL,
  `no_recognize_property` varchar(1024) DEFAULT NULL,
  `file_unique` varchar(50) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记,0:未删除,1:已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `file_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_file_unique` (`file_unique`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据接入质量报告';
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `modify_filename` varchar(128) DEFAULT NULL COMMENT '上传修正文件名称',
  `success` tinyint(4) DEFAULT NULL,
  `modify_download_filename` varchar(128) DEFAULT NULL COMMENT '上传修正文件名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `import_template`
--

DROP TABLE IF EXISTS `import_template`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `import_template` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `templ_type` int(5) NOT NULL COMMENT '模板类型\n0-主数据\n1-人口属性\n2-客户标签\n3-埋点统计\n4-会员卡记录\n5-登录行为\n6-支付记录\n7-购物记录',
  `templ_name` varchar(45) NOT NULL DEFAULT '' COMMENT '模板名称',
  `field_name` varchar(45) NOT NULL COMMENT '列名',
  `field_code` varchar(45) NOT NULL COMMENT '英文列名，对应data_app等表的column',
  `selected` tinyint(1) NOT NULL DEFAULT '1' COMMENT '选中 0-未选中 1-选中',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_templ_name_field_name` (`field_name`,`templ_name`)
) ENGINE=InnoDB AUTO_INCREMENT=243 DEFAULT CHARSET=utf8 COMMENT='导入模板表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='id mapping主键映射区域';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `operation_log`
--

DROP TABLE IF EXISTS `operation_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operation_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL,
  `operate_desc` varchar(100) DEFAULT NULL COMMENT '操作描述',
  `operate_time` datetime NOT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户操作日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `original_data_arch_point`
--

DROP TABLE IF EXISTS `original_data_arch_point`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_arch_point` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `event_id` int(11) DEFAULT NULL COMMENT '事件ID',
  `event_name` varchar(45) DEFAULT NULL COMMENT '事件名称',
  `click_time` datetime DEFAULT NULL COMMENT '点击时间',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `cookie` varchar(128) DEFAULT NULL,
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `original_data_customer_tags`
--

DROP TABLE IF EXISTS `original_data_customer_tags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_customer_tags` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_type` varchar(1) DEFAULT NULL COMMENT '0 - 文本类型 , 1 - 日期类型',
  `tag_name` varchar(45) DEFAULT NULL,
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `identify_no` varchar(19) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `original_data_login`
--

DROP TABLE IF EXISTS `original_data_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_login` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `login_type` varchar(100) DEFAULT NULL COMMENT '登录方式',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `logout_time` datetime DEFAULT NULL COMMENT '退出时间',
  `login_ip` varchar(45) DEFAULT NULL COMMENT '登录IP',
  `login_device` varchar(45) DEFAULT NULL COMMENT '登录设备',
  `resolution_ratio` varchar(100) DEFAULT NULL COMMENT '分辨率',
  `login_url` varchar(100) DEFAULT NULL COMMENT '登录页面',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `original_data_member`
--

DROP TABLE IF EXISTS `original_data_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_member` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `member_id` varchar(45) DEFAULT NULL COMMENT '会员卡号',
  `member_points` varchar(10) DEFAULT NULL COMMENT '会员积分',
  `member_level` varchar(45) DEFAULT NULL COMMENT '会员等级',
  `regist_time` date DEFAULT NULL COMMENT '开卡时间',
  `card_amt` decimal(22,2) DEFAULT NULL COMMENT '卡内余额',
  `expire` date DEFAULT NULL COMMENT '过期日',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `original_data_payment`
--

DROP TABLE IF EXISTS `original_data_payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_payment` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `pay_channel` varchar(10) DEFAULT NULL COMMENT '支付渠道',
  `pay_acct` varchar(45) DEFAULT NULL COMMENT '支付账号',
  `pay_serial` varchar(45) DEFAULT NULL COMMENT '支付流水',
  `trans_serial` varchar(45) DEFAULT NULL COMMENT '业务流水',
  `order_no` varchar(45) DEFAULT NULL COMMENT '订单编号',
  `product_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `create_time` datetime DEFAULT NULL,
  `complete_time` datetime DEFAULT NULL COMMENT '结束时间',
  `pay_status` varchar(45) DEFAULT NULL COMMENT '支付状态',
  `counter_acct` varchar(45) DEFAULT NULL COMMENT '对方账号',
  `income_amt` decimal(22,2) DEFAULT NULL COMMENT '收入金额',
  `paid_amt` decimal(22,2) DEFAULT NULL COMMENT '支出金额',
  `acct_amt` decimal(22,2) DEFAULT NULL COMMENT '账号余额',
  `comments` varchar(100) DEFAULT NULL COMMENT '备注',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `wxmp_id` varchar(45) DEFAULT NULL COMMENT '微信公众号标识',
  `wx_code` varchar(45) DEFAULT NULL COMMENT '微信openid',
  `bitmap` varchar(18) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `original_data_population`
--

DROP TABLE IF EXISTS `original_data_population`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_population` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `name` varchar(100) DEFAULT NULL COMMENT '姓名',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别\n1-男\n2-女 \n3-未确定 \n4-不确定',
  `birthday` date DEFAULT NULL COMMENT '出生年月日',
  `provice` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `job` varchar(100) DEFAULT NULL COMMENT '职业',
  `monthly_income` decimal(22,2) DEFAULT NULL COMMENT '月收入',
  `monthly_consume` decimal(22,2) DEFAULT NULL COMMENT '月均消费',
  `marital_status` varchar(45) DEFAULT NULL COMMENT '婚姻状况',
  `education` varchar(45) DEFAULT NULL COMMENT '教育程度',
  `employment` varchar(45) DEFAULT NULL COMMENT '就业情况',
  `nationality` varchar(45) DEFAULT NULL COMMENT '民族',
  `blood_type` varchar(45) DEFAULT NULL COMMENT '血型',
  `citizenship` varchar(45) DEFAULT NULL COMMENT '国籍',
  `iq` int(11) DEFAULT NULL,
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `wxmp_id` varchar(128) DEFAULT NULL,
  `wx_code` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `original_data_shopping`
--

DROP TABLE IF EXISTS `original_data_shopping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `original_data_shopping` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `channel_type` varchar(45) DEFAULT NULL COMMENT '购物渠道分类',
  `channel_id` varchar(10) DEFAULT NULL COMMENT '购物渠道ID',
  `channel_name` varchar(45) DEFAULT NULL COMMENT '购物渠道名称',
  `pay_type` varchar(45) DEFAULT NULL COMMENT '支付方式',
  `trans_serial` varchar(45) DEFAULT NULL COMMENT '业务流水',
  `order_no` varchar(100) DEFAULT NULL COMMENT '商品订单号',
  `trans_time` datetime DEFAULT NULL COMMENT '消费时间',
  `product_id` varchar(100) DEFAULT NULL COMMENT '商品编号',
  `specification` varchar(45) DEFAULT NULL COMMENT '商品规格',
  `color` varchar(45) DEFAULT NULL COMMENT '颜色',
  `discount_type` varchar(45) DEFAULT NULL COMMENT '折扣类型',
  `discount_amt` decimal(22,2) DEFAULT NULL COMMENT '折扣金额',
  `price` decimal(22,2) DEFAULT NULL COMMENT '单价',
  `amount` int(11) DEFAULT NULL COMMENT '数量',
  `inventory` int(11) DEFAULT NULL COMMENT '库存量',
  `brand_id` varchar(45) DEFAULT NULL COMMENT '品牌iD',
  `brand_name` varchar(45) DEFAULT NULL COMMENT '品牌名称',
  `class1_id` varchar(45) DEFAULT NULL COMMENT '一级品类ID',
  `class1_name` varchar(45) DEFAULT NULL COMMENT '一级品类名称',
  `class2_id` varchar(45) DEFAULT NULL COMMENT '二级品类ID',
  `class2_name` varchar(45) DEFAULT NULL COMMENT '二级品类名称',
  `class3_id` varchar(45) DEFAULT NULL COMMENT '三级品类ID',
  `class3_name` varchar(45) DEFAULT NULL COMMENT '三级品类名称',
  `class4_id` varchar(45) DEFAULT NULL COMMENT '四级品类ID',
  `class4_name` varchar(45) DEFAULT NULL COMMENT '二级品类名称',
  `sale_assist_id` varchar(45) DEFAULT NULL COMMENT '导购员编号',
  `sale_assistance` varchar(45) DEFAULT NULL COMMENT '导购员',
  `settlement_clerk_id` varchar(45) DEFAULT NULL COMMENT '结算人员编号',
  `settlement_clerk` varchar(45) DEFAULT NULL COMMENT '结算人员',
  `identify_no` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `driving_license` varchar(45) DEFAULT NULL COMMENT '驾驶证号',
  `email` varchar(100) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `tel` varchar(45) DEFAULT NULL COMMENT '固定电话',
  `qq` varchar(45) DEFAULT NULL,
  `acct_type` varchar(45) DEFAULT NULL COMMENT '私有账号类型',
  `acct_no` varchar(45) DEFAULT NULL COMMENT '私有账号',
  `idfa` varchar(45) DEFAULT NULL COMMENT 'iphone手机识别码',
  `imei` varchar(45) DEFAULT NULL COMMENT '手机识别码',
  `udid` varchar(45) DEFAULT NULL,
  `phone_mac` varchar(45) DEFAULT NULL COMMENT '手机网卡MAC',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '删除时间',
  `source` varchar(45) DEFAULT NULL COMMENT '数据来源',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '数据导入批次iD',
  `file_unique` varchar(45) DEFAULT NULL,
  `order_status` varchar(10) DEFAULT NULL COMMENT '订单状态',
  `delivery_way` varchar(10) DEFAULT NULL COMMENT '配送方式',
  `logistics_status` varchar(10) DEFAULT NULL COMMENT '物流状态',
  `shipping_fee` decimal(22,2) DEFAULT NULL COMMENT '运费',
  `shipping_way` varchar(10) DEFAULT NULL COMMENT '运送方式',
  `express_company` varchar(100) DEFAULT NULL COMMENT '快递公司',
  `express_order` varchar(50) DEFAULT NULL COMMENT '快递单号',
  `consignee` varchar(50) DEFAULT NULL COMMENT '收货人',
  `consignee_tel` varchar(20) DEFAULT NULL COMMENT '收货人电话',
  `consignee_addr` varchar(200) DEFAULT NULL COMMENT '收货地址',
  `buyer_comment` varchar(200) DEFAULT NULL COMMENT '买家备注',
  `wxmp_id` varchar(128) DEFAULT NULL COMMENT '公众号标识',
  `wx_code` varchar(128) DEFAULT NULL COMMENT 'openid',
  `product_name` varchar(200) DEFAULT NULL,
  `bitmap` varchar(18) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `party_behavior`
--

DROP TABLE IF EXISTS `party_behavior`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `party_behavior` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contact_id` varchar(20) NOT NULL COMMENT '联系人ID',
  `behavior_type` varchar(45) DEFAULT NULL COMMENT '行为类型\n0-微信、1-web、2-营销活动',
  `behavior_name` varchar(45) DEFAULT NULL,
  `wechat_type` varchar(45) DEFAULT NULL,
  `wechat_name` varchar(45) DEFAULT NULL,
  `message` varchar(45) DEFAULT NULL,
  `createtime` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `party_radar`
--

DROP TABLE IF EXISTS `party_radar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `party_radar` (
  `contact_id` varchar(20) NOT NULL DEFAULT '0' COMMENT '联系人ID',
  `contact_name` varchar(20) DEFAULT NULL COMMENT '联系人姓名',
  `recent_buy_time` datetime DEFAULT NULL COMMENT '	最近购买时间',
  `buy_rate` decimal(5,2) DEFAULT NULL COMMENT '消费频率',
  `goods_types` int(11) DEFAULT NULL COMMENT '购买商品品类数',
  `average_trans_amt` decimal(22,2) DEFAULT NULL COMMENT '平均交易金额',
  `top_trans_amt` decimal(22,2) DEFAULT NULL COMMENT '单次最高交易额',
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `description` varchar(100) DEFAULT NULL COMMENT '资源描述',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名',
  `branch_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `crated_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色-资源关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='1.保存最近3小时的结果';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='细分的覆盖人数';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `segmentation_body`
--

DROP TABLE IF EXISTS `segmentation_body`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `segmentation_body` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `head_id` int(11) DEFAULT NULL COMMENT '管理segmentation_head表的id',
  `tag_group_id` int(11) DEFAULT NULL COMMENT '标签所属分组的id',
  `tag_id` int(11) DEFAULT NULL COMMENT '标签id',
  `exclude` tinyint(4) DEFAULT '0' COMMENT '是否排除,0:不排除,1:排除',
  `group_index` int(11) DEFAULT NULL COMMENT '分组序号,0开始递增',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记,0:未删除,1:已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `group_seq` int(11) DEFAULT NULL COMMENT '组内顺序号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='细分body表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `segmentation_head`
--

DROP TABLE IF EXISTS `segmentation_head`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `segmentation_head` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '细分编号',
  `name` varchar(40) DEFAULT NULL COMMENT '细分名称',
  `publish_status` tinyint(4) DEFAULT '0' COMMENT '0:未生效,1:已生效',
  `oper` varchar(45) DEFAULT NULL COMMENT '操作者',
  `tag_ids` varchar(200) DEFAULT NULL COMMENT '关联cutom_tag表的id\n细分所关联的标签id，多个用逗号分隔',
  `refer_campaign_count` int(11) NOT NULL DEFAULT '0' COMMENT '被活动使用次数',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:未删除,1:已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='受众细分';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag`
--

DROP TABLE IF EXISTS `tag`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签编号',
  `name` varchar(40) DEFAULT NULL COMMENT '标签名称',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tag_group_id` varchar(500) DEFAULT NULL COMMENT '标签所属组的id(所有父节点的id)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6653 DEFAULT CHARSET=utf8 COMMENT='标签表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `audience_count` char(10) DEFAULT NULL COMMENT '标签覆盖的人数',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='标签分布';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=5568 DEFAULT CHARSET=utf8 COMMENT='标签和分组的关联关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tag_party_relation`
--

DROP TABLE IF EXISTS `tag_party_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tag_party_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_id` int(11) NOT NULL,
  `party_id` int(11) NOT NULL COMMENT '主数据(联系人)ID',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8 COMMENT='系统推荐标签组表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `taggroup`
--

DROP TABLE IF EXISTS `taggroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taggroup` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签组ID',
  `name` varchar(40) DEFAULT NULL COMMENT '标签组名称',
  `parent_group_id` bigint(20) DEFAULT NULL COMMENT '父标签组ID',
  `level` int(11) DEFAULT NULL COMMENT '标签组层级 0-最未级 1-次级   n-最高级(最大分类)',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14875 DEFAULT CHARSET=utf8 COMMENT='标签组表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `task_type` tinyint(4) NOT NULL DEFAULT '2' COMMENT '任务是否在前端显示,1:显示,2:不显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='后台任务执行日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task_schedule`
--

DROP TABLE IF EXISTS `task_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `task_schedule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(45) DEFAULT NULL COMMENT '任务类型',
  `task_descript` varchar(45) DEFAULT NULL COMMENT '任务描述',
  `task_type` varchar(45) DEFAULT NULL COMMENT '任务类型',
  `start_time` datetime DEFAULT NULL COMMENT '任务开始时间，为空表示立即启动',
  `end_time` datetime DEFAULT NULL COMMENT '任务结束时间，为空表示没有结束时间',
  `interval_minutes` float DEFAULT NULL COMMENT '任务运行间隔时间,小于等于0则只运行1次',
  `schedule` varchar(45) DEFAULT NULL COMMENT '执行时间安排cron table,如果为空，表示只运行1次',
  `service_name` varchar(512) DEFAULT NULL COMMENT '定时执行的service类名',
  `task_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '任务状态,0:可运行,1:不可运行',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记,0:正常,1:已删除',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `campaign_head_id` int(11) DEFAULT NULL COMMENT '关联campagin_head表的id',
  `campaign_item_id` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2668 DEFAULT CHARSET=utf8 COMMENT='任务编排表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `tenement`
--

DROP TABLE IF EXISTS `tenement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tenement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '企业名称',
  `shortname` varchar(50) DEFAULT NULL COMMENT '企业简称',
  `pid` varchar(45) NOT NULL COMMENT '企业注册H5plus的pid',
  `email` varchar(100) DEFAULT NULL COMMENT '企业邮箱',
  `mobile` varchar(45) DEFAULT NULL COMMENT '负责人手机',
  `province` varchar(100) DEFAULT NULL COMMENT '省',
  `city` varchar(100) DEFAULT NULL COMMENT '市',
  `area` varchar(100) DEFAULT NULL COMMENT '区',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `session_token` varchar(200) DEFAULT NULL,
  `bas_id` varchar(100) DEFAULT NULL,
  `register_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:代表注册微信pid的信息  1:代表注册bas_id的信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pid_UNIQUE` (`pid`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='企业客户(租户)表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色关系表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `precision` varchar(255) DEFAULT NULL COMMENT '地理位置精度',
  `menuId` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=622 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_asset`
--

DROP TABLE IF EXISTS `wechat_asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL DEFAULT '0',
  `asset_type` int(11) NOT NULL DEFAULT '0' COMMENT '0:服务号 1:个人号 2:订阅号',
  `asset_name` varchar(500) DEFAULT NULL COMMENT '资产名称',
  `nickname` varchar(500) DEFAULT NULL COMMENT '昵称',
  `wx_acct` varchar(200) DEFAULT NULL COMMENT '微信账号',
  `consignation_time` datetime DEFAULT NULL,
  `total_count` int(11) DEFAULT NULL,
  `group_ids` varchar(1000) DEFAULT NULL,
  `imgfile_url` varchar(1000) DEFAULT NULL COMMENT '头像缩略图',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `app_id` varchar(22) NOT NULL,
  `wechat_qrcode` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_wx_acct` (`wx_acct`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_asset_group`
--

DROP TABLE IF EXISTS `wechat_asset_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_asset_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `import_group_id` bigint(20) DEFAULT NULL,
  `name` varchar(500) DEFAULT NULL COMMENT '群组名称',
  `members` int(11) DEFAULT NULL COMMENT '群组人数',
  `wx_acct` varchar(500) DEFAULT NULL COMMENT '对应wechat_asset.wx_acct',
  `is_sys_group` int(11) DEFAULT NULL,
  `asset_id` int(11) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_channel`
--

DROP TABLE IF EXISTS `wechat_channel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_channel` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '渠道编号',
  `ch_name` varchar(100) NOT NULL COMMENT '渠道名称',
  `status` varchar(1) NOT NULL DEFAULT '0' COMMENT '状态\n0-正常\n1-删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  `type` int(11) DEFAULT '0' COMMENT '渠道类型\n0-系统默认渠道\n1-自定义渠道',
  `is_removed` int(11) DEFAULT '0' COMMENT '0-不可删除\n1-可以删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='微信二维码渠道';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_group`
--

DROP TABLE IF EXISTS `wechat_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '群组编号',
  `group_id` varchar(100) DEFAULT NULL COMMENT 'H5返回的group_id',
  `group_name` varchar(500) DEFAULT NULL COMMENT '群组名称，好友全部对到好友组',
  `group_nickname` varchar(500) DEFAULT NULL COMMENT '群组的别称',
  `header_image` varchar(500) DEFAULT NULL COMMENT '群组头像缩略图',
  `wx_acct` varchar(500) NOT NULL COMMENT '对应微信号：wechat_register.wx_acct\n',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `count` int(11) DEFAULT NULL COMMENT '群组人数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='微信群组信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_member`
--

DROP TABLE IF EXISTS `wechat_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wx_code` varchar(200) DEFAULT NULL COMMENT '成员微信账号：\n公众号：openid\n个人号 ：ucode',
  `wx_name` varchar(200) DEFAULT NULL COMMENT '成员微信名称',
  `wx_group_id` varchar(200) DEFAULT NULL COMMENT '所属微信群组的ID，wechat_group.id',
  `nickname` varchar(1000) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `country` varchar(500) DEFAULT NULL,
  `province` varchar(500) DEFAULT NULL,
  `city` varchar(500) DEFAULT NULL,
  `county` varchar(500) DEFAULT NULL COMMENT '位置',
  `birthday` varchar(500) DEFAULT NULL,
  `signature` varchar(1000) DEFAULT NULL COMMENT '签名',
  `is_friend` varchar(45) DEFAULT NULL COMMENT '是否好友',
  `pub_id` varchar(200) DEFAULT NULL COMMENT '公众号唯一标识',
  `uin` varchar(200) DEFAULT NULL COMMENT '个人号唯一标识',
  `subscribe_yn` varchar(200) DEFAULT NULL,
  `subscribe_time` varchar(500) DEFAULT NULL,
  `active_time` varchar(500) DEFAULT NULL,
  `activity_48h_yn` varchar(200) DEFAULT NULL,
  `head_image_url` varchar(1000) DEFAULT NULL,
  `remark` varchar(5000) DEFAULT NULL,
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `selected` tinyint(4) NOT NULL DEFAULT '0' COMMENT '往data_party表里的同步状态',
  `bitmap` varchar(18) DEFAULT NULL,
  `keyid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36453 DEFAULT CHARSET=utf8 COMMENT='微信成员(好友、粉丝)信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `wechat_personal_uuid`
--

DROP TABLE IF EXISTS `wechat_personal_uuid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_personal_uuid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) NOT NULL,
  `status` int(10) unsigned zerofill NOT NULL DEFAULT '0000000000' COMMENT 'uuid的状态，有效为0，无效为1',
  `uin` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uuid_index` (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_qrcode`
--

DROP TABLE IF EXISTS `wechat_qrcode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_name` varchar(100) DEFAULT NULL COMMENT '微信公众号名称',
  `wx_acct` varchar(500) DEFAULT NULL COMMENT '微信账号\n个人号： uin\n服务号/订阅号：weixin_id',
  `ch_code` int(11) DEFAULT NULL COMMENT '渠道编号\nwechat_channel.id',
  `is_audience` tinyint(4) DEFAULT NULL COMMENT '是否新建固定人群\n0-否\n1-是',
  `audience_name` varchar(100) DEFAULT NULL COMMENT '固定人群名称\n',
  `related_tags` varchar(2000) DEFAULT NULL COMMENT '关联系统标签ID，多个标签ID，以;分隔，例如：\n101;102;103',
  `comments` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `expiration_time` datetime DEFAULT NULL COMMENT '失效时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态\n0-未用\n1-已用\n2-删除3-过期',
  `qrcode_pic` varchar(128) DEFAULT NULL COMMENT '二维码图片文件名称',
  `qrcode_url` varchar(128) DEFAULT NULL COMMENT '二维码url',
  `batch_id` varchar(45) DEFAULT NULL COMMENT '批次号：批量产生二维码的编号',
  `ticket` text,
  `qrcode_name` varchar(128) DEFAULT NULL COMMENT '二维码名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8 COMMENT='微信公众号二维码';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_qrcode_focus`
--

DROP TABLE IF EXISTS `wechat_qrcode_focus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode_focus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `qrcode_id` varchar(45) DEFAULT NULL COMMENT '二维码编号',
  `wx_name` varchar(100) DEFAULT NULL COMMENT '微信公众号名称',
  `ch_code` int(11) DEFAULT NULL COMMENT '渠道编号\nwechat_channel.id',
  `openid` varchar(128) DEFAULT NULL COMMENT '关注者openid',
  `focus_datetime` datetime DEFAULT NULL COMMENT '关注时间',
  `unfocus_datetime` datetime DEFAULT NULL COMMENT '取消关注时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '0:关注时间 1:取消关注时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='关注二维码记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_qrcode_log`
--

DROP TABLE IF EXISTS `wechat_qrcode_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `source_filename` varchar(128) DEFAULT NULL COMMENT '数据源文件名称',
  `total_rows` int(11) DEFAULT NULL COMMENT '总条数',
  `success` tinyint(4) DEFAULT NULL COMMENT '成功条数',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0-失败\n1-成功',
  `create_time` datetime DEFAULT NULL,
  `batch_id` varchar(45) DEFAULT NULL COMMENT '批次号：批量产生二维码的编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_qrcode_scan`
--

DROP TABLE IF EXISTS `wechat_qrcode_scan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode_scan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL COMMENT '用户ID',
  `user_host` varchar(500) DEFAULT NULL COMMENT '扫描用户手机host name',
  `qrcode_id` int(11) DEFAULT NULL COMMENT '二维码场景编号，wechat_qrcode.id',
  `create_time` datetime DEFAULT NULL COMMENT '扫描时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录扫描次数和人数';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_qrcode_ticket`
--

DROP TABLE IF EXISTS `wechat_qrcode_ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_qrcode_ticket` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ticket` text COMMENT '获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码',
  `url` varchar(128) DEFAULT NULL COMMENT '二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片',
  `scene_id` int(11) DEFAULT NULL COMMENT '场景值ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `state` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `wechat_register`
--

DROP TABLE IF EXISTS `wechat_register`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `wechat_register` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wx_acct` varchar(500) NOT NULL COMMENT '微信账号\n个人号： uin\n服务号/订阅号：weixin_id',
  `name` varchar(500) DEFAULT NULL COMMENT '微信(个人、订阅、服务号)名称',
  `type` int(11) NOT NULL DEFAULT '-1' COMMENT '微信接入类型\n0-服务号\n1-个人号\n2-订阅号\n3-未认证服务号\n4-未认证订阅号',
  `nickname` varchar(500) DEFAULT NULL COMMENT '个人号的昵称',
  `header_image` varchar(1000) DEFAULT NULL COMMENT '个人号的头像缩略图',
  `sex` varchar(10) DEFAULT NULL COMMENT '个人号的性别',
  `province` varchar(50) DEFAULT NULL COMMENT '个人号的省',
  `city` varchar(50) DEFAULT NULL COMMENT '个人号的市',
  `signature` varchar(100) DEFAULT NULL COMMENT '个人号的签名文字',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_time` datetime DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `app_id` varchar(22) NOT NULL,
  `wechat_qrcode` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-09 18:00:04
