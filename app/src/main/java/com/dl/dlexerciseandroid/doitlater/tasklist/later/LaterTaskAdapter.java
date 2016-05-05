package com.dl.dlexerciseandroid.doitlater.tasklist.later;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.doitlater.tasklist.main.DoItLaterViewItem;
import com.dl.dlexerciseandroid.utility.utils.DbUtils;
import com.dl.dlexerciseandroid.utility.utils.DoItLaterUtils;
import com.dl.dlexerciseandroid.utility.utils.Utils;

import java.util.List;

/**
 * Created by logicmelody on 2016/4/18.
 */
public class LaterTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private RecyclerView mRecyclerView;

    private List<DoItLaterViewItem> mDataSet;

    private class TaskViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView title;
        public TextView description;
        public TextView time;

        public TaskViewHolder(View itemView) {
            super(itemView);
            findViews(itemView);
            setupViews();
        }

        private void findViews(View itemView) {
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.text_view_task_item_title);
            description = (TextView) itemView.findViewById(R.id.text_view_task_item_description);
            time = (TextView) itemView.findViewById(R.id.text_view_task_item_time);
        }

        private void setupViews() {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private class LaterTaskViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView title;
        public TextView description;
        public TextView time;

        public LaterTaskViewHolder(View itemView) {
            super(itemView);
            findViews(itemView);
            setupViews();
        }

        private void findViews(View itemView) {
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.text_view_task_item_title);
            description = (TextView) itemView.findViewById(R.id.text_view_task_item_description);
            time = (TextView) itemView.findViewById(R.id.text_view_task_item_time);
        }

        private void setupViews() {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fireDoItLaterIntent();
                }
            });
        }

        private void fireDoItLaterIntent() {
            String laterCallback = mDataSet.get(getAdapterPosition()).task.laterCallback;

            if (TextUtils.isEmpty(laterCallback)) {
                return;
            }

            mContext.startActivity(DoItLaterUtils.composeDoItLaterIntentFrom(mContext, laterCallback));
        }
    }


    // 在Adapter的constructor中將需要顯示在RecyclerView上的data bind起來，
    // 要注意的是，之後這個List所指的記憶體空間就不能更動，不然會有不可預期的問題
    public LaterTaskAdapter(Context context, RecyclerView recyclerView, List<DoItLaterViewItem> dataSet) {
        mContext = context;
        mRecyclerView = recyclerView;
        mDataSet = dataSet;
    }

    /**
     * This method will remove an item from our task list, and notifies the adapter that an item has been removed.
     * By notifying the adapter, we are not rebinding the data but simply altering the positions
     *
     * @param position
     */
    public void remove(int position) {
        final long removedTaskId = mDataSet.get(position).task.id;
        final int removedPosition = position;
        final DoItLaterViewItem removedTaskItem = mDataSet.get(position);

        mDataSet.remove(position);

        // 跟notifyDataSetChanged()不一樣，不會rebind全部的data，只會通知被刪除的那筆資料，然後其他存在的data的position會變動
        // 這是RecyclerView強大的地方，使用這個method，其他的item移到新的position會有動畫
        notifyItemRemoved(position);

        // 設定Snackbar
        Snackbar snackbar = Snackbar.make(mRecyclerView, R.string.do_it_later_delete_task, Snackbar.LENGTH_LONG);

        // 設定Undo button，如果按下Undo的話，會把本來被刪除的task加回來
        snackbar.setAction(R.string.all_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataSet.add(removedPosition, removedTaskItem);
                notifyItemInserted(removedPosition);
            }
        });

        // 當Snackbar消失時，而且消失的原因不是點擊Undo button，就會真的把task從db中刪除
        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if (Snackbar.Callback.DISMISS_EVENT_ACTION == event) {
                   return;
                }

                DbUtils.deleteTask(mContext, removedTaskId);
            }
        });

        snackbar.show();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DoItLaterViewItem.ViewType.NORMAL:
                return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_do_it_later_normal_task,
                                          parent, false));

            case DoItLaterViewItem.ViewType.LATER:
                return new LaterTaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_do_it_later_later_task,
                                          parent, false));

            default:
                return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_do_it_later_normal_task,
                                          parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (mDataSet.get(position).viewType) {
            case DoItLaterViewItem.ViewType.NORMAL:
                onBindNormalTask((TaskViewHolder) holder, position);
                break;

            case DoItLaterViewItem.ViewType.LATER:
                onBindLaterTask((LaterTaskViewHolder) holder, position);
                break;
        }
    }

    private void onBindNormalTask(TaskViewHolder holder, int position) {
        holder.title.setText(mDataSet.get(position).task.title);
        holder.description.setText(mDataSet.get(position).task.description);
        holder.time.setText(Utils.timeToString(mDataSet.get(position).task.time, Utils.DataFormat.YYYYMMDDHHMM));
    }

    private void onBindLaterTask(LaterTaskViewHolder holder, int position) {
        holder.title.setText(mDataSet.get(position).task.title);
        holder.description.setText(mDataSet.get(position).task.description);
        holder.time.setText(Utils.timeToString(mDataSet.get(position).task.time, Utils.DataFormat.YYYYMMDDHHMM));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).viewType;
    }
}
