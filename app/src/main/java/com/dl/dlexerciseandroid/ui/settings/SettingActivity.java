package com.dl.dlexerciseandroid.ui.settings;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.settings.SettingData;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    private SettingManager mSettingManager;
    private RecyclerView mSettingRecyclerView;

    private Button mTestButton;

    private boolean mIsFirstEnter = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initialize();

        mIsFirstEnter = false;
    }

    private void initialize() {
        findViews();
        setupViews();
        setupActionBar();
        setupSettingManager();

        setSettingsData();
    }

    private void findViews() {
        mSettingRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_setting_list);
        mTestButton = (Button) findViewById(R.id.button_setting_test);
    }

    private void setupViews() {
        mTestButton.setOnClickListener(this);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.all_setting));
        }
    }

    private void setupSettingManager() {
        mSettingManager = new SettingManager(this, mSettingRecyclerView);
    }

    private void setSettingsData() {
        mSettingManager.clearList();

        mSettingManager.addItem(new SettingData("Item1", "Item1", false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "Item1 click", Toast.LENGTH_SHORT).show();
            }
        }));

        mSettingManager.addItem(new SettingData("Item2", "Item2", false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "Item2 click", Toast.LENGTH_SHORT).show();
            }
        }));

        mSettingManager.addItem(new SettingData("Item3", "Item3", false, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingActivity.this, "Item3 click", Toast.LENGTH_SHORT).show();
            }
        }));

        if (mIsFirstEnter) {
            mSettingManager.addItem(new SettingData("Item4", "Item4", true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SettingActivity.this, "Item4 click", Toast.LENGTH_SHORT).show();
                }
            }));

            mSettingManager.addItem(new SettingData("Item5", "Item5", true, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(SettingActivity.this, "Item5 click", Toast.LENGTH_SHORT).show();
                }
            }));
        }

        mSettingManager.refreshList();
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
        switch (v.getId()) {
            case R.id.button_setting_test:
                setSettingsData();

                break;
        }
    }
}
