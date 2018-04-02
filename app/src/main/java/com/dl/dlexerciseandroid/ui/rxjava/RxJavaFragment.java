package com.dl.dlexerciseandroid.ui.rxjava;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class RxJavaFragment extends Fragment implements RxJavaContract.View {

    // 以後Fragment的tag name都用此class的name來命名比較方便
    // e.g. MusicPlayerFragment
    public static final String TAG = RxJavaFragment.class.getName();

    private Context mContext;
    private RxJavaContract.Presenter mPresenter;

    private StringBuilder mLogStringBuilder;

    @BindView(R.id.text_view_rxjava_log)
    public TextView mLogTextView;

    @BindView(R.id.image_view_rxjava_iron_man)
    public ImageView mIronManImageView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rxjava, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        mPresenter = new RxJavaPresenter(this);

        initialize();

        mPresenter.testFromArray();
        mPresenter.testPrintHelloWorld();
        mPresenter.testEmpty();
        mPresenter.testFlatMap();
        mPresenter.testFlatMap();
        mPresenter.loadIronMan();
    }

    private void initialize() {
        mLogStringBuilder = new StringBuilder();
    }

    private void loadIronMan() {
        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Drawable> subscriber) throws Exception {
                // 處理background load image的工作
                //Thread.sleep(3000);

                // 2. Thread create() = RxCachedThreadScheduler-1
                Log.d("danny", "Thread create() = " + Thread.currentThread().getName());

                Drawable drawable = mContext.getResources().getDrawable(R.drawable.poster_iron_man);
                subscriber.onNext(drawable);
                subscriber.onComplete();
            }
        })
                // 跟產生資料的thread是同一個thread
                .doOnNext(new Consumer<Drawable>() {
                    @Override
                    public void accept(Drawable drawable) throws Exception {
                        // 3. Thread doOnNext() = RxCachedThreadScheduler-1
                        Log.d("danny", "Thread doOnNext() = " + Thread.currentThread().getName());

                        Thread.sleep(3000);
                    }
                })

                // 這個Scheduler主要用來執行存取disk的資料或是網路存取資料，不是在UI thread，所以我們可以進行些需要耗費時間的工作，
                // 甚至可以用Thread.sleep()把工作暫停
                .subscribeOn(Schedulers.io())

                // 這個Scheduler主要用來執行比較需要花時間和大量的運算
                //.subscribeOn(Schedulers.computation())

                // Observer在UI thread執行
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // 1. Thread subscribe() = main
                        Log.d("danny", "Thread subscribe() = " + Thread.currentThread().getName());

                        showLog("loadIronMan()", "onSubscribe!");
                    }

                    @Override
                    public void onNext(@NonNull Drawable drawable) {
                        showLog("loadIronMan()", "onNext = drawable");

                        mIronManImageView.setImageDrawable(drawable);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showLog("loadIronMan()", "onError = " + e.toString());
                        setLogText();

                        Toast.makeText(mContext, "Iron Man Error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        showLog("loadIronMan()", "onComplete!");
                        setLogText();
                    }
                });
    }

    @Override
    public void showLog(String tag, String log) {
        String printLog = tag + " " + log;

        Log.d("danny", printLog);
        appendLog(printLog);
    }

    private void appendLog(String log) {
        if (TextUtils.isEmpty(log)) {
            return;
        }

        mLogStringBuilder.append(log).append("\n");
    }

    @Override
    public void setLogText() {
        mLogTextView.setText(mLogStringBuilder.toString());
    }

    @Override
    public Drawable getIronManDrawable() {
        return getResources().getDrawable(R.drawable.poster_iron_man);
    }

    @Override
    public void setIronManImageView(Drawable drawable) {
        mIronManImageView.setImageDrawable(drawable);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(mContext, "Iron Man Error!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(RxJavaContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
