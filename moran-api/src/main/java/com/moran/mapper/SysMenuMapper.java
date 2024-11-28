package com.moran.mapper;


import com.moran.model.SysMenu;
import io.mybatis.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sys_menu - 菜单表
 *
 * @author 系统自动生成
 */
@org.apache.ibatis.annotations.Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu, Integer> {

    List<SysMenu> findByUserId(@Param("userId") Integer userId);
}