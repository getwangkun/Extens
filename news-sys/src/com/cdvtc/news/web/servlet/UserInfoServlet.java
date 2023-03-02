package com.cdvtc.news.web.servlet;

import com.cdvtc.news.model.User;
import com.cdvtc.news.service.UserService;
import com.cdvtc.news.service.impl.UserServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "UserInfoServlet", value = "/UserInfoServlet")
public class UserInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(10 * 1024 * 1024); // 单个文件大小限制10M
        upload.setSizeMax(50 * 1024 * 1024); // 总文件大小限制 5OM
        upload.setHeaderEncoding("UTF-8"); // 对中文文件编码处理

        User user = new User();
        UserService userService = new UserServiceImpl();
        try {
            List<FileItem> fileItemList = upload.parseRequest(request);

            for (FileItem fileItem : fileItemList) {
                if (fileItem.isFormField()) { //普通表单
                    String name = fileItem.getFieldName();
                    String value = fileItem.getString();
                    if("account".equalsIgnoreCase(name)){
                        user.setAccount(value);
                    } else if("nickname".equalsIgnoreCase(name)){
                        value = new String(value.getBytes("ISO-8859-1"),
                                "UTF-8"); //必须进行转码，否则会出现中文乱码
                        user.setNickname(value);
                    }else if("birthday".equalsIgnoreCase(name)){
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        Date birthday = df.parse(value); // 解析日期字符串为Date对象
                        user.setBirthday(birthday);
                    }else if("email".equalsIgnoreCase(name)){
                        user.setEmail(value);
                    }else if("mobile".equalsIgnoreCase(name)){
                        user.setMobile(value);
                    }
                } else { //上传文件
                    if (fileItem.getSize() > 0) { //文件不为空
                        String path = this.getServletContext().getRealPath("/img/photos");  //获取图片存储路径（服务器端目录）
                        String fileName = fileItem.getName();
                        File file = new File(path, fileName);

                        if(!file.exists()){ //文件不存在时才写入
                            fileItem.write(file); //写入文件
                        }
                        user.setPhoto(fileName); //将文件名写入头像字段
                    }
                }
            }

            if(user.getPhoto() == null) { //如果没有上传头像，使用以前头像
                String photo = ((User)request.getSession().getAttribute("user")).getPhoto(); //从session中获得当前值
                user.setPhoto(photo);
            }

            //保存用户信息至数据库
            userService.updateUser(user);
            //更新当前会话中用的户信息对象
            request.getSession().setAttribute("user", user);
        } catch (FileUploadException | ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("page", "index.jsp");
        request.setAttribute("message", "个人信息修改成功。");
        request.getRequestDispatcher("alert_jump.jsp").forward(request, response);
    }
}
