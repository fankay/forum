<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>找回密码</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
</head>
<body>
<%@ include file="../include/nav.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title">找回密码</span>
        </div>

        <c:choose>
            <c:when test="${param.state == '1001'}">
                <div class="alert alert-success">邮件发送成功，请查收</div>
            </c:when>
        </c:choose>

        <form method="post" class="form-horizontal" id="forgetForm">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" name="username">
                </div>
            </div>
            <div class="form-actions">
                <button class="btn btn-primary" type="button" id="btn">提交</button>
            </div>

        </form>



    </div>
    <!--box end-->
</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script>
    $(function(){


        $("#btn").click(function(){
            $("#forgetForm").submit();
        });

        $("#forgetForm").validate({
            errorClass:"text-error",
            errorElement:"span",
            rules:{
                username:{
                    required:true,
                    remote:"/validate/username.do?action=forget"
                }
            },
            messages:{
                username:{
                    required:"请输入账号",
                    remote:"账号输入错误"
                }
            }

        });



    });
</script>
</body>
</html>