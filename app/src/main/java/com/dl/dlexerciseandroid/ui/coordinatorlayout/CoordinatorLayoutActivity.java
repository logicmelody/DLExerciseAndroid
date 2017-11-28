package com.dl.dlexerciseandroid.ui.coordinatorlayout;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dl.dlexerciseandroid.R;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

public class CoordinatorLayoutActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mNumberList;
    private NumberListAdapter mNumberListAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private Toolbar mToolBar;
    private ActionBar mActionBar;

    private FloatingActionButton mFloatingActionButton;

    private ItemsPositionGetter mItemsPositionGetter;
    private ListItemsVisibilityCalculator mListItemVisibilityCalculator;

    private int mScrollState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupActionBar();
        setupNumberList();
    }

    private void findViews() {
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mNumberList = (RecyclerView) findViewById(R.id.recycler_view_coordinator_layout_number_list);
        mFloatingActionButton =
                (FloatingActionButton) findViewById(R.id.floating_action_button_coordinator_layout);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolBar);
        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(getString(R.string.all_coordinator_layout));
            //mActionBar.setSubtitle(mActivity.getString(R.string.all_app_version));
        }
    }

    private void setupViews() {
        mFloatingActionButton.setOnClickListener(this);
    }

    private void setupNumberList() {
        mNumberListAdapter = new NumberListAdapter(this);
        mLinearLayoutManager = new LinearLayoutManager(this);

        mNumberList.setLayoutManager(mLinearLayoutManager);
        mNumberList.setAdapter(mNumberListAdapter);
        mListItemVisibilityCalculator =
                new SingleListViewItemActiveCalculator(new DefaultSingleItemCalculatorCallback(), mNumberListAdapter.getDatas());

        mNumberList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
                mScrollState = scrollState;
                if(scrollState == RecyclerView.SCROLL_STATE_IDLE && !mNumberListAdapter.isEmpty()){

                    mListItemVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLinearLayoutManager.findFirstVisibleItemPosition(),
                            mLinearLayoutManager.findLastVisibleItemPosition());
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(!mNumberListAdapter.isEmpty()){
                    mListItemVisibilityCalculator.onScroll(
                            mItemsPositionGetter,
                            mLinearLayoutManager.findFirstVisibleItemPosition(),
                            mLinearLayoutManager.findLastVisibleItemPosition() - mLinearLayoutManager.findFirstVisibleItemPosition() + 1,
                            mScrollState);
                }
            }
        });

        setNumberListData();

        mNumberListAdapter.notifyDataSetChanged();

        mItemsPositionGetter = new RecyclerViewItemPositionGetter(mLinearLayoutManager, mNumberList);
    }

    private void setNumberListData() {
        for (int i = 0 ; i < 100 ; i++) {
            mNumberListAdapter.add(new NumberItem(i));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 代表action bar上左上角的back鍵
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_action_button_coordinator_layout:

                break;
        }
    }
}
