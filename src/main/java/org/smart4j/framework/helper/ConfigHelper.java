package org.smart4j.framework.helper;

import org.smart4j.framework.ConfigConstant;
import org.smart4j.framework.util.PropsUtil;

import java.util.Properties;

/**
 * 属性文件助手类
 */
public final class ConfigHelper {
    private static final Properties CONFIG_PROPS=PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取JDBC驱动
     * @return String
     */
    public static String getJdbcDriver(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取JDBC URL
     * @return String
     */
    public static String getJdbcUrl(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_URL);
    }
    /**
     * 获取JDBC USERNAME
     * @return String
     */
    public static String getJdbcUserName(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_USERNAME);
    }
    /**
     * 获取JDBC PASSWORD
     * @return String
     */
    public static String getJdbcPassword(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取App Base Package
     * @return String
     */
    public static String getAppBasePackage(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_BASE_PACKAGE);
    }
    /**
     * 获取App Jsp Path
     * @return String
     */
    public static String getAppJspPath(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_JSP_PATH);
    }
    /**
     * 获取App Asset Path
     * @return String
     */
    public static String getAppAssetPath(){
        return PropsUtil.getString(CONFIG_PROPS,ConfigConstant.APP_ASSET_PATH);
    }

    /**
     * 获取应用文件上传限制
     * @return
     */
    public static int getAppUploadLimit(){
        return PropsUtil.getInt(CONFIG_PROPS,ConfigConstant.APP_UPLOAD_LIMIT,10);
    }
}
