package com.moran.controller.dictionary;

import cn.dev33.satoken.stp.StpUtil;
import com.moran.conf.bean.ResponseBean;
import com.moran.controller.Controller;
import com.moran.model.SysMenu;
import com.moran.model.SysRole;
import com.moran.model.vo.OptionVO;
import com.moran.model.vo.TreeVO;
import com.moran.model.vo.UserInfo;
import com.moran.model.vo.auth.RouterVO;
import com.moran.service.SysMenuService;
import com.moran.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 字典管理
 * @author : moran
 * @date : 2024/3/8 16:45
 */
@RestController
@RequestMapping("/dictionary")
@AllArgsConstructor
public class DictionaryController extends Controller{
    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;


    /**
     * 获取菜单
     * @author :moran
     * @date :2024/3/26 10:11
     **/
    @GetMapping("/menus")
    public ResponseBean<List<TreeVO>> getMenus() {
        List<SysMenu> menus = sysMenuService.findAll();
        return ResponseBean.ok(menus.stream()
                .filter(m -> m.getType() == 1 && m.getParentId() == null)
                .map(m -> TreeVO.convert(m, menus))
                .collect(Collectors.toList()));
    }

    /**
     * 角色列表
     * @author :moran
     * @date :2024/3/12 10:54
     **/
    @GetMapping("/roles")
    public ResponseBean<List<OptionVO>> roles() {
        List<SysRole> roles = sysRoleService.findAll();
        return ResponseBean.ok(roles.stream().map(r -> OptionVO.convert(r.getId(), r.getName())).collect(Collectors.toList()));
    }

    /**
     * 获取用户菜单
     * @author :moran
     * @date :2024/3/8 16:47
     **/
    @GetMapping("/routers")
    public ResponseBean<List<RouterVO>> routers() {
        UserInfo userInfo = getUserInfo();
        return ResponseBean.ok(userInfo.getMenus()
                .stream()
                .filter(m -> m.getParentId() == null)
                .map(m -> RouterVO.convert(m, userInfo.getMenus()))
                .collect(Collectors.toList()));
    }

    /**
     * 权限
     * @author :moran
     * @date :2024/3/25 14:34
     **/
    @GetMapping("/permissions")
    public ResponseBean<List<String>> permissions() {
        return ResponseBean.ok(getUserInfo().getPermissions());
    }

    /**
     * 登出
     * @author :moran
     * @date :2024/3/8 17:27
     **/
    @PostMapping("/logout")
    public ResponseBean logout() {
        StpUtil.logout();
        return ResponseBean.ok();
    }

}
