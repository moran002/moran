package com.moran.controller.system;

import com.moran.conf.bean.ResponseBean;
import com.moran.controller.Controller;
import com.moran.model.SysRole;
import com.moran.model.dto.system.RoleDTO;
import com.moran.model.vo.system.RoleVO;
import com.moran.service.SysMenuService;
import com.moran.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色管理
 * @author : moran
 */
@RestController
@RequestMapping("/system/role")
@AllArgsConstructor
public class RoleController extends Controller {
    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;

    /**
     * 分配菜单
     * @author :moran
     **/
    @PostMapping("/menus")
    public ResponseBean<Object> menus(@RequestBody RoleDTO dto) {
        Set<Integer> menuIds = sysMenuService.completionMenuIds(dto.getMenuIds());
        sysRoleService.updateByMenuIds(dto.getId(), menuIds);
        return ResponseBean.ok();
    }

    /**
     * 删除
     * @author :moran
     **/
    @PostMapping("/del")
    public ResponseBean<Object> del(@RequestBody RoleDTO dto) {
        SysRole role = sysRoleService.findById(dto.getId());
        if (role == null || role.getRoot()) {
            return ResponseBean.fail("当前角色为指定角色,无法删除");
        }
        sysRoleService.deleteById(dto.getId());
        return ResponseBean.ok();
    }

    /**
     * 编辑
     * @author :moran
     **/
    @PostMapping("/update")
    public ResponseBean<Object> update(@RequestBody RoleDTO dto) {
        sysRoleService.updateRole(dto);
        return ResponseBean.ok();
    }

    /**
     * 列表
     * @author :moran
     **/
    @GetMapping("/list")
    public ResponseBean<List<RoleVO>> list(String roleName, String remark) {
        startPage();
        List<SysRole> list = sysRoleService.list(roleName, remark);
        return ResponseBean.ok(list, list.stream().map(RoleVO::convert).collect(Collectors.toList()));
    }
}
