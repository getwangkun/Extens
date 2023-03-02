package com.cdvtc.news.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

/**
 * 数据库连接工具类
 */
public class DBUtil {

//    private static final String url = "jdbc:mysql://localhost/news-sys?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Shanghai";
////    private static final String user = "root";
////    private static final String password="123456";
////
////    /**
////     * 获取数据库连接
////     */
////    public static Connection getConnection()  {
////        // 获取连接,URL
////        Connection con = null;
////        try {
////            Class.forName("com.mysql.cj.jdbc.Driver");
////            con = DriverManager.getConnection(url, user, password);
////        } catch (SQLException | ClassNotFoundException e) {
////            e.printStackTrace();
////        }
////        return con;
////    }
    private static DataSource ds;  // 数据源，使用连接池后替代DriverManager

    public static Connection getConnection() {
        Connection con = null;
        try {
            if(ds == null){
                Context ctx = new InitialContext();
                // 获取jdbc数据源，“comp/env”为默认路径，“jdbc/news”为数据源名
                ds = (DataSource) ctx.lookup("java:comp/env/jdbc/news");
            }
            con = ds.getConnection();
        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }

        return con;
    }
    /**
     * * 释放JDBC相关资源
     * @param rs
     * @param st
     * @param conn
     */
    public static void release(ResultSet rs, Statement st, Connection conn) {
        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection conn = DBUtil.getConnection();
        //获取数据
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("select * from category");// 执行SQL查询
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            System.out.println("编号：" + id + ", 类别：" + name);
        }

        //释放连接资源
        DBUtil.release(rs, st, conn);
    }

}
