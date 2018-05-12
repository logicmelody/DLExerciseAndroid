package com.dl.dlexerciseandroid.features.doitlater.tasklist.later;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

/**
 * Created by logicmelody on 2016/5/5.
 */

/**
 * 在RecyclerView中，如果要對item進行swipe to dismiss或是drag & drop的動作，可以利用這個class來實作
 */
public class LaterTaskItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private LaterTaskAdapter mLaterTaskAdapter;


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
    public LaterTaskItemTouchHelper(LaterTaskAdapter adapter) {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mLaterTaskAdapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mLaterTaskAdapter.removeWithSnackBar(viewHolder.getAdapterPosition());
        //mLaterTaskAdapter.removeWithToast(viewHolder.getAdapterPosition());
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

        switch (actionState) {
            case ItemTouchHelper.ACTION_STATE_SWIPE:
                Log.d("danny", "dX = " + dX);
                Log.d("danny", "Item is being swiped");

                // 在swipe的過程中，我們利用dX來判斷要不要將正在滑動的view變透明
                if (dX == 0) {
                    viewHolder.itemView.setAlpha(1);

                } else {
                    viewHolder.itemView.setAlpha(0.3F);
                }

                break;
        }
    }
}
