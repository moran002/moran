package com.moran.model.vo.system;

import com.moran.model.SysLog;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * 日志
 * @author : moran
 * @date : 2024/3/28 14:55
 */
@Setter
@Getter
public class LogVO {
    private String account;

    private String nickName;

    private String ip;

    private String methodName;

    private String className;

    private String url;

    private String deviceName;

    private Date optime;

    private String consumeTime;

    public static LogVO convert(SysLog sysLog) {
        LogVO vo = new LogVO();
        BeanUtils.copyProperties(sysLog, vo);
        if ("0:0:0:0:0:0:0:1".equals(sysLog.getIp())) {
            vo.setIp("主机登录");
        }
        vo.setDeviceName(simpleName(sysLog.getDeviceName()));
        vo.setConsumeTime(new BigDecimal(sysLog.getConsumeTime()).divide(new BigDecimal(1000), 3, RoundingMode.CEILING) + "秒");
        return vo;
    }

    private static String simpleName(String name) {
        if (name.contains("Android")) {
            return "Android Device";
        } else if (name.contains("iPhone")) {
            return "iPhone";
        } else if (name.contains("iPad")) {
            return "iPad";
        } else if (name.contains("Windows Phone")) {
            return "Windows Phone";
        } else if (name.contains("Windows NT 10.0")) {
            return "Windows NT 10.0";
        }else if (name.contains("Windows")) {
            return "Windows";
        } else {
            return name;
        }
    }
}
