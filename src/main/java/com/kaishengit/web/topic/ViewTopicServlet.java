package com.kaishengit.web.topic;

import com.kaishengit.entity.Topic;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/topic/view.do")
public class ViewTopicServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if(StringUtils.isNumeric(id)) {
            TopicService topicService = new TopicService();
            Topic topic = topicService.viewTopic(Integer.valueOf(id));
            req.setAttribute("topic",topic);
            forward(req,resp,"topic/view");
        } else {
            resp.sendError(404,"查看的内容不存在或已被删除");
        }

    }
}
