package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import org.joda.time.DateTime;

import java.util.List;

public class TopicService {

    private NodeDao nodeDao = new NodeDao();
    private TopicDao topicDao = new TopicDao();

    public List<Node> findAllNode() {
        return nodeDao.findAll();
    }

    public Integer saveNewTopic(String title, String text, String nodeId, User user) {
        String now = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");

        Topic topic = new Topic();
        topic.setTitle(title);
        topic.setText(text);
        topic.setNodeid(Integer.valueOf(nodeId));
        topic.setUserid(user.getId());
        topic.setCreatetime(now);
        topic.setViewnum(0);
        topic.setLikenum(0);
        topic.setFavnum(0);
        topic.setReplynum(0);
        topic.setReplytime(now);

        return topicDao.save(topic);
    }

    public Topic findTopicById(Integer id) {
        return topicDao.findById(id);
    }
}
