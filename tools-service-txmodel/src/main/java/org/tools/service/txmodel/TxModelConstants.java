/**
 * 
 */
package org.tools.service.txmodel;

/**
 * 常量定义
 * 
 * @author megapro
 *
 */
public interface TxModelConstants {

	/**
	 * 行为类型枚举
	 * 
	 * @author megapro
	 */
	public enum BHVTYPE {

		/**
		 * 默认行为类型: 无分类
		 */
		NOCATEGORY("nocategory", "无分类"),

		/**
		 * 行为类型: 账务类
		 */
		ACCOUNT("account", "账务类"),

		/**
		 * 行为类型: 维护类
		 */
		MAINTAIN("maintain", "维护类");

		private String type = "";
		private String name = "";

		private BHVTYPE(String type, String name) {
			this.type = type;
			this.name = name;
		}

		public String getType() {
			return this.type;
		}

		public String getName() {
			return this.name;
		}
		
		public String toString(){
			return this.type + ":" + this.name;
		}
	}

	/**
	 * 操作代码枚举
	 * 
	 * @author megapro
	 *
	 */
	public enum BHVCODE {
		
		/**
		 * 行为代码: 空操作
		 */
		NONOPERATOR("non-op", "空操作"),
		
		/**
		 * 行为代码: 打开交易
		 */
		OPEN_TX("open-tx", "打开交易"),

		/**
		 * 行为代码: 关闭交易
		 */
		CLOSE_TX("close-tx", "关闭交易");

		private String bhvCode = "";
		private String bhvName = "";

		private BHVCODE(String code, String name) {
			this.bhvCode = code;
			this.bhvName = name;
		}

		public String getBhvCode() {
			return this.bhvCode;
		}

		public String getBhvName() {
			return this.bhvName;
		}
	}

}
