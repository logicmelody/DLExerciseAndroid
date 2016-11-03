package com.dl.dlexerciseandroid.ui.customizedview;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.view.DurationTextView;
import com.dl.dlexerciseandroid.view.LineChartView;
import com.dl.dlexerciseandroid.view.ValueBar;
import com.dl.dlexerciseandroid.view.ValueSelectorView;

/**
 * Created by logicmelody on 2016/8/18.
 */
public class CustomizedViewFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = CustomizedViewFragment.class.getName();

    private Context mContext;

    private DurationTextView mDuration;
    private LineChartView mLineChartView;
    private ValueSelectorView mValueSelectorView;
    private ValueBar mValueBar;

    private Button mUpdateValueButton;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customized_view, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupDuration();
        setupLineChartView();
        //setupValueBar();
    }

    private void findViews() {
        mDuration = (DurationTextView) getView().findViewById(R.id.duration_text_view_duration);
        mLineChartView = (LineChartView) getView().findViewById(R.id.line_chart_view_customized_view_chart);
        mValueSelectorView = (ValueSelectorView) getView().findViewById(R.id.value_selector_view_customized_view_selector);
        mValueBar = (ValueBar) getView().findViewById(R.id.value_bar_customized_view_bar);
        mUpdateValueButton = (Button) getView().findViewById(R.id.button_customized_view_update_value_button);
    }

    private void setupViews() {
        mUpdateValueButton.setOnClickListener(this);
    }

    private void setupDuration() {
        // About 2 hrs 48 mins
        mDuration.setDuration(10000);
    }

    private void setupLineChartView() {
        mLineChartView.setChartData(new float[] {12, 30, 10, 50, 1, 20, 3});
    }

    private void setupValueBar() {
        mValueBar.setValue(mValueSelectorView.getValue());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_customized_view_update_value_button:
                mValueBar.setValue(mValueSelectorView.getValue());

                break;
        }
    }
}
