package com.dl.dlexerciseandroid.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.overview.OverviewFragment;
import com.dl.dlexerciseandroid.test.TestFragment;
import com.dl.dlexerciseandroid.utility.Utils;


/**
 * Created by logicmelody on 2016/3/28.
 */
public class UIController {

    private AppCompatActivity mActivity;
    private FragmentManager mFragmentManager;

    private Fragment mCurrentFragment;

    private Toolbar mToolBar;
    private ActionBar mActionBar;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;


    public UIController(AppCompatActivity activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

    public void onCreate(Bundle savedInstanceState) {
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
        setupInitFragment();
        setupDrawerLayout();
        setupLeftDrawer();
    }

    private void findViews() {
        mToolBar = (Toolbar) mActivity.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout_main);
        mNavigationView = (NavigationView) mActivity.findViewById(R.id.navigation_view_main_left_side_drawer);

        // 如果我們想要get HeaderView裡面的其他View，比如：Button, TextView，我們可以用以下方式來取得HeaderView
        // There is usually only 1 header view.
        // Multiple header views can technically be added at runtime.
        // We can use navigationView.getHeaderCount() to determine the total number.
        View headerLayout = mNavigationView.getHeaderView(0);
    }

    private void setupActionBar() {
        mActivity.setSupportActionBar(mToolBar);
        mActionBar = mActivity.getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(mActivity.getString(R.string.all_overview));
            //mActionBar.setSubtitle(mActivity.getString(R.string.all_app_version));
        }
    }

    private void setupInitFragment() {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(Utils.FragmentTag.OVERVIEW);

        if (fragment == null) {
            fragment = new OverviewFragment();
            transaction.add(R.id.frame_layout_main_container, fragment, Utils.FragmentTag.OVERVIEW);
        }

        mCurrentFragment = fragment;

        transaction.commit();
    }

    private void setupDrawerLayout() {
        // ActionBarDrawerToggle這個class是將原本action bar(Toolbar)上的up button變成hamburger的關鍵，
        // 同時他也可以當成drawer layout的callback listener使用
        // 他有兩種constructor：
        //
        // public ActionBarDrawerToggle (Activity activity, DrawerLayout drawerLayout, Toolbar toolbar,
        //                               int openDrawerContentDescRes, int closeDrawerContentDescRes)
        //
        // public ActionBarDrawerToggle (Activity activity, DrawerLayout drawerLayout,
        //                               int openDrawerContentDescRes, int closeDrawerContentDescRes)
        //
        // 差別在於有沒有傳Toolbar的object，如果這個Activity有用Toolbar取代掉原本的Action bar，記得要用有傳Toolbar的他有兩種constructor
        mActionBarDrawerToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, mToolBar,
                                                           R.string.all_drawer_open, R.string.all_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    private void setupLeftDrawer() {
        // NavigationView要設callback listener，處理click item之後要做什麼事情，這邊是replace對應的Fragment
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_left_drawer_overview:
                        replaceFragmentTo(OverviewFragment.class, Utils.FragmentTag.OVERVIEW);
                        break;

                    case R.id.menu_item_left_drawer_test:
                        replaceFragmentTo(TestFragment.class, Utils.FragmentTag.TEST);
                        break;
                }

                item.setChecked(true);
                mActionBar.setTitle(item.getTitle());
                closeLeftDrawer();

                return true;
            }
        });
    }

    private void replaceFragmentTo(Class<?> fragmentClass, String fragmentTag) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTag);
        if (fragment == null) {
            try {
                fragment = (Fragment) fragmentClass.newInstance();
                fragmentTransaction.replace(R.id.frame_layout_main_container, fragment, fragmentTag);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        mCurrentFragment = fragment;

        fragmentTransaction.commit();
    }

    private void closeLeftDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void onPostCreate(Bundle savedInstanceState) {
        // 要記得在Activity的onPostCreate()中call mActionBarDrawerToggle.syncState()，不然hamburger會沒有變化
        // 使用DrawerToggle一定要完成此步驟

        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        // 使用DrawerToggle一定要完成此步驟

        // Pass any configuration change to the drawer toggles
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // 使用DrawerToggle一定要完成此步驟
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;

        } else {
            switch (item.getItemId()) {
                default:
                    return false;
            }
        }
    }
}
