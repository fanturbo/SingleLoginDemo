package com.example.jpushdemo.bean;

import java.util.List;

/**
 * Created by turbo on 2016/10/17.
 */
public class User {

    /**
     * code : 201
     * message : 登录参数为空
     * data : []
     */

    private int code;
    private String message;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
