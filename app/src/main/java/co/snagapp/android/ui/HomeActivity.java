package co.snagapp.android.ui;

import co.snagapp.android.R;
import co.snagapp.android.SpamNumber;
import co.snagapp.android.SpamNumbersAdapter;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

public class HomeActivity extends AppCompatActivity implements SpamNumbersAdapter.SpamNumberDetailsItemOnClickListener {

    DrawerLayout drawerLayout;
    ActionBar actionBar;
    Toolbar toolbar;
    FloatingActionButton fab;
    ActionBarDrawerToggle drawerToggle;

    private RecyclerView spamNumbers;
    private LinearLayoutManager mLayoutManager;
    private SpamNumbersAdapter mAdapter;
    private Collection<SpamNumber> numbers = new ArrayList<>();

    NavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //finding all the views
        spamNumbers = (RecyclerView) findViewById(R.id.spam_numbers_list);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab_add_number);

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

        setupOnClickListeners();
    }

    public void setupOnClickListeners(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "add new", Toast.LENGTH_SHORT).show();
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Toast.makeText(HomeActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }
    @Override
    public void onClick(SpamNumber spamNumber) {

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