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

import org.tis.tools.dao.mapper.jnl.TbJnlHosttransMapper;
import org.tis.tools.model.po.jnl.TbJnlHosttrans;


/**
 * 主机流水(tb_jnl_hosttrans)基础业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class TbJnlHosttransService {

	@Autowired
	TbJnlHosttransMapper tbJnlHosttransMapper;
	
    public void insert(TbJnlHosttrans t){
    	tbJnlHosttransMapper.insert(t);
    }
    
    public void update(TbJnlHosttrans t){
    	tbJnlHosttransMapper.update(t); 
    }
   
    public void updateForce(TbJnlHosttrans t){
    	tbJnlHosttransMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	tbJnlHosttransMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	tbJnlHosttransMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,TbJnlHosttrans t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	tbJnlHosttransMapper.updateByCondition(map); 
    }

    public List<TbJnlHosttrans> query(WhereCondition wc){
    	return tbJnlHosttransMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return tbJnlHosttransMapper.count(wc);
    }
   
    public TbJnlHosttrans loadById(String id){
    	return tbJnlHosttransMapper.loadById(id);
    }
}
