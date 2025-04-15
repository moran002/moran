package com.moran;

import cn.hutool.json.JSONUtil;
import com.moran.conf.cp.WxCpConfiguration;
import lombok.SneakyThrows;
import me.chanjar.weixin.cp.api.WxCpExternalContactService;
import me.chanjar.weixin.cp.api.WxCpService;
import me.chanjar.weixin.cp.bean.external.WxCpMsgTemplate;
import me.chanjar.weixin.cp.bean.external.WxCpMsgTemplateAddResult;
import me.chanjar.weixin.cp.bean.external.WxCpUserExternalGroupChatList;
import me.chanjar.weixin.cp.bean.external.contact.WxCpExternalContactBatchInfo;
import me.chanjar.weixin.cp.bean.external.msg.Text;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author : moran
 */
@SpringBootTest
public class CpTest {
    @Test
    @SneakyThrows
    void test() {
        WxCpService cpService = WxCpConfiguration.getCpService(1000054);
        WxCpExternalContactService externalContactService = cpService.getExternalContactService();
        WxCpUserExternalGroupChatList groupChatList = externalContactService.listGroupChat(null, null, 0, null);
        System.out.println(JSONUtil.toJsonStr(groupChatList));
        //{"groupChatList":[{"chatId":"wraTFjQQAA_UwjjeTAX5PzXX4gOzN9Tg","status":0}],"errcode":0,"errmsg":"ok"}
    }

    @Test
    @SneakyThrows
    void test2() {
        WxCpService cpService = WxCpConfiguration.getCpService(1000054);
        WxCpExternalContactService externalContactService = cpService.getExternalContactService();
        WxCpExternalContactBatchInfo list = externalContactService.getContactDetailBatch(new String[]{"liuyun"}, null, null);
        System.out.println(JSONUtil.toJsonStr(list));
        //{"externalContactList":[{"externalContact":{"externalUserId":"wmaTFjQQAA3XdL2wl9F3-xTTVATNcJbQ","name":"起个名字叫什么","avatar":"http://wx.qlogo.cn/mmhead/7SPO0mRJt6DnqjIlWyMgk1IqvbacKAY5khQfKGoYQIicEjek6JjTjuLFPFVbeK2Nsg0yG0sJ4P9I/0","type":1,"gender":1},"followInfo":{"userId":"liuyun","remark":"起个名字叫什么","description":"","createTime":1741310190,"remarkMobiles":[],"tagIds":[],"addWay":"1","operatorUserId":"wmaTFjQQAA3XdL2wl9F3-xTTVATNcJbQ"}},{"externalContact":{"externalUserId":"woaTFjQQAA8dHeMNrGOm43hCclAnB35w","name":"刘云","avatar":"https://wework.qpic.cn/wwpic3az/611613_rlHQsW8pShade9b_1725323228/0","corpName":"芒果云计算","corpFullName":"芒果云计算（大连）有限公司","type":2,"gender":1},"followInfo":{"userId":"liuyun","remark":"芒果云客服","description":"","createTime":1723511916,"remarkMobiles":[],"tagIds":[],"addWay":"8","operatorUserId":"liuyun"}},{"externalContact":{"externalUserId":"woaTFjQQAAb4i7F9cwwGonxJ9wXrY2Cw","name":"刘云","avatar":"https://wework.qpic.cn/wwpic3az/611613_rlHQsW8pShade9b_1725323228/0","corpName":"凯群科技","corpFullName":"凯群科技（北京）有限公司","type":2,"gender":1,"externalProfile":{"externalAttrs":[{"type":1,"name":"动态","web":{"title":" ","url":"http://ww24a4ea0124805cdf.wemoment.wshmi.com/H5/moment?user_id=870164&corpid=ww24a4ea0124805cdf"}}]}},"followInfo":{"userId":"liuyun","remark":"WORK云办公平台客服","description":"","createTime":1720063333,"remarkMobiles":[],"tagIds":[],"addWay":"8","operatorUserId":"liuyun"}}],"nextCursor":"","errcode":0,"errmsg":"ok"}
    }

    @Test
    @SneakyThrows
    void test3() {
        WxCpService cpService = WxCpConfiguration.getCpService(1000054);
        WxCpExternalContactService externalContactService = cpService.getExternalContactService();
        WxCpMsgTemplate template = new WxCpMsgTemplate();
        template.setChatType("single");
        template.setExternalUserid(List.of("wmaTFjQQAA3XdL2wl9F3-xTTVATNcJbQ"));
        template.setSender("liuyun");
        template.setAllowSelect(true);
        Text text = new Text();
        text.setContent("hahaha");
        template.setText(text);
        WxCpMsgTemplateAddResult result = externalContactService.addMsgTemplate(template);
        System.out.println(result);
    }

    @Test
    @SneakyThrows
    void test4() {
        WxCpService cpService = WxCpConfiguration.getCpService(1000022);
        String accessToken = cpService.getAccessToken();
        System.out.println(accessToken);
    }
}
