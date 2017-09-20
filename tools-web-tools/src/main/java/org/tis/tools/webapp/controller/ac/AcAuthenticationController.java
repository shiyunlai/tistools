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
import org.tis.tools.model.def.JNLConstants;
import org.tis.tools.model.po.ac.AcOperator;
import org.tis.tools.model.po.ac.AcOperatorIdentity;
import org.tis.tools.rservice.ac.capable.IAuthenticationRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.log.LogThreadLocal;
import org.tis.tools.webapp.log.OperateLog;
import org.tis.tools.webapp.log.ReturnType;
import org.tis.tools.webapp.util.AjaxUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
    @RequestMapping(value="/checkUserStatus", produces ="application/json; charset=UTF-8", method= RequestMethod.POST)
    public Map<String, Object> checkUserStatus(@RequestBody String content)  {
        JSONObject jsonObject= JSONObject.parseObject(content);
        String userId = jsonObject.getString("userId");
        List<AcOperatorIdentity> acOperatorIdentityList = authenticationRService.userStatusCheck(userId);
        return getReturnMap(acOperatorIdentityList);
    }

    /**
     * 登录
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_LOGIN,
            operateDesc = "登录ABF",
            retType = ReturnType.Map,
            id = "user.operatorGuid",
            name = "user.operatorName"
    )
    @ResponseBody
    @RequestMapping(value="/login", produces ="application/json;charset=UTF-8", method= RequestMethod.POST)
    public Map<String, Object> login(@RequestBody String content, HttpServletRequest request,
                      HttpServletResponse response, HttpSession httpSession) {
        JSONObject jsonObject= JSONObject.parseObject(content);
        String userId = jsonObject.getString("userId");
        String password = jsonObject.getString("password");
        String identityGuid = jsonObject.getString("identityGuid");
        String appGuid = jsonObject.getString("appGuid");
        LogThreadLocal.getLogBuilderLocal().getLog().setUserId(userId);
        AcOperator acOperator = authenticationRService.loginCheck(userId, password, identityGuid, appGuid);
        Map<String, Object> loginInfo = authenticationRService.getInitInfoByUserIdAndIden(userId, identityGuid, appGuid);
        LogThreadLocal.getLogBuilderLocal().getLog().setOperatorName(acOperator.getOperatorName());
        httpSession.setAttribute("userId", userId);
        httpSession.setAttribute("operatorName", acOperator.getOperatorName());


        

        return getReturnMap(loginInfo);
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
     * 注销登陆
     */
    @ResponseBody
    @RequestMapping(value="/logout" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public Map<String,Object> logout(@RequestBody String content, HttpSession httpSession) {
        while(httpSession.getAttributeNames().hasMoreElements()) {
            httpSession.removeAttribute(httpSession.getAttributeNames().nextElement());
        }
        return getReturnMap(null);
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
