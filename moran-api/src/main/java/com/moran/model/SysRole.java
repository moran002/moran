package com.moran.model;

import io.mybatis.provider.Entity;
import org.apache.ibatis.type.JdbcType;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * sys_role - 角色表
 *
 * @author 系统自动生成
 */
@Getter
@Setter
@Entity.Table(value = "sys_role", remark = "角色表", autoResultMap = true)
public class SysRole {
  @Entity.Column(value = "id", id = true, remark = "角色ID", updatable = false, insertable = false)
  private Integer id;

  @Entity.Column(value = "name", remark = "角色名称")
  private String name;

  @Entity.Column(value = "remark", remark = "备注")
  private String remark;

  @Entity.Column(value = "menu_ids", remark = "菜单ID集合")
  private String menuIds;

  @Entity.Column(value = "create_time", remark = "创建时间", jdbcType = JdbcType.TIMESTAMP)
  private Date createTime;

  @Entity.Column(value = "root", remark = "固定角色", jdbcType = JdbcType.TIMESTAMP)
  private Boolean root;

}
