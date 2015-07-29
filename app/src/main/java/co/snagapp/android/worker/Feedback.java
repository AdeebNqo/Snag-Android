package co.snagapp.android.worker;

import android.content.Context;

/**
 * Created by zola on 2015/07/28.
 */
public interface Feedback {
    void sendFeedback(Context context, String email, String subject);
}