package com.cdvtc.news.web.servlet.admin;

import com.cdvtc.news.model.Tag;
import com.cdvtc.news.service.TagService;
import com.cdvtc.news.service.impl.TagServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "TagServlet", value = "/admin/tag")
public class TagServlet extends BaseServlet {
    TagService tagService = new TagServiceImpl();

    public void list() throws ServletException, IOException {
        List<Tag> tagList = tagService.getAllTags();
        this.request.setAttribute("tagList", tagList);

        this.request.getRequestDispatcher("/admin/tag-list.jsp").forward(this.request, this.response);
    }
}
