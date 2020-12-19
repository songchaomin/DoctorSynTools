package com.kuka.listener;

import com.kuka.event.SendMailEvent;
import com.kuka.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;
import java.net.URLConnection;
import java.sql.Connection;

@Service
@Slf4j
public class SendMailListener implements ApplicationListener<SendMailEvent>, Servlet {
private  static  String remoteAddress;
    @Override
    public void onApplicationEvent(SendMailEvent sendMailEvent) {
        System.out.println("远程地址："+remoteAddress);

    }

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {

    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
             remoteAddress=servletRequest.getRemoteAddr();
        System.out.println("远程地址："+remoteAddress);
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
