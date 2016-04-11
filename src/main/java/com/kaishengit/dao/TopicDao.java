package com.kaishengit.dao;

import com.kaishengit.entity.Topic;
import com.kaishengit.util.DBHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class TopicDao {

    public Integer save(Topic topic) {
        String sql = "INSERT INTO t_topic(title,`TEXT`,createtime,userid,nodeid,viewnum,favnum,likenum,replynum,replytime)" +
                "values (?,?,?,?,?,?,?,?,?,?)";
        return DBHelp.insert(sql,topic.getTitle(),topic.getText(),topic.getCreatetime(),topic.getUserid(),topic.getNodeid(),topic.getViewnum(),topic.getFavnum(),topic.getLikenum(),topic.getReplynum(),topic.getReplytime()).intValue();
    }

    public Topic findById(Integer id) {
        String sql = "select * from t_topic where id = ?";
        return DBHelp.query(sql,new BeanHandler<>(Topic.class),id);
    }
}
