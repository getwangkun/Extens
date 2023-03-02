package com.cdvtc.news.web.servlet.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //保存请求和响应对象，在子类中使用
        this.request = request;
        this.response = response;

        request.setCharacterEncoding("UTF-8"); //设置编码（可解决post方式的中文乱码问题）

        // 获取用户请求的方法名
        String methodName = request.getParameter("method");

        try {
            /**
             * 利用Java反射机制，实现根据方法名动态获取到对应方法，并执行方法调用
             */
            Method method = this.getClass().getMethod(methodName, null);
            method.invoke(this, null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
