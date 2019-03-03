package com.antiplagiarism.fileuploadservice.core.services;

public enum FileExceptions {
    FILE_WITH_THAT_NAME_ALREADY_EXISTS("File with that name Already exists"), SORRY_FILENAME_CONTAINS_INVALID_PATH_SEQUENCE("Sorry! Filename contains invalid path sequence "), COULD_NOT_STORE_FILE("Could not store file "), PLEASE_TRY_AGAIN(". Please try again!");
    private String value;

    public String getValue() {
        return value;
    }

    FileExceptions(String value) {
        this.value = value;
    }
}
