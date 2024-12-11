package com.moran.model.vo.system;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.moran.model.SysRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 角色
 * @author : moran
 */
@Setter
@Getter
public class RoleVO {

    private Integer id;
    private String name;
    private String remark;
    private List<Integer> menuIds;
    private String createTime;

    public static RoleVO convert(SysRole sysRole) {
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(sysRole, vo);
        vo.setCreateTime(DateUtil.formatDateTime(sysRole.getCreateTime()));
        if (StringUtils.hasLength(sysRole.getMenuIds())) {
            vo.setMenuIds(JSONUtil.toList(sysRole.getMenuIds(), Integer.class));
        }
        return vo;
    }
}
