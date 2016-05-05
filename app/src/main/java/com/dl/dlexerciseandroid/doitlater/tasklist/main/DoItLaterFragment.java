package com.dl.dlexerciseandroid.doitlater.tasklist.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.dialog.activity.AddTaskActivity;
import com.dl.dlexerciseandroid.doitlater.tasklist.later.LaterTaskFragment;
import com.dl.dlexerciseandroid.doitlater.tasklist.normal.NormalTaskFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/4/18.
 */

public class DoItLaterFragment extends Fragment implements View.OnClickListener, ViewPager.OnPageChangeListener,
        TabLayout.OnTabSelectedListener {

    public static final String TAG = "com.dl.dlexerciseandroid.DoItLaterFragment";

    private Context mContext;

    private TabLayout mTabLayout;

    private ViewPager mTaskListViewPager;
    private DoItLaterPagerAdapter mDoItLaterPagerAdapter;
    private List<Fragment> mFragmentList = new ArrayList<>();

    private FloatingActionButton mAddTaskButton;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_do_it_later, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupFragments();
        setupViewPager();
        setupTabLayout();
    }

    private void findViews() {
        mTabLayout = (TabLayout) getView().findViewById(R.id.tab_layout_do_it_later_task_list);
        mTaskListViewPager = (ViewPager) getView().findViewById(R.id.view_pager_do_it_later_task_list_container);
        mAddTaskButton = (FloatingActionButton) getView().findViewById(R.id.floating_action_button_do_it_later_add_task);
    }

    private void setupViews() {
        mAddTaskButton.setOnClickListener(this);
    }

    private void setupFragments() {
        mFragmentList.add(getFragment(NormalTaskFragment.class, NormalTaskFragment.TAG));
        mFragmentList.add(getFragment(LaterTaskFragment.class, LaterTaskFragment.TAG));
    }

    private Fragment getFragment(Class<? extends Fragment> fragmentClass, String tag) {
        Fragment fragment = getChildFragmentManager().findFragmentByTag(tag);

        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();

            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return fragment;
    }

    private void setupViewPager() {
        String[] tabTitleList = {
                getString(R.string.all_normal),
                getString(R.string.all_later)
        };
        mDoItLaterPagerAdapter = new DoItLaterPagerAdapter(getChildFragmentManager(), mFragmentList, tabTitleList);

        mTaskListViewPager.setAdapter(mDoItLaterPagerAdapter);

        // 設定要保留幾個在idle狀態下的Fragment，如果Fragment的數量是固定的，
        // 設定這個值可以讓animation順暢，不用再花時間重新new一個Fragment
        mTaskListViewPager.setOffscreenPageLimit(2);
        mTaskListViewPager.setOnPageChangeListener(this);
    }

    private void setupTabLayout() {
        // 將ViewPager跟TabLayout連動，如此一來在OnPageChangeListener跟OnTabSelectedListener不需要做任何設定
        // 就可以讓ViewPager跟TabLayout的行為一致
        mTabLayout.setupWithViewPager(mTaskListViewPager);
        mTabLayout.setOnTabSelectedListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_action_button_do_it_later_add_task:
                addTask();
                break;
        }
    }

    private void addTask() {
        startActivity(new Intent(mContext, AddTaskActivity.class));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        // 有可能其中一個Fragment scroll之後讓Floating action button消失，但是切換到另外一個Fragment之後，
        // 因為item個數太少，無法scroll，造成Floating action button沒有辦法再出現，
        // 所以我們這邊強制讓Floating action button在切換Fragment的時候出現
        mAddTaskButton.show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mTaskListViewPager.setCurrentItem(tab.getPosition());
        mAddTaskButton.show();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}