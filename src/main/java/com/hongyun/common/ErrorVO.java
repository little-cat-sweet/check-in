package com.hongyun.common;

public class ErrorVO {

    private String errorCode;
    private String errorMessage;
    private String parameter;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public ErrorVO() {
    }

    public ErrorVO(String errorCode, String errorMessage, String parameter) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.parameter = parameter;
    }
}
