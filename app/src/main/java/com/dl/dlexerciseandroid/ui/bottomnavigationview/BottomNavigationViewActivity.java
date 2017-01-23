package com.dl.dlexerciseandroid.ui.bottomnavigationview;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.dl.dlexerciseandroid.R;

public class BottomNavigationViewActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = BottomNavigationViewActivity.class.getName();

    private Toolbar mToolbar;

    private BottomNavigationView mBottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
        setupBottomMenu();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mBottomBar = (BottomNavigationView) findViewById(R.id.bottom_navigation_view_bottom_navigation_view_bottom_menu);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    // 對應xml中的屬性，也可以用程式來控制：
    // inflateMenu(int menuResource) — Inflate a menu for the bottom navigation view using a menu resource identifier.
    // setItemBackgroundResource(int backgroundResource) — The background to be used for the menu items.
    // setItemTextColor(ColorStateList colorStateList) — A ColorStateList used to color the text used for the menu items
    // setItemIconTintList(ColorStateList colorStateList) — A ColorStateList used to tint the icons used for the menu items
    //
    // Note:
    // 1. 最多可以顯示在BottomNavigationView上的item數目是5個，如果超過五個的話會crash
    // 2. 只有兩個item的時候，左右兩邊會有奇怪的margin
    // 3. 只有三個item的時候是最正常的
    // 4. 四個以上的item，會有一個特別的動畫()
    // 5. 以後做這種東西，還是自己刻會比較好
    private void setupBottomMenu() {
        mBottomBar.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 記得要return true，這個menu item才會被設定成checked，記得是checked，而不是selected
    // 所以對應的selector裡的屬性要寫對
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_bottom_navigation_view_bottom_bar_home:
                break;

            case R.id.menu_item_bottom_navigation_view_bottom_bar_search:
                break;

            case R.id.menu_item_bottom_navigation_view_bottom_bar_add:
                break;

            case R.id.menu_item_bottom_navigation_view_bottom_bar_notification:
                break;

            case R.id.menu_item_bottom_navigation_view_bottom_bar_person:
                break;
        }

        return true;
    }
}