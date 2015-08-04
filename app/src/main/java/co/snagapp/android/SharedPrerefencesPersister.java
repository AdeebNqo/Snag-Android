package co.snagapp.android;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import co.snagapp.android.worker.DataPersister;

/**
 * Created by zola on 2015/08/04.
 */
public class SharedPrerefencesPersister implements DataPersister {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String filename = SharedPrerefencesPersister.class.getName();
    private DataPersistenceEventListener dataPersistenceEventListener;

    public SharedPrerefencesPersister(Context context, DataPersistenceEventListener listener){
        sharedPreferences = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        dataPersistenceEventListener = listener;
    }

    @Override
    public Collection<String> getAllBlockedNumbers() {
        Collection<String> someCollection = new ArrayList<String>();
        Map<String, ?> map = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : map.entrySet()) {
            someCollection.add(entry.getKey());
        }
        return someCollection;
    }

    @Override
    public void addNumberToBlockedNumbers(String number) {
        editor = sharedPreferences.edit();
        editor.putString(number, number);
        editor.commit();
        dataPersistenceEventListener.onItemAdded();
    }

}
