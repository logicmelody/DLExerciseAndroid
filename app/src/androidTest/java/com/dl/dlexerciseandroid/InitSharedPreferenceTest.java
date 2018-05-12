package com.dl.dlexerciseandroid;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import com.dl.dlexerciseandroid.utils.PreferenceUtils;

import org.hamcrest.MatcherAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.is;

/**
 * Created by logicmelody on 2017/5/23.
 */

// 這是一個簡單的example，用來測試launch app時所產生的shared preference是否正確
@RunWith(AndroidJUnit4.class)
@SmallTest
public class InitSharedPreferenceTest  {

    private Context mContext;


    @Before
    public void setup() {
        // 在Unit test中，若是想要拿到app的context，可以用此種做法
        mContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void initSharedPreferenceTest() {
        MatcherAssert.assertThat(
                        PreferenceManager.getDefaultSharedPreferences(mContext)
                                .getString(PreferenceUtils.PREFERENCE_TESTING, ""),
                is(PreferenceUtils.PREFERENCE_TESTING));
    }
}
