package org.tis.tools.webapp.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.tis.tools.webapp.util.AjaxUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //获取Session
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("userId");
        if(username != null){
            return true;
        }
        //不符合条件的，跳转到登录界面
        AjaxUtils.ajaxJsonFailMessage(response, "SYS_4444", "权限失效，请重新登录");
        return false;
    }


    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
