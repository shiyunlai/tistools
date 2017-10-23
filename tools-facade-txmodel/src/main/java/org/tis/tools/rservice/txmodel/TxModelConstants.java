/**
 * 
 */
package org.tis.tools.rservice.txmodel;

/**
 * 交易模式服务模块使用到的常量定义
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
		NO_CATEGORY("无分类"),

		/**
		 * 行为类型: 账务类
		 */
		ACCOUNT("账务类"),

		/**
		 * 行为类型: 维护类
		 */
		MAINTAIN("维护类");

		/**
		 * 分类描述信息
		 */
		private String desc = "";

		private BHVTYPE( String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return this.desc;
		}
		
		public String toString(){
			return this.name() + ":" + getDesc();
		}
	}

	/**
	 * <pre>
	 * 操作代码枚举
	 * 
	 * 构造：操作代码("操作代码描述")
	 * 
	 * </pre>
	 * 
	 * @author megapro
	 *
	 */
	public enum BHVCODE {
		
		/**
		 * 行为代码: 空操作
		 */
		NONOPERATOR("空操作"),
		
		/**
		 * 行为代码: 打开交易
		 */
		OPEN_TX("打开交易"),

		/**
		 * 行为代码: 关闭交易
		 */
		CLOSE_TX("关闭交易");

		/**
		 * 操作代码描述
		 */
		private String desc = "";

		private BHVCODE(String desc) {
			this.desc = desc;
		}

		public String getDesc() {
			return this.desc;
		}
		
		public String toString(){
			return this.name() + ":" + getDesc() ; 
		}
	}
}
