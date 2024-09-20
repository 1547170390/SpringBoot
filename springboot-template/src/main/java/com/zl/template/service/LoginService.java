package com.zl.template.service;

import com.zl.template.domain.ResponseResult;
import com.zl.template.domain.SystemUser;

public interface LoginService {
    ResponseResult login(SystemUser systemUser);
    ResponseResult logout();
}
