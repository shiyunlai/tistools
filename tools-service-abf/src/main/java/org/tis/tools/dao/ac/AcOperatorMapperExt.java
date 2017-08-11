package org.tis.tools.dao.ac;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AcOperatorMapperExt {

    List<Map> queryOperatorIdentityreses(@Param("identityGuid") String identityGuid);
}
