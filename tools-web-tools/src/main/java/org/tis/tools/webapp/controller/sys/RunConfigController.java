package org.tis.tools.webapp.controller.sys;

import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.po.sys.SysRunConfig;
import org.tis.tools.rservice.sys.basic.ISysRunConfigRService;
import org.tis.tools.rservice.sys.capable.IRunConfigRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/RunConfigController")
public class RunConfigController extends BaseController {

    private Map<String, Object> responseMsg ;

    @Autowired
    IRunConfigRService sysRunConfigRService;

    /**
     * 查询系统运行参数列表
     *
     */
    @ResponseBody
    @RequestMapping(value="/queryRunConfigList" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryRunConfigList(@RequestBody String content, HttpServletRequest request,
                                HttpServletResponse response)  {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryRunConfigList request : " + content);
            }
            List<SysRunConfig> sysRunConfigs = sysRunConfigRService.queryAllSysRunConfig();
            AjaxUtils.ajaxJsonSuccessMessage(response,sysRunConfigs);
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
     * 新增系统参数
     */
    @ResponseBody
    @RequestMapping(value="/createRunConfig" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String createRunConfig(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("createRunConfig request : " + content);
            }
            SysRunConfig sysRunConfig = JSON.parseObject(content, SysRunConfig.class);
            sysRunConfigRService.createSysRunConfig(sysRunConfig);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("createRunConfig exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("createRunConfig exception : ", e);
        }
        return null;
    }

    /**
     * 修改系统参数
     */
    @ResponseBody
    @RequestMapping(value="/editRunConfig" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String editRole(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("editRunConfig request : " + content);
            }
            SysRunConfig sysRunConfig = JSON.parseObject(content, SysRunConfig.class);
            sysRunConfigRService.editSysRunConfig(sysRunConfig);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("editRunConfig exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("editRunConfig exception : ", e);
        }
        return null;
    }

    /**
     * 删除系统参数
     */
    @ResponseBody
    @RequestMapping(value="/deleteRunConfig" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String deleteRole(@RequestBody String content, HttpServletRequest request,
                             HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("deleteRunConfig request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String guid = jsonObject.getString("cfgGuid");
            sysRunConfigRService.deleteSysRunConfig(guid);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("deleteRunConfig exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("deleteRunConfig exception : ", e);
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
