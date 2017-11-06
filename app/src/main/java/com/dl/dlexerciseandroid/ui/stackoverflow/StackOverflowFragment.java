package com.dl.dlexerciseandroid.ui.stackoverflow;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.model.stackoverflow.SOAnswersResponse;
import com.dl.dlexerciseandroid.model.stackoverflow.SOItem;
import com.dl.dlexerciseandroid.utility.utils.ApiUtils;

import java.util.List;

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
    }

    private void findViews() {

    }

    private void loadAnswers() {
        ApiUtils.generateSOApi().getAnswers()
                .flatMap(new Function<SOAnswersResponse, ObservableSource<SOItem>>() {
                    @Override
                    public ObservableSource<SOItem> apply(SOAnswersResponse soAnswersResponse) throws Exception {
                        List<SOItem> list = soAnswersResponse.getItems();

                        return Observable.fromArray(list.toArray(new SOItem[list.size()]));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SOItem>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("danny", "StackOverflow onSubscribe()");
                    }

                    @Override
                    public void onNext(SOItem soItem) {
                        Log.d("danny", "StackOverflow SOItem = " + soItem.getSOOwner().getDisplayName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("danny", "StackOverflow onError() = " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("danny", "StackOverflow onComplete()");
                    }
                });
    }
}
