package org.javaboy.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.TimeoutException;

/**
 * @author 江南一点雨
 * @微信公众号 江南一点雨
 * @网站 http://www.itboyhub.com
 * @国际站 http://www.javaboy.org
 * @微信 a_java_boy
 * @GitHub https://github.com/lenve
 * @Gitee https://gitee.com/lenve
 */
public class TranDemo {
    public static void main(String[] args) throws TimeoutException {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService2 userService2 = ctx.getBean(UserService2.class);
        userService2.m5();
    }
}
