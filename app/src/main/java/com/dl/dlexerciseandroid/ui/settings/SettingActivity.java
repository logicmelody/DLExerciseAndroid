package com.dl.dlexerciseandroid.ui.settings;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.settings.SettingData;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mSettingRecyclerView;
    private SettingAdapter mSettingAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
        setupSettingsRecyclerView();

        setSettingsData();
    }

    private void findViews() {
        mSettingRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_setting_list);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.all_setting));
        }
    }

    private void setupSettingsRecyclerView() {
        mSettingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSettingAdapter = new SettingAdapter(this);

        mSettingRecyclerView.setAdapter(mSettingAdapter);
    }

    private void setSettingsData() {
        mSettingAdapter.clear();

        mSettingAdapter.add(new SettingData("Item1", "Item1", false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "Item1 click", Toast.LENGTH_SHORT).show();
            }
        }));

        mSettingAdapter.add(new SettingData("Item2", "Item2", false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "Item2 click", Toast.LENGTH_SHORT).show();
            }
        }));

        mSettingAdapter.add(new SettingData("Item3", "Item3", false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "Item3 click", Toast.LENGTH_SHORT).show();
            }
        }));

        mSettingAdapter.add(new SettingData("Item4", "Item4", true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "Item4 click", Toast.LENGTH_SHORT).show();
            }
        }));

        mSettingAdapter.add(new SettingData("Item5", "Item5", true, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "Item5 click", Toast.LENGTH_SHORT).show();
            }
        }));

        mSettingAdapter.refresh();
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
