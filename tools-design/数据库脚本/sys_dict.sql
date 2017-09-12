/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : 127.0.0.1:3306
Source Database       : mysql

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-09-12 14:30:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `DICT_KEY` varchar(128) NOT NULL COMMENT '业务字典',
  `DICT_TYPE` char(1) NOT NULL COMMENT '类型 : 见业务字典： DICT_TYPEa 应用级（带业务含义的业务字典，应用开发时可扩展）s 系统级（平台自己的业务字典）',
  `DICT_NAME` varchar(128) DEFAULT NULL COMMENT '字典名称',
  `FROM_TYPE` char(1) DEFAULT NULL COMMENT '字典项的来源类型',
  `DICT_DESC` varchar(512) DEFAULT NULL COMMENT '解释说明',
  `GUID_PARENTS` varchar(128) DEFAULT NULL COMMENT '父字典GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `DEFAULT_VALUE` varchar(512) DEFAULT NULL COMMENT '业务字典默认值 : 指定某个字典项（ITEM_VALUE）为本业务字典的默认值（用于扶助View层实现展示默认值）',
  `FROM_TABLE` varchar(512) DEFAULT NULL COMMENT '字典项来源表 : 如果业务字典用来描述某个表中的字段选项，则本字段保存表名；\r\n其他情况默认为空；',
  `USE_FOR_KEY` varchar(512) DEFAULT NULL COMMENT '作为字典项的列 : 如果业务字典用来描述某个表中的字段选项，则本字段保存字段名；其他情况默认为空；',
  `USE_FOR_NAME` varchar(512) DEFAULT NULL COMMENT '作为字典项名称的列',
  `SQL_FILTER` varchar(512) DEFAULT NULL COMMENT 'SQL过滤条件',
  `SEQNO` decimal(12,0) DEFAULT NULL COMMENT '顺序号 : 顺序号，从0开始排，按小到大排序',
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `DICT_KEY` (`DICT_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务字典 : 业务字典表，定义系统中下拉菜单的数据（注意：仅仅包括下拉菜单中的数据，而不包括下拉菜单样式，是否多选这些与';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('DICT1501076672', 'DICT_BIZ_TYPE', 'A', '业务类型', '0', '银行业务的类型划分', 'DICT1501076672', null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076673', 'DICT_CONTACT_MODE', 'A', '接触方式', '0', '客户与银行接触的方式分类', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076674', 'DICT_TRANS_STATUS', 'A', '交易状态', '0', '交易流水的状态信息', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076675', 'DICT_ACTION_TYPE', 'S', '操作行为类型', '0', '柜员操作系统的行为类型', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076676', 'DICT_TRANS_PHASE', 'S', '交易操作阶段', '0', '对交易执行了什么操作', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076678', 'DICT_SERVICE_APPRAISE', 'A', '服务评价', '0', '客户对服务过程的评价', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076679', 'DICT_SERVICE_STATUS', 'A', '服务状态', '0', '客户服务过程的状态标识', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076680', 'DICT_SERVICE_TYPE', 'A', '服务类型', '0', '银行对客户服务的分类', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076681', 'DICT_SD_PAPERTYPE', 'A', '证件类型', '0', '', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076682', 'DICT_HSOTTRANS_STATUS', 'A', '主机交易状态', '0', '主机交易的状态信息', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076683', 'DICT_MSG_TYPE', 'S', '报文类型', '0', '主机交易报文的类型', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076684', 'DICT_QUEUE_BIZ_TYPE', 'A', '排队业务类型', '0', '客户排队时可以选择的办理业务类型', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076685', 'DICT_PREFILL_STATUS', 'A', '预填处理状态', '0', '预填单的生命周期状态', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076686', 'DICT_PROMOTING_BIZ_TYPE', 'A', '营销业务类型', '0', '营销业务类型可随业务而扩展增加', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076687', 'DICT_PROMOTING_FEEDBACK', 'A', '营销反馈', '0', '也是营销的结果', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076688', 'DICT_PROMOTING_ORIGIN', 'A', '营销信息来源', '0', '标识某条营销信息来自那个应用系统', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076689', 'DICT_YON', 'S', '是或否', '0', '表达是、否', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076690', 'DICT_TYPE', 'S', '业务字典类型', '0', '应用级，系统级', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076691', 'DICT_AC_PARTYTYPE', 'S', '组织类型', '0', '组织模型中的各种组织分类，角色权限集中定义PARTY', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076692', 'DICT_OM_ORGSTATUS', 'S', '机构状态', '0', '系统暂定了机构状态，可根据需求调整', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076693', 'DICT_SD_AREA', 'A', '地区编码', '0', '', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076694', 'DICT_OM_ORGDEGREE', 'S', '机构等级', '0', '根据银行对机构的分级进行定义', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076695', 'DICT_OM_ORGTYPE', 'S', '机构类型', '0', '根据机构性质进行的分类', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076696', 'DICT_SD_ZIPCODE', 'A', '邮编', '0', '', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076697', 'DICT_OM_BUSIDOMAIN', 'S', '业务条线', '0', '对机构进行业务化的‘条线’分域，按需维护', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076698', 'DICT_OM_NODETYPE', 'S', '业务机构节点类型', '0', '', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076699', 'DICT_OM_GENDER', 'A', '性别', '0', '', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076700', 'DICT_OM_EMPSTATUS', 'A', '人员状态', '0', '企业中员工的状态，平台给出默认实现', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076701', 'DICT_SD_PARTY', 'A', '政治面貌', '0', '', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076702', 'DICT_OM_EMPDEGREE', 'A', '人员职级', '0', '员工在企业中的职称级别', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076703', 'DICT_OM_GROUPTYPE', 'S', '工作组类型', '0', '平台提供示意数据，根据需求调整', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076704', 'DICT_OM_GROUPSTATUS', 'S', '工作组状态', '0', '平台提供模型管理，根据需求调整', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076705', 'DICT_OM_DUTYTYPE', 'S', '职务套别', '0', '职务条线或职务类型', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076706', 'DICT_OM_POSITYPE', 'S', '岗位类别', '0', '平台提供示意数据，根据需求调整', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076707', 'DICT_OM_POSISTATUS', 'S', '岗位状态', '0', '平台提供模型管理，根据需求调整', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076708', 'DICT_AC_ROLETYPE', 'S', '角色类别', '0', '对角色的类别表示标识', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076709', 'DICT_AC_APPTYPE', 'S', '应用类型', '0', '应用系统的类型标识', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076710', 'DICT_AC_FUNCTYPE', 'S', '功能类型', '0', '表示某个功能的类别，如：交易、页面流、接口服务', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076711', 'DICT_AC_BEHAVIORTYPE', 'S', '操作行为类型', '0', '某个功能的操作行为，具体值在功能设计时确定', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076712', 'DICT_AC_FUNCRESTYPE', 'S', '功能对应资源类型', '0', '如：工作流，JSP，表示功能触发后实际执行的资源种类', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076713', 'DICT_AC_OPENMODE', 'S', '界面打开方式', '0', '触发某个功能时，对应的界面打开方式', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076714', 'DICT_AC_AUTHTYP', 'S', '人员特殊权限授权标志', '0', 'E1：特别开通，2：特别禁止', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076715', 'DICT_AC_ENTITYTYPE', 'S', '数据实体类型', '0', '标识某个数据实体的类型', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076716', 'DICT_AC_DATAOPTYPE', 'S', '数据操作类型', '0', '可以对数据实体作出的操作行为分类', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076717', 'DICT_AC_AUTHMODE', 'S', '认证模式', '0', '操作员登陆系统时的认证模式', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076718', 'DICT_AC_OPERATOR_STATUS', 'S', '操作员状态', '0', '', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076719', 'DICT_AC_CONFIGTYPE', 'S', '配置类型', '0', '操作员个性化配置的类型划分', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076720', 'DICT_AC_RESOURCETYPE', 'S', '权限资源类型', '0', '系统对权限的分类类型', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076721', 'DICT_AC_MENUTYPE', 'S', '菜单风格', '0', '', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076722', 'DICT_SYS_RESET', 'S', '序号数重置方式', '0', '对序号资源重置的策略方式', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076723', 'DICT_OPERATOR_TYPE', 'S', '日志类型', '0', '操作日志中区分操作类型', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1501076724', 'DICT_OPERATOR_RESULT', 'S', '操作结果', '0', '操作日志中记录操作结果', null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1503908002', 'DICT_PROVINCE_CN', 'A', '中国省份', '0', null, 'DICT1503975030', null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1503975022', 'DICT_DAO_JP', 'A', '日本都道', '0', null, 'DICT1503975030', null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1503975026', 'DICT_COUNTY_NJ', 'A', '南京区县', '0', null, 'DICT1503994276', 'NJ', null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1503975030', 'DICT_COUNTY', 'A', '国家业务字典', '0', null, null, null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1503994276', 'DUCT_CITY_JS', 'A', '江苏省城市', '0', null, 'DICT1503908002', null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1504072036', 'DICT_COUNTY_GD', 'A', '广东省城市', '0', null, 'DICT1503908002', '', null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1504072037', 'DICT_COUNTY_ZJ', 'A', '浙江省城市', '0', null, 'DICT1503908002', null, null, null, null, null, '0');
INSERT INTO `sys_dict` VALUES ('DICT1504072038', 'DICT_COUNTY_HZ', 'A', '杭州城区', '0', null, 'DICT1504072037', null, null, null, null, null, '0');

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_DICT` varchar(128) NOT NULL COMMENT '隶属业务字典 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `ITEM_NAME` varchar(128) NOT NULL COMMENT '字典项名称',
  `ITEM_TYPE` varchar(128) NOT NULL,
  `ITEM_VALUE` varchar(128) NOT NULL COMMENT '字典项',
  `SEND_VALUE` varchar(128) NOT NULL COMMENT '实际值 : 实际值，及选中字典项后，实际发送值给系统的数值。',
  `SEQNO` decimal(12,0) DEFAULT NULL COMMENT '顺序号 : 顺序号，从0开始排，按小到大排序',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务字典项 : 业务字典内容项， 展示下拉菜单结构时，一般需要： 字典项，字典项名称，实际值';

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077408', 'DICT1501076673', '被动接触', 'value', 'passive', 'passive', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077409', 'DICT1501076674', '处理中，柜员开启了一笔交易，正在办理中', 'value', '1', '0', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077410', 'DICT1501076674', '取消，柜员强行关闭了未提交的交易；这种交易不能再次提交；', 'value', 'CANCEL', '1', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077411', 'DICT1501076674', '暂存，柜员通过暂存功能关闭了未提交的交易；这种交易可再次提交；', 'value', 'HOLD', '2', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077412', 'DICT1501076674', '处理成功', 'value', 'SUCC', '3', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077413', 'DICT1501076674', '处理失败，业务上有错，具体错误见错误码', 'value', 'FAIL', '4', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077414', 'DICT1501076675', '预填交易', 'value', 'PREFILL', 'PREFILL', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077415', 'DICT1501076675', '打开交易', 'value', 'DRIVE', 'DRIVE', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077416', 'DICT1501076675', '打开预填单交易', 'value', 'DRIVE_PREFILL', 'DRIVE_PREFILL', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077417', 'DICT1501076675', '取消交易', 'value', 'CANCEL', 'CANCEL', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077418', 'DICT1501076675', '暂存交易', 'value', 'HOLD', 'HOLD', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077420', 'DICT1501076676', 'HOLD操作，此时交易还未提交，只是暂存；', 'value', 'HOLD', 'HOLD', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077421', 'DICT1501076676', '提交交易，执行提交时，交易的上下文信息', 'value', 'COMMIT', 'COMMIT', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077422', 'DICT1501076676', '回显数据（修改前数据）', 'value', 'ECHO', 'ECHO', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077423', 'DICT1501076676', '最终数据（修改后数据）', 'value', 'FINAL', 'FINAL', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077428', 'DICT1501076678', '好评', 'value', 'positive', 'positive', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077429', 'DICT1501076678', '一般', 'value', 'normal', 'normal', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077430', 'DICT1501076678', '差评', 'value', 'negative', 'negative', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077431', 'DICT1501076679', '开始服务', 'value', 'start', 'start', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077432', 'DICT1501076679', '暂停服务', 'value', 'pause', 'pause', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077433', 'DICT1501076679', '结束服务', 'value', 'finish', 'finish', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077434', 'DICT1501076680', '个人业务', 'value', '01', '01', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077435', 'DICT1501076680', '公司业务', 'value', '02', '02', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077436', 'DICT1501076680', '理财业务', 'value', '03', '03', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077437', 'DICT1501076680', '外汇业务', 'value', '04', '04', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077438', 'DICT1501076681', '身份证', 'value', '01', '01', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077439', 'DICT1501076681', '户口薄', 'value', '02', '02', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077440', 'DICT1501076681', '军官证', 'value', '03', '03', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077441', 'DICT1501076681', '学生证', 'value', '04', '04', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077442', 'DICT1501076681', '护照', 'value', '05', '05', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077443', 'DICT1501076681', '其它', 'value', '06', '06', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077444', 'DICT1501076682', '请求已发出', 'value', 'SENT', '0', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077445', 'DICT1501076682', '成功', 'value', 'SUCC', '1', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077446', 'DICT1501076682', '业务错误', 'value', 'FAIL', '2', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077447', 'DICT1501076682', '超时', 'value', 'TIMEOUT', '3.0', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077448', 'DICT1501076683', '请求报文', 'value', 'REQ', '1', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077449', 'DICT1501076683', '响应报文', 'value', 'RSP', '2', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077450', 'DICT1501076684', '个人现金', 'value', 'Personal Cash Service', '01', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077451', 'DICT1501076684', '外币业务', 'value', 'Foreign Exchange Business', '02', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077452', 'DICT1501076684', '个人开户、签约及理财', 'value', 'Personal account', '03', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077453', 'DICT1501076684', '个人转账、挂饰、销户、修改密码', 'value', 'Fund transfer', '04', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077454', 'DICT1501076684', '对公现金', 'value', 'Corporate Cash Service', '11', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077455', 'DICT1501076684', '对公转账及汇款', 'value', 'Corporate transfer', '12', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077456', 'DICT1501076684', '对公开户、销户、签约、及其他', 'value', 'Corporate account', '13', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077457', 'DICT1501076684', '公司付款', 'value', 'Corporate Payment', '14', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077458', 'DICT1501076684', '公司收款', 'value', 'Corporate proceeds', '15', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077459', 'DICT1501076684', '70岁以上老年客户服务', 'value', 'Other', '90', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077460', 'DICT1501076685', '预填', 'value', 'PREFILL', 'PREFILL', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077461', 'DICT1501076685', '交易（已经根据预填信息发起了交易，但是交易的状态另见交易流水记录）', 'value', 'TRANS', 'TRANS', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077462', 'DICT1501076685', '取消（柜员对预填记录执行了取消操作）', 'value', 'CANCEL', 'CANCEL', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077463', 'DICT1501076686', '理财产品', 'value', 'FINANCIAL', '01', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077464', 'DICT1501076686', '基金产品', 'value', 'FOUNDATION', '02', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077465', 'DICT1501076687', '未呈现（等待营销呈现）', 'value', 'INIT', 'INIT', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077466', 'DICT1501076687', '已经呈现（客户服务时向客户展示了营销信息）', 'value', 'SHOW', 'SHOW', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077467', 'DICT1501076687', '有兴趣', 'value', 'INTEREST', 'INTEREST', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077468', 'DICT1501076687', '无兴趣', 'value', 'DISINTEREST', 'DISINTEREST', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077469', 'DICT1501076687', '转介', 'value', 'TRUN2TRANS', 'TRUN2TRANS', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077470', 'DICT1501076688', '客户分析系统', 'value', 'AUTO_ANY', 'AUTO_ANY', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077471', 'DICT1501076688', '客户关系分析系统', 'value', 'CRM_ANY', 'CRM_ANY', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077472', 'DICT1501076688', '理财系统', 'value', 'FINANCE', 'FINANCE', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077473', 'DICT1501076689', '是', 'value', 'YES', 'Y', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077474', 'DICT1501076689', '否', 'value', 'NO', 'N', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077475', 'DICT1501076690', '系统级', 'value', 'S', 'S', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077476', 'DICT1501076690', '应用级', 'value', 'A', 'A', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077477', 'DICT1501076691', '机构', 'value', 'organization', 'organization', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077478', 'DICT1501076691', '工作组', 'value', 'workgroup', 'workgroup', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077479', 'DICT1501076691', '岗位', 'value', 'position', 'position', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077480', 'DICT1501076691', '职务', 'value', 'duty', 'duty', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077481', 'DICT1501076692', '正常', 'value', 'running', 'running', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077482', 'DICT1501076692', '注销', 'value', 'cancel', 'cancel', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077483', 'DICT1501076692', '停用', 'value', 'stop', 'stop', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077484', 'DICT1501076693', '北京市', 'value', '1000', '1000', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077485', 'DICT1501076693', '上海市', 'value', '2900', '2900', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077486', 'DICT1501076694', '总行', 'value', '0', 'BS', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077487', 'DICT1501076694', '分行', 'value', '1', 'YF', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077488', 'DICT1501076694', '海外', 'value', '2', 'HW', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077489', 'DICT1501076694', '区域分行', 'value', '3', 'QY', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077490', 'DICT1501076694', '网点', 'value', '4', 'CN', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077491', 'DICT1501076695', '总公司', 'value', '10', '10', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077492', 'DICT1501076695', '总部部门', 'value', '11', '11', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077493', 'DICT1501076695', '分公司', 'value', '20', '20', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077494', 'DICT1501076695', '分公司部门', 'value', '21', '21', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077495', 'DICT1501076695', '营业网点', 'value', '90', '90', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077496', 'DICT1501076696', 'null', 'value', '100036.0', '100036.0', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077497', 'DICT1501076696', 'null', 'value', '100040.0', '100040.0', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077498', 'DICT1501076696', 'null', 'value', '100041.0', '100041.0', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077499', 'DICT1501076696', 'null', 'value', '100043.0', '100043.0', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077500', 'DICT1501076696', 'null', 'value', '100049.0', '100049.0', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077501', 'DICT1501076697', '华东区', 'value', '001', '001', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077502', 'DICT1501076697', '华南区', 'value', '002', '002', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077503', 'DICT1501076697', '华北区', 'value', '003', '003', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077504', 'DICT1501076697', '华中区', 'value', '004', '004', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077505', 'DICT1501076697', '华西区', 'value', '005', '005', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077506', 'DICT1501076698', '虚拟节点', 'value', 'dummy', 'dummy', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077507', 'DICT1501076698', '实体节点', 'value', 'reality', 'reality', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077508', 'DICT1501076699', '男', 'value', 'M', 'M', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077509', 'DICT1501076699', '女', 'value', 'F', 'F', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077510', 'DICT1501076699', '未知', 'value', 'U', 'U', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077511', 'DICT1501076700', '在招', 'value', 'offer', 'offer', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077512', 'DICT1501076700', '在职', 'value', 'onjob', 'onjob', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077513', 'DICT1501076700', '离职', 'value', 'offjob', 'offjob', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077514', 'DICT1501076701', '群众', 'value', '01', '01', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077515', 'DICT1501076701', '团员', 'value', '02', '02', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077516', 'DICT1501076701', '党员', 'value', '03', '03', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077517', 'DICT1501076702', '实习', 'value', 'P1', 'P1', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077518', 'DICT1501076702', '初级', 'value', 'P2', 'P2', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077519', 'DICT1501076702', '中级', 'value', 'P3', 'P3', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077520', 'DICT1501076702', '中高级', 'value', 'P4', 'P4', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077521', 'DICT1501076702', '高级', 'value', 'P5', 'P5', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077522', 'DICT1501076702', '特高级', 'value', 'P6', 'P6', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077523', 'DICT1501076702', '资深', 'value', 'P7', 'P7', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077524', 'DICT1501076702', '专家', 'value', 'P8', 'P8', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077525', 'DICT1501076702', '科学家', 'value', 'P9', 'P9', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077526', 'DICT1501076702', '初级管理', 'value', 'M1', 'M1', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077527', 'DICT1501076702', '初中级管理', 'value', 'M2', 'M2', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077528', 'DICT1501076702', '中级管理', 'value', 'M3', 'M3', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077529', 'DICT1501076702', '中高级管理', 'value', 'M4', 'M4', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077530', 'DICT1501076702', '高级管理', 'value', 'M5', 'M5', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077531', 'DICT1501076702', '总管', 'value', 'M6', 'M6', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077532', 'DICT1501076703', '普通工作组', 'value', 'normal', 'normal', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077533', 'DICT1501076703', '项目型', 'value', 'project', 'project', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077534', 'DICT1501076703', '事务型', 'value', 'affair', 'affair', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077535', 'DICT1501076704', '正常', 'value', 'running', 'running', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077536', 'DICT1501076704', '注销', 'value', 'cancel', 'cancel', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077542', 'DICT1501076706', '机构岗位', 'value', '01', '01', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077543', 'DICT1501076706', '工作组岗位', 'value', '02', '02', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077544', 'DICT1501076707', '正常', 'value', 'running', 'running', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077545', 'DICT1501076707', '注销', 'value', 'cancel', 'cancel', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077546', 'DICT1501076708', '系统级', 'value', 'sys', 'sys', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077547', 'DICT1501076708', '应用级', 'value', 'app', 'app', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077548', 'DICT1501076709', '本地', 'value', 'local', 'local', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077549', 'DICT1501076709', '远程', 'value', 'remote', 'remote', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077550', 'DICT1501076710', '页面流', 'value', 'pageprocess', '0', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077551', 'DICT1501076710', '交易流', 'value', 'tradeprocess', '1', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077552', 'DICT1501076710', 'RESTFul服务', 'value', 'restful', '2', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077553', 'DICT1501076710', '柜面交易', 'value', 'twstx', '3.0', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077554', 'DICT1501076711', 'BHVTYPE_NAME', 'value', 'AC_BHVTYPE_DEF', 'BHVTYPE_CODE', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077555', 'DICT1501076712', 'JSP页面', 'value', 'jsp', 'jsp', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077556', 'DICT1501076712', '页面流', 'value', 'pageflow', 'pageflow', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077557', 'DICT1501076712', '工作流', 'value', 'workflow', 'workflow', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077558', 'DICT1501076713', '主操作区', 'value', 'mainarea', 'mainarea', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077559', 'DICT1501076713', '弹出框方式', 'value', 'popup', 'popup', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077560', 'DICT1501076714', '特别开通', 'value', 'permit', '1', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077561', 'DICT1501076714', '特别禁止', 'value', 'forbid', '0', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077562', 'DICT1501076715', '表', 'value', 'table', '0', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077563', 'DICT1501076715', '试图', 'value', 'view', '1', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077564', 'DICT1501076715', '查询实体', 'value', 'query', '2', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077565', 'DICT1501076715', '内存对象', 'value', 'cache', '3', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077566', 'DICT1501076716', '新建', 'value', 'CREATE', 'C', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077567', 'DICT1501076716', '删除', 'value', 'REMOVE', 'R', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077568', 'DICT1501076716', '修改', 'value', 'UPDATE', 'U', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077569', 'DICT1501076716', '查询', 'value', 'QUERY', 'Q', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077570', 'DICT1501076717', '密码', 'value', 'password', 'password', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077571', 'DICT1501076717', '动态密码', 'value', 'dynpassword', 'dynpassword', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077572', 'DICT1501076717', '验证码', 'value', 'captcha', 'captcha', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077573', 'DICT1501076717', 'LDAP认证', 'value', 'ldap', 'ldap', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077574', 'DICT1501076717', '指纹', 'value', 'fingerprint', 'fingerprint', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077575', 'DICT1501076717', '指纹卡', 'value', 'fingerprintcard', 'fingerprintcard', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077576', 'DICT1501076718', '正常', 'value', 'login', 'login', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077577', 'DICT1501076718', '挂起', 'value', 'pause', 'pause', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077578', 'DICT1501076718', '注销', 'value', 'clear', 'clear', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077579', 'DICT1501076718', '锁定', 'value', 'lock', 'lock', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077580', 'DICT1501076718', '退出', 'value', 'logout', 'logout', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077581', 'DICT1501076718', '停用', 'value', 'stop', 'stop', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077582', 'DICT1501076719', '页面布局配置', 'value', 'style', 'style', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077583', 'DICT1501076719', '身份配置', 'value', 'identity', 'identity', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077584', 'DICT1501076719', '重组菜单配置', 'value', 'menureorg', 'menureorg', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077585', 'DICT1501076720', '功能', 'value', 'function', 'function', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077586', 'DICT1501076720', '角色', 'value', 'role', 'role', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077587', 'DICT1501076720', '岗位', 'value', 'position', 'position', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077588', 'DICT1501076720', '职务', 'value', 'duty', 'duty', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077589', 'DICT1501076720', '工作组', 'value', 'workgroup', 'workgroup', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077590', 'DICT1501076720', '机构', 'value', 'organization', 'organization', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077591', 'DICT1501076721', '默认风格', 'value', 'default', 'default', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077592', 'DICT1501076721', '多TAB风格', 'value', 'tabs', 'tabs', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077593', 'DICT1501076721', 'OUTLOOK风格', 'value', 'outlook', 'outlook', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077594', 'DICT1501076722', '不重置', 'value', 'EVER', 'E', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077595', 'DICT1501076722', '按天重置', 'value', 'DAY', 'D', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077596', 'DICT1501076722', '按周重置', 'value', 'WEEK', 'W', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077597', 'DICT1501076722', '自定义重置周期', 'value', 'CUSTOM', 'C', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077598', 'DICT1501076723', '查询', 'value', 'query', 'query', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077599', 'DICT1501076723', '新增数据', 'value', 'add', 'add', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077600', 'DICT1501076723', '修改数据', 'value', 'update', 'update', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077601', 'DICT1501076723', '删除数据', 'value', 'delete', 'delete', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077602', 'DICT1501076724', '成功', 'value', 'succ', 'succ', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077603', 'DICT1501076724', '失败', 'value', 'fail', 'fail', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1501077604', 'DICT1501076724', '系统处理异常', 'value', 'exception', 'exception', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503275926', 'DICT1503275793', '海口市', 'value', '6410', '6410', '1');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503275927', 'DICT1503275793', '琼中县', 'value', '6410', '6410', '2');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503275928', 'DICT1503275793', '保亭县', 'value', '6410', '6410', '3');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503275929', 'DICT1503275793', '陵水县', 'value', '6410', '6410', '4');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503275930', 'DICT1503275793', '乐东县', 'value', '6410', '6410', '5');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503275931', 'DICT1501076705', '银行网点', 'value', '06', '06', '3');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503275932', 'DICT1501076705', '科技部', 'value', '07', '07', '2');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503494565', 'DICT1501076705', '总行外汇部', 'value', '01', '01', '1');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503908034', 'DICT1503907997', '中国', 'value', 'DICT_SF', 'CN', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503908035', 'DICT1503907998', '江苏省', 'value', 'DICT_SF_JS', 'JS', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503908036', 'DICT1503907999', '无锡', 'value', 'DICT_AREA-WX', 'wx', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503908037', 'DICT1503907997', '日本', 'value', 'DICT_JP_DAO', 'dao', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503908038', 'DICT1503907998', '浙江省', 'value', 'DICT_SF_ZJ', 'ZJ', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974926', 'DICT1503908002', '江苏省', 'dict', 'DUCT_CITY_JS', 'JS', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974941', 'DICTITEM1503974926', '南京', 'dict', 'DUCT_CITY_JS', 'NJ', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974942', 'DICTITEM1503974926', '盐城', 'dict', 'DUCT_CITY_JS', 'YC', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974943', 'DICT1503994276', '苏州', 'value', '苏州', 'SZ', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974945', 'DICTITEM1503974944', '泰州', 'dict', 'DUCT_CITY_JS', 'TZ', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974947', 'DICT1503975030', '中国', 'dict', 'DICT_PROVINCE_CN', 'CN', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974948', 'DICT1503975030', '日本', 'dict', 'DICT_DAO_JP', 'JANPAN', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974950', 'DICTITEM1503974926', '无锡', 'value', 'wx', 'wx', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974951', 'DICTITEM1503974926', '无锡', 'dict', 'DUCT_CITY_JS', 'wx', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974952', 'DICT1503994276', '无锡', 'value', 'wx', 'wx', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974953', 'DICTITEM1503974926', '泰州', 'value', 'TZ', 'TZ', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974954', 'DICTITEM1503974926', '盐城', 'value', 'YC', 'YC', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974955', 'DICTITEM1503974926', '盐城', 'value', 'YC', 'YC', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974956', 'DICTITEM1503974926', '盐城', 'value', 'EC', 'EC', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974957', 'DICTITEM1503974926', '盐城', 'value', 'YC', 'YC', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974958', 'DICTITEM1503974926', '盐城', 'value', 'YC', 'YC', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974959', 'DICTITEM1503974926', '盐城市', 'value', 'YC', 'YC', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974960', 'DICTITEM1503974926', '太仓', 'value', '1', '1', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974961', 'DICTITEM1503974926', '泰州', 'dict', 'DICT_TRANS_STATUS', 'TZ', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974963', 'DICT1503994276', '南京', 'dict', 'DICT_COUNTY_NJ', 'NJ', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974964', 'DICT1503975026', '江宁区', 'value', 'JN', 'JN', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1503974965', 'DICT1503975026', '鼓楼区', 'value', 'GL', 'GL', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1504072010', 'DICT1503994276', '盐城', 'dict', 'DICT_COUNTY_YC', 'YC', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1504072017', 'DICT1503994276', '徐州', 'dict', 'DICT_COUNTY_XZ', 'XZ', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1504072018', 'DICT1503994276', '常州', 'dict', 'DICT_COUNTY_CZ', 'CZ', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1504072038', 'DICT1503908002', '广东省', 'dict', 'DICT_COUNTY_GD', 'GD', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1504072039', 'DICT1503908002', '浙江省', 'dict', 'DICT_COUNTY_ZJ', 'ZJ', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1504072040', 'DICT1504072037', '杭州', 'dict', 'DICT_COUNTY_HZ', 'HZ', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1505038037', 'DICT1505030053', 'ITEM_V_1', 'value', 'ITEM_V_1', '1', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1505038038', 'DICT1505030053', 'ITEM_V_2', 'value', '2', '2', '0');
INSERT INTO `sys_dict_item` VALUES ('DICTITEM1505038039', 'DICT1505030053', 'ITEM_V_3', 'dict', 'DICT_BIZ_TYPE', '3', '0');
