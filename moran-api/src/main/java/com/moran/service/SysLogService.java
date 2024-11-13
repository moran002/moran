package com.moran.service;

import io.mybatis.mapper.example.ExampleWrapper;
import org.springframework.stereotype.Service;
import io.mybatis.service.AbstractService;
import com.moran.model.SysLog;
import com.moran.mapper.SysLogMapper;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * sys_log - 登录日志
 *
 * @author 系统自动生成
 */
@Service
public class  SysLogService extends AbstractService<SysLog, Integer, SysLogMapper> {

    public List<SysLog> list(String account, String nickName, String startTime, String endTime) {
        ExampleWrapper<SysLog, Integer> wrapper = baseMapper.wrapper();
        wrapper.orderByDesc(SysLog::getOptime);
        if (StringUtils.hasLength(account)) {
            wrapper.contains(SysLog::getAccount, account);
        }
        if (StringUtils.hasLength(nickName)) {
            wrapper.contains(SysLog::getNickName, nickName);
        }
        if (StringUtils.hasLength(startTime)) {
            wrapper.ge(SysLog::getOptime, startTime);
        }
        if (StringUtils.hasLength(endTime)) {
            wrapper.le(SysLog::getOptime, endTime);
        }
        return wrapper.list();
    }
}
