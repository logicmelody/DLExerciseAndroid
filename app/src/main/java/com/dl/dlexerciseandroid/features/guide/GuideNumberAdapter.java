package com.dl.dlexerciseandroid.features.guide;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2017/9/22.
 */

public class GuideNumberAdapter extends RecyclerView.Adapter<GuideNumberAdapter.NumberViewHolder> {

    private Context mContext;

    private List<Integer> mDataList;


    public class NumberViewHolder extends RecyclerView.ViewHolder {

        public TextView number;


        public NumberViewHolder(View itemView) {
            super(itemView);
            findViews(itemView);
            setupViews(itemView);
        }

        private void findViews(View itemView) {
            number = (TextView) itemView.findViewById(R.id.text_view_item_layout_number);
        }

        private void setupViews(View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, number.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void bind(int num) {
            number.setText(String.valueOf(num));
        }
    }

    public GuideNumberAdapter(Context context) {
        mContext = context;
        mDataList = new ArrayList<>();
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new NumberViewHolder(
                LayoutInflater.from(mContext).inflate(R.layout.item_layout_number_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(NumberViewHolder numberViewHolder, int i) {
        numberViewHolder.bind(mDataList.get(i));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void add(int num) {
        mDataList.add(num);
    }

    public void refresh() {
        notifyDataSetChanged();
    }
}
