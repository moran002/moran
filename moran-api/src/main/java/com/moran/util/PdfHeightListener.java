package com.moran.util;

import cn.hutool.json.JSONUtil;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.ImageRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;

import java.util.Set;

/**
 * @author : moran
 */
public class PdfHeightListener implements IEventListener {
    private float maxY = 0;
    private float minY = 10000;
    @Override
    public void eventOccurred(IEventData data, EventType type) {
        if (data instanceof TextRenderInfo) {
            // 处理文本内容
            TextRenderInfo textData = (TextRenderInfo) data;
            System.out.println("textData" + JSONUtil.toJsonStr(textData));
            float textY = textData.getBaseline().getStartPoint().get(Vector.I2);
            System.out.println("textY: " + textY);
            if (textY > maxY) {
                maxY = textY;
            }
            if (textY < minY) {
                minY = textY;
            }
        } else if (data instanceof ImageRenderInfo) {
            // 处理图像内容
            ImageRenderInfo imageData = (ImageRenderInfo) data;
            // 获取图像的变换矩阵
            Matrix ctm = imageData.getImageCtm();
            // 提取图像的 Y 坐标
            float imageY = ctm.get(Matrix.I32);
            System.out.println("imageY: " + imageY);
            if (imageY > maxY) {
                maxY = imageY;
            }
            if (imageY < minY) {
                minY = imageY;
            }
        }
    }

    @Override
    public Set<EventType> getSupportedEvents() {
        // 返回 null 表示支持所有事件
        return null;
    }

    public float getHeight() {
        return maxY - minY;
    }
}
