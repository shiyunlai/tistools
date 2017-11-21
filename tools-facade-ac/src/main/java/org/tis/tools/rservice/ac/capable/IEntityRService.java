package org.tis.tools.rservice.ac.capable;

import org.tis.tools.model.po.ac.AcEntity;
import org.tis.tools.rservice.ac.exception.EntityManagementException;

import java.util.List;

/**
 * 数据实体服务
 *
 * 开发一个通用数据维护功能；指定表名，即可进行数据的CRUD操作，并且能做引用关系维护；
 *
 */
public interface IEntityRService {

    /**
     * 新增数据实体
     * @param acEntity 新增实体对象信息
     * @return
     * @throws EntityManagementException
     */
    AcEntity createAcEntity(AcEntity acEntity) throws EntityManagementException;

    /**
     * 删除数据实体
     * @param list 需要删除的实体集合
     * @return
     * @throws EntityManagementException
     */
    List<AcEntity> deleteAcEntity(List<AcEntity> list) throws EntityManagementException;

    /**
     * 修改数据实体
     * @param acEntity 修改的实体对象信息
     * @return
     * @throws EntityManagementException
     */
    AcEntity editAcEntity(AcEntity acEntity) throws EntityManagementException;


    List<AcEntity> queryAcEntityList() throws EntityManagementException;



}
