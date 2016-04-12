package com.kaishengit.service;

import com.kaishengit.dao.CommentDao;
import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.entity.Comment;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.List;

public class TopicService {

    private NodeDao nodeDao = new NodeDao();
    private TopicDao topicDao = new TopicDao();
    private CommentDao commentDao = new CommentDao();

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

        if(topic == null) {
            throw new ServiceException("该主题不存在或已被删除");
        }

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

    public Topic findTopicById(Integer topicId) {
        return topicDao.findById(topicId);
    }

    public Comment saveNewComment(String comment, Topic topic, User user) {
        String now = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");

        Comment commentObj = new Comment();
        commentObj.setComment(comment);
        commentObj.setCreatetime(now);
        commentObj.setTopicid(topic.getId());
        commentObj.setUserid(user.getId());

        Integer id = commentDao.save(commentObj);
        commentObj.setId(id);
        commentObj.setUser(user);

        topic.setReplynum(topic.getReplynum() + 1);
        topic.setReplytime(now);

        topicDao.update(topic);

        return commentObj;
    }

    public List<Comment> findCommentListByTopicId(Integer topicId) {
        return commentDao.findAllLoadUserByTopicId(topicId);
    }
}
