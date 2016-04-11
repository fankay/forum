<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>首页</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="include/nav.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="talk-item">
            <ul class="topic-type unstyled inline" style="margin-bottom:0px;">
                <li class="${empty param.node ? 'active' : ''}"><a href="/index.do">全部</a></li>
                <c:forEach items="${nodeList}" var="node">
                    <li class="${param.node == node.id ? 'active' : ''}"><a href="/index.do?node=${node.id}">${node.nodename}</a></li>
                </c:forEach>
            </ul>
        </div>
        <c:forEach items="${page.items}" var="topic">
            <div class="talk-item">
                <table class="talk-table">
                    <tr>
                        <td width="50">
                            <img class="avatar" src="http://7xs9b4.com1.z0.glb.clouddn.com/${topic.user.avatar}?imageView2/1/w/40/h/40" alt="">
                        </td>
                        <td width="80">
                            <a href="">${topic.user.username}</a>
                        </td>
                        <td width="auto">
                            <a href="/topic/view.do?id=${topic.id}">${topic.title}</a>
                        </td>
                        <td width="50" align="center">

                                <c:if test="${topic.replynum != 0}">
                                    <span class="badge">${topic.replynum}</span>
                                </c:if>


                        </td>
                    </tr>
                </table>
            </div>
        </c:forEach>

        <c:if test="${page.totalPages > 1}">
            <div class="pagination pagination-right" id="pages" style="margin:0px 20px 20px 0px"></div>
        </c:if>
    </div>
    <!--box end-->
</div>
<!--container end-->
<div class="footer">
    <div class="container">
        Copyright © 2015 kaishengit
    </div>
</div>
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function(){
        $("#pages").twbsPagination({
            totalPages:${page.totalPages},
            visiblePages:5,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href:"?node=${param.node}&page={{number}}"
        });
    });
</script>
</body>
</html>