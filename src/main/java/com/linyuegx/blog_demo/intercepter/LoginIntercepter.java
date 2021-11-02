package com.linyuegx.blog_demo.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by lin on  2021/10/26 0:15
 */

public class LoginIntercepter implements HandlerInterceptor {

        public boolean preHandle(HttpServletRequest request,
                               HttpServletResponse response,
                               Object Handler) throws Exception {
        if (request.getSession().getAttribute("user")==null){
            response.sendRedirect("/admin");
            return false;
        }
        return true;
    }
}
