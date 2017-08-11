package org.tis.tools.service.ac;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.dao.ac.AcOperatorMapperExt;

import java.util.List;
import java.util.Map;

@Service
public class AcOperatorServiceExt {

    @Autowired
    AcOperatorMapperExt acOperatorMapperExt;

    public List<Map> queryOperatorIdentityreses(String identityGuid) {
        return acOperatorMapperExt.queryOperatorIdentityreses(identityGuid);
    }
}
