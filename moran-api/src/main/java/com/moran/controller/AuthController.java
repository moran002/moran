package com.moran.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.moran.conf.bean.ResponseBean;
import com.moran.conf.constant.CommonConstant;
import com.moran.model.SysMenu;
import com.moran.model.SysUser;
import com.moran.model.dto.LoginDTO;
import com.moran.model.vo.UserInfo;
import com.moran.service.SysMenuService;
import com.moran.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 登录管理
 * @author : moran
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final SysUserService userService;
    private final SysMenuService menuService;

    /**
     * 登陆
     * @author :moran
     **/
    @PostMapping("/login")
    public ResponseBean<String> login(@RequestBody LoginDTO dto) {
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
}
