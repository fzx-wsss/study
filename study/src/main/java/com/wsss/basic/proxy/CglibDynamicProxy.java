//package com.wsss.basic.proxy;
//
//import net.sf.cglib.proxy.Enhancer;
//import net.sf.cglib.proxy.MethodInterceptor;
//import net.sf.cglib.proxy.MethodProxy;
//
//import org.springframework.cglib.proxy.Enhancer;
//import org.springframework.cglib.proxy.MethodProxy;
//
//import java.lang.reflect.Method;
//
//public class CglibDynamicProxy implements MethodInterceptor {
//    //　这个就是我们要代理的真实对象
//    private Object subject;
//
//    public CglibDynamicProxy(Object subject) {
//        this.subject = subject;
//    }
//
//    public static void main(String[] args) {
//        Target target = new Target();
//
//        // 通过CGLIB动态代理获取代理对象的过程
//        Enhancer enhancer = new Enhancer();
//        // 设置enhancer对象的父类
//        enhancer.setSuperclass(Target.class);
//        // 设置enhancer的回调对象
//        enhancer.setCallback(new CglibDynamicProxy(target));
//        // 创建代理对象
//        Target proxy = (Target)enhancer.create();
//        // 通过代理对象调用目标方法
//        proxy.hello();
//        proxy.hello();
//        proxy.hello("123");
//        proxy.hello2();
//    }
//    @Override
//    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
////        System.out.println("cglib before");
////        System.out.println(methodProxy.getSuperName());
////        // 调用代理类FastClass对象
////        Object result =  methodProxy.invokeSuper(o, objects);
//////        Object result = methodProxy.invoke(target, objects);
////        System.out.println("cglib after");
//        return result;
//    }
//    static class Target {
//        public void hello()
//        {
//            System.out.println("I want to rent my house");
//        }
//
//        public void hello(String str)
//        {
//            System.out.println("hello: " + str);
//        }
//
//        public void hello2()
//        {
//            System.out.println("hello");
//        }
//    }
//}
