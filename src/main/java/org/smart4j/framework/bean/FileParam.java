package org.smart4j.framework.bean;

import java.io.InputStream;

public class FileParam {
    private String fieldName;
    private String fileName;
    private long fileSize;
    private String contentType;
    private InputStream inputSteam;

    public FileParam(String fieldName, String fileName,
                     long fileSize, String contentType,
                     InputStream inputSteam) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputSteam = inputSteam;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputSteam() {
        return inputSteam;
    }
}
