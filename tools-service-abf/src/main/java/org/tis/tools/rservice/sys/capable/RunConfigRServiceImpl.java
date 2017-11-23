package org.tis.tools.rservice.sys.capable;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.sys.SysRunConfig;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.sys.exception.SysManagementException;
import org.tis.tools.service.sys.SysRunConfigService;

import java.util.ArrayList;
import java.util.List;

import static org.tis.tools.common.utils.BasicUtil.surroundBracketsWithLFStr;
import static org.tis.tools.common.utils.BasicUtil.wrap;

/**
 * 系统运行参数服务实现
 * @author
 *
 */
public class RunConfigRServiceImpl extends BaseRService implements IRunConfigRService {

    @Autowired
    SysRunConfigService sysRunConfigService;

    /**
     * 查询所有系统运行参数
     *
     * @return 系统运行参数集合
     * @throws SysManagementException
     */
    @Override
    public List<SysRunConfig> queryAllSysRunConfig() throws SysManagementException {
        List<SysRunConfig> SysRunConfigList = new ArrayList<>();
        try {
            SysRunConfigList = sysRunConfigService.query(new WhereCondition());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    wrap("SYS_RUN_CONFIG", e));
        }
        return SysRunConfigList;
    }

    /**
     * <p>新增系统运行参数</p>
     *
     * <pre>
     *     验证必输字段：
     *          1.应用GUID:’guidApp’;
     *          2.参数组别:’groupName’;
     *          3.参数键: ‘keyName’ ;
     *          4.值来源类型:’valueFrom’ ;
     *          5.参数值:’value’ ;
     *
     *     服务端业务逻辑：
     *          1.生成主键GUID，通过方法GUID.runConfig(）
     * </pre>
     *
     * @param sysRunConfig 系统运行参数对象
     * @return void
     * @throws SysManagementException
     */
    @Override
    public SysRunConfig createSysRunConfig(SysRunConfig sysRunConfig) throws SysManagementException {

        try {
            if(null == sysRunConfig) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("SysRunConfig","SYS_RUN_CONFIG"));
            }
            // 校验传入参数
            if(StringUtil.isEmpty(sysRunConfig.getGuidApp())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("GUIDAPP","SYS_RUN_CONFIG"));
            }
            if(StringUtil.isEmpty(sysRunConfig.getGroupName())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("GROUPNAME","SYS_RUN_CONFIG"));
            }
            if(StringUtil.isEmpty(sysRunConfig.getKeyName())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("KEYNAME","SYS_RUN_CONFIG"));
            }
            if(StringUtil.isEmpty(sysRunConfig.getValue())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("VALUE","SYS_RUN_CONFIG"));
            }
            if(StringUtil.isEmpty(sysRunConfig.getValueFrom())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("VALUEFROM","SYS_RUN_CONFIG"));
            }
            /** 添加GUID和系统参数*/
            String guid = GUID.runConfig();
            sysRunConfig.setGuid(guid);
            sysRunConfigService.insert(sysRunConfig);
            return sysRunConfig;
        } catch (SysManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT,
                    wrap("SYS_RUN_CONFIG", e));
        }

    }

    /**
     * <p>修改系统运行参数</p>
     * <p>
     * <pre>
     *     验证必输字段：
     *          1.应用GUID:’guidApp’;
     *          2.参数组别:’groupName’;
     *          3.参数键: ‘keyName’ ;
     *          4.值来源类型:’valueFrom’ ;
     *          5.参数值:’value’ ;
     *
     *     服务端业务逻辑：
     *          无
     * </pre>
     *
     * @param sysRunConfig
     * @throws SysManagementException
     */
    @Override
    public void editSysRunConfig(SysRunConfig sysRunConfig) throws SysManagementException {

        try {
            if(null == sysRunConfig) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap("SysRunConfig","SYS_RUN_CONFIG"));
            }
            // 校验传入参数
            if(StringUtil.isEmpty(sysRunConfig.getGuidApp())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap("GUIDAPP","SYS_RUN_CONFIG"));
            }
            if(StringUtil.isEmpty(sysRunConfig.getGroupName())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap("GROUPNAME","SYS_RUN_CONFIG"));
            }
            if(StringUtil.isEmpty(sysRunConfig.getKeyName())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap("KEYNAME","SYS_RUN_CONFIG"));
            }
            if(StringUtil.isEmpty(sysRunConfig.getValue())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap("VALUE","SYS_RUN_CONFIG"));
            }
            if(StringUtil.isEmpty(sysRunConfig.getValueFrom())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap("VALUEFROM","SYS_RUN_CONFIG"));
            }
            sysRunConfigService.update(sysRunConfig);

        } catch (SysManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysManagementException(
                    ExceptionCodes.FAILURE_WHEN_UPDATE,
                    wrap("SYS_RUN_CONFIG", e));
        }

    }

    /**
     * <p>删除系统运行参数</p>
     * <p>
     * <pre>
     *     验证必输字段：
     *          1.系统运行参数GUID:’guid’;
     *
     *     服务端业务逻辑：
     *          无
     *
     * @param guid
     * @throws SysManagementException
     */
    @Override
    public SysRunConfig deleteSysRunConfig(String guid) throws SysManagementException {
        try {
            if(null == guid) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, wrap("GUID","SYS_RUN_CONFIG"));
            }
            SysRunConfig sysRunConfig = sysRunConfigService.loadByGuid(guid);
            if (sysRunConfig == null)
                throw new SysManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                        wrap(surroundBracketsWithLFStr(SysRunConfig.COLUMN_GUID, guid), SysRunConfig.TABLE_NAME));
            sysRunConfigService.delete(guid);
            return sysRunConfig;
        } catch (SysManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE,
                    wrap("SYS_RUN_CONFIG", e));
        }
    }
}
