package com.kaishengit.web.user;

import com.google.common.collect.Maps;
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

@WebServlet("/forgetpassword/setpassword.do")
public class ForgetPasswordSetPasswordServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String password = req.getParameter("password");

        Map<String,Object> result = Maps.newHashMap();
        if(StringUtils.isEmpty(token) || StringUtils.isEmpty(password)) {
            result.put("state","error");
            result.put("message","参数错误");
        } else {
            UserService userService = new UserService();

            try {
                userService.forgetPasswordSetNewPassword(token, password);
                result.put("state","success");
            } catch (ServiceException ex) {
                result.put("state","error");
                result.put("message",ex.getMessage());
            }
        }

        rendJson(resp,result);


    }
}
