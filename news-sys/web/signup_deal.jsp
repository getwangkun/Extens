<%@ page import="com.cdvtc.news.model.User" %>
<%@ page import="com.cdvtc.news.util.Md5Util" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cdvtc.news.service.UserService" %>
<%@ page import="com.cdvtc.news.service.impl.UserServiceImpl" %><%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2021/12/20
  Time: 15:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    /**
     * 获取参数
     */
    request.setCharacterEncoding("UTF-8"); //设置编码，解决Post方式的中文乱码问题
    String account = request.getParameter("account");
    String nickname = request.getParameter("nickname");
    String mobile = request.getParameter("mobile");
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String birthday = request.getParameter("birthday");
    String photo = request.getParameter("photo");

    /**
     * 数据验证
     */
    UserService userService = new UserServiceImpl();
    String error_account = null;
    if(userService.isUserExisted("account", account)){
        error_account = "账户已经存在！";
        request.setAttribute("error_account", error_account);
    }
    String error_nickname = null;
    if(userService.isUserExisted("nickname", nickname)){
        error_nickname = "昵称已经存在！";
        request.setAttribute("error_nickname", error_nickname);
    }
    String error_mobile = null;
    if(userService.isUserExisted("mobile", mobile)){
        error_mobile = "手机号码已经存在！";
        request.setAttribute("error_mobile", error_mobile);
    }
    String error_email = null;
    if(email != null && userService.isUserExisted("email", email)){ // 注意：email为null时
        error_email = "电子邮件已经存在！";
        request.setAttribute("error_email", error_email);
    }


    /**
     * 根据验证结果进行页面跳转
     */
    if(error_account==null && error_nickname==null && error_mobile==null && error_email==null){ //验证通过
        User user = new User();
        user.setNickname(nickname);
        user.setEmail(email);
        user.setMobile(mobile);
        user.setAccount(account);
        user.setPassword(Md5Util.md5(password)); //密码使用MD5加密保存
        user.setPhoto(photo);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        user.setBirthday(df.parse(birthday));

        userService.addUser(user);

        //进入登陆页面
//        response.sendRedirect("login.jsp");
        request.setAttribute("page", "login.jsp");
        request.setAttribute("message", "账户注册成功，请使用该账户登陆系统。");
        request.getRequestDispatcher("alert_jump.jsp").forward(request, response);
    } else { //返回至注册页面
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }
%>
