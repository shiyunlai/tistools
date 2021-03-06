/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.ac;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tis.tools.base.WhereCondition;

import org.tis.tools.dao.ac.AcOperatorShortcutMapper;
import org.tis.tools.model.po.ac.AcOperatorShortcut;


/**
 * 操作员快捷菜单(AC_OPERATOR_SHORTCUT)数据的基础业务逻辑
 * @author by generated by tools:gen-dao
 *
 */
@Service
public class AcOperatorShortcutService {

	@Autowired
	AcOperatorShortcutMapper acOperatorShortcutMapper;
	
    public void insert(AcOperatorShortcut t){
    	acOperatorShortcutMapper.insert(t);
    }
    
    public void update(AcOperatorShortcut t){
    	acOperatorShortcutMapper.update(t); 
    }
   
    public void updateForce(AcOperatorShortcut t){
    	acOperatorShortcutMapper.updateForce(t); 
    }
   
    public void delete(String guid){
    	acOperatorShortcutMapper.delete(guid);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	acOperatorShortcutMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,AcOperatorShortcut t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	acOperatorShortcutMapper.updateByCondition(map); 
    }

    public List<AcOperatorShortcut> query(WhereCondition wc){
    	return acOperatorShortcutMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return acOperatorShortcutMapper.count(wc);
    }
   
    public AcOperatorShortcut loadByGuid(String guid){
    	return acOperatorShortcutMapper.loadByGuid(guid);
    }
}
