package com.example.jpushdemo.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pub.war3.demo.login.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvUsername = (TextView) findViewById(R.id.tv_username);
        Intent intent = getIntent();
        if (intent != null) {
            String username = intent.getStringExtra("username");
            tvUsername.setText("Welcome," + username + "!");
        }
        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mJpushHandler.sendMessage(mJpushHandler.obtainMessage(MSG_SET_ALIAS, ""));
            }
        });
    }
}
