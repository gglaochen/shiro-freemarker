package com.example.demo.shirofreemarker.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ChenHanLin 2020/2/25
 */
@Slf4j
@Component
public class MyRealm extends AuthorizingRealm {

    /**
     *  获取授权的信息
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取了用户名
        /*String username = (String)principalCollection.getPrimaryPrincipal();
        User user = iUserService.findByUserName(username);*/

        //定义返回的授权信息
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //角色集合
        Set<String> roleSet = new HashSet<>();
        //权限集合
        Set<String> shiroPermissions = new HashSet<>();

        roleSet.add("admin");
        shiroPermissions.add("admin::item");
        authorizationInfo.addStringPermissions(shiroPermissions);
        authorizationInfo.addRoles(roleSet);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取传入的身份信息封装
        //身份信息(用户名)
        String username = (String) authenticationToken.getPrincipal();
        //凭证信息（密码）
        Object credentials = authenticationToken.getCredentials();
        /*//获取数据库信息
        User user = iUserService.findByUserName(username);
        //身份校验
        if(user==null){
            throw new UnknownAccountException("用户名或密码不正确");
        }
        //密码校验
        if(credentials == null){
            throw new IncorrectCredentialsException("密码错误");
        }
        //判断是否锁定
        if(StringUtils.equals(Stirng.valueOf(user.getLocked()),"1")){
            throw new LockedAccountException("帐号锁定");
        }*/

        String password = new String((char[])credentials);
        log.info("登录操作，用户名: {},密码: {}",username,password);
        //将这些校验信息返回，目的是让subject能获取到
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username,password,getName());

        return info;
    }
}
