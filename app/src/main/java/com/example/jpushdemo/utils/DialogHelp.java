package com.example.jpushdemo.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;

import pub.war3.demo.login.R;


/**
 * 对话框辅助类
 */
public class DialogHelp {
    public static AlertDialog.Builder getConfirmDialog(Context context, String title, String unConfirmText, String confirmText, String message, DialogInterface.OnClickListener positiveClickListener, DialogInterface.OnClickListener negativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        builder.setMessage(Html.fromHtml(message));
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(Html.fromHtml("<font color='#0c50a6'>" + title + "</font>"));
        }
        builder.setPositiveButton(confirmText, positiveClickListener);
        builder.setNegativeButton(unConfirmText, negativeClickListener);
        return builder;
    }
}
