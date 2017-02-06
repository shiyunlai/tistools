<#list tables as table>
<#if table.type!="view">
CREATE TABLE IF NOT EXISTS `${table.id}` (
<#list table.fields as field>
  <#if field.type=="string">`${field.id}` varchar(${field.length}) DEFAULT NULL comment '${field.name}'</#if><#if field.type=="long">`${field.id}` bigint(${field.length}) DEFAULT NULL comment '${field.name}'</#if><#if field.type=="decimal">`${field.id}` decimal(${field.length}) DEFAULT NULL comment '${field.name}'</#if><#if field.type=="bigdecimal">`${field.id}` decimal(${field.length}) DEFAULT NULL comment '${field.name}'</#if><#if field.type=="datetime">`${field.id}` datetime DEFAULT NULL comment '${field.name}'</#if><#if field.type=="int">`${field.id}` int(${field.length}) DEFAULT NULL comment '${field.name}'</#if><#if field_has_next>,</#if>
</#list>
	<#if table.primarykey??>
	,PRIMARY KEY (`${table.primarykey}`)
	</#if>
  <#if table.uniquekeyList?? && table.uniquekeyList?size &gt;0 >
  <#list table.uniquekeyList as uniquekey>
   ,UNIQUE KEY `${uniquekey}` (`${uniquekey}`)
  </#list>
  </#if>
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
</#if>
</#list>