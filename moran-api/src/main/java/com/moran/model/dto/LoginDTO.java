package com.moran.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * 登录 账号密码
 * @author : moran
 * @date : 2024/3/21 10:09
 */
@Setter
@Getter
public class LoginDTO {
    /**
     * 账号
     */
    @NotBlank(message = "请输入账号")
    private String account;
    /**
     * 密码
     */
    @NotBlank(message = "请输入密码")
    private String password;
    /**
     * 验证码ID
     */
    @NotBlank(message = "请输入验证码")
    private String captchaId;
    /**
     * 验证码
     */
    @NotBlank(message = "请输入验证码")
    private String verifyCode;
}
