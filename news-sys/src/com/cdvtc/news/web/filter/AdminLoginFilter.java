package com.cdvtc.news.web.filter;

import com.cdvtc.news.model.Admin;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//import java.net.http.HttpRequest;

@WebFilter(filterName = "AdminLoginFilter", urlPatterns = "/admin/*")
public class AdminLoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
//        HttpServletResponse httpResponse = (HttpServletResponse)response;
        String uri = httpRequest.getRequestURI();
        if(!uri.contains("/login")) {
            Admin admin = (Admin)httpRequest.getSession().getAttribute("admin");
            if(admin == null){
                //未登陆，先进入自动登陆页面
                httpRequest.getRequestDispatcher("/admin/autoLogin").forward(request, response); // 这里的路径是相对于Web应用的根目录
//                httpResponse.sendRedirect("/admin/autoLogin");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
