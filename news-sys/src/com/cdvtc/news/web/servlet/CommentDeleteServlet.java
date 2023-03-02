package com.cdvtc.news.web.servlet;

import com.cdvtc.news.model.User;
import com.cdvtc.news.service.CommentService;
import com.cdvtc.news.service.impl.CommentServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.Writer;

@WebServlet(name = "CommentDeleteServlet", value = "/deleteComment")
public class CommentDeleteServlet extends HttpServlet {
    private CommentService commentService = new CommentServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean result = false;
        String id = request.getParameter("id");
        User user = (User)request.getSession().getAttribute("user");
        if(id != null &&  user!= null) { //验证参数不为空，用户已登陆
            try {
                result = commentService.deleteComment(Long.valueOf(id));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // 将结果返回前端
        response.setContentType("text/plain;charset=UTF-8");  //设置返回类型，普通文本
        Writer writer = response.getWriter();
        writer.write(Boolean.toString(result));
    }
}
