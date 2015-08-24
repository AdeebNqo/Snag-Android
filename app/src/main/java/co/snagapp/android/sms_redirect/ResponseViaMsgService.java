package co.snagapp.android.sms_redirect;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import co.snagapp.android.worker.impl.SmsManager;

public class ResponseViaMsgService extends Service {
    private SmsManager smsManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this.getApplicationContext(), ResponseViaMsgService.class.getName(), Toast.LENGTH_SHORT).show();
        if (smsManager==null){
            smsManager = new SmsManager(this);
        }
        smsManager.sendSmsToDefaultApp(intent);
        return null;
    }
}