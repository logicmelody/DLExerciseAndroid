package com.dl.dlexerciseandroid.features.strategypattern.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.features.strategypattern.activities.EatActivity;
import com.dl.dlexerciseandroid.features.strategypattern.activities.SleepActivity;

/**
 * Created by logicmelody on 2017/5/8.
 */

public class StrategyPatternFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = StrategyPatternFragment.class.getName();

    private Context mContext;

    private Button mEatButton;
    private Button mSleepButton;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_strategy_pattern, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupButtons();
    }

    private void findViews() {
        mEatButton = (Button) getView().findViewById(R.id.button_strategy_pattern_eat);
        mSleepButton = (Button) getView().findViewById(R.id.button_strategy_pattern_sleep);
    }

    private void setupButtons() {
        mEatButton.setOnClickListener(this);
        mSleepButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_strategy_pattern_eat:
                startActivity(new Intent(mContext, EatActivity.class));
                break;

            case R.id.button_strategy_pattern_sleep:
                startActivity(new Intent(mContext, SleepActivity.class));
                break;
        }
    }
}
