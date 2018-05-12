package com.dl.dlexerciseandroid.features.guide;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2017/9/20.
 */
public class GuideActivity extends AppCompatActivity implements GuideView.OnGuideViewListener {

    public static final String TAG = GuideActivity.class.getName();

    private Toolbar mToolbar;

    private RecyclerView mRecyclerView;
    private GuideNumberAdapter mGuideNumberAdapter;

    private GuideView mGuideView;
    private DisableRecyclerViewTouchListener mDisableRecyclerViewTouchListener;

    // 利用這個touch listener來攔截RecyclerView滑動的事件，這樣可以disable滑動事件，但是click事件還是可以存在
    private class DisableRecyclerViewTouchListener implements RecyclerView.OnItemTouchListener {

        @Override
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return RecyclerView.SCROLL_STATE_DRAGGING == recyclerView.getScrollState();
        }

        @Override
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
        setupNumberList();
        setupGuideView();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_guide_number_list);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) {
            return;
        }

        actionBar.setTitle(getString(R.string.all_guide));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setupNumberList() {
        mGuideNumberAdapter = new GuideNumberAdapter(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mGuideNumberAdapter);

        setNumberListData();
    }

    private void setNumberListData() {
        for (int i = 1 ; i < 100 ; i++) {
            mGuideNumberAdapter.add(i);
        }

        mGuideNumberAdapter.refresh();
    }

    private void removeGuideView() {
        if (!hasGuideViewOnWindow()) {
            return;
        }

        mGuideView.removeSelf();
    }

    private boolean hasGuideViewOnWindow() {
        return mGuideView != null && ViewCompat.isAttachedToWindow(mGuideView);
    }

    private void setupGuideView() {
        // TourGuide can only be setup after all the views is ready and obtain it's position/measurement
        // so when this is the 1st time TourGuide is being added,
        // else block will be executed, and ViewTreeObserver will make TourGuide setup process to be delayed until
        // everything is ready when this is run the 2nd or more times, if block will be executed
        if (ViewCompat.isAttachedToWindow(mRecyclerView)) {
            setGuideView();

        } else {
            final ViewTreeObserver viewTreeObserver = mRecyclerView.getViewTreeObserver();

            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mRecyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    } else {
                        mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                    setGuideView();
                }
            });
        }
    }

    private void setGuideView() {
        addGuideView(mRecyclerView.getChildAt(1));
        setNumberListScrollEnabled(false);
    }

    private void setNumberListScrollEnabled(boolean isScrollEnabled) {
        if (isScrollEnabled) {
            if (mDisableRecyclerViewTouchListener == null) {
                return;
            }

            mRecyclerView.removeOnItemTouchListener(mDisableRecyclerViewTouchListener);

        } else {
            mDisableRecyclerViewTouchListener = new DisableRecyclerViewTouchListener();
            mRecyclerView.addOnItemTouchListener(mDisableRecyclerViewTouchListener);
        }
    }

    private void addGuideView(View highlightView) {
        mGuideView = new GuideView(this, highlightView, this);

        // android.R.id.content拿到的會是Activity中content的部分，也就是action bar以下的地方，所以我們在加GuideView的時候，
        // 會從action bar以下開始加，如果想要guide view全屏，可以使用Toolbar，將Activity的action bar給拿掉
        ViewGroup contentArea = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT);

        // GuideView's coordinates are calculated taking full screen height into account
        // but we're adding it to the content area only, so we need to offset it to the same Y value of contentArea
        int [] pos = new int[2];
        contentArea.getLocationOnScreen(pos);
        layoutParams.setMargins(0, -pos[1], 0, 0);

        contentArea.addView(mGuideView, layoutParams);
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

    @Override
    public void onBackPressed() {
        if (hasGuideViewOnWindow()) {
            removeGuideView();
            setNumberListScrollEnabled(true);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClickIKnow() {
        setNumberListScrollEnabled(true);
    }
}


