package com.asiainfo.outbound.handle;

import com.asiainfo.outbound.service.IOutboundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author User
 * @date 2018-04-25 16:20
 * @desc 频次管控处理程序
 * 1、外呼频次：五星级用户每月外呼不超过1次、其它用户每月外呼不超过2次，敏感客户不进行外呼；
 * 2、时间占用：按运营人员提交的外呼时间占用用户外呼状态；外呼时间到期后将未外呼的用户回复为可外呼状态；
 */
@Component
public class FrequencyControlHandle {

    /**
     * redis key 活动目标用户list
     */
    private static final String REDIS_KEY_USER_LIST = "SET-USERLIST:";

    private static final Integer INTERVAL_TIME = 30;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private IOutboundService outboundService;

    public List filterPhoneNo(String activityId) {


        //活动目标用户
        Set<String> actUsers = jedisCluster.smembers(REDIS_KEY_USER_LIST + activityId);

        //已外呼用户（一段时间内）
        List<Map<String, String>> phoneList = outboundService.getOutboundActivityUser(INTERVAL_TIME);

        for (Map<String, String> map : phoneList) {
            String phoneNo = map.get("phone_no");
//            String callTime = map.get("call_time");
            int frequency = Integer.parseInt(map.get("frequency"));
            int tags = Integer.parseInt(map.get("tags"));
//            int state = Integer.parseInt(map.get("state"));

            if (actUsers.contains(phoneNo)) {
                //外呼频次：五星级用户每月外呼不超过1次、其它用户每月外呼不超过2次，敏感客户不进行外呼
                if ((tags == 5 && frequency == 1) || (frequency == 2)) {
                    actUsers.remove(phoneNo);
                }
            }
        }
        return (List) actUsers;
    }

    public List<Map<String,String>> markUser(List actUser){

        //初始化打标用户list。打标结果为：key:18328005498,value:1
        List<Map<String,String>> markedUser = null;

        for (Object user: actUser){
            //从数据表查出该手机号的星级
            int tags = 3;

            Map<String,String> map = new HashMap<>();
            map.put(String.valueOf(user), String.valueOf(tags));
            markedUser.add(map);
        }
        return markedUser;
    }


}
