<#assign classNameVar="${humpClassName(table.id)}Mapper">
/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package ${packageName};

import ${mainPackage}.base.BaseMapper;
import ${mainPackage}.model.po.${bizmodelId}.${poClassNameVar};

/**
 * 
 * ${table.name}的DAO
 * 
 * @author su.zhang
 *
 */
public interface ${classNameVar} extends BaseMapper<${poClassNameVar}>{

}
