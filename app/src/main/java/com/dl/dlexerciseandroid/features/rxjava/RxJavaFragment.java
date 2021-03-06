package com.dl.dlexerciseandroid.features.rxjava;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.Injection;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RxJavaFragment extends Fragment implements RxJavaContract.View {

    // 以後Fragment的tag name都用此class的name來命名比較方便
    // e.g. MusicPlayerFragment
    public static final String TAG = RxJavaFragment.class.getName();

    private Context mContext;
    private RxJavaContract.Presenter mPresenter;

    private StringBuilder mLogStringBuilder;

    @BindView(R.id.text_view_rxjava_log)
    public TextView mLogTextView;

    @BindView(R.id.image_view_rxjava_iron_man)
    public ImageView mIronManImageView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rxjava, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initialize();

        mPresenter.testFromArray();
        mPresenter.testPrintHelloWorld();
        mPresenter.testEmpty();
        mPresenter.testFlatMap();
        mPresenter.testConcat();
        mPresenter.loadIronMan();
    }

    private void initialize() {
        mPresenter = new RxJavaPresenter(this, Injection.provideRepository(), Injection.provideSchedulerProvider());

        mLogStringBuilder = new StringBuilder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.onDestroy();
    }

    @Override
    public void showLog(String tag, String log) {
        String printLog = tag + " " + log;

        Log.d("danny", printLog);
        appendLog(printLog);
    }

    private void appendLog(String log) {
        if (TextUtils.isEmpty(log)) {
            return;
        }

        mLogStringBuilder.append(log).append("\n");
    }

    @Override
    public void setLogText() {
        mLogTextView.setText(mLogStringBuilder.toString());
    }

    @Override
    public Drawable getIronManDrawable() {
        return getResources().getDrawable(R.drawable.poster_iron_man);
    }

    @Override
    public void setIronManImageView(Drawable drawable) {
        mIronManImageView.setImageDrawable(drawable);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(RxJavaContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.button_rxjava_start_5_sec_toast)
    public void onClickStart5SecToast() {
        mPresenter.test5SecToast();
    }

    @OnClick(R.id.button_rxjava_stop_5_sec_toast)
    public void onClickStop5SecToast() {
        mPresenter.stop5SecToast();
    }
}
