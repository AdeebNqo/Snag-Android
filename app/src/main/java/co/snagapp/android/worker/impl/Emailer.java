package co.snagapp.android.worker.impl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import co.snagapp.android.R;
import co.snagapp.android.worker.Feedback;

public class Emailer implements Feedback {
    @Override
    public void sendFeedback(Context context, String email, String subject) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", email, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        context.startActivity(Intent.createChooser(emailIntent, context.getString(R.string.send_feedback)));

    }
}
