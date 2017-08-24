package com.dl.dlexerciseandroid.ui.main;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.dialog.dialogfragment.alert.AlertDialogFragment;
import com.dl.dlexerciseandroid.ui.rightdrawer.RightDrawerFragment;

public class MainActivity extends AppCompatActivity implements AlertDialogFragment.OnClickAlertDialogListener,
        RightDrawerFragment.OnRightDrawerListener {

    private UIController mUIController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        Log.d("danny", "MainActivity onCreate()");

//        Intent intent = getIntent();
//
//        if (intent != null) {
//            Log.d("danny", intent.toString());
//
//            Bundle bundle = intent.getExtras();
//            String[] keyArray = bundle.keySet().toArray(new String[bundle.keySet().size()]);
//
//            for (String key : keyArray) {
//                Log.d("danny", "Key = " + key);
//            }
//        }

        setContentView(R.layout.activity_main);
        mUIController = new UIController(this);
        mUIController.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("danny", "MainActivity onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("danny", "MainActivity onResume()");

        mUIController.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("danny", "MainActivity onPause()");

        mUIController.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("danny", "MainActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("danny", "MainActivity onDestroy()");
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mUIController.onSaveInstanceState(outState);
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

    // Fragment要與Activity溝通必須要透過interface，所以將RightDrawerFragment的interface實作在這裡
    @Override
    public void onCloseRightDrawer() {
        mUIController.onCloseRightDrawer();
    }
}
