<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2023/1/14
  Time: 11:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>JSTL使用示例</title>
</head>
<body>
<!-- out标签的用法 -->
<%
    String username = "张三";
    request.setAttribute("username", username);
%>
姓名：<c:out value="${username}"></c:out>
<hr>

<!-- if标签的用法 -->
<form method="post">
    请输入成绩（100分制）：<input type="text" name="score" value="${param.score}" /> <input
        type="submit" />
</form>

<c:if test="${param.score>=90}">
    <c:out value="你的成绩是优秀"></c:out>
    <br>
</c:if>
<hr>

<!-- choose when otherwise标签的使用 -->
成绩等级：
<c:choose>
    <c:when test="${param.score>=90 && param.score<=100}">
        <c:out value="优秀"></c:out>
    </c:when>
    <c:when test="${param.score>=80 && param.score<90}">
        <c:out value="良"></c:out>
    </c:when>
    <c:when test="${param.score>=70 && param.score<80}">
        <c:out value="中等"></c:out>
    </c:when>
    <c:when test="${param.score>=60 && param.score<70}">
        <c:out value="及格"></c:out>
    </c:when>
    <c:when test="${param.score>=0 && param.score<60}">
        <c:out value="不及格"></c:out>
    </c:when>
    <c:otherwise>
        <c:out value="你的输入有误！"></c:out>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${param.score==100}">
        <c:out value="你是满分"></c:out>
    </c:when>
</c:choose>
<hr>


<%
    List<String> fruits = new ArrayList<String>();
    fruits.add("苹果");
    fruits.add("橘子");
    fruits.add("香蕉");
    fruits.add("葡萄");
    fruits.add("梨子");
    request.setAttribute("fruits", fruits);
%>


<!-- foreach标签的使用 -->
<c:forEach var="fruit" items="${fruits}">
    <c:out value="${fruit}"></c:out>
    <br>
</c:forEach>
<hr>


<c:forEach var="fruit" items="${fruits}" begin="1" end="3">
    <c:out value="${fruit}"></c:out>
    <br>
</c:forEach>
<hr>


<c:forEach var="fruit" items="${fruits}" step="2">
    <c:out value="${fruit}"></c:out>
    <br>
</c:forEach>
<hr>


<c:forEach var="fruit" items="${fruits}" varStatus="fru">
    <c:out value="${fruit}的四个属性值"></c:out>
    <c:out value="index:${fru.index}"></c:out>
    <c:out value="count:${fru.count}"></c:out>
    <c:out value="first:${fru.first}"></c:out>
    <c:out value="last:${fru.last}"></c:out>
    <br>
</c:forEach>
<hr>
</body>
</html>
