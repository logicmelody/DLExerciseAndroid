package com.dl.dlexerciseandroid.ui.chat.viewholder;

import android.view.View;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.message.Message;

/**
 * Created by dannylin on 2016/11/16.
 */

public class YingNormalMessageViewHolder extends BaseViewHolder {

    public TextView messageText;


    public YingNormalMessageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    void findViews(View itemView) {
        messageText = (TextView) itemView.findViewById(R.id.text_view_ying_normal_message_text);
    }

    @Override
    public void bind(Message message) {
        messageText.setText(message.getMessage());
    }
}
