package co.snagapp.android.worker;

import java.util.Collection;

/**
 * Created by zola on 2015/08/04.
 */
public interface DataPersister {
    Collection<String> getAllBlockedNumbers();
    void addNumberToBlockedNumbers(String number);

    interface DataPersistenceEventListener{
        void onItemAdded();
    }
}
