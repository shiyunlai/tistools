package org.tis.tools.rservice.ac.capable;

import org.tis.tools.model.po.ac.AcDatascope;
import org.tis.tools.model.po.ac.AcEntity;
import org.tis.tools.model.po.ac.AcEntityfield;
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
     * @param list 需要删除的实体GUID集合
     * @return
     * @throws EntityManagementException
     */
    List<AcEntity> deleteAcEntity(List<String> list) throws EntityManagementException;

    /**
     * 修改数据实体
     * @param acEntity 修改的实体对象信息
     * @return
     * @throws EntityManagementException
     */
    AcEntity editAcEntity(AcEntity acEntity) throws EntityManagementException;

    /**
     * 查询数据实体对象集合
     * @param appGuid 应用GUID
     * @param entityType 实体类型
     * @return
     * @throws EntityManagementException
     */
    List<AcEntity> queryAcEntityList(String appGuid, String entityType) throws EntityManagementException;

    /**
     * 新增实体属性
     * @param acEntityfield 新增实体属性信息
     * @return
     * @throws EntityManagementException
     */
    AcEntityfield createAcEntityfield(AcEntityfield acEntityfield) throws EntityManagementException;

    /**
     * 删除实体属性
     * @param list 需要删除的实体属性集合
     * @return
     * @throws EntityManagementException
     */
    List<AcEntityfield> deleteAcEntityfield(List<String> list) throws EntityManagementException;

    /**
     * 修改实体属性
     * @param acEntityfield 修改的实体属性信息
     * @return
     * @throws EntityManagementException
     */
    AcEntityfield editAcEntityfield(AcEntityfield acEntityfield) throws EntityManagementException;

    /**
     * 查询数据实体属性集合
     * @param entityGuid 实体GUID
     * @return
     * @throws EntityManagementException
     */
    List<AcEntityfield> queryAcEntityfieldList(String entityGuid) throws EntityManagementException;

    /**
     * 新增数据范围权限
     * @param acDatascope 新增数据范围权限信息
     * @return
     * @throws EntityManagementException
     */
    AcDatascope createAcDatascope(AcDatascope acDatascope) throws EntityManagementException;

    /**
     * 删除数据范围权限
     * @param list 需要删除的数据范围权限集合
     * @return
     * @throws EntityManagementException
     */
    List<AcDatascope> deleteAcDatescope(List<String> list) throws EntityManagementException;

    /**
     * 修改数据范围权限
     * @param acDatascope 修改的数据范围权限信息
     * @return
     * @throws EntityManagementException
     */
    AcDatascope editAcDatascope(AcDatascope acDatascope) throws EntityManagementException;

    /**
     * 查询数据实体数据范围权限集合
     * @param entityGuid 实体GUID
     * @return
     * @throws EntityManagementException
     */
    List<AcDatascope> queryAcDatascopeList(String entityGuid) throws EntityManagementException;






}
