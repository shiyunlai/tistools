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

import org.tis.tools.dao.mapper.jnl.TbJnlPrefilledMapper;
import org.tis.tools.model.po.jnl.TbJnlPrefilled;


/**
 * 预填流水(tb_jnl_prefilled)基础业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class TbJnlPrefilledService {

	@Autowired
	TbJnlPrefilledMapper tbJnlPrefilledMapper;
	
    public void insert(TbJnlPrefilled t){
    	tbJnlPrefilledMapper.insert(t);
    }
    
    public void update(TbJnlPrefilled t){
    	tbJnlPrefilledMapper.update(t); 
    }
   
    public void updateForce(TbJnlPrefilled t){
    	tbJnlPrefilledMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	tbJnlPrefilledMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	tbJnlPrefilledMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,TbJnlPrefilled t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	tbJnlPrefilledMapper.updateByCondition(map); 
    }

    public List<TbJnlPrefilled> query(WhereCondition wc){
    	return tbJnlPrefilledMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return tbJnlPrefilledMapper.count(wc);
    }
   
    public TbJnlPrefilled loadById(String id){
    	return tbJnlPrefilledMapper.loadById(id);
    }
}
