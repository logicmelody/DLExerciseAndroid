package com.dl.dlexerciseandroid.features.loadimagefrominternet.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/7/12.
 */
public class ImageListPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private String[] mTabTitleList;


    public ImageListPagerAdapter(FragmentManager fm, String[] tabTitleList) {
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

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitleList[position];
    }

    public void add(Fragment fragment) {
        mFragmentList.add(fragment);
    }
}
