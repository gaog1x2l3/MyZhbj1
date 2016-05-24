package mobiletrain.org.myzhbj1.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/5/19.
 * SharePreference封装
 */
public class PrefUtils {
    public static final String PREF_NAME = "config";//ctrl+shift+u,大小写切换

    public static boolean getBoolean(Context ctx, String key, boolean defaultVaule) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultVaule);
    }

    public static void setBoolean(Context ctx, String key, boolean value) {
        SharedPreferences sp = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();//ctrl+shift+l  格式化代码
    }
}
