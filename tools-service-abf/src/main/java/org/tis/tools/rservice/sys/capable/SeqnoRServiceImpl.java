package org.tis.tools.rservice.sys.capable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.po.sys.SysSeqno;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.sys.exception.SysManagementException;
import org.tis.tools.service.sys.SysSeqnoService;

import java.util.List;

public class SeqnoRServiceImpl extends BaseRService implements ISeqnoRService {
    
    @Autowired
    SysSeqnoService sysSeqnoService;

    /**
     * 查询所有资源序列号
     *
     * @return
     * @throws SysManagementException
     */
    @Override
    public List<SysSeqno> queryAllSeqno() throws SysManagementException {
        try {
            return sysSeqnoService.query(new WhereCondition());
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("SYS_SEQNO",e.getCause().getMessage()));
        }
    }

    /**
     * 新增资源序号
     *
     * @param sysSeqno
     * @return
     * @throws SysManagementException
     */
    @Override
    public SysSeqno createSeqno(SysSeqno sysSeqno) throws SysManagementException {
        try {
            if(null == sysSeqno) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("sysSeqno","SYS_SEQNO"));
            }
            // 校验传入参数
            if(StringUtils.isBlank(sysSeqno.getSeqKey())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("SEQ_KEY","SYS_SEQNO"));
            }
            if(sysSeqnoService.count(new WhereCondition().andEquals("SEQ_KEY", sysSeqno.getSeqKey())) > 0) {
                throw new SysManagementException(ExceptionCodes.DUPLICATE_WHEN_INSERT, BasicUtil.wrap("SEQ_KEY","SYS_SEQNO"));
            }
            if(StringUtils.isBlank(sysSeqno.getResetParams())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("RESET_PARAMS","SYS_SEQNO"));
            }
            if(StringUtils.isBlank(sysSeqno.getReset())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("RESET","SYS_SEQNO"));
            }
            if(null == sysSeqno.getSeqNo()) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("SEQ_NO","SYS_SEQNO"));
            }

            sysSeqnoService.insert(sysSeqno);
            return sysSeqno;
        } catch (SysManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT,
                    BasicUtil.wrap("SYS_SEQNO",e.getCause().getMessage()));
        }
    }

    /**
     * 修改资源序号
     *
     * @param sysSeqno
     * @return
     * @throws SysManagementException
     */
    @Override
    public SysSeqno editSeqno(SysSeqno sysSeqno) throws SysManagementException {
        try {
            if(null == sysSeqno) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, BasicUtil.wrap("sysSeqno","SYS_SEQNO"));
            }
            // 校验传入参数
            if(StringUtils.isBlank(sysSeqno.getSeqKey())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, BasicUtil.wrap("SEQ_KEY","SYS_SEQNO"));
            }
            if(sysSeqnoService.count(new WhereCondition().andEquals("SEQ_KEY", sysSeqno.getSeqKey())) != 1) {
                throw new SysManagementException(ExceptionCodes.NOT_FOUND_WHEN_UPDATE, BasicUtil.wrap("SEQ_KEY","SYS_SEQNO"));
            }
            if(StringUtils.isBlank(sysSeqno.getResetParams())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, BasicUtil.wrap("RESET_PARAMS","SYS_SEQNO"));
            }
            if(StringUtils.isBlank(sysSeqno.getReset())) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, BasicUtil.wrap("RESET","SYS_SEQNO"));
            }
            if(null == sysSeqno.getSeqNo()) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, BasicUtil.wrap("SEQ_NO","SYS_SEQNO"));
            }

            sysSeqnoService.updateByCondition(new WhereCondition().andEquals("SEQ_KEY", sysSeqno.getSeqKey()), sysSeqno);
            return sysSeqno;
        } catch (SysManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysManagementException(
                    ExceptionCodes.FAILURE_WHEN_UPDATE,
                    BasicUtil.wrap("SYS_SEQNO",e.getCause().getMessage()));
        }
    }

    /**
     * 删除资源序号
     *
     * @param seqKey
     * @throws SysManagementException
     */
    @Override
    public void deleteSeqno(String seqKey) throws SysManagementException {
        try {
            if(null == seqKey) {
                throw new SysManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("SEQ_KEY","SYS_SEQNO"));
            }
            sysSeqnoService.deleteByCondition(new WhereCondition().andEquals("SEQ_KEY", seqKey));

        } catch (SysManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SysManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE,
                    BasicUtil.wrap("SYS_SEQNO",e.getCause().getMessage()));
        }
    }
}
