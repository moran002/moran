package com.moran.model.vo.auth;

import com.moran.model.SysMenu;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 路由
 * @author : moran
 * @date : 2024/3/8 17:17
 */
@Setter
@Getter
public class RouterVO {
    private String id;
    private String icon;
    private String title;
    private String path;
    private String component;
    private String redirect;
    private Boolean isShow;
    private List<RouterVO> children;

    public static RouterVO convert(SysMenu menu, List<SysMenu> menus) {
        RouterVO vo = new RouterVO();
        BeanUtils.copyProperties(menu, vo);
        vo.setTitle(menu.getName());
        vo.setId(menu.getPath());
        List<RouterVO> children = findChildren(menu.getId(), menus);
        if (!CollectionUtils.isEmpty(children)) {
            vo.setChildren(children);
        }
        return vo;
    }

    private static List<RouterVO> findChildren(Integer parentId, List<SysMenu> menus) {
        return menus.stream().filter(m -> m.getType() == 1 && parentId.equals(m.getParentId()))
                .map(m -> RouterVO.convert(m, menus)).collect(Collectors.toList());
    }

}
