package com.example.jpushdemo.bean;

/**
 * Created by snail on 16/10/16.
 */
public class JPushLoginEvent {
    /**
     * type : 1
     * msg : 登录
     */

    private int type;
    private String msg;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
