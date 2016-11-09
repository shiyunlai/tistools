<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC 
	"-//mybatis.org//DTD Mapper 3.0//EN" 
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	
<mapper namespace="d.dao.mapper.${table.id?cap_first}Mapper">
  
  	<insert id="insert" parameterType="d.dao.model.${table.id?cap_first}">
	    INSERT INTO ${table.id} (<#list table.fields as field><#if field_index == 0>${field.id}<#else>,${field.id}</#if>
  </#list>)
	    VALUES (<#list table.fields as field><#if field.type =="string">#<#nt>{${field.id},jdbcType=VARCHAR}</#if><#if field.type =="long">#<#nt>{${field.id},jdbcType=BIGINT}</#if><#if field.type =="decimal">#<#nt>{${field.id},jdbcType=DECIMAL}</#if><#if field.type =="datetime">#<#nt>{${field.id},jdbcType=TIMESTAMP}</#if><#if field.type =="int">#<#nt>{${field.id},jdbcType=INTEGER}</#if><#if field_has_next>,</#if>
  </#list>)
 	</insert>
 	
 	<update id="update" parameterType="d.dao.model.${table.id?cap_first}">
    	UPDATE ${table.id} 
	    <set>
	    	<#list table.fields as field>
	<#if field.type =="string">
		<if test="${field.id} != null" >
        ${field.id} = #<#nt>{${field.id},jdbcType=VARCHAR},
      </if>
	</#if>
	<#if field.type =="long">
		<if test="${field.id} != null" >
        ${field.id} = #<#nt>{${field.id},jdbcType=BIGINT},
      </if>
	</#if>
	<#if field.type =="decimal">
		<if test="${field.id} != null" >
        ${field.id} = #<#nt>{${field.id},jdbcType=DECIMAL},
      </if>
	</#if>
	<#if field.type =="datetime">
		<if test="${field.id} != null" >
        ${field.id} = #<#nt>{${field.id},jdbcType=TIMESTAMP},
      </if>
	</#if>
	<#if field.type =="int">
		<if test="${field.id} != null" >
        ${field.id} = #<#nt>{${field.id},jdbcType=INTEGER},
      </if>
	</#if>
	</#list>
	    </set>
	    WHERE 1=1 and id = #<#nt>{id}
	    
 	</update>
 	
 	<update id="updateForce" parameterType="d.dao.model.${table.id?cap_first}">
    	UPDATE ${table.id} 
	    <set>
	    	<#list table.fields as field>
	<#if field.type =="string">
        ${field.id} = #<#nt>{${field.id},jdbcType=VARCHAR},
	</#if>
	<#if field.type =="long">
        ${field.id} = #<#nt>{${field.id},jdbcType=BIGINT},
	</#if>
	<#if field.type =="decimal">
        ${field.id} = #<#nt>{${field.id},jdbcType=DECIMAL},
	</#if>
	<#if field.type =="datetime">
        ${field.id} = #<#nt>{${field.id},jdbcType=TIMESTAMP},
	</#if>
	<#if field.type =="int">
        ${field.id} = #<#nt>{${field.id},jdbcType=INTEGER},
	</#if>
	</#list>
	    </set>
	    WHERE 1=1 and id = #<#nt>{id}
 	</update>
 	 
    <delete id="delete" parameterType="java.lang.String">
	  	delete from ${table.id} where 1=1 and id = #<#nt>{id}
	</delete>
	
	<delete id="deleteByCondition" parameterType="d.dao.model.WhereCondition">
	  	delete from ${table.id} <include refid="Where_Clause" />
	</delete>
	
	 <update id="updateByCondition" parameterType="map">
    	UPDATE ${table.id} 
	   <set>
	    	<#list table.fields as field>
	<#if field.type =="string">
		<if test="domain.${field.id} != null" >
        ${field.id} = #<#nt>{domain.${field.id},jdbcType=VARCHAR},
      </if>
	</#if>
	<#if field.type =="long">
		<if test="domain.${field.id} != null" >
        ${field.id} = #<#nt>{domain.${field.id},jdbcType=BIGINT},
      </if>
	</#if>
	<#if field.type =="decimal">
		<if test="domain.${field.id} != null" >
        ${field.id} = #<#nt>{domain.${field.id},jdbcType=DECIMAL},
      </if>
	</#if>
	<#if field.type =="datetime">
		<if test="domain.${field.id} != null" >
        ${field.id} = #<#nt>{domain.${field.id},jdbcType=TIMESTAMP},
      </if>
	</#if>
	<#if field.type =="int">
		<if test="domain.${field.id} != null" >
        ${field.id} = #<#nt>{domain.${field.id},jdbcType=INTEGER},
      </if>
	</#if>
	</#list>
	    </set>
	   <where>$<#nt>{wc.condition}</where>
 	</update>
	 
	<select id="loadById" parameterType="String" resultType="d.dao.model.${table.id?cap_first}">
		select <include refid="Base_Column_List"/> from ${table.id} where 1=1 and id = #<#nt>{id}
	</select>
	
    <select id="query" resultType="d.dao.model.${table.id?cap_first}" parameterType="d.dao.model.WhereCondition" >
    	<if test="length > 0 and url == 'oracle'" >
    	select <include refid="Base_Column_List" /> from
   			(select a.*,rownum row_num from
      			(</if>
      			select <include refid="Base_Column_List" /> from ${table.id} <include refid="Where_Clause" />
        			<if test="orderBy != null" >$<#nt>{orderBy}</if>
        			<if test="length > 0 and url == 'mysql'" >LIMIT $<#nt>{offset}, $<#nt>{length}</if>
        		<if test="length > 0 and url == 'oracle'" >) a
   			) b 
		where b.row_num between $<#nt>{offset} and $<#nt>{length}</if>
    
  	</select>
  
   	<select id="count" resultType="int" parameterType="d.dao.model.WhereCondition" >
	    select count(*) from ${table.id} <include refid="Where_Clause"/>
  	</select>
  
	<sql id="Where_Clause">
	    <if test="condition != null"><where>$<#nt>{condition}</where></if> 
	</sql>
  
	<sql id="Base_Column_List" >
	  <#list table.fields as field><#if field_index == 0>${field.id}<#else>,${field.id}</#if>
  </#list>
	</sql>
</mapper>