<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2021/12/30
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>在线会员</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<%@include file="commons/navbar.jsp"%>
<div class="container-fluid">
    <div class="row">
        <%@include file="commons/menu.jsp"%>
        <div class="main">
            <div class="span6">  <!-- 面包屑导航 -->
                <ul class="breadcrumb">
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/">首页</a> <span class="divider"></span>
                    </li>
                    <li class="active">在线会员</li>
                </ul>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">在线会员列表 &nbsp;<i class="fa fa-book pull-right" aria-hidden="true"></i></h3>
                </div>
                <div class="panel-body">
                    <table class="table">
                        <thead>
                        <tr>
                            <th>会话</th>
                            <th>IP地址</th>
                            <th>编号</th>
                            <th>账户</th>
                            <th>昵称</th>
                            <th>手机号</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${onlineUserMap.keySet()}" var="session">
                            <tr>
                                <td>${session.id}</td>
                                <td>${session.getAttribute('ipAddress')}</>
                                <td>${onlineUserMap[session].id}</td>
                                <td>${onlineUserMap[session].account}</td>
                                <td>${onlineUserMap[session].nickname}</td>
                                <td>${onlineUserMap[session].mobile}</td>
                                <td><a href="${pageContext.request.contextPath}/admin/online?method=offline&sid=${session.id}">强制下线</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div> <!-- /container -->
<%@include file="./commons/footer.jsp"%>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
</body>
</html>
