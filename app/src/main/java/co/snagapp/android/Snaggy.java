package co.snagapp.android;

import android.app.Application;

import co.snagapp.android.injector.RoboGuiceModule;
import roboguice.RoboGuice;


public class Snaggy extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RoboGuice.getOrCreateBaseApplicationInjector(this, RoboGuice.DEFAULT_STAGE,
                RoboGuice.newDefaultRoboModule(this), new RoboGuiceModule(getApplicationContext()));
    }

}