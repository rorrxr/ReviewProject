package com.minju.reviewproject.dto;

import lombok.Data;

@Data
public class FileInfo {
    private String fileName;
    private byte[] data;

    public FileInfo(String fileName, byte[] data) {
        this.fileName = fileName;
        this.data = data;
    }
}