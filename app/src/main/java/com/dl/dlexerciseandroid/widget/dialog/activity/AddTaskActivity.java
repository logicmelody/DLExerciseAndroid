package com.dl.dlexerciseandroid.widget.dialog.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.backgroundtask.service.TaskService;

public class AddTaskActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ActionBar mActionBar;

    private EditText mTaskTitle;
    private EditText mTaskDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mTaskTitle = (EditText) findViewById(R.id.edit_text_add_task_task_title);
        mTaskDescription = (EditText) findViewById(R.id.edit_text_add_task_task_description);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();

        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setTitle(getString(R.string.add_task_dialog_title));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.menu_item_add_task_ok:
                saveTask();
                finish();
                return true;

            case R.id.menu_item_add_task_cancel:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveTask() {
        String title = mTaskTitle.getText().toString();
        String description = mTaskDescription.getText().toString();

        if (TextUtils.isEmpty(title)) {
            return;
        }

        startService(TaskService.generateSaveNormalTaskIntent(this, title, description));
    }
}
