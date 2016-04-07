package com.kaishengit.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseServlet extends HttpServlet {

    public void forward(HttpServletRequest req, HttpServletResponse resp,String viewName) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/"+viewName+".jsp").forward(req,resp);
    }

}
