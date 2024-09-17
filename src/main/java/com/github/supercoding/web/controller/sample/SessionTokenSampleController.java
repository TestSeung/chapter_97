package com.github.supercoding.web.controller.sample;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SessionTokenSampleController {

    @GetMapping("/set-session")
    public String setSession(HttpSession session) {
        session.setAttribute("user", "아이유");
        session.setAttribute("gender","여자");
        session.setAttribute("job","가수");
        return "Session Set successfully";
    }

    @GetMapping("/get-session")
    public String getSession(HttpSession session) {
        String user = (String) session.getAttribute("user");
        String gender = (String) session.getAttribute("gender");
        String job = (String) session.getAttribute("job");

        return user + ":" + gender + ":" + job;
    }

}
