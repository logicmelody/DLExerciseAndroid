package com.dl.dlexerciseandroid.ui.file;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by logicmelody on 2016/4/8.
 */
public class FileFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = FileFragment.class.getName();
    public static final String TEST_FILE_NAME = "test.txt";

    private Context mContext;

    private TextView mLogText;
    private Button mWriteFileButton;

    private StringBuilder mLogStringBuilder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_file, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        mLogStringBuilder = new StringBuilder();

        findViews();
        setupViews();
        setupFile();
        refreshLogText();
    }

    private void refreshLogText() {
        mLogText.setText(mLogStringBuilder.toString());
    }

    private void findViews() {
        mLogText = (TextView) getView().findViewById(R.id.text_view_file_log_text);
        mWriteFileButton = (Button) getView().findViewById(R.id.button_file_write_file);
    }

    private void setupViews() {
        mWriteFileButton.setOnClickListener(this);
    }

    private void setupFile() {
        if (isTestFileExisted()) {
            mLogStringBuilder.append("File existed!" + "\n");
            mLogStringBuilder
                    .append("Test file path = " +
                            new File(mContext.getFilesDir(), TEST_FILE_NAME).getAbsolutePath() + "\n");

            writeToTestFile(readFromTestFile(mContext) + TEST_FILE_NAME);

        } else {
            String fileNotExistString = "Test file doesn't exist. Create test.txt";
            writeToTestFile("Data in!");

            mLogStringBuilder.append(fileNotExistString + "\n");
        }
    }

    private boolean isTestFileExisted() {
        // mContext.getFilesDir()可以拿到這個app在device中儲存內部檔案的路徑，此內部儲存空間不需要任何permission，
        // 搭配file name就可以得到這個file的絕對路徑
        return new File(mContext.getFilesDir(), TEST_FILE_NAME).exists();
    }

    private void writeToTestFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(mContext.openFileOutput(TEST_FILE_NAME, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();

            mLogStringBuilder.append("Write file : " + data + "\n");

        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private String readFromTestFile(Context context) {
        String ret = "";

        try {
            InputStream inputStream = context.openFileInput(TEST_FILE_NAME);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }

        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_file_write_file:
                writeToTestFile(readFromTestFile(mContext) + TEST_FILE_NAME);
                refreshLogText();

                break;
        }
    }
}