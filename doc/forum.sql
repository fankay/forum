/*
SQLyog Ultimate v11.24 (64 bit)
MySQL - 5.5.37 : Database - forum
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`forum` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `forum`;

/*Table structure for table `t_comment` */

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment` longtext COMMENT '评论内容',
  `createtime` varchar(45) DEFAULT NULL COMMENT '发表时间',
  `userid` int(11) DEFAULT NULL COMMENT '发表人ID',
  `topicid` int(11) DEFAULT NULL COMMENT '对应的主题ID',
  PRIMARY KEY (`id`),
  KEY `fk_t_comment_t_user1_idx` (`userid`),
  KEY `fk_t_comment_t_topic1_idx` (`topicid`),
  CONSTRAINT `fk_t_comment_t_user1` FOREIGN KEY (`userid`) REFERENCES `t_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_comment_t_topic1` FOREIGN KEY (`topicid`) REFERENCES `t_topic` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

/*Data for the table `t_comment` */

insert  into `t_comment`(`id`,`comment`,`createtime`,`userid`,`topicid`) values (1,'<p>第一个回复</p>','2016-04-12 09:46:40',11,3),(2,'<p>第二个回复</p>','2016-04-12 10:11:59',11,3),(3,'<p>第三个回复</p>','2016-04-12 10:13:52',11,3),(4,'<p>第四个回复</p>','2016-04-12 10:14:42',11,3),(5,'<p>颜色不错哦</p>','2016-04-12 10:24:56',11,5),(6,'<p>zzz</p>','2016-04-12 13:55:39',11,3),(7,'<p>234234234</p>','2016-04-12 14:14:48',11,3),(8,'<p>zzz</p>','2016-04-12 14:21:07',11,3),(9,'<p>123123123</p>','2016-04-12 14:24:45',11,3),(10,'<p>ssss</p>','2016-04-12 14:28:53',11,3),(11,'<p>#9楼234234234</p>','2016-04-12 14:30:46',11,3),(12,'<p><a href=\"#reply2\">#2楼</a>&nbsp; 你说的对</p>','2016-04-12 14:34:05',11,3),(13,'<p><a href=\"#reply1\">#1楼</a>&nbsp; 你也说的对</p>','2016-04-12 14:36:31',11,3),(14,'<p><span style=\"color: rgb(51, 51, 51); font-size: 15px;\">委托事件有一个优势，他们能在</span>后代元素<span style=\"color: rgb(51, 51, 51); font-size: 15px;\">添加到文档后，可以处理这些事件。 在确保所选择的元素已经存在的情况下，进行事件绑定时，您可以使用委派的事件，以避免频繁的绑定事件及解除绑定事件。 例如，这个已经存在的元素可以是 Model-View-Controller（模型 - 视图 - 控制器）模式中 View（视图） 的一个容器元素，或</span><code>document</code><span style=\"color: rgb(51, 51, 51); font-size: 15px;\">。如果想监视所有文档中的冒泡事件的话。在加载任何其它 HTML 之前，</span><code>document</code><span style=\"color: rgb(51, 51, 51); font-size: 15px;\">&nbsp;元素在&nbsp;</span><code>head</code><span style=\"color: rgb(51, 51, 51); font-size: 15px;\">&nbsp;中就是有效的，所以您可以安全的在&nbsp;</span><code>head</code><span style=\"color: rgb(51, 51, 51); font-size: 15px;\">&nbsp;中进行事件绑定，而不需要等待文档加载完。</span><br></p>','2016-04-12 14:38:41',11,3),(15,'<p><a href=\"#reply2\">#2楼</a>&nbsp; 好的</p>','2016-04-12 14:39:15',11,3),(16,'<p>非常好用</p>','2016-04-12 14:44:15',11,1),(17,'<p>解决了我的问题</p>','2016-04-12 14:45:53',11,6),(18,'<p>123123</p>','2016-04-12 14:46:05',11,6),(19,'<p>123123</p>','2016-04-12 15:46:03',11,5);

/*Table structure for table `t_fav` */

DROP TABLE IF EXISTS `t_fav`;

CREATE TABLE `t_fav` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topicid` int(11) DEFAULT NULL COMMENT '主题ID',
  `userid` int(11) DEFAULT NULL COMMENT '用户ID',
  `createtime` varchar(45) DEFAULT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`),
  KEY `fk_t_topic_has_t_user_t_user1_idx` (`userid`),
  KEY `fk_t_topic_has_t_user_t_topic1_idx` (`topicid`),
  CONSTRAINT `fk_t_topic_has_t_user_t_topic1` FOREIGN KEY (`topicid`) REFERENCES `t_topic` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_t_topic_has_t_user_t_user1` FOREIGN KEY (`userid`) REFERENCES `t_user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `t_fav` */

insert  into `t_fav`(`id`,`topicid`,`userid`,`createtime`) values (3,6,11,'2016-04-12 15:41:17'),(5,1,11,'2016-04-12 15:42:51'),(7,5,11,'2016-04-12 15:49:58');

/*Table structure for table `t_forgetpassword` */

DROP TABLE IF EXISTS `t_forgetpassword`;

CREATE TABLE `t_forgetpassword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `token` varchar(100) DEFAULT NULL,
  `createtime` varchar(45) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `t_forgetpassword` */

/*Table structure for table `t_node` */

DROP TABLE IF EXISTS `t_node`;

CREATE TABLE `t_node` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nodename` varchar(45) DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `t_node` */

insert  into `t_node`(`id`,`nodename`) values (1,'问与答'),(2,'内容分享'),(3,'Java'),(4,'前端开发'),(5,'其他');

/*Table structure for table `t_topic` */

DROP TABLE IF EXISTS `t_topic`;

CREATE TABLE `t_topic` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL COMMENT '主题的标题',
  `text` longtext COMMENT '正文',
  `createtime` varchar(45) DEFAULT NULL COMMENT '发表时间',
  `userid` int(11) DEFAULT NULL COMMENT '发表人ID',
  `nodeid` int(11) DEFAULT NULL COMMENT '所属节点的ID',
  `viewnum` int(11) DEFAULT NULL COMMENT '点击(浏览)次数',
  `favnum` int(11) DEFAULT NULL COMMENT '收藏次数',
  `likenum` int(11) DEFAULT NULL COMMENT '感谢次数',
  `replynum` int(11) DEFAULT NULL COMMENT '回复次数',
  `replytime` varchar(45) DEFAULT NULL COMMENT '最后回复的时间',
  PRIMARY KEY (`id`),
  KEY `fk_t_topic_t_user_idx` (`userid`),
  KEY `fk_t_topic_t_node1_idx` (`nodeid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `t_topic` */

insert  into `t_topic`(`id`,`title`,`text`,`createtime`,`userid`,`nodeid`,`viewnum`,`favnum`,`likenum`,`replynum`,`replytime`) values (1,'github的使用','<p>你好，github</p>','2016-04-11 09:38:01',11,2,10,0,0,1,'2016-04-12 14:44:15'),(2,'第二个帖子','<p>第二个帖子<br></p>','2016-04-11 09:59:34',11,1,1,0,0,0,'2016-04-11 09:59:34'),(3,'第三个帖子','<p>第三个帖子<br></p>','2016-04-11 10:00:36',11,1,52,0,0,0,'2016-04-11 10:00:36'),(4,'timeago.js的使用','<p>下面的代码就可以显示中文了</p><pre><code class=\"lang-js\">$.extend($.timeago, {\r\n    settings: {\r\n        refreshMillis: 60000,\r\n        allowPast: true,\r\n        allowFuture: false,\r\n        localeTitle: false,\r\n        cutoff: 0,\r\n        autoDispose: true,\r\n        strings: {\r\n            prefixAgo: null,\r\n            prefixFromNow: \"从现在开始\",\r\n            suffixAgo: \"之前\",\r\n            suffixFromNow: null,\r\n            seconds: \"不到1分钟\",\r\n            minute: \"大约1分钟\",\r\n            minutes: \"%d分钟\",\r\n            hour: \"大约1小时\",\r\n            hours: \"大约%d小时\",\r\n            day: \"1天\",\r\n            days: \"%d天\",\r\n            month: \"大约1个月\",\r\n            months: \"%d月\",\r\n            year: \"大约1年\",\r\n            years: \"%d年\",\r\n            numbers: [],\r\n            wordSeparator: \"\"        }\r\n    }<br></code></pre>','2016-04-11 14:43:26',11,4,16,0,0,0,'2016-04-11 14:43:26'),(5,'各种代码高亮演示','<pre><code class=\"lang-java\">@Overrideprotected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {\r\n    String id = req.getParameter(\"id\");\r\n\r\n    if(StringUtils.isNumeric(id)) {\r\n        TopicService topicService = new TopicService();\r\n        Topic topic = topicService.viewTopic(Integer.valueOf(id));\r\n        req.setAttribute(\"topic\",topic);\r\n        forward(req,resp,\"topic/view\");\r\n    } else {\r\n        resp.sendError(404,\"查看的内容不存在或已被删除\");\r\n    }\r\n\r\n}<br></code></pre><pre><code class=\"lang-html\">&lt;div class=\"header-bar\"&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &lt;div class=\"container\"&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;a href=\"#\" class=\"brand\"&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;i class=\"fa fa-reddit-alien\"&gt;&lt;/i&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;/a&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;ul class=\"unstyled inline pull-right\"&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;li&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;a href=\"#\"&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;img src=\"http://7xp5t4.com1.z0.glb.clouddn.com/Fqb8f9uDknAt2ilBoY-ipSZRMes-?imageView2/1/w/20/h/20\" class=\"img-circle\" alt=\"\"&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;/a&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;/li&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;li&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;a href=\"\"&gt;&lt;i class=\"fa fa-plus\"&gt;&lt;/i&gt;&lt;/a&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;/li&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;li&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;a href=\"#\"&gt;&lt;i class=\"fa fa-bell\"&gt;&lt;/i&gt;&lt;/a&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;/li&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;li&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;a href=\"#\"&gt;&lt;i class=\"fa fa-cog\"&gt;&lt;/i&gt;&lt;/a&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;/li&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;li&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;a href=\"#\"&gt;&lt;i class=\"fa fa-sign-out\"&gt;&lt;/i&gt;&lt;/a&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;/li&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &lt;/ul&gt;\r\n&nbsp; &nbsp; &nbsp; &nbsp; &lt;/div&gt;\r\n&nbsp; &nbsp; &lt;/div&gt;<br></code></pre><pre><code class=\"lang-js\">$(\"#regBtn\").click(function(){\r\n\r\n    $(\"#regForm\").submit();\r\n});\r\n\r\n$(\"#changePic\").click(function(){\r\n    $(\"#img\").attr(\"src\",\"/patchca.png?_=\"+new Date().getTime());\r\n});<br></code></pre>','2016-04-11 14:59:26',11,2,20,1,0,1,'2016-04-12 15:46:03'),(6,'jQuery中的代理事件','<p>第一种方法：</p><pre><code class=\"lang-js\">$(document).delegate(\".replyLink\",\"click\",function(){\r\n    var counter = $(this).attr(\"data-count\");\r\n    alert(\"counter：\" + counter );\r\n});<br></code></pre><p>第二种方法：</p><pre><code class=\"lang-js\">$(document).on(\"click\",\".replyLink\",function(){\r\n    var counter = $(this).attr(\"data-count\");\r\n    var msg = \"&lt;a href=\'#reply\"+counter+\"\'&gt;#\" + counter + \"楼&lt;/a&gt;&amp;nbsp;&amp;nbsp;\";\r\n    editor.setValue(msg);\r\n    editor.focus();\r\n    window.location.href=\"#new\";\r\n});<br></code></pre>','2016-04-12 14:45:34',11,4,15,0,0,2,'2016-04-12 14:46:05');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) DEFAULT NULL COMMENT '账号',
  `password` varchar(100) DEFAULT NULL COMMENT '密码（MD5加密）',
  `email` varchar(100) DEFAULT NULL COMMENT '电子邮件',
  `avatar` varchar(200) DEFAULT NULL COMMENT '用户头像地址',
  `createtime` varchar(45) DEFAULT NULL COMMENT '创建时间',
  `loginip` varchar(45) DEFAULT NULL COMMENT '最后登录的IP地址',
  `logintime` varchar(45) DEFAULT NULL COMMENT '最后登录的时间',
  `state` varchar(45) DEFAULT NULL COMMENT '用户状态(未激活,正常,禁用)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`username`,`password`,`email`,`avatar`,`createtime`,`loginip`,`logintime`,`state`) values (10,'tom','56481aecfd58b9882bd463a609b97cf7','tom@a.com','avatar-default.png','2016-04-07 15:59:51',NULL,NULL,'正常'),(11,'rose','56481aecfd58b9882bd463a609b97cf7','fankai@kaishengit.com','FgBVI-EguNXnqpxSQ2_PBg5WYgWT','2016-04-08 08:50:32','127.0.0.1','2016-04-12 15:34:51','正常');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
