package com.dl.dlexerciseandroid.ui.guide;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2017/9/20.
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = GuideActivity.class.getName();

    private Toolbar mToolbar;

    private Button mHighlightButton;
    private Button mCallGuideButton;

    private GuideView mGuideView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupActionBar();
        setupGuideView();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mHighlightButton = (Button) findViewById(R.id.button_guide_highlight);
        mCallGuideButton = (Button) findViewById(R.id.button_guide_call_guide);
    }

    private void setupViews() {
        mHighlightButton.setOnClickListener(this);
        mCallGuideButton.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_guide_highlight:
                if (!hasGuideViewOnWindow()) {
                    return;
                }

                removeGuideView();

                break;

            case R.id.button_guide_call_guide:
                if (hasGuideViewOnWindow()) {
                    return;
                }

                setupGuideView();

                break;
        }
    }

    private boolean hasGuideViewOnWindow() {
        return mGuideView != null && ViewCompat.isAttachedToWindow(mGuideView);
    }

    private void removeGuideView() {
        mGuideView.cleanUp();
        mGuideView = null;
    }

    private void setupGuideView() {
        // TourGuide can only be setup after all the views is ready and obtain it's position/measurement
        // so when this is the 1st time TourGuide is being added,
        // else block will be executed, and ViewTreeObserver will make TourGuide setup process to be delayed until
        // everything is ready when this is run the 2nd or more times, if block will be executed
        if (ViewCompat.isAttachedToWindow(mHighlightButton)){
            addGuideView();

        } else {
            final ViewTreeObserver viewTreeObserver = mHighlightButton.getViewTreeObserver();

            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        mHighlightButton.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                    } else {
                        mHighlightButton.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }

                    addGuideView();
                }
            });
        }
    }

    private void addGuideView() {
        mGuideView = new GuideView(this, mHighlightButton);

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

        } else {
            super.onBackPressed();
        }
    }
}


