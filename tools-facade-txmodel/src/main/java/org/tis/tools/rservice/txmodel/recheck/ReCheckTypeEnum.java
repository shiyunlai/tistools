/**
 * 
 */
package org.tis.tools.rservice.txmodel.recheck;

/**
 * 复核方式枚举
 * 
 * @author megapro
 *
 */
public enum ReCheckTypeEnum {

	/**
	 * 双敲式复核：</br>
	 * 复核员打开待复核交易，重新录入指定交易要素，</br>
	 * 当交易柜员与复核柜员前后两次录入的交易数据完全一直是，复核才算通过。
	 */
	RE_INPUT("双敲式复核"),

	/**
	 * 浏览式复核：</br>
	 * 复核员打开待复核交易，完整的浏览过所有指定交易要素后，复核才算通过。</br>
	 */
	SCAN_OVER("浏览式复核");

	private String desc = null;

	ReCheckTypeEnum(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return this.desc;
	}

	public String toString() {
		return this.name() + ":" + getDesc();
	}
}
