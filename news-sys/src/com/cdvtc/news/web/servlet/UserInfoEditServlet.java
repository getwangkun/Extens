package com.cdvtc.news.web.servlet;

import com.cdvtc.news.model.User;
import com.cdvtc.news.service.UserService;
import com.cdvtc.news.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet(name = "UserInfoEditServlet", value = "/UserInfoEditServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*10, 	// 10 MB
        maxFileSize=1024*1024*10,      	// 单个文件大小限制10M
        maxRequestSize=1024*1024*50)   	// 总文件大小限制 5OM
public class UserInfoEditServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");  // 防止中文文件名乱码

        String account = request.getParameter("account");
        String nickname = request.getParameter("nickname");
        String birthday = request.getParameter("birthday");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");

        User user = new User();

        user.setAccount(account);
        user.setNickname(nickname);
        user.setEmail(email);
        user.setMobile(mobile);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            user.setBirthday( df.parse(birthday)); // 解析日期字符串为Date对象
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // 获取头像
        Part part = request.getPart("photo");
        if (part != null && part.getSize() > 0) {
            String uploadFilePath = this.getServletContext().getRealPath("/img/photos");
            String fileName = part.getSubmittedFileName();
            // 写文件
            part.write(uploadFilePath + File.separator + fileName);
            user.setPhoto(fileName);
        } else {
            String photo = ((User)request.getSession().getAttribute("user")).getPhoto(); //从session中获得当前值
            user.setPhoto(photo);
        }

        //保存用户信息至数据库
        UserService userService = new UserServiceImpl();
        userService.updateUser(user);
        //更新当前会话中用的户信息对象
        request.getSession().setAttribute("user", user);


        // 页面跳转
        request.setAttribute("page", "index.jsp");
        request.setAttribute("message", "个人信息修改成功。");
        request.getRequestDispatcher("alert_jump.jsp").forward(request, response);
    }
}
