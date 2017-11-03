package com.dl.dlexerciseandroid.ui.chat.viewholder;

import android.view.View;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.model.message.Message;

/**
 * Created by dannylin on 2016/11/16.
 */

/**
 * 每一個list item都需要一個view holder去記錄這個view中包含的元件
 */
public class NormalMessageViewHolder extends BaseViewHolder {

    public TextView messageText;


    public NormalMessageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void findViews(View itemView) {
        messageText = (TextView) itemView.findViewById(R.id.text_view_normal_message_text);
    }

    @Override
    public void bind(Message message) {
        messageText.setText(message.getMessage());
    }
}
