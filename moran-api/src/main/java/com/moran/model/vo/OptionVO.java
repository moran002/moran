package com.moran.model.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * 键值对
 * @author : moran
 * @date : 2024/3/12 10:56
 */
@Setter
@Getter
public class OptionVO {
    /**
     * 值(主键)
     */
    private Integer value;
    /**
     * 标签值(名称)
     */
    private String label;

    public static OptionVO convert(Integer value, String label) {
        OptionVO vo = new OptionVO();
        vo.setValue(value);
        vo.setLabel(label);
        return vo;
    }
}
