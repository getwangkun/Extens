package com.cdvtc.news.web.listener;

import com.cdvtc.news.model.User;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    public static Map<HttpSession, User> onlineUserMap = new HashMap<>(); // 全局保存在线用户

    public SessionListener() {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        User user = (User)se.getSession().getAttribute("user");
        if(user != null) {
            onlineUserMap.remove(se.getSession());
        }
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        if("user".equals(sbe.getName())) {
            User user = (User)sbe.getValue();
            onlineUserMap.put(sbe.getSession(), user);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
