package com.moran.model.vo;

import cn.hutool.core.util.StrUtil;
import com.moran.model.SysMenu;
import com.moran.model.SysUser;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息
 * @author : moran
 * @date : 2023/3/7 10:07
 */
@Setter
@Getter
public class UserInfo {

    /**
     * 用户ID
     */
    private Integer userId;
    private SysUser user;
    private List<SysMenu> menus;
    /**
     * api
     */
    private List<String> permissions;

    public static UserInfo convert(SysUser user, List<SysMenu> menus) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setUser(user);
        userInfo.setMenus(menus);
        userInfo.setPermissions(menus.stream().map(SysMenu::getApi).filter(StrUtil::isNotBlank).collect(Collectors.toList()));
        return userInfo;
    }
}
