package com.asiainfo.outbound.handle;

import com.asiainfo.outbound.enity.Activity;
import com.asiainfo.outbound.service.IOutboundService;
import com.asiainfo.outbound.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author User
 * @date 2018-04-25 16:38
 * @desc
 */
@Component
public class FTPHandle {

    private static final String FILE_PATH = "E:\\Asiainfo\\test";

    private static final String FILE_PRE_NAME = "QCD_D0005_";

    private static final String HOST_NAME = "192.168.201.50";

    private static final Integer PORT = 21;

    private static final String USER_NAME = "ftpuser";

    private static final String PASSWORD = "123456";

    private static final String PATH = "/home/ftp";


    private static final Logger logger = LoggerFactory.getLogger(FTPHandle.class);

    @Autowired
    private IOutboundService outboundService;

    @Autowired
    private FrequencyControlHandle frequencyControlHandle;

    public void ftpToWaihu(String activityId) throws IOException {

        //活动目标用户(已经过频次管控处理)
        List actUser = frequencyControlHandle.filterPhoneNo(activityId);

        //活动目标用户
        List<Map<String, String>> markedUser = frequencyControlHandle.markUser(actUser);

        //活动基本信息
        Activity activity = outboundService.getActivityDetailInfo(activityId);

        //写文件
        File file = new File(FILE_PATH + File.separator + FILE_PRE_NAME + activityId + ".dat");

        FileWriter out = new FileWriter(file);

        //其中 key为手机号，value为tags
        for (Map<String, String> map : markedUser) {
            for (String key : map.keySet()) {
                out.write(key +
                        "&" + activityId +
                        "&" + activity.getActivityName() +
                        "&" + activity.getCityId() +
                        "&" + map.get(key));
            }
        }
        out.close();

        //ftp至目标路径
        boolean flag = FTPUtil.uploadFile(HOST_NAME,
                PORT,
                USER_NAME,
                PASSWORD,
                PATH,
                activityId + ".txt",
                new FileInputStream(file));
        if (flag) {
            //目标用户入外呼渠道活动推送用户记录表
            actUser.forEach(user -> outboundService.saveUserRecord(activityId, String.valueOf(user)));
        }
    }

}
