/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : mysql

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-10-20 09:47:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ac_config
-- ----------------------------
DROP TABLE IF EXISTS `ac_config`;
CREATE TABLE `ac_config` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键',
  `GUID_APP` varchar(128) NOT NULL COMMENT '应用GUID',
  `CONFIG_TYPE` varchar(64) NOT NULL COMMENT '配置类型',
  `CONFIG_NAME` varchar(64) NOT NULL COMMENT '配置名',
  `CONFIG_DICT` varchar(256) NOT NULL COMMENT '配置值字典',
  `CONFIG_STYLE` varchar(64) DEFAULT NULL,
  `CONFIG_VALUE` varchar(128) NOT NULL COMMENT '默认配置值',
  `ENABLED` varchar(10) DEFAULT NULL COMMENT '是否启用',
  `DISPLAY_ORDER` decimal(4,0) DEFAULT NULL,
  `CONFIG_DESC` varchar(512) DEFAULT NULL COMMENT '配置描述说明',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='个性化配置';

-- ----------------------------
-- Records of ac_config
-- ----------------------------
INSERT INTO `ac_config` VALUES ('OPERATORCFG1508463961', 'APP1499956132', 'style', '测试', 'DICT_STYLE_COLOR', 'radio', 'darkblue', 'Y', '0', null);

-- ----------------------------
-- Table structure for ac_operator_config
-- ----------------------------
DROP TABLE IF EXISTS `ac_operator_config`;
CREATE TABLE `ac_operator_config` (
  `GUID_OPERATOR` varchar(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_CONFIG` varchar(128) NOT NULL COMMENT '配置GUID',
  `CONFIG_VALUE` varchar(1024) DEFAULT NULL COMMENT '配置值'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ac_operator_config
-- ----------------------------
INSERT INTO `ac_operator_config` VALUES ('111', 'OPERATORCFG1507720783', 'default');
