package com.sample.store.servicesgateway.config.security.basicauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

public class CheckAuthCookieFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("Run before Filter");

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(httpServletRequest);

        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies !=null) {
            System.out.println("Cookies length: " + cookies.length);
            System.out.println(cookies[0].getName());
        }
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                logger.debug(cookie.getName() + " : " + cookie.getValue());
                if (cookie.getName().equalsIgnoreCase("auth")) {
                    String newCookieValue = "Basic " + cookie.getValue() + "=";
                    System.out.println("New cookie: " + newCookieValue);
                    mutableRequest.putHeader("authorization", URLDecoder.decode(newCookieValue, "utf-8"));
                }
            }
        }
        chain.doFilter(mutableRequest, response);
    }
}
