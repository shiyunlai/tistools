<#assign wcClassPackageVar="${mainPackage}.base.WhereCondition">
<#assign poClassPackageVar="${mainPackage}.model.po.${bizmodelId}.${poClassNameVar}">
<#assign mapperJavaPackageVar="${mainPackage}.dao.${bizmodelId}.${poClassNameVar}Mapper">
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC 
	"-//mybatis.org//DTD Mapper 3.0//EN" 
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	
<mapper namespace="${mapperJavaPackageVar}">
  
  	<insert id="insert" parameterType="${poClassPackageVar}">
	    INSERT INTO ${table.id} 
	    (
<#list table.fields as field>
<#if field.physical !="false">
	    	<#if field_index == 0> ${field.id}<#else>,${field.id}</#if>
</#if>
</#list>
	    )
	    VALUES 
	    (
<#list table.fields as field>
<#if field.physical !="false">
			#<#nt>{${humpClassName(field.id)?uncap_first},jdbcType=${field.type?upper_case}}<#if field_has_next>,</#if>
</#if>
</#list>
  		)
 	</insert>
 	
 	<update id="update" parameterType="${poClassPackageVar}">
    	UPDATE ${table.id} 
	    <set>
<#list table.fields as field>
<#if field.physical !="false">
		<if test="${field.id} != null" >
			${field.id} = #<#nt>{${humpClassName(field.id)?uncap_first},jdbcType=${field.type?upper_case}}<#if field_has_next>,</#if>
		</if>
</#if>
</#list>
	    </set>
	    WHERE guid = #<#nt>{guid}
 	</update>
 	
 	<update id="updateForce" parameterType="${poClassPackageVar}">
    	UPDATE ${table.id} 
	    <set>
<#list table.fields as field>
<#if field.physical !="false">
		${field.id} = #<#nt>{${humpClassName(field.id)?uncap_first},jdbcType=${field.type?upper_case}}<#if field_has_next>,</#if>
</#if>
</#list>
	    </set>
	    WHERE guid = #<#nt>{guid}
 	</update>
 	 
    <delete id="delete" parameterType="java.lang.String">
	  	delete from ${table.id} where guid = #<#nt>{guid}
	</delete>
	
	<delete id="deleteByCondition" parameterType="${wcClassPackageVar}">
	  	delete from ${table.id} <include refid="Where_Clause" />
	</delete>
	
	 <update id="updateByCondition" parameterType="map">
    	UPDATE ${table.id} 
	   <set>
<#list table.fields as field>
<#if field.physical !="false">
		<if test="domain.${field.id} != null" >
        	${field.id} = #<#nt>{domain.${humpClassName(field.id)?uncap_first},jdbcType=${field.type?upper_case}}
      	</if>
</#if>
</#list>
	    </set>
	   <where>$<#nt>{wc.condition}</where>
 	</update>
	 
	<select id="loadByGuid" parameterType="String" resultType="${poClassPackageVar}">
		select <include refid="Base_Column_List"/> from ${table.id} where guid = #<#nt>{guid}
	</select>
	
    <select id="query" resultType="${poClassPackageVar}" parameterType="${wcClassPackageVar}" >
    	select <include refid="Base_Column_List" /> from ${table.id} <include refid="Where_Clause" />
        <if test="orderBy != null" >$<#nt>{orderBy}</if>
        <if test="length > 0" >LIMIT $<#nt>{offset}, $<#nt>{length}</if>
  	</select>
  
   	<select id="count" resultType="int" parameterType="${wcClassPackageVar}" >
	    select count(*) from ${table.id} <include refid="Where_Clause"/>
  	</select>
  
	<sql id="Where_Clause">
	    <if test="condition != null"><where>$<#nt>{condition}</where></if> 
	</sql>
  
	<sql id="Base_Column_List" >
	  <#list table.fields as field><#if field.physical !="false">
		<#if field_index == 0> ${field.id}<#else>,${field.id}</#if></#if>
	  </#list>
	</sql>
</mapper>