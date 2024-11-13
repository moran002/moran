package com.moran.model;

import io.mybatis.provider.Entity;
import org.apache.ibatis.type.JdbcType;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * sys_log - 登录日志
 *
 * @author 系统自动生成
 */
@Getter
@Setter
@Entity.Table(value = "sys_log", remark = "登录日志", autoResultMap = true)
public class SysLog {
  @Entity.Column(value = "id", id = true, remark = "ID", updatable = false, insertable = false)
  private Integer id;

  @Entity.Column(value = "account", remark = "登录账号")
  private String account;

  @Entity.Column(value = "nick_name", remark = "名称")
  private String nickName;

  @Entity.Column(value = "ip", remark = "IP地址")
  private String ip;

  @Entity.Column(value = "method_name", remark = "方法名称")
  private String methodName;

  @Entity.Column(value = "class_name", remark = "类名称")
  private String className;

  @Entity.Column(value = "url", remark = "连接")
  private String url;

  @Entity.Column(value = "device_name", remark = "设备名")
  private String deviceName;

  @Entity.Column(value = "optime", remark = "操作时间", jdbcType = JdbcType.TIMESTAMP)
  private Date optime;

  @Entity.Column(value = "consume_time", remark = "时长")
  private Long consumeTime;
}
