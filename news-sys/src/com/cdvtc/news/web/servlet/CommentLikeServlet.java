package com.cdvtc.news.web.servlet;

import com.cdvtc.news.service.CommentService;
import com.cdvtc.news.service.impl.CommentServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.Writer;

@WebServlet(name = "CommentLikeServlet", value = "/CommentLikeServlet")
public class CommentLikeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommentService commentService = new CommentServiceImpl();

        int id =  Integer.valueOf(request.getParameter("id"));
        String operate = request.getParameter("operate");
        int resultNum = -1;
        if("like".equals(operate) || "unlike".equals(operate)) {  //点赞或取消点赞
            resultNum = commentService.updateLikeNum(id, "like".equals(operate));
        } else if("dislike".equals(operate) || "undislike".equals(operate)) { //踩或取消踩
            resultNum = commentService.updateDislikeNum(id, "dislike".equals(operate));
        }

        response.setContentType("text/plain;charset=UTF-8");  //设置返回类型，普通文本
        Writer writer = response.getWriter();
        writer.write(String.valueOf(resultNum));  //注意转为文本写入，数字会被认为ascii码
        writer.flush();
        writer.close();
    }
}
