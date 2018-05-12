package com.dl.dlexerciseandroid.features.rxjava;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.dl.dlexerciseandroid.data.Repository;
import com.dl.dlexerciseandroid.utils.Utils;
import com.dl.dlexerciseandroid.widget.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * Created by logicmelody on 2018/4/1.
 */

public class RxJavaPresenter implements RxJavaContract.Presenter {

    private RxJavaContract.View mView;
    private Repository mRepository;
    private BaseSchedulerProvider mSchedulerProvider;

    private Disposable mDisposableTest5SecToast;
    private Disposable mDisposableTestFromArray;
    private Disposable mDisposableTestPrintHelloWorld;
    private Disposable mDisposableTestEmpty;
    private Disposable mDisposableTestFlatMap;
    private Disposable mDisposableTestLoadIronMan;
    private Disposable mDisposableTestConcat;


    public RxJavaPresenter(@NonNull RxJavaContract.View view,
                           @NonNull Repository repository,
                           @NonNull BaseSchedulerProvider schedulerProvider) {
        mView = view;
        mRepository = repository;
        mSchedulerProvider = schedulerProvider;
    }

    @Override
    public void start() {

    }

    @Override
    public void test5SecToast() {
        // 如果我們想要創造一個Observable每隔5秒鐘就發出一個data，可以使用interval的運算子
        // 以下的範例，是每隔5秒鐘會發出一個data，但是這個data只有累加的數字而已，如果我們想要做一些其他的事情，
        // 比如：每隔5秒鐘就呼叫一次API，則需要再串上flatMap產生另外一個Observable
        // Note: 要注意的是，interval的運算子，會造成這個Observable永遠存在，就算退出app之後，還是會繼續發送data，
        //       所以記得要用Disposable的dispose()來將Observer跟Observable斷開
        Observable.interval(5, TimeUnit.SECONDS)
                .flatMap((Function<Long, ObservableSource<String>>) aLong -> {
                    mView.showLog("test5SecToast()", "Interval count = " + aLong);

                    return Observable.create(emitter -> {
                        emitter.onNext("Show interval count = " + aLong);
                        emitter.onComplete();
                    });
                })
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposableTest5SecToast = d;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.showToast(s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    // TODO: 需要再處理unsubscribe
    @Override
    public void testFromArray() {
        String[] names = new String[] {"DANNY", "Steven", "D LUFFY", "Kobe"};

        // Observable用來產生資料
        // Emits "Hello World"
        // 由於RxJava的預設規則，事件的發出和消費都是在同一個thread，只用下面的做法，實作出來的只是一個同一條thread的觀察者模式
        Observable.fromArray(names)

                // 可以用來過濾資料
                .filter(s -> s.startsWith("D"))

                // 只取前面兩筆資料
                .take(2)

                // 取出來的資料重複三次
                .repeat(3)

                // 消除重複的資料
                //.distinct()

                // 有新的資料，也就是跟之前資料不同的時候，才會發出新的資料
                .distinctUntilChanged()

                // 每30秒才會更新一次資料，抓取最後一筆資料，take the last
                //.sample(30, TimeUnit.SECONDS)

                // 每30秒才會更新一次資料，抓取第一筆資料，take the first
                //.throttleFirst(30, TimeUnit.SECONDS)

                // 可以利用map將原始資料做一次轉換，轉換成我們想要的
                .map(String::toLowerCase)

                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())

                // 這個會執行在onComplete()或是onError()之前
                .doOnTerminate(() -> mView.showLog("testFromArray()", "doOnTerminate"))

                // 這個會執行在onComplete()或是onError()之後
                .doAfterTerminate(() -> mView.showLog("testFromArray()", "doAfterTerminate"))

                // Subscribe Observer，Observer只需要管拿到data之後要做什麼事情，在這邊就是把log印出來
                // Observer用來接收Observable產生的資料，Observer有多種callback可以使用
                // 所有的事件會是存在一個queue中，當queue中的事件全部都執行完畢，或是中間有出現錯誤的時候，
                // 會call onComplete()跟onError()，onComplete()跟onError()彼此是互斥，只會有一個執行
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLog("testFromArray()", "onSubscribe!");

                        mDisposableTestFromArray = d;
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        mView.showLog("testFromArray()", "onNext = " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showLog("testFromArray()", "onError = " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        mView.showLog("testFromArray()", "onComplete!");
                    }
                });
    }

    @Override
    public void testPrintHelloWorld() {
        // Observable用來產生資料，如果是用create()的方法，產生資料的方法都要自己處理
        // Emits "Hello World"
        Observable.create((ObservableOnSubscribe<String>) subscriber -> {
            // 會對應到Observer的callback：onNext(), onComplete(), onError()
            subscriber.onNext("Hello World");
            //subscriber.onError(new Throwable());
            subscriber.onComplete();
        })
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())

                .doOnTerminate(() -> mView.showLog("testPrintHelloWorld()", "doOnTerminate"))

                .doAfterTerminate(() -> mView.showLog("testPrintHelloWorld()", "doAfterTerminate"))

                // Observer用來接收Observable產生的資料，Observer有多種callback可以使用
                // 所有的事件會是存在一個queue中，當queue中的事件全部都執行完畢，或是中間有出現錯誤的時候，
                // 會call onComplete()跟onError()，onComplete()跟onError()彼此是互斥，只會有一個執行
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mView.showLog("testPrintHelloWorld()", "onSubscribe!");
                        mDisposableTestPrintHelloWorld = d;
                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        // Called each time the observable emits data
                        mView.showLog("testPrintHelloWorld()", "onNext = " + s);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        // Called when the observable encounters an error
                        mView.showLog("testPrintHelloWorld()", "onError = " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        // Called when the observable has no more data to emit
                        mView.showLog("testPrintHelloWorld()", "onComplete!");
                    }
                });
    }

    @Override
    public void testEmpty() {
        Observable.empty()
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposableTestEmpty = d;
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

    @Override
    public void testFlatMap() {
        List<Integer> list = new ArrayList<>();

        for (int i = 0 ; i < 10 ; i++) {
            list.add(i);
        }

        Observable.fromArray(list)
                // 第一階段取得的資料是一個ArrayList包含很多Integer：List<Integer>
                // 我們利用flatMap()將第一階段的資料轉成另一個Observable包含很多個String：Observable.fromArray(strings.toArray(new String[strings.size()]))
                .flatMap((Function<List<Integer>, ObservableSource<String>>) integers -> {
                    List<String> strings = convertIntegerListToStringList(integers);

                    return Observable.fromArray(strings.toArray(new String[strings.size()]));
                })
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())

                .doOnTerminate(() -> mView.showLog("testFlatMap()", "doOnTerminate"))

                .doAfterTerminate(() -> mView.showLog("testFlatMap()", "doAfterTerminate"))

                // Observer處理的是很多個String
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposableTestFlatMap = d;
                    }

                    @Override
                    public void onNext(String s) {
                        mView.showLog("testFlatMap()", "onNext = " + s);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mView.showLog("testFlatMap()", "onComplete");
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

    @Override
    public void loadIronMan() {
        Observable.create((ObservableOnSubscribe<Drawable>) subscriber -> {
            // 處理background load image的工作
            //Thread.sleep(3000);

            // 2. Thread create() = RxCachedThreadScheduler-1
            mView.showLog("loadIronMan()", "Thread create() = " + Thread.currentThread().getName());

            Drawable drawable = mView.getIronManDrawable();
            subscriber.onNext(drawable);
            subscriber.onComplete();
        })
                .doOnNext(drawable -> {
                    // 3. Thread doOnNext() = RxCachedThreadScheduler-1
                    mView.showLog("loadIronMan()", "Thread doOnNext() = " + Thread.currentThread().getName());

                    Thread.sleep(3000);
                })

                // 這個Scheduler主要用來執行存取disk的資料或是網路存取資料，不是在UI thread，所以我們可以進行些需要耗費時間的工作，
                // 甚至可以用Thread.sleep()把工作暫停
                .subscribeOn(mSchedulerProvider.io())

                // 這個Scheduler主要用來執行比較需要花時間和大量的運算
                //.subscribeOn(Schedulers.computation())

                // Observer在UI thread執行
                .observeOn(mSchedulerProvider.ui())

                .doOnTerminate(() -> mView.showLog("loadIronMan()", "doOnTerminate"))

                .doAfterTerminate(() -> mView.showLog("loadIronMan()", "doAfterTerminate"))

                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        // 1. Thread subscribe() = main
                        Log.d("danny", "Thread subscribe() = " + Thread.currentThread().getName());

                        mView.showLog("loadIronMan()", "onSubscribe!");
                        mDisposableTestLoadIronMan = d;
                    }

                    @Override
                    public void onNext(@NonNull Drawable drawable) {
                        mView.showLog("loadIronMan()", "onNext = drawable");
                        mView.setIronManImageView(drawable);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showLog("loadIronMan()", "onError = " + e.toString());
                        mView.showToast("Iron Man Error!");
                    }

                    @Override
                    public void onComplete() {
                        mView.showLog("loadIronMan()", "onComplete!");
                        mView.showToast("Iron Man Completed!");
                    }
                });
    }

    @Override
    public void testConcat() {
        // 組合多個Observable後再一起發送data，發送的順序是按照順序執行
        // concat(): 組合的Observable <= 4
        // concatArray(): 組合的Observable > 4
        Observable.concatArray(
                Observable.just(1, 2),
                Observable.just(3, 4),
                Observable.just(5, 6),
                Observable.just(7, 8),
                Observable.just(9, 10))

                // doOnNext()執行的thread會跟observeOn()所設定的thread一樣
                // 在這邊執行的話，因為還沒有決定observeOn()要執行在哪一條thread，但是已經有設定subscribeOn()的thread，所以
                // observeOn()的thread會跟subscribeOn()的thread一樣，因此doOnNext()也會執行在跟subscribeOn()一樣的thread
//                .doOnNext(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        mView.showLog("testConcat()",
//                                "doOnNext data is " + integer + ", thread in " + Thread.currentThread().getName());
//                    }
//                })

                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())

                // doOnNext()執行的thread會跟observeOn()所設定的thread一樣
                // 在這邊執行的話，因為已經決定observeOn()是執行在UI thread，但是已經有設定subscribeOn()的thread，所以
                // doOnNext()也會執行在跟observeOn()一樣的thread
                .doOnNext(integer -> mView.showLog("testConcat()",
                        "doOnNext data is " + integer + ", thread in " + Thread.currentThread().getName()))
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposableTestConcat = d;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        mView.showLog("testConcat()", "Current data is " + integer);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        mView.showLog("testConcat()", "onComplete()!");
                    }
                });
    }

    @Override
    public void stop5SecToast() {
        if (!Utils.isObserverDisposed(mDisposableTest5SecToast)) {
            mDisposableTest5SecToast.dispose();
        }
    }

    @Override
    public void onDestroy() {
        if (!Utils.isObserverDisposed(mDisposableTest5SecToast)) {
            mDisposableTest5SecToast.dispose();
        }

        if (!Utils.isObserverDisposed(mDisposableTestFromArray)) {
            mDisposableTestFromArray.dispose();
        }

        if (!Utils.isObserverDisposed(mDisposableTestPrintHelloWorld)) {
            mDisposableTestPrintHelloWorld.dispose();
        }

        if (!Utils.isObserverDisposed(mDisposableTestEmpty)) {
            mDisposableTestEmpty.dispose();
        }

        if (!Utils.isObserverDisposed(mDisposableTestFlatMap)) {
            mDisposableTestFlatMap.dispose();
        }

        if (!Utils.isObserverDisposed(mDisposableTestLoadIronMan)) {
            mDisposableTestLoadIronMan.dispose();
        }
    }
}
