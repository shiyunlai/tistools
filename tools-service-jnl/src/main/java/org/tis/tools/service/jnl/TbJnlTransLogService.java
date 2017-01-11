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

import org.tis.tools.dao.mapper.jnl.TbJnlTransLogMapper;
import org.tis.tools.model.po.jnl.TbJnlTransLog;


/**
 * 交易办理日志(tb_jnl_trans_log)基础业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class TbJnlTransLogService {

	@Autowired
	TbJnlTransLogMapper tbJnlTransLogMapper;
	
    public void insert(TbJnlTransLog t){
    	tbJnlTransLogMapper.insert(t);
    }
    
    public void update(TbJnlTransLog t){
    	tbJnlTransLogMapper.update(t); 
    }
   
    public void updateForce(TbJnlTransLog t){
    	tbJnlTransLogMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	tbJnlTransLogMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	tbJnlTransLogMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,TbJnlTransLog t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	tbJnlTransLogMapper.updateByCondition(map); 
    }

    public List<TbJnlTransLog> query(WhereCondition wc){
    	return tbJnlTransLogMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return tbJnlTransLogMapper.count(wc);
    }
   
    public TbJnlTransLog loadById(String id){
    	return tbJnlTransLogMapper.loadById(id);
    }
}
