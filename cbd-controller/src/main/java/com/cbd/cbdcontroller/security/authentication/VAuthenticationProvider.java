package com.cbd.cbdcontroller.security.authentication;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author shy_black
 * @date 2020/5/31 14:35
 * @Description:
 * 认证提供者，校验用户，登录名密码，以及向系统提供一个用户信息的综合封装
 *
 *  这个实现类的核心就是authenticate方法，一步步看，系统会将用户在登录请求操作的时候，把输入的用户名密码等，
 *  封装到一个Authentication对象中，我们从这个对象里拿到用户名，通过 VUserDetailsService
 *  获取到 VUserDetails 对象，然后拿这个对象的密码属性，和请求Authentication对象中获取的密码进行对比，
 *  如果一切OK，验证功过，然后再将这些所有信息，整体封装成一个Authentication对
 *  象（这里边我们用的是UsernamePasswordAuthenticationToken），交给系统框架，后期可以随时取用
 */
@Slf4j
@Component
public class VAuthenticationProvider implements AuthenticationProvider {

    Gson gson = new Gson();

    @Autowired
    VUserDetailsService vUserDetailsService;
    /**
     * 首先，在用户登录的时候，系统将用户输入的的用户名和密码封装成一个Authentication对象
     * 然后，根据用户名去数据源中查找用户的数据，这个数据是封装成的VUserDetails对象
     * 接着，将两个对象进行信息比对，如果密码正确，通过校验认证
     * 最后，将用户信息（含身份信息、细节信息、密码、权限等）封装成一个对象，此处参考UsernamePasswordAuthenticationToken
     * 最最后，会将这个对象交给系统SecurityContextHolder中（功能类似Session），以便后期随时取用
     */

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        log.info("VAuthenticationProvider authenticate login user [username={}, password={}]", username, password);

        VUserDetails vUserDetails = (VUserDetails)vUserDetailsService.loadUserByUsername(username);
        log.info("VAuthenticationProvider authenticate vUserDetails [vUserDetails={}]", gson.toJson(vUserDetails));

        if(vUserDetails == null) {
            throw new BadCredentialsException("用户没有找到");
        }
        if (!password.equals(vUserDetails.getPassword())) {
            log.info("VAuthenticationProvider authenticate BadCredentialsException [inputPassword={}, DBPassword={}]", password, vUserDetails.getPassword());
            throw new BadCredentialsException("密码错误");
        }

        //认证校验通过后，封装UsernamePasswordAuthenticationToken返回
        return new UsernamePasswordAuthenticationToken(vUserDetails, password, vUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        AuthenticationProvider authenticationProvider = new VAuthenticationProvider();
        return authenticationProvider;
    }


}
