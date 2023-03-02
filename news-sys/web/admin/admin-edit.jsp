<%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2023/1/9
  Time: 15:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>管理员编辑</title>
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
                        <a href="${pageContext.request.contextPath}/admin/admin?method=list">管理员列表</a> <span class="divider"></span>
                    </li>
                    <li class="active">管理员编辑</li>
                </ul>
            </div>
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">${param.method eq 'add'? '新增' : '编辑'}管理员<i class="fa fa-book pull-right" aria-hidden="true"></i></h3>
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" id="adminForm" action="${pageContext.request.contextPath}/admin/admin?method=save" method="post">
                        <c:if test="${not empty requestScope.admin}">
                            <input type="hidden" name="id" value="${requestScope.admin.id}">
                        </c:if>
                        <div class="form-group">
                            <label for="account" class="col-sm-2 control-label">账号：</label>
                            <div class="col-sm-10">
                                <input type="text" name="account" class="form-control" id="account" placeholder="请输入账号" value="${requestScope.admin.account}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">姓名：</label>
                            <div class="col-sm-10">
                                <input type="text" name="name" class="form-control" id="name" placeholder="请输入姓名" value="${requestScope.admin.name}">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="password" class="col-sm-2 control-label">密码：</label>
                            <div class="col-sm-10">
                                <input type="password" name="password" class="form-control" id="password" placeholder="请输入密码" value="${requestScope.admin.password}">
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
<script src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/js/messages_zh.js"></script>
<script>


    $().ready(function (){
        $('#adminForm').validate({
            rules: {
                account: 'required',
                name: 'required',
                password: {
                    required: true,
                    minlength: 6
                }
            },
            errorElement: "em",
            errorPlacement: function ( error, element ) {
                // 给错误元素增加`text-danger` class 样式
                error.addClass( "text-danger" );

                // Add `has-feedback` class to the parent div.form-group
                // in order to add icons to inputs
                element.parents( ".col-sm-5" ).addClass( "has-feedback" );

                if ( element.prop( "type" ) === "checkbox" ) {
                    error.insertAfter( element.parent( "label" ) );
                } else {
                    error.insertAfter( element );
                }

                // Add the span element, if doesn't exists, and apply the icon classes to it.
                if ( !element.next( "span" )[ 0 ] ) {
                    $( "<span class='glyphicon glyphicon-remove form-control-feedback'></span>" ).insertAfter( element );
                }
            }
        });
    });
</script>
</body>
</html>