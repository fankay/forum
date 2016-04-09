package com.kaishengit.web.user;

import com.kaishengit.util.ConfigProp;
import com.kaishengit.web.BaseServlet;
import com.qiniu.util.Auth;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/setting.do")
public class UserSettingServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //产生七牛上传的Token
        Auth auth = Auth.create(ConfigProp.get("qiniu.ak"),ConfigProp.get("qiniu.sk"));
        String token = auth.uploadToken(ConfigProp.get("qiniu.bucketname"));
        req.setAttribute("token",token);

        forward(req,resp,"user/setting");
    }
}
