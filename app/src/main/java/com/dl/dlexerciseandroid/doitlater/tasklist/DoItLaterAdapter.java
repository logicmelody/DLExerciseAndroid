package com.dl.dlexerciseandroid.doitlater.tasklist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utility.utils.Utils;

import java.util.List;

/**
 * Created by logicmelody on 2016/4/18.
 */
public class DoItLaterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

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
                    Toast.makeText(mContext, "Click later task", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    // 在Adapter的constructor中將需要顯示在RecyclerView上的data bind起來，
    // 要注意的是，之後這個List所指的記憶體空間就不能更動，不然會有不可預期的問題
    public DoItLaterAdapter(Context context, List<DoItLaterViewItem> dataSet) {
        mContext = context;
        mDataSet = dataSet;
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
