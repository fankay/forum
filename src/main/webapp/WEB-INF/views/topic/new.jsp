<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>发布新主题</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
</head>
<body>
<%@ include file="../include/nav.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-plus"></i> 发布新主题</span>
        </div>

        <form method="post" style="padding: 20px" id="topicForm">
            <label class="control-label">主题标题</label>
            <input type="text" name="title" style="width: 100%;box-sizing: border-box;height: 30px" placeholder="请输入主题标题，如果标题能够表达完整内容，则正文可以为空">
            <label class="control-label">正文</label>
            <textarea name="text" id="editor"></textarea>

            <select name="nodeid" style="margin-top:15px;">
                <option value="">请选择节点</option>
                <c:forEach items="${nodeList}" var="node">
                <option value="${node.id}">${node.nodename}</option>
                </c:forEach>
            </select>

        </form>
        <div class="form-actions" style="text-align: right">
            <button id="sendBtn" class="btn btn-primary">发布主题</button>
        </div>


    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script>
    $(function(){
        var editor = new Simditor({
            textarea: $('#editor')
        });

        $("#sendBtn").click(function(){
            $("#topicForm").submit();
        });

        $("#topicForm").validate({
            errorClass:"text-error",
            errorElement:"span",
            rules:{
                title:{
                    required:true,
                    maxlength:40
                },
                nodeid:{
                    required:true
                }
            },
            messages:{
                title:{
                    required:"请输入标题",
                    maxlength:"标题内容最多40个字符"
                },
                nodeid:{
                    required:"请选择节点"
                }
            }
        });

    });
</script>

</body>
</html>