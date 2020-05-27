package com.black.lei.service.Impl;

import com.black.lei.dao.userDao;

import com.cbd.cbdcommoninterface.cbd_interface.user.IRoleDefineService;
import com.cbd.cbdcommoninterface.pojo.leipojo.user;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional
@Service("MyUserDetailService")
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private userDao userDao;

    @Autowired
    private IRoleDefineService roleDefineService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user userBean = userDao.findByPhone(username);
//        Collection<SimpleGrantedAuthority> authorities = userBean.getAuthorities();
        List<SimpleGrantedAuthority> authorities =
                roleDefineService.getUserRoleByID(userBean.getID());
        if (userBean == null){
            throw new UsernameNotFoundException("数据库查无此人");
        }

        user myUser = new user(userBean.getUsername(), userBean.getPassword(), authorities);
        return myUser;
    }
}


//public class MyUserDetailService implements UserDetailsService {
//
//    @Autowired
//    private userDao userDao;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        user userBean = userDao.findByPhone(username);
//        if (userBean == null){
//            throw new UsernameNotFoundException("数据库查无此人");
//        }
//        return userBean;
//    }
//}


//public class MyUserDetailsService implements UserDetailsService {
//    @Autowired
//    private IUserService userService;
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        //从数据库中取出用户信息
//        user userLoginInfo = userService.findByPhone(s);
//        if(userLoginInfo == null) {
//            log.info("用户未找到");
//            throw new GlobalException(CodeMsg.SERVER_ERROR);
//        }
//
//        return null;
//    }
//}

//public class MyUserDetailsService implements UserDetailsService {
//
//
//    @Autowired
//    private com.black.lei.dao.userDao userDao;
//    @Autowired
//    HttpSession httpSession;
//
//    public MyUserDetailsService() {
//        super();
//    }
//
//    // API
//    //获得一个通过验证的Security类型的user对象
//    @Override
//    public UserDetails loadUserByUsername(final String userNameAndVerificationCode) throws UsernameNotFoundException {
//        try {
//
//            Map<String, String> userNameAndVerificationCodeMap = new ObjectMapper().readValue(userNameAndVerificationCode,new TypeReference<Map<String,String>>(){});
//            String username = userNameAndVerificationCodeMap.get("username");
//            String verificationCode = userNameAndVerificationCodeMap.get("verificationCode");
//            Object verificationCodeObject = httpSession.getAttribute("verificationCode") ;
//
////            if(verificationCodeObject==null||!verificationCode.equalsIgnoreCase(verificationCodeObject.toString())){
////                throw new LoginException("No verificationCode found: "+verificationCode);
////            }
//            final User user = userDao.findByEmail(username);
//            if (user == null) {
//                throw new UsernameNotFoundException("No user found with username: " + username);
//            }
//
//            return new org.springframework.security.core.userdetails.User(
//                    user.getEmail(),
//                    user.getPassword(),
//                    user.isEnabled(),
//                    true,
//                    true,
//                    true,
//                    getAuthorities(user.getRoles())
//            );
//        } catch (final Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // UTIL
//    private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
//        return getGrantedAuthorities(getPrivileges(roles));
//    }
//
//    //从DB中获取该用户对应的角色，权限信息
//    //返回该用户对应角色的所有权限
//    private final List<String> getPrivileges(final Collection<Role> roles) {
//        final List<String> privileges = new ArrayList<>();
//        final List<Privilege> collection = new ArrayList<>();
//        for (final Role role : roles) {
//            collection.addAll(role.getPrivileges());
//        }
//        for (final Privilege item : collection) {
//            privileges.add(item.getName());
//        }
//
//        return privileges;
//    }
//
//    //返回所有所有可以访问到的  RESTfulAPI  的授权
//    private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
//        final List<GrantedAuthority> authorities = new ArrayList<>();
//        for (final String privilege : privileges) {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        }
//        return authorities;
//    }
//
//}
