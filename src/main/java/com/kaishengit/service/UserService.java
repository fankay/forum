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
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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

    public Integer validateForgetPasswordToken(String token) {
        ForgetPassword forgetPassword = forgetPasswordDao.findByToken(token);
        if(forgetPassword == null) {
            throw new ServiceException("该链接无效");
        } else {
            //判断链接时效 创建时间+30分钟>当前时间
            String createTime = forgetPassword.getCreatetime();//13:50 + 30 > now

            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            DateTime dateTime = formatter.parseDateTime(createTime);
            dateTime = dateTime.plusMinutes(30);

            if(dateTime.isAfterNow()) {
                //有效
                return forgetPassword.getUid();
            } else {
                throw new ServiceException("该链接已失效");
            }


        }
    }

    /**
     * 用户找回密码：设置新密码
     * @param token
     * @param password
     */
    public void forgetPasswordSetNewPassword(String token, String password) {
        ForgetPassword forgetPassword = forgetPasswordDao.findByToken(token);
        if(forgetPassword == null) {
            throw new ServiceException("token无效");
        } else {
            Integer uid = forgetPassword.getUid();
            User user = userDao.findById(uid);

            //修改用户密码
            user.setPassword(DigestUtils.md5Hex(password + ConfigProp.get("user.password.salt")));
            userDao.update(user);

            //删除找回密码记录
            forgetPasswordDao.deleteByUid(uid);

        }


    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void changePassword(User user, String password) {
        user.setPassword(DigestUtils.md5Hex(password+ConfigProp.get("user.password.salt")));
        updateUser(user);
    }
}
