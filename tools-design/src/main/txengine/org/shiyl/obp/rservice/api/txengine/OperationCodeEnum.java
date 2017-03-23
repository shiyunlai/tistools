/**
 * 
 */
package org.shiyl.obp.rservice.api.txengine;

/**
 * 
 * 操作码枚举
 * 
 * @author megapro
 *
 */
public enum OperationCodeEnum {

	/** 操作码： 判断交易操作权限（judged-tx） */
	JUDGED,

	/** 操作码： 启动交易（drive-tx） */
	DRIVE,
	
	/** 操作码： 暂存交易（hold-tx） */
	HOLD,
	
	/** 操作码： 提交交易（commit-tx） */
	COMMIT,
	
	/** 操作码： 补录暂存交易（type-in） */
	TYPE_IN,
	
	/** 操作码： 再提交交易（commit-ag） */
	COMMIT_AG,
	
	/** 操作码： 取消未成功交易（cancel-tx） */
	CANCEL,

	/** 操作码： 删除已完成交易（delete-tx） */
	DELETE,
	
	/** 操作码： 补打交易凭证（re-print） */
	RE_PRINT,

	/** 操作码： 复制交易数据（copy-tx） */
	COPY_DATE,

	/** 操作码： 关闭交易（close-tx） */
	CLOSE
}
