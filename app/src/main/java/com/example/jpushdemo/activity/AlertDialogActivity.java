package com.example.jpushdemo.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.example.jpushdemo.utils.ExampleUtil;
import com.example.jpushdemo.utils.SPUtils;
import com.example.jpushdemo.utils.DialogHelp;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by turbo on 2016/9/20.
 */
public class AlertDialogActivity extends AppCompatActivity {

    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        SPUtils.saveBoolean(this, "logInAnotherPlace", false);
        displayAlert();
        logout();
    }

    private void displayAlert() {
        DialogHelp.getConfirmDialog(this, "下线提示", "退出", "重新登录", "您的账户在另一台设备登录，如非本人操作，请尽快重新登录修改密码", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //TODO 跳转到登录Activity
                ExampleUtil.showToast("前往登录页面",context);
                finish();
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        }).setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                AlertDialogActivity.this.finish();
            }
        }).show();
    }

    private void logout() {
        //TODO 清除手机上保存的登录用户状态
        mJpushHandler.sendMessage(mJpushHandler.obtainMessage(MSG_SET_ALIAS, ""));
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }

    //以下都是极光推送相关
    private static final String TAG = "JPush";
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mJpushHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    break;

                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    if (ExampleUtil.checkNetworkState(context)) {
                        mJpushHandler.sendMessageDelayed(mJpushHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    } else {
                        Log.i(TAG, "No network");
                    }
                    break;

                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
        }

    };
}