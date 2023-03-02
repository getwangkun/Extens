package com.cdvtc.news.web.servlet.admin;

import com.cdvtc.news.model.Admin;
import com.cdvtc.news.service.AdminService;
import com.cdvtc.news.service.impl.AdminServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "AutoLoginServlet", value = "/admin/autoLogin")
public class AutoLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminLogin =  null;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("adminLogin")){
                adminLogin = cookie.getValue();
            }
        }

        if(adminLogin != null) {
            String[] loginStr = adminLogin.split("::");

            AdminService adminService = new AdminServiceImpl();
            Admin admin = adminService.login(loginStr[0], loginStr[1]);
            if (admin != null) { //登陆成功
                request.getSession().setAttribute("admin", admin);

                String rootPath = request.getContextPath();
                response.sendRedirect(rootPath + "/admin/index.jsp"); //获取项目根目录的绝对路径
            } else { //登陆失败，进入登陆页面，不提示错误
                request.getRequestDispatcher("/admin/login.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath()+"/admin/login.jsp"); //进入登陆页面
        }
    }
}
