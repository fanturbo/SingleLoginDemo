package com.example.jpushdemo.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.example.jpushdemo.api.ApiClient;
import com.example.jpushdemo.bean.User;
import com.example.jpushdemo.utils.SPUtils;

import cn.jpush.android.api.JPushInterface;
import pub.war3.demo.login.R;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        context = this;
        JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        autoLogin();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

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
            if (user.getCode() == 203) {
                SPUtils.saveBoolean(context, "logInAnotherPlace", true);
            } else if (user.getCode() == 200) {
                //TODO 自动登录成功，应该需要做更新token之类，更新用户数据。。。。等等操作，不过这儿只是为了演示解决第5中情况，所以没有任何处理
            }
        }
    };

    private void autoLogin() {
        unsubscribe();
        //TODO 这儿的username和password应该是在客户端本地保存的，无论是使用SP还是数据库都可以，不过别忘了加密
        subscription = ApiClient.getDemoApi()
                .login("war3", "war3", "1")
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
