package com.cbd.cbdcontroller.security.filter;//package com.black.lei.security.filter;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//
//public class SimpleAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    public static final String SPRING_SECURITY_FORM_VERIFICATION_CODE_KEY = "verificationCode";
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//        throws AuthenticationException {
//
//        if (!request.getMethod()
//            .equals("POST")) {
//            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
//        }
//
//        UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
//        setDetails(request, authRequest);
//        return this.getAuthenticationManager()
//            .authenticate(authRequest);
//    }
//
//    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request)   {
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);
//        String verificationCode = obtainDomain(request);
//
//        if (username == null) {
//            username = "";
//        }
//        if (password == null) {
//            password = "";
//        }
//        if (verificationCode == null) {
//            verificationCode = "";
//        }
//
//        HashMap<String, String> hashMap = new HashMap<>();
//        hashMap.put("username",username);
//        hashMap.put("verificationCode",verificationCode);
//        String userNameAndVerificationCode = "";
//        try {
//            userNameAndVerificationCode = new ObjectMapper().writeValueAsString(hashMap);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        return new UsernamePasswordAuthenticationToken(userNameAndVerificationCode, password);
//    }
//
//    private String obtainDomain(HttpServletRequest request) {
//        return request.getParameter(SPRING_SECURITY_FORM_VERIFICATION_CODE_KEY);
//    }
//}
