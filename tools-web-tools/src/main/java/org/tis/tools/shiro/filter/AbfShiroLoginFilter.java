package org.tis.tools.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.tis.tools.webapp.util.AjaxUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public class AbfShiroLoginFilter extends AdviceFilter {

    /**
     * 在访问controller前判断是否登录，返回错误信息，不进行重定向。
     * @param request
     * @param response
     * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()) {
            return true;
        }
        AjaxUtils.ajaxJsonFailMessage((HttpServletResponse) response, "SYS_4444", "会话失效，请重新登录");
        return false;
    }

}