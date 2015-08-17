package co.snagapp.android.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.etsy.android.grid.StaggeredGridView;

import java.util.List;

import co.snagapp.android.R;
import co.snagapp.android.ui.adapter.DefaultSmsAppAdapter;
import co.snagapp.android.worker.impl.SmsManager;

public class DefaultSmsAppActivity extends AppCompatActivity {

    private StaggeredGridView smsAppChoices;
    private SmsManager smsManager;
    private LayoutInflater inflater;
    private List<String> apps;

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
        smsAppChoices.setAdapter(new DefaultSmsAppAdapter(this, R.layout.material_radio_button, apps));
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

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
