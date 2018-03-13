package com.dl.dlexerciseandroid.ui.http;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by logicmelody on 2018/3/12.
 */

public class HttpFragment extends Fragment {

    public static final String TAG = HttpFragment.class.getName();

    private Context mContext;

    @BindView(R.id.text_view_http_get_url)
    public TextView mTextViewGetUrl;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_http, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initialize();
    }

    private void initialize() {

    }
}
