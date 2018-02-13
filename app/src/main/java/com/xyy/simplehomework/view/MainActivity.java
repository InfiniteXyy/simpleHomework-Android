package com.xyy.simplehomework.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mikepenz.crossfadedrawerlayout.view.CrossfadeDrawerLayout;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.MiniDrawer;
import com.mikepenz.materialdrawer.interfaces.ICrossfader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;
import com.mikepenz.materialize.util.UIUtils;
import com.xyy.simplehomework.R;
import com.xyy.simplehomework.view.fragments.DayFragment;
import com.xyy.simplehomework.view.fragments.SemesterFragment;
import com.xyy.simplehomework.view.fragments.WeekFragment;
import com.xyy.simplehomework.view.helper.DialogHelper;
import com.xyy.simplehomework.view.helper.TitleSwitcher;
import com.xyy.simplehomework.viewmodel.ProjectViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public TitleSwitcher titleSwitcher;
    public ProjectViewModel viewModel;
    private Drawer result;
    private AccountHeader header;
    private WeekFragment weekFragment;
    private DayFragment dayFragment;
    private SemesterFragment semesterFragment;
    private long exitTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up view
        setDefaultFragment();
        setUpTools(savedInstanceState);

        // set up view model
        viewModel = new ProjectViewModel(this);
    }

    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        dayFragment = new DayFragment();
        transaction.add(R.id.mainFragment, dayFragment);
        weekFragment = new WeekFragment();
        transaction.add(R.id.mainFragment, weekFragment);
        semesterFragment = new SemesterFragment();
        transaction.show(dayFragment).hide(weekFragment).hide(semesterFragment);
        transaction.commitAllowingStateLoss();
    }

    private void setUpTools(Bundle state) {
        // init toolbar
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(new SimpleDateFormat("M.d").format(new Date()));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // init AppBar
        titleSwitcher = new TitleSwitcher(this);
        AppBarLayout appBarLayout = findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int newAlpha = 255 + verticalOffset;
                newAlpha = newAlpha < 0 ? 0 : newAlpha;
                titleSwitcher.setAlpha((float) newAlpha / 255);
            }
        });

        // init result

        final IProfile profile = new ProfileDrawerItem().withName("ハクメイ").withEmail("Hakumei@gmail.com").withIcon(R.drawable.avatar1);
        final IProfile profile2 = new ProfileDrawerItem().withName("ミコチ").withEmail("mikochi@gmail.com").withIcon(R.drawable.avatar2);
        header = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.headerbg)
                .withSavedInstance(state)
                .addProfiles(
                        profile, profile2
                ).build();

        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
                .withDrawerLayout(R.layout.drawer_crossfader)
                .withDrawerWidthDp(72)
                .withGenerateMiniDrawer(true)
                .withAccountHeader(header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.day).withIcon(R.drawable.ic_today_black_24px).withSetSelected(true).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.week).withIcon(R.drawable.ic_view_week_black_24px).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.month).withIcon(R.drawable.ic_assignment_black_24px).withIdentifier(3),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.setting).withIcon(R.drawable.ic_settings_black_24px).withIdentifier(4),
                        new SecondaryDrawerItem().withName(R.string.year).withIcon(R.drawable.ic_account_circle_black_24px).withEnabled(false).withIdentifier(5)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // get transaction helper
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                        switch (position) {
                            case 1:
                                transaction.show(dayFragment).hide(weekFragment).hide(semesterFragment).commit();
                                titleSwitcher.changeFragmentTitle(TitleSwitcher.DAY);
                                break;
                            case 2:
                                transaction.show(weekFragment).hide(dayFragment).hide(semesterFragment).commit();
                                titleSwitcher.changeFragmentTitle(TitleSwitcher.WEEK);
                                break;
                            case 3:
                                transaction.show(semesterFragment).hide(weekFragment).hide(dayFragment).commit();
                                titleSwitcher.changeFragmentTitle(TitleSwitcher.SEMESTER);
                                break;
                            case 5:
                                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                                break;
                            case 6:
                                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                                startActivity(intent);
                                break;
                        }
                        return false;
                    }
                })
                .withShowDrawerOnFirstLaunch(true)
                .withSavedInstance(state)
                .build();
        final CrossfadeDrawerLayout crossfadeDrawerLayout = (CrossfadeDrawerLayout) result.getDrawerLayout();
        crossfadeDrawerLayout.setMaxWidthPx(DrawerUIUtils.getOptimalDrawerWidth(this));

        final MiniDrawer miniResult = result.getMiniDrawer();
        View view = miniResult.build(this);
        view.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(this, com.mikepenz.materialdrawer.R.attr.material_drawer_background, com.mikepenz.materialdrawer.R.color.material_drawer_background));
        crossfadeDrawerLayout.getSmallView().addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        miniResult.withCrossFader(new ICrossfader() {
            @Override
            public void crossfade() {
                boolean isFaded = isCrossfaded();
                crossfadeDrawerLayout.crossfade(400);

                if (isFaded) {
                    result.getDrawerLayout().closeDrawer(GravityCompat.START);
                }
            }

            @Override
            public boolean isCrossfaded() {
                return crossfadeDrawerLayout.isCrossfaded();
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
            case R.id.add_projects:
                new DialogHelper(this).show();
                break;
            default:
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        //handle the back press :D close the result first and if the result is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //add the values which need to be saved from the result to the bundle
        outState = result.saveInstanceState(outState);
        //add the values which need to be saved from the accountHeader to the bundle
        outState = header.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
}

