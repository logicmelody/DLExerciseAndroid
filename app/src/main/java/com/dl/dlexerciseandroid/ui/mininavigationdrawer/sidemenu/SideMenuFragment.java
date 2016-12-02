package com.dl.dlexerciseandroid.ui.mininavigationdrawer.sidemenu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dl.dlexerciseandroid.R;

/**
 * Created by dannylin on 2016/12/2.
 */

public class SideMenuFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = SideMenuFragment.class.getName();

    private Context mContext;

    private ImageView mYellowButton;
    private ImageView mBlueButton;
    private ImageView mWhiteButton;
    private ImageView mSettingButton;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_side_menu, container, false);
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
        mYellowButton = (ImageView) getView().findViewById(R.id.image_view_mini_navigation_drawer_side_menu_yellow_button);
        mBlueButton = (ImageView) getView().findViewById(R.id.image_view_mini_navigation_drawer_side_menu_blue_button);
        mWhiteButton = (ImageView) getView().findViewById(R.id.image_view_mini_navigation_drawer_side_menu_white_button);
        mSettingButton = (ImageView) getView().findViewById(R.id.image_view_mini_navigation_drawer_side_menu_setting_button);
    }

    private void setupViews() {
        mYellowButton.setOnClickListener(this);
        mBlueButton.setOnClickListener(this);
        mWhiteButton.setOnClickListener(this);
        mSettingButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_view_mini_navigation_drawer_side_menu_yellow_button:
                break;

            case R.id.image_view_mini_navigation_drawer_side_menu_blue_button:
                break;

            case R.id.image_view_mini_navigation_drawer_side_menu_white_button:
                break;

            case R.id.image_view_mini_navigation_drawer_side_menu_setting_button:
                break;
        }
    }
}
