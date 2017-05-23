SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS SYS_CHANNEL_CTL;
DROP TABLE IF EXISTS SYS_DICT_ITEM;
DROP TABLE IF EXISTS SYS_DICT;
DROP TABLE IF EXISTS SYS_ERR_CODE;
DROP TABLE IF EXISTS SYS_SEQNO;
DROP TABLE IF EXISTS SYS_TEST;




/* Create Tables */

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


-- 测试
CREATE TABLE SYS_TEST
(
	TEST_BIGINT BIGINT COMMENT '测试BIGINT',
	TEST_GIBINTN BIGINT(N) COMMENT '测试GIBINT(8)',
	TEST_CHAR CHAR COMMENT '测试CHAR',
	TEST_CHARN CHAR(16) COMMENT '测试CHAR(16)',
	TEST_DATE DATE COMMENT '测试DATE',
	TEST_DATETIME DATETIME COMMENT '测试DATETIME',
	TEST_DECIMAL DECIMAL COMMENT '测试DECIMAL',
	TEST_DECIMALP DECIMAL(12) COMMENT '测试DECIMAL(12)',
	TEST_DECIMALPS DECIMAL(12,4) COMMENT '测试DECIMAL(12,4)',
	TEST_DOUBLE DOUBLE COMMENT '测试DOUBLE',
	TEST_DOUBLEMD DOUBLE(10,4) COMMENT '测试DOUBLE(10,4)',
	TEST_FLOAT FLOAT COMMENT '测试FLOAT',
	TEST_FLOATP FLOAT(10) COMMENT '测试FLOAT(10)',
	TEST_FLOATMD FLOAT(10,4) COMMENT '测试FLOAT(10,4)',
	TEST_INT INT COMMENT '测试INT',
	TEST_INTN INT(N) COMMENT '测试INT(12)',
	TEST_TIME TIME COMMENT '测试TIME',
	TEST_TIMESTAMP TIMESTAMP COMMENT '测试TIMESTAMP',
	TEST_VARCHARN VARCHAR(32) COMMENT '测试VARCHAR(32)',
	TSET_TEXT TEXT COMMENT '测试TEXT',
	TEST_BLOB BLOB COMMENT '测试BLOB'
) COMMENT = '测试';



