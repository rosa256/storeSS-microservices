package com.sample.store.servicesgateway;

import com.sample.store.servicesgateway.config.AuthBean;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@ResponseBody
public class AuthController {

    @GetMapping(path = "/basicauth")
    public AuthBean authenticate(@RequestHeader("Authorization") String authorizationHeader, HttpServletResponse response){

        System.out.println("HEADER: "+ authorizationHeader);
        String authValue = authorizationHeader
                .replace("Basic ","")
                .replace("=","");
        System.out.println("HEADER2: "+ authValue);

        Cookie cookie = new Cookie("auth", authValue);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
        return new AuthBean("You are authenticated");
    }

}
