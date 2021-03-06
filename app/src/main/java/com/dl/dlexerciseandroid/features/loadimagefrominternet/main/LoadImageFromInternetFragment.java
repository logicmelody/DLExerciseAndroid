package com.dl.dlexerciseandroid.features.loadimagefrominternet.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.features.loadimagefrominternet.imagelist.lru.LruFragment;
import com.dl.dlexerciseandroid.features.loadimagefrominternet.imagelist.picasso.PicassoFragment;
import com.dl.dlexerciseandroid.utils.FragmentUtils;

/**
 * Created by logicmelody on 2016/7/12.
 */
public class LoadImageFromInternetFragment extends Fragment implements ViewPager.OnPageChangeListener,
        TabLayout.OnTabSelectedListener {

    public static final String TAG = LoadImageFromInternetFragment.class.getName();

    private Context mContext;

    private ViewPager mImageListViewPager;
    private ImageListPagerAdapter mImageListPagerAdapter;

    private TabLayout mImageListTabLayout;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_load_image_from_internet, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViewPager();
        setupTabLayout();
    }

    private void findViews() {
        mImageListTabLayout = (TabLayout) getView().findViewById(R.id.tab_layout_load_image_from_internet_image_list);
        mImageListViewPager = (ViewPager) getView().findViewById(R.id.view_pager_load_image_from_internet_image_list);
    }

    private void setupViewPager() {
        String[] tabTitleList = {
                getString(R.string.all_picasso),
                getString(R.string.all_lru)
        };

        // 這邊第一個參數如果用getFragmentManager()會有問題，因為我們是在DoItLaterFragment中的ViewPager再加Fragment
        // 所以應該要使用getChildFragmentManager()
        mImageListPagerAdapter = new ImageListPagerAdapter(getChildFragmentManager(), tabTitleList);
        setFragments();

        mImageListViewPager.setAdapter(mImageListPagerAdapter);

        // 設定要保留幾個在idle狀態下的Fragment，如果Fragment的數量是固定的，
        // 設定這個值可以讓animation順暢，不用再花時間重新new一個Fragment
        mImageListViewPager.setOffscreenPageLimit(2);
        mImageListViewPager.setOnPageChangeListener(this);
    }

    private void setFragments() {
        mImageListPagerAdapter.add(FragmentUtils.getFragment(getChildFragmentManager(),
                PicassoFragment.class, PicassoFragment.TAG));
        mImageListPagerAdapter.add(FragmentUtils.getFragment(getChildFragmentManager(),
                LruFragment.class, LruFragment.TAG));
    }

    private void setupTabLayout() {
        // 將ViewPager跟TabLayout連動，如此一來在OnPageChangeListener跟OnTabSelectedListener不需要做任何設定
        // 就可以讓ViewPager跟TabLayout的行為一致
        mImageListTabLayout.setupWithViewPager(mImageListViewPager);
        mImageListTabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mImageListViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
