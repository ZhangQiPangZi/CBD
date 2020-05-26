//package com.black.lei.security.filter;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//import org.springframework.web.filter.CharacterEncodingFilter;
//
///**
// * @author shy_black
// * @date 2020/5/23 16:09
// * @Description:
// */
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled =true) // 启用授权注解
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
//
//    /** 获取数据库中信息存到User对象中 */
//    @Bean
//    public UserDetailsService userService(){ //注册userService 的bean
//        return new UserServiceImpl();
//    }
//    /**加密密码*/
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    /**MD5加密密码*/
//    @Bean
//    PasswordEncoder md5PasswordEncoder() {
//        return new MD5PasswordEncoder();
//    }
//
//    /** 放行静态资源 */
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        //解决静态资源被拦截的问题
//        web.ignoring().antMatchers("/css/**");
//        web.ignoring().antMatchers("/js/**");
//        web.ignoring().antMatchers("/images/**");
//        web.ignoring().antMatchers("/login/**");
//        //解决服务注册url被拦截的问题
//        web.ignoring().antMatchers("/resources/**");
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 登录
//        http.formLogin().loginPage("/toLogin").loginProcessingUrl("/doLogin")
//                .defaultSuccessUrl("/index")
//                .failureUrl("/toLogin?error=true");
//        //解决非thymeleaf的form表单提交被拦截问题
//        http.csrf().disable();
//
//        http
//                .authorizeRequests()
//                .antMatchers("/toLogin")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//        ;
//
//        //session管理
//        //session失效后跳转到登录页面
//        http.sessionManagement().invalidSessionUrl("/toLogin");
//
//        //单用户登录，如果有一个登录了，同一个用户在其他地方登录将前一个剔除下线
//        //http.sessionManagement().maximumSessions(1).expiredSessionStrategy(expiredSessionStrategy());
//
//        //单用户登录，如果有一个登录了，同一个用户在其他地方不能登录
//        http.sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(true);
//        //退出删除cookie
//        http.logout()
//                .permitAll()
//                .logoutUrl("/logout")  //执行注销的url
//                .invalidateHttpSession(true) // 指定是否在注销时让httpSession无效
//                .deleteCookies("JESSIONID")  // 清除cookie
//                .logoutSuccessUrl("/toLogin"); // 注销成功后跳转的url
//        super.configure(http);
//
//        //解决中文乱码问题
//        CharacterEncodingFilter filter = new CharacterEncodingFilter();
//        filter.setEncoding("UTF-8");
//        filter.setForceEncoding(true);
//        //http.addFilterBefore(filter,CsrfFilter.class);
//
//        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
//
//    }
//
//    /**
//     * 认证器
//     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // 自定义加密
//        auth.userDetailsService(userService()).passwordEncoder(md5PasswordEncoder());
//    }
//
//
//}
//
