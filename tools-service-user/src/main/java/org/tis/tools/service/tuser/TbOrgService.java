/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.tuser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tis.tools.base.WhereCondition;

import org.tis.tools.dao.mapper.tuser.TbOrgMapper;
import org.tis.tools.model.po.tuser.TbOrg;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class TbOrgService {

	@Autowired
	TbOrgMapper tbOrgMapper;
	
    public void insert(TbOrg t){
    	tbOrgMapper.insert(t);
    }
    
    public void update(TbOrg t){
    	tbOrgMapper.update(t); 
    }
   
    public void updateForce(TbOrg t){
    	tbOrgMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	tbOrgMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	tbOrgMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,TbOrg t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	tbOrgMapper.updateByCondition(map); 
    }

    public List<TbOrg> query(WhereCondition wc){
    	return tbOrgMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return tbOrgMapper.count(wc);
    }
   
    public TbOrg loadById(String id){
    	return tbOrgMapper.loadById(id);
    }
}
