package co.snagapp.android.worker.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import co.snagapp.android.worker.DataPersister;

/**
 * Created by zola on 2015/08/04.
 */
public class SharedPrerefencesPersister implements DataPersister {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String filename = SharedPrerefencesPersister.class.getName();
    private DataPersistenceEventListener dataPersistenceEventListener;

    private Gson jsonBuilder;

    private String defaultSMSApp = "DEFAULT_SMS_APP";

    @Inject
    public SharedPrerefencesPersister(Context context, DataPersistenceEventListener listener){
        sharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        dataPersistenceEventListener = listener;
        jsonBuilder = new Gson();
    }

    @Override
    public Collection<String> getAllBlockedNumbers() {
        Collection<String> someCollection = new ArrayList<String>();
        Map<String, ?> map = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            someCollection.add(entry.getKey());
        }
        return someCollection;
    }

    @Override
    public void addNumberToBlockedNumbers(String number) {
        editor = sharedPreferences.edit();
        editor.putString(number, number);
        editor.commit();
        dataPersistenceEventListener.onItemAdded();
    }

    @Override
    public void saveDefaultApp(ResolveInfo resolveInfo) {
        String serializedResolvedInfo  = jsonBuilder.toJson(resolveInfo, ResolveInfo.class);
        editor = sharedPreferences.edit();
        editor.putString(defaultSMSApp, serializedResolvedInfo);
        editor.commit();
    }

    @Override
    public ResolveInfo getDefaultApp() {
        String serializedResolvedInfo = sharedPreferences.getString(defaultSMSApp, "{}");
        ResolveInfo resolveInfo = jsonBuilder.fromJson(serializedResolvedInfo, ResolveInfo.class);
        return resolveInfo;
    }

    @Override
    public void removeNumberFromBlockedNumbers(String number) {
        sharedPreferences.edit().remove(number).commit();
        dataPersistenceEventListener.onItemRemoved();
    }

    @Override
    public boolean isNumberBlocked(String number) {
        return sharedPreferences.contains(number);
    }
}
