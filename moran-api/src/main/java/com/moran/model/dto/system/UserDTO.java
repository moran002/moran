package com.moran.model.dto.system;

import cn.hutool.json.JSONUtil;
import com.moran.model.SysUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户入参
 * @author : moran
 */
@Setter
@Getter
public class UserDTO {
    private Integer id;
    private String account;
    private String password;
    private String nickName;
    private String avatar;
    private String mobile;
    private String email;
    private Boolean status;
    private List<Integer> roleIds;

    public SysUser convert() {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(this, user);
        if (!CollectionUtils.isEmpty(roleIds)) {
            user.setRoleIds(JSONUtil.toJsonStr(roleIds));
        }
        return user;
    }
}
