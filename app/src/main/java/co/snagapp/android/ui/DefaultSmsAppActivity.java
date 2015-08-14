package co.snagapp.android.ui;

import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.gson.Gson;

import java.util.List;

import co.snagapp.android.R;
import co.snagapp.android.worker.impl.SmsManager;

public class DefaultSmsAppActivity extends AppCompatActivity {

    private RadioGroup smsAppChoices;

    private SmsManager smsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_sms_app);



        loadViews();
        loadSmsApps();
    }

    private void loadViews(){
        smsAppChoices = (RadioGroup) findViewById(R.id.sms_choices);
    }

    private void loadSmsApps(){
        if (smsManager == null){
            smsManager = new SmsManager(this);
        }

        List<String> apps =  smsManager.getAllNamesOfSmsApps();
        for (String appName : apps){

            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(appName);
            smsAppChoices.addView(radioButton);
        }
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
