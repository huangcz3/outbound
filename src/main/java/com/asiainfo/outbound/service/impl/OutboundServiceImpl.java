package com.asiainfo.outbound.service.impl;

import com.asiainfo.outbound.enity.Activity;
import com.asiainfo.outbound.enity.ActivityChannelDetail;
import com.asiainfo.outbound.enity.ActivityRecommendProduct;
import com.asiainfo.outbound.mapper.OutboundMapper;
import com.asiainfo.outbound.service.IOutboundService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author User
 * @date 2018-04-25 15:41
 * @desc
 */
@Service
public class OutboundServiceImpl implements IOutboundService {

    private static final Logger logger = LoggerFactory.getLogger(OutboundServiceImpl.class);

    private String outboundUrl;

    @Value("fcm.customer.red.white.black.filter.url")
    private String filterUrl;

    @Autowired
    private OutboundMapper outboundMapper;


    @Override
    public Activity getActivityDetailInfo(String activityId) {

        //获取基本信息
        Activity activityBean = outboundMapper.getActivityById(activityId);
        //获取渠道信息
        List<ActivityChannelDetail> activityChannelDetailList = outboundMapper.getChannelDetail(activityId);
        activityBean.setActivityChannelDetailList(activityChannelDetailList);

        //获取推荐业务
        List<ActivityRecommendProduct> activityRecommendProductList = outboundMapper.getRecommendProduct(activityId);
        activityBean.setActivityRecommendProducts(activityRecommendProductList);

        return activityBean;
    }

    @Override
    public List<Activity> getOutboundActivityList() {
        return outboundMapper.getOutboundActivityList();
    }

    @Override
    public Map sendActivityInfo(String activityId) throws IOException {

//        //获取活动基本信息
//        Activity activityInfo = outboundMapper.getActivityById(activityId);
//        //获取渠道信息
//        List<ActivityChannelDetail> activityChannelDetailList = outboundMapper.getChannelDetail(activityId);
//        //获取推荐业务
//        List<ActivityRecommendProduct> activityRecommendProductList = outboundMapper.getRecommendProduct(activityId);
//
//        String activityDesc = activityChannelDetailList.get(0).
//                getActivityChannelRuleDetailList().get(0).
//                getRuleValue();
//
//        Map outboundMap = new HashMap<>();
//        outboundMap.put("activityId", activityId);
//        outboundMap.put("activityName", activityInfo.getActivityName());
//        outboundMap.put("activityType", activityInfo.getSceneFlag());
//        outboundMap.put("startTime", activityInfo.getStartTime());
//        outboundMap.put("endTime", activityInfo.getEndTime());
//        outboundMap.put("activityDesc", activityDesc);
//        outboundMap.put("recommend", activityRecommendProductList);
//        String lines = HttpUtil.sendGet(outboundUrl, outboundMap);
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map<Object, Object> resultMap = objectMapper.readValue(lines, Map.class);
//
//
//        int code = (int) resultMap.get("code");
//        //请求成功后，保存taskId
//        if (code == 200) {
//
//            outboundMapper.saveOutboundActivityPushRecordInfo(outboundMap);
//
//            //过滤红白黑
//            Map map = new HashMap();
//            map.put("activityId", activityId);
//            HttpUtil.sendGet(filterUrl,map);
//
//            logger.info("活动{}推送成功！", activityId);
//        } else {
//            logger.error("推送失败！" + resultMap.get("data"));
//        }

        return null;
    }

    @Override
    public List<String> getRencentTouchUser(Map paramMap) {
        return outboundMapper.getRencentTouchUser(paramMap);
    }

    @Override
    public void saveUserRecord(String activityId, String phoneNo) {
        outboundMapper.saveUserRecord(activityId, phoneNo);
    }

    @Override
    public void saveOutboundActivityPushRecordInfo(Map outboundMap) {
        outboundMapper.saveOutboundActivityPushRecordInfo(outboundMap);
    }

    @Override
    public List<Map<String, String>> getOutboundActivityUser(int intervalTime) {
        return outboundMapper.getOutboundActivityUser(intervalTime);
    }


    @Override
    public List<String> getBlackRedWhiteUser() {
        return null;
    }
}
