package com.antiplagiarism.fileuploadservice.core.controllers;

public enum ControllerConstants {
    FILE_UPLOADED_WAS_SCHEDULED("File Uploaded Was scheduled");
    private String value;

    public String getValue() {
        return value;
    }

    ControllerConstants(String value) {
        this.value = value;
    }
}
