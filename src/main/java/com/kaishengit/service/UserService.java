package com.kaishengit.service;

import com.kaishengit.dao.ForgetPasswordDao;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.ForgetPassword;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.ConfigProp;
import com.kaishengit.util.EmailUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;

import java.util.UUID;


public class UserService {

    private UserDao userDao = new UserDao();
    private ForgetPasswordDao forgetPasswordDao = new ForgetPasswordDao();

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

    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    public User login(String userName, String password,String ip) {
        User user = findByUserName(userName);
        if(user != null && user.getPassword().equals(DigestUtils.md5Hex(password + ConfigProp.get("user.password.salt")))) {
            //登录成功
            user.setLoginip(ip);
            user.setLogintime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));

            userDao.update(user);
            return user;
        }
        return null;
    }

    /**
     * 找回密码：根据账号，向账号对应的email发送找回密码邮件
     * @param username
     */
    public void forgetPassword(String username) {
        User user = findByUserName(username);
        if(user != null) {
            String uuid = UUID.randomUUID().toString();
            String email = user.getEmail();
            String title = "凯盛论坛-找回密码邮件";
            String url = "http://localhost/forget/callback.do?token="+uuid;
            String msg = user.getUsername() + ":<br>\n" +
                    "点击该<a href='"+url+"'>链接</a>进行设置新密码，该链接30分钟内有效。<br>\n" +
                    url;

            ForgetPassword forgetPassword = new ForgetPassword();
            forgetPassword.setCreatetime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
            forgetPassword.setToken(uuid);
            forgetPassword.setUid(user.getId());
            forgetPasswordDao.save(forgetPassword);

            EmailUtil.sendHtmlEmail(title,msg,email);
        }
    }
}
