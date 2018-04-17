package com.dl.dlexerciseandroid.ui.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogTriggerFragment extends Fragment implements DialogTriggerContract.View {

    public static final String TAG = DialogTriggerFragment.class.getName();

    private Context mContext;
    private DialogTriggerContract.Presenter mPresenter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_trigger, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        initialize();
    }

    private void initialize() {
        mPresenter = new DialogTriggerPresenter(this);
    }

    @Override
    public void setPresenter(DialogTriggerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.button_dialog_trigger_fullscreen_dialog)
    public void onClickFullscreenDialog() {
        mPresenter.openFullscreenDialog();
    }

    @Override
    public void showFullscreenDialog() {
        Toast.makeText(mContext, "Fullscreen dialog", Toast.LENGTH_SHORT).show();
    }
}
