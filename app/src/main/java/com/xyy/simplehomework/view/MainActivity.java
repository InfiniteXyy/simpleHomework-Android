package com.xyy.simplehomework.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.fragments.DayFragment;
import com.xyy.simplehomework.view.fragments.SemesterFragment;
import com.xyy.simplehomework.view.fragments.SubjectFragment;
import com.xyy.simplehomework.view.fragments.WeekFragment;
import com.xyy.simplehomework.view.helper.DateHelper;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity implements SubjectFragment.OnFragmentInteractionListener {
    public ProjectViewModel viewModel;
    private Fragment[] fragments;
    private DrawerLayout drawerLayout;
    private TabLayout tabLayout;
    private BottomNavigationView bottomNavigationView;
    private Stack<Integer> fragmentPos;
    private List<Integer> pos2id = Arrays.asList(R.id.day,
            R.id.week,
            R.id.month);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up view model
        viewModel = new ProjectViewModel(this);

        // set up view
        setDefaultFragment();
        setUpTools();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        fragmentPos = new Stack<>();
        fragments = new Fragment[]{
                new DayFragment(),
                new WeekFragment(),
                new SemesterFragment()
        };
        for (Fragment fragment : fragments)
            transaction.add(R.id.mainFragment, fragment);
        transaction.show(fragments[0]).hide(fragments[1]).hide(fragments[2]).commit();
        fragmentPos.push(0);
    }

    private void setUpTools() {
        // init toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("ShWork");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // init drawer layout
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.day);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.set:
                        startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                        break;
                    case R.id.profile:
                        Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                changeFrag(pos2id.indexOf(item.getItemId()));
                return true;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            case R.id.add_projects:
                break;
            default:
        }
        return true;
    }

    public void changeFrag(int position) {
        int old_position = fragmentPos.peek();
        if (position == old_position) return;

        if (tabLayout == null) tabLayout = findViewById(R.id.tabs);
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().hide(fragments[old_position]).show(fragments[position]).commit();

//        transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        if (position == 0) tabLayout.setVisibility(View.VISIBLE);
        else tabLayout.setVisibility(View.GONE);

        if (fragmentPos.contains(position)) fragmentPos.remove(position);
        fragmentPos.push(position);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else {
            if (fragmentPos.size() == 1)
                super.onBackPressed();
            else {
                int old_pos = fragmentPos.pop();
                int new_pos = fragmentPos.peek();
                FragmentManager fm = getSupportFragmentManager();
                fm.beginTransaction().hide(fragments[old_pos]).show(fragments[new_pos]).commit();
                if (new_pos == 0) tabLayout.setVisibility(View.VISIBLE);
                else tabLayout.setVisibility(View.GONE);

                bottomNavigationView.setSelectedItemId(pos2id.get(new_pos));
            }
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

