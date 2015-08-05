package co.snagapp.android.worker.impl;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

import co.snagapp.android.model.Sms;
import co.snagapp.android.worker.SmsReader;

/**
 * Created by zola on 2015/07/29.
 */
public class SmsReaderImpl implements SmsReader {
    @Override
    public List<Sms> getAllSms(Context context) {
        List<Sms> lstSms = new ArrayList<Sms>();
        Sms dummySms = new Sms();
        dummySms.setFolderName("inbox"); dummySms.setId("0719396470"); dummySms.setReadState("1"); dummySms.setTime("Tues.");
        lstSms.add(dummySms);
        return lstSms;
    }
}
