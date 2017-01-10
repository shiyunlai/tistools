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

import org.tis.tools.dao.mapper.jnl.TbJnlTransMapper;
import org.tis.tools.model.po.jnl.TbJnlTrans;


/**
 * 交易流水(tb_jnl_trans)基础业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class TbJnlTransService {

	@Autowired
	TbJnlTransMapper tbJnlTransMapper;
	
    public void insert(TbJnlTrans t){
    	tbJnlTransMapper.insert(t);
    }
    
    public void update(TbJnlTrans t){
    	tbJnlTransMapper.update(t); 
    }
   
    public void updateForce(TbJnlTrans t){
    	tbJnlTransMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	tbJnlTransMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	tbJnlTransMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,TbJnlTrans t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	tbJnlTransMapper.updateByCondition(map); 
    }

    public List<TbJnlTrans> query(WhereCondition wc){
    	return tbJnlTransMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return tbJnlTransMapper.count(wc);
    }
   
    public TbJnlTrans loadById(String id){
    	return tbJnlTransMapper.loadById(id);
    }
}
