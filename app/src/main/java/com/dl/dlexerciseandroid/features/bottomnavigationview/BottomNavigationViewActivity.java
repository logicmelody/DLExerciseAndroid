package com.dl.dlexerciseandroid.features.bottomnavigationview;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

public class BottomNavigationViewActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationContract.View {

    public static final String TAG = BottomNavigationViewActivity.class.getName();

    private BottomNavigationContract.Presenter mBottomNavigationPresenter;

    private Toolbar mToolbar;

    private TextView mContentText;
    private BottomNavigationView mBottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation_view);
        initialize();
    }

    private void initialize() {
        mBottomNavigationPresenter = new BottomNavigationPresenter(this);

        findViews();
        setupActionBar();
        setupBottomMenu();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mContentText = (TextView) findViewById(R.id.text_view_bottom_navigation_view_content);
        mBottomBar = (BottomNavigationView) findViewById(R.id.bottom_navigation_view_bottom_navigation_view_bottom_menu);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.all_bottom_navigation_view));
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
                mBottomNavigationPresenter.finishActivity();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 記得要return true，這個menu item才會被設定成checked，記得是checked，而不是selected
    // 所以對應的selector裡的屬性要寫對
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return selectNavigationItem(item.getItemId());
    }

    @Override
    public void setPresenter(BottomNavigationContract.Presenter presenter) {
        mBottomNavigationPresenter = presenter;
    }

    @Override
    public boolean selectNavigationItem(int menuItemId) {
        String content = "";

        switch (menuItemId) {
            case R.id.menu_item_bottom_navigation_view_bottom_bar_home:
                content = getString(R.string.bottom_navigation_view_home);

                break;

            case R.id.menu_item_bottom_navigation_view_bottom_bar_search:
                content = getString(R.string.bottom_navigation_view_search);

                break;

            case R.id.menu_item_bottom_navigation_view_bottom_bar_add:
                content = getString(R.string.bottom_navigation_view_add);

                break;

            case R.id.menu_item_bottom_navigation_view_bottom_bar_notification:
                content = getString(R.string.bottom_navigation_view_notification);

                break;

            case R.id.menu_item_bottom_navigation_view_bottom_bar_person:
                content = getString(R.string.bottom_navigation_view_person);

                break;
        }

        mBottomNavigationPresenter.changeContent(content);

        return true;
    }

    @Override
    public void setContent(String content) {
        mContentText.setText(content);
    }
}