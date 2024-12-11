package com.moran.model.vo.system;

import cn.hutool.core.date.DateUtil;
import com.moran.model.SysMenu;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单
 * @author : moran
 */
@Setter
@Getter
public class MenuVO {
    private Integer id;

    private Integer parentId;

    private String icon;

    private String title;

    private String path;

    private String component;

    private String redirect;

    private Integer type;

    private Integer sort;

    private Boolean isShow;

    private String api;

    private String createTime;

    private List<MenuVO> children;

    public static MenuVO convert(SysMenu m, List<SysMenu> list) {
        MenuVO vo = new MenuVO();
        BeanUtils.copyProperties(m, vo);
        vo.setCreateTime(DateUtil.formatDateTime(m.getCreateTime()));
        List<MenuVO> children = findChildren(m.getId(), list);
        if (!CollectionUtils.isEmpty(children)) {
            vo.setChildren(children);
        }
        return vo;
    }

    private static List<MenuVO> findChildren(Integer parentId, List<SysMenu> list) {
        return list.stream().filter(m -> parentId.equals(m.getParentId()))
                .map(m -> MenuVO.convert(m, list))
                .collect(Collectors.toList());
    }
}
