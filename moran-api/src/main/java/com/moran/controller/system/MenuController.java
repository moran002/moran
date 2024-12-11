package com.moran.controller.system;

import com.moran.conf.bean.ResponseBean;
import com.moran.controller.Controller;
import com.moran.model.SysMenu;
import com.moran.model.dto.system.MenuDTO;
import com.moran.model.vo.system.MenuVO;
import com.moran.service.SysMenuService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单管理
 * @author : moran
 */
@RestController
@RequestMapping("/system/menu")
@AllArgsConstructor
public class MenuController extends Controller {
    private final SysMenuService sysMenuService;

    /**
     * 删除
     * @author :moran
     **/
    @PostMapping("/del")
    public ResponseBean<Object> delById(@RequestBody MenuDTO dto) {
        sysMenuService.deleteById(dto.getId());
        return ResponseBean.ok();
    }

    /**
     * 编辑
     * @author :moran
     **/
    @PostMapping("/update")
    public ResponseBean<Object> update(@RequestBody MenuDTO dto) {
        sysMenuService.updateMenu(dto);
        return ResponseBean.ok();
    }

    /**
     * 列表
     * @author :moran
     **/
    @GetMapping("/list")
    public ResponseBean<List<MenuVO>> list(String name) {
        List<SysMenu> list = sysMenuService.list(name);
        return ResponseBean.ok(list.stream()
                .filter(m -> m.getType() == 1 && m.getParentId() == null)
                .map(m -> MenuVO.convert(m, list))
                .collect(Collectors.toList()));
    }
}
