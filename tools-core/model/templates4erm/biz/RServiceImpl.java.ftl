<#assign poServiceClassVar="${poClassName}Service">
<#assign poServiceAttrVar="${poClassName?uncap_first}Service">

/**
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package ${packageName};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ${mainPackage}.base.WhereCondition;
import ${mainPackage}.model.po.${bizmodelId}.${poClassName};
import ${mainPackage}.rservice.${bizmodelId}.${apiClassName};
import ${mainPackage}.service.${bizmodelId}.${poServiceClassVar};

import com.alibaba.dubbo.config.annotation.Service;

/**
 * ${table.name}(${table.id}) 基本远程服务实现(服务提供者)
 * 
 * @author by auto-gen
 *
 */
//TEMPLATES
@Service(group = "${bizmodelId}", version = "1.0", interfaceClass = ${apiClassName}.class, timeout = 2000, document = "${table.name}的基础远程服务")
public class ${implClassName} implements ${apiClassName} {

	@Autowired
	${poServiceClassVar} ${poServiceAttrVar};

	@Override
	public void insert(${poClassName} t) {
		${poServiceAttrVar}.insert(t);
	}

	@Override
	public void update(${poClassName} t) {
		${poServiceAttrVar}.update(t);
	}

	@Override
	public void updateForce(${poClassName} t) {
		${poServiceAttrVar}.updateForce(t);
	}

	@Override
	public void delete(String id) {
		${poServiceAttrVar}.delete(id);
	}

	@Override
	public void deleteByCondition(WhereCondition wc) {
		${poServiceAttrVar}.deleteByCondition(wc);
	}

	@Override
	public void updateByCondition(WhereCondition wc, ${poClassName} t) {
		${poServiceAttrVar}.updateByCondition(wc,t);
	}

	@Override
	public List<${poClassName}> query(WhereCondition wc) {
		return ${poServiceAttrVar}.query(wc);
	}

	@Override
	public int count(WhereCondition wc) {
		return ${poServiceAttrVar}.count(wc);
	}

	@Override
	public ${poClassName} loadById(String id) {
		return ${poServiceAttrVar}.loadById(id);
	}

}
