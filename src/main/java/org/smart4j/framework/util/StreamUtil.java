package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class StreamUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    public static String getString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException ex) {
            LOGGER.error("get string failure", ex);
            throw new RuntimeException(ex);
        }
        return sb.toString();
    }

    public static void copyStream(InputStream inputStream, OutputStream outputStream){
        try {
            int length;
            byte[] buffer=new byte[4*1024];
            while ((length=inputStream.read(buffer,0,buffer.length))!=-1){
                outputStream.write(buffer,0,length);
            }
            outputStream.flush();
        }catch (Exception ex){
            LOGGER.error("copy stream failure",ex);
            throw new RuntimeException(ex);
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            }catch (Exception ex){
                LOGGER.error("close stream failure");
            }
        }
    }
}
