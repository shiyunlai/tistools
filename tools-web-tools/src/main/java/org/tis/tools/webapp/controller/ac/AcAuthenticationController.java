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
            retType = ReturnType.Object,
            id = "userId",
            name = "operatorName"
    )
    @ResponseBody
    @RequestMapping(value="/login", produces ="application/json;charset=UTF-8", method= RequestMethod.POST)
    public Map<String, Object> login(@RequestBody String content, HttpSession httpSession) {
        JSONObject jsonObject= JSONObject.parseObject(content);
        String userId = jsonObject.getString("userId");
        String password = jsonObject.getString("password");
        String identityGuid = jsonObject.getString("identityGuid");
        String appGuid = jsonObject.getString("appGuid");
        LogThreadLocal.getLogBuilderLocal().getLog().setUserId(userId);
        AcOperator acOperator = authenticationRService.loginCheck(userId, password, identityGuid, appGuid);
        LogThreadLocal.getLogBuilderLocal().getLog().setOperatorName(acOperator.getOperatorName());
        httpSession.setAttribute("userId", userId);
        httpSession.setAttribute("identity", identityGuid);
        httpSession.setAttribute("operatorName", acOperator.getOperatorName());
        httpSession.setAttribute("app", appGuid);
        return getReturnMap(acOperator);
    }


    /**
     * 修改密码
     */
    @OperateLog(
            operateType = JNLConstants.OPEARTE_TYPE_UPDATE,
            operateDesc = "修改密码",
            retType = ReturnType.Object,
            id = "userId",
            name = "operatorName"
    )
    @ResponseBody
    @RequestMapping(value="/updatePassword" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String,Object> updatePassword(@RequestBody String content, HttpSession httpSession) {

        JSONObject jsonObject= JSONObject.parseObject(content);
        String userId = jsonObject.getString("userId");
        String newPwd = jsonObject.getString("newPwd");
        String oldPwd = jsonObject.getString("oldPwd");
        AcOperator acOperator = authenticationRService.updatePassword(userId, oldPwd, newPwd);
        while(httpSession.getAttributeNames().hasMoreElements()) {
            httpSession.removeAttribute(httpSession.getAttributeNames().nextElement());
        }
        return getReturnMap(acOperator);
    }

    /**
     * 注销登陆
     */
    @ResponseBody
    @RequestMapping(value="/logout" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String,Object> logout(@RequestBody String content, HttpSession httpSession) {
        while(httpSession.getAttributeNames().hasMoreElements()) {
            httpSession.removeAttribute(httpSession.getAttributeNames().nextElement());
        }
        return getReturnMap(null);
    }

    /**
     * 页面初始化
     * @param httpSession
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/pageInit" ,produces = "application/json;charset=UTF-8",method= RequestMethod.POST)
    public Map<String, Object> pageInit(HttpSession httpSession) {
        String userId = (String) httpSession.getAttribute("userId");
        String identityGuid = (String) httpSession.getAttribute("identity");
        String appGuid = (String) httpSession.getAttribute("app");
        return getReturnMap(authenticationRService.getInitInfoByUserIdAndIden(userId, identityGuid, appGuid));
    }


}
