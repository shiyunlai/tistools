package org.tis.tools.service.ac;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.dao.ac.AcFuncMapperExt;
import org.tis.tools.model.po.ac.AcFunc;

import java.util.List;
import java.util.Map;

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
    public List<AcFunc> queryFuncListInApp(String appGuid) {
        return  acFuncMapperExt.queryFuncListInApp(appGuid);
    }

    /**
     * 查询功能资源信息
     * 用于组装路由
     * @param funcGuids 功能GUID集合
     * @return
     */
    public List<Map> queryFuncResourcesWithFuncCode(List<String> funcGuids) {
        StringBuilder sb = new StringBuilder("(");
        if (CollectionUtils.isEmpty(funcGuids)) {
            sb.append("''");
        } else {
            for (int k = 0; k < funcGuids.size(); k++) {
                sb.append("'").append(funcGuids.get(k)).append("'");
                if (k != funcGuids.size() - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append(")");
        return  acFuncMapperExt.queryFuncResourcesWithFuncCode(String.valueOf(sb));
    }


}
