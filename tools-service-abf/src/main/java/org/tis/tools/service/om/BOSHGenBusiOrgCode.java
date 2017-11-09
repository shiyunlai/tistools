/**
 * 
 */
package org.tis.tools.service.om;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.rservice.om.exception.BusiOrgManagementException;
import org.tis.tools.rservice.om.exception.OrgManagementException;
import org.tis.tools.rservice.sys.capable.ISeqnoRService;
import org.tis.tools.service.base.SequenceService;
import org.tis.tools.service.om.exception.OMExceptionCodes;
import org.tis.tools.service.sys.SysDictServiceExt;
import org.tis.tools.spi.om.IBusiOrgCodeGenerator;

/**
 * 上海银行机构代码生成
 * @author megapro
 *
 */
@Service
public class BOSHGenBusiOrgCode implements IBusiOrgCodeGenerator {
	
	@Autowired
	SysDictServiceExt sysDictServiceExt  ; 
	
	@Autowired
	SequenceService sequenceService ;
	
	@Autowired
	OmOrgService omOrgService ;

	@Autowired
	ISeqnoRService seqnoRService;

	/**
	 * <pre>
	 * 生成一个未被使用的业务机构代码。
	 * parms中需要指定的参数对包括：
	 * </pre>
	 * @param nodeType 节点类型
	 * @param busiDomain 业务条线
	 * @return
	 * @throws BusiOrgManagementException
	 */
	@Override
	public String genBusiOrgCode(String nodeType, String busiDomain) throws BusiOrgManagementException {
		
//		String nodeType = parms.get("nodeType") ;
		if(StringUtils.isEmpty(nodeType)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_BUSIORGCODE,new Object[]{"nodeType"}) ;
		}
		
//		String busiDomain = parms.get("busiDomain") ;
		if(StringUtils.isEmpty(busiDomain)) {
			throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_BUSIORGCODE,new Object[]{"busiDomain"}) ;
		}

		/**
		 * <pre>
		 * 业务机构代码规则：
		 * 1.共10位；
		 * 2.组成结构： 节点类型(两位) + 业务条线(三位) + 序号(五位)
		 * 节点类型来自业务字典： DICT_OM_NODETYPE，取其中的实际值
		 * 业务条线来自业务字典：  DICT_OM_BUSIDOMAIN，取其中的实际值
		 * 序号：全行范围内机构数量顺序排号
		 * </pre>
		 */
		StringBuffer sb = new StringBuffer() ;
		
		// 节点类型
		if("dummy".equals(nodeType)){
			sb.append("02") ;//虚拟02
		}else{
			sb.append("01");//实际01
		}

		// 业务条线
		sb.append(busiDomain) ;
		// 序号， 以 BOSHGenOrgCode.class.getName() 唯一key，顺序编号
//		sb.append(toSeqNO(sequenceService.getNextSeqNo(BOSHGenBusiOrgCode.class.getName()))) ;//五位机构顺序号
		sb.append(toSeqNO(seqnoRService.getNextSequence("BUSI_ORG_CODE", "业务机构代码序号")));
		//TODO 检查机构代码未被使用，否则要重新生成，直到新机构代码可用
//		WhereCondition wc = new WhereCondition() ; 
//		wc.andEquals(OmOrg.ORG_CODE, sb.toString()) ; 
//		List<OmOrg> orgs = omOrgService.query(wc) ; 
//		if( orgs.size() !=0 ){
//			
//		}
		
		return "BUSIORG"+sb.toString();
	}

	/**
	 * 把当前机构数量转为5位字符串，不足部分以0左补齐
	 * @param totalOrgCount
	 * @return
	 */
	private Object toSeqNO(long totalOrgCount) {
		
		String t = String.valueOf(totalOrgCount) ;
		
		return org.tis.tools.common.utils.StringUtil.leftPad(t, 5, '0');
		
	}

}
