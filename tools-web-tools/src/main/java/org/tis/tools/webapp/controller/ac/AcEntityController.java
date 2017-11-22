package org.tis.tools.webapp.controller.ac;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.model.def.JNLConstants;
import org.tis.tools.model.po.ac.AcDatascope;
import org.tis.tools.model.po.ac.AcEntity;
import org.tis.tools.model.po.ac.AcEntityfield;
import org.tis.tools.rservice.ac.capable.IEntityRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.log.OperateLog;
import org.tis.tools.webapp.log.ReturnType;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/entity")
public class AcEntityController extends BaseController {
    
    @Autowired
    IEntityRService entityRService;

    /**
     * 查询实体对象列表
     */
    @ResponseBody
    @RequestMapping(value = "/queryAcEntityList", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> queryRoleList() {
        return getReturnMap(entityRService.queryAcEntityList());
    }

    /**
     * 新增实体对象
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_ADD,  // 操作类型
            operateDesc = "新增实体对象", // 操作描述
            retType = ReturnType.Object, // 返回类型，对象或数组
            id = "guid", // 操作对象标识
            name = "entityName", // 操作对象名
            keys = {"guidApp", "tableName"})
    @ResponseBody
    @RequestMapping(value = "/createAcEntity", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> createAcEntity(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        AcEntity acEntity = JSON.parseObject(jsonObject.getJSONObject("data").toJSONString(), AcEntity.class);
        return getReturnMap(entityRService.createAcEntity(acEntity));
    }

    /**
     * 修改实体对象
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_UPDATE,  // 操作类型
            operateDesc = "修改实体对象", // 操作描述
            retType = ReturnType.Object, // 返回类型，对象或数组
            id = "guid", // 操作对象标识
            name = "entityName", // 操作对象名
            keys = {"guidApp", "tableName"})
    @ResponseBody
    @RequestMapping(value = "/editAcEntity", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> editAcEntity(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        AcEntity acEntity = JSON.parseObject(jsonObject.getJSONObject("data").toJSONString(), AcEntity.class);
        return getReturnMap(entityRService.editAcEntity(acEntity));
    }

    /**
     * 删除实体对象
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_DELETE,  // 操作类型
            operateDesc = "删除实体对象", // 操作描述
            retType = ReturnType.List, // 返回类型，对象或数组
            id = "guid", // 操作对象标识
            name = "entityName", // 操作对象名
            keys = {"guidApp", "tableName"})
    @ResponseBody
    @RequestMapping(value = "/deleteAcEntity", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> deleteAcEntity(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        List<String> guids = JSON.parseArray(jsonObject.getJSONArray("data").toJSONString(), String.class);
        return getReturnMap(entityRService.deleteAcEntity(guids));
    }

    /**
     * 查询实体属性列表
     */
    @ResponseBody
    @RequestMapping(value = "/queryAcEntityfieldList", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> queryAcEntityfieldList(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        String guid = jsonObject.getJSONObject("data").getString("entityGuid");
        return getReturnMap(entityRService.queryAcEntityfieldList(guid));
    }

    /**
     * 新增实体对象属性
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_ADD,  // 操作类型
            operateDesc = "新增实体对象属性", // 操作描述
            retType = ReturnType.Object, // 返回类型，对象或数组
            id = "guid", // 操作对象标识
            name = "fieldName", // 操作对象名
            keys = {"guid", "columnName"})
    @ResponseBody
    @RequestMapping(value = "/createAcEntityfield", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> createAcEntityfield(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        AcEntityfield acEntityfield = JSON.parseObject(jsonObject.getJSONObject("data").toJSONString(), AcEntityfield.class);
        return getReturnMap(entityRService.createAcEntityfield(acEntityfield));
    }

    /**
     * 修改实体对象
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_UPDATE,  // 操作类型
            operateDesc = "修改实体对象属性", // 操作描述
            retType = ReturnType.Object, // 返回类型，对象或数组
            id = "guid", // 操作对象标识
            name = "fieldName", // 操作对象名
            keys = {"guid", "columnName"})
    @ResponseBody
    @RequestMapping(value = "/editAcEntityfield", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> editAcEntityfield(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        AcEntityfield acEntityfield = JSON.parseObject(jsonObject.getJSONObject("data").toJSONString(), AcEntityfield.class);
        return getReturnMap(entityRService.editAcEntityfield(acEntityfield));
    }

    /**
     * 删除实体对象
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_DELETE,  // 操作类型
            operateDesc = "删除实体对象属性", // 操作描述
            retType = ReturnType.List, // 返回类型，对象或数组
            id = "guid", // 操作对象标识
            name = "fieldName", // 操作对象名
            keys = {"guid", "columnName"})
    @ResponseBody
    @RequestMapping(value = "/deleteAcEntityfield", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> deleteAcEntityfield(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        List<String> guids = JSON.parseArray(jsonObject.getJSONArray("data").toJSONString(), String.class);
        return getReturnMap(entityRService.deleteAcEntityfield(guids));
    }

    /**
     * 查询数据范围权限列表
     */
    @ResponseBody
    @RequestMapping(value = "/queryAcDatascopeList", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> queryAcDatascopeList(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        String guid = jsonObject.getJSONObject("data").getString("entityGuid");
        return getReturnMap(entityRService.queryAcDatascopeList(guid));
    }

    /**
     * 新增实体数据范围权限
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_ADD,  // 操作类型
            operateDesc = "新增实体数据范围权限", // 操作描述
            retType = ReturnType.Object, // 返回类型，对象或数组
            id = "guid", // 操作对象标识
            name = "fieldName", // 操作对象名
            keys = {"guid", "columnName"})
    @ResponseBody
    @RequestMapping(value = "/createAcDatascope", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> createAcDatascope(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        AcDatascope acDatascope = JSON.parseObject(jsonObject.getJSONObject("data").toJSONString(), AcDatascope.class);
        return getReturnMap(entityRService.createAcDatascope(acDatascope));
    }

    /**
     * 修改实体数据范围权限
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_UPDATE,  // 操作类型
            operateDesc = "修改实体数据范围权限", // 操作描述
            retType = ReturnType.Object, // 返回类型，对象或数组
            id = "guid", // 操作对象标识
            name = "fieldName", // 操作对象名
            keys = {"guid", "columnName"})
    @ResponseBody
    @RequestMapping(value = "/editAcDatascope", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> editAcDatascope(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        AcDatascope acDatascope = JSON.parseObject(jsonObject.getJSONObject("data").toJSONString(), AcDatascope.class);
        return getReturnMap(entityRService.editAcDatascope(acDatascope));
    }

    /**
     * 删除实体数据范围权限
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_DELETE,  // 操作类型
            operateDesc = "删除实体数据范围权限", // 操作描述
            retType = ReturnType.List, // 返回类型，对象或数组
            id = "guid", // 操作对象标识
            name = "fieldName", // 操作对象名
            keys = {"guid", "columnName"})
    @ResponseBody
    @RequestMapping(value = "/deleteAcDatescope", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> deleteAcDatescope(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        List<String> guids = JSON.parseArray(jsonObject.getJSONArray("data").toJSONString(), String.class);
        return getReturnMap(entityRService.deleteAcDatescope(guids));
    }
}
