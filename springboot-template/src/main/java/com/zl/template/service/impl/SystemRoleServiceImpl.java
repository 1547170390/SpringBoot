package com.zl.template.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zl.template.domain.SystemRole;
import com.zl.template.service.SystemRoleService;
import com.zl.template.mapper.SystemRoleMapper;
import org.springframework.stereotype.Service;

/**
* @author xuzili
* @description 针对表【system_role(角色信息表)】的数据库操作Service实现
* @createDate 2024-10-30 11:26:44
*/
@Service
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRole>
    implements SystemRoleService{

}




