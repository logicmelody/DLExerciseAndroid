package com.dl.dlexerciseandroid.ui.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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


    /**
     * 一定要實作的method!!!
     * Called when a fragment is first attached to its context.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("danny", "TestFragment onAttach()");

        mContext = context;
    }

    /**
     * 會在onAttach()之後call
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("danny", "TestFragment onCreate()");
    }

    /**
     * 一定要實作的method!!!
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("danny", "TestFragment onCreateView()");

        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    /**
     * 一定要實作的method!!!
     *
     * 會在這個Fragment的Activity的onCreate()完成之後call，我們會在這個method進行find views的動作
     *
     *
     * Called when the fragment's activity has been created and this fragment's view hierarchy instantiated.
     * It can be used to do final initialization once these pieces are in place, such as retrieving views or restoring state.
     * It is also useful for fragments that use setRetainInstance(boolean) to retain their instance,
     * as this callback tells the fragment when it is fully associated with the new activity instance.
     * This is called after onCreateView(LayoutInflater, ViewGroup, Bundle) and before onViewStateRestored(Bundle).
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("danny", "TestFragment onActivityCreated()");

        initialize();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d("danny", "TestFragment onViewStateRestored()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("danny", "TestFragment onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("danny", "TestFragment onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("danny", "TestFragment onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("danny", "TestFragment onStop()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("danny", "TestFragment onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("danny", "TestFragment onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("danny", "TestFragment onDetach()");
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
