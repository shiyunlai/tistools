/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.service.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.tis.tools.base.WhereCondition;

import org.tis.tools.dao.log.LogAbfKeywordMapper;
import org.tis.tools.model.po.log.LogAbfKeyword;


/**
 * 记录关键值(LOG_ABF_KEYWORD)数据的基础业务逻辑
 * @author by generated by tools:gen-dao
 *
 */
@Service
public class LogAbfKeywordService {

	@Autowired
	LogAbfKeywordMapper logAbfKeywordMapper;
	
    public void insert(LogAbfKeyword t){
    	logAbfKeywordMapper.insert(t);
    }
    
    public void update(LogAbfKeyword t){
    	logAbfKeywordMapper.update(t); 
    }
   
    public void updateForce(LogAbfKeyword t){
    	logAbfKeywordMapper.updateForce(t); 
    }
   
    public void delete(String guid){
    	logAbfKeywordMapper.delete(guid);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	logAbfKeywordMapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,LogAbfKeyword t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	logAbfKeywordMapper.updateByCondition(map); 
    }

    public List<LogAbfKeyword> query(WhereCondition wc){
    	return logAbfKeywordMapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return logAbfKeywordMapper.count(wc);
    }
   
    public LogAbfKeyword loadByGuid(String guid){
    	return logAbfKeywordMapper.loadByGuid(guid);
    }
}
