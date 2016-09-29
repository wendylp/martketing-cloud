CREATE TABLE `wechat_interface_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `interface_name` varchar(500) NOT NULL COMMENT '接口名称',
  `method_name` varchar(500) DEFAULT NULL COMMENT '方法名称',
  `params` varchar(500) DEFAULT NULL COMMENT '参数',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;