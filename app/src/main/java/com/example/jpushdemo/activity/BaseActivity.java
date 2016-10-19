package com.example.jpushdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jpushdemo.utils.ExampleUtil;
import com.example.jpushdemo.utils.SPUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by turbo on 2016/10/17.
 */

public class BaseActivity extends AppCompatActivity {

    protected Context context = null;
    //以下都是极光推送相关
    protected static final String TAG = "JPush";
    protected static final int MSG_SET_ALIAS = 1001;
    protected final Handler mJpushHandler = new Handler() {
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
                    if (ExampleUtil.checkNetworkState(getApplicationContext())) {
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

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在JPushReceiver processCustomMessage里保存logInAnotherPlace这个值
        //TODO 针对第2种情况，重新进入app并不想在启动页（或者其他页面）展示退出登录提醒，那么对this进行判断   &&(!(this instanceof WelcomeActivity || this instanceof GuideActivity)
        if(SPUtils.getBoolean(this,"logInAnotherPlace",false)&&(!(this instanceof WelcomeActivity))){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(context, AlertDialogActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            },3000);
        }
    }
}
