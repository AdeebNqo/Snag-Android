package co.snagapp.android.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by zola on 2015/07/15.
 */
public class SettingsUtil {
    private SharedPreferences mSettings;
    private Context mContext;

    private static SettingsUtil instance;
    public static SettingsUtil getInstance(Context context){
        if (instance == null){
            instance = new SettingsUtil(context);
        }
        return instance;
    }
    private SettingsUtil(Context context){
        mContext = context;
        mSettings = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public void save(String key, boolean value){
        save(key, value ? "true" : "false");
    }
    public void save(String key, String value){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(key, value);
        editor.commit();
    }
    /**
     *
     */
    public String get(String key){
        return mSettings.getString(key, "");
    }
}
