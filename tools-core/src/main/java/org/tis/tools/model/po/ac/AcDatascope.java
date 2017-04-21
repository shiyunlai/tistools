/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.model.po.ac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Time;
import java.util.Date;

import org.tis.tools.common.utils.StringUtil;

/**
 * 
 * <pre>
 * 数据范围权限
 * 模型文件 ： /Users/megapro/Develop/tis/tools/tools-core/model/ABF-mysql.erm
 * 业务域：ac
 * 模型：AC_DATASCOPE 数据范围权限
 *
 * 定义能够操作某个表数据的范围
 *
 * </pre>
 * @author generated by tools:gen-dao
 *
 */
public class AcDatascope implements Serializable {

 	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/* AC_DATASCOPE table's columns definition */
	/** GUID ：数据主键<br/><br/>全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成； */
	public static final String GUID = "guid" ; 
	/** GUID_ENTITY ：数据主键<br/><br/> */
	public static final String GUID_ENTITY = "guid_entity" ; 
	/** PRIV_NAME ：数据范围权限名称<br/><br/> */
	public static final String PRIV_NAME = "priv_name" ; 
	/** DATA_OP_TYPE ：数据操作类型<br/><br/>取值来自业务菜单：DICT_AC_DATAOPTYPE 对本数据范围内的数据，可以做哪些操作：增加、修改、删除、查询 如果为空，表示都不限制； 多个操作用逗号分隔，如： 增加,修改,删除 */
	public static final String DATA_OP_TYPE = "data_op_type" ; 
	/** ENTITY_NAME ：实体名称<br/><br/> */
	public static final String ENTITY_NAME = "entity_name" ; 
	/** FILTER_SQL_STRING ：过滤SQL<br/><br/>例： (orgSEQ IS NULL or orgSEQ like '$[SessionEntity/orgSEQ]%') 通过本SQL，限定了数据范围 */
	public static final String FILTER_SQL_STRING = "filter_sql_string" ; 
	
	
	/** 字段类型：varchar<br/>字段名：数据主键<br/>描述：全局唯一标识符（GUID，Globally Unique Identifier），系统自动生成； */
	private String guid ;
	
	/** 字段类型：varchar<br/>字段名：数据主键<br/>描述： */
	private String guidEntity ;
	
	/** 字段类型：varchar<br/>字段名：数据范围权限名称<br/>描述： */
	private String privName ;
	
	/** 字段类型：varchar<br/>字段名：数据操作类型<br/>描述：取值来自业务菜单：DICT_AC_DATAOPTYPE 对本数据范围内的数据，可以做哪些操作：增加、修改、删除、查询 如果为空，表示都不限制； 多个操作用逗号分隔，如： 增加,修改,删除 */
	private String dataOpType ;
	
	/** 字段类型：varchar<br/>字段名：实体名称<br/>描述： */
	private String entityName ;
	
	/** 字段类型：varchar<br/>字段名：过滤SQL<br/>描述：例： (orgSEQ IS NULL or orgSEQ like '$[SessionEntity/orgSEQ]%') 通过本SQL，限定了数据范围 */
	private String filterSqlString ;
	
	
	/**
	 * Set the 数据主键.
	 * 
	 * @param guid
	 *            数据主键
	 */
	public void setGuid(String guid) {
 		this.guid = guid == null ? null : guid.trim() ;
    }
    
    /**
	 * Get the 数据主键.
	 * 
	 * @return 数据主键
	 */
	public String getGuid(){
		return this.guid ;
    }
	
	/**
	 * Set the 数据主键.
	 * 
	 * @param guidEntity
	 *            数据主键
	 */
	public void setGuidEntity(String guidEntity) {
 		this.guidEntity = guidEntity == null ? null : guidEntity.trim() ;
    }
    
    /**
	 * Get the 数据主键.
	 * 
	 * @return 数据主键
	 */
	public String getGuidEntity(){
		return this.guidEntity ;
    }
	
	/**
	 * Set the 数据范围权限名称.
	 * 
	 * @param privName
	 *            数据范围权限名称
	 */
	public void setPrivName(String privName) {
 		this.privName = privName == null ? null : privName.trim() ;
    }
    
    /**
	 * Get the 数据范围权限名称.
	 * 
	 * @return 数据范围权限名称
	 */
	public String getPrivName(){
		return this.privName ;
    }
	
	/**
	 * Set the 数据操作类型.
	 * 
	 * @param dataOpType
	 *            数据操作类型
	 */
	public void setDataOpType(String dataOpType) {
 		this.dataOpType = dataOpType == null ? null : dataOpType.trim() ;
    }
    
    /**
	 * Get the 数据操作类型.
	 * 
	 * @return 数据操作类型
	 */
	public String getDataOpType(){
		return this.dataOpType ;
    }
	
	/**
	 * Set the 实体名称.
	 * 
	 * @param entityName
	 *            实体名称
	 */
	public void setEntityName(String entityName) {
 		this.entityName = entityName == null ? null : entityName.trim() ;
    }
    
    /**
	 * Get the 实体名称.
	 * 
	 * @return 实体名称
	 */
	public String getEntityName(){
		return this.entityName ;
    }
	
	/**
	 * Set the 过滤SQL.
	 * 
	 * @param filterSqlString
	 *            过滤SQL
	 */
	public void setFilterSqlString(String filterSqlString) {
 		this.filterSqlString = filterSqlString == null ? null : filterSqlString.trim() ;
    }
    
    /**
	 * Get the 过滤SQL.
	 * 
	 * @return 过滤SQL
	 */
	public String getFilterSqlString(){
		return this.filterSqlString ;
    }
	
	public String toString(){
		return StringUtil.toString(this) ; 
	}
}