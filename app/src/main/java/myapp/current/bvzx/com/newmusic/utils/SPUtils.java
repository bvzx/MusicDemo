package myapp.current.bvzx.com.newmusic.utils;

import android.content.Context;
import android.content.SharedPreferences;

import myapp.current.bvzx.com.newmusic.bean.MusicSet;

/**
 * Created by wuhao on 2016/1/5.
 */
public class SPUtils {

    //最近播放的歌曲
    public static final int RECENT_PLAYED_SONG = 0x2;

    //最近播放列表
    public static final int RECENT_PLAYED_SONGS = 0x4;

    //最喜爱的明星
    public static final int FAVOURITE_STAR = 0x6;

    //配置文件名
    public static final String SHAREDPREFERENCES_NAME="config";

    //得到最喜欢的明星
    public static String getFavouriteStar(Context context) {
        SharedPreferences sp=context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        return sp.getString(FAVOURITE_STAR+"","游览,苏打绿,刘德华,");
    }

    public static void setFavouriteStar(Context context,String favouriteStar) {
        SharedPreferences sp=context.getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(FAVOURITE_STAR+"",favouriteStar).commit();
    }
}
