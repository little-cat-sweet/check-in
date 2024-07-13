package com.hongyun.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseObjectVO<T> {
    private Boolean success;
    private String message;
    private T data = null;
    private PageVO pagination = null;
    private ErrorVO error = null;

    public ResponseObjectVO<T> getSuccessResponseVo(String message) {
        this.setSuccess(true);
        this.setMessage(message);
        this.setError(null);
        return this;

    }

    public ResponseObjectVO<T> getSuccess(String message, T data) {
        this.setSuccess(true);
        this.setData(data);
        this.setMessage(message);
        this.setError(null);
        return this;
    }


    public ResponseObjectVO<T> getFailResponseVo(String message) {
        this.setSuccess(false);
        this.setData(null);
        this.setMessage(message);
        this.setError(error);
        return this;
    }

    public ResponseObjectVO<T> getFail(String message, ErrorVO error) {
        this.setSuccess(false);
        this.setData(null);
        this.setMessage(message);
        this.setError(error);
        return this;
    }

    public ResponseObjectVO<T> buildSuccess(String message, T data) {
        this.setSuccess(true);
        this.setData(data);
        this.setMessage(message);
        this.setError(null);
        return this;
    }
}