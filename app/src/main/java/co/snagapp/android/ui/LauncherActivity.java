package co.snagapp.android.ui;

import com.afollestad.materialdialogs.AlertDialogWrapper;
import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.ArrayList;
import java.util.List;

import co.snagapp.android.Constants;
import co.snagapp.android.R;
import co.snagapp.android.settings.SettingsUtil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class LauncherActivity extends AppCompatActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());
        setContentView(R.layout.viewpager);
        initialize();
    }

    private void initialize(){
        String introShown = SettingsUtil.getInstance(this).get(Constants.INTRO_SHOWN);
        if (TextUtils.isEmpty(introShown)){
            showIntro();
        }else{
            boolean hasIntroBeenShown = Boolean.parseBoolean(introShown);
            if (hasIntroBeenShown){
                startBtnClicked(new Button(this));
            }else{
                showIntro();
            }
        }
    }

    private void showIntro(){
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new LauncherPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void startBtnClicked(View v) {
        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        SettingsUtil.getInstance(this).save(Constants.INTRO_SHOWN, true);
    }
    
    private class LauncherPagerAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments;

        public LauncherPagerAdapter(FragmentManager fm) {
            super(fm);
            this.mFragments = new ArrayList<Fragment>();
            mFragments.add(new LauncherFragmentA());
            mFragments.add(new LauncherFragmentB());
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}