package com.dl.dlexerciseandroid.features.mininavigationdrawer.sidemenu;

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

    public interface OnSideMenuListener {
        void onClickSideMenuTab(int tab);
    }

    public static final class TabType {
        public static final int YELLOW = 0;
        public static final int BLUE = 1;
        public static final int WHITE = 2;
    }

    private Context mContext;

    private ImageView mYellowTab;
    private ImageView mBlueTab;
    private ImageView mWhiteTab;
    private ImageView mSettingButton;

    private OnSideMenuListener mOnSideMenuListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mOnSideMenuListener = (OnSideMenuListener) context;
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
        mYellowTab = (ImageView) getView().findViewById(R.id.image_view_mini_navigation_drawer_side_menu_yellow_tab);
        mBlueTab = (ImageView) getView().findViewById(R.id.image_view_mini_navigation_drawer_side_menu_blue_tab);
        mWhiteTab = (ImageView) getView().findViewById(R.id.image_view_mini_navigation_drawer_side_menu_white_tab);
        mSettingButton = (ImageView) getView().findViewById(R.id.image_view_mini_navigation_drawer_side_menu_setting_button);
    }

    private void setupViews() {
        mYellowTab.setOnClickListener(this);
        mBlueTab.setOnClickListener(this);
        mWhiteTab.setOnClickListener(this);
        mSettingButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            // Tabs
            case R.id.image_view_mini_navigation_drawer_side_menu_yellow_tab:
                mOnSideMenuListener.onClickSideMenuTab(TabType.YELLOW);
                break;

            case R.id.image_view_mini_navigation_drawer_side_menu_blue_tab:
                mOnSideMenuListener.onClickSideMenuTab(TabType.BLUE);
                break;

            case R.id.image_view_mini_navigation_drawer_side_menu_white_tab:
                mOnSideMenuListener.onClickSideMenuTab(TabType.WHITE);
                break;

            // Button
            case R.id.image_view_mini_navigation_drawer_side_menu_setting_button:
                break;
        }
    }
}
