package d.service.biz;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import d.dao.mapper.${table.id?cap_first}Mapper;
import d.dao.model.${table.id?cap_first};
import d.dao.model.WhereCondition;
/**
 * 服务层
 * @author su.zhang
 *
 */
@Service
public class ${table.id?cap_first}Service {
	@Autowired
	${table.id?cap_first}Mapper ${table.id}Mapper;
<#if table.type!="view">
    public void insert(${table.id?cap_first} t){
    	${table.id}Mapper.insert(t);
    }
    
    public void update(${table.id?cap_first} t){
    	${table.id}Mapper.update(t); 
    }
   
    public void updateForce(${table.id?cap_first} t){
    	${table.id}Mapper.updateForce(t); 
    }
   
    public void delete(String id){
    	${table.id}Mapper.delete(id);
    }
   
    public void deleteByCondition(WhereCondition wc){
    	${table.id}Mapper.deleteByCondition(wc); 
    }
	
	public void updateByCondition(WhereCondition wc,${table.id?cap_first} t){
    	Map map  = new HashMap();
    	map.put("domain", t);
    	map.put("wc", wc);
    	${table.id}Mapper.updateByCondition(map); 
    }
   </#if> 
    public List<${table.id?cap_first}> query(WhereCondition wc){
    	return ${table.id}Mapper.query(wc); 
    }
    
    public int count(WhereCondition wc){
    	return ${table.id}Mapper.count(wc);
    }
   
    public ${table.id?cap_first} loadById(String id){
    	return ${table.id}Mapper.loadById(id);
    }
}
