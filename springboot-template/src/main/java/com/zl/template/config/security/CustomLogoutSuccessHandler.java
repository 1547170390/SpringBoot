package com.zl.template.config.security;

import com.alibaba.fastjson.JSON;
import com.zl.template.domain.ResponseResult;
import com.zl.template.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 登出成功处理器
 * SpringSecurity提供了/logout端点来处理登出请求，可以通过发送请求到这个url来进行登出
 * 这样走的就是自定义的登出逻辑。
 * 也可以自己写一个接口做登出逻辑。
 */
@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        // 登出成功后的处理逻辑
        ResponseResult result = new ResponseResult(HttpServletResponse.SC_OK, "登出成功～喵");
        String json = JSON.toJSONString(result);
        WebUtils.renderString(response,json);
    }
}
