package com.cdvtc.news.web.servlet.admin;

import com.cdvtc.news.model.Comment;
import com.cdvtc.news.model.News;
import com.cdvtc.news.model.Tag;
import com.cdvtc.news.service.CommentService;
import com.cdvtc.news.service.NewsService;
import com.cdvtc.news.service.TagService;
import com.cdvtc.news.service.impl.CommentServiceImpl;
import com.cdvtc.news.service.impl.NewsServiceImpl;
import com.cdvtc.news.service.impl.TagServiceImpl;
import com.cdvtc.news.util.CommentUtil;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@WebServlet(name = "CommentAdminServlet", value = "/admin/comment")
public class CommentServlet extends BaseServlet {
    private CommentService commentService = new CommentServiceImpl();
    private NewsService newsService = new NewsServiceImpl();
    private TagService tagService = new TagServiceImpl();

    public void list() throws ServletException, IOException {
       Integer newsId = Integer.valueOf(request.getParameter("newsId"));

       List<Comment> comments = commentService.getCommentsByNewsId(newsId);
       request.setAttribute("comments", comments);
       request.setAttribute("commentMap", CommentUtil.toMap(comments)); //保存评论Map，解决jsp:param不能传对象的问题（改为只传Id）

       News news = newsService.getNewsById(newsId);
       request.setAttribute("news", news);

       Set<Tag> tagSet = tagService.getTagsByNewsId(newsId);
        request.setAttribute("tagSet", tagSet);

       request.getRequestDispatcher("/admin/comments.jsp").forward(request, response);
    }

    /**
     * 删除评论
     */
    public void delete() throws IOException {
        Integer newsId = Integer.parseInt(request.getParameter("newsId"));
        Long commentId = Long.parseLong(request.getParameter("commentId"));
        commentService.deleteComment(commentId);

        response.sendRedirect(request.getContextPath() + "/admin/comment?method=list&newsId="+newsId);
    }

    /**
     * *恢复评论
     */
    public void recover() throws IOException {
        Integer newsId = Integer.parseInt(request.getParameter("newsId"));
        Long commentId = Long.parseLong(request.getParameter("commentId"));
        commentService.recoverComment(commentId);

        response.sendRedirect(request.getContextPath() + "/admin/comment?method=list&newsId="+newsId);
    }
}
