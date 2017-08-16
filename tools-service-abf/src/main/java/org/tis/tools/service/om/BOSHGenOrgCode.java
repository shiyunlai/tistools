/**
 *
 */
package org.tis.tools.service.om;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.rservice.om.exception.OrgManagementException;
import org.tis.tools.rservice.sys.capable.DictConstants;
import org.tis.tools.service.base.SequenceService;
import org.tis.tools.service.om.exception.OMExceptionCodes;
import org.tis.tools.service.sys.SysDictServiceExt;
import org.tis.tools.spi.om.IOrgCodeGenerator;

/**
 * 上海银行机构代码生成
 *
 * @author megapro
 */
@Service
public class BOSHGenOrgCode implements IOrgCodeGenerator {

    @Autowired
    SysDictServiceExt sysDictServiceExt;

    @Autowired
    SequenceService sequenceService;

    @Autowired
    OmOrgService omOrgService;

    /**
     * <pre>
     * 生成一个未被使用的机构代码。
     * parms中需要指定的参数对包括：
     * orgDegree 机构等级
     * areaCode  地区码
     * </pre>
     *
     * @param parms 参数
     * @return 机构代码
     * @throws OrgManagementException
     */
    @Override
    public String genOrgCode(Map<String, String> parms) throws OrgManagementException {

        String orgDegree = parms.get("orgDegree");
        if (StringUtils.isEmpty(orgDegree)) {
            throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE, new Object[]{"orgDegree"});
        }

        String areaCode = parms.get("areaCode");
        if (StringUtils.isEmpty(areaCode)) {
            throw new OrgManagementException(OMExceptionCodes.LAKE_PARMS_FOR_GEN_ORGCODE, new Object[]{"areaCode"});
        }

        /**
         * <pre>
         * 机构代码规则：
         * 1.共10位；
         * 2.组成结构： 机构等级(两位) + 地区码(三位) + 序号(五位)
         * 机构等级来自业务字典： DICT_OM_ORGDEGREE，取其中的实际值
         * 地区码来自业务字典：  DICT_SD_AREA，取其中的实际值
         * 序号：全行范围内机构数量顺序排号
         * </pre>
         */
        StringBuffer sb = new StringBuffer();

        // 机构等级
        sb.append(orgDegree);
        // 地区码
        sb.append(areaCode);
        // 序号， 以 BOSHGenOrgCode.class.getName() 唯一key，顺序编号
        sb.append(toSeqNO(sequenceService.getNextSeqNo(BOSHGenOrgCode.class.getName())));//五位机构顺序号

        //TODO 检查机构代码未被使用，否则要重新生成，直到新机构代码可用
//		WhereCondition wc = new WhereCondition() ; 
//		wc.andEquals(OmOrg.ORG_CODE, sb.toString()) ; 
//		List<OmOrg> orgs = omOrgService.query(wc) ; 
//		if( orgs.size() !=0 ){
//			
//		}

        return sb.toString();
    }

    /**
     * 把当前机构数量转为5位字符串，不足部分以0左补齐
     *
     * @param totalOrgCount
     * @return
     */
    private Object toSeqNO(int totalOrgCount) {

        String t = String.valueOf(totalOrgCount).toString();

        return org.tis.tools.common.utils.StringUtil.leftPad(t, 5, '0');

    }

}
