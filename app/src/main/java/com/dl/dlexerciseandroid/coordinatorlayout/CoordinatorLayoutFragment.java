package com.dl.dlexerciseandroid.coordinatorlayout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/4/20.
 */
public class CoordinatorLayoutFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "com.dl.dlexerciseandroid.CoordinatorLayoutFragment";

    private Context mContext;

    private RecyclerView mNumberList;
    private NumberListAdapter mNumberListAdapter;
    private List<Integer> mNumberListDataSet = new ArrayList<>();

    private FloatingActionButton mFloatingActionButton;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coordinator_layout, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupNumberList();
    }

    private void findViews() {
        mNumberList = (RecyclerView) getView().findViewById(R.id.recyclerView_coordinator_layout_number_list);
        mFloatingActionButton =
                (FloatingActionButton) getView().findViewById(R.id.floating_action_button_coordinator_layout);
    }

    private void setupViews() {
        mFloatingActionButton.setOnClickListener(this);
    }

    private void setupNumberList() {
        setNumberListData();

        mNumberListAdapter = new NumberListAdapter(mContext, mNumberListDataSet);

        mNumberList.setLayoutManager(new LinearLayoutManager(mContext));
        mNumberList.setAdapter(mNumberListAdapter);
    }

    private void setNumberListData() {
        for (int i = 0 ; i < 100 ; i++) {
            mNumberListDataSet.add(i);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_action_button_coordinator_layout:

                break;
        }
    }
}
