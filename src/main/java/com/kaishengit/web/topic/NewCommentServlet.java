package com.kaishengit.web.topic;

import com.google.common.collect.Maps;
import com.kaishengit.entity.Comment;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/topic/comment/new.do")
public class NewCommentServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(isAjaxRequest(req)) {
            TopicService topicService = new TopicService();

            Map<String,Object> result = Maps.newHashMap();

            String comment = req.getParameter("comment");
            String topicId = req.getParameter("topicId");

            if(StringUtils.isEmpty(comment) || !StringUtils.isNumeric(topicId)) {
                result.put("state","error");
                result.put("message","参数错误");
            } else {

                Topic topic = topicService.findTopicById(Integer.valueOf(topicId));
                User user = getLoginUser(req);
                if(topic == null || user == null) {
                    result.put("state","error");
                    result.put("message","参数错误");
                } else {

                    Comment commentObj = topicService.saveNewComment(comment,topic,user);
                    result.put("state","success");
                    result.put("data",commentObj);

                }

            }


            rendJson(resp,result);
        }

    }
}
