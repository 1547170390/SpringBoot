package com.zl.template.service.impl;

import com.zl.template.config.redis.RedisCache;
import com.zl.template.domain.LoginUser;
import com.zl.template.domain.ResponseResult;
import com.zl.template.domain.SystemUser;
import com.zl.template.service.LoginService;
import com.zl.template.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(SystemUser systemUser) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(systemUser.getUserName(), systemUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名密码错误");
        }
        //使用userid生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getSystemUser().getUserId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //authenticate放入redis
        redisCache.setCacheObject("login:"+userId, loginUser);
        //把token响应给前端
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return new ResponseResult(200,"登录成功",map);
    }

    /**
     * 登出接口
     * @return
     */
    @Override
    public ResponseResult logout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getSystemUser().getUserId();
        redisCache.deleteObject("login:"+userId);
        return new ResponseResult(200,"退出成功");
    }
}
