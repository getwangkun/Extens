package com.cdvtc.news.web.servlet.admin;

import com.cdvtc.news.model.Category;
import com.cdvtc.news.service.CategoryService;
import com.cdvtc.news.service.impl.CategoryServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/admin/category")
public class CategoryServlet extends BaseServlet {
    CategoryService categoryService = new CategoryServiceImpl();

    public void list() throws ServletException, IOException {
        List<Category> categoryList = categoryService.getAllCategories();
        this.request.setAttribute("categoryList", categoryList);

        this.request.getRequestDispatcher("/admin/category-list.jsp").forward(this.request, this.response);
    }
}
