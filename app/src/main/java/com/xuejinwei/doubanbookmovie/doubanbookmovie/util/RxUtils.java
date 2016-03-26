package com.xuejinwei.doubanbookmovie.doubanbookmovie.util;

import android.os.Handler;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.android.schedulers.HandlerScheduler;
import rx.schedulers.Schedulers;

/**
 * Created by xuejinwei on 15/12/24.
 * 使用 ReactiveX 时常用的一些方法包装
 */
public class RxUtils {
    /**
     * 在 UI 线程定于并执行于一个新线程
     */
    public static <T> Observable<T> callOnUI(Observable<T> observable) {
        return observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    /**
     * 在 UI 线程定于并为执行线程指定一个调度器
     */
    public static <T> Observable<T> callOnThread(Observable<T> observable, Handler handler) {
        return observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(HandlerScheduler.from(handler));
    }
}
