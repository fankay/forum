package com.kaishengit.dao;

import com.kaishengit.entity.User;
import com.kaishengit.util.DBHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class UserDao {

    public void save(User user) {
        String sql = "insert into t_user(username,password,email,avatar,createtime,state) values(?,?,?,?,?,?)";
        DBHelp.update(sql,user.getUsername(),user.getPassword(),user.getEmail(),user.getAvatar(),user.getCreatetime(),user.getState());
    }

    public User findByUserName(String username) {
        String sql = "select * from t_user where username = ?";
        return DBHelp.query(sql,new BeanHandler<>(User.class),username);
    }

    public User findByEmail(String email) {
        String sql = "select * from t_user where email = ?";
        return DBHelp.query(sql,new BeanHandler<>(User.class),email);
    }

    public void update(User user) {
        String sql = "update t_user set password = ?,avatar=?,email=?,loginip=?,logintime=?,state=? where id = ?";
        DBHelp.update(sql,user.getPassword(),user.getAvatar(),user.getEmail(),user.getLoginip(),user.getLogintime(),user.getState(),user.getId());
    }

    public User findById(Integer uid) {
        String sql = "select * from t_user where id = ?";
        return DBHelp.query(sql,new BeanHandler<>(User.class),uid);
    }
}
