package org.tis.tools.service.om;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.dao.om.OmGroupMapper;
import org.tis.tools.dao.om.OmGroupMapperExt;
import org.tis.tools.model.po.om.OmGroup;

/**
 *工作组信息扩展业务逻辑
 * @author megapro
 *
 */
@Service
public class OmGroupServiceExt {
	@Autowired
	OmGroupMapper omGroupMapper;
	@Autowired
	OmGroupMapperExt omGroupMapperExt;
	
	/**
	 * 查询所有根工作组
	 * @return
	 */
	public List<OmGroup> queryAllRoot()  {
		
		List<OmGroup> ogs = omGroupMapperExt.queryAllRoot() ; 
		if( null == ogs ){
			ogs = new ArrayList<OmGroup>() ; 
		}
		return ogs ; 
	}
}
