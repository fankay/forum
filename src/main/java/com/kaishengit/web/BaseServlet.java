package com.kaishengit.web;

import com.google.gson.Gson;
import com.kaishengit.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseServlet extends HttpServlet {

    public void forward(HttpServletRequest req, HttpServletResponse resp,String viewName) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/"+viewName+".jsp").forward(req,resp);
    }

    public void rendJson(HttpServletResponse response,Object result) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(new Gson().toJson(result));
        out.flush();
        out.close();
    }

    public void rendText(HttpServletResponse response,String result) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }

    public String getRemoteIp(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

    public boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    public User getLoginUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("curr_user");
    }

}
