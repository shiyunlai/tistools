/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.jnl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tis.tools.base.WhereCondition;

import org.tis.tools.dao.mapper.jnl.TbUniqueMapper;
import org.tis.tools.model.po.jnl.TbUnique;


/**
 * 序号表(tb_unique)基础业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class TbUniqueService {

	@Autowired
	TbUniqueMapper tbUniqueMapper;
	
    public void insert(TbUnique t){
    	tbUniqueMapper.insert(t);
    }
    
    public void update(TbUnique t){
    	tbUniqueMapper.update(t); 
    }
   
    public void updateForce(TbUnique t){
    	tbUniqueMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	tbUniqueMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	tbUniqueMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,TbUnique t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	tbUniqueMapper.updateByCondition(map); 
    }

    public List<TbUnique> query(WhereCondition wc){
    	return tbUniqueMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return tbUniqueMapper.count(wc);
    }
   
    public TbUnique loadById(String id){
    	return tbUniqueMapper.loadById(id);
    }
}
