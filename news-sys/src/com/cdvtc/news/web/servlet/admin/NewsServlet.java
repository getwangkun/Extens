package com.cdvtc.news.web.servlet.admin;

import com.cdvtc.news.dao.Page;
import com.cdvtc.news.model.Admin;
import com.cdvtc.news.model.Category;
import com.cdvtc.news.model.News;
import com.cdvtc.news.model.Tag;
import com.cdvtc.news.service.CategoryService;
import com.cdvtc.news.service.NewsService;
import com.cdvtc.news.service.TagService;
import com.cdvtc.news.service.impl.CategoryServiceImpl;
import com.cdvtc.news.service.impl.NewsServiceImpl;
import com.cdvtc.news.service.impl.TagServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "NewsAdminServlet", value = "/admin/news")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,    // 10 MB
        maxFileSize = 1024 * 1024 * 10,        // 单个文件大小限制10M
        maxRequestSize = 1024 * 1024 * 50)    // 总文件大小限制 5OM
public class NewsServlet extends BaseServlet {
    NewsService newsService = new NewsServiceImpl();
    CategoryService categoryService = new CategoryServiceImpl();
    TagService tagService = new TagServiceImpl();

    public void list() throws ServletException, IOException {

        int pageSize = 10;
        int pageNumer = 1;
        String page = this.request.getParameter("page");
        String size = this.request.getParameter("size");
        String key = this.request.getParameter("key");
        if (page != null) {
            pageNumer = Integer.valueOf(page);
        }
        if (size != null) {
            pageSize = Integer.valueOf(size);
        }

//        List<News> newsList = newsDao.getAllNews();

        Page<News> pagedNews = newsService.getPagedNews(pageNumer, pageSize, key);

        this.request.setAttribute("pagedNews", pagedNews);
        this.request.getRequestDispatcher("/admin/news-list.jsp").forward(this.request, this.response);
    }

    public void add() throws ServletException, IOException {
        List<Category> categories = categoryService.getAllCategories();
        List<Tag> tags = tagService.getAllTags();
        this.request.setAttribute("categories", categories);
        this.request.setAttribute("tags", tags);
        this.request.getRequestDispatcher("/admin/news-edit.jsp").forward(this.request, this.response);
    }

    public void save() throws Exception {
        //获取普通表单参数
        request.setCharacterEncoding("UTF-8"); // 防止参数值及文件出现中文乱码
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String stick = request.getParameter("stick");
        String categoryId = request.getParameter("categoryId");


        String[] tags = request.getParameterValues("tag"); // 注意标签是多值参数
        Set<Integer> tagIds = new HashSet<>();
        for (String tag : tags) {
            tagIds.add(Integer.parseInt(tag));
        }

        // 将表单参数写入数据库
        News news = new News();
        news.setEditor((Admin) this.request.getSession().getAttribute("admin")); //以当前登陆用户作为新闻编辑
        if (id != null) {
            news.setId(Integer.parseInt(id));
        }
        news.setTitle(title);
        news.setContent(content);
        news.setStick(Boolean.valueOf(stick)); // Boolean类型，需要类型转换

        Category category = new Category();
        category.setId(Integer.valueOf(categoryId));
        news.setCategory(category);

        //获取上传文件
        Part part = request.getPart("img");
        if (part != null && part.getSize() > 0) {
            String uploadFilePath = this.getServletContext().getRealPath("/img");  //获取图片存储路径（服务器端目录）
            String uploadFileName = part.getSubmittedFileName();

            //为避免文件名重复，使用UUID文件名，后缀名不变
            String fileName = UUID.randomUUID() + uploadFileName.substring(uploadFileName.indexOf("."));

            //写入文件
            part.write(uploadFilePath + File.separator + fileName);
            news.setImg(fileName); //写入上传图片的文件名
        }

        if (news.getId() != null) { //更新
            newsService.updateNews(news);
            tagService.updateTagsForNews(news.getId(), tagIds);
        } else { //新增
            if (news.getImg() == null) {
                news.setImg("news.jpg"); //使用默认图片
            }
            int newsId = newsService.addNews(news);
            tagService.addTagsForNews(newsId, tagIds); // 单独更新新闻标签
        }

        this.response.sendRedirect(request.getContextPath() + "/admin/news?method=list");
    }

    public void edit() throws ServletException, IOException {
        int id = Integer.valueOf(this.request.getParameter("id"));
        News news = newsService.getNewsById(id);

        //获取新闻的标签编号集合
        Set<Tag> tagSet = tagService.getTagsByNewsId(id);
        Set<Integer> tagIdSet = new HashSet<>();
        for (Tag tag : tagSet) {
            tagIdSet.add(tag.getId());
        }

        List<Category> categories = categoryService.getAllCategories();
        List<Tag> tags = tagService.getAllTags();
        this.request.setAttribute("news", news);
        this.request.setAttribute("tagIdSet", tagIdSet);
        this.request.setAttribute("categories", categories);
        this.request.setAttribute("tags", tags);
        this.request.getRequestDispatcher("/admin/news-edit.jsp").forward(this.request, this.response);
    }
}
