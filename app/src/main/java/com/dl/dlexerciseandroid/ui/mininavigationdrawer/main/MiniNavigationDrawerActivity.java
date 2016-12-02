package com.dl.dlexerciseandroid.ui.mininavigationdrawer.main;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.ui.mininavigationdrawer.content.YellowFragment;
import com.dl.dlexerciseandroid.ui.mininavigationdrawer.sidemenu.SideMenuFragment;
import com.dl.dlexerciseandroid.utility.utils.FragmentUtils;

public class MiniNavigationDrawerActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_navigation_drawer);
        initialize();
    }

    private void initialize() {
        mFragmentManager = getSupportFragmentManager();

        setupFragments();
    }

    private void setupFragments() {
        // 將SideMenuFragment放到左邊的container
        FragmentUtils.addFragmentTo(mFragmentManager, SideMenuFragment.class,
                R.id.view_group_mini_navigation_drawer_side_menu_container, SideMenuFragment.TAG);

        // 將YellowFragment放到右邊的container
        // Note: 初始先暫時放YellowFragment，之後如果要處理旋轉狀況的話，需要紀錄fragment的tag
        FragmentUtils.addFragmentTo(mFragmentManager, YellowFragment.class,
                R.id.view_group_mini_navigation_drawer_content_container, YellowFragment.TAG);
    }
}
