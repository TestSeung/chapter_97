package com.github.supercoding.web.controller.sample;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @GetMapping("/generate-token")
    public String generateToken(HttpServletResponse httpServletResponse) {
        String jwt = Jwts.builder()
                .setSubject("token1")
                .claim("user","조인성")
                .claim("gender","남자")
                .claim("job","배우")
                .compact();
        httpServletResponse.addHeader("Token", jwt);
        return "JWT set Successfully";
    }

    @GetMapping("/show-token")
    public String showToken(@RequestHeader("Token") String token) {
        Claims claims = Jwts.parser()
                .parseClaimsJwt(token)
                .getBody();

        String user = (String) claims.get("user");
        String gender = (String) claims.get("gender");
        String job = (String) claims.get("job");
        return user + ":" + gender + ":" + job;
    }
}
