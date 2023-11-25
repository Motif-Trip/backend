package com.ssafy.motif.app.util.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class CookieUtil {
    public void setCookie(String key, String value, int expiredTime, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(expiredTime);
        response.addCookie(cookie);
    }

    public void removeCookie(String key, HttpServletResponse response) {
        Cookie cookie = new Cookie(key, null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
