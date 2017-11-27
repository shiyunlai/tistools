package org.tis.tools.dao.log;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface LogMapperExt {
    public Map<String, Object> getEntityInfo(@Param("tableName") String tableName, @Param("guid") String guid);
}
