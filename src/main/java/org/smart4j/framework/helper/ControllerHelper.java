package org.smart4j.framework.helper;


import org.smart4j.framework.annotation.Action;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Request;
import org.smart4j.framework.util.ArrayUtil;
import org.smart4j.framework.util.CollectionUtil;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 */
public final class ControllerHelper {
    private static final Map<Request,Handler> ACTION_MAP=new HashMap<Request, Handler>();
    static {
        Set<Class<?>> controllerClassSet =ClassHelper.getControllerClassSet();
        if(CollectionUtil.isNotEmpty(controllerClassSet)){
            for (Class<?> cls:controllerClassSet) {
                Method[] methods =cls.getMethods();
                for (Method method:methods) {
                    if(method.isAnnotationPresent(Action.class)){
                        Action action=method.getAnnotation(Action.class);
                        String mapping = action.value();
                        if(mapping.matches("\\w+:/\\w*")){
                            String[] array=mapping.split(":");
                            if(ArrayUtil.isNotEmpty(array) && array.length==2){
                                String requestMethod=array[0];
                                String requestPath=array[1];
                                Request request=new Request(requestMethod,requestPath);
                                Handler handler=new Handler(cls,method);
                                ACTION_MAP.put(request,handler);
                            }
                        }
                    }
                }
            }
        }
    }
    public static Handler getHandler(String requestMethod,String requestPath){
        Request request = new Request(requestMethod,requestPath);
        return ACTION_MAP.get(request);
    }
}
