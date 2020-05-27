package com.cbd.cbdcontroller.security.config;


import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//继承 WebSecurityConfigurerAdapter工具类，重写里面两个方法 认证、鉴权

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private MyUserDetailService myUserDetailService;

    // 鉴权
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()

                .anyRequest().permitAll().and().logout().permitAll();

//                // 匹配 "/","/index" 路径，不需要权限即可访问
//                .antMatchers("/","/index","/error").permitAll()
//                // 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
//                .antMatchers("/user/**").hasRole("USER")
//                // 匹配 "/admin" 及其以下所有路径，都需要 "ADMIN" 权限
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .and()
//                // 指定登录地址 /login , 成功后 跳转到 /user
//                .formLogin().loginPage("/login").defaultSuccessUrl("/")
//                .and()
//                // 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/login");
//        /* 开启记住我功能，相当于 cookie，默认保存两周 */
//        http.rememberMe().rememberMeParameter("remember");
//        /*这个是用来防范CSRF跨站请求伪造攻击的，在学习阶段关掉*/

        http.csrf().disable();
        //开启跨域
        http.cors();
    }


    // 认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /**
         * 在内存中创建一个名为 "user" 的用户，密码为 "pwd"，拥有 "USER" 权限，密码使用BCryptPasswordEncoder加密
         */
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("user").password(new BCryptPasswordEncoder().encode("pwd")).roles("USER");
        /**
         * 在内存中创建一个名为 "admin" 的用户，密码为 "pwd"，拥有 "USER" 和"ADMIN"权限
         */
//        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//                .withUser("admin").password(new BCryptPasswordEncoder().encode("pwd")).roles("USER","ADMIN");
        //加入数据库验证类，下面的语句实际上在验证链中加入了一个DaoAuthenticationProvider
//        auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }
}





























//
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Configuration
//public class SecurityJavaConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//    @Autowired
//    private MySavedRequestAwareAuthenticationSuccessHandler mySuccessHandler;
//    @Autowired
//    private MySimpleUrlLogoutSuccessHandler logoutSuccessHandler;
//    @Autowired
//    private MyUserDetailsService userDetailsService;
//    private SimpleUrlAuthenticationFailureHandler myFailureHandler = new SimpleUrlAuthenticationFailureHandler();
//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .addFilterBefore( authenticationFilter() , UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling()
//                .authenticationEntryPoint(restAuthenticationEntryPoint)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/user/**").authenticated()
//               // .antMatchers("/api/authentication/verificationCode").anonymous()
//                .and()
//                .formLogin()
//                .successHandler(mySuccessHandler)
//                .failureHandler(myFailureHandler)
//                .and()
//                .logout().logoutSuccessHandler(logoutSuccessHandler)
//                .and().rememberMe().tokenValiditySeconds(60*60*24);
//    }
//
//    public SimpleAuthenticationFilter authenticationFilter() throws Exception {
//        SimpleAuthenticationFilter filter = new SimpleAuthenticationFilter();
//        filter.setAuthenticationManager(authenticationManagerBean());
//        return filter;
//    }
//
//
////    @Autowired
////    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
////        auth.authenticationProvider(authProvider());
////    }
////    public AuthenticationProvider authProvider() {
////        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
////        provider.setUserDetailsService(userDetailsService);
////        provider.setPasswordEncoder(passwordEncoder());
////        return provider;
////    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
