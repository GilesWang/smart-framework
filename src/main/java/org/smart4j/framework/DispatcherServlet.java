package org.smart4j.framework;

import org.smart4j.framework.bean.Data;
import org.smart4j.framework.bean.Handler;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.bean.View;
import org.smart4j.framework.helper.*;
import org.smart4j.framework.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;

@WebServlet(urlPatterns = "/*", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletHelper.init(req, resp);
        try {
            String requestMethod = ServletHelper.getRequest().getMethod().toLowerCase();
            String requestPath = ServletHelper.getRequest().getPathInfo();

            if (requestPath.equals("/favicon.ico")) {
                return;
            }
            Handler handler = ControllerHelper.getHandler(requestMethod, requestPath);
            if (handler != null) {
                Class<?> controllerClass = handler.getControllerClass();
                Object controllerBean = BeanHelper.getBean(controllerClass);

                Param param;
                if (UploadHelper.isMultipart(req)) {
                    param = UploadHelper.createParam(req);
                } else {
                    param = RequestHelper.createParam(req);
                }

//            Map<String, Object> paramMap = new HashMap<String, Object>();
//            Enumeration<String> paramNames = req.getParameterNames();
//            while (paramNames.hasMoreElements()) {
//                String paramName = paramNames.nextElement();
//                String paramValue = req.getParameter(paramName);
//                paramMap.put(paramName, paramValue);
//            }
//            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
//            if (StringUtil.isNotEmpty(body)) {
//                String[] params = StringUtils.split(body, "&");
//                if (ArrayUtil.isNotEmpty(params)) {
//                    for (String param : params) {
//                        String[] array = StringUtils.split(param, "=");
//                        if (ArrayUtil.isNotEmpty(array)) {
//                            paramMap.put(array[0], array[1]);
//                        }
//                    }
//                }
//            }
//
//            Param param = new Param(paramMap);
                Object result;
                Method actionMethod = handler.getActionMethod();
                if (param.isEmpty()) {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                } else {
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }
                if (result instanceof View) {
                    handleViewResult((View) result, req, resp);
                } else if (result instanceof Data) {
                    handleDataResult((Data) result, req, resp);
                }
            }
        } finally {
            ServletHelper.destroy();
        }


    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        HelperLoader.init();
        ServletContext servletContext = config.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");

        UploadHelper.init(servletContext);
    }

    private void handleViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
            }
        }
    }

    private void handleDataResult(Data data, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
        }
    }
}
