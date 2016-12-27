<#assign bizClassNameVar="${humpClassName(table.id)}Biz">
<#assign mapperTypeVar="${humpClassName(table.id)}Mapper">
<#assign mapperVar="${humpClassName(table.id)?uncap_first}Mapper">
<#assign wcClassPackageVar="${mainPackage}.base.WhereCondition">
/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package ${packageName};

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${wcClassPackageVar};

import ${mainPackage}.dao.mapper.${bizmodelId}.${mapperTypeVar};
import ${mainPackage}.model.po.${bizmodelId}.${poClassNameVar};


/**
 * 业务逻辑
 * @author su.zhang
 *
 */
@Service
public class ${bizClassNameVar} {

	@Autowired
	${mapperTypeVar} ${mapperVar};
	
<#if table.type!="view">
    public void insert(${poClassNameVar} t){
    	${mapperVar}.insert(t);
    }
    
    public void update(${poClassNameVar} t){
    	${mapperVar}.update(t); 
    }
   
    public void updateForce(${poClassNameVar} t){
    	${mapperVar}.updateForce(t); 
    }
   
    public void delete(String id){
    	${mapperVar}.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	${mapperVar}.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,${poClassNameVar} t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	${mapperVar}.updateByCondition(map); 
    }
</#if> 

    public List<${poClassNameVar}> query(WhereCondition wc){
    	return ${mapperVar}.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return ${mapperVar}.count(wc);
    }
   
    public ${poClassNameVar} loadById(String id){
    	return ${mapperVar}.loadById(id);
    }
}
