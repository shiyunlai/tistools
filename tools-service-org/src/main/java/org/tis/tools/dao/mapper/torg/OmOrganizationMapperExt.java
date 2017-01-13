package org.tis.tools.dao.mapper.torg;

import java.util.List;

import org.tis.tools.model.po.torg.OmEmployee;

public interface OmOrganizationMapperExt {

	public List<OmEmployee> loadEmpByOrg(String orgId);
}
