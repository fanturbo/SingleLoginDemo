package com.example.jpushdemo.bean;

import com.hwangjr.rxbus.annotation.Produce;

/**
 * Created by turbo on 2016/10/19.
 */
public class Login {
    String username;

    public Login(String username) {
        this.username = username;
    }

    @Produce
    public String login() {
        return this.username;
    }
}
