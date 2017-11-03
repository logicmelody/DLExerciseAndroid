package com.dl.dlexerciseandroid.ui.doitlater.tasklist.later;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.database.dbscheme.DLExerciseContract;
import com.dl.dlexerciseandroid.model.Task;
import com.dl.dlexerciseandroid.ui.doitlater.tasklist.main.DoItLaterViewItem;

/**
 * Created by logicmelody on 2016/4/18.
 */

// 利用LoaderManager.LoaderCallbacks跟ContentProvider，可以將顯示data的UI(RecyclerView)跟load db裡的資料分開
// RecyclerView: 只需要bind好Adapter就好
// LoaderManager.LoaderCallbacks: load資料的部分就交給LoaderManager.LoaderCallbacks，只要DB的資料有更新
// 就會呼叫onLoadFinished()這個callback method，我們可以在這個method裡面拿到最新的data
public class LaterTaskFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = LaterTaskFragment.class.getName();

    private static int LOADER_ID = 12;

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

    private static String mSelection = DLExerciseContract.Task.LATER_CALL_BACK + " IS NOT NULL";
    protected static String[] mSelectionArgs;

    // 在order欄位的後面加上"ASC" or "DESC"，可以指定要由小到大 or 由大到小排序
    private static final String mSortOrder = DLExerciseContract.Task.TIME + " DESC";

    private Context mContext;

    private RecyclerView mTaskList;
    private StaggeredGridLayoutManager mTaskListLayoutManager;
    private LaterTaskAdapter mLaterTaskAdapter;

    private TextView mNoTaskText;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_later_task, container, false);
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
        setupViews();
        setupTaskList();
    }

    private void findViews() {
        mTaskList = (RecyclerView) getView().findViewById(R.id.recycler_view_do_it_later_task_list);
        mNoTaskText = (TextView) getView().findViewById(R.id.text_view_do_it_later_task_list_no_task);
    }

    private void setupViews() {

    }

    private void setupTaskList() {
        mTaskListLayoutManager =
                new StaggeredGridLayoutManager(getResources().getInteger(R.integer.span_count_do_it_later_task_list),
                                               StaggeredGridLayoutManager.VERTICAL);

        // 這個地方就先將要顯示的data儲存List與Adapter bind在一起，之後要clear或是delete或是add資料，
        // 都在這裡對mTaskListDataSet進行操作
        mLaterTaskAdapter = new LaterTaskAdapter(mContext, mTaskList);

        // RecyclerView必須要設定的三個元件：
        // LayoutManager
        // RecyclerView.Adapter
        // Data List
        mTaskList.setLayoutManager(mTaskListLayoutManager);
        mTaskList.setAdapter(mLaterTaskAdapter);

        // Swipe to delete
        ItemTouchHelper.Callback callback = new LaterTaskItemTouchHelper(mLaterTaskAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mTaskList);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mContext, DLExerciseContract.Task.CONTENT_URI, mProjection, mSelection, null, mSortOrder);
    }

    // 在這個method裡面使用完cursor之後，不需要去把cursor關掉，LoaderManager會自動處理掉這件事
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d("danny", "Do It Later onLoadFinished()");

        // Cursor的data count=0，代表沒有data符合我們下的query SQL語法
        // Cursor若是null，代表在query的時候發生錯誤，也有可能會丟出Exception
        if (data == null) {
            return;
        }

        setTaskListData(data);
    }

    private void setTaskListData(Cursor data) {
        mLaterTaskAdapter.clear();

        while (data.moveToNext()) {
            long id = data.getLong(ID);
            String title = data.getString(TITLE);
            String description = data.getString(DESCRIPTION);
            String laterPackageName = data.getString(LATER_PACKAGE_NAME);
            String laterCallback = data.getString(LATER_CALL_BACK);
            long time = data.getLong(TIME);
            int viewType = TextUtils.isEmpty(laterCallback) ?
                    DoItLaterViewItem.ViewType.NORMAL : DoItLaterViewItem.ViewType.LATER;

            Task task = new Task(id, title, description, laterPackageName, laterCallback, time);

            mLaterTaskAdapter.add(new DoItLaterViewItem(task, viewType));
        }

        mLaterTaskAdapter.notifyDataSetChanged();
        mNoTaskText.setVisibility(mLaterTaskAdapter.getDataListSize() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
