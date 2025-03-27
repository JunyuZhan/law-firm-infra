package com.lawfirm.api.config;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.DefaultApplicationArguments;

/**
 * Runner桥接工厂
 * 提供从ApplicationRunner到org.springframework.boot.Runner的兼容性转换
 */
public class RunnerBridgeFactory {
    
    /**
     * 创建一个动态代理，将ApplicationRunner转换为org.springframework.boot.Runner
     */
    public static Object createRunnerBridge(final ApplicationRunner delegate) {
        try {
            // 尝试获取org.springframework.boot.Runner类
            Class<?> runnerClass = Class.forName("org.springframework.boot.Runner");
            
            // 创建动态代理
            return Proxy.newProxyInstance(
                RunnerBridgeFactory.class.getClassLoader(),
                new Class<?>[] { runnerClass },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 如果是run方法，调用委托对象的run方法
                        if ("run".equals(method.getName()) && args != null && args.length == 1) {
                            // 创建简单的参数数组
                            String[] emptyArgs = new String[0];
                            
                            // 创建DefaultApplicationArguments包装
                            ApplicationArguments appArgs = new DefaultApplicationArguments(emptyArgs);
                            
                            // 委托调用
                            delegate.run(appArgs);
                            return null;
                        }
                        
                        // 其他方法按照默认行为处理
                        if ("toString".equals(method.getName())) {
                            return "Runner Bridge for " + delegate.toString();
                        }
                        if ("hashCode".equals(method.getName())) {
                            return System.identityHashCode(proxy);
                        }
                        if ("equals".equals(method.getName())) {
                            return proxy == args[0];
                        }
                        
                        return null;
                    }
                }
            );
        } catch (ClassNotFoundException e) {
            // 如果找不到Runner类，返回原始ApplicationRunner
            return delegate;
        }
    }
} 