package com.moran.service;

import cn.hutool.json.JSONUtil;
import com.moran.mapper.SysRoleMapper;
import com.moran.model.SysRole;
import com.moran.model.dto.system.RoleDTO;
import io.mybatis.mapper.example.ExampleWrapper;
import io.mybatis.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * sys_role - 角色表
 *
 * @author 系统自动生成
 */
@Service
public class  SysRoleService extends AbstractService<SysRole, Integer, SysRoleMapper> {
    public List<SysRole> list(String roleName, String remark) {
        ExampleWrapper<SysRole, Integer> wrapper = baseMapper.wrapper();
        wrapper.orderByDesc(SysRole::getCreateTime);
        if (StringUtils.hasLength(roleName)) {
            wrapper.contains(SysRole::getName, roleName);
        }
        if (StringUtils.hasLength(remark)) {
            wrapper.contains(SysRole::getRemark, remark);
        }
        return wrapper.list();
    }

    public void updateRole(RoleDTO dto) {
        SysRole role = dto.convert();
        if (dto.getId() == null) {
            saveSelective(role);
            return;
        }
        updateSelective(role);
    }

    public void updateByMenuIds(Integer id, Set<Integer> menuIds) {
        SysRole role = new SysRole();
        role.setId(id);
        role.setMenuIds(JSONUtil.toJsonStr(menuIds));
        updateSelective(role);
    }
}
