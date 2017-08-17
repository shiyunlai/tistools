package org.tis.tools.webapp.controller.ac;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcFunc;
import org.tis.tools.model.po.ac.AcFuncgroup;
import org.tis.tools.model.po.ac.AcMenu;
import org.tis.tools.model.po.ac.AcOperatorMenu;
import org.tis.tools.model.vo.ac.AcMenuDetail;
import org.tis.tools.rservice.ac.capable.IApplicationRService;
import org.tis.tools.rservice.ac.capable.IMenuRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoch on 2017/7/16.
 */
@Controller
@RequestMapping("/AcMenuController")
public class AcMenuController extends BaseController {
	
    @Autowired
    IMenuRService menuRService;   
    @Autowired
	IApplicationRService applicationRService;
    private Map<String, Object> responseMsg ;


    /**
     * 查询所有应用
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/queryAllAcApp" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryAllAcApp(@RequestBody String content, HttpServletRequest request,
                                     HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryAllAcApp request : " + content);
            }
            List<AcApp> acAppList= menuRService.queryAllAcApp();
            AjaxUtils.ajaxJsonSuccessMessage(response,acAppList);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryAllAcApp exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryAllAcApp exception : ", e);
        }
        return null;
    }

    /**
     * 新增菜单
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/createMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String createMenu(@RequestBody String content, HttpServletRequest request,
                                        HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("createMenu request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String GUID_APP = jsonObject.getString("guidApp");//所属应用GUID
            String GUID_FUNC = jsonObject.getString("guidFunc");//所属功能GUID
            String MENU_NAME = jsonObject.getString("menuName");//菜单名称
            String MENU_LABEL = jsonObject.getString("menuLable");//菜单显示
            String GUID_PARENTS = jsonObject.getString("guidParents");//父菜单GUID
            String GUID_ROOT = jsonObject.getString("guidRoot");//根菜单GUID

//            String GUID_APP = jsonObject.getString("GUID_APP");
//            i.	入参:{ appid : guidApp,菜单名称:menuName,菜单显示（中文）:menuLable,菜单代码:’menuCode’,是否叶子菜单:’ISLEAF’,父菜单giidparents: giidparents};
//            AjaxUtils.ajaxJsonSuccessMessage(response,acFuncgroupList);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("createMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("createMenu exception : ", e);
        }
        return null;
    }
    
    
    /**
     * 查询应用下的菜单
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/queryRootMenuTree" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryRootMenuTree(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
    	Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryRootMenuTree request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String guidApp = jsonObject.getString("guidApp");//所属应用GUID
            
            WhereCondition wc;
			if("#".equals(guidApp)){
				Map map=new HashMap();
				map.put("rootName", "应用菜单");
				map.put("rootCode", "AC0000");
				result.put("data", map);//返回给前台的数据
			}else{
				 List<AcMenu> acMenuList = menuRService.queryRootMenu(guidApp);
				 result.put("data", acMenuList);//返回给前台的数据
			}
            AjaxUtils.ajaxJsonSuccessMessage(response,result);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryRootMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryRootMenu exception : ", e);
        }
        return null;
    }
    

    /**
     * 查询应用下的根菜单
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/queryRootMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryRootMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryRootMenu request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String guidApp = jsonObject.getString("guidApp");//所属应用GUID
            List<AcMenu> acMenuList = menuRService.queryRootMenu(guidApp);
            AjaxUtils.ajaxJsonSuccessMessage(response,acMenuList);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryRootMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryRootMenu exception : ", e);
        }
        return null;
    }

    /**
     * 查询菜单的子菜单
     * @param content
     * @param request
     * @param response
     * @return【
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/queryChildMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryChildMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryChildMenu request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String guidApp = jsonObject.getString("guidMenu");//所属应用GUID
            List<AcMenu> acMenuList = menuRService.queryChildMenu(guidApp);
            AjaxUtils.ajaxJsonSuccessMessage(response,acMenuList);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryChildMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryChildMenu exception : ", e);
        }
        return null;
    }

    /**
     * 新增根菜单
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/createRootMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String createRootMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("createRootMenu request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            AcMenu acMenu = new AcMenu();
            BeanUtils.populate(acMenu, jsonObject);
//            String guidApp = jsonObject.getString("guidMenu");//所属应用GUID
            menuRService.createRootMenu(acMenu);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryChildMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryChildMenu exception : ", e);
        }
        return null;
    }

    /**
     * 新增子菜单
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/createChildMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String createChildMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("createChildMenu request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            AcMenu acMenu = new AcMenu();
            BeanUtils.populate(acMenu, jsonObject);
//            String guidApp = jsonObject.getString("guidMenu");//所属应用GUID
            menuRService.createChildMenu(acMenu);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("createChildMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("createChildMenu exception : ", e);
        }
        return null;
    }
    
    
    /**
     * 修改子菜单
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/editMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String editMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("editMenu request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            AcMenu acMenu = new AcMenu();
            BeanUtils.populate(acMenu, jsonObject);
            menuRService.editMenu(acMenu);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("editMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("editMenu exception : ", e);
        }
        return null;
    }
    /**
     * 删除子菜单
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value="/deleteMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String deleteMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("deleteMenu request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String menuGuid = jsonObject.getString("menuGuid");//菜单GUID
            menuRService.deleteMenu(menuGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("deleteMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("deleteMenu exception : ", e);
        }
        return null;
    }
    
    
    
    /**
     * 移动菜单到菜单 (String , String , BigDecimal )
     */
    @ResponseBody
    @RequestMapping(value="/moveMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String moveMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("moveMenu request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);

            String targetGuid = jsonObject.getString("targetGuid");
            String moveGuid = jsonObject.getString("moveGuid");
            BigDecimal order = jsonObject.getBigDecimal("order");
            menuRService.moveMenu(targetGuid, moveGuid, order);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("moveMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("moveMenu exception : ", e);
        }
        return null;
    }
    
    /**
     * 获取应用下的功能列表
     */
    @ResponseBody
    @RequestMapping(value="/queryAllFuncInApp" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryAllFuncInApp(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryAllFuncInApp request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String appGuid = jsonObject.getString("appGuid");//所属应用GUID
            List<AcFunc> funcList = applicationRService.queryFuncListInApp(appGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response, funcList);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryAllFuncInApp exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryAllFuncInApp exception : ", e);
        }
        return null;
    }
    
    
    /**
     * 获取操作员拥有的菜单
     */
    @ResponseBody
    @RequestMapping(value="/getMenuByUserId" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String getMenuByUserId(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("getMenuByUserId request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);

            String appGuid = jsonObject.getString("appGuid");//所属应用GUID
            String userId = jsonObject.getString("userId");//登陆GUID
            AcMenuDetail menu = menuRService.getMenuByUserId(userId, appGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,menu.toJson());
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("getMenuByUserId exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("getMenuByUserId exception : ", e);
        }
        return null;
    }


    /**
     * 获取操作员的重组菜单
     */
    @ResponseBody
    @RequestMapping(value="/getOperatorMenuByUserId" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String getOperatorMenuByUserId(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("getOperatorMenuByUserId request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);

            String appGuid = jsonObject.getString("appGuid");//所属应用GUID
            String userId = jsonObject.getString("userId");//所属应用GUID

            AcMenuDetail menu = menuRService.getOperatorMenuByUserId(userId, appGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,menu.toJson());
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("getOperatorMenuByUserId exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("getOperatorMenuByUserId exception : ", e);
        }
        return null;
    }
    
    /**
     * 复制菜单到个人重组菜单
     */
    @ResponseBody
    @RequestMapping(value="/copyMenuToOperatorMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String copyMenuToOperatorMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("copyMenuToOperatorMenu request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);

            String operatorGuid = jsonObject.getString("operatorGuid");
            String copyGuid = jsonObject.getString("copyGuid");
            String goalGuid = jsonObject.getString("goalGuid");
            BigDecimal order = jsonObject.getBigDecimal("order");
            menuRService.copyMenuToOperatorMenu(operatorGuid, copyGuid, goalGuid, order);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("copyMenuToOperatorMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("copyMenuToOperatorMenu exception : ", e);
        }
        return null;
    }

    
    /**
     * 移动菜单到个人重组菜单 (String , String , BigDecimal )
     */
    @ResponseBody
    @RequestMapping(value="/moveOperatorMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String moveOperatorMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("moveOperatorMenu request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);

            String targetGuid = jsonObject.getString("targetGuid");
            String moveGuid = jsonObject.getString("moveGuid");
            BigDecimal order = jsonObject.getBigDecimal("order");
            menuRService.moveOperatorMenu(targetGuid, moveGuid, order);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("moveOperatorMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("moveOperatorMenu exception : ", e);
        }
        return null;
    }

    

    
    /**
     * 新增重组跟菜单
     * @param content
     * @param request
     * @param response
     * @return
     * @throws ToolsRuntimeException
     * @throws ParseException
     */
    //修改 删除 一样的 复制过来，每个都加上Operator就可以了，照着这个改，自己写
    @ResponseBody
    @RequestMapping(value="/createRootOperatorMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String createRootOperatorMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("createRootMenu request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            AcOperatorMenu acMenu = new AcOperatorMenu();
            BeanUtils.populate(acMenu, jsonObject);
//            String guidApp = jsonObject.getString("guidMenu");//所属应用GUID
            menuRService.createRootOperatorMenu(acMenu);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("createRootOperatorMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryChildMenu exception : ", e);
        }
        return null;
    }
    
    
   
    
    /**
     * 新增子重组菜单
     */
    @ResponseBody
    @RequestMapping(value="/createChildOperatorMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String createChildOperatorMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response){
        try {
            if (logger.isInfoEnabled()) {
                logger.info("createChildOperatorMenu request : " + content);
            }
            AcOperatorMenu acOperatorMenu = JSON.parseObject(content, AcOperatorMenu.class);
            menuRService.createChildOperatorMenu(acOperatorMenu);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("createChildOperatorMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("createChildOperatorMenu exception : ", e);
        }
        return null;
    }
    
    
    /**
     * 修改重组菜单
     */
    @ResponseBody
    @RequestMapping(value="/editOperatorMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String editOperatorMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("editOperatorMenu request : " + content);
            }
            AcOperatorMenu acOperatorMenu = JSON.parseObject(content, AcOperatorMenu.class);
            menuRService.editOperatorMenu(acOperatorMenu);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("editOperatorMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("editOperatorMenu exception : ", e);
        }
        return null;
    }

    
    /**
     * 删除重组菜单
     */
    @ResponseBody
    @RequestMapping(value="/deleteOperatorMenu" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String deleteOperatorMenu(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("deleteOperatorMenu request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String operatorMenuGuid = jsonObject.getString("operatorMenuGuid");//菜单GUID
            menuRService.deleteOperatorMenu(operatorMenuGuid);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("deleteOperatorMenu exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("deleteOperatorMenu exception : ", e);
        }
        return null;
    }

    
    /**
     * 要求子类构造自己的响应数据
     *
     * @return
     */
    @Override
    public Map<String, Object> getResponseMessage() {
        if( null == responseMsg ){
            responseMsg = new HashMap<String, Object>() ;
        }
        return responseMsg;
    }
}
