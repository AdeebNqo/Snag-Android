package co.snagapp.android.ui;

import co.snagapp.android.R;
import co.snagapp.android.model.Sms;
import co.snagapp.android.ui.adapter.SpamNumbersAdapter;
import co.snagapp.android.worker.DataPersister;
import co.snagapp.android.worker.Feedback;
import roboguice.RoboGuice;
import roboguice.activity.RoboActionBarActivity;
import roboguice.activity.RoboActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;

public class HomeActivity extends RoboActionBarActivity implements SMSListFragment.SpamNumberDetailsItemOnClickListener, PhoneInputScreen.OnFragmentInteractionListener, View.OnClickListener, DataPersister.DataPersistenceEventListener {

    private DrawerLayout drawerLayout;
    private ActionBar actionBar;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;

    private FloatingActionMenu fabMenu;
    private com.github.clans.fab.FloatingActionButton fabAddFromSms;
    private com.github.clans.fab.FloatingActionButton fabAddManually;

    private RecyclerView spamNumbers;
    private LinearLayoutManager mLayoutManager;
    private SpamNumbersAdapter mAdapter;
    private List<Sms> numbers = new ArrayList<>();
    ItemTouchHelper.SimpleCallback listItemSwipeListener;

    private NavigationView navView;

    @Inject
    private Feedback feedback;
    @Inject
    private DataPersister dataPersister;


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
        listItemSwipeListener = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                mAdapter.swapItems(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                Toast.makeText(HomeActivity.this, getString(R.string.removed), Toast.LENGTH_SHORT).show();
                int position = viewHolder.getAdapterPosition();
                dataPersister.removeNumberFromBlockedNumbers(numbers.get(position).getId());
                numbers.remove(position);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(listItemSwipeListener);
        itemTouchHelper.attachToRecyclerView(spamNumbers);

        loadSavedData();
        setupOnClickListeners();
    }

    private void loadSavedData(){
        Collection<String> blockedNumbers = dataPersister.getAllBlockedNumbers();
        Iterator<String> numIterator = blockedNumbers.iterator();
        while(numIterator.hasNext()){
            Sms sms = new Sms();
            sms.setId(numIterator.next());
            numbers.add(sms);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemAdded() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemRemoved() {
        mAdapter.notifyDataSetChanged();
    }

    public void setupOnClickListeners(){

        fabAddManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new PhoneInputScreen());
            }
        });

        fabAddFromSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(new SMSListFragment());
            }
        });

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.action_about: {
                        Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.action_feedback: {
                        feedback.sendFeedback(HomeActivity.this, getString(R.string.conf_email), getString(R.string.conf_feedbacksubj));
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void showFragment(Fragment someFragment){

        fabMenu.close(false);
        fabMenu.hideMenuButton(false);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_in_bottom, R.anim.slide_out_top, R.anim.slide_out_top);
        transaction.replace(R.id.manual_number_add_container, someFragment);
        transaction.addToBackStack(someFragment.getClass().getName());
        transaction.commit();
    }

    private boolean isFloatingFragmentVisible(){
        boolean isVisible = getSupportFragmentManager().getBackStackEntryCount() > 0;
        return  isVisible;
    }

    private void closeFloatingFragment(){
        getSupportFragmentManager().popBackStack();
        fabMenu.showMenuButton(false);
    }

    @Override
    public void onBackPressed() {
        if (isFloatingFragmentVisible()){
            closeFloatingFragment();
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
        dataPersister.addNumberToBlockedNumbers(sms.getId());
        numbers.add(sms);
        onBackPressed();
    }

    @Override
    public void onInputGiven(String number) {
        //add number
        dataPersister.addNumberToBlockedNumbers(number);
        Sms sms = new Sms();
        sms.setId(number);
        numbers.add(sms);
        onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isFloatingFragmentVisible())
            closeFloatingFragment();

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