package com.zl.template.service;

import com.zl.template.domain.SystemUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
* @author xuzili
* @description 针对表【system_user(用户信息表)】的数据库操作Service
* @createDate 2024-09-15 10:12:57
*/
public interface SystemUserService extends IService<SystemUser> {

    //建立user索引库，并往里面存入数据
    void systemUserToElasticsearch();

    //从es中取出涵哥数据
    String getHanData() throws IOException;
}
