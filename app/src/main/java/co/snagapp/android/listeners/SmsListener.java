package co.snagapp.android.listeners;

import co.snagapp.android.worker.DataPersister;
import co.snagapp.android.worker.impl.SmsManager;
import roboguice.RoboGuice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import javax.inject.Inject;

public class SmsListener extends BroadcastReceiver {
    @Inject
    private DataPersister dataPersister;
    SmsManager smsManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        RoboGuice.getInjector(context).injectMembersWithoutViews(this);

        if (smsManager==null){
            smsManager = new SmsManager(context);
        }

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            SmsMessage [] msgs = null;
            String msgFrom;
            String msgBody;
            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msgFrom = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();

                        if (dataPersister!=null && !dataPersister.isNumberBlocked(msgFrom)){
                            //number is not blocked, redirect sms to default sms app
                            smsManager.sendSmsToDefaultApp(intent);
                        }
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
