package com.zl.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zl.template.domain.LoginUser;
import com.zl.template.domain.SystemUser;
import com.zl.template.mapper.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 用户验证处理
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private SystemUserMapper systemUserMapper;

    @Override
    public UserDetails loadUserByUsername(String username){
        LambdaQueryWrapper<SystemUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SystemUser::getUserName, username);
        SystemUser systemUser = systemUserMapper.selectOne(wrapper);
        if (Objects.isNull(systemUser)){
            throw new RuntimeException("用户名错误");
        }
        //查询用户权限信息，添加到LoginUser中
        List<String> list = new ArrayList<>(Arrays.asList("test","test1"));
        return new LoginUser(systemUser,list);
    }
}
