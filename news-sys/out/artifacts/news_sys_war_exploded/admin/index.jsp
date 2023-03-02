<%--
  Created by IntelliJ IDEA.
  User: zhao
  Date: 2021/12/15
  Time: 14:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>新闻系统</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
</head>
<body>
<%@include file="commons/navbar.jsp"%>

<div class="container-fluid">
    <div class="row">
        <%@include file="commons/menu.jsp"%>
        <div class="main">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">系统运行监控&nbsp;<i class="fa fa-book pull-right" aria-hidden="true"></i></h3>
                </div>
                <div class="panel-body">
                    <div class="col-md-6" id="stat-1" style="height: 400px;">

                    </div><!-- /.col-lg-6 -->
                    <div class="col-md-6" id="stat-2" style="height: 400px;">

                    </div>
                    <div class="col-md-6" id="stat-3" style="height: 400px;">

                    </div>
                    <div class="col-md-6" id="stat-4" style="height: 400px;">

                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="./commons/footer.jsp"%>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
<script type="text/javascript">
    $.get('${pageContext.request.contextPath}/admin/stat?method=newsCountByCategory', function (data) {
        // console.log("data:"+JSON.stringify(data));
        // 指定图表的配置项和数据
        var option1 = {
            title: {
                text: '新闻分类',
                left: 'center'
            },
            dataset: {
                source: data
            },
            tooltip: {},
            xAxis: {type: 'category'},
            yAxis: {type: 'value'},
            series: [
                {
                    type: 'bar',
                    encode: {
                        // 将 "name" 列映射到 Y 轴。
                        x: 'name',
                        // 将 "count" 列映射到 X 轴。
                        y: 'count'
                    }
                }
            ]
        };
        // 基于准备好的dom，初始化echarts实例
        var myChart1 = echarts.init(document.getElementById('stat-1'));
        myChart1.setOption(option1);
    });

    $.get('${pageContext.request.contextPath}/admin/stat?method=newsCommentCountByDate', function (data) {
        // console.log("data:"+JSON.stringify(data));
        // 指定图表的配置项和数据
        var option2 = {
            title: {
                text: '新闻评论',
                left: 'center'
            },
            dataset: {
                source: data
            },
            legend: {
                data: ['标题', "日期"]
            },
            tooltip: {},
            xAxis: {type: 'category'},
            yAxis: {type: 'value'},
            series: [
                {
                    type: 'line',
                    areaStyle: {},
                    encode: {
                        x: 'date',
                        y: 'count'
                    }
                }
            ]
        };
        // 基于准备好的dom，初始化echarts实例
        var myChart2 = echarts.init(document.getElementById('stat-2'));
        myChart2.setOption(option2);
    });

    $.get('${pageContext.request.contextPath}/admin/stat?method=newsCountByTag', function (data) {
        // 指定图表的配置项和数据
        var option3 = {
            title: {
                text: '新闻标签',
                left: 'center'
            },
            dataset: {
                source: data
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left'
            },
            series: [
                {
                    type: 'pie'
                }
            ]
        };
        // 基于准备好的dom，初始化echarts实例
        var myChart3 = echarts.init(document.getElementById('stat-3'));
        myChart3.setOption(option3);
    });

    $.get('${pageContext.request.contextPath}/admin/stat?method=newsCommentCountByUser', function (data) {
        // 指定图表的配置项和数据
        var option4 = {
            title: {
                text: '用户评论',
                left: 'center'
            },
            dataset: {
                source: data
            },
            tooltip: {
                trigger: 'item'
            },
            xAxis: {type: 'category'},
            yAxis: {type: 'value'},
            series: [
                {
                    type: 'bar',
                    showBackground: true,
                    encode: {
                        x: 'nickname',
                        y: 'count'
                    }
                }
            ]
        };
        // 基于准备好的dom，初始化echarts实例
        var myChart4 = echarts.init(document.getElementById('stat-4'));
        myChart4.setOption(option4);
    });
</script>
</body>
</html>

