package com.moran.model.dto.system;

import cn.hutool.json.JSONUtil;
import com.moran.model.SysRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 角色入参
 * @author : moran
 */
@Setter
@Getter
public class RoleDTO {
    private Integer id;
    private String name;
    private String remark;
    private List<Integer> menuIds;

    public SysRole convert() {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(this, role);
        if (!CollectionUtils.isEmpty(menuIds)) {
            role.setMenuIds(JSONUtil.toJsonStr(menuIds));
        }
        return role;
    }
}
