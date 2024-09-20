package com.zl.template.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("abc")
public class TestController {

    @GetMapping("/hello")
//    @PreAuthorize("hasAuthority('test123')")
    @PreAuthorize("@zl.hasPermission('test')")
    public String hello() {
        return "hello";
    }
}
