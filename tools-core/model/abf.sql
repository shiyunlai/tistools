
/* Drop Tables */

DROP TABLE AC_ROLE_DATASCOPE CASCADE CONSTRAINTS;
DROP TABLE AC_DATASCOPE CASCADE CONSTRAINTS;
DROP TABLE AC_ROLE_ENTITYFIELD CASCADE CONSTRAINTS;
DROP TABLE AC_ENTITYFIELD CASCADE CONSTRAINTS;
DROP TABLE AC_ROLE_ENTITY CASCADE CONSTRAINTS;
DROP TABLE AC_ENTITY CASCADE CONSTRAINTS;
DROP TABLE AC_OPERATOR_BEHAVIOR CASCADE CONSTRAINTS;
DROP TABLE AC_BEHAVIOR CASCADE CONSTRAINTS;
DROP TABLE AC_FUNC_RESOURCE CASCADE CONSTRAINTS;
DROP TABLE AC_OPERATOR_FUNC CASCADE CONSTRAINTS;
DROP TABLE AC_ROLE_FUNC CASCADE CONSTRAINTS;
DROP TABLE AC_FUNC CASCADE CONSTRAINTS;
DROP TABLE AC_FUNCGROUP CASCADE CONSTRAINTS;
DROP TABLE AC_APP CASCADE CONSTRAINTS;
DROP TABLE AC_MENU CASCADE CONSTRAINTS;
DROP TABLE AC_OPERATOR_CONFIG CASCADE CONSTRAINTS;
DROP TABLE AC_OPERATOR_IDENTITYRES CASCADE CONSTRAINTS;
DROP TABLE AC_OPERATOR_IDENTITY CASCADE CONSTRAINTS;
DROP TABLE AC_OPERATOR_MENU CASCADE CONSTRAINTS;
DROP TABLE AC_OPERATOR_ROLE CASCADE CONSTRAINTS;
DROP TABLE AC_OPERATOR_SHORTCUT CASCADE CONSTRAINTS;
DROP TABLE AC_OPERATOR CASCADE CONSTRAINTS;
DROP TABLE AC_PARTY_ROLE CASCADE CONSTRAINTS;
DROP TABLE AC_ROLE CASCADE CONSTRAINTS;
DROP TABLE OM_APP_GROUP CASCADE CONSTRAINTS;
DROP TABLE OM_APP_POSITION CASCADE CONSTRAINTS;
DROP TABLE OM_BUSIORG CASCADE CONSTRAINTS;
DROP TABLE OM_EMP_POSITION CASCADE CONSTRAINTS;
DROP TABLE OM_GROUP_POSITION CASCADE CONSTRAINTS;
DROP TABLE OM_POSITION CASCADE CONSTRAINTS;
DROP TABLE OM_DUTY CASCADE CONSTRAINTS;
DROP TABLE OM_EMP_GROUP CASCADE CONSTRAINTS;
DROP TABLE OM_EMP_ORG CASCADE CONSTRAINTS;
DROP TABLE OM_EMPLOYEE CASCADE CONSTRAINTS;
DROP TABLE OM_GROUP CASCADE CONSTRAINTS;
DROP TABLE OM_ORG CASCADE CONSTRAINTS;




/* Create Tables */

-- 应用系统 : 应用系统（Application）注册表
CREATE TABLE AC_APP
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 应用代码
	APP_CODE VARCHAR2(10) NOT NULL,
	-- 应用名称
	APP_NAME VARCHAR2(50) NOT NULL,
	-- 应用类型 : 取值来自业务菜单： DICT_AC_APPTYPE
	-- 如：本地，远程
	APP_TYPE VARCHAR2(255) NOT NULL,
	-- 是否开通 : 取值来自业务菜单： DICT_YON
	-- 默认为N，新建后，必须执行应用开通操作，才被开通。
	ISOPEN CHAR(1) DEFAULT 'N' NOT NULL,
	-- 开通日期
	OPEN_DATE DATE,
	-- 访问地址
	URL VARCHAR2(256),
	-- 应用描述
	APP_DESC VARCHAR2(512),
	-- 管理维护人员
	GUID_EMP_MAINTENANCE VARCHAR2(128),
	-- 应用管理角色
	GUID_ROLE_MAINTENANCE VARCHAR2(128),
	-- 备注
	REMARK VARCHAR2(512),
	-- 是否接入集中工作平台 : 取值来自业务菜单： DICT_YON
	INIWP CHAR(1),
	-- 是否接入集中任务中心 : 取值来自业务菜单： DICT_YON
	INTASKCENTER CHAR(1),
	-- IP
	IP_ADDR VARCHAR2(50),
	-- 端口
	IP_PORT VARCHAR2(10),
	PRIMARY KEY (GUID)
);


-- 功能操作行为 : 操作行为，权限控制模块中最细粒度的权限控制点；
-- 一个功能中包括多个操作行为（operate behavior）；
-- 如：一个柜面交易功能，其中操作行为有 —— 打开交易、提交交易、取消交易、暂存交易....。
CREATE TABLE AC_BEHAVIOR
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 功能GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_FUNC VARCHAR2(128) NOT NULL,
	-- 操作行为编码 : 每个操作行为的代码标识
	BEHAVIOR_CODE VARCHAR2(64) NOT NULL,
	-- 操作行为描述
	BEHAVIOR_DESC VARCHAR2(512),
	PRIMARY KEY (GUID)
);


-- 数据范围权限 : 定义能够操作某个表数据的范围
CREATE TABLE AC_DATASCOPE
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 实体GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ENTITY VARCHAR2(128) NOT NULL,
	-- 数据范围权限名称
	PRIV_NAME VARCHAR2(64) NOT NULL,
	-- 数据操作类型 : 取值来自业务菜单：DICT_AC_DATAOPTYPE
	-- 对本数据范围内的数据，可以做哪些操作：增加、修改、删除、查询
	-- 如果为空，表示都不限制；
	-- 多个操作用逗号分隔，如： 增加,修改,删除
	DATA_OP_TYPE VARCHAR2(20),
	-- 实体名称
	ENTITY_NAME VARCHAR2(64),
	-- 过滤SQL : 例： (orgSEQ IS NULL or orgSEQ like '$[SessionEntity/orgSEQ]%') 
	-- 通过本SQL，限定了数据范围
	FILTER_SQL_STRING VARCHAR2(1024),
	PRIMARY KEY (GUID)
);


-- 实体 : 数据实体定义表
CREATE TABLE AC_ENTITY
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 隶属应用GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_APP VARCHAR2(128) NOT NULL,
	-- 实体名称
	ENTITY_NAME VARCHAR2(50) NOT NULL,
	-- 数据库表名
	TABLE_NAME VARCHAR2(64),
	-- 实体描述
	ENTITY_DESC VARCHAR2(512),
	-- 顺序
	DISPLAY_ORDER NUMBER(4) DEFAULT 0 NOT NULL,
	-- 实体类型 : 取值来自业务字典：DICT_AC_ENTITYTYPE
	-- 0-表
	-- 1-视图
	-- 2-查询实体
	-- 3-内存对象（系统运行时才存在）
	ENTITY_TYPE VARCHAR2(255) DEFAULT '0' NOT NULL,
	-- 是否可增加 : 取值来自业务菜单： DICT_YON
	ISADD CHAR(1) DEFAULT 'N' NOT NULL,
	-- 是否可删除 : 取值来自业务菜单： DICT_YON
	ISDEL CHAR(1) DEFAULT 'N' NOT NULL,
	-- 可修改 : 取值来自业务菜单： DICT_YON
	ISMODIFY CHAR(1) DEFAULT 'N' NOT NULL,
	-- 可查看 : 取值来自业务菜单： DICT_YON
	ISVIEW CHAR(1) DEFAULT 'Y' NOT NULL,
	-- 是否需要分页显示 : 取值来自业务菜单： DICT_YON
	ISPAGE CHAR(1) DEFAULT 'Y' NOT NULL,
	-- 每页记录数
	PAGE_LEN NUMBER(5) DEFAULT 10,
	-- 删除记录检查引用关系 : 根据引用关系定义，检查关联记录是否需要同步删除；
	-- 引用关系定义格式： table.column/[Y/N];table.column/[Y/N];...
	-- 举例：
	-- 假如，存在实体acct，且引用关系定义如下
	-- 
	-- guid:tws_abc.acct_guid/Y;tws_nnn.acctid/N;
	-- 
	-- 当前删除acct实体guid＝9988的记录，系统自动执行引用关系删除，逻辑如下：
	-- 查找tws_abc 表，acct_guid = 9988 的记录，并删除；
	-- 查找tws_nnn 表，acctid=9988的记录，但不删除；
	-- 
	-- 如果采用系统默认的命名方式，规则可以简化为：
	-- guid:tws_abc/Y;tws_nnn/N;
	-- 则
	-- 查找tws_abc 表，acct_guid = 9988 的记录，并删除；
	-- 查找tws_nnn 表，acct_guid = 9988 的记录，但不删除；
	-- 
	-- 前提，必须基于实体的GUID进行引用。
	CHECK_REF VARCHAR2(1024),
	PRIMARY KEY (GUID)
);


-- 实体属性 : 数据实体的字段（属性）定义表
CREATE TABLE AC_ENTITYFIELD
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 隶属实体GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ENTITY VARCHAR2(128) NOT NULL,
	-- 属性名称
	FIELD_NAME VARCHAR2(50) NOT NULL,
	-- 属性描述
	FIELD_DESC VARCHAR2(100),
	-- 显示格式 : 如：属性为日期时，可以设置显示格式 yyyy/MM/dd；
	-- 当查询出数据，返回给调用着之前生效本显示格式（返回的数据已经被格式化）；
	DISPLAY_FORMAT VARCHAR2(128),
	-- 代码大类
	DOCLIST_CODE VARCHAR2(20),
	-- CHECKBOX_VALUE
	CHECKBOX_VALUE VARCHAR2(128),
	-- 外键录入URL
	FK_INPUTURL VARCHAR2(64),
	-- 外键描述字段名
	FK_FIELDDESC VARCHAR2(64),
	-- 外键列名
	FK_COLUMNNAME VARCHAR2(64),
	-- 外键表名
	FK_TABLENAME VARCHAR2(64),
	-- 描述字段名
	DESC_FIELDNAME VARCHAR2(64),
	-- 引用类型 : 0 业务字典
	-- 1 其他表
	REF_TYPE VARCHAR2(2),
	-- 字段类型 : 0 字符串
	-- 1 整数
	-- 2 小数
	-- 3 日期
	-- 4 日期时间
	-- 5 CHECKBOX
	-- 6 引用
	FIELD_TYPE VARCHAR2(255) NOT NULL,
	-- 顺序
	DISPLAY_ORDER NUMBER(4) DEFAULT 0 NOT NULL,
	-- 数据库列名
	COLUMN_NAME VARCHAR2(64) NOT NULL,
	-- 宽度
	WIDTH NUMBER(4),
	-- 缺省值
	DEFAULT_VALUE VARCHAR2(128),
	-- 最小值
	MIN_VALUE VARCHAR2(20),
	-- 最大值
	MAX_VALUE VARCHAR2(20),
	-- 长度
	LENGTH_VALUE NUMBER(4),
	-- 小数位
	PRECISION_VALUE NUMBER(4),
	-- 页面校验类型
	VALIDATE_TYPE VARCHAR2(64),
	-- 是否可修改 : 取值来自业务菜单： DICT_YON
	ISMODIFY CHAR(1) DEFAULT 'Y' NOT NULL,
	-- 是否显示 : 取值来自业务菜单： DICT_YON
	ISDISPLAY CHAR(1) DEFAULT 'Y' NOT NULL,
	-- 是否必须填写 : 取值来自业务菜单： DICT_YON
	ISINPUT CHAR(1) DEFAULT 'N' NOT NULL,
	-- 是否是主键 : 取值来自业务菜单： DICT_YON
	ISPK CHAR(1) DEFAULT 'N' NOT NULL,
	-- 是否自动产生主键 : 取值来自业务菜单： DICT_YON
	ISAUTOKEY CHAR(1) DEFAULT 'N',
	PRIMARY KEY (GUID)
);


-- 功能 : 功能定义表，每个功能属于一个功能点，隶属于某个应用系统，同时也隶属于某个功能组。
-- 应用系统中的某个功能，如：柜面系统中的一个交易，柜面系统上软叫号功能组的‘呼号’功能。
CREATE TABLE AC_FUNC
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL UNIQUE,
	-- 隶属功能组GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_FUNCGROUP VARCHAR2(128) NOT NULL,
	-- 功能编号 : 业务上对功能的编码
	FUNC_CODE VARCHAR2(255) NOT NULL UNIQUE,
	-- 功能名称
	FUNC_NAME VARCHAR2(128) NOT NULL,
	-- 功能描述
	FUNC_DESC VARCHAR2(512),
	-- 功能调用入口
	FUNC_ACTION VARCHAR2(256),
	-- 输入参数 : 需要定义参数规范
	PARA_INFO VARCHAR2(256),
	-- 功能类型 : 取值来自业务菜单：DICT_AC_FUNCTYPE
	-- 如：页面流、交易流、渠道服务、柜面交易...
	FUNC_TYPE VARCHAR2(255) DEFAULT '''1''',
	-- 是否验证权限 : 取值来自业务菜单： DICT_YON
	ISCHECK CHAR(1),
	-- 可否定义为菜单 : 取值来自业务菜单：DICT_YON。
	-- 该功能是否可以作为菜单入口，如果作为菜单入口，则会展示在菜单树（有些接口服务功能无需挂在菜单上）
	-- 
	ISMENU CHAR(1),
	PRIMARY KEY (GUID)
);


-- 功能组 : 功能组可以理解为功能模块或者构件包，是指一类相关功能的集合。定义功能组主要是为了对系统的功能进行归类管理
-- 功能组隶属于某个应用
-- 
-- 功能组支持层次
CREATE TABLE AC_FUNCGROUP
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 隶属应用GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_APP VARCHAR2(128) NOT NULL,
	-- 功能组名称
	FUNCGROUP_NAME VARCHAR2(40),
	-- 父功能组GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_PARENTS VARCHAR2(128),
	-- 节点层次
	GROUP_LEVEL NUMBER,
	-- 功能组路径序列
	FUNCGROUP_SEQ VARCHAR2(256),
	-- 是否叶子节点 : 取值来自业务菜单： DICT_YON
	ISLEAF CHAR(1),
	-- 子节点数 : 对功能组进行子节点的增加、删除时需要同步维护
	SUB_COUNT NUMBER(10),
	PRIMARY KEY (GUID)
);


-- 功能资源对应 : 功能点包含的系统资源内容，如jsp、页面流、逻辑流等资源。
-- 功能点对应实际的代码资源。
CREATE TABLE AC_FUNC_RESOURCE
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 对应功能GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_FUNC VARCHAR2(128) NOT NULL,
	-- 资源类型 : 见业务字典： DICT_AC_FUNCRESTYPE
	-- 如：JSP、页面流、逻辑流等
	RES_TYPE VARCHAR2(255),
	-- 资源路径
	RES_PATH VARCHAR2(256),
	-- 构件包名
	COMPACK_NAME VARCHAR2(40),
	-- 资源显示名称
	RES_SHOW_NAME VARCHAR2(128),
	PRIMARY KEY (GUID)
);


-- 菜单 : 应用菜单表，从逻辑上为某个应用系统中的功能组织为一个有分类，有层级的树结构。
-- UI可根据菜单数据结构，进行界面呈现（PC上，PAD上，手机上....充分考虑用户交互体验）
CREATE TABLE AC_MENU
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 应用GUID
	GUID_APP VARCHAR2(128) NOT NULL,
	-- 功能GUID
	GUID_FUNC VARCHAR2(128),
	-- 菜单名称 : 菜单树上显示的名称，一般同功能名称
	MENU_NAME VARCHAR2(40) NOT NULL,
	-- 菜单显示（中文）
	MENU_LABEL VARCHAR2(40) NOT NULL,
	-- 菜单代码 : 业务上对本菜单记录的编码
	MENU_CODE VARCHAR2(40) NOT NULL,
	-- 是否叶子菜单 : 数值取自业务菜单：DICT_YON
	ISLEAF CHAR(1) NOT NULL,
	-- UI入口 : 针对EXT模式提供，例如abf_auth/function/module.xml
	UI_ENTRY VARCHAR2(256),
	-- 菜单层次 : 原类型smalint
	MENU_LEVEL NUMBER(4),
	-- 父菜单GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_PARENTS VARCHAR2(128),
	-- 根菜单GUID : 本菜单所在菜单树的根节点菜单GUID
	GUID_ROOT VARCHAR2(40),
	-- 显示顺序 : 原类型smalint
	DISPLAY_ORDER NUMBER(4),
	-- 菜单闭合图片路径
	IMAGE_PATH VARCHAR2(256),
	-- 菜单展开图片路径
	EXPAND_PATH VARCHAR2(256),
	-- 菜单路径序列 : 类似面包屑导航，可以看出菜单的全路径；
	-- 从应用系统开始，系统自动维护，如： /teller/loan/TX010112
	-- 表示柜面系统（teller）中贷款功能组（loan）中的TX010112功能（交易）
	MENU_SEQ VARCHAR2(256),
	-- 页面打开方式 : 数值取自业务菜单： DICT_AC_OPENMODE
	-- 如：主窗口打开、弹出窗口打开...
	OPEN_MODE VARCHAR2(255),
	-- 子节点数 : 菜单维护时同步更新
	SUB_COUNT NUMBER(10),
	PRIMARY KEY (GUID)
);


-- 操作员 : 系统登录用户表，一个用户只能有一个或零个操作员
CREATE TABLE AC_OPERATOR
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 登录用户名
	USER_ID VARCHAR2(64) NOT NULL,
	-- 密码
	PASSWORD VARCHAR2(100),
	-- 密码失效日期
	INVAL_DATE DATE,
	-- 操作员名称 : 一般同人员姓名 EMP_NAME
	OPERATOR_NAME VARCHAR2(64),
	-- 认证模式 : 取值来自业务菜单：DICT_AC_AUTHMODE
	-- 如：本地密码认证、LDAP认证、等
	-- 可以多选，以逗号分隔，且按照出现先后顺序进行认证；
	-- 如：
	-- pwd,captcha
	-- 表示输入密码，并且还需要验证码
	AUTH_MODE VARCHAR2(255) NOT NULL,
	-- 操作员状态 : 取值来自业务菜单：DICT_AC_OPERATOR_STATUS
	-- 正常，挂起，注销，锁定...
	-- 系统处理状态间的流转
	OPERATOR_STATUS VARCHAR2(255) NOT NULL,
	-- 锁定次数限制 : 登陆错误超过本数字，系统锁定操作员，默认5次。
	-- 可为操作员单独设置；
	LOCK_LIMIT NUMBER(4) DEFAULT 5 NOT NULL,
	-- 当前错误登录次数
	ERR_COUNT NUMBER(10),
	-- 锁定时间
	LOCK_TIME DATE,
	-- 解锁时间 : 当状态为锁定时，解锁的时间
	UNLOCK_TIME DATE,
	-- 菜单风格 : 取值来自业务菜单：DICT_AC_MENUTYPE
	-- 用户登录后菜单的风格
	MENU_TYPE VARCHAR2(255) NOT NULL,
	-- 最近登录时间
	LAST_LOGIN TIMESTAMP,
	-- 有效开始日期 : 启用操作员时设置，任何时间可设置；
	START_DATE DATE,
	-- 有效截止日期 : 启用操作员时设置，任何时间可设置；
	END_DATE DATE,
	-- 允许时间范围 : 定义一个规则表达式，表示允许操作的有效时间范围，格式为：
	-- [{begin:"HH:mm",end:"HH:mm"},{begin:"HH:mm",end:"HH:mm"},...]
	-- 如：
	-- [{begin:"08:00",end:"11:30"},{begin:"14:30",end:"17:00"}]
	-- 表示，该操作员被允许每天有两个时间段进行系统操作，分别 早上08:00 - 11:30，下午14:30 － 17:00 
	VALID_TIME VARCHAR2(1024),
	-- 允许MAC码 : 允许设置多个MAC，以逗号分隔，控制操作员只能在这些机器上登陆。
	MAC_CODE VARCHAR2(1024),
	-- 允许IP地址 : 允许设置多个IP地址
	IP_ADDRESS VARCHAR2(1024),
	PRIMARY KEY (GUID)
);


-- 人员特殊功能行为配置 : 配合人员特殊授权配置表一起使用
-- 特别授权某个功能给操作员时，只开放/禁止其中的部分功能；
-- 如果不存在记录，则表示对操作员开放/禁止该功能的全部功能；
CREATE TABLE AC_OPERATOR_BEHAVIOR
(
	-- 操作员GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_OPERATOR VARCHAR2(128) NOT NULL,
	-- 操作行为GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_BEHAVIOR VARCHAR2(128) NOT NULL,
	-- 授权标志 : 取值来自业务菜单：DICT_AC_AUTHTYPE
	-- 如：特别禁止、特别允许
	AUTH_TYPE VARCHAR2(255) NOT NULL
);


-- 操作员个性配置 : 操作员个性化配置
-- 如颜色配置
--     登录风格
--     是否使用重组菜单
--     默认身份
--     等
-- 
-- “操作员＋应用系统”，将配置按应用系统进行区分。
CREATE TABLE AC_OPERATOR_CONFIG
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 操作员GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_OPERATOR VARCHAR2(128) NOT NULL,
	-- 应用GUID
	GUID_APP NUMBER(10) NOT NULL,
	-- 配置类型 : 见业务字典： DICT_AC_CONFIGTYPE
	CONFIG_TYPE VARCHAR2(255) NOT NULL,
	-- 配置名
	CONFIG_NAME VARCHAR2(255) NOT NULL,
	-- 配置值
	CONFIG_VALUE VARCHAR2(1024),
	-- 是否启用 : 见业务菜单： DICT_YON
	ISVALID CHAR(1) NOT NULL,
	PRIMARY KEY (GUID),
	UNIQUE (GUID_APP, CONFIG_TYPE, CONFIG_NAME)
);


-- 人员特殊权限配置 : 针对人员配置的特殊权限，如特别开通的功能，或者特别禁止的功能
CREATE TABLE AC_OPERATOR_FUNC
(
	-- 操作员GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_OPERATOR VARCHAR2(128) NOT NULL,
	-- 功能GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_FUNC VARCHAR2(128) NOT NULL,
	-- 授权标志 : 取值来自业务菜单：DICT_AC_AUTHTYPE
	-- 如：特别禁止、特别允许
	AUTH_TYPE VARCHAR2(255) NOT NULL,
	-- 有效开始日期
	START_DATE DATE,
	-- 有效截至日期
	END_DATE DATE,
	-- 应用GUID : 冗余字段
	GUID_APP VARCHAR2(128),
	-- 功能组GUID : 冗余字段
	GUID_FUNCGROUP VARCHAR2(128)
);


-- 操作员身份 : 操作员对自己的权限进行组合形成一个固定的登录身份；
-- 供登录时选项，每一个登录身份是员工操作员的权限子集；
-- 登陆应用系统时，可以在权限子集间选择，如果不指定，则采用默认身份登陆。
-- （可基于本表扩展支持：根据登陆渠道返回操作员的权限）
CREATE TABLE AC_OPERATOR_IDENTITY
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 操作员GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_OPERATOR VARCHAR2(128) NOT NULL,
	-- 身份名称
	IDENTITY_NAME VARCHAR2(255) NOT NULL,
	-- 默认身份标志 : 见业务字典： DICT_YON
	-- 只能有一个默认身份 Y是默认身份 N不是默认身份
	IDENTITY_FLAG CHAR(1) NOT NULL,
	-- 显示顺序
	SEQ_NO NUMBER,
	PRIMARY KEY (GUID)
);


-- 身份权限集 : 操作员身份对应的权限子集
-- 可配置内容包括 
-- 角色
-- 组织
CREATE TABLE AC_OPERATOR_IDENTITYRES
(
	-- 身份GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_IDENTITY VARCHAR2(128) NOT NULL,
	-- 资源类型 : 资源：操作员所拥有的权限来源
	-- 见业务字典： DICT_AC_RESOURCETYPE
	-- 表示：角色编号或者组织编号（如机构编号，工作组编号）
	AC_RESOURCETYPE VARCHAR2(255) NOT NULL,
	-- 资源GUID : 根据资源类型对应到不同权限资源的GUID
	GUID_AC_RESOURCE VARCHAR2(128) NOT NULL
);


-- 用户重组菜单 : 重组菜单；
-- 操作员对AC_MENU的定制化重组
CREATE TABLE AC_OPERATOR_MENU
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 操作员GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_OPERATOR VARCHAR2(128) NOT NULL,
	-- 应用GUID
	GUID_APP VARCHAR2(128),
	-- 功能GUID
	GUID_FUNC VARCHAR2(255),
	-- 菜单名称
	MENU_NAME VARCHAR2(40) NOT NULL,
	-- 菜单显示（中文）
	MENU_LABEL VARCHAR2(40) NOT NULL,
	-- 父菜单GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_PARENTS VARCHAR2(128),
	-- 是否叶子菜单
	ISLEAF CHAR(1),
	-- UI入口 : 针对EXT模式提供，例如abf_auth/function/module.xml
	UI_ENTRY VARCHAR2(256),
	-- 菜单层次 : 原类型smallint
	MENU_LEVEL NUMBER,
	-- 根菜单GUID
	GUID_ROOT VARCHAR2(128),
	-- 显示顺序 : 原类型smallint
	DISPLAY_ORDER NUMBER,
	-- 菜单图片路径
	IMAGE_PATH VARCHAR2(256),
	-- 菜单展开图片路径
	EXPAND_PATH VARCHAR2(256),
	-- 菜单路径序列
	MENU_SEQ VARCHAR2(256),
	-- 页面打开方式 : 数值取自业务菜单： DICT_AC_OPENMODE
	-- 如：主窗口打开、弹出窗口打开...
	OPEN_MODE VARCHAR2(255),
	-- 子节点数
	SUB_COUNT NUMBER(10),
	PRIMARY KEY (GUID)
);


-- 操作员与权限集（角色）对应关系 : 操作员与权限集（角色）对应关系表
CREATE TABLE AC_OPERATOR_ROLE
(
	-- 操作员GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_OPERATOR VARCHAR2(128) NOT NULL,
	-- 拥有角色GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ROLE VARCHAR2(128) NOT NULL,
	-- 是否可分级授权 : 预留字段，暂不使用。意图将操作员所拥有的权限赋予其他操作员。
	AUTH NUMBER(255)
);


-- 用户快捷菜单 : 用户自定义的快捷菜单，以应用系统进行区分；
-- 快捷菜单中的功能可在快捷菜单面板中点击启动，也可通过对应的快捷按键启动（限于按键数量，只提供 CTRL ＋ 0 ～ 9 ，一共10个快捷按键，其余快捷交易只能通过快捷面板中点击启动。
CREATE TABLE AC_OPERATOR_SHORTCUT
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 操作员GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_OPERATOR VARCHAR2(128) NOT NULL,
	-- 功能GUID
	GUID_FUNC VARCHAR2(128) NOT NULL,
	-- 功能组GUID : 冗余字段，方便为快捷键分组
	GUID_FUNCGROUP VARCHAR2(128) NOT NULL,
	-- 应用GUID : 冗余字段，方便为快捷键分组
	GUID_APP VARCHAR2(128) NOT NULL,
	-- 排列顺序 : 原类型smallint
	ORDER_NO NUMBER NOT NULL,
	-- 快捷菜单图片路径
	IMAGE_PATH VARCHAR2(128),
	-- 快捷按键 : 如：CTRL+1 表示启动TX010505，本字段记录 CTRL+1 这个信息
	SHORTCUT_KEY VARCHAR2(255),
	PRIMARY KEY (GUID)
);


-- 组织对象与角色对应关系 : 设置机构、工作组、岗位、职务等组织对象与角色之间的对应关系
CREATE TABLE AC_PARTY_ROLE
(
	-- 组织对象类型 : 取值范围，见业务字典 DICT_OM_PARTYTYPE
	-- 如：机构、工作组、岗位、职务
	PARTYTYPE VARCHAR2(255) NOT NULL,
	-- 组织对象GUID : 根据组织类型存储对应组织的GUID
	GUID_PARTY NUMBER(10) NOT NULL,
	-- 拥有角色GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ROLE VARCHAR2(128) NOT NULL
);


-- 权限集(角色) : 权限集（角色）定义表
CREATE TABLE AC_ROLE
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 隶属应用GUID
	GUID_APP NUMBER(10) NOT NULL,
	-- 角色代号 : 业务上对角色的编码
	ROLE_CODE VARCHAR2(64) NOT NULL,
	-- 角色名称
	ROLE_NAME VARCHAR2(64) NOT NULL,
	-- 角色类别 : 取值范围见 DICT_AC_ROLETYPE
	ROLE_TYPE VARCHAR2(255) NOT NULL,
	-- 角色描述
	ROLE_DESC VARCHAR2(256),
	PRIMARY KEY (GUID)
);


-- 角色数据范围权限对应 : 配置角色具有的数据权限。
-- 说明角色拥有某个实体数据中哪些范围的操作权。
CREATE TABLE AC_ROLE_DATASCOPE
(
	-- 角色GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ROLE VARCHAR2(128) NOT NULL,
	-- 拥有数据范围GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_DATASCOPE VARCHAR2(128) NOT NULL
);


-- 角色实体关系 : 角色与数据实体的对应关系。
-- 说明角色拥有哪些实体操作权。
CREATE TABLE AC_ROLE_ENTITY
(
	-- 角色GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ROLE VARCHAR2(128) NOT NULL,
	-- 拥有实体GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ENTITY VARCHAR2(128) NOT NULL,
	-- 可增加 : 取值来自业务菜单： DICT_YON
	ISADD CHAR(1) DEFAULT 'N' NOT NULL,
	-- 可删除 : 取值来自业务菜单： DICT_YON
	ISDEL CHAR(1) DEFAULT 'N' NOT NULL,
	-- 可修改 : 取值来自业务菜单： DICT_YON
	ISMODIFY CHAR(1) DEFAULT 'N' NOT NULL,
	-- 可查看 : 取值来自业务菜单： DICT_YON
	ISVIEW CHAR(1) DEFAULT 'Y' NOT NULL
);


-- 角色与实体属性关系 : 角色与实体字段（属性）的对应关系。
-- 说明某个角色拥有哪些属性的操作权。
CREATE TABLE AC_ROLE_ENTITYFIELD
(
	-- 角色GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ROLE VARCHAR2(128) NOT NULL,
	-- 拥有实体属性GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ENTITYFIELD VARCHAR2(128) NOT NULL,
	-- 可修改 : 取值来自业务菜单： DICT_YON
	ISMODIFY CHAR(1) DEFAULT 'N' NOT NULL,
	-- 可查看 : 取值来自业务菜单： DICT_YON
	ISVIEW CHAR(1) DEFAULT 'Y' NOT NULL
);


-- 权限集(角色)功能对应关系 : 角色所包含的功能清单
CREATE TABLE AC_ROLE_FUNC
(
	-- 角色GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ROLE VARCHAR2(128) NOT NULL,
	-- 拥有功能GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_FUNC VARCHAR2(128) NOT NULL,
	-- 应用GUID : 冗余字段
	GUID_APP VARCHAR2(128) NOT NULL,
	-- 功能组GUID : 冗余字段
	GUID_FUNCGROUP VARCHAR2(128) NOT NULL
);


-- 应用工作组列表 : 应用包含的工作组列表，限定了工作组能选择哪些岗位
CREATE TABLE OM_APP_GROUP
(
	-- 应用GUID
	GUID_APP NUMBER(10) NOT NULL,
	-- 工作组GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_GROUP VARCHAR2(128) NOT NULL
);


-- 应用岗位列表 : 应用包含的岗位列表信息
CREATE TABLE OM_APP_POSITION
(
	-- 应用GUID
	GUID_APP NUMBER(10) NOT NULL,
	-- 岗位GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_POSITION VARCHAR2(128) NOT NULL
);


-- 业务机构 : 业务机构是以业务视角来对机构进行分类分组，每个业务视角称为“业务套别”或者“业务条线”，
-- 作为业务处理的机构线或者是业务统计的口径。
-- 比如审计条线，在总行有审计部，各分行也有审计部，总行审计部是审计条线的主管部门；
-- 允许添加虚拟节点（机构表中对应记录）；
-- 如：某公司在全国有33个分公司，为统计各个区域如华东、华北、华南、华西的销售情况，
-- 但公司内部没有华东、华北、华南、华西这样的机构，
-- 这时可以建立一个“区域”业务机构套别，在这个套别下建立华东、华北、华南、华西四个虚节点，
-- 然后将各个分公司分到不同的区域下，这样就可以按照区域进行统计了。
CREATE TABLE OM_BUSIORG
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 业务机构编码 : 业务上对业务机构的编码
	BUSIORG_CODE VARCHAR2(64) NOT NULL UNIQUE,
	-- 业务条线 : 取值范围业务菜单 DICT_OM_BUSIDOMAIN
	BUSI_DOMAIN VARCHAR2(255) NOT NULL,
	-- 业务机构名称
	BUSIORG_NAME VARCHAR2(64) NOT NULL,
	-- 业务机构层次
	BUSIORG_LEVEL NUMBER NOT NULL,
	-- 对应实体机构GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ORG VARCHAR2(128) NOT NULL,
	-- 父业务机构GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_PARENTS VARCHAR2(128),
	-- 主管岗位
	GUID_POSITION VARCHAR2(128),
	-- 节点类型 : 业务字典 DICT_OM_NODETYPE
	-- 该业务机构的节点类型，虚拟节点，机构节点，如果是机构节点，则对应机构信息表的一个机构
	NODE_TYPE VARCHAR2(255) NOT NULL,
	-- 机构代号
	ORG_CODE VARCHAR2(32),
	-- 序列号 : 业务机构的面包屑导航信息
	SEQNO VARCHAR2(256),
	-- 排列顺序编号
	SORTNO NUMBER,
	-- 是否叶子节点 : 见业务字典： DICT_YON
	ISLEAF CHAR(1) NOT NULL,
	-- 子节点数
	SUB_COUNT NUMBER(10) NOT NULL,
	PRIMARY KEY (GUID)
);


-- 职务定义表 : 职务及responsiblity。定义职务及上下级关系（可以把“职务”理解为岗位的岗位类型，岗位是在机构、部门中实例化后的职务，如：A机构设有‘总经理’这个岗位，其职务为‘总经理’）
CREATE TABLE OM_DUTY
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 职务代码
	DUTY_CODE VARCHAR2(20) NOT NULL,
	-- 职务名称
	DUTY_NAME VARCHAR2(30) NOT NULL,
	-- 职务套别 : 见业务字典： DICT_OM_DUTYTYPE
	-- 例如科技类，审计类等
	DUTY_TYPE VARCHAR2(255) NOT NULL,
	-- 父职务GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_PARENTS VARCHAR2(128) NOT NULL,
	-- 是否叶子节点 : 取值来自业务菜单：DICT_YON
	ISLEAF CHAR(10) NOT NULL,
	-- 子节点数
	SUB_COUNT NUMBER(10) NOT NULL,
	-- 职务层次
	DUTY_LEVEL NUMBER,
	-- 职务序列号 : 职务的面包屑定位信息
	-- 
	DUTY_SEQ VARCHAR2(256),
	-- 备注
	REMARK VARCHAR2(256),
	PRIMARY KEY (GUID)
);


-- 人员 : 人员信息表
-- 人员至少隶属于一个机构；
-- 本表记录了：人员基本信息，人员联系信息，人员在机构中的信息，人员对应的操作员信息集成了人员的多个维度信息一起。
CREATE TABLE OM_EMPLOYEE
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 人员代码
	EMP_CODE VARCHAR2(30),
	-- 人员姓名
	EMP_NAME VARCHAR2(50),
	-- 人员全名
	EMP_REALNAME VARCHAR2(50),
	-- 性别
	GENDER VARCHAR2(255),
	-- 出生日期
	BIRTHDATE DATE,
	-- 人员状态 : 见业务字典： DICT_OM_EMPSTATUS
	EMPSTATUS VARCHAR2(255),
	-- 证件类型 : 见业务字典： DICT_SD_PAPERTYPE
	PAPER_TYPE VARCHAR2(255),
	-- 证件号码
	PAPER_NO VARCHAR2(64),
	-- 人员职级 : 见业务字典： DICT_OM_EMPDEGREE
	EMP_DEGREE VARCHAR2(255),
	-- 主机构编号 : 人员所属主机构编号（冗余设计）
	GUID_ORG VARCHAR2(128),
	-- 基本岗位
	GUID_POSITION VARCHAR2(128),
	-- 直接主管
	GUID_EMP_MAJOR VARCHAR2(128),
	-- 入职日期
	INDATE DATE,
	-- 离职日期
	OUTDATE DATE,
	-- 办公电话
	OTEL VARCHAR2(12),
	-- 办公地址
	OADDRESS VARCHAR2(255),
	-- 办公邮编 : 见业务字典： DICT_SD_ZIPCODE
	OZIPCODE VARCHAR2(10),
	-- 办公邮件
	OEMAIL VARCHAR2(128),
	-- 传真号码
	FAXNO VARCHAR2(14),
	-- 手机号码
	MOBILENO VARCHAR2(14),
	-- MSN号码
	MSN VARCHAR2(16),
	-- 家庭电话
	HTEL VARCHAR2(12),
	-- 家庭地址
	HADDRESS VARCHAR2(128),
	-- 家庭邮编 : 见业务字典： DICT_SD_ZIPCODE
	HZIPCODE VARCHAR2(10),
	-- 政治面貌 : 见业务字典： DICT_SD_PARTY
	PARTY VARCHAR2(255),
	-- 私人电子邮箱
	PEMAIL VARCHAR2(128),
	-- 操作员编号
	GUID_OPERATOR VARCHAR2(128),
	-- 操作员登录号
	USER_ID VARCHAR2(64),
	-- 可授权角色 : 限定了该人员对应的操作员登陆系统时，可为其他操作员分配角色的范围；
	-- 可选内容来自角色表（AC_ROLE），json数组形式，如： [{roleid:"444555"},{roleid:"999888"},....]
	SPECIALTY VARCHAR2(1024),
	-- 可管理机构 : 限定了本人员对应的操作员可维护哪些机构信息（机构，人员等与机构关联的信息），json数组形式，如：
	-- [{orgid:"123"},{orgid:"456"},....]
	-- 如果为空，则表示无任何机构的管理权限
	ORG_LIST VARCHAR2(1024),
	-- 工作描述
	WORKEXP VARCHAR2(512),
	-- 备注
	REMARK VARCHAR2(512),
	-- 注册日期 : 首次新增人员记录数据的日期
	REGDATE DATE,
	-- 创建时间
	CREATETIME TIMESTAMP,
	-- 最新更新时间
	LASTMODYTIME DATE,
	PRIMARY KEY (GUID)
);


-- 人员工作组对应关系 : 定义工作组包含的人员（工作组中有哪些人员）
-- 如：某个项目组有哪些人员
CREATE TABLE OM_EMP_GROUP
(
	-- 人员GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_EMP VARCHAR2(128) NOT NULL,
	-- 隶属工作组GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_GROUP VARCHAR2(128) NOT NULL
);


-- 人员隶属机构关系表 : 定义人员和机构的关系表（机构有哪些人员）。
-- 允许一个人员同时在多个机构，但是只能有一个主机构。
CREATE TABLE OM_EMP_ORG
(
	-- 人员GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_EMP VARCHAR2(128) NOT NULL,
	-- 隶属机构GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ORG VARCHAR2(128) NOT NULL,
	-- 是否主机构 : 取值来自业务菜单： DICT_YON
	-- 必须有且只能有一个主机构
	ISMAIN CHAR(1)
);


-- 人员岗位对应关系 : 定义人员和岗位的对应关系，需要注明，一个人员可以设定一个基本岗位
CREATE TABLE OM_EMP_POSITION
(
	-- 人员GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_EMP VARCHAR2(128) NOT NULL,
	-- 所在岗位GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_POSITION VARCHAR2(128) NOT NULL,
	-- 是否主岗位 : 取值来自业务菜单：DICT_YON
	-- 只能有一个主岗位
	ISMAIN CHAR(1)
);


-- 工作组 : 工作组定义表，用于定义临时组、虚拟组，跨部门的项目组等。
-- 工作组实质上与机构类似，是为了将项目组、工作组等临时性的组织机构管理起来，业务上通常工作组有一定的时效性，是一个非常设机构。
CREATE TABLE OM_GROUP
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 工作组编号 : 业务上对工作组的编码
	GROUP_CODE NUMBER(10) NOT NULL UNIQUE,
	-- 工作组名称
	GROUP_NAME VARCHAR2(50) NOT NULL,
	-- 工作组类型 : 见业务字典： DICT_OM_GROUPTYPE
	GROUP_TYPE VARCHAR2(255) NOT NULL,
	-- 工作组状态 : 见业务字典： DICT_OM_GROUPSTATUS
	GROUP_STATUS VARCHAR2(255) NOT NULL,
	-- 工作组描述
	GROUP_DESC VARCHAR2(512),
	-- 隶属机构GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ORG VARCHAR2(128) NOT NULL,
	-- 父工作组GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_PARENTS VARCHAR2(128),
	-- 是否叶子节点 : 见业务菜单： DICT_YON
	ISLEAF CHAR(1) NOT NULL,
	-- 子节点数
	SUB_COUNT NUMBER(10) NOT NULL,
	-- 工作组层次
	GROUP_LEVEL NUMBER,
	-- 工作组路径序列 : 本工作组的面包屑定位信息
	GROUP_SEQ VARCHAR2(256),
	-- 工作组有效开始日期
	START_DATE DATE,
	-- 工作组有效截止日期
	END_DATE DATE,
	-- 负责人 : 选择范围来自 OM_EMPLOYEE表
	GUID_EMP_MANAGER VARCHAR2(128),
	-- 创建时间
	CREATETIME TIMESTAMP,
	-- 最近更新时间
	LASTUPDATE DATE,
	-- 最近更新人员
	UPDATOR VARCHAR2(128),
	PRIMARY KEY (GUID)
);


-- 工作组岗位列表 : 工作组岗位列表:一个工作组允许定义多个岗位，岗位之间允许存在层次关系
CREATE TABLE OM_GROUP_POSITION
(
	-- 工作组GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_GROUP VARCHAR2(128) NOT NULL,
	-- 岗位GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_POSITION VARCHAR2(128) NOT NULL,
	UNIQUE ()
);


-- 机构信息表 : 机构部门（Organization）表
-- 允许定义多个平行机构
CREATE TABLE OM_ORG
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 机构代码 : 业务上对机构实体的编码
	ORG_CODE VARCHAR2(32) NOT NULL UNIQUE,
	-- 机构名称
	ORG_NAME VARCHAR2(64),
	-- 机构层次
	ORG_LEVEL NUMBER(2) DEFAULT 1,
	-- 父机构GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_PARENTS VARCHAR2(128),
	-- 机构等级 : 见业务字典： DICT_OM_ORGDEGREE
	-- 如：总行，分行，海外分行...
	ORG_DEGREE VARCHAR2(255),
	-- 机构序列 : 类似面包屑导航，明确示意出本机构所处层级归属
	-- 格式： 父机构编码.父机构编码.本机构编码
	ORG_SEQ VARCHAR2(512),
	-- 机构类型 : 见业务字典： DICT_OM_ORGTYPE
	-- 如：总公司/总部部门/分公司/分公司部门...
	ORG_TYPE VARCHAR2(12),
	-- 机构地址
	ORG_ADDR VARCHAR2(256),
	-- 邮编 : 见业务字典： DICT_SD_ZIPCODE
	ZIPCODE VARCHAR2(10),
	-- 机构主管岗位GUID
	GUID_POSITION VARCHAR2(128),
	-- 机构主管人员GUID
	GUID_EMP_MASTER VARCHAR2(128),
	-- 机构管理员GUID : 机构管理员能够给本机构的人员进行授权，多个机构管理员之间用,分隔
	GUID_EMP_MANAGER VARCHAR2(128),
	-- 联系人姓名
	LINK_MAN VARCHAR2(30),
	-- 联系电话
	LINK_TEL VARCHAR2(20),
	-- 电子邮件
	EMAIL VARCHAR2(128),
	-- 网站地址
	WEB_URL VARCHAR2(512),
	-- 生效日期
	START_DATE DATE,
	-- 失效日期
	END_DATE DATE,
	-- 机构状态 : 见业务字典： DICT_OM_ORGSTATUS
	ORG_STATUS VARCHAR2(255),
	-- 所属地域 : 见业务字典： DICT_SD_AREA
	AREA VARCHAR2(30),
	-- 创建时间
	CREATE_TIME TIMESTAMP,
	-- 最近更新时间
	LAST_UPDATE DATE,
	-- 最近更新人员
	UPDATOR VARCHAR2(128),
	-- 排列顺序编号 : 维护时，可手工指定从0开始的自然数字；如果为空，系统将按照机构代码排序。
	SORT_NO NUMBER,
	-- 是否叶子节点 : 系统根据当前是否有下级机构判断更新（见业务字典 DICT_YON）
	ISLEAF CHAR(1),
	-- 子节点数 : 维护时系统根据当前拥有子机构／部分数实时更新
	SUB_COUNT NUMBER(10),
	-- 备注
	REMARK VARCHAR2(512),
	PRIMARY KEY (GUID)
);


-- 岗位/职位 : 岗位定义表
-- 岗位是职务在机构上的实例化表现（某个机构／部门中对某个职务（Responsibility）的工作定义）；
-- 一般情况下，每个岗位都需要配置一个职务系统当然出于业务上扩展考虑，并没有限制岗位一定要对应上职务；
-- 例如，一个公司有三个部门A，B，C，每个部门都设置了管理岗位 A部门经理，B部门经理，C部门经理。同时可在三个岗位上设置共同的职务为“经理”
CREATE TABLE OM_POSITION
(
	-- 数据主键 : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID VARCHAR2(128) NOT NULL,
	-- 隶属机构GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_ORG VARCHAR2(128) NOT NULL,
	-- 岗位代码 : 业务上对岗位的编码
	POSITION_CODE VARCHAR2(64) NOT NULL,
	-- 岗位名称
	POSITION_NAME VARCHAR2(128) NOT NULL,
	-- 岗位类别 : 见业务字典： DICT_OM_POSITYPE
	-- 机构岗位，工作组岗位
	POSITION_TYPE VARCHAR2(255) NOT NULL,
	-- 岗位状态 : 见业务字典： DICT_OM_POSISTATUS
	POSITION_STATUS VARCHAR2(255) NOT NULL,
	-- 是否叶子岗位 : 见业务字典： DICT_YON
	ISLEAF CHAR(1) NOT NULL,
	-- 子节点数
	SUB_COUNT NUMBER(10) NOT NULL,
	-- 岗位层次
	POSITION_LEVEL NUMBER(2),
	-- 岗位序列 : 岗位的面包屑定位信息
	POSITION_SEQ VARCHAR2(512),
	-- 父岗位GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_PARENTS VARCHAR2(128),
	-- 所属职务GUID : 记录的全局性唯一ID，系统自动生成；
	-- 一般根据实体做规则标识，以增强阅读性和辨识度，
	-- 如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
	-- 功能的数据主键规则为 function-xxxx-xxxx-xxxx
	GUID_DUTY VARCHAR2(128) NOT NULL,
	-- 创建时间
	CREATETIME TIMESTAMP,
	-- 最近更新时间
	LASTUPDATE DATE,
	-- 最近更新人员
	UPDATOR VARCHAR2(128),
	-- 岗位有效开始日期
	START_DATE DATE,
	-- 岗位有效截止日期
	END_DATE DATE,
	PRIMARY KEY (GUID)
);



/* Create Foreign Keys */

ALTER TABLE AC_ENTITY
	ADD CONSTRAINT F_APP_ENTITY FOREIGN KEY (GUID_APP)
	REFERENCES AC_APP (GUID)
;


ALTER TABLE AC_FUNCGROUP
	ADD CONSTRAINT F_APP_FUNCTION FOREIGN KEY (GUID_APP)
	REFERENCES AC_APP (GUID)
;


ALTER TABLE AC_OPERATOR_BEHAVIOR
	ADD FOREIGN KEY (GUID_BEHAVIOR)
	REFERENCES AC_BEHAVIOR (GUID)
;


ALTER TABLE AC_ROLE_DATASCOPE
	ADD CONSTRAINT F_DATA_ROLE FOREIGN KEY (GUID_DATASCOPE)
	REFERENCES AC_DATASCOPE (GUID)
;


ALTER TABLE AC_DATASCOPE
	ADD CONSTRAINT F_ENTITY_DATA FOREIGN KEY (GUID_ENTITY)
	REFERENCES AC_ENTITY (GUID)
;


ALTER TABLE AC_ENTITYFIELD
	ADD CONSTRAINT F_ENTITY_FILED FOREIGN KEY (GUID_ENTITY)
	REFERENCES AC_ENTITY (GUID)
;


ALTER TABLE AC_ROLE_ENTITY
	ADD CONSTRAINT F_ENTITY_ROLE FOREIGN KEY (GUID_ENTITY)
	REFERENCES AC_ENTITY (GUID)
;


ALTER TABLE AC_ROLE_ENTITYFIELD
	ADD CONSTRAINT F_FIELD_ROLE FOREIGN KEY (GUID_ENTITYFIELD)
	REFERENCES AC_ENTITYFIELD (GUID)
;


ALTER TABLE AC_BEHAVIOR
	ADD FOREIGN KEY (GUID_FUNC)
	REFERENCES AC_FUNC (GUID)
;


ALTER TABLE AC_FUNC_RESOURCE
	ADD CONSTRAINT F_FUN_RES FOREIGN KEY (GUID_FUNC)
	REFERENCES AC_FUNC (GUID)
;


ALTER TABLE AC_OPERATOR_FUNC
	ADD CONSTRAINT F_FUN_OPER FOREIGN KEY (GUID_FUNC)
	REFERENCES AC_FUNC (GUID)
;


ALTER TABLE AC_ROLE_FUNC
	ADD CONSTRAINT F_FUN_ROLE FOREIGN KEY (GUID_FUNC)
	REFERENCES AC_FUNC (GUID)
;


ALTER TABLE AC_FUNC
	ADD CONSTRAINT F_FUNGROUP_FUN FOREIGN KEY (GUID_FUNCGROUP)
	REFERENCES AC_FUNCGROUP (GUID)
;


ALTER TABLE AC_FUNCGROUP
	ADD CONSTRAINT F_FUNG_FUNG FOREIGN KEY (GUID_PARENTS)
	REFERENCES AC_FUNCGROUP (GUID)
;


ALTER TABLE AC_MENU
	ADD FOREIGN KEY (GUID_PARENTS)
	REFERENCES AC_MENU (GUID)
;


ALTER TABLE AC_OPERATOR_BEHAVIOR
	ADD FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
;


ALTER TABLE AC_OPERATOR_CONFIG
	ADD CONSTRAINT F_OPER_CONF FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
;


ALTER TABLE AC_OPERATOR_FUNC
	ADD CONSTRAINT F_OPER_FUN FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
;


ALTER TABLE AC_OPERATOR_IDENTITY
	ADD CONSTRAINT F_OPER_STATUS FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
;


ALTER TABLE AC_OPERATOR_MENU
	ADD CONSTRAINT F_OPER_RMENU FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
;


ALTER TABLE AC_OPERATOR_ROLE
	ADD CONSTRAINT F_OPER_ROLE FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
;


ALTER TABLE AC_OPERATOR_SHORTCUT
	ADD CONSTRAINT F_OPER_QMENU FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
;


ALTER TABLE AC_OPERATOR_IDENTITYRES
	ADD CONSTRAINT F_STATUS_FUN FOREIGN KEY (GUID_IDENTITY)
	REFERENCES AC_OPERATOR_IDENTITY (GUID)
;


ALTER TABLE AC_OPERATOR_MENU
	ADD CONSTRAINT F_ROM_ROM FOREIGN KEY (GUID_PARENTS)
	REFERENCES AC_OPERATOR_MENU (GUID)
;


ALTER TABLE AC_OPERATOR_ROLE
	ADD CONSTRAINT F_ROLE_OPER FOREIGN KEY (GUID_ROLE)
	REFERENCES AC_ROLE (GUID)
;


ALTER TABLE AC_PARTY_ROLE
	ADD CONSTRAINT P_PARTYROLE FOREIGN KEY (GUID_ROLE)
	REFERENCES AC_ROLE (GUID)
;


ALTER TABLE AC_ROLE_DATASCOPE
	ADD CONSTRAINT F_ROLE_DATA FOREIGN KEY (GUID_ROLE)
	REFERENCES AC_ROLE (GUID)
;


ALTER TABLE AC_ROLE_ENTITY
	ADD CONSTRAINT F_ROLE_ENTITY FOREIGN KEY (GUID_ROLE)
	REFERENCES AC_ROLE (GUID)
;


ALTER TABLE AC_ROLE_ENTITYFIELD
	ADD CONSTRAINT F_ROLENTY_ROLE FOREIGN KEY (GUID_ROLE)
	REFERENCES AC_ROLE (GUID)
;


ALTER TABLE AC_ROLE_FUNC
	ADD CONSTRAINT F_ROLE_FUN FOREIGN KEY (GUID_ROLE)
	REFERENCES AC_ROLE (GUID)
;


ALTER TABLE OM_BUSIORG
	ADD CONSTRAINT F_BORG_BORG FOREIGN KEY (GUID_PARENTS)
	REFERENCES OM_BUSIORG (GUID)
;


ALTER TABLE OM_DUTY
	ADD CONSTRAINT F_DUTY_DUTY FOREIGN KEY (GUID_PARENTS)
	REFERENCES OM_DUTY (GUID)
;


ALTER TABLE OM_POSITION
	ADD CONSTRAINT F_DUTY_POS FOREIGN KEY (GUID_DUTY)
	REFERENCES OM_DUTY (GUID)
;


ALTER TABLE OM_EMP_GROUP
	ADD CONSTRAINT F_EMP_GROUP FOREIGN KEY (GUID_EMP)
	REFERENCES OM_EMPLOYEE (GUID)
;


ALTER TABLE OM_EMP_ORG
	ADD FOREIGN KEY (GUID_EMP)
	REFERENCES OM_EMPLOYEE (GUID)
;


ALTER TABLE OM_EMP_POSITION
	ADD CONSTRAINT F_EMP_POS FOREIGN KEY (GUID_EMP)
	REFERENCES OM_EMPLOYEE (GUID)
;


ALTER TABLE OM_APP_GROUP
	ADD CONSTRAINT F_GROUP_APP FOREIGN KEY (GUID_GROUP)
	REFERENCES OM_GROUP (GUID)
;


ALTER TABLE OM_EMP_GROUP
	ADD FOREIGN KEY (GUID_GROUP)
	REFERENCES OM_GROUP (GUID)
;


ALTER TABLE OM_GROUP
	ADD CONSTRAINT F_GROUP_GROUP FOREIGN KEY (GUID_PARENTS)
	REFERENCES OM_GROUP (GUID)
;


ALTER TABLE OM_GROUP_POSITION
	ADD CONSTRAINT F_GROUP_POS FOREIGN KEY (GUID_GROUP)
	REFERENCES OM_GROUP (GUID)
;


ALTER TABLE OM_BUSIORG
	ADD CONSTRAINT F_BIZ_ORG FOREIGN KEY (GUID_ORG)
	REFERENCES OM_ORG (GUID)
;


ALTER TABLE OM_EMP_ORG
	ADD FOREIGN KEY (GUID_ORG)
	REFERENCES OM_ORG (GUID)
;


ALTER TABLE OM_GROUP
	ADD CONSTRAINT F_ORG_GROUP FOREIGN KEY (GUID_ORG)
	REFERENCES OM_ORG (GUID)
;


ALTER TABLE OM_ORG
	ADD CONSTRAINT F_ORG_ORG FOREIGN KEY (GUID_PARENTS)
	REFERENCES OM_ORG (GUID)
;


ALTER TABLE OM_POSITION
	ADD CONSTRAINT F_ORG_POS FOREIGN KEY (GUID_ORG)
	REFERENCES OM_ORG (GUID)
;


ALTER TABLE OM_APP_POSITION
	ADD FOREIGN KEY (GUID_POSITION)
	REFERENCES OM_POSITION (GUID)
;


ALTER TABLE OM_EMP_POSITION
	ADD CONSTRAINT F_POS_EMP FOREIGN KEY (GUID_POSITION)
	REFERENCES OM_POSITION (GUID)
;


ALTER TABLE OM_GROUP_POSITION
	ADD FOREIGN KEY (GUID_POSITION)
	REFERENCES OM_POSITION (GUID)
;


ALTER TABLE OM_POSITION
	ADD CONSTRAINT F_POS_POS FOREIGN KEY (GUID_PARENTS)
	REFERENCES OM_POSITION (GUID)
;



/* Comments */

COMMENT ON TABLE AC_APP IS '应用系统 : 应用系统（Application）注册表';
COMMENT ON COLUMN AC_APP.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_APP.APP_CODE IS '应用代码';
COMMENT ON COLUMN AC_APP.APP_NAME IS '应用名称';
COMMENT ON COLUMN AC_APP.APP_TYPE IS '应用类型 : 取值来自业务菜单： DICT_AC_APPTYPE
如：本地，远程';
COMMENT ON COLUMN AC_APP.ISOPEN IS '是否开通 : 取值来自业务菜单： DICT_YON
默认为N，新建后，必须执行应用开通操作，才被开通。';
COMMENT ON COLUMN AC_APP.OPEN_DATE IS '开通日期';
COMMENT ON COLUMN AC_APP.URL IS '访问地址';
COMMENT ON COLUMN AC_APP.APP_DESC IS '应用描述';
COMMENT ON COLUMN AC_APP.GUID_EMP_MAINTENANCE IS '管理维护人员';
COMMENT ON COLUMN AC_APP.GUID_ROLE_MAINTENANCE IS '应用管理角色';
COMMENT ON COLUMN AC_APP.REMARK IS '备注';
COMMENT ON COLUMN AC_APP.INIWP IS '是否接入集中工作平台 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_APP.INTASKCENTER IS '是否接入集中任务中心 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_APP.IP_ADDR IS 'IP';
COMMENT ON COLUMN AC_APP.IP_PORT IS '端口';
COMMENT ON TABLE AC_BEHAVIOR IS '功能操作行为 : 操作行为，权限控制模块中最细粒度的权限控制点；
一个功能中包括多个操作行为（operate behavior）；
如：一个柜面交易功能，其中操作行为有 —— 打开交易、提交交易、取消交易、暂存交易....。';
COMMENT ON COLUMN AC_BEHAVIOR.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_BEHAVIOR.GUID_FUNC IS '功能GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_BEHAVIOR.BEHAVIOR_CODE IS '操作行为编码 : 每个操作行为的代码标识';
COMMENT ON COLUMN AC_BEHAVIOR.BEHAVIOR_DESC IS '操作行为描述';
COMMENT ON TABLE AC_DATASCOPE IS '数据范围权限 : 定义能够操作某个表数据的范围';
COMMENT ON COLUMN AC_DATASCOPE.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_DATASCOPE.GUID_ENTITY IS '实体GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_DATASCOPE.PRIV_NAME IS '数据范围权限名称';
COMMENT ON COLUMN AC_DATASCOPE.DATA_OP_TYPE IS '数据操作类型 : 取值来自业务菜单：DICT_AC_DATAOPTYPE
对本数据范围内的数据，可以做哪些操作：增加、修改、删除、查询
如果为空，表示都不限制；
多个操作用逗号分隔，如： 增加,修改,删除';
COMMENT ON COLUMN AC_DATASCOPE.ENTITY_NAME IS '实体名称';
COMMENT ON COLUMN AC_DATASCOPE.FILTER_SQL_STRING IS '过滤SQL : 例： (orgSEQ IS NULL or orgSEQ like ''$[SessionEntity/orgSEQ]%'') 
通过本SQL，限定了数据范围';
COMMENT ON TABLE AC_ENTITY IS '实体 : 数据实体定义表';
COMMENT ON COLUMN AC_ENTITY.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_ENTITY.GUID_APP IS '隶属应用GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_ENTITY.ENTITY_NAME IS '实体名称';
COMMENT ON COLUMN AC_ENTITY.TABLE_NAME IS '数据库表名';
COMMENT ON COLUMN AC_ENTITY.ENTITY_DESC IS '实体描述';
COMMENT ON COLUMN AC_ENTITY.DISPLAY_ORDER IS '顺序';
COMMENT ON COLUMN AC_ENTITY.ENTITY_TYPE IS '实体类型 : 取值来自业务字典：DICT_AC_ENTITYTYPE
0-表
1-视图
2-查询实体
3-内存对象（系统运行时才存在）';
COMMENT ON COLUMN AC_ENTITY.ISADD IS '是否可增加 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ENTITY.ISDEL IS '是否可删除 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ENTITY.ISMODIFY IS '可修改 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ENTITY.ISVIEW IS '可查看 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ENTITY.ISPAGE IS '是否需要分页显示 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ENTITY.PAGE_LEN IS '每页记录数';
COMMENT ON COLUMN AC_ENTITY.CHECK_REF IS '删除记录检查引用关系 : 根据引用关系定义，检查关联记录是否需要同步删除；
引用关系定义格式： table.column/[Y/N];table.column/[Y/N];...
举例：
假如，存在实体acct，且引用关系定义如下

guid:tws_abc.acct_guid/Y;tws_nnn.acctid/N;

当前删除acct实体guid＝9988的记录，系统自动执行引用关系删除，逻辑如下：
查找tws_abc 表，acct_guid = 9988 的记录，并删除；
查找tws_nnn 表，acctid=9988的记录，但不删除；

如果采用系统默认的命名方式，规则可以简化为：
guid:tws_abc/Y;tws_nnn/N;
则
查找tws_abc 表，acct_guid = 9988 的记录，并删除；
查找tws_nnn 表，acct_guid = 9988 的记录，但不删除；

前提，必须基于实体的GUID进行引用。';
COMMENT ON TABLE AC_ENTITYFIELD IS '实体属性 : 数据实体的字段（属性）定义表';
COMMENT ON COLUMN AC_ENTITYFIELD.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_ENTITYFIELD.GUID_ENTITY IS '隶属实体GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_ENTITYFIELD.FIELD_NAME IS '属性名称';
COMMENT ON COLUMN AC_ENTITYFIELD.FIELD_DESC IS '属性描述';
COMMENT ON COLUMN AC_ENTITYFIELD.DISPLAY_FORMAT IS '显示格式 : 如：属性为日期时，可以设置显示格式 yyyy/MM/dd；
当查询出数据，返回给调用着之前生效本显示格式（返回的数据已经被格式化）；';
COMMENT ON COLUMN AC_ENTITYFIELD.DOCLIST_CODE IS '代码大类';
COMMENT ON COLUMN AC_ENTITYFIELD.CHECKBOX_VALUE IS 'CHECKBOX_VALUE';
COMMENT ON COLUMN AC_ENTITYFIELD.FK_INPUTURL IS '外键录入URL';
COMMENT ON COLUMN AC_ENTITYFIELD.FK_FIELDDESC IS '外键描述字段名';
COMMENT ON COLUMN AC_ENTITYFIELD.FK_COLUMNNAME IS '外键列名';
COMMENT ON COLUMN AC_ENTITYFIELD.FK_TABLENAME IS '外键表名';
COMMENT ON COLUMN AC_ENTITYFIELD.DESC_FIELDNAME IS '描述字段名';
COMMENT ON COLUMN AC_ENTITYFIELD.REF_TYPE IS '引用类型 : 0 业务字典
1 其他表';
COMMENT ON COLUMN AC_ENTITYFIELD.FIELD_TYPE IS '字段类型 : 0 字符串
1 整数
2 小数
3 日期
4 日期时间
5 CHECKBOX
6 引用';
COMMENT ON COLUMN AC_ENTITYFIELD.DISPLAY_ORDER IS '顺序';
COMMENT ON COLUMN AC_ENTITYFIELD.COLUMN_NAME IS '数据库列名';
COMMENT ON COLUMN AC_ENTITYFIELD.WIDTH IS '宽度';
COMMENT ON COLUMN AC_ENTITYFIELD.DEFAULT_VALUE IS '缺省值';
COMMENT ON COLUMN AC_ENTITYFIELD.MIN_VALUE IS '最小值';
COMMENT ON COLUMN AC_ENTITYFIELD.MAX_VALUE IS '最大值';
COMMENT ON COLUMN AC_ENTITYFIELD.LENGTH_VALUE IS '长度';
COMMENT ON COLUMN AC_ENTITYFIELD.PRECISION_VALUE IS '小数位';
COMMENT ON COLUMN AC_ENTITYFIELD.VALIDATE_TYPE IS '页面校验类型';
COMMENT ON COLUMN AC_ENTITYFIELD.ISMODIFY IS '是否可修改 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ENTITYFIELD.ISDISPLAY IS '是否显示 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ENTITYFIELD.ISINPUT IS '是否必须填写 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ENTITYFIELD.ISPK IS '是否是主键 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ENTITYFIELD.ISAUTOKEY IS '是否自动产生主键 : 取值来自业务菜单： DICT_YON';
COMMENT ON TABLE AC_FUNC IS '功能 : 功能定义表，每个功能属于一个功能点，隶属于某个应用系统，同时也隶属于某个功能组。
应用系统中的某个功能，如：柜面系统中的一个交易，柜面系统上软叫号功能组的‘呼号’功能。';
COMMENT ON COLUMN AC_FUNC.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_FUNC.GUID_FUNCGROUP IS '隶属功能组GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_FUNC.FUNC_CODE IS '功能编号 : 业务上对功能的编码';
COMMENT ON COLUMN AC_FUNC.FUNC_NAME IS '功能名称';
COMMENT ON COLUMN AC_FUNC.FUNC_DESC IS '功能描述';
COMMENT ON COLUMN AC_FUNC.FUNC_ACTION IS '功能调用入口';
COMMENT ON COLUMN AC_FUNC.PARA_INFO IS '输入参数 : 需要定义参数规范';
COMMENT ON COLUMN AC_FUNC.FUNC_TYPE IS '功能类型 : 取值来自业务菜单：DICT_AC_FUNCTYPE
如：页面流、交易流、渠道服务、柜面交易...';
COMMENT ON COLUMN AC_FUNC.ISCHECK IS '是否验证权限 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_FUNC.ISMENU IS '可否定义为菜单 : 取值来自业务菜单：DICT_YON。
该功能是否可以作为菜单入口，如果作为菜单入口，则会展示在菜单树（有些接口服务功能无需挂在菜单上）
';
COMMENT ON TABLE AC_FUNCGROUP IS '功能组 : 功能组可以理解为功能模块或者构件包，是指一类相关功能的集合。定义功能组主要是为了对系统的功能进行归类管理
功能组隶属于某个应用

功能组支持层次';
COMMENT ON COLUMN AC_FUNCGROUP.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_FUNCGROUP.GUID_APP IS '隶属应用GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_FUNCGROUP.FUNCGROUP_NAME IS '功能组名称';
COMMENT ON COLUMN AC_FUNCGROUP.GUID_PARENTS IS '父功能组GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_FUNCGROUP.GROUP_LEVEL IS '节点层次';
COMMENT ON COLUMN AC_FUNCGROUP.FUNCGROUP_SEQ IS '功能组路径序列';
COMMENT ON COLUMN AC_FUNCGROUP.ISLEAF IS '是否叶子节点 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_FUNCGROUP.SUB_COUNT IS '子节点数 : 对功能组进行子节点的增加、删除时需要同步维护';
COMMENT ON TABLE AC_FUNC_RESOURCE IS '功能资源对应 : 功能点包含的系统资源内容，如jsp、页面流、逻辑流等资源。
功能点对应实际的代码资源。';
COMMENT ON COLUMN AC_FUNC_RESOURCE.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_FUNC_RESOURCE.GUID_FUNC IS '对应功能GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_FUNC_RESOURCE.RES_TYPE IS '资源类型 : 见业务字典： DICT_AC_FUNCRESTYPE
如：JSP、页面流、逻辑流等';
COMMENT ON COLUMN AC_FUNC_RESOURCE.RES_PATH IS '资源路径';
COMMENT ON COLUMN AC_FUNC_RESOURCE.COMPACK_NAME IS '构件包名';
COMMENT ON COLUMN AC_FUNC_RESOURCE.RES_SHOW_NAME IS '资源显示名称';
COMMENT ON TABLE AC_MENU IS '菜单 : 应用菜单表，从逻辑上为某个应用系统中的功能组织为一个有分类，有层级的树结构。
UI可根据菜单数据结构，进行界面呈现（PC上，PAD上，手机上....充分考虑用户交互体验）';
COMMENT ON COLUMN AC_MENU.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_MENU.GUID_APP IS '应用GUID';
COMMENT ON COLUMN AC_MENU.GUID_FUNC IS '功能GUID';
COMMENT ON COLUMN AC_MENU.MENU_NAME IS '菜单名称 : 菜单树上显示的名称，一般同功能名称';
COMMENT ON COLUMN AC_MENU.MENU_LABEL IS '菜单显示（中文）';
COMMENT ON COLUMN AC_MENU.MENU_CODE IS '菜单代码 : 业务上对本菜单记录的编码';
COMMENT ON COLUMN AC_MENU.ISLEAF IS '是否叶子菜单 : 数值取自业务菜单：DICT_YON';
COMMENT ON COLUMN AC_MENU.UI_ENTRY IS 'UI入口 : 针对EXT模式提供，例如abf_auth/function/module.xml';
COMMENT ON COLUMN AC_MENU.MENU_LEVEL IS '菜单层次 : 原类型smalint';
COMMENT ON COLUMN AC_MENU.GUID_PARENTS IS '父菜单GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_MENU.GUID_ROOT IS '根菜单GUID : 本菜单所在菜单树的根节点菜单GUID';
COMMENT ON COLUMN AC_MENU.DISPLAY_ORDER IS '显示顺序 : 原类型smalint';
COMMENT ON COLUMN AC_MENU.IMAGE_PATH IS '菜单闭合图片路径';
COMMENT ON COLUMN AC_MENU.EXPAND_PATH IS '菜单展开图片路径';
COMMENT ON COLUMN AC_MENU.MENU_SEQ IS '菜单路径序列 : 类似面包屑导航，可以看出菜单的全路径；
从应用系统开始，系统自动维护，如： /teller/loan/TX010112
表示柜面系统（teller）中贷款功能组（loan）中的TX010112功能（交易）';
COMMENT ON COLUMN AC_MENU.OPEN_MODE IS '页面打开方式 : 数值取自业务菜单： DICT_AC_OPENMODE
如：主窗口打开、弹出窗口打开...';
COMMENT ON COLUMN AC_MENU.SUB_COUNT IS '子节点数 : 菜单维护时同步更新';
COMMENT ON TABLE AC_OPERATOR IS '操作员 : 系统登录用户表，一个用户只能有一个或零个操作员';
COMMENT ON COLUMN AC_OPERATOR.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR.USER_ID IS '登录用户名';
COMMENT ON COLUMN AC_OPERATOR.PASSWORD IS '密码';
COMMENT ON COLUMN AC_OPERATOR.INVAL_DATE IS '密码失效日期';
COMMENT ON COLUMN AC_OPERATOR.OPERATOR_NAME IS '操作员名称 : 一般同人员姓名 EMP_NAME';
COMMENT ON COLUMN AC_OPERATOR.AUTH_MODE IS '认证模式 : 取值来自业务菜单：DICT_AC_AUTHMODE
如：本地密码认证、LDAP认证、等
可以多选，以逗号分隔，且按照出现先后顺序进行认证；
如：
pwd,captcha
表示输入密码，并且还需要验证码';
COMMENT ON COLUMN AC_OPERATOR.OPERATOR_STATUS IS '操作员状态 : 取值来自业务菜单：DICT_AC_OPERATOR_STATUS
正常，挂起，注销，锁定...
系统处理状态间的流转';
COMMENT ON COLUMN AC_OPERATOR.LOCK_LIMIT IS '锁定次数限制 : 登陆错误超过本数字，系统锁定操作员，默认5次。
可为操作员单独设置；';
COMMENT ON COLUMN AC_OPERATOR.ERR_COUNT IS '当前错误登录次数';
COMMENT ON COLUMN AC_OPERATOR.LOCK_TIME IS '锁定时间';
COMMENT ON COLUMN AC_OPERATOR.UNLOCK_TIME IS '解锁时间 : 当状态为锁定时，解锁的时间';
COMMENT ON COLUMN AC_OPERATOR.MENU_TYPE IS '菜单风格 : 取值来自业务菜单：DICT_AC_MENUTYPE
用户登录后菜单的风格';
COMMENT ON COLUMN AC_OPERATOR.LAST_LOGIN IS '最近登录时间';
COMMENT ON COLUMN AC_OPERATOR.START_DATE IS '有效开始日期 : 启用操作员时设置，任何时间可设置；';
COMMENT ON COLUMN AC_OPERATOR.END_DATE IS '有效截止日期 : 启用操作员时设置，任何时间可设置；';
COMMENT ON COLUMN AC_OPERATOR.VALID_TIME IS '允许时间范围 : 定义一个规则表达式，表示允许操作的有效时间范围，格式为：
[{begin:"HH:mm",end:"HH:mm"},{begin:"HH:mm",end:"HH:mm"},...]
如：
[{begin:"08:00",end:"11:30"},{begin:"14:30",end:"17:00"}]
表示，该操作员被允许每天有两个时间段进行系统操作，分别 早上08:00 - 11:30，下午14:30 － 17:00 ';
COMMENT ON COLUMN AC_OPERATOR.MAC_CODE IS '允许MAC码 : 允许设置多个MAC，以逗号分隔，控制操作员只能在这些机器上登陆。';
COMMENT ON COLUMN AC_OPERATOR.IP_ADDRESS IS '允许IP地址 : 允许设置多个IP地址';
COMMENT ON TABLE AC_OPERATOR_BEHAVIOR IS '人员特殊功能行为配置 : 配合人员特殊授权配置表一起使用
特别授权某个功能给操作员时，只开放/禁止其中的部分功能；
如果不存在记录，则表示对操作员开放/禁止该功能的全部功能；';
COMMENT ON COLUMN AC_OPERATOR_BEHAVIOR.GUID_OPERATOR IS '操作员GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_BEHAVIOR.GUID_BEHAVIOR IS '操作行为GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_BEHAVIOR.AUTH_TYPE IS '授权标志 : 取值来自业务菜单：DICT_AC_AUTHTYPE
如：特别禁止、特别允许';
COMMENT ON TABLE AC_OPERATOR_CONFIG IS '操作员个性配置 : 操作员个性化配置
如颜色配置
    登录风格
    是否使用重组菜单
    默认身份
    等

“操作员＋应用系统”，将配置按应用系统进行区分。';
COMMENT ON COLUMN AC_OPERATOR_CONFIG.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_CONFIG.GUID_OPERATOR IS '操作员GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_CONFIG.GUID_APP IS '应用GUID';
COMMENT ON COLUMN AC_OPERATOR_CONFIG.CONFIG_TYPE IS '配置类型 : 见业务字典： DICT_AC_CONFIGTYPE';
COMMENT ON COLUMN AC_OPERATOR_CONFIG.CONFIG_NAME IS '配置名';
COMMENT ON COLUMN AC_OPERATOR_CONFIG.CONFIG_VALUE IS '配置值';
COMMENT ON COLUMN AC_OPERATOR_CONFIG.ISVALID IS '是否启用 : 见业务菜单： DICT_YON';
COMMENT ON TABLE AC_OPERATOR_FUNC IS '人员特殊权限配置 : 针对人员配置的特殊权限，如特别开通的功能，或者特别禁止的功能';
COMMENT ON COLUMN AC_OPERATOR_FUNC.GUID_OPERATOR IS '操作员GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_FUNC.GUID_FUNC IS '功能GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_FUNC.AUTH_TYPE IS '授权标志 : 取值来自业务菜单：DICT_AC_AUTHTYPE
如：特别禁止、特别允许';
COMMENT ON COLUMN AC_OPERATOR_FUNC.START_DATE IS '有效开始日期';
COMMENT ON COLUMN AC_OPERATOR_FUNC.END_DATE IS '有效截至日期';
COMMENT ON COLUMN AC_OPERATOR_FUNC.GUID_APP IS '应用GUID : 冗余字段';
COMMENT ON COLUMN AC_OPERATOR_FUNC.GUID_FUNCGROUP IS '功能组GUID : 冗余字段';
COMMENT ON TABLE AC_OPERATOR_IDENTITY IS '操作员身份 : 操作员对自己的权限进行组合形成一个固定的登录身份；
供登录时选项，每一个登录身份是员工操作员的权限子集；
登陆应用系统时，可以在权限子集间选择，如果不指定，则采用默认身份登陆。
（可基于本表扩展支持：根据登陆渠道返回操作员的权限）';
COMMENT ON COLUMN AC_OPERATOR_IDENTITY.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_IDENTITY.GUID_OPERATOR IS '操作员GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_IDENTITY.IDENTITY_NAME IS '身份名称';
COMMENT ON COLUMN AC_OPERATOR_IDENTITY.IDENTITY_FLAG IS '默认身份标志 : 见业务字典： DICT_YON
只能有一个默认身份 Y是默认身份 N不是默认身份';
COMMENT ON COLUMN AC_OPERATOR_IDENTITY.SEQ_NO IS '显示顺序';
COMMENT ON TABLE AC_OPERATOR_IDENTITYRES IS '身份权限集 : 操作员身份对应的权限子集
可配置内容包括 
角色
组织';
COMMENT ON COLUMN AC_OPERATOR_IDENTITYRES.GUID_IDENTITY IS '身份GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_IDENTITYRES.AC_RESOURCETYPE IS '资源类型 : 资源：操作员所拥有的权限来源
见业务字典： DICT_AC_RESOURCETYPE
表示：角色编号或者组织编号（如机构编号，工作组编号）';
COMMENT ON COLUMN AC_OPERATOR_IDENTITYRES.GUID_AC_RESOURCE IS '资源GUID : 根据资源类型对应到不同权限资源的GUID';
COMMENT ON TABLE AC_OPERATOR_MENU IS '用户重组菜单 : 重组菜单；
操作员对AC_MENU的定制化重组';
COMMENT ON COLUMN AC_OPERATOR_MENU.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_MENU.GUID_OPERATOR IS '操作员GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_MENU.GUID_APP IS '应用GUID';
COMMENT ON COLUMN AC_OPERATOR_MENU.GUID_FUNC IS '功能GUID';
COMMENT ON COLUMN AC_OPERATOR_MENU.MENU_NAME IS '菜单名称';
COMMENT ON COLUMN AC_OPERATOR_MENU.MENU_LABEL IS '菜单显示（中文）';
COMMENT ON COLUMN AC_OPERATOR_MENU.GUID_PARENTS IS '父菜单GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_MENU.ISLEAF IS '是否叶子菜单';
COMMENT ON COLUMN AC_OPERATOR_MENU.UI_ENTRY IS 'UI入口 : 针对EXT模式提供，例如abf_auth/function/module.xml';
COMMENT ON COLUMN AC_OPERATOR_MENU.MENU_LEVEL IS '菜单层次 : 原类型smallint';
COMMENT ON COLUMN AC_OPERATOR_MENU.GUID_ROOT IS '根菜单GUID';
COMMENT ON COLUMN AC_OPERATOR_MENU.DISPLAY_ORDER IS '显示顺序 : 原类型smallint';
COMMENT ON COLUMN AC_OPERATOR_MENU.IMAGE_PATH IS '菜单图片路径';
COMMENT ON COLUMN AC_OPERATOR_MENU.EXPAND_PATH IS '菜单展开图片路径';
COMMENT ON COLUMN AC_OPERATOR_MENU.MENU_SEQ IS '菜单路径序列';
COMMENT ON COLUMN AC_OPERATOR_MENU.OPEN_MODE IS '页面打开方式 : 数值取自业务菜单： DICT_AC_OPENMODE
如：主窗口打开、弹出窗口打开...';
COMMENT ON COLUMN AC_OPERATOR_MENU.SUB_COUNT IS '子节点数';
COMMENT ON TABLE AC_OPERATOR_ROLE IS '操作员与权限集（角色）对应关系 : 操作员与权限集（角色）对应关系表';
COMMENT ON COLUMN AC_OPERATOR_ROLE.GUID_OPERATOR IS '操作员GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_ROLE.GUID_ROLE IS '拥有角色GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_ROLE.AUTH IS '是否可分级授权 : 预留字段，暂不使用。意图将操作员所拥有的权限赋予其他操作员。';
COMMENT ON TABLE AC_OPERATOR_SHORTCUT IS '用户快捷菜单 : 用户自定义的快捷菜单，以应用系统进行区分；
快捷菜单中的功能可在快捷菜单面板中点击启动，也可通过对应的快捷按键启动（限于按键数量，只提供 CTRL ＋ 0 ～ 9 ，一共10个快捷按键，其余快捷交易只能通过快捷面板中点击启动。';
COMMENT ON COLUMN AC_OPERATOR_SHORTCUT.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_SHORTCUT.GUID_OPERATOR IS '操作员GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_OPERATOR_SHORTCUT.GUID_FUNC IS '功能GUID';
COMMENT ON COLUMN AC_OPERATOR_SHORTCUT.GUID_FUNCGROUP IS '功能组GUID : 冗余字段，方便为快捷键分组';
COMMENT ON COLUMN AC_OPERATOR_SHORTCUT.GUID_APP IS '应用GUID : 冗余字段，方便为快捷键分组';
COMMENT ON COLUMN AC_OPERATOR_SHORTCUT.ORDER_NO IS '排列顺序 : 原类型smallint';
COMMENT ON COLUMN AC_OPERATOR_SHORTCUT.IMAGE_PATH IS '快捷菜单图片路径';
COMMENT ON COLUMN AC_OPERATOR_SHORTCUT.SHORTCUT_KEY IS '快捷按键 : 如：CTRL+1 表示启动TX010505，本字段记录 CTRL+1 这个信息';
COMMENT ON TABLE AC_PARTY_ROLE IS '组织对象与角色对应关系 : 设置机构、工作组、岗位、职务等组织对象与角色之间的对应关系';
COMMENT ON COLUMN AC_PARTY_ROLE.PARTYTYPE IS '组织对象类型 : 取值范围，见业务字典 DICT_OM_PARTYTYPE
如：机构、工作组、岗位、职务';
COMMENT ON COLUMN AC_PARTY_ROLE.GUID_PARTY IS '组织对象GUID : 根据组织类型存储对应组织的GUID';
COMMENT ON COLUMN AC_PARTY_ROLE.GUID_ROLE IS '拥有角色GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON TABLE AC_ROLE IS '权限集(角色) : 权限集（角色）定义表';
COMMENT ON COLUMN AC_ROLE.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_ROLE.GUID_APP IS '隶属应用GUID';
COMMENT ON COLUMN AC_ROLE.ROLE_CODE IS '角色代号 : 业务上对角色的编码';
COMMENT ON COLUMN AC_ROLE.ROLE_NAME IS '角色名称';
COMMENT ON COLUMN AC_ROLE.ROLE_TYPE IS '角色类别 : 取值范围见 DICT_AC_ROLETYPE';
COMMENT ON COLUMN AC_ROLE.ROLE_DESC IS '角色描述';
COMMENT ON TABLE AC_ROLE_DATASCOPE IS '角色数据范围权限对应 : 配置角色具有的数据权限。
说明角色拥有某个实体数据中哪些范围的操作权。';
COMMENT ON COLUMN AC_ROLE_DATASCOPE.GUID_ROLE IS '角色GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_ROLE_DATASCOPE.GUID_DATASCOPE IS '拥有数据范围GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON TABLE AC_ROLE_ENTITY IS '角色实体关系 : 角色与数据实体的对应关系。
说明角色拥有哪些实体操作权。';
COMMENT ON COLUMN AC_ROLE_ENTITY.GUID_ROLE IS '角色GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_ROLE_ENTITY.GUID_ENTITY IS '拥有实体GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_ROLE_ENTITY.ISADD IS '可增加 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ROLE_ENTITY.ISDEL IS '可删除 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ROLE_ENTITY.ISMODIFY IS '可修改 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ROLE_ENTITY.ISVIEW IS '可查看 : 取值来自业务菜单： DICT_YON';
COMMENT ON TABLE AC_ROLE_ENTITYFIELD IS '角色与实体属性关系 : 角色与实体字段（属性）的对应关系。
说明某个角色拥有哪些属性的操作权。';
COMMENT ON COLUMN AC_ROLE_ENTITYFIELD.GUID_ROLE IS '角色GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_ROLE_ENTITYFIELD.GUID_ENTITYFIELD IS '拥有实体属性GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_ROLE_ENTITYFIELD.ISMODIFY IS '可修改 : 取值来自业务菜单： DICT_YON';
COMMENT ON COLUMN AC_ROLE_ENTITYFIELD.ISVIEW IS '可查看 : 取值来自业务菜单： DICT_YON';
COMMENT ON TABLE AC_ROLE_FUNC IS '权限集(角色)功能对应关系 : 角色所包含的功能清单';
COMMENT ON COLUMN AC_ROLE_FUNC.GUID_ROLE IS '角色GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_ROLE_FUNC.GUID_FUNC IS '拥有功能GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN AC_ROLE_FUNC.GUID_APP IS '应用GUID : 冗余字段';
COMMENT ON COLUMN AC_ROLE_FUNC.GUID_FUNCGROUP IS '功能组GUID : 冗余字段';
COMMENT ON TABLE OM_APP_GROUP IS '应用工作组列表 : 应用包含的工作组列表，限定了工作组能选择哪些岗位';
COMMENT ON COLUMN OM_APP_GROUP.GUID_APP IS '应用GUID';
COMMENT ON COLUMN OM_APP_GROUP.GUID_GROUP IS '工作组GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON TABLE OM_APP_POSITION IS '应用岗位列表 : 应用包含的岗位列表信息';
COMMENT ON COLUMN OM_APP_POSITION.GUID_APP IS '应用GUID';
COMMENT ON COLUMN OM_APP_POSITION.GUID_POSITION IS '岗位GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON TABLE OM_BUSIORG IS '业务机构 : 业务机构是以业务视角来对机构进行分类分组，每个业务视角称为“业务套别”或者“业务条线”，
作为业务处理的机构线或者是业务统计的口径。
比如审计条线，在总行有审计部，各分行也有审计部，总行审计部是审计条线的主管部门；
允许添加虚拟节点（机构表中对应记录）；
如：某公司在全国有33个分公司，为统计各个区域如华东、华北、华南、华西的销售情况，
但公司内部没有华东、华北、华南、华西这样的机构，
这时可以建立一个“区域”业务机构套别，在这个套别下建立华东、华北、华南、华西四个虚节点，
然后将各个分公司分到不同的区域下，这样就可以按照区域进行统计了。';
COMMENT ON COLUMN OM_BUSIORG.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_BUSIORG.BUSIORG_CODE IS '业务机构编码 : 业务上对业务机构的编码';
COMMENT ON COLUMN OM_BUSIORG.BUSI_DOMAIN IS '业务条线 : 取值范围业务菜单 DICT_OM_BUSIDOMAIN';
COMMENT ON COLUMN OM_BUSIORG.BUSIORG_NAME IS '业务机构名称';
COMMENT ON COLUMN OM_BUSIORG.BUSIORG_LEVEL IS '业务机构层次';
COMMENT ON COLUMN OM_BUSIORG.GUID_ORG IS '对应实体机构GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_BUSIORG.GUID_PARENTS IS '父业务机构GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_BUSIORG.GUID_POSITION IS '主管岗位';
COMMENT ON COLUMN OM_BUSIORG.NODE_TYPE IS '节点类型 : 业务字典 DICT_OM_NODETYPE
该业务机构的节点类型，虚拟节点，机构节点，如果是机构节点，则对应机构信息表的一个机构';
COMMENT ON COLUMN OM_BUSIORG.ORG_CODE IS '机构代号';
COMMENT ON COLUMN OM_BUSIORG.SEQNO IS '序列号 : 业务机构的面包屑导航信息';
COMMENT ON COLUMN OM_BUSIORG.SORTNO IS '排列顺序编号';
COMMENT ON COLUMN OM_BUSIORG.ISLEAF IS '是否叶子节点 : 见业务字典： DICT_YON';
COMMENT ON COLUMN OM_BUSIORG.SUB_COUNT IS '子节点数';
COMMENT ON TABLE OM_DUTY IS '职务定义表 : 职务及responsiblity。定义职务及上下级关系（可以把“职务”理解为岗位的岗位类型，岗位是在机构、部门中实例化后的职务，如：A机构设有‘总经理’这个岗位，其职务为‘总经理’）';
COMMENT ON COLUMN OM_DUTY.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_DUTY.DUTY_CODE IS '职务代码';
COMMENT ON COLUMN OM_DUTY.DUTY_NAME IS '职务名称';
COMMENT ON COLUMN OM_DUTY.DUTY_TYPE IS '职务套别 : 见业务字典： DICT_OM_DUTYTYPE
例如科技类，审计类等';
COMMENT ON COLUMN OM_DUTY.GUID_PARENTS IS '父职务GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_DUTY.ISLEAF IS '是否叶子节点 : 取值来自业务菜单：DICT_YON';
COMMENT ON COLUMN OM_DUTY.SUB_COUNT IS '子节点数';
COMMENT ON COLUMN OM_DUTY.DUTY_LEVEL IS '职务层次';
COMMENT ON COLUMN OM_DUTY.DUTY_SEQ IS '职务序列号 : 职务的面包屑定位信息
';
COMMENT ON COLUMN OM_DUTY.REMARK IS '备注';
COMMENT ON TABLE OM_EMPLOYEE IS '人员 : 人员信息表
人员至少隶属于一个机构；
本表记录了：人员基本信息，人员联系信息，人员在机构中的信息，人员对应的操作员信息集成了人员的多个维度信息一起。';
COMMENT ON COLUMN OM_EMPLOYEE.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_EMPLOYEE.EMP_CODE IS '人员代码';
COMMENT ON COLUMN OM_EMPLOYEE.EMP_NAME IS '人员姓名';
COMMENT ON COLUMN OM_EMPLOYEE.EMP_REALNAME IS '人员全名';
COMMENT ON COLUMN OM_EMPLOYEE.GENDER IS '性别';
COMMENT ON COLUMN OM_EMPLOYEE.BIRTHDATE IS '出生日期';
COMMENT ON COLUMN OM_EMPLOYEE.EMPSTATUS IS '人员状态 : 见业务字典： DICT_OM_EMPSTATUS';
COMMENT ON COLUMN OM_EMPLOYEE.PAPER_TYPE IS '证件类型 : 见业务字典： DICT_SD_PAPERTYPE';
COMMENT ON COLUMN OM_EMPLOYEE.PAPER_NO IS '证件号码';
COMMENT ON COLUMN OM_EMPLOYEE.EMP_DEGREE IS '人员职级 : 见业务字典： DICT_OM_EMPDEGREE';
COMMENT ON COLUMN OM_EMPLOYEE.GUID_ORG IS '主机构编号 : 人员所属主机构编号（冗余设计）';
COMMENT ON COLUMN OM_EMPLOYEE.GUID_POSITION IS '基本岗位';
COMMENT ON COLUMN OM_EMPLOYEE.GUID_EMP_MAJOR IS '直接主管';
COMMENT ON COLUMN OM_EMPLOYEE.INDATE IS '入职日期';
COMMENT ON COLUMN OM_EMPLOYEE.OUTDATE IS '离职日期';
COMMENT ON COLUMN OM_EMPLOYEE.OTEL IS '办公电话';
COMMENT ON COLUMN OM_EMPLOYEE.OADDRESS IS '办公地址';
COMMENT ON COLUMN OM_EMPLOYEE.OZIPCODE IS '办公邮编 : 见业务字典： DICT_SD_ZIPCODE';
COMMENT ON COLUMN OM_EMPLOYEE.OEMAIL IS '办公邮件';
COMMENT ON COLUMN OM_EMPLOYEE.FAXNO IS '传真号码';
COMMENT ON COLUMN OM_EMPLOYEE.MOBILENO IS '手机号码';
COMMENT ON COLUMN OM_EMPLOYEE.MSN IS 'MSN号码';
COMMENT ON COLUMN OM_EMPLOYEE.HTEL IS '家庭电话';
COMMENT ON COLUMN OM_EMPLOYEE.HADDRESS IS '家庭地址';
COMMENT ON COLUMN OM_EMPLOYEE.HZIPCODE IS '家庭邮编 : 见业务字典： DICT_SD_ZIPCODE';
COMMENT ON COLUMN OM_EMPLOYEE.PARTY IS '政治面貌 : 见业务字典： DICT_SD_PARTY';
COMMENT ON COLUMN OM_EMPLOYEE.PEMAIL IS '私人电子邮箱';
COMMENT ON COLUMN OM_EMPLOYEE.GUID_OPERATOR IS '操作员编号';
COMMENT ON COLUMN OM_EMPLOYEE.USER_ID IS '操作员登录号';
COMMENT ON COLUMN OM_EMPLOYEE.SPECIALTY IS '可授权角色 : 限定了该人员对应的操作员登陆系统时，可为其他操作员分配角色的范围；
可选内容来自角色表（AC_ROLE），json数组形式，如： [{roleid:"444555"},{roleid:"999888"},....]';
COMMENT ON COLUMN OM_EMPLOYEE.ORG_LIST IS '可管理机构 : 限定了本人员对应的操作员可维护哪些机构信息（机构，人员等与机构关联的信息），json数组形式，如：
[{orgid:"123"},{orgid:"456"},....]
如果为空，则表示无任何机构的管理权限';
COMMENT ON COLUMN OM_EMPLOYEE.WORKEXP IS '工作描述';
COMMENT ON COLUMN OM_EMPLOYEE.REMARK IS '备注';
COMMENT ON COLUMN OM_EMPLOYEE.REGDATE IS '注册日期 : 首次新增人员记录数据的日期';
COMMENT ON COLUMN OM_EMPLOYEE.CREATETIME IS '创建时间';
COMMENT ON COLUMN OM_EMPLOYEE.LASTMODYTIME IS '最新更新时间';
COMMENT ON TABLE OM_EMP_GROUP IS '人员工作组对应关系 : 定义工作组包含的人员（工作组中有哪些人员）
如：某个项目组有哪些人员';
COMMENT ON COLUMN OM_EMP_GROUP.GUID_EMP IS '人员GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_EMP_GROUP.GUID_GROUP IS '隶属工作组GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON TABLE OM_EMP_ORG IS '人员隶属机构关系表 : 定义人员和机构的关系表（机构有哪些人员）。
允许一个人员同时在多个机构，但是只能有一个主机构。';
COMMENT ON COLUMN OM_EMP_ORG.GUID_EMP IS '人员GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_EMP_ORG.GUID_ORG IS '隶属机构GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_EMP_ORG.ISMAIN IS '是否主机构 : 取值来自业务菜单： DICT_YON
必须有且只能有一个主机构';
COMMENT ON TABLE OM_EMP_POSITION IS '人员岗位对应关系 : 定义人员和岗位的对应关系，需要注明，一个人员可以设定一个基本岗位';
COMMENT ON COLUMN OM_EMP_POSITION.GUID_EMP IS '人员GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_EMP_POSITION.GUID_POSITION IS '所在岗位GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_EMP_POSITION.ISMAIN IS '是否主岗位 : 取值来自业务菜单：DICT_YON
只能有一个主岗位';
COMMENT ON TABLE OM_GROUP IS '工作组 : 工作组定义表，用于定义临时组、虚拟组，跨部门的项目组等。
工作组实质上与机构类似，是为了将项目组、工作组等临时性的组织机构管理起来，业务上通常工作组有一定的时效性，是一个非常设机构。';
COMMENT ON COLUMN OM_GROUP.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_GROUP.GROUP_CODE IS '工作组编号 : 业务上对工作组的编码';
COMMENT ON COLUMN OM_GROUP.GROUP_NAME IS '工作组名称';
COMMENT ON COLUMN OM_GROUP.GROUP_TYPE IS '工作组类型 : 见业务字典： DICT_OM_GROUPTYPE';
COMMENT ON COLUMN OM_GROUP.GROUP_STATUS IS '工作组状态 : 见业务字典： DICT_OM_GROUPSTATUS';
COMMENT ON COLUMN OM_GROUP.GROUP_DESC IS '工作组描述';
COMMENT ON COLUMN OM_GROUP.GUID_ORG IS '隶属机构GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_GROUP.GUID_PARENTS IS '父工作组GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_GROUP.ISLEAF IS '是否叶子节点 : 见业务菜单： DICT_YON';
COMMENT ON COLUMN OM_GROUP.SUB_COUNT IS '子节点数';
COMMENT ON COLUMN OM_GROUP.GROUP_LEVEL IS '工作组层次';
COMMENT ON COLUMN OM_GROUP.GROUP_SEQ IS '工作组路径序列 : 本工作组的面包屑定位信息';
COMMENT ON COLUMN OM_GROUP.START_DATE IS '工作组有效开始日期';
COMMENT ON COLUMN OM_GROUP.END_DATE IS '工作组有效截止日期';
COMMENT ON COLUMN OM_GROUP.GUID_EMP_MANAGER IS '负责人 : 选择范围来自 OM_EMPLOYEE表';
COMMENT ON COLUMN OM_GROUP.CREATETIME IS '创建时间';
COMMENT ON COLUMN OM_GROUP.LASTUPDATE IS '最近更新时间';
COMMENT ON COLUMN OM_GROUP.UPDATOR IS '最近更新人员';
COMMENT ON TABLE OM_GROUP_POSITION IS '工作组岗位列表 : 工作组岗位列表:一个工作组允许定义多个岗位，岗位之间允许存在层次关系';
COMMENT ON COLUMN OM_GROUP_POSITION.GUID_GROUP IS '工作组GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_GROUP_POSITION.GUID_POSITION IS '岗位GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON TABLE OM_ORG IS '机构信息表 : 机构部门（Organization）表
允许定义多个平行机构';
COMMENT ON COLUMN OM_ORG.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_ORG.ORG_CODE IS '机构代码 : 业务上对机构实体的编码';
COMMENT ON COLUMN OM_ORG.ORG_NAME IS '机构名称';
COMMENT ON COLUMN OM_ORG.ORG_LEVEL IS '机构层次';
COMMENT ON COLUMN OM_ORG.GUID_PARENTS IS '父机构GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_ORG.ORG_DEGREE IS '机构等级 : 见业务字典： DICT_OM_ORGDEGREE
如：总行，分行，海外分行...';
COMMENT ON COLUMN OM_ORG.ORG_SEQ IS '机构序列 : 类似面包屑导航，明确示意出本机构所处层级归属
格式： 父机构编码.父机构编码.本机构编码';
COMMENT ON COLUMN OM_ORG.ORG_TYPE IS '机构类型 : 见业务字典： DICT_OM_ORGTYPE
如：总公司/总部部门/分公司/分公司部门...';
COMMENT ON COLUMN OM_ORG.ORG_ADDR IS '机构地址';
COMMENT ON COLUMN OM_ORG.ZIPCODE IS '邮编 : 见业务字典： DICT_SD_ZIPCODE';
COMMENT ON COLUMN OM_ORG.GUID_POSITION IS '机构主管岗位GUID';
COMMENT ON COLUMN OM_ORG.GUID_EMP_MASTER IS '机构主管人员GUID';
COMMENT ON COLUMN OM_ORG.GUID_EMP_MANAGER IS '机构管理员GUID : 机构管理员能够给本机构的人员进行授权，多个机构管理员之间用,分隔';
COMMENT ON COLUMN OM_ORG.LINK_MAN IS '联系人姓名';
COMMENT ON COLUMN OM_ORG.LINK_TEL IS '联系电话';
COMMENT ON COLUMN OM_ORG.EMAIL IS '电子邮件';
COMMENT ON COLUMN OM_ORG.WEB_URL IS '网站地址';
COMMENT ON COLUMN OM_ORG.START_DATE IS '生效日期';
COMMENT ON COLUMN OM_ORG.END_DATE IS '失效日期';
COMMENT ON COLUMN OM_ORG.ORG_STATUS IS '机构状态 : 见业务字典： DICT_OM_ORGSTATUS';
COMMENT ON COLUMN OM_ORG.AREA IS '所属地域 : 见业务字典： DICT_SD_AREA';
COMMENT ON COLUMN OM_ORG.CREATE_TIME IS '创建时间';
COMMENT ON COLUMN OM_ORG.LAST_UPDATE IS '最近更新时间';
COMMENT ON COLUMN OM_ORG.UPDATOR IS '最近更新人员';
COMMENT ON COLUMN OM_ORG.SORT_NO IS '排列顺序编号 : 维护时，可手工指定从0开始的自然数字；如果为空，系统将按照机构代码排序。';
COMMENT ON COLUMN OM_ORG.ISLEAF IS '是否叶子节点 : 系统根据当前是否有下级机构判断更新（见业务字典 DICT_YON）';
COMMENT ON COLUMN OM_ORG.SUB_COUNT IS '子节点数 : 维护时系统根据当前拥有子机构／部分数实时更新';
COMMENT ON COLUMN OM_ORG.REMARK IS '备注';
COMMENT ON TABLE OM_POSITION IS '岗位/职位 : 岗位定义表
岗位是职务在机构上的实例化表现（某个机构／部门中对某个职务（Responsibility）的工作定义）；
一般情况下，每个岗位都需要配置一个职务系统当然出于业务上扩展考虑，并没有限制岗位一定要对应上职务；
例如，一个公司有三个部门A，B，C，每个部门都设置了管理岗位 A部门经理，B部门经理，C部门经理。同时可在三个岗位上设置共同的职务为“经理”';
COMMENT ON COLUMN OM_POSITION.GUID IS '数据主键 : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_POSITION.GUID_ORG IS '隶属机构GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_POSITION.POSITION_CODE IS '岗位代码 : 业务上对岗位的编码';
COMMENT ON COLUMN OM_POSITION.POSITION_NAME IS '岗位名称';
COMMENT ON COLUMN OM_POSITION.POSITION_TYPE IS '岗位类别 : 见业务字典： DICT_OM_POSITYPE
机构岗位，工作组岗位';
COMMENT ON COLUMN OM_POSITION.POSITION_STATUS IS '岗位状态 : 见业务字典： DICT_OM_POSISTATUS';
COMMENT ON COLUMN OM_POSITION.ISLEAF IS '是否叶子岗位 : 见业务字典： DICT_YON';
COMMENT ON COLUMN OM_POSITION.SUB_COUNT IS '子节点数';
COMMENT ON COLUMN OM_POSITION.POSITION_LEVEL IS '岗位层次';
COMMENT ON COLUMN OM_POSITION.POSITION_SEQ IS '岗位序列 : 岗位的面包屑定位信息';
COMMENT ON COLUMN OM_POSITION.GUID_PARENTS IS '父岗位GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_POSITION.GUID_DUTY IS '所属职务GUID : 记录的全局性唯一ID，系统自动生成；
一般根据实体做规则标识，以增强阅读性和辨识度，
如：操作员的数据主键规则为 operator-xxxx-xxxx-xxxx
功能的数据主键规则为 function-xxxx-xxxx-xxxx';
COMMENT ON COLUMN OM_POSITION.CREATETIME IS '创建时间';
COMMENT ON COLUMN OM_POSITION.LASTUPDATE IS '最近更新时间';
COMMENT ON COLUMN OM_POSITION.UPDATOR IS '最近更新人员';
COMMENT ON COLUMN OM_POSITION.START_DATE IS '岗位有效开始日期';
COMMENT ON COLUMN OM_POSITION.END_DATE IS '岗位有效截止日期';



