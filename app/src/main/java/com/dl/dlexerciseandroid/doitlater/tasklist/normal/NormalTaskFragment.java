package com.dl.dlexerciseandroid.doitlater.tasklist.normal;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.database.dbscheme.DLExerciseContract;
import com.dl.dlexerciseandroid.datastructure.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by logicmelody on 2016/5/4.
 */
public class NormalTaskFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "com.dl.dlexerciseandroid.NormalTaskFragment";

    private static final int LOADER_ID = 66;

    private static final String[] mProjection = new String[] {
            DLExerciseContract.Task._ID,
            DLExerciseContract.Task.TITLE,
            DLExerciseContract.Task.DESCRIPTION,
            DLExerciseContract.Task.TIME,
            DLExerciseContract.Task.LATER_PACKAGE_NAME,
            DLExerciseContract.Task.LATER_CALL_BACK
    };
    private static final int ID = 0;
    private static final int TITLE = 1;
    private static final int DESCRIPTION = 2;
    private static final int TIME = 3;
    private static final int LATER_PACKAGE_NAME = 4;
    private static final int LATER_CALL_BACK = 5;

    private static String mSelection = DLExerciseContract.Task.LATER_CALL_BACK + " IS NULL";
    protected static String[] mSelectionArgs;

    // 在order欄位的後面加上"ASC" or "DESC"，可以指定要由小到大 or 由大到小排序
    private static final String mSortOrder = DLExerciseContract.Task.TIME + " DESC";

    private Context mContext;

    private RecyclerView mTaskList;
    private NormalTaskAdapter mNormalTaskAdapter;
    private LinearLayoutManager mTaskListLayoutManager;

    private TextView mNoTextView;

    private List<Task> mTaskData = new ArrayList<>();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_normal_task, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();

        if (savedInstanceState == null) {
            getLoaderManager().initLoader(LOADER_ID, null, this);

        } else {
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    private void initialize() {
        findViews();
        setupNormalTaskList();
    }

    private void findViews() {
        mTaskList = (RecyclerView) getView().findViewById(R.id.recyclerView_do_it_later_normal_task_list);
        mNoTextView = (TextView) getView().findViewById(R.id.text_view_do_it_later_task_list_no_task);
    }

    private void setupNormalTaskList() {
        mTaskListLayoutManager = new LinearLayoutManager(mContext);
        mNormalTaskAdapter = new NormalTaskAdapter(mContext, mTaskData);

        mTaskList.setLayoutManager(mTaskListLayoutManager);
        mTaskList.setAdapter(mNormalTaskAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mContext, DLExerciseContract.Task.CONTENT_URI, mProjection, mSelection, null, mSortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("danny", "Do It Later onLoadFinished()");

        if (data == null) {
            return;
        }

        setTaskListData(data);
    }

    private void setTaskListData(Cursor data) {
        mTaskData.clear();

        while (data.moveToNext()) {
            int id = data.getInt(ID);
            String title = data.getString(TITLE);
            String description = data.getString(DESCRIPTION);
            String laterPackageName = data.getString(LATER_PACKAGE_NAME);
            String laterCallback = data.getString(LATER_CALL_BACK);
            long time = data.getLong(TIME);

            mTaskData.add(new Task(title, description, laterPackageName, laterCallback, time));
        }

        mNormalTaskAdapter.notifyDataSetChanged();
        mNoTextView.setVisibility(mTaskData.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
