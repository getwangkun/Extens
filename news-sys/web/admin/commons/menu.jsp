<%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2021/12/15
  Time: 17:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<div class="sidebar">
    <h4>功能导航</h4>
    <div class="list-group">
        <a href="${pageContext.request.contextPath}/admin/news?method=list" class="list-group-item ${fn:contains(requestScope['javax.servlet.forward.request_uri'], '/admin/news') ? 'active': ''}">
            新闻管理
        </a>
        <a href="${pageContext.request.contextPath}/admin/category?method=list" class="list-group-item ${fn:contains(requestScope['javax.servlet.forward.request_uri'], '/admin/category') ? 'active': ''}">
            分类管理
        </a>
        <a href="${pageContext.request.contextPath}/admin/tag?method=list" class="list-group-item ${fn:contains(requestScope['javax.servlet.forward.request_uri'], '/admin/tag') ? 'active': ''}">
            标签管理
        </a>
        <a href="${pageContext.request.contextPath}/admin/admin?method=list" class="list-group-item ${fn:contains(requestScope['javax.servlet.forward.request_uri'], '/admin/admin') ? 'active': ''}">
           管理员管理
        </a>
        <a href="${pageContext.request.contextPath}/admin/user?method=list" class="list-group-item ${fn:contains(requestScope['javax.servlet.forward.request_uri'], '/admin/user') ? 'active': ''}">
            会员管理
        </a>
        <a href="${pageContext.request.contextPath}/admin/online?method=list" class="list-group-item ${fn:contains(requestScope['javax.servlet.forward.request_uri'], '/admin/online') ? 'active': ''}">
            在线会员
        </a>
        <a href="#" class="list-group-item">修改密码</a>
    </div>
</div>
