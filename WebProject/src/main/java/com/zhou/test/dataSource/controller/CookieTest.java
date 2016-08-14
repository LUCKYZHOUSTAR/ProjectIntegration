/**
 * 
 */
package com.zhou.test.dataSource.controller;

import java.io.PrintWriter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LUCKY
 *
 */
@Controller
@RequestMapping(value = "/cookie")
public class CookieTest {

    @RequestMapping(value = "test")
    @ResponseBody
    public void cookieTest(HttpServletRequest request, HttpServletResponse response)
                                                                                    throws Exception {

        Cookie cookie = null;
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        Cookie[] cookies = request.getCookies();
        //如果没有cookie，新建一个cookie
        if (cookies == null) {
            out.println("<b>cookies is null.</b></br>");
            cookie = new Cookie("new", "1");
            cookie.setMaxAge(100000);
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            out.println("<b>cookies is not null.</b></br>");

            for (int i = 0; i < cookies.length; i++) {

                cookie = cookies[i];

                out.println("cookie" + i + " name: " + cookie.getName() + "</br>");
                out.println("cookie comment" + cookie.getComment() +"domain"+cookie.getDomain()+"path"+cookie.getPath()+ "</br>");
            }
        }

        HttpSession session = request.getSession();
        if (session == null) {
            out.println("<b>session is null.</b></br>");

        } else {
            out.println("<b>session is not null.</b></br>");
            out.println("session id: " + session.getId() + "</br>");
        }

        out.println("</body></html>");
    }
}
