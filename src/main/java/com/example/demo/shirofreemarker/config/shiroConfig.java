package com.example.demo.shirofreemarker.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ChenHanLin 2020/2/25
 */
@Configuration
public class shiroConfig {
    @Bean(name = "realm")
    public Realm getRealm(){
        return new MyRealm();
    }

    @Bean(name = "shiroCacheManager")
    @ConditionalOnMissingBean
    public CacheManager cacheManager(){
        return new MemoryConstrainedCacheManager();
    }

    /**
     * 安全管理器
     */
    @Bean(name="securityManager")
    public DefaultSecurityManager getSecurityManager(){
        return new DefaultWebSecurityManager();
    }

    /**
     *  web环境的过滤器工厂
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactory(@Qualifier("securityManager") DefaultSecurityManager securityManager,
                                                        @Qualifier("realm") Realm realm){
        securityManager.setRealm(realm);
        // 创建过滤器工厂
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 登录信息不存在的时候（跳转的路径）
        shiroFilterFactoryBean.setLoginUrl("/login");

        // 登录成功之后的跳转页面
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");

        // 身份校验通过了，但是呢，没有权限的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");

        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 那些不需要拦截（静态资源）
        filterChainDefinitionMap.put("/assets/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");

        // 获取相应的权限（校验什么权限）
        /*List<Permission> list = iPermissionService.findAll();
        for (Permission resource : list) {
            // permission 权限 perms[]
            filterChainDefinitionMap.put(resource.getSourceUrl(), "perms[" + resource.getSourceKey() + "]");
        }*/
        filterChainDefinitionMap.put("/admin/index","perms[admin::item]");

        // 需要校验的路径
        filterChainDefinitionMap.put("/admin/**", "authc");

        return shiroFilterFactoryBean;
    }
}
