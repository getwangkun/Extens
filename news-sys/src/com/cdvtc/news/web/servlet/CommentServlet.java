package com.cdvtc.news.web.servlet;

import com.cdvtc.news.model.Comment;
import com.cdvtc.news.model.News;
import com.cdvtc.news.model.User;
import com.cdvtc.news.service.CommentService;
import com.cdvtc.news.service.impl.CommentServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CommentServlet", value = "/CommentServlet")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer newsId =  Integer.valueOf(request.getParameter("newsId"));
        String replyForId = request.getParameter("replyForId"); //被回复的评论ID，新评论则为null
        String content = request.getParameter("content");
        User user = (User)request.getSession().getAttribute("user"); // 获取当前登陆用户

        Comment comment = new Comment();
        comment.setContent(content);
        News news = new News();
        news.setId(newsId);
        comment.setNews(news);
        comment.setCreator(user);
        comment.setIpAddress(request.getRemoteAddr());  //获取用户IP地址
        if(replyForId != null){
            Comment replyFor = new Comment();
            replyFor.setId(Long.valueOf(replyForId));
            comment.setReplyFor(replyFor);
        }

        CommentService commentService = new CommentServiceImpl();
        commentService.addComment(comment);

        //页面跳转回当前新闻页
        response.sendRedirect("news.jsp?id="+newsId);
    }
}
