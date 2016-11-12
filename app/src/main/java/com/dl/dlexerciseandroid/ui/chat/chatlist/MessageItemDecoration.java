package com.dl.dlexerciseandroid.ui.chat.chatlist;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dl.dlexerciseandroid.R;

public class MessageItemDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;


    public MessageItemDecoration(Context context) {
        mContext = context;
    }

    // 用來設定每一個在RecyclerView中item的margin offset，如此一來可以在item之間創造出margin
    // 主要要設定每一個list item的
    // 1. Top margin
    // 2. Bottom margin
    // 3. Left margin
    // 4. Right margin
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemCount = parent.getAdapter().getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);

        // 單位是pixel
        int boundaryMargin = mContext.getResources().getDimensionPixelSize(R.dimen.margin_boundary_chat_list);
        int itemMargin = mContext.getResources().getDimensionPixelSize(R.dimen.margin_between_chat_message_item);

        // Top, bottom
        // 第一個item的top = boundaryMargin
        if (itemPosition == 0) {
            outRect.top = boundaryMargin;
            outRect.bottom = itemMargin;

        // 最後一個item的bottom = boundaryMargin
        } else if (itemPosition == itemCount - 1) {
            outRect.top = itemMargin;
            outRect.bottom = boundaryMargin;

        } else {
            outRect.top = itemMargin;
            outRect.bottom = itemMargin;
        }

        // Left, right
        outRect.left = boundaryMargin;
        outRect.right = boundaryMargin;

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