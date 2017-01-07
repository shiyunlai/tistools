/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.torg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tis.tools.base.WhereCondition;

import org.tis.tools.dao.mapper.torg.OmOrganizationMapper;
import org.tis.tools.model.po.torg.OmOrganization;


/**
 * 业务逻辑
 * @author by auto-gen
 *
 */
@Service
public class OmOrganizationService {

	@Autowired
	OmOrganizationMapper omOrganizationMapper;
	
    public void insert(OmOrganization t){
    	omOrganizationMapper.insert(t);
    }
    
    public void update(OmOrganization t){
    	omOrganizationMapper.update(t); 
    }
   
    public void updateForce(OmOrganization t){
    	omOrganizationMapper.updateForce(t); 
    }
   
    public void delete(String id){
    	omOrganizationMapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	omOrganizationMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,OmOrganization t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	omOrganizationMapper.updateByCondition(map); 
    }

    public List<OmOrganization> query(WhereCondition wc){
    	return omOrganizationMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return omOrganizationMapper.count(wc);
    }
   
    public OmOrganization loadById(String id){
    	return omOrganizationMapper.loadById(id);
    }
}
