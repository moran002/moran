package com.moran.service;

import com.moran.model.dto.system.MenuDTO;
import io.mybatis.mapper.example.ExampleWrapper;
import org.springframework.stereotype.Service;
import io.mybatis.service.AbstractService;
import com.moran.model.SysMenu;
import com.moran.mapper.SysMenuMapper;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * sys_menu - 菜单表
 *
 * @author 系统自动生成
 */
@Service
public class  SysMenuService extends AbstractService<SysMenu, Integer, SysMenuMapper> {

    public List<SysMenu> findByUserId(Integer userId) {
        return baseMapper.findByUserId(userId);
    }

    public List<SysMenu> list(String name) {
        ExampleWrapper<SysMenu, Integer> wrapper = baseMapper.wrapper();
        wrapper.orderByAsc(SysMenu::getSort);
        if (StringUtils.hasLength(name)) {
            wrapper.contains(SysMenu::getName, name);
        }
        return wrapper.list();
    }

    public void updateMenu(MenuDTO dto) {
        SysMenu menu = dto.convert();
        if (menu.getId() == null) {
            saveSelective(menu);
            return;
        }
        updateSelective(menu);
    }
}
