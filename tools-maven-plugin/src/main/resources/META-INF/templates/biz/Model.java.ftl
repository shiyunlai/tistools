/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
 package ${packageName};

import java.util.Date;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * 
 * <pre>
 * 自动生成，不修改。
 * ${table.name}
 * 描述：${table.desc}
 * 定义来自 ： 
 *  业务域：${bizmodelId}
 *  模型：${table.id} ${table.name}
 *  定义文件：${defineFile}
 * </pre>
 * @author mvn bronsp:gendao
 *
 */
public class ${humpClassName(table.id)} implements Serializable {

 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	<#list table.fields as field>
	<#if field.type="string">
	 private String ${field.id};//${field.name}
	 </#if>
	 <#if field.type="long">
	 private Long ${field.id};//${field.name}
	 </#if>
	 <#if field.type="decimal">
	 private Double ${field.id};//${field.name}
	 </#if>
	 <#if field.type="bigdecimal">
	 private BigDecimal ${field.id};//${field.name}
	 </#if>
	  <#if field.type="datetime">
	 private Date ${field.id};//${field.name}
	 </#if>
	  <#if field.type="int">
	 private Integer ${field.id};//${field.name}
	 </#if>
	</#list>
	
	<#list table.fields as field>
	<#if field.type="string">
	public void set${field.id?cap_first}(String ${field.id}) {
        this.${field.id} = ${field.id} == null ? null : ${field.id}.trim();
    }
	 public String get${field.id?cap_first}(){
        return ${field.id};
    }
	 </#if>
	 <#if field.type="long">
	 public void set${field.id?cap_first}(Long ${field.id}) {
        this.${field.id} = ${field.id} ;
    }
	 public Long get${field.id?cap_first}(){
		if(${field.id}==null){
			return 0l;
		}
        return ${field.id};
    }
	 </#if>
	 <#if field.type="decimal">
	 public void set${field.id?cap_first}(Double ${field.id}) {
          this.${field.id} = ${field.id} ;
    }
	 public Double get${field.id?cap_first}(){
	 if(${field.id}==null){
			return 0d;
		}
        return ${field.id};
    }
	 </#if>
	 <#if field.type="bigdecimal">
	 public void set${field.id?cap_first}(BigDecimal ${field.id}) {
          this.${field.id} = ${field.id} ;
    }
	 public BigDecimal get${field.id?cap_first}(){
	 if(${field.id}==null){
			return new BigDecimal(0d);
		}
        return ${field.id};
    }
	 </#if>
	  <#if field.type="datetime">
	 public void set${field.id?cap_first}(Date ${field.id}) {
          this.${field.id} = ${field.id} ;
    }
	 public Date get${field.id?cap_first}(){
        return ${field.id};
    }
	 </#if>
	  <#if field.type="int">
	 public void set${field.id?cap_first}(Integer ${field.id}) {
          this.${field.id} = ${field.id} ;
    }
	 public Integer get${field.id?cap_first}(){
        return ${field.id};
    }
	 </#if>
	</#list>
}