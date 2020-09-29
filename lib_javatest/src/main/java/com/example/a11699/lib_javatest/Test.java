package com.example.a11699.lib_javatest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Create time 2020/6/26
 * Create Yu
 * description:
 */
public class Test {

    public static void main(String[] args) {
        final JavaDeveloper zack =new JavaDeveloper("java");
        Developer zackProxy = (Developer) Proxy.newProxyInstance(
                zack.getClass().getClassLoader(), zack.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if(method.getName().equals("code")){
                            System.out.println("Zack is praying for code");
                            return method.invoke(zack,args);
                        }
                        if(method.getName().equals("debug")){
                            System.out.println("Zack is has no bug");
                            return null;
                        }
                        return null;
                    }
                });
        zackProxy.code();
        zackProxy.debug();
    }

}
