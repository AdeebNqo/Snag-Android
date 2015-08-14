package co.snagapp.android.worker;

import android.content.Context;
import java.util.List;
import co.snagapp.android.model.Sms;

/**
 * Created by zola on 2015/07/29.
 */
public interface SmsReader {
    List<Sms> getAllSms(Context context);
}
