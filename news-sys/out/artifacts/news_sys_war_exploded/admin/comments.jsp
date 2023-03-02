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
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/news?method=list">新闻列表</a> <span class="divider"></span>
                    </li>
                    <li class="active">评论管理</li>
                </ul>
            </div>

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="news-title">${news.title}
                    </h3>
                    <div class="news-status">
                        ${news.clickCount}阅读 • ${news.pubDateInterval}
                        <c:forEach var="tag" items="${tagSet}">
                            <div class="label label-default">${tag.name}</div>
                        </c:forEach>
                    </div>
                </div>
                <div class="panel-body">
                    <c:forEach items="${comments}" var="comment">
                        <jsp:include page="commons/comment.jsp">
                            <jsp:param name="commentId" value="${comment.id}"/>
                        </jsp:include>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div> <!-- /container -->
<%@include file="./commons/footer.jsp"%>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script>
    function deleteComment(newsId, commentId) {
        if(confirm("您确定要删除该评论？")){
            location.href = '${pageContext.request.contextPath}/admin/comment?method=delete&newsId='+newsId+'&commentId='+commentId
        }
    }
    function recoverComment(newsId, commentId) {
        if(confirm("您确定要恢复该评论？")){
            location.href = '${pageContext.request.contextPath}/admin/comment?method=recover&newsId='+newsId+'&commentId='+commentId
        }
    }
</script>
</body>
</html>
