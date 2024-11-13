package com.moran.controller.system;

import com.moran.conf.bean.ResponseBean;
import com.moran.controller.Controller;
import com.moran.model.SysUser;
import com.moran.model.dto.system.UserDTO;
import com.moran.model.vo.system.UserVO;
import com.moran.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户管理
 * @author : moran
 * @date : 2024/3/22 16:48
 */
@RestController
@RequestMapping("/system/user")
@AllArgsConstructor
public class UserController extends Controller {
    private final SysUserService userService;

    /**
     * 修改密码
     * @author :moran
     * @date :2024/3/25 17:03
     **/
    @PostMapping("/password")
    public ResponseBean<Object> password(@RequestBody UserDTO dto) {
        userService.password(dto.getId(), dto.getPassword());
        return ResponseBean.ok();
    }

    /**
     * 状态
     * @author :moran
     * @date :2024/3/25 15:33
     **/
    @PostMapping("/status")
    public ResponseBean<Object> status(@RequestBody UserDTO dto) {
        userService.updateStatus(dto.getId(), dto.getStatus());
        return ResponseBean.ok();
    }

    /**
     * 移除
     * @author :moran
     * @date :2024/3/25 13:08
     **/
    @PostMapping("/del")
    public ResponseBean<Object> delUser(@RequestBody UserDTO dto) {
        SysUser user = userService.findById(dto.getId());
        if (user == null || user.getRoot()) {
            return ResponseBean.fail("当前账号无法删除");
        }
        userService.deleteById(dto.getId());
        return ResponseBean.ok();
    }

    /**
     * 编辑
     * @author :moran
     * @date :2024/3/25 13:08
     **/
    @PostMapping("/update")
    public ResponseBean updateUser(@RequestBody UserDTO dto) {
        userService.updateUser(dto);
        return ResponseBean.ok();
    }

    /**
     * 列表
     * @author :moran
     * @date :2024/3/22 16:48
     **/
    @GetMapping("/list")
    public ResponseBean<List<UserVO>> list(String account, String nickName) {
        startPage();
        List<SysUser> list = userService.list(account, nickName);
        return ResponseBean.ok(list, list.stream().map(UserVO::convert).collect(Collectors.toList()));
    }
}
