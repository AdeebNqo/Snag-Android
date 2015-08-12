package co.snagapp.android.listeners;

import com.google.inject.Singleton;

import co.snagapp.android.ui.adapter.SpamNumbersAdapter;
import co.snagapp.android.worker.DataPersister;

/**
 * Created on 2015/08/12.
 */
@Singleton
public class MainAdapterWatcher implements DataPersister.DataPersistenceEventListener {

    private SpamNumbersAdapter mAdapter;

    public void setAdapter(SpamNumbersAdapter someAdapter){
        mAdapter = someAdapter;
    }

    @Override
    public void onItemAdded() {
        refreshAdapter();
    }

    @Override
    public void onItemRemoved() {
        refreshAdapter();
    }

    private void refreshAdapter(){
        if (mAdapter!=null){
            mAdapter.notifyDataSetChanged();
        }
    }
}
