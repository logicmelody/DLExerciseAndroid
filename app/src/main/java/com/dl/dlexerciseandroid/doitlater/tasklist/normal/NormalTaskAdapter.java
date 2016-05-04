package com.dl.dlexerciseandroid.doitlater.tasklist.normal;

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
 * Created by logicmelody on 2016/5/4.
 */
public class NormalTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    private List<Task> mTaskData;

    private class NormalTaskViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public TextView title;
        public TextView description;
        public TextView time;

        public NormalTaskViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.text_view_task_item_title);
            description = (TextView) itemView.findViewById(R.id.text_view_task_item_description);
            time = (TextView) itemView.findViewById(R.id.text_view_task_item_time);
        }
    }


    public NormalTaskAdapter(Context context, List<Task> taskData) {
        mContext = context;
        mTaskData = taskData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalTaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_do_it_later_normal_task,
                                        parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NormalTaskViewHolder vh = (NormalTaskViewHolder) holder;

        vh.title.setText(mTaskData.get(position).title);
        vh.description.setText(mTaskData.get(position).description);
        vh.time.setText(Utils.timeToString(mTaskData.get(position).time, Utils.DataFormat.YYYYMMDDHHMM));
    }

    @Override
    public int getItemCount() {
        return mTaskData.size();
    }
}
