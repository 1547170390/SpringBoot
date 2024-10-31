package com.zl.template.aspect;

import com.zl.template.annotation.MyAnnotation;
import jdk.jfr.Category;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 自定义注解切面
 */
@Aspect
@Component
public class AnnotationAspect {

    @Before("@annotation(com.zl.template.annotation.MyAnnotation)")
    public void before(JoinPoint joinPoint) throws NoSuchMethodException {
        //获取目标类
        Class<?> targetClass = joinPoint.getTarget().getClass();
        //获取方法名
        String name = joinPoint.getSignature().getName();
        //获取方法参数类型
        Class<?>[] parameterTypes = ((org.aspectj.lang.reflect.MethodSignature) joinPoint.getSignature()).getParameterTypes();
        //使用反射找到方法
        Method method = targetClass.getMethod(name, parameterTypes);
        MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
        if (myAnnotation != null) {
            //获取注解的name值
            String name1 = myAnnotation.name();
            System.out.println(name1);
        }
    }
}
