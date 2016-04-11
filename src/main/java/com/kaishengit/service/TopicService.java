package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.List;

public class TopicService {

    private NodeDao nodeDao = new NodeDao();
    private TopicDao topicDao = new TopicDao();
    private UserDao userDao = new UserDao();

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

    public Topic viewTopic(Integer id) {
        Topic topic = topicDao.findByIdLoadUserAndNode(id);
        //User user = userDao.findById(topic.getUserid());
        //Node node = nodeDao.findById(topic.getNodeid());

        //topic.setNode(node);
        //topic.setUser(user);

        topic.setViewnum(topic.getViewnum() + 1);
        topicDao.update(topic);

        return topic;
    }

    public Page<Topic> showIndexTopic(String node,String pageNo) {

        int pageSize = 20;
        Page<Topic> page ;

        if(StringUtils.isNumeric(node)) {
            //filter
            int count = topicDao.count(Integer.valueOf(node));
            page = new Page<>(pageNo,count,pageSize);

            List<Topic> topicList = topicDao.findByPage(Integer.valueOf(node),page.getStart(),page.getSize());
            page.setItems(topicList);
        } else {
            //all
            int count = topicDao.count();
            page = new Page<>(pageNo,count,pageSize);

            List<Topic> topicList = topicDao.findByPage(page.getStart(),page.getSize());
            page.setItems(topicList);
        }


        return page;
    }
}
