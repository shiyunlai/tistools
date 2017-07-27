package org.tis.tools.rservice.sys.capable;

import org.tis.tools.model.po.sys.SysRunConfig;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.sys.exception.SysManagementException;

import java.util.List;

/**
 * 系统运行参数服务实现
 * @author
 *
 */
public class RunConfigRServiceImpl extends BaseRService implements IRunConfigRService {
    /**
     * 查询所有系统运行参数
     *
     * @return 系统运行参数集合
     * @throws SysManagementException
     */
    @Override
    public List<SysRunConfig> queryAllSysRunConfig() throws SysManagementException {
        return null;
    }

    /**
     * <p>新增系统运行参数</p>
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
     *          1.生成主键GUID，通过方法GUID.runConfig(）
     * </pre>
     *
     * @param sysRunConfig 系统运行参数对象
     * @return void
     * @throws SysManagementException
     */
    @Override
    public void createSysRunConfig(SysRunConfig sysRunConfig) throws SysManagementException {

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
    public void deleteSysRunConfig(String guid) throws SysManagementException {

    }
}
