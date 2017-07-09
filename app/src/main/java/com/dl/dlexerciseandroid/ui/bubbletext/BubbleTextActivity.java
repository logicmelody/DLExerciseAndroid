package com.dl.dlexerciseandroid.ui.bubbletext;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.dl.dlexerciseandroid.R;

/**
 * 練習如何用一個Service，將一個View利用WindowManager畫在screen上，類似Facebook Messenger一直浮在畫面上的bubble
 *
 * Created by logicmelody on 2016/6/27.
 */
public class BubbleTextActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = BubbleTextActivity.class.getName();

    private EditText mInputText;

    private Button mShowBubbleTextView;
    private Button mSendMessageToBubbleTextViewButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_text);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupActionBar();
    }

    private void findViews() {
        mInputText = (EditText) findViewById(R.id.edit_text_bubble_text_input);
        mShowBubbleTextView = (Button) findViewById(R.id.button_bubble_text_show_bubble_text_view);
        mSendMessageToBubbleTextViewButton =
                (Button) findViewById(R.id.button_bubble_text_send_message_to_bubble_text_view);
    }

    private void setupViews() {
        mShowBubbleTextView.setOnClickListener(this);
        mSendMessageToBubbleTextViewButton.setOnClickListener(this);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getString(R.string.all_bubble_text));
        }
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
            case R.id.button_bubble_text_show_bubble_text_view:
                startService(new Intent(this, BubbleTextService.class));

                break;

            case R.id.button_bubble_text_send_message_to_bubble_text_view:
                saveMessage();

                break;
        }
    }

    private void saveMessage() {
        String input = mInputText.getText().toString();

        if (TextUtils.isEmpty(input)) {
            return;
        }

        startService(SaveMessageService.generateSaveMessageIntent(this, input));
    }
}
