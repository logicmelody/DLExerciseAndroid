package com.dl.dlexerciseandroid.widget.dialog.dialogfragment.fullscreen;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;

import butterknife.ButterKnife;

// DialogFragment有兩種呈現View的方式：
// 1. onCreateView(): 這種方式有一個好處，就是可以是情況把這個DialogFragment當成一個Dialog跳出來，或是當成一個一般的Fragment放在UI中，
//    但是用這種方法有一個缺點，就是沒有onBackPressed()的callback
// 2. onCreateDialog(): 這種View的create方式就只能把這個DialogFragment當dialog使用，但是用此方法，
//                      可以在Dialog中偵測到onBackPressed()的事件
public class FullscreenDialogFragment extends DialogFragment {

    public static final String TAG = FullscreenDialogFragment.class.getName();

    private Context mContext;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_NoTitleBar);
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_fullscreen_dialog, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		ButterKnife.bind(this, getView());
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);

		// Add back button listener
		dialog.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
			// getAction to make sure this doesn't double fire
			if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_UP) {
				Log.d("danny", "FullscreenDialogFragment press back key");

				dismiss();

				return true; // Capture onKey
			}

			return false; // Don't capture
		});

		return dialog;
	}
}
