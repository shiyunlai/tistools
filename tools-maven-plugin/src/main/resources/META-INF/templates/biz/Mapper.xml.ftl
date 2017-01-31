<#assign wcClassPackageVar="${mainPackage}.base.WhereCondition">
<#assign poClassPackageVar="${mainPackage}.model.po.${bizmodelId}.${poClassNameVar}">
<#assign mapperJavaPackageVar="${mainPackage}.dao.mapper.${bizmodelId}.${poClassNameVar}Mapper">

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC 
	"-//mybatis.org//DTD Mapper 3.0//EN" 
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	
<mapper namespace="${mapperJavaPackageVar}">
  
  	<insert id="insert" parameterType="${poClassPackageVar}">
	    INSERT INTO ${table.id} (<#list table.fields as field><#if field.physical !="false"><#if field_index == 0>${field.id}<#else>,${field.id}</#if></#if>
  </#list>)
	    VALUES (<#list table.fields as field><#if field.physical !="false"><#if field.type =="string">#<#nt>{${field.id},jdbcType=VARCHAR}</#if><#if field.type =="long">#<#nt>{${field.id},jdbcType=BIGINT}</#if><#if field.type =="decimal">#<#nt>{${field.id},jdbcType=DECIMAL}</#if><#if field.type =="bigdecimal">#<#nt>{${field.id},jdbcType=DECIMAL}</#if><#if field.type =="datetime">#<#nt>{${field.id},jdbcType=TIMESTAMP}</#if><#if field.type =="int">#<#nt>{${field.id},jdbcType=INTEGER}</#if><#if field_has_next>,</#if></#if>
  </#list>)
 	</insert>
 	
 	<update id="update" parameterType="${poClassPackageVar}">
    	UPDATE ${table.id} 
	    <set>
<#list table.fields as field><#if field.physical !="false">
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
	<#if field.type =="bigdecimal">
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
	</#if>
</#list>
	    </set>
	    WHERE id = #<#nt>{id}
 	</update>
 	
 	<update id="updateForce" parameterType="${poClassPackageVar}">
    	UPDATE ${table.id} 
	    <set>
	    	<#list table.fields as field><#if field.physical !="false">
	<#if field.type =="string">
        ${field.id} = #<#nt>{${field.id},jdbcType=VARCHAR},
	</#if>
	<#if field.type =="long">
        ${field.id} = #<#nt>{${field.id},jdbcType=BIGINT},
	</#if>
	<#if field.type =="decimal">
        ${field.id} = #<#nt>{${field.id},jdbcType=DECIMAL},
	</#if>
	<#if field.type =="bigdecimal">
        ${field.id} = #<#nt>{${field.id},jdbcType=DECIMAL},
	</#if>
	<#if field.type =="datetime">
        ${field.id} = #<#nt>{${field.id},jdbcType=TIMESTAMP},
	</#if>
	<#if field.type =="int">
        ${field.id} = #<#nt>{${field.id},jdbcType=INTEGER},
	</#if></#if>
	</#list>
	    </set>
	    WHERE id = #<#nt>{id}
 	</update>
 	 
    <delete id="delete" parameterType="java.lang.String">
	  	delete from ${table.id} where id = #<#nt>{id}
	</delete>
	
	<delete id="deleteByCondition" parameterType="${wcClassPackageVar}">
	  	delete from ${table.id} <include refid="Where_Clause" />
	</delete>
	
	 <update id="updateByCondition" parameterType="map">
    	UPDATE ${table.id} 
	   <set>
	    	<#list table.fields as field><#if field.physical !="false">
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
	<#if field.type =="bigdecimal">
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
	</#if></#if>
	</#list>
	    </set>
	   <where>$<#nt>{wc.condition}</where>
 	</update>
	 
	<select id="loadById" parameterType="String" resultType="${poClassPackageVar}">
		select <include refid="Base_Column_List"/> from ${table.id} where id = #<#nt>{id}
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
	  <#list table.fields as field><#if field.physical !="false"><#if field_index == 0>${field.id}<#else>,${field.id}</#if></#if>
  </#list>
	</sql>
</mapper>