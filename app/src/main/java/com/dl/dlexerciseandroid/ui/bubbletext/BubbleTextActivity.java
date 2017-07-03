package com.dl.dlexerciseandroid.ui.bubbletext;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2016/6/27.
 */
public class BubbleTextActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = BubbleTextActivity.class.getName();

    private Button mShowBubbleTextView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bubble_text);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
    }

    private void findViews() {
        mShowBubbleTextView = (Button) findViewById(R.id.button_bubble_text_show_bubble_text_view);
    }

    private void setupViews() {
        mShowBubbleTextView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_bubble_text_show_bubble_text_view:
                startService(new Intent(this, BubbleTextService.class));

                break;
        }
    }
}
