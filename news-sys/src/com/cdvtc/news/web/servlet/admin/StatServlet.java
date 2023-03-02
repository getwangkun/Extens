package com.cdvtc.news.web.servlet.admin;

import com.cdvtc.news.service.NewsService;
import com.cdvtc.news.service.impl.NewsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

@WebServlet(name = "StatServlet", value = "/admin/stat")
public class StatServlet extends BaseServlet implements SingleThreadModel{  //特别注意：多个方法共用Servlet有线程安全问题
    NewsService newsService = new NewsServiceImpl();

    /**
     * 向客户端返回JSON数据
     * @param statsList
     * @throws IOException
     */
    private void writeJson(List<Map<String, Object>> statsList) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("text/json;charset=UTF-8");  //设置返回类型，JSON格式

        String json = objectMapper.writeValueAsString(statsList);  // 将Java对象转为JSON字符串
        Writer out = response.getWriter();
        out.write(json);

        out.flush();
        if(out != null) {
            out.close();
        }
    }

    public void newsCountByCategory() throws IOException {
        List<Map<String, Object>> statsList = newsService.statNewsCountByCategory();

        writeJson(statsList);
    }

    public void newsCountByTag() throws IOException {
        List<Map<String, Object>> statsList = newsService.statNewsCountByTag();

        writeJson(statsList);
    }


    public void newsCommentCountByDate() throws IOException {
        List<Map<String, Object>> statsList = newsService.statNewsCommentCountByDate();

        writeJson(statsList);
    }

    public void newsCommentCountByUser() throws IOException {
        List<Map<String, Object>> statsList = newsService.statNewsCommentCountByUser();

        writeJson(statsList);
    }
}
