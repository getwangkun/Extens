package com.cdvtc.news.web.servlet;

import com.cdvtc.news.model.User;
import com.cdvtc.news.service.UserService;
import com.cdvtc.news.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        User user = userService.login(account, password);
        if (user != null) { //登陆成功
            request.getSession().setAttribute("user", user);

            String rootPath = request.getServletPath(); //获取项目根目录的绝对路径
            response.sendRedirect(rootPath + "/index.jsp");
        } else { //登陆失败
            request.setAttribute("error", "账户或密码错误！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
