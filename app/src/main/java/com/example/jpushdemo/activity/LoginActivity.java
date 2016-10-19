package com.example.jpushdemo.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jpushdemo.api.ApiClient;
import com.example.jpushdemo.bean.Login;
import com.example.jpushdemo.bean.User;
import com.example.jpushdemo.utils.ExampleUtil;
import com.hwangjr.rxbus.RxBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import pub.war3.demo.login.R;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

public class LoginActivity extends BaseActivity {

    private EditText etUsername;
    private EditText etPassowrd;
    protected Subscription subscription;
    Observer<User> observer = new Observer<User>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(User user) {
            if (user.getCode() == 200) {
                mJpushHandler.sendMessage(mJpushHandler.obtainMessage(MSG_SET_ALIAS, user.getData().getUid()));
                RxBus.get().post(new Login(user.getData().getUsername()));
                finish();
            } else {
                ExampleUtil.showToast("登录信息错误", LoginActivity.this);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        etUsername = (EditText) findViewById(R.id.et_username);
        etPassowrd = (EditText) findViewById(R.id.et_password);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });
    }

    private void load() {
        unsubscribe();
        subscription = ApiClient.getDemoApi()
                .login(etUsername.getText().toString().trim(), etPassowrd.getText().toString().trim(), "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unsubscribe();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
