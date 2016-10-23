package com.tukkeendoo.app.views.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.controllers.Controller;
import com.tukkeendoo.app.views.base.BaseActivity;
import com.tukkeendoo.app.views.base.BasePage;
import com.tukkeendoo.app.views.main.home.HomePage;
import com.tukkeendoo.app.views.main.message.MessagePage;
import com.tukkeendoo.app.views.main.trip.TripPage;
import com.tukkeendoo.app.views.main.vehicle.VehiclePage;
import com.tukkeendoo.app.views.setting.SettingActivity;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final int HOME_POSITION = 0;
    public static final int MY_TRIPS_POSITION = 1;
    public static final int MY_VEHICLES_POSITION = 2;
    public static final int MY_MESSAGES_POSITION = 3;
    public static final int SETTINGS_POSITION = 4;

    private MainPagerAdapter pagerAdapter;
    private ViewPager pager;
    private List<BasePage> pages;

    private View.OnClickListener fabButtonClickListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (pager.getCurrentItem()){
                case MY_VEHICLES_POSITION:
                    break;
                case MY_MESSAGES_POSITION:
                    break;
                default:

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildMenuAndToolBar();
        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab);
        fabButton.setOnClickListener(fabButtonClickListener);
    }

    public void buildMenuAndToolBar() {

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.search_a_trip);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        pages = new LinkedList<>();
        pages.add(HOME_POSITION, new HomePage());
        pages.add(MY_TRIPS_POSITION, new TripPage());
        pages.add(MY_VEHICLES_POSITION, new VehiclePage());
        pages.add(MY_MESSAGES_POSITION, new MessagePage());

        pagerAdapter = new MainPagerAdapter(this, pages);


        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_menu_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.app_menu, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //TODO chercher le text entré en paramètre
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //TODO appliquer un filtre et proposer une liste de mots auquels l'utilisateur pourrait acoir envie de taper
//                return false;
//            }
//        });
//
//        return true;
//    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        switch (item.getItemId()) {

            case R.id.action_home: {
                pager.setCurrentItem(HOME_POSITION);
                break;
            }
            case  R.id.action_my_trips: {
                pager.setCurrentItem(MY_TRIPS_POSITION);
                break;
            }
            case R.id.action_my_vehicles: {
                pager.setCurrentItem(MY_VEHICLES_POSITION);
                break;
            }
            case R.id.action_my_messages: {
                pager.setCurrentItem(MY_MESSAGES_POSITION);
                break;
            }
            case R.id.action_settings: {
                Controller.startActivity(this, SettingActivity.class, false);
                break;
            }
        }

        //Close the menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
