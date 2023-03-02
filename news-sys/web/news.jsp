<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.cdvtc.news.dao.*" %>
<%@ page import="com.cdvtc.news.dao.impl.*" %>
<%@ page import="com.cdvtc.news.model.*" %>
<%@ page import="java.util.*" %>
<%@ page import="com.cdvtc.news.util.CommentUtil" %>
<%@ page import="com.cdvtc.news.service.NewsService" %>
<%@ page import="com.cdvtc.news.service.impl.NewsServiceImpl" %>
<%@ page import="com.cdvtc.news.service.CommentService" %>
<%@ page import="com.cdvtc.news.service.impl.CommentServiceImpl" %><%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2021/12/15
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新闻详情</title>
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="css/quill.snow.css">
    <link rel="stylesheet" href="css/quill-emoji.css">
    <link rel="stylesheet" href="css/main.css">
</head>
<body>
<jsp:include page="commons/header.jsp"></jsp:include>
<%
    int id = Integer.parseInt(request.getParameter("id"));
    NewsService newsService = new NewsServiceImpl();
    News news = newsService.getNewsById(id);

    CommentService commentService = new CommentServiceImpl();
    List<Comment> comments = commentService.getCommentsByNewsId(id);
    pageContext.setAttribute("comments", comments); //存入页面作用域，EL表达式才能获取值
    request.setAttribute("commentMap", CommentUtil.toMap(comments)); //保存评论Map，解决jsp:param不能传对象的问题（改为只传Id）

    TagDao tagDao = new TagDaoImpl();
    Set<Tag> tagSet = tagDao.getTagsByNewsId(id);

    List<News> recommendedNews = newsService.getRecommendedNews(id);

    // 更新点击计数
    newsService.updateClickCount(id);
%>
<div class="container">
    <div class="col-xs-8">

        <h2 class="news-title"><%=news.getTitle()%>
        </h2>
        <div class="news-status">
            <%=news.getClickCount()%>阅读 • <%=news.getPubDateInterval()%>
            <%for (Tag tag : tagSet) {%>
            <div class="label label-default"><%=tag.getName()%>
            </div>
            <%}%>
        </div>
        <div class="news-content">
            <%=news.getContent()%>
        </div>
        <div>
            <h3>我要评论</h3>
            <form class="form-horizontal" action="${pageContext.request.contextPath}/CommentServlet?newsId=${param.id}" method="post"
                  onsubmit="return setContent()">
                <div id="editor">
                    ${news.content}
                </div>
                <input type="hidden" name="content" id="content">
                <%--                    <input type="hidden" name="content">--%>
                <button type="submit" class="btn btn-default" <c:if test="${empty sessionScope.user}">disabled</c:if>>
                    发布
                </button><c:if test="${empty sessionScope.user}">您还未登陆，请先<a href="login.jsp">登陆</a></c:if>
            </form>
        </div>
        <h3>最新评论</h3>
        <c:forEach items="${comments}" var="comment">
            <jsp:include page="commons/comment.jsp">
                <jsp:param name="commentId" value="${comment.id}"/>
            </jsp:include>
        </c:forEach>
    </div>
    <div class="col-xs-4">
        <div class="side-bar-card">
            <div class="card-title">相关推荐</div>
            <div class="card-body">
                <div class="list">
                    <% for (News n : recommendedNews) {%>
                    <div class="item clearfix">
                        <div class="col-xs-5 no-padding-h">
                            <img src="img/<%=n.getImg()%>" alt="#">
                        </div>
                        <div class="col-xs-7">
                            <div class="title"><a href="news.jsp?id=<%=n.getId()%>"><%=n.getTitle()%>
                            </div>
                            <div class="desc"><%=n.getClickCount()%>阅读 <%=n.getCommentNum()%>评论</div>
                        </div>
                    </div>
                    <%}%>
                </div>
            </div>
        </div>
        <jsp:include page="commons/hot-news.jsp"></jsp:include>
    </div>
</div>
<%@include file="commons/footer.jsp" %>
<script src="js/jquery.min.js"></script>
<script src="js/jquery.cookie.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/quill.min.js"></script>
<script src="js/quill-emoji.js"></script>
<script>
    var toolbarOptions = {
        container: [
            ['bold', 'italic', 'underline', 'strike'],
            ['blockquote', 'code-block'],
            ['emoji']
        ],
        handlers: {
            'emoji': function () {}
        }
    };

    var quill = new Quill('#editor', {
        modules: {
            "toolbar": toolbarOptions,
            "emoji-toolbar": true,
            "emoji-shortname": true,
            "emoji-textarea": true
        },
        theme: 'snow'
    });
    var replyQuill; //定义全局变量

    function setContent() {
        if(quill && quill.getLength() > 1){
            $("#content").val(quill.root.innerHTML);
            return true;
        } else{
            alert("内容不能为空！")
            return false;
        }
    }

    function setReplyContent() {
        if(replyQuill && replyQuill.getLength() > 1){
            $("#reply-content").val(replyQuill.root.innerHTML);
            return true;
        } else {
            alert("内容不能为空！")
            return false;
        }
    }

    function reply(commentId) {
        $(".editor-form").remove(); //清除编辑表单
        $(".btn-reply").removeAttr("disabled"); // 恢复按钮功能

        $("#btn-"+commentId).attr("disabled", "disabled");  //停止重复点击
        $("#comment-"+commentId).append("<form action='${pageContext.request.contextPath}/CommentServlet?newsId=${param.id}&replyForId="+commentId
            +"' class='editor-form' method='post' onsubmit='return setReplyContent()'><div id='ql-editor'></div><input type='hidden' name='content' id='reply-content'><button class='btn btn-default'"
             <c:if test="${empty sessionScope.user}">+ ' disabled'</c:if> + ">回复</button>"
             <c:if test="${empty sessionScope.user}">+ '您还未登陆，请先<a href="login.jsp">登陆</a>'</c:if> + "</form>");
        replyQuill = new Quill('#ql-editor', {
            modules: {
                "toolbar": toolbarOptions,
                "emoji-toolbar": true,
                "emoji-shortname": true,
                "emoji-textarea": true
            },
            theme: 'snow'
        });
    }

    function like(commentId) {
        var like = $.cookie('comment-like-'+commentId);  // 从Cookie中获取点赞记录
        var operate = like? 'unlike': 'like';

        $.post('${pageContext.request.contextPath}/CommentLikeServlet?id='+commentId + '&operate=' + operate, function (num) {
            // 更新点赞数和图标状态
            $('#like-num-'+commentId).text(num > 0 ? num : '赞');
            if(like){
                $('#like-icon-'+commentId).removeClass('alert-danger');
                $.removeCookie('comment-like-'+commentId, { path: "/"});  // 删除Cookie
            } else {
                $('#like-icon-'+commentId).addClass('alert-danger');
                $.cookie('comment-like-'+commentId, true, { path: "/"}); // 写入Cookie
            }
        });
    }

    function dislike(commentId) {
        var dislike = $.cookie('comment-dislike-'+commentId);  // 从Cookie中获取点踩记录
        var operate = dislike? 'undislike': 'dislike';

        $.post('${pageContext.request.contextPath}/CommentLikeServlet?id='+commentId + '&operate=' + operate, function (num) {
            // 更新点赞数和图标状态
            $('#dislike-num-'+commentId).text(num > 0 ? num : '踩');
            if(dislike){
                $('#dislike-icon-'+commentId).removeClass('alert-danger');
                $.removeCookie('comment-dislike-'+commentId, { path: "/"});  // 删除Cookie
            } else {
                $('#dislike-icon-'+commentId).addClass('alert-danger');
                $.cookie('comment-dislike-'+commentId, true, { path: "/"}); // 写入Cookie
            }
        });
    }

    function deleteComment(commentId) {
        if(confirm("您确定要删除该评论？")){
            $.post('${pageContext.request.contextPath}/deleteComment', {id: commentId},
            function (result){
                if(result) {
                    alert("删除评论成功！")
                    location.reload();  // 刷新当前页面
                } else {
                    alert("删除评论失败！")
                }
            })
        }
    }
</script>
</body>
</html>
