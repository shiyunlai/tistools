package org.tis.tools.webapp.controller.ac;

/**
 * Created by Administrator on 2017/7/20.
 **/

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcFuncgroup;
import org.tis.tools.model.po.ac.AcOperatorIdentity;
import org.tis.tools.rservice.ac.capable.IAuthenticationRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import org.tis.tools.model.po.ac.AcOperator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhaoch on 2017/7/16.
 */
@Controller
@RequestMapping("/AcAuthenticationController")
public class AcAuthenticationController extends BaseController {

    private Map<String, Object> responseMsg ;
    @Autowired
    IAuthenticationRService authenticationRService;

    @ResponseBody
    @RequestMapping(value="/checkUserStatus" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public void checkUserStatus(@RequestBody String content, HttpServletRequest request,
                                     HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("checkUserStatus request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String userId = jsonObject.getString("userId");

            List<AcOperatorIdentity> acOperatorIdentityList = authenticationRService.userStatusCheck(userId);
            AjaxUtils.ajaxJsonSuccessMessage(response,acOperatorIdentityList);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("checkUserStatus exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("checkUserStatus exception : ", e);
        }
    }

    
   
    
    /**
     * 登录
     */
    @ResponseBody
    @RequestMapping(value="/login" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public void login(@RequestBody String content, HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("login request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String userId = jsonObject.getString("userId");
            String password = jsonObject.getString("password");
            String identityGuid = jsonObject.getString("identityGuid");
            String appGuid = jsonObject.getString("appGuid");
            AcOperator acOperator = authenticationRService.loginCheck(userId, password, identityGuid, appGuid);
            Map<String, Object> loginInfo = authenticationRService.getInitInfoByUserIdAndIden(userId, identityGuid, appGuid);
            AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response,loginInfo,"YYYY-MM-dd HH:mm:ss");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("login exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("login exception : ", e);
        }
    }


    /**
     * 修改密码
     */
    @ResponseBody
    @RequestMapping(value="/updatePassword" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public void updatePassword(@RequestBody String content, HttpServletRequest request,
                                HttpServletResponse response) {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("updatePassword request : " + content);
            }
            JSONObject jsonObject= JSONObject.parseObject(content);
            String userId = jsonObject.getString("userId");
            String newPwd = jsonObject.getString("newPwd");
            String oldPwd = jsonObject.getString("oldPwd");;
             authenticationRService.updatePassword(userId, oldPwd, newPwd);
            AjaxUtils.ajaxJsonSuccessMessage(response,"");
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("updatePassword exception : ", e);
        } catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("updatePassword exception : ", e);
        }
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
