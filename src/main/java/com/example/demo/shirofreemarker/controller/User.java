package com.example.demo.shirofreemarker.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author ChenHanLin 2020/2/25
 */
@Controller
public class User {

    @GetMapping("/admin/index")
    public String index(){
        return "admin/index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        ModelMap model, HttpSession session) {
        try {
            // 是不是要创建一个subject对象
            Subject subject = SecurityUtils.getSubject();

            // 封装用户信息和密码
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);

            // 身份校验（登录）
            subject.login(token);

            return "/admin/index";
        }catch (Exception e){
            model.put("message", e.getMessage());
        }
        return "login";
    }
}
