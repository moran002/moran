package com.moran.controller.system;

import com.moran.conf.bean.ResponseBean;
import com.moran.controller.Controller;
import com.moran.model.SysLog;
import com.moran.model.vo.system.LogVO;
import com.moran.service.SysLogService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : moran
 * @date : 2024/3/27 17:43
 */
@RestController
@RequestMapping("/system/log")
@AllArgsConstructor
public class LogController extends Controller {
    private final SysLogService sysLogService;

    /**
     * 列表
     * @author :moran
     * @date :2024/3/27 17:43
     **/
    @GetMapping("/list")
    public ResponseBean<List<LogVO>> list(String account, String nickName, String startTime, String endTime) {
        startPage();
        List<SysLog> list = sysLogService.list(account, nickName, startTime, endTime);
        return ResponseBean.ok(list, list.stream().map(LogVO::convert).collect(Collectors.toList()));
    }
}
