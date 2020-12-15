package com.kuka.event;

import com.alibaba.fastjson.JSONObject;
import com.kuka.utils.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

public class SynItemEvent extends ApplicationEvent {
    @Autowired
    private Md5Utils md5Utils;

    public SynItemEvent(Object source) {
        super(source);
    }
    public void synItem() {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("tenantCode","ylkj");
        jsonObject.put("ownerUserId","");
        jsonObject.put("itemId","");
        String requestBody=jsonObject.toJSONString();
        try {
            String url = md5Utils.getSingnUrl("WMS_SKU_INFO_NOTIFY", requestBody);
            if (!StringUtils.isEmpty(url)) {
                String result = md5Utils.doPost(url, requestBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
