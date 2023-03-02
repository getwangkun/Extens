<%@ page import="com.cdvtc.news.model.User" %><%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2023/1/13
  Time: 17:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>EL表达式示例</title>
</head>
<body>
<%
    User user = new User();
    user.setAccount("user1");
    user.setNickname("JSP高手");
    pageContext.setAttribute("u", user);

    User user2 = new User();
    user2.setAccount("user2");
    user2.setNickname("JSP高高手");
    session.setAttribute("u", user2);
%>
<p>账号：${u.account}</p>
<p>姓名：${u['nickname']}</p>
<p>Session中的账户：${sessionScope.u.account}</p>
<div>全部Session对象：${sessionScope}</div>
</body>
</html>
