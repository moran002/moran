package com.moran.model.vo.system;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.moran.model.SysUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 用户
 * @author : moran
 * @date : 2024/3/21 17:26
 */
@Setter
@Getter
public class UserVO {
    private Integer id;

    private String account;

    private String nickName;

    private String avatar;

    private String mobile;

    private String email;

    private String remark;

    private String createTime;

    private List<Integer> roleIds;

    private Boolean status;

    private String updateTime;

    private String roleNames;

    public static UserVO convert(SysUser sysUser) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(sysUser, vo);
        if (StringUtils.hasLength(sysUser.getRoleIds())) {
            vo.setRoleIds(JSONUtil.toList(sysUser.getRoleIds(), Integer.class));
            vo.setRoleNames(sysUser.getRoleNames());
        }
        vo.setCreateTime(DateUtil.formatDateTime(sysUser.getCreateTime()));
        vo.setUpdateTime(DateUtil.formatDateTime(sysUser.getUpdateTime()));
        return vo;
    }
}
