package com.kaishengit.dao;

import com.google.common.collect.Lists;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.util.DBHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public Topic findByIdLoadUserAndNode(Integer id) {
        String sql = "SELECT t_topic.*,t_user.`username`,t_user.`avatar`,t_node.`nodename`FROM t_topic \n" +
                "INNER JOIN t_user ON t_topic.`userid` = t_user.`id`\n" +
                "INNER JOIN t_node ON t_topic.`nodeid` = t_node.`id`\n" +
                "WHERE t_topic.`id` = ?";
        return DBHelp.query(sql, new ResultSetHandler<Topic>() {
            @Override
            public Topic handle(ResultSet resultSet) throws SQLException {
                if(resultSet.next()) {
                    BasicRowProcessor rowProcessor = new BasicRowProcessor();
                    Topic topic = rowProcessor.toBean(resultSet,Topic.class);
                    User user = rowProcessor.toBean(resultSet,User.class);
                    Node node = rowProcessor.toBean(resultSet,Node.class);

                    topic.setUser(user);
                    topic.setNode(node);
                    return topic;
                }
                return null;
            }
        }, id);
    }

    public void update(Topic topic) {
        String sql = "update t_topic set title = ?,text=?,nodeid=?,viewnum=?,favnum=?,likenum=?,replynum=?,replytime=? where id = ?";
        DBHelp.update(sql,topic.getTitle(),topic.getText(),topic.getNodeid(),topic.getViewnum(),topic.getFavnum(),topic.getLikenum(),topic.getReplynum(),topic.getReplytime(),topic.getId());
    }

    public int count(Integer nodeid) {
        String sql = "select count(*) from t_topic where nodeid = ?";
        return DBHelp.query(sql,new ScalarHandler<Long>(),nodeid).intValue();
    }

    public int count() {
        String sql = "select count(*) from t_topic";
        return DBHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public List<Topic> findByPage(Integer nodeId, int start, int size) {
        String sql = "SELECT t_topic.*,t_user.`username`,t_user.avatar from t_topic inner join t_user on t_topic.userid = t_user.id where nodeid = ? order by replytime desc limit ?,?";
        return DBHelp.query(sql,new TopicUserHandler(),nodeId,start,size);
    }

    public List<Topic> findByPage(int start, int size) {
        String sql = "SELECT t_topic.*,t_user.`username`,t_user.avatar from t_topic inner join t_user on t_topic.userid = t_user.id order by replytime desc limit ?,?";
        return DBHelp.query(sql,new TopicUserHandler(),start,size);
    }


    private class TopicUserHandler implements ResultSetHandler<List<Topic>> {
        @Override
        public List<Topic> handle(ResultSet rs) throws SQLException {
            List<Topic> topicList = Lists.newArrayList();
            BasicRowProcessor rowProcessor = new BasicRowProcessor();
            while (rs.next()) {

                Topic topic = rowProcessor.toBean(rs,Topic.class);
                User user = rowProcessor.toBean(rs,User.class);
                topic.setUser(user);
                topicList.add(topic);
            }
            return topicList;
        }
    }

}
