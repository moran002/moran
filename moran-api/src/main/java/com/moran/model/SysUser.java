package com.moran.model;

import io.mybatis.provider.Entity;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.JdbcType;

import java.util.Date;

/**
 * sys_user - 用户表
 *
 * @author 系统自动生成
 */
@Getter
@Setter
@Entity.Table(value = "sys_user", remark = "用户表", autoResultMap = true)
public class SysUser {
  @Entity.Column(value = "id", id = true, remark = "用户ID", updatable = false, insertable = false)
  private Integer id;

  @Entity.Column(value = "account", remark = "账号")
  private String account;

  @Entity.Column(value = "password", remark = "密码")
  private String password;

  @Entity.Column(value = "salt", remark = "密码盐")
  private String salt;

  @Entity.Column(value = "nick_name", remark = "用户昵称")
  private String nickName;

  @Entity.Column(value = "avatar", remark = "头像")
  private String avatar;

  @Entity.Column(value = "mobile", remark = "手机号")
  private String mobile;

  @Entity.Column(value = "email", remark = "邮箱")
  private String email;

  @Entity.Column(value = "remark", remark = "备注")
  private String remark;

  @Entity.Column(value = "create_time", remark = "创建时间", jdbcType = JdbcType.TIMESTAMP)
  private Date createTime;

  @Entity.Column(value = "role_ids", remark = "角色ID集合")
  private String roleIds;

  @Entity.Column(value = "status", remark = "状态")
  private Boolean status;

  @Entity.Column(value = "update_time", remark = "更新时间", jdbcType = JdbcType.TIMESTAMP)
  private Date updateTime;

  @Entity.Column(value = "root", remark = "主账号", jdbcType = JdbcType.TINYINT)
  private Boolean root;

  @Entity.Transient
  private String roleNames;
}
