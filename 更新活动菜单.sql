delete from campaign_node_item;

INSERT INTO `campaign_node_item` VALUES ('1', '-1', '0', '触发', 'trigger', null, '0', null, null, '0', null, '2016-06-08 14:11:03');
INSERT INTO `campaign_node_item` VALUES ('2', '-1', '1', '受众', 'audiences', null, '1', null, null, '0', null, '2016-06-08 14:11:03');
INSERT INTO `campaign_node_item` VALUES ('3', '-1', '2', '决策', 'decisions', null, '2', null, null, '0', null, '2016-06-08 14:11:03');
INSERT INTO `campaign_node_item` VALUES ('4', '-1', '3', '行动', 'activity', null, '3', null, null, '0', null, '2016-06-08 14:11:03');
INSERT INTO `campaign_node_item` VALUES ('5', '0', '0', '预约触发', 'timer-trigger', '&#xe60e;', '0', null, null, '0', null, '2016-07-21 17:20:34');
INSERT INTO `campaign_node_item` VALUES ('6', '0', '2', '手动触发', 'manual-trigger', '&#xe633;', '2', null, null, '0', null, '2016-06-08 14:16:10');
INSERT INTO `campaign_node_item` VALUES ('7', '1', '0', '目标人群', 'target-group', '&#xe630;', '0', null, null, '0', null, '2016-06-08 14:16:10');
INSERT INTO `campaign_node_item` VALUES ('8', '2', '0', '联系人属性比较', 'attr-comparison', '&#xe66e;', '0', null, null, '1', null, '2016-07-01 10:07:47');
INSERT INTO `campaign_node_item` VALUES ('9', '2', '1', '微信图文是否发送', 'wechat-send', '&#xe66d;', '1', null, null, '1', null, '2016-06-27 15:23:36');
INSERT INTO `campaign_node_item` VALUES ('10', '2', '2', '微信图文是否查看', 'wechat-check', '&#xe66c;', '2', null, null, '1', null, '2016-09-29 10:13:56');
INSERT INTO `campaign_node_item` VALUES ('11', '2', '3', '微信图文是否转发', 'wechat-forwarded', '&#xe673;', '3', null, null, '1', null, '2016-09-29 10:13:59');
INSERT INTO `campaign_node_item` VALUES ('12', '2', '4', '是否订阅公众号', 'subscriber-public', '&#xe62e;', '4', null, null, '0', null, '2016-06-08 14:16:10');
INSERT INTO `campaign_node_item` VALUES ('14', '2', '6', '标签判断', 'label-judgment', '&#xe607;', '6', null, null, '0', null, '2016-06-08 14:16:10');
INSERT INTO `campaign_node_item` VALUES ('15', '3', '0', '等待', 'wait-set', '&#xe60b;', '0', null, null, '0', null, '2016-06-08 14:16:10');
INSERT INTO `campaign_node_item` VALUES ('16', '3', '1', '保存当前人群', 'save-current-group', '&#xe603;', '1', null, null, '0', null, '2016-06-08 14:16:10');
INSERT INTO `campaign_node_item` VALUES ('17', '3', '2', '设置标签', 'set-tag', '&#xe62a;', '2', null, null, '0', null, '2016-06-08 14:16:10');
INSERT INTO `campaign_node_item` VALUES ('18', '3', '3', '添加到其它活动', null, null, '3', null, null, '1', null, '2016-06-08 11:31:10');
INSERT INTO `campaign_node_item` VALUES ('19', '3', '4', '转移到其它活动', null, null, '4', null, null, '1', null, '2016-06-08 11:31:10');
INSERT INTO `campaign_node_item` VALUES ('20', '3', '5', '发送微信图文', 'send-img', '&#xe616;', '5', null, null, '0', null, '2016-06-08 14:16:10');