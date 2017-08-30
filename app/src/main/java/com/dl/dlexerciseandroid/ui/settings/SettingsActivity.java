package com.dl.dlexerciseandroid.ui.settings;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.settings.SettingData;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mSettingsRecyclerView;
    private SettingsAdapter mSettingsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
        setupSettingsRecyclerView();

        setSettingsData();
    }

    private void findViews() {
        mSettingsRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_settings_list);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.all_settings));
        }
    }

    private void setupSettingsRecyclerView() {
        mSettingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSettingsAdapter = new SettingsAdapter(this);

        mSettingsRecyclerView.setAdapter(mSettingsAdapter);
    }

    private void setSettingsData() {
        mSettingsAdapter.add(new SettingData("Item1", "Item1", false));
        mSettingsAdapter.add(new SettingData("Item2", "Item2", false));
        mSettingsAdapter.add(new SettingData("Item3", "Item3", false));
        mSettingsAdapter.add(new SettingData("Item4", "Item4", true));
        mSettingsAdapter.add(new SettingData("Item5", "Item5", true));

        mSettingsAdapter.refresh();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
