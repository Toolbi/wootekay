package com.tukkeendoo.app.views.main;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

import com.tukkeendoo.app.R;
import com.tukkeendoo.app.views.base.BaseActivity;
import com.tukkeendoo.app.views.base.BasePage;
import com.tukkeendoo.app.views.main.home.HomePage;
import com.tukkeendoo.app.views.main.trip.TripPage;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private MainPagerAdapter pagerAdapter;
    private ViewPager pager;
    private List<BasePage> pages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buildMenuAndToolBar();
    }

    public void buildMenuAndToolBar() {

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        pages = new LinkedList<>();
        pages.add(new HomePage());
        pages.add(new TripPage());

        pagerAdapter = new MainPagerAdapter(this, pages);

        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);


//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_layout);
//        tabLayout.setupWithViewPager(pager);


//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

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

                break;
            }
            case  R.id.action_my_trips: {

                break;
            }
            case R.id.action_my_vehicles: {

                break;
            }
            case R.id.action_my_messages: {

                break;
            }
            case R.id.action_settings: {

                break;
            }
        }

        //Close the menu
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
