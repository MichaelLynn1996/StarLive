package us.xingkong.starlive.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

    private static final String APP_DATA = "data";
    private static final String NICKNAME = "nickname";
    private static final String USERNAME = "username";
    private static final String IS_LOGGED = "logged";

    public static void saveLoginstatus(Context context, boolean isLogged, String nickname, String username) {
        SharedPreferences preference = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(IS_LOGGED, isLogged);
        editor.putString(NICKNAME, nickname);
        editor.putString(USERNAME, username);
        editor.apply();
    }

    public static boolean getLoginstatus(Context context){
        SharedPreferences preference = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
        return preference.getBoolean(IS_LOGGED,false);
    }

    public static String getLoginNickname(Context context){
        SharedPreferences preference = context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE);
        return preference.getString(NICKNAME,"");
    }
}
