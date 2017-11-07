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

public class RxJavaFragment extends Fragment {

    // 以後Fragment的tag name都用此class的name來命名比較方便
    // e.g. MusicPlayerFragment
    public static final String TAG = RxJavaFragment.class.getName();

    private Context mContext;

    private TextView mLogTextView;
    private ImageView mIronManImageView;

    private StringBuilder mLogStringBuilder;


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
        initialize();
        rxJavaFromArray();
        printHelloWorld();
        rxJavaEmpty();
        rxJavaFlatMap();
        loadIronMan();
    }

    private void initialize() {
        mLogStringBuilder = new StringBuilder();

        findViews();
    }

    private void findViews() {
        mLogTextView = (TextView) getView().findViewById(R.id.text_view_rxjava_log);
        mIronManImageView = (ImageView) getView().findViewById(R.id.image_view_rxjava_iron_man);
    }

    private void rxJavaFromArray() {
        String[] names = new String[] {"DANNY", "Steven", "D LUFFY", "Kobe"};

        // Observable用來產生資料
        // Emits "Hello World"
        // 由於RxJava的預設規則，事件的發出和消費都是在同一個thread，只用下面的做法，實作出來的只是一個同一條thread的觀察者模式
        Observable.fromArray(names)

                // 可以用來過濾資料
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        return s.startsWith("D");
                    }
                })

                // 只取前面兩筆資料
                .take(2)

                // 取出來的資料重複三次
                .repeat(3)

                // 消除重複的資料
                .distinct()

                // 有新的資料，也就是跟之前資料不同的時候，才會發出新的資料
                .distinctUntilChanged()

                // 每30秒才會更新一次資料，抓取最後一筆資料，take the last
                //.sample(30, TimeUnit.SECONDS)

                // 每30秒才會更新一次資料，抓取第一筆資料，take the first
                //.throttleFirst(30, TimeUnit.SECONDS)

                // 可以利用map將原始資料做一次轉換，轉換成我們想要的
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        return s.toLowerCase();
                    }
                })

                // Subscribe Observer，Observer只需要管拿到data之後要做什麼事情，在這邊就是把log印出來
                // Observer用來接收Observable產生的資料，Observer有多種callback可以使用
                // 所有的事件會是存在一個queue中，當queue中的事件全部都執行完畢，或是中間有出現錯誤的時候，
                // 會call onComplete()跟onError()，onComplete()跟onError()彼此是互斥，只會有一個執行
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showLog("rxJavaFromArray()", "onSubscribe!");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        showLog("rxJavaFromArray()", "onNext = " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showLog("rxJavaFromArray()", "onError = " + e.toString());
                        setLogText();
                    }

                    @Override
                    public void onComplete() {
                        showLog("rxJavaFromArray()", "onComplete!");
                        setLogText();
                    }
                });
    }

    private void printHelloWorld() {
        // Observable用來產生資料
        // Emits "Hello World"
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> subscriber) throws Exception {
                // 會對應到Observer的callback：onNext(), onComplete(), onError()
                subscriber.onNext("Hello World");
                //subscriber.onError(new Throwable());
                subscriber.onComplete();
            }
        })
                // Observer用來接收Observable產生的資料，Observer有多種callback可以使用
                // 所有的事件會是存在一個queue中，當queue中的事件全部都執行完畢，或是中間有出現錯誤的時候，
                // 會call onComplete()跟onError()，onComplete()跟onError()彼此是互斥，只會有一個執行
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showLog("printHelloWorld()", "onSubscribe!");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        // Called each time the observable emits data
                        showLog("printHelloWorld()", "onNext = " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // Called when the observable encounters an error
                        showLog("printHelloWorld()", "onError = " + e.toString());
                        setLogText();
                    }

                    @Override
                    public void onComplete() {
                        // Called when the observable has no more data to emit
                        showLog("printHelloWorld()", "onComplete!");
                        setLogText();
                    }
                });
    }

    private void rxJavaEmpty() {
        Observable.empty()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object o) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void rxJavaFlatMap() {
        List<Integer> list = new ArrayList<>();

        for (int i = 0 ; i < 10 ; i++) {
            list.add(i);
        }

        Observable.fromArray(list)
                // 第一階段取得的資料是一個ArrayList包含很多Integer：List<Integer>
                // 我們利用flatMap()將第一階段的資料轉成另一個Observable包含很多個String：Observable.fromArray(strings.toArray(new String[strings.size()]))
                .flatMap(new Function<List<Integer>, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(List<Integer> integers) throws Exception {
                        List<String> strings = convertIntegerListToStringList(integers);

                        return Observable.fromArray(strings.toArray(new String[strings.size()]));
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())

                // Observer處理的是很多個String
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        showLog("rxJavaFlatMap()", "onNext = " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private List<String> convertIntegerListToStringList(List<Integer> list) {
        List<String> result = new ArrayList<>();

        if (list == null || list.size() == 0) {
            return result;
        }

        for (int num : list) {
            result.add(convertIntegerToString(num));
        }

        return result;
    }

    private String convertIntegerToString(int num) {
        if (num <= 0) {
            return "zero";
        }

        String result = "";

        switch (num) {
            case 1:
                return "one";

            case 2:
                return "two";

            case 3:
                return "three";

            case 4:
                return "four";

            case 5:
                return "five";

            case 6:
                return "six";

            case 7:
                return "seven";

            case 8:
                return "eight";

            case 9:
                return "nine";
        }

        return result;
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

    private void showLog(String tag, String log) {
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

    private void setLogText() {
        mLogTextView.setText(mLogStringBuilder.toString());
    }
}
