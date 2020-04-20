package com.cbd.cbdcontroller.init;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;

import com.cbd.cbdcommoninterface.cbd_interface.redis.RedisService;
import com.cbd.cbdcommoninterface.keys.AccessAuthKey;
import com.cbd.cbdcommoninterface.pojo.user.AccessAuthEntity;
import com.cbd.cbdcommoninterface.utils.AnnotationUtil;
import com.cbd.cbdcommoninterface.utils.ClassUtil;
import com.cbd.cbdcontroller.annotation.AuthScan;
import com.cbd.cbdcontroller.annotation.Login;
import com.cbd.cbdcontroller.annotation.Permission;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author 大闲人柴毛毛
 * @date 2017/11/1 上午10:04
 * @description 初始化用户信息
 */
@AuthScan("com.cbd.cbdcontroller.controller")
@Component
@Slf4j
public class InitAuth implements CommandLineRunner {

    @Autowired
    private RedisService redisService;

    /**
     * 接口权限列表
     */
    private Map<String, AccessAuthEntity> accessAuthMap = Maps.newHashMap();

    /**
     * 反斜杠
     */
    private static final String Back_Slash = "/";


    @Override
    public void run(String... strings) throws Exception {
        // 加载接口访问权限
        loadAccessAuth();
    }


    /**
     * 加载接口访问权限
     */
    private void loadAccessAuth() throws IOException {
        // 获取待扫描的包名
        AuthScan authScan = AnnotationUtil.getAnnotationValueByClass(this.getClass(), AuthScan.class);
        String pkgName = authScan.value();

        // 获取包下所有类
        List<Class<?>> classes = ClassUtil.getClasses(pkgName);
        if (CollectionUtils.isEmpty(classes)) {
            return;
        }

        // 遍历类
        for (Class clazz : classes) {
            Method[] methods = clazz.getMethods();
            if (methods == null || methods.length == 0) {
                continue;
            }

            // 遍历函数
            for (Method method : methods) {
                AccessAuthEntity accessAuthEntity = buildAccessAuthEntity(method);
                if (accessAuthEntity != null) {
                    // 生成key
                    String key = generateKey(accessAuthEntity);
                    // 存至本地Map
                    accessAuthMap.put(key, accessAuthEntity);
                    log.debug("", accessAuthEntity);
                }
            }
        }
        // 存至Redis
        redisService.hmset(AccessAuthKey.Access_Auth, "all", accessAuthMap);

        log.info("接口访问权限已加载完毕！" + accessAuthMap);
    }

    /**
     * 生成接口权限信息的Key
     * Key = 'AUTH'+请求URL
     *
     * @param accessAuthEntity 接口权限信息
     * @return Key
     */
    private String generateKey(AccessAuthEntity accessAuthEntity) {
        return accessAuthEntity.getUrl();
    }

    /**
     * 构造AccessAuthEntity对象
     *
     * @param method
     * @return
     */
    private AccessAuthEntity buildAccessAuthEntity(Method method) {
        RequestMapping requestMapping = AnnotationUtil.getAnnotationValueByMethod(method, RequestMapping.class);
        AccessAuthEntity accessAuthEntity = null;

        if (requestMapping != null
                && requestMapping.value() != null
                && requestMapping.value().length == 1
                && StringUtils.isNotEmpty(requestMapping.value()[0])) {
            accessAuthEntity = new AccessAuthEntity();
            accessAuthEntity.setUrl(trimUrl(requestMapping.value()[0]));
        }

        // 解析@Login 和 @Permission
        if (accessAuthEntity != null) {
            accessAuthEntity = getLoginAndPermission(method, accessAuthEntity);
            accessAuthEntity.setMethodName(method.getName());
        }

        return accessAuthEntity;
    }


    /**
     * 处理URL
     * 1. 将URL两侧的斜杠去掉
     * 2. 将URL中的"{xxx}"替换为"*"
     *
     * @param url 原始URL
     * @return 处理后的URL
     */
    private static String trimUrl(String url) {
        // 清除首尾的反斜杠
        if (url.startsWith(Back_Slash)) {
            url = url.substring(1);
        }
        if (url.endsWith(Back_Slash)) {
            url = url.substring(0, url.length() - 1);
        }

        // 将"{xxx}"替换为"*"
        // TODO 正则表达式要继续完善（纠正/user/{xxxxx}/{yyyy}——>user/*的情况）
        url = url.replaceAll("\\{(.*)\\}", "*");
        return url;
    }

    /**
     * 获取指定方法上的@Login的值和@@Permission的值
     *
     * @param method           目标方法
     * @param accessAuthEntity
     * @return
     */
    private AccessAuthEntity getLoginAndPermission(Method method, AccessAuthEntity accessAuthEntity) {
        // 获取@Permission的值
        Permission permission = AnnotationUtil.getAnnotationValueByMethod(method, Permission.class);
        if (permission != null && StringUtils.isNotEmpty(permission.value())) {
            accessAuthEntity.setPermission(permission.value());
            accessAuthEntity.setLogin(true);
            return accessAuthEntity;
        }

        // 获取@Login的值
        Login login = AnnotationUtil.getAnnotationValueByMethod(method, Login.class);
        if (login != null) {
            accessAuthEntity.setLogin(true);
        }

        accessAuthEntity.setLogin(false);
        return accessAuthEntity;
    }

    public static void main(String[] args) {
        System.out.println("user:" + trimUrl("user"));
        System.out.println("{}:" + trimUrl("{}"));
        System.out.println("/user:" + trimUrl("/user"));
        System.out.println("/user/:" + trimUrl("/user/"));
        System.out.println("user/{xxxx}:" + trimUrl("user/{xxxx}"));
        System.out.println("/user/{xxxxx}/{yyyy}:" + trimUrl("/user/{xxxxx}/{yyyy}"));
        System.out.println("/user/home/{sdsds}:" + trimUrl("/user/home/{sdsds}"));
        System.out.println("/user/{home}/{zzzzz}/:" + trimUrl("/user/{home}/{zzzzz}/"));
    }

}
