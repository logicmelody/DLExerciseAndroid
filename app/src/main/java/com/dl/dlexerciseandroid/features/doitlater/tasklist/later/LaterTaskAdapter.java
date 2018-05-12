package com.dl.dlexerciseandroid.features.doitlater.tasklist.later;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.features.doitlater.tasklist.main.DoItLaterViewItem;
import com.dl.dlexerciseandroid.utils.DbUtils;
import com.dl.dlexerciseandroid.utils.DoItLaterUtils;
import com.dl.dlexerciseandroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/4/18.
 */
public class LaterTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private Handler mHandler;

    private RecyclerView mRecyclerView;

    private List<DoItLaterViewItem> mDataList;

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
            String laterCallback = mDataList.get(getAdapterPosition()).task.laterCallback;

            if (TextUtils.isEmpty(laterCallback)) {
                return;
            }

            mContext.startActivity(DoItLaterUtils.composeDoItLaterIntentFrom(mContext, laterCallback));
        }
    }


    // 在Adapter的constructor中將需要顯示在RecyclerView上的data bind起來，
    // 要注意的是，之後這個List所指的記憶體空間就不能更動，不然會有不可預期的問題
    public LaterTaskAdapter(Context context, RecyclerView recyclerView) {
        mContext = context;
        mHandler = new Handler();
        mRecyclerView = recyclerView;
        mDataList = new ArrayList<>();
    }

    /**
     * This method will remove an item from our task list, and notifies the adapter that an item has been removed.
     * By notifying the adapter, we are not rebinding the data but simply altering the positions
     *
     * @param position
     */
    public void removeWithSnackBar(int position) {
        final long removedTaskId = mDataList.get(position).task.id;
        final int removedPosition = position;
        final DoItLaterViewItem removedTaskItem = mDataList.get(position);

        mDataList.remove(position);

        // 跟notifyDataSetChanged()不一樣，不會rebind全部的data，只會通知被刪除的那筆資料，然後其他存在的data的position會變動
        // 這是RecyclerView強大的地方，使用這個method，其他的item移到新的position會有動畫
        notifyItemRemoved(position);

        // 設定Snackbar
        Snackbar snackbar = Snackbar.make(mRecyclerView, R.string.do_it_later_delete_task, Snackbar.LENGTH_LONG);

        // 設定Undo button，如果按下Undo的話，會把本來被刪除的task加回來
        snackbar.setAction(R.string.all_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataList.add(removedPosition, removedTaskItem);
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

    public void removeWithToast(int position) {
        final long removedTaskId = mDataList.get(position).task.id;
        final int removedPosition = position;
        final DoItLaterViewItem removedTaskItem = mDataList.get(position);

        mDataList.remove(position);

        // 跟notifyDataSetChanged()不一樣，不會rebind全部的data，只會通知被刪除的那筆資料，然後其他存在的data的position會變動
        // 這是RecyclerView強大的地方，使用這個method，其他的item移到新的position會有動畫
        notifyItemRemoved(position);

        // 因為我們想要模擬出系統toast的樣子，所以new一個sample toast來取出他的一些property，並且指定給PopupWindow
        final Toast sampleToast = new Toast(mContext);
        final PopupWindow undoToast = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final Runnable deleteTaskRunnable = new Runnable() {
            @Override
            public void run() {
                DbUtils.deleteTask(mContext, removedTaskId);
                undoToast.dismiss();
            }
        };

        ViewGroup undoToastView = (ViewGroup) LayoutInflater.from(mContext).inflate(R.layout.layout_all_undo_toast, null);
        TextView undoButton = (TextView) undoToastView.findViewById(R.id.text_view_undo_toast_undo_button);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 如果按下Undo button，記得要把deleteTaskRunnable從message queue中給remove掉，
                // 不然那個thread三秒之後還是會把task從db中刪除
                mHandler.removeCallbacks(deleteTaskRunnable);

                mDataList.add(removedPosition, removedTaskItem);
                notifyItemInserted(removedPosition);
                undoToast.dismiss();
            }
        });

        // PopupWindow可以指定自己的layout
        undoToast.setContentView(undoToastView);
        undoToast.setAnimationStyle(android.R.style.Animation_Toast);
        undoToast.showAtLocation(mRecyclerView.getRootView(), sampleToast.getGravity(),
                                 sampleToast.getXOffset(), sampleToast.getYOffset());

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 將刪除task的runnable塞到message queue中，3秒之後會執行
                mHandler.postDelayed(deleteTaskRunnable, 3000);
            }
        }).start();
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
        switch (mDataList.get(position).viewType) {
            case DoItLaterViewItem.ViewType.NORMAL:
                onBindNormalTask((TaskViewHolder) holder, position);
                break;

            case DoItLaterViewItem.ViewType.LATER:
                onBindLaterTask((LaterTaskViewHolder) holder, position);
                break;
        }
    }

    private void onBindNormalTask(TaskViewHolder holder, int position) {
        holder.title.setText(mDataList.get(position).task.title);
        holder.description.setText(mDataList.get(position).task.description);
        holder.time.setText(Utils.timeToString(mDataList.get(position).task.time, Utils.DataFormat.YYYYMMDDHHMM));
    }

    private void onBindLaterTask(LaterTaskViewHolder holder, int position) {
        holder.title.setText(mDataList.get(position).task.title);
        holder.description.setText(mDataList.get(position).task.description);
        holder.time.setText(Utils.timeToString(mDataList.get(position).task.time, Utils.DataFormat.YYYYMMDDHHMM));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDataList.get(position).viewType;
    }

    public void clear() {
        mDataList.clear();
    }

    public void add(DoItLaterViewItem doItLaterViewItem) {
        mDataList.add(doItLaterViewItem);
    }

    public int getDataListSize() {
        return mDataList.size();
    }
}
