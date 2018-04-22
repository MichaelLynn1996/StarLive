package us.xingkong.starlive.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.AppCompatEditText;

public class AlertUtil {

    public static void buildAlert(Context context, int title,
                                  DialogInterface.OnClickListener positiveListener,
                                  DialogInterface.OnClickListener negativeListener) {
        new AlertDialog.Builder(context).setTitle(title)
                .setPositiveButton("确定", positiveListener)
                .setNegativeButton("取消", negativeListener)
                .show();
    }

    public static void buildAlertWithEditText(Context context, int title,final AppCompatEditText et,
                                              DialogInterface.OnClickListener positiveListener,
                                              DialogInterface.OnClickListener negativeListener) {
        new AlertDialog.Builder(context).setTitle(title).setView(et)
                .setPositiveButton("确定", positiveListener)
                .setNegativeButton("取消", negativeListener)
                .show();


    }
}
