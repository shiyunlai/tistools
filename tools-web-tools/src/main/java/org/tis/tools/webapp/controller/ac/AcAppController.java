package org.tis.tools.webapp.controller.ac;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.def.JNLConstants;
import org.tis.tools.model.po.ac.*;
import org.tis.tools.model.vo.ac.AcFuncVo;
import org.tis.tools.rservice.ac.capable.IApplicationRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.log.OperateLog;
import org.tis.tools.webapp.log.ReturnType;
import org.tis.tools.webapp.util.AjaxUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/AcAppController")
public class AcAppController extends BaseController {
    @Autowired
    IApplicationRService applicationRService;

    /**
     * appAdd新增应用服务
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_ADD,
            operateDesc = "新增应用",
            retType = ReturnType.Object,
            id = "guid",
            name = "appName",
            keys = "appCode"
    )
    @ResponseBody
    @RequestMapping(value = "/appAdd", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> createAcApp(@RequestBody String content) {
        return getReturnMap(applicationRService.createAcApp(JSONObject.parseObject(content, AcApp.class)));
    }

    /**
     * appDel删除方法
     *
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_DELETE,
            operateDesc = "删除应用",
            retType = ReturnType.Object,
            id = "guid",
            name = "appName",
            keys = "appCode"
    )
    @ResponseBody
    @RequestMapping(value = "/appDel", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> appDel(@RequestBody String content) {
        return getReturnMap(applicationRService.deleteAcApp(JSONObject.parseObject(content).getString("id")));
    }

    /**
     * appEdit修改方法
     *
     * @param content
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_UPDATE,
            operateDesc = "修改应用",
            retType = ReturnType.Object,
            id = "guid",
            name = "appName",
            keys = "appCode"
    )
    @ResponseBody
    @RequestMapping(value = "/appEdit", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> appEdit(@RequestBody String content) {
        return getReturnMap(applicationRService.updateAcApp(JSONObject.parseObject(content, AcApp.class)));
    }

    /**
     * appQuery查询应用
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/appQuery", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> appQuery(@RequestBody String content) {
        Map<String, Object> result = new HashMap<String, Object>();
        JSONObject jsonObj = JSONObject.parseObject(content);
        String id = jsonObj.getString("id");
        //通过id判断需要加载的节点
        WhereCondition wc;
        if ("#".equals(id)) {
            Map map = new HashMap();
            map.put("rootName", "应用功能管理");
            map.put("rootCode", "AC0000");
            result.put("data", map);//返回给前台的数据
        } else if ("AC0000".equals(id)) {
            //调用远程服务,#:根
            List<AcApp> ac = applicationRService.queryAcRootList();
            result.put("data", ac);//返回给前台的数据
        } else if (id.length() > 3 && "APP".equals(id.substring(0, 3))) {
            List<AcFuncgroup> group = applicationRService.queryAcRootFuncgroup(id);
            result.put("data", group);//返回给前台的数据
        } else if (id.length() > 9 && "FUNCGROUP".equals(id.substring(0, 9))) {
            Map map = new HashMap();
            List<AcFuncgroup> groupList = applicationRService.queryAcChildFuncgroup(id);
            if (groupList.size() > 0) {
                map.put("groupList", groupList);
            }
            List<AcFunc> funcList = applicationRService.queryAcFunc(id);
            if (funcList.size() > 0) {
                map.put("funcList", funcList);
            }
            result.put("data", map);//返回给前台的数据
        }
        return getReturnMap(result.get("data"));
    }

    /**
     * groupAdd新增功能组
     *
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_ADD,
            operateDesc = "新增功能组",
            retType = ReturnType.Object,
            id = "guid",
            name = "funcgroupName"
    )
    @ResponseBody
    @RequestMapping(value = "/groupAdd", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> groupAdd(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
        String funcgroupName = jsonObj.getString("funcgroupName");
        String groupLevel = jsonObj.getString("groupLevel");
        String guidApp = jsonObj.getString("guidApp");
        String guidParents = jsonObj.getString("guidParents");
        //转换成BigDecimal类型
        BigDecimal groupLevelBd = new BigDecimal(groupLevel);
        groupLevelBd = groupLevelBd.setScale(2, BigDecimal.ROUND_HALF_UP); //小数位2位，四舍五入
        AcFuncgroup acFuncgroup = new AcFuncgroup();//new 一个新对象
        acFuncgroup.setFuncgroupName(funcgroupName);
        acFuncgroup.setGroupLevel(groupLevelBd);
        acFuncgroup.setGuidApp(guidApp);
        acFuncgroup.setGuidParents(guidParents);
        return getReturnMap(applicationRService.createAcFuncGroup(acFuncgroup));//把new的并且填入参数的对象，传入，返回
    }

    /**
     * groupDel删除功能组
     *
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_DELETE,
            operateDesc = "删除功能组",
            retType = ReturnType.Object,
            id = "guid",
            name = "funcgroupName"
    )
    @ResponseBody
    @RequestMapping(value = "/groupDel", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> groupDel(@RequestBody String content) {

        return getReturnMap(applicationRService.deleteAcFuncGroup(JSONObject.parseObject(content).getString("id")));

    }

    /**
     * groupEdit修改功能组
     *
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_UPDATE,
            operateDesc = "删除功能组",
            retType = ReturnType.Object,
            id = "guid",
            name = "funcgroupName"
    )
    @ResponseBody
    @RequestMapping(value = "/groupEdit", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> groupEdit(@RequestBody String content) {

            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String guid = jsonObj.getString("id");
            AcFuncgroup acFuncgroup = applicationRService.queryFuncgroup(guid);

            String funcgroupName = jsonObj.getString("funcgroupName");
            String groupLevel = jsonObj.getString("groupLevel");
            String guidParents = jsonObj.getString("guidParents");
            acFuncgroup.setFuncgroupName(funcgroupName);
            acFuncgroup.setGroupLevel(new BigDecimal(groupLevel));
            acFuncgroup.setGuidParents(guidParents);
            return getReturnMap(applicationRService.updateAcFuncgroup(acFuncgroup));
    }

    /**
     * acFuncAdd新增功能
     *
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_ADD,
            operateDesc = "新增功能",
            retType = ReturnType.Object,
            id = "guid",
            name = "funcName",
            keys = {"funcCode", "funcType"}
    )
    @ResponseBody
    @RequestMapping(value = "/acFuncAdd", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> acFuncAdd(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
        JSONObject data = jsonObj.getJSONObject("data");
        AcFunc acFunc = JSONObject.parseObject(data.toJSONString(), AcFunc.class);
        AcFunc func = applicationRService.createAcFunc(acFunc);//把new的并且填入参数的对象，传入，返回
        return getReturnMap(func);
    }

    /**
     * acFuncDel删除功能
     *
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_DELETE,
            operateDesc = "删除功能",
            retType = ReturnType.Object,
            id = "guid",
            name = "funcName",
            keys = {"funcCode", "funcType"}
    )
    @ResponseBody
    @RequestMapping(value = "/acFuncDel", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> acFuncDel(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
        JSONObject data = jsonObj.getJSONObject("data");
        String guid = data.getString("id");
        AcFunc func = applicationRService.deleteAcFunc(guid);//把new的并且填入参数的对象，传入，返回
        return getReturnMap(func);
    }

    /**
     * acFuncEdit更新功能
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_UPDATE,
            operateDesc = "修改功能",
            retType = ReturnType.Object,
            id = "guid",
            name = "funcName",
            keys = {"funcCode", "funcType"}
    )
    @ResponseBody
    @RequestMapping(value = "/acFuncEdit", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> acFuncEdit(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
        JSONObject data = jsonObj.getJSONObject("data");
        AcFunc acFunc = JSONObject.parseObject(data.toJSONString(), AcFunc.class);
        AcFunc func = applicationRService.updateAcFunc(acFunc);//把new的并且填入参数的对象，传入，返回
        return getReturnMap(func);
    }

    /**
     * acFuncResource更新功能对应资源
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    /*@ResponseBody
    @RequestMapping(value = "/acFuncResourceEdit", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String acFuncResourceEdit(@RequestBody String content, HttpServletRequest request,
                                     HttpServletResponse response) {

        try {
            if (logger.isInfoEnabled()) {
                logger.info("acFuncEdit request : " + content);
            }
            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数

            //设置功能对应资源
            AcFuncResource acFuncResource = new AcFuncResource();

            String guid = jsonObj.getString("id");
            String resType = jsonObj.getString("resType");
            String compackName = jsonObj.getString("compackName");
            String resshowName = jsonObj.getString("resShowName");
            String resPath = jsonObj.getString("resPath");
            acFuncResource.setGuidFunc(guid);
            acFuncResource.setResType(resType);
            acFuncResource.setCompackName(compackName);
            acFuncResource.setResShowName(resshowName);
            acFuncResource.setResPath(resPath);

            applicationRService.updateAcFuncResource(acFuncResource);
            AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("acFuncEdit exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
            logger.error("acFuncEdit exception : ", e);
        }
        return null;
    }*/

    /**
     * acFuncResouceQuery功能对应资源查询
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/acFuncResouceQuery", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String acFuncResouceQuery(@RequestBody String content, HttpServletRequest request,
                                     HttpServletResponse response) {

        try {
            if (logger.isInfoEnabled()) {
                logger.info("acFuncResouceQuery request : " + content);
            }
            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String guid = jsonObj.getString("id");
            AcFuncResource funcResouce = applicationRService.queryFuncResource(guid);
            AjaxUtils.ajaxJsonSuccessMessage(response, funcResouce);//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("acFuncResouceQuery exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
            logger.error("acFuncResouceQuery exception : ", e);
        }
        return null;
    }


    /**
     * queryAllFunc查询所有应用
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryAllFunc", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String queryAllFunc(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response) {

        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryAllFunc request : " + content);
            }
            List<AcFunc> acfunc = applicationRService.queryAllFunc();
            AjaxUtils.ajaxJsonSuccessMessage(response, acfunc);//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("queryAllFunc exception : ", e);
        }
        return null;
    }

    /**
     * importFunc导入应用
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/importFunc", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String importFunc(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) {

        try {
            if (logger.isInfoEnabled()) {
                logger.info("importFunc request : " + content);
            }

            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String guid = jsonObj.getString("id");
            JSONArray jsonArray = jsonObj.getJSONArray("list");
            List list = JSONObject.parseArray(jsonArray.toJSONString(), String.class);
            applicationRService.importFunc(guid, list);
            AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("importFunc exception : ", e);
        }
        return null;
    }

    /**
     * functypeAdd新增行为类型
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/functypeAdd", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String functypeAdd(@RequestBody String content, HttpServletRequest request,
                              HttpServletResponse response) {

        try {
            if (logger.isInfoEnabled()) {
                logger.info("functypeAdd request : " + content);
            }

            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String bhvtypeCode = jsonObj.getString("bhvtypeCode");
            String bhvtypeName = jsonObj.getString("bhvtypeName");
            AcBhvtypeDef acBhvtypeDef = new AcBhvtypeDef();
            acBhvtypeDef.setBhvtypeCode(bhvtypeCode);
            acBhvtypeDef.setBhvtypeName(bhvtypeName);

            applicationRService.functypeAdd(acBhvtypeDef);
            AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("functypeAdd exception : ", e);
        }
        return null;
    }

    /**
     * functypeDel删除行为类型
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/functypeDel", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String functypeDel(@RequestBody String content, HttpServletRequest request,
                              HttpServletResponse response) {

        try {
            if (logger.isInfoEnabled()) {
                logger.info("functypeDel request : " + content);
            }

            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String guid = jsonObj.getString("id");
            applicationRService.functypeDel(guid);
            AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("functypeDel exception : ", e);
        }
        return null;
    }

    /**
     * functypeEdit 修改行为类型
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/functypeEdit", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String functypeEdit(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response) {

        try {
            if (logger.isInfoEnabled()) {
                logger.info("functypeEdit request : " + content);
            }

            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String guid = jsonObj.getString("id");
            String bhvtypeCode = jsonObj.getString("bhvtypeCode");
            String bhvtypeName = jsonObj.getString("bhvtypeName");
            AcBhvtypeDef acBhvtypeDef = new AcBhvtypeDef();
            acBhvtypeDef.setGuid(guid);
            acBhvtypeDef.setBhvtypeCode(bhvtypeCode);
            acBhvtypeDef.setBhvtypeName(bhvtypeName);
            applicationRService.functypeEdit(acBhvtypeDef);
            AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("functypeEdit exception : ", e);
        }
        return null;
    }

    /**
     * functypequery 查询行为类型
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/functypequery", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String functypequery(@RequestBody String content, HttpServletRequest request,
                                HttpServletResponse response) {

        try {
            if (logger.isInfoEnabled()) {
                logger.info("functypequery request : " + content);
            }
            List<AcBhvtypeDef> list = applicationRService.functypequery();
            AjaxUtils.ajaxJsonSuccessMessage(response, list);//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("functypequery exception : ", e);
        }
        return null;
    }


    /**
     * funactAdd 新增功能操作行为
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/funactAdd", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String funactAdd(@RequestBody String content, HttpServletRequest request,
                            HttpServletResponse response) {

        try {
            if (logger.isInfoEnabled()) {
                logger.info("funactAdd request : " + content);
            }

            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String guidBehtype = jsonObj.getString("guid");
            String bhvCode = jsonObj.getString("bhvCode");
            String bhvName = jsonObj.getString("bhvName");
            AcBhvDef acBhvDef = new AcBhvDef();
            acBhvDef.setBhvCode(bhvCode);
            acBhvDef.setBhvName(bhvName);
            acBhvDef.setGuidBehtype(guidBehtype);
            applicationRService.funactAdd(acBhvDef);

            AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("funactAdd exception : ", e);
        }
        return null;
    }


    /**
     * funactDel 删除功能操作行为
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/funactDel", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String funactDel(@RequestBody String content, HttpServletRequest request,
                            HttpServletResponse response) {

        try {
            if (logger.isInfoEnabled()) {
                logger.info("funactDel request : " + content);
            }

            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String idstr = jsonObj.getJSONArray("ids").toJSONString();
            List<String> ids = JSONObject.parseArray(idstr, String.class);
            applicationRService.funactDel(ids);
            AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("funactDel exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
            logger.error("funactDel exception : ", e);
        }
        return null;
    }

    /**
     * funactEdit 修改功能操作行为
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/funactEdit", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String funactEdit(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) {

        try {
            if (logger.isInfoEnabled()) {
                logger.info("funactEdit request : " + content);
            }

            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String guid = jsonObj.getString("id");
            String bhvCode = jsonObj.getString("bhvCode");
            String bhvName = jsonObj.getString("bhvName");
            AcBhvDef acBhvDef = new AcBhvDef();
            acBhvDef.setBhvCode(bhvCode);
            acBhvDef.setBhvName(bhvName);
            acBhvDef.setGuid(guid);
            applicationRService.funactEdit(acBhvDef);

            AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("funactEdit exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
            logger.error("funactEdit exception : ", e);
        }
        return null;
    }


    /**
     * queryBhvtypeDefByFunc 根据功能的GUID查询行为类型定义
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryBhvtypeDefByFunc", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String queryBhvtypeDefByFunc(@RequestBody String content, HttpServletRequest request,
                                        HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryBhvtypeDefByFunc request : " + content);
            }
            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String funcGuid = jsonObj.getString("id");
            List<AcBhvtypeDef> list = applicationRService.queryBhvtypeDefByFunc(funcGuid);

            AjaxUtils.ajaxJsonSuccessMessage(response, list);//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("queryBhvtypeDefByFunc exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
            logger.error("queryBhvtypeDefByFunc exception : ", e);
        }
        return null;

    }

    /**
     * queryBhvDefByBhvType 根据行为类型的GUID查询所有的操作行为
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryBhvDefByBhvType", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String queryBhvDefByBhvType(@RequestBody String content, HttpServletRequest request,
                                       HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryBhvDefByBhvType request : " + content);
            }
            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String bhvtypeGuid = jsonObj.getString("id");
            List<AcBhvDef> list = applicationRService.queryBhvDefByBhvType(bhvtypeGuid);

            AjaxUtils.ajaxJsonSuccessMessage(response, list);//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("queryBhvDefByBhvType exception : ", e);
        }
        return null;

    }



    /**
     * addBhvDefForFunc 功能添加行为定义
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addBhvDefForFunc", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String addBhcDefForFunc(@RequestBody String content, HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("addBhvDefForFunc request : " + content);
            }
            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String funcGuid = jsonObj.getString("id");
            List bhvtypeGuids = JSONObject.parseArray(jsonObj.getJSONArray("typeGuidList").toJSONString(), String.class);
            applicationRService.addBhvDefForFunc(funcGuid, bhvtypeGuids);

            AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("addBhvDefForFunc exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
            logger.error("addBhvDefForFunc exception : ", e);
        }
        return null;
    }




    /**
     * 查询功能下全部的操作行为
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryAllBhvDefForFunc", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String queryAllBhcDefForFunc(@RequestBody String content, HttpServletRequest request,
                                        HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryAllBhvDefForFunc request : " + content);
            }
            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String funcGuid = jsonObj.getString("funcGuid"); // 功能GUID

            List list = applicationRService.queryAllBhvDefForFunc(funcGuid);

            AjaxUtils.ajaxJsonSuccessMessage(response, list);//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("queryAllBhvDefForFunc exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
            logger.error("queryAllBhvDefForFunc exception : ", e);
        }
        return null;

    }
    /**
     * 删除功能对应的行为定义
     *
     * @param content
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delFuncBhvDef", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String delFuncBhvDef(@RequestBody String content, HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("delFuncBhvDef request : " + content);
            }
            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数
            String funcGuid = jsonObj.getString("funcGuid"); // 功能GUID
            String idstr = jsonObj.getJSONArray("bhvDefGuids").toJSONString();
            List<String> bhvDefGuids = JSONObject.parseArray(idstr, String.class); // 行为定义GUID

            applicationRService.delFuncBhvDef(funcGuid, bhvDefGuids);

            AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("delFuncBhvDef exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
            logger.error("delFuncBhvDef exception : ", e);
        }
        return null;
    }

    /**
     * 开启应用
     *
     * @param content
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/enableApp", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public void enableApp(@RequestBody String content, HttpServletRequest request,
                          HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("enableApp request : " + content);
            }
            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数

            String appGuid = jsonObj.getString("appGuid"); // 应用GUID
            Date openDate = jsonObj.getDate("openStr");//开通时间

            applicationRService.enableApp(appGuid, openDate);

            AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("enableApp exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
            logger.error("enableApp exception : ", e);
        }
    }

    /**
     * 关闭应用
     *
     * @param content
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/disableApp", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public void disableApp(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("disableApp request : " + content);
            }
            JSONObject jsonObj = JSONObject.parseObject(content);    //传入的参数

            String appGuid = jsonObj.getString("appGuid"); // 应用GUID

            applicationRService.disableApp(appGuid);

            AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
            logger.error("disableApp exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
            logger.error("disableApp exception : ", e);
        }
    }

    /**
     * 新增功能资源
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_ADD,
            operateDesc = "新增功能资源",
            retType = ReturnType.Object,
            id = "guidFunc",
            name = "attrKey")
    @ResponseBody
    @RequestMapping(value = "/createAcFuncResource", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> createAcFuncResource(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);

        JSONObject data = jsonObj.getJSONObject("data");
        AcFuncResource acFuncResource = JSONObject.parseObject(data.toJSONString(), AcFuncResource.class);
        AcFuncResource funcResource = applicationRService.createAcFuncResource(acFuncResource);
        return getReturnMap(funcResource);
    }
    /**
     * 删除功能资源
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_DELETE,
            operateDesc = "删除功能资源",
            retType = ReturnType.List,
            id = "guidFunc",
            name = "attrKey")
    @ResponseBody
    @RequestMapping(value = "/deleteAcFuncResource", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> deleteAcFuncResource(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);

        JSONArray data = jsonObj.getJSONArray("data");
        List<AcFuncResource> acFuncResources = JSONObject.parseArray(data.toJSONString(), AcFuncResource.class);
        List<AcFuncResource> funcResources = applicationRService.deleteAcFuncResource(acFuncResources);
        return getReturnMap(funcResources);
    }
    /**
     * 删除功能资源
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_UPDATE,
            operateDesc = "修改功能资源",
            retType = ReturnType.Object,
            id = "guidFunc",
            name = "attrKey")
    @ResponseBody
    @RequestMapping(value = "/updateAcFuncResource", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> updateAcFuncResource(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);
        JSONObject data = jsonObj.getJSONObject("data");
        AcFuncResource acFuncResource = JSONObject.parseObject(data.toJSONString(), AcFuncResource.class);
        AcFuncResource funcResource = applicationRService.updateAcFuncResource(acFuncResource);
        return getReturnMap(funcResource);
    }

    /**
     * 查询功能资源
     * @param content
     * @return
     */
    @RequestMapping(value = "/queryAcFuncResource", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> queryAcFuncResource(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);
        JSONObject data = jsonObj.getJSONObject("data");
        String funcGuid = data.getString("funcGuid");
        List<AcFuncResource> funcResources = applicationRService.queryAcFuncResource(funcGuid);
        return getReturnMap(funcResources);
    }




}