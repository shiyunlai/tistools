/**
 * 
 */
package org.tis.tools.rservice.om.capable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.po.om.OmPosition;
import org.tis.tools.rservice.om.exception.OrgManagementException;
import org.tis.tools.service.om.IGenOrgCode;

/**
 * <pre>
 * 机构（Organization）管理服务功能的实现类
 * 
 * <pre>
 * 
 * @author megapro
 *
 */
public class OrgRServiceImpl implements IOrgRService {

	@Autowired
	IGenOrgCode boshGenOrgCode ;
	
	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#genOrgCode(java.lang.String, java.lang.String)
	 */
	@Override
	public String genOrgCode(String areaCode, String orgDegree, String orgType) throws OrgManagementException {
		Map<String,String> parms = new HashMap<String,String>() ;
		parms.put("orgDegree", orgDegree) ; 
		parms.put("areaCode", areaCode) ; 
		return boshGenOrgCode.genOrgCode(parms);
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#createRootOrg(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public OmOrg createRootOrg(String orgCode, String orgName, String orgType, String orgDegree)
			throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#createChildOrg(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public OmOrg createChildOrg(String orgCode, String orgName, String orgType, String orgDegree, String parentsOrgCode,
			int sortNo) throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#createChildOrg(org.tis.tools.model.po.om.OmOrg)
	 */
	@Override
	public OmOrg createChildOrg(OmOrg newOrg) throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#updateOrg(org.tis.tools.model.po.om.OmOrg)
	 */
	@Override
	public void updateOrg(OmOrg omOrg) throws OrgManagementException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#moveOrg(java.lang.String, java.lang.String, java.lang.String, int)
	 */
	@Override
	public boolean moveOrg(String orgCode, String fromParentsOrgCode, String toParentsOrgCode, int toSortNo)
			throws OrgManagementException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#copyOrg(java.lang.String, java.lang.String)
	 */
	@Override
	public OmOrg copyOrg(String copyFromOrgCode, String newOrgCode) throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#copyOrgDeep(java.lang.String, java.lang.String, boolean, boolean, boolean, boolean, boolean)
	 */
	@Override
	public OmOrg copyOrgDeep(String copyFromOrgCode, String newOrgCode, boolean copyOrgRole, boolean copyPosition,
			boolean copyPositionRole, boolean copyGroup, boolean copyGroupRole) throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#enabledOrg(java.lang.String)
	 */
	@Override
	public OmOrg enabledOrg(String orgCode) throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#reenabledOrg(java.lang.String)
	 */
	@Override
	public OmOrg reenabledOrg(String orgCode) throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#disabledOrg(java.lang.String)
	 */
	@Override
	public OmOrg disabledOrg(String orgCode) throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#cancelOrg(java.lang.String)
	 */
	@Override
	public OmOrg cancelOrg(String orgCode) throws OrgManagementException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#deleteOrg(java.lang.String)
	 */
	@Override
	public void deleteOrg(String orgCode) throws OrgManagementException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#deleteOrgsByCondition(org.tis.tools.base.WhereCondition)
	 */
	@Override
	public void deleteOrgsByCondition(WhereCondition wc) throws OrgManagementException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryOrg(java.lang.String)
	 */
	@Override
	public OmOrg queryOrg(String orgCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryOrgsByCondition(org.tis.tools.base.WhereCondition)
	 */
	@Override
	public List<OmOrg> queryOrgsByCondition(WhereCondition wc) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryChilds(java.lang.String)
	 */
	@Override
	public List<OmOrg> queryChilds(String orgCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryAllChilds(java.lang.String)
	 */
	@Override
	public List<OmOrg> queryAllChilds(String orgCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryChildsByCondition(java.lang.String, org.tis.tools.model.po.om.OmOrg)
	 */
	@Override
	public List<OmOrg> queryChildsByCondition(String orgCode, OmOrg orgCondition) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryEmployee(java.lang.String, org.tis.tools.model.po.om.OmEmployee)
	 */
	@Override
	public List<OmEmployee> queryEmployee(String orgCode, OmEmployee empCondition) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.tis.tools.rservice.om.capable.IOrgRService#queryPosition(java.lang.String, org.tis.tools.model.po.om.OmPosition)
	 */
	@Override
	public List<OmPosition> queryPosition(String orgCode, OmPosition positionCondition) {
		// TODO Auto-generated method stub
		return null;
	}

}
