package org.tools.design.provider.service;

import org.tis.tools.model.po.sys.SysDict;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.rservice.sys.capable.IDictRService;

public class SYSRServiceTest {

	IDictRService dictRService  ;

	/**
	 * @return the dictRService
	 */
	public IDictRService getDictRService() {
		return dictRService;
	}

	/**
	 * @param dictRService the dictRService to set
	 */
	public void setDictRService(IDictRService dictRService) {
		this.dictRService = dictRService;
	} 
	
	public void callSYSRService() throws Exception {

		SysDict dict = new SysDict() ;
		dictRService.addDict(dict);
		
		SysDictItem dictItem = new SysDictItem() ; 
		dictRService.addDictItem(dictItem);
		
		dictRService.queryActualValue("DICT_TEST_BY_JAVA", "DICT_TEST_ITEM") ;
	}
}
