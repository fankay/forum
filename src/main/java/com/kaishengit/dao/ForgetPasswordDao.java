package com.kaishengit.dao;

import com.kaishengit.entity.ForgetPassword;
import com.kaishengit.util.DBHelp;

public class ForgetPasswordDao {

    public void save(ForgetPassword fp) {
        String sql = "insert into t_forgetpassword(token,createtime,uid) values(?,?,?)";
        DBHelp.update(sql,fp.getToken(),fp.getCreatetime(),fp.getUid());
    }

}
