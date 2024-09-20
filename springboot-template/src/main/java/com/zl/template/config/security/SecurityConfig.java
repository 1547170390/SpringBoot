package com.zl.template.config.security;

import com.alibaba.fastjson.JSON;
import com.zl.template.domain.ResponseResult;
import com.zl.template.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 通过将 AuthenticationManager 定义为一个 bean，可以在其他需要进行认证的地方轻松注入它，
     * 实现认证功能的复用。
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandlerImpl  accessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //关闭csrf
                .formLogin(AbstractHttpConfigurer::disable)//取消默认登录页面的使用
                .logout(logout ->{
                    logout.logoutSuccessHandler(new CustomLogoutSuccessHandler()); //自定义登出成功处理
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//禁用session，因为我们已经使用了JWT
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)//将用户授权时用到的JWT校验过滤器添加进SecurityFilterChain中，并放在UsernamePasswordAuthenticationFilter的前面
                .httpBasic(AbstractHttpConfigurer::disable)//禁用httpBasic，因为我们传输数据用的是post，而且请求体是JSON
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/test/**").permitAll() //以/login开头和/test的接口不需要认证
                        .anyRequest().authenticated()); //其余接口都需要认证

        http.exceptionHandling(exception -> {
            exception.accessDeniedHandler(accessDeniedHandler); //授权失败处理
            exception.authenticationEntryPoint(authenticationEntryPoint);//认证失败处理
        });
        http.cors(Customizer.withDefaults());//允许跨域请求
        return http.build();

    }


}
