package org.tis.tools.service.ac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.dao.ac.AcFuncMapperExt;
import org.tis.tools.model.po.ac.AcFunc;

import java.util.List;

@Service
public class AcFuncServiceExt {
    @Autowired
    AcFuncMapperExt acFuncMapperExt;

    /**
     * 查询应用下的所有功能
     *
     * @param appGuid
     * @return
     */
    List<AcFunc> queryFuncListInApp(String appGuid) {
        return  acFuncMapperExt.queryFuncListInApp(appGuid);
    }
}
