package co.snagapp.android.worker.impl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import co.snagapp.android.worker.DataPersister;

/**
 * Created on 2015/08/14.
 */
public class SmsManager {

    @Inject
    DataPersister dataPersister;
    private Intent smsIntent;
    private List<ResolveInfo> resolveInfoList;
    private Context context;
    PackageManager packageManager;

    public SmsManager(Context context){
        this.context = context;

        packageManager = context.getPackageManager();

        smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");

        resolveInfoList = context.getPackageManager()
                .queryIntentActivities(smsIntent, 0);
    }

    public void sendSmsToDefaultApp(Intent intent){
        ActivityInfo activity= getDefaultApp().activityInfo;
        ComponentName name=new ComponentName(activity.applicationInfo.packageName,
                activity.name);
        Intent i=new Intent(Intent.ACTION_MAIN);

        i.addCategory(Intent.CATEGORY_LAUNCHER);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        i.setComponent(name);

        context.startActivity(i);
    }

    private ResolveInfo getDefaultApp(){
        return dataPersister.getDefaultApp();
    }

    public List<ResolveInfo> getAllSmsApps(){
        return resolveInfoList;
    }

    public List<String> getAllNamesOfSmsApps(){
        List<String> names = new ArrayList<>();
        for (ResolveInfo resolveInfo : resolveInfoList){
            names.add(resolveInfo.activityInfo.applicationInfo.loadLabel(packageManager).toString());
        }
        return names;
    }
}
