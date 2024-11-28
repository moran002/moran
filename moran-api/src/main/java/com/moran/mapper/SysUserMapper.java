package com.moran.mapper;

import com.moran.model.SysUser;
import io.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys_user - 用户表
 *
 * @author 系统自动生成
 */
@org.apache.ibatis.annotations.Mapper
public interface SysUserMapper extends BaseMapper<SysUser, Integer> {

    List<SysUser> list(@Param("account") String account, @Param("nickName") String nickName);
}