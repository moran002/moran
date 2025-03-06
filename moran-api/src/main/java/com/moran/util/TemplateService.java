package com.moran.util;

import cn.hutool.core.io.FileUtil;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author : moran
 */
@Service
public class TemplateService {
    @Resource
    private TemplateEngine templateEngine;


    /**
     * 渲染 Thymeleaf 模板
     *
     * @param templateName 模板名称（不需要后缀）
     * @param data         模板中需要的数据
     * @return 渲染后的 HTML 内容
     */
    public String renderTemplate(String templateName, Object data) {
        // 创建 StringTemplateResolver
        StringTemplateResolver stringTemplateResolver = new StringTemplateResolver();
        stringTemplateResolver.setTemplateMode("HTML"); // 设置模板模式（HTML、TEXT、JAVASCRIPT、CSS、RAW等）
        stringTemplateResolver.setCacheable(false); // 关闭缓存（可选）
        templateEngine.setTemplateResolver(stringTemplateResolver);
        Context context = new Context();
        // 将数据放入上下文
        context.setVariable("data", data);
        return templateEngine.process(downloadFromUrl(templateName), context);
    }

    /**
     * 渲染模板并将结果写入文件
     *
     * @param templateName 模板名称
     * @param data         模板数据
     * @param outputPath   输出文件路径
     */
    @SneakyThrows
    public void renderTemplateToFile(String templateName, Object data, String outputPath) {
        String htmlContent = renderTemplate(templateName, data);
        System.out.println("html start ======>");
        System.out.println(htmlContent);
        System.out.println("======> html end ");
        FileUtil.appendUtf8String(htmlContent, outputPath);
    }

    @SneakyThrows
    public static String downloadFromUrl(String urlString) {
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine).append("\n");
            }
        }
        connection.disconnect();
        return content.toString();
    }

    public static void main(String[] args) {
        String s = downloadFromUrl("https://file.teamauto.sg/24hrs/pdf/index.html");
        System.out.println();
    }
}
