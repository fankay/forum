package com.kaishengit.service;

import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.ConfigProp;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;


public class UserService {

    private UserDao userDao = new UserDao();

    /**
     * 新用户注册
     * @param username
     * @param password
     * @param email
     */
    public void saveNewUser(String username, String password, String email) {

        if(findByUserName(username) != null) {
            throw new ServiceException("注册失败，账号已被占用");
        }
        if(findByEmail(email) != null) {
            throw new ServiceException("注册失败，电子邮件已被注册");
        }


        User user = new User();
        user.setUsername(username);
        user.setPassword(DigestUtils.md5Hex(password + ConfigProp.get("user.password.salt")));
        user.setEmail(email);
        user.setCreatetime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
        user.setAvatar(ConfigProp.get("user.default.avatar"));
        user.setState(User.USER_STATE_NORMAL);

        userDao.save(user);
    }

    /**
     * 根据账号查找User对象
     * @param username
     * @return
     */
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    /**
     * 根据电子邮件查找User对象
     * @param email
     * @return
     */
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
