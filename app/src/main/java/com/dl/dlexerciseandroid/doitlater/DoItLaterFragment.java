package com.dl.dlexerciseandroid.doitlater;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.datastructure.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/4/18.
 */
public class DoItLaterFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "com.dl.dlexerciseandroid.DoItLaterFragment";

    private Context mContext;

    private RecyclerView mTaskList;
    private StaggeredGridLayoutManager mTaskListLayoutManager;
    private DoItLaterAdapter mDoItLaterAdapter;
    private List<Task> mTaskListDataSet = new ArrayList<>();

    private FloatingActionButton mAddTaskButton;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_do_it_later, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupTaskList();
    }

    private void findViews() {
        mTaskList = (RecyclerView) getView().findViewById(R.id.recyclerView_do_it_later_task_list);
        mAddTaskButton = (FloatingActionButton) getView().findViewById(R.id.floating_action_button_do_it_later_add_task);
    }

    private void setupViews() {
        mAddTaskButton.setOnClickListener(this);
    }

    private void setupTaskList() {
        setTaskListData();

        mTaskListLayoutManager =
                new StaggeredGridLayoutManager(getResources().getInteger(R.integer.span_count_do_it_later_task_list),
                                               StaggeredGridLayoutManager.VERTICAL);

        // 這個地方就先將要顯示的data儲存List與Adapter bind在一起，之後要clear或是delete或是add資料，
        // 都在這裡對mTaskListDataSet進行操作
        mDoItLaterAdapter = new DoItLaterAdapter(mContext, mTaskListDataSet);

        // RecyclerView必須要設定的三個元件：
        // LayoutManager
        // RecyclerView.Adapter
        // Data List
        mTaskList.setLayoutManager(mTaskListLayoutManager);
        mTaskList.setAdapter(mDoItLaterAdapter);
    }

    private void setTaskListData() {
        mTaskListDataSet.add(new Task("Good", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good time Ya Ya", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good time Ya Ya", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good time Ya Ya", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good time Ya Ya", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good time Ya Ya", "Description", System.currentTimeMillis()));
        mTaskListDataSet.add(new Task("Good", "Description", System.currentTimeMillis()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floating_action_button_do_it_later_add_task:
                break;
        }
    }
}
