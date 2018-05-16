package com.asiainfo.outbound.service;


import com.asiainfo.outbound.enity.Activity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author User
 * @date 2018-04-16 15:01
 * @desc 外呼渠道服务接口
 */
public interface IOutboundService {


    /**
     * 获取活动详情
     */
    Activity getActivityDetailInfo(String activityId);


    /**
     * 查询外呼渠道非实时的活动且可推送至外呼系统
     * @return
     */
    List<Activity> getOutboundActivityList();


    /**
     * 活动信息推送
     * @param activityId
     * @return
     * @throws IOException
     */
    Map sendActivityInfo(String activityId) throws IOException;


    List<String> getRencentTouchUser(Map paramMap);

    void saveUserRecord(String activityId, String phoneNo);

    void saveOutboundActivityPushRecordInfo(Map outboundMap);

    List<Map<String,String>> getOutboundActivityUser(int intervalTime);

    List<String> getBlackRedWhiteUser();
}
