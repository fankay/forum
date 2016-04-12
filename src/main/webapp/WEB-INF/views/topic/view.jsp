<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${topic.title}</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <link rel="stylesheet" href="/static/js/code/hemisu-light.css">
    <style>
        body{
            background-image: url(/static/img/bg.jpg);
        }
        .simditor .simditor-body {
            min-height: 100px;
        }
    </style>
</head>
<body>
<%@ include file="../include/nav.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <ul class="breadcrumb" style="background-color: #fff;margin-bottom: 0px;">
            <li><a href="/index.do">首页</a> <span class="divider">/</span></li>
            <li class="active">${topic.node.nodename}</li>
        </ul>
        <div class="topic-head">
            <img class="img-rounded avatar" src="http://7xs9b4.com1.z0.glb.clouddn.com/${topic.user.avatar}?imageView2/1/w/60/h/60" alt="">
            <h3 class="title">${topic.title}</h3>
            <p class="topic-msg muted"><a href="">${topic.user.username}</a> · <span class="timeago" title="${topic.createtime}"></span> </p>
        </div>
        <div class="topic-body">
            ${topic.text}
        </div>
        <div class="topic-toolbar">
            <c:if test="${not empty sessionScope.curr_user}">
                <ul class="unstyled inline pull-left">
                    <c:choose>
                        <c:when test="${action == 'fav'}">
                            <li><a href="javascript:;" class="fav">取消收藏</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="javascript:;" class="fav">加入收藏</a></li>
                        </c:otherwise>
                    </c:choose>

                    <li><a href="">感谢</a></li>
                </ul>
            </c:if>
            <ul class="unstyled inline pull-right muted">
                <li>${topic.viewnum}次点击</li>
                <li>${topic.favnum}人收藏</li>
                <li>${topic.likenum}人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end-->

    <div class="box" style="margin-top:20px;">
        <div class="talk-item muted" style="font-size: 12px">
            <span id="replyNum"></span>个回复 | 直到 <span id="replyTime"></span>
        </div>
        <div id="comment-list"></div>
    </div>

    <c:choose>
        <c:when test="${not empty sessionScope.curr_user}">
            <div class="box" style="margin:20px 0px;">
                <a name="new"></a>
                <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
                <form id="commentForm" style="padding: 15px;margin-bottom:0px;">
                    <textarea name="" id="editor"></textarea>
                </form>
                <div class="talk-item muted" style="text-align: right;font-size: 12px">
                    <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                    <button class="btn btn-primary" id="sendComment">发布</button>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="box" style="margin:20px 0px;">
                <div style="padding: 20px">
                    请 <a href="/login.do?redirecturl=/topic/view.do?id=${topic.id}">登录</a> 后在发表回复
                </div>
            </div>
        </c:otherwise>
    </c:choose>


</div>
<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/timeago.js"></script>
<script src="/static/js/code/prettify.js"></script>
<script src="/static/js/handlebars-v4.0.5.js"></script>
<script src="/static/js/moment.min.js"></script>
<script type="text/mytemplate" id="commentListTemplate">
    {{#each data}}
    <div class="talk-item">

        <table class="talk-table">
            <tr>
                <td width="50">
                    <img class="avatar" src="http://7xs9b4.com1.z0.glb.clouddn.com/{{user.avatar}}?imageView2/1/w/40/h/40" alt="">
                </td>
                <td width="auto">
                    <a href="" style="font-size: 12px">{{user.username}}</a> <span style="font-size: 12px" class="reply timeago" title="{{createtime}}"></span>
                    <br>
                    {{{comment}}}
                    <a name="reply{{counter @index}}"></a>
                </td>
                <td width="70" align="right" style="font-size: 12px">
                    <a href="javascript:;" class="replyLink" data-count="{{counter @index}}" title="回复"><i class="fa fa-reply"></i></a>&nbsp;
                    <span class="badge">{{counter @index}}</span>
                </td>
            </tr>
        </table>

    </div>
    {{/each}}
</script>
<script>
    $(function(){
        <c:if test="${not empty sessionScope.curr_user}">
        var editor = new Simditor({
            textarea: $('#editor'),
            toolbar:false
        });
        $("#sendComment").click(function(){
            var value = editor.getValue();
            if(value) {
                sendComment();
            } else {
                editor.focus();
            }
        });

        function sendComment() {
            var $btn = $("#sendComment");
            $.ajax({
                url:"/topic/comment/new.do",
                type:"post",
                data:{"comment":editor.getValue(),"topicId":"${topic.id}"},
                beforeSend:function(){
                    $btn.text("发布中...").attr("disabled","disabled");
                },
                success:function(json){
                    if(json.state == "error") {
                        alert(json.message);
                    } else {
                        initComment();
                        editor.setValue("");
                    }
                },
                error:function(){
                    alert("服务器忙，请稍后再试");
                },
                complete:function(){
                    $btn.text("发布").removeAttr("disabled");
                }
            });
        }
        </c:if>

        Handlebars.registerHelper("counter", function (index){
            return index + 1;
        });


        //异步查询当前主题的所有评论
        function initComment() {
            $.ajax({
                url:"/topic/comment/load.do",
                type:"post",
                data:{"topicId":"${topic.id}"},
                beforeSend:function(){
                    
                },
                success:function(json){
                    if(json.state == "error") {
                        alert(json.message);
                    } else {
                        $("#comment-list").html("");
                        var source = $("#commentListTemplate").html();
                        var template = Handlebars.compile(source);
                        var html = template(json);
                        $("#comment-list").append(html);

                        //统计回复的数量
                        $("#replyNum").text(json.data.length);
                        //最后回复的时间
                        if(json.data.length != 0) {
                            $("#replyTime").text(json.data[json.data.length - 1].createtime);
                        } else {
                            $("#replyTime").text(moment().format("YYYY-MM-DD HH:mm:ss"));
                        }

                        $(".timeago").timeago();
                    }
                },
                error:function(){
                    alert("服务器错误，请稍后再试");
                },
                complete:function(){
                    
                }
            });
        }




        $(".timeago").timeago();

        $("pre").addClass("prettyprint");
        prettyPrint();

        initComment();

        //点击评论内容的回复超链接
       /* $(document).delegate(".replyLink","click",function(){
            var counter = $(this).attr("data-count");
            alert("counter：" + counter );
        });*/
        $(document).on("click",".replyLink",function(){
            var counter = $(this).attr("data-count");
            var msg = "<a href='#reply"+counter+"'>#" + counter + "楼</a>&nbsp;&nbsp;";
            editor.setValue(msg);
            editor.focus();
            window.location.href="#new";
        });

        //加入和取消收藏
        $(".fav").click(function(){
            var $this = $(this);
            var action = $this.text() == "加入收藏" ? 'fav' : 'unfav';
            $.post("/topic/fav.do",{"topicId":"${topic.id}","action":action}).done(function(result){
                if(result.state == "error") {
                    alert(result.message);
                } else {
                    if(action == "fav") {
                        $this.text("取消收藏");
                    } else {
                        $this.text("加入收藏");
                    }
                }
            }).fail(function(){
                alert("服务器忙，请稍后再试");
            });


        });


    });
</script>

</body>
</html>