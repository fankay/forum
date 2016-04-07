package com.kaishengit.web.user;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/reg.do")
public class RegServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forward(req,resp,"user/reg");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        Map<String,Object> result = Maps.newHashMap();

        UserService userService = new UserService();

        //服务端验证
        if(StringUtils.isNotEmpty(username) && StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(email)) {

            try {
                userService.saveNewUser(username, password, email);
                result.put("state","success");
            }   catch (ServiceException ex) {
                result.put("state","error");
                result.put("message",ex.getMessage());
            }
        } else {
            result.put("state","error");
            result.put("message","参数错误");
        }

        rendJson(resp,result);
    }
}
