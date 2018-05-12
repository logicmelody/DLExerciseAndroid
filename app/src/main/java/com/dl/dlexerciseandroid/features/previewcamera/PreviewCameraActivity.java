package com.dl.dlexerciseandroid.features.previewcamera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utils.Utils;

import java.io.IOException;

public class PreviewCameraActivity extends AppCompatActivity implements
        SurfaceHolder.Callback, View.OnClickListener {

    public static final String TAG = PreviewCameraActivity.class.getName();
    private static final int REQUEST_CODE_CAMERA = 11;

    private Toolbar mToolbar;

    private ViewGroup mCameraPermissionExplanationView;
    private Button mRequestCameraPermissionButton;

    // SurfaceView是一個View，我們可以用獨立的thread去變更SurfaceView上呈現的東西，因為是獨立的thread，
    // 所以我們可以進行比較複雜的UI操作，而且不會block住main thread
    private SurfaceView mPreviewCameraSurfaceView;
    private SurfaceHolder mPreviewCameraSurfaceHolder;

    private Camera mCamera;
    private int mDefaultCameraId = -1;

    private boolean mHasCameraPermission = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_camera);
        initialize();
    }

    private void initialize() {
        findViews();
        setupActionBar();
        setupViews();
        setupPreviewCamera();
    }

    private void findViews() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mPreviewCameraSurfaceView = (SurfaceView) findViewById(R.id.surface_view_preview_camera);
        mCameraPermissionExplanationView
                = (ViewGroup) findViewById(R.id.view_group_preview_camera_request_camera_permission_explanation);
        mRequestCameraPermissionButton
                = (Button) findViewById(R.id.button_preview_camera_request_camera_permission);
    }

    private void setupActionBar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.all_preview_camera));
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupViews() {
        mRequestCameraPermissionButton.setOnClickListener(this);
    }

    private void setupPreviewCamera() {
        mPreviewCameraSurfaceHolder = mPreviewCameraSurfaceView.getHolder();
        mPreviewCameraSurfaceHolder.addCallback(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mHasCameraPermission = hasCameraPermission();

        if (mHasCameraPermission) {
            // 相機的初始化在onResume()執行
            setupCamera();

        } else {
            showRequestCameraPermissionOrExplanationDialog();
        }
    }

    private void setupCamera() {
        if (mCamera != null) {
            return;
        }

        mCamera = Camera.open();
        mDefaultCameraId = getDefaultCameraId();
    }

    private int getDefaultCameraId() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int defaultCameraId = -1;

        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, cameraInfo);

            if (Camera.CameraInfo.CAMERA_FACING_BACK == cameraInfo.facing) {
                defaultCameraId = i;
            }
        }

        return defaultCameraId;
    }

    private boolean hasCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private void showRequestCameraPermissionOrExplanationDialog() {
        // Should we show an explanation?
        // 在Activity中如果想要shouldShowRequestPermissionRationale，可以利用ActivityCompat這個class
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            // 如果user有在permission跳出的時候，選擇deny，或是自己手動到Settings中把permission關掉，就會走到這塊，
            // 也就是需要跳出explanation

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

            Log.d("danny", "Show CAMERA explanation");

            mCameraPermissionExplanationView.setVisibility(View.VISIBLE);
            mPreviewCameraSurfaceView.setVisibility(View.GONE);

        } else {
            // 第一次跳出permission視窗的時候，不會跳出explanation，會直接向user提出request permission
            // No explanation needed, we can request the permission.
            Log.d("danny", "No explanation needed, we can request the permission.");

            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.CAMERA},
                REQUEST_CODE_CAMERA);

        // READ_CODE_EXTERNAL_STORAGE is an
        // app-defined int constant. The callback method gets the
        // result of the request.
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 相機的destroy在onPause()執行
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera == null) {
            return;
        }

        mCamera.release();
        mCamera = null;
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
    public void surfaceCreated(SurfaceHolder holder) {
        if (!mHasCameraPermission) {
            return;
        }

        // 這邊我們在surface建立成功後就開始進行camera的preview
        startCameraPreview();
    }

    private void startCameraPreview() {
        if (mCamera == null) {
            return;
        }

        try {
            // 設定要用哪個surface來preview camera的畫面，我們這邊是用SurfaceView來實作，
            // 所以必須等到SurfaceView的surface create成功之後才可以call mCamera.setPreviewDisplay()
            mCamera.setPreviewDisplay(mPreviewCameraSurfaceHolder);

            // 開始擷取畫面，並且將擷取到的image在設定好的surface上呈現
            mCamera.startPreview();

        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(this, "Unable to open camera.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("danny", "PreviewCameraActivity: surfaceChanged");

        // 預設的camera角度會有點問題，可以利用Google提供的sample method把preview的畫面轉成正確的方向
        Utils.setCameraDisplayOrientation(this, mDefaultCameraId, mCamera);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.d("danny", "CAMERA permission was granted!");

                    mCameraPermissionExplanationView.setVisibility(View.GONE);
                    mPreviewCameraSurfaceView.setVisibility(View.VISIBLE);

                    setupCamera();

                } else {
                    Log.d("danny", "CAMERA permission was denied!");

                    mCameraPermissionExplanationView.setVisibility(View.VISIBLE);
                    mPreviewCameraSurfaceView.setVisibility(View.GONE);

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }

                break;

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_preview_camera_request_camera_permission:
                requestCameraPermission();

                break;
        }
    }
}
