package com.moran.conf.constant;

/**
 * @author moran
 * 项目通用常量池
 **/
public final class CommonConstant {


    private CommonConstant() {
        throw new RuntimeException("can not init constant class");
    }

    /**
     * 请求头
     */
    public static final String HEADER = "Authorization";
    public static final String START_WITH = "Bearer ";
    public static final Long EXPIRE = 5 * 60L;

    /**
     * 成功字符串
     */
    public static final String SUCCESS = "success";
    /**
     * 用户信息key
     */
    public static final String USER_INFO = "user-info";
    public static final String MENUS = "menus";
}
