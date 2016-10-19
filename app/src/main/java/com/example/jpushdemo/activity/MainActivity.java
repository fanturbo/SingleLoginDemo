package com.example.jpushdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.jpushdemo.bean.Exit;
import com.example.jpushdemo.bean.Login;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Produce;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import java.util.Arrays;
import java.util.List;

import pub.war3.demo.login.R;

public class MainActivity extends BaseActivity {

    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        RxBus.get().register(this);
        tvUsername = (TextView) findViewById(R.id.tv_username);
        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJpushHandler.sendMessage(mJpushHandler.obtainMessage(MSG_SET_ALIAS, ""));
                tvUsername.setText("exit");
            }
        });
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.btn_exit_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        RxBus.get().unregister(this);
        super.onDestroy();
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD
    )
    public void exit(Exit exit) {
        tvUsername.setText(exit.produce());
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD
    )
    public void login(Login login) {
        tvUsername.setText("Welcome," + login.login() + "!");
    }
}
