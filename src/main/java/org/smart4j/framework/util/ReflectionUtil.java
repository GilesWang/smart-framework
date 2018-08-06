package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类
 */
public class ReflectionUtil {
    private static final Logger LOGGER=LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     * @param cls
     * @return
     */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance=cls.newInstance();
        }catch (Exception ex){
            LOGGER.error("new instance failure",ex);
            throw new RuntimeException(ex);
        }
        return instance;
    }

    public static Object invokeMethod(Object obj, Method method,Object... args){
        Object result;
        try {
            method.setAccessible(true);
            result=method.invoke(obj,args);
        }catch (Exception ex)        {
            LOGGER.error("invoke method failure",ex);
            throw new RuntimeException(ex);
        }
        return result;
    }

    public static void setField(Object obj, Field field,Object value){
        try {
            field.setAccessible(true);
            field.set(obj,value);
        }catch (Exception ex){
            LOGGER.error("set field failure",ex);
            throw new RuntimeException(ex);
        }
    }
}
