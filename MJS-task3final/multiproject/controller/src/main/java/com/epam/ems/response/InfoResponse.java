package com.epam.ems.response;

public class InfoResponse {

    private int status;
    private String message;
    private String code;

    public InfoResponse() {
    }

    public InfoResponse(int status, String message, String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

