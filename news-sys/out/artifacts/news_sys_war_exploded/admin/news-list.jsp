<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2021/12/15
  Time: 14:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新闻管理</title>
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
                    <li class="active">新闻列表</li>
                </ul>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">新闻列表 &nbsp;<i class="fa fa-book pull-right" aria-hidden="true"></i></h3>
                </div>
                <div class="panel-body">
                    <div class="pull-right">
                        <a class="btn btn-primary" href="${pageContext.request.contextPath}/admin/news?method=add">新增</a>
                    </div>
                    <table class="table">
                        <thead>
                        <tr>
                            <th>编号</th>
                            <th>标题</th>
                            <th>图片</th>
                            <th>编辑</th>
                            <th>发布时间</th>
                            <th>是否置顶</th>
                            <th>评论数</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pagedNews.pagedData}" var="news">
                            <tr>
                                <td>${news.id}</td>
                                <td>${news.title}</td>
                                <td><img src="${pageContext.request.contextPath}/img/${news.img}" class="img-rounded" width="100px"></td>
                                <td>${news.editor.name}</td>
                                <td><fmt:formatDate value="${news.pubDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                                <td>${news.stick?"是":"否"}</td>
                                <td>
                                    <c:if test="${news.commentNum > 0}">
                                        <a href="${pageContext.request.contextPath}/admin/comment?method=list&newsId=${news.id}">${news.commentNum}</a>
                                    </c:if>
                                    <c:if test="${news.commentNum == 0}">
                                        ${news.commentNum}
                                    </c:if>
                                </td>
                                <td><a href="${pageContext.request.contextPath}/admin/news?method=edit&id=${news.id}">编辑</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <div class="row text-center">
                        <ul class="pagination">
                            <li class="pull-left">
                                总共${pagedNews.totalPages}页,${pagedNews.totalRows}条新闻
                                当前显示第${pagedNews.startRow}至${pagedNews.endRow}条
                            </li>
                            <li><a href="${pageContext.request.contextPath}/admin/news?method=list&size=10&page=${pagedNews.previousPage}">&laquo;</a></li>
                            <c:forEach begin="1" end="${pagedNews.totalPages}" var="pageNumber">
                                <li><a href="${pageContext.request.contextPath}/admin/news?method=list&size=10&page=${pageNumber}">${pageNumber}</a></li>
                            </c:forEach>
                            <li><a href="${pageContext.request.contextPath}/admin/news?method=list&size=10&page=${pagedNews.nextPage}">&raquo;</a></li>
                        </ul>
                    </div>
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
