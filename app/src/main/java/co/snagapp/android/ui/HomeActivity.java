package co.snagapp.android.ui;

import co.snagapp.android.Emailer;
import co.snagapp.android.R;
import co.snagapp.android.Sms;
import co.snagapp.android.SpamNumber;
import co.snagapp.android.SpamNumbersAdapter;
import co.snagapp.android.worker.Feedback;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements SMSListFragment.SpamNumberDetailsItemOnClickListener, PhoneInputScreen.OnFragmentInteractionListener, View.OnClickListener{

    DrawerLayout drawerLayout;
    ActionBar actionBar;
    Toolbar toolbar;
    ActionBarDrawerToggle drawerToggle;

    FloatingActionMenu fabMenu;
    private com.github.clans.fab.FloatingActionButton fabAddFromSms;
    private com.github.clans.fab.FloatingActionButton fabAddManually;

    private RecyclerView spamNumbers;
    private LinearLayoutManager mLayoutManager;
    private SpamNumbersAdapter mAdapter;
    private List<Sms> numbers = new ArrayList<>();

    private NavigationView navView;
    private Feedback feedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //finding all the views
        spamNumbers = (RecyclerView) findViewById(R.id.spam_numbers_list);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fabMenu = (FloatingActionMenu) findViewById(R.id.fab_add_number);
        fabAddFromSms = (FloatingActionButton) fabMenu.findViewById(R.id.menu_add_from_sms);
        fabAddManually = (FloatingActionButton) fabMenu.findViewById(R.id.menu_add_manual);

        // action bar
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        navView = (NavigationView) findViewById(R.id.navigation_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open_content_desc, R.string.drawer_close_content_desc);
        drawerLayout.setDrawerListener(drawerToggle);

        actionBar.setTitle(getString(R.string.app_name));

        // list of numbers
        mLayoutManager = new LinearLayoutManager(this);
        spamNumbers.setLayoutManager(mLayoutManager);
        mAdapter = new SpamNumbersAdapter(numbers, this);
        spamNumbers.setAdapter(mAdapter);

        //other items
        feedback = new Emailer();

        setupOnClickListeners();
    }

    public void setupOnClickListeners(){

        fabAddManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fabMenu.close(false);
                fabMenu.hideMenuButton(false);

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_in_bottom, R.anim.slide_out_top);
                transaction.replace(R.id.manual_number_add_container, new PhoneInputScreen());
                transaction.addToBackStack(PhoneInputScreen.class.getName());
                transaction.commit();
            }
        });

        fabAddFromSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fabMenu.close(false);
                fabMenu.hideMenuButton(false);

                getFragmentManager().popBackStackImmediate();

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_out_top);
                transaction.replace(R.id.manual_number_add_container, new SMSListFragment());
                transaction.addToBackStack(SMSListFragment.class.getName());
                transaction.commit();
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()){
                    case R.id.action_about:{
                        Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.action_feedback:{
                        feedback.sendFeedback(HomeActivity.this, getString(R.string.conf_email), getString(R.string.conf_feedbacksubj));
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
            fabMenu.showMenuButton(false);
        }else {
            if (fabMenu.isOpened()){
                fabMenu.close(true);
            }else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onClick(Sms sms) {
        //add sms
        Log.d("foobar", sms.getId());
        onBackPressed();
    }

    @Override
    public void onInputGiven(String number) {
        //add number
        Log.d("foobar", number);
        onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}