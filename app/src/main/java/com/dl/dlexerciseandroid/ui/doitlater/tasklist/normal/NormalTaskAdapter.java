package com.dl.dlexerciseandroid.ui.doitlater.tasklist.normal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.Task;
import com.dl.dlexerciseandroid.utility.utils.DbUtils;
import com.dl.dlexerciseandroid.utility.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/5/4.
 */
public class NormalTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private class NormalTaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View view;

        public ViewGroup contentContainer;
        public ViewGroup deletedContainer;

        public TextView title;
        public TextView description;
        public TextView time;
        public TextView undoButton;


        public NormalTaskViewHolder(View itemView) {
            super(itemView);
            findViews();
            setupViews();
        }

        private void findViews() {
            view = itemView;
            contentContainer = (ViewGroup) itemView.findViewById(R.id.view_group_normal_task_content_container);
            deletedContainer = (ViewGroup) itemView.findViewById(R.id.view_group_normal_task_deleted_container);
            title = (TextView) itemView.findViewById(R.id.text_view_task_item_title);
            description = (TextView) itemView.findViewById(R.id.text_view_task_item_description);
            time = (TextView) itemView.findViewById(R.id.text_view_task_item_time);
            undoButton = (TextView) itemView.findViewById(R.id.text_view_normal_task_undo_button);
        }

        private void setupViews() {
            contentContainer.setOnClickListener(this);
            undoButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.view_group_normal_task_content_container:
                    Log.d("danny", "Click position = " + getAdapterPosition());
                    break;

                case R.id.text_view_normal_task_undo_button:
                    int restorePosition = mPendingRemovePosition;
                    mPendingRemovePosition = -1;
                    notifyItemChanged(restorePosition);

                    break;
            }
        }
    }

    private Context mContext;

    private List<Task> mDataList;
    private List<Long> mDeletedTaskList = new ArrayList<>();

    private int mPendingRemovePosition = -1;


    public NormalTaskAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    public void pendingRemove(int position) {
        // 記錄現在哪一筆task已經被swipe了
        mPendingRemovePosition = position;

        // 我們要把task item swipe之後的畫面show出來，使用notifyItemChanged()統一交由onBindViewHolder()處理item view的改變
        notifyItemChanged(position);
    }

    public void remove() {
        if (mPendingRemovePosition == -1) {
            return;
        }

        long removedTaskId = mDataList.get(mPendingRemovePosition).id;
        mDeletedTaskList.add(removedTaskId);

        mDataList.remove(mPendingRemovePosition);

        // 跟notifyDataSetChanged()不一樣，不會rebind全部的data，只會通知被刪除的那筆資料，然後其他存在的data的position會變動
        // 這是RecyclerView強大的地方，使用這個method，其他的item移到新的position會有動畫
        notifyItemRemoved(mPendingRemovePosition);

        mPendingRemovePosition = -1;
        DbUtils.deleteTask(mContext, removedTaskId);
    }

    public int getPendingRemovePosition() {
        return mPendingRemovePosition;
    }

    // 為了要讓notifyItemRemoved()的動畫正常運作，我們可以先把要delete的task id先存在一個List裡面，
    // 等到Fragment onStop()的時候，我們在一起把task delete掉
    public void deleteTaskFromDb() {
        Log.d("danny", "Delete normal task from db");

        for (long taskId : mDeletedTaskList) {
            DbUtils.deleteTask(mContext, taskId);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_do_it_later_normal_task,
                                        parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalTaskViewHolder vh = (NormalTaskViewHolder) holder;

        vh.title.setText(mDataList.get(position).title);
        vh.description.setText(mDataList.get(position).description);
        vh.time.setText(GeneralUtils.timeToString(mDataList.get(position).time, GeneralUtils.DataFormat.YYYYMMDDHHMM));

        // 被swipe的item經由notifyItemChanged()，會執行這裡
        // Show出swipe之後的畫面
        if (position == mPendingRemovePosition) {
            vh.contentContainer.setVisibility(View.INVISIBLE);
            vh.deletedContainer.setVisibility(View.VISIBLE);
            vh.undoButton.setEnabled(true);

        // 還原本來的畫面
        } else {
            vh.contentContainer.setVisibility(View.VISIBLE);
            vh.deletedContainer.setVisibility(View.INVISIBLE);
            vh.undoButton.setEnabled(false);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void clear() {
        mDataList.clear();
    }

    public void add(Task task) {
        mDataList.add(task);
    }

    public int getDataListSize() {
        return mDataList.size();
    }
}
