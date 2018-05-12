package com.dl.dlexerciseandroid.features.test;

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
     * Activity跟Fragment之間的lifecycle關係：
     *
     先將Fragment貼到Activity上
     11-23 14:30:32.468 5384-5384/com.dl.dlexerciseandroid D/danny: TestFragment onAttach()

     Fragment的onCreate()有可能會在Activity的onCreate()完成之前
     11-23 14:30:32.468 5384-5384/com.dl.dlexerciseandroid D/danny: TestFragment onCreate()
     11-23 14:30:32.468 5384-5384/com.dl.dlexerciseandroid D/danny: MainActivity onCreate()
     11-23 14:30:32.516 5384-5384/com.dl.dlexerciseandroid D/danny: TestFragment onCreateView()
     11-23 14:30:32.522 5384-5384/com.dl.dlexerciseandroid D/danny: TestFragment onActivityCreated()
     11-23 14:30:32.522 5384-5384/com.dl.dlexerciseandroid D/danny: TestFragment onViewStateRestored()

     基本上就是跟著Activity
     11-23 14:30:32.522 5384-5384/com.dl.dlexerciseandroid D/danny: TestFragment onStart()
     11-23 14:30:32.523 5384-5384/com.dl.dlexerciseandroid D/danny: MainActivity onStart()

     基本上就是跟著Activity
     11-23 14:30:32.525 5384-5384/com.dl.dlexerciseandroid D/danny: MainActivity onResume()
     11-23 14:30:32.527 5384-5384/com.dl.dlexerciseandroid D/danny: TestFragment onResume()

     基本上就是跟著Activity
     11-23 14:33:18.131 5384-5384/com.dl.dlexerciseandroid D/danny: TestFragment onPause()
     11-23 14:33:18.132 5384-5384/com.dl.dlexerciseandroid D/danny: MainActivity onPause()

     基本上就是跟著Activity
     11-23 14:33:18.465 5384-5384/com.dl.dlexerciseandroid D/danny: TestFragment onStop()
     11-23 14:33:18.465 5384-5384/com.dl.dlexerciseandroid D/danny: MainActivity onStop()

     先將Fragment給destroy掉之後，再destroy Activity
     11-23 14:33:18.476 5384-5384/com.dl.dlexerciseandroid D/danny: TestFragment onDestroyView()
     11-23 14:33:18.478 5384-5384/com.dl.dlexerciseandroid D/danny: TestFragment onDestroy()
     11-23 14:33:18.478 5384-5384/com.dl.dlexerciseandroid D/danny: TestFragment onDetach()
     11-23 14:33:18.478 5384-5384/com.dl.dlexerciseandroid D/danny: MainActivity onDestroy()
     */

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
<<<<<<< HEAD
=======
     * 這個method有可能會在Activity的onCreate()完成之前call
     *
     * Note that this can be called while the fragment's activity is still in the process of being created.
     * As such, you can not rely on things like the activity's content view hierarchy being initialized at this point.
>>>>>>> a44c745... [Test] Add log for fragment lifecycle
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
