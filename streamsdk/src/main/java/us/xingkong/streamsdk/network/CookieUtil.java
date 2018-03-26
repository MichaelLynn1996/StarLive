package us.xingkong.streamsdk.network;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SeaLynn0 on 2018/1/27.
 */

public class CookieUtil {

    private static final String ISLOGINED = "data";
    private static final String COOKIE = "cookie";

    public static void saveCookiePreference(Context context, String value) {
        SharedPreferences preference = context.getSharedPreferences(ISLOGINED, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(COOKIE, value);
        editor.apply();
    }

    public static String getCookiePreference(Context context) {
        SharedPreferences preference = context.getSharedPreferences(ISLOGINED, Context.MODE_PRIVATE);
        return preference.getString(COOKIE, "");
    }
}
