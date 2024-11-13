package com.moran.model.vo;

import com.moran.model.SysMenu;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 树
 * @author : moran
 * @date : 2024/3/26 10:12
 */
@Getter
@Setter
public class TreeVO {

    /**
     * 值(主键)
     */
    private Integer id;
    /**
     * 标签值(名称)
     */
    private String title;

    private String icon;

    private List<TreeVO> children;

    public static TreeVO convert(SysMenu m, List<SysMenu> menus) {
        TreeVO vo = new TreeVO();
        vo.setTitle(m.getName());
        vo.setId(m.getId());
        vo.setIcon(m.getIcon());
        List<TreeVO> children = findChildren(m.getId(), menus);
        if (!CollectionUtils.isEmpty(children)) {
            vo.setChildren(children);
        }
        return vo;
    }

    private static List<TreeVO> findChildren(Integer parentId, List<SysMenu> menus) {
        return menus.stream()
                .filter(m -> parentId.equals(m.getParentId()))
                .map(m -> convert(m, menus))
                .collect(Collectors.toList());
    }
}
