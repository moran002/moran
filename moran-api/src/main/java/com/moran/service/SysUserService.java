package com.moran.service;

import com.moran.conf.exception.ServiceException;
import com.moran.mapper.SysUserMapper;
import com.moran.model.SysUser;
import com.moran.model.dto.system.UserDTO;
import com.moran.util.MD5Util;
import io.mybatis.service.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * sys_user - 用户表
 *
 * @author 系统自动生成
 */
@Service
public class  SysUserService extends AbstractService<SysUser, Integer, SysUserMapper> {

    public Optional<SysUser> findByAccount(String account) {
        return baseMapper.wrapper().eq(SysUser::getAccount, account).one();
    }

    public List<SysUser> list(String account, String nickName) {
        return baseMapper.list(account, nickName);
    }

    public void updateUser(UserDTO dto) {
        SysUser user = dto.convert();
        Optional<SysUser> optional = baseMapper.wrapper().eq(SysUser::getNickName, dto.getNickName()).one();
        if (dto.getId() == null) {
            if (optional.isPresent()) {
                throw new ServiceException("当前姓名已存在");
            }

            user.setSalt(MD5Util.generateSalt());
            user.setPassword(MD5Util.encrypt(dto.getPassword(), user.getSalt()));
            saveSelective(user);
            return;
        }
        if (optional.isPresent() && !Objects.equals(optional.get().getId(), dto.getId())) {
            throw new ServiceException("当前姓名已存在");
        }
        user.setPassword(null);
        user.setSalt(null);
        updateSelective(user);
    }

    public void updateStatus(Integer id, Boolean status) {
        SysUser user = findById(id);
        if (user == null) {
            throw new ServiceException("当前用户不存在");
        }
        user.setStatus(status);
        updateSelective(user);
    }

    public void password(Integer id, String password) {
        SysUser user = findById(id);
        if (user == null) {
            throw new ServiceException("请选择用户");
        }
        user.setPassword(MD5Util.encrypt(password, user.getSalt()));
        updateSelective(user);
    }
}
