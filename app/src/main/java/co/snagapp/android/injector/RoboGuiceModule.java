package co.snagapp.android.injector;

import android.content.Context;

import com.google.inject.Binder;
import com.google.inject.Module;

import co.snagapp.android.ui.HomeActivity;
import co.snagapp.android.worker.DataPersister;
import co.snagapp.android.worker.Feedback;
import co.snagapp.android.worker.ImageLoader;
import co.snagapp.android.worker.SmsReader;
import co.snagapp.android.worker.impl.Emailer;
import co.snagapp.android.worker.impl.PicassoImageLoader;
import co.snagapp.android.worker.impl.SmsReaderImpl;

/**
 * Created on 2015/08/05.
 */
public class RoboGuiceModule implements Module {

    Context context;

    public RoboGuiceModule(Context someContext){
        context = someContext;
    }

    @Override
    public void configure(Binder binder) {
        //binder.bind(DataPersister.DataPersistenceEventListener.class).to(HomeActivity.class);
        binder.bind(DataPersister.class).toProvider(DataPersisterProvider.class);
        binder.bind(SmsReader.class).to(SmsReaderImpl.class);
        binder.bind(ImageLoader.class).to(PicassoImageLoader.class);
        binder.bind(Feedback.class).to(Emailer.class);
    }
}
