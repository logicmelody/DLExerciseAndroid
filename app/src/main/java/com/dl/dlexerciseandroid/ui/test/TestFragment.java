package com.dl.dlexerciseandroid.ui.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2016/3/30.
 */
public class TestFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = TestFragment.class.getName();

    private Context mContext;

    private EditText mInputText;
    private Button mInputButton;
    private TextView mResultText;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
    }

    private void findViews() {
        mInputText = (EditText) getView().findViewById(R.id.edit_text_testing_input_text);
        mInputButton = (Button) getView().findViewById(R.id.button_testing_input);
        mResultText = (TextView) getView().findViewById(R.id.text_view_testing_result);
    }

    private void setupViews() {
        mInputButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_testing_input:
                updateResultText();
                break;
        }
    }

    private void updateResultText() {
        String s = mInputText.getText().toString();

        if (TextUtils.isEmpty(s)) {
            return;
        }

        mResultText.setText(s);
    }
}
