package com.dl.dlexerciseandroid.ui.exoplayer.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;

import butterknife.ButterKnife;

/**
 * Created by logicmelody on 2018/3/18.
 */

public class ExoplayerMainFragment extends Fragment implements ExoPlayerMainContract.View {

    public static final String TAG = ExoplayerMainFragment.class.getName();

    private Context mContext;
    private ExoPlayerMainContract.Presenter mPresenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exoplayer_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initialize();
    }

    private void initialize() {
        mPresenter = new ExoPlayerMainPresenter(this);
    }

    @Override
    public void setPresenter(ExoPlayerMainContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
