package com.dl.dlexerciseandroid.coordinatorlayout;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dl.dlexerciseandroid.R;

import java.util.ArrayList;
import java.util.List;

public class CoordinatorLayoutActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mNumberList;
    private NumberListAdapter mNumberListAdapter;
    private List<Integer> mNumberListDataSet = new ArrayList<>();

    private Toolbar mToolBar;
    private ActionBar mActionBar;

    private FloatingActionButton mFloatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupActionBar();
        setupNumberList();
    }

    private void findViews() {
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mNumberList = (RecyclerView) findViewById(R.id.recyclerView_coordinator_layout_number_list);
        mFloatingActionButton =
                (FloatingActionButton) findViewById(R.id.floating_action_button_coordinator_layout);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolBar);
        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(getString(R.string.all_coordinator_layout));
            //mActionBar.setSubtitle(mActivity.getString(R.string.all_app_version));
        }
    }

    private void setupViews() {
        mFloatingActionButton.setOnClickListener(this);
    }

    private void setupNumberList() {
        setNumberListData();

        mNumberListAdapter = new NumberListAdapter(this, mNumberListDataSet);

        mNumberList.setLayoutManager(new LinearLayoutManager(this));
        mNumberList.setAdapter(mNumberListAdapter);
    }

    private void setNumberListData() {
        for (int i = 0 ; i < 100 ; i++) {
            mNumberListDataSet.add(i);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // 代表action bar上左上角的back鍵
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
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