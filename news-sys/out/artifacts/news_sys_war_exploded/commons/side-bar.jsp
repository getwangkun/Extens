<%@ page import="com.cdvtc.news.dao.TagDao" %>
<%@ page import="com.cdvtc.news.dao.impl.TagDaoImpl" %>
<%@ page import="com.cdvtc.news.model.Tag" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2021/12/15
  Time: 13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    TagDao tagDao = new TagDaoImpl();
    List<Tag> tagList = tagDao.getAllTags();
    String tag = request.getParameter("tag");
%>
<div class="list-group side-bar hidden-xs">
    <%for (Tag t: tagList) { %>
    <a href="index.jsp?tag=<%=t.getId()%>" class="list-group-item<%if(tag !=null && tag.equalsIgnoreCase(String.valueOf(t.getId()))){%> active<%}%>"><%=t.getName()%></a>
    <%}%>
</div>
