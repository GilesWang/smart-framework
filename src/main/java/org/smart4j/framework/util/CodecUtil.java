package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.net.URLEncoder;

public final class CodecUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    public static String encodeURL(String source){
        String target;
        try {
            target=URLEncoder.encode(source,"UTF-8");
        }catch (Exception ex){
            LOGGER.error("encode url failure", ex);
            throw new RuntimeException(ex);
        }
        return target;
    }
    public static String decodeURL(String source){
        String target;
        try {
            target=URLDecoder.decode(source,"UTF-8");
        }catch (Exception ex){
            LOGGER.error("decode url failure", ex);
            throw new RuntimeException(ex);
        }
        return target;
    }

}
