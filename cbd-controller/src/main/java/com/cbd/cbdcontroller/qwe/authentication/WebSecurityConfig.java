package com.cbd.cbdcontroller.qwe.authentication;


import com.cbd.cbdcontroller.qwe.authorization.VAccessDecisionManager;
import com.cbd.cbdcontroller.qwe.authorization.VFilterInvocationSecurityMetadataSource;
import com.cbd.cbdcontroller.qwe.authorization.VFilterSecurityInterceptor;
import com.cbd.cbdcontroller.qwe.handler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

/**
 * Security 主配置文件
 *
 * @author shy_black
 */
@Configuration
@EnableWebSecurity //开启Spring Security的功能
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启注解控制权限
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //登录成功处理逻辑
    @Autowired
    CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

    //登录失败处理逻辑
    @Autowired
    CustomizeAuthenticationFailureHandler authenticationFailureHandler;

    ///权限拒绝处理逻辑
    @Autowired
    CustomizeAccessDeniedHandler accessDeniedHandler;

    //匿名用户访问无权限资源时的异常
    @Autowired
    CustomizeAuthenticationEntryPoint authenticationEntryPoint;


    //登出成功处理逻辑
    @Autowired
    CustomizeLogoutSuccessHandler logoutSuccessHandler;

    //访问决策管理器
    @Autowired
    VAccessDecisionManager accessDecisionManager;

    //实现权限拦截
    @Autowired
    VFilterInvocationSecurityMetadataSource securityMetadataSource;

    @Autowired
    private VFilterSecurityInterceptor securityInterceptor;

    @Autowired
    CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    VAuthenticationProvider vAuthenticationProvider;

    @Bean
    public UserDetailsService userDetailsService() {
        //获取用户账号密码及权限信息
        return new VUserDetailsService();
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        // 设置默认的加密方式（强hash方式加密）
//        return new BCryptPasswordEncoder();
//
//    }

    @Bean
    public JWTPasswordEncoder passwordEncoder() {
        // 设置默认的加密方式（强hash方式加密）
        //return new BCryptPasswordEncoder();
        return new JWTPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
                .and()
                .authenticationProvider(vAuthenticationProvider);
    }

    /**
     * 开启注解控制权限
     * 见Controller 中 @PreAuthorize("hasAuthority('XXX')")
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    /**
     * 安全策略配置
     */

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 加入自定义的安全认证
//        auth.userDetailsService()
//                .passwordEncoder(this.passwordEncoder())
//                .and()
//                //添加自定义的认证管理类
//                .authenticationProvider(smsAuthenticationProvider())
//                .authenticationProvider(authenticationProvider());
//    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors().and().csrf().disable();


        httpSecurity.authorizeRequests().
                antMatchers("/role/showRoleList ").hasAuthority("admin").
                antMatchers("/**").fullyAuthenticated().
                        withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setAccessDecisionManager(accessDecisionManager);//决策管理器
                        o.setSecurityMetadataSource(securityMetadataSource);//安全元数据源
                        return o;
                    }
                }).
                //登出
                        and().logout().
                permitAll().//允许所有用户
                logoutSuccessHandler(logoutSuccessHandler).//登出成功处理逻辑
                deleteCookies("JSESSIONID").//登出之后删除cookie
                //登入
                        and().formLogin().
                permitAll().//允许所有用户
                successHandler(authenticationSuccessHandler).//登录成功处理逻辑
                failureHandler(authenticationFailureHandler).//登录失败处理逻辑
                //异常处理(权限拒绝、登录失效等)
                        and().exceptionHandling().
                accessDeniedHandler(accessDeniedHandler).//权限拒绝处理逻辑
                authenticationEntryPoint(authenticationEntryPoint).//匿名用户访问无权限资源时的异常处理
                //会话管理
                        and().sessionManagement().
                maximumSessions(1).//同一账号同时登录最大用户数
                expiredSessionStrategy(sessionInformationExpiredStrategy);//会话失效(账号被挤下线)处理逻辑
        httpSecurity.addFilterBefore(securityInterceptor, FilterSecurityInterceptor.class);



//        httpSecurity
//                .authorizeRequests()
//                // 对于网站部分资源需要指定鉴权
//                //.antMatchers("/admin/**").hasRole("ADMIN")
//                // 除上面外的所有请求全部需要鉴权认证
//                .anyRequest().authenticated()
//                //异常处理(权限拒绝、登录失效等)
//                .and().exceptionHandling().
//                //匿名用户访问无权限资源时的异常处理
//                        authenticationEntryPoint(authenticationEntryPoint)
//                //登入
//                .and().formLogin().
//                permitAll().//允许所有用户
//                successHandler(authenticationSuccessHandler).//登录成功处理逻辑
//                //登录失败处理逻辑
//                        failureHandler(authenticationFailureHandler).
//                //登出
//                        and().logout().
//                permitAll().//允许所有用户
//                logoutSuccessHandler(logoutSuccessHandler).//登出成功处理逻辑
//                //登出之后删除cookie
//                        deleteCookies("JSESSIONID")
//
//
//                // 定义当需要用户登录时候，转到的登录页面
//                .and()
//                .formLogin().loginPage("/login").defaultSuccessUrl("/index").permitAll().and()
//                // 定义登出操作
//                .logout().logoutSuccessUrl("/login?logout").permitAll().and()
//                .csrf().disable()
//        ;
//        // 禁用缓存
//        httpSecurity.headers().cacheControl();
    }

    /**
     * 开启注解控制权限
     * 见Controller 中 @PreAuthorize("hasAuthority('XXX')")
     */
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
}


//        httpSecurity.authorizeRequests()
//
//                .anyRequest().permitAll().and().logout().permitAll();