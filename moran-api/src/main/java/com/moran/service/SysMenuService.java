package com.moran.service;

import cn.hutool.core.collection.CollUtil;
import com.moran.mapper.SysMenuMapper;
import com.moran.model.SysMenu;
import com.moran.model.dto.system.MenuDTO;
import io.mybatis.mapper.example.ExampleWrapper;
import io.mybatis.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<Integer> completionMenuIds(List<Integer> menuIds) {
        Set<Integer> set = new HashSet<>(menuIds);
        List<SysMenu> menus = findAll();
        Set<Integer> collect = menus.stream().filter(m -> menuIds.contains(m.getId())).map(SysMenu::getParentId).collect(Collectors.toSet());
        set.addAll(collect);
        if (CollUtil.isNotEmpty(collect)) {
            Set<Integer> collect2 = menus.stream().filter(m -> collect.contains(m.getId())).map(SysMenu::getParentId).collect(Collectors.toSet());
            set.addAll(collect2);
        }
        return set;
    }
}
