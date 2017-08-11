package org.tis.tools.rservice.sys.capable;

import org.tis.tools.model.po.sys.SysSeqno;
import org.tis.tools.rservice.sys.exception.SysManagementException;

import java.util.List;

public interface ISeqnoRService {

    /**
     * 查询所有资源序列号
     * @return
     * @throws SysManagementException
     */
    List<SysSeqno> queryAllSeqno() throws SysManagementException;

    /**
     * 新增资源序号
     *
     * @param sysSeqno
     *
     * @return
     * @throws SysManagementException
     */
    SysSeqno createSeqno(SysSeqno sysSeqno) throws SysManagementException;

    /**
     * 修改资源序号
     *
     * @param sysSeqno
     * @return
     * @throws SysManagementException
     */
    SysSeqno editSeqno(SysSeqno sysSeqno) throws SysManagementException;

    /**
     * 删除资源序号
     * @param seqKey
     * @throws SysManagementException
     */
    void deleteSeqno(String seqKey) throws SysManagementException;
}
