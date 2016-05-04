package com.dl.dlexerciseandroid.doitlater.tasklist.main;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dl.dlexerciseandroid.R;

public class TaskItemDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;


    public TaskItemDecoration(Context context) {
        mContext = context;
    }

    // 用來設定每一個在RecyclerView中item的offset，如此一來可以在item之間創造出margin
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemCount = parent.getAdapter().getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);

        // 單位是pixel
        int windowMargin = mContext.getResources().getDimensionPixelSize(R.dimen.padding_all_window);
        int itemMargin = mContext.getResources().getDimensionPixelSize(R.dimen.margin_do_it_later_between_task_item);




//        if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
//            outRect.top = mDivider.getIntrinsicHeight();
//
//            if (mShowLastPadding && itemPosition == itemCount - 1) {
//                outRect.bottom = mLastPaddingSize;
//            }
//
//        } else {
//            outRect.left = mDivider.getIntrinsicWidth();
//
//            if (mShowLastPadding && itemPosition == itemCount - 1) {
//                outRect.right = mLastPaddingSize;
//            }
//        }
    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else {
            throw new IllegalStateException(
                    "DividerItemDecoration can only be used with a LinearLayoutManager.");
        }
    }
}