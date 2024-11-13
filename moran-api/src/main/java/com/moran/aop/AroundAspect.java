package com.moran.aop;

import cn.dev33.satoken.stp.StpUtil;
import com.moran.conf.constant.CommonConstant;
import com.moran.model.SysLog;
import com.moran.model.vo.UserInfo;
import com.moran.service.SysLogService;
import com.moran.util.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author : moran
 * @date : 2024/3/27 9:32
 */
@Aspect
@Component
@AllArgsConstructor
public class AroundAspect {
    private final SysLogService sysLogService;

    @Around(value = "execution(* com.moran.controller.*.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        SysLog log = addLog(joinPoint);
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.setConsumeTime(endTime - startTime);
        sysLogService.saveSelective(log);
        return proceed;
    }

    @Around(value = "execution(* com.moran.controller.AuthController.login(..))")
    public Object afterReturning(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        SysLog log = addLog(joinPoint);
        long endTime = System.currentTimeMillis();
        log.setConsumeTime(endTime - startTime);
        sysLogService.saveSelective(log);
        return proceed;
    }

    private SysLog addLog(ProceedingJoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        SysLog log = new SysLog();
        if (StpUtil.isLogin()) {
            UserInfo userInfo = (UserInfo)StpUtil.getSession().get(CommonConstant.USER_INFO);
            log.setAccount(userInfo.getUser().getAccount());
            log.setNickName(userInfo.getUser().getNickName());
        }
        log.setIp(RequestUtil.getIpAddress(request));
        log.setMethodName(joinPoint.getSignature().getName());
        log.setClassName(target.getClass().getSimpleName());
        log.setUrl(request.getRequestURI());
        log.setDeviceName(request.getHeader("User-Agent"));
        return log;
    }
}
