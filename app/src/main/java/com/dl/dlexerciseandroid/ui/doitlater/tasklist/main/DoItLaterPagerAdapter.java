package com.dl.dlexerciseandroid.ui.doitlater.tasklist.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/5/4.
 */

// 給ViewPager使用的PagerAdapter跟我們常使用的RecyclerView的Adapter做法差不多
public class DoItLaterPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private String[] mTabTitleList;


    public DoItLaterPagerAdapter(FragmentManager fm, String[] tabTitleList) {
        super(fm);
        mFragmentList = new ArrayList<>();
        mTabTitleList = tabTitleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    // 如果有搭配TabLayout使用，利用這個method設定tab上的title
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitleList[position];
    }

    public void add(Fragment fragment) {
        mFragmentList.add(fragment);
    }
}
