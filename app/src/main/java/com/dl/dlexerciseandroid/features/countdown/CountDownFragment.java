package com.dl.dlexerciseandroid.features.countdown;

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

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.widget.dialog.dialogfragment.countdown.CountDownDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by logicmelody on 2018/1/6.
 */

public class CountDownFragment extends Fragment implements CountDownContract.View {

    public static final String TAG = CountDownFragment.class.getName();

    private Context mContext;
    private CountDownContract.Presenter mPresenter;

    @BindView(R.id.edit_text_count_down)
    public EditText mEditTextCountDown;

    @BindView(R.id.button_count_down_start)
    public Button mButtonStart;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_count_down, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initialize();
    }

    private void initialize() {
        mPresenter = new CountDownPresenter(this);
    }

    @Override
    public void setPresenter(CountDownContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.button_count_down_start)
    public void onClickStartButton() {
        String s = mEditTextCountDown.getText().toString();

        if (TextUtils.isEmpty(s)) {
            return;
        }

        int sec = Integer.decode(s);

        if (sec <= 0) {
            return;
        }

        mPresenter.startCountDown(sec);
    }

    @Override
    public void showCountDownDialog(int sec) {
        CountDownDialogFragment countDownDialogFragment = new CountDownDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(CountDownDialogFragment.EXTRA_INT_COUNT_DOWN_SECONDS, sec);

        countDownDialogFragment.setArguments(bundle);
        countDownDialogFragment.show(getFragmentManager(), CountDownDialogFragment.TAG);
    }
}
