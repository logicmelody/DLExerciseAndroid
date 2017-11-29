package com.dl.dlexerciseandroid.ui.coordinatorlayout;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.volokh.danylo.visibility_utils.items.ListItem;

/**
 * Created by dannylin on 2017/11/28.
 */

public class NumberItem implements ListItem {

    private static final String TAG = NumberItem.class.getSimpleName();

    private String mText;

    private final Rect mCurrentViewRect = new Rect();


    public NumberItem(String text) {
        mText = text;
    }

    public String getInteger() {
        return mText;
    }

    public void setInteger(String integer) {
        this.mText = integer;
    }

    @Override
    public int getVisibilityPercents(View view) {
        int percents = 100;

        view.getLocalVisibleRect(mCurrentViewRect);

        int height = view.getHeight();

        if (viewIsPartiallyHiddenTop()) {
            // view is partially hidden behind the top edge
            percents = (height - mCurrentViewRect.top) * 100 / height;

        } else if (viewIsPartiallyHiddenBottom(height)) {
            percents = mCurrentViewRect.bottom * 100 / height;
        }

        return percents;
    }

    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        Log.d("danny", "setActive item = " + mText);
    }

    @Override
    public void deactivate(View currentView, int position) {
        Log.d("danny", "deactivate item = " + mText);
    }
}
