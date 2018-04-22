package us.xingkong.starlive;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by SeaLynn0 on 2018/3/26.
 */

public class StarLiveApplication extends Application {

    private static long exitTime = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (activity.findViewById(R.id.toolbar)!=null){
                    if (activity instanceof AppCompatActivity){
                        ((AppCompatActivity)activity).setSupportActionBar((Toolbar)activity.findViewById(R.id.toolbar));
                    }
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * 退出APP
     */
    public static void exitApp() {
        if (System.currentTimeMillis() - exitTime > 2000) {
//            ToastUtils.shortToast(getAppContext(), appContext.getString(R.string.text_press_again));
            exitTime = System.currentTimeMillis();
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
