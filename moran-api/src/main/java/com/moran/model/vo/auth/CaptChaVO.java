package com.moran.model.vo.auth;

import lombok.Getter;
import lombok.Setter;

/**
 * 验证码
 * @author : moran
 */
@Getter
@Setter
public class CaptChaVO {
    /**
     * 验证码key
     */
    private String captchaId;
    /**
     * 验证码base64
     */
    private String imgUrl;

    public static CaptChaVO convert(String key, String toBase64) {
        CaptChaVO vo = new CaptChaVO();
        vo.setCaptchaId(key);
        vo.setImgUrl(toBase64);
        return vo;
    }
}
