package com.cdvtc.news.web.servlet.admin;

import com.cdvtc.news.model.User;
import com.cdvtc.news.service.UserService;
import com.cdvtc.news.service.impl.UserServiceImpl;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.Boolean;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "UserServlet", value = "/admin/user")
public class UserServlet extends BaseServlet {
    UserService userService = new UserServiceImpl();
    public void list() throws ServletException, IOException {
        List<User> userList = userService.getAllUsers();
        request.setAttribute("userList", userList);

        request.getRequestDispatcher("/admin/user-list.jsp").forward(request, response);
    }

    public void forbidden() throws ServletException, IOException {
        int id = Integer.valueOf(request.getParameter("id"));
        boolean forbidden = Boolean.valueOf(request.getParameter("forbidden"));
        userService.forbiddenUser(id, forbidden);

        this.list();  // 调用显示列表方法(重用代码）
    }

    public void export() throws IOException, WriteException {


        //设置响应头参数
        String fileName = "会员列表.xls";//设置导出的文件名称
        String contentType = "application/vnd.ms-excel";//定义导出文件的格式的字符串
        String recommendedName = new String(fileName.getBytes("GBK"), "iso_8859_1");//设置文件名称的编码格式
        response.setContentType(contentType);//设置导出文件格式
        response.setHeader("Content-Disposition", "attachment; filename=" + recommendedName);//
        response.resetBuffer();

        //将输出流传递给JXL进行写Excel工作簿内容
        WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
        WritableSheet sheet =workbook.createSheet("会员列表",0); // 创建工作表


        //第一行设置列名:创建数组
        String[] titles = {"编号", "账号", "昵称", "手机号码", "电子邮件", "生日", "注册时间", "是否禁用"};
        Label label = null;
        //设置列名
        for (int i = 0; i < titles.length; i++) {
            label = new Label(i, 0, titles[i]);
            sheet.addCell(label);
        }
        // 获取数据
        List<User> userList = userService.getAllUsers();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // 生日格式化
        DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //注册时间格式化
        //追加数据
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);

            label = new Label(0, i + 1, user.getId().toString()); //第1列：编号
            sheet.addCell(label);

            label = new Label(1, i + 1, user.getAccount()); //第2列：账号
            sheet.addCell(label);

            label = new Label(2, i + 1, user.getNickname()); //第3列：昵称
            sheet.addCell(label);

            label = new Label(3, i + 1, user.getMobile()); //第4列：手机号码
            sheet.addCell(label);

            label = new Label(4, i + 1, user.getEmail()); //第4列：电子邮件
            sheet.addCell(label);

            label = new Label(5, i + 1, dateFormat.format(user.getBirthday())); //第5列：生日
            sheet.addCell(label);

            label = new Label(6, i + 1, timeFormat.format(user.getRegDate())); //第6列：注册时间
            sheet.addCell(label);

            label = new Label(7, i + 1, user.getForbidden() ? "是" : "否"); //第7列：是否禁用
            sheet.addCell(label);
        }

        workbook.write();  //执行写操作
        workbook.close();  //关闭工作簿
    }
}
