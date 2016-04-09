package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/user/changeavatar.do")
public class ChangeAvatarServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(isAjaxRequest(req)) {
            String key = req.getParameter("key");
            User user = getLoginUser(req);
            user.setAvatar(key);
            new UserService().updateUser(user);

            Map<String,Object> result = Maps.newHashMap();
            result.put("state","success");
            rendJson(resp,result);
        }
    }
}
