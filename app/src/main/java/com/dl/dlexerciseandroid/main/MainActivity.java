package com.dl.dlexerciseandroid.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.dialog.AlertDialogFragment;

public class MainActivity extends AppCompatActivity implements AlertDialogFragment.OnClickAlertDialogListener {

    private UIController mUIController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUIController = new UIController(this);
        mUIController.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUIController.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUIController.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return mUIController.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mUIController.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mUIController.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUIController.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mUIController.onActivityResult(requestCode, resultCode, data);
    }

    // 呼叫Dialog的interface要實作在Activity或是Fragment
    // Note: UIController不是Activity，他只是將Activity要做的事情拆出去，所以不能實作在UIController
    @Override
    public void onClickAlertDialogOk() {
        mUIController.onClickAlertDialogOk();
    }

    @Override
    public void onClickAlertDialogCancel() {
        mUIController.onClickAlertDialogCancel();
    }
}
