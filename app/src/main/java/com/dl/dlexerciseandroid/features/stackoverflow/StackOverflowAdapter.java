package com.dl.dlexerciseandroid.features.stackoverflow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.data.model.stackoverflow.SOItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2017/11/6.
 */

public class StackOverflowAdapter extends RecyclerView.Adapter<StackOverflowAdapter.StackOverflowViewHolder> {

    private Context mContext;

    private List<SOItem> mDatas;


    public class StackOverflowViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;


        public StackOverflowViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text_view_stack_overflow_item);
        }

        public void bind(SOItem soItem) {
            mTextView.setText(soItem.getSOOwner().getDisplayName());
        }
    }

    public StackOverflowAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }

    @Override
    public StackOverflowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StackOverflowViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_stack_overflow, parent, false));
    }

    @Override
    public void onBindViewHolder(StackOverflowViewHolder holder, int position) {
        holder.bind(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void add(SOItem soItem) {
        mDatas.add(soItem);
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}
