package com.cbd.cbdcommoninterface.utils.utilsForPlatForm.security.component;//package com.black.lei.security.component;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
//import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
//import org.springframework.security.web.savedrequest.RequestCache;
//import org.springframework.security.web.savedrequest.SavedRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//
///**
// * 权限验证成功后页面处理器
// * 和Security提供的 SavedRequestAwareAuthenticationSuccessHandler 使用一样的处理逻辑
// * 更改返回的默认路径和返回码 如验证成功，返回200
// */
//@Component
//public class MySavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
//
//    private RequestCache requestCache = new HttpSessionRequestCache();
//
//    @Override
//    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws ServletException, IOException {
//        final SavedRequest savedRequest = requestCache.getRequest(request, response);
//
//        //request == null ,说明现在该用户的session为null
//        //如果请求为空，则清空请求？？？q
//        if (savedRequest == null) {
//            clearAuthenticationAttributes(request);
//            return;
//        }
//        //clearAuthenticationAttributes的代码块如下：
//        // false参数 在其他的接口实现方法中多为create，即大意为获得的session为null，
//        //HttpSession session = request.getSession(false);
//        //
//        //        if (session != null) {
//        //            session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
//        //        }
//
//        //获取返回Url
//        final String targetUrlParameter = getTargetUrlParameter();
//        //
//        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
//            requestCache.removeRequest(request, response);
//            clearAuthenticationAttributes(request);
//            return;
//        }
//
//        clearAuthenticationAttributes(request);
//    }
//    public void setRequestCache(final RequestCache requestCache) {
//        this.requestCache = requestCache;
//    }
//}
