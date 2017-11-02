package org.tis.tools.webapp.controller.ac;

import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.JNLConstants;
import org.tis.tools.model.po.ac.*;
import org.tis.tools.rservice.ac.capable.IApplicationRService;
import org.tis.tools.rservice.ac.capable.IRoleRService;
import org.tis.tools.rservice.ac.exception.RoleManagementException;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.log.OperateLog;
import org.tis.tools.webapp.log.ReturnType;
import org.tis.tools.webapp.util.AjaxUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by zhaoch on 2017/7/16.
 */
@Controller
@RequestMapping("/AcRoleController")
public class AcRoleController extends BaseController {

    @Autowired
    IRoleRService roleRService;
    
    @Autowired
	IApplicationRService applicationRService;

    /**
     * 查询角色列表
     *
     */
    @ResponseBody
    @RequestMapping(value="/queryRoleList" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryRoleList(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryRoleList request : " + content);
            }
            List<Map> acRoles = roleRService.queryAllRoleExt();
            AjaxUtils.ajaxJsonSuccessMessage(response,acRoles);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryRoleList exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryRoleList exception : ", e);
        }
        return null;
    }

    /**
     * 新增角色
     */
    @ResponseBody
    @RequestMapping(value="/createRole" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String createRole(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("createRole request : " + content);
            }
/*            JSONObject jsonObject= JSONObject.parseObject(content);
            AcRole acRole = new AcRole();
            BeanUtils.populate(acRole, jsonObject);*/
            AcRole acRole = JSON.parseObject(content, AcRole.class);
            AcRole retRole = roleRService.createAcRole(acRole);
            AjaxUtils.ajaxJsonSuccessMessage(response,retRole);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("createRole exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("createRole exception : ", e);
        }
        return null;
    }

    /**
     * 修改角色
     */
    @ResponseBody
    @RequestMapping(value="/editRole" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String editRole(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("editRole request : " + content);
            }
//            JSONObject jsonObject= JSONObject.parseObject(content);
//            AcRole acrole = new AcRole();
//            BeanUtils.populate(acrole, jsonObject);
            AcRole acRole = JSON.parseObject(content, AcRole.class);
            AcRole retRole = roleRService.eidtAcRole(acRole);
            AjaxUtils.ajaxJsonSuccessMessage(response,retRole);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("editRole exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("editRole exception : ", e);
        }
        return null;
    }

    /**
     * 删除角色
     */
    @ResponseBody
    @RequestMapping(value="/deleteRole" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String deleteRole(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("deleteRole request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String guid = jsonObject.getString("roleGuid");
            roleRService.deleteAcRole(guid);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("deleteRole exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("deleteRole exception : ", e);
        }
        return null;
    }
    
    /**
	 * appQuery查询对应的应用应用
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/appQuery" ,method=RequestMethod.POST)
	public String appQuery(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("appQuery request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);
			String id = jsonObj.getString("id");
			
			//通过id判断需要加载的节点
			WhereCondition wc;
			if("#".equals(id.substring(0, 1))){
				AcApp app = applicationRService.queryAcApp(id.substring(1));
				List list = new ArrayList();
				Map map=new HashMap();
				map.put("rootName", app.getAppName());
				map.put("rootCode", app.getGuid());
				map.put("guid", app.getGuid());
				list.add(map);
				result.put("data", list);//返回给前台的数据
				result.put("type", "root");//返回给前台的数据
			}else  if(id.length()>3&&"APP".equals(id.substring(0, 3))){	
				List<AcFuncgroup> group = applicationRService.queryAcRootFuncgroup(id);
				result.put("data", group);//返回给前台的数据
				result.put("type", "app");//返回给前台的数据
			}else if(id.length()>9&&"FUNCGROUP".equals(id.substring(0, 9))){
				Map map=new HashMap();
				List<AcFuncgroup> groupList = applicationRService.queryAcChildFuncgroup(id);
				if(groupList.size()>0){
					map.put("groupList", groupList);
				}
				List<AcFunc> funcList = applicationRService.queryAcFunc(id);
				if(funcList.size()>0){
					map.put("funcList", funcList);
				}
				result.put("data", map);//返回给前台的数据
				result.put("type", "group");//返回给前台的数据
			}
			AjaxUtils.ajaxJsonSuccessMessage(response, result);
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("appQuery exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("appQuery exception : ", e);
		}
		return null;
	}
	
	

    /**
     * 角色配置功能权限
     */
    @ResponseBody
    @RequestMapping(value="/configRoleFunc" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String addRoleFunc(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("configRoleFunc request : " + content);
            }
            JSONObject jsonObject = JSONObject.parseObject(content);
            String roleGuid = jsonObject.getString("roleGuid");
            String appGuid = jsonObject.getString("appGuid");
            JSONArray funcList = jsonObject.getJSONArray("funcList");
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        //删除修改前的角色权限配置
                        roleRService.removeRoleFuncWithApp(roleGuid, appGuid);
                        for(Iterator iterator = funcList.iterator(); iterator.hasNext();) {
                        	JSONObject job = (JSONObject) iterator.next();
                            AcRoleFunc acRoleFunc = new AcRoleFunc();
                            acRoleFunc.setGuidApp(appGuid);
                            acRoleFunc.setGuidRole(roleGuid);
                            acRoleFunc.setGuidFuncgroup(job.getString("groupGuid"));
                            acRoleFunc.setGuidFunc(job.getString("funcGuid"));
                            roleRService.addRoleFunc(acRoleFunc);
                        }
                    }  catch (ToolsRuntimeException te) {
                        throw te;
                    }catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new RoleManagementException(
                                ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap("AC_FUNC_ROLE", e.getCause().getMessage()));
                    }
                }
            });
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("addRoleFunc exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("addRoleFunc exception : ", e);
        }
        return null;
    }

    
    
    /**
     * 查询角色的功能权限
     */
    @ResponseBody
    @RequestMapping(value="/queryRoleFunc" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryRoleFunc(@RequestBody String content, HttpServletRequest request,
                              HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryRoleFunc request : " + content);
            }
            JSONObject jsonObject = JSONObject.parseObject(content);
            String roleGuid = jsonObject.getString("roleGuid");
            List<AcRoleFunc> acRoleFuncs = roleRService.queryAllRoleFunByRoleGuid(roleGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,acRoleFuncs);
        } catch (ToolsRuntimeException e) {
        AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
        logger.error("queryRoleFunc exception : ", e);
    }catch (Exception e) {
        AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
        logger.error("queryRoleFunc exception : ", e);
    }
        return null;
    }

    
    
    /**
     * 角色添加组织权限
     */
    @ResponseBody
    @RequestMapping(value="/addPartyRole" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String removeRoleFunc(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("addPartyRole request : " + content);
            }
//            AcPartyRole acPartyRole = JSONObject.parseObject(content, AcPartyRole.class);
            List<AcPartyRole> acPartyRoleList = JSON.parseArray(content, AcPartyRole.class);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        for(AcPartyRole partyRole : acPartyRoleList) {
                            roleRService.addRoleParty(partyRole);
                        }
                    }  catch (ToolsRuntimeException te) {
                        throw te;
                    }catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new RoleManagementException(
                                ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap("AC_PARTY_ROLE", e.getCause().getMessage()));
                    }
                }
            });
//            roleRService.addRoleParty(acPartyRole);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("addPartyRole exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("addPartyRole exception : ", e);
        }
        return null;
    }
    
    
    
    

    /**
     * 查询角色下某组织类型的权限信息
     */
    @ResponseBody
    @RequestMapping(value="/queryRoleInParty" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryRoleInParty(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryRoleInParty request : " + content);
            }
            JSONObject jsonObject = JSONObject.parseObject(content);
            String roleGuid = jsonObject.getString("roleGuid");
            String partyType = jsonObject.getString("partyType");

            List<Map> acPartyRoleList = roleRService.queryAllRolePartyExt(roleGuid, partyType);
            AjaxUtils.ajaxJsonSuccessMessage(response,acPartyRoleList);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryRoleInParty exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryRoleInParty exception : ", e);
        }
        return null;
    }

    /**
     * 移除角色下的组织对象
     */
    @ResponseBody
    @RequestMapping(value="/removePartyRole" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String removePartyRole(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("removePartyRole request : " + content);
            }

//            JSONObject jsonObject = JSONObject.parseObject(content);
//            String roleGuid = jsonObject.getString("roleGuid");
//            String partyGuid = jsonObject.getString("partyGuid");
            JSONArray jsonArray = JSON.parseArray(content);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        for(int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String roleGuid = jsonObject.getString("roleGuid");
                            String partyGuid = jsonObject.getString("partyGuid");
                            roleRService.removeRoleParty(roleGuid, partyGuid);
                        }
                    }  catch (ToolsRuntimeException te) {
                        throw te;
                    }catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new RoleManagementException(
                                ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_PARTY_ROLE", e.getCause().getMessage()));
                    }
                }
            });
//            roleRService.removeRoleParty(roleGuid, roleGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("removePartyRole exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("removePartyRole exception : ", e);
        }
        return null;
    }

    /**
     * 角色添加操作员
     */
    @ResponseBody
    @RequestMapping(value="/addOperatorRole" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String addOperatorRole(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("addOperatorRole request : " + content);
            }
//            AcOperatorRole acOperatorRole = JSONObject.parseObject(content, AcOperatorRole.class);
//            roleRService.addOperatorRole(acOperatorRole);

            List<AcOperatorRole> acOperatorRoleList = JSON.parseArray(content, AcOperatorRole.class);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        for(AcOperatorRole operatorRole : acOperatorRoleList) {
                            roleRService.addOperatorRole(operatorRole);
                        }
                    }  catch (ToolsRuntimeException te) {
                        throw te;
                    }catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new RoleManagementException(
                                ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap("AC_OPERATOR_ROLE", e.getCause().getMessage()));
                    }
                }
            });
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("addOperatorRole exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("addOperatorRole exception : ", e);
        }
        return null;
    }


    /**
     * 查询角色下的操作员集合
     */
    @ResponseBody
    @RequestMapping(value="/queryOperatorRole",produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryOperatorRole(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryOperatorRole request : " + content);
            }
            JSONObject jsonObject = JSONObject.parseObject(content);
            String roleGuid = jsonObject.getString("roleGuid");

            List<Map> acOperatorRoles = roleRService.queryAllOperatorRoleExt(roleGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,acOperatorRoles);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryOperatorRole exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryOperatorRole exception : ", e);
        }
        return null;
    }

    /**
     * 移除角色下的操作员
     */
    @ResponseBody
    @RequestMapping(value="/removeOperatorRole" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String removeOperatorRole(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("removeOperatorRole request : " + content);
            }
//            JSONObject jsonObject = JSONObject.parseObject(content);
//            String roleGuid = jsonObject.getString("roleGuid");
//            String operatorGuid = jsonObject.getString("operatorGuid");
//            roleRService.removeOperatorRole(roleGuid, operatorGuid);

            JSONArray jsonArray = JSON.parseArray(content);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        for(int i = 0; i < jsonArray.size(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String roleGuid = jsonObject.getString("roleGuid");
                            String operatorGuid = jsonObject.getString("operatorGuid");
                            roleRService.removeOperatorRole(roleGuid, operatorGuid);
                        }
                    }  catch (ToolsRuntimeException te) {
                        throw te;
                    }catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new RoleManagementException(
                                ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_OPERATOR_ROLE", e.getCause().getMessage()));
                    }
                }
            });

            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("removeOperatorRole exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("removeOperatorRole exception : ", e);
        }
        return null;
    }

    /**
     * 查询角色拥有功能的权限行为
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/queryAcRoleBhvsByFuncGuid" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> queryAcRoleBhvsByFuncGuid(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        JSONObject data = jsonObject.getJSONObject("data");
        String roleGuid = data.getString("roleGuid");
        String funcGuid = data.getString("funcGuid");
        return getReturnMap(roleRService.queryAcRoleBhvsByFuncGuid(roleGuid, funcGuid));
    }

    /**
     * 角色添加功能的权限行为
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_ADD,
            operateDesc = "新增角色功能行为定义",
            retType = ReturnType.List,
            id = "guid",
            keys = {"guidFuncBhv", "guidApp"}
    )
    @ResponseBody
    @RequestMapping(value="/addAcRoleBhvs" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> addAcRoleBhvs(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        JSONArray data = jsonObject.getJSONArray("data");
        List<AcRoleBhv> acRoleBhvs = JSON.parseArray(data.toJSONString(), AcRoleBhv.class);
        roleRService.addAcRoleBhvs(acRoleBhvs);
        return getReturnMap(acRoleBhvs);
    }

    /**
     * 角色删除功能的权限行为
     * @param content
     * @return
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_DELETE,
            operateDesc = "删除角色功能行为定义",
            retType = ReturnType.List,
            id = "guid",
            keys = {"guidFuncBhv", "guidApp"}
    )
    @ResponseBody
    @RequestMapping(value="/removeAcRoleBhvs" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> removeAcRoleBhvs(@RequestBody String content) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        JSONArray data = jsonObject.getJSONArray("data");
        List<AcRoleBhv> acRoleBhvs = JSON.parseArray(data.toJSONString(), AcRoleBhv.class);
        roleRService.removeAcRoleBhvs(acRoleBhvs);
        return getReturnMap(acRoleBhvs);
    }
}
