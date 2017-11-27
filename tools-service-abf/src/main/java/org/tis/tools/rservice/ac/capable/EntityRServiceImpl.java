package org.tis.tools.rservice.ac.capable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BeanFieldValidateUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.*;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.EntityManagementException;
import org.tis.tools.service.ac.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.tis.tools.common.utils.BasicUtil.wrap;

/**
 *
 * propagation :事务的传播行为
 * isolation :事务的隔离级别
 * readOnly :只读
 * rollbackFor :发生哪些异常回滚
 * noRollbackFor :发生哪些异常不回滚
 * rollbackForClassName 根据异常类名回滚
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
public class EntityRServiceImpl extends BaseRService implements IEntityRService {

    @Autowired
    AcEntityService acEntityService;
    @Autowired
    AcEntityfieldService acEntityfieldService;
    @Autowired
    AcDatascopeService acDatascopeService;
    @Autowired
    AcRoleEntityService acRoleEntityService;
    @Autowired
    AcRoleEntityfieldService acRoleEntityfieldService;
    @Autowired
    AcRoleDatascopeService acRoleDatascopeService;

    /**
     * 新增数据实体
     *
     * @param acEntity 新增实体对象信息
     * @return
     * @throws EntityManagementException
     */
    @Override
    public AcEntity createAcEntity(AcEntity acEntity) throws EntityManagementException {
        if (acEntity == null)
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("acEntity", AcEntity.TABLE_NAME));
        try {
            acEntity.setGuid(GUID.entity());
            String[] validate = {"guid", "entityName", "guidApp", "tableName", "entityType"};
            String s = BeanFieldValidateUtil.checkObjFieldRequired(acEntity, validate);
            if(StringUtils.isNotBlank(s)) {
                throw new EntityManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, wrap(s, AcEntity.TABLE_NAME));
            }
            acEntityService.insert(acEntity);
            return acEntity;
        } catch (ToolsRuntimeException e) {
            logger.error("createAcEntity exception: ", e);
            throw e;
        } catch (Exception e) {
            logger.error("createAcEntity exception: ", e);
            throw new EntityManagementException(ExceptionCodes.FAILURE_WHEN_INSERT, wrap(AcEntity.TABLE_NAME, e));
        }
    }

    /**
     * 删除数据实体
     *
     * @param list 需要删除的实体集合
     * @return
     * @throws EntityManagementException
     */
    @Override
    public List<AcEntity> deleteAcEntity(List<String> list) throws EntityManagementException {
        if(CollectionUtils.isEmpty(list))
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, wrap("list", AcEntity.TABLE_NAME));
        List<AcEntity> acEntities = acEntityService.query(new WhereCondition().andIn(AcEntity.COLUMN_GUID, list));
        // 1.实体对应多个实体属性 2.实体属性与角色有对应关系
        // 3.实体对应多个数据范围权限 4.数据范围权限与角色有对应关系
        // 5.实体与角色有对应关系
        // 6.删除实体
        WhereCondition wc = new WhereCondition();
        wc.andIn("guid_entity", list);
        try {
            List<AcEntityfield> entityfieldList = acEntityfieldService.query(wc);
            if (CollectionUtils.isNotEmpty(entityfieldList)) {
                List<String> entityfieldGuids = entityfieldList.stream().map(AcEntityfield::getGuid).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(entityfieldGuids))
                    acRoleEntityfieldService.deleteByCondition(new WhereCondition().andIn(AcRoleEntityfield.COLUMN_GUID_ENTITYFIELD, entityfieldGuids));
                acEntityfieldService.deleteByCondition(wc);
            }
            List<AcDatascope> datascopeList = acDatascopeService.query(wc);
            if (CollectionUtils.isNotEmpty(datascopeList)) {
                List<String> datascopeGuids = datascopeList.stream().map(AcDatascope::getGuid).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(datascopeGuids))
                    acRoleDatascopeService.deleteByCondition(new WhereCondition().andIn(AcRoleDatascope.COLUMN_GUID_DATASCOPE, datascopeGuids));
                acDatascopeService.deleteByCondition(wc);
            }
            acRoleEntityService.deleteByCondition(wc);
            acEntityService.deleteByCondition(new WhereCondition().andIn(AcEntity.COLUMN_GUID, list));
            return acEntities;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteAcEntity exception: ", e);
            throw new EntityManagementException(ExceptionCodes.FAILURE_WHEN_DELETE, wrap(AcEntity.TABLE_NAME, e));
        }
    }

    /**
     * 修改数据实体
     *
     * @param acEntity 修改的实体对象信息
     * @return
     * @throws EntityManagementException
     */
    @Override
    public AcEntity editAcEntity(AcEntity acEntity) throws EntityManagementException {
        if (acEntity == null)
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap("acEntity", AcEntity.TABLE_NAME));
        try {
            String[] validate = {"guid", "entityName", "guidApp", "tableName", "entityType"};
            String s = BeanFieldValidateUtil.checkObjFieldRequired(acEntity, validate);
            if(StringUtils.isNotBlank(s)) {
                throw new EntityManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, wrap(s, AcEntity.TABLE_NAME));
            }
            acEntityService.update(acEntity);
            return acEntity;
        } catch (ToolsRuntimeException e) {
            logger.error("editAcEntity exception: ", e);
            throw e;
        } catch (Exception e) {
            logger.error("editAcEntity exception: ", e);
            throw new EntityManagementException(ExceptionCodes.FAILURE_WHEN_UPDATE, wrap(AcEntity.TABLE_NAME, e));
        }
    }


    /**
     * 查询数据实体对象集合
     *
     * @param appGuid    应用GUID
     * @param entityType 实体类型
     * @return
     * @throws EntityManagementException
     */
    @Override
    public List<AcEntity> queryAcEntityList(String appGuid, String entityType) throws EntityManagementException {
        if(StringUtils.isBlank(appGuid)) {
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("appGuid(String)", AcEntity.TABLE_NAME));
        }
        if(StringUtils.isBlank(entityType)) {
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("entityType(String)", AcEntity.TABLE_NAME));
        }
        List<AcEntity> list = acEntityService.query(new WhereCondition()
                .andEquals(AcEntity.COLUMN_GUID_APP, appGuid)
                .andEquals(AcEntity.COLUMN_ENTITY_TYPE, entityType));
        return list;
    }

    /**
     * 新增实体属性
     *
     * @param acEntityfield 新增实体属性信息
     * @return
     * @throws EntityManagementException
     */
    @Override
    public AcEntityfield createAcEntityfield(AcEntityfield acEntityfield) throws EntityManagementException {
        if (acEntityfield == null)
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("acEntityfield", AcEntityfield.TABLE_NAME));
        try {
            acEntityfield.setGuid(GUID.entityField());
            String[] validate = {"guid", "guidEntity", "fieldName", "columnName"};
            String s = BeanFieldValidateUtil.checkObjFieldRequired(acEntityfield, validate);
            if(StringUtils.isNotBlank(s)) {
                throw new EntityManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, wrap(s, AcEntityfield.TABLE_NAME));
            }
            acEntityfieldService.insert(acEntityfield);
            return acEntityfield;
        } catch (ToolsRuntimeException e) {
            logger.error("createAcEntityfield exception: ", e);
            throw e;
        } catch (Exception e) {
            logger.error("createAcEntityfield exception: ", e);
            throw new EntityManagementException(ExceptionCodes.FAILURE_WHEN_INSERT, wrap(AcEntityfield.TABLE_NAME, e));
        }
    }

    /**
     * 删除实体属性
     *
     * @param list 需要删除的实体属性集合
     * @return
     * @throws EntityManagementException
     */
    @Override
    public List<AcEntityfield> deleteAcEntityfield(List<String> list) throws EntityManagementException {
        if(CollectionUtils.isEmpty(list))
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, wrap("list", AcEntityfield.TABLE_NAME));
        List<AcEntityfield> acEntityfields = acEntityfieldService.query(new WhereCondition().andIn(AcEntityfield.COLUMN_GUID, list));
        try {
            // 实体属性与角色有对应关系
            // 删除实体属性
            acRoleEntityfieldService.deleteByCondition(new WhereCondition().andIn(AcRoleEntityfield.COLUMN_GUID_ENTITYFIELD, list));
            acEntityfieldService.deleteByCondition(new WhereCondition().andIn(AcEntityfield.COLUMN_GUID, list));
            return acEntityfields;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteAcEntityfield exception: " , e);
            throw new EntityManagementException(ExceptionCodes.FAILURE_WHEN_DELETE, wrap(AcEntityfield.TABLE_NAME, e));
        }
    }

    /**
     * 修改实体属性
     *
     * @param acEntityfield 修改的实体属性信息
     * @return
     * @throws EntityManagementException
     */
    @Override
    public AcEntityfield editAcEntityfield(AcEntityfield acEntityfield) throws EntityManagementException {
        if (acEntityfield == null)
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap("acEntityfield", AcEntityfield.TABLE_NAME));
        try {
            String[] validate = {"guid", "guidEntity", "fieldName", "columnName"};
            String s = BeanFieldValidateUtil.checkObjFieldRequired(acEntityfield, validate);
            if(StringUtils.isNotBlank(s)) {
                throw new EntityManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, wrap(s, AcEntityfield.TABLE_NAME));
            }
            acEntityfieldService.update(acEntityfield);
            return acEntityfield;
        } catch (ToolsRuntimeException e) {
            logger.error("editAcEntityfield exception: ", e);
            throw e;
        } catch (Exception e) {
            logger.error("editAcEntityfield exception: ", e);
            throw new EntityManagementException(ExceptionCodes.FAILURE_WHEN_UPDATE, wrap(AcEntityfield.TABLE_NAME, e));
        }
    }

    /**
     * 查询数据实体属性集合
     *
     * @param entityGuid 实体GUID
     * @return
     * @throws EntityManagementException
     */
    @Override
    public List<AcEntityfield> queryAcEntityfieldList(String entityGuid) throws EntityManagementException {
        if (StringUtils.isBlank(entityGuid))
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("entityGuid(String)", AcEntityfield.TABLE_NAME));
        return acEntityfieldService.query(new WhereCondition().andEquals(AcEntityfield.COLUMN_GUID_ENTITY, entityGuid));
    }

    /**
     * 新增数据范围权限
     *
     * @param acDatascope 新增数据范围权限信息
     * @return
     * @throws EntityManagementException
     */
    @Override
    public AcDatascope createAcDatascope(AcDatascope acDatascope) throws EntityManagementException {
        if (acDatascope == null)
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("acEntityfield", AcDatascope.TABLE_NAME));
        try {
            acDatascope.setGuid(GUID.dataScope());
            String s = BeanFieldValidateUtil.checkObjFieldAllRequired(acDatascope);
            if(StringUtils.isNotBlank(s)) {
                throw new EntityManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, wrap(s, AcDatascope.TABLE_NAME));
            }
            acDatascopeService.insert(acDatascope);
            return acDatascope;
        } catch (ToolsRuntimeException e) {
            logger.error("createAcDatascope exception: ", e);
            throw e;
        } catch (Exception e) {
            logger.error("createAcDatascope exception: ", e);
            throw new EntityManagementException(ExceptionCodes.FAILURE_WHEN_INSERT, wrap(AcDatascope.TABLE_NAME, e));
        }
    }

    /**
     * 删除数据范围权限
     *
     * @param list 需要删除的数据范围权限集合
     * @return
     * @throws EntityManagementException
     */
    @Override
    public List<AcDatascope> deleteAcDatescope(List<String> list) throws EntityManagementException {
        if(CollectionUtils.isEmpty(list))
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, wrap("list", AcDatascope.TABLE_NAME));
        List<AcDatascope> acDatascopes = acDatascopeService.query(new WhereCondition().andIn(AcDatascope.COLUMN_GUID, list));
        try {
            // 数据范围权限与角色有对应关系
            // 删除实体属性
            acRoleDatascopeService.deleteByCondition(new WhereCondition().andIn(AcRoleDatascope.COLUMN_GUID_DATASCOPE, list));
            acDatascopeService.deleteByCondition(new WhereCondition().andIn(AcDatascope.COLUMN_GUID, list));
            return acDatascopes;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("deleteAcEntityfield exception: " , e);
            throw new EntityManagementException(ExceptionCodes.FAILURE_WHEN_DELETE, wrap(AcDatascope.TABLE_NAME, e));
        }
    }

    /**
     * 修改数据范围权限
     *
     * @param acDatascope 修改的数据范围权限信息
     * @return
     * @throws EntityManagementException
     */
    @Override
    public AcDatascope editAcDatascope(AcDatascope acDatascope) throws EntityManagementException {
        if (acDatascope == null)
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap("acEntityfield", AcDatascope.TABLE_NAME));
        try {
            acDatascope.setGuid(GUID.dataScope());
            String s = BeanFieldValidateUtil.checkObjFieldAllRequired(acDatascope);
            if(StringUtils.isNotBlank(s)) {
                throw new EntityManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, wrap(s, AcDatascope.TABLE_NAME));
            }
            acDatascopeService.update(acDatascope);
            return acDatascope;
        } catch (ToolsRuntimeException e) {
            logger.error("createAcDatascope exception: ", e);
            throw e;
        } catch (Exception e) {
            logger.error("createAcDatascope exception: ", e);
            throw new EntityManagementException(ExceptionCodes.FAILURE_WHEN_UPDATE, wrap(AcDatascope.TABLE_NAME, e));
        }
    }

    /**
     * 查询数据实体数据范围权限集合
     *
     * @param entityGuid 实体GUID
     * @return
     * @throws EntityManagementException
     */
    @Override
    public List<AcDatascope> queryAcDatascopeList(String entityGuid) throws EntityManagementException {
        if (StringUtils.isBlank(entityGuid))
            throw new EntityManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("entityGuid(String)", AcDatascope.TABLE_NAME));
        return acDatascopeService.query(new WhereCondition().andEquals(AcDatascope.COLUMN_GUID_ENTITY, entityGuid));

    }
}
