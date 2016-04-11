package com.kaishengit.web;

import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.Page;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/index.do")
public class IndexServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String node = req.getParameter("node");
        String pageNo = req.getParameter("page");

        TopicService topicService = new TopicService();
        List<Node> nodeList = topicService.findAllNode();
        Page<Topic> page = topicService.showIndexTopic(node,pageNo);

        req.setAttribute("page",page);
        req.setAttribute("nodeList",nodeList);
        forward(req,resp,"index");
    }
}
