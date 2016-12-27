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

import org.tis.tools.dao.mapper.tuser.TbUserMapper;
import org.tis.tools.model.po.tuser.TbUser;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class TbUserService {

	@Autowired
	TbUserMapper tbUserMapper;
	
    public void insert(TbUser t){
    	tbUserMapper.insert(t);
    }
    
    public void update(TbUser t){
    	tbUserMapper.update(t); 
    }
   
    public void updateForce(TbUser t){
    	tbUserMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	tbUserMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	tbUserMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,TbUser t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	tbUserMapper.updateByCondition(map); 
    }

    public List<TbUser> query(WhereCondition wc){
    	return tbUserMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return tbUserMapper.count(wc);
    }
   
    public TbUser loadById(String id){
    	return tbUserMapper.loadById(id);
    }
}
