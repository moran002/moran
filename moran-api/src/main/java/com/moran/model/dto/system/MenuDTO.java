package com.moran.model.dto.system;

import com.moran.model.SysMenu;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * 菜单入参
 * @author : moran
 * @date : 2024/3/26 13:37
 */
@Setter
@Getter
public class MenuDTO {
    private Integer id;

    private Integer parentId;

    private String icon;

    private String name;

    private String path;

    private String component;

    private Integer type;

    private Integer sort;

    private Boolean isShow;

    private String api;

    public SysMenu convert() {
        SysMenu menu = new SysMenu();
        BeanUtils.copyProperties(this, menu);
        return menu;
    }
}
