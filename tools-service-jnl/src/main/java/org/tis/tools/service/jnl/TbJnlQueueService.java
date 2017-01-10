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

import org.tis.tools.dao.mapper.jnl.TbJnlQueueMapper;
import org.tis.tools.model.po.jnl.TbJnlQueue;


/**
 * 排队流水(tb_jnl_queue)基础业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class TbJnlQueueService {

	@Autowired
	TbJnlQueueMapper tbJnlQueueMapper;
	
    public void insert(TbJnlQueue t){
    	tbJnlQueueMapper.insert(t);
    }
    
    public void update(TbJnlQueue t){
    	tbJnlQueueMapper.update(t); 
    }
   
    public void updateForce(TbJnlQueue t){
    	tbJnlQueueMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	tbJnlQueueMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	tbJnlQueueMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,TbJnlQueue t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	tbJnlQueueMapper.updateByCondition(map); 
    }

    public List<TbJnlQueue> query(WhereCondition wc){
    	return tbJnlQueueMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return tbJnlQueueMapper.count(wc);
    }
   
    public TbJnlQueue loadById(String id){
    	return tbJnlQueueMapper.loadById(id);
    }
}
