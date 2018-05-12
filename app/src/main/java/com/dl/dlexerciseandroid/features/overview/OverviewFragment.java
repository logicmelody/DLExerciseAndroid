package com.dl.dlexerciseandroid.features.overview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.BuildConfig;
import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.data.model.PersonData;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by logicmelody on 2016/3/30.
 */
public class OverviewFragment extends Fragment implements OverviewContract.View {

    // 以後Fragment的tag name都用此class的name來命名比較方便
    // e.g. MusicPlayerFragment
    public static final String TAG = OverviewFragment.class.getName();

    private Context mContext;
    private OverviewContract.Presenter mPresenter;

    @BindView(R.id.text_view_overview_is_dev)
    public TextView mTextViewIsDev;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initialize();
    }

    private void initialize() {
        mPresenter = new OverviewPresenter(this);

        setupIsDev();
    }

    private void setupIsDev() {
        mTextViewIsDev.setText("Is dev = " + BuildConfig.IS_DEV);
    }

    @Override
    public void setPresenter(OverviewContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
