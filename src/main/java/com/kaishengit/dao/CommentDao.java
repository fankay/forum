package com.kaishengit.dao;

import com.google.common.collect.Lists;
import com.kaishengit.entity.Comment;
import com.kaishengit.entity.User;
import com.kaishengit.util.DBHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CommentDao {

    public Integer save(Comment comment) {
        String sql = "INSERT INTO t_comment(`comment`,createtime,userid,topicid) values(?,?,?,?)";
        return DBHelp.insert(sql,comment.getComment(),comment.getCreatetime(),comment.getUserid(),comment.getTopicid()).intValue();
    }

    public List<Comment> findAllLoadUserByTopicId(Integer topicId) {
        String sql = "SELECT t_comment.*,username,avatar FROM t_comment \n" +
                "INNER JOIN t_user ON t_comment.`userid` = t_user.`id`\n" +
                "WHERE t_comment.`topicid` = ?";
        return DBHelp.query(sql, new ResultSetHandler<List<Comment>>() {
            @Override
            public List<Comment> handle(ResultSet rs) throws SQLException {
                List<Comment> commentList = Lists.newArrayList();
                BasicRowProcessor rowProcessor = new BasicRowProcessor();
                while(rs.next()) {
                    Comment comment = rowProcessor.toBean(rs,Comment.class);
                    User user = rowProcessor.toBean(rs,User.class);
                    comment.setUser(user);
                    commentList.add(comment);
                }
                return commentList;
            }
        }, topicId);
    }
}
