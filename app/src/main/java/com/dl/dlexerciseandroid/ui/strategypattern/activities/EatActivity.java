package com.dl.dlexerciseandroid.ui.strategypattern.activities;

import com.dl.dlexerciseandroid.R;

public class EatActivity extends BasedBehaviorActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eat;
    }

    @Override
    protected String getActionBarTitle() {
        return getString(R.string.strategy_pattern_eat);
    }

    @Override
    protected String getFinishedToastString() {
        return "I'm full";
    }
}
