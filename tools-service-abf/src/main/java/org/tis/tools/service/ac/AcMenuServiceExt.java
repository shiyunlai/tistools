package org.tis.tools.service.ac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.dao.ac.AcMenuMapperExt;
import org.tis.tools.model.po.ac.AcMenu;

import java.util.List;

@Service
public class AcMenuServiceExt {
    @Autowired
    AcMenuMapperExt acMenuMapperExt;


    /**
     * 获取用户下的功能菜单
     * @param userId
     * @return
     */
    public List<AcMenu> getMenuByUserId(String userId, String appGuid) {
        return  acMenuMapperExt.getMenuByUserId(userId, appGuid);
    }

    /**
     * 获取身份下的功能菜单
     * @param identityGuid
     * @param appGuid
     * @return
     */
    public List<AcMenu> getMenuByUserIdentity(String identityGuid, String appGuid) {
        return  acMenuMapperExt.getMenuByUserIdentity(identityGuid, appGuid);
    }

}
