package org.tis.tools.webapp.controller.ac;

import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.def.JNLConstants;
import org.tis.tools.model.po.ac.*;
import org.tis.tools.model.vo.ac.AcOperatorFuncDetail;
import org.tis.tools.rservice.ac.capable.IApplicationRService;
import org.tis.tools.rservice.ac.capable.IOperatorRService;
import org.tis.tools.rservice.ac.capable.IRoleRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.log.OperateLog;
import org.tis.tools.webapp.log.ReturnType;
import org.tis.tools.webapp.util.AjaxUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/AcOperatorController")
public class AcOperatorController extends BaseController {

    private Map<String, Object> responseMsg ;

    @Autowired
    IOperatorRService operatorRService;
    @Autowired
    IRoleRService roleRService;
    @Autowired
    IApplicationRService applicationRService;
    
    /**
     * 查询操作员列表
     * @param content
     */
    @ResponseBody
    @RequestMapping(value="/queryAllOperator" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> queryAllOperator(@RequestBody String content) {
        List<AcOperator> acOperators = operatorRService.queryOperators();
        return getReturnMap(acOperators);
    }

    /**
     * 新增操作员
     * @param content
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_ADD,
            operateDesc = "新增操作员",
            retType = ReturnType.Object,
            id = "guid",
            name = "operatorName",
            keys = "userId"
    )
    @ResponseBody
    @RequestMapping(value="/createOperator" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> createOperator(@RequestBody String content)  {
        JSONObject jsonObject = JSONObject.parseObject(content);
        AcOperator acOperator = jsonObject.getObject("data", AcOperator.class);
        return getReturnMap(operatorRService.createOperator(acOperator));
    }

    /**
     * 删除操作员
     * @param content
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_DELETE,
            operateDesc = "删除操作员",
            retType = ReturnType.Object,
            id = "guid",
            name = "operatorName",
            keys = "userId"
    )
    @ResponseBody
    @RequestMapping(value="/deleteOperator" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> deleteOperator(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        String operatorGuid = jsonObject.getJSONObject("data").getString("operatorGuid");
        return getReturnMap(operatorRService.deleteOperator(operatorGuid));
    }
    /**
     * 修改操作员
     * @param content
     * @return
     */
    /**
     * 删除操作员
     * @param content
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_UPDATE,
            operateDesc = "修改操作员",
            retType = ReturnType.Object,
            id = "guid",
            name = "operatorName",
            keys = "userId"
    )
    @ResponseBody
    @RequestMapping(value="/editOperator" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> editOperator(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        AcOperator acOperator = jsonObject.getObject("data", AcOperator.class);
        return getReturnMap(operatorRService.editOperator(acOperator));
    }

    /**
     * 设置默认操作员身份
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/setDefaultOperatorIdentity" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String setDefaultOperator(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("setDefaultOperatorIdentity request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String identityGuid = jsonObject.getString("identityGuid");
            operatorRService.setDefaultOperatorIdentity(identityGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("setDefaultOperatorIdentity exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("setDefaultOperatorIdentity exception : ", e);
        }
        return null;
    }

    
    /**
     * 查询操作员身份列表
     *     传入操作员GUID
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/queryAllOperatorIdentity" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String queryAllOperatorIdentity(@RequestBody String content, HttpServletRequest request,
                                   HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryAllOperatorIdentity request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String userId = jsonObject.getString("userId");
            List<AcOperatorIdentity> acOperatorIdentities = operatorRService.queryOperatorIdentitiesByUserId(userId);
            AjaxUtils.ajaxJsonSuccessMessage(response,acOperatorIdentities);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryAllOperatorIdentity exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryAllOperatorIdentity exception : ", e);
        }
        return null;
    }

    /**
     * 新增操作员身份
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/createOperatorIdentity" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String createOperatorIdentity(@RequestBody String content, HttpServletRequest request,
                                 HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("createOperatorIdentity request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            AcOperatorIdentity acOperatorIdentity = new AcOperatorIdentity();
            BeanUtils.populate(acOperatorIdentity, jsonObject);
            operatorRService.createOperatorIdentity(acOperatorIdentity);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("createOperatorIdentity exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("createOperatorIdentity exception : ", e);
        }
        return null;
    }

    /**
     * 删除操作员身份
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/deleteOperatorIdentity" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String deleteMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("deleteOperatorIdentity request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String identityGuid = jsonObject.getString("identityGuid");//所属身份GUID
            operatorRService.deleteOperatorIdentity(identityGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("deleteOperatorIdentity exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("deleteOperatorIdentity exception : ", e);
        }
        return null;
    }

    /**
     * 修改操作员身份
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/editOperatorIdentity" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String editOperatorIdentity(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("editOperatorIdentity request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            AcOperatorIdentity acOperatorIdentity = new AcOperatorIdentity();
            BeanUtils.populate(acOperatorIdentity, jsonObject);
            operatorRService.editOperatorIdentity(acOperatorIdentity);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("editOperatorIdentity exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("editOperatorIdentity exception : ", e);
        }
        return null;
    }

    /**
     * 查询操作员身份权限集列表
     */
    @ResponseBody
    @RequestMapping(value="/queryAllOperatorIdentityRes" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String queryAllOperatorIdentityRes(@RequestBody String content, HttpServletRequest request,
                                   HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryAllOperatorIdentityRes request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String identityGuid = jsonObject.getString("identityGuid");
            List<Map> acOperatorIdentityreses = operatorRService.queryOperatorIdentityreses(identityGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,acOperatorIdentityreses);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryAllOperatorIdentityRes exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryAllOperatorIdentityRes exception : ", e);
        }
        return null;
    }

    /**
     * 新增操作员身份权限
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/createOperatorIdentityres" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String createOperatorIdentityres(@RequestBody String content, HttpServletRequest request,
                                 HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("createOperatorIdentityres request : " + content);
            }
           /* JSONObject jsonObject= JSONObject.parseObject(content);*/
            List<AcOperatorIdentityres> acOperatorIdentityreses = JSON.parseArray(content, AcOperatorIdentityres.class);
            operatorRService.createOperatorIdentityres(acOperatorIdentityreses);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("createOperatorIdentityres exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("createOperatorIdentityres exception : ", e);
        }
        return null;
    }

    /**
     * 删除操作员身份权限
     */
    @ResponseBody
    @RequestMapping(value="/deleteOperatorIdentityres" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String deleteOperatorIdentityres(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("deleteOperatorIdentityres request : " + content);
            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
            List<AcOperatorIdentityres> acOperatorIdentityreses = JSON.parseArray(content, AcOperatorIdentityres.class);
            operatorRService.deleteOperatorIdentityres(acOperatorIdentityreses);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("deleteOperatorIdentityres exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("deleteOperatorIdentityres exception : ", e);
        }
        return null;
    }

    /**
     * 修改操作员身份权限
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/editOperatorIdentityres" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String editOperatorIdentityres(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("editOperatorIdentityres request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            AcOperatorIdentityres acOperatorIdentityres = new AcOperatorIdentityres();
            BeanUtils.populate(acOperatorIdentityres, jsonObject);
            operatorRService.editOperatorIdentityres(acOperatorIdentityres);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("editOperatorIdentityres exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("editOperatorIdentityres exception : ", e);
        }
        return null;
    }
    
    
    /**
     * 根据资源类型查询操作员对应角色
     */
    @ResponseBody
    @RequestMapping(value="/queryRoleInOperatorByResType" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String queryRoleInOperatorByResType(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response)  {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryRoleInOperatorByResType request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String operatorGuid = jsonObject.getString("operatorGuid");//操作员GUID
            String resType = jsonObject.getString("resType");//资源类型
            List<AcRole> roleList = operatorRService.queryOperatorRoleByResType(operatorGuid, resType);
            AjaxUtils.ajaxJsonSuccessMessage(response, roleList);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryRoleInOperatorByResType exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryRoleInOperatorByResType exception : ", e);
        }
        return null;
    }




    /**
     * 根据USERID查询操作员GUID
     */
    @ResponseBody
    @RequestMapping(value="/queryOperatorByUserId" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String queryOperatorByUserId(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response)  {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryOperatorByUserId request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String userId = jsonObject.getString("userId");//操作员USER_ID
            AcOperator acOperator = operatorRService.queryOperatorByUserId(userId);
            AjaxUtils.ajaxJsonSuccessMessage(response, acOperator);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryOperatorByUserId exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryOperatorByUserId exception : ", e);
        }
        return null;
    }

    
    /**
     * 根据USERID查询操作员未授权角色
     */
    @ResponseBody
    @RequestMapping(value="/queryOperatorUnauthorizedRoleList" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String queryOperatorUnauthorizedRoleList(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response)  {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryOperatorUnauthorizedRoleList request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String userId = jsonObject.getString("userId");//操作员USER_ID
            List<AcRole> acRoleList = roleRService.queryOperatorUnauthorizedRoleList(userId);
            AjaxUtils.ajaxJsonSuccessMessage(response, acRoleList);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryOperatorUnauthorizedRoleList exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryOperatorUnauthorizedRoleList exception : ", e);
        }
        return null;
    }
    
    /**
     * 根据USERID查询操作员已授权角色
     */
    @ResponseBody
    @RequestMapping(value="/queryOperatorAuthorizedRoleList" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String queryOperatorAuthorizedRoleList(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response)  {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryOperatorAuthorizedRoleList request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String userId = jsonObject.getString("userId");//操作员USER_ID
            List<AcRole> acRoleList = roleRService.queryOperatorAuthorizedRoleList(userId);
            AjaxUtils.ajaxJsonSuccessMessage(response, acRoleList);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryOperatorAuthorizedRoleList exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryOperatorAuthorizedRoleList exception : ", e);
        }
        return null;
    }    
    /**
     * 根据USERID查询操作员继承角色
     */
    @ResponseBody
    @RequestMapping(value="/queryOperatorInheritRoleList" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String queryOperatorInheritRoleList(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response)  {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryOperatorInheritRoleList request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String userId = jsonObject.getString("userId");//操作员USER_ID
            List<AcRole> acRoleList = roleRService.queryOperatorInheritRoleList(userId);
            AjaxUtils.ajaxJsonSuccessMessage(response, acRoleList);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryOperatorInheritRoleList exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryOperatorInheritRoleList exception : ", e);
        }
        return null;
    }

    
    /**
     * 根据USERID查询特殊权限树
     */
    @ResponseBody
    @RequestMapping(value="/queryOperatorFuncInfoInApp" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String queryOperatorFuncInfoInApp(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response)  {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryOperatorFuncInfoInApp request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String userId = jsonObject.getString("userId");//操作员USER_ID
            AcOperatorFuncDetail info = operatorRService.queryOperatorFuncInfoInApp(userId);
            AjaxUtils.ajaxJsonSuccessMessage(response, info.toString());
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryOperatorInheritRoleList exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryOperatorInheritRoleList exception : ", e);
        }
        return null;
    } 
    
    /**
     * 查询用户的所有功能列表
     *  用于展示特殊功能列表
     *  用于展示用户已有的功能权限
     */
    @ResponseBody
    @RequestMapping(value="/queryAcOperatorFunListByUserId" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String queryAcOperatorFunListByUserId(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response)  {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryAcOperatorFunListByUserId request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String userId = jsonObject.getString("userId");//操作员USER_ID
            List<Map> info = operatorRService.queryAcOperatorFunListByUserId(userId);
            AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response,info, "YYYY-MM-dd");//转换时间类型
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryAcOperatorFunListByUserId exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryAcOperatorFunListByUserId exception : ", e);
        }
        return null;
    } 
    

    /**
     * 新增用户特殊功能权限
     */
    @ResponseBody
    @RequestMapping(value="/addAcOperatorFun" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String addAcOperatorFun(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response)  {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("addAcOperatorFun request : " + content);
            }
            AcOperatorFunc acOperatorFunc = JSON.parseObject(content, AcOperatorFunc.class);
            operatorRService.addAcOperatorFun(acOperatorFunc);
            AjaxUtils.ajaxJsonSuccessMessage(response, "");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("addAcOperatorFun exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("addAcOperatorFun exception : ", e);
        }
        return null;
    }
    
    /**
     * 移除用户特殊功能权限
     */
    @ResponseBody
    @RequestMapping(value="/removeAcOperatorFun" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public String removeAcOperatorFun(@RequestBody String content, HttpServletRequest request,
                               HttpServletResponse response)  {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("removeAcOperatorFun request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String operatorGuid = jsonObject.getString("operatorGuid");//操作员GUID
            String funcGuid = jsonObject.getString("funcGuid");// 功能GUID
            operatorRService.removeAcOperatorFun(operatorGuid, funcGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response, "");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("removeAcOperatorFun exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("removeAcOperatorFun exception : ", e);
        }
        return null;
    }

    /**
     * 查询操作员的所有应用
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/queryOperatorAllApp" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> queryOperatorAllApp(@RequestBody String content) {
        return getReturnMap(applicationRService.queryOperatorAllApp(JSONObject.parseObject(content).getString("userId")));
    }
    
    /**
     * 修改个人配置
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_UPDATE,
            operateDesc = "修改个人配置",
            retType = ReturnType.Object,
            id = "guidConfig"
    )
    @ResponseBody
    @RequestMapping(value="/saveOperatorLog" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> saveOperatorLog(@RequestBody String content) {
        return getReturnMap(operatorRService.saveOperatorLog(JSONObject.parseObject(content, AcOperatorConfig.class)));
    }


    /**
     * 查询操作员的个性化配置
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/queryOperatorConfig" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> queryOperatorConfig(@RequestBody String content) {
        String userId = JSONObject.parseObject(content).getString("userId");
        String appGuid = JSONObject.parseObject(content).getString("appGuid");
        return getReturnMap(operatorRService.queryOperatorConfig(userId, appGuid)
                .stream()
                .collect(Collectors.groupingBy(AcConfig::getConfigType)));
    }

    /**
     * 查询操作员在某功能的行为白名单和黑名单
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/queryOperatorBhvListInFunc" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> queryOperatorBhvListInFunc(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        String funcGuid = jsonObject.getString("funcGuid");
        String userId = jsonObject.getString("userId");
        return getReturnMap(operatorRService.queryOperatorBhvListInFunc(funcGuid, userId));
    }

    
    
    /**
     * 操作员功能行为添加黑名单
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_ADD,
            operateDesc = "添加操作员功能行为黑名单",
            retType = ReturnType.List,
            id = "guidFuncBhv",
            name = "bhvName",
            keys = "bhvCode"
    )
    @ResponseBody
    @RequestMapping(value="/addOperatorBhvBlackList" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> addOperatorBhvBlackList(@RequestBody String content) {
        return getReturnMap(operatorRService.addOperatorBhvBlackList(JSON.parseArray(content, AcOperatorBhv.class)));
    }

    /**
     * 操作员功能行为移除黑名单
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_DELETE,
            operateDesc = "移除操作员功能行为黑名单",
            retType = ReturnType.List,
            id = "guidFuncBhv",
            name = "bhvName",
            keys = "bhvCode"
    )
    @ResponseBody
    @RequestMapping(value="/deleteOperatorBhvBlackList" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> deleteOperatorBhvBlackList(@RequestBody String content) {
        return getReturnMap(operatorRService.deleteOperatorBhvBlackList(JSON.parseArray(content, AcOperatorBhv.class)));
    }

    /**
     * 根据用户和应用id查询功能树
     */
    @ResponseBody
    @RequestMapping(value="/getOperatorFuncInfo" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> getOperatorFuncInfo(@RequestBody String content)  {
        JSONObject jsonObject= JSONObject.parseObject(content);
        String userId = jsonObject.getString("userId"); // 操作员USER_ID
        String appGuid = jsonObject.getString("appGuid");// 应用id
        AcOperatorFuncDetail info = operatorRService.getOperatorFuncInfo(userId, appGuid);
        return getReturnMap(info.toString());
    }

    /**
     * 获取没有关联员工的操作员
     */
    @ResponseBody
    @RequestMapping(value="/getOperatorsNotLinkEmp" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> getOperatorsNotLinkEmp()  {
        return getReturnMap(operatorRService.getOperatorsNotLinkEmp());
    }

    /**
     * 改变操作员状态
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_UPDATE,
            operateDesc = "修改操作员状态",
            retType = ReturnType.Object,
            id = "guid",
            name = "operatorName",
            keys = "operatorStatus"
    )
    @ResponseBody
    @RequestMapping(value="/changeOperatorStatus" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> changeOperatorStatus(@RequestBody String content) {
        JSONObject jsonObject= JSONObject.parseObject(content);
        JSONObject data= jsonObject.getJSONObject("data");
        String userId = data.getString("userId");
        String status = data.getString("status");
        return getReturnMap(operatorRService.changeOperatorStatus(userId, status));
    }
}
