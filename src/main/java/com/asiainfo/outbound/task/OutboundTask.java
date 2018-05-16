package com.asiainfo.outbound.task;


import com.asiainfo.outbound.enity.Activity;
import com.asiainfo.outbound.service.IOutboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author User
 * @date 2018-04-13 15:29
 * @desc
 */
@Component
public class OutboundTask {

    private static final Logger logger = LoggerFactory.getLogger(OutboundTask.class);

    private String outboundUrl;

    @Value("fcm.customer.red.white.black.filter.url")
    private String filterUrl;


    @Autowired
    private IOutboundService outboundService;


    @Scheduled(cron = "*/10 * * * * ?")
    public void sendOutboundActivityInfo() {

        //查询外呼渠道的活动
        List<Activity> outboundActivityList = outboundService.getOutboundActivityList();

        if (outboundActivityList.size() == 0) {
            logger.info("= = = = = 暂无外呼渠道活动 = = = = =");
        } else {
            outboundActivityList.forEach(activity -> {
                try {
                    outboundService.sendActivityInfo(activity.getActivityId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

    }


}
