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

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
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
        testRxJava();
        testRxJava2();
        testRxJava3();
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

    private void testRxJava() {
        appendLog("====== testRxJava() ======");

        // Observable用來產生資料
        // Emits "Hello"
        Observable<String> myObservable = Observable.just("Hello", "World");

        // Observer用來接收Observable產生的資料，Observer有多種callback可以使用
        // 所有的事件會是存在一個queue中，當queue中的事件全部都執行完畢，或是中間有出現錯誤的時候，
        // 會call onComplete()跟onError()，onComplete()跟onError()彼此是互斥，只會有一個執行
        Observer<String> myObserver = new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                showLog("onSubscribe!");
            }

            @Override
            public void onNext(String value) {
                // Called each time the observable emits data
                showLog("onNext = " + value);
            }

            @Override
            public void onError(Throwable e) {
                // Called when the observable encounters an error
                showLog("onError = " + e.toString());
                appendLog("======================");
            }

            @Override
            public void onComplete() {
                // Called when the observable has no more data to emit
                showLog("onComplete!");
                appendLog("======================");
                setLogText();
            }
        };

        // Subscribe之後才會執行Observer裡的callback動作
        myObservable.subscribe(myObserver);
    }

    private void appendLog(String log) {
        if (TextUtils.isEmpty(log)) {
            return;
        }

        mLogStringBuilder.append(log).append("\n");
    }

    private void showLog(String log) {
        Log.d("danny", log);
        appendLog(log);
    }

    private void testRxJava2() {
        appendLog("====== testRxJava2() ======");

        String[] names = new String[] {"Danny", "Steven", "Kobe"};

        // 由於RxJava的預設規則，事件的發出和消費都是在同一個thread，只用下面的做法，實作出來的只是一個同一條thread的觀察者模式
        Observable.fromArray(names)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showLog("onSubscribe!");
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        showLog("onNext = " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showLog("onError = " + e.toString());
                        appendLog("======================");
                    }

                    @Override
                    public void onComplete() {
                        showLog("onComplete!");
                        appendLog("======================");
                        setLogText();
                    }
                });
    }

    private void testRxJava3() {
        appendLog("====== testRxJava3() ======");

        Observable.just(1, 2, 3, 4, 5)

                // 以下這兩行的設定非常常見，適用於多數的background thread取資料，main thread顯示UI
                .subscribeOn(Schedulers.io()) // 指定Observable執行在IO thread
                .observeOn(AndroidSchedulers.mainThread()) // 指定Observer執行在main thread

                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showLog("onSubscribe!");
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        showLog("onNext = " + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showLog("onError = " + e.toString());
                        appendLog("======================");
                    }

                    @Override
                    public void onComplete() {
                        showLog("onComplete!");
                        appendLog("======================");
                        setLogText();
                    }
                });
    }

    private void loadIronMan() {
        Observable.create(new ObservableOnSubscribe<Drawable>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Drawable> subscriber) throws Exception {
                Drawable drawable = mContext.getResources().getDrawable(R.drawable.poster_iron_man);
                subscriber.onNext(drawable);
                subscriber.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Drawable drawable) {
                        mIronManImageView.setImageDrawable(drawable);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(mContext, "Iron Man Error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void setLogText() {
        mLogTextView.setText(mLogStringBuilder.toString());
    }
}
