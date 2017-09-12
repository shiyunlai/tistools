SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS AC_ROLE_DATASCOPE;
DROP TABLE IF EXISTS AC_DATASCOPE;
DROP TABLE IF EXISTS AC_ROLE_ENTITYFIELD;
DROP TABLE IF EXISTS AC_ENTITYFIELD;
DROP TABLE IF EXISTS AC_ROLE_ENTITY;
DROP TABLE IF EXISTS AC_ENTITY;
DROP TABLE IF EXISTS AC_OPERATOR_BHV;
DROP TABLE IF EXISTS AC_FUNC_BHV;
DROP TABLE IF EXISTS AC_FUNC_BHVTYPE;
DROP TABLE IF EXISTS AC_FUNC_RESOURCE;
DROP TABLE IF EXISTS AC_OPERATOR_FUNC;
DROP TABLE IF EXISTS AC_ROLE_FUNC;
DROP TABLE IF EXISTS AC_FUNC;
DROP TABLE IF EXISTS AC_FUNCGROUP;
DROP TABLE IF EXISTS AC_APP;
DROP TABLE IF EXISTS AC_BHV_DEF;
DROP TABLE IF EXISTS AC_BHVTYPE_DEF;
DROP TABLE IF EXISTS AC_MENU;
DROP TABLE IF EXISTS AC_OPERATOR_CONFIG;
DROP TABLE IF EXISTS AC_OPERATOR_IDENTITYRES;
DROP TABLE IF EXISTS AC_OPERATOR_IDENTITY;
DROP TABLE IF EXISTS AC_OPERATOR_MENU;
DROP TABLE IF EXISTS AC_OPERATOR_ROLE;
DROP TABLE IF EXISTS AC_OPERATOR_SHORTCUT;
DROP TABLE IF EXISTS AC_OPERATOR;
DROP TABLE IF EXISTS AC_PARTY_ROLE;
DROP TABLE IF EXISTS AC_ROLE;
DROP TABLE IF EXISTS OM_BUSIORG;
DROP TABLE IF EXISTS OM_EMP_POSITION;
DROP TABLE IF EXISTS OM_GROUP_POSITION;
DROP TABLE IF EXISTS OM_POSITION_APP;
DROP TABLE IF EXISTS OM_POSITION;
DROP TABLE IF EXISTS OM_DUTY;
DROP TABLE IF EXISTS OM_EMP_GROUP;
DROP TABLE IF EXISTS OM_EMP_ORG;
DROP TABLE IF EXISTS OM_EMPLOYEE;
DROP TABLE IF EXISTS OM_GROUP_APP;
DROP TABLE IF EXISTS OM_GROUP;
DROP TABLE IF EXISTS OM_ORG;
DROP TABLE IF EXISTS SYS_CHANNEL_CTL;
DROP TABLE IF EXISTS SYS_DICT_ITEM;
DROP TABLE IF EXISTS SYS_DICT;
DROP TABLE IF EXISTS SYS_ERR_CODE;
DROP TABLE IF EXISTS SYS_OPERATOR_LOG;
DROP TABLE IF EXISTS SYS_RUN_CONFIG;
DROP TABLE IF EXISTS SYS_SEQNO;




/* Create Tables */

-- 应用系统 : 应用系统（Application）注册表
CREATE TABLE AC_APP
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	APP_CODE VARCHAR(64) NOT NULL COMMENT '应用代码',
	APP_NAME VARCHAR(128) NOT NULL COMMENT '应用名称',
	-- 取值来自业务菜单： DICT_AC_APPTYPE
	-- 如：本地，远程
	APP_TYPE VARCHAR(255) NOT NULL COMMENT '应用类型 : 取值来自业务菜单： DICT_AC_APPTYPE
如：本地，远程',
	-- 取值来自业务菜单： DICT_YON
	-- 默认为N，新建后，必须执行应用开通操作，才被开通。
	ISOPEN CHAR(1) DEFAULT 'N' NOT NULL COMMENT '是否开通 : 取值来自业务菜单： DICT_YON
默认为N，新建后，必须执行应用开通操作，才被开通。',
	-- 记录到时分秒
	OPEN_DATE TIMESTAMP COMMENT '开通时间 : 记录到时分秒',
	URL VARCHAR(256) COMMENT '访问地址',
	APP_DESC VARCHAR(512) COMMENT '应用描述',
	GUID_EMP_MAINTENANCE VARCHAR(128) COMMENT '管理维护人员',
	GUID_ROLE_MAINTENANCE VARCHAR(128) COMMENT '应用管理角色',
	REMARK VARCHAR(512) COMMENT '备注',
	-- 取值来自业务菜单： DICT_YON
	INIWP CHAR(1) COMMENT '是否接入集中工作平台 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	INTASKCENTER CHAR(1) COMMENT '是否接入集中任务中心 : 取值来自业务菜单： DICT_YON',
	IP_ADDR VARCHAR(50) COMMENT 'IP',
	IP_PORT VARCHAR(10) COMMENT '端口',
	PRIMARY KEY (GUID),
	UNIQUE (APP_CODE)
) COMMENT = '应用系统 : 应用系统（Application）注册表';


-- 行为类型定义 : 功能行为的分类定义，以便很好的归类和配置功能的行为（AC_FUNC_BHV）
CREATE TABLE AC_BHVTYPE_DEF
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 应用中定义，定义了某种功能特有的操作行为
	BHVTYPE_CODE VARCHAR(64) COMMENT '行为类型代码 : 应用中定义，定义了某种功能特有的操作行为',
	BHVTYPE_NAME VARCHAR(128) COMMENT '行为类型名称',
	PRIMARY KEY (GUID)
) COMMENT = '行为类型定义 : 功能行为的分类定义，以便很好的归类和配置功能的行为（AC_FUNC_BHV）';


-- 功能操作行为定义 : 每类行为中至少有一个行为定义
CREATE TABLE AC_BHV_DEF
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_BEHTYPE VARCHAR(128) NOT NULL COMMENT '操作类型GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	BHV_CODE VARCHAR(64) COMMENT '行为代码',
	BHV_NAME VARCHAR(512) COMMENT '行为名称',
	PRIMARY KEY (GUID)
) COMMENT = '功能操作行为定义 : 每类行为中至少有一个行为定义';


-- 数据范围权限 : 定义能够操作某个表数据的范围
CREATE TABLE AC_DATASCOPE
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ENTITY VARCHAR(128) NOT NULL COMMENT '实体GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	PRIV_NAME VARCHAR(64) NOT NULL COMMENT '数据范围权限名称',
	-- 取值来自业务菜单：DICT_AC_DATAOPTYPE
	-- 对本数据范围内的数据，可以做哪些操作：增加、修改、删除、查询
	-- 如果为空，表示都不限制；
	-- 多个操作用逗号分隔，如： 增加,修改,删除
	DATA_OP_TYPE VARCHAR(20) COMMENT '数据操作类型 : 取值来自业务菜单：DICT_AC_DATAOPTYPE
对本数据范围内的数据，可以做哪些操作：增加、修改、删除、查询
如果为空，表示都不限制；
多个操作用逗号分隔，如： 增加,修改,删除',
	ENTITY_NAME VARCHAR(64) COMMENT '实体名称',
	-- 例： (orgSEQ IS NULL or orgSEQ like '$[SessionEntity/orgSEQ]%') 
	-- 通过本SQL，限定了数据范围
	FILTER_SQL_STRING VARCHAR(1024) COMMENT '过滤SQL : 例： (orgSEQ IS NULL or orgSEQ like ''$[SessionEntity/orgSEQ]%'') 
通过本SQL，限定了数据范围',
	PRIMARY KEY (GUID)
) COMMENT = '数据范围权限 : 定义能够操作某个表数据的范围';


-- 实体 : 数据实体定义表
CREATE TABLE AC_ENTITY
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_APP VARCHAR(128) NOT NULL COMMENT '隶属应用GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	ENTITY_NAME VARCHAR(50) NOT NULL COMMENT '实体名称',
	TABLE_NAME VARCHAR(64) COMMENT '数据库表名',
	ENTITY_DESC VARCHAR(512) COMMENT '实体描述',
	DISPLAY_ORDER DECIMAL(4) DEFAULT 0 NOT NULL COMMENT '顺序',
	-- 取值来自业务字典：DICT_AC_ENTITYTYPE
	-- 0-表
	-- 1-视图
	-- 2-查询实体
	-- 3-内存对象（系统运行时才存在）
	ENTITY_TYPE VARCHAR(255) DEFAULT '0' NOT NULL COMMENT '实体类型 : 取值来自业务字典：DICT_AC_ENTITYTYPE
0-表
1-视图
2-查询实体
3-内存对象（系统运行时才存在）',
	-- 取值来自业务菜单： DICT_YON
	ISADD CHAR(1) DEFAULT 'N' NOT NULL COMMENT '是否可增加 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	ISDEL CHAR(1) DEFAULT 'N' NOT NULL COMMENT '是否可删除 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	ISMODIFY CHAR(1) DEFAULT 'N' NOT NULL COMMENT '可修改 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	ISVIEW CHAR(1) DEFAULT 'Y' NOT NULL COMMENT '可查看 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	ISPAGE CHAR(1) DEFAULT 'Y' NOT NULL COMMENT '是否需要分页显示 : 取值来自业务菜单： DICT_YON',
	PAGE_LEN DECIMAL(5) DEFAULT 10 COMMENT '每页记录数',
	-- 根据引用关系定义，检查关联记录是否需要同步删除；
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
	CHECK_REF VARCHAR(1024) COMMENT '删除记录检查引用关系 : 根据引用关系定义，检查关联记录是否需要同步删除；
引用关系定义格式： table.column/[Y/N];table.column/[Y/N];...
举例：
假如，存在实体acct，且引用关系定义如下

guid:tws_abc.acct_guid/Y;tws_nnn.acctid/N;

当前删除acct实体guid＝9988的记录，系统自动执行引用关系删除，逻辑如下：
查找tws_abc 表，acct_guid = 9988 的记录，并删除；
查找tws_nnn 表，ac',
	PRIMARY KEY (GUID)
) COMMENT = '实体 : 数据实体定义表';


-- 实体属性 : 数据实体的字段（属性）定义表
CREATE TABLE AC_ENTITYFIELD
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ENTITY VARCHAR(128) NOT NULL COMMENT '隶属实体GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	FIELD_NAME VARCHAR(50) NOT NULL COMMENT '属性名称',
	FIELD_DESC VARCHAR(100) COMMENT '属性描述',
	-- 如：属性为日期时，可以设置显示格式 yyyy/MM/dd；
	-- 当查询出数据，返回给调用着之前生效本显示格式（返回的数据已经被格式化）；
	DISPLAY_FORMAT VARCHAR(128) COMMENT '显示格式 : 如：属性为日期时，可以设置显示格式 yyyy/MM/dd；
当查询出数据，返回给调用着之前生效本显示格式（返回的数据已经被格式化）；',
	DOCLIST_CODE VARCHAR(20) COMMENT '代码大类',
	CHECKBOX_VALUE VARCHAR(128) COMMENT 'CHECKBOX_VALUE',
	FK_INPUTURL VARCHAR(64) COMMENT '外键录入URL',
	FK_FIELDDESC VARCHAR(64) COMMENT '外键描述字段名',
	FK_COLUMNNAME VARCHAR(64) COMMENT '外键列名',
	FK_TABLENAME VARCHAR(64) COMMENT '外键表名',
	DESC_FIELDNAME VARCHAR(64) COMMENT '描述字段名',
	-- 0 业务字典
	-- 1 其他表
	REF_TYPE VARCHAR(2) COMMENT '引用类型 : 0 业务字典
1 其他表',
	-- 0 字符串
	-- 1 整数
	-- 2 小数
	-- 3 日期
	-- 4 日期时间
	-- 5 CHECKBOX
	-- 6 引用
	FIELD_TYPE VARCHAR(255) NOT NULL COMMENT '字段类型 : 0 字符串
1 整数
2 小数
3 日期
4 日期时间
5 CHECKBOX
6 引用',
	DISPLAY_ORDER DECIMAL(4) DEFAULT 0 NOT NULL COMMENT '顺序',
	COLUMN_NAME VARCHAR(64) NOT NULL COMMENT '数据库列名',
	WIDTH DECIMAL(4) COMMENT '宽度',
	DEFAULT_VALUE VARCHAR(128) COMMENT '缺省值',
	MIN_VALUE VARCHAR(20) COMMENT '最小值',
	MAX_VALUE VARCHAR(20) COMMENT '最大值',
	LENGTH_VALUE DECIMAL(4) COMMENT '长度',
	PRECISION_VALUE DECIMAL(4) COMMENT '小数位',
	VALIDATE_TYPE VARCHAR(64) COMMENT '页面校验类型',
	-- 取值来自业务菜单： DICT_YON
	ISMODIFY CHAR(1) DEFAULT 'Y' NOT NULL COMMENT '是否可修改 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	ISDISPLAY CHAR(1) DEFAULT 'Y' NOT NULL COMMENT '是否显示 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	ISINPUT CHAR(1) DEFAULT 'N' NOT NULL COMMENT '是否必须填写 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	ISPK CHAR(1) DEFAULT 'N' NOT NULL COMMENT '是否是主键 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	ISAUTOKEY CHAR(1) DEFAULT 'N' COMMENT '是否自动产生主键 : 取值来自业务菜单： DICT_YON',
	PRIMARY KEY (GUID)
) COMMENT = '实体属性 : 数据实体的字段（属性）定义表';


-- 功能 : 功能定义表，每个功能属于一个功能点，隶属于某个应用系统，同时也隶属于某个功能组。
-- 应用系统中的某个功能，如：柜
CREATE TABLE AC_FUNC
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_FUNCGROUP VARCHAR(128) NOT NULL COMMENT '隶属功能组GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 业务上对功能的编码
	FUNC_CODE VARCHAR(64) NOT NULL COMMENT '功能编号 : 业务上对功能的编码',
	FUNC_NAME VARCHAR(128) NOT NULL COMMENT '功能名称',
	FUNC_DESC VARCHAR(512) COMMENT '功能描述',
	FUNC_ACTION VARCHAR(256) COMMENT '功能调用入口',
	-- 需要定义参数规范
	PARA_INFO VARCHAR(256) COMMENT '输入参数 : 需要定义参数规范',
	-- 取值来自业务菜单：DICT_AC_FUNCTYPE
	-- 如：页面流、交易流、渠道服务、柜面交易...
	FUNC_TYPE VARCHAR(255) DEFAULT '''1''' COMMENT '功能类型 : 取值来自业务菜单：DICT_AC_FUNCTYPE
如：页面流、交易流、渠道服务、柜面交易...',
	-- 取值来自业务菜单： DICT_YON
	ISCHECK CHAR(1) COMMENT '是否验证权限 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单：DICT_YON。
	-- 该功能是否可以作为菜单入口，如果作为菜单入口，则会展示在菜单树（有些接口服务功能无需挂在菜单上）
	-- 
	ISMENU CHAR(1) COMMENT '可否定义为菜单 : 取值来自业务菜单：DICT_YON。
该功能是否可以作为菜单入口，如果作为菜单入口，则会展示在菜单树（有些接口服务功能无需挂在菜单上）
',
	PRIMARY KEY (GUID),
	UNIQUE (GUID),
	UNIQUE (FUNC_CODE)
) COMMENT = '功能 : 功能定义表，每个功能属于一个功能点，隶属于某个应用系统，同时也隶属于某个功能组。
应用系统中的某个功能，如：柜';


-- 功能组 : 功能组可以理解为功能模块或者构件包，是指一类相关功能的集合。定义功能组主要是为了对系统的功能进行归类管理
-- 
CREATE TABLE AC_FUNCGROUP
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_APP VARCHAR(128) NOT NULL COMMENT '隶属应用GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	FUNCGROUP_NAME VARCHAR(64) COMMENT '功能组名称',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_PARENTS VARCHAR(128) COMMENT '父功能组GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	GROUP_LEVEL DECIMAL(4) COMMENT '节点层次',
	FUNCGROUP_SEQ VARCHAR(256) COMMENT '功能组路径序列',
	-- 取值来自业务菜单： DICT_YON
	ISLEAF CHAR(1) COMMENT '是否叶子节点 : 取值来自业务菜单： DICT_YON',
	-- 对功能组进行子节点的增加、删除时需要同步维护
	SUB_COUNT DECIMAL(10) COMMENT '子节点数 : 对功能组进行子节点的增加、删除时需要同步维护',
	PRIMARY KEY (GUID)
) COMMENT = '功能组 : 功能组可以理解为功能模块或者构件包，是指一类相关功能的集合。定义功能组主要是为了对系统的功能进行归类管理
';


-- 功能操作行为 : Behavior（BHV）操作行为，权限控制模块中最细粒度的授权、控制单位；一个功能中包括多个操作行为
CREATE TABLE AC_FUNC_BHV
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_FUNC VARCHAR(128) NOT NULL COMMENT '功能GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_BHV VARCHAR(128) NOT NULL COMMENT '行为GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 见业务字典： DICT_YON
	-- Y 有效（默认都是有效的操作行为）
	-- N 无效
	ISEFFECTIVE CHAR(1) NOT NULL COMMENT '是否有效 : 见业务字典： DICT_YON
Y 有效（默认都是有效的操作行为）
N 无效',
	PRIMARY KEY (GUID)
) COMMENT = '功能操作行为 : Behavior（BHV）操作行为，权限控制模块中最细粒度的授权、控制单位；一个功能中包括多个操作行为';


-- 功能行为类型表 : 功能有那些行为类型，通过本映射关系，也指明了功能可能具有的行为；
-- 每个功能可以有多个行为类型，至少一
CREATE TABLE AC_FUNC_BHVTYPE
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_FUNC VARCHAR(128) NOT NULL COMMENT '功能GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_BHVTYPE VARCHAR(128) NOT NULL COMMENT '行为类型GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；'
) COMMENT = '功能行为类型表 : 功能有那些行为类型，通过本映射关系，也指明了功能可能具有的行为；
每个功能可以有多个行为类型，至少一';


-- 功能资源对应 : 功能点包含的系统资源内容，如jsp、页面流、逻辑流等资源。
-- 功能点对应实际的代码资源。
CREATE TABLE AC_FUNC_RESOURCE
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_FUNC VARCHAR(128) NOT NULL COMMENT '对应功能GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 见业务字典： DICT_AC_FUNCRESTYPE
	-- 如：JSP、页面流、逻辑流等
	RES_TYPE VARCHAR(255) COMMENT '资源类型 : 见业务字典： DICT_AC_FUNCRESTYPE
如：JSP、页面流、逻辑流等',
	RES_PATH VARCHAR(256) COMMENT '资源路径',
	COMPACK_NAME VARCHAR(40) COMMENT '构件包名',
	RES_SHOW_NAME VARCHAR(128) COMMENT '资源显示名称'
) COMMENT = '功能资源对应 : 功能点包含的系统资源内容，如jsp、页面流、逻辑流等资源。
功能点对应实际的代码资源。';


-- 菜单 : 应用菜单表，从逻辑上为某个应用系统中的功能组织为一个有分类，有层级的树结构。
-- UI可根据菜单数据结构，进行界面
CREATE TABLE AC_MENU
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	GUID_APP VARCHAR(128) NOT NULL COMMENT '应用GUID',
	GUID_FUNC VARCHAR(128) COMMENT '功能GUID',
	-- 菜单树上显示的名称，一般同功能名称
	MENU_NAME VARCHAR(40) NOT NULL COMMENT '菜单名称 : 菜单树上显示的名称，一般同功能名称',
	MENU_LABEL VARCHAR(40) NOT NULL COMMENT '菜单显示（中文）',
	-- 业务上对本菜单记录的编码
	MENU_CODE VARCHAR(64) NOT NULL COMMENT '菜单代码 : 业务上对本菜单记录的编码',
	-- 数值取自业务菜单：DICT_YON
	ISLEAF CHAR(1) NOT NULL COMMENT '是否叶子菜单 : 数值取自业务菜单：DICT_YON',
	-- 针对EXT模式提供，例如abf_auth/function/module.xml
	UI_ENTRY VARCHAR(256) COMMENT 'UI入口 : 针对EXT模式提供，例如abf_auth/function/module.xml',
	-- 原类型smalint
	MENU_LEVEL DECIMAL(4) COMMENT '菜单层次 : 原类型smalint',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_PARENTS VARCHAR(128) COMMENT '父菜单GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 本菜单所在菜单树的根节点菜单GUID
	GUID_ROOT VARCHAR(40) COMMENT '根菜单GUID : 本菜单所在菜单树的根节点菜单GUID',
	-- 原类型smalint
	DISPLAY_ORDER DECIMAL(4) COMMENT '显示顺序 : 原类型smalint',
	IMAGE_PATH VARCHAR(256) COMMENT '菜单闭合图片路径',
	EXPAND_PATH VARCHAR(256) COMMENT '菜单展开图片路径',
	-- 类似面包屑导航，可以看出菜单的全路径；
	-- 从应用系统开始，系统自动维护，如： /teller/loan/TX010112
	-- 表示柜面系统（teller）中贷款功能组（loan）中的TX010112功能（交易）
	MENU_SEQ VARCHAR(256) COMMENT '菜单路径序列 : 类似面包屑导航，可以看出菜单的全路径；
从应用系统开始，系统自动维护，如： /teller/loan/TX010112
表示柜面系统（teller）中贷款功能组（loan）中的TX010112功能（交易）',
	-- 数值取自业务菜单： DICT_AC_OPENMODE
	-- 如：主窗口打开、弹出窗口打开...
	OPEN_MODE VARCHAR(255) COMMENT '页面打开方式 : 数值取自业务菜单： DICT_AC_OPENMODE
如：主窗口打开、弹出窗口打开...',
	-- 菜单维护时同步更新
	SUB_COUNT DECIMAL(10) COMMENT '子节点数 : 菜单维护时同步更新',
	PRIMARY KEY (GUID)
) COMMENT = '菜单 : 应用菜单表，从逻辑上为某个应用系统中的功能组织为一个有分类，有层级的树结构。
UI可根据菜单数据结构，进行界面';


-- 操作员 : 系统登录用户表，一个用户只能有一个或零个操作员
CREATE TABLE AC_OPERATOR
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	USER_ID VARCHAR(64) NOT NULL COMMENT '登录用户名',
	PASSWORD VARCHAR(100) COMMENT '密码',
	-- 记录当前操作员姓名（只记录当前值，不随之改变）
	OPERATOR_NAME VARCHAR(64) COMMENT '操作员姓名 : 记录当前操作员姓名（只记录当前值，不随之改变）',
	-- 取值来自业务菜单：DICT_AC_OPERATOR_STATUS
	-- 正常，挂起，注销，锁定...
	-- 系统处理状态间的流转
	OPERATOR_STATUS VARCHAR(255) NOT NULL COMMENT '操作员状态 : 取值来自业务菜单：DICT_AC_OPERATOR_STATUS
正常，挂起，注销，锁定...
系统处理状态间的流转',
	-- 指定失效时间具体到时分秒
	INVAL_DATE TIMESTAMP COMMENT '密码失效日期 : 指定失效时间具体到时分秒',
	-- 取值来自业务菜单：DICT_AC_AUTHMODE
	-- 如：本地密码认证、LDAP认证、等
	-- 可以多选，以逗号分隔，且按照出现先后顺序进行认证；
	-- 如：
	-- pwd,captcha
	-- 表示输入密码，并且还需要验证码
	AUTH_MODE VARCHAR(255) NOT NULL COMMENT '认证模式 : 取值来自业务菜单：DICT_AC_AUTHMODE
如：本地密码认证、LDAP认证、等
可以多选，以逗号分隔，且按照出现先后顺序进行认证；
如：
pwd,captcha
表示输入密码，并且还需要验证码',
	-- 登陆错误超过本数字，系统锁定操作员，默认5次。
	-- 可为操作员单独设置；
	LOCK_LIMIT DECIMAL(4) DEFAULT 5 NOT NULL COMMENT '锁定次数限制 : 登陆错误超过本数字，系统锁定操作员，默认5次。
可为操作员单独设置；',
	ERR_COUNT DECIMAL(10) COMMENT '当前错误登录次数',
	LOCK_TIME TIMESTAMP COMMENT '锁定时间',
	-- 当状态为锁定时，解锁的时间
	UNLOCK_TIME TIMESTAMP COMMENT '解锁时间 : 当状态为锁定时，解锁的时间',
	-- 取值来自业务菜单：DICT_AC_MENUTYPE
	-- 用户登录后菜单的风格
	MENU_TYPE VARCHAR(255) NOT NULL COMMENT '菜单风格 : 取值来自业务菜单：DICT_AC_MENUTYPE
用户登录后菜单的风格',
	LAST_LOGIN TIMESTAMP COMMENT '最近登录时间',
	-- 启用操作员时设置，任何时间可设置；
	START_DATE DATE COMMENT '有效开始日期 : 启用操作员时设置，任何时间可设置；',
	-- 启用操作员时设置，任何时间可设置；
	END_DATE DATE COMMENT '有效截止日期 : 启用操作员时设置，任何时间可设置；',
	-- 定义一个规则表达式，表示允许操作的有效时间范围，格式为：
	-- [{begin:"HH:mm",end:"HH:mm"},{begin:"HH:mm",end:"HH:mm"},...]
	-- 如：
	-- [{begin:"08:00",end:"11:30"},{begin:"14:30",end:"17:00"}]
	-- 表示，该操作员被允许每天有两个时间段进行系统操作，分别 早上08:00 - 11:30，下午14:30 － 17:00 
	VALID_TIME VARCHAR(1024) COMMENT '允许时间范围 : 定义一个规则表达式，表示允许操作的有效时间范围，格式为：
[{begin:"HH:mm",end:"HH:mm"},{begin:"HH:mm",end:"HH:mm"},...]
如：
[{begin:"08:00",end:"11:30"},{begin:"14:30",end:"17:00"}]
表示，该操作员被允许每天有两个时间段进行系统操作，分别 早上08:00 - 11:30，下午14:30 － 17:00 ',
	-- 允许设置多个MAC，以逗号分隔，控制操作员只能在这些机器上登陆。
	MAC_CODE VARCHAR(1024) COMMENT '允许MAC码 : 允许设置多个MAC，以逗号分隔，控制操作员只能在这些机器上登陆。',
	-- 允许设置多个IP地址
	IP_ADDRESS VARCHAR(1024) COMMENT '允许IP地址 : 允许设置多个IP地址',
	PRIMARY KEY (GUID)
) COMMENT = '操作员 : 系统登录用户表，一个用户只能有一个或零个操作员';


-- 操作员特殊功能行为配置 : 配合人员特殊授权配置表一起使用，可设置操作员只有功能的某些行为权限；
-- 特别授权某个功能给操作
CREATE TABLE AC_OPERATOR_BHV
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_OPERATOR VARCHAR(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_FUNC_BHV VARCHAR(128) NOT NULL COMMENT '操作行为GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 取值来自业务菜单：DICT_AC_AUTHTYPE
	-- 如：特别禁止、特别允许
	AUTH_TYPE VARCHAR(255) NOT NULL COMMENT '授权标志 : 取值来自业务菜单：DICT_AC_AUTHTYPE
如：特别禁止、特别允许'
) COMMENT = '操作员特殊功能行为配置 : 配合人员特殊授权配置表一起使用，可设置操作员只有功能的某些行为权限；
特别授权某个功能给操作';


-- 操作员个性配置 : 操作员个性化配置
-- 如颜色配置
--     登录风格
--     是否使用重组菜单
--     默认身份
-- 
CREATE TABLE AC_OPERATOR_CONFIG
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_OPERATOR VARCHAR(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	GUID_APP VARCHAR(128) NOT NULL COMMENT '应用GUID',
	-- 见业务字典： DICT_AC_CONFIGTYPE
	CONFIG_TYPE VARCHAR(64) NOT NULL COMMENT '配置类型 : 见业务字典： DICT_AC_CONFIGTYPE',
	CONFIG_NAME VARCHAR(64) NOT NULL COMMENT '配置名',
	CONFIG_VALUE VARCHAR(1024) COMMENT '配置值',
	-- 见业务菜单： DICT_YON
	ISVALID CHAR(1) NOT NULL COMMENT '是否启用 : 见业务菜单： DICT_YON',
	UNIQUE (GUID_APP, CONFIG_TYPE, CONFIG_NAME)
) COMMENT = '操作员个性配置 : 操作员个性化配置
如颜色配置
    登录风格
    是否使用重组菜单
    默认身份
';


-- 操作员特殊权限配置 : 针对人员配置的特殊权限，如特别开通的功能，或者特别禁止的功能
CREATE TABLE AC_OPERATOR_FUNC
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_OPERATOR VARCHAR(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_FUNC VARCHAR(128) NOT NULL COMMENT '功能GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 取值来自业务菜单：DICT_AC_AUTHTYPE
	-- 如：特别禁止、特别允许
	AUTH_TYPE VARCHAR(255) NOT NULL COMMENT '授权标志 : 取值来自业务菜单：DICT_AC_AUTHTYPE
如：特别禁止、特别允许',
	START_DATE DATE COMMENT '有效开始日期',
	END_DATE DATE COMMENT '有效截至日期',
	-- 冗余字段
	GUID_APP VARCHAR(128) COMMENT '应用GUID : 冗余字段',
	-- 冗余字段
	GUID_FUNCGROUP VARCHAR(128) COMMENT '功能组GUID : 冗余字段'
) COMMENT = '操作员特殊权限配置 : 针对人员配置的特殊权限，如特别开通的功能，或者特别禁止的功能';


-- 操作员身份 : 操作员对自己的权限进行组合形成一个固定的登录身份；
-- 供登录时选项，每一个登录身份是员工操作员的权限子集
CREATE TABLE AC_OPERATOR_IDENTITY
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_OPERATOR VARCHAR(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	IDENTITY_NAME VARCHAR(255) NOT NULL COMMENT '身份名称',
	-- 见业务字典： DICT_YON
	-- 只能有一个默认身份 Y是默认身份 N不是默认身份
	IDENTITY_FLAG CHAR(1) NOT NULL COMMENT '默认身份标志 : 见业务字典： DICT_YON
只能有一个默认身份 Y是默认身份 N不是默认身份',
	SEQ_NO DECIMAL(4) COMMENT '显示顺序',
	PRIMARY KEY (GUID)
) COMMENT = '操作员身份 : 操作员对自己的权限进行组合形成一个固定的登录身份；
供登录时选项，每一个登录身份是员工操作员的权限子集';


-- 身份权限集 : 操作员身份对应的权限子集
-- 可配置内容包括 
-- 角色
-- 组织
CREATE TABLE AC_OPERATOR_IDENTITYRES
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_IDENTITY VARCHAR(128) NOT NULL COMMENT '身份GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 资源：操作员所拥有的权限来源
	-- 见业务字典： DICT_AC_RESOURCETYPE
	-- 表示：角色编号或者组织编号（如机构编号，工作组编号）
	AC_RESOURCETYPE VARCHAR(255) NOT NULL COMMENT '资源类型 : 资源：操作员所拥有的权限来源
见业务字典： DICT_AC_RESOURCETYPE
表示：角色编号或者组织编号（如机构编号，工作组编号）',
	-- 根据资源类型对应到不同权限资源的GUID
	GUID_AC_RESOURCE VARCHAR(128) NOT NULL COMMENT '资源GUID : 根据资源类型对应到不同权限资源的GUID'
) COMMENT = '身份权限集 : 操作员身份对应的权限子集
可配置内容包括 
角色
组织';


-- 操作员重组菜单 : 重组菜单；
-- 操作员对AC_MENU的定制化重组
CREATE TABLE AC_OPERATOR_MENU
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_OPERATOR VARCHAR(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	GUID_APP VARCHAR(128) COMMENT '应用GUID',
	GUID_FUNC VARCHAR(255) COMMENT '功能GUID',
	MENU_NAME VARCHAR(64) NOT NULL COMMENT '菜单名称',
	MENU_LABEL VARCHAR(64) NOT NULL COMMENT '菜单显示（中文）',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_PARENTS VARCHAR(128) COMMENT '父菜单GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	ISLEAF CHAR(1) COMMENT '是否叶子菜单',
	-- 针对EXT模式提供，例如abf_auth/function/module.xml
	UI_ENTRY VARCHAR(256) COMMENT 'UI入口 : 针对EXT模式提供，例如abf_auth/function/module.xml',
	-- 原类型smallint
	MENU_LEVEL DECIMAL(4) COMMENT '菜单层次 : 原类型smallint',
	GUID_ROOT VARCHAR(128) COMMENT '根菜单GUID',
	-- 原类型smallint
	DISPLAY_ORDER DECIMAL(4) COMMENT '显示顺序 : 原类型smallint',
	IMAGE_PATH VARCHAR(256) COMMENT '菜单图片路径',
	EXPAND_PATH VARCHAR(256) COMMENT '菜单展开图片路径',
	MENU_SEQ VARCHAR(256) COMMENT '菜单路径序列',
	-- 数值取自业务菜单： DICT_AC_OPENMODE
	-- 如：主窗口打开、弹出窗口打开...
	OPEN_MODE VARCHAR(255) COMMENT '页面打开方式 : 数值取自业务菜单： DICT_AC_OPENMODE
如：主窗口打开、弹出窗口打开...',
	SUB_COUNT DECIMAL(10) COMMENT '子节点数',
	PRIMARY KEY (GUID)
) COMMENT = '操作员重组菜单 : 重组菜单；
操作员对AC_MENU的定制化重组';


-- 操作员与权限集（角色）对应关系 : 操作员与权限集（角色）对应关系表
CREATE TABLE AC_OPERATOR_ROLE
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_OPERATOR VARCHAR(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ROLE VARCHAR(128) NOT NULL COMMENT '拥有角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 预留字段，暂不使用。意图将操作员所拥有的权限赋予其他操作员。
	AUTH VARCHAR(255) COMMENT '是否可分级授权 : 预留字段，暂不使用。意图将操作员所拥有的权限赋予其他操作员。'
) COMMENT = '操作员与权限集（角色）对应关系 : 操作员与权限集（角色）对应关系表';


-- 操作员快捷菜单 : 用户自定义的快捷菜单（以应用系统进行区分）；
-- 快捷菜单中的功能可在快捷菜单面板中点击启动，也可通过对
CREATE TABLE AC_OPERATOR_SHORTCUT
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_OPERATOR VARCHAR(128) NOT NULL COMMENT '操作员GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	GUID_FUNC VARCHAR(128) NOT NULL COMMENT '功能GUID',
	-- 冗余字段，方便为快捷键分组
	GUID_FUNCGROUP VARCHAR(128) NOT NULL COMMENT '功能组GUID : 冗余字段，方便为快捷键分组',
	-- 冗余字段，方便为快捷键分组
	GUID_APP VARCHAR(128) NOT NULL COMMENT '应用GUID : 冗余字段，方便为快捷键分组',
	-- 原类型smallint
	ORDER_NO DECIMAL(4) NOT NULL COMMENT '排列顺序 : 原类型smallint',
	IMAGE_PATH VARCHAR(128) COMMENT '快捷菜单图片路径',
	-- 如：CTRL+1 表示启动TX010505，本字段记录 CTRL+1 这个信息
	SHORTCUT_KEY VARCHAR(255) COMMENT '快捷按键 : 如：CTRL+1 表示启动TX010505，本字段记录 CTRL+1 这个信息'
) COMMENT = '操作员快捷菜单 : 用户自定义的快捷菜单（以应用系统进行区分）；
快捷菜单中的功能可在快捷菜单面板中点击启动，也可通过对';


-- 组织对象与角色对应关系 : 设置机构、工作组、岗位、职务等组织对象与角色之间的对应关系
CREATE TABLE AC_PARTY_ROLE
(
	-- 取值范围，见业务字典 DICT_OM_PARTYTYPE
	-- 如：机构、工作组、岗位、职务
	PARTY_TYPE VARCHAR(255) NOT NULL COMMENT '组织对象类型 : 取值范围，见业务字典 DICT_OM_PARTYTYPE
如：机构、工作组、岗位、职务',
	-- 根据组织类型存储对应组织的GUID
	GUID_PARTY VARCHAR(128) NOT NULL COMMENT '组织对象GUID : 根据组织类型存储对应组织的GUID',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ROLE VARCHAR(128) NOT NULL COMMENT '拥有角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；'
) COMMENT = '组织对象与角色对应关系 : 设置机构、工作组、岗位、职务等组织对象与角色之间的对应关系';


-- 权限集(角色) : 权限集（角色）定义表
CREATE TABLE AC_ROLE
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	GUID_APP VARCHAR(128) NOT NULL COMMENT '隶属应用GUID',
	-- 业务上对角色的编码
	ROLE_CODE VARCHAR(64) NOT NULL COMMENT '角色代码 : 业务上对角色的编码',
	ROLE_NAME VARCHAR(128) NOT NULL COMMENT '角色名称',
	-- 取值范围见 DICT_AC_ROLETYPE
	ROLE_TYPE VARCHAR(255) NOT NULL COMMENT '角色类别 : 取值范围见 DICT_AC_ROLETYPE',
	ROLE_DESC VARCHAR(512) COMMENT '角色描述',
	PRIMARY KEY (GUID),
	UNIQUE (ROLE_CODE)
) COMMENT = '权限集(角色) : 权限集（角色）定义表';


-- 角色数据范围权限对应 : 配置角色具有的数据权限。
-- 说明角色拥有某个实体数据中哪些范围的操作权。
CREATE TABLE AC_ROLE_DATASCOPE
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ROLE VARCHAR(128) NOT NULL COMMENT '角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_DATASCOPE VARCHAR(128) NOT NULL COMMENT '拥有数据范围GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；'
) COMMENT = '角色数据范围权限对应 : 配置角色具有的数据权限。
说明角色拥有某个实体数据中哪些范围的操作权。';


-- 角色实体关系 : 角色与数据实体的对应关系。
-- 说明角色拥有哪些实体操作权。
CREATE TABLE AC_ROLE_ENTITY
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ROLE VARCHAR(128) NOT NULL COMMENT '角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ENTITY VARCHAR(128) NOT NULL COMMENT '拥有实体GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 取值来自业务菜单： DICT_YON
	ISADD CHAR(1) DEFAULT 'N' NOT NULL COMMENT '可增加 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	ISDEL CHAR(1) DEFAULT 'N' NOT NULL COMMENT '可删除 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	ISMODIFY CHAR(1) DEFAULT 'N' NOT NULL COMMENT '可修改 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	ISVIEW CHAR(1) DEFAULT 'Y' NOT NULL COMMENT '可查看 : 取值来自业务菜单： DICT_YON'
) COMMENT = '角色实体关系 : 角色与数据实体的对应关系。
说明角色拥有哪些实体操作权。';


-- 角色与实体属性关系 : 角色与实体字段（属性）的对应关系。
-- 说明某个角色拥有哪些属性的操作权。
CREATE TABLE AC_ROLE_ENTITYFIELD
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ROLE VARCHAR(128) NOT NULL COMMENT '角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ENTITYFIELD VARCHAR(128) NOT NULL COMMENT '拥有实体属性GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 取值来自业务菜单： DICT_YON
	ISMODIFY CHAR(1) DEFAULT 'N' NOT NULL COMMENT '可修改 : 取值来自业务菜单： DICT_YON',
	-- 取值来自业务菜单： DICT_YON
	ISVIEW CHAR(1) DEFAULT 'Y' NOT NULL COMMENT '可查看 : 取值来自业务菜单： DICT_YON'
) COMMENT = '角色与实体属性关系 : 角色与实体字段（属性）的对应关系。
说明某个角色拥有哪些属性的操作权。';


-- 权限集(角色)功能对应关系 : 角色所包含的功能清单
CREATE TABLE AC_ROLE_FUNC
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ROLE VARCHAR(128) NOT NULL COMMENT '角色GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_FUNC VARCHAR(128) NOT NULL COMMENT '拥有功能GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 冗余字段
	GUID_APP VARCHAR(128) NOT NULL COMMENT '应用GUID : 冗余字段',
	-- 冗余字段
	GUID_FUNCGROUP VARCHAR(128) NOT NULL COMMENT '功能组GUID : 冗余字段'
) COMMENT = '权限集(角色)功能对应关系 : 角色所包含的功能清单';


-- 业务机构 : 业务机构是以业务视角来对机构进行分类分组，每个业务视角称为“业务套别”或者“业务条线”，
-- 作为业务处理的机
CREATE TABLE OM_BUSIORG
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 业务字典 DICT_OM_NODETYPE
	-- 该业务机构的节点类型，虚拟节点，机构节点，如果是机构节点，则对应机构信息表的一个机构
	NODE_TYPE VARCHAR(255) NOT NULL COMMENT '节点类型 : 业务字典 DICT_OM_NODETYPE
该业务机构的节点类型，虚拟节点，机构节点，如果是机构节点，则对应机构信息表的一个机构',
	-- 业务上对业务机构的编码
	BUSIORG_CODE VARCHAR(64) NOT NULL COMMENT '业务机构代码 : 业务上对业务机构的编码',
	-- 取值范围业务菜单 DICT_OM_BUSIDOMAIN
	BUSI_DOMAIN VARCHAR(255) NOT NULL COMMENT '业务条线 : 取值范围业务菜单 DICT_OM_BUSIDOMAIN',
	BUSIORG_NAME VARCHAR(64) NOT NULL COMMENT '业务机构名称',
	BUSIORG_LEVEL DECIMAL(4) NOT NULL COMMENT '业务机构层次',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ORG VARCHAR(128) COMMENT '对应实体机构GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_PARENTS VARCHAR(128) COMMENT '父业务机构GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	GUID_POSITION VARCHAR(128) COMMENT '主管岗位',
	ORG_CODE VARCHAR(32) COMMENT '机构代号',
	-- 业务机构的面包屑导航信息
	SEQNO VARCHAR(256) COMMENT '序列号 : 业务机构的面包屑导航信息',
	SORTNO DECIMAL(4) COMMENT '排列顺序编号',
	-- 见业务字典： DICT_YON
	ISLEAF CHAR(1) NOT NULL COMMENT '是否叶子节点 : 见业务字典： DICT_YON',
	SUB_COUNT DECIMAL(10) NOT NULL COMMENT '子节点数',
	PRIMARY KEY (GUID),
	UNIQUE (BUSIORG_CODE)
) COMMENT = '业务机构 : 业务机构是以业务视角来对机构进行分类分组，每个业务视角称为“业务套别”或者“业务条线”，
作为业务处理的机';


-- 职务定义表 : 职务及responsiblity。定义职务及上下级关系（可以把“职务”理解为岗位的岗位类型，岗位是在机构
CREATE TABLE OM_DUTY
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	DUTY_CODE VARCHAR(64) NOT NULL COMMENT '职务代码',
	DUTY_NAME VARCHAR(128) NOT NULL COMMENT '职务名称',
	-- 见业务字典： DICT_OM_DUTYTYPE
	-- 例如科技类，审计类等
	DUTY_TYPE VARCHAR(255) NOT NULL COMMENT '职务套别 : 见业务字典： DICT_OM_DUTYTYPE
例如科技类，审计类等',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_PARENTS VARCHAR(128) NOT NULL COMMENT '父职务GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 取值来自业务菜单：DICT_YON
	ISLEAF CHAR(10) NOT NULL COMMENT '是否叶子节点 : 取值来自业务菜单：DICT_YON',
	SUB_COUNT DECIMAL(10) NOT NULL COMMENT '子节点数',
	DUTY_LEVEL DECIMAL COMMENT '职务层次',
	-- 职务的面包屑定位信息
	-- 
	DUTY_SEQ VARCHAR(256) COMMENT '职务序列 : 职务的面包屑定位信息
',
	REMARK VARCHAR(256) COMMENT '备注',
	PRIMARY KEY (GUID),
	UNIQUE (DUTY_CODE)
) COMMENT = '职务定义表 : 职务及responsiblity。定义职务及上下级关系（可以把“职务”理解为岗位的岗位类型，岗位是在机构';


-- 员工 : 人员信息表
-- 人员至少隶属于一个机构；
-- 本表记录了：人员基本信息，人员联系信息，人员在机构中的信息，人员对应的操
CREATE TABLE OM_EMPLOYEE
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	EMP_CODE VARCHAR(32) NOT NULL COMMENT '员工代码',
	EMP_NAME VARCHAR(50) NOT NULL COMMENT '员工姓名',
	EMP_REALNAME VARCHAR(50) COMMENT '员工全名',
	-- 见业务菜单：DICT_OM_GENDER
	GENDER VARCHAR(255) NOT NULL COMMENT '性别 : 见业务菜单：DICT_OM_GENDER',
	-- 见业务字典： DICT_OM_EMPSTATUS
	EMPSTATUS VARCHAR(255) NOT NULL COMMENT '员工状态 : 见业务字典： DICT_OM_EMPSTATUS',
	-- 见业务字典： DICT_OM_EMPDEGREE
	EMP_DEGREE VARCHAR(255) NOT NULL COMMENT '员工职级 : 见业务字典： DICT_OM_EMPDEGREE',
	-- 人员所属主机构编号（冗余设计）
	GUID_ORG VARCHAR(128) NOT NULL COMMENT '主机构编号 : 人员所属主机构编号（冗余设计）',
	GUID_POSITION VARCHAR(128) NOT NULL COMMENT '基本岗位',
	GUID_EMP_MAJOR VARCHAR(128) COMMENT '直接主管',
	INDATE DATE COMMENT '入职日期',
	OUTDATE DATE COMMENT '离职日期',
	OTEL VARCHAR(12) COMMENT '办公电话',
	OADDRESS VARCHAR(255) COMMENT '办公地址',
	-- 见业务字典： DICT_SD_ZIPCODE
	OZIPCODE VARCHAR(10) COMMENT '办公邮编 : 见业务字典： DICT_SD_ZIPCODE',
	OEMAIL VARCHAR(128) COMMENT '办公邮件',
	FAXNO VARCHAR(14) COMMENT '传真号码',
	MOBILENO VARCHAR(14) COMMENT '手机号码',
	MSN VARCHAR(16) COMMENT 'MSN号码',
	-- 见业务字典： DICT_SD_PAPERTYPE
	PAPER_TYPE VARCHAR(255) COMMENT '证件类型 : 见业务字典： DICT_SD_PAPERTYPE',
	PAPER_NO VARCHAR(64) COMMENT '证件号码',
	BIRTHDATE DATE COMMENT '出生日期',
	HTEL VARCHAR(12) COMMENT '家庭电话',
	HADDRESS VARCHAR(128) COMMENT '家庭地址',
	-- 见业务字典： DICT_SD_ZIPCODE
	HZIPCODE VARCHAR(10) COMMENT '家庭邮编 : 见业务字典： DICT_SD_ZIPCODE',
	-- 见业务字典： DICT_SD_PARTY
	PARTY VARCHAR(255) COMMENT '政治面貌 : 见业务字典： DICT_SD_PARTY',
	PEMAIL VARCHAR(128) COMMENT '私人电子邮箱',
	GUID_OPERATOR VARCHAR(128) COMMENT '操作员编号',
	-- 登陆用户id
	USER_ID VARCHAR(64) COMMENT '操作员 : 登陆用户id',
	-- 限定了该人员对应的操作员登陆系统时，可为其他操作员分配角色的范围；
	-- 可选内容来自角色表（AC_ROLE），json数组形式，如： [{roleid:"444555"},{roleid:"999888"},....]
	SPECIALTY VARCHAR(1024) COMMENT '可授权角色 : 限定了该人员对应的操作员登陆系统时，可为其他操作员分配角色的范围；
可选内容来自角色表（AC_ROLE），json数组形式，如： [{roleid:"444555"},{roleid:"999888"},....]',
	-- 限定了本人员对应的操作员可维护哪些机构信息（机构，人员等与机构关联的信息），json数组形式，如：
	-- [{orgid:"123"},{orgid:"456"},....]
	-- 如果为空，则表示无任何机构的管理权限
	ORG_LIST VARCHAR(1024) COMMENT '可管理机构 : 限定了本人员对应的操作员可维护哪些机构信息（机构，人员等与机构关联的信息），json数组形式，如：
[{orgid:"123"},{orgid:"456"},....]
如果为空，则表示无任何机构的管理权限',
	WORKEXP VARCHAR(512) COMMENT '工作描述',
	REMARK VARCHAR(512) COMMENT '备注',
	-- 首次新增人员记录数据的日期
	REGDATE DATE COMMENT '注册日期 : 首次新增人员记录数据的日期',
	CREATETIME TIMESTAMP COMMENT '创建时间',
	LASTMODYTIME TIMESTAMP COMMENT '最新更新时间',
	PRIMARY KEY (GUID),
	UNIQUE (EMP_CODE)
) COMMENT = '员工 : 人员信息表
人员至少隶属于一个机构；
本表记录了：人员基本信息，人员联系信息，人员在机构中的信息，人员对应的操';


-- 人员工作组对应关系 : 定义工作组包含的人员（工作组中有哪些人员）
-- 如：某个项目组有哪些人员
CREATE TABLE OM_EMP_GROUP
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_EMP VARCHAR(128) NOT NULL COMMENT '员工GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_GROUP VARCHAR(128) NOT NULL COMMENT '隶属工作组GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；'
) COMMENT = '人员工作组对应关系 : 定义工作组包含的人员（工作组中有哪些人员）
如：某个项目组有哪些人员';


-- 员工隶属机构关系表 : 定义人员和机构的关系表（机构有哪些人员）。
-- 允许一个人员同时在多个机构，但是只能有一个主机构。
CREATE TABLE OM_EMP_ORG
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_EMP VARCHAR(128) NOT NULL COMMENT '员工GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ORG VARCHAR(128) NOT NULL COMMENT '隶属机构GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 取值来自业务菜单： DICT_YON
	-- 必须有且只能有一个主机构，默认N，人员管理时程序检查当前是否只有一条主机构；
	ISMAIN CHAR(1) DEFAULT 'N' NOT NULL COMMENT '是否主机构 : 取值来自业务菜单： DICT_YON
必须有且只能有一个主机构，默认N，人员管理时程序检查当前是否只有一条主机构；'
) COMMENT = '员工隶属机构关系表 : 定义人员和机构的关系表（机构有哪些人员）。
允许一个人员同时在多个机构，但是只能有一个主机构。';


-- 员工岗位对应关系 : 定义人员和岗位的对应关系，需要注明，一个人员可以设定一个基本岗位
CREATE TABLE OM_EMP_POSITION
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_EMP VARCHAR(128) NOT NULL COMMENT '员工GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_POSITION VARCHAR(128) NOT NULL COMMENT '所在岗位GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 取值来自业务菜单：DICT_YON
	-- 只能有一个主岗位
	ISMAIN CHAR(1) NOT NULL COMMENT '是否主岗位 : 取值来自业务菜单：DICT_YON
只能有一个主岗位'
) COMMENT = '员工岗位对应关系 : 定义人员和岗位的对应关系，需要注明，一个人员可以设定一个基本岗位';


-- 工作组 : 工作组定义表，用于定义临时组、虚拟组，跨部门的项目组等。
-- 工作组实质上与机构类似，是为了将项目组、工作组等临
CREATE TABLE OM_GROUP
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 业务上对工作组的编码
	GROUP_CODE VARCHAR(64) NOT NULL COMMENT '工作组代码 : 业务上对工作组的编码',
	GROUP_NAME VARCHAR(50) NOT NULL COMMENT '工作组名称',
	-- 见业务字典： DICT_OM_GROUPTYPE
	GROUP_TYPE VARCHAR(255) NOT NULL COMMENT '工作组类型 : 见业务字典： DICT_OM_GROUPTYPE',
	-- 见业务字典： DICT_OM_GROUPSTATUS
	GROUP_STATUS VARCHAR(255) NOT NULL COMMENT '工作组状态 : 见业务字典： DICT_OM_GROUPSTATUS',
	GROUP_DESC VARCHAR(512) COMMENT '工作组描述',
	-- 选择范围来自 OM_EMPLOYEE表
	GUID_EMP_MANAGER VARCHAR(128) COMMENT '负责人 : 选择范围来自 OM_EMPLOYEE表',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ORG VARCHAR(128) NOT NULL COMMENT '隶属机构GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_PARENTS VARCHAR(128) COMMENT '父工作组GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 见业务菜单： DICT_YON
	ISLEAF CHAR(1) NOT NULL COMMENT '是否叶子节点 : 见业务菜单： DICT_YON',
	SUB_COUNT DECIMAL(10) NOT NULL COMMENT '子节点数',
	GROUP_LEVEL DECIMAL(4) COMMENT '工作组层次',
	-- 本工作组的面包屑定位信息
	GROUP_SEQ VARCHAR(256) COMMENT '工作组序列 : 本工作组的面包屑定位信息',
	START_DATE DATE COMMENT '工作组有效开始日期',
	END_DATE DATE COMMENT '工作组有效截止日期',
	CREATETIME TIMESTAMP COMMENT '创建时间',
	LASTUPDATE TIMESTAMP COMMENT '最近更新时间',
	UPDATOR VARCHAR(128) COMMENT '最近更新人员',
	PRIMARY KEY (GUID),
	UNIQUE (GROUP_CODE)
) COMMENT = '工作组 : 工作组定义表，用于定义临时组、虚拟组，跨部门的项目组等。
工作组实质上与机构类似，是为了将项目组、工作组等临';


-- 工作组应用列表 : 工作组所拥有（允许操作）的应用列表
CREATE TABLE OM_GROUP_APP
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_GROUP VARCHAR(128) NOT NULL COMMENT '工作组GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	GUID_APP VARCHAR(128) NOT NULL COMMENT '应用GUID'
) COMMENT = '工作组应用列表 : 工作组所拥有（允许操作）的应用列表';


-- 工作组岗位列表 : 工作组岗位列表:一个工作组允许定义多个岗位，岗位之间允许存在层次关系
CREATE TABLE OM_GROUP_POSITION
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_GROUP VARCHAR(128) NOT NULL COMMENT '工作组GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_POSITION VARCHAR(128) NOT NULL COMMENT '岗位GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；'
) COMMENT = '工作组岗位列表 : 工作组岗位列表:一个工作组允许定义多个岗位，岗位之间允许存在层次关系';


-- 机构信息表 : 机构部门（Organization）表
-- 允许定义多个平行机构
CREATE TABLE OM_ORG
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 业务上对机构实体的编码。
	-- 一般根据机构等级和机构类型进行有规则的编码。
	ORG_CODE VARCHAR(32) NOT NULL COMMENT '机构代码 : 业务上对机构实体的编码。
一般根据机构等级和机构类型进行有规则的编码。',
	ORG_NAME VARCHAR(64) NOT NULL COMMENT '机构名称',
	-- 见业务字典： DICT_OM_ORGTYPE
	-- 如：总公司/总部部门/分公司/分公司部门...
	ORG_TYPE VARCHAR(12) NOT NULL COMMENT '机构类型 : 见业务字典： DICT_OM_ORGTYPE
如：总公司/总部部门/分公司/分公司部门...',
	-- 见业务字典： DICT_OM_ORGDEGREE
	-- 如：总行，分行，海外分行...
	ORG_DEGREE VARCHAR(255) NOT NULL COMMENT '机构等级 : 见业务字典： DICT_OM_ORGDEGREE
如：总行，分行，海外分行...',
	-- 见业务字典： DICT_OM_ORGSTATUS
	ORG_STATUS VARCHAR(255) NOT NULL COMMENT '机构状态 : 见业务字典： DICT_OM_ORGSTATUS',
	ORG_LEVEL DECIMAL(2) DEFAULT 1 COMMENT '机构层次',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_PARENTS VARCHAR(128) COMMENT '父机构GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 类似面包屑导航，以“.”分割所有父机构GUID，明确示意出本机构所处层级归属
	-- 格式： 父机构GUID.父机构GUID....本机构GUID
	ORG_SEQ VARCHAR(512) COMMENT '机构序列 : 类似面包屑导航，以“.”分割所有父机构GUID，明确示意出本机构所处层级归属
格式： 父机构GUID.父机构GUID....本机构GUID',
	ORG_ADDR VARCHAR(256) COMMENT '机构地址',
	-- 见业务字典： DICT_SD_ZIPCODE
	ZIPCODE VARCHAR(10) COMMENT '邮编 : 见业务字典： DICT_SD_ZIPCODE',
	GUID_POSITION VARCHAR(128) COMMENT '机构主管岗位GUID',
	GUID_EMP_MASTER VARCHAR(128) COMMENT '机构主管人员GUID',
	-- 机构管理员能够给本机构的人员进行授权，多个机构管理员之间用,分隔
	GUID_EMP_MANAGER VARCHAR(128) COMMENT '机构管理员GUID : 机构管理员能够给本机构的人员进行授权，多个机构管理员之间用,分隔',
	LINK_MAN VARCHAR(30) COMMENT '联系人姓名',
	LINK_TEL VARCHAR(20) COMMENT '联系电话',
	EMAIL VARCHAR(128) COMMENT '电子邮件',
	WEB_URL VARCHAR(512) COMMENT '网站地址',
	START_DATE DATE COMMENT '生效日期',
	END_DATE DATE COMMENT '失效日期',
	-- 见业务字典： DICT_SD_AREA
	AREA VARCHAR(30) COMMENT '所属地域 : 见业务字典： DICT_SD_AREA',
	CREATE_TIME TIMESTAMP COMMENT '创建时间',
	LAST_UPDATE TIMESTAMP COMMENT '最近更新时间',
	UPDATOR VARCHAR(128) COMMENT '最近更新人员',
	-- 维护时，可手工指定从0开始的自然数字；如果为空，系统将按照机构代码排序。
	SORT_NO DECIMAL(4) COMMENT '排列顺序编号 : 维护时，可手工指定从0开始的自然数字；如果为空，系统将按照机构代码排序。',
	-- 系统根据当前是否有下级机构判断更新（见业务字典 DICT_YON）
	ISLEAF CHAR(1) NOT NULL COMMENT '是否叶子节点 : 系统根据当前是否有下级机构判断更新（见业务字典 DICT_YON）',
	-- 维护时系统根据当前拥有子机构／部分数实时更新
	SUB_COUNT DECIMAL(10) COMMENT '子节点数 : 维护时系统根据当前拥有子机构／部分数实时更新',
	REMARK VARCHAR(512) COMMENT '备注',
	PRIMARY KEY (GUID),
	UNIQUE (ORG_CODE)
) COMMENT = '机构信息表 : 机构部门（Organization）表
允许定义多个平行机构';


-- 岗位 : 岗位定义表
-- 岗位是职务在机构上的实例化表现（某个机构／部门中对某个职务（Responsibility）的工作定
CREATE TABLE OM_POSITION
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_ORG VARCHAR(128) NOT NULL COMMENT '隶属机构GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 业务上对岗位的编码
	POSITION_CODE VARCHAR(64) NOT NULL COMMENT '岗位代码 : 业务上对岗位的编码',
	POSITION_NAME VARCHAR(128) NOT NULL COMMENT '岗位名称',
	-- 见业务字典： DICT_OM_POSITYPE
	-- 机构岗位，工作组岗位
	POSITION_TYPE VARCHAR(255) NOT NULL COMMENT '岗位类别 : 见业务字典： DICT_OM_POSITYPE
机构岗位，工作组岗位',
	-- 见业务字典： DICT_OM_POSISTATUS
	POSITION_STATUS VARCHAR(255) NOT NULL COMMENT '岗位状态 : 见业务字典： DICT_OM_POSISTATUS',
	-- 见业务字典： DICT_YON
	ISLEAF CHAR(1) NOT NULL COMMENT '是否叶子岗位 : 见业务字典： DICT_YON',
	SUB_COUNT DECIMAL(10) NOT NULL COMMENT '子节点数',
	POSITION_LEVEL DECIMAL(2) COMMENT '岗位层次',
	-- 岗位的面包屑定位信息
	POSITION_SEQ VARCHAR(512) COMMENT '岗位序列 : 岗位的面包屑定位信息',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_PARENTS VARCHAR(128) COMMENT '父岗位GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_DUTY VARCHAR(128) NOT NULL COMMENT '所属职务GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	START_DATE DATE COMMENT '岗位有效开始日期',
	END_DATE DATE COMMENT '岗位有效截止日期',
	CREATETIME TIMESTAMP COMMENT '创建时间',
	LASTUPDATE TIMESTAMP COMMENT '最近更新时间',
	UPDATOR VARCHAR(128) COMMENT '最近更新人员',
	PRIMARY KEY (GUID),
	UNIQUE (POSITION_CODE)
) COMMENT = '岗位 : 岗位定义表
岗位是职务在机构上的实例化表现（某个机构／部门中对某个职务（Responsibility）的工作定';


-- 岗位应用列表 : 岗位所拥有（允许操作）的应用列表信息
CREATE TABLE OM_POSITION_APP
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_POSITION VARCHAR(128) NOT NULL COMMENT '岗位GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	GUID_APP VARCHAR(128) NOT NULL COMMENT '应用GUID'
) COMMENT = '岗位应用列表 : 岗位所拥有（允许操作）的应用列表信息';


-- 渠道参数控制表
CREATE TABLE SYS_CHANNEL_CTL
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 记录接触系统对应的渠道代码
	CHN_CODE VARCHAR(128) NOT NULL COMMENT '渠道代码 : 记录接触系统对应的渠道代码',
	CHN_NAME VARCHAR(256) NOT NULL COMMENT '渠道名称',
	CHN_REMARK VARCHAR(1024) COMMENT '渠道备注信息',
	PRIMARY KEY (GUID),
	UNIQUE (GUID),
	UNIQUE (CHN_CODE)
) COMMENT = '渠道参数控制表';


-- 业务字典 : 业务字典表，定义系统中下拉菜单的数据（注意：仅仅包括下拉菜单中的数据，而不包括下拉菜单样式，是否多选这些与
CREATE TABLE SYS_DICT
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	DICT_KEY VARCHAR(128) NOT NULL COMMENT '业务字典',
	-- 见业务字典： DICT_TYPE
	-- a 应用级（带业务含义的业务字典，应用开发时可扩展）
	-- s 系统级（平台自己的业务字典）
	DICT_TYPE CHAR(1) NOT NULL COMMENT '类型 : 见业务字典： DICT_TYPE
a 应用级（带业务含义的业务字典，应用开发时可扩展）
s 系统级（平台自己的业务字典）',
	DICT_NAME VARCHAR(128) NOT NULL COMMENT '字典名称',
	DICT_DESC VARCHAR(512) COMMENT '解释说明',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_PARENTS VARCHAR(128) COMMENT '父字典GUID : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 指定某个字典项（ITEM_VALUE）为本业务字典的默认值（用于扶助View层实现展示默认值）
	DEFAULT_VALUE VARCHAR(512) COMMENT '业务字典默认值 : 指定某个字典项（ITEM_VALUE）为本业务字典的默认值（用于扶助View层实现展示默认值）',
	-- 如果业务字典用来描述某个表中的字段选项，则本字段保存表名；
	-- 其他情况默认为空；
	FROM_TABLE VARCHAR(512) COMMENT '字典项来源表 : 如果业务字典用来描述某个表中的字段选项，则本字段保存表名；
其他情况默认为空；',
	-- 如果业务字典用来描述某个表中的字段选项，则本字段保存字段名；
	-- 其他情况默认为空；
	USE_FOR_KEY VARCHAR(512) COMMENT '作为字典项的列 : 如果业务字典用来描述某个表中的字段选项，则本字段保存字段名；
其他情况默认为空；',
	USE_FOR_NAME VARCHAR(512) COMMENT '作为字典项名称的列',
	-- 顺序号，从0开始排，按小到大排序
	SEQNO DECIMAL(12) COMMENT '顺序号 : 顺序号，从0开始排，按小到大排序',
	SQL_FILTER VARCHAR(512) COMMENT '过滤条件',
	-- 来源类型:0:来自字典项 1:来自单表  2:多表或视图
	FROM_TYPE CHAR(1) COMMENT '字典项来源类型 : 来源类型:0:来自字典项 1:来自单表  2:多表或视图',
	PRIMARY KEY (GUID),
	UNIQUE (DICT_KEY)
) COMMENT = '业务字典 : 业务字典表，定义系统中下拉菜单的数据（注意：仅仅包括下拉菜单中的数据，而不包括下拉菜单样式，是否多选这些与';


-- 业务字典项 : 业务字典内容项， 展示下拉菜单结构时，一般需要： 字典项，字典项名称，实际值
CREATE TABLE SYS_DICT_ITEM
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID_DICT VARCHAR(128) NOT NULL COMMENT '隶属业务字典 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	ITEM_NAME VARCHAR(128) NOT NULL COMMENT '字典项名称',
	-- 来自 dict 字典、value 实际值
	ITEM_TYPE VARCHAR(128) NOT NULL COMMENT '字典项类型 : 来自 dict 字典、value 实际值',
	ITEM_VALUE VARCHAR(128) NOT NULL COMMENT '字典项',
	-- 实际值，及选中字典项后，实际发送值给系统的数值。
	SEND_VALUE VARCHAR(128) NOT NULL COMMENT '实际值 : 实际值，及选中字典项后，实际发送值给系统的数值。',
	-- 顺序号，从0开始排，按小到大排序
	SEQNO DECIMAL(12) COMMENT '顺序号 : 顺序号，从0开始排，按小到大排序',
	PRIMARY KEY (GUID)
) COMMENT = '业务字典项 : 业务字典内容项， 展示下拉菜单结构时，一般需要： 字典项，字典项名称，实际值';


-- 错误码表 : 记录系统中的各种错误代码信息，如系统抛出的错误信息，交易执行时的错误码等
CREATE TABLE SYS_ERR_CODE
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 见业务字典： DICT_ERRCODE_KIND
	-- SYS 系统错误码
	-- TRANS 交易错误码
	-- 
	ERRCODE_KIND CHAR(8) COMMENT '错误代码分类 : 见业务字典： DICT_ERRCODE_KIND
SYS 系统错误码
TRANS 交易错误码
',
	ERR_CODE VARCHAR(32) COMMENT '错误代码',
	ERR_MSG VARCHAR(256) COMMENT '错误信息',
	PRIMARY KEY (GUID),
	UNIQUE (GUID)
) COMMENT = '错误码表 : 记录系统中的各种错误代码信息，如系统抛出的错误信息，交易执行时的错误码等';


-- 操作日志 : 记录操作员使用系统的操作日志（交易操作日志另见： LOG_TX_TRACE）
CREATE TABLE SYS_OPERATOR_LOG
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 见业务字典：DICT_OPERATOR_TYPE
	OPERATOR_TYPE VARCHAR(64) COMMENT '操作类型 : 见业务字典：DICT_OPERATOR_TYPE',
	OPERATOR_TIME TIMESTAMP COMMENT '操作时间',
	-- 见业务字典：DICT_OPERATOR_RESULT
	OPERATOR_RESULT VARCHAR(255) COMMENT '操作结果 : 见业务字典：DICT_OPERATOR_RESULT',
	-- 记录当前操作员姓名（只记录当前值，不随之改变）
	OPERATOR_NAME VARCHAR(64) COMMENT '操作员姓名 : 记录当前操作员姓名（只记录当前值，不随之改变）',
	-- 登陆用户id
	USER_ID VARCHAR(64) COMMENT '操作员 : 登陆用户id',
	APP_CODE VARCHAR(64) COMMENT '应用代码',
	APP_NAME VARCHAR(128) COMMENT '应用名称',
	-- 业务上对功能的编码
	FUNC_CODE VARCHAR(64) COMMENT '功能编号 : 业务上对功能的编码',
	FUNC_NAME VARCHAR(128) COMMENT '功能名称',
	-- 功能对应的RESTFul服务地址
	RESTFUL_RUL VARCHAR(512) COMMENT '服务地址 : 功能对应的RESTFul服务地址',
	-- 记录异常堆栈信息，超过4000的部分被自动丢弃
	STACK_TRACE VARCHAR(4000) COMMENT '异常堆栈 : 记录异常堆栈信息，超过4000的部分被自动丢弃',
	-- 记录功能执行时的业务处理信息
	PROCSS_DESC VARCHAR(1024) COMMENT '处理描述 : 记录功能执行时的业务处理信息',
	PRIMARY KEY (GUID)
) COMMENT = '操作日志 : 记录操作员使用系统的操作日志（交易操作日志另见： LOG_TX_TRACE）';


-- 系统运行参数表 : 运行期系统参数表，以三段式结构进行参数存储
CREATE TABLE SYS_RUN_CONFIG
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；
	GUID VARCHAR(128) NOT NULL COMMENT '数据主键 : 全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成；',
	-- 用于表识一组参数属于某个应用系统 。下拉AC_APP表记录
	GUID_APP VARCHAR(64) NOT NULL COMMENT '应用系统GUID : 用于表识一组参数属于某个应用系统 。下拉AC_APP表记录',
	-- 参数组别，手工输入
	GROUP_NAME VARCHAR(64) NOT NULL COMMENT '参数组别 : 参数组别，手工输入',
	-- 参数键名称，手工输入
	KEY_NAME VARCHAR(64) NOT NULL COMMENT '参数键 : 参数键名称，手工输入',
	-- H：手工指定
	-- 或者选择业务字典的GUID（此时存储业务字典名称 SYS_DICT.DICT_KEY)
	VALUE_FROM VARCHAR(128) NOT NULL COMMENT '值来源类型 : H：手工指定
或者选择业务字典的GUID（此时存储业务字典名称 SYS_DICT.DICT_KEY)',
	-- 当value_from为H时，手工输入任意有效字符串；
	-- 当value_from为业务字典时，下拉选择；
	VALUE VARCHAR(1024) NOT NULL COMMENT '参数值 : 当value_from为H时，手工输入任意有效字符串；
当value_from为业务字典时，下拉选择；',
	-- 参数功能描述
	DESCRIPTION VARCHAR(128) COMMENT '参数描述 : 参数功能描述',
	PRIMARY KEY (GUID)
) COMMENT = '系统运行参数表 : 运行期系统参数表，以三段式结构进行参数存储';


-- 序号资源表 : 每个SEQ_KEY表示一个序号资源，顺序增加使用序号。
CREATE TABLE SYS_SEQNO
(
	SEQ_KEY VARCHAR(128) NOT NULL COMMENT '序号键值',
	-- 顺序增加的数字
	SEQ_NO DECIMAL(20) DEFAULT 0 NOT NULL COMMENT '序号数 : 顺序增加的数字',
	-- 来自业务字典： DICT_SYS_RESET
	-- 如：
	-- 不重置（默认）
	-- 按天重置
	-- 按周重置
	-- 自定义重置周期（按指定时间间隔重置）
	-- ...
	RESET VARCHAR(32) NOT NULL COMMENT '重置方式 : 来自业务字典： DICT_SYS_RESET
如：
不重置（默认）
按天重置
按周重置
自定义重置周期（按指定时间间隔重置）
...',
	-- 重置程序执行时的输入参数，通过本参数指定六重置周期，重置执行时间，重置起始数字等
	RESET_PARAMS VARCHAR(1024) COMMENT '重置处理参数 : 重置程序执行时的输入参数，通过本参数指定六重置周期，重置执行时间，重置起始数字等',
	PRIMARY KEY (SEQ_KEY)
) COMMENT = '序号资源表 : 每个SEQ_KEY表示一个序号资源，顺序增加使用序号。';



/* Create Foreign Keys */

ALTER TABLE AC_ENTITY
	ADD CONSTRAINT F_APP_ENTITY FOREIGN KEY (GUID_APP)
	REFERENCES AC_APP (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_FUNCGROUP
	ADD CONSTRAINT F_APP_FUNCTION FOREIGN KEY (GUID_APP)
	REFERENCES AC_APP (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_BHV_DEF
	ADD FOREIGN KEY (GUID_BEHTYPE)
	REFERENCES AC_BHVTYPE_DEF (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_FUNC_BHVTYPE
	ADD FOREIGN KEY (GUID_BHVTYPE)
	REFERENCES AC_BHVTYPE_DEF (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_FUNC_BHV
	ADD FOREIGN KEY (GUID_BHV)
	REFERENCES AC_BHV_DEF (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_ROLE_DATASCOPE
	ADD CONSTRAINT F_DATA_ROLE FOREIGN KEY (GUID_DATASCOPE)
	REFERENCES AC_DATASCOPE (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_DATASCOPE
	ADD CONSTRAINT F_ENTITY_DATA FOREIGN KEY (GUID_ENTITY)
	REFERENCES AC_ENTITY (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_ENTITYFIELD
	ADD CONSTRAINT F_ENTITY_FILED FOREIGN KEY (GUID_ENTITY)
	REFERENCES AC_ENTITY (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_ROLE_ENTITY
	ADD CONSTRAINT F_ENTITY_ROLE FOREIGN KEY (GUID_ENTITY)
	REFERENCES AC_ENTITY (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_ROLE_ENTITYFIELD
	ADD CONSTRAINT F_FIELD_ROLE FOREIGN KEY (GUID_ENTITYFIELD)
	REFERENCES AC_ENTITYFIELD (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_FUNC_BHV
	ADD FOREIGN KEY (GUID_FUNC)
	REFERENCES AC_FUNC (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_FUNC_BHVTYPE
	ADD FOREIGN KEY (GUID_FUNC)
	REFERENCES AC_FUNC (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_FUNC_RESOURCE
	ADD CONSTRAINT F_FUN_RES FOREIGN KEY (GUID_FUNC)
	REFERENCES AC_FUNC (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_OPERATOR_FUNC
	ADD CONSTRAINT F_FUN_OPER FOREIGN KEY (GUID_FUNC)
	REFERENCES AC_FUNC (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_ROLE_FUNC
	ADD CONSTRAINT F_FUN_ROLE FOREIGN KEY (GUID_FUNC)
	REFERENCES AC_FUNC (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_FUNC
	ADD CONSTRAINT F_FUNGROUP_FUN FOREIGN KEY (GUID_FUNCGROUP)
	REFERENCES AC_FUNCGROUP (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_FUNCGROUP
	ADD CONSTRAINT F_FUNG_FUNG FOREIGN KEY (GUID_PARENTS)
	REFERENCES AC_FUNCGROUP (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_OPERATOR_BHV
	ADD FOREIGN KEY (GUID_FUNC_BHV)
	REFERENCES AC_FUNC_BHV (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_MENU
	ADD FOREIGN KEY (GUID_PARENTS)
	REFERENCES AC_MENU (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_OPERATOR_BHV
	ADD FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_OPERATOR_CONFIG
	ADD CONSTRAINT F_OPER_CONF FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_OPERATOR_FUNC
	ADD CONSTRAINT F_OPER_FUN FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_OPERATOR_IDENTITY
	ADD CONSTRAINT F_OPER_STATUS FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_OPERATOR_MENU
	ADD CONSTRAINT F_OPER_RMENU FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_OPERATOR_ROLE
	ADD CONSTRAINT F_OPER_ROLE FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_OPERATOR_SHORTCUT
	ADD CONSTRAINT F_OPER_QMENU FOREIGN KEY (GUID_OPERATOR)
	REFERENCES AC_OPERATOR (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_OPERATOR_IDENTITYRES
	ADD CONSTRAINT F_STATUS_FUN FOREIGN KEY (GUID_IDENTITY)
	REFERENCES AC_OPERATOR_IDENTITY (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_OPERATOR_MENU
	ADD CONSTRAINT F_ROM_ROM FOREIGN KEY (GUID_PARENTS)
	REFERENCES AC_OPERATOR_MENU (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_OPERATOR_ROLE
	ADD CONSTRAINT F_ROLE_OPER FOREIGN KEY (GUID_ROLE)
	REFERENCES AC_ROLE (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_PARTY_ROLE
	ADD CONSTRAINT P_PARTYROLE FOREIGN KEY (GUID_ROLE)
	REFERENCES AC_ROLE (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_ROLE_DATASCOPE
	ADD CONSTRAINT F_ROLE_DATA FOREIGN KEY (GUID_ROLE)
	REFERENCES AC_ROLE (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_ROLE_ENTITY
	ADD CONSTRAINT F_ROLE_ENTITY FOREIGN KEY (GUID_ROLE)
	REFERENCES AC_ROLE (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_ROLE_ENTITYFIELD
	ADD CONSTRAINT F_ROLENTY_ROLE FOREIGN KEY (GUID_ROLE)
	REFERENCES AC_ROLE (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE AC_ROLE_FUNC
	ADD CONSTRAINT F_ROLE_FUN FOREIGN KEY (GUID_ROLE)
	REFERENCES AC_ROLE (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_BUSIORG
	ADD CONSTRAINT F_BORG_BORG FOREIGN KEY (GUID_PARENTS)
	REFERENCES OM_BUSIORG (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_DUTY
	ADD CONSTRAINT F_DUTY_DUTY FOREIGN KEY (GUID_PARENTS)
	REFERENCES OM_DUTY (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_POSITION
	ADD CONSTRAINT F_DUTY_POS FOREIGN KEY (GUID_DUTY)
	REFERENCES OM_DUTY (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_EMP_GROUP
	ADD CONSTRAINT F_EMP_GROUP FOREIGN KEY (GUID_EMP)
	REFERENCES OM_EMPLOYEE (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_EMP_ORG
	ADD FOREIGN KEY (GUID_EMP)
	REFERENCES OM_EMPLOYEE (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_EMP_POSITION
	ADD CONSTRAINT F_EMP_POS FOREIGN KEY (GUID_EMP)
	REFERENCES OM_EMPLOYEE (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_EMP_GROUP
	ADD FOREIGN KEY (GUID_GROUP)
	REFERENCES OM_GROUP (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_GROUP
	ADD CONSTRAINT F_GROUP_GROUP FOREIGN KEY (GUID_PARENTS)
	REFERENCES OM_GROUP (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_GROUP_APP
	ADD CONSTRAINT F_GROUP_APP FOREIGN KEY (GUID_GROUP)
	REFERENCES OM_GROUP (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_GROUP_POSITION
	ADD CONSTRAINT F_GROUP_POS FOREIGN KEY (GUID_GROUP)
	REFERENCES OM_GROUP (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_BUSIORG
	ADD CONSTRAINT F_BIZ_ORG FOREIGN KEY (GUID_ORG)
	REFERENCES OM_ORG (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_EMP_ORG
	ADD FOREIGN KEY (GUID_ORG)
	REFERENCES OM_ORG (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_GROUP
	ADD CONSTRAINT F_ORG_GROUP FOREIGN KEY (GUID_ORG)
	REFERENCES OM_ORG (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_ORG
	ADD CONSTRAINT F_ORG_ORG FOREIGN KEY (GUID_PARENTS)
	REFERENCES OM_ORG (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_POSITION
	ADD CONSTRAINT F_ORG_POS FOREIGN KEY (GUID_ORG)
	REFERENCES OM_ORG (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_EMP_POSITION
	ADD CONSTRAINT F_POS_EMP FOREIGN KEY (GUID_POSITION)
	REFERENCES OM_POSITION (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_GROUP_POSITION
	ADD FOREIGN KEY (GUID_POSITION)
	REFERENCES OM_POSITION (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_POSITION
	ADD CONSTRAINT F_POS_POS FOREIGN KEY (GUID_PARENTS)
	REFERENCES OM_POSITION (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE OM_POSITION_APP
	ADD FOREIGN KEY (GUID_POSITION)
	REFERENCES OM_POSITION (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE SYS_DICT
	ADD FOREIGN KEY (GUID_PARENTS)
	REFERENCES SYS_DICT (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE SYS_DICT_ITEM
	ADD FOREIGN KEY (GUID_DICT)
	REFERENCES SYS_DICT (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



