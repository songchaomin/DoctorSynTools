package com.kuka.listener;

import com.kuka.event.SendMailEvent;
import com.kuka.utils.Md5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import java.io.IOException;
import java.net.URLConnection;
import java.sql.Connection;

@Service
@Slf4j
public class SendMailListener implements ApplicationListener<SendMailEvent> {
@Autowired
private  Md5Utils md5Utils;
    @Override
    public void onApplicationEvent(SendMailEvent sendMailEvent) {


    }

}
