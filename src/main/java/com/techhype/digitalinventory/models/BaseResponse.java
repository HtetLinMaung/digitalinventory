package com.techhype.digitalinventory.models;

import com.techhype.digitalinventory.constants.ServerMessage;
import com.techhype.digitalinventory.constants.ServerStatus;

public class BaseResponse {
    private int code = ServerStatus.OK;
    private String message = ServerMessage.OK;
    private Object data;

    public BaseResponse() {
    }

    public BaseResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static BaseResponse ok(Object data) {
        return new BaseResponse(data);
    }

    public static BaseResponse internalServerError() {
        return new BaseResponse(ServerStatus.INTERNAL_SERVER_ERROR, ServerMessage.INTERNAL_SERVER_ERROR, null);
    }

    public static BaseResponse notFound() {
        return new BaseResponse(ServerStatus.NOT_FOUND, ServerMessage.NOT_FOUND, null);
    }

    public BaseResponse(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
