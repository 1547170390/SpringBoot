package com.zl.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.template.service.SystemUserService;
import com.zl.template.domain.SystemUser;
import com.zl.template.mapper.SystemUserMapper;
import org.springframework.stereotype.Service;

/**
* @author xuzili
* @description 针对表【system_user(用户信息表)】的数据库操作Service实现
* @createDate 2024-09-15 10:12:57
*/
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
    implements SystemUserService {

}




