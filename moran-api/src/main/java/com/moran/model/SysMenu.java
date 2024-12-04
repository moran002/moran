package com.moran.model;

import io.mybatis.provider.Entity;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * sys_menu - 菜单表
 *
 * @author 系统自动生成
 */
@Getter
@Setter
@Entity.Table(value = "sys_menu", remark = "菜单表", autoResultMap = true)
public class SysMenu {
  @Entity.Column(value = "id", id = true, remark = "菜单ID", updatable = false, insertable = false)
  private Integer id;

  @Entity.Column(value = "parent_id", remark = "上级ID")
  private Integer parentId;

  @Entity.Column(value = "icon", remark = "图标")
  private String icon;

  @Entity.Column(value = "title", remark = "菜单名称")
  private String title;

  @Entity.Column(value = "path", remark = "路由地址")
  private String path;

  @Entity.Column(value = "component", remark = "组件路径")
  private String component;

  @Entity.Column(value = "redirect", remark = "")
  private String redirect;

  @Entity.Column(value = "type", remark = "类型:1:菜单,2:按钮")
  private Integer type;

  @Entity.Column(value = "sort", remark = "排序")
  private Integer sort;

  @Entity.Column(value = "is_show", remark = "是否隐藏")
  private Boolean isShow;

  @Entity.Column(value = "api", remark = "权限标识")
  private String api;

  @Entity.Column(value = "create_time", remark = "创建时间", jdbcType = JdbcType.TIMESTAMP)
  private Date createTime;

}
