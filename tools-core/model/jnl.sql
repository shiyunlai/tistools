SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS JNL_CONTACT_LOG;
DROP TABLE IF EXISTS JNL_ENQUEUE;
DROP TABLE IF EXISTS JNL_PREFILL;
DROP TABLE IF EXISTS JNL_PROMOTING;
DROP TABLE IF EXISTS JNL_HOSTTRANS_MSG;
DROP TABLE IF EXISTS JNL_HOSTTRANS;
DROP TABLE IF EXISTS JNL_TRANS_CTX;
DROP TABLE IF EXISTS JNL_TRANS_FEATURE;
DROP TABLE IF EXISTS JNL_TRANS_PRINTED;
DROP TABLE IF EXISTS JNL_TRANS;
DROP TABLE IF EXISTS JNL_CUST_SERVICE;
DROP TABLE IF EXISTS JNL_TELLER_TRACE;




/* Create Tables */

-- 记录客户与网点系统接触的所有日志明细，这些接触行为包括：
-- 客户主动接触网点，如：使用自助设备；
-- 柜员主动接触客户，如：厅
CREATE TABLE JNL_CONTACT_LOG
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
	-- yyyyMMddHHmmssSSS
	CONTACT_TIME VARCHAR(18) COMMENT 'yyyyMMddHHmmssSSS',
	-- 记录接触系统对应的渠道代码
	CHN_CODE VARCHAR(128) COMMENT '记录接触系统对应的渠道代码',
	-- 本次接触活动的柜员
	TELLER_NO VARCHAR(128) COMMENT '本次接触活动的柜员',
	-- 本次接触的网点代码
	INSTNO VARCHAR(128) COMMENT '本次接触的网点代码',
	-- 接触行为类型，见业务字典： DICT_CONTACT_MODE
	-- passive － 被动接触
	-- active - 主动接触
	CONTACT_MODE VARCHAR(16) COMMENT '接触行为类型，见业务字典： DICT_CONTACT_MODE
passive － 被动接触
active - 主动接触',
	-- 系统中的客户编号
	CUST_NO VARCHAR(128) COMMENT '系统中的客户编号',
	CUST_NAME VARCHAR(128),
	-- 对银行业务的类型划分
	-- 见业务字典： DICT_BIZ_TYPE
	BIZ_TYPE VARCHAR(32) COMMENT '对银行业务的类型划分
见业务字典： DICT_BIZ_TYPE',
	PRIMARY KEY (GUID)
) COMMENT = '记录客户与网点系统接触的所有日志明细，这些接触行为包括：
客户主动接触网点，如：使用自助设备；
柜员主动接触客户，如：厅';


-- 服务流水：服务流水 应该是 对客户的服务过程， 如果没有客户的参与不算入内
-- 一个服务流水的生命周期：
-- 1、开始，网点第一
CREATE TABLE JNL_CUST_SERVICE
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 某次服务的义务唯一标识，由系统按照规则生成，不能重复
	SERVICE_SNO VARCHAR(128) NOT NULL COMMENT '某次服务的义务唯一标识，由系统按照规则生成，不能重复',
	-- 存储到毫秒级别的时间 yyyyMMddHHmmssSSS
	START_TIME VARCHAR(18) COMMENT '存储到毫秒级别的时间 yyyyMMddHHmmssSSS',
	-- yyyyMMddHHmmssSSS
	END_TIME VARCHAR(18) COMMENT 'yyyyMMddHHmmssSSS',
	-- 系统中的客户编号
	CUST_NO VARCHAR(128) COMMENT '系统中的客户编号',
	-- 银行对客户的评级
	CUST_LEVEL VARCHAR(16) COMMENT '银行对客户的评级',
	-- 证件号码
	PAPER_NO VARCHAR(128) COMMENT '证件号码',
	-- 证件类型，见业务字典： DICT_PAPER_TYPE
	PAPER_TYPE CHAR(2) COMMENT '证件类型，见业务字典： DICT_PAPER_TYPE',
	-- 本次客户服务的上下文。预先获取的关于客户的信息，如：客户基本信息，客户资产信息。。。 便于客户服务过程中柜员使用，提高服务质量；
	-- 以JSON格式存储信息； 
	-- 一般：
	-- 1、排队时获取
	-- 2、预约时获取
	-- 3、身份核查时获取
	-- 新客户则为空
	CUST_SERVICE_CTX TEXT COMMENT '本次客户服务的上下文。预先获取的关于客户的信息，如：客户基本信息，客户资产信息。。。 便于客户服务过程中柜员使用，提高服务质量；
以JSON格式存储信息； 
一般：
1、排队时获取
2、预约时获取
3、身份核查时获取
新客户则为空',
	-- 从开始服务到结束服务两个状态之间的时长
	SERVICE_INTERVAL INT(8) COMMENT '从开始服务到结束服务两个状态之间的时长',
	-- 银行所提供的客户服务类型
	-- 见义务字典： DICT_SERVICE_TYPE
	SERVICE_TYPE VARCHAR(16) COMMENT '银行所提供的客户服务类型
见义务字典： DICT_SERVICE_TYPE',
	-- 对客户服务的评价，见义务字典：DICT_SERVICE_APPRAISE
	-- positive	好评
	-- normal	一般
	-- negative	差评
	SERVICE_APPRAISE VARCHAR(16) COMMENT '对客户服务的评价，见义务字典：DICT_SERVICE_APPRAISE
positive	好评
normal	一般
negative	差评',
	-- 详见业务字典： DICT_SERVICE_STATUS
	-- start - 开始服务
	-- pause - 暂停服务
	-- finish - 结束服务
	-- 
	SERVICE_STATUS CHAR(32) NOT NULL COMMENT '详见业务字典： DICT_SERVICE_STATUS
start - 开始服务
pause - 暂停服务
finish - 结束服务
',
	-- 服务过程中成功操作过的交易笔数（包括：柜员操作、系统自动完成、客户自助操作等），事后系统自己统计完善客户服务流水；
	TRANS_NUM INT COMMENT '服务过程中成功操作过的交易笔数（包括：柜员操作、系统自动完成、客户自助操作等），事后系统自己统计完善客户服务流水；',
	PRIMARY KEY (GUID, SERVICE_SNO),
	UNIQUE (GUID),
	UNIQUE (SERVICE_SNO)
) COMMENT = '服务流水：服务流水 应该是 对客户的服务过程， 如果没有客户的参与不算入内
一个服务流水的生命周期：
1、开始，网点第一';


-- 每新开始一次排队，会生成一笔对应的服务流水。
-- 如果是客户服务内取号排队，则计入同一个客户服务过程内；
CREATE TABLE JNL_ENQUEUE
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 关联服务流水记录
	GUID_CUST_SERVICE VARCHAR(128) NOT NULL COMMENT '关联服务流水记录',
	-- 某次服务的义务唯一标识，由系统按照规则生成，不能重复
	SERVICE_SNO VARCHAR(128) NOT NULL COMMENT '某次服务的义务唯一标识，由系统按照规则生成，不能重复',
	-- 排队顺序号
	ENQUEUE_NO INT(8) NOT NULL COMMENT '排队顺序号',
	-- 见业务字典：DICT_QUEUE_BIZ_TYPE
	ENQUEUE_BIZ_TYPE VARCHAR(32) NOT NULL COMMENT '见业务字典：DICT_QUEUE_BIZ_TYPE',
	-- 记录接触系统对应的渠道代码；
	-- 来自渠道参数控制表： SYS_CHANNEL_CTL
	CHN_CODE VARCHAR(128) NOT NULL COMMENT '记录接触系统对应的渠道代码；
来自渠道参数控制表： SYS_CHANNEL_CTL',
	-- yyyyMMddHHmmSSsss
	ENQUEUE_IN_TIME VARCHAR(18) NOT NULL COMMENT 'yyyyMMddHHmmSSsss',
	-- 每台排队机都有唯一的编号，类似工作站编号；
	-- 但是，不一定每次排队都有排队机编号
	ENQUEUE_CODE VARCHAR(32) COMMENT '每台排队机都有唯一的编号，类似工作站编号；
但是，不一定每次排队都有排队机编号',
	-- 系统中的客户编号
	CUST_NO VARCHAR(128) COMMENT '系统中的客户编号',
	CUST_NAME VARCHAR(128),
	-- 银行对客户的评级
	CUST_LEVEL VARCHAR(16) COMMENT '银行对客户的评级',
	-- 证件类型，见业务字典： DICT_PAPER_TYPE
	PAPER_TYPE CHAR(2) COMMENT '证件类型，见业务字典： DICT_PAPER_TYPE',
	-- 证件号码
	PAPER_NO VARCHAR(128) COMMENT '证件号码',
	CALL_TELLER VARCHAR(32),
	-- yyyyMMddHHmmSSsss
	CALL_TIME VARCHAR(18) COMMENT 'yyyyMMddHHmmSSsss',
	CALL_INSTNO VARCHAR(32),
	PRIMARY KEY (GUID),
	UNIQUE (GUID)
) COMMENT = '每新开始一次排队，会生成一笔对应的服务流水。
如果是客户服务内取号排队，则计入同一个客户服务过程内；';


-- 主机交易流水表记录了我方向主机系统发出的请求记录；
-- 一般每个交易都会有对应的主机交易流水；
-- 如果直接发起的主机交易，则不
CREATE TABLE JNL_HOSTTRANS
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 关联交易流水记录
	-- 但不是所有主机交易都能关联到交易流水记录
	-- 有些自动发器的主机交易处理就不会有交易流水
	GUID_TRANS VARCHAR(128) COMMENT '关联交易流水记录
但不是所有主机交易都能关联到交易流水记录
有些自动发器的主机交易处理就不会有交易流水',
	-- 交易流水号，唯一标识业务的唯一性
	TRANS_SNO VARCHAR(128) COMMENT '交易流水号，唯一标识业务的唯一性',
	-- 主机系统的服务标识代码，唯一标识了某个主机系统
	HOST_CODE VARCHAR(32) NOT NULL COMMENT '主机系统的服务标识代码，唯一标识了某个主机系统',
	HOSTTRANS_CODE VARCHAR(32) NOT NULL,
	HOSTTRANS_NAME VARCHAR(256),
	-- 存储到毫秒级别的时间 yyyyMMddHHmmssSSS
	START_TIME VARCHAR(18) COMMENT '存储到毫秒级别的时间 yyyyMMddHHmmssSSS',
	-- yyyyMMddHHmmssSSS
	END_TIME VARCHAR(18) COMMENT 'yyyyMMddHHmmssSSS',
	-- 唯一标识某次主机请求过程的流水号，由我方生成
	HOST_SNO_REQ VARCHAR(128) NOT NULL COMMENT '唯一标识某次主机请求过程的流水号，由我方生成',
	-- 主机系统唯一标识本次请求的流水号，由主机方生成，我方在收到后更新
	HOST_SNO_RSP VARCHAR(128) COMMENT '主机系统唯一标识本次请求的流水号，由主机方生成，我方在收到后更新',
	-- 见业务字典：DICT_HSOTTRANS_STATUS
	HSOTTRANS_STATUS VARCHAR(2) NOT NULL COMMENT '见业务字典：DICT_HSOTTRANS_STATUS',
	-- 主机系统返回的错误码
	HOST_ERR_CODE VARCHAR(128) COMMENT '主机系统返回的错误码',
	-- 主机系统返回的错误信息，超长的会被自动截取，完整的内容则只能在主机响应报文中获得。
	HOST_ERR_MSG VARCHAR(512) COMMENT '主机系统返回的错误信息，超长的会被自动截取，完整的内容则只能在主机响应报文中获得。',
	PRIMARY KEY (GUID),
	UNIQUE (GUID)
) COMMENT = '主机交易流水表记录了我方向主机系统发出的请求记录；
一般每个交易都会有对应的主机交易流水；
如果直接发起的主机交易，则不';


-- 主机交易对应的报文信息，一笔主机交易可能重复执行请求，则本表中记录当次主机交易的请求/响应报文；
-- 假如一个主机交易成功的
CREATE TABLE JNL_HOSTTRANS_MSG
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 关联主机交易流水记录
	GUID_HOSTTRANS VARCHAR(128) NOT NULL COMMENT '关联主机交易流水记录',
	-- 见业务字典：DICT_MSG_TYPE
	MSG_TYPE CHAR(8) NOT NULL COMMENT '见业务字典：DICT_MSG_TYPE',
	-- 存储到毫秒级别的时间 yyyyMMddHHmmssSSS
	EXEC_TIME VARCHAR(18) NOT NULL COMMENT '存储到毫秒级别的时间 yyyyMMddHHmmssSSS',
	-- 报文内容
	MSG_INFO TEXT NOT NULL COMMENT '报文内容',
	PRIMARY KEY (GUID),
	UNIQUE (GUID),
	UNIQUE (GUID_HOSTTRANS)
) COMMENT = '主机交易对应的报文信息，一笔主机交易可能重复执行请求，则本表中记录当次主机交易的请求/响应报文；
假如一个主机交易成功的';


-- 预填交易流水记录
-- 预填可以由客户自己完成，也可以由某柜员代替完成，
-- 根据预填记录启动了某个交易需更新相关的交易信息
CREATE TABLE JNL_PREFILL
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 关联服务流水记录
	GUID_CUST_SERVICE VARCHAR(128) NOT NULL COMMENT '关联服务流水记录',
	-- 某次服务的义务唯一标识，由系统按照规则生成，不能重复
	SERVICE_SNO VARCHAR(128) NOT NULL COMMENT '某次服务的义务唯一标识，由系统按照规则生成，不能重复',
	PREFILL_DATE CHAR(8),
	-- yyyyMMddHHmmSSsss
	PREFILL_TIME VARCHAR(18) COMMENT 'yyyyMMddHHmmSSsss',
	TRANS_CODE VARCHAR(32) NOT NULL,
	PREFILL_DATA TEXT,
	-- 见业务字典：DICT_PREFILL_STATUS
	PREFILL_STATUS VARCHAR(8) NOT NULL COMMENT '见业务字典：DICT_PREFILL_STATUS',
	-- 预填来源渠道（渠道代码）
	PREFILL_CHN VARCHAR(128) NOT NULL COMMENT '预填来源渠道（渠道代码）',
	-- 本次接触活动的柜员
	TELLER_NO VARCHAR(128) COMMENT '本次接触活动的柜员',
	INSTNO VARCHAR(128),
	-- 本次交易操作的柜员代码
	TRANS_TELLER VARCHAR(32) COMMENT '本次交易操作的柜员代码',
	-- 本次交易发生所在机构
	TRANS_INSTNO VARCHAR(32) COMMENT '本次交易发生所在机构',
	PRIMARY KEY (GUID),
	UNIQUE (GUID)
) COMMENT = '预填交易流水记录
预填可以由客户自己完成，也可以由某柜员代替完成，
根据预填记录启动了某个交易需更新相关的交易信息';


CREATE TABLE JNL_PROMOTING
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 关联服务流水
	GUID_CUST_SERVICE VARCHAR(128) NOT NULL COMMENT '关联服务流水',
	-- 某次服务的义务唯一标识，由系统按照规则生成，不能重复
	SERVICE_SNO VARCHAR(128) NOT NULL COMMENT '某次服务的义务唯一标识，由系统按照规则生成，不能重复',
	-- 标识本营销信息来自何处（可能是应用系统、或自动程序等等...），见业务字典： DICT_PROMOTING_ORIGIN
	-- 
	PROMOTING_ORIGIN VARCHAR(128) NOT NULL COMMENT '标识本营销信息来自何处（可能是应用系统、或自动程序等等...），见业务字典： DICT_PROMOTING_ORIGIN
',
	-- 见业务字典： DICT_PROMOTING_BIZ_TYPE
	PROMOTING_BIZ_TYPE VARCHAR(32) NOT NULL COMMENT '见业务字典： DICT_PROMOTING_BIZ_TYPE',
	-- 本营销内容的标题信息，有字数限制200个汉字以内
	PROMOTING_TITLE VARCHAR(512) NOT NULL COMMENT '本营销内容的标题信息，有字数限制200个汉字以内',
	PROMOTING_INFO TEXT,
	-- 本次营销的行为，通常是按钮事件，系统根据本字段进行营销按钮呈现
	PROMOTING_ACTION VARCHAR(1024) COMMENT '本次营销的行为，通常是按钮事件，系统根据本字段进行营销按钮呈现',
	-- 本次接触活动的柜员
	TELLER_NO VARCHAR(128) COMMENT '本次接触活动的柜员',
	INSTNO VARCHAR(128),
	PROMOTING_DATE CHAR(8),
	-- yyyyMMddHHmmSSsss
	PROMOTING_TIME VARCHAR(18) COMMENT 'yyyyMMddHHmmSSsss',
	-- 见业务字典：DICT_PROMOTING_FEEDBACK
	PROMOTING_FEEDBACK VARCHAR(16) NOT NULL COMMENT '见业务字典：DICT_PROMOTING_FEEDBACK',
	PRIMARY KEY (GUID),
	UNIQUE (GUID)
);


-- 记录柜员使用系统的操作日志记录
CREATE TABLE JNL_TELLER_TRACE
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 本次接触的网点代码
	INSTNO VARCHAR(128) NOT NULL COMMENT '本次接触的网点代码',
	-- 本次接触活动的柜员
	TELLER_NO VARCHAR(128) NOT NULL COMMENT '本次接触活动的柜员',
	-- 记录接触系统对应的渠道代码；
	-- 来自渠道参数控制表： SYS_CHANNEL_CTL
	CHN_CODE VARCHAR(128) NOT NULL COMMENT '记录接触系统对应的渠道代码；
来自渠道参数控制表： SYS_CHANNEL_CTL',
	-- 操作行为类型
	-- 见业务字典： DICT_ACTION_TYPE
	ACTION_TYPE VARCHAR(16) NOT NULL COMMENT '操作行为类型
见业务字典： DICT_ACTION_TYPE',
	ACTION_TIME VARCHAR(18) NOT NULL,
	-- 交易流水号，唯一标识业务的唯一性
	TRANS_SNO VARCHAR(128) COMMENT '交易流水号，唯一标识业务的唯一性',
	TRANS_CODE VARCHAR(32),
	-- 柜员工作站编码
	TWS_CODE VARCHAR(128) COMMENT '柜员工作站编码',
	-- 记录操作过程中的备注信息，比如存储操作前后某些字典的变化对照
	REMARK_INFO TEXT COMMENT '记录操作过程中的备注信息，比如存储操作前后某些字典的变化对照',
	PRIMARY KEY (GUID),
	UNIQUE (GUID)
) COMMENT = '记录柜员使用系统的操作日志记录';


CREATE TABLE JNL_TRANS
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 关联客户服务流水记录
	GUID_CUST_SERVICE VARCHAR(128) COMMENT '关联客户服务流水记录',
	-- 某次服务的义务唯一标识，由系统按照规则生成，不能重复
	SERVICE_SNO VARCHAR(128) COMMENT '某次服务的义务唯一标识，由系统按照规则生成，不能重复',
	-- 交易流水号，唯一标识业务的唯一性
	TRANS_SNO VARCHAR(128) NOT NULL COMMENT '交易流水号，唯一标识业务的唯一性',
	-- 启动本交易的来源方式，见业务字典： DICT_TRANS_ORIGIN
	TRANS_ORIGIN VARCHAR(16) NOT NULL COMMENT '启动本交易的来源方式，见业务字典： DICT_TRANS_ORIGIN',
	-- 对应本交易启动来源的GUID
	-- 如：预填单启动时，本字段记录预填流水GUID；关联启动时，本字段记录关联交易的交易流水GUID；为空时，表示柜员直接启动的交易；
	GUID_ORIGIN VARCHAR(128) COMMENT '对应本交易启动来源的GUID
如：预填单启动时，本字段记录预填流水GUID；关联启动时，本字段记录关联交易的交易流水GUID；为空时，表示柜员直接启动的交易；',
	TRANS_CODE VARCHAR(32) NOT NULL,
	TRANS_NAME VARCHAR(128),
	-- 交易发生的日期 yyyyMMdd
	TRANS_DATE CHAR(8) NOT NULL COMMENT '交易发生的日期 yyyyMMdd',
	-- 存储到毫秒级别的时间 yyyyMMddHHmmssSSS
	START_TIME VARCHAR(18) COMMENT '存储到毫秒级别的时间 yyyyMMddHHmmssSSS',
	-- yyyyMMddHHmmssSSS
	END_TIME VARCHAR(18) COMMENT 'yyyyMMddHHmmssSSS',
	-- 本次交易操作的柜员代码
	TRANS_TELLER VARCHAR(32) COMMENT '本次交易操作的柜员代码',
	-- 本次交易发生所在机构
	TRANS_INSTNO VARCHAR(32) COMMENT '本次交易发生所在机构',
	-- 见业务字典：DICT_TRANS_STATUS
	TRANS_STATUS VARCHAR(16) NOT NULL COMMENT '见业务字典：DICT_TRANS_STATUS',
	-- 交易的错误编码，另见错误代码表 SYS_ERR_CODE
	TRANS_ERR_CODE VARCHAR(32) NOT NULL COMMENT '交易的错误编码，另见错误代码表 SYS_ERR_CODE',
	-- 交易的错误信息，另见错误代码表 SYS_ERR_CODE
	TRANS_ERR_MSG VARCHAR(256) COMMENT '交易的错误信息，另见错误代码表 SYS_ERR_CODE',
	PRIMARY KEY (GUID, TRANS_SNO),
	UNIQUE (GUID),
	UNIQUE (TRANS_SNO)
);


-- 存储交易在某个执行阶段的上下文信息
-- 如：hold操作时，系统会把当前未提交的界面信息全部存储起来，以便后续继续补录提交；
CREATE TABLE JNL_TRANS_CTX
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 关联交易流水记录
	GUID_TRANS VARCHAR(128) NOT NULL COMMENT '关联交易流水记录',
	-- 交易流水号，唯一标识业务的唯一性
	TRANS_SNO VARCHAR(128) NOT NULL COMMENT '交易流水号，唯一标识业务的唯一性',
	-- 交易执行到某个阶段，见业务字典： DICT_TRANS_PHASE
	TRANS_PHASE VARCHAR(16) NOT NULL COMMENT '交易执行到某个阶段，见业务字典： DICT_TRANS_PHASE',
	-- yyyyMMddHHmmssSSS
	SAVE_TIME VARCHAR(18) NOT NULL COMMENT 'yyyyMMddHHmmssSSS',
	TRANS_CTX TEXT,
	PRIMARY KEY (GUID),
	UNIQUE (GUID)
) COMMENT = '存储交易在某个执行阶段的上下文信息
如：hold操作时，系统会把当前未提交的界面信息全部存储起来，以便后续继续补录提交；';


-- 直接记录交易中某个字段数据信息
CREATE TABLE JNL_TRANS_FEATURE
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 关联交易流水记录
	GUID_TRANS VARCHAR(128) NOT NULL COMMENT '关联交易流水记录',
	-- 交易流水号，唯一标识业务的唯一性
	TRANS_SNO VARCHAR(128) NOT NULL COMMENT '交易流水号，唯一标识业务的唯一性',
	-- 对应交易界面上的字段代码
	TRANS_FIELD VARCHAR(32) NOT NULL COMMENT '对应交易界面上的字段代码',
	-- 交易当时的数据信息，所有信息转换为String的形式存储
	TRANS_DATA VARCHAR(512) NOT NULL COMMENT '交易当时的数据信息，所有信息转换为String的形式存储',
	PRIMARY KEY (GUID),
	UNIQUE (GUID)
) COMMENT = '直接记录交易中某个字段数据信息';


CREATE TABLE JNL_TRANS_PRINTED
(
	-- 全局唯一标识符（GUID，Globally Unique Identifier）
	GUID VARCHAR(128) NOT NULL COMMENT '全局唯一标识符（GUID，Globally Unique Identifier）',
	-- 关联交易流水记录
	GUID_TRANS VARCHAR(128) NOT NULL COMMENT '关联交易流水记录',
	-- 交易流水号，唯一标识业务的唯一性
	TRANS_SNO VARCHAR(128) NOT NULL COMMENT '交易流水号，唯一标识业务的唯一性',
	TRANS_CODE VARCHAR(32) NOT NULL,
	-- 凭证格式的代码
	VOUCHER_CODE VARCHAR(128) NOT NULL COMMENT '凭证格式的代码',
	-- 存储生成的打印数据（已经是打印机执行编码）
	PRINTED_DATA TEXT NOT NULL COMMENT '存储生成的打印数据（已经是打印机执行编码）',
	-- 存储交易凭证电子影像对应的记录ID，根据此ID，可查找对应的电子凭证影像记录。
	-- 如：影像套号
	-- 但不是每笔交易都有电子影像
	VOUCHER_IMG_ID VARCHAR(128) COMMENT '存储交易凭证电子影像对应的记录ID，根据此ID，可查找对应的电子凭证影像记录。
如：影像套号
但不是每笔交易都有电子影像',
	-- 首次打印凭证的日期yyyyMMdd
	PRINTED_DATE CHAR(8) NOT NULL COMMENT '首次打印凭证的日期yyyyMMdd',
	-- 记录最近一次打印的时间yyyyMMddHHmmSSsss
	LAST_PRINTED_TIME CHAR(18) NOT NULL COMMENT '记录最近一次打印的时间yyyyMMddHHmmSSsss',
	-- 凭证被打印的次数，每次打印都＋1
	PRINTED_NUM INT NOT NULL COMMENT '凭证被打印的次数，每次打印都＋1',
	PRIMARY KEY (GUID),
	UNIQUE (GUID),
	UNIQUE (GUID_TRANS),
	UNIQUE (TRANS_SNO)
);



/* Create Foreign Keys */

ALTER TABLE JNL_ENQUEUE
	ADD FOREIGN KEY (GUID_CUST_SERVICE, SERVICE_SNO)
	REFERENCES JNL_CUST_SERVICE (GUID, SERVICE_SNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE JNL_PREFILL
	ADD FOREIGN KEY (GUID_CUST_SERVICE, SERVICE_SNO)
	REFERENCES JNL_CUST_SERVICE (GUID, SERVICE_SNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE JNL_PROMOTING
	ADD FOREIGN KEY (GUID_CUST_SERVICE, SERVICE_SNO)
	REFERENCES JNL_CUST_SERVICE (GUID, SERVICE_SNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE JNL_TRANS
	ADD FOREIGN KEY (GUID_CUST_SERVICE, SERVICE_SNO)
	REFERENCES JNL_CUST_SERVICE (GUID, SERVICE_SNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE JNL_HOSTTRANS_MSG
	ADD FOREIGN KEY (GUID_HOSTTRANS)
	REFERENCES JNL_HOSTTRANS (GUID)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE JNL_HOSTTRANS
	ADD FOREIGN KEY (GUID_TRANS, TRANS_SNO)
	REFERENCES JNL_TRANS (GUID, TRANS_SNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE JNL_TRANS_CTX
	ADD FOREIGN KEY (GUID_TRANS, TRANS_SNO)
	REFERENCES JNL_TRANS (GUID, TRANS_SNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE JNL_TRANS_FEATURE
	ADD FOREIGN KEY (GUID_TRANS, TRANS_SNO)
	REFERENCES JNL_TRANS (GUID, TRANS_SNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE JNL_TRANS_PRINTED
	ADD FOREIGN KEY (GUID_TRANS, TRANS_SNO)
	REFERENCES JNL_TRANS (GUID, TRANS_SNO)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



