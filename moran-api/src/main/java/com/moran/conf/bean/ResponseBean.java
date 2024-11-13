package com.moran.conf.bean;

import com.github.pagehelper.Page;
import com.moran.conf.constant.CodeConstant;
import com.moran.conf.constant.CommonConstant;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : moran
 * @date : 2024/6/5 14:18
 */
@Setter
@Getter
public class ResponseBean<T> {
    /**
     * 码值
     */
    private Integer code;
    /**
     * 消息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;
    /**
     * 总数
     */
    private String total;

    public static <T> ResponseBean<T> ok() {
        return createResult(CodeConstant.SUCCESS, CommonConstant.SUCCESS, null);
    }

    public static <T> ResponseBean<T> ok(T data) {
        ResponseBean<T> result = createResult(CodeConstant.SUCCESS, CommonConstant.SUCCESS, data);
        if (data instanceof Page) {
            result.setTotal(String.valueOf(new PageData((Page) data).getTotal()));
        }
        return result;
    }

    public static <T> ResponseBean<T> ok(Object pageData, T data) {
        ResponseBean<T> result = createResult(CodeConstant.SUCCESS, CommonConstant.SUCCESS, data);
        if (pageData instanceof Page) {
            result.setTotal(String.valueOf(new PageData((Page) pageData).getTotal()));
        }
        result.setData(data);
        return result;
    }

    public static <T> ResponseBean<T> fail(String msg) {
        return createResult(CodeConstant.ERROR, msg, null);
    }

    public static <T> ResponseBean<T> createResult(int code, String msg, T data) {
        ResponseBean<T> result = new ResponseBean<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * com.github.pagehelper.Page 的代理类
     */
    static class PageData {
        private Page page;

        PageData(Page page) {
            if (page == null) {
                throw new RuntimeException("page can not be null");
            }
            this.page = page;
        }

        /**
         * 总数
         */
        public long getTotal() {
            return page.getTotal();
        }
    }
}
