<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2021/12/15
  Time: 14:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新闻编辑</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/quill.snow.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<%@include file="commons/navbar.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@include file="commons/menu.jsp" %>
        <div class="main">
            <div class="span6">  <!-- 面包屑导航 -->
                <ul class="breadcrumb">
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/">首页</a> <span class="divider"></span>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/news?method=list">新闻列表</a> <span class="divider"></span>
                    </li>
                    <li class="active">新闻编辑</li>
                </ul>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">${param.method eq 'add' ?'新增':'编辑'}新闻 &nbsp;<i class="fa fa-book pull-right" aria-hidden="true"></i></h3>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" action="${pageContext.request.contextPath}/admin/news?method=save" method="post" enctype="multipart/form-data" onsubmit="setContent();">
                        <c:if test="${not empty news}">
                            <input type="hidden" name="id" value="${news.id}">
                        </c:if>
                        <div class="form-group">
                            <label for="title" class="col-sm-2 control-label">标题：</label>
                            <div class="col-sm-10">
                                <input type="text" name="title" class="form-control" id="title" placeholder="请输入新闻标题" value="${news.title}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="img" class="col-sm-2 control-label">图片：</label>
                            <div class="col-sm-10">
                                <input type="file" name="img" id="img" accept="image/jpg,image/jpeg,image/png">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="editor" class="col-sm-2 control-label">内容：</label>
                            <div class="col-sm-10">
                                <div id="editor">
                                    ${news.content}
                                </div>
                                <input type="hidden" name="content" id="content">
<%--                                <textarea class="form-control" name="content" id="content" rows="10">${news.content}</textarea>--%>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="categoryId" class="col-sm-2 control-label">分类：</label>
                            <div class="col-sm-10">
                                <select name="categoryId" id="categoryId" class="form-control">
                                    <c:forEach items="${categories}" var="category">
                                        <option value="${category.id}" <c:if test="${news.category.id eq category.id}">selected</c:if>>${category.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="categoryId" class="col-sm-2 control-label">标签：</label>
                            <div class="col-sm-10">
                                <div class="checkbox">
                                    <c:forEach items="${tags}" var="tag">
                                        <label class="checkbox-inline">
                                            <input type="checkbox" name="tag" value="${tag.id}" <c:if test="${tagIdSet.contains(tag.id)}">checked</c:if>>${tag.name}
                                        </label>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="categoryId" class="col-sm-2 control-label">是否置顶：</label>
                            <div class="col-sm-10">
                                <label class="radio-inline">
                                    <input type="radio" name="stick" value="true" <c:if test="${news.stick}">checked</c:if>>是
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="stick" value="false" <c:if test="${empty news or !news.stick}">checked</c:if>>否
                                </label>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-primary">确定</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="./commons/footer.jsp" %>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/quill.min.js"></script>
<script>
    var quill = new Quill('#editor', {
        theme: 'snow',
        modules: {
            toolbar: [
                [{header: [1, 2, 3, false]}],
                ['bold', 'italic', 'underline'],
                [{'list': 'ordered'}, {'list': 'bullet'}],
                [{'align': []}],
                [{'font': []}],
                [{'color': []}, {'background': []}],
                ['image', 'video']
            ]
        }
    });

    function setContent(){
        // var delta = quill.getText();
        // console.log(delta);
        $("#content").val(quill.root.innerHTML);

        return false;
    }
</script>
</body>
</html>
