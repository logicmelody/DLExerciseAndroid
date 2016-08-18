package com.dl.dlexerciseandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2016/8/18.
 */
public class DurationTextView extends TextView {

    private Context mContext;

    private String mTemplate;


    // 一個View都會有以下三種constructor
    public DurationTextView(Context context) {
        super(context);
        mContext = context;
    }

    public DurationTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        getAttributes(attrs);
    }

    public DurationTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private void getAttributes(AttributeSet attrs) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(
                attrs,
                com.dl.dlexerciseandroid.R.styleable.DurationTextView,
                0, 0);
        try {
            mTemplate = a.getString(com.dl.dlexerciseandroid.R.styleable.DurationTextView_template);

        } finally {
            a.recycle();
        }

        if (TextUtils.isEmpty(mTemplate) || !mTemplate.contains("%s")) {
            mTemplate = "%s";
        }
    }

    /**
     * Updates the text of the view with the new duration, properly
     * formatted
     *
     * @param duration
     * The duration in seconds
     */
    public void setDuration(float duration) {
        int durationInMinutes = Math.round(duration / 60);
        int hours = durationInMinutes / 60;
        int minutes = durationInMinutes % 60;

        String hourText = "";
        String minuteText = "";

        if (hours > 0) {
            hourText = hours + (hours == 1 ? " hour " : " hours ");
        }

        if (minutes > 0) {
            minuteText = minutes + (minutes == 1 ? " minute" : " minutes");
        }

        if (hours == 0 && minutes == 0) {
            minuteText = "less than 1 minute";
        }

        // String.format(String, String... args)，用這種方式可以動態塞字串到String中
        String durationText = String.format(mTemplate, hourText + minuteText);
        setText(Html.fromHtml(durationText), BufferType.SPANNABLE);
    }
}