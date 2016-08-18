package com.dl.dlexerciseandroid.ui.coordinatorlayout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

import java.util.List;

/**
 * Created by logicmelody on 2016/4/20.
 */
public class NumberListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;

    List<Integer> mDataSet;


    private static class NumberViewHolder extends RecyclerView.ViewHolder {

        public TextView number;

        public NumberViewHolder(View itemView) {
            super(itemView);
            number = (TextView) itemView.findViewById(R.id.text_view_coordinator_layout_number);
        }
    }

    public NumberListAdapter(Context context, List<Integer> dataSet) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NumberViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_coordinator_layout_number_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NumberViewHolder numberVH = (NumberViewHolder) holder;

        numberVH.number.setText(String.valueOf(mDataSet.get(position)));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
