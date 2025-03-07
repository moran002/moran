package com.moran.controller;

import com.moran.conf.cp.WxCpConfiguration;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import me.chanjar.weixin.cp.api.WxCpExternalContactService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.external.WxCpMsgTemplate;
import me.chanjar.weixin.cp.bean.external.WxCpMsgTemplateAddResult;
import me.chanjar.weixin.cp.bean.external.msg.Text;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : moran
 */
@RestController
@RequestMapping("/cp")
@AllArgsConstructor
public class CpController {

    @GetMapping("/send")
    @SneakyThrows
    public void send() {
        WxCpService cpService = WxCpConfiguration.getCpService(1000054);
        WxCpExternalContactService externalContactService = cpService.getExternalContactService();
        WxCpMsgTemplate template = new WxCpMsgTemplate();
        template.setChatType("single");
        template.setExternalUserid(List.of("woAJ2GCAAAXtWyujaWJHDDGi0mACAAAA"));
        template.setSender("liuyun");
        template.setAllowSelect(true);
        Text text = new Text();
        text.setContent("hahaha");
        template.setText(text);
        WxCpMsgTemplateAddResult result = externalContactService.addMsgTemplate(template);
        System.out.println(result);
    }
}
