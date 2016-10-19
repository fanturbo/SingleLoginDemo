// (c)2016 Flipboard Inc, All Rights Reserved.

package com.example.jpushdemo.api;

import com.example.jpushdemo.bean.User;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

public interface DemoApi {
    @FormUrlEncoded
    @POST("/turbo/some_good_examples/singlelogindemo/login.php")
    Observable<User> login(@Field("username") String useranme, @Field("password") String password,@Field("auto") String auto);
}
