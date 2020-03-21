package com.hust.projectmanagement.taskservice.dto;

public enum APIStatus {

    ERR_INVALID_DATA(100, "Input data is not valid."),
    INVALID_PARAMETER(200, "Invalid request parameter"),
    OK(200, null),
    ERR_INTERNAL_SERVER(500, "Internal Error"),
    SQL_ERROR(501, "SQL Error"),
    ERR_BAD_REQUEST(400, "Bad request"),    
    ERR_SESSION_DATA_INVALID(603, "Session data invalid"),
    ERR_SESSION_NOT_FOUND(604, "Session not found"),
    //User status
    ERR_USER_NOT_FOUND(404, "User Not Found");
    private final int code;
    private final String description;
    private APIStatus(int s, String v) {
        code = s;
        description = v;
    }
    public int getCode() {
        return code;
    }
    public String getDescription() {
        return description;
    }
}
