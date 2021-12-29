package com.udacity.jwdnd.course1.cloudstorage.model;

public class ResponseFile {
    private Integer fileId;
    private String fileName;
    private String dataUrl;

    public ResponseFile(Integer fileId, String fileName, String dataUrl) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.dataUrl = dataUrl;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }
}
