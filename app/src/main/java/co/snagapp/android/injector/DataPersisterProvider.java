package co.snagapp.android.injector;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Provider;

import co.snagapp.android.worker.DataPersister;
import co.snagapp.android.worker.impl.SharedPrerefencesPersister;

/**
 * Created on 2015/08/06.
 */
public class DataPersisterProvider implements Provider<DataPersister> {
    Context context;
    DataPersister.DataPersistenceEventListener listener;

    @Inject
    public DataPersisterProvider(Context context, DataPersister.DataPersistenceEventListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    public DataPersister get() {
        return new SharedPrerefencesPersister(context, listener);
    }
}
