package com.kaishengit.web.user;

import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/forget/callback.do")
public class ForgetPasswordCallbackServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");

        if(StringUtils.isEmpty(token)) {
            resp.sendError(400);
        } else {
            UserService userService = new UserService();
            try {
                userService.validateForgetPasswordToken(token);
                req.setAttribute("token",token);
                forward(req,resp,"user/newpassword");
            } catch (ServiceException ex) {
                req.setAttribute("message",ex.getMessage());
                forward(req,resp,"user/tokenerror");
            }
        }

    }
}
