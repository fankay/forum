package com.kaishengit.web.patchca;

import com.kaishengit.web.BaseServlet;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.predefined.CurvesRippleFilterFactory;
import org.patchca.service.ConfigurableCaptchaService;
import org.patchca.utils.encoder.EncoderHelper;
import org.patchca.word.RandomWordFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/patchca.png")
public class PatchcaServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ConfigurableCaptchaService service = new ConfigurableCaptchaService();
        service.setColorFactory(new SingleColorFactory(new Color(26, 69, 226)));
        service.setFilterFactory(new CurvesRippleFilterFactory(service.getColorFactory()));

        RandomWordFactory wordFactory = new RandomWordFactory();
        wordFactory.setMinLength(6);
        wordFactory.setMaxLength(6);
        service.setWordFactory(wordFactory);

        response.setContentType("image/png");

        OutputStream outputStream = response.getOutputStream();
        String captcha = EncoderHelper.getChallangeAndWriteImage(service,"png",outputStream);

        HttpSession session = request.getSession();
        session.setAttribute("captcha",captcha);

        outputStream.flush();
        outputStream.close();
    }
}
