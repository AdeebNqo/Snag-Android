package co.snagapp.android.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import com.etsy.android.grid.StaggeredGridView;
import java.util.List;
import co.snagapp.android.R;
import co.snagapp.android.ui.adapter.DefaultSmsAppAdapter;
import co.snagapp.android.worker.impl.SmsManager;
import roboguice.activity.RoboActionBarActivity;

public class DefaultSmsAppActivity extends RoboActionBarActivity {

    private StaggeredGridView smsAppChoices;
    private SmsManager smsManager;
    private LayoutInflater inflater;
    private List<String> apps;
    private DefaultSmsAppAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_sms_app);

        loadSmsApps();
        loadViews();
    }

    private void loadViews(){
        smsAppChoices = (StaggeredGridView) findViewById(R.id.sms_choices);
        inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adapter = new DefaultSmsAppAdapter(this, R.layout.sms_app_view, apps);
        smsAppChoices.setAdapter(adapter);
    }

    private void loadSmsApps(){
        if (smsManager == null){
            smsManager = new SmsManager(this);
        }
        apps =  smsManager.getAllNamesOfSmsApps();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_default_sms_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String appName = adapter.getSelectedApplication();
        if (!TextUtils.isEmpty(appName)){
            smsManager.saveDefaultApp(adapter.getSelectedApplication());
        }
    }
}
