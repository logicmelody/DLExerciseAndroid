package com.dl.dlexerciseandroid.doitlater.tasklist.normal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.dl.dlexerciseandroid.R;

/**
 * Created by logicmelody on 2016/5/5.
 */

/**
 * 在RecyclerView中，如果要對item進行swipe to dismiss或是drag & drop的動作，可以利用這個class來實作
 */
public class NormalTaskItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private NormalTaskAdapter mNormalTaskAdapter;

    private Drawable mBackground;
    private Drawable mTrashCanIcon;
    private int mTrashCanMargin = 0;

    /**
     * Creates a Callback for the given drag and swipe allowance. These values serve as
     * defaults
     * and if you want to customize behavior per ViewHolder, you can override
     * {@link #getSwipeDirs(RecyclerView, RecyclerView.ViewHolder)}
     * and / or {@link #getDragDirs(RecyclerView, RecyclerView.ViewHolder)}.
     *
     * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
     *                  composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
     *                  #END},
     *                  {@link #UP} and {@link #DOWN}.
     */
    public NormalTaskItemTouchHelper(Context context, NormalTaskAdapter adapter) {
        // Constructor裡面可以設定這個ItemTouchHelper可以接受哪些動作：
        // 上下drag
        // 左右swipe
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);

        mNormalTaskAdapter = adapter;
        mBackground = new ColorDrawable(context.getResources().getColor(R.color.background_normal_task_swiped));
        mTrashCanIcon = context.getResources().getDrawable(R.drawable.ic_normal_task_trash_can);
        mTrashCanMargin = context.getResources().getDimensionPixelSize(R.dimen.padding_task_item);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        // 當某一個item已經是swiped的狀態，如果再swipe同一個item的話，swipe完成之後就會直接刪除這筆資料
        if (viewHolder.getAdapterPosition() == mNormalTaskAdapter.getPendingRemovePosition()) {
            mNormalTaskAdapter.remove();

        } else {
            // Swipe結束之後，itemView會保留在swipe結束之後的位置，這樣會導致在notifyItemChanged()的時候，
            // 會多一個translation動畫是把itemView移回本來的位置，但我們只想要fade in/out的效果
            // 所以swipe結束之後，我們先把itemVie設回原位
            viewHolder.itemView.setTranslationX(0);
            mNormalTaskAdapter.pendingRemove(viewHolder.getAdapterPosition());
        }
    }

    /**
     * If you would like to customize how your View's respond to user interactions, this is a good place to override.
     * Default implementation translates the child by the given dX, dY
     *
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */

    // 利用這個method，我們可以
    // 1. 對正在滑動的view做處理：GoogleKeep的卡片變透明
    // 2. 對滑動的view的下面那層做處理：Gmail的Undo
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;

        switch (actionState) {

            // 只要是在swipe中，都會進到這個action state
            case ItemTouchHelper.ACTION_STATE_SWIPE:
                Log.d("danny", "Swipe dX = " + dX);

                // itemView.getLeft()和itemView.getRight()都是原本起始的位子
                Log.d("danny", "Swipe itemView getLeft = " + itemView.getLeft());
                Log.d("danny", "Swipe itemView getRight = " + itemView.getRight());

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                // 當某一個item已經是swiped的狀態，如果再swipe同一個item的話，那就只會執行最基本的swipe動作
                // 但是背景還是要畫成灰色
                if (viewHolder.getAdapterPosition() == mNormalTaskAdapter.getPendingRemovePosition()) {
                    if (dX > 0) {
                        mBackground.setBounds(itemView.getLeft(), itemView.getTop(),
                                              itemView.getLeft() + Math.round(dX), itemView.getBottom());

                    } else {
                        mBackground.setBounds(itemView.getLeft() + Math.round(dX), itemView.getTop(),
                                              itemView.getRight(), itemView.getBottom());
                    }

                    mBackground.draw(c);

                } else {
                    int itemHeight = itemView.getBottom() - itemView.getTop();
                    int intrinsicWidth = mTrashCanIcon.getIntrinsicWidth();
                    int intrinsicHeight = mTrashCanIcon.getIntrinsicHeight();

                    // 根據往左swipe或是往右swipe，來畫底下的背景
                    // 以及trash can icon的位置
                    // 都是用itemView.getLeft()和itemView.getRight()原本起始的位子(ex: (0, 720))來當基準，
                    // 算出trash can icon的left, top, right, bottom
                    if (dX > 0) {
                        mBackground.setBounds(itemView.getLeft(), itemView.getTop(),
                                itemView.getLeft() + Math.round(dX), itemView.getBottom());

                        int trashCanLeft = itemView.getLeft() + mTrashCanMargin;
                        int trashCanRight = itemView.getLeft() + mTrashCanMargin + intrinsicWidth;
                        int trashCanTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                        int trashCanBottom = trashCanTop + intrinsicHeight;

                        mTrashCanIcon.setBounds(trashCanLeft, trashCanTop, trashCanRight, trashCanBottom);

                    } else {
                        mBackground.setBounds(itemView.getLeft() + Math.round(dX), itemView.getTop(),
                                itemView.getRight(), itemView.getBottom());

                        int trashCanLeft = itemView.getRight() - mTrashCanMargin - intrinsicWidth;
                        int trashCanRight = itemView.getRight() - mTrashCanMargin;
                        int trashCanTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                        int trashCanBottom = trashCanTop + intrinsicHeight;

                        mTrashCanIcon.setBounds(trashCanLeft, trashCanTop, trashCanRight, trashCanBottom);
                    }

                    mBackground.draw(c);
                    mTrashCanIcon.draw(c);
                }

                break;
        }
    }
}
