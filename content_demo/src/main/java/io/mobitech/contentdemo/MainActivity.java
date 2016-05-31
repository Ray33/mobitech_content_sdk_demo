package io.mobitech.contentdemo;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import io.mobitech.contentdemo.fragments.DocumentContainerFragment;
import io.mobitech.contentdemo.fragments.EntitiesContainerFragment;
import io.mobitech.contentdemo.fragments.InterestsCategoriesContainerFragment;
import io.mobitech.contentdemo.fragments.InterestsDocumentsContainerFragment;
import io.mobitech.contentdemo.fragments.MainScreenContainerFragment;
import io.mobitech.contentdemo.fragments.RecommendationsContainerFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        onNavigationItemSelected(null);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

       int id = item!=null ?  item.getItemId() :  Integer.MAX_VALUE;;

        Fragment fragment = null;

        if (id == R.id.recommendations) {
            fragment = new RecommendationsContainerFragment();
        } else if (id == R.id.interests_categories) {
            fragment = new InterestsCategoriesContainerFragment();
        } else if (id == R.id.interests_document) {
            fragment = new InterestsDocumentsContainerFragment();
        } else if (id == R.id.entities) {
            fragment = new EntitiesContainerFragment();
        } else if (id == R.id.get_a_document){
            fragment = new DocumentContainerFragment();
        }else{
            fragment = new MainScreenContainerFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        }catch (IllegalStateException e){
            Log.w("MainActivity",e.getMessage(),e);
        }

        return true;
    }
}
