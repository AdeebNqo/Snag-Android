package co.snagapp.android.worker;

import java.util.Collection;

/**
 * Created by zola on 2015/07/17.
 */
public interface IClassifier {
    public boolean isSpam(String sms);
    public void addSpamSMS(String from, String sms);
    public void train(String from, String sms);
    public Collection getLikelySpamMessages();
}
