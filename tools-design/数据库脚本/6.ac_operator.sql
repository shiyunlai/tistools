/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : mysql

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-09-12 14:44:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ac_operator
-- ----------------------------
DROP TABLE IF EXISTS `ac_operator`;
CREATE TABLE `ac_operator` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `USER_ID` varchar(64) NOT NULL COMMENT '登录用户名',
  `PASSWORD` varchar(100) DEFAULT NULL COMMENT '密码',
  `OPERATOR_NAME` varchar(64) DEFAULT NULL COMMENT '操作员姓名 : 记录当前操作员姓名（只记录当前值，不随之改变）',
  `OPERATOR_STATUS` varchar(255) NOT NULL COMMENT '操作员状态 : 取值来自业务菜单：DICT_AC_OPERATOR_STATUS\r\n正常，挂起，注销，锁定...\r\n系统处理状态间的流转',
  `INVAL_DATE` timestamp NULL DEFAULT NULL COMMENT '密码失效日期 : 指定失效时间具体到时分秒',
  `AUTH_MODE` varchar(255) NOT NULL COMMENT '认证模式 : 取值来自业务菜单：DICT_AC_AUTHMODE\r\n如：本地密码认证、LDAP认证、等\r\n可以多选，以逗号分隔，且按照出现先后顺序进行认证；\r\n如：\r\npwd,captcha\r\n表示输入密码，并且还需要验证码',
  `LOCK_LIMIT` decimal(4,0) NOT NULL DEFAULT '5' COMMENT '锁定次数限制 : 登陆错误超过本数字，系统锁定操作员，默认5次。\r\n可为操作员单独设置；',
  `ERR_COUNT` decimal(10,0) DEFAULT NULL COMMENT '当前错误登录次数',
  `LOCK_TIME` timestamp NULL DEFAULT NULL COMMENT '锁定时间',
  `UNLOCK_TIME` timestamp NULL DEFAULT NULL COMMENT '解锁时间 : 当状态为锁定时，解锁的时间',
  `MENU_TYPE` varchar(255) NOT NULL COMMENT '菜单风格 : 取值来自业务菜单：DICT_AC_MENUTYPE\r\n用户登录后菜单的风格',
  `LAST_LOGIN` timestamp NULL DEFAULT NULL COMMENT '最近登录时间',
  `START_DATE` date DEFAULT NULL COMMENT '有效开始日期 : 启用操作员时设置，任何时间可设置；',
  `END_DATE` date DEFAULT NULL COMMENT '有效截止日期 : 启用操作员时设置，任何时间可设置；',
  `VALID_TIME` varchar(1024) DEFAULT NULL COMMENT '允许时间范围 : 定义一个规则表达式，表示允许操作的有效时间范围，格式为：\r\n[{begin:"HH:mm",end:"HH:mm"},{begin:"HH:mm",end:"HH:mm"},...]\r\n如：\r\n[{begin:"08:00",end:"11:30"},{begin:"14:30",end:"17:00"}]\r\n表示，该操作员被允许每天有两个时间段进行系统操作，分别 早上08:00 - 11:30，下午14:30 － 17:00 ',
  `MAC_CODE` varchar(1024) DEFAULT NULL COMMENT '允许MAC码 : 允许设置多个MAC，以逗号分隔，控制操作员只能在这些机器上登陆。',
  `IP_ADDRESS` varchar(1024) DEFAULT NULL COMMENT '允许IP地址 : 允许设置多个IP地址',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员 : 系统登录用户表，一个用户只能有一个或零个操作员';

-- ----------------------------
-- Records of ac_operator
-- ----------------------------
INSERT INTO `ac_operator` VALUES ('111', 'admin', '96e79218965eb72c92a549dd5a330112', '系统管理员', 'login', null, 'password', '5', '0', '2017-08-11 13:38:10', null, 'default', '2017-09-12 14:22:26', null, null, null, null, null);

-- ----------------------------
-- Table structure for ac_operator_func
-- ----------------------------
DROP TABLE IF EXISTS `ac_operator_func`;
CREATE TABLE `ac_operator_func` (
  `GUID_OPERATOR` varchar(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_FUNC` varchar(128) NOT NULL COMMENT '功能GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `AUTH_TYPE` varchar(255) NOT NULL COMMENT '授权标志 : 取值来自业务菜单：DICT_AC_AUTHTYPE\r\n如：特别禁止、特别允许',
  `START_DATE` date DEFAULT NULL COMMENT '有效开始日期',
  `END_DATE` date DEFAULT NULL COMMENT '有效截至日期',
  `GUID_APP` varchar(128) DEFAULT NULL COMMENT '应用GUID : 冗余字段',
  `GUID_FUNCGROUP` varchar(128) DEFAULT NULL COMMENT '功能组GUID : 冗余字段'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员特殊权限配置 : 针对人员配置的特殊权限，如特别开通的功能，或者特别禁止的功能';

-- ----------------------------
-- Records of ac_operator_func
-- ----------------------------
INSERT INTO `ac_operator_func` VALUES ('OPERATOR1501215851', 'FUNC1500601505', 'Y', '2017-08-28', '2017-08-31', 'APP1499956132', 'FUNCGROUP1500544715');

-- ----------------------------
-- Table structure for ac_operator_identity
-- ----------------------------
DROP TABLE IF EXISTS `ac_operator_identity`;
CREATE TABLE `ac_operator_identity` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_OPERATOR` varchar(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `IDENTITY_NAME` varchar(255) NOT NULL COMMENT '身份名称',
  `IDENTITY_FLAG` char(1) NOT NULL COMMENT '默认身份标志 : 见业务字典： DICT_YON\r\n只能有一个默认身份 Y是默认身份 N不是默认身份',
  `SEQ_NO` decimal(4,0) DEFAULT NULL COMMENT '显示顺序',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员身份 : 操作员对自己的权限进行组合形成一个固定的登录身份；\r\n供登录时选项，每一个登录身份是员工操作员的权限子集';

-- ----------------------------
-- Records of ac_operator_identity
-- ----------------------------
INSERT INTO `ac_operator_identity` VALUES ('3', '111', '全功能权限', 'Y', '1');

-- ----------------------------
-- Table structure for ac_operator_role
-- ----------------------------
DROP TABLE IF EXISTS `ac_operator_role`;
CREATE TABLE `ac_operator_role` (
  `GUID_OPERATOR` varchar(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_ROLE` varchar(128) NOT NULL COMMENT '拥有角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `AUTH` varchar(255) DEFAULT NULL COMMENT '是否可分级授权 : 预留字段，暂不使用。意图将操作员所拥有的权限赋予其他操作员。'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员与权限集（角色）对应关系 : 操作员与权限集（角色）对应关系表';

-- ----------------------------
-- Records of ac_operator_role
-- ----------------------------
INSERT INTO `ac_operator_role` VALUES ('111', 'ROLE1505052711', null);

-- ----------------------------
-- Table structure for ac_role_func
-- ----------------------------
DROP TABLE IF EXISTS `ac_role_func`;
CREATE TABLE `ac_role_func` (
  `GUID_ROLE` varchar(128) NOT NULL COMMENT '角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_FUNC` varchar(128) NOT NULL COMMENT '拥有功能GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_APP` varchar(128) NOT NULL COMMENT '应用GUID : 冗余字段',
  `GUID_FUNCGROUP` varchar(128) NOT NULL COMMENT '功能组GUID : 冗余字段'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限集(角色)功能对应关系 : 角色所包含的功能清单';

-- ----------------------------
-- Records of ac_role_func
-- ----------------------------
INSERT INTO `ac_role_func` VALUES ('ROLE1502354676', 'FUNC1500601536', 'APP1499956132', 'FUNCGROUP1500544717');
INSERT INTO `ac_role_func` VALUES ('ROLE1502354676', 'FUNC1500601537', 'APP1499956132', 'FUNCGROUP1500544717');
INSERT INTO `ac_role_func` VALUES ('ROLE1502354676', 'FUNC1500601538', 'APP1499956132', 'FUNCGROUP1500544717');
INSERT INTO `ac_role_func` VALUES ('ROLE1502354676', 'FUNC1500601539', 'APP1499956132', 'FUNCGROUP1500544717');
INSERT INTO `ac_role_func` VALUES ('ROLE1502354676', 'FUNC1500601540', 'APP1499956132', 'FUNCGROUP1500544718');
INSERT INTO `ac_role_func` VALUES ('ROLE1502354676', 'FUNC1500601543', 'APP1499956132', 'FUNCGROUP1500544718');
INSERT INTO `ac_role_func` VALUES ('ROLE1502354676', 'FUNC1502354964', 'APP1499956132', 'FUNCGROUP1500544718');
INSERT INTO `ac_role_func` VALUES ('ROLE1502354676', 'FUNC1504581747', 'APP1499956132', 'FUNCGROUP1500544718');
INSERT INTO `ac_role_func` VALUES ('ROLE1502354676', 'FUNC1504890587', 'APP1499956132', 'FUNCGROUP1500544718');
INSERT INTO `ac_role_func` VALUES ('ROLE1502354676', 'FUNC1504890589', 'APP1499956132', 'FUNCGROUP1505021734');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601486', 'APP1499956132', 'FUNCGROUP1500544715');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601487', 'APP1499956132', 'FUNCGROUP1500544715');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601488', 'APP1499956132', 'FUNCGROUP1500544715');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601505', 'APP1499956132', 'FUNCGROUP1500544715');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601528', 'APP1499956132', 'FUNCGROUP1500544715');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601529', 'APP1499956132', 'FUNCGROUP1500544716');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601530', 'APP1499956132', 'FUNCGROUP1500544716');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601531', 'APP1499956132', 'FUNCGROUP1500544716');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601532', 'APP1499956132', 'FUNCGROUP1500544716');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601534', 'APP1499956132', 'FUNCGROUP1500544716');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601535', 'APP1499956132', 'FUNCGROUP1500544716');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601536', 'APP1499956132', 'FUNCGROUP1500544717');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601537', 'APP1499956132', 'FUNCGROUP1500544717');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601538', 'APP1499956132', 'FUNCGROUP1500544717');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601539', 'APP1499956132', 'FUNCGROUP1500544717');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601540', 'APP1499956132', 'FUNCGROUP1500544718');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1500601543', 'APP1499956132', 'FUNCGROUP1500544718');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1502354964', 'APP1499956132', 'FUNCGROUP1500544718');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1504581747', 'APP1499956132', 'FUNCGROUP1500544718');
INSERT INTO `ac_role_func` VALUES ('ROLE1505052711', 'FUNC1504890587', 'APP1499956132', 'FUNCGROUP1500544718');
