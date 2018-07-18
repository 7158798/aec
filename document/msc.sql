/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.21
Source Server Version : 50722
Source Host           : 192.168.0.21:3306
Source Database       : msc

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2018-07-06 11:29:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_app_version
-- ----------------------------
DROP TABLE IF EXISTS `t_app_version`;
CREATE TABLE `t_app_version` (
  `id` varchar(36) NOT NULL,
  `source` int(1) DEFAULT '1' COMMENT '平台：1.普通用户app登录,2.银商webapp登录',
  `os_type` int(1) NOT NULL DEFAULT '0' COMMENT '操作系统类型:0 ios,1 android',
  `code` int(4) NOT NULL COMMENT '版本代码',
  `value` varchar(16) NOT NULL DEFAULT '' COMMENT '版本号',
  `force_update` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否强制更新, 0: 不强制 1: 强制',
  `download_link` varchar(256) NOT NULL DEFAULT '' COMMENT 'app下载地址',
  `comment` varchar(1024) NOT NULL DEFAULT '' COMMENT '版本说明,描述这个版本改进了什么问题',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='app版本表';

-- ----------------------------
-- Records of t_app_version
-- ----------------------------
INSERT INTO `t_app_version` VALUES ('1', '1', '0', '1', '0.1', '1', 'http://192.168.0.8/download/file/WCBUser.ipa', '优化广告模块1', '2018-06-20 11:29:13');
INSERT INTO `t_app_version` VALUES ('2', '1', '1', '2', '1.0.2', '0', 'http://192.168.0.8/download/file/user.apk', '用户版本更新1.0.2', '2018-06-20 11:29:58');
INSERT INTO `t_app_version` VALUES ('3', '2', '0', '1', '0.1', '1', 'http://192.168.0.8/download/file/WCBUser.ipa', '3344', '2018-06-20 11:30:38');
INSERT INTO `t_app_version` VALUES ('4', '2', '1', '2', '1.0.2', '0', 'http://192.168.0.8/download/file/shop.apk', '银商版本更新1.0.2', '2018-06-20 11:31:08');

-- ----------------------------
-- Table structure for t_appeal_type
-- ----------------------------
DROP TABLE IF EXISTS `t_appeal_type`;
CREATE TABLE `t_appeal_type` (
  `id` varchar(36) NOT NULL,
  `descri` varchar(64) DEFAULT '' COMMENT '申诉内容',
  `create_user` varchar(36) CHARACTER SET utf8 DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='申诉类型';

-- ----------------------------
-- Records of t_appeal_type
-- ----------------------------
INSERT INTO `t_appeal_type` VALUES ('1', '已付款，未放币', '', '2018-06-01 19:05:33');
INSERT INTO `t_appeal_type` VALUES ('2', '打款金额有误', '', '2018-06-01 19:05:35');
INSERT INTO `t_appeal_type` VALUES ('3', '其他', '', '2018-07-06 11:12:30');

-- ----------------------------
-- Table structure for t_bond_apply
-- ----------------------------
DROP TABLE IF EXISTS `t_bond_apply`;
CREATE TABLE `t_bond_apply` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `type` int(1) DEFAULT '0' COMMENT '0.申请提高，1.申请降低，2.提取保证金',
  `user_id` varchar(36) DEFAULT '',
  `total_bond` decimal(20,8) DEFAULT '0.00000000' COMMENT '当前保证金',
  `bond` decimal(20,8) DEFAULT '0.00000000' COMMENT '申请提高/降低保证金/提取保证金数量',
  `status` int(1) DEFAULT '0' COMMENT '0.申请中，1.审核通过，2.审核不通过',
  `reason` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT '' COMMENT '原因描述',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='保证金申请表';

-- ----------------------------
-- Records of t_bond_apply
-- ----------------------------

-- ----------------------------
-- Table structure for t_generation_award_parameter
-- ----------------------------
DROP TABLE IF EXISTS `t_generation_award_parameter`;
CREATE TABLE `t_generation_award_parameter` (
  `id` varchar(36) NOT NULL,
  `qualified_num` int(11) NOT NULL COMMENT '第一代合格人数',
  `self_amount` decimal(20,8) NOT NULL COMMENT '自购金额',
  `generation_num` int(11) NOT NULL COMMENT '享受奖励代数',
  `create_user` varchar(36) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='代数奖励参数表';

-- ----------------------------
-- Records of t_generation_award_parameter
-- ----------------------------
INSERT INTO `t_generation_award_parameter` VALUES ('1', '1', '1000.00000000', '2', '1', '2018-06-11 17:19:00', '6', '2018-07-04 18:11:54');
INSERT INTO `t_generation_award_parameter` VALUES ('2', '2', '2000.00000000', '4', '1', '2018-06-11 17:19:23', '6', '2018-07-04 18:11:59');
INSERT INTO `t_generation_award_parameter` VALUES ('3', '3', '3000.00000000', '6', '1', '2018-06-11 17:19:47', '6', '2018-07-04 18:12:10');
INSERT INTO `t_generation_award_parameter` VALUES ('4', '4', '4000.00000000', '8', '1', '2018-06-11 17:20:01', '6', '2018-07-04 18:12:16');
INSERT INTO `t_generation_award_parameter` VALUES ('5', '5', '5000.00000000', '10', '1', '2018-06-11 17:20:18', '6', '2018-07-04 18:12:24');

-- ----------------------------
-- Table structure for t_global_parameter
-- ----------------------------
DROP TABLE IF EXISTS `t_global_parameter`;
CREATE TABLE `t_global_parameter` (
  `id` varchar(60) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `cron_key` varchar(64) CHARACTER SET utf8 DEFAULT '' COMMENT 'key',
  `cron_value` varchar(512) CHARACTER SET utf8 DEFAULT '' COMMENT 'value',
  `min_value` varchar(64) DEFAULT '' COMMENT '提现手续费时使用，代表手续费最低额',
  `group_name` varchar(25) DEFAULT '' COMMENT '组',
  `descri` varchar(512) CHARACTER SET utf8 DEFAULT '' COMMENT '描述',
  `create_user` varchar(36) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `inx_unique_cronkey` (`cron_key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='基础参数表';

-- ----------------------------
-- Records of t_global_parameter
-- ----------------------------
INSERT INTO `t_global_parameter` VALUES ('1', 'payment_fee_amount', '1', '', 'paymentfee', '支付手续费金额', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-14 17:57:21');
INSERT INTO `t_global_parameter` VALUES ('10', 'generation_award_percent', '1', '', 'genaward', '代数奖励比例', '00000000000000', '2018-06-10 14:22:09', '', '2018-07-04 18:41:14');
INSERT INTO `t_global_parameter` VALUES ('11', 'generation_reward_switch', '0', '', 'switch', '代数奖励开关  0：关闭，1：打开', '00000000000000', '2018-06-12 14:52:23', '', '2018-07-06 11:28:37');
INSERT INTO `t_global_parameter` VALUES ('12', 'level_reward_switch', '0', '', 'switch', '级差奖励开关 0：关闭，1：打开', '00000000000000', '2018-06-12 15:02:29', '', '2018-07-06 11:28:40');
INSERT INTO `t_global_parameter` VALUES ('13', 'withdraw_switch', '1', '', 'switch', '提现开关 0：关闭，1：打开', '00000000000000', '2018-06-12 15:05:04', '', '2018-07-06 11:28:43');
INSERT INTO `t_global_parameter` VALUES ('14', 'aec_withdraw_fee_percent', '1', '', 'aecwithdraw', 'AEC提现手续费百分比数', '00000000000000', '2018-06-13 10:01:27', '', '2018-07-04 18:42:23');
INSERT INTO `t_global_parameter` VALUES ('15', 'msc_withdraw_fee_percent', '1', '', 'mscwithdraw', 'MSC提现手续费百分比数', '00000000000000', '2018-06-13 11:23:57', '', '2018-07-04 18:42:23');
INSERT INTO `t_global_parameter` VALUES ('16', 'aec_withdraw_fee_amount', '1', '', 'aecwithdraw', 'AEC提现手续费金额', '00000000000000', '2018-06-13 10:00:38', '', '2018-07-04 18:42:23');
INSERT INTO `t_global_parameter` VALUES ('17', 'MSC/AEC', '1', '', 'basesetup', 'AEC兑换MSC比', '00000000000000', '2018-06-02 15:26:50', '', '2018-06-20 17:33:15');
INSERT INTO `t_global_parameter` VALUES ('18', 'aec_withdraw_fee_rule', '2', '', 'aecwithdraw', 'AEC提现手续费规则(1金额,2百分百,3金额+百分比)', '00000000000000', '2018-06-13 10:02:45', '', '2018-06-14 17:57:21');
INSERT INTO `t_global_parameter` VALUES ('19', 'rest_msc_amount', '0.00', '', 'basesetup', 'MSC价格增长剩余基数', '00000000000000', '2018-06-17 12:50:27', '', '2018-06-20 17:33:15');
INSERT INTO `t_global_parameter` VALUES ('2', 'msc_increase_price', '0.010', '', 'basesetup', 'MSC增长价格', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-20 17:33:15');
INSERT INTO `t_global_parameter` VALUES ('20', 'payment_fee_percent', '1', '', 'paymentfee', '支付手续费百分比数', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-14 17:57:21');
INSERT INTO `t_global_parameter` VALUES ('21', 'aliyunBuketName', 'blockbank-test', '', 'oss', 'OSS图片上传信息', '00000000000000', '2018-06-20 11:54:21', '', '2018-07-06 11:28:45');
INSERT INTO `t_global_parameter` VALUES ('22', 'aliyunAccessKeyID', 'LTAIg8IlrH22IFBS', '', 'oss', 'OSS图片上传信息', '00000000000000', '2018-06-20 11:54:17', '', '2018-07-06 11:28:47');
INSERT INTO `t_global_parameter` VALUES ('23', 'payment_fee_rule', '1', '', 'paymentfee', '支付手续费规则(1金额,2百分百,3金额+百分比)', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-14 17:57:21');
INSERT INTO `t_global_parameter` VALUES ('24', 'foreign_crurrency_exchange', '0.97', '', 'basesetup', '外币兑换折价率', '00000000000000', '2018-06-02 15:27:16', '', '2018-07-06 11:28:50');
INSERT INTO `t_global_parameter` VALUES ('26', 'c2c_fee_rule', '2', '', 'c2cfee', 'C2C手续费规则(1金额,2百分百,3金额+百分比)', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-14 17:57:21');
INSERT INTO `t_global_parameter` VALUES ('27', 'advert_min_aec', '1.0', '', 'basesetup', '广告位AEC最低价', '00000000000000', '2018-06-02 15:28:29', '', '2018-06-20 17:33:15');
INSERT INTO `t_global_parameter` VALUES ('28', 'ltc_withdraw_fee_percent', '0.1', '', 'ltcwithdraw', 'LTC提现手续费百分比数', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-14 17:57:21');
INSERT INTO `t_global_parameter` VALUES ('29', 'advert_max_aec', '100.0', '', 'basesetup', '广告位AEC最高价', '00000000000000', '2018-06-02 15:29:32', '', '2018-06-20 17:33:15');
INSERT INTO `t_global_parameter` VALUES ('3', 'AEC/CNY', '1', '', 'basesetup', 'CNY兑换AEC比', '00000000000000', '2018-06-02 15:26:11', '', '2018-06-20 17:33:15');
INSERT INTO `t_global_parameter` VALUES ('30', 'MSC/CNY', '1', '', 'basesetup', 'CNY兑换MSC比', '00000000000000', '2018-06-14 12:34:25', '', '2018-06-20 17:33:15');
INSERT INTO `t_global_parameter` VALUES ('31', 'btc_withdraw_fee_percent', '1', '', 'btcwithdraw', 'BTC提现手续费百分比数', '00000000000000', '2018-06-13 11:23:57', '', '2018-07-04 18:42:23');
INSERT INTO `t_global_parameter` VALUES ('32', 'btc_withdraw_fee_amount', '1', '', 'btcwithdraw', 'BTC提现手续费金额', '00000000000000', '2018-06-13 11:23:57', '', '2018-07-04 18:42:23');
INSERT INTO `t_global_parameter` VALUES ('33', 'min_bond', '1000.0', '', 'basesetup', '保证金最低额', '00000000000000', '2018-06-02 15:30:48', '', '2018-07-04 15:34:31');
INSERT INTO `t_global_parameter` VALUES ('34', 'withdraw_aec', '0.003', '', 'withdraw', 'aec提现手续费', '00000000000000', '2018-06-04 12:16:53', '', '2018-07-06 11:28:55');
INSERT INTO `t_global_parameter` VALUES ('35', 'withdraw_msc', '0.004', '', 'withdraw', 'msc提现手续费', '00000000000000', '2018-06-04 12:19:14', '', '2018-07-06 11:28:57');
INSERT INTO `t_global_parameter` VALUES ('36', 'msc_withdraw_fee_rule', '2', '', 'mscwithdraw', 'MSC提现手续费规则(1金额,2百分百,3金额+百分比)', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-14 17:57:21');
INSERT INTO `t_global_parameter` VALUES ('37', 'msc_increase_split', '3000.0', '', 'basesetup', 'MSC增长间隔', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-20 17:33:15');
INSERT INTO `t_global_parameter` VALUES ('38', 'withdraw_btc', '0.005', '', 'withdraw', 'btc提现手续费', '00000000000000', '2018-06-04 12:19:19', '', '2018-07-06 11:29:00');
INSERT INTO `t_global_parameter` VALUES ('39', 'ltc_exchange_percent', '1', '', 'basesetup', 'LTC兑换折价率（%）', '00000000000000', '2018-06-13 11:23:57', '', '2018-07-04 15:34:31');
INSERT INTO `t_global_parameter` VALUES ('4', 'withdraw_ltc', '0.006', '', 'withdraw', 'ltc提现手续费', '00000000000000', '2018-06-04 12:19:22', '', '2018-07-06 11:29:02');
INSERT INTO `t_global_parameter` VALUES ('40', 'btc_exchange_percent', '1', '', 'basesetup', 'BTC兑换折价率（%）', '00000000000000', '2018-06-13 11:23:57', '', '2018-07-04 15:34:31');
INSERT INTO `t_global_parameter` VALUES ('41', 'ltc_withdraw_fee_rule', '2', '', 'ltcwithdraw', 'LTC提现手续费规则(1金额,2百分百,3金额+百分比)', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-14 17:57:21');
INSERT INTO `t_global_parameter` VALUES ('42', 'c2c_fee_amount', '2', '', 'c2cfee', 'C2C手续费金额', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-14 17:57:21');
INSERT INTO `t_global_parameter` VALUES ('43', 'btc_withdraw_fee_rule', '2', '', 'btcwithdraw', 'BTC提现手续费规则(1金额,2百分百,3金额+百分比)', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-14 17:57:21');
INSERT INTO `t_global_parameter` VALUES ('44', 'aliyunAccessKeySecret', 'WwGh5qlsTuErC0MLjeeXFnuCQT4cVa', '', 'oss', 'OSS图片上传信息', '00000000000000', '2018-06-20 11:54:19', '', '2018-07-06 11:29:05');
INSERT INTO `t_global_parameter` VALUES ('45', 'c2c_fee_percent', '0.1', '', 'c2cfee', 'C2C手续费百分比数', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-14 17:57:21');
INSERT INTO `t_global_parameter` VALUES ('46', 'msc_withdraw_fee_amount', '1', '', 'mscwithdraw', 'MSC提现手续费金额', '00000000000000', '2018-06-13 11:23:57', '', '2018-07-04 18:42:23');
INSERT INTO `t_global_parameter` VALUES ('5', 'msc_froze_day', '90', '', 'mscset', 'MSC冻结设置冻结天数', '00000000000000', '2018-06-06 16:07:00', '', '2018-06-14 16:23:47');
INSERT INTO `t_global_parameter` VALUES ('6', 'msc_release_percent', '0.010', '', 'mscset', 'MSC冻结设置释放百分比', '00000000000000', '2018-06-06 16:09:16', '', '2018-06-14 16:23:47');
INSERT INTO `t_global_parameter` VALUES ('7', 'ltc_withdraw_fee_amount', '2', '', 'ltcwithdraw', 'LTC提现手续费金额', '00000000000000', '2018-06-13 11:23:57', '', '2018-06-14 17:57:21');
INSERT INTO `t_global_parameter` VALUES ('8', 'aliyunEndPoint', 'oss-cn-beijing.aliyuncs.com', '', 'oss', 'OSS图片上传信息', '00000000000000', '2018-06-20 11:54:25', '', '2018-07-06 11:29:08');
INSERT INTO `t_global_parameter` VALUES ('9', 'generation_award_amount', '100', '', 'genaward', '代数奖励合格业绩', '00000000000000', '2018-06-10 14:20:54', '', '2018-07-04 18:41:14');

-- ----------------------------
-- Table structure for t_level_award_parameter
-- ----------------------------
DROP TABLE IF EXISTS `t_level_award_parameter`;
CREATE TABLE `t_level_award_parameter` (
  `id` varchar(36) NOT NULL,
  `level_name` varchar(100) NOT NULL COMMENT '级别名称',
  `level_code` varchar(50) NOT NULL COMMENT '等级编码',
  `min_amt` decimal(20,8) NOT NULL COMMENT '级别最小业绩',
  `max_amt` decimal(20,8) NOT NULL COMMENT '级别最大业绩',
  `prize_aec` decimal(4,2) NOT NULL COMMENT 'aec奖励百分比',
  `prize_msc` decimal(4,2) NOT NULL COMMENT 'msc奖励百分比',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uq_level_code` (`level_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='等级奖励参数表';

-- ----------------------------
-- Records of t_level_award_parameter
-- ----------------------------
INSERT INTO `t_level_award_parameter` VALUES ('1', '一级', 'first', '50000.00000000', '300000.00000000', '0.05', '0.50');
INSERT INTO `t_level_award_parameter` VALUES ('2', '二级', 'second', '300000.00000000', '1000000.00000000', '0.10', '0.10');
INSERT INTO `t_level_award_parameter` VALUES ('3', '三级', 'third', '1000000.00000000', '3000000.00000000', '0.15', '0.15');
INSERT INTO `t_level_award_parameter` VALUES ('4', '四级', 'fourth', '3000000.00000000', '10000000.00000000', '0.20', '0.20');
INSERT INTO `t_level_award_parameter` VALUES ('5', '五级', 'fifth', '10000000.00000000', '30000000.00000000', '0.25', '0.25');
INSERT INTO `t_level_award_parameter` VALUES ('6', '六级', 'sixth', '30000000.00000000', '999999999999.00000000', '0.30', '0.30');

-- ----------------------------
-- Table structure for t_msc_profits
-- ----------------------------
DROP TABLE IF EXISTS `t_msc_profits`;
CREATE TABLE `t_msc_profits` (
  `id` varchar(36) NOT NULL,
  `amount` decimal(20,8) NOT NULL COMMENT '分红AEC币总数',
  `status` int(1) NOT NULL COMMENT '分红状态0-未分红；1-已分红',
  `descri` varchar(100) DEFAULT '' COMMENT '分红描述',
  `create_user` varchar(36) DEFAULT '' COMMENT '创建人',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT '' COMMENT '修改人',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='MSC股权分红表';

-- ----------------------------
-- Records of t_msc_profits
-- ----------------------------

-- ----------------------------
-- Table structure for t_msc_record
-- ----------------------------
DROP TABLE IF EXISTS `t_msc_record`;
CREATE TABLE `t_msc_record` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL COMMENT '购买用户id',
  `wallet_record_id` varchar(36) NOT NULL COMMENT '钱包明细id',
  `aec_amount` decimal(20,8) NOT NULL COMMENT '消耗AEC币个数',
  `msc_amount` decimal(20,8) NOT NULL COMMENT '购买MSC币个数',
  `rest_msc_amount` decimal(20,8) NOT NULL COMMENT '未释放MSC币数',
  `status` int(1) NOT NULL DEFAULT '0' COMMENT '释放状态：0-未完成；1-已完成',
  `type` int(1) NOT NULL COMMENT 'msc记录类型:1-AEC购买MSC；2-级差奖励MSC；3-批量拨MSC币冻结',
  `effect_status` int(1) DEFAULT '0' COMMENT 'MSC有效标识：0-有效；1-无效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(36) DEFAULT '' COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `inx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='msc币冻结记录表';

-- ----------------------------
-- Records of t_msc_record
-- ----------------------------

-- ----------------------------
-- Table structure for t_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_notice`;
CREATE TABLE `t_notice` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `user_id` varchar(36) DEFAULT '',
  `notice_type_id` varchar(36) DEFAULT '' COMMENT '消息类型id',
  `title` varchar(64) DEFAULT '' COMMENT '消息标题',
  `summary` varchar(64) DEFAULT '' COMMENT '消息摘要',
  `content` text COMMENT '内容',
  `url` varchar(512) DEFAULT '' COMMENT '外部链接',
  `status` int(1) DEFAULT '0' COMMENT '0.有效，1.无效',
  `create_user` varchar(36) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消息表';

-- ----------------------------
-- Records of t_notice
-- ----------------------------

-- ----------------------------
-- Table structure for t_notice_type
-- ----------------------------
DROP TABLE IF EXISTS `t_notice_type`;
CREATE TABLE `t_notice_type` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `type` varchar(10) DEFAULT '' COMMENT '消息类型',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消息类型表';

-- ----------------------------
-- Records of t_notice_type
-- ----------------------------
INSERT INTO `t_notice_type` VALUES ('1', '消息', '2018-06-04 20:01:41');
INSERT INTO `t_notice_type` VALUES ('2', '通知', '2018-06-04 20:02:12');
INSERT INTO `t_notice_type` VALUES ('3', '公告', '2018-06-04 20:02:15');

-- ----------------------------
-- Table structure for t_notice_user_look
-- ----------------------------
DROP TABLE IF EXISTS `t_notice_user_look`;
CREATE TABLE `t_notice_user_look` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `user_id` varchar(36) DEFAULT '' COMMENT '用户id',
  `notice_id` varchar(36) DEFAULT '' COMMENT '消息id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='消息查看记录';

-- ----------------------------
-- Records of t_notice_user_look
-- ----------------------------

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `transaction_pair` varchar(10) DEFAULT '' COMMENT '交易对',
  `type` int(1) DEFAULT '0' COMMENT '0.卖出,1.买入',
  `order_no` varchar(64) DEFAULT '' COMMENT '订单号',
  `adveru_user_id` varchar(36) CHARACTER SET utf8 DEFAULT '' COMMENT '发广告的人',
  `advert_id` varchar(36) DEFAULT '' COMMENT '广告id',
  `user_id` varchar(36) DEFAULT '' COMMENT '用户id',
  `unit_price` decimal(20,4) DEFAULT '0.0000' COMMENT 'AEC单价',
  `quantity` decimal(20,8) DEFAULT '0.00000000' COMMENT 'AEC数量',
  `money` decimal(20,8) DEFAULT '0.00000000' COMMENT '金额',
  `tax_rate` varchar(10) DEFAULT '' COMMENT '税率（填写人民币兑换其他币税率）',
  `cancel_status` int(1) DEFAULT '0' COMMENT '订单是否取消 0.未发起取消，1.发起取消，2.成功，3.失败',
  `appeal_status` int(1) DEFAULT '0' COMMENT '订单是否发起申诉：0.未发起，1.申诉中，2.申诉成功，3.申诉失败,4.申诉撤回',
  `status` int(1) DEFAULT '0' COMMENT '0.未支付，1，已付款，2.申诉中，3.已取消，4.完成',
  `role` int(1) DEFAULT '0' COMMENT '0.普通用户，1.商家，2.银商',
  `create_time` datetime DEFAULT NULL,
  `confirm_time` datetime DEFAULT NULL COMMENT '买家确认付款时间',
  `update_user` varchar(36) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  `appeal_user` varchar(36) DEFAULT '' COMMENT '申诉人',
  `appeal_time` datetime DEFAULT NULL COMMENT '申诉时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `inx_unique_orderno` (`order_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单表';

-- ----------------------------
-- Records of t_order
-- ----------------------------

-- ----------------------------
-- Table structure for t_order_appeal
-- ----------------------------
DROP TABLE IF EXISTS `t_order_appeal`;
CREATE TABLE `t_order_appeal` (
  `id` varchar(36) NOT NULL,
  `order_id` varchar(36) DEFAULT '' COMMENT '订单id',
  `appeal_id` varchar(36) DEFAULT '' COMMENT '申诉类型id',
  `appeal_content` varchar(64) DEFAULT '' COMMENT '申诉类型描述',
  `user_id` varchar(36) DEFAULT '',
  `content` text,
  `img` varchar(2048) DEFAULT '' COMMENT '图片链接，多张图片以逗号隔开',
  `status` int(1) DEFAULT '0' COMMENT '0.未处理，1.仲裁中，2. 撤回申诉，3.买家胜出，4.卖家胜出',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单申诉表';

-- ----------------------------
-- Records of t_order_appeal
-- ----------------------------

-- ----------------------------
-- Table structure for t_order_look
-- ----------------------------
DROP TABLE IF EXISTS `t_order_look`;
CREATE TABLE `t_order_look` (
  `id` varchar(36) NOT NULL DEFAULT '' COMMENT '订单id',
  `order_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='订单查看记录';

-- ----------------------------
-- Records of t_order_look
-- ----------------------------

-- ----------------------------
-- Table structure for t_raise_currency_apply
-- ----------------------------
DROP TABLE IF EXISTS `t_raise_currency_apply`;
CREATE TABLE `t_raise_currency_apply` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `user_id` varchar(36) DEFAULT '',
  `from_address` varchar(125) DEFAULT '',
  `to_address` varchar(125) DEFAULT '',
  `amount` decimal(20,8) DEFAULT '0.00000000' COMMENT '提币数量',
  `status` int(1) DEFAULT '0' COMMENT '提币申请状态：0.申请中，1.审核通过，2.审核不通过',
  `reason` varchar(512) DEFAULT '' COMMENT '原因',
  `create_user` varchar(36) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='提币申请表';

-- ----------------------------
-- Records of t_raise_currency_apply
-- ----------------------------

-- ----------------------------
-- Table structure for t_sync_block
-- ----------------------------
DROP TABLE IF EXISTS `t_sync_block`;
CREATE TABLE `t_sync_block` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `code` varchar(100) DEFAULT '',
  `value` varchar(100) DEFAULT '',
  `descri` varchar(10) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `inx_unique_code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='同步区块高度';

-- ----------------------------
-- Records of t_sync_block
-- ----------------------------
INSERT INTO `t_sync_block` VALUES ('1', 'SYNC_BTC_BLOCK_NO', '31214', '同步比特币最新区块号', '2018-06-02 17:59:45', '2018-07-06 10:57:17');
INSERT INTO `t_sync_block` VALUES ('2', 'SYNC_ETH_BLOCK_NO', '737783', '同步以太坊最新区块号', '2018-06-10 15:04:45', '2018-06-29 20:29:43');
INSERT INTO `t_sync_block` VALUES ('3', 'SYNC_LTC_BLOCK_NO', '1438155', '同步莱特币最新区块号', '2018-06-02 18:00:37', '2018-06-12 19:34:18');
INSERT INTO `t_sync_block` VALUES ('4', 'SYNC_AEC_BLOCK_NO', '737741', '同步USD最新区块号', '2018-06-08 18:16:56', '2018-06-29 20:20:15');
INSERT INTO `t_sync_block` VALUES ('5', 'SYNC_MSC_BLOCK_NO', '737764', '同步MSC最新区块号', '2018-06-08 18:17:41', '2018-06-29 20:25:15');

-- ----------------------------
-- Table structure for t_sync_external_transaction
-- ----------------------------
DROP TABLE IF EXISTS `t_sync_external_transaction`;
CREATE TABLE `t_sync_external_transaction` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `type` varchar(10) DEFAULT '' COMMENT '币种类型：btc,ltc,AEC,MSC',
  `tx_hash` varchar(100) DEFAULT '' COMMENT '交易哈希',
  `from_address` varchar(125) DEFAULT '',
  `to_address` varchar(125) DEFAULT '',
  `amount` decimal(20,8) DEFAULT '0.00000000' COMMENT '到账数量',
  `fee` decimal(20,8) DEFAULT '0.00000000' COMMENT '交易手续费',
  `status` int(1) DEFAULT '0' COMMENT '0.未入账，1.已入账',
  `create_user` varchar(36) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='链外交易数据同步';

-- ----------------------------
-- Records of t_sync_external_transaction
-- ----------------------------

-- ----------------------------
-- Table structure for t_trade_out_log
-- ----------------------------
DROP TABLE IF EXISTS `t_trade_out_log`;
CREATE TABLE `t_trade_out_log` (
  `id` char(36) NOT NULL,
  `shopUserId` char(36) DEFAULT NULL COMMENT '商家用户id',
  `userId` char(36) DEFAULT NULL COMMENT '会员用户id',
  `billCode` varchar(100) DEFAULT NULL,
  `ordNo` varchar(50) DEFAULT NULL COMMENT '商家订单号',
  `tradeType` varchar(10) DEFAULT NULL COMMENT '交易类型:A会员转商家/B商家转会员',
  `coinType` varchar(10) DEFAULT '' COMMENT '交易币种:AEC/MSC',
  `amount` decimal(30,8) DEFAULT NULL COMMENT '金额',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '交易状态: 0已生成 1已完成',
  `createTime` datetime DEFAULT NULL,
  `lastTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_userId` (`userId`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_trade_out_log
-- ----------------------------

-- ----------------------------
-- Table structure for t_transaction_pair
-- ----------------------------
DROP TABLE IF EXISTS `t_transaction_pair`;
CREATE TABLE `t_transaction_pair` (
  `id` varchar(36) NOT NULL,
  `type` varchar(10) DEFAULT '' COMMENT '交易对',
  `create_user` varchar(36) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='交易对信息';

-- ----------------------------
-- Records of t_transaction_pair
-- ----------------------------
INSERT INTO `t_transaction_pair` VALUES ('a', 'AEC/CNY', '', '2018-06-01 17:23:03');
INSERT INTO `t_transaction_pair` VALUES ('b', 'AEC/USD', '', '2018-06-01 17:23:20');
INSERT INTO `t_transaction_pair` VALUES ('c', 'AEC/EUR', '', '2018-06-01 17:24:23');
INSERT INTO `t_transaction_pair` VALUES ('d', 'AEC/HKD', '', '2018-06-01 17:24:54');
INSERT INTO `t_transaction_pair` VALUES ('e', 'BTC/AEC', '', '2018-06-02 17:25:35');
INSERT INTO `t_transaction_pair` VALUES ('f', 'LTC/AEC', '', '2018-06-02 17:25:39');
INSERT INTO `t_transaction_pair` VALUES ('g', 'AEC/MSC', '', '2018-06-02 17:25:41');
INSERT INTO `t_transaction_pair` VALUES ('h', 'AEC/AEC', '', '2018-06-02 17:32:01');
INSERT INTO `t_transaction_pair` VALUES ('i', 'MSC/MSC', '', '2018-06-02 17:32:06');
INSERT INTO `t_transaction_pair` VALUES ('j', 'BTC/BTC', '', '2018-06-02 17:32:08');
INSERT INTO `t_transaction_pair` VALUES ('k', 'LTC/LTC', '', '2018-06-02 17:32:10');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `name` varchar(10) DEFAULT '' COMMENT '名称',
  `uid` varchar(50) DEFAULT '' COMMENT '账户id,注册时由用户填写',
  `nick_name` varchar(20) DEFAULT '' COMMENT '昵称',
  `national_code` varchar(10) DEFAULT '' COMMENT '手机区号',
  `phone` varchar(11) CHARACTER SET utf8 DEFAULT '' COMMENT '手机号',
  `email` varchar(64) DEFAULT '' COMMENT '邮箱',
  `gender` int(1) DEFAULT '0' COMMENT '性别(0.未知, 1.男, 2.女)',
  `avatar` varchar(512) DEFAULT '' COMMENT '头像',
  `status` int(1) DEFAULT '0' COMMENT '0.有效，1.禁用,，2.注销',
  `level` int(1) DEFAULT '0' COMMENT '用户等级：',
  `role` int(1) DEFAULT '0' COMMENT '用户角色：0.普通用户，2.银商',
  `password` varchar(255) DEFAULT '' COMMENT '登录密码',
  `salt` varchar(255) DEFAULT NULL COMMENT '盐',
  `pay_password` varchar(255) DEFAULT '' COMMENT '支付密码',
  `pay_salt` varchar(255) DEFAULT '' COMMENT '支付盐',
  `id_card` varchar(64) DEFAULT '' COMMENT '身份证号',
  `login_status` int(1) DEFAULT '2' COMMENT '银商登录状态：0.在线，1.离线，2.未知',
  `is_shop` int(1) DEFAULT '0' COMMENT '0、非商家 1、商家',
  `create_user` varchar(36) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
-- Records of t_user
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_bank_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_bank_info`;
CREATE TABLE `t_user_bank_info` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `type` int(1) DEFAULT '0' COMMENT '0.银行卡,1.PayPal,2. 西联汇款,3. SWIFT国际汇款',
  `user_id` varchar(36) DEFAULT '',
  `name` varchar(10) DEFAULT '' COMMENT '姓名',
  `bank_name` varchar(36) DEFAULT '' COMMENT '开户银行',
  `bank_address` varchar(100) DEFAULT '' COMMENT '开启地址',
  `bank_no` varchar(64) DEFAULT '' COMMENT '银行卡号',
  `status` int(1) DEFAULT '0' COMMENT '0.启用，1.不启用，2.删除',
  `CNY` int(1) DEFAULT '1' COMMENT '是否支持人民币交易：0支持，1不支持',
  `USD` int(1) DEFAULT '1' COMMENT '是否支持美元交易：0支持，1不支持',
  `EUR` int(1) DEFAULT '1' COMMENT '是否支持欧元交易：0支持，1不支持',
  `HKD` int(1) DEFAULT '1' COMMENT '是否支持港币交易：0支持，1不支持',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='银行卡信息';

-- ----------------------------
-- Records of t_user_bank_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_login
-- ----------------------------
DROP TABLE IF EXISTS `t_user_login`;
CREATE TABLE `t_user_login` (
  `id` varchar(36) NOT NULL,
  `user_id` varchar(36) DEFAULT NULL COMMENT '用户id',
  `source` int(1) DEFAULT '1' COMMENT '登录平台：0.pc登录,1.普通用户app登录,2.银商app登录',
  `token` varchar(50) DEFAULT '' COMMENT '令牌内容',
  `expire_time` datetime DEFAULT NULL COMMENT '失效时间',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `status` int(1) DEFAULT NULL COMMENT '用户状态：0 登入，登出',
  `login_cnt` bigint(20) DEFAULT '0' COMMENT '登录次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `inx_source_token` (`source`,`token`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='用户登录日志';

-- ----------------------------
-- Records of t_user_login
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_receive
-- ----------------------------
DROP TABLE IF EXISTS `t_user_receive`;
CREATE TABLE `t_user_receive` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `user_id` varchar(36) DEFAULT '',
  `type` int(1) DEFAULT '0' COMMENT '0.支付宝，1.微信',
  `name` varchar(255) DEFAULT '',
  `no` varchar(64) DEFAULT '' COMMENT '账号',
  `address` varchar(512) DEFAULT '' COMMENT '收款二维码地址',
  `status` int(1) DEFAULT '0' COMMENT '0.启用，1.不启用，2.删除',
  `remark` varchar(512) DEFAULT '' COMMENT '备注',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `inx_userid` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='收款（支付宝、微信）信息表';

-- ----------------------------
-- Records of t_user_receive
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_recommend
-- ----------------------------
DROP TABLE IF EXISTS `t_user_recommend`;
CREATE TABLE `t_user_recommend` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `recommend_id` varchar(36) DEFAULT '' COMMENT '推荐人id',
  `referee_id` varchar(36) DEFAULT '' COMMENT '被推荐人id',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='推荐表';

-- ----------------------------
-- Records of t_user_recommend
-- ----------------------------

-- ----------------------------
-- Table structure for t_user_sms
-- ----------------------------
DROP TABLE IF EXISTS `t_user_sms`;
CREATE TABLE `t_user_sms` (
  `id` varchar(36) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `phone` varchar(11) DEFAULT '' COMMENT '手机号码',
  `type` int(1) DEFAULT '0' COMMENT '0 注册，1修改登录密码，2 修改支付密码 3 转账 4 更换手机号、5 忘记登录密码',
  `code` varchar(20) DEFAULT '' COMMENT '验证码',
  `status` int(1) DEFAULT '0' COMMENT '0 未使用，1 已使用',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='短信验证码表';

-- ----------------------------
-- Records of t_user_sms
-- ----------------------------

-- ----------------------------
-- Table structure for t_wallet_info
-- ----------------------------
DROP TABLE IF EXISTS `t_wallet_info`;
CREATE TABLE `t_wallet_info` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `user_id` varchar(36) DEFAULT '' COMMENT '用户id',
  `status` int(1) DEFAULT '0' COMMENT '0 有效，1 禁用',
  `type` varchar(10) DEFAULT '' COMMENT '币种类型',
  `balance` decimal(20,8) DEFAULT '0.00000000' COMMENT '账户余额',
  `frozen_blance` decimal(20,8) DEFAULT '0.00000000' COMMENT '冻结金额',
  `bond` decimal(20,8) DEFAULT '0.00000000' COMMENT '保证金',
  `address` varchar(125) DEFAULT '' COMMENT '钱包地址',
  `wallet_password` varchar(125) DEFAULT '' COMMENT '钱包地址（以太坊系有效）',
  `wallet_file` varchar(512) DEFAULT '' COMMENT '钱包信息文件（以太坊系有效）',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `inx_userid_type` (`user_id`,`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='钱包信息';

-- ----------------------------
-- Records of t_wallet_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_wallet_preset_address
-- ----------------------------
DROP TABLE IF EXISTS `t_wallet_preset_address`;
CREATE TABLE `t_wallet_preset_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(10) DEFAULT NULL COMMENT '钱包类型',
  `status` int(1) DEFAULT '0' COMMENT '是否绑定 0 未知，1未绑定  2绑定',
  `user_id` varchar(36) DEFAULT '' COMMENT '用户id',
  `address` varchar(125) NOT NULL COMMENT '预置生成地址',
  `account` varchar(36) DEFAULT NULL COMMENT '账户：针对比特币系地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `address` (`address`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15425 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of t_wallet_preset_address
-- ----------------------------

-- ----------------------------
-- Table structure for t_wallet_record
-- ----------------------------
DROP TABLE IF EXISTS `t_wallet_record`;
CREATE TABLE `t_wallet_record` (
  `id` varchar(36) NOT NULL,
  `transaction_pair` varchar(10) DEFAULT '' COMMENT '交易对',
  `from_id` varchar(36) DEFAULT '' COMMENT 'from用户id',
  `to_id` varchar(36) DEFAULT '' COMMENT 'to用户id',
  `from_address` varchar(125) DEFAULT '' COMMENT 'from钱包地址',
  `to_address` varchar(125) DEFAULT '' COMMENT 'to钱包地址',
  `from_amount` decimal(20,8) DEFAULT '0.00000000' COMMENT 'from总量',
  `to_amount` decimal(20,8) DEFAULT '0.00000000' COMMENT '到账金额',
  `fee` decimal(20,8) DEFAULT '0.00000000' COMMENT '交易手续费',
  `rate` varchar(10) DEFAULT '0' COMMENT '手续费率',
  `operate` int(1) DEFAULT '0' COMMENT '0.平台拨币，1.充币，2.提币，3.转账，4.c2c转账，5.兑换（AEC兑换MSC，BTC兑换AEC，LTC兑换AEC），6.冻结，7.解冻，8.提高保证金，9.降低保证金，10.差额奖励，11.MSC释放，12.股权分红，13.代数奖励 14、商家付款，15.平台扣币，16.提取保证金，17.成为银商,18.BTC兑换AEC,19.LTC兑换AEC,,20.注册奖励MSC,21.推荐奖励MSC',
  `descri` varchar(100) DEFAULT '' COMMENT '描述',
  `source` varchar(100) DEFAULT '' COMMENT '来源：如c2c交易订单id,同步链外交易id..',
  `trans_type` int(11) DEFAULT '0' COMMENT '0.内部交易，1.链外交易(转入)，2.链外交易（转出）',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='钱包交易明细';

-- ----------------------------
-- Records of t_wallet_record
-- ----------------------------

-- ----------------------------
-- Table structure for t_wallet_system
-- ----------------------------
DROP TABLE IF EXISTS `t_wallet_system`;
CREATE TABLE `t_wallet_system` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `wallet_balance_aec` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT 'AEC可用额统计',
  `wallet_frozen_blance_aec` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT 'AEC冻结额统计',
  `wallet_total_aec` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT 'AEC总额',
  `wallet_balance_msc` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT 'MSC可用额总额',
  `wallet_frozen_blance_msc` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT 'MSC冻结额统计',
  `wallet_total_msc` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT 'MSC总额',
  `wallet_balance_ltc` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT 'LTC可用额统计',
  `wallet_frozen_balance_ltc` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT 'LTC冻结额统计',
  `wallet_total_ltc` decimal(20,8) unsigned zerofill NOT NULL DEFAULT '000000000000.00000000' COMMENT 'LTC总额',
  `wallet_balance_btc` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT 'BTC可用额统计',
  `wallet_frozen_balance_btc` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT 'BTC冻结额统计',
  `wallet_total_btc` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT 'BTC总额',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `btc_total` double NOT NULL DEFAULT '0',
  `ltc_total` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='按日统计钱包表中各类型币的总量';

-- ----------------------------
-- Records of t_wallet_system
-- ----------------------------

-- ----------------------------
-- Table structure for t_yinshang_advert
-- ----------------------------
DROP TABLE IF EXISTS `t_yinshang_advert`;
CREATE TABLE `t_yinshang_advert` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `type` int(1) DEFAULT '0' COMMENT '0.卖出，1.买入',
  `stock` decimal(20,8) DEFAULT '0.00000000' COMMENT '银商该笔广告AEC总量',
  `enable_stock` decimal(20,8) DEFAULT '0.00000000' COMMENT '银商该笔广告可用AEC总量',
  `unit_price` decimal(20,4) DEFAULT '0.0000' COMMENT '单价',
  `is_limit` int(1) DEFAULT '0' COMMENT '0.限制，1.不限制(弃用)',
  `min_value` decimal(20,8) DEFAULT '0.00000000' COMMENT '最小值',
  `max_value` decimal(20,8) DEFAULT '0.00000000' COMMENT '最大值',
  `status` int(1) DEFAULT '0' COMMENT '0.上架，1.下架',
  `CNY` int(1) DEFAULT '1' COMMENT '是否支持人民币交易：0支持，1不支持',
  `USD` int(1) DEFAULT '1' COMMENT '是否支持美元交易：0支持，1不支持',
  `EUR` int(1) DEFAULT '1' COMMENT '是否支持欧元交易：0支持，1不支持',
  `HKD` int(1) DEFAULT '1' COMMENT '是否支持港币交易：0支持，1不支持',
  `create_user` varchar(36) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='银商发布广告表';

-- ----------------------------
-- Records of t_yinshang_advert
-- ----------------------------

-- ----------------------------
-- Table structure for t_yinshang_apply
-- ----------------------------
DROP TABLE IF EXISTS `t_yinshang_apply`;
CREATE TABLE `t_yinshang_apply` (
  `id` varchar(36) NOT NULL DEFAULT '',
  `user_id` varchar(36) DEFAULT '',
  `status` int(1) DEFAULT '0' COMMENT '0.申请中，1.驳回，2.通过',
  `reason` varchar(512) DEFAULT '' COMMENT '驳回原因',
  `data_flag_status` int(1) NOT NULL DEFAULT '0' COMMENT '0、有效  1、失效',
  `create_time` datetime DEFAULT NULL,
  `update_user` varchar(36) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='银商申请表';

-- ----------------------------
-- Records of t_yinshang_apply
-- ----------------------------

-- ----------------------------
-- Table structure for t_yinshang_is_pay
-- ----------------------------
DROP TABLE IF EXISTS `t_yinshang_is_pay`;
CREATE TABLE `t_yinshang_is_pay` (
  `id` varchar(36) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `user_id` varchar(36) DEFAULT NULL,
  `CNY` int(1) DEFAULT '1' COMMENT '是否支持人民币交易：0支持，1不支持',
  `USD` int(1) DEFAULT '1' COMMENT '是否支持美元交易：0支持，1不支持',
  `EUR` int(1) DEFAULT '1' COMMENT '是否支持欧元交易：0支持，1不支持',
  `HKD` int(1) DEFAULT '1' COMMENT '是否支持港币交易：0支持，1不支持',
  `Alipay` int(1) DEFAULT '1' COMMENT '是否支持支付宝：0支持，1不支持',
  `PayPal` int(1) DEFAULT '1' COMMENT '是否支持Paypal：0支持，1不支持',
  `Xilian` int(1) DEFAULT '1' COMMENT '是否支持西联汇款：0支持，1不支持',
  `SWIFT` int(1) DEFAULT '1' COMMENT '是否支持SWIFT国际汇款：0支持，1不支持',
  `Weixin` int(1) DEFAULT '1' COMMENT '是否支持微信汇款：0支持，1不支持',
  `create_time` datetime DEFAULT NULL,
  `upate_user` varchar(36) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `inx_userid` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='银商支持支付方式表';

-- ----------------------------
-- Records of t_yinshang_is_pay
-- ----------------------------

-- ----------------------------
-- Table structure for t_yinshang_quantity
-- ----------------------------
DROP TABLE IF EXISTS `t_yinshang_quantity`;
CREATE TABLE `t_yinshang_quantity` (
  `advert_user_id` varchar(36) NOT NULL,
  `quantity` decimal(20,8) DEFAULT '0.00000000',
  PRIMARY KEY (`advert_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='银商卖出aec数量（对应订单状态未支付，已付款，申诉中）';

-- ----------------------------
-- Records of t_yinshang_quantity
-- ----------------------------

-- ----------------------------
-- Table structure for tb_eth_transaction_receipt
-- ----------------------------
DROP TABLE IF EXISTS `tb_eth_transaction_receipt`;
CREATE TABLE `tb_eth_transaction_receipt` (
  `hash` varchar(100) NOT NULL,
  `status` char(1) DEFAULT NULL COMMENT '0 未确认\r\n1 完成\r\n2 失败',
  `createTime` datetime DEFAULT NULL,
  `lastTime` datetime DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `receipt` text,
  PRIMARY KEY (`hash`) USING BTREE,
  KEY `idx_status` (`status`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of tb_eth_transaction_receipt
-- ----------------------------

-- ----------------------------
-- Table structure for web_user
-- ----------------------------
DROP TABLE IF EXISTS `web_user`;
CREATE TABLE `web_user` (
  `id` varchar(36) CHARACTER SET utf8mb4 NOT NULL,
  `user_name` varchar(50) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '用户名',
  `user_nick` varchar(50) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '用户昵称',
  `phone` varchar(11) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '手机号',
  `email` varchar(64) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '邮箱',
  `status` int(1) DEFAULT '0' COMMENT '用户状态：0有效，-1冻结',
  `password` varchar(64) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '登录密码',
  `pay_password` varchar(64) CHARACTER SET utf8mb4 DEFAULT '' COMMENT '支付密码',
  `token` varchar(64) CHARACTER SET utf8mb4 DEFAULT '' COMMENT 'token',
  `token_expire_time` datetime DEFAULT NULL COMMENT 'token过期时间',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `unique_username` (`user_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='后台用户表';

-- ----------------------------
-- Records of web_user
-- ----------------------------
INSERT INTO `web_user` VALUES ('1', 'admin', '管理员', '12345678910', '', '0', 'e10adc3949ba59abbe56e057f20f883e', '', 'c5c82d9f8fa5444a7d55ad7a5bf35784', '2018-07-05 17:07:25', '2018-06-19 19:02:37', '2018-06-05 19:02:39');

-- ----------------------------
-- Procedure structure for appealOrder
-- ----------------------------
DROP PROCEDURE IF EXISTS `appealOrder`;
DELIMITER ;;
CREATE DEFINER=`block`@`%` PROCEDURE `appealOrder`(in formId varchar(36),in toId varchar(36),in fromAmount decimal(20,8),in toAmount decimal(20,8),in coinType varchar(20))
BEGIN
DECLARE balanceTemp VARCHAR(50);
DECLARE bondTemp VARCHAR(50);
DECLARE coinTypeTemp VARCHAR(20);
DECLARE result int;
set result=0;
set coinTypeTemp = substring_index(coinType, '/', 1);
select balance,bond into balanceTemp,bondTemp from t_wallet_info where user_id=formId and type=coinTypeTemp;
IF (balanceTemp>=fromAmount) 
THEN
update t_wallet_info set balance=balance-fromAmount where user_id=formId and type=coinTypeTemp;
update t_wallet_info set balance=balance+toAmount where user_id=toId and type=coinTypeTemp;
set result=1;
ELSEIF ((balanceTemp+bondTemp)>=fromAmount and balanceTemp < fromAmount)
THEN
update t_wallet_info set bond=bond-(fromAmount-balance),balance=0 where user_id=formId and type=coinTypeTemp;
update t_wallet_info set balance=balance+toAmount where user_id=toId and type=coinTypeTemp;
set result=1;
END IF;
select result;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for p_bind_wallet_info
-- ----------------------------
DROP PROCEDURE IF EXISTS `p_bind_wallet_info`;
DELIMITER ;;
CREATE DEFINER=`block`@`%` PROCEDURE `p_bind_wallet_info`(in userId VARCHAR(36))
begin 

	update t_wallet_preset_address set `status` = 2,user_id = userId where `status`=1 and type = 'BTC' limit 1;
	update t_wallet_preset_address set `status` = 2,user_id = userId where `status`=1 and type = 'LTC' limit 1;
	update t_wallet_preset_address set `status` = 2,user_id = userId where `status`=1 and type = 'ETH' limit 1;
	
	begin
			declare walletType VARCHAR(10);
			declare walletAddress varchar(64);
			declare done int default 0;				
			declare rs cursor for select type,address from t_wallet_preset_address where user_id = userId;		
		  declare continue handler for not found set done=1;
	    open rs;
				 fetch rs into walletType,walletAddress;
		     while done=0 do
				     insert into t_wallet_info(id,user_id,type,address,create_time) VALUES(uuid(),userId,walletType,walletAddress,now());
						 if walletType='ETH' then
								 insert into t_wallet_info(id,user_id,type,address,create_time) VALUES(uuid(),userId,'AEC',walletAddress,now());
								 insert into t_wallet_info(id,user_id,type,address,create_time) VALUES(uuid(),userId,'MSC',walletAddress,now());
						 end if;
				     fetch rs into walletType,walletAddress;
				 end while;
	    close rs;						
	end;
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for proc_addNum
-- ----------------------------
DROP PROCEDURE IF EXISTS `proc_addNum`;
DELIMITER ;;
CREATE DEFINER=`block`@`%` PROCEDURE `proc_addNum`(in x int,in y int,out sum int)
BEGIN
SET sum= x + y;
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for totalWalletInfo
-- ----------------------------
DROP PROCEDURE IF EXISTS `totalWalletInfo`;
DELIMITER ;;
CREATE DEFINER=`block`@`%` PROCEDURE `totalWalletInfo`()
    COMMENT '统计系统内用户各类型币的总量'
BEGIN
	
	set @totalSql = "insert into t_wallet_system (
	wallet_balance_aec, wallet_frozen_blance_aec, wallet_total_aec, 
	wallet_balance_msc, wallet_frozen_blance_msc, wallet_total_msc,
	wallet_balance_ltc, wallet_frozen_balance_ltc, wallet_total_ltc, 
	wallet_balance_btc, wallet_frozen_balance_btc, wallet_total_btc)
	select 
	a.wallet_balance_aec, a.wallet_frozen_blance_aec, a.wallet_total_aec, 
	b.wallet_balance_msc, b.wallet_frozen_blance_msc, b.wallet_total_msc,
	c.wallet_balance_ltc, c.wallet_frozen_balance_ltc, c.wallet_total_ltc, 
	d.wallet_balance_btc, d.wallet_frozen_balance_btc, d.wallet_total_btc
	from 
	(select sum(balance) wallet_balance_aec, sum(frozen_blance) wallet_frozen_blance_aec, sum(balance + frozen_blance) wallet_total_aec, 0 _type from t_wallet_info where type = 'AEC' ) a left join
	(select sum(balance) wallet_balance_msc, sum(frozen_blance) wallet_frozen_blance_msc, sum(balance + frozen_blance) wallet_total_msc, 0 _type from t_wallet_info where type = 'MSC' ) b on a._type = b._type left join
	(select sum(balance) wallet_balance_ltc, sum(frozen_blance) wallet_frozen_balance_ltc, sum(balance + frozen_blance) wallet_total_ltc, 0 _type from t_wallet_info where type = 'LTC' ) c on a._type = c._type left join
	(select sum(balance) wallet_balance_btc, sum(frozen_blance) wallet_frozen_balance_btc, sum(balance + frozen_blance) wallet_total_btc, 0 _type from t_wallet_info where type = 'BTC' ) d on a._type = d._type";
	
	
	
	PREPARE stmt FROM @totalSql;
   EXECUTE stmt ;
   DEALLOCATE PREPARE stmt;
	

END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for queryParentUserIds
-- ----------------------------
DROP FUNCTION IF EXISTS `queryParentUserIds`;
DELIMITER ;;
CREATE DEFINER=`block`@`%` FUNCTION `queryParentUserIds`(userId VARCHAR(36)) RETURNS varchar(1000) CHARSET utf8mb4
BEGIN
		DECLARE fid VARCHAR (36) DEFAULT NULL;
		DECLARE parentIds VARCHAR (1000) DEFAULT userId;
		WHILE userId IS NOT NULL DO
			SET fid = (
				SELECT
					tur.recommend_id
				FROM
					t_user_recommend tur
				WHERE
					tur.referee_id = userId
			);

			IF fid IS NOT NULL THEN
				SET parentIds = CONCAT(parentIds,',',fid);	
			END IF;
			
			SET userId = fid;

		END WHILE;
		
		RETURN parentIds;
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for totalWalletCoin
-- ----------------------------
DROP EVENT IF EXISTS `totalWalletCoin`;
DELIMITER ;;
CREATE DEFINER=`block`@`%` EVENT `totalWalletCoin` ON SCHEDULE EVERY 1 DAY STARTS '2018-06-15 23:58:00' ON COMPLETION NOT PRESERVE ENABLE COMMENT '统计系统内用户钱包各币种总额' DO BEGIN

	CALL totalWalletInfo();
	
END
;;
DELIMITER ;
