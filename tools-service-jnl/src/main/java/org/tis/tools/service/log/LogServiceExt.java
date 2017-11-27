package org.tis.tools.service.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.dao.log.LogMapperExt;

import java.util.Map;

@Service
public class LogServiceExt {

    @Autowired
    LogMapperExt logMapperExt;

    public Map<String, Object> getEntityInfo(String tableName, String guid) {
        return logMapperExt.getEntityInfo(tableName, guid);
    }
}
