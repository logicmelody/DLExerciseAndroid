package com.dl.dlexerciseandroid.ui.stackoverflow;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.model.stackoverflow.SOAnswersResponse;
import com.dl.dlexerciseandroid.model.stackoverflow.SOItem;
import com.dl.dlexerciseandroid.api.ApiHelper;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class StackOverflowFragment extends Fragment {

    // 以後Fragment的tag name都用此class的name來命名比較方便
    // e.g. MusicPlayerFragment
    public static final String TAG = StackOverflowFragment.class.getName();

    private Context mContext;

    private RecyclerView mSORecyclerView;
    private StackOverflowAdapter mStackOverflowAdapter;

    private ProgressBar mProgressBar;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stack_overflow, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
        loadAnswers();
    }

    private void initialize() {
        findViews();
        setupSORecyclerView();
    }

    private void findViews() {
        mSORecyclerView = (RecyclerView) getView().findViewById(R.id.recycler_view_stack_overflow_list);
        mProgressBar = (ProgressBar) getView().findViewById(R.id.progress_bar_stack_overflow_loading);
    }

    private void setupSORecyclerView() {
        mStackOverflowAdapter = new StackOverflowAdapter(mContext);

        mSORecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mSORecyclerView.setAdapter(mStackOverflowAdapter);
    }

    private void loadAnswers() {
        ApiHelper.generateSOApi().getAnswers()
                .flatMap(new Function<SOAnswersResponse, ObservableSource<SOItem>>() {
                    @Override
                    public ObservableSource<SOItem> apply(SOAnswersResponse soAnswersResponse) throws Exception {
                        return Observable.fromIterable(soAnswersResponse.getItems());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SOItem>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SOItem soItem) {
                        mStackOverflowAdapter.add(soItem);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("danny", "StackOverflow onError() = " + e.toString());

                        mProgressBar.setVisibility(View.GONE);

                        Toast.makeText(mContext, "StackOverflow onError()", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        mStackOverflowAdapter.refresh();
                        mProgressBar.setVisibility(View.GONE);

                        Toast.makeText(mContext, "StackOverflow onComplete()", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
