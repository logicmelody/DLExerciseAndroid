package com.dl.dlexerciseandroid.ui.time;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeFragment extends Fragment {

    public static final String TAG = TimeFragment.class.getName();

    private Context mContext;

    private TextView mTimeMillisecond;
    private TextView mUsTimeZoneTime;
    private TextView mDefaultTimeZoneTime;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_time, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupTime();
    }

    private void findViews() {
        mTimeMillisecond = (TextView) getView().findViewById(R.id.text_view_time_millisecond);
        mUsTimeZoneTime = (TextView) getView().findViewById(R.id.text_view_time_us_time_zone_time);
        mDefaultTimeZoneTime = (TextView) getView().findViewById(R.id.text_view_time_default_time_zone_time);
    }

    private void setupTime() {
        // Calendar拿到的物件，經由calendar.getTimeInMillis()所拿到的millisecond是UTC的標準時間，只會有一種結果
        // Returns this Calendar's time value in milliseconds.
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormatUS = new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy");
        SimpleDateFormat dateFormatDefault = new SimpleDateFormat("EE MMM dd HH:mm:ss zzz yyyy");

        // 如果要根據timezone的不同來顯示對應的時間，需要在轉成text的時候進行設定
        // 也就是對SimpleDateFormat設定timezone
        TimeZone timeZone = TimeZone.getTimeZone("US");
        dateFormatUS.setTimeZone(timeZone);

//        Log.d("danny", "UTC 1 " + calendar.getTimeInMillis());
//        Log.d("danny", "UTC 2 " + calendar.getTime().getTime());

        mTimeMillisecond.setText(String.valueOf(calendar.getTimeInMillis()));
        mUsTimeZoneTime.setText(dateFormatUS.format(calendar.getTime()));
        mDefaultTimeZoneTime.setText(dateFormatDefault.format(calendar.getTime()));
    }
}
