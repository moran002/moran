package com.moran.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.moran.conf.constant.CommonConstant;
import com.moran.model.vo.UserInfo;
import com.moran.util.PageUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author : moran
 */
public class Controller {

    /**
     * 开启分页
     */
    public void startPage() {
        PageUtil.startPage();
    }

    /**
     * 获取request
     * @author :moran
     **/
    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取response
     * @author :moran
     **/
    public HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 获取用户设备信息
     * @author :moran
     **/
    public String getDeviceInfo() {
        return getRequest().getHeader("User-Agent");
    }

    /**
     * 获取用户ID
     */
    public UserInfo getUserInfo() {
        return (UserInfo) StpUtil.getSession().get(CommonConstant.USER_INFO);
    }
    /**
     * 获取用户ID
     */
    public Integer getUserId() {
        return getUserInfo().getUserId();
    }
}
