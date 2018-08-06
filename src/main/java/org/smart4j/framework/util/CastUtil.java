package org.smart4j.framework.util;

public class CastUtil {

    public static String castString(Object object) {
        return CastUtil.castString(object, "");
    }

    public static String castString(Object object, String defaultValue) {
        return object != null ? String.valueOf(object) : defaultValue;
    }

    public static int castInt(Object obj) {
        return CastUtil.castInt(obj,0);
    }

    public static int castInt(Object object, int defaultValue) {
        int intValue = defaultValue;
        if (object != null) {
            String strValue = CastUtil.castString(object);
            if (StringUtil.isNotEmpty(strValue)) {
                try {
                    intValue = Integer.parseInt(strValue);
                } catch (NumberFormatException ex) {
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }
    public static long castLong(Object obj) {
        Long defaultValue=new Long(0);
        return CastUtil.castLong(obj,defaultValue);
    }

    public static long castLong(Object obj,long defaultValue){
        long longValue=defaultValue;
        if(obj!=null){
            String strValue=castString(obj);
            if(StringUtil.isNotEmpty(strValue)){
                try {
                    longValue=Long.parseLong(strValue);
                }catch (NumberFormatException ex) {
                    longValue = defaultValue;
                }
            }
        }
        return longValue;
    }

    public static boolean castBoolean(Object obj) {
        return CastUtil.castBoolean(obj, false);
    }

    public static boolean castBoolean(Object obj, boolean defautlValue) {
        boolean boolValue = defautlValue;
        if (obj != null) {
            boolValue = Boolean.parseBoolean(castString(obj));
        }
        return boolValue;
    }

    public static Double castDouble(Object obj,Double defaultValue){
        Double doubleValue=defaultValue;
        if(obj!=null){
            doubleValue=Double.parseDouble(castString(obj));
        }
        return doubleValue;
    }
    public static Double castDouble(Object obj){
        return castDouble(obj,0d);
    }
}
