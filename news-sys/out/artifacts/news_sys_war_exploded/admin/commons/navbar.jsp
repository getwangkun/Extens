<%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2021/12/15
  Time: 17:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/admin/">新闻管理系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse">
            <form class="navbar-form navbar-left" action="${pageContext.request.contextPath}/admin/news">
                <input type="hidden" name="method" value="list">
                <div class="form-group">
                    <input type="text" name="key" class="form-control" placeholder="新闻关键词">
                </div>
                <button type="submit" class="btn btn-default">搜索</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">${admin.name}</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">更多操作 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="javascript:logout()">退出</a></li>
                        <li><a href="#">修改密码</a></li>
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<script>
    function logout(){
        if(confirm("您确定要退出新闻管理端？")){
            location.href = '${pageContext.request.contextPath}/admin/logout';
        }
    }
</script>
