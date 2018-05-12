package com.dl.dlexerciseandroid.features.strategypattern.activities;

import com.dl.dlexerciseandroid.R;

public class SleepActivity extends BasedBehaviorActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sleep;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.strategy_pattern_sleep);
    }

    @Override
    protected String getFinishedToastString() {
        return "Wake up";
    }
}
