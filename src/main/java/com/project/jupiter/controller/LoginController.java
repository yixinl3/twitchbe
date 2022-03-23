package com.project.jupiter.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.jupiter.entity.request.LoginRequestBody;
import com.project.jupiter.entity.response.LoginResponseBody;
import com.project.jupiter.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestBody LoginRequestBody requestBody, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String firstName = loginService.verifyLogin(requestBody.getUserId(), requestBody.getPassword());
        if (!firstName.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("user_id", requestBody.getUserId());
            session.setMaxInactiveInterval(600); // 10 min

            LoginResponseBody loginResponseBody = new LoginResponseBody(requestBody.getUserId(), firstName);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().print(new ObjectMapper().writeValueAsString(loginResponseBody));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
