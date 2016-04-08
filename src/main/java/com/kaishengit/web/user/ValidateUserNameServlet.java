package com.kaishengit.web.user;

import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/validate/username.do")
public class ValidateUserNameServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String username = req.getParameter("username");
        username = new String(username.getBytes("ISO8859-1"),"UTF-8");

        UserService userService = new UserService();
        User user = userService.findByUserName(username);

        String result;
        if(user == null) {
            if("forget".equals(action)) {
                result = "false";
            } else {
                result = "true";
            }
        } else {
            if("forget".equals(action)) {
                result = "true";
            } else {
                result = "false";
            }
        }
        rendText(resp,result);
    }
}
