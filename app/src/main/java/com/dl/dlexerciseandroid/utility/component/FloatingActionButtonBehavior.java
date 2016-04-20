package com.dl.dlexerciseandroid.utility.component;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by logicmelody on 2016/4/20.
 */

// 這個class要搭配CoordinatorLayout使用，實作當CoordinatorLayout中的anchor view發生某些特定事件的時候
// 對應的元件要做什麼動作

// FloatingActionButton本來就有預設的behavior：當SnackBar跳出來的時候，FloatingActionButton會往上浮
// 這邊我們必須extend FloatingActionButton.Behavior這個class，實作當CoordinatorLayout中的view有
// 發生scroll事件的時候，FloatingActionButton要做什麼
public class FloatingActionButtonBehavior extends FloatingActionButton.Behavior {

    // 因為我們讓behavior可以在xml中設定，所以需要加此constructor
    public FloatingActionButtonBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                                       View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
               super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);

        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide();

        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
        }
    }
}
