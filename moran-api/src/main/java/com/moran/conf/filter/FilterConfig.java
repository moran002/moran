package com.moran.conf.filter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 开放url配置
 * @author :moran
 * @date :2023/8/29 15:39
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix = "filter")
public class FilterConfig {
    /**
     * 需要登录
     */
    private List<String> logins;
}
