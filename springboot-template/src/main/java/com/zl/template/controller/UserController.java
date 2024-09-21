package com.zl.template.controller;

import com.zl.template.domain.SystemUser;
import com.zl.template.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    SystemUserService systemUserService;

    @GetMapping
    public List<SystemUser> getUsers() {
        List<SystemUser> list = systemUserService.list();
        return list;
    }
    /**
     * 创建用户的es索引
     */

}
