package com.cdvtc.news.web.servlet.admin;

import com.cdvtc.news.model.Admin;
import com.cdvtc.news.service.AdminService;
import com.cdvtc.news.service.impl.AdminServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminServlet", value = "/admin/admin")
public class AdminServlet extends BaseServlet {
    AdminService adminService = new AdminServiceImpl();
    public void list() throws ServletException, IOException {
        List<Admin> adminList = adminService.getAllAdmins();
        request.setAttribute("adminList", adminList);

        request.getRequestDispatcher("/admin/admin-list.jsp").forward(request, response);
    }

    public void add() throws ServletException, IOException {
        request.getRequestDispatcher("/admin/admin-edit.jsp").forward(request, response);
    }

    public void edit() throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Admin admin = adminService.getAdminById(id);
        request.setAttribute("admin", admin);

        request.getRequestDispatcher("/admin/admin-edit.jsp").forward(request, response);
    }

    public void save() throws IOException {
        String account =request.getParameter("account");
        String name =request.getParameter("name");
        String password =request.getParameter("password");

        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setName(name);
        admin.setPassword(password);

        String id = request.getParameter("id");
        if(id != null) { // id不为空，更新管理员
            adminService.update(admin);
        } else { // 新增管理员
            adminService.add(admin);
        }

        response.sendRedirect(request.getContextPath() + "/admin/admin?method=list");
    }

    public void delete() throws IOException {
        Integer id = Integer.parseInt(request.getParameter("id"));

        adminService.delete(id);
        response.sendRedirect(request.getContextPath() + "/admin/admin?method=list");
    }
}
