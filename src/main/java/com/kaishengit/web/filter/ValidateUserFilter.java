package com.kaishengit.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 判断用户是否登录的过滤器
 */
public class ValidateUserFilter extends AbstractFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //先获取用户请求的路径
        String path = request.getRequestURI();

        if(path.startsWith("/user/")) {
            HttpSession session = request.getSession();
            if(session.getAttribute("curr_user") == null) {
                response.sendRedirect("/login.do?state=1001&redirecturl="+path);
            } else {
                filterChain.doFilter(request,response);
            }
        } else {
            filterChain.doFilter(request,response);
        }


    }
}
