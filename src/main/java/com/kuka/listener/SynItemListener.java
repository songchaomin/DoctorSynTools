package com.kuka.listener;

import com.kuka.event.SynItemEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

@Service
public class SynItemListener implements ApplicationListener<SynItemEvent> {
    @Override
    public void onApplicationEvent(SynItemEvent event) {
        event.synItem();
    }
}
