package com.zl.template.controller;

import com.zl.template.domain.ResponseResult;
import com.zl.template.domain.SystemUser;
import com.zl.template.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody SystemUser systemUser) {
        return loginService.login(systemUser);
    }
    @GetMapping("/logout")
    public ResponseResult logout() {
        return loginService.logout();
    }
}
