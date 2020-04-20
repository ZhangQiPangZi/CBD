package com.cbd.cbdcontroller.handler;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@ControllerAdvice
@ResponseBody
@Slf4j
public class LogHandle {

    @Pointcut("execution(public * com.cbd.cbdcontroller.controller..*.*(..))")
    public void restLog(){}

    @Around("restLog()")
    public void doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        // 生成本次请求时间戳
        String timestamp = System.currentTimeMillis()+"";

        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        log.info(timestamp + ", url: {}, method: {}, uri: {}, params: {}", url, method, uri, queryString);

        // result的值就是被拦截方法的返回值
        Object result = joinPoint.proceed();
        log.info(timestamp + " , " + result.toString());
    }

}
