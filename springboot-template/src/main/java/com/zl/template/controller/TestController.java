package com.zl.template.controller;

import com.zl.template.domain.SystemUser;
import com.zl.template.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    SystemUserService systemUserService;

    @GetMapping("/hello")
//    @PreAuthorize("hasAuthority('test123')")
//    @PreAuthorize("@zl.hasPermission('test')")
    public String hello() {
        return "hello";
    }
    /**
     * 测试一下用户的索引试试
     */
    @GetMapping("/esIndex")
    public String esIndexTest() {
        systemUserService.systemUserToElasticsearch();
        return "esIndex建立成功";
    }
    /**
     * 从es中取出涵哥的数据
     */
    @GetMapping("/getHan")
    public String esDataTest() throws IOException {
        String hanData = systemUserService.getHanData();
        return hanData;
    }
    /**
     * 多表连接查询
     */
    @GetMapping("/getUser/{userId}")
    public SystemUser getUser(@PathVariable("userId") Long userId) {
        SystemUser systemUser = systemUserService.getSystemUser(userId);
        return systemUser;
    }
}
