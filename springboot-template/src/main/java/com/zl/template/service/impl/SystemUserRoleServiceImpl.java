package com.zl.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.template.domain.SystemUserRole;
import com.zl.template.mapper.SystemUserRoleMapper;
import com.zl.template.service.SystemUserRoleService;
import org.springframework.stereotype.Service;

/**
* @author xuzili
* @description 针对表【system_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2024-10-30 11:24:39
*/
@Service
public class SystemUserRoleServiceImpl extends ServiceImpl<SystemUserRoleMapper, SystemUserRole>
    implements SystemUserRoleService {

}




