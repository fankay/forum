<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户设置</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/webuploader/webuploader.css">
</head>
<body>
<%@ include file="../include/nav.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-cog"></i> 基本设置</span>
        </div>

        <form action="" class="form-horizontal" id="emailForm">
            <div class="control-group">
                <label class="control-label">账号</label>
                <div class="controls">
                    <input type="text" value="${sessionScope.curr_user.username}" readonly>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">电子邮件</label>
                <div class="controls">
                    <input type="text" name="email" value="${sessionScope.curr_user.email}">
                </div>
            </div>
            <div class="form-actions">
                <button class="btn btn-primary" type="button" id="btnSaveEmail">保存</button>
                <span id="emailHelp" class="text-success hide">电子邮件修改成功</span>
            </div>

        </form>

    </div>
    <!--box end-->
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-key"></i> 密码设置</span>
            <span class="pull-right muted" style="font-size: 12px">如果你不打算更改密码，请留空以下区域</span>
        </div>

        <form action="" class="form-horizontal" id="passwordForm">
            <div class="control-group">
                <label class="control-label">密码</label>
                <div class="controls">
                    <input type="password" name="password" id="password">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">重复密码</label>
                <div class="controls">
                    <input type="password" name="repassword">

                </div>
            </div>
            <div class="form-actions">
                <button class="btn btn-primary" id="btnSavePassword" type="button">保存</button>
                <span id="passwordHelp" class="text-success hide">密码修改成功,请重新登录</span>
            </div>

        </form>

    </div>
    <!--box end-->

    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-user"></i> 头像设置</span>
        </div>

        <form action="" class="form-horizontal">
            <div class="control-group">
                <label class="control-label">当前头像</label>
                <div class="controls">
                    <img src="http://7xs9b4.com1.z0.glb.clouddn.com/${sessionScope.curr_user.avatar}?imageView2/1/w/40/h/40" class="img-circle avatar2" alt="">
                </div>
            </div>
            <hr>
            <p style="padding-left: 20px">关于头像的规则</p>
            <ul>
                <li>禁止使用任何低俗或者敏感图片作为头像</li>
                <li>如果你是男的，请不要用女人的照片作为头像，这样可能会对其他会员产生误导</li>
            </ul>
            <div class="form-actions">
                <div id="picker">上传新头像</div>
            </div>


        </form>

    </div>
    <!--box end-->

</div>
<!--container end-->
<script src="/static/js/jquery-1.11.3.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/webuploader/webuploader.min.js"></script>
<script>
    $(function(){

        var uploader = WebUploader.create({
            swf:'/static/js/webuploder/Uploader.swf',
            server:"http://upload.qiniu.com",
            pick:"#picker",
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            },
            auto:true,
            fileVal:"file",
            formData:{"token":"${token}"}
        });

        uploader.on("uploadProgress",function(file){
          $(".webuploader-pick").text("头像上传中...").attr("disabled","disabled");
        })

        //文件上传失败时调用
        uploader.on("uploadError",function(file){
            alert("上传服务器错误");
        });

        //无论上传成功还是失败都调用
        uploader.on("uploadComplete",function(){
           $(".webuploader-pick").text("上传新头像").removeAttr("disabled");
        });


        uploader.on("uploadSuccess",function(file,result){
            var key = result.key;
            $.post("/user/changeavatar.do",{"key":key}).done(function(json){
                if(json.state == "success") {
                    $(".avatar2").attr("src","http://7xs9b4.com1.z0.glb.clouddn.com/"+key+"?imageView2/1/w/40/h/40");
                    $(".avatar1").attr("src","http://7xs9b4.com1.z0.glb.clouddn.com/"+key+"?imageView2/1/w/20/h/20");
                    uploader.removeFile(file,true);
                }
            }).fail(function(){
                alert("服务器忙，请稍后再试");
            });

        });




        $("#btnSaveEmail").click(function(){
            $("#emailForm").submit();
        });

        $("#emailForm").validate({
            errorClass:"text-error",
            errorElement:"span",
            rules:{
                email:{
                    required:true,
                    email:true,
                    remote:"/validate/email.do?action=self"
                }
            },
            messages:{
                email:{
                    required:"请输入电子邮件",
                    email:"电子邮件格式错误",
                    remote:"邮件地址已注册"
                }
            },
            submitHandler:function(form){
                var $btn = $("#btnSaveEmail");
                $.ajax({
                    url:"/user/changeemail.do",
                    type:"post",
                    data:$(form).serialize(),
                    beforeSend:function(){
                        $btn.text("保存中...").attr("disabled","disabled");
                    },
                    success:function(data){
                        if(data.state == "success") {
                            $("#emailHelp").show().fadeOut(2000);
                        } else {
                            alert("修改失败");
                        }
                    },
                    error:function(){
                        alert("服务器异常，请稍后再试");
                    },
                    complete:function(){
                        $btn.text("保存").removeAttr("disabled");
                    }
                });
            }
        });

        $("#btnSavePassword").click(function(){
            $("#passwordForm").submit();
        });

        $("#passwordForm").validate({
            errorClass:"text-error",
            errorElement:"span",
            rules:{
                password:{
                    required:true,
                    rangelength:[6,18]
                },
                repassword:{
                    required:true,
                    rangelength:[6,18],
                    equalTo:"#password"
                }
            },
            messages:{
                password:{
                    required:"请输入密码",
                    rangelength:"密码长度6~18位"
                },
                repassword:{
                    required:"请输入确认密码",
                    rangelength:"密码长度6~18位",
                    equalTo:"两次密码不一致"
                }
            },
            submitHandler:function(form){
                var $btn = $("#btnSavePassword");
                $.ajax({
                    url:"/user/changepassword.do",
                    type:"post",
                    data:$(form).serialize(),
                    beforeSend:function(){
                        $btn.text("保存中...").attr("disabled","disabled");
                    },
                    success:function(data){
                        if(data.state == "success") {
                            $("#passwordHelp").show().fadeOut(1000,function(){
                                window.location.href = "/logout.do";
                            });
                        } else {
                            alert("保存失败");
                        }
                    },
                    error:function(){
                        alert("服务器忙，请稍后再试");
                    },
                    complete:function(){
                        $btn.text("保存").removeAttr("disabled");
                    }
                });
            }
        });

    });
</script>



</body>
</html>