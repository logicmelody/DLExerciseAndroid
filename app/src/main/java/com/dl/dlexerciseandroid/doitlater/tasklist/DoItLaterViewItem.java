package com.dl.dlexerciseandroid.doitlater.tasklist;

import com.dl.dlexerciseandroid.datastructure.Task;

/**
 * Created by logicmelody on 2016/4/28.
 */

// 在RecyclerView Adapter中，我們可以藉由getItemViewType()這個method來取得現在這筆data要用哪一個view呈現
// 所以我們在一般的data之外套上這個class
public class DoItLaterViewItem {

    public static final class ViewType {
        public static final int NORMAL = 0;
        public static final int LATER = 1;
    }

    public Task task;
    public int viewType;

    public DoItLaterViewItem(Task task, int viewType) {
        this.task = task;
        this.viewType = viewType;
    }
}
