package com.example.jpushdemo.bean;

import android.util.Log;

import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;

/**
 * Created by turbo on 2016/10/19.
 */

public class Exit {
    @Produce
    public String produce() {
        return "exit";
    }
}
