package com.bos.tis.tools.util;
//package d.util;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 业务字典工具类
// * @author mega-t420
// *
// */
//public class CTFDictionaryUseDemo {
//	
////	public static void main(String[] args) {
////		
////		List<BTFTransInfo> l = new ArrayList<BTFTransInfo>() ; 
////		for( BTFTransInfo t : l ){
////			
////			//转换 biz_type
////			CTFDictionary d = CTFDictionaryManager.instance.getDictionaryByTableName("btf_trans_info", "biz_type") ;
////			
////			//
////		}
////		
////	}
//
//	public class BTFTransInfo{
//		
//		public String getbiz_type() { return null ; };
//		public String getvalid_flag(){ return null ; };
//	}
//	
//	
//	
//	
//	
//	/**
//	 * 业务字典项
//	 * @author mega-t420
//	 *
//	 */
//	public class CTFDictionaryItem{
//	
//		//.....
//		
//		public String getItemValue(){
//			
//			return null ; 
//		}
//		
//		public String getSendValue(){
//			
//			return null ; 
//		}
//	}
//	
//	/**
//	 * 业务字典
//	 * @author mega-t420
//	 *
//	 */
//	public class CTFDictionary{
//		
//		String dict_key =null ; 
//		Map<String,CTFDictionaryItem> items = new HashMap<String,CTFDictionaryItem>() ;
//		
//		public CTFDictionaryItem getItemByName(String itemName){
//			
//			return items.get(itemName) ; 
//		}
//	}
//	
//	/**
//	 * 业务字典管理器
//	 * @author mega-t420
//	 *
//	 */
//	public class CTFDictionaryManager{
//		// key 
//		Map<String, CTFDictionary > dictions = new HashMap<String, CTFDictionary >() ;
//		
//		//table.column 
//		Map<String, CTFDictionary > dictions2 = new HashMap<String, CTFDictionary >() ;
//		
//		public static final CTFDictionaryManager instance = new CTFDictionaryManager() ;
//		
//		private CTFDictionaryManager() {
//			loadDictionarys() ;
//		}
//		
//		
//		/**
//		 * 加载业务字典数据
//		 */
//		private void loadDictionarys() {
//			//读ctf_dictionary
//			//循环ctf_dictionary
//			//读ctf_dictionary_item组装一个 CTFDictionary
//			
//		}
//
//
//
//		CTFDictionary getDictionaryByKey(String dictKey){
//			return dictions.get(dictKey) ;
//		}
//		
//		CTFDictionary getDictionaryByTableName(String tableName, String columnName){
//			
//			return dictions2.get(tableName+"."+columnName);
//		}
//		
//	}
//}
