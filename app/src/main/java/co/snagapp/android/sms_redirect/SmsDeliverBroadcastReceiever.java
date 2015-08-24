package co.snagapp.android.sms_redirect;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import co.snagapp.android.worker.impl.SmsManager;

public class SmsDeliverBroadcastReceiever extends BroadcastReceiver{
    private SmsManager smsManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(), SmsDeliverBroadcastReceiever.class.getName(), Toast.LENGTH_SHORT).show();
        if (smsManager==null){
            smsManager = new SmsManager(context);
        }
        smsManager.sendSmsToDefaultApp(intent);
    }
}
