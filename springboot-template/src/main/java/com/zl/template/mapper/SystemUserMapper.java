package com.zl.template.mapper;

import com.zl.template.domain.SystemUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author xuzili
* @description 针对表【system_user(用户信息表)】的数据库操作Mapper
* @createDate 2024-09-15 10:12:57
* @Entity generator.domain.SystemUser
*/
public interface SystemUserMapper extends BaseMapper<SystemUser> {
    SystemUser selectByUserId(Long userId);
    SystemUser selectByUsername(String username);
}




