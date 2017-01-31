INSERT INTO `ziduanshezhi` (`id`, `ziduanmingcheng`, `ziduandaima`, `ziduanbieming`, `ziduanleixing`, `ziduanbiaoqian`, `ziduanpaixu`, `zhuti`, `qiyong`, `delstatus`,`ziduankuozhan`,`ziduanxingzhi`, `createTime`, `modifiedTime`) VALUES
<#assign a=0>
<#list pages as page>
<#list page.table as field><#assign a=field_index/>
('${page_index}${field_index}', '${field.name}', '${field.id}', '${field.name}', '<#if field.select??>select</#if>', '${field.tag!}', '1', '${page.id}', 'true', '','${field.select!''}','1', NULL, NULL)<#if field_has_next>,</#if>
</#list>;
INSERT INTO `ziduanshezhi` (`id`, `ziduanmingcheng`, `ziduandaima`, `ziduanbieming`, `ziduanleixing`, `ziduanbiaoqian`, `ziduanpaixu`, `zhuti`, `qiyong`, `delstatus`,`ziduankuozhan`,`ziduanxingzhi`, `createTime`, `modifiedTime`) VALUES
('${page_index}${a+1}', '扩展字段0', 'ext0', '', '', '', '1', '${page.id}', 'false', '','','2', NULL, NULL),
('${page_index}${a+2}', '扩展字段1', 'ext1', '', '', '', '1', '${page.id}', 'false', '','','2', NULL, NULL),
('${page_index}${a+3}', '扩展字段2', 'ext2', '', '', '', '1', '${page.id}', 'false', '','','2', NULL, NULL),
('${page_index}${a+4}', '扩展字段3', 'ext3', '', '', '', '1', '${page.id}', 'false', '','','2', NULL, NULL),
('${page_index}${a+5}', '扩展字段4', 'ext4', '', '', '', '1', '${page.id}', 'false', '','','2', NULL, NULL),
('${page_index}${a+6}', '扩展字段5', 'ext5', '', '', '', '1', '${page.id}', 'false', '','','2', NULL, NULL),
('${page_index}${a+7}', '扩展字段6', 'ext6', '', '', '', '1', '${page.id}', 'false', '','','2', NULL, NULL),
('${page_index}${a+8}', '扩展字段7', 'ext7', '', '', '', '1', '${page.id}', 'false', '','','2', NULL, NULL),
('${page_index}${a+9}', '扩展字段8', 'ext8', '', '', '', '1', '${page.id}', 'false', '','','2', NULL, NULL),
('${page_index}${a+10}', '扩展字段9', 'ext9', '', '', '', '1', '${page.id}', 'false', '','','2', NULL, NULL)
;
</#list>
