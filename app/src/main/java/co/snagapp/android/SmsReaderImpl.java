package co.snagapp.android;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import co.snagapp.android.worker.SmsReader;

/**
 * Created by zola on 2015/07/29.
 */
public class SmsReaderImpl implements SmsReader {
    @Override
    public List<Sms> getAllSms(Context context) {
        List<Sms> lstSms = new ArrayList<Sms>();

        Uri uriSms = Uri.parse("content://sms");
        Cursor cursor = context.getContentResolver().query(uriSms, null, null, null, null);

        if (cursor != null) {
            cursor.moveToLast();
            if (cursor.getCount() > 0) {
                do {
                    Sms message = new Sms();
                    message.setId(cursor.getString(cursor
                            .getColumnIndex("address")));
                    message.setMsg(cursor.getString(cursor.getColumnIndex("body")));
                    lstSms.add(message);
                } while (cursor.moveToPrevious());
            }
        }

        return lstSms;
    }
}
