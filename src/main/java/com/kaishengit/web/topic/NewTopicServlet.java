package com.kaishengit.web.topic;

import com.kaishengit.entity.Node;
import com.kaishengit.entity.User;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/topic/new.do")
public class NewTopicServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Node> nodeList = new TopicService().findAllNode();

        req.setAttribute("nodeList",nodeList);
        forward(req,resp,"topic/new");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String text = req.getParameter("text");
        String nodeId = req.getParameter("nodeid");

        if(StringUtils.isEmpty(title.trim()) || StringUtils.isEmpty(nodeId)) {
            resp.sendError(400,"参数错误");
        } else {
            TopicService topicService = new TopicService();

            User user = getLoginUser(req);
            Integer id = topicService.saveNewTopic(title,text,nodeId,user);

            resp.sendRedirect("/topic/view.do?id=" + id);
        }

    }
}
