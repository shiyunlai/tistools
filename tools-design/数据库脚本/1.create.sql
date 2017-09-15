SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for AC_APP
-- ----------------------------
DROP TABLE IF EXISTS `AC_APP`;
CREATE TABLE `AC_APP` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `APP_CODE` varchar(64) NOT NULL COMMENT '应用代码',
  `APP_NAME` varchar(128) NOT NULL COMMENT '应用名称',
  `APP_TYPE` varchar(255) NOT NULL COMMENT '应用类型 : 取值来自业务菜单： DICT_AC_APPTYPE\r\n如：本地，远程',
  `ISOPEN` char(1) NOT NULL DEFAULT 'N' COMMENT '是否开通 : 取值来自业务菜单： DICT_YON\r\n默认为N，新建后，必须执行应用开通操作，才被开通。',
  `OPEN_DATE` timestamp NULL DEFAULT NULL COMMENT '开通时间 : 记录到时分秒',
  `URL` varchar(256) DEFAULT NULL COMMENT '访问地址',
  `APP_DESC` varchar(512) DEFAULT NULL COMMENT '应用描述',
  `GUID_EMP_MAINTENANCE` varchar(128) DEFAULT NULL COMMENT '管理维护人员',
  `GUID_ROLE_MAINTENANCE` varchar(128) DEFAULT NULL COMMENT '应用管理角色',
  `REMARK` varchar(512) DEFAULT NULL COMMENT '备注',
  `INIWP` char(1) DEFAULT NULL COMMENT '是否接入集中工作平台 : 取值来自业务菜单： DICT_YON',
  `INTASKCENTER` char(1) DEFAULT NULL COMMENT '是否接入集中任务中心 : 取值来自业务菜单： DICT_YON',
  `IP_ADDR` varchar(50) DEFAULT NULL COMMENT 'IP',
  `IP_PORT` varchar(10) DEFAULT NULL COMMENT '端口',
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `APP_CODE` (`APP_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='应用系统 : 应用系统（Application）注册表';

-- ----------------------------
-- Table structure for AC_BHV_DEF
-- ----------------------------
DROP TABLE IF EXISTS `AC_BHV_DEF`;
CREATE TABLE `AC_BHV_DEF` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_BEHTYPE` varchar(128) NOT NULL COMMENT '操作类型GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `BHV_CODE` varchar(64) DEFAULT NULL COMMENT '行为代码',
  `BHV_NAME` varchar(512) DEFAULT NULL COMMENT '行为名称',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能操作行为定义 : 每类行为中至少有一个行为定义';

-- ----------------------------
-- Table structure for AC_BHVTYPE_DEF
-- ----------------------------
DROP TABLE IF EXISTS `AC_BHVTYPE_DEF`;
CREATE TABLE `AC_BHVTYPE_DEF` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `BHVTYPE_CODE` varchar(64) DEFAULT NULL COMMENT '行为类型代码 : 应用中定义，定义了某种功能特有的操作行为',
  `BHVTYPE_NAME` varchar(128) DEFAULT NULL COMMENT '行为类型名称',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行为类型定义 : 功能行为的分类定义，以便很好的归类和配置功能的行为（AC_FUNC_BHV）';

-- ----------------------------
-- Table structure for AC_DATASCOPE
-- ----------------------------
DROP TABLE IF EXISTS `AC_DATASCOPE`;
CREATE TABLE `AC_DATASCOPE` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_ENTITY` varchar(128) NOT NULL COMMENT '实体GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `PRIV_NAME` varchar(64) NOT NULL COMMENT '数据范围权限名称',
  `DATA_OP_TYPE` varchar(20) DEFAULT NULL COMMENT '数据操作类型 : 取值来自业务菜单：DICT_AC_DATAOPTYPE\r\n对本数据范围内的数据，可以做哪些操作：增加、修改、删除、查询\r\n如果为空，表示都不限制；\r\n多个操作用逗号分隔，如： 增加,修改,删除',
  `ENTITY_NAME` varchar(64) DEFAULT NULL COMMENT '实体名称',
  `FILTER_SQL_STRING` varchar(1024) DEFAULT NULL COMMENT '过滤SQL : 例： (orgSEQ IS NULL or orgSEQ like ''$[SessionEntity/orgSEQ]%'') \r\n通过本SQL，限定了数据范围',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据范围权限 : 定义能够操作某个表数据的范围';

-- ----------------------------
-- Table structure for AC_ENTITY
-- ----------------------------
DROP TABLE IF EXISTS `AC_ENTITY`;
CREATE TABLE `AC_ENTITY` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_APP` varchar(128) NOT NULL COMMENT '隶属应用GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `ENTITY_NAME` varchar(50) NOT NULL COMMENT '实体名称',
  `TABLE_NAME` varchar(64) DEFAULT NULL COMMENT '数据库表名',
  `ENTITY_DESC` varchar(512) DEFAULT NULL COMMENT '实体描述',
  `DISPLAY_ORDER` decimal(4,0) NOT NULL DEFAULT '0' COMMENT '顺序',
  `ENTITY_TYPE` varchar(255) NOT NULL DEFAULT '0' COMMENT '实体类型 : 取值来自业务字典：DICT_AC_ENTITYTYPE\r\n0-表\r\n1-视图\r\n2-查询实体\r\n3-内存对象（系统运行时才存在）',
  `ISADD` char(1) NOT NULL DEFAULT 'N' COMMENT '是否可增加 : 取值来自业务菜单： DICT_YON',
  `ISDEL` char(1) NOT NULL DEFAULT 'N' COMMENT '是否可删除 : 取值来自业务菜单： DICT_YON',
  `ISMODIFY` char(1) NOT NULL DEFAULT 'N' COMMENT '可修改 : 取值来自业务菜单： DICT_YON',
  `ISVIEW` char(1) NOT NULL DEFAULT 'Y' COMMENT '可查看 : 取值来自业务菜单： DICT_YON',
  `ISPAGE` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否需要分页显示 : 取值来自业务菜单： DICT_YON',
  `PAGE_LEN` decimal(5,0) DEFAULT '10' COMMENT '每页记录数',
  `CHECK_REF` varchar(1024) DEFAULT NULL COMMENT '删除记录检查引用关系 : 根据引用关系定义，检查关联记录是否需要同步删除；\r\n引用关系定义格式： table.column/[Y/N];table.column/[Y/N];...\r\n举例：\r\n假如，存在实体acct，且引用关系定义如下\r\n\r\nguid:tws_abc.acct_guid/Y;tws_nnn.acctid/N;\r\n\r\n当前删除acct实体guid＝9988的记录，系统自动执行引用关系删除，逻辑如下：\r\n查找tws_abc 表，acct_guid = 9988 的记录，并删除；\r\n查找tws_nnn 表，ac',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实体 : 数据实体定义表';

-- ----------------------------
-- Table structure for AC_ENTITYFIELD
-- ----------------------------
DROP TABLE IF EXISTS `AC_ENTITYFIELD`;
CREATE TABLE `AC_ENTITYFIELD` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_ENTITY` varchar(128) NOT NULL COMMENT '隶属实体GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `FIELD_NAME` varchar(50) NOT NULL COMMENT '属性名称',
  `FIELD_DESC` varchar(100) DEFAULT NULL COMMENT '属性描述',
  `DISPLAY_FORMAT` varchar(128) DEFAULT NULL COMMENT '显示格式 : 如：属性为日期时，可以设置显示格式 yyyy/MM/dd；\r\n当查询出数据，返回给调用着之前生效本显示格式（返回的数据已经被格式化）；',
  `DOCLIST_CODE` varchar(20) DEFAULT NULL COMMENT '代码大类',
  `CHECKBOX_VALUE` varchar(128) DEFAULT NULL COMMENT 'CHECKBOX_VALUE',
  `FK_INPUTURL` varchar(64) DEFAULT NULL COMMENT '外键录入URL',
  `FK_FIELDDESC` varchar(64) DEFAULT NULL COMMENT '外键描述字段名',
  `FK_COLUMNNAME` varchar(64) DEFAULT NULL COMMENT '外键列名',
  `FK_TABLENAME` varchar(64) DEFAULT NULL COMMENT '外键表名',
  `DESC_FIELDNAME` varchar(64) DEFAULT NULL COMMENT '描述字段名',
  `REF_TYPE` varchar(2) DEFAULT NULL COMMENT '引用类型 : 0 业务字典\r\n1 其他表',
  `FIELD_TYPE` varchar(255) NOT NULL COMMENT '字段类型 : 0 字符串\r\n1 整数\r\n2 小数\r\n3 日期\r\n4 日期时间\r\n5 CHECKBOX\r\n6 引用',
  `DISPLAY_ORDER` decimal(4,0) NOT NULL DEFAULT '0' COMMENT '顺序',
  `COLUMN_NAME` varchar(64) NOT NULL COMMENT '数据库列名',
  `WIDTH` decimal(4,0) DEFAULT NULL COMMENT '宽度',
  `DEFAULT_VALUE` varchar(128) DEFAULT NULL COMMENT '缺省值',
  `MIN_VALUE` varchar(20) DEFAULT NULL COMMENT '最小值',
  `MAX_VALUE` varchar(20) DEFAULT NULL COMMENT '最大值',
  `LENGTH_VALUE` decimal(4,0) DEFAULT NULL COMMENT '长度',
  `PRECISION_VALUE` decimal(4,0) DEFAULT NULL COMMENT '小数位',
  `VALIDATE_TYPE` varchar(64) DEFAULT NULL COMMENT '页面校验类型',
  `ISMODIFY` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否可修改 : 取值来自业务菜单： DICT_YON',
  `ISDISPLAY` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否显示 : 取值来自业务菜单： DICT_YON',
  `ISINPUT` char(1) NOT NULL DEFAULT 'N' COMMENT '是否必须填写 : 取值来自业务菜单： DICT_YON',
  `ISPK` char(1) NOT NULL DEFAULT 'N' COMMENT '是否是主键 : 取值来自业务菜单： DICT_YON',
  `ISAUTOKEY` char(1) DEFAULT 'N' COMMENT '是否自动产生主键 : 取值来自业务菜单： DICT_YON',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实体属性 : 数据实体的字段（属性）定义表';

-- ----------------------------
-- Table structure for AC_FUNC
-- ----------------------------
DROP TABLE IF EXISTS `AC_FUNC`;
CREATE TABLE `AC_FUNC` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_FUNCGROUP` varchar(128) NOT NULL COMMENT '隶属功能组GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `FUNC_CODE` varchar(64) NOT NULL COMMENT '功能编号 : 业务上对功能的编码',
  `FUNC_NAME` varchar(128) NOT NULL COMMENT '功能名称',
  `FUNC_DESC` varchar(512) DEFAULT NULL COMMENT '功能描述',
  `FUNC_ACTION` varchar(256) DEFAULT NULL COMMENT '功能调用入口',
  `PARA_INFO` varchar(256) DEFAULT NULL COMMENT '输入参数 : 需要定义参数规范',
  `FUNC_TYPE` varchar(255) DEFAULT '''1''' COMMENT '功能类型 : 取值来自业务菜单：DICT_AC_FUNCTYPE\r\n如：页面流、交易流、渠道服务、柜面交易...',
  `ISCHECK` char(1) DEFAULT NULL COMMENT '是否验证权限 : 取值来自业务菜单： DICT_YON',
  `ISMENU` char(1) DEFAULT NULL COMMENT '可否定义为菜单 : 取值来自业务菜单：DICT_YON。\r\n该功能是否可以作为菜单入口，如果作为菜单入口，则会展示在菜单树（有些接口服务功能无需挂在菜单上）\r\n',
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `GUID` (`GUID`),
  UNIQUE KEY `FUNC_CODE` (`FUNC_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能 : 功能定义表，每个功能属于一个功能点，隶属于某个应用系统，同时也隶属于某个功能组。\r\n应用系统中的某个功能，如：柜';

-- ----------------------------
-- Table structure for AC_FUNC_BHV
-- ----------------------------
DROP TABLE IF EXISTS `AC_FUNC_BHV`;
CREATE TABLE `AC_FUNC_BHV` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_FUNC` varchar(128) NOT NULL COMMENT '功能GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_BHV` varchar(128) NOT NULL COMMENT '行为GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `ISEFFECTIVE` char(1) NOT NULL COMMENT '是否有效 : 见业务字典： DICT_YON\r\nY 有效（默认都是有效的操作行为）\r\nN 无效',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能操作行为 : Behavior（BHV）操作行为，权限控制模块中最细粒度的授权、控制单位；一个功能中包括多个操作行为';

-- ----------------------------
-- Table structure for AC_FUNC_BHVTYPE
-- ----------------------------
DROP TABLE IF EXISTS `AC_FUNC_BHVTYPE`;
CREATE TABLE `AC_FUNC_BHVTYPE` (
  `GUID_FUNC` varchar(128) NOT NULL COMMENT '功能GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_BHVTYPE` varchar(128) NOT NULL COMMENT '行为类型GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能行为类型表 : 功能有那些行为类型，通过本映射关系，也指明了功能可能具有的行为；\r\n每个功能可以有多个行为类型，至少一';

-- ----------------------------
-- Table structure for AC_FUNC_RESOURCE
-- ----------------------------
DROP TABLE IF EXISTS `AC_FUNC_RESOURCE`;
CREATE TABLE `AC_FUNC_RESOURCE` (
  `GUID_FUNC` varchar(128) NOT NULL COMMENT '对应功能GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `RES_TYPE` varchar(255) DEFAULT NULL COMMENT '资源类型 : 见业务字典： DICT_AC_FUNCRESTYPE\r\n如：JSP、页面流、逻辑流等',
  `RES_PATH` varchar(256) DEFAULT NULL COMMENT '资源路径',
  `COMPACK_NAME` varchar(40) DEFAULT NULL COMMENT '构件包名',
  `RES_SHOW_NAME` varchar(128) DEFAULT NULL COMMENT '资源显示名称'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能资源对应 : 功能点包含的系统资源内容，如jsp、页面流、逻辑流等资源。\r\n功能点对应实际的代码资源。';

-- ----------------------------
-- Table structure for AC_FUNCGROUP
-- ----------------------------
DROP TABLE IF EXISTS `AC_FUNCGROUP`;
CREATE TABLE `AC_FUNCGROUP` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_APP` varchar(128) NOT NULL COMMENT '隶属应用GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `FUNCGROUP_NAME` varchar(64) DEFAULT NULL COMMENT '功能组名称',
  `GUID_PARENTS` varchar(128) DEFAULT NULL COMMENT '父功能组GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GROUP_LEVEL` decimal(4,0) DEFAULT NULL COMMENT '节点层次',
  `FUNCGROUP_SEQ` varchar(256) DEFAULT NULL COMMENT '功能组路径序列',
  `ISLEAF` char(1) DEFAULT NULL COMMENT '是否叶子节点 : 取值来自业务菜单： DICT_YON',
  `SUB_COUNT` decimal(10,0) DEFAULT NULL COMMENT '子节点数 : 对功能组进行子节点的增加、删除时需要同步维护',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='功能组 : 功能组可以理解为功能模块或者构件包，是指一类相关功能的集合。定义功能组主要是为了对系统的功能进行归类管理\r\n';

-- ----------------------------
-- Table structure for AC_MENU
-- ----------------------------
DROP TABLE IF EXISTS `AC_MENU`;
CREATE TABLE `AC_MENU` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_APP` varchar(128) NOT NULL COMMENT '应用GUID',
  `GUID_FUNC` varchar(128) DEFAULT NULL COMMENT '功能GUID',
  `MENU_NAME` varchar(40) NOT NULL COMMENT '菜单名称 : 菜单树上显示的名称，一般同功能名称',
  `MENU_LABEL` varchar(40) NOT NULL COMMENT '菜单显示（中文）',
  `MENU_CODE` varchar(64) NOT NULL COMMENT '菜单代码 : 业务上对本菜单记录的编码',
  `ISLEAF` char(1) NOT NULL COMMENT '是否叶子菜单 : 数值取自业务菜单：DICT_YON',
  `UI_ENTRY` varchar(256) DEFAULT NULL COMMENT 'UI入口 : 针对EXT模式提供，例如abf_auth/function/module.xml',
  `MENU_LEVEL` decimal(4,0) DEFAULT NULL COMMENT '菜单层次 : 原类型smalint',
  `GUID_PARENTS` varchar(128) DEFAULT NULL COMMENT '父菜单GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_ROOT` varchar(40) DEFAULT NULL COMMENT '根菜单GUID : 本菜单所在菜单树的根节点菜单GUID',
  `DISPLAY_ORDER` decimal(4,0) DEFAULT NULL COMMENT '显示顺序 : 原类型smalint',
  `IMAGE_PATH` varchar(256) DEFAULT NULL COMMENT '菜单闭合图片路径',
  `EXPAND_PATH` varchar(256) DEFAULT NULL COMMENT '菜单展开图片路径',
  `MENU_SEQ` varchar(256) DEFAULT NULL COMMENT '菜单路径序列 : 类似面包屑导航，可以看出菜单的全路径；\r\n从应用系统开始，系统自动维护，如： /teller/loan/TX010112\r\n表示柜面系统（teller）中贷款功能组（loan）中的TX010112功能（交易）',
  `OPEN_MODE` varchar(255) DEFAULT NULL COMMENT '页面打开方式 : 数值取自业务菜单： DICT_AC_OPENMODE\r\n如：主窗口打开、弹出窗口打开...',
  `SUB_COUNT` decimal(10,0) DEFAULT NULL COMMENT '子节点数 : 菜单维护时同步更新',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单 : 应用菜单表，从逻辑上为某个应用系统中的功能组织为一个有分类，有层级的树结构。\r\nUI可根据菜单数据结构，进行界面';

-- ----------------------------
-- Table structure for AC_OPERATOR
-- ----------------------------
DROP TABLE IF EXISTS `AC_OPERATOR`;
CREATE TABLE `AC_OPERATOR` (
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
-- Table structure for AC_OPERATOR_BHV
-- ----------------------------
DROP TABLE IF EXISTS `AC_OPERATOR_BHV`;
CREATE TABLE `AC_OPERATOR_BHV` (
  `GUID_OPERATOR` varchar(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_FUNC_BHV` varchar(128) NOT NULL COMMENT '操作行为GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `AUTH_TYPE` varchar(255) NOT NULL COMMENT '授权标志 : 取值来自业务菜单：DICT_AC_AUTHTYPE\r\n如：特别禁止、特别允许'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员特殊功能行为配置 : 配合人员特殊授权配置表一起使用，可设置操作员只有功能的某些行为权限；\r\n特别授权某个功能给操作';

-- ----------------------------
-- Table structure for AC_OPERATOR_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `AC_OPERATOR_CONFIG`;
CREATE TABLE `AC_OPERATOR_CONFIG` (
  `GUID_OPERATOR` varchar(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_APP` varchar(128) NOT NULL COMMENT '应用GUID',
  `CONFIG_TYPE` varchar(64) NOT NULL COMMENT '配置类型 : 见业务字典： DICT_AC_CONFIGTYPE',
  `CONFIG_NAME` varchar(64) NOT NULL COMMENT '配置名',
  `CONFIG_VALUE` varchar(1024) DEFAULT NULL COMMENT '配置值',
  `ISVALID` char(1) NOT NULL COMMENT '是否启用 : 见业务菜单： DICT_YON',
  UNIQUE KEY `GUID_APP` (`GUID_APP`,`CONFIG_TYPE`,`CONFIG_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员个性配置 : 操作员个性化配置\r\n如颜色配置\r\n    登录风格\r\n    是否使用重组菜单\r\n    默认身份\r\n';

-- ----------------------------
-- Table structure for AC_OPERATOR_FUNC
-- ----------------------------
DROP TABLE IF EXISTS `AC_OPERATOR_FUNC`;
CREATE TABLE `AC_OPERATOR_FUNC` (
  `GUID_OPERATOR` varchar(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_FUNC` varchar(128) NOT NULL COMMENT '功能GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `AUTH_TYPE` varchar(255) NOT NULL COMMENT '授权标志 : 取值来自业务菜单：DICT_AC_AUTHTYPE\r\n如：特别禁止、特别允许',
  `START_DATE` date DEFAULT NULL COMMENT '有效开始日期',
  `END_DATE` date DEFAULT NULL COMMENT '有效截至日期',
  `GUID_APP` varchar(128) DEFAULT NULL COMMENT '应用GUID : 冗余字段',
  `GUID_FUNCGROUP` varchar(128) DEFAULT NULL COMMENT '功能组GUID : 冗余字段'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员特殊权限配置 : 针对人员配置的特殊权限，如特别开通的功能，或者特别禁止的功能';

-- ----------------------------
-- Table structure for AC_OPERATOR_IDENTITY
-- ----------------------------
DROP TABLE IF EXISTS `AC_OPERATOR_IDENTITY`;
CREATE TABLE `AC_OPERATOR_IDENTITY` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_OPERATOR` varchar(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `IDENTITY_NAME` varchar(255) NOT NULL COMMENT '身份名称',
  `IDENTITY_FLAG` char(1) NOT NULL COMMENT '默认身份标志 : 见业务字典： DICT_YON\r\n只能有一个默认身份 Y是默认身份 N不是默认身份',
  `SEQ_NO` decimal(4,0) DEFAULT NULL COMMENT '显示顺序',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员身份 : 操作员对自己的权限进行组合形成一个固定的登录身份；\r\n供登录时选项，每一个登录身份是员工操作员的权限子集';

-- ----------------------------
-- Table structure for AC_OPERATOR_IDENTITYRES
-- ----------------------------
DROP TABLE IF EXISTS `AC_OPERATOR_IDENTITYRES`;
CREATE TABLE `AC_OPERATOR_IDENTITYRES` (
  `GUID_IDENTITY` varchar(128) NOT NULL COMMENT '身份GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `AC_RESOURCETYPE` varchar(255) NOT NULL COMMENT '资源类型 : 资源：操作员所拥有的权限来源\r\n见业务字典： DICT_AC_RESOURCETYPE\r\n表示：角色编号或者组织编号（如机构编号，工作组编号）',
  `GUID_AC_RESOURCE` varchar(128) NOT NULL COMMENT '资源GUID : 根据资源类型对应到不同权限资源的GUID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='身份权限集 : 操作员身份对应的权限子集\r\n可配置内容包括 \r\n角色\r\n组织';

-- ----------------------------
-- Table structure for AC_OPERATOR_MENU
-- ----------------------------
DROP TABLE IF EXISTS `AC_OPERATOR_MENU`;
CREATE TABLE `AC_OPERATOR_MENU` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_OPERATOR` varchar(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_APP` varchar(128) DEFAULT NULL COMMENT '应用GUID',
  `GUID_FUNC` varchar(255) DEFAULT NULL COMMENT '功能GUID',
  `MENU_NAME` varchar(64) NOT NULL COMMENT '菜单名称',
  `MENU_LABEL` varchar(64) NOT NULL COMMENT '菜单显示（中文）',
  `GUID_PARENTS` varchar(128) DEFAULT NULL COMMENT '父菜单GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `ISLEAF` char(1) DEFAULT NULL COMMENT '是否叶子菜单',
  `UI_ENTRY` varchar(256) DEFAULT NULL COMMENT 'UI入口 : 针对EXT模式提供，例如abf_auth/function/module.xml',
  `MENU_LEVEL` decimal(4,0) DEFAULT NULL COMMENT '菜单层次 : 原类型smallint',
  `GUID_ROOT` varchar(128) DEFAULT NULL COMMENT '根菜单GUID',
  `DISPLAY_ORDER` decimal(4,0) DEFAULT NULL COMMENT '显示顺序 : 原类型smallint',
  `IMAGE_PATH` varchar(256) DEFAULT NULL COMMENT '菜单图片路径',
  `EXPAND_PATH` varchar(256) DEFAULT NULL COMMENT '菜单展开图片路径',
  `MENU_SEQ` varchar(256) DEFAULT NULL COMMENT '菜单路径序列',
  `OPEN_MODE` varchar(255) DEFAULT NULL COMMENT '页面打开方式 : 数值取自业务菜单： DICT_AC_OPENMODE\r\n如：主窗口打开、弹出窗口打开...',
  `SUB_COUNT` decimal(10,0) DEFAULT NULL COMMENT '子节点数',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员重组菜单 : 重组菜单；\r\n操作员对AC_MENU的定制化重组';

-- ----------------------------
-- Table structure for AC_OPERATOR_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `AC_OPERATOR_ROLE`;
CREATE TABLE `AC_OPERATOR_ROLE` (
  `GUID_OPERATOR` varchar(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_ROLE` varchar(128) NOT NULL COMMENT '拥有角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `AUTH` varchar(255) DEFAULT NULL COMMENT '是否可分级授权 : 预留字段，暂不使用。意图将操作员所拥有的权限赋予其他操作员。'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员与权限集（角色）对应关系 : 操作员与权限集（角色）对应关系表';

-- ----------------------------
-- Table structure for AC_OPERATOR_SHORTCUT
-- ----------------------------
DROP TABLE IF EXISTS `AC_OPERATOR_SHORTCUT`;
CREATE TABLE `AC_OPERATOR_SHORTCUT` (
  `GUID_OPERATOR` varchar(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_FUNC` varchar(128) NOT NULL COMMENT '功能GUID',
  `GUID_FUNCGROUP` varchar(128) NOT NULL COMMENT '功能组GUID : 冗余字段，方便为快捷键分组',
  `GUID_APP` varchar(128) NOT NULL COMMENT '应用GUID : 冗余字段，方便为快捷键分组',
  `ORDER_NO` decimal(4,0) NOT NULL COMMENT '排列顺序 : 原类型smallint',
  `IMAGE_PATH` varchar(128) DEFAULT NULL COMMENT '快捷菜单图片路径',
  `SHORTCUT_KEY` varchar(255) DEFAULT NULL COMMENT '快捷按键 : 如：CTRL+1 表示启动TX010505，本字段记录 CTRL+1 这个信息'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作员快捷菜单 : 用户自定义的快捷菜单（以应用系统进行区分）；\r\n快捷菜单中的功能可在快捷菜单面板中点击启动，也可通过对';

-- ----------------------------
-- Table structure for AC_PARTY_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `AC_PARTY_ROLE`;
CREATE TABLE `AC_PARTY_ROLE` (
  `PARTY_TYPE` varchar(255) NOT NULL COMMENT '组织对象类型 : 取值范围，见业务字典 DICT_OM_PARTYTYPE\r\n如：机构、工作组、岗位、职务',
  `GUID_PARTY` varchar(128) NOT NULL COMMENT '组织对象GUID : 根据组织类型存储对应组织的GUID',
  `GUID_ROLE` varchar(128) NOT NULL COMMENT '拥有角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织对象与角色对应关系 : 设置机构、工作组、岗位、职务等组织对象与角色之间的对应关系';

-- ----------------------------
-- Table structure for AC_ROLE
-- ----------------------------
DROP TABLE IF EXISTS `AC_ROLE`;
CREATE TABLE `AC_ROLE` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_APP` varchar(128) NOT NULL COMMENT '隶属应用GUID',
  `ROLE_CODE` varchar(64) NOT NULL COMMENT '角色代码 : 业务上对角色的编码',
  `ROLE_NAME` varchar(128) NOT NULL COMMENT '角色名称',
  `ROLE_TYPE` varchar(255) NOT NULL COMMENT '角色类别 : 取值范围见 DICT_AC_ROLETYPE',
  `ROLE_DESC` varchar(512) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `ROLE_CODE` (`ROLE_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限集(角色) : 权限集（角色）定义表';

-- ----------------------------
-- Table structure for AC_ROLE_DATASCOPE
-- ----------------------------
DROP TABLE IF EXISTS `AC_ROLE_DATASCOPE`;
CREATE TABLE `AC_ROLE_DATASCOPE` (
  `GUID_ROLE` varchar(128) NOT NULL COMMENT '角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_DATASCOPE` varchar(128) NOT NULL COMMENT '拥有数据范围GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色数据范围权限对应 : 配置角色具有的数据权限。\r\n说明角色拥有某个实体数据中哪些范围的操作权。';

-- ----------------------------
-- Table structure for AC_ROLE_ENTITY
-- ----------------------------
DROP TABLE IF EXISTS `AC_ROLE_ENTITY`;
CREATE TABLE `AC_ROLE_ENTITY` (
  `GUID_ROLE` varchar(128) NOT NULL COMMENT '角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_ENTITY` varchar(128) NOT NULL COMMENT '拥有实体GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `ISADD` char(1) NOT NULL DEFAULT 'N' COMMENT '可增加 : 取值来自业务菜单： DICT_YON',
  `ISDEL` char(1) NOT NULL DEFAULT 'N' COMMENT '可删除 : 取值来自业务菜单： DICT_YON',
  `ISMODIFY` char(1) NOT NULL DEFAULT 'N' COMMENT '可修改 : 取值来自业务菜单： DICT_YON',
  `ISVIEW` char(1) NOT NULL DEFAULT 'Y' COMMENT '可查看 : 取值来自业务菜单： DICT_YON'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色实体关系 : 角色与数据实体的对应关系。\r\n说明角色拥有哪些实体操作权。';

-- ----------------------------
-- Table structure for AC_ROLE_ENTITYFIELD
-- ----------------------------
DROP TABLE IF EXISTS `AC_ROLE_ENTITYFIELD`;
CREATE TABLE `AC_ROLE_ENTITYFIELD` (
  `GUID_ROLE` varchar(128) NOT NULL COMMENT '角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_ENTITYFIELD` varchar(128) NOT NULL COMMENT '拥有实体属性GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `ISMODIFY` char(1) NOT NULL DEFAULT 'N' COMMENT '可修改 : 取值来自业务菜单： DICT_YON',
  `ISVIEW` char(1) NOT NULL DEFAULT 'Y' COMMENT '可查看 : 取值来自业务菜单： DICT_YON'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色与实体属性关系 : 角色与实体字段（属性）的对应关系。\r\n说明某个角色拥有哪些属性的操作权。';

-- ----------------------------
-- Table structure for AC_ROLE_FUNC
-- ----------------------------
DROP TABLE IF EXISTS `AC_ROLE_FUNC`;
CREATE TABLE `AC_ROLE_FUNC` (
  `GUID_ROLE` varchar(128) NOT NULL COMMENT '角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_FUNC` varchar(128) NOT NULL COMMENT '拥有功能GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_APP` varchar(128) NOT NULL COMMENT '应用GUID : 冗余字段',
  `GUID_FUNCGROUP` varchar(128) NOT NULL COMMENT '功能组GUID : 冗余字段'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限集(角色)功能对应关系 : 角色所包含的功能清单';

-- ----------------------------
-- Table structure for LOG_ABF_HISTORY
-- ----------------------------
DROP TABLE IF EXISTS `LOG_ABF_HISTORY`;
CREATE TABLE `LOG_ABF_HISTORY` (
  `GUID` varchar(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
  `GUID_OPERATE` varchar(128) DEFAULT NULL,
  `OBJ_FROM` varchar(255) DEFAULT NULL,
  `OBJ_TYPE` varchar(128) DEFAULT NULL,
  `OBJ_GUID` varchar(128) DEFAULT NULL,
  `OBJ_NAME` varchar(128) DEFAULT NULL,
  `OBJ_VALUE` varchar(4000) DEFAULT NULL,
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录客户与网点系统接触的所有日志明细，这些接触行为包括：\r\n客户主动接触网点，如：使用自助设备；\r\n柜员主动接触客户，如';

-- ----------------------------
-- Table structure for LOG_ABF_KEYWORD
-- ----------------------------
DROP TABLE IF EXISTS `LOG_ABF_KEYWORD`;
CREATE TABLE `LOG_ABF_KEYWORD` (
  `GUID_HISTORY` varchar(128) DEFAULT NULL,
  `PARAM` varchar(128) DEFAULT NULL,
  `VALUE` varchar(512) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for LOG_ABF_OPERATE
-- ----------------------------
DROP TABLE IF EXISTS `LOG_ABF_OPERATE`;
CREATE TABLE `LOG_ABF_OPERATE` (
  `GUID` varchar(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
  `OPERATE_FROM` varchar(256) DEFAULT NULL,
  `OPERATE_TYPE` varchar(64) DEFAULT NULL COMMENT '见业务字典：DICT_OPERATOR_TYPE',
  `OPERATE_TIME` timestamp NULL DEFAULT NULL,
  `OPERATE_DESC` varchar(512) DEFAULT NULL,
  `OPERATE_RESULT` varchar(255) DEFAULT NULL COMMENT '见业务字典：DICT_OPERATOR_RESULT',
  `OPERATOR_NAME` varchar(64) DEFAULT NULL COMMENT '记录当前操作员姓名（只记录当前值，不随之改变）',
  `USER_ID` varchar(64) DEFAULT NULL COMMENT '登陆用户id',
  `APP_CODE` varchar(64) DEFAULT NULL,
  `APP_NAME` varchar(128) DEFAULT NULL,
  `FUNC_CODE` varchar(64) DEFAULT NULL COMMENT '业务上对功能的编码',
  `FUNC_NAME` varchar(128) DEFAULT NULL,
  `RESTFUL_URL` varchar(512) DEFAULT NULL COMMENT '功能对应的RESTFul服务地址',
  `STACK_TRACE` text COMMENT '记录异常堆栈信息，超过4000的部分被自动丢弃',
  `PROCESS_DESC` varchar(1024) DEFAULT NULL COMMENT '记录功能执行时的业务处理信息',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录操作员对ABF系统的操作日志（交易操作日志另见： LOG_TX_TRACE）';

-- ----------------------------
-- Table structure for OM_BUSIORG
-- ----------------------------
DROP TABLE IF EXISTS `OM_BUSIORG`;
CREATE TABLE `OM_BUSIORG` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `NODE_TYPE` varchar(255) NOT NULL COMMENT '节点类型 : 业务字典 DICT_OM_NODETYPE\r\n该业务机构的节点类型，虚拟节点，机构节点，如果是机构节点，则对应机构信息表的一个机构',
  `BUSIORG_CODE` varchar(64) NOT NULL COMMENT '业务机构代码 : 业务上对业务机构的编码',
  `BUSI_DOMAIN` varchar(255) NOT NULL COMMENT '业务条线 : 取值范围业务菜单 DICT_OM_BUSIDOMAIN',
  `BUSIORG_NAME` varchar(64) NOT NULL COMMENT '业务机构名称',
  `BUSIORG_LEVEL` decimal(4,0) NOT NULL COMMENT '业务机构层次',
  `GUID_ORG` varchar(128) DEFAULT NULL COMMENT '对应实体机构GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_PARENTS` varchar(128) DEFAULT NULL COMMENT '父业务机构GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_POSITION` varchar(128) DEFAULT NULL COMMENT '主管岗位',
  `ORG_CODE` varchar(32) DEFAULT NULL COMMENT '机构代号',
  `SEQNO` varchar(256) DEFAULT NULL COMMENT '序列号 : 业务机构的面包屑导航信息',
  `SORTNO` decimal(4,0) DEFAULT NULL COMMENT '排列顺序编号',
  `ISLEAF` char(1) NOT NULL COMMENT '是否叶子节点 : 见业务字典： DICT_YON',
  `SUB_COUNT` decimal(10,0) NOT NULL COMMENT '子节点数',
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `BUSIORG_CODE` (`BUSIORG_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务机构 : 业务机构是以业务视角来对机构进行分类分组，每个业务视角称为“业务套别”或者“业务条线”，\r\n作为业务处理的机';

-- ----------------------------
-- Table structure for OM_DUTY
-- ----------------------------
DROP TABLE IF EXISTS `OM_DUTY`;
CREATE TABLE `OM_DUTY` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `DUTY_CODE` varchar(64) NOT NULL COMMENT '职务代码',
  `DUTY_NAME` varchar(128) NOT NULL COMMENT '职务名称',
  `DUTY_TYPE` varchar(255) NOT NULL COMMENT '职务套别 : 见业务字典： DICT_OM_DUTYTYPE\r\n例如科技类，审计类等',
  `GUID_PARENTS` varchar(128) DEFAULT NULL COMMENT '父职务GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `ISLEAF` char(10) NOT NULL COMMENT '是否叶子节点 : 取值来自业务菜单：DICT_YON',
  `SUB_COUNT` decimal(10,0) NOT NULL COMMENT '子节点数',
  `DUTY_LEVEL` decimal(10,0) DEFAULT NULL COMMENT '职务层次',
  `DUTY_SEQ` varchar(256) DEFAULT NULL COMMENT '职务序列 : 职务的面包屑定位信息\r\n',
  `REMARK` varchar(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `DUTY_CODE` (`DUTY_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='职务定义表 : 职务及responsiblity。定义职务及上下级关系（可以把“职务”理解为岗位的岗位类型，岗位是在机构';

-- ----------------------------
-- Table structure for OM_EMP_GROUP
-- ----------------------------
DROP TABLE IF EXISTS `OM_EMP_GROUP`;
CREATE TABLE `OM_EMP_GROUP` (
  `GUID_EMP` varchar(128) NOT NULL COMMENT '员工GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_GROUP` varchar(128) NOT NULL COMMENT '隶属工作组GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='人员工作组对应关系 : 定义工作组包含的人员（工作组中有哪些人员）\r\n如：某个项目组有哪些人员';

-- ----------------------------
-- Table structure for OM_EMP_ORG
-- ----------------------------
DROP TABLE IF EXISTS `OM_EMP_ORG`;
CREATE TABLE `OM_EMP_ORG` (
  `GUID_EMP` varchar(128) NOT NULL COMMENT '员工GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_ORG` varchar(128) NOT NULL COMMENT '隶属机构GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `ISMAIN` char(1) NOT NULL DEFAULT 'N' COMMENT '是否主机构 : 取值来自业务菜单： DICT_YON\r\n必须有且只能有一个主机构，默认N，人员管理时程序检查当前是否只有一条主机构；'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工隶属机构关系表 : 定义人员和机构的关系表（机构有哪些人员）。\r\n允许一个人员同时在多个机构，但是只能有一个主机构。';

-- ----------------------------
-- Table structure for OM_EMP_POSITION
-- ----------------------------
DROP TABLE IF EXISTS `OM_EMP_POSITION`;
CREATE TABLE `OM_EMP_POSITION` (
  `GUID_EMP` varchar(128) NOT NULL COMMENT '员工GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_POSITION` varchar(128) NOT NULL COMMENT '所在岗位GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `ISMAIN` char(1) NOT NULL COMMENT '是否主岗位 : 取值来自业务菜单：DICT_YON\r\n只能有一个主岗位'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工岗位对应关系 : 定义人员和岗位的对应关系，需要注明，一个人员可以设定一个基本岗位';

-- ----------------------------
-- Table structure for OM_EMPLOYEE
-- ----------------------------
DROP TABLE IF EXISTS `OM_EMPLOYEE`;
CREATE TABLE `OM_EMPLOYEE` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `EMP_CODE` varchar(32) NOT NULL COMMENT '员工代码',
  `EMP_NAME` varchar(50) NOT NULL COMMENT '员工姓名',
  `EMP_REALNAME` varchar(50) DEFAULT NULL COMMENT '员工全名',
  `GENDER` varchar(255) NOT NULL COMMENT '性别 : 见业务菜单：DICT_OM_GENDER',
  `EMPSTATUS` varchar(255) NOT NULL COMMENT '员工状态 : 见业务字典： DICT_OM_EMPSTATUS',
  `EMP_DEGREE` varchar(255) NOT NULL COMMENT '员工职级 : 见业务字典： DICT_OM_EMPDEGREE',
  `GUID_ORG` varchar(128) NOT NULL COMMENT '主机构编号 : 人员所属主机构编号（冗余设计）',
  `GUID_POSITION` varchar(128) NOT NULL COMMENT '基本岗位',
  `GUID_EMP_MAJOR` varchar(128) DEFAULT NULL COMMENT '直接主管',
  `INDATE` date DEFAULT NULL COMMENT '入职日期',
  `OUTDATE` date DEFAULT NULL COMMENT '离职日期',
  `OTEL` varchar(12) DEFAULT NULL COMMENT '办公电话',
  `OADDRESS` varchar(255) DEFAULT NULL COMMENT '办公地址',
  `OZIPCODE` varchar(10) DEFAULT NULL COMMENT '办公邮编 : 见业务字典： DICT_SD_ZIPCODE',
  `OEMAIL` varchar(128) DEFAULT NULL COMMENT '办公邮件',
  `FAXNO` varchar(14) DEFAULT NULL COMMENT '传真号码',
  `MOBILENO` varchar(14) DEFAULT NULL COMMENT '手机号码',
  `MSN` varchar(16) DEFAULT NULL COMMENT 'MSN号码',
  `PAPER_TYPE` varchar(255) DEFAULT NULL COMMENT '证件类型 : 见业务字典： DICT_SD_PAPERTYPE',
  `PAPER_NO` varchar(64) DEFAULT NULL COMMENT '证件号码',
  `BIRTHDATE` date DEFAULT NULL COMMENT '出生日期',
  `HTEL` varchar(12) DEFAULT NULL COMMENT '家庭电话',
  `HADDRESS` varchar(128) DEFAULT NULL COMMENT '家庭地址',
  `HZIPCODE` varchar(10) DEFAULT NULL COMMENT '家庭邮编 : 见业务字典： DICT_SD_ZIPCODE',
  `PARTY` varchar(255) DEFAULT NULL COMMENT '政治面貌 : 见业务字典： DICT_SD_PARTY',
  `PEMAIL` varchar(128) DEFAULT NULL COMMENT '私人电子邮箱',
  `GUID_OPERATOR` varchar(128) DEFAULT NULL COMMENT '操作员编号',
  `USER_ID` varchar(64) DEFAULT NULL COMMENT '操作员 : 登陆用户id',
  `SPECIALTY` varchar(1024) DEFAULT NULL COMMENT '可授权角色 : 限定了该人员对应的操作员登陆系统时，可为其他操作员分配角色的范围；\r\n可选内容来自角色表（AC_ROLE），json数组形式，如： [{roleid:"444555"},{roleid:"999888"},....]',
  `ORG_LIST` varchar(1024) DEFAULT NULL COMMENT '可管理机构 : 限定了本人员对应的操作员可维护哪些机构信息（机构，人员等与机构关联的信息），json数组形式，如：\r\n[{orgid:"123"},{orgid:"456"},....]\r\n如果为空，则表示无任何机构的管理权限',
  `WORKEXP` varchar(512) DEFAULT NULL COMMENT '工作描述',
  `REMARK` varchar(512) DEFAULT NULL COMMENT '备注',
  `REGDATE` date DEFAULT NULL COMMENT '注册日期 : 首次新增人员记录数据的日期',
  `CREATETIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `LASTMODYTIME` timestamp NULL DEFAULT NULL COMMENT '最新更新时间',
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `EMP_CODE` (`EMP_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工 : 人员信息表\r\n人员至少隶属于一个机构；\r\n本表记录了：人员基本信息，人员联系信息，人员在机构中的信息，人员对应的操';

-- ----------------------------
-- Table structure for OM_GROUP
-- ----------------------------
DROP TABLE IF EXISTS `OM_GROUP`;
CREATE TABLE `OM_GROUP` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GROUP_CODE` varchar(64) NOT NULL COMMENT '工作组代码 : 业务上对工作组的编码',
  `GROUP_NAME` varchar(50) NOT NULL COMMENT '工作组名称',
  `GROUP_TYPE` varchar(255) NOT NULL COMMENT '工作组类型 : 见业务字典： DICT_OM_GROUPTYPE',
  `GROUP_STATUS` varchar(255) NOT NULL COMMENT '工作组状态 : 见业务字典： DICT_OM_GROUPSTATUS',
  `GROUP_DESC` varchar(512) DEFAULT NULL COMMENT '工作组描述',
  `GUID_EMP_MANAGER` varchar(128) DEFAULT NULL COMMENT '负责人 : 选择范围来自 OM_EMPLOYEE表',
  `GUID_ORG` varchar(128) NOT NULL COMMENT '隶属机构GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_PARENTS` varchar(128) DEFAULT NULL COMMENT '父工作组GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `ISLEAF` char(1) NOT NULL COMMENT '是否叶子节点 : 见业务菜单： DICT_YON',
  `SUB_COUNT` decimal(10,0) NOT NULL COMMENT '子节点数',
  `GROUP_LEVEL` decimal(4,0) DEFAULT NULL COMMENT '工作组层次',
  `GROUP_SEQ` varchar(256) DEFAULT NULL COMMENT '工作组序列 : 本工作组的面包屑定位信息',
  `START_DATE` date DEFAULT NULL COMMENT '工作组有效开始日期',
  `END_DATE` date DEFAULT NULL COMMENT '工作组有效截止日期',
  `CREATETIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `LASTUPDATE` timestamp NULL DEFAULT NULL COMMENT '最近更新时间',
  `UPDATOR` varchar(128) DEFAULT NULL COMMENT '最近更新人员',
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `GROUP_CODE` (`GROUP_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作组 : 工作组定义表，用于定义临时组、虚拟组，跨部门的项目组等。\r\n工作组实质上与机构类似，是为了将项目组、工作组等临';

-- ----------------------------
-- Table structure for OM_GROUP_APP
-- ----------------------------
DROP TABLE IF EXISTS `OM_GROUP_APP`;
CREATE TABLE `OM_GROUP_APP` (
  `GUID_GROUP` varchar(128) NOT NULL COMMENT '工作组GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_APP` varchar(128) NOT NULL COMMENT '应用GUID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作组应用列表 : 工作组所拥有（允许操作）的应用列表';

-- ----------------------------
-- Table structure for OM_GROUP_POSITION
-- ----------------------------
DROP TABLE IF EXISTS `OM_GROUP_POSITION`;
CREATE TABLE `OM_GROUP_POSITION` (
  `GUID_GROUP` varchar(128) NOT NULL COMMENT '工作组GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_POSITION` varchar(128) NOT NULL COMMENT '岗位GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作组岗位列表 : 工作组岗位列表:一个工作组允许定义多个岗位，岗位之间允许存在层次关系';

-- ----------------------------
-- Table structure for OM_ORG
-- ----------------------------
DROP TABLE IF EXISTS `OM_ORG`;
CREATE TABLE `OM_ORG` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `ORG_CODE` varchar(32) NOT NULL COMMENT '机构代码 : 业务上对机构实体的编码。\r\n一般根据机构等级和机构类型进行有规则的编码。',
  `ORG_NAME` varchar(64) NOT NULL COMMENT '机构名称',
  `ORG_TYPE` varchar(12) NOT NULL COMMENT '机构类型 : 见业务字典： DICT_OM_ORGTYPE\r\n如：总公司/总部部门/分公司/分公司部门...',
  `ORG_DEGREE` varchar(255) NOT NULL COMMENT '机构等级 : 见业务字典： DICT_OM_ORGDEGREE\r\n如：总行，分行，海外分行...',
  `ORG_STATUS` varchar(255) NOT NULL COMMENT '机构状态 : 见业务字典： DICT_OM_ORGSTATUS',
  `ORG_LEVEL` decimal(2,0) DEFAULT '1' COMMENT '机构层次',
  `GUID_PARENTS` varchar(128) DEFAULT NULL COMMENT '父机构GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `ORG_SEQ` varchar(512) DEFAULT NULL COMMENT '机构序列 : 类似面包屑导航，以“.”分割所有父机构GUID，明确示意出本机构所处层级归属\r\n格式： 父机构GUID.父机构GUID....本机构GUID',
  `ORG_ADDR` varchar(256) DEFAULT NULL COMMENT '机构地址',
  `ZIPCODE` varchar(10) DEFAULT NULL COMMENT '邮编 : 见业务字典： DICT_SD_ZIPCODE',
  `GUID_POSITION` varchar(128) DEFAULT NULL COMMENT '机构主管岗位GUID',
  `GUID_EMP_MASTER` varchar(128) DEFAULT NULL COMMENT '机构主管人员GUID',
  `GUID_EMP_MANAGER` varchar(128) DEFAULT NULL COMMENT '机构管理员GUID : 机构管理员能够给本机构的人员进行授权，多个机构管理员之间用,分隔',
  `LINK_MAN` varchar(30) DEFAULT NULL COMMENT '联系人姓名',
  `LINK_TEL` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `EMAIL` varchar(128) DEFAULT NULL COMMENT '电子邮件',
  `WEB_URL` varchar(512) DEFAULT NULL COMMENT '网站地址',
  `START_DATE` date DEFAULT NULL COMMENT '生效日期',
  `END_DATE` date DEFAULT NULL COMMENT '失效日期',
  `AREA` varchar(30) DEFAULT NULL COMMENT '所属地域 : 见业务字典： DICT_SD_AREA',
  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `LAST_UPDATE` timestamp NULL DEFAULT NULL COMMENT '最近更新时间',
  `UPDATOR` varchar(128) DEFAULT NULL COMMENT '最近更新人员',
  `SORT_NO` decimal(4,0) DEFAULT NULL COMMENT '排列顺序编号 : 维护时，可手工指定从0开始的自然数字；如果为空，系统将按照机构代码排序。',
  `ISLEAF` char(1) NOT NULL COMMENT '是否叶子节点 : 系统根据当前是否有下级机构判断更新（见业务字典 DICT_YON）',
  `SUB_COUNT` decimal(10,0) DEFAULT NULL COMMENT '子节点数 : 维护时系统根据当前拥有子机构／部分数实时更新',
  `REMARK` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `ORG_CODE` (`ORG_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='机构信息表 : 机构部门（Organization）表\r\n允许定义多个平行机构';

-- ----------------------------
-- Table structure for OM_POSITION
-- ----------------------------
DROP TABLE IF EXISTS `OM_POSITION`;
CREATE TABLE `OM_POSITION` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_ORG` varchar(128) NOT NULL COMMENT '隶属机构GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `POSITION_CODE` varchar(64) NOT NULL COMMENT '岗位代码 : 业务上对岗位的编码',
  `POSITION_NAME` varchar(128) NOT NULL COMMENT '岗位名称',
  `POSITION_TYPE` varchar(255) NOT NULL COMMENT '岗位类别 : 见业务字典： DICT_OM_POSITYPE\r\n机构岗位，工作组岗位',
  `POSITION_STATUS` varchar(255) NOT NULL COMMENT '岗位状态 : 见业务字典： DICT_OM_POSISTATUS',
  `ISLEAF` char(1) NOT NULL COMMENT '是否叶子岗位 : 见业务字典： DICT_YON',
  `SUB_COUNT` decimal(10,0) NOT NULL COMMENT '子节点数',
  `POSITION_LEVEL` decimal(2,0) DEFAULT NULL COMMENT '岗位层次',
  `POSITION_SEQ` varchar(512) DEFAULT NULL COMMENT '岗位序列 : 岗位的面包屑定位信息',
  `GUID_PARENTS` varchar(128) DEFAULT NULL COMMENT '父岗位GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_DUTY` varchar(128) NOT NULL COMMENT '所属职务GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `START_DATE` date DEFAULT NULL COMMENT '岗位有效开始日期',
  `END_DATE` date DEFAULT NULL COMMENT '岗位有效截止日期',
  `CREATETIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `LASTUPDATE` timestamp NULL DEFAULT NULL COMMENT '最近更新时间',
  `UPDATOR` varchar(128) DEFAULT NULL COMMENT '最近更新人员',
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `POSITION_CODE` (`POSITION_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位 : 岗位定义表\r\n岗位是职务在机构上的实例化表现（某个机构／部门中对某个职务（Responsibility）的工作定';

-- ----------------------------
-- Table structure for OM_POSITION_APP
-- ----------------------------
DROP TABLE IF EXISTS `OM_POSITION_APP`;
CREATE TABLE `OM_POSITION_APP` (
  `GUID_POSITION` varchar(128) NOT NULL COMMENT '岗位GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_APP` varchar(128) NOT NULL COMMENT '应用GUID'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='岗位应用列表 : 岗位所拥有（允许操作）的应用列表信息';

-- ----------------------------
-- Table structure for SYS_CHANNEL_CTL
-- ----------------------------
DROP TABLE IF EXISTS `SYS_CHANNEL_CTL`;
CREATE TABLE `SYS_CHANNEL_CTL` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier）',
  `CHN_CODE` varchar(128) NOT NULL COMMENT '渠道代码 : 记录接触系统对应的渠道代码',
  `CHN_NAME` varchar(256) NOT NULL COMMENT '渠道名称',
  `CHN_REMARK` varchar(1024) DEFAULT NULL COMMENT '渠道备注信息',
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `GUID` (`GUID`),
  UNIQUE KEY `CHN_CODE` (`CHN_CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='渠道参数控制表';

-- ----------------------------
-- Table structure for SYS_DICT
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DICT`;
CREATE TABLE `SYS_DICT` (
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
-- Table structure for SYS_DICT_ITEM
-- ----------------------------
DROP TABLE IF EXISTS `SYS_DICT_ITEM`;
CREATE TABLE `SYS_DICT_ITEM` (
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
-- Table structure for SYS_ERR_CODE
-- ----------------------------
DROP TABLE IF EXISTS `SYS_ERR_CODE`;
CREATE TABLE `SYS_ERR_CODE` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier）',
  `ERRCODE_KIND` char(8) DEFAULT NULL COMMENT '错误代码分类 : 见业务字典： DICT_ERRCODE_KIND\r\nSYS 系统错误码\r\nTRANS 交易错误码\r\n',
  `ERR_CODE` varchar(32) DEFAULT NULL COMMENT '错误代码',
  `ERR_MSG` varchar(256) DEFAULT NULL COMMENT '错误信息',
  PRIMARY KEY (`GUID`),
  UNIQUE KEY `GUID` (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='错误码表 : 记录系统中的各种错误代码信息，如系统抛出的错误信息，交易执行时的错误码等';

-- ----------------------------
-- Table structure for SYS_OPERATOR_LOG
-- ----------------------------
DROP TABLE IF EXISTS `SYS_OPERATOR_LOG`;
CREATE TABLE `SYS_OPERATOR_LOG` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier）',
  `OPERATOR_TYPE` varchar(64) DEFAULT NULL COMMENT '操作类型 : 见业务字典：DICT_OPERATOR_TYPE',
  `OPERATOR_TIME` timestamp NULL DEFAULT NULL COMMENT '操作时间',
  `OPERATOR_RESULT` varchar(255) DEFAULT NULL COMMENT '操作结果 : 见业务字典：DICT_OPERATOR_RESULT',
  `OPERATOR_NAME` varchar(64) DEFAULT NULL COMMENT '操作员姓名 : 记录当前操作员姓名（只记录当前值，不随之改变）',
  `USER_ID` varchar(64) DEFAULT NULL COMMENT '操作员 : 登陆用户id',
  `APP_CODE` varchar(64) DEFAULT NULL COMMENT '应用代码',
  `APP_NAME` varchar(128) DEFAULT NULL COMMENT '应用名称',
  `FUNC_CODE` varchar(64) DEFAULT NULL COMMENT '功能编号 : 业务上对功能的编码',
  `FUNC_NAME` varchar(128) DEFAULT NULL COMMENT '功能名称',
  `RESTFUL_RUL` varchar(512) DEFAULT NULL COMMENT '服务地址 : 功能对应的RESTFul服务地址',
  `STACK_TRACE` varchar(4000) DEFAULT NULL COMMENT '异常堆栈 : 记录异常堆栈信息，超过4000的部分被自动丢弃',
  `PROCSS_DESC` varchar(1024) DEFAULT NULL COMMENT '处理描述 : 记录功能执行时的业务处理信息',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志 : 记录操作员使用系统的操作日志（交易操作日志另见： LOG_TX_TRACE）';

-- ----------------------------
-- Table structure for SYS_RUN_CONFIG
-- ----------------------------
DROP TABLE IF EXISTS `SYS_RUN_CONFIG`;
CREATE TABLE `SYS_RUN_CONFIG` (
  `GUID` varchar(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
  `GUID_APP` varchar(64) NOT NULL COMMENT '应用系统GUID : 用于表识一组参数属于某个应用系统 。下拉AC_APP表记录',
  `GROUP_NAME` varchar(64) NOT NULL COMMENT '参数组别 : 参数组别，手工输入',
  `KEY_NAME` varchar(64) NOT NULL COMMENT '参数键 : 参数键名称，手工输入',
  `VALUE_FROM` varchar(128) NOT NULL COMMENT '值来源类型 : H：手工指定\r\n或者选择业务字典的GUID（此时存储业务字典名称 SYS_DICT.DICT_KEY)',
  `VALUE` varchar(1024) NOT NULL COMMENT '参数值 : 当value_from为H时，手工输入任意有效字符串；\r\n当value_from为业务字典时，下拉选择；',
  `DESCRIPTION` varchar(128) DEFAULT NULL COMMENT '参数描述 : 参数功能描述',
  PRIMARY KEY (`GUID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统运行参数表 : 运行期系统参数表，以三段式结构进行参数存储';

-- ----------------------------
-- Table structure for SYS_SEQNO
-- ----------------------------
DROP TABLE IF EXISTS `SYS_SEQNO`;
CREATE TABLE `SYS_SEQNO` (
  `SEQ_KEY` varchar(128) NOT NULL COMMENT '序号键值',
  `SEQ_NO` decimal(20,0) NOT NULL DEFAULT '0' COMMENT '序号数 : 顺序增加的数字',
  `RESET` varchar(32) NOT NULL COMMENT '重置方式 : 来自业务字典： DICT_SYS_RESET\r\n如：\r\n不重置（默认）\r\n按天重置\r\n按周重置\r\n自定义重置周期（按指定时间间隔重置）\r\n...',
  `RESET_PARAMS` varchar(1024) DEFAULT NULL COMMENT '重置处理参数 : 重置程序执行时的输入参数，通过本参数指定六重置周期，重置执行时间，重置起始数字等',
  PRIMARY KEY (`SEQ_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='序号资源表 : 每个SEQ_KEY表示一个序号资源，顺序增加使用序号。';
