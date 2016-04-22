package com.dl.dlexerciseandroid.doitlater.tasklist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.Task;
import com.dl.dlexerciseandroid.utility.utils.Utils;

import java.util.List;

/**
 * Created by logicmelody on 2016/4/18.
 */
public class DoItLaterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<Task> mDataSet;

    private class TaskViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView title;
        public TextView description;
        public TextView time;

        public TaskViewHolder(View itemView) {
            super(itemView);
            findViews();
            setupViews();
        }

        private void setupViews() {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        private void findViews() {
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.text_view_task_item_title);
            description = (TextView) itemView.findViewById(R.id.text_view_task_item_description);
            time = (TextView) itemView.findViewById(R.id.text_view_task_item_time);
        }
    }


    // 在Adapter的constructor中將需要顯示在RecyclerView上的data bind起來，
    // 要注意的是，之後這個List所指的記憶體空間就不能更動，不然會有不可預期的問題
    public DoItLaterAdapter(Context context, List<Task> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_task_do_it_later, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TaskViewHolder taskViewHolder = (TaskViewHolder) holder;

        taskViewHolder.title.setText(mDataSet.get(position).title);
        taskViewHolder.description.setText(mDataSet.get(position).description);
        taskViewHolder.time.setText(Utils.timeToString(mDataSet.get(position).time, Utils.DataFormat.YYYYMMDDHHMM));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
