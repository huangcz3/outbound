package com.asiainfo.outbound.mapper;

import com.asiainfo.outbound.enity.Activity;
import com.asiainfo.outbound.enity.ActivityChannelDetail;
import com.asiainfo.outbound.enity.ActivityRecommendProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author User
 * @date 2018-04-17 14:50
 * @desc
 */
public interface OutboundMapper {

    void saveOutboundActivityPushRecordInfo(Map paramMap);

    List<Activity> getOutboundActivityList();

    void exportCustomer(Map paramMap);

    List<String> getRencentTouchUser(Map paramMap);

    void saveUserRecord(@Param("activityId") String activityId, @Param("phoneNo") String phoneNo);

    /**
     * 获取活动基本信息
     */
    Activity getActivityById(String activityId);

    /**
     * 获取活动渠道详情
     */
    List<ActivityChannelDetail> getChannelDetail(String activityId);

    /**
     * 获取活动推荐业务信息
     */
    List<ActivityRecommendProduct> getRecommendProduct(String activityId);

    /**
     * 获取外呼渠道活动目标用户月表信息
     */
    List<Map<String,String>> getOutboundActivityUser(@Param("intervalTime")int intervalTime);
}
