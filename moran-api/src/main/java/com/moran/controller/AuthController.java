package com.moran.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.moran.conf.bean.ResponseBean;
import com.moran.conf.constant.CommonConstant;
import com.moran.conf.redis.RedisService;
import com.moran.model.SysMenu;
import com.moran.model.SysUser;
import com.moran.model.dto.LoginDTO;
import com.moran.model.vo.UserInfo;
import com.moran.model.vo.auth.CaptChaVO;
import com.moran.service.SysMenuService;
import com.moran.service.SysUserService;
import com.wf.captcha.SpecCaptcha;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 登录管理
 * @author : moran
 * @date : 2024/3/21 9:53
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final RedisService redisService;
    private final SysUserService userService;
    private final SysMenuService menuService;

    /**
     * 登陆
     * @author :moran
     * @date :2024/3/22 11:16
     **/
    @PostMapping("/login")
    public ResponseBean<String> login(@RequestBody LoginDTO dto) {
//        if (!redisService.hasKey(dto.getCaptchaId())) {
//            return ResponseBean.fail("验证码错误");
//        }
//        String code = redisService.get(dto.getCaptchaId());
//        if (!code.equals(dto.getVerifyCode())) {
//            return ResponseBean.fail("验证码错误");
//        }
        Optional<SysUser> optional = userService.findByAccount(dto.getAccount());
        if (optional.isEmpty()) {
            return ResponseBean.fail("账号不存在或密码错误");
        }
        SysUser user = optional.get();
        if (!user.getStatus() || !StringUtils.hasLength(user.getRoleIds())) {
            return ResponseBean.fail("权限不足");
        }
        List<SysMenu> menus = menuService.findByUserId(user.getId());
        if (CollectionUtils.isEmpty(menus)) {
            return ResponseBean.fail("权限不足");
        }
        UserInfo userInfo = UserInfo.convert(user, menus);
        StpUtil.login(userInfo.getUserId());
        StpUtil.getSession().set(CommonConstant.USER_INFO, userInfo);
        return ResponseBean.ok(StpUtil.getTokenValue());
    }

    /**
     * 获取验证码
     * @author :moran
     * @date :2024/3/21 9:54
     **/
    @GetMapping("/captcha")
    public ResponseBean<CaptChaVO> captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(100, 50, 4);
        try {
            specCaptcha.setFont(0);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }
        specCaptcha.setCharType(6);
        String key = IdUtil.getSnowflakeNextIdStr();
        String code = specCaptcha.text().toLowerCase();
        String base64 = specCaptcha.toBase64();
        redisService.set(key, code, 5*60);
        return ResponseBean.ok(CaptChaVO.convert(key, base64));
    }
    
    /**
     * health
     * @Author :moran
     * @Date : 2024/11/22
     **/
    @GetMapping("/health")
    public ResponseBean<Boolean> health() {
        return ResponseBean.ok(true);
    }
}
