package com.liang.sharedpreferences02;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.StackView;

/**
 * 强制下线广播
 */
public class ForceOfflineReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        //创建弹出框AlertDialog的构造对象builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //设置弹出框标题
        builder.setTitle("Waring");
        //设置弹出框信息
        builder.setMessage("You are forced to be offline,please try to login again!");
        //设置弹出框不可取消
        builder.setCancelable(false);
        //设置OK
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //销毁所有活动
                ActivityCollector.finishAll();
                //重新启动登录页面
                Intent intent = new Intent(context,LoginActivity.class);
                //由于是在广播接收器里启动活动的，因此一定要给intent加入FLAG_ACTIVITY_NEW_TASK这个标志
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //重新启动LoginActivity
                context.startActivity(intent);
            }
        });

        //创建弹出框对象
        AlertDialog alertDialog = builder.create();

        //必须设置AlertDialog的类型，保证其在广播接收器中可以正常弹出
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

        //弹出AlertDialog
        alertDialog.show();
    }
}
